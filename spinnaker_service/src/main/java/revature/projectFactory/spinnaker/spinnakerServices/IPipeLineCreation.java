package revature.projectFactory.spinnaker.spinnakerServices;

public interface IPipeLineCreation {
    /**
     * Creates a pipeline on the connected Spinnaker Application
     * 
     * @author Reese Benson
     * @version 1.0.0
     * @return a success status
     * @since 10/9/2020
     */
    public boolean create();
}
