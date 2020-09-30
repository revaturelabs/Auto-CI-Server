package revature.projectFactory.spinnaker.spinnakerServices;

import java.io.File;

import revature.projectFactory.spinnaker.processbuilderUtils.ProcessBuilderUtility;

public class ApplicationCreation implements IApplicationCreation{
    
    private final String execDirectory = new File("/").getAbsolutePath();

    public void create(String projectName, String gitHubEmail, String... cloudProvider) {
        String spinCommand ="spin application save" + " --application-name " + projectName + " --owner-email " + gitHubEmail +" --cloud-providers \"";
        for (int i = 0; i < cloudProvider.length; i++) {
            if(i == cloudProvider.length-1) {
                spinCommand += cloudProvider[i] + "\"";
            } else {
                spinCommand += cloudProvider[i] + ",";
            }      
        }
        System.out.println(spinCommand);
        System.out.println(ProcessBuilderUtility.pbGenerate(spinCommand, execDirectory));
    }

}
