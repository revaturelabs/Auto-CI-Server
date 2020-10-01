package com.revature.model;


public class NpmDataObj {
    private String projectName;
    private String version;
    private String description;
    private String mainEntrypoint;
    private String keywords;
    private String author;
    private String license;
    private NpmDependsObj dependencies;
    private NpmDependsObj devDependencies;
    private String scripts;

    public NpmDataObj(String projectName, String version, String description, String mainEntrypoint, String keywords,
            String author, String license, NpmDependsObj dependencies, NpmDependsObj devDependencies, String scripts) {
        this.projectName = projectName;
        this.version = version;
        this.description = description;
        this.mainEntrypoint = mainEntrypoint;
        this.keywords = keywords;
        this.author = author;
        this.license = license;
        this.dependencies = dependencies;
        this.devDependencies = devDependencies;
        this.scripts = scripts;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMainEntrypoint() {
        return mainEntrypoint;
    }

    public void setMainEntrypoint(String mainEntrypoint) {
        this.mainEntrypoint = mainEntrypoint;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public NpmDependsObj getDependencies() {
        return dependencies;
    }

    public void setDependencies(NpmDependsObj dependencies) {
        this.dependencies = dependencies;
    }

    public NpmDependsObj getDevDependencies() {
        return devDependencies;
    }

    public void setDevDependencies(NpmDependsObj devDependencies) {
        this.devDependencies = devDependencies;
    }

    public String getScripts() {
        return scripts;
    }

    public void setScripts(String scripts) {
        this.scripts = scripts;
    }


}
