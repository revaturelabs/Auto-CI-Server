package revature.projectFactory.spinnaker.ProcessBuilderUtilsTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Test;

import revature.projectFactory.spinnaker.processbuilderUtils.ProcessBuilderUtility;

public class processBuilderTest {
    @Test
    public void testValidCommand(){
        assertEquals( 0 ,ProcessBuilderUtility.pbGenerate("whoami", new File(".").getAbsolutePath()));
    }
    @Test
    public void testInvalidCommand(){
        assertTrue(ProcessBuilderUtility.pbGenerate("veryBadcommandNeverUse in command line shouldn't work, please break", new File(".").getAbsolutePath()) > 0);
    }
}
