package revature.projectFactory.spinnaker.POJO;

public class PipelinePojo {
    private String jobName;
    private String gitUri;
    private String metaData;
    private String cloudProviders;
    private String email;
    private String projectName;
    private String branch;

    public PipelinePojo() {
        jobName = "";
        gitUri = "";
        metaData = "";
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

    public String getMetaData() {
        return metaData;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getGitUri() {
        return gitUri;
    }

    public void setGitUri(String gitUri) {
        this.gitUri = gitUri;
    }

    public void setMetaData(String metaData) {
        this.metaData = metaData;
    }

    @Override
    public String toString(){
        return "jobName: " +jobName +", gitUri: " + gitUri + ", metaData: " + metaData + ", cloudProviders: " + cloudProviders + ", email: " + email + ", projectName: " + projectName + ", branch: " + branch;
    }

    public PipelinePojo(String jobName, String gitUri, String metaData, String cloudProviders, String email,
            String projectName, String branch) {
        this.jobName = jobName;
        this.gitUri = gitUri;
        this.metaData = metaData;
        this.cloudProviders = cloudProviders;
        this.email = email;
        this.projectName = projectName;
        this.branch = branch;
    }
}
