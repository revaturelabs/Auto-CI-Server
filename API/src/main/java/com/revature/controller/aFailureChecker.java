package com.revature.controller;

import okhttp3.Response;

public class aFailureChecker {
    
    public static boolean CheckCode(Response response) {
        if (response.code() == 200) {
            return true;
        } else {
            return false;
        }
    }
}
