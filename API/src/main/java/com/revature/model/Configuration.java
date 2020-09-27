package com.revature.model;

public class Configuration {

    private String gitUser;
    private String jenkinsURI;
    private String jenOrGit; // Was Jenkins specified, or are we using Github Actions?
    private String projMetadata;

    public Configuration() { }

    public Configuration(String gitUser, String jenkinsURI, String jenOrGit, String projMetadata) {
        this.gitUser = gitUser;
        this.jenkinsURI = jenkinsURI;
        this.jenOrGit = jenOrGit;
        this.projMetadata = projMetadata;
    }

    public String getGitUser() {
        return gitUser;
    }

    public void setGitUser(String gitUser) {
        this.gitUser = gitUser;
    }

    public String getJenkinsURI() {
        return jenkinsURI;
    }

    public void setJenkinsURI(String jenkinsURI) {
        this.jenkinsURI = jenkinsURI;
    }

    public String getJenOrGit() {
        return jenOrGit;
    }

    public void setJenOrGit(String jenOrGit) {
        this.jenOrGit = jenOrGit;
    }

    public String getProjMetadata() {
        return projMetadata;
    }

    public void setProjMetadata(String projMetadata) {
        this.projMetadata = projMetadata;
    }

    
}