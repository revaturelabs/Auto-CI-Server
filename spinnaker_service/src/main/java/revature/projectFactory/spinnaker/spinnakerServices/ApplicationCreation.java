package revature.projectFactory.spinnaker.spinnakerServices;

import java.io.File;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import revature.projectFactory.spinnaker.processbuilderUtils.ProcessBuilderUtility;

public class ApplicationCreation implements IApplicationCreation{
    
    private final String execDirectory = new File("/").getAbsolutePath();
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public int create(String projectName, String gitHubEmail, String... cloudProvider) {
        String spinCommand ="spin application save" + " --application-name " + projectName + " --owner-email " + gitHubEmail +" --cloud-providers ";
        for (int i = 0; i < cloudProvider.length; i++) {
            if(i == cloudProvider.length-1) {
                spinCommand += cloudProvider[i];
            } else {
                spinCommand += cloudProvider[i] + ",";
            }      
        }
        int exited = ProcessBuilderUtility.pbGenerate(spinCommand, execDirectory);
        if(exited == 1){
            log.warn("Failed to create or save application " + projectName + ".");
        }else{
            log.info("Created application " + projectName + "on Spinnaker server.");
        }
        return exited;
    }

}
