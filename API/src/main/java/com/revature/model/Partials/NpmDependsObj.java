package com.revature.model.Partials;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "name", "version" })
public class NpmDependsObj {
    private String name;
    private String version;

    public NpmDependsObj() { }


    public NpmDependsObj(String name, String version) {
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

    public NpmDependsObj name(String name) {
        this.name = name;
        return this;
    }

    public NpmDependsObj version(String version) {
        this.version = version;
        return this;
    }
}
