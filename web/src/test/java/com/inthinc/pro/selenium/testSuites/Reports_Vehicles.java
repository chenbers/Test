package com.inthinc.pro.selenium.testSuites;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.internal.selenesedriver.GetCurrentUrl;

import com.inthinc.pro.selenium.pageObjects.PageLogin;
import com.inthinc.pro.selenium.pageObjects.PageVehicleReport;

public class Reports_Vehicles extends WebRallyTest {
    
    private String username = "dmonk";
    private String password = "password";
    private PageVehicleReport reports_vehicles;
    private PageLogin login_page;
    
    @Before
    public void setuppage() {
        reports_vehicles = new PageVehicleReport();
        login_page = new PageLogin();
    }

    @Test
    public void Bookmark_entry() {
        set_test_case("TC1614");

        //Login
        reports_vehicles.loginProcess(username, password);
        
        //Navigate to reports>vehicles page
        reports_vehicles._link().reports().click();
        reports_vehicles._link().vehicles().click();

                                                                //TODO: dmonk: Add in check to make sure location is correct.
        
        //Get and save current URL
        savePageLink();
  
        //Logout
        reports_vehicles._link().logout().click();
        
        //Open saved URL and login
        openSavedPage();
        login_page._textField().userName().type(username);
        login_page._textField().password().type(password);
        login_page._button().logIn().click();
        
        //Add validation of location                            //TODO: dmonk: Add in check to make sure location is correct.

    }
    
    @Test
    public void Bookmark_entry_DifferentAccount() {
        set_test_case("TC1615");
        
        //Login
        reports_vehicles.loginProcess(username, password);
        
        //Navigate to reports>vehicles page
        reports_vehicles._link().reports().click();
        reports_vehicles._link().vehicles().click();
        
        //Add validation for current location.                  //TODO: dmonk: Add in check to make sure location is correct.  
        
        //Bookmark page
        savePageLink();
        
        //Logout
//        reports_vehicles._link().logout().click();
        
        //Login to different account.                           //TODO: dmonk: Change login for account other than QA.
        String temp_user = "0004";
        
        reports_vehicles.loginProcess(temp_user, password);
//        openSavedPage();
        
        //Add validation of location                            //TODO: dmonk: Add in check tomake sure location is correct.
        
    }
    
//    @Test
//    public void 
}
