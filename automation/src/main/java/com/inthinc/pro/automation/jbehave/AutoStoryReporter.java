package com.inthinc.pro.automation.jbehave;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.httpclient.NameValuePair;
import org.jbehave.core.reporters.ConcurrentStoryReporter;
import org.jbehave.core.reporters.StoryReporter;

import com.inthinc.pro.automation.test.RallyTest;
import com.inthinc.pro.rally.RallyWebServices;
import com.inthinc.pro.rally.TestCase;
import com.inthinc.pro.rally.TestCaseResult;

public class AutoStoryReporter extends ConcurrentStoryReporter {
    
    private final static String testCase = "[Tt][Cc][0123456789]*";
    private final static String defect = "[Dd][Ee][0123456789]*";
    
    private TestCaseResult tcr;

    public AutoStoryReporter(StoryReporter crossReferencing, StoryReporter delegate, boolean multiThreading) {
        super(crossReferencing, delegate, multiThreading);
    }

    
    @Override
    public void beforeScenario(String scenarioTitle) {
//        Pattern tc = Pattern.compile(testCase);
//        Matcher matchTC = tc.matcher(scenarioTitle);
//        
//        if (matchTC.find()) {
//            
//            String tcID = scenarioTitle.substring(matchTC.start(), matchTC.end());
//            tcr = new TestCaseResult(RallyWebServices.username, RallyWebServices.password, RallyWebServices.INTHINC);
//            tcr.setTestCase(new NameValuePair(TestCase.Fields.FORMATTED_ID.toString(), tcID));
//            
//            Pattern de = Pattern.compile(defect);
//            Matcher matchDE = de.matcher(scenarioTitle);
//            if (matchDE.find()){
//                String deID = scenarioTitle.substring(matchDE.start(), matchDE.end());
//                tcr.setNotes("  DefectID: "+deID);
//            }
//        }
        super.beforeScenario(scenarioTitle);
    }
    
    @Override
    public void afterScenario() {
        
    }

}
