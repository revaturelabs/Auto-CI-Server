package com.revature.autoci.init;

import java.util.List;
import java.util.Map;

public class NpmJSON {
    private String projectName;
    private String description;
    private String version;
    private String mainEntrypoint;
    private String author;
    private String license;
    private List<String> keywords;
    private Map<String, String> dependencies;
    private Map<String, String> devDependencies;
    private Map<String, String> scripts;

    public NpmJSON(String projectName, String description, String version, String mainEntrypoint, String author, String license,
            List<String> keywords, Map<String, String> dependencies, Map<String, String> devDependencies,
            Map<String, String> npmScripts) {
        this.projectName = projectName;
        this.description = description;
        this.version = version;
        this.author = author;
        this.mainEntrypoint = mainEntrypoint;
        this.license = license;
        this.keywords = keywords;
        this.dependencies = dependencies;
        this.devDependencies = devDependencies;
        this.scripts = npmScripts;
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
    public String getAuthor()
    {
        return author;
    }

    public String getMainEntrypoint() {
        return mainEntrypoint;
    }

    public String getLicense() {
        return license;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public Map<String, String> getDependencies() {
        return dependencies;
    }

    public Map<String, String> getDevDependencies() {
        return devDependencies;
    }

    public Map<String, String> getScripts() {
        return scripts;
    }



    

    
}
