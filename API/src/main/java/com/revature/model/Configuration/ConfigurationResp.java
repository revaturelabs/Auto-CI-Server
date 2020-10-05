package com.revature.model.Configuration;

public class ConfigurationResp {
    private String githubURL;
    private String madeHook; // false if using GHActions or on error

    public ConfigurationResp() {
    }

    public String getGithubURL() {
        return this.githubURL;
    }

    public void setGithubURL(String githubURL) {
        this.githubURL = githubURL;
    }

    public String getMadeHook() {
        return this.madeHook;
    }

    public void setMadeHook(String madeHook) {
        this.madeHook = madeHook;
    }

    public ConfigurationResp githubURL(String githubURL) {
        this.githubURL = githubURL;
        return this;
    }

    public ConfigurationResp madeHook(String madeHook) {
        this.madeHook = madeHook;
        return this;
    }

}
