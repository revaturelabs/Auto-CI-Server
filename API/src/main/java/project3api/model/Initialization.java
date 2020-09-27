package project3api.model;

public class Initialization {

    private String gitUser;
    private String repoURL;
    private String mvnOrNpm; // Are we doind maven, or bpm?
    private String projMetadata;
    private String depends; // stands for "Dependencies" (but also is a brand of adult diapers)

    public Initialization() { }

    public Initialization(String gitUser, String repoURL, String mvnOrNpm, String projMetadata, String depends) {
        this.gitUser = gitUser;
        this.repoURL = repoURL;
        this.mvnOrNpm = mvnOrNpm;
        this.projMetadata = projMetadata;
        this.depends = depends;
    }

    public String getGitUser() {
        return gitUser;
    }

    public void setGitUser(String gitUser) {
        this.gitUser = gitUser;
    }

    public String getRepoURL() {
        return repoURL;
    }

    public void setRepoURL(String repoURL) {
        this.repoURL = repoURL;
    }

    public String getMvnOrNpm() {
        return mvnOrNpm;
    }

    public void setMvnOrNpm(String mvnOrNpm) {
        this.mvnOrNpm = mvnOrNpm;
    }

    public String getProjMetadata() {
        return projMetadata;
    }

    public void setProjMetadata(String projMetadata) {
        this.projMetadata = projMetadata;
    }

    public String getDepends() {
        return depends;
    }

    public void setDepends(String depends) {
        this.depends = depends;
    }
}