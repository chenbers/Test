package com.inthinc.pro.selenium.testSuites;

import org.junit.Before;
import org.junit.Test;

import com.inthinc.pro.automation.utils.RandomValues;
import com.inthinc.pro.selenium.pageObjects.PageFuelStops;
import com.inthinc.pro.selenium.pageObjects.PageFuelStopsAddEdit;
import com.inthinc.pro.selenium.pageObjects.PageMyAccount;

public class HOSFuelStops extends WebRallyTest {

    private PageMyAccount myAccount;
    private RandomValues random;
    private PageFuelStops myFuelStops;
    private PageFuelStopsAddEdit myFuelStopsAddEdit;
    private String USERNAME = "tnilson";
    private String PASSWORD = "password123";

    @Before
    public void setupPage() {
        random = new RandomValues();
        myAccount = new PageMyAccount();
        myFuelStops = new PageFuelStops();
        myFuelStopsAddEdit = new PageFuelStopsAddEdit();
    }

    @Test
    public void FuelStopsAddError() {
        set_test_case("TC5627");

        // .0 Login
        myAccount.loginProcess(USERNAME, PASSWORD);
        myAccount._link().hos().click();
        myFuelStops._link().hosFuelStops().click();
        myFuelStops._textField().vehicle().type("108406");
        myFuelStops._textField().vehicle().getSuggestion("108406").click();
        myFuelStops._button().add().click();
        myFuelStopsAddEdit._button().bottomSave().click();
        myFuelStopsAddEdit._text().errorMaster().validate("1 error(s) occurred. Please verify all the data entered is correct.");
        myFuelStopsAddEdit._text().errorVehicleFuel().validate("Vehicle or Trailer fuel required.");
        myFuelStopsAddEdit._button().bottomCancel().click();

    }

    @Test
    public void CancelAddFuelStop() {
        set_test_case("TC5628");
        myAccount.loginProcess(USERNAME, PASSWORD);
        myAccount._link().hos().click();
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
        myFuelStopsAddEdit._button().bottomCancel().click();

    }

    @Test
    public void AddFuelStop() {
        set_test_case("TC5631");
        myAccount.loginProcess(USERNAME, PASSWORD);
        myAccount._link().hos().click();
        myFuelStops._link().hosFuelStops().click();
        myFuelStops._textField().vehicle().type("108406");
        myFuelStops._textField().vehicle().getSuggestion("108406").click();
        myFuelStops._button().add().click();
        // myFuelStopsAddEdit._textField().date().
        myFuelStopsAddEdit._textField().trailer().type("123");
        myFuelStopsAddEdit._textField().vehicleFuel().type("123");
        myFuelStopsAddEdit._dropDown().driver().select(2);
        myFuelStopsAddEdit._button().bottomSave().click();

    }

    @Test
    public void CancelEditFuelStop() {
        set_test_case("TC5632");
        myAccount.loginProcess(USERNAME, PASSWORD);
        myAccount._link().hos().click();
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

    }

    @Test
    public void EditFuelStop() {
        set_test_case("TC5630");
        myAccount.loginProcess(USERNAME, PASSWORD);
        myAccount._link().hos().click();
        myFuelStops._link().hosFuelStops().click();
        myFuelStops._textField().vehicle().type("108406");
        myFuelStops._textField().vehicle().getSuggestion("108406").click();
        myFuelStops._link().valueEdit().row(1).click();
        myFuelStopsAddEdit._textField().date().type("");
        myFuelStopsAddEdit._textField().trailer().type("456");
        myFuelStopsAddEdit._textField().vehicleFuel().type("456");
        myFuelStopsAddEdit._textField().trailerFuel().type("456");
        myFuelStopsAddEdit._button().bottomSave().click();

    }

    @Test
    public void CancelDeleteFuelStop() {
        set_test_case("TC5633");
        myAccount.loginProcess(USERNAME, PASSWORD);
        myAccount._link().hos().click();
        myFuelStops._link().hosFuelStops().click();
        myFuelStops._textField().vehicle().type("108406");
        myFuelStops._textField().vehicle().getSuggestion("108406").click();
        myFuelStops._checkBox().entryCheckBox().row(1).check();
        myFuelStops._button().delete().click();
        myFuelStops._popUp().delete()._button().cancel().click();

    }

    @Test
    public void DeleteFuelStop() {
        set_test_case("TC5629");
        myAccount.loginProcess(USERNAME, PASSWORD);
        myAccount._link().hos().click();
        myFuelStops._link().hosFuelStops().click();
        myFuelStops._textField().vehicle().type("108406");
        myFuelStops._textField().vehicle().getSuggestion("108406").click();
        myFuelStops._checkBox().entryCheckBox().row(1).check();
        myFuelStops._button().delete().click();
        myFuelStops._popUp().delete()._button().delete().click();

    }

    @Test
    public void EditColumns() {
        set_test_case("TC5702");
        myAccount.loginProcess(USERNAME, PASSWORD);
        myAccount._link().hos().click();
        myFuelStops._link().hosFuelStops().click();
        // myFuelStops._button().().click();

    }
}