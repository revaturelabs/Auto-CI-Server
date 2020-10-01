package com.revature.model;

public class ConfigurationResp {
    private String githubURL;
    private boolean madeHook; // false if using GHActions or on error

    public ConfigurationResp(String githubURL, boolean madeHook) {
        this.githubURL = githubURL;
        this.madeHook = madeHook;
    }

    public String getGithubURL() {
        return githubURL;
    }

    public void setGithubURL(String githubURL) {
        this.githubURL = githubURL;
    }

    public boolean isMadeHook() {
        return madeHook;
    }

    public void setMadeHook(boolean madeHook) {
        this.madeHook = madeHook;
    }
}
