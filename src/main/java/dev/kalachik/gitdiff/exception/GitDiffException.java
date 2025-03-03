package dev.kalachik.gitdiff.exception;

public class GitDiffException extends RuntimeException {
    public GitDiffException(String message) {
        super(message);
    }

    public GitDiffException(String message, Throwable cause) {
        super(message, cause);
    }
}
