package com.inthinc.pro.selenium.testSuites;

import org.junit.Before;
import org.junit.Test;

import com.inthinc.pro.automation.utils.AutomationCalendar;
import com.inthinc.pro.automation.utils.AutomationCalendar.WebDateFormat;
import com.inthinc.pro.selenium.pageObjects.PageMyAccount;
import com.inthinc.pro.selenium.pageObjects.PageFuelStops;
import com.inthinc.pro.selenium.pageObjects.PageFuelStopsAddEdit;


public class HOSFuelStops extends WebRallyTest {
    
    private PageMyAccount myAccount;
    private PageFuelStops myFuelStops;
    private PageFuelStopsAddEdit myFuelStopsAddEdit;
    private String USERNAME = "tnilson";
    private String PASSWORD = "password123";

    @Before
    public void setupPage() {
        myAccount = new PageMyAccount();
        myFuelStops = new PageFuelStops();
        myFuelStopsAddEdit = new PageFuelStopsAddEdit();
    }

    @Test
    public void FuelStopErrors() {
        set_test_case("TC5627");

        // 0.Login
        myFuelStops.loginProcess(USERNAME, PASSWORD);
        myFuelStops._link().hos().click();
        myFuelStops._link().hosFuelStops().click();
        
        //1.Selected vehicle id and click on Add        
        myFuelStops._textField().vehicle().type("108406");
        myFuelStops._textField().vehicle().getSuggestion("108406").click();
        myFuelStops._button().add().click();
        myFuelStopsAddEdit._dropDown().driver().select(1);
        
        //2.Generate Driver and Vehicle Fuel Errors
        myFuelStopsAddEdit._button().bottomSave().click();
        myFuelStopsAddEdit._text().errorMaster().validate("2 error(s) occurred. Please verify all the data entered is correct.");
        myFuelStopsAddEdit._text().errorVehicleFuel().validate("Vehicle fuel is required.");
        myFuelStopsAddEdit._text().errorDriver().validate("Driver is required");
        pause(5,"");
        myFuelStopsAddEdit._button().bottomCancel().click();
        
        //3. Generate Trailer Fuel Errors
        myFuelStops._button().add().click();
        myFuelStopsAddEdit._textField().trailer().type("123");
        myFuelStopsAddEdit._dropDown().driver().select(2);
        myFuelStopsAddEdit._button().bottomSave().click();
        
        myFuelStopsAddEdit._text().errorMaster().validate("1 error(s) occurred. Please verify all the data entered is correct.");
        myFuelStopsAddEdit._text().errorVehicleFuel().validate("Vehicle or Trailer fuel required.");  //TODO tina: should this be "is required"?  currently it is not.
        myFuelStopsAddEdit._button().bottomCancel().click();
        
        //4. Generate Date/Time in Future error
      
        myFuelStops._button().add().click();
        
        AutomationCalendar calendar = new AutomationCalendar(WebDateFormat.DATE_RANGE_FIELDS);
        calendar.addToDay(1);
        String tomorrow = calendar.toString();
        
        myFuelStopsAddEdit._textField().date().type(tomorrow);
        myFuelStopsAddEdit._textField().vehicleFuel().type("123");
        myFuelStopsAddEdit._dropDown().driver().select(2);
        myFuelStopsAddEdit._button().bottomSave().click();
        myFuelStopsAddEdit._text().errorMaster().validate("1 error(s) occurred. Please verify all the data entered is correct.");
        myFuelStopsAddEdit._text().errorDate().validate("Date/Time in the future is not valid.");
        myFuelStopsAddEdit._button().bottomCancel().click();
         
   
    }

    @Test
    public void CancelAddFuelStop() {
        set_test_case("TC5628");
        myFuelStops.loginProcess(USERNAME, PASSWORD);
        myFuelStops._link().hos().click();
        myFuelStops._link().hosFuelStops().click();
        myFuelStops._textField().vehicle().type("108406");
        myFuelStops._textField().vehicle().getSuggestion("108406").click();
        myFuelStops._button().add().click();
        myFuelStopsAddEdit._text().valueVehicle().validatePresence(true);
        // myFuelStopsAddEdit._textField().date();
        myFuelStopsAddEdit._text().timeMessage().validatePresence(true);
        myFuelStopsAddEdit._textField().trailer().type("123");
        myFuelStopsAddEdit._textField().vehicleFuel().type("123");
        myFuelStopsAddEdit._textField().trailerFuel().type("456");
        myFuelStopsAddEdit._dropDown().driver().select(2);
        myFuelStopsAddEdit._text().valueLocation().validatePresence(true);
        myFuelStopsAddEdit._button().topCancel().click();
        //TODO: verify that a new fuel entry was not added.
    }

    @Test
    public void AddFuelStop() {
        set_test_case("TC5631");
        myFuelStops.loginProcess(USERNAME, PASSWORD);
        myFuelStops._link().hos().click();
        myFuelStops._link().hosFuelStops().click();
        myFuelStops._textField().vehicle().type("108406");
        myFuelStops._textField().vehicle().getSuggestion("108406").click();
        myFuelStops._button().add().click();
        // myFuelStopsAddEdit._textField().date().
        myFuelStopsAddEdit._textField().trailer().type("123");
        myFuelStopsAddEdit._textField().vehicleFuel().type("123");
        myFuelStopsAddEdit._textField().trailerFuel().type("123");
        myFuelStopsAddEdit._dropDown().driver().select(2);
        myFuelStopsAddEdit._button().bottomSave().click();
        myFuelStops._text().valueVehicleFuel().row(1).validate("123Gallons");
        myFuelStops._text().valueTrailerFuel().row(1).validate("123Gallons");
        myFuelStops._text().valueTrailer().row(1).validate("123");
    }

    @Test
    public void CancelEditFuelStop() {
        set_test_case("TC5632");
        myFuelStops.loginProcess(USERNAME, PASSWORD);
        myFuelStops._link().hos().click();
        myFuelStops._link().hosFuelStops().click();
        myFuelStops._textField().vehicle().type("108406");
        myFuelStops._textField().vehicle().getSuggestion("108406").click();
        myFuelStops._link().valueEdit().row(1).click();
        myFuelStopsAddEdit._textField().date().type("");
        myFuelStopsAddEdit._textField().trailer().clear();
        myFuelStopsAddEdit._textField().trailer().type("456");
        myFuelStopsAddEdit._textField().vehicleFuel().clear();
        myFuelStopsAddEdit._textField().vehicleFuel().type("456");
        myFuelStopsAddEdit._textField().trailerFuel().type("456");
        myFuelStopsAddEdit._button().bottomCancel().click();
        
        //Verify Edits were not saved.
        myFuelStops._text().valueVehicleFuel().row(1).validate("123Gallons");
        myFuelStops._text().valueTrailerFuel().row(1).validate("123Gallons");
        myFuelStops._text().valueTrailer().row(1).validate("123");
    }

    @Test
    public void EditFuelStop() {
        set_test_case("TC5630");
        myFuelStops.loginProcess(USERNAME, PASSWORD);
        myFuelStops._link().hos().click();
        myFuelStops._link().hosFuelStops().click();
        myFuelStops._textField().vehicle().type("108406");
        myFuelStops._textField().vehicle().getSuggestion("108406").click();
        myFuelStops._link().valueEdit().row(1).click();
        myFuelStopsAddEdit._textField().date().type("");
        myFuelStopsAddEdit._textField().trailer().type("456");
        myFuelStopsAddEdit._textField().vehicleFuel().type("456");
        myFuelStopsAddEdit._textField().trailerFuel().type("456");
        myFuelStopsAddEdit._button().bottomSave().click();
        
        //Verify Edits were saved.
        myFuelStops._text().valueVehicleFuel().row(1).validate("456Gallons");
        myFuelStops._text().valueTrailerFuel().row(1).validate("456Gallons");
        myFuelStops._text().valueTrailer().row(1).validate("456");
    }

    @Test
    public void CancelDeleteFuelStop() {
        set_test_case("TC5633");
        myFuelStops.loginProcess(USERNAME, PASSWORD);
        myFuelStops._link().hos().click();
        myFuelStops._link().hosFuelStops().click();
        myFuelStops._textField().vehicle().type("108406");
        myFuelStops._textField().vehicle().getSuggestion("108406").click();
        myFuelStops._checkBox().entryCheckBox().row(1).check();
        myFuelStops._button().delete().click();
        myFuelStops._popUp().delete()._button().cancel().click();
        myFuelStops._text().valueVehicleFuel().row(1).validate("456Gallons");
        myFuelStops._text().valueTrailerFuel().row(1).validate("456Gallons");
        myFuelStops._text().valueTrailer().row(1).validate("456");
    }

    @Test
    public void DeleteFuelStop() {
        set_test_case("TC5629");
        myFuelStops.loginProcess(USERNAME, PASSWORD);
        myFuelStops._link().hos().click();
        myFuelStops._link().hosFuelStops().click();
        myFuelStops._textField().vehicle().type("108406");
        myFuelStops._textField().vehicle().getSuggestion("108406").click();
        myFuelStops._checkBox().entryCheckBox().row(1).check();
        myFuelStops._button().delete().click();
        myFuelStops._popUp().delete()._button().delete().click();
        //TODO: verify deletion of fuel stop entry.
    }

    @Test
    public void EditColumns() {
        set_test_case("TC5702");
        //0. Login
        myFuelStops.loginProcess(USERNAME, PASSWORD);
        myFuelStops._link().hos().click();
        myFuelStops._link().hosFuelStops().click();
        
        //1. Edit - uncheck columns and save
        myFuelStops._button().editColumns().click();
        myFuelStops._popUp().editColumns()._checkBox().row(3).uncheck();
        myFuelStops._popUp().editColumns()._checkBox().row(7).uncheck();
        myFuelStops._popUp().editColumns()._button().save().click();
        
        //2. Verify columns do not display after saving.
        myFuelStops._link().sortVehicle().validatePresence(false);
        myFuelStops._link().sortVehicleFuel().validatePresence(false);
        
        //3. Edit columns and select the previously unchecked columns.
        myFuelStops._button().editColumns().click();
        myFuelStops._popUp().editColumns()._checkBox().row(3).check();
        myFuelStops._popUp().editColumns()._checkBox().row(7).check();
        myFuelStops._popUp().editColumns()._button().save().click();
   
        //4. Verify columns display after saving.
        myFuelStops._link().sortVehicle().validatePresence(true);
        myFuelStops._link().sortVehicleFuel().validatePresence(true);

    }
    @Test
    public void VehicleField() {
        set_test_case("TC5700");
        //0. Login
        myFuelStops.loginProcess(USERNAME, PASSWORD);
        myFuelStops._link().hos().click();
        myFuelStops._link().hosFuelStops().click();
        
        //1. Vehicle field remains blank when only clicking on refresh
        myFuelStops._textField().vehicle().clear();
        myFuelStops._button().refresh().click();
        myFuelStops._textField().vehicle().validate("");
       
        //2. Enter a valid TIWI ID - should not offer suggestions
        myFuelStops._textField().vehicle().type("Tina");
        myFuelStops._textField().vehicle().getSuggestion(1).validate("No vehicles found");
        
        //3. Enter a valid MCM ID - select from suggestions
        myFuelStops._textField().vehicle().clear();
        myFuelStops._textField().vehicle().type("108406");
        myFuelStops._textField().vehicle().getSuggestion("108406").click();
        myFuelStops._textField().vehicle().validatePresence(true);
                
    }
    
    @Test
    public void FuelFields() {
        set_test_case("TC5703");
        //0. Login
        myFuelStops.loginProcess(USERNAME, PASSWORD);
        myFuelStops._link().hos().click();
        myFuelStops._link().hosFuelStops().click();
    
        //1. Get vehicle and click on Add
        myFuelStops._textField().vehicle().type("108406");
        myFuelStops._textField().vehicle().getSuggestion("108406").click();
        myFuelStops._button().add().click();
        
        //2. Enter Alpha char and save.
        myFuelStopsAddEdit._textField().trailer().type("123ABC");
        myFuelStopsAddEdit._textField().vehicleFuel().type("abcdefg");
        myFuelStopsAddEdit._textField().trailerFuel().type("abcdefg");
        myFuelStopsAddEdit._dropDown().driver().select(2);
        myFuelStopsAddEdit._button().topSave().click();
        
        //3. validate error message.
        myFuelStopsAddEdit._text().errorVehicleFuel().validate("Must be a number greater than zero");
        myFuelStopsAddEdit._text().errorTrailerFuel().validate("Must be a number greater than zero");
        myFuelStopsAddEdit._button().bottomCancel().click();
        
        //4. Enter special char and save
        myFuelStops._button().add().click();
        myFuelStopsAddEdit._textField().vehicleFuel().type("&$#!");
        myFuelStopsAddEdit._dropDown().driver().select(2);
        myFuelStopsAddEdit._button().topSave().click();
        
        //5. validate error message.
        myFuelStopsAddEdit._text().errorVehicleFuel().validate("Must be a number greater than zero");
        myFuelStopsAddEdit._button().bottomCancel().click();
        
        //6. Enter zero and save
        myFuelStops._button().add().click();
        myFuelStopsAddEdit._textField().vehicleFuel().type("0");
        myFuelStopsAddEdit._dropDown().driver().select(2);
        myFuelStopsAddEdit._button().topSave().click();
        
        //7. validate error message.
        myFuelStopsAddEdit._text().errorVehicleFuel().validate("Vehicle or Trailer fuel required.");
        myFuelStopsAddEdit._button().bottomCancel().click();
        
        //8. Enter maximum characters and save
        myFuelStops._button().add().click();
        
        myFuelStopsAddEdit._textField().vehicleFuel().type("1234567891");
        myFuelStopsAddEdit._dropDown().driver().select(2);
        myFuelStopsAddEdit._button().topSave().click();
        
        //9. Validate fraction fuel entry was saved
        myFuelStops._text().valueVehicleFuel().row(1).validate("1234567891Gallons");
                
        //10. Delete newly added fuel entry
        myFuelStops._checkBox().entryCheckBox().row(1).check();
        myFuelStops._button().delete().click();
        myFuelStops._popUp().delete()._button().delete().click();
        
        //11. Enter more than maximum characters and save
        myFuelStops._button().add().click();
        
        myFuelStopsAddEdit._textField().vehicleFuel().type("12345678912");
        myFuelStopsAddEdit._dropDown().driver().select(2);
        myFuelStopsAddEdit._button().topSave().click();
        
        //12. Validate only 10 characters were saved
        myFuelStops._text().valueVehicleFuel().row(1).validate("1234567891Gallons");
                
        //13. Delete newly added fuel entry
        myFuelStops._checkBox().entryCheckBox().row(1).check();
        myFuelStops._button().delete().click();
        myFuelStops._popUp().delete()._button().delete().click();
                             
        //14. Enter fractions and save
        myFuelStops._button().add().click();
        
        myFuelStopsAddEdit._textField().vehicleFuel().type("1.5");
        myFuelStopsAddEdit._dropDown().driver().select(2);
        myFuelStopsAddEdit._button().topSave().click();
        
        //15. Validate fraction fuel entry was saved
        myFuelStops._text().valueVehicleFuel().row(1).validate("1.5Gallons");
        
        //16. Delete fraction fuel entry. 
        myFuelStops._checkBox().entryCheckBox().row(1).check();
        myFuelStops._button().delete().click();
        myFuelStops._popUp().delete()._button().delete().click();
        
        //17. Switch to Metric and Save
        myAccount._link().admin().click();
        myAccount._button().edit().click();
        myAccount._select().measurement().select("Metric");
        myAccount._button().save().click();
        
        //18. Verify fields for Gallons now displays as Liters
        myFuelStops._link().hos().click();
        myFuelStops._link().hosFuelStops().click();
        myFuelStops._textField().vehicle().type("108406");
        myFuelStops._textField().vehicle().getSuggestion("108406").click();
        myFuelStops._button().add().click();
        
        myFuelStopsAddEdit._textField().trailer().type("125");
        myFuelStopsAddEdit._textField().vehicleFuel().type("125");
        
        myFuelStopsAddEdit._textField().trailerFuel().type("125");
        myFuelStopsAddEdit._dropDown().driver().select(2);
        myFuelStopsAddEdit._button().bottomSave().click();
        myFuelStops._text().valueVehicleFuel().row(1).validate("125liters");
        myFuelStops._text().valueTrailerFuel().row(1).validate("125liters");
        myFuelStops._text().valueTrailer().row(1).validate("125");
        
        //19. Switch back to Gallons
        myAccount._link().admin().click();
        myAccount._button().edit().click();
        myAccount._select().measurement().select("English");
        myAccount._button().save().click();
       
        myFuelStops._link().hos().click();
        myFuelStops._link().hosFuelStops().click();
        myFuelStops._text().valueVehicleFuel().row(1).validate("33Gallons");
        myFuelStops._text().valueTrailerFuel().row(1).validate("33Gallons");
        myFuelStops._text().valueTrailer().row(1).validate("125");
        myFuelStops._popUp().delete()._button().delete().click();
              
    } 
    
        
}