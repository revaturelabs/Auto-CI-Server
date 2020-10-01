package revature.projectFactory.spinnaker.mapperTest;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import revature.projectFactory.spinnaker.POJO.PipelinePojo;
import revature.projectFactory.spinnaker.mapper.Mapper;

public class MapperTest {
    private String jsonTest = "{\"jobName\":\"testJob\",\"gitUri\":\"testUri/.:?\",\"metaData\":\"testData\",\"cloudProviders\":\"testProviders,Other\",\"email\":\"testemail\",\"projectName\":\"test\"}";
    private PipelinePojo expectedObj = new PipelinePojo("testJob", "testUri/.:?", "testData", "testProviders,Other", "testemail", "test");
    
    @Test
    public void testPipelinePojoReadMapper(){
        Mapper mapper = new Mapper();
        PipelinePojo actualObj = mapper.pipelinePojoReadMapper(jsonTest);
        assertEquals(expectedObj.toString(),actualObj.toString());
    }

    @Test
    public void testWriteMapper(){
        Mapper mapper = new Mapper();
        String actual = mapper.writeMapper(expectedObj);
        assertEquals(jsonTest, actual);
    }
}
