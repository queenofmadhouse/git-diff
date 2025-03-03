package dev.kalachik.gitdiff;

import dev.kalachik.gitdiff.exception.GitDiffException;
import dev.kalachik.gitdiff.api.RemoteGitClient;
import dev.kalachik.gitdiff.api.LocalGitClient;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class DiffAnalyzer {
    private final RemoteGitClient remoteClient;
    private final LocalGitClient localClient;

    public DiffAnalyzer(RemoteGitClient remoteClient, LocalGitClient localClient) {
        this.remoteClient = remoteClient;
        this.localClient = localClient;
    }

    /**
     * @param owner             repository owner name
     * @param repo              repository name
     * @param remoteBranch      remote branch name
     * @param localBranch       local branch name
     * @param localRepoPath     local repository path
     * @param accessToken       GitHub API token
     * @return                  list of changed files in both branches
     * @throws GitDiffException in case of exception during any step of analysis
     */
    public List<String> analyzeDiff(String owner, String repo, String remoteBranch, String localBranch, String localRepoPath, String accessToken) throws GitDiffException {
        // 1. Finding merge base
        String mergeBase = localClient.findMergeBase(remoteBranch, localBranch);

        // 2. Receiving list of changed files in remote branch using GitHub API
        List<String> remoteChangedFiles = remoteClient.getChangedFiles(owner, repo, remoteBranch, mergeBase, accessToken);

        // 3. Receiving list of changed files in local branch
        List<String> localChangedFiles = localClient.getChangedFiles(localBranch, mergeBase);

        // 4. Finding intersections and returning them
        return filterCommonChanges(remoteChangedFiles, localChangedFiles);
    }

    /**
     * @param remoteFiles   list of changed files in remote branch
     * @param localFiles    list of changed files in local branch
     * @return              list of files that were modified in both branches
    **/
    private List<String> filterCommonChanges(List<String> remoteFiles, List<String> localFiles) {

        Set<String> remoteSet = new HashSet<>(remoteFiles);
        return localFiles.stream()
                .filter(remoteSet::contains)
                .collect(Collectors.toList());
    }
}
