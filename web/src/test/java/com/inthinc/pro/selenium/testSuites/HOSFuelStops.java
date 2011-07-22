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
        myFuelStopsAddEdit._text().labelVehicle().validate("108406");
        // myFuelStopsAddEdit._textField().date();
        myFuelStopsAddEdit._text().timeMessage().validate("Fuel stops more than 25 days old will not be included in the IFTA aggregation.");
        myFuelStopsAddEdit._textField().trailer().type("123");
        myFuelStopsAddEdit._textField().vehicleFuel().type("123");
        myFuelStopsAddEdit._dropDown().driver().select(2);
        // myFuelStopsAddEdit._text().labelLocation().validate("");
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
        myFuelStops.loginProcess(USERNAME, PASSWORD);
        myFuelStops._link().hos().click();
        myFuelStops._link().hosFuelStops().click();
        myFuelStops._popUp().editColumns()._checkBox().row(3);
        myFuelStops._popUp().editColumns()._button().save().click();
       //TODO: finish this test case //myFuelStops.
      

    }
}