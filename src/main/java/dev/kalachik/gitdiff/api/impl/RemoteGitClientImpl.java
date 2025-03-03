package dev.kalachik.gitdiff.api.impl;

import dev.kalachik.gitdiff.api.RemoteGitClient;
import dev.kalachik.gitdiff.exception.GitDiffException;

import java.util.List;

public class RemoteGitClientImpl implements RemoteGitClient {

    @Override
    public List<String> getChangedFiles(String owner, String repo, String remoteBranch, String mergeBase, String accessToken) throws GitDiffException {
        return List.of();
    }
}

