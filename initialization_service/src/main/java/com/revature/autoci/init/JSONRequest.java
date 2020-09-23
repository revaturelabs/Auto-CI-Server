package com.revature.autoci.init;

import java.util.List;
import java.util.Map;


public class JSONRequest {
    private String githubUsername;
    private boolean isMaven;
    private String githubURL;
    private Map<String, String> metadata;
    private List<Map<String,String>> dependencies;
    public JSONRequest(String githubUsername, boolean isMaven, String githubURL, Map<String, String> metadata, List<Map<String, String>> dependencies)
    {
        this.githubUsername = githubUsername;
        this.isMaven = isMaven;
        this.githubURL = githubURL;
        this.metadata = metadata;
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

    public List<Map<String, String>> getDependencies() {
        return dependencies;
    }

    public String getMetadataValue(String key)
    {
        return metadata.get(key);
    }
    
}
