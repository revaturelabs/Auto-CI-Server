package com.revature.model.Progress;

public class ReturnResp {
    private String message;
    private Boolean status;

    public ReturnResp(){}

    public ReturnResp(Boolean status, String message){
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
