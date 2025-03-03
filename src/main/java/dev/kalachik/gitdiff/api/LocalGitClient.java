package dev.kalachik.gitdiff.api;

import dev.kalachik.gitdiff.exception.GitDiffException;

import java.util.List;

/**
 * Interface for working with a local Git repository.
 * Provides methods to determine merge base and track local changes.
 */
public interface LocalGitClient {
    /**
     * Finds merge base commit between two branches
     *
     * @param remoteBranch      remoteBranch name
     * @param localBranch       localBranch name
     * @return                  hash merge base
     * @throws GitDiffException in case of exception during executing git command
     */
    String findMergeBase(String remoteBranch, String localBranch) throws GitDiffException;

    /**
     * Receives list of changed files in local branch starting with merge base
     *
     * @param localBranch       local branch name
     * @param mergeBase         hash merge base
     * @return                  list of file paths changed in the local localBranch starting with merge base
     * @throws GitDiffException in case of exception during executing git command
     */
    List<String> getChangedFiles(String localBranch, String mergeBase) throws GitDiffException;
}
