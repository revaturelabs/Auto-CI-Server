package com.revature.autoci.init;

import com.google.gson.annotations.JsonAdapter;

public class JSONRequest {
    private String githubUsername;
    private boolean isMaven;
    private String githubURL;
    private String[] dependencies;
    public JSONRequest(String githubUsername, boolean isMaven, String githubURL, String[] dependencies)
    {
        this.githubUsername = githubUsername;
        this.isMaven = isMaven;
        this.githubURL = githubURL;
        this.dependencies = dependencies;
    }

    public String getGithubUsername() {
        return githubUsername;
    }

    public boolean isMaven() {
        return isMaven;
    }

    public String getGithubURL() {
        return githubURL;
    }

    public String[] getDependencies() {
        return dependencies;
    }
    
}
