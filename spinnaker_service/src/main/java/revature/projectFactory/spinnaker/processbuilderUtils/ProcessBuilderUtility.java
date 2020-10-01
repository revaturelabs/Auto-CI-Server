package revature.projectFactory.spinnaker.processbuilderUtils;

import java.io.File;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ProcessBuilderUtility {

    public static int pbGenerate(String buildcommand, String execDirectory) {

        ProcessBuilder processBuilder = new ProcessBuilder();

        // check system
        boolean isWindows = System.getProperty("os.name").toLowerCase().startsWith("windows");

        // setting command
        if (isWindows) {

            processBuilder.command("cmd.exe", "/c", buildcommand);

        } else {
            buildcommand = "screen -dm " + buildcommand;
            processBuilder.command(buildcommand.split(" "));
        }
        System.out.println(processBuilder.command());
        processBuilder.directory(new File(execDirectory));
        Process process = null;
        int exited = 0;

        try {
            process = processBuilder.start();
            exited = process.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return exited;

    }
}
