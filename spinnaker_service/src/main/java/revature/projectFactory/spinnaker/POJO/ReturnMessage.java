package revature.projectFactory.spinnaker.POJO;

public class ReturnMessage {
    private boolean applicationCreated;
    private boolean pipelineCreated;

    public boolean getPipelineCreated() {
        return pipelineCreated;
    }

    public boolean getApplicationCreated() {
        return applicationCreated;
    }

    public void setApplicationCreated(Boolean applicationCreationStatus) {
        this.applicationCreated = applicationCreationStatus;
    }

    public void setPipelineCreated(Boolean pipelineCreationStatus) {
        this.pipelineCreated = pipelineCreationStatus;
    }

	public ReturnMessage() {
	}

    public ReturnMessage(Boolean applicationCreationStatus, Boolean pipelineCreationStatus) {
        this.applicationCreated = applicationCreationStatus;
        this.pipelineCreated = pipelineCreationStatus;
    }

    @Override
    public String toString() {
        return "applicationCreated:"+applicationCreated+", pipelineCreated:"+pipelineCreated;
    }
  
}
