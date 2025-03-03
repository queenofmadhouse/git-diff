package dev.kalachik.gitdiff;

import dev.kalachik.gitdiff.api.LocalGitClient;
import dev.kalachik.gitdiff.api.RemoteGitClient;
import dev.kalachik.gitdiff.exception.GitDiffException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DiffAnalyzerTest {

    private RemoteGitClient mockRemoteClient;
    private LocalGitClient mockLocalClient;
    private DiffAnalyzer diffAnalyzer;

    @BeforeEach
    void setUp() {
        mockRemoteClient = Mockito.mock(RemoteGitClient.class);
        mockLocalClient = Mockito.mock(LocalGitClient.class);
        diffAnalyzer = new DiffAnalyzer(mockRemoteClient, mockLocalClient);
    }

    @Test
    void testAnalyzeDiffReturnsIntersection() throws GitDiffException {
        String mergeBase = "abc123";

        // Define test data: remote and local changed files lists.
        List<String> remoteFiles = Arrays.asList("file1.txt", "file2.txt", "file3.txt");
        List<String> localFiles = Arrays.asList("file2.txt", "file3.txt", "file4.txt");

        // Stub methods on the mocks.
        when(mockLocalClient.findMergeBase("remoteBranch", "localBranch")).thenReturn(mergeBase);
        when(mockRemoteClient.getChangedFiles("owner", "repo", "remoteBranch", mergeBase, "token"))
                .thenReturn(remoteFiles);
        when(mockLocalClient.getChangedFiles("localBranch", mergeBase)).thenReturn(localFiles);

        List<String> commonFiles = diffAnalyzer.analyzeDiff("owner", "repo", "remoteBranch", "localBranch", "some/path", "token");

        // The intersection should be file2.txt and file3.txt.
        assertNotNull(commonFiles);
        assertEquals(2, commonFiles.size());
        assertTrue(commonFiles.contains("file2.txt"));
        assertTrue(commonFiles.contains("file3.txt"));
    }

    @Test
    void testAnalyzeDiffPropagatesException() throws GitDiffException {
        // Stub the findMergeBase to throw an exception.
        when(mockLocalClient.findMergeBase("remoteBranch", "localBranch"))
                .thenThrow(new GitDiffException("Merge base error"));

        GitDiffException exception = assertThrows(GitDiffException.class,
                () -> diffAnalyzer.analyzeDiff("owner", "repo", "remoteBranch", "localBranch", "some/path", "token"));
        assertTrue(exception.getMessage().contains("Merge base error"));
    }
}
