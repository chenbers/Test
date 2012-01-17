package com.inthinc.pro.automation.test;

import com.inthinc.pro.automation.enums.ErrorLevel;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.automation.test.RallyTest.RallyTestInterface;

/****************************************************************************************
 * Purpose: To standardize the setup and teardown for System Test Automation tests
 * <p>
 * 
 * @author dtanner
 */
public class BrowserRallyTest extends BrowserTest implements RallyTestInterface {
    
    private RallyTest rallyTest;
    

    public BrowserRallyTest(SeleniumEnums version) {
        super(version);
        rallyTest = new RallyTest(this);
    }


    @Override
    public void before() {
        super.before();
        rallyTest.before();
    }

    @Override
    public void after() {
        super.after();
        rallyTest.after();
        
    }

    @Override
    public boolean runToday() {
        return rallyTest.runToday();
    }

    @Override
    public void setTestSet(String name) {
        rallyTest.setTestSet(name);
    }

    @Override
    public void set_test_case(String formattedID) {
        rallyTest.set_test_case(formattedID);
    }
    
    public void set_defect(String formattedID) {
        rallyTest.set_defect(formattedID);
        addError("This TestCase is linked to Defect: "+formattedID, ErrorLevel.WARN);
    }

    @Override
    public String get_test_case() {
        return rallyTest.get_test_case();
    }

    @Override
    public String getTestSet() {
        return rallyTest.getTestSet();
    }
}
