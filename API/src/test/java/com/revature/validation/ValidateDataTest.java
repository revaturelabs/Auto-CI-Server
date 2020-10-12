package com.revature.validation;

import com.revature.controller.ValidateData;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class ValidateDataTest {
    
    @Test
    public void projectVersionFormatCheckerTest() {
        String str = "1.2.0";
        String str2 = "111.222.333";
        String str3 = "2.3"; //in the case of not following the X.X.X format
        String str4 = "X.X.X"; //if they were to put all leters
        String str5 = "1.2.3b"; //Not just putting digits
        String str6 = "1.2.3.4"; //too many digits
        String str7 = "0.0.0"; //all zeroes
        assertTrue(ValidateData.isNormalInteger(str) && ValidateData.isNormalInteger(str2));
        assertFalse(ValidateData.isNormalInteger(str3));
        assertFalse(ValidateData.isNormalInteger(str4));
        assertFalse(ValidateData.isNormalInteger(str5));
        assertFalse(ValidateData.isNormalInteger(str6));
        assertFalse(ValidateData.isNormalInteger(str7));

    }

    @Test
    public void groupIdPackgeFormatCheckerTest() {
        String str1 = "com.revature.this.should.work";
        String str2 = "!hi";
        String str3 = "this should not work.";
        String str4 = "this.\nshould.not.work";
        String str5 = "com.revature.This.should.not.work";
        assertTrue(ValidateData.packageAndGroupCheck(str1));
        assertFalse(ValidateData.packageAndGroupCheck(str2));
        assertFalse(ValidateData.packageAndGroupCheck(str3));
        assertFalse(ValidateData.packageAndGroupCheck(str4));
        assertFalse(ValidateData.packageAndGroupCheck(str5));
    }

    @Test
    public void prefixCheckerTest() {
        String str1 = "com.revature.this.should.work";
        String str2 = "com.revaturelabs.this.should.also.work";
        String str3 = "this.should.not.work";
        String str5 = "com.revaturethis.should.not.work";
        String str6 = "com.revaturelabsthis.should.also.not.work";
        assertTrue(ValidateData.prefixCheck(str1));
        assertTrue(ValidateData.prefixCheck(str2));
        assertFalse(ValidateData.prefixCheck(str3));
        assertFalse(ValidateData.prefixCheck(str5));
        assertFalse(ValidateData.prefixCheck(str6));


    }
    
}
