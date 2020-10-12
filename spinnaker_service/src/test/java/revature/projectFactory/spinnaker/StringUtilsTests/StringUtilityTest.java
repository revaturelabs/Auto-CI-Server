package revature.projectFactory.spinnaker.StringUtilsTests;

import static org.junit.Assert.assertEquals;

import java.beans.BeanProperty;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import revature.projectFactory.spinnaker.POJO.PipelinePojo;
import revature.projectFactory.spinnaker.stringUtils.StringUtility;

public class StringUtilityTest {
    String[] CommaSeperatedStringExpected = new String []{"one","one,two",",,","blah,blah,OOOh,la,la",""};
    List<String> InputList = new ArrayList<String>();

    @Before
    public void emptyList(){
        InputList.clear();
    }

    @Test
    public void testCommaSeperatedStringWithOneValue(){
        InputList.add("one");
        String output = StringUtility.asCommaSeperatedString(InputList);
        assertEquals(CommaSeperatedStringExpected[0],output);
    }

    @Test
    public void testCommaSeperatedStringWithTwoValues(){
        InputList.add("one");
        InputList.add("two");
        String output = StringUtility.asCommaSeperatedString(InputList);
        assertEquals(CommaSeperatedStringExpected[1],output);
    }

    @Test
    public void testCommaSeperatedStringWithThreeEmptyValues(){
        InputList.add("");
        InputList.add("");
        InputList.add("");
        String output = StringUtility.asCommaSeperatedString(InputList);
        assertEquals(CommaSeperatedStringExpected[2],output);
    }

    @Test
    public void testCommaSeperatedStringWithManyValues(){
        InputList.add("blah");
        InputList.add("blah");
        InputList.add("OOOh");
        InputList.add("la");
        InputList.add("la");
        String output = StringUtility.asCommaSeperatedString(InputList);
        assertEquals(CommaSeperatedStringExpected[3],output);
    }

    @Test
    public void testCommaSeperatedStringEmptyList(){
        String output = StringUtility.asCommaSeperatedString(InputList);
        assertEquals(CommaSeperatedStringExpected[4],output);
    }
}
