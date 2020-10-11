package com.revature.controller;
import com.revature.model.Frontend.FrontendObj;
import com.revature.model.Validate.ValidateObj;

public class ValidateData {
    public static ValidateObj validate(FrontendObj obj){
        //validate return object
        ValidateObj validate = new ValidateObj();
        Boolean status = true;

        //check if maven project
        Boolean isMaven = obj.getIsMaven();

        //Maven
        String projectNameMaven = obj.getMavenData().getProjectName();
        // String projectVersionMaven = obj.getMavenData().getVersion();

        //Node
        String projectNameNode = obj.getNpmData().getProjectName();
        // String projectVersionNode = obj.getNpmData().getVersion();
        
        if(isMaven) {
            if(projectNameMaven.contains(" ")){
                validate.setIsValid(false);
                validate.setMessage("the maven project cannot have spaces");
                status = false;
            }
        } else {
            if(projectNameNode.contains(" ")){
                validate.setIsValid(false);
                validate.setMessage("the node project cannot have spaces");
                status = false;
            }
        }

        // if all is good set and return this stuff
        if(status){
            validate.setIsValid(true);
            validate.setMessage("Valid Input");
        }

        return validate;
    }
}
