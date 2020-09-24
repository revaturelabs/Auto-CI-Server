package com.revature;

import java.io.IOException;

import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;

public class GitHubAPI {
    final private String token = ""; // NEVER COMMIT TOKENS

    private GitHub gh = null;

    public GitHub getInstance() throws IOException {
        if (gh == null) {
            if (token.isEmpty()) {
                // Requires env GITHUB_OAUTH to be set
                gh = GitHubBuilder.fromEnvironment().build();
            } else {
                gh = new GitHubBuilder().withOAuthToken(token).build();
            }
        }
        return gh;
    }
}
