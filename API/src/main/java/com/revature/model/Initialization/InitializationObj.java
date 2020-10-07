package com.revature.model.Initialization;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "isDone" })
public class InitializationObj {
    private String isDone;

    public InitializationObj(String isDone) {
        this.isDone = isDone;
    }

    public InitializationObj() {
    }

    public String getIsDone() {
        return isDone;
    }

    public void setIsDone(String isDone) {
        this.isDone = isDone;
    }
}