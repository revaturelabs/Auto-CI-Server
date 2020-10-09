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

    /**
     * A constuctor that creates an object mapper.
     * @author John Stibbards
     */
    public Mapper() {
        objectmapper = new ObjectMapper();
    }

    /**
     * A function to map json to a pipeline pojo
     * @param json
     * @return a pipeline pojo
     * @author John Stibbards
     */
    public PipelinePojo pipelinePojoReadMapper(String json) {
        PipelinePojo obj = null;
        try {
            obj = objectmapper.readValue(json, PipelinePojo.class);
        } catch (IOException e) {
            log.error("Exception occurred when mapping Json String to a Pipeline object \n perhaps the Json was structured incorrectly" , e);
        }
        return obj;
    }

    /**
     * A function to map json to a Return Message object
     * @param json the json to map
     * @return a Return message object
     * @author Reese Benson
     */
    public ReturnMessage returnMessageMapper(String json){
        ReturnMessage obj = null;
        try {
            obj = objectmapper.readValue(json, ReturnMessage.class);
        } catch (IOException e) {
            log.error("Exception occurred when mapping Json String to a ReturnMessage object \n perhaps the Json was structured incorrectly" , e);
        }
        return obj;
    }

    /**
     * A function to map a pipeline pojo to json
     * @param obj a pipeline pojo
     * @return a json repersentation of the pipeline pojo
     * @author John Stibbards
     */
    public String writeMapper(PipelinePojo obj) {
        String json = null;
        try {
            json = objectmapper.writeValueAsString(obj);
            log.info("Spinnaker pipeline mapper write ran");
        } catch (JsonProcessingException e) {
            log.error("Spinnaker Exception with write mapper ", e);
        }
        return json;
    }

    /**
     * A function to map a Return Message to json
     * @param obj a Return Message
     * @return a json repersentation of the pipeline pojo
     * @author Reese Benson
     */
    public String writeMapper(ReturnMessage obj) {
        String json = null;
        try {
            json = objectmapper.writeValueAsString(obj);
            log.info("Spinnaker ReturnMessage mapper write ran");
        } catch (JsonProcessingException e) {
            log.error("Spinnaker Exception with write mapper ", e);
        }
        return json;
    }

}
