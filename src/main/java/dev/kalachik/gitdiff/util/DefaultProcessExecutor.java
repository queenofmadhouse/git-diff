package dev.kalachik.gitdiff.util;

import dev.kalachik.gitdiff.exception.GitDiffException;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

public class DefaultProcessExecutor implements ProcessExecutor {

    @Override
    public String execute(String repositoryPath, String... command) throws GitDiffException {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder(command);
            processBuilder.directory(new File(repositoryPath));
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();

            String output = new BufferedReader(
                    new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8))
                    .lines()
                    .collect(Collectors.joining("\n"));

            int exitCode = process.waitFor();
            if (exitCode != 0) {
                throw new GitDiffException("Error executing git command: " + String.join(" ", command));
            }
            return output.trim();
        } catch (Exception e) {
            throw new GitDiffException("Error executing git command", e);
        }
    }
}
