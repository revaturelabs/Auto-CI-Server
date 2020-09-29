package com.revature.autoci.init;

import java.util.List;
import java.util.Map;

public class MavenJSON {
    private String projectName;
    private String description;
    private String version;
    private String groupId;
    private String artifactId;
    private String packaging;
    private String javaVersion;
    private String mainClass;
    private List<Map<String, String>> dependencies;

    public MavenJSON(String projectName, String description, String version, String groupId, String artifactId,
            String packaging, String javaVersion, String mainClass, List<Map<String, String>> dependencies) {
        this.projectName = projectName;
        this.description = description;
        this.version = version;
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

    public String getDescription() {
        return description;
    }

    public String getVersion() {
        return version;
    }

    public String getGroupId() {
        return groupId;
    }

    public String getArtifactId() {
        return artifactId;
    }

    public String getPackaging() {
        return packaging;
    }

    public String getJavaVersion() {
        return javaVersion;
    }

    public String getMainClass() {
        return mainClass;
    }

    public List<Map<String, String>> getDependencies() {
        return dependencies;
    }


    
    
}
