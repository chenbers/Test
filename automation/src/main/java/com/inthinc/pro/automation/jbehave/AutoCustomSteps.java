package com.inthinc.pro.automation.jbehave;

import java.awt.event.KeyEvent;
import java.util.List;

import org.jbehave.core.annotations.AfterScenario;
import org.jbehave.core.annotations.Aliases;
import org.jbehave.core.annotations.Composite;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Named;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.openqa.selenium.WebDriver;

import com.inthinc.pro.automation.AutomationPropertiesBean;
import com.inthinc.pro.automation.models.Account;
import com.inthinc.pro.automation.models.User;
import com.inthinc.pro.automation.rest.RestCommands;
import com.inthinc.pro.automation.selenium.AutomationProperties;
import com.inthinc.pro.automation.selenium.CoreMethodLib;
import com.inthinc.pro.automation.utils.KeyCommands;
import com.inthinc.pro.automation.utils.MasterTest;

public class AutoCustomSteps {
    

    private static final String editableAccountUser = "an account that can be edited";
    private static final String editableUser = "as a user that can be edited";
    private static final String mainUser = "as the default user";
    
    private static ThreadLocal<User> myUser = new ThreadLocal<User>();

    private final AutomationPropertiesBean apb;
    private static ThreadLocal<RestCommands> rest = new ThreadLocal<RestCommands>();
    
    private static ThreadLocal<String> savedPage = new ThreadLocal<String>();
    private static ThreadLocal<Account> account = new ThreadLocal<Account>();
    private MasterTest test;

    
    public AutoCustomSteps(){
        apb = AutomationProperties.getPropertyBean(); 
        test = new MasterTest(){};
    }

    
    public static String getEditableaccountuser() {
        return editableAccountUser;
    }
    
    public static String getEditableuser() {
        return editableUser;
    }
    
    
    public static String getMainuser() {
        return mainUser;
    }

    public User getMyUser() {
        return myUser.get();
    }
    
    @Given("I hit the Period key")
    @When("I hit the Period key")
    public static void keyPeriod() {
        KeyCommands.typeKey(KeyEvent.VK_PERIOD);
    }
    
    @Given("I hit the Spacebar")
    @When("I hit the Spacebar")
    public static void spaceBar() {
        KeyCommands.typeKey(KeyEvent.VK_SPACE);
    }
    
    @Then("I assert \"$lookfor\" is on the page")
    public boolean assertIsTextOnPage(String lookfor) { 
        return test.assertTrue(CoreMethodLib.getSeleniumThread().isTextPresent(lookfor), lookfor + " was not found on this page.");
    }
    

    @AfterScenario
    public void clearUser(){
        if (rest.get() == null || myUser == null){
            return;
        }
        User isUpdated = rest.get().getObject(User.class, myUser.get().getUserID());
        if (isUpdated.doesPasswordMatch(myUser.get().getPassword())){
            return;
        } 
        rest.get().putObject(User.class, myUser.get(), null);
    }


    @Given("I hit the Enter Key")
    @When("I hit the Enter Key")
    public void enterKey() {
        CoreMethodLib.getSeleniumThread().enterKey();
    }
    

    @Given("I am logged in $params")
    @Composite(steps = {
            "Given I am using <params> for my user",
            "Given I log in"})
    public void givenIAmLoggedInAs(@Named("params")String params){
    }
    
    @Given("I am logged in")
    @Composite(steps = {
            "Given I am using as the default user for my user",
            "Given I log in"})
    public void givenIAmLoggedIn(){}
    
    @Given("I log in")
    @When("I log in")
    @Composite(steps = {
            "Given I am on the Login page", 
            "When I type my user name into the Username field", 
            "When I type my password into the Password field", 
            "When I click the Login button"})
    public void loginProcess(){}

    @Given("I click the bookmark I just added")
    @When("I click the bookmark I just added")
    public void openSavedPage() {
        test.open(savedPage.get());
    }


    @Given("I bookmark the page")
    @When("I bookmark the page")
    public void savePageLink() {
        savedPage.set(test.getCurrentLocation());
    }

    @Given("I hit the Tab Key")
    @When("I hit the Tab Key")
    public void tabKey() {
        CoreMethodLib.getSeleniumThread().tabKey();
    }

    @Given("I type to the active field")
    @When("I type to the active field")
    @Aliases(values={"I type to the element with focus"})
    public void typeToElementWithFocus(String type) {
        WebDriver web = CoreMethodLib.getSeleniumThread().getWrappedDriver();
        web.switchTo().activeElement().sendKeys(type);
    }
    

    @Given("I am using $params for my user")
    public void useParamsToSetDefaultUser(@Named("params")String params){
        if (params.equalsIgnoreCase(mainUser)){
            List<String> users = apb.getMainAutomation();
            rest.set(new RestCommands(users.get(0), apb.getPassword()));
            myUser.set(rest.get().getObject(User.class, users.get(1)));
        } else if (params.equalsIgnoreCase(editableUser)){
            
        } else if (params.equalsIgnoreCase(editableAccountUser)){
            List<String> users = apb.getEditableAccount();
            rest.set(new RestCommands(users.get(0), apb.getPassword()));
            myUser.set(rest.get().getObject(User.class, users.get(1)));
            account.set(rest.get().getObject(Account.class, null));
        }
        if (myUser == null){
            throw new NullPointerException("Type of user was bad: " + params);
        }
        AutoStepVariables.getVariables().put("my user name", myUser.get().getUsername());
        AutoStepVariables.getVariables().put("my password", apb.getPassword());
    }
    
    @AfterScenario
    public void afterScenario(){
        if (account.get()!= null){
            rest.get().putObject(Account.class, account.get(), null);
        }
    }


    @Then("I verify $lookfor is on the page")
    public boolean verifyIsTextOnPage(String lookfor) { 
        String actualString = AutoStepVariables.getComparator(lookfor);
        return test.validateTrue(CoreMethodLib.getSeleniumThread().isTextPresent(actualString), actualString + " was not found on this page.");
    }

}
