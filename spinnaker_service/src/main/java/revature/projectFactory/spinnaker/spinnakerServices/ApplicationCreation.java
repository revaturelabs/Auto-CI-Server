package revature.projectFactory.spinnaker.spinnakerServices;

public class ApplicationCreation {
    
    private final String execDirectory = "~";

    public void create(String projectName, String gitHubEmail, String... cloudProvider) {
        String spinCommand = "spin application save --application-name " + projectName + " --owner-email " + gitHubEmail
                + " --cloud-providers \"";
        for (int i = 0; i < cloudProvider.length; i++) {
            if(i == cloudProvider.length-1) {
                spinCommand += cloudProvider[i] + "\" ";
            } else {
                spinCommand += cloudProvider[i] + ",";
            }
            
        }

        ApplicationPB.pbGenerate(spinCommand, execDirectory);

    }

}
