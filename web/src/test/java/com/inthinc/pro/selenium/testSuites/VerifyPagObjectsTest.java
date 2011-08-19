package com.inthinc.pro.selenium.testSuites;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import com.inthinc.pro.selenium.pageObjects.PageAddEditUser;
import com.inthinc.pro.selenium.pageObjects.PageAdminUsers;
import com.inthinc.pro.selenium.pageObjects.PageAdminVehicles;
import com.inthinc.pro.selenium.pageObjects.PageDriverReport;
import com.inthinc.pro.selenium.pageObjects.PageMyAccount;
import com.inthinc.pro.selenium.pageObjects.PageNotificationsRedFlags;
import com.inthinc.pro.selenium.pageObjects.PageWaysmartReport;

public class VerifyPagObjectsTest extends WebRallyTest {

	private PageMyAccount my;
	
	private String USERNAME="jwimmer";
	private String PASSWORD="password";

	@Before
	public void setupPage() {
		my = new PageMyAccount();
	}

	@Test()
	public void allPages_navigateAndValidate_() {
		set_test_case("TC5255");
//      Log in.
		my.loginProcess(USERNAME, PASSWORD);

//		Click Reports.
//		my._link().reports().click();
//		PageDriverReport driverReport = new PageDriverReport();
//		driverReport.validate();
		
//		Click Vehicles.
//		driverReport._link().vehicles().click();
//		PageVehicleReport vehicleReport = new PageVehicleReport();
//		vehicleReport.validate();
		
//		Click Idling.
//		vehicleReport._link().idling().click();
//		PageIdlingReport idlingReport = new PageIdlingReport();
//		idlingReport.validate();
		
//		Click Devices.
//		idlingReport._link().devices().click();
//		PageDeviceReport deviceReport = new PageDeviceReport();
//		deviceReport.validate();
		
		//Click waySmart.
		my._link().reports().click();
		PageDriverReport driverReport = new PageDriverReport();
		driverReport._link().waySmart().click();
        PageWaysmartReport waysmartReport = new PageWaysmartReport();
        waysmartReport.validate();		
        
        ArrayList<String> desiredOptions = new ArrayList<String>();
        desiredOptions.add("Driving Time Violations Summary Report");
        desiredOptions.add("Driving Time Violations Detail Report");
        desiredOptions.add("Payroll Summary Report");

        desiredOptions.add("Payroll Report Driver Report");
        desiredOptions.add("Payroll Report Driver Sign off");
        desiredOptions.add("Ten Hour Day Violations");
        desiredOptions.add("Driver Hours");
        desiredOptions.add("Driver Performance per Group");
        desiredOptions.add("Driver Performance RYG per Group");

        desiredOptions.add("Mileage By Vehicle");
        desiredOptions.add("State Mileage By Vehicle");
        desiredOptions.add("State Mileage By Vehicle - Road Status");
        desiredOptions.add(">State Mileage Fuel By Vehicle");
        desiredOptions.add("State Mileage By Month");
        desiredOptions.add("Group Comparison By State/Province");
        for(String desiredOption: desiredOptions){
            waysmartReport._dropDown().reportDropDown().select(desiredOption);
            waysmartReport._dropDown().reportDropDown().assertEquals(desiredOption);
            pause(5, "just pausing to manually inspect test");
        }
//		Click Notifications.
//        deviceReport._link().notifications().click();
        PageNotificationsRedFlags page = new PageNotificationsRedFlags();
        page.validate();
        
        //TODO: this section of the test cannot be completed until these pageObjects have been created.
//		Select a Team and Time Frame and click Refresh.
//		Click Safety.
//		Select a Team and Time Frame and click Refresh.
//		Click Diagnostics.
//		Select a Team and Time Frame and click Refresh.
//		Click Zones.
//		Select a Team and Time Frame and click Refresh.
//		Click HOS Exceptions.
//		Select a Team and Time Frame and click Refresh.
//		Click Emergency.
//		Select a Team and Time Frame and click Refresh.
//		Click Crash History.
//		Select a Team and Time Frame and click Refresh.
//		Click Live Fleet.
//		Click HOS.
//		Select a Driver and click Refresh.
        addError("pageObjects don't exist", "notification pageObjects need created", ErrorLevel.ERROR.FAIL);
        
//		Click Reports.
        //deviceReport._link().reports().click();
        driverReport.validate();
        
//		Click Admin.
        driverReport._link().admin().click();
        PageAdminUsers adminUsers = new PageAdminUsers();
        adminUsers.validate();
        
//		Click Add User.
        adminUsers._link().adminAddUser().click();
        PageAddEditUser addUser = new PageAddEditUser();
        addUser.validate();
        
//		Click Vehicles.
        addUser._link().adminVehicles().click();
        PageAdminVehicles adminVehicles = new PageAdminVehicles();
        adminVehicles.validate();
        
//		Click Add Vehicle.
        //TODO: PageAddEditVehicle does not exist
//        adminVehicles._link().adminAddVehicle().click();
//        PageAddEditVehicle addVehicle = new PageAddEditVehicle();
//        addVehicle.validate();
        
//		Click Devices.
      //TODO: PageObject does not exist
//        addVehicle._link().adminDevices().click();
//        PageAdminDevices adminDevices = new PageAdminDevices();
//        adminDevices.validate();
        
//		Click Zones. 
      //TODO: PageObject does not exist
//        adminDevices._link().adminZones().click();
//        PageAdminZones adminZones = new PageAdminZones();
//        adminZones.validate();
        
//		Click Red Flags.
      //TODO: PageObject does not exist
//        adminZones._link().adminRedFlags().click();
//        PageAdminRedFlags adminRedFlags = new PageAdminRedFlags();
//        adminRedFlags.validate();
        
//		Click Add Red Flag.
      //TODO: PageObject does not exist
//        adminRedFlags._link().adminAddRedFlag().click();
//        PageAddEditRedFlag addRedFlag = new PageAddEditRedFlag();
//        addRedFlag.validate();
        
//		Click Reports.
      //TODO: PageObject does not exist
//        addRedFlag._link().adminReports().click();
//        PageAdminReports adminReports = new PageAdminReports();
//        adminReports.validate();
        
//		Click Add Report.
      //TODO: PageObject does not exist
//        adminReports._link().adminAddReport().click();
//        PageAddEditReport addReport = new PageAddEditReport();
//        addReport.validate();
        
//		Click Organization.
      //TODO: PageObject does not exist
//        addReport._link().adminOrganization().click();
//        PageAdminOrganization adminOrg = new PageAdminOrganization();
//        adminOrg.validate();
        
//		Click Custom Roles.
      //TODO: PageObject does not exist
//        addReport._link().adminCustomRoles().click();
//        PageAdminCustomRoles adminCustomRoles = new PageAdminCustomRoles();
//        adminCustomRoles.validate();
        
//		Click Add Custom Role.
      //TODO: PageObject does not exist
//        adminCustomRoles._link().adminAddCustomRole().click();
//        PageAddEditCustomRole addCustomRole = new PageAddEditCustomRole();
//        addCustomRole.validate();
        
//		Click Speed By Street.
      //TODO: PageObject does not exist
//        addCustomRole._link().adminSpeedByStreet().click();
//        PageAdminSBS adminSBS = new PageAdminSBS();
//        adminSBS.validate();
        
//		Click Account.
      //TODO: PageObject does not exist
//        adminSBS._link().adminAccount().click();
//        PageAdminAccount adminAccount = new PageAdminAccount();
//        adminAccount.validate();
	}
}
