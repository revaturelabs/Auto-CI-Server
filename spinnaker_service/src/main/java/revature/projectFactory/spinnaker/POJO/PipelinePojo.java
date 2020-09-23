package revature.projectFactory.spinnaker.POJO;

public class PipelinePojo {
    private String jobName;
    private String gitUri;
    private String metaData;

    public PipelinePojo(){
        jobName = "";
        gitUri = "";
        metaData = "";
    }

    public PipelinePojo(String jobName, String gitUri, String metaData){
        this.jobName = jobName;
        this.gitUri = gitUri;
        this.metaData = metaData;
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
        return "jobName: " +jobName +", gitUri: " + gitUri + ", metaData: " + metaData;
    }
}
