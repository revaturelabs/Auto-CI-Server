package com.revature;

import java.io.IOException;

import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;

public class GitHubAPI {
    final private String dbgToken = ""; // NEVER COMMIT TOKENS

    private GitHub gh = null;
    public boolean debugMode = true;

    public GitHub getInstance() throws IOException {
        if (gh == null) {
            if (debugMode && !dbgToken.isEmpty()) {
                gh = new GitHubBuilder().withOAuthToken(dbgToken).build();
            } else {
                // Requires env GITHUB_OAUTH to be set
                gh = GitHubBuilder.fromEnvironment().build();
            }
        }
        return gh;
    }
}
