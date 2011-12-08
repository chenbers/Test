package com.inthinc.pro.selenium.steps;

import java.util.HashMap;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Pending;
import org.jbehave.core.annotations.When;

import com.inthinc.pro.automation.enums.LoginCapability;
import com.inthinc.pro.automation.models.AutomationUser;
import com.inthinc.pro.automation.objects.AutomationUsers;
import com.inthinc.pro.selenium.pageObjects.PageAdminUsers;
import com.inthinc.pro.selenium.pageObjects.PageLogin;
import com.inthinc.pro.selenium.pageObjects.PageTeamDashboardStatistics;
import com.inthinc.pro.selenium.testSuites.WebRallyTest;

public class RallySteps extends WebRallyTest {
    @Given("I should record these test results in Rally for $rallyTestCaseNumber")
    public void givenRecordTestResultsInRally(String rallyTestCaseNumber){
        set_test_case(rallyTestCaseNumber);
    }
    @Given("I should associate these test results with $rallyDefectNumber in Rally")
    public void givenAssociateWithRallyDefect(String rallyDefectNumber){
        set_defect(rallyDefectNumber);
    }
    
    @When("I press the enter key on my keyboard")
    public void whenIPressTheEnterKeyOnMyKeyboard() {
        enterKey();
    }

    @When("I hit the Tab Key")
    public void whenIHitTheTabKey() {
        tabKey();
    }

}
