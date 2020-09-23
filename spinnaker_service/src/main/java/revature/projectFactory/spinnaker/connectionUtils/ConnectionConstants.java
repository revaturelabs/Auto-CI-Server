package revature.projectFactory.spinnaker.connectionUtils;

public class ConnectionConstants {
    private static String SPINNAKERURI;
    private static int spinnakerEditCount = 0;

    public static String getSPINNAKERURI() {
        return SPINNAKERURI;
    }

    public static void setSPINNAKERURI(String uri) {
        if(spinnakerEditCount == 0){
            SPINNAKERURI = uri;
            spinnakerEditCount ++;
        }
    }
}
