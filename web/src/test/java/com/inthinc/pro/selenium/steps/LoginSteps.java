package com.inthinc.pro.selenium.steps;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import com.inthinc.pro.automation.logging.Log;
import com.inthinc.pro.automation.models.AutomationUser;
import com.inthinc.pro.automation.utils.RandomValues;
import com.inthinc.pro.selenium.pageObjects.PageAdminAddEditUser;
import com.inthinc.pro.selenium.pageObjects.PageAdminAddEditVehicle;
import com.inthinc.pro.selenium.pageObjects.PageAdminDevices;
import com.inthinc.pro.selenium.pageObjects.PageAdminEditDevice;
import com.inthinc.pro.selenium.pageObjects.PageAdminVehicles;
import com.inthinc.pro.selenium.pageObjects.PageFormsSubmissions;
import com.inthinc.pro.selenium.pageObjects.PageLogin;
import com.inthinc.pro.selenium.pageObjects.PageNotificationsDiagnostics;
import com.inthinc.pro.selenium.pageObjects.PopUps;

public class LoginSteps extends WebSteps {
	
    String firstNames[] = { 
    		"Mike",
    		"Jason",
    		"Colleen",
    		"Jacquie",
    		"Todd",
    		"Matt",
    		"Joe",
    		"Dan",
    		"Bob",
    		"Richard"
    };
    
    String lastNames[] = { 
    		"Smith",
    		"Jones",
    		"Roberts",
    		"Hansen",
    		"Jensen",
    		"Walton",
    		"Western",
    		"Smiles",
    		"Starr",
    		"Lennon"
    };
	
    String makes[] = { 
    		"Ford",
    		"Volkswagen",
    		"Chevrolet",
    		"Hyundai",
    		"Honda",
    		"Toyota",
    		"Dodge",
    		"GMC",
    		"Mazda",
    		"Nissan"
    };
    String models[][] = {
    		{"F-150", "F-250", "Taurus", "F-350", "Mustang", "Focus", "Explorer", "GT", "Mondeo"},
    		{"Jetta", "Rabbit", "Beetle", "Golf", "Sirocco", "GTI", "Passat", "Tiguan", "Touareg"},
    		{"Blazer", "Camaro", "Corvette", "Cruze", "Equinox", "Impala", "Malibu", "Sonic", "Spark"},
    		{"Accent", "Elantra", "Equus", "Genesis", "Santa Fe", "Sonata", "Tucson", "Veloster", "Azera"},
    		{"Accord", "Civic", "CR-V", "Fit", "Odyssey", "Pilot", "CR-Z", "Crosstour", "Insight"},
    		{"Tundra", "Rav-4", "Corolla", "Matrix", "Land Cruiser", "Sequoia", "Sienna", "Venza", "4Runner"},
    		{"Challenger", "Dart", "Stratus", "Avenger", "Charger", "Intrepid", "Neon", "Viper", "Durango"},
    		{"Acadia", "Savana", "Terrain", "Yukon", "Sierra 1500", "Sierra 2500", "Sierra 3500", "Canyon", "Denali"},
    		{"MX-6", "Mazda2", "Mazda3", "Mazdaspeed3", "Mazda5", "Mazda6", "Mazdaspeed6", "Protege", "Millenia"},
    		{"Frontier", "Skyline", "Titan", "Altima", "Maxima", "350Z", "370Z", "Armada", "GT-R"},
    };
	
    PageLogin loginPage = new PageLogin();
    AutomationUser login;
    PageAdminAddEditUser useraddedit = new PageAdminAddEditUser();
    PageAdminVehicles vehPage = new PageAdminVehicles();
    PageAdminAddEditVehicle vehaddedit = new PageAdminAddEditVehicle();
    PageAdminDevices devicesPage = new PageAdminDevices();
    PageAdminEditDevice deviceEditPage = new PageAdminEditDevice();
    PageNotificationsDiagnostics notifdiag = new PageNotificationsDiagnostics();
    PageFormsSubmissions submissions = new PageFormsSubmissions();

    private static final PageLogin page = new PageLogin();
    private static final PopUps popup = new PopUps();
    private RandomValues random;

    // @When("I type an user name in the wrong case")
    // public void whenITypeAnUserNameInTheWrongCase() {
    // page._textField().userName().type(MasterTest.switchCase(autouser.getUsername()));
    //
    // }

    // @When("I type a password in the wrong case")
    // public void whenITypeAPasswordInTheWrongCase() {
    // page._textField().password().type(MasterTest.switchCase(autouser.getPassword()));
    // }

    @Given("I clean the database")
    public void cleanup() {
    	//TODO: Add code to clear out the forms database before running all tests
    }
    
    @Given("I log in to the util")
    public void givenILogInToTheUtil() {
    	loginPage.open("https://dev.tiwipro.com:8413/tiwiproutil");
    	loginPage._textField().username().type("mweiss");
    	loginPage._textField().password().type("password");
    	loginPage._button().logIn().click();
    }
    
    @When("I create one thousand users")
    public void whenICreateOneThousandAccounts() {
    	int i = 0;
    	while (i < 1001) {
    	random = new RandomValues();
		String emp_firstname = random.getCharString(20);
		String emp_lastname = random.getCharString(20);
		String emp_email = random.getEmail();
		
    	useraddedit._link().adminAddUser().click();
    	useraddedit._textField().firstName().type(emp_firstname);
    	useraddedit._textField().lastName().type(emp_lastname);
    	useraddedit._dropDown().driverTeam().select("Top - Stress Team One");
    	useraddedit._dropDown().timeZone().select("US/Mountain (GMT-7:00)");
    	useraddedit._checkBox().loginInformation().uncheck();
    	useraddedit._textField().emailOne().type(emp_email);
    	useraddedit._button().saveTop().click();
    	i++;
    	}
    }
    
    @When("I create one thousand vehicles")
    public void whenICreateOneThousandVehicles() {
    	int i = 0;
    	while (i < 1001) {
    	random = new RandomValues();
		String veh_vin = random.getCharString(17);
		String veh_id = random.getCharString(20);
		int getrandom = random.getInt(9);
        String make = makes[getrandom];
        String model = models[getrandom][random.getInt(8)];
        
		vehaddedit._link().adminVehicles().click();
		vehaddedit._link().adminAddVehicle().click();
		vehaddedit._textField().VIN().type(veh_vin);
		vehaddedit._textField().make().type(make);
		vehaddedit._textField().model().type(model);
		vehaddedit._textField().vehicleID().type(veh_id);
		vehaddedit._dropDown().team().select("Top - Stress Team One");
		vehaddedit._button().saveTop().click();
    	i++;
    	}
    }
    
    @When("I assign devices to vehicles")
    public void whenIAssignDevicesToVehicles() {
    	int i = 1;
    	
    	
    	while (i < 1001) {
    		devicesPage._link().adminDevices().click();
    		devicesPage._link().vehicleID().click();
    		devicesPage._link().entryEdit().getFirstClickableLink().click();
    		deviceEditPage._link().showHideVehiclesForAssignment().click();
    		deviceEditPage._link().assigned().click();
    		deviceEditPage._link().assigned().click();
    		deviceEditPage._button().vehicleTableEntrySelector().click();
    		deviceEditPage._button().saveTop().click();
    	i++;
    	}
    }
    
    @When("I assign drivers to vehicles")
    public void whenIAssignDriversToVehicles() {
    	int i = 1;
    	random = new RandomValues();
    	int getrandom = random.getInt(9);
        String make = makes[getrandom];
        String model = models[getrandom][random.getInt(8)];
        	
    	while (i < 1001) {
    		vehaddedit._link().adminVehicles().click();
    		vehPage._textField().searchVehicle().type("Unknown Driver");
    		vehPage._button().searchVehicle().click();
    		vehPage._link().sortByDevice().click();
    		vehPage._link().sortByDevice().click();
    		vehPage._link().entryEdit().click();
    		vehaddedit._link().assignDriver().click();
    		vehaddedit._popUp().assign()._links().sortAssigned().click();
    		vehaddedit._popUp().assign()._links().assign().getFirstClickableLink().click();
    		vehaddedit._textField().eCallPhone().type("1111111111");
    		vehaddedit._textField().make().type(make);
    		vehaddedit._textField().model().type(model);
    		vehaddedit._button().saveTop().click();
    	i++;
    	}
    }   

//    @When("I enter non valid email text into the email address field")
//    public void whenIEnterNonValidEmailTextIntoTheEmailAddressField() {
//        page._popUp().forgotPassword()._textField().email().type("z"); // doesn't conform to rule * x to y characters ??
//        page._popUp().forgotPassword()._button().send().click();
//        page._popUp().forgotPassword()._textField().email().type("test@@test.com"); /*
//                                                                                     * doesn't conform to rule * Only one commercial at (@) is allowed to separate the user name
//                                                                                     * from the domain name User Name
//                                                                                     */
//        page._popUp().forgotPassword()._button().send().click();
//        page._popUp().forgotPassword()._textField().email().type("test@test@test.com"); /*
//                                                                                         * doesn't conform to rule * Only one commercial at (@) is allowed to separate the user name
//                                                                                         * from the domain name User Name
//                                                                                         */
//        page._popUp().forgotPassword()._button().send().click();
//        page._popUp().forgotPassword()._textField().email().type("!@#...com"); /*
//                                                                                * doesn't conform to rule * A - Z, a - z, 0 - 9, underscore (_), hyphen (-), and period (.)
//                                                                                * characters are allowed (Note: A period at the beginning, a period at the end, and 2 consecutive
//                                                                                * periods are allowed.)
//                                                                                */
//        page._popUp().forgotPassword()._button().send().click();
//        page._popUp().forgotPassword()._textField().email().type("test test@email.com"); /*
//                                                                                          * doesn't conform to rule * All other characters, inclusive of a blank space and a quoted
//                                                                                          * string (i.e. between double quotes), are NOT allowed Domain Name
//                                                                                          */
//        page._popUp().forgotPassword()._button().send().click();
//        page._popUp().forgotPassword()._textField().email().type("\"test\"@email.com"); /*
//                                                                                         * doesn't conform to rule * All other characters, inclusive of a blank space and a quoted
//                                                                                         * string (i.e. between double quotes), are NOT allowed Domain Name
//                                                                                         */
//    }

    //
    // @Given("I am logged in as a user in a role that does have the $accessPointName accesspoint")
    // public void loginAsUserWithAccesspoint(String accesspointName){
    // //LoginCapabilities.NoAccessPointUserInfo;
    // LoginCapability hasThisCapability = null;
    // if(accesspointName.equals("UserInfo"))
    // hasThisCapability = LoginCapability.AccessPointUserInfo;
    //
    // AutomationUser user = AutomationUsers.getUsers().getOneBy(hasThisCapability);
    //
    // PageLogin login = new PageLogin();
    // login.loginProcess(user);
    // }

    @Given("I am logged in as TEST_39880 user")
    public void loggedInAsTeamOnlyUser() {
        page._textField().username().type("TEST_39880");
        page._textField().password().type("password");
        page._button().logIn().click();
    }

     @Given("I am logged in as a \"$roleName\" user")
     @When("I am logged in as a \"$roleName\" user")
     public void loginAsAUserofRole(String roleName) {
//     LoginCapability hasThisCapability = null;
      if(roleName.equals("TestUser"))
      {
      page._textField().username().type("TEST_39880");
      page._textField().password().type("password");
      page._button().logIn().click();
      }
      if(roleName.equals("TeamOnly"))
      {
      page._textField().username().type("CaptainNemo");
      page._textField().password().type("Muttley");
      page._button().logIn().click();
      }
    // if(roleName.equals("Admin"))
    // hasThisCapability = LoginCapability.RoleAdmin;
    // else if(roleName.equals("HOS"))
    // hasThisCapability = LoginCapability.RoleHOS;
    // else if(roleName.equals("Live Fleet"))
    // hasThisCapability = LoginCapability.RoleLiveFleet;
    // else
    // test.addError("there is no defined role of "+roleName, ErrorLevel.ERROR);
    //
    // AutomationUser user = AutomationUsers.getUsers().getOneBy(hasThisCapability);
    //
    // PageLogin login = new PageLogin();
    // login.loginProcess(user);
     }

    // @Given("I am logged in as a user in a role that does not have the $accesspointName accesspoint")
    // public void loginAsUserWithoutAccesspoint(String accesspointName){
    // Log.error("I am logged in as a user in a role that does not have "+ accesspointName+" accesspoint");
    // LoginCapability hasThisCapability = null;
    // if(accesspointName.equals("UserInfo"))
    // hasThisCapability = LoginCapability.NoAccessPointUserInfo;//TODO: jwimmer: I do not like this manual mapping???
    //
    // AutomationUser user = AutomationUsers.getUsers().getOneBy(hasThisCapability);
    //
    // PageLogin login = new PageLogin();
    // login.loginProcess(user);
    // }

    // admin users
    // @Given("I am on the \"$pageDescription\" page")
    // public void givenIAmOnThePage(String pageDescription){
    // MasterTest.print("public void givenIAmOnThePage(String "+pageDescription+")", Level.DEBUG);
    // boolean matched = false;
    // PageTeamDashboardStatistics dummyPage = new PageTeamDashboardStatistics();
    // if(pageDescription.equalsIgnoreCase("admin users")) {
    // matched = true;
    // PageAdminUsers adminUsersPage = new PageAdminUsers();
    // adminUsersPage.load();
    // }
    // if(pageDescription.startsWith("Admin -")){
    // matched = true;
    // if(pageDescription.endsWith("- Users"))
    // new PageAdminCustomRoleAddEdit().load();
    // else if(pageDescription.endsWith("- Add User"))
    // new PageAdminUserAddEdit();
    // else if(pageDescription.endsWith("- Vehicles"))
    // new PageAdminVehicles();
    // else if(pageDescription.endsWith("- Add Vehicle"))
    // new PageAdminVehicleEdit().load();
    // else if(pageDescription.endsWith("- Devices"))
    // new PageAdminDevices().load();
    // else if(pageDescription.endsWith("- Zones"))
    // new PageAdminZones().load();
    // else if(pageDescription.endsWith("- Red Flags"))
    // new PageAdminRedFlags().load();
    // else if(pageDescription.endsWith("- Add Red Flag"))
    // new PageAdminRedFlagAddEdit().load();
    // else if(pageDescription.endsWith("- Reports"))
    // new PageAdminReports().load();
    // else if(pageDescription.endsWith("- Add Report")){
    // matched = false;
    // test.addError("Admin - Add Report does not have a PageObject ???");
    // }
    // else if(pageDescription.endsWith("- Organization"))
    // new PageAdminOrganization().load();
    // else if(pageDescription.endsWith("- Custom Roles"))
    // new PageAdminCustomRoles().load();
    // else if(pageDescription.endsWith("- Add Custom Role"))
    // new PageAdminCustomRoleAddEdit().load();
    // else if(pageDescription.endsWith("- Speed By Street")){
    // matched = false;
    // test.addError("Admin - Speed by Street does not have a PageObject ???");
    // }
    // else if(pageDescription.endsWith("- Account Details"))
    // new PageAdminAccountDetails().load();
    // else
    // matched = false;
    // }
    //
    // if(!matched)
    // test.addError("Automation Framework does not know about the '"+pageDescription+"' page");
    //
    // PageAdminUsers adminUsers = new PageAdminUsers();
    // adminUsers.load();
    // }

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
    
    //TODO: This can be deleted once forms is integrated into the main site
    //will need to go through all the story files and change this line to
    //And I click the Forms link
    @When("I go to the forms admin page")
    public void whenIGoToTheFormsAdminPage() {
    	//loginPage.open("https://schlumberger.inthinc.com:8443/forms/");  // for slb
    	loginPage.open("http://dev.tiwipro.com:8080/forms/");  //for dev
    }
    
    @When("I go to the forms submissions page")
    public void whenIGoToTheFormsSubmissionsPage() {    	
    	//loginPage.open("https://schlumberger.inthinc.com:8443/forms/submissions");  // for slb
    	loginPage.open("http://dev.tiwipro.com:8080/forms/submissions");  //for dev
    }
    
    //TODO: MWEISS - I am still working on how this will work
    @Then("the Sort By Date Time column sorts correctly")
    public void thenIValidateTheSortByDateTimeColumnSortsCorrectly() {
        
        notifdiag._link().sortByDateTime().click();
        
        String text1 = notifdiag._text().entryDateTime().row(1).getText();
        String text2 = notifdiag._text().entryDateTime().row(15).getText();
        
        int result = text1.compareTo(text2);
        
        if (result == 0) {
            System.out.println("The names are equal.");
       }
       else if (result > 0) {
            System.out.println(
                "name2 comes before name1 alphabetically.");
       }
       else if (result < 0) {
            System.out.println(
               "name1 comes before name2 alphabetically.");
       }
    }
}