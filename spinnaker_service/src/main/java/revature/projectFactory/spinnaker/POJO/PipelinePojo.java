package revature.projectFactory.spinnaker.POJO;

import java.util.ArrayList;
import java.util.List;

import revature.projectFactory.spinnaker.stringUtils.StringUtility;

public class PipelinePojo {
    /**
     * Git uri used in post request to create pipeline, and in get request to recieve pipeline file
     */
    private String gitUri;
    
    /**
     * Git branch used in get request to recieve pipeline file
     */
    private String branch;

    /**
     * Comma separated string of cloud provider used in the application creation call.
     */
    private List<String> cloudProviders;
    
    /**
     * Email used as owner email of Application in Spinnaker
     */
    private String email;

    /**
     * The project name that will be used as the name of the application.
     * [Note]: Should be the same as the name value present in spinnaker pipeline.json
     */
    private String projectName;

    /**
     * A no args constuctor for the Pipeline pojo.
     * @since 10/9/2020
     * @author Reese Benson
     * @version 1.0.0
     */
    public PipelinePojo() {
        gitUri = "";
        email = "";
        projectName = "";
        cloudProviders = new ArrayList<String>();
        branch ="";
    }

    /**
     * @author Reese Benson
     * @return the Source control Branch name to use when retriving data 
     */
    public String getBranch() {
        return branch;
    }

    /**
     * @author Reese Benson
     * @param branch the Source control Branch name to use when retriving data
     */
    public void setBranch(String branch) {
        this.branch = branch;
    }

    /**
     * @author Reese Benson
     * @return the project name to be used in application creation
     */
    public String getProjectName() {
        return projectName;
    }


    /**
     * @author Reese Benson
     * @param projectName the project name to be used in application creation
     */
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    /**
     * @author Reese Benson
     * @return the email to create the applcation under
     */
    public String getEmail() {
        return email;
    }

    /**
     * Set the email to create the application under
     * @param email
     * @author Reese Benson 
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @author Reese Benson
     * @return A list repersentation of all cloud providers to be used in application creation
     */
    public List<String> getCloudProviders() {
        return cloudProviders;
    }

    /**
     * @author Reese Benson
     * @param cloudProviders A list repersentation of all cloud providers to be used in application creation
     */
    public void setCloudProviders(List<String> cloudProviders) {
        this.cloudProviders = cloudProviders;
    }


    /**
     * @author Reese Benson
     * @return The url to github project hosting spinnaker.json configuration file
     */
    public String getGitUri() {
        return gitUri;
    }

    /**
     * @author Reese Benson
     * @param gitUri The url to github project hosting spinnaker.json configuration file
     */
    public void setGitUri(String gitUri) {
        this.gitUri = gitUri;
    }

    @Override
    public String toString(){
        return "gitUri: " + gitUri + ", cloudProviders: " + StringUtility.asCommaSeperatedString(cloudProviders) + ", email: " + email + ", projectName: " + projectName + ", branch: " + branch;
    }

    /**
     * @author Reese Benson
     * @param gitUri The url to github project hosting spinnaker.json configuration file
     * @param branch The branch on the github repo hosting spinnaker.json configuration file
     * @param cloudProviders A list of cloud providers to use in application creation
     * @param email An email to create the application under
     * @param projectName the name of the project, used in application creation
     */
    public PipelinePojo(String gitUri, String branch, List<String> cloudProviders, String email, String projectName) {
        this.gitUri = gitUri;
        this.branch = branch;
        this.cloudProviders = cloudProviders;
        this.email = email;
        this.projectName = projectName;
    }
    
}
