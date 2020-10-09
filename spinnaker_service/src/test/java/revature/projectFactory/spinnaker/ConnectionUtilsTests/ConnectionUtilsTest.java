package revature.projectFactory.spinnaker.ConnectionUtilsTests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import revature.projectFactory.spinnaker.connectionUtils.ConnectionConstants;

public class ConnectionUtilsTest {

    @Test
    public void TestSpinnakerURICanOnlyBeSetOnce(){
        ConnectionConstants.setSPINNAKERURI("uri");
        ConnectionConstants.setSPINNAKERURI("other");
        assertEquals("uri", ConnectionConstants.getSPINNAKERURI());
    }
    
}
