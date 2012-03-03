package com.inthinc.pro.automation.test;

import java.util.HashMap;

import org.apache.commons.httpclient.NameValuePair;
import org.apache.log4j.Level;
import org.json.JSONObject;

import com.inthinc.pro.automation.AutomationPropertiesBean;
import com.inthinc.pro.automation.selenium.AutomationProperties;
import com.inthinc.pro.automation.utils.MasterTest;
import com.inthinc.pro.rally.RallyWebServices;
import com.inthinc.pro.rally.TestCase;
import com.inthinc.pro.rally.TestCaseResult;
import com.inthinc.pro.rally.TestCaseResult.Verdicts;

public class RallyTest {
    
    private volatile static HashMap<Long, TestCaseResult> tcrByThread = new HashMap<Long, TestCaseResult>();
    private volatile static HashMap<Long, String> testCaseByThread = new HashMap<Long, String>();
    
    private final Long threadID = Thread.currentThread().getId();
    
    public interface RallyTestInterface {
        
        public void before();

        public void after();
        
        public boolean runToday();
        
        public void setTestSet(String name);

        public void set_test_case(String formattedID) ;
        public String get_test_case();
        
        public String getTestSet() ;
        
    }
    
//    private TestCaseResult tcr;
    private AutomationPropertiesBean apb = AutomationProperties.getPropertyBean();
    private String testCase;
    private JSONObject deletelastResults;
    private Test superTest;
    
    
    public RallyTest(Test superTest){
        this.superTest = superTest;
        addTestCaseResult(threadID);
    }
    
    public static synchronized void addTestCaseResult(Long threadID){
        if (!tcrByThread.containsKey(threadID)){
            TestCaseResult tcr = new TestCaseResult(RallyWebServices.username, RallyWebServices.password, RallyWebServices.INTHINC);
            tcrByThread.put(threadID, tcr);
        } else {
            tcrByThread.get(threadID).newResults();
        }
    }
    
    public void before() {
        try {
            if (!tcrByThread.containsKey(threadID)){
                addTestCaseResult(threadID);
            }
        } catch (Exception e) {
            e.printStackTrace();
            superTest.setSkip(true);
            throw new NullPointerException();
        }
    }

    public void after() {
        TestCaseResult tcr = tcrByThread.get(threadID);
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
                if (tcr.hasVitalFields()){
                    tcr.send_test_case_results();
                } else {
                    MasterTest.print("Test case is missing fields necessary to update Rally", Level.FATAL);
                }
                tcr = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    public boolean runToday(){
        TestCaseResult tcr = tcrByThread.get(threadID);
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
        MasterTest.print("setTestSet("+name+")");
        tcrByThread.get(threadID).setTestSet(new NameValuePair("Name", name));
    }

    public static synchronized void set_test_case(String formattedID, Long threadID) {
        MasterTest.print("set_test_case("+formattedID+") for Thread : " + threadID);
        testCaseByThread.put(threadID, formattedID);
        tcrByThread.get(threadID).setTestCase(new NameValuePair(TestCase.Fields.FORMATTED_ID.toString(), formattedID));
    }
    public static synchronized void set_defect(String formattedID, Long threadID){
        MasterTest.print("set_defect("+formattedID+") for Thread : " + threadID);
        tcrByThread.get(threadID).setNotes("  DefectID: "+formattedID);
    }
    public String get_test_case(){
        return testCaseByThread.get(threadID);
    }
    
    public String getTestSet() {
        return apb.getRallyName();
    }

}
