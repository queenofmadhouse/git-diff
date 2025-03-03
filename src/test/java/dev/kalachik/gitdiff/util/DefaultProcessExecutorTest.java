package dev.kalachik.gitdiff.util;

import dev.kalachik.gitdiff.exception.GitDiffException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DefaultProcessExecutorTest {

    private final DefaultProcessExecutor executor = new DefaultProcessExecutor();

    @Test
    void testExecuteSuccess() throws GitDiffException {
        // Using "echo" command to simulate a successful command execution.
        String output = executor.execute(System.getProperty("user.dir"), "echo", "Hello");
        // "echo" usually returns "Hello" followed by a newline which is trimmed.
        assertEquals("Hello", output);
    }

    @Test
    void testExecuteFailure() {
        // Execute a command that will likely fail. For instance, a non-existent command.
        GitDiffException exception = assertThrows(GitDiffException.class,
                () -> executor.execute(System.getProperty("user.dir"), "nonexistent_command"));
        assertTrue(exception.getMessage().contains("Error executing git command"));
    }

    @Test
    void testExecuteNonZeroExitCode() {
        GitDiffException exception = assertThrows(GitDiffException.class,
                () -> executor.execute(System.getProperty("user.dir"), "sh", "-c", "exit 1"));
        assertTrue(exception.getMessage().contains("Error executing git command"),
                "Exception message should indicate error during git command execution");
    }
}
