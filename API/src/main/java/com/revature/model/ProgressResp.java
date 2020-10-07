package com.revature.model;

public class ProgressResp {
    private String message;
    private Boolean status;

    public ProgressResp(String message, Boolean status){
        this.message = message;
        this.status = status;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getStatus() {
        return this.status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
