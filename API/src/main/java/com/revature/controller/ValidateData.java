package com.revature.controller;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        String projectVersionMaven = obj.getMavenData().getVersion();
        String packageMaven = obj.getMavenData().getPackaging();
        String groupIdMaven = obj.getMavenData().getGroupId();
        String packageAndGroup = packageMaven + groupIdMaven;

        //Node
        String projectNameNode = obj.getNpmData().getProjectName();
        String projectVersionNode = obj.getNpmData().getVersion();
        

        //check for spaces in project name
        if(isMaven) {
            if(projectNameMaven.contains(" ")) {
                validate.setIsValid(false);
                validate.setMessage("the maven project cannot have spaces");
                status = false;
                return validate;
            }
        } else {
            if(projectNameNode.contains(" ")){
                validate.setIsValid(false);
                validate.setMessage("the node project cannot have spaces");
                status = false;
                return validate;
            }
        }


        //check that the version formatting is correct
        if(isMaven) {
            if(isNormalInteger(projectVersionMaven) == false) {
                validate.setIsValid(false);
                validate.setMessage("Incorrect version formatting.  Must follow X.X.X format. X cannot be a negative number and you cannot have all zeroes.");
                status = false;
                return validate;
            }
        } else {
            if(isNormalInteger(projectVersionNode) == false){
                validate.setIsValid(false);
                validate.setMessage("Incorrect version formatting.  Must follow X.X.X format. X cannot be a negative number and you cannot have all zeroes.");
                status = false;
                return validate;
            }
        }

        //check that the package prefix
        if(isMaven) {
            if(prefixCheck(packageMaven) == false) {
                validate.setIsValid(false);
                validate.setMessage("Your package must begin with com.revature. or com.revaturelabs.");
                status = false;
                return validate;
            }
        }


        //check that the package and groupid formatting
        if(isMaven) {
            if(packageAndGroupCheck(packageAndGroup) == false) {
                validate.setIsValid(false);
                validate.setMessage("Your package and groupID cannot have capital letters or special characters that are not a period.");
                status = false;
                return validate;
            }
        }


        // if all is good set and return this stuff
        if(status){
            validate.setIsValid(true);
            validate.setMessage("Valid Input");
        }

        return validate;
    }

    

    //this method will check the version input to make sure it is proper format.  Return true if it is.
    public static boolean isNormalInteger(String str) {
        
        //Check for if it follows the X.X.X format
        boolean match = str.matches("\\d+\\.\\d+\\.\\d+");
        if (match == false) {
            return false;
        }

        
        //Check if valid integer that is not negative and not just all 0s
        String str2 = str.replace(".", "");
        try {
            int nums = Integer.parseInt(str2);
            if (nums <= 0) {
                return false;
            }
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }


    public static boolean packageAndGroupCheck(String str) {
        String regex = "^[.a-z0-9]*$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        Boolean bool = matcher.matches();

        if (bool == false || bool == null) {
            return false;
        }
        return true;
    }

    
    public static boolean prefixCheck(String projpackage) {
        if (projpackage.startsWith("com.revature.") == false &&  projpackage.startsWith("com.revaturelabs.") == false) {
            return false;
        }
        return true;
    }

}
