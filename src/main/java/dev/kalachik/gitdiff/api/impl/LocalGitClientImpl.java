package dev.kalachik.gitdiff.api.impl;

import dev.kalachik.gitdiff.api.LocalGitClient;
import dev.kalachik.gitdiff.exception.GitDiffException;
import dev.kalachik.gitdiff.util.ProcessExecutor;

import java.util.List;
import java.util.stream.Collectors;

/**
 * LocalGitClientImpl provides local Git operations by executing Git commands
 * through an injected ProcessExecutor.
 */
public class LocalGitClientImpl implements LocalGitClient {

    private final String repositoryPath;
    private final ProcessExecutor processExecutor;

    public LocalGitClientImpl(String localRepoPath, ProcessExecutor processExecutor) {
        this.repositoryPath = localRepoPath;
        this.processExecutor = processExecutor;
    }

    @Override
    public String findMergeBase(String remoteBranch, String localBranch) throws GitDiffException {
        return processExecutor.execute(repositoryPath, "git", "merge-base", localBranch, remoteBranch);
    }

    @Override
    public List<String> getChangedFiles(String localBranch, String mergeBase) throws GitDiffException {
        String output = processExecutor.execute(repositoryPath, "git", "diff", "--name-only", mergeBase, localBranch);
        return output.lines().filter(line -> !line.isEmpty()).collect(Collectors.toList());
    }
}
