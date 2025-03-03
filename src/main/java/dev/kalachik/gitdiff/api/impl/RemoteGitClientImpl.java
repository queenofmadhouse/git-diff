package dev.kalachik.gitdiff.api.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.kalachik.gitdiff.api.RemoteGitClient;
import dev.kalachik.gitdiff.exception.GitDiffException;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * RemoteGitClientImpl implements the RemoteGitClient interface to interact with the GitHub API.
 * It fetches the list of files changed in a remote branch (branchA) compared to a given merge base.
 * This class uses java.net.http.HttpClient for HTTP requests and Jackson's ObjectMapper for JSON parsing.
 */
public class RemoteGitClientImpl implements RemoteGitClient {

    private static final String GITHUB_API_URL = "https://api.github.com";
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public RemoteGitClientImpl() {
        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();
    }

    /**
     * Retrieves the list of changed file paths from the remote GitHub repository
     * between the specified merge base and branchA.
     *
     * @param owner       the repository owner
     * @param repo        the repository name
     * @param branchA     the remote branch name
     * @param mergeBase   the merge base commit hash
     * @param accessToken the personal access token for GitHub API authentication
     * @return a list of file paths changed in branchA since the merge base
     * @throws GitDiffException if an error occurs during the API request or JSON parsing
     */
    @Override
    public List<String> getChangedFiles(String owner, String repo, String branchA, String mergeBase, String accessToken) throws GitDiffException {
        try {
            String url = String.format("%s/repos/%s/%s/compare/%s...%s", GITHUB_API_URL, owner, repo, mergeBase, branchA);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Authorization", "token " + accessToken)
                    .header("Accept", "application/vnd.github.v3+json")
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                throw new GitDiffException("GitHub API error: " + response.body());
            }

            JsonNode rootNode = objectMapper.readTree(response.body());
            List<String> changedFiles = new ArrayList<>();
            rootNode.path("files").forEach(fileNode ->
                    changedFiles.add(fileNode.path("filename").asText())
            );
            return changedFiles;
        } catch (Exception e) {
            throw new GitDiffException("Error fetching changed files from GitHub API", e);
        }
    }
}
