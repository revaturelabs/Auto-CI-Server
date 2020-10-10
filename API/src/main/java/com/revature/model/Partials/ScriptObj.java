package com.revature.model.Partials;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "command", "script" })
public class ScriptObj {
    private String command;
    private String script;


    public ScriptObj() {
    }

    public ScriptObj(String command, String script) {
        this.command = command;
        this.script = script;
    }

    public String getCommand() {
        return this.command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getScript() {
        return this.script;
    }

    public void setScript(String script) {
        this.script = script;
    }

    public ScriptObj command(String command) {
        this.command = command;
        return this;
    }

    public ScriptObj script(String script) {
        this.script = script;
        return this;
    }
    
    
}
