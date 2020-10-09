package revature.projectFactory.spinnaker.processbuilderUtils;

import java.io.File;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProcessBuilderUtility {
    private final static Logger log = LoggerFactory.getLogger(ProcessBuilderUtility.class);

    /**
     * Builds and runs a Process Builder Command on the current OS.
     * @param buildcommand the command to be executed
     * @param execDirectory the directory to execute the command in
     * @return
     * @author Douglas Liu, Reese Benson, Lawerence Dang
     * @version 1.0.0
     * @since 10/9/2020
     */
    public static int pbGenerate(String buildcommand, String execDirectory) {
        ProcessBuilder processBuilder = new ProcessBuilder();

        // check system
        boolean isWindows = System.getProperty("os.name").toLowerCase().startsWith("windows");

        // setting command
        if (isWindows) {

            processBuilder.command("cmd.exe", "/c", buildcommand);

        // screen -dm command was used to replace sh for now to the command can be set

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
            log.info("Spinnaker Process Builder Command Ran");
        } catch (Exception e) {
          log.error(e.getMessage());   
        }
        return exited;

    }
}
