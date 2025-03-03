package dev.kalachik.gitdiff.util;

import dev.kalachik.gitdiff.exception.GitDiffException;

public interface ProcessExecutor {
    String execute(String repositoryPath, String... command) throws GitDiffException;
}
