package dev.kalachik.gitdiff.api.impl;

import dev.kalachik.gitdiff.api.LocalGitClient;
import dev.kalachik.gitdiff.exception.GitDiffException;

import java.util.List;

public class LocalGitClientImpl implements LocalGitClient {

    public LocalGitClientImpl(String localRepoPath) {
    }

    @Override
    public String findMergeBase(String remoteBranch, String localBranch) throws GitDiffException {
        return "";
    }

    @Override
    public List<String> getChangedFiles(String localBranch, String mergeBase) throws GitDiffException {
        return List.of();
    }
}
