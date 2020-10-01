package revature.projectFactory.spinnaker.mapper;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import revature.projectFactory.spinnaker.POJO.PipelinePojo;
import revature.projectFactory.spinnaker.POJO.ReturnMessage;

public class Mapper {
    private ObjectMapper objectmapper;
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public Mapper() {
        objectmapper = new ObjectMapper();
    }

    public PipelinePojo pipelinePojoReadMapper(String json) {
        PipelinePojo obj = null;
        try {
            obj = objectmapper.readValue(json, PipelinePojo.class);
        } catch (IOException e) {
            log.error("Exception occurred when mapping Json String to a Pipeline object \n perhaps the Json was structured incorrectly" , e);
            e.printStackTrace();
        }
        return obj;
    }

    public ReturnMessage returnMessageMapper(String json){
        ReturnMessage obj = null;
        try {
            obj = objectmapper.readValue(json, ReturnMessage.class);
        } catch (IOException e) {
            log.error("Exception occurred when mapping Json String to a ReturnMessage object \n perhaps the Json was structured incorrectly" , e);
            e.printStackTrace();
        }
        return obj;
    }

    public String writeMapper(PipelinePojo obj) {
        String json = null;
        try {
            json = objectmapper.writeValueAsString(obj);
            log.info("Spinnaker pipeline mapper write ran");
        } catch (JsonProcessingException e) {
            log.error("Spinnaker Exception with write mapper ", e);
            e.printStackTrace();
        }
        return json;
    }

    public String writeMapper(ReturnMessage obj) {
        String json = null;
        try {
            json = objectmapper.writeValueAsString(obj);
            log.info("Spinnaker ReturnMessage mapper write ran");
        } catch (JsonProcessingException e) {
            log.error("Spinnaker Exception with write mapper ", e);
            e.printStackTrace();
        }
        return json;
    }

}
