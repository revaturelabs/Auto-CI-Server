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
        //String output = "";

        try {
            process = processBuilder.start();
            /*try (BufferedReader inputReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                    BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()))) {
                while (inputReader.ready()) {
                    output += inputReader.readLine() + "\n";
                }
                while (errorReader.ready()) {
                    output += errorReader.readLine() + "\n";
                }*/
            try{
                exited = process.waitFor();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return exited;

    }
}
