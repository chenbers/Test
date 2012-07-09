package com.inthinc.pro.selenium.testSuites;

import org.junit.Ignore;

@Ignore
public class VerifyPageObjectsTest extends WebRallyTest {
//    private AutomationUser login;
//
//	@Before
//	public void setupPage() {
//		login = AutomationUsers.getUsers().getOne();
//	}
//	
//	@Test
//	public void pageOrgPage() {
//	    
//	    PageAdminOrganization page = new PageAdminOrganization();
//	    page.loginProcess(login);
//	    page._link().admin().click();
//	    page._link().adminOrganization().click();
////	    page.getFleet().getUser("Albina Gregoric").text().click();
////
////	    page.getFleet().getTeam("Team Test Tina").icon().click();
////	    page.getFleet().getTeam("Team Test Tina").text().click();
////	    page.getFleet().getTeam("Team Test Tina").arrow().click();
////	    page.getFleet().getTeam("Team Test Tina").getDriver("Albina Gregoric").text().click();
//	    
//	    page._button().add().click();
//	    //page._button().add().click();
//	    pause(15, "just clicked add() ");
//	    page._button().edit().click();
//	    page._dropDown().parentGroup().selectRow(4);
//	    page._textField().editFindAddress().type("950 Laird Ave Salt Lake City");
//	    
//	}
//	
////	@Test
////	public void pageWaysmartReport() {
////	    PageWaysmartReport page = new PageWaysmartReport();
////	    page.loginProcess(USERNAME, PASSWORD);
////	    page._link().reports().click();
////	    PageReportsDrivers driverReport = new PageReportsDrivers();
////        driverReport._link().waySmart().click();
////        
////	    ArrayList<String> desiredOptions = new ArrayList<String>();
////        desiredOptions.add(" Driving Time Violations Summary Report");
////        desiredOptions.add(" Driving Time Violations Detail Report");
////        desiredOptions.add(" Payroll Summary Report");
////
////        desiredOptions.add(" Payroll Report Driver Report");
////        desiredOptions.add(" Payroll Report Driver Sign off");
////        desiredOptions.add(" Ten Hour Day Violations");
////        desiredOptions.add(" Driver Hours");
////        desiredOptions.add(" Driver Performance per Group");
////        desiredOptions.add(" Driver Performance RYG per Group");
////
////        desiredOptions.add("Mileage By Vehicle");
////        desiredOptions.add("State Mileage By Vehicle");
////        desiredOptions.add("State Mileage By Vehicle - Road Status");
////        desiredOptions.add(">State Mileage Fuel By Vehicle");
////        desiredOptions.add("State Mileage By Month");
////        desiredOptions.add("Group Comparison By State/Province");
////        int i = 1;
////        for(String desiredOption: desiredOptions){            
////            page._dropDown().reportDropDown().select(i);
////            i++;
////            pause(5, "just pausing to manually inspect test");
////        }
////        page._dropDown().reportDropDown().select(2);
////        AutomationCalendar calendar = new AutomationCalendar(WebDateFormat.DATE_RANGE_FIELDS);
////        calendar.addToDay(-1);
////        page._textField().stopDate().type(calendar);
////        calendar.addToDay(-10);
////        page._textField().startDate().type(calendar);
////        
////	    
////	}
//	
//	public void allPages_navigateAndValidate_() {
//		//set_test_case("TC5255");
////      Log in.
//		PageMyAccount my = new PageMyAccount();
//		my.loginProcess(login);
//
////		Click Reports.
////		my._link().reports().click();
////		PageDriverReport driverReport = new PageDriverReport();
////		driverReport.validate();
//		
////		Click Vehicles.
////		driverReport._link().vehicles().click();
////		PageVehicleReport vehicleReport = new PageVehicleReport();
////		vehicleReport.validate();
//		
////		Click Idling.
////		vehicleReport._link().idling().click();
////		PageIdlingReport idlingReport = new PageIdlingReport();
////		idlingReport.validate();
//		
////		Click Devices.
////		idlingReport._link().devices().click();
////		PageDeviceReport deviceReport = new PageDeviceReport();
////		deviceReport.validate();
//		
//		//Click waySmart.
//		my._link().reports().click();
//		PageReportsDrivers driverReport = new PageReportsDrivers();
//		driverReport._link().waySmart().click();
////        PageWaysmartReport waysmartReport = new PageWaysmartReport();
////        waysmartReport.validate();		
//        
//        
////		Click Notifications.
////        deviceReport._link().notifications().click();
//        PageNotificationsRedFlags page = new PageNotificationsRedFlags();
//        page.validate();
//        
//        //TODO: this section of the test cannot be completed until these pageObjects have been created.
////		Select a Team and Time Frame and click Refresh.
////		Click Safety.
////		Select a Team and Time Frame and click Refresh.
////		Click Diagnostics.
////		Select a Team and Time Frame and click Refresh.
////		Click Zones.
////		Select a Team and Time Frame and click Refresh.
////		Click HOS Exceptions.
////		Select a Team and Time Frame and click Refresh.
////		Click Emergency.
////		Select a Team and Time Frame and click Refresh.
////		Click Crash History.
////		Select a Team and Time Frame and click Refresh.
////		Click Live Fleet.
////		Click HOS.
////		Select a Driver and click Refresh.
//        addError("pageObjects don't exist", "notification pageObjects need created", ErrorLevel.ERROR.FAIL);
//        
////		Click Reports.
//        //deviceReport._link().reports().click();
//        driverReport.validate();
//        
////		Click Admin.
//        driverReport._link().admin().click();
//        PageAdminUsers adminUsers = new PageAdminUsers();
//        adminUsers.validate();
//        
////		Click Add User.
//        adminUsers._link().adminAddUser().click();
//        PageAdminAddEditUser addUser = new PageAdminAddEditUser();
//        addUser.validate();
//        
////		Click Vehicles.
//        addUser._link().adminVehicles().click();
//        PageAdminVehicles adminVehicles = new PageAdminVehicles();
//        adminVehicles.validate();
//        
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
//	}
}
