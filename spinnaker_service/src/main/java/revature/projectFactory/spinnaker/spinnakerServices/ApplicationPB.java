package revature.projectFactory.spinnaker.spinnakerServices;

import java.io.File;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ApplicationPB {

    public static String pbGenerate(String buildcommand, String execDirectory) {

        ProcessBuilder processBuilder = new ProcessBuilder();

        // check system
        boolean isWindows = System.getProperty("os.name").toLowerCase().startsWith("windows");

        // setting command
        if (isWindows) {

            processBuilder.command("cmd.exe", "/c", buildcommand);

        } else {

            processBuilder.command("sh", "-c", buildcommand);

        }

        processBuilder.directory(new File(execDirectory));
        Process process = null;
        int exited = 0;
        String output = "";

        try {
            process = processBuilder.start();

            try (BufferedReader inputReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                    BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()))) {
                exited = process.waitFor();
                while (errorReader.ready()) {
                    output += errorReader.readLine() + "\n";
                }
                while (inputReader.ready()) {
                    output += inputReader.readLine() + "\n";
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return output + exited;

    }
}
