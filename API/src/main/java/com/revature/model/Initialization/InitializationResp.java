package com.revature.model.Initialization;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "isDone" })
public class InitializationResp {
    private String isDone;

    public InitializationResp(String isDone) {
        this.isDone = isDone;
    }

    public InitializationResp() {
    }

    public String getIsDone() {
        return isDone;
    }

    public void setIsDone(String isDone) {
        this.isDone = isDone;
    }
}