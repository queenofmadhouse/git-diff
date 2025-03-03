package dev.kalachik.gitdiff.api;

import dev.kalachik.gitdiff.exception.GitDiffException;

import java.util.List;

/**
 * Interface for interacting with a remote Git repository.
 * Provides methods to retrieve information about changes in remote branches.
 */
public interface RemoteGitClient {
    /**
     * Gets a list of modified files in the remote branch starting from the specified merge base.
     *
     * @param owner             repository owner
     * @param repo              repository name
     * @param remoteBranch      remote branch name
     * @param mergeBase         hash merge base
     * @param accessToken       GitHub API Token
     * @return list of file paths changed in the remote branch starting with the merge base
     * @throws GitDiffException in case of exception during request to API or handling exception
    **/
    List<String> getChangedFiles(String owner, String repo, String remoteBranch, String mergeBase, String accessToken) throws GitDiffException;
}
