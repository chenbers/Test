package com.inthinc.pro.automation.selenium;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.support.ClassPathXmlApplicationContext;

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
                setBuildNumber(selenium.getText("footerForm:version"));
                selenium.stop();

                // check error var for entries
                if (errors.isEmpty()) {
                    setTestVerdict(Verdicts.PASS); // no errors = pass
                } else if (!errors.isEmpty()) {
                    setTestVerdict(Verdicts.FAIL); // errors = fail
                }

            } catch (Exception e) {
                logger.debug(StackToString.toString(e));
            }
        }
    }

    public void before() {
        startTime = currentTime();
        try {
            try {
                String[] configFiles = new String[] { "classpath:spring/applicationContext-automation.xml" };
                BeanFactory factory = new ClassPathXmlApplicationContext(configFiles);
                AutomationPropertiesBean apb = (AutomationPropertiesBean) factory.getBean("automationPropertiesBean");
                selenium = GlobalSelenium.getYourOwn(apb.getDefaultWebDriver(), apb.getBaseURL());
            } catch (NoSuchBeanDefinitionException e) {
                logger.error(StackToString.toString(e));
                selenium = GlobalSelenium.getYourOwn();
            } catch (BeanCreationException e) {
                logger.error(StackToString.toString(e));
                selenium = GlobalSelenium.getYourOwn();
            }
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
        selenium.Pause(timeToPauseInSeconds);
    }

}
