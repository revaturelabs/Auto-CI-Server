package com.revature.autoci.init;

import java.util.List;
import java.util.Map;

/**
 * Represents the JSON body of a post request.
 */
public class JSONRequest {
    private String githubUsername;
    private String githubURL;
    private String ide;
    private boolean isMaven;
    private boolean generateGithubActions;
    private MavenJSON mavenData;
    private NpmJSON npmData;

    public JSONRequest(String githubUsername, String githubURL, String ide, boolean isMaven, boolean generateGHActions,
            MavenJSON mavenData) {
        this.githubUsername = githubUsername;
        this.githubURL = githubURL;
        this.ide = ide;
        this.isMaven = isMaven;
        this.generateGithubActions = generateGHActions;
        this.mavenData = mavenData;
        this.npmData = null;;
    }

    public JSONRequest(String githubUsername, String githubURL, String ide, boolean isMaven, boolean generateGHActions,
            NpmJSON npmData) {
        this.githubUsername = githubUsername;
        this.githubURL = githubURL;
        this.ide = ide;
        this.isMaven = isMaven;
        this.generateGithubActions = generateGHActions;
        this.mavenData = null;
        this.npmData = npmData;
    }

    public String getGithubUsername() {
        return githubUsername;
    }

    public String getGithubURL() {
        return githubURL;
    }

    public String getIDE() {
        return ide;
    }

    public boolean isMaven() {
        return isMaven;
    }

    public boolean shouldGenerateGHActions() {
        return generateGithubActions;
    }

    public MavenJSON getMavenData() {
        return mavenData;
    }

    public NpmJSON getNpmData() {
        return npmData;
    }

    

    
}
