package com.inthinc.pro.automation.selenium;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

import org.apache.log4j.Logger;

import com.inthinc.pro.automation.AutomationPropertiesBean;
import com.inthinc.pro.automation.utils.StackToString;
import com.inthinc.pro.rally.TestCaseResult.Verdicts;

public class AutomatedTest {

    protected Long startTime;
    protected ErrorCatcher errors;
    private AutomationPropertiesBean automationPropertiesBean;

    private final static Logger logger = Logger.getLogger(AutomatedTest.class);

    protected Boolean skip = false;
    private Verdicts testVerdict;

    private String buildNumber;
    protected Long stopTime;
    protected CoreMethodLib selenium;

    public static void afterClass() {
        GlobalSelenium.dieSeleniumDie();
    }// tear down

    public static void print(Object printToScreen) {
        StackTraceElement element = Thread.currentThread().getStackTrace()[2];
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String time = sdf.format(GregorianCalendar.getInstance().getTime());
        String className = element.getFileName().replace(".java", "");
        System.out.printf("%s, %s.%s:%3d - %s\n", time, className, element.getMethodName(), element.getLineNumber(), printToScreen.toString());
    }

    // public abstract void failTest();
    public void after() {
        stopTime = currentTime();
        if (!skip) {
            try {
                errors = selenium.getErrors();
                // check error var for entries
                if (errors.isEmpty() || errors==null) {
                    setTestVerdict(Verdicts.PASS); // no errors = pass
                } else if (!errors.isEmpty()) {
                    setTestVerdict(Verdicts.FAIL); // errors = fail
                }
                setBuildNumber(selenium.getText("footerForm:version"));
            } catch (Exception e) {
                logger.fatal(StackToString.toString(e));
            }finally{
                GlobalSelenium.dieSeleniumDie();   
            }
        }
    }

    public void before() {
        startTime = currentTime();
        try {
            selenium = GlobalSelenium.getYourOwn();
        } catch (Exception e) {
            logger.fatal(StackToString.toString(e));
            skip = true;
            throw new NullPointerException();
        }
    }

    public AutomationPropertiesBean getAutomationPropertiesBean() {
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
            throw new AssertionError(errors.toString());
        }
    }
    
    public void pause(Integer timeToPauseInSeconds){
        selenium.pause(timeToPauseInSeconds);
    }

}
