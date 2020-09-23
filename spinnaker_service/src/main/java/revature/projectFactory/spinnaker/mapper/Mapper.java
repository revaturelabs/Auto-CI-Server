package revature.projectFactory.spinnaker.mapper;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import revature.projectFactory.spinnaker.POJO.PipelinePojo;

public class Mapper {
    private ObjectMapper objectmapper;

    public Mapper() {
        objectmapper = new ObjectMapper();
    }

    public PipelinePojo pipelinePojoReadMapper(String json) {
        PipelinePojo obj = null;
        try {
            obj = objectmapper.readValue(json, PipelinePojo.class);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return obj;
    }

    public String writeMapper(PipelinePojo obj) {
        String json = null;
        try {
            json = objectmapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return json;
    }

}
