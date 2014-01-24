package com.inthinc.pro.selenium.steps;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import com.inthinc.pro.automation.logging.Log;
import com.inthinc.pro.automation.models.AutomationUser;
import com.inthinc.pro.selenium.pageObjects.PageAdminVehicles;
import com.inthinc.pro.selenium.pageObjects.PageFormsManage;
import com.inthinc.pro.selenium.pageObjects.PageLogin;
import com.inthinc.pro.selenium.pageObjects.PageNotificationsDiagnostics;
import com.inthinc.pro.selenium.pageObjects.PageNotificationsSafety;

public class LoginSteps extends WebSteps {
    
    PageLogin loginPage = new PageLogin();
    AutomationUser login;
    PageNotificationsDiagnostics notifdiag = new PageNotificationsDiagnostics();
    PageNotificationsSafety safteydiag = new PageNotificationsSafety();
    PageFormsManage manage = new PageFormsManage();
    PageAdminVehicles pav = new PageAdminVehicles();
    
    private static final PageLogin page = new PageLogin();
    
    @When("I log back in")
    public void whenILogBackIn() {
        loginPage._textField().username().type("secondPrime");
        loginPage._textField().password().type("2ut2CFmnH$f!");
        loginPage._button().logIn().click();
    }
    
    @When("I log back in under the editable account")
    public void whenILogBackInUnderTheEditableAccount() {
        loginPage._textField().username().type("secondEditable");
        loginPage._textField().password().type("2ut2CFmnH$f!");
        loginPage._button().logIn().click();
    }
    
    @Given("I navigate to the assets trailers page")
    public void givenINavigateToTheAssetsTrailersPage() {
        loginPage.open("https://qa.inthinc.com/assets/trailers");
        loginPage._textField().username().type("secondPrime");
        loginPage._textField().password().type("2ut2CFmnH$f!");
        loginPage._button().logIn().click();
    }
    
    @Given("I am logged in as a \"$roleName\" user")
    @When("I am logged in as a \"$roleName\" user")
    public void loginAsAUserofRole(String roleName) {
        
        if (roleName.equals("TestUser")) {
            page._textField().username().type("TEST_39880");
            page._textField().password().type("password");
            page._button().logIn().click();
        }
        if (roleName.equals("TeamOnly")) {
            page._textField().username().type("CaptainNemo");
            page._textField().password().type("Muttley");
            page._button().logIn().click();
        }
    }
    
    private String flipCase(String original) {
        char[] charArray = new char[original.length()];
        original.getChars(0, original.length(), charArray, 0);
        for (char ch : charArray) {
            if (Character.isUpperCase(ch))
                Character.toLowerCase(ch);
            else if (Character.isLowerCase(ch))
                Character.toUpperCase(ch);
        }
        return new String(charArray);
    }
    
    @When("I change the password to an incorrect case")
    public void whenIChangeThePasswordToAnIncorrectCase() {
        String originalPassword = loginPage._textField().password().getText();
        Log.debug("originalPassword: " + originalPassword);
        
        String incorrectCasePassword = flipCase(originalPassword);
        Log.debug("incorrectCasePassword: " + incorrectCasePassword);
        // TODO: jwimmer: watch the loggers and see if this works... I wouldn't be surprised if we do NOT get the original password correctly???
        loginPage._textField().password().type(incorrectCasePassword);
    }
    
    @When("the focus should be on the User Name Field")
    public void whenTheFocusShouldBeOnUserNameField() {
        if (!page._textField().username().hasFocus())
            test.addError("The User Name field does NOT have focus");
    }
    
    @Then("the focus should be on the Password Field")
    public void thenTheFocusShouldBeOnThePasswordField() {
        if (!loginPage._textField().password().hasFocus())
            test.addError("The Password field does NOT have focus");
    }
    
    @Then("the focus should be on the Log In Button")
    public void thenTheFocusShouldBeOnTheLogInButton() {
        if (!loginPage._button().logIn().hasFocus())
            test.addError("The log in button does NOT have focus");
    }
    
    @Then("the focus should be on the Forgot User Name or Password Link")
    public void thenTheFocusShouldBeOnTheForgotUserNameOrPasswordLink() {
        if (!loginPage._link().forgotUsernameOrPassword().hasFocus())
            test.addError("The forgot username or password link does NOT have focus");
    }
    
    @Then("the focus should be on the Privacy Policy Link")
    public void thenTheFocusShouldBeOnThePrivacyPolicyLink() {
        if (!loginPage._link().privacyPolicy().hasFocus())
            test.addError("The Password field does NOT have focus");
    }
    
    @Then("the focus should be on the Legal Notice Link")
    public void thenTheFocusShouldBeOnTheLegalNoticeLink() {
        if (!loginPage._textField().password().hasFocus())
            test.addError("The Legal Notice Link does NOT have focus");
    }
    
    @Then("the focus should be on the Support Link")
    public void thenTheFocusShouldBeOnTheSupportLink() {
        if (!loginPage._link().support().hasFocus())
            test.addError("The Support link does NOT have focus");
    }
    
    @Given("I move the focus to the <initialFocusedElement>")
    public void givenIMoveTheFocusToTheinitialFocusedElement(String initialFocusedElement) {
        // TODO: jwimmer: move focus based on initialFocusedElement String var?
        loginPage._textField().username().focus();
    }
    
    @Then("the focus should be on the <finalFocusedElement>")
    public void thenTheFocusShouldBeOnThefinalFocusedElement(String finalFocusedElement) {
        // TODO: jwimmer: check focus based on finalFocusedElement String var?
        if (!loginPage._link().support().hasFocus())
            test.addError("The " + finalFocusedElement + " does NOT have focus");
    }
    
    @When("I change the username to an incorrect case")
    public void whenIChangeTheUsernameToAnIncorrectCase() {
        String originalUserName = loginPage._textField().username().getText();
        Log.debug("originalUserName: " + originalUserName);
        
        String incorrectCaseUserName = flipCase(originalUserName);
        Log.debug("incorrectCasePassword: " + incorrectCaseUserName);
        // TODO: jwimmer: watch the loggers and see if this works... I wouldn't be surprised if we do NOT get the original password correctly???
        loginPage._textField().password().type(incorrectCaseUserName);
    }
    
    @When("I go to the admin vehicles page")
    public void whenIGoToTheAdminVehiclesPage() {
        pav.load();
    }
    
    // TODO: MWEISS - This now works, I just need to make it universal so I can be fed a column from any page
    @Then("the \"$columnLink\" column sorts correctly")
    // @Then("the \"$page\" \"$type\" \"$element\" column sorts correctly") I'm thinking of implementing this method so it takes it and
    // turns it into a string, and then I pass that string
    public void thenIValidateTheSortByDateTimeColumnSortsCorrectly(String columnLink) {
        
        String notifRedFlagPageDateTime = "notifredflag._link().sortByDateTime().click();";
        String notifRedFlagPageGroup = "notifredflag._link().sortByGroup().click();";
        String notifRedFlagPageDriver = "notifredflag._link().sortByDriver().click();";
        String notifRedFlagPageVehicle = "notifredflag._link().sortByVehicle().click();";
        
        String notifDiagPageDateTime = "notifdiag._link().sortByDateTime().click();";
        String notifDiagPageGroup = "notifdiag._link().sortByGroup().click();";
        String notifDiagPageDriver = "notifdiag._link().sortByDriver().click();";
        String notifDiagPageVehicle = "notifdiag._link().sortByVehicle().click();";
        
        String notifSafetyPageDateTime = "notifsafety._link().sortByDateTime().click();";
        String notifSafetyPageGroup = "notifsafety._link().sortByGroup().click();";
        String notifSafetyPageDriver = "notifsafety._link().sortByDriver().click();";
        String notifSafetyPageVehicle = "notifsafety._link().sortByVehicle().click();";
        
        String notifZonesPageDateTime = "notifzones._link().sortByDateTime().click();";
        String notifZonesPageGroup = "notifzones._link().sortByGroup().click();";
        String notifZonesPageDriver = "notifzones._link().sortByDriver().click();";
        String notifZonesPageVehicle = "notifzones._link().sortByVehicle().click();";
        
        String notifHOSPageDateTime = "notifhos._link().sortByDateTime().click();";
        String notifHOSPageGroup = "notifhos._link().sortByGroup().click();";
        String notifHOSPageDriver = "notifhos._link().sortByDriver().click();";
        String notifHOSPageVehicle = "notifhos._link().sortByVehicle().click();";
        
        String notifEmergencyPageDateTime = "notifemergency._link().sortByDateTime().click();";
        String notifEmergencyPageGroup = "notifemergency._link().sortByGroup().click();";
        String notifEmergencyPageDriver = "notifemergency._link().sortByDriver().click();";
        String notifEmergencyPageVehicle = "notifemergency._link().sortByVehicle().click();";
        
        String notifCrashPageDateTime = "notifcrash._link().sortByDateTime().click();";
        String notifCrashPageGroup = "notifcrash._link().sortByGroup().click();";
        String notifCrashPageDriver = "notifcrash._link().sortByDriver().click();";
        String notifCrashPageVehicle = "notifcrash._link().sortByVehicle().click();";
        String notifCrashPageOccupants = "notifcrash._link().sortByOccupants().click();";
        String notifCrashPageStatus = "notifcrash._link().sortByStatus().click();";
        String notifCrashPageWeather = "notifcrash._link().sortByWeather().click();";
        
        String notifDriverLoginsPageDateTime = "notifdriverlogins._link().sortByDateTime().click();";
        String notifDriverLoginsPageGroup = "notifdriverlogins._link().sortByGroup().click();";
        String notifDriverLoginsPageDriver = "notifdriverlogins._link().sortByDriver().click();";
        String notifDriverLoginsPageVehicle = "notifdriverlogins._link().sortByVehicle().click();";
        
        if (columnLink.equals("Sort By Date Time")) {
            descending();
            notifdiag._link().sortByDateTime().click();
            System.out.println("And I click the sort link");
            ascending();
        }
    }
    
    public void ascending() {
        int y = 1;
        int g = 1;
        int h = 2;
        
        while (y < 15) {
            
            String entry1 = notifdiag._text().entryDateTime().row(g).getText(); // get the first entry in the table
            String entry2 = notifdiag._text().entryDateTime().row(h).getText(); // get the last entry in the table
            // System.out.print("Entry 1: " + entry1 + "\nEntry 2: " + entry2 + "\n");
            
            int result = entry1.compareTo(entry2); // compare the two entries
            
            if (result == 0) {
                System.out.println("The names are equal.");
            } else if (result > 0) {
                System.out.println("Entry 2 comes before Entry 1 alphabetically.  This is incorrect.  FAILED");
                System.exit(0);// I need a more graceful error method
            } else if (result < 0) {
                System.out.println("Entry 1 comes before Entry 2 alphabetically.");
            }
            g++;
            h++;
            y++;
        }
        
    }
    
    public void descending() {
        
        int z = 1;
        int i = 1;
        int j = 2;
        
        while (z < 15) {
            
            String entry3 = notifdiag._text().entryDateTime().row(i).getText(); // get the first entry in the table
            String entry4 = notifdiag._text().entryDateTime().row(j).getText(); // get the last entry in the table
            // System.out.print("Entry 1: " + entry1 + "\nEntry 2: " + entry2 + "\n");
            
            int result = entry3.compareTo(entry4); // compare the two entries
            
            if (result == 0) {
                System.out.println("The names are equal.");
            } else if (result > 0) {
                System.out.println("Entry 2 comes before Entry 1 alphabetically.");
            } else if (result < 0) {
                System.out.println("Entry 1 comes before Entry 2 alphabetically.  This is incorrect.  FAILED");
                System.exit(0);// I need a more graceful error method
            }
            i++;
            j++;
            z++;
        }
    }
    
    @When("I copy the last form")
    public void iCopyTheLastForm() {
        iActOnTheLastForm("copy");
    }
    
    @When("I edit the last form")
    public void iEditTheLastForm() {
        iActOnTheLastForm("edit");
    }
    
    public void iActOnTheLastForm(String sType) {
        String[] sEntries;
        String sEntry;
        int iRow = 0, iNext = 0, iTop = 0, iLast = 0, iDiff = 0, j = -1;
        
        try {
            sEntries = manage._text().entries().getText().split(" ");
            while (sEntries[1].equalsIgnoreCase("0")) {
                sEntries = manage._text().entries().getText().split(" ");
            }
        } catch (Exception t) {}
        
        try {
            sEntries = manage._text().entries().getText().split(" ");
            for (String entry : sEntries) {
                j++;
                if (entry.equalsIgnoreCase("entries")) {
                    iRow = j - 1;
                }
            }
            
            sEntry = sEntries[iRow];
            if (sEntry.matches("^[0-9]+$")) {
                iRow = Integer.parseInt(sEntry);
            }
            
            if (iRow > 100) {
                iNext = Math.round(iRow / 100);
                manage._dropDown().recordsPerPage().selectRow(4);
                for (int z = 0; z < iNext; z++) {
                    manage._link().next().click();
                    iRow -= iNext;
                }
            } else {
                manage._dropDown().recordsPerPage().selectRow(4);
            }
            
            sEntries = manage._text().entries().getText().split(" ");
            iTop = Integer.parseInt(sEntries[1]);
            iLast = Integer.parseInt(sEntries[3]);
            iDiff = iLast - iTop + 1;
            
            manage._button().gear().row(iDiff).click();
            if (sType.equalsIgnoreCase("copy")) {
                manage._link().copy().row(iDiff).click();
            } else {
                manage._link().edit().row(iDiff).click();
            }
        } catch (Exception p) {
            System.out.println(p.getStackTrace());
        }
    }
}