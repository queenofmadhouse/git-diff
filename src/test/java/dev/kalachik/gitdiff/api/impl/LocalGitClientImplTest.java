package dev.kalachik.gitdiff.api.impl;

import dev.kalachik.gitdiff.exception.GitDiffException;
import dev.kalachik.gitdiff.util.ProcessExecutor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

class LocalGitClientImplTest {

    private ProcessExecutor mockExecutor;
    private LocalGitClientImpl localGitClient;
    private final String repoPath = "/fake/repo";

    @BeforeEach
    void setUp() {
        mockExecutor = Mockito.mock(ProcessExecutor.class);
        localGitClient = new LocalGitClientImpl(repoPath, mockExecutor);
    }

    @Test
    void testFindMergeBase() throws GitDiffException {
        // Simulate git merge-base command output
        when(mockExecutor.execute(eq(repoPath), any(String[].class)))
                .thenReturn("abc123");

        String mergeBase = localGitClient.findMergeBase("branchA", "master");
        assertEquals("abc123", mergeBase);
    }

    @Test
    void testGetChangedFiles() throws GitDiffException {
        // Simulate git diff command output
        String diffOutput = "file1.txt\nfile2.txt\n";
        when(mockExecutor.execute(eq(repoPath), any(String[].class)))
                .thenReturn(diffOutput);

        List<String> changedFiles = localGitClient.getChangedFiles("master", "abc123");
        assertEquals(2, changedFiles.size());
        assertTrue(changedFiles.contains("file1.txt"));
        assertTrue(changedFiles.contains("file2.txt"));
    }
}
