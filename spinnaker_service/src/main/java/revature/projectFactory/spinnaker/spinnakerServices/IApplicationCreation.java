package revature.projectFactory.spinnaker.spinnakerServices;

public interface IApplicationCreation {

    /**
     * Creates an applocation of name project name and sets up the email githubemail as the owner, with a List of cloud providers to give the application.
     * @author Reese Benson
     * @version 1.0.0
     * @since 10/9/2020
     * @param projectName the name of the application being created
     * @param email the email to create the application under
     * @param cloudProvider a list of cloud providers, either as indiviual strings or a single , seperated sting like "AWS,kubernetes"
     * @return a success status 0 success, and non zero integer a failure
     */
    public int create(String projectName, String email, String... cloudProvider);

}
