package com.inthinc.pro.selenium.testSuites;

import org.junit.Before;
import org.junit.Test;

import com.inthinc.pro.automation.utils.RandomValues;
import com.inthinc.pro.selenium.pageObjects.PageFuelStops;
import com.inthinc.pro.selenium.pageObjects.PageFuelStopsAddEdit;


public class HOSFuelStops extends WebRallyTest {

    private RandomValues random;
    private PageFuelStops myFuelStops;
    private PageFuelStopsAddEdit myFuelStopsAddEdit;
    private String USERNAME = "tnilson";
    private String PASSWORD = "password123";

    @Before
    public void setupPage() {
        random = new RandomValues();
        myFuelStops = new PageFuelStops();
        myFuelStopsAddEdit = new PageFuelStopsAddEdit();
    }

    @Test
    public void FuelStopsAddError() {
        set_test_case("TC5627");

        // .0 Login
        myFuelStops.loginProcess(USERNAME, PASSWORD);
        myFuelStops._link().hos().click();
        myFuelStops._link().hosFuelStops().click();
        myFuelStops._textField().vehicle().type("108406");
        myFuelStops._textField().vehicle().getSuggestion("108406").click();
        myFuelStops._button().add().click();
        myFuelStopsAddEdit._button().bottomSave().click();
        myFuelStopsAddEdit._text().errorMaster().validate("1 error(s) occurred. Please verify all the data entered is correct.");
        myFuelStopsAddEdit._text().errorVehicleFuel().validate("Vehicle fuel is required.");
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
        myFuelStopsAddEdit._dropDown().driver().select(2);
        myFuelStopsAddEdit._button().bottomSave().click();
        //TODO: verify the new fuel entry displays
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
        //TODO: verify the edits were not saved.
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
        //TODO: verify edits to fuel stop entry
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
        //TODO: verify fuel stop entry was not deleted.
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
        myFuelStopsAddEdit._textField().vehicleFuel().type("abcdefg");
        myFuelStopsAddEdit._dropDown().driver().select(2);
        myFuelStopsAddEdit._button().topSave().click();
        
        //3. validate error message.
        myFuelStopsAddEdit._text().errorVehicleFuel().validate("Must be a number greater than zero");
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
        
        //8. Enter fractions and save
        myFuelStops._button().add().click();
        
        myFuelStopsAddEdit._textField().vehicleFuel().type("1.5");
        myFuelStopsAddEdit._dropDown().driver().select(2);
        myFuelStopsAddEdit._button().topSave().click();
        
        //9. Validate fraction fuel entry was saved
        myFuelStops._text().valueVehicleFuel().row(1).validate("1.5Gallons");
        
        //10. Delete fraction fuel entry. 
        myFuelStops._checkBox().entryCheckBox().row(1).check();
        myFuelStops._button().delete().click();
        myFuelStops._popUp().delete()._button().delete().click();
               
    
    } 
    
}