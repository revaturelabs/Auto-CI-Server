package com.revature.autoci.init;

import java.util.List;
import java.util.Map;

public class NpmProject {
    
    private String name;
    private String description;
    private String version;
    private String main;
    private Map<String, String> scripts;
    private Map<String, String> repository;
    private List<String> keywords;
    private String author;
    private String license;
    private Map<String, String> dependencies;
    private Map<String, String> devDependencies;

    public NpmProject(String projectName, String description, String version, String main, Map<String, String> scripts, Map<String, String> repository, List<String> keywords, String author, String license, Map<String, String> dependencies, Map<String, String> devDependencies) {
        this.name = projectName;
        this.description = description;
        this.version = version;
        this.main = main;
        this.scripts = scripts;
        this.repository = repository;
        this.keywords = keywords;
        this.author = author;
        this.license = license;
        this.dependencies = dependencies;
        this.devDependencies = devDependencies;
    }

}

