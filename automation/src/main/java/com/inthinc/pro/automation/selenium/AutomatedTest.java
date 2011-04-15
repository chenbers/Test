package com.inthinc.pro.automation.selenium;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.HashMap;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;

import com.inthinc.automation.AutomationPropertiesBean;

public class AutomatedTest {

    protected Long startTime;
    protected static HashMap<String, HashMap<String, String>> errors;
    protected String testVerdict = "Fail";// TODO: jwimmer: right now there is no way to FAIL a test? just "Pass" or "Errors"
    AutomationPropertiesBean automationPropertiesBean;

    public AutomationPropertiesBean getAutomationPropertiesBean() {
        return automationPropertiesBean;
    }
    public void setAutomationPropertiesBean(AutomationPropertiesBean automationProps) {
        this.automationPropertiesBean = automationProps;
    }
    public void setTestVerdict(String testVerdict) {
        this.testVerdict = testVerdict;
    }

    protected Boolean skip = false;

    protected static CoreMethodLib selenium;

    public void before() {
        try {
            String[] configFiles = new String[] { "classpath:spring/applicationContext-automation.xml" };
            BeanFactory factory = new ClassPathXmlApplicationContext (configFiles);
            AutomationPropertiesBean apb = (AutomationPropertiesBean) factory.getBean("automationPropertiesBean");
            
            selenium = GlobalSelenium.getYourOwn(apb.getDefaultWebDriver(), apb.getBaseURL());
            startTime = System.currentTimeMillis() / 1000;
        } catch (Exception e) {
            e.printStackTrace();
            skip = true; 
            throw new NullPointerException();  //TODO: jwimmer: dTanner: why throw a NullPointerException here?
        }
    }
    
    //public abstract void failTest();
    public void after() {
        if (!skip) {
            try {
                errors = selenium.getErrors().get_errors();
                selenium.stop();

                // check error var for entries
                if (errors.isEmpty())
                    testVerdict = "Pass"; // no errors = pass
                else if (!errors.isEmpty())
                    testVerdict = "Errors"; // errors = fail

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        startTime = null;
    }

    public static void afterClass() {
        GlobalSelenium.dieSeleniumDie();
    }// tear down

    public static void print(String printToScreen) {
        StackTraceElement element = Thread.currentThread().getStackTrace()[2];
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String className = element.getFileName().replace(".java", "");
        System.out.printf("%s, %s.%s:%3d - %s\n",
                sdf.format(GregorianCalendar.getInstance().getTime()), 
                className, element.getMethodName(), element.getLineNumber(), printToScreen);
    }

}
