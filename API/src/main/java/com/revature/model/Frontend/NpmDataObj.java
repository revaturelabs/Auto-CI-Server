package com.revature.model.Frontend;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "projectName", "version", "description", "mainEntrypoint", "keywords", "author", "license", "dependencies", "devDependencies", "scripts"})
public class NpmDataObj {
    private String projectName;
    private String version;
    private String description;
    private String mainEntrypoint;
    private List<KeywordObj> keywords = null;
    private String author;
    private String license;
    private List<NpmDependsObj> dependencies = null;
    private List<NpmDevDependsObj> devDependencies = null;
    private List<ScriptObj> scripts = null;


    public NpmDataObj() {
    }

    public String getProjectName() {
        return this.projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getVersion() {
        return this.version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMainEntrypoint() {
        return this.mainEntrypoint;
    }

    public void setMainEntrypoint(String mainEntrypoint) {
        this.mainEntrypoint = mainEntrypoint;
    }

    public List<KeywordObj> getKeywords() {
        return this.keywords;
    }

    public void setKeywords(List<KeywordObj> keywords) {
        this.keywords = keywords;
    }

    public String getAuthor() {
        return this.author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getLicense() {
        return this.license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public List<NpmDependsObj> getDependencies() {
        return this.dependencies;
    }

    public void setDependencies(List<NpmDependsObj> dependencies) {
        this.dependencies = dependencies;
    }

    public List<NpmDevDependsObj> getDevDependencies() {
        return this.devDependencies;
    }

    public void setDevDependencies(List<NpmDevDependsObj> devDependencies) {
        this.devDependencies = devDependencies;
    }

    public List<ScriptObj> getScripts() {
        return this.scripts;
    }

    public void setScripts(List<ScriptObj> scripts) {
        this.scripts = scripts;
    }

    public NpmDataObj projectName(String projectName) {
        this.projectName = projectName;
        return this;
    }

    public NpmDataObj version(String version) {
        this.version = version;
        return this;
    }

    public NpmDataObj description(String description) {
        this.description = description;
        return this;
    }

    public NpmDataObj mainEntrypoint(String mainEntrypoint) {
        this.mainEntrypoint = mainEntrypoint;
        return this;
    }

    public NpmDataObj keywords(List<KeywordObj> keywords) {
        this.keywords = keywords;
        return this;
    }

    public NpmDataObj author(String author) {
        this.author = author;
        return this;
    }

    public NpmDataObj license(String license) {
        this.license = license;
        return this;
    }

    public NpmDataObj dependencies(List<NpmDependsObj> dependencies) {
        this.dependencies = dependencies;
        return this;
    }

    public NpmDataObj devDependencies(List<NpmDevDependsObj> devDependencies) {
        this.devDependencies = devDependencies;
        return this;
    }

    public NpmDataObj scripts(List<ScriptObj> scripts) {
        this.scripts = scripts;
        return this;
    }


}
