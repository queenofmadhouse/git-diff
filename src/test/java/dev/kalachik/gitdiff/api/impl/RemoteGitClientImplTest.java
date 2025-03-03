package dev.kalachik.gitdiff.api.impl;

import dev.kalachik.gitdiff.exception.GitDiffException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;

import java.lang.reflect.Field;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RemoteGitClientImplTest {

    private RemoteGitClientImpl remoteGitClient;
    private HttpClient mockHttpClient;
    private HttpResponse<String> mockHttpResponse;

    @BeforeEach
    void setUp() throws Exception {
        remoteGitClient = new RemoteGitClientImpl();
        // Create mock HttpClient and HttpResponse
        mockHttpClient = mock(HttpClient.class);
        mockHttpResponse = mock(HttpResponse.class);
        // Use reflection to replace the httpClient field in remoteGitClient with our mock
        Field httpClientField = RemoteGitClientImpl.class.getDeclaredField("httpClient");
        httpClientField.setAccessible(true);
        httpClientField.set(remoteGitClient, mockHttpClient);
    }

    @Test
    void testGetChangedFilesSuccessful() throws Exception {
        // Prepare JSON response with a "files" array
        String jsonResponse = "{\"files\":[" +
                "{\"filename\":\"file1.txt\"}," +
                "{\"filename\":\"file2.txt\"}" +
                "]}";
        // Simulate successful response
        when(mockHttpResponse.statusCode()).thenReturn(200);
        when(mockHttpResponse.body()).thenReturn(jsonResponse);
        // Make the send() method return our mock response
        when(mockHttpClient.send(any(HttpRequest.class), ArgumentMatchers.any(HttpResponse.BodyHandler.class)))
                .thenReturn(mockHttpResponse);

        List<String> changedFiles = remoteGitClient.getChangedFiles("branchA", "mergeBase", "owner", "repo", "dummyToken");
        assertNotNull(changedFiles);
        assertEquals(2, changedFiles.size());
        assertTrue(changedFiles.contains("file1.txt"));
        assertTrue(changedFiles.contains("file2.txt"));
    }

    @Test
    void testGetChangedFilesApiError() throws Exception {
        // Simulate API error with non-200 status code
        String errorBody = "{\"message\":\"Not Found\"}";
        when(mockHttpResponse.statusCode()).thenReturn(404);
        when(mockHttpResponse.body()).thenReturn(errorBody);
        when(mockHttpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenReturn(mockHttpResponse);

        GitDiffException exception = assertThrows(GitDiffException.class,
                () -> remoteGitClient.getChangedFiles("branchA", "mergeBase", "owner", "repo", "dummyToken"));
        assertTrue(exception.getMessage().contains("Error fetching changed files from GitHub API"));
    }

    @Test
    void testGetChangedFilesThrowsExceptionOnSendError() throws Exception {
        // Simulate an exception when sending the request
        when(mockHttpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenThrow(new RuntimeException("Connection error"));

        GitDiffException exception = assertThrows(GitDiffException.class,
                () -> remoteGitClient.getChangedFiles("branchA", "mergeBase", "owner", "repo", "dummyToken"));
        assertTrue(exception.getMessage().contains("Error fetching changed files from GitHub API"));
    }
}
