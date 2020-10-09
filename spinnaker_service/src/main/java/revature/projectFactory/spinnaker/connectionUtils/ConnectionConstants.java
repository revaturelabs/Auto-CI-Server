package revature.projectFactory.spinnaker.connectionUtils;

public class ConnectionConstants {
    private static volatile String SPINNAKERURI;
    private static volatile int spinnakerEditCount = 0;

    /**
     * Returns the spinnaker uri
     * @return
     */
    public static String getSPINNAKERURI() {
        return SPINNAKERURI;
    }

    /**
     * Sets the spinnaker Uri, Ensures the Spinnaker uri is only set once 
     * @param uri
     */
    public static void setSPINNAKERURI(String uri) {
        if(spinnakerEditCount == 0){
            SPINNAKERURI = uri;
            spinnakerEditCount ++;
        }
    }
}
