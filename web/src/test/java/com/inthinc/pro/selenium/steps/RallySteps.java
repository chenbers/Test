package com.inthinc.pro.selenium.steps;

import org.jbehave.core.annotations.Given;

public class RallySteps extends WebSteps {
    @Given("I should record these test results in Rally for $rallyTestCaseNumber")
    public void givenRecordTestResultsInRally(String rallyTestCaseNumber){
        set_test_case(rallyTestCaseNumber);
    }
    @Given("I should associate these test results with $rallyDefectNumber in Rally")
    public void givenAssociateWithRallyDefect(String rallyDefectNumber){
        set_defect(rallyDefectNumber);
    }
}
