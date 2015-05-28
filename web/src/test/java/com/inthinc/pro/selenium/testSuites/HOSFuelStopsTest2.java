package com.inthinc.pro.selenium.testSuites;

import org.jbehave.core.annotations.UsingSteps;
import org.junit.Test;

import com.inthinc.pro.automation.annotations.AutomationAnnotations.PageObjects;
import com.inthinc.pro.automation.annotations.AutomationAnnotations.StoryPath;
import com.inthinc.pro.selenium.pageObjects.PageAdminUsers;
import com.inthinc.pro.selenium.pageObjects.PageExecutiveDashboard;
import com.inthinc.pro.selenium.pageObjects.PageExecutiveOverallExpansion;
import com.inthinc.pro.selenium.pageObjects.PageHOSAddEditDriverLogs;
import com.inthinc.pro.selenium.pageObjects.PageHOSAddEditFuelStops;
import com.inthinc.pro.selenium.pageObjects.PageHOSDriverLogs;
import com.inthinc.pro.selenium.pageObjects.PageHOSFuelStops;
import com.inthinc.pro.selenium.pageObjects.PageHOSReports;
import com.inthinc.pro.selenium.pageObjects.PageLogin;
import com.inthinc.pro.selenium.pageObjects.PageMyAccount;
import com.inthinc.pro.selenium.pageObjects.PageReportsDrivers;
import com.inthinc.pro.selenium.pageObjects.PageTeamDriverStatistics;
import com.inthinc.pro.selenium.steps.LoginSteps;


@UsingSteps(instances={LoginSteps.class})
@PageObjects(list={PageLogin.class, PageExecutiveDashboard.class, PageAdminUsers.class, PageHOSDriverLogs.class, PageHOSFuelStops.class, 
        PageHOSAddEditFuelStops.class, PageMyAccount.class, PageHOSAddEditDriverLogs.class, PageHOSReports.class, PageMyAccount.class,
        PageExecutiveOverallExpansion.class, PageReportsDrivers.class, PageTeamDriverStatistics.class})
@StoryPath(path="HOSFuelStops2.story")
public class HOSFuelStopsTest2 extends WebStories {
    
    @Test
    public void test(){}

}

//@Ignore
//public class HOSFuelStopsTest extends WebRallyTest {
    
    
    
    
    //TODO: on TC5703, steps 10 thru 15 are commented out, and not in Rally, need to see if they were moved to another test
    
    
//    private PageMyAccount myAccount;
//    private PageAdminUsers usersPage;
//    private PageAdminUserDetails userDetails;
//    
//    private PageHOSFuelStops myFuelStops; 
//    private PageHOSAddEditFuelStops myFuelStopsAddEdit;
//    private AutomationUser login;
//    
//    
//    @Before
//    public void setupPage() {
//        myAccount = new PageMyAccount();
//        myFuelStops = new PageHOSFuelStops();
//        myFuelStopsAddEdit = new PageHOSAddEditFuelStops();
//        login = AutomationUsers.getUsers().getOneBy(AccountCapability.HOSEnabled, LoginCapability.IsDriver, LoginCapability.HasWaySmart, LoginCapability.HasVehicle, LoginCapability.RoleHOS);
//        if (login == null){
//            throw new NullPointerException(login.toString());
//        }
//    }
//
//    @Test
////    @Ignore //TODO: waiting on errorVehicleFuel().validate() pageObject fix
//    public void FuelStopErrors() {
//        
//        set_test_case("TC5627");
//
//        // 0.Login
//        myFuelStops.loginProcess(login);
//        
//        //0.1.Determine driver's full name to use in driver dropdown
//        myFuelStops._link().admin().click();
//        usersPage = new PageAdminUsers();
//        usersPage.search(login.getUsername());
//        usersPage.clickFullNameMatching(UserColumns.USER_NAME, login.getUsername());
//        
//        userDetails = new PageAdminUserDetails();
//        String driverFullName = userDetails.getFullName();
//
//        myFuelStops._link().hos().click();
//        myFuelStops._link().hosFuelStops().click();
//        
//        //1.Selected vehicle id and click on Add        
//        myFuelStops._textField().vehicle().type(login.getUsername().substring(0, login.getUsername().length()-1));
//        myFuelStops._textField().vehicle().getSuggestion(login.getUsername()).click();
//        myFuelStops._button().add().click();
//               
//        //2.Generate Driver and Vehicle Fuel Errors
//        myFuelStopsAddEdit._dropDown().driver().select(1);
//        myFuelStopsAddEdit._button().bottomSave().click();
//        myFuelStopsAddEdit._text().errorMaster().validate("2 error(s) occurred. Please verify all the data entered is correct.");
//        myFuelStopsAddEdit._text().errorVehicleFuel().validate("Vehicle fuel is required.");
//        myFuelStopsAddEdit._text().errorDriver().validate("Driver is required");
//        myFuelStopsAddEdit._button().bottomCancel().click();
//        
//        myFuelStops._button().add().click();
//        //3. Generate Trailer Fuel Errors
//        myFuelStopsAddEdit._textField().trailer().type("123");
//        myFuelStopsAddEdit._dropDown().driver().select(driverFullName);
//        myFuelStopsAddEdit._button().bottomSave().click();
//        
//        myFuelStopsAddEdit._text().errorMaster().validate("1 error(s) occurred. Please verify all the data entered is correct.");
//        myFuelStopsAddEdit._text().errorBothVehicleAndTrailerFuel().validate("Vehicle or Trailer fuel required.");  
//        myFuelStopsAddEdit._button().bottomCancel().click();
//        
//        //4. Generate Date/Time in Future error
//      
//        myFuelStops._button().add().click();
//        
//        AutomationCalendar calendar = new AutomationCalendar(WebDateFormat.DATE_RANGE_FIELDS);
//        calendar.addToDay(1);
//        
//        myFuelStopsAddEdit._dateSelector().date().click(calendar);
//        myFuelStopsAddEdit._textField().vehicleFuel().type("123");
//        myFuelStopsAddEdit._dropDown().driver().select(driverFullName);
//        myFuelStopsAddEdit._button().bottomSave().click();
//        myFuelStopsAddEdit._text().errorMaster().validate("1 error(s) occurred. Please verify all the data entered is correct.");
//        myFuelStopsAddEdit._text().errorDate().validate("Date/Time in the future is not valid.");
//        myFuelStopsAddEdit._button().bottomCancel().click();
//         
//   
//    }
//
//    @Test
//    public void CancelAddFuelStop() {
//        set_test_case("TC5628");
//        myFuelStops.loginProcess(login);
//       
//        //0.1.Determine driver's full name to use in driver dropdown
//        myFuelStops._link().admin().click();
//        usersPage = new PageAdminUsers();
//        usersPage.search(login.getUsername());
//        usersPage.clickFullNameMatching(UserColumns.USER_NAME, login.getUsername());
//        
//        userDetails = new PageAdminUserDetails();
//        String driverFullName = userDetails.getFullName();
//        
//        myFuelStops._link().hos().click();
//        myFuelStops._link().hosFuelStops().click();
//      //1.Selected vehicle id and click on Add        
//        myFuelStops._textField().vehicle().type(login.getUsername().substring(0, login.getUsername().length()-1));
//        myFuelStops._textField().vehicle().getSuggestion(login.getUsername()).click();
//        myFuelStops._button().add().click();
//        
//        myFuelStopsAddEdit._text().valueVehicle().validatePresence(true);
//        // myFuelStopsAddEdit._textField().date();
//        myFuelStopsAddEdit._text().timeMessage().validatePresence(true);
//        myFuelStopsAddEdit._textField().trailer().type("789");
//        myFuelStopsAddEdit._textField().vehicleFuel().type("789");
//        myFuelStopsAddEdit._textField().trailerFuel().type("789");
//        myFuelStopsAddEdit._dropDown().driver().select(driverFullName);
//        myFuelStopsAddEdit._text().valueLocation().validatePresence(true);
//        myFuelStopsAddEdit._button().topCancel().click();
//        //TODO: verify that a new fuel entry was not added.
//    }
//
//    @Test
//    public void AddFuelStop() {
//        set_test_case("TC5631");
//        myFuelStops.loginProcess(login);
//        
//        //0.1.Determine driver's full name to use in driver dropdown
//        myFuelStops._link().admin().click();
//        usersPage = new PageAdminUsers();
//        usersPage.search(login.getUsername());
//        usersPage.clickFullNameMatching(UserColumns.USER_NAME, login.getUsername());
//        
//        userDetails = new PageAdminUserDetails();
//        String driverFullName = userDetails.getFullName();
//        
//        myFuelStops._link().hos().click();
//        myFuelStops._link().hosFuelStops().click();
//        //Selected vehicle id and click on Add        
//        myFuelStops._textField().vehicle().type(login.getUsername().substring(0, login.getUsername().length()-1));
//        myFuelStops._textField().vehicle().getSuggestion(login.getUsername()).click();
//        myFuelStops._button().add().click();
//        
//
//        // myFuelStopsAddEdit._textField().date().
//        myFuelStopsAddEdit._textField().trailer().type("123");
//        myFuelStopsAddEdit._textField().vehicleFuel().type("123");
//        myFuelStopsAddEdit._textField().trailerFuel().type("123");
//        myFuelStopsAddEdit._dropDown().driver().select(driverFullName); 
//        myFuelStopsAddEdit._button().bottomSave().click();
//        myFuelStops._text().valueVehicleFuel().row(1).validate("123.00 Gallons");
//        myFuelStops._text().valueTrailerFuel().row(1).validate("123.00 Gallons");
//        myFuelStops._text().valueTrailer().row(1).validate("123");
//    }
//
//    @Test
//    public void CancelEditFuelStop() {
//        set_test_case("TC5632");
//        myFuelStops.loginProcess(login);
//        myFuelStops._link().hos().click();
//        myFuelStops._link().hosFuelStops().click();
//        
//        //Selected vehicle id and click on Add        
//        myFuelStops._textField().vehicle().type(login.getUsername().substring(0, login.getUsername().length()-1));
//        myFuelStops._textField().vehicle().getSuggestion(login.getUsername()).click();
//        
//        //Add Fuel Stop
//        myFuelStops._button().add().click();
//        myFuelStopsAddEdit._textField().trailer().type("123");//TODO: failing on this line edit-form:editfuelStop_trailer not found
//        myFuelStopsAddEdit._textField().vehicleFuel().type("123");
//        myFuelStopsAddEdit._textField().trailerFuel().type("123");
//        myFuelStopsAddEdit._dropDown().driver().selectRandom();
//        System.out.println("selected text "+myFuelStopsAddEdit._dropDown().driver().getText());
//        myFuelStopsAddEdit._button().bottomSave().click();
//        pause(5,"wait to save new fuel stop");
//        
//        //Edit row one
//        myFuelStops._link().valueEdit().row(1).click();
//        myFuelStopsAddEdit._textField().trailer().clear();
//        myFuelStopsAddEdit._textField().trailer().type("456");
//        myFuelStopsAddEdit._textField().vehicleFuel().clear();
//        myFuelStopsAddEdit._textField().vehicleFuel().type("456");
//        myFuelStopsAddEdit._textField().trailerFuel().type("456");
//        myFuelStopsAddEdit._button().bottomCancel().click();
//        
//        //Verify Edits were not saved.
//        myFuelStops._text().valueVehicleFuel().row(1).validate("123.00 Gallons");
//        myFuelStops._text().valueTrailerFuel().row(1).validate("123.00 Gallons");
//        myFuelStops._text().valueTrailer().row(1).validate("123");
//    }
//
//    @Test
//    public void EditFuelStop() {
//        set_test_case("TC5630");
//        myFuelStops.loginProcess(login);
//        myAccount._link().myAccount().click();
//        myAccount._button().edit().click();
//        myAccount._select().measurement().select("English");
//        myAccount._button().save().click();
//        
//        myFuelStops._link().hos().click();
//        myFuelStops._link().hosFuelStops().click();
//      //Selected vehicle id and click on Add        
//        myFuelStops._textField().vehicle().type(login.getUsername().substring(0, login.getUsername().length()-1));
//        myFuelStops._textField().vehicle().getSuggestion(login.getUsername()).click();
//        myFuelStops._button().refresh().click();
//        
//        myFuelStops._link().valueEdit().row(1).click();
//        myFuelStopsAddEdit._textField().trailer().type("456");
//        pause(2,"");
//        myFuelStopsAddEdit._textField().vehicleFuel().type("456");
//        myFuelStopsAddEdit._textField().trailerFuel().type("456");
//        myFuelStopsAddEdit._button().bottomSave().click();
//        
//        //Verify Edits were saved.
//        myFuelStops._text().valueVehicleFuel().row(1).validate("456.00 Gallons");
//        myFuelStops._text().valueTrailerFuel().row(1).validate("456.00 Gallons");
//        myFuelStops._text().valueTrailer().row(1).validate("456");
//    }
//
//    @Test
//    public void CancelDeleteFuelStop() {
//        set_test_case("TC5633");
//        myFuelStops.loginProcess(login);
//        myFuelStops._link().hos().click();
//        myFuelStops._link().hosFuelStops().click();
//        //Selected vehicle id and click on Add        
//        myFuelStops._textField().vehicle().type(login.getUsername().substring(0, login.getUsername().length()-1));
//        myFuelStops._textField().vehicle().getSuggestion(login.getUsername()).click();
//
//        myFuelStops._checkBox().entryCheckBox().row(1).check();
//        myFuelStops._button().delete().click();
//        myFuelStops._popUp().delete()._button().cancel().click();
//        
//        //Verify data was not deleted
//        myFuelStops._text().valueVehicleFuel().row(1).validate("456.00 Gallons");
//        myFuelStops._text().valueTrailerFuel().row(1).validate("456.00 Gallons");
//        myFuelStops._text().valueTrailer().row(1).validate("456");
//    }
//
//    @Test
//    public void DeleteFuelStop() {
//        set_test_case("TC5629");
//        myFuelStops.loginProcess(login);
//        myFuelStops._link().hos().click();
//        myFuelStops._link().hosFuelStops().click();
//        //Selected vehicle id and click on Add        
//        myFuelStops._textField().vehicle().type(login.getUsername().substring(0, login.getUsername().length()-1));
//        myFuelStops._textField().vehicle().getSuggestion(login.getUsername()).click();
//
//        myFuelStops._checkBox().entryCheckBox().row(1).check();
//        myFuelStops._button().delete().click();
//        myFuelStops._popUp().delete()._button().delete().click();
//        //TODO: verify deletion of fuel stop entry.
//    }
//
//    @Test
//    public void EditColumns() {
//        set_test_case("TC5702");
//        //0. Login
//        myFuelStops.loginProcess(login);
//        myFuelStops._link().hos().click();
//        myFuelStops._link().hosFuelStops().click();
//        
//        //1. Edit - uncheck columns and save
//        myFuelStops._button().editColumns().click();
//        myFuelStops._popUp().editColumns()._checkBox().row(3).uncheck();
//        myFuelStops._popUp().editColumns()._checkBox().row(7).uncheck();
//        myFuelStops._popUp().editColumns()._button().save().click();
//        
//        //2. Verify columns do not display after saving.
//        myFuelStops._link().sortVehicle().validatePresence(false);
//        myFuelStops._link().sortVehicleFuel().validatePresence(false);
//        
//        //3. Edit columns and select the previously unchecked columns.
//        myFuelStops._button().editColumns().click();
//        myFuelStops._popUp().editColumns()._checkBox().row(3).check();
//        myFuelStops._popUp().editColumns()._checkBox().row(7).check();
//        myFuelStops._popUp().editColumns()._button().save().click();
//   
//        //4. Verify columns display after saving.
//        myFuelStops._link().sortVehicle().validatePresence(true);
//        myFuelStops._link().sortVehicleFuel().validatePresence(true);
//
//    }
//    @Test
//    public void VehicleField() {
//        set_test_case("TC5700");
//        //0. Login
//        myFuelStops.loginProcess(login);
//        myFuelStops._link().hos().click();
//        myFuelStops._link().hosFuelStops().click();
//        
//        //1. Vehicle field remains blank when only clicking on refresh
//        myFuelStops._textField().vehicle().clear();
//        myFuelStops._button().refresh().click();
//        myFuelStops._textField().vehicle().validate("");
//       
//        //2. Enter a valid TIWI ID - should not offer suggestions
//        AutomationUser tiwiLogin = AutomationUsers.getUsers().getOneBy(login.getAccount(), LoginCapability.HasTiwiPro);
//        myFuelStops._textField().vehicle().type(tiwiLogin.getUsername());
//        myFuelStops._textField().vehicle().getSuggestion(1).validate("No vehicles found");
//        
//        //3. Enter a valid MCM ID - select from suggestions
//        myFuelStops._textField().vehicle().clear();
//        myFuelStops._textField().vehicle().type(login.getUsername().substring(0, login.getUsername().length()-1));
//        myFuelStops._textField().vehicle().getSuggestion(login.getUsername()).click();
//        myFuelStops._textField().vehicle().validatePresence(true);
//                
//    }
//    
//    @Test
//    public void FuelFields() {
//        set_test_case("TC5703") ;
//        //0. Login
//        myFuelStops.loginProcess(login);
//        //0.1.Determine driver's full name to use in driver dropdown
//        myFuelStops._link().admin().click();
//        usersPage = new PageAdminUsers();
//        usersPage.search(login.getUsername());
//        usersPage.clickFullNameMatching(UserColumns.USER_NAME, login.getUsername());
//        
//        userDetails = new PageAdminUserDetails();
//        String driverFullName = userDetails.getFullName();
//        
//        myAccount._link().myAccount().click();
//        myAccount._button().edit().click();
//        myAccount._select().measurement().select("English");
//        myAccount._button().save().click();
//        
//        myFuelStops._link().hos().click();
//        myFuelStops._link().hosFuelStops().click();
//       
//    
//        //1. Get vehicle and click on Add
//        //Selected vehicle id and click on Add        
//        myFuelStops._textField().vehicle().type(login.getUsername().substring(0, login.getUsername().length()-1));
//        myFuelStops._textField().vehicle().getSuggestion(login.getUsername()).click();
//        
//        pause(5,"");
//        myFuelStops._button().add().click();
//        
//        //2. Enter Alpha char and save.
//        myFuelStopsAddEdit._textField().trailer().type("123ABC");
//        pause(5,"");
//        myFuelStopsAddEdit._textField().vehicleFuel().type("abcdefg");
//        myFuelStopsAddEdit._textField().trailerFuel().type("abcdefg");
//        myFuelStopsAddEdit._dropDown().driver().select(driverFullName);
//        pause(5,"");
//        myFuelStopsAddEdit._button().topSave().click();
//        
//        //3. validate error message.
//        myFuelStopsAddEdit._text().errorMaster().validate("3 error(s) occurred. Please verify all the data entered is correct.");
//        myFuelStopsAddEdit._text().errorBothVehicleAndTrailerFuel().validate("Vehicle or Trailer fuel required.");
//        myFuelStopsAddEdit._text().errorVehicleFuel().validate("Must be a number greater than zero");
//        myFuelStopsAddEdit._text().errorTrailerFuel().validate("Must be a number greater than zero");
//        myFuelStopsAddEdit._button().bottomCancel().click();
//        
//        myFuelStops._button().add().click();
//        
//        //4. Enter special char and save
//        myFuelStopsAddEdit._textField().trailer().type("123ABC");
//        pause(5,"");
//        myFuelStopsAddEdit._textField().vehicleFuel().type("&$#!");
//        myFuelStopsAddEdit._textField().trailerFuel().type("&$#!");
//        myFuelStopsAddEdit._dropDown().driver().select(driverFullName);
//        pause(5,"");
//        myFuelStopsAddEdit._button().topSave().click();
//                     
//        //5. validate error message.
//        myFuelStopsAddEdit._text().errorMaster().validate("3 error(s) occurred. Please verify all the data entered is correct.");
//        myFuelStopsAddEdit._text().errorBothVehicleAndTrailerFuel().validate("Vehicle or Trailer fuel required.");
//        myFuelStopsAddEdit._text().errorVehicleFuel().validate("Must be a number greater than zero");
//        myFuelStopsAddEdit._text().errorTrailerFuel().validate("Must be a number greater than zero");
//        myFuelStopsAddEdit._button().bottomCancel().click();
//        
//        myFuelStops._button().add().click();
//        
//        //6. Enter zero and save
//        myFuelStopsAddEdit._textField().trailer().type("123ABC");
//        pause(5,"");
//        myFuelStopsAddEdit._textField().vehicleFuel().type("0");
//        myFuelStopsAddEdit._textField().trailerFuel().type("0");
//        myFuelStopsAddEdit._dropDown().driver().select(driverFullName);
//        pause(5,"");
//        myFuelStopsAddEdit._button().topSave().click();
//        
//        //7. validate error message.
//        myFuelStopsAddEdit._text().errorMaster().validate("3 error(s) occurred. Please verify all the data entered is correct.");
//        myFuelStopsAddEdit._text().errorBothVehicleAndTrailerFuel().validate("Vehicle or Trailer fuel required.");
//        myFuelStopsAddEdit._text().errorVehicleFuel().validate("Must be a number greater than zero");
//        myFuelStopsAddEdit._text().errorTrailerFuel().validate("Must be a number greater than zero");
//        myFuelStopsAddEdit._button().bottomCancel().click();
//      
//        myFuelStops._button().add().click();
//        
//        
//        //8. Enter negative number and save
//        myFuelStopsAddEdit._textField().trailer().type("123ABC");
//        pause(5,"");
//        myFuelStopsAddEdit._textField().vehicleFuel().type("-1");
//        myFuelStopsAddEdit._textField().trailerFuel().type("-1");
//        myFuelStopsAddEdit._dropDown().driver().select(driverFullName);
//        pause(5,"");
//        myFuelStopsAddEdit._button().topSave().click();
//        
//        //8. validate error message.
//        myFuelStopsAddEdit._text().errorMaster().validate("3 error(s) occurred. Please verify all the data entered is correct.");
//        myFuelStopsAddEdit._text().errorBothVehicleAndTrailerFuel().validate("Vehicle or Trailer fuel required.");
//        myFuelStopsAddEdit._text().errorVehicleFuel().validate("Must be a number greater than zero");
//        myFuelStopsAddEdit._text().errorTrailerFuel().validate("Must be a number greater than zero");
//        myFuelStopsAddEdit._button().bottomCancel().click();
//        
//        //10. Enter maximum characters and save
//       // myFuelStops._button().add().click();
//        
//       // myFuelStopsAddEdit._textField().vehicleFuel().type("1234567891");
//       // myFuelStopsAddEdit._dropDown().driver().select(2);
//       // myFuelStopsAddEdit._button().topSave().click();
//        
//        //11. Validate fraction fuel entry was saved
//        //myFuelStops._text().valueVehicleFuel().row(1).validate("1234567891 Gallons");
//                
//        //12. Delete newly added fuel entry
//        //myFuelStops._checkBox().entryCheckBox().row(1).check();
//        //myFuelStops._button().delete().click();
//        //myFuelStops._popUp().delete()._button().delete().click();
//        
//        //13. Enter more than maximum characters and save
//        //myFuelStops._button().add().click();
//        
//        //myFuelStopsAddEdit._textField().vehicleFuel().type("12345678912");
//        //myFuelStopsAddEdit._dropDown().driver().select(2);
//        //myFuelStopsAddEdit._button().topSave().click();
//        
//        //14. Validate only 10 characters were saved
//        //myFuelStops._text().valueVehicleFuel().row(1).validate("1234567891 Gallons");
//                
//        //15. Delete newly added fuel entry
//        //myFuelStops._checkBox().entryCheckBox().row(1).check();
//        //myFuelStops._button().delete().click();
//        //myFuelStops._popUp().delete()._button().delete().click();
//                             
//        //16. Enter fractions and save
//        //Set to English Units
//        myAccount._link().myAccount().click();
//        myAccount._button().edit().click();
//        myAccount._select().measurement().select("English");
//        myAccount._button().save().click();
//        
//        myFuelStops._link().hos().click();
//        myFuelStops._link().hosFuelStops().click();
//        myFuelStops._button().add().click();
//        
//        myFuelStopsAddEdit._textField().trailer().type("123ABC");
//        pause(5,"");
//        myFuelStopsAddEdit._textField().vehicleFuel().type("1.5");
//        myFuelStopsAddEdit._textField().trailerFuel().type("1.5");
//        myFuelStopsAddEdit._dropDown().driver().select(driverFullName);
//        pause(5,"");
//        myFuelStopsAddEdit._button().topSave().click();
//        
//        //17. Validate fraction fuel entry was saved
//        myFuelStops._text().valueVehicleFuel().row(1).validate("1.50 Gallons");
//        myFuelStops._text().valueTrailerFuel().row(1).validate("1.50 Gallons");
//       
//        //18. Delete fraction fuel entry. 
//        myFuelStops._checkBox().entryCheckBox().row(1).check();
//        myFuelStops._button().delete().click();
//        myFuelStops._popUp().delete()._button().delete().click();
//        
//        //19. Switch to Metric and Save
//        myAccount._link().myAccount().click();
//        myAccount._button().edit().click();
//        myAccount._select().measurement().select("Metric");
//        myAccount._button().save().click();
//        
//        //20. Verify fields for Gallons now displays as Liters
//        myFuelStops._link().hos().click();
//        myFuelStops._link().hosFuelStops().click();
//        pause(5,"");
//        myFuelStops._textField().vehicle().type(login.getUsername().substring(0, login.getUsername().length()-1));
//        myFuelStops._textField().vehicle().getSuggestion(login.getUsername()).click();
//        myFuelStops._button().add().click();
//        
//        myFuelStopsAddEdit._textField().trailer().type("125");
//        pause(5,"");
//        myFuelStopsAddEdit._textField().vehicleFuel().type("125");
//        myFuelStopsAddEdit._textField().trailerFuel().type("125");
//        myFuelStopsAddEdit._dropDown().driver().select(driverFullName);
//        pause(5,"");
//        myFuelStopsAddEdit._button().bottomSave().click();
//        
//        myFuelStops._text().valueVehicleFuel().row(1).validate("124.99 Liters");
//        myFuelStops._text().valueTrailerFuel().row(1).validate("124.99 Liters");
//        myFuelStops._text().valueTrailer().row(1).validate("125");
//        
//        //21. Switch back to Gallons
//        myAccount._link().myAccount().click();
//        myAccount._button().edit().click();
//        myAccount._select().measurement().select("English");
//        myAccount._button().save().click();
//        
//        myFuelStops._link().hos().click();
//        myFuelStops._link().hosFuelStops().click();
//        myFuelStops._textField().vehicle().type(login.getUsername().substring(0, login.getUsername().length()-1));
//        myFuelStops._textField().vehicle().getSuggestion(login.getUsername()).click();
//       
//        myFuelStops._text().valueVehicleFuel().row(1).validate("33.02 Gallons");
//        myFuelStops._text().valueTrailerFuel().row(1).validate("33.02 Gallons");
//        myFuelStops._text().valueTrailer().row(1).validate("125");
//        myFuelStops._checkBox().entryCheckBox().row(1).check();
//        myFuelStops._button().delete().click();
//        myFuelStops._popUp().delete()._button().delete().click();
//              
//    } 
//    @Test
//    public void IftaDateRange() {
//        set_test_case("TC5701");
//        //0. Login
//        myFuelStops.loginProcess(login);
//        myFuelStops._link().hos().click();
//        myFuelStops._link().hosFuelStops().click();
//    
//        //1. Get vehicle and click on Add
//        myFuelStops._textField().vehicle().type(login.getUsername().substring(0, login.getUsername().length()-1));
//        myFuelStops._textField().vehicle().getSuggestion(login.getUsername()).click();
//        pause(5, "Wait for propogation");
//              
//        //2. Verify Edit Link is available for current date range
//        myFuelStops._link().valueEdit().row(1).validateClickable(true);
//        
//        //3. Change date range to be outside the IFTA Aggregation
//        AutomationCalendar calendar = new AutomationCalendar(WebDateFormat.DATE_RANGE_FIELDS);
//        calendar.addToDay(-25);
//        myFuelStops._dateSelector().dateStop().click(calendar);
//        pause(2, "Wait for propogation");
////        myFuelStops._button().refresh().click();                
//        
//        calendar.addToDay(-25);
//        myFuelStops._dateSelector().dateStart().click(calendar);
//        pause(2, "Wait for propogation");
//        myFuelStops._button().refresh().click();
//        pause(2, "Wait for propogation");
//              
//        //4. Verify Edit Link is not clickable
//        if(myFuelStops._link().valueEdit().row(1).isPresent())
//            myFuelStops._link().valueEdit().row(1).validateClickable(false);
//        
//              
//    }
//}