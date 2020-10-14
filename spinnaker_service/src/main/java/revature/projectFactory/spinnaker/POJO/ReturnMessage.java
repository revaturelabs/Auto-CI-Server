package revature.projectFactory.spinnaker.POJO;

public class ReturnMessage {
    private boolean applicationCreated;
    private boolean pipelineCreated;

    /**
     * Returns a boolean repersenting the success of the Spinnaker Pipeline Creation
     * @return the pipeline creation success status
     */
    public boolean getPipelineCreated() {
        return pipelineCreated;
    }

    /**
     *  Returns a boolean repersenting the success of the Spinnaker Application Creation
     * @return the application creation success status
     */
    public boolean getApplicationCreated() {
        return applicationCreated;
    }

    /**
     * Set the application creation success status
     * @param applicationCreationStatus true = created
     */
    public void setApplicationCreated(Boolean applicationCreationStatus) {
        this.applicationCreated = applicationCreationStatus;
    }

    /**
     * Set the Pipeline creation sucess status
     * @param pipelineCreationStatus true = created
     */
    public void setPipelineCreated(Boolean pipelineCreationStatus) {
        this.pipelineCreated = pipelineCreationStatus;
    }

    /**
     * A noArgs constuctor for return Message.
     */
	public ReturnMessage() {
	}

    /**
     * The return message constructor
     * @param applicationCreationStatus the application success status, true = created
     * @param pipelineCreationStatus the pipeline success status, true 
     */
    public ReturnMessage(Boolean applicationCreationStatus, Boolean pipelineCreationStatus) {
        this.applicationCreated = applicationCreationStatus;
        this.pipelineCreated = pipelineCreationStatus;
    }

    @Override
    public String toString() {
        return "applicationCreated:"+applicationCreated+", pipelineCreated:"+pipelineCreated;
    }
  
}
