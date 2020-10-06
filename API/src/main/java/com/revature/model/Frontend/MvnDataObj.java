package com.revature.model.Frontend;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "projectName", "version", "description", "groupId", "artifactId", "packaging", "javaVersion", "mainClass"})
public class MvnDataObj {
    private String projectName;
    private String version;
    private String description;
    private String groupId;
    private String artifactId;
    private String packaging;
    private String javaVersion;
    private String mainClass;
    private List<MvnDependsObj> dependencies = null;

    public MvnDataObj() { }

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

    public List<MvnDependsObj> getDependencies() {
        return dependencies;
    }

    public void setDependencies(List<MvnDependsObj> dependencies) {
        this.dependencies = dependencies;
    }
    
    
}
