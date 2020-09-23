package revature.projectFactory.spinnaker.POJO;

import javax.print.attribute.standard.JobName;

public class PipelinePojo {
    private String jobName;
    private String gitUri;
    private String metaData;

    public PipelinePojo(){
        jobName = "";
        gitUri = "";
        metaData = "";
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
}
