package com.inthinc.pro.automation.test;

import com.inthinc.pro.automation.test.RallyTest.RallyTestInterface;


public class DeviceOnlyRallyTest extends DeviceOnlyTest implements RallyTestInterface {
    private RallyTest rallyTest;
    
    public DeviceOnlyRallyTest(){
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
        RallyTest.set_test_case(formattedID, Thread.currentThread().getId());
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
