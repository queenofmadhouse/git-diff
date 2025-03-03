package dev.kalachik.gitdiff;

import dev.kalachik.gitdiff.api.RemoteGitClient;
import dev.kalachik.gitdiff.api.LocalGitClient;
import dev.kalachik.gitdiff.api.impl.RemoteGitClientImpl;
import dev.kalachik.gitdiff.api.impl.LocalGitClientImpl;
import dev.kalachik.gitdiff.exception.GitDiffException;
import dev.kalachik.gitdiff.util.DefaultProcessExecutor;
import dev.kalachik.gitdiff.util.ProcessExecutor;

import java.util.List;

public class GitDiffCLI {
    public static void main(String[] args) {
        try {
            if (args.length < 6) {
                System.err.println("Usage: gitdiff <owner> <repo> <accessToken> <localRepoPath> <branchA> <branchB>");
                System.exit(1);
            }
            List<String> commonChangedFiles = getStrings(args);

            if (commonChangedFiles.isEmpty()) {
                System.out.println("No common modified files found");
            } else {
                System.out.println("Common modified files found:");
                commonChangedFiles.forEach(System.out::println);
            }
        } catch (GitDiffException e) {
            System.err.println("Exception during analysis: " + e.getMessage());
            System.exit(1);
        }
    }

    private static List<String> getStrings(String[] args) {
        String owner = args[0];
        String repo = args[1];
        String accessToken = args[2];
        String localRepoPath = args[3];
        String branchA = args[4];
        String branchB = args[5];

        RemoteGitClient remoteClient = new RemoteGitClientImpl();
        ProcessExecutor processExecutor = new DefaultProcessExecutor();
        LocalGitClient localClient = new LocalGitClientImpl(localRepoPath, processExecutor);

        DiffAnalyzer analyzer = new DiffAnalyzer(remoteClient, localClient);
        List<String> commonChangedFiles = analyzer.analyzeDiff(owner, repo, branchA, branchB, localRepoPath, accessToken);
        return commonChangedFiles;
    }
}

