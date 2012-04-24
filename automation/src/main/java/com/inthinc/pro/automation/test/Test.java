package com.inthinc.pro.automation.test;

import com.inthinc.pro.automation.AutomationPropertiesBean;
import com.inthinc.pro.automation.selenium.AutomationProperties;
import com.inthinc.pro.automation.selenium.ErrorCatcher;
import com.inthinc.pro.automation.utils.MasterTest;
import com.inthinc.pro.rally.TestCaseResult.Verdicts;

public class Test extends MasterTest {
    
    protected Long startTime;

    private AutomationPropertiesBean automationPropertiesBean;

    private ErrorCatcher errors;

    protected Boolean skip = false;
    private Verdicts testVerdict;
    
    private String buildNumber;
    protected Long stopTime;
    
    public void before(){
        setStartTime();
    }
    
    public void after(){
        setStopTime();
        setTestVerdict();
    }
    
    public void setErrorCatcher(ErrorCatcher errors){
        this.errors = errors;
    }
    
    public ErrorCatcher getErrorCatcher(){
        return errors;
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

    public void setTestVerdict() {
        this.testVerdict = errors.getHighestLevel();
    }
    
    public static Long currentTime(){
        return System.currentTimeMillis()/1000;
    }
    
    public void didTestFail(){
        if (getTestVerdict() != Verdicts.PASS) {
            throw new AssertionError(errors != null ? errors.toString() : "errors was null?");
        }
    }
    

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime() {
        this.startTime = currentTime();
    }

    public Boolean getSkip() {
        return skip;
    }

    public void setSkip(Boolean skip) {
        this.skip = skip;
    }

    public Long getStopTime() {
        return stopTime;
    }

    public void setStopTime() {
        this.stopTime = currentTime();
    }
}
