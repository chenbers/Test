package com.inthinc.pro.automation.selenium;

import org.apache.log4j.Logger;

import com.inthinc.pro.automation.AutomationPropertiesBean;
import com.inthinc.pro.automation.enums.SeleniumEnumWrapper;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.automation.utils.MasterTest;
import com.inthinc.pro.automation.utils.StackToString;
import com.inthinc.pro.rally.TestCaseResult.Verdicts;

public class AutomatedTest extends MasterTest{

    protected Long startTime;
    private AutomationPropertiesBean automationPropertiesBean;

    protected final Logger logger = Logger.getLogger(AutomatedTest.class);

    protected Boolean skip = false;
    private Verdicts testVerdict;
    
    private static final String buildNotFound = "Couldn't Get Build Number";

    private String buildNumber = buildNotFound;
    protected Long stopTime;
    protected CoreMethodInterface selenium;
    
    private final SeleniumEnumWrapper webVersionID;
    private static boolean deviceTest;

    public AutomatedTest(){
        webVersionID=null;
    }
    
    public AutomatedTest(SeleniumEnums version){
    	webVersionID = new SeleniumEnumWrapper(version);
    }

    
    // public abstract void failTest();
    public void after() {
        stopTime = currentTime();
        if (!skip || deviceTest) {
            try {
                // check error var for entries
                setTestVerdict(errors.getHighestLevel());
                if (buildNumber.equals(buildNotFound) && selenium !=null){
                    setBuildNumber(selenium.getText(webVersionID));
                }
            } catch (Exception e) {
                logger.fatal(StackToString.toString(e));
            }finally{
                if (!deviceTest){
                    CoreMethodLib.closeSeleniumThread();   
                }
            }
        } else {
            logger.info(" skip ");
        }
    }

    public void before() {
        startTime = currentTime();
        if (!deviceTest){
            try {
                selenium = super.getSelenium();
            } catch (Exception e) {
                logger.fatal(StackToString.toString(e));
                skip = true;
                throw new NullPointerException();
            }
        }
    }

    public AutomationPropertiesBean getAutomationPropertiesBean() {
        if(automationPropertiesBean == null)
            automationPropertiesBean = AutomationProperties.getPropertyBean();
        return automationPropertiesBean;
    }

    public String getBuildNumber() {
        return buildNumber;
    }

    public Verdicts getTestVerdict() {
        return testVerdict;
    }

    public void setAutomationPropertiesBean(AutomationPropertiesBean automationProps) {
        this.automationPropertiesBean = automationProps;
    }

    public void setBuildNumber(String buildNumber) {
        this.buildNumber = buildNumber;
    }

    public void setTestVerdict(Verdicts testVerdict) {
        this.testVerdict = testVerdict;
    }
    
    public static Long currentTime(){
        return System.currentTimeMillis()/1000;
    }
    
    public void didTestFail(){
        if (getTestVerdict() != Verdicts.PASS) {
            throw new AssertionError(errors != null ? errors.toString() : "errors was null?");
        }
    }
    
    protected static void setAsDeviceTest(){
        deviceTest = true;
        errors = new ErrorCatcher();
    }
}
