package com.revature.model.Partials;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "name", "version" })
public class NpmDevDependsObj {
    private String name;
    private String version;

    public NpmDevDependsObj() { }


    public NpmDevDependsObj(String name, String version) {
        this.name = name;
        this.version = version;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return this.version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public NpmDevDependsObj name(String name) {
        this.name = name;
        return this;
    }

    public NpmDevDependsObj version(String version) {
        this.version = version;
        return this;
    }
}
