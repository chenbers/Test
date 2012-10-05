package com.inthinc.pro.automation.jbehave;

import java.io.StringWriter;
import java.util.List;
import java.util.Map;

import org.jbehave.core.annotations.AfterScenario;
import org.jbehave.core.annotations.Aliases;
import org.jbehave.core.annotations.Composite;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Named;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.interactions.Actions;

import com.inthinc.pro.automation.AutomationPropertiesBean;
import com.inthinc.pro.automation.logging.Log;
import com.inthinc.pro.automation.models.Account;
import com.inthinc.pro.automation.models.Person;
import com.inthinc.pro.automation.models.User;
import com.inthinc.pro.automation.rest.RestCommands;
import com.inthinc.pro.automation.selenium.CoreMethodLib;
import com.inthinc.pro.automation.utils.MasterTest;

public class AutoCustomSteps {
    

    private static final String editableAccountUser = "an account that can be edited";
    private static final String editableUser = "as a user that can be edited";
    private static ThreadLocal<Account> loginAccount = new ThreadLocal<Account>();
    
    private static ThreadLocal<User> loginUser = new ThreadLocal<User>();
    private static final String mainUser = "as the default user";

    private static ThreadLocal<RestCommands> rest = new ThreadLocal<RestCommands>();
    private static ThreadLocal<String> savedPage = new ThreadLocal<String>();
    
    private static final String userVariable = "my user name";
    private static final String passwordVariable = "my password";
    
    public static final String getUserVariable(){
        return userVariable;
    }
    
    public static final String getPasswordVariable(){
        return passwordVariable;
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
    
    private final AutomationPropertiesBean apb;

    private MasterTest test;
    
    public AutoCustomSteps(){
        apb = AutomationPropertiesBean.getPropertyBean(); 
        test = new MasterTest(){};
    }
    
    @AfterScenario
    public void afterScenario(){
        if (loginAccount.get() == null || loginUser.get() == null || rest.get() == null){
            Log.info("The default login info was not set");
            return;
        }
        
        rest.get().putObject(Account.class, loginAccount.get(), null);
        rest.get().putObject(User.class, loginUser.get(), null);
        
    }
    
    @Then("I assert \"$lookfor\" is on the page")
    public boolean assertIsTextOnPage(String lookfor) { 
        return test.assertTrue(CoreMethodLib.getSeleniumThread().isTextPresent(lookfor), lookfor + " was not found on this page.");
    }
    


    @Given("I change my username to $username and my password to $password")
    public void changeUserNameAndPassword(@Named("username")String username, @Named("password")String password){
        AutoStepVariables.getVariables().put(userVariable, loginUser.get().getUsername());
        AutoStepVariables.getVariables().put(passwordVariable, apb.getPassword());
    }
    

    @Given("I combine $variableList and save them as $variableName")
    @When("I combine $variableList and save them as $variableName")
    @Then("I combine $variableList and save them as $variableName")
    public void combineVariables(@Named("variableList")String variableList, @Named("variableName")String variableName){
        String[] variables = variableList.split("\\s+with\\s+");
        StringWriter writer = new StringWriter();
        for (String variable : variables){
            writer.write(AutoStepVariables.getValue(variable));
        }
        AutoStepVariables.getVariables().put(variableName, writer.toString());
    }
    
    @Given("I press the Enter Key")
    @When("I press the Enter Key")
    @Then("I press the Enter Key")
    public void keyEnter() {
    	
    	//WebDriver driver = new FirefoxDriver();
    	//driver.findElement(By.xpath("//button[@class='btn btn-large btn-block btn-inthinc']")).sendKeys(Keys.RETURN);
    	//CoreMethodLib.getSeleniumThread().keyDownNative(java.awt.event.KeyEvent.VK_ENTER + "");
    	//CoreMethodLib.getSeleniumThread().keyUpNative(java.awt.event.KeyEvent.VK_ENTER + "");
        CoreMethodLib.getSeleniumThread().enterKey();
    }
    
    @Given("I press the Period Key")
    @When("I press the Period Key")
    @Then("I press the Period Key")
    public static void keyPeriod() {
    	CoreMethodLib.getSeleniumThread().periodKey();
    }
    
    @Given("I press the Spacebar Key")
    @When("I press the Spacebar Key")
    @Then("I press the Spacebar Key")
    public static void spaceBar() {
    	CoreMethodLib.getSeleniumThread().spacebarKey();
    }
    
    @Given("I press the Tab Key")
    @When("I press the Tab Key")
    @Then("I press the Tab Key")
    public void tabKey() {
        CoreMethodLib.getSeleniumThread().tabKey();
    }
    
    public User getMyUser() {
        return loginUser.get();
    }

    @Given("I am logged in")
    @Composite(steps = {
            "Given I am using as the default user for my user",
            "Given I log in"})
    public void givenIAmLoggedIn(){}


    @Given("I am logged in $params")
    @Composite(steps = {
            "Given I am using <params> for my user",
            "Given I log in"})
    public void givenIAmLoggedInAs(@Named("params")String params){
    }

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
        System.out.println("Clicked page: " + savedPage.get());
        
    }
    

    @Given("I go back to the default user")
    public void resetUsernameAndPassword(){
        if (loginUser.get() != null){
            changeUserNameAndPassword(loginUser.get().getUsername(), apb.getPassword());
        }
    }
    
    @Given("I bookmark the page")
    @When("I bookmark the page")
    public void savePageLink() {
        savedPage.set(test.getCurrentLocation());
        System.out.println("Saved page: " + test.getCurrentLocation());
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
        List<String> users;

        if (params.equalsIgnoreCase(mainUser)){
            users = apb.getMainAutomation();
        } else if (params.equalsIgnoreCase(editableUser)){
            users = apb.getEditableAccount();
        } else if (params.equalsIgnoreCase(editableAccountUser)){
            users = apb.getEditableAccount();
        } else {
            return;
        }
        
        rest.set(new RestCommands(users.get(0), apb.getPassword()));
        loginUser.set(rest.get().getObject(User.class, users.get(1)));
        loginUser.get().setPerson(rest.get().getObject(Person.class, loginUser.get().getPersonID()));
        loginAccount.set(rest.get().getObject(Account.class, null));

        changeUserNameAndPassword(loginUser.get().getUsername(), apb.getPassword());
    }
    
    @Then("I verify $lookfor is on the page")
    public boolean verifyIsTextOnPage(String lookfor) { 
        String actualString = AutoStepVariables.getComparator(lookfor);
        return test.validateTrue(CoreMethodLib.getSeleniumThread().isTextPresent(actualString), actualString + " was not found on this page.");
    }
    
    @Given("I split $varName with \"$symbol\" and save it again as $varList")
    @When("I split $varName with \"$symbol\" and save it again as $varList")
    @Then("I split $varName with \"$symbol\" and save it again as $varList")
    public void splitVariable(@Named("varName")String varName, @Named("symbol")String symbol, @Named("varList")String varList){
        String[] splitList = AutoStepVariables.getValue(varName).split(symbol);
        String[] varNames = varList.split(", ");
        int length = varNames.length;
        if (splitList.length < length){
            throw new IllegalArgumentException(String.format("The variable: '%s' cannot be split %d ways", varName, varNames.length));
        }
        Map<String, String> variables = AutoStepVariables.getVariables();
        for (int i=0;i<length;i++){
            variables.put(varNames[i], splitList[i]);
        }
    }
    
    @Given("I replace \"$replaced\" with \"$toReplace\" in variable $variable")
    @When("I replace \"$replaced\" with \"$toReplace\" in variable $variable")
    @Then("I replace \"$replaced\" with \"$toReplace\" in variable $variable")
    public void replaceString(@Named("replaced")String replaced, @Named("toReplace")String toReplace, @Named("variable")String variable){
        String oldVar = AutoStepVariables.getValue(variable);
        String newVar = oldVar.replace(replaced, toReplace);
        AutoStepVariables.getVariables().put(variable, newVar);
    }
    
    @Given("I save \"$toSave\" as $variableName")
    @When("I save \"$toSave\" as $variableName")
    @Then("I save \"$toSave\" as $variableName")
    public void saveString(@Named("toSave")String toSave, @Named("variableName")String variableName){
        AutoStepVariables.getVariables().put(variableName, toSave);
    }

}
