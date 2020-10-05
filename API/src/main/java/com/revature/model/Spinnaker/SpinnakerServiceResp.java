package com.revature.model.Spinnaker;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "applicationCreated", "pipelineCreated" })
public class SpinnakerServiceResp {
    private String applicationCreated;
    private String pipelineCreated;

    public SpinnakerServiceResp() { }

    public SpinnakerServiceResp(String applicationCreated, String pipelineCreated) {
        this.applicationCreated = applicationCreated;
        this.pipelineCreated = pipelineCreated;
    }

    public String getApplicationCreated() {
        return applicationCreated;
    }

    public void setApplicationCreated(String applicationCreated) {
        this.applicationCreated = applicationCreated;
    }

    public String getPipelineCreated() {
        return pipelineCreated;
    }

    public void setPipelineCreated(String pipelineCreated) {
        this.pipelineCreated = pipelineCreated;
    }
}
