package com.revature.model.Azure;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "pipeline", "login", "pipeline-vars"})
public class AzureRespObj {

    private String pipeline;
    private String login;
    private String pipelineVars;

    public String getPipeline() {
        return this.pipeline;
    }

    public void setPipeline(String pipeline) {
        this.pipeline = pipeline;
    }

    public String getLogin() {
        return this.login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPipelineVars() {
        return this.pipelineVars;
    }

    public void setPipelineVars(String pipelineVars) {
        this.pipelineVars = pipelineVars;
    }
    
}
