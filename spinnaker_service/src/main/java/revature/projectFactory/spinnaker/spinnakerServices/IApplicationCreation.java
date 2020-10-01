package revature.projectFactory.spinnaker.spinnakerServices;

public interface IApplicationCreation {

    public int create(String projectName, String gitHubEmail, String... cloudProvider);

}
