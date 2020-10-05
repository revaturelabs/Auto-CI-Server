package com.revature;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

public class CommandExecutor {
    private ProcessBuilder builder = new ProcessBuilder();
    private boolean isWinOS;
    private String baseDir = "";
    private boolean lastCmdSuccessful = false;

    public CommandExecutor() {
        this.isWinOS = System.getProperty("os.name").toLowerCase().startsWith("windows");
    }

    public CommandExecutor(String dir) {
        this();
        this.baseDir = dir;
    }

    public boolean wasLastCmdSuccess() {
        return lastCmdSuccessful;
    }

    public String execute(String command) {
        return execute(command, "");
    }
    
    public String execute(String command, String dir) {
        if (this.isWinOS) {
            builder.command("cmd.exe", "/c", command);
        } else {
            builder.command("sh", "-c", command);
        }

        dir = this.baseDir + dir;
        if (!dir.isEmpty()) {
            File d = new File(dir);
            if (!d.exists()) {
                d.mkdirs();
            }
            builder.directory(d);
        }
        
        String errorPrefix = "[" + command + "] error: ";
        try {
            Process process = builder.start();

            StringBuilder output = new StringBuilder();
            BufferedReader inputReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            
            String line;
            while ((line = inputReader.readLine()) != null) {
                output.append(line + "\n");
            }
            while ((line = errorReader.readLine()) != null) {
                output.append(line + "\n");
            }

            int exitCode = process.waitFor();
            if (exitCode == 0) {
                lastCmdSuccessful = true;
                return output.toString();
            } else {
                lastCmdSuccessful = false;
                return errorPrefix + "error code " + exitCode + "...\n" + output.toString();
            }
        } catch (Exception e) {
            lastCmdSuccessful = false;
            return errorPrefix + e.getMessage();
        }
    }
}