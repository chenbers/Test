package com.inthinc.pro.automation.test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.httpclient.NameValuePair;
import org.jbehave.core.annotations.AfterScenario;
import org.jbehave.core.annotations.BeforeScenario;
import org.json.JSONObject;

import com.inthinc.pro.automation.AutomationPropertiesBean;
import com.inthinc.pro.automation.jbehave.RegexTerms;
import com.inthinc.pro.automation.logging.Log;
import com.inthinc.pro.automation.selenium.AutomationProperties;
import com.inthinc.pro.rally.RallyWebServices;
import com.inthinc.pro.rally.TestCase;
import com.inthinc.pro.rally.TestCaseResult;
import com.inthinc.pro.rally.TestCaseResult.Verdicts;

public class RallyTest {
    
    public interface RallyTestInterface {
        
        public void before();

        public void after();
        
        public boolean runToday();
        
        public void setTestSet(String name);

        public void set_test_case(String formattedID) ;
        public String get_test_case();
        
        public String getTestSet() ;
        
    }
    
    private TestCaseResult tcr;
    private AutomationPropertiesBean apb = AutomationProperties.getPropertyBean();
    private String testCase;
    private JSONObject deletelastResults;
    private Test superTest;
    
    
    
    public RallyTest(Test superTest){
        this.superTest = superTest;
    }
    
    public void before() {
        try {
            getTcr().newResults();
        } catch (Exception e) {
            Log.error(e);
            superTest.setSkip(true);
            throw new NullPointerException();
        }
    }

    public void after() {
        if (!superTest.getSkip()) {
        	if (tcr.hasVitalFields()){
	            try {
	                if (deletelastResults != null){
	                    tcr.deleteTestCaseResult(deletelastResults);
	                }
	                if(apb.getAddTestSet()){
	                    setTestSet(getTestSet());
	                }
	                tcr.setBuildNumber(superTest.getBuildNumber());
	                tcr.setVerdict(superTest.getTestVerdict());
	                tcr.setNotes(getTestSet(), superTest.getErrorCatcher());
	                tcr.setDuration(superTest.getStopTime() - superTest.getStartTime());
	                tcr.send_test_case_results();
	                tcr = null;
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
        	} else {
        		Log.error("Test case is missing fields necessary to update Rally");
        	}
        }
    }
    
    public boolean runToday(){
        TestCase tc = new TestCase(getTcr());
        tc.setTestCase(testCase);
        superTest.setSkip(tc.wasRunToday());
        if (!superTest.getSkip()){
            getTcr().setBuildNumber("N/A");
            getTcr().setVerdict(Verdicts.PASS);
            deletelastResults = getTcr().send_test_case_results();
        }
        return !superTest.getSkip();
    }
    
    public void setTestSet(String name){
        Log.info("setTestSet("+name+")");
        getTcr().setTestSet(new NameValuePair("Name", name));
    }
    
    public void parseJBehaveStep(String stepAsString){
        Pattern tc = Pattern.compile(RegexTerms.testCase);
        Matcher matchTC = tc.matcher(stepAsString);

        if (matchTC.find()) {

            String tcID = stepAsString.substring(matchTC.start(), matchTC.end());
            set_test_case(tcID);

            Pattern de = Pattern.compile(RegexTerms.defect);
            Matcher matchDE = de.matcher(stepAsString);
            if (matchDE.find()) {
                String deID = stepAsString.substring(matchDE.start(), matchDE.end());
                set_defect(deID);
            }
        }
    }
    
    private void resetTCR(){
        tcr = new TestCaseResult(RallyWebServices.username, RallyWebServices.password, RallyWebServices.INTHINC);   
    }
    
    private TestCaseResult getTcr(){
        if (tcr == null){
            resetTCR();
        }
        return tcr;
    }

    public void set_test_case(String formattedID) {
        Log.info("set_test_case("+formattedID+")");
        testCase = formattedID;
        getTcr().setTestCase(new NameValuePair(TestCase.Fields.FORMATTED_ID.toString(), formattedID));
    }
    public void set_defect(String formattedID){
        getTcr().setNotes("  DefectID: "+formattedID);
    }
    public String get_test_case(){
        return testCase;
    }
    
    public String getTestSet() {
        return apb.getRallyName();
    }

}
