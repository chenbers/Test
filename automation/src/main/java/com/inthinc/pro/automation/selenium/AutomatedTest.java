package com.inthinc.pro.automation.selenium;

import java.util.HashMap;

public abstract class AutomatedTest {

    protected Long startTime;
    protected static HashMap<String, HashMap<String, String>> errors;
    protected String testVerdict = "Fail";//TODO: jwimmer: right now there is no way to FAIL a test?  just "Pass" or "Errors"

    public void setTestVerdict(String testVerdict) {
        this.testVerdict = testVerdict;
    }

    protected Boolean skip = false;

    protected static CoreMethodLib selenium;

    public void before() {
        try {
            selenium = GlobalSelenium.getYourOwn();
            startTime = System.currentTimeMillis() / 1000;
        } catch (Exception e) {
            e.printStackTrace();
            skip = true;
            throw new NullPointerException();
        }

    }

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

}
