package com.inthinc.pro.selenium.testSuites;

import org.junit.Test;

import com.inthinc.pro.selenium.pageEnums.TAE.TimeDuration;
import com.inthinc.pro.selenium.pageObjects.PageAdminUsers;
import com.inthinc.pro.selenium.pageObjects.PageDriverPerformance;
import com.inthinc.pro.selenium.pageObjects.PageDriverPerformanceSeatBelt;
import com.inthinc.pro.selenium.pageObjects.PageDriverPerformanceSpeed;
import com.inthinc.pro.selenium.pageObjects.PageDriverPerformanceStyle;
import com.inthinc.pro.selenium.pageObjects.PageDriverReport;
import com.inthinc.pro.selenium.pageObjects.PageVehiclePerformance;

public class ReportsDriver extends WebRallyTest {

	private String username = "danniauto";
	private String password = "password";
	private PageDriverReport driver = new PageDriverReport();
	private PageDriverPerformance performance = new PageDriverPerformance();
	private PageDriverPerformanceStyle style = new PageDriverPerformanceStyle();
	private PageDriverPerformanceSeatBelt belt = new PageDriverPerformanceSeatBelt();
	private PageDriverPerformanceSpeed speeding = new PageDriverPerformanceSpeed();
	private PageVehiclePerformance vehicle = new PageVehiclePerformance();
	private PageAdminUsers admin = new PageAdminUsers();

	
	
	@Test
	public void DriverLink(){
		set_test_case("TC1543");
		
		//1- Login
		driver.loginProcess(username, password);
		
		//2- Click on Reports link
		driver._link().reports().click();
		
		//3- Select a driver name to click on
		String drivername = driver._link().driverValue().getText(1);
		driver._link().driverValue().click(1);
		
		//4- Compare to validate the correct page is pulled
		performance._link().driverName().assertEquals(drivername);
		
		
	}
	
	@Test
	public void DrivingStyleScore() {
		set_test_case("TC1545");

		//1- Login
		driver.loginProcess(username, password);
		
		//2- Click on Reports link
		driver._link().reports().click();
		
		//3- Take note of the top score in the Driving Style column.
		String driverstyle = driver._link().styleValue().getText(1);
				
		//4- Click on the Driving Style score box for that driver.
		driver._link().styleValue().click(1);
				
		//5- Click on the 12 months link
		style._link().duration(TimeDuration.MONTHS_12).click();
						
		//6- Verify the score is the same as in step 3.
		style._text().drivingStyleScoreValue().assertEquals(driverstyle);
		
	}
	
	@Test
	public void DriverReportEmail() {
		set_test_case("TC1547");
				
		//1- Login
		driver.loginProcess(username, password);
		
		//2- Click on Reports link
		driver._link().reports().click();
		
		//3- Click the Tools button
		driver._button().tools().click();
		
		//4- Select Email this Report
		driver._button().exportEmail().click();
		
		//5- Click Email
		driver._popUp().emailReport()._button().email().click();
				
	}
	
	@Test
	public void DriverReportExcel() {
		set_test_case("TC1548");
		
		//1- Login
		driver.loginProcess(username, password);
		
		//2- Click on Reports link
		driver._link().reports().click();
		
		//3- Click the Tools button
		driver._button().tools().click();
		
		//4- Select Export to Excel
		driver._button().exportExcel().click();
		
	}
	
	@Test
	public void DriverReportPDF() {
		set_test_case("TC1549");
		
		//1- Login
		driver.loginProcess(username, password);
		
		//2- Click on Reports link
		driver._link().reports().click();
		
		//3- Click the Tools button
		driver._button().tools().click();
		
		//4- Select Export to PDF
		driver._button().exportPDF().click();
		
	}

	@Test
	public void DriverReportOverallScore() {
		set_test_case("TC1553");
		
		//1- Login
		driver.loginProcess(username, password);
		
		//2- Click on Reports link
		driver._link().reports().click();
		
		//3- Take note of the top score in the Overall Score column.
		String overallscore = driver._link().overallValue().getText(1);
				
		//4- Click on the Driving Style score box for that driver.
		driver._link().overallValue().click(1);
				
		//5- Click on the 12 months link
		performance._link().overallDuration(TimeDuration.MONTHS_12).click();
						
		//6- Verify the score is the same as in step 3.
		performance._text().overallScore().assertEquals(overallscore);
				
	}
	
	@Test
	 public void DriverReportSearch() {
	  set_test_case("TC1560");
	  
	  PageDriverReport driver = new PageDriverReport();
	  // 1- Login
	  driver.loginProcess(username, password);

	  // 2- Click on Reports link
	  driver._link().reports().click();

	  // 3- Start entering information for group/driver/vehicle in a text
	  // field
	  driver._textField().driverSearch().type("Danni");

	  // 4- Remove focus
	  driver._textField().groupSearch().focus();

	  // 5- Let the page refresh to show results
	  pause(15, "Let the page refresh to show results");

	  // 6- Verify only that name shows on list
	  String name = "Danni";
	  boolean hasOnlyExpectedDrivernames = true;
	  boolean hasThisRow = true;
	  for (int i = 1; i < 10 && hasOnlyExpectedDrivernames && hasThisRow; i++) {
	   hasThisRow = driver._link().driverValue().isPresent(i);
	   if(hasThisRow) {
	    hasOnlyExpectedDrivernames &= driver._link().driverValue().validateContains(i, name);
	   }
	   }
	 }
	
	@Test
	public void DriverReportSeatBelt() {
		set_test_case("TC1562");
		
		//1- Login
		driver.loginProcess(username, password);
		
		//2- Click on Reports link
		driver._link().reports().click();
		driver._button().editColumns().click();
		driver._popUp().editColumns()._checkBox().check(9);
		driver._popUp().editColumns()._button().save().click();
		
		//3- Take note of the top score in the Seat Belt column.
		String seatbelt = driver._link().seatbeltValue().getText(1);
				
		//4- Click on the Seat Belt score box for that driver.
		driver._link().seatbeltValue().click(1);
				
		//5- Click on the 12 months link
		belt._link().duration(TimeDuration.MONTHS_12);
						
		//6- Verify the score is the same as in step 3.
		belt._text().overallScoreValue().assertEquals(seatbelt);
			
	}
	
	@Test
	public void DriverReportSpeed() {
		set_test_case("TC1564");
		
		//1- Login
		driver.loginProcess(username, password);
		
		//2- Click on Reports link
		driver._link().reports().click();
		driver._button().editColumns().click();
		driver._popUp().editColumns()._checkBox().check(7);
		driver._popUp().editColumns()._button().save().click();
		
		//3- Take note of the top score in the Speed column.
		String speed = driver._link().speedValue().getText(1);
		
		//4- Click on the Speed score box for that driver.
		driver._link().speedValue().click(1);
		
		//5- Click on the 12 months link
		speeding._link().duration(TimeDuration.MONTHS_12);
		
		//6- Verify the score is the same as in step 3.
		speeding._text().mainOverall().assertEquals(speed);
		
	}
	
	@Test
	public void DriverReportVehicleLink() {
		set_test_case("TC1569");
		
		//1- Login
		driver.loginProcess(username, password);
		
		//2- Click on Reports link
		driver._link().reports().click();
		
		//3- Select a vehicle to click on
		driver._link().vehicleSort().click();
		driver._link().vehicleSort().click();
		String vehiclename = driver._link().vehicleValue().getText(1);
		
		//4- Click on Vehicle
		driver._link().vehicleValue().click(1);
		
		//5- Compare to validate the correct page is pulled
		vehicle._link().vehicleName().assertEquals(vehiclename);
				
	}
	
	@Test
	public  void EditColumnsCancel() {
		set_test_case("TC1571");
		
		//1- Login
		driver.loginProcess(username, password);
		
		//2- Click on Reports link
		driver._link().reports().click();
		
		//3- Take note of what information shows in the columns
		boolean originallyHadGroupColumnGroup = driver._link().groupSort().isPresent();
		boolean originallyHadGroupColumnDriver = driver._link().driverSort().isPresent();
		boolean originallyHadGroupColumnVehicle = driver._link().vehicleSort().isPresent();
		boolean originallyHadGroupColumnDistance = driver._link().distanceDrivenSort().isPresent();
		boolean originallyHadGroupColumnOverall = driver._link().overallSort().isPresent();
		boolean originallyHadGroupColumnStyle = driver._link().styleSort().isPresent();
		boolean originallyHadGroupColumnSeatbelt = driver._link().seatBeltSort().isPresent();
				
		//4- Click on the Edit Columns link
		driver._button().editColumns().click();
		
		//5- Check one or more of the boxes in the pop up
		driver._popUp().editColumns()._checkBox().click(3);
		driver._popUp().editColumns()._checkBox().click(1);
		driver._popUp().editColumns()._checkBox().click(5);
		
		//6- Click Cancel
		driver._popUp().editColumns()._button().cancel().click();
		
		//7- Verify nothing has changed
		if(originallyHadGroupColumnGroup !=driver._link().groupSort().isPresent()){addError("expected Group column to remain the same", ErrorLevel.ERROR);
		}
		if(originallyHadGroupColumnDriver !=driver._link().driverSort().isPresent()){addError("expected Driver column to remain the same", ErrorLevel.ERROR);
		}
		if(originallyHadGroupColumnVehicle !=driver._link().vehicleSort().isPresent()){addError("expected Vehicle column to remain the same", ErrorLevel.ERROR);
		}
		if(originallyHadGroupColumnDistance !=driver._link().distanceDrivenSort().isPresent()){addError("expected Distance column to remain the same", ErrorLevel.ERROR);
		}
		if(originallyHadGroupColumnOverall !=driver._link().overallSort().isPresent()){addError("expected Overall column to remain the same", ErrorLevel.ERROR);
		}
		if(originallyHadGroupColumnStyle !=driver._link().styleSort().isPresent()){addError("expected Style column to remain the same", ErrorLevel.ERROR);
		}
		if(originallyHadGroupColumnSeatbelt !=driver._link().seatBeltSort().isPresent()){addError("expected Seat Belt column to remain the same", ErrorLevel.ERROR);
		}
		
	}
	
	@Test
	public void EditColumnsNoChangeCancel() {
		set_test_case("TC1572");
		
		//1- Login
		driver.loginProcess(username, password);
		
		//2- Click on Reports link
		driver._link().reports().click();
		
		//3- Take note of what information shows in the columns
		boolean originallyHadGroupColumnGroup = driver._link().groupSort().isPresent();
		boolean originallyHadGroupColumnDriver = driver._link().driverSort().isPresent();
		boolean originallyHadGroupColumnVehicle = driver._link().vehicleSort().isPresent();
		boolean originallyHadGroupColumnDistance = driver._link().distanceDrivenSort().isPresent();
		boolean originallyHadGroupColumnOverall = driver._link().overallSort().isPresent();
		boolean originallyHadGroupColumnStyle = driver._link().styleSort().isPresent();
		boolean originallyHadGroupColumnSeatbelt = driver._link().seatBeltSort().isPresent();
		
		//3- Click on the Edit Columns link
		driver._button().editColumns().click();
		
		//4- Click Cancel
		driver._popUp().editColumns()._button().cancel().click();
		
		//5- Verify nothing has changed
		if(originallyHadGroupColumnGroup !=driver._link().groupSort().isPresent()){addError("expected Group column to remain the same", ErrorLevel.ERROR);
		}
		if(originallyHadGroupColumnDriver !=driver._link().driverSort().isPresent()){addError("expected Driver column to remain the same", ErrorLevel.ERROR);
		}
		if(originallyHadGroupColumnVehicle !=driver._link().vehicleSort().isPresent()){addError("expected Vehicle column to remain the same", ErrorLevel.ERROR);
		}
		if(originallyHadGroupColumnDistance !=driver._link().distanceDrivenSort().isPresent()){addError("expected Distance column to remain the same", ErrorLevel.ERROR);
		}
		if(originallyHadGroupColumnOverall !=driver._link().overallSort().isPresent()){addError("expected Overall column to remain the same", ErrorLevel.ERROR);
		}
		if(originallyHadGroupColumnStyle !=driver._link().styleSort().isPresent()){addError("expected Style column to remain the same", ErrorLevel.ERROR);
		}
		if(originallyHadGroupColumnSeatbelt !=driver._link().seatBeltSort().isPresent()){addError("expected Seat Belt column to remain the same", ErrorLevel.ERROR);
		}
	}
	
	@Test
	public void EditColumnsBoxMouse() {
		set_test_case("TC1573");
		
		//1- Login
		driver.loginProcess(username, password);
		
		//2- Click on Reports link
		driver._link().reports().click();
		
		//3- Click on the Edit Columns link
		driver._button().editColumns().click();
		
		//4- Using the mouse, click on a check box
		driver._popUp().editColumns()._checkBox().click(1);
		
	}
	
	@Test
	public void EditColumnsRetention() {
		set_test_case("TC1575");
		
		//1- Login
		driver.loginProcess(username, password);
		
		//2- Click on Reports link
		driver._link().reports().click();
		
		//3- Make note of what columns are currently there
		boolean originallyHadGroupColumnGroup = driver._link().groupSort().isPresent();
		boolean originallyHadGroupColumnDriver = driver._link().driverSort().isPresent();
		boolean originallyHadGroupColumnVehicle = driver._link().vehicleSort().isPresent();
		boolean originallyHadGroupColumnDistance = driver._link().distanceDrivenSort().isPresent();
		boolean originallyHadGroupColumnOverall = driver._link().overallSort().isPresent();
		boolean originallyHadGroupColumnStyle = driver._link().styleSort().isPresent();
		boolean originallyHadGroupColumnSeatbelt = driver._link().seatBeltSort().isPresent();
		
		//4- Click on the Edit Columns link
		driver._button().editColumns().click();
		
		//5- Check one or more of the boxes in the pop up
		driver._popUp().editColumns()._checkBox().click(3);
		driver._popUp().editColumns()._checkBox().click(1);
		driver._popUp().editColumns()._checkBox().click(5);
		
		//6- Click Save
		driver._popUp().editColumns()._button().save().click();
		
		//7- Verify changes are there
		if(originallyHadGroupColumnGroup ==driver._link().groupSort().isPresent()){addError("expected Group Column to be different", ErrorLevel.ERROR);
		}
		if(originallyHadGroupColumnDriver ==driver._link().driverSort().isPresent()){addError("expected Driver Column to be different", ErrorLevel.ERROR);
		}
		if(originallyHadGroupColumnVehicle !=driver._link().vehicleSort().isPresent()){addError("expected Vehicle column to remain the same", ErrorLevel.ERROR);
		}
		if(originallyHadGroupColumnDistance ==driver._link().distanceDrivenSort().isPresent()){addError("expected Distance Column to be different", ErrorLevel.ERROR);
		}
		if(originallyHadGroupColumnOverall !=driver._link().overallSort().isPresent()){addError("expected Overall column to remain the same", ErrorLevel.ERROR);
		}
		if(originallyHadGroupColumnStyle !=driver._link().styleSort().isPresent()){addError("expected Style column to remain the same", ErrorLevel.ERROR);
		}
		if(originallyHadGroupColumnSeatbelt !=driver._link().seatBeltSort().isPresent()){addError("expected Seat Belt column to remain the same", ErrorLevel.ERROR);
		}
		
		//8- Click on any other link on the page
		driver._link().admin().click();
		
		//9- Click back on the reports tab
		admin._link().reports().click();
		
		//10- Verify changes are still there
		if(originallyHadGroupColumnGroup ==driver._link().groupSort().isPresent()){addError("expected Group Column to be different", ErrorLevel.ERROR);
		}
		if(originallyHadGroupColumnDriver ==driver._link().driverSort().isPresent()){addError("expected Driver Column to be different", ErrorLevel.ERROR);
		}
		if(originallyHadGroupColumnVehicle !=driver._link().vehicleSort().isPresent()){addError("expected Vehicle column to remain the same", ErrorLevel.ERROR);
		}
		if(originallyHadGroupColumnDistance ==driver._link().distanceDrivenSort().isPresent()){addError("expected Distance Column to be different", ErrorLevel.ERROR);
		}
		if(originallyHadGroupColumnOverall !=driver._link().overallSort().isPresent()){addError("expected Overall column to remain the same", ErrorLevel.ERROR);
		}
		if(originallyHadGroupColumnStyle !=driver._link().styleSort().isPresent()){addError("expected Style column to remain the same", ErrorLevel.ERROR);
		}
		if(originallyHadGroupColumnSeatbelt !=driver._link().seatBeltSort().isPresent()){addError("expected Seat Belt column to remain the same", ErrorLevel.ERROR);
		}
		
		//11- Click Edit Columns again
		driver._button().editColumns().click();
		
		//12- Change back to original settings
		driver._popUp().editColumns()._checkBox().click(3);
		driver._popUp().editColumns()._checkBox().click(1);
		driver._popUp().editColumns()._checkBox().click(5);
		
		//13- Click Save
		driver._popUp().editColumns()._button().save().click();
		
	}
	
	@Test
	public void EditColumnsSaveButton() {
		set_test_case("TC1577");
		
		//1- Login
		driver.loginProcess(username, password);
		
		//2- Click on Reports link
		driver._link().reports().click();
		
		//3- Make note of what columns are currently there
		boolean originallyHadGroupColumnGroup = driver._link().groupSort().isPresent();
		boolean originallyHadGroupColumnDriver = driver._link().driverSort().isPresent();
		boolean originallyHadGroupColumnVehicle = driver._link().vehicleSort().isPresent();
		boolean originallyHadGroupColumnDistance = driver._link().distanceDrivenSort().isPresent();
		boolean originallyHadGroupColumnOverall = driver._link().overallSort().isPresent();
		boolean originallyHadGroupColumnStyle = driver._link().styleSort().isPresent();
		boolean originallyHadGroupColumnSeatbelt = driver._link().seatBeltSort().isPresent();
		
		//4- Click on the Edit Columns link
		driver._button().editColumns().click();
		
		//5- Check one or more of the boxes in the pop up
		driver._popUp().editColumns()._checkBox().click(3);
		
		//6- Click Save
		driver._popUp().editColumns()._button().save().click();
		
		//7- Verify changes are there
		if(originallyHadGroupColumnGroup !=driver._link().groupSort().isPresent()){addError("expected Group column to remain the same", ErrorLevel.ERROR);
		}
		if(originallyHadGroupColumnDriver ==driver._link().driverSort().isPresent()){addError("expected Driver Column to be different", ErrorLevel.ERROR);
		}
		if(originallyHadGroupColumnVehicle !=driver._link().vehicleSort().isPresent()){addError("expected Vehicle column to remain the same", ErrorLevel.ERROR);
		}
		if(originallyHadGroupColumnDistance !=driver._link().distanceDrivenSort().isPresent()){addError("expected Distance column to remain the same", ErrorLevel.ERROR);
		}
		if(originallyHadGroupColumnOverall !=driver._link().overallSort().isPresent()){addError("expected Overall column to remain the same", ErrorLevel.ERROR);
		}
		if(originallyHadGroupColumnStyle !=driver._link().styleSort().isPresent()){addError("expected Style column to remain the same", ErrorLevel.ERROR);
		}
		if(originallyHadGroupColumnSeatbelt !=driver._link().seatBeltSort().isPresent()){addError("expected Seat Belt column to remain the same", ErrorLevel.ERROR);
		}
		
		//8- Click Edit Columns again
		driver._button().editColumns().click();
		
		//9- Change back to original settings
		driver._popUp().editColumns()._checkBox().click(3);
		
		//10- Click Save
		driver._popUp().editColumns()._button().save().click();
	}
	
	@Test
	public void EditColumnsOldSession() {
		set_test_case("TC1578");
		
		//1- Login
		driver.loginProcess(username, password);
		
		//2- Click on Reports link
		driver._link().reports().click();
		
		//3- Make note of what columns are currently there
		boolean originallyHadGroupColumnGroup = driver._link().groupSort().isPresent();
		boolean originallyHadGroupColumnDriver = driver._link().driverSort().isPresent();
		boolean originallyHadGroupColumnVehicle = driver._link().vehicleSort().isPresent();
		boolean originallyHadGroupColumnDistance = driver._link().distanceDrivenSort().isPresent();
		boolean originallyHadGroupColumnOverall = driver._link().overallSort().isPresent();
		boolean originallyHadGroupColumnStyle = driver._link().styleSort().isPresent();
		boolean originallyHadGroupColumnSeatbelt = driver._link().seatBeltSort().isPresent();
		
		//4- Click on the Edit Columns link
		driver._button().editColumns().click();
		
		//5- Check one or more of the boxes in the pop up
		driver._popUp().editColumns()._checkBox().click(3);
		
		//6- Click Save
		driver._popUp().editColumns()._button().save().click();
		
		//7- Verify changes and make note
		if(originallyHadGroupColumnGroup !=driver._link().groupSort().isPresent()){addError("expected Group column to remain the same", ErrorLevel.ERROR);
		}
		if(originallyHadGroupColumnDriver ==driver._link().driverSort().isPresent()){addError("expected Driver Column to be different", ErrorLevel.ERROR);
		}
		if(originallyHadGroupColumnVehicle !=driver._link().vehicleSort().isPresent()){addError("expected Vehicle column to remain the same", ErrorLevel.ERROR);
		}
		if(originallyHadGroupColumnDistance !=driver._link().distanceDrivenSort().isPresent()){addError("expected Distance column to remain the same", ErrorLevel.ERROR);
		}
		if(originallyHadGroupColumnOverall !=driver._link().overallSort().isPresent()){addError("expected Overall column to remain the same", ErrorLevel.ERROR);
		}
		if(originallyHadGroupColumnStyle !=driver._link().styleSort().isPresent()){addError("expected Style column to remain the same", ErrorLevel.ERROR);
		}
		if(originallyHadGroupColumnSeatbelt !=driver._link().seatBeltSort().isPresent()){addError("expected Seat Belt column to remain the same", ErrorLevel.ERROR);
		}
		
		//8- Log out
		driver._link().logout().click();
		
		//9- Login
		driver.loginProcess(username, password);
		
		//10- Click back on reports link
		driver._link().reports().click();
		
		//11- Verify changes are still there
		if(originallyHadGroupColumnGroup !=driver._link().groupSort().isPresent()){addError("expected Group column to remain the same", ErrorLevel.ERROR);
		}
		if(originallyHadGroupColumnDriver ==driver._link().driverSort().isPresent()){addError("expected Driver Column to be different", ErrorLevel.ERROR);
		}
		if(originallyHadGroupColumnVehicle !=driver._link().vehicleSort().isPresent()){addError("expected Vehicle column to remain the same", ErrorLevel.ERROR);
		}
		if(originallyHadGroupColumnDistance !=driver._link().distanceDrivenSort().isPresent()){addError("expected Distance column to remain the same", ErrorLevel.ERROR);
		}
		if(originallyHadGroupColumnOverall !=driver._link().overallSort().isPresent()){addError("expected Overall column to remain the same", ErrorLevel.ERROR);
		}
		if(originallyHadGroupColumnStyle !=driver._link().styleSort().isPresent()){addError("expected Style column to remain the same", ErrorLevel.ERROR);
		}
		if(originallyHadGroupColumnSeatbelt !=driver._link().seatBeltSort().isPresent()){addError("expected Seat Belt column to remain the same", ErrorLevel.ERROR);
		}
		
		//12- Click Edit Columns again
		driver._button().editColumns().click();
		
		//13- Change back to original settings
		driver._popUp().editColumns()._checkBox().click(3);
		
		//14- Click Save
		driver._popUp().editColumns()._button().save().click();

	}
	
	@Test
	
	public void EditColumnsUI() {
		set_test_case("TC1580");
		
		//1- Login
		driver.loginProcess(username, password);
		
		//2- Click on Reports link
		driver._link().reports().click();
				
		//3- Click on the Edit Columns link
		driver._button().editColumns().click();
		
		//4- Verify the UI of the Edit Columns pop up
		Integer group = 1;
		Integer employee = 2;
		Integer drive = 3;
		Integer vehicle = 4;
		Integer distance = 5;
		Integer overall = 6;
		Integer speed = 7;
		Integer style = 8;
		Integer belt = 9;
		driver._popUp().editColumns()._checkBox().assertVisibility(group, true);
		driver._popUp().editColumns()._checkBox().assertVisibility(employee, true);
		driver._popUp().editColumns()._checkBox().assertVisibility(drive, true);
		driver._popUp().editColumns()._checkBox().assertVisibility(vehicle, true);
		driver._popUp().editColumns()._checkBox().assertVisibility(distance, true);
		driver._popUp().editColumns()._checkBox().assertVisibility(overall, true);
		driver._popUp().editColumns()._checkBox().assertVisibility(speed, true);
		driver._popUp().editColumns()._checkBox().assertVisibility(style, true);
		driver._popUp().editColumns()._checkBox().assertVisibility(belt, true);
		
	}
	
	
}