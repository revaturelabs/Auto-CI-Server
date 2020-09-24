package project3api.model;

public class JenkinsPost {

    private String status;

    public JenkinsPost(){

    }

    public JenkinsPost(String status){
        this.status = status;
    }

    public String getStats() {
        return this.status;
    }

    public void setStats(String status) {
        this.status = status;
    }
    
}