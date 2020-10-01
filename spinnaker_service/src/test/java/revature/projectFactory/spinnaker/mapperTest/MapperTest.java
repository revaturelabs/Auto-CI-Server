package revature.projectFactory.spinnaker.mapperTest;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import revature.projectFactory.spinnaker.POJO.PipelinePojo;
import revature.projectFactory.spinnaker.POJO.ReturnMessage;
import revature.projectFactory.spinnaker.mapper.Mapper;

public class MapperTest {
    private String jsonPipelineTest = "{\"gitUri\":\"testUri/.:?\",\"branch\":\"testBranch\",\"cloudProviders\":\"testProviders,Other\",\"email\":\"testemail\",\"projectName\":\"test\"}";
    private PipelinePojo expectedPipelineObj = new PipelinePojo("testUri/.:?", "testBranch", "testProviders,Other", "testemail", "test");
    private String jsonReturnMessage = "{\"applicationCreated\":true,\"pipelineCreated\":true}";
    private ReturnMessage rtnMessage = new ReturnMessage(true,true);
    private Mapper mapper = new Mapper();

    @Test
    public void testPipelinePojoReadMapper(){
        PipelinePojo actualObj = mapper.pipelinePojoReadMapper(jsonPipelineTest);
        assertEquals(expectedPipelineObj.toString(),actualObj.toString());
    }

    @Test
    public void testPipelineWriteMapper(){
        String actual = mapper.writeMapper(expectedPipelineObj);
        assertEquals(jsonPipelineTest, actual);
    }

    @Test
    public void testReturnMessageReadMapper(){
        ReturnMessage actual = mapper.returnMessageMapper(jsonReturnMessage);
        assertEquals(rtnMessage.toString(), actual.toString());
    }
    
    @Test
    public void testReturnMessageWriteMapper(){
        String actual = mapper.writeMapper(rtnMessage);
        assertEquals(jsonReturnMessage, actual);
    }
}
