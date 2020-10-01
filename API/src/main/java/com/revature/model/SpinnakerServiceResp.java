package com.revature.model;

public class SpinnakerServiceResp {
    private boolean applicationCreated;
    private boolean pipelineCreated;

    public SpinnakerServiceResp(boolean applicationCreated, boolean pipelineCreated) {
        this.applicationCreated = applicationCreated;
        this.pipelineCreated = pipelineCreated;
    }

    public boolean isApplicationCreated() {
        return applicationCreated;
    }

    public void setApplicationCreated(boolean applicationCreated) {
        this.applicationCreated = applicationCreated;
    }

    public boolean isPipelineCreated() {
        return pipelineCreated;
    }

    public void setPipelineCreated(boolean pipelineCreated) {
        this.pipelineCreated = pipelineCreated;
    }
}
