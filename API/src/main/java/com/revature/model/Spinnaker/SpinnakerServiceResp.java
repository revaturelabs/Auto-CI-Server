package com.revature.model.Spinnaker;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "applicationCreated", "pipelineCreated" })
public class SpinnakerServiceResp {
    private Boolean applicationCreated;
    private Boolean pipelineCreated;

    public SpinnakerServiceResp() { }

    public SpinnakerServiceResp(Boolean applicationCreated, Boolean pipelineCreated) {
        this.applicationCreated = applicationCreated;
        this.pipelineCreated = pipelineCreated;
    }

    public Boolean getApplicationCreated() {
        return applicationCreated;
    }

    public void setApplicationCreated(Boolean applicationCreated) {
        this.applicationCreated = applicationCreated;
    }

    public Boolean getPipelineCreated() {
        return pipelineCreated;
    }

    public void setPipelineCreated(Boolean pipelineCreated) {
        this.pipelineCreated = pipelineCreated;
    }
}
