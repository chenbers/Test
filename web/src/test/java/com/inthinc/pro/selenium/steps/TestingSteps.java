package com.inthinc.pro.selenium.steps;

import org.apache.commons.lang.StringEscapeUtils;
import org.jbehave.core.annotations.Then;

import com.inthinc.pro.automation.logging.Log;

public class TestingSteps {
    
    @Then("I play $testString")
    public void testString(String testString){
        String html = StringEscapeUtils.unescapeHtml(testString);
        String java = StringEscapeUtils.unescapeJava(testString);
        
        Log.info(testString);
        
        Log.info(java);
        Log.info(StringEscapeUtils.unescapeHtml(java));
        
        Log.info(html);
        Log.info(StringEscapeUtils.unescapeJava(html));
    }

}
