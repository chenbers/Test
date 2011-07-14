package com.inthinc.pro.convert;

import org.junit.Test;

import com.inthinc.pro.backing.BaseBeanTest;
import com.inthinc.pro.backing.LocaleBean;

public class ForceUpperCaseConverterTest extends BaseBeanTest {

    @Test
    public void convertToUpperCaseTest() {
        
        loginUser("custom101");
        LocaleBean localeBean = new LocaleBean();
        localeBean.getLocale();
        
        ForceUpperCaseConverter converter = new ForceUpperCaseConverter();
        String lowerEmpID = "abcdefg";
        String expectedUpperEmID = "ABCDEFG";
        String upperEmpID = converter.getAsString(null, null, lowerEmpID);
        assertEquals(expectedUpperEmID,upperEmpID);
    }

    @Test
    public void convertToUpperCaseFromObjectTest() {
        
        loginUser("custom101");
        LocaleBean localeBean = new LocaleBean();
        localeBean.getLocale();
        
        ForceUpperCaseConverter converter = new ForceUpperCaseConverter();
        String lowerEmpID = "abcdefg";
        String expectedUpperEmID = "ABCDEFG";
        String upperEmpID = (String) converter.getAsObject(null, null, lowerEmpID);
        assertEquals(expectedUpperEmID,upperEmpID);
    }

}
