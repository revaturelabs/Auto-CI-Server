package revature.projectFactory.spinnaker.spinnakerServices;

public interface IApplicationCreation {

    public void create(String projectName, String gitHubEmail, String... cloudProvider);

}
