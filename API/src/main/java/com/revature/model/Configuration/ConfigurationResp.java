package com.revature.model.Configuration;

public class ConfigurationResp {
    private String githubURL;
    private Boolean madeHook; // false if using GHActions or on error
    private String errorMsg;

    public ConfigurationResp() {
    }

    public String getGithubURL() {
        return this.githubURL;
    }

    public void setGithubURL(String githubURL) {
        this.githubURL = githubURL;
    }

    public Boolean getMadeHook() {
        return this.madeHook;
    }

    public void setMadeHook(Boolean madeHook) {
        this.madeHook = madeHook;
    }

    public ConfigurationResp githubURL(String githubURL) {
        this.githubURL = githubURL;
        return this;
    }

    public ConfigurationResp madeHook(Boolean madeHook) {
        this.madeHook = madeHook;
        return this;
    }

    public String getErrorMsg() {
        return this.errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

}
