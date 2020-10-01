package com.revature.model;

public class MvnDataObj {
    private String projectName;
    private String version;
    private String description;
    private String groupId;
    private String artifactId;
    private String packaging;
    private String javaVersion;
    private String mainClass;
    private MvnDependsObj dependencies; // make this an array

    public MvnDataObj(String projectName, String version, String description, String groupId, String artifactId,
            String packaging, String javaVersion, String mainClass, MvnDependsObj dependencies) {
        this.projectName = projectName;
        this.version = version;
        this.description = description;
        this.groupId = groupId;
        this.artifactId = artifactId;
        this.packaging = packaging;
        this.javaVersion = javaVersion;
        this.mainClass = mainClass;
        this.dependencies = dependencies;
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

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getArtifactId() {
        return artifactId;
    }

    public void setArtifactId(String artifactId) {
        this.artifactId = artifactId;
    }

    public String getPackaging() {
        return packaging;
    }

    public void setPackaging(String packaging) {
        this.packaging = packaging;
    }

    public String getJavaVersion() {
        return javaVersion;
    }

    public void setJavaVersion(String javaVersion) {
        this.javaVersion = javaVersion;
    }

    public String getMainClass() {
        return mainClass;
    }

    public void setMainClass(String mainClass) {
        this.mainClass = mainClass;
    }

    public MvnDependsObj getDependencies() {
        return dependencies;
    }

    public void setDependencies(MvnDependsObj dependencies) {
        this.dependencies = dependencies;
    }

    
}
