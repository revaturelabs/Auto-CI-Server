package revature.projectFactory.spinnaker.POJO;

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
    private String cloudProviders;
    
    /**
     * Email used as owner email of Application in Spinnaker
     */
    private String email;

    /**
     * The project name that will be used as the name of the application.
     * [Note]: Should be the same as the name value present in spinnaker pipeline.json
     */
    private String projectName;

    public PipelinePojo() {
        gitUri = "";
        email = "";
        projectName = "";
        cloudProviders = "";
        branch ="";
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCloudProviders() {
        return cloudProviders;
    }

    public void setCloudProviders(String cloudProviders) {
        this.cloudProviders = cloudProviders;
    }


    public String getGitUri() {
        return gitUri;
    }

    public void setGitUri(String gitUri) {
        this.gitUri = gitUri;
    }

    @Override
    public String toString(){
        return "gitUri: " + gitUri + ", cloudProviders: " + cloudProviders + ", email: " + email + ", projectName: " + projectName + ", branch: " + branch;
    }

    public PipelinePojo(String gitUri, String branch, String cloudProviders, String email, String projectName) {
        this.gitUri = gitUri;
        this.branch = branch;
        this.cloudProviders = cloudProviders;
        this.email = email;
        this.projectName = projectName;
    }
    
}
