package com.inthinc.pro.automation.test;

import org.apache.commons.httpclient.NameValuePair;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import com.inthinc.pro.automation.AutomationPropertiesBean;
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
    protected final static Logger logger = Logger.getLogger(BrowserRallyTest.class);
    
    
    public RallyTest(Test superTest){
        this.superTest = superTest;
    }
    
    public void before() {
        try {
            tcr = new TestCaseResult(RallyWebServices.username, RallyWebServices.password, RallyWebServices.INTHINC);
            tcr.newResults();
        } catch (Exception e) {
            e.printStackTrace();
            superTest.setSkip(true);
            throw new NullPointerException();
        }
    }

    public void after() {
        if (!superTest.getSkip()) {
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
        }
    }
    
    public boolean runToday(){
        TestCase tc = new TestCase(tcr);
        tc.setTestCase(testCase);
        superTest.setSkip(tc.wasRunToday());
        if (!superTest.getSkip()){
            tcr.setBuildNumber("N/A");
            tcr.setVerdict(Verdicts.PASS);
            deletelastResults = tcr.send_test_case_results();
        }
        return !superTest.getSkip();
    }
    
    public void setTestSet(String name){
        logger.info("setTestSet("+name+")");
        tcr.setTestSet(new NameValuePair("Name", name));
    }

    public void set_test_case(String formattedID) {
        logger.info("set_test_case("+formattedID+")");
        testCase = formattedID;
        tcr.setTestCase(new NameValuePair(TestCase.Fields.FORMATTED_ID.toString(), formattedID));
    }
    public void set_defect(String formattedID){
        tcr.setNotes("  DefectID: "+formattedID);
    }
    public String get_test_case(){
        return testCase;
    }
    
    public String getTestSet() {
        return apb.getRallyName();
    }

}
