package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.Button;
import com.inthinc.pro.automation.elements.DhxDropDown;
import com.inthinc.pro.automation.elements.TextTable;
import com.inthinc.pro.automation.elements.TextTableLink;
import com.inthinc.pro.automation.elements.Text;
import com.inthinc.pro.automation.elements.TextButton;
import com.inthinc.pro.automation.elements.TextField;
import com.inthinc.pro.automation.elements.TextLink;
import com.inthinc.pro.selenium.pageEnums.DeviceReportEnum;
import com.inthinc.pro.selenium.pageEnums.DriverReportEnum;
import com.inthinc.pro.selenium.pageEnums.PopUpEnum;
import com.inthinc.pro.selenium.pageEnums.ReportsBarEnum;

public class PageDeviceReport extends ReportsBar {
	private String page = "devices";

	public class DeviceReportPopUps extends PopUps{
    	public DeviceReportPopUps(){
    		super(page, Types.REPORT, 3);
    	}
    	
    	public Email emailReport(){
    		return new Email();
    	}
    	
    	public EditColumns editColumns(){
    		return new EditColumns();
    	}
    }

	public DeviceReportPopUps _popUp() {
		return new DeviceReportPopUps();
	}
	
	public DeviceReportDropDowns _dropDown(){
		return new DeviceReportDropDowns();
	}
	public class DeviceReportDropDowns extends ReportsBarDropDowns{
		
		protected DhxDropDown status() {
			return new DhxDropDown(DeviceReportEnum.STATUS_DHX);
		}
	}
	public DeviceReportsLinks _link(){
		return new DeviceReportsLinks();
	}
	
	public class DeviceReportsLinks extends ReportsBarLinks{
		
		public TextTableLink vehicleValue(){
			return new TextTableLink(DriverReportEnum.VEHICLE_VALUE);
		}
		
		public TextLink deviceIDSort(){
			return new TextLink(DeviceReportEnum.DEVICE_ID_SORT);
		}
		
		public TextLink assignedVehicleSort(){
			return new TextLink(DeviceReportEnum.VEHICLE_SORT);
		}
		
		public TextLink imeiSort(){
			return new TextLink(DeviceReportEnum.IMEI_SORT);
		}
		
		public TextLink devicePhoneSort(){
			return new TextLink(DeviceReportEnum.PHONE_SORT);
		}

	}
	
	public DeviceReportsTexts _text(){
		return new DeviceReportsTexts();
	}
	
	public class DeviceReportsTexts extends ReportsBarTexts{
		
		public Text statusColumnHeader(){
			return new Text(DeviceReportEnum.STATUS_NONSORT);
		}
		
		public TextTable deviceIDValue(){
			return new TextTable(DeviceReportEnum.DEVICE_ID_VALUE);
		}
		
		public TextTable imeiValue(){
			return new TextTable(DeviceReportEnum.IMEI_VALUE);
		}
		
		public TextTable phoneNumberValue(){
			return new TextTable(DeviceReportEnum.PHONE_VALUE);
		}
		
		public TextTable statusValue(){
			return new TextTable(DeviceReportEnum.STATUS_VALUE);
		}
		
		public Text counter(){
			return new Text(ReportsBarEnum.COUNTER, page);
		}
	}
	
	public DeviceReportsButtons _button(){
		return new DeviceReportsButtons();
	}
	
	public class DeviceReportsButtons extends ReportsBarButtons{
		
		public TextButton editColumns() {
			return new TextButton(PopUpEnum.EDIT_COLUMNS, page);
		}

		public TextButton exportEmail() {
			return new TextButton(ReportsBarEnum.TOOL_EMAIL, page);
		}

		public TextButton exportExcel() {
			return new TextButton(ReportsBarEnum.TOOL_EXCEL, page);
		}

		public TextButton exportPDF() {
			return new TextButton(ReportsBarEnum.TOOL_PDF, page);
		}

		public Button tools() {
			return new Button(PopUpEnum.TOOL_BUTTON, page);
		}
		
	}
	
	public DeviceReportsTextFields _textField(){
		return new DeviceReportsTextFields();
	}
	
	public class DeviceReportsTextFields extends ReportsBarTextFields{
		
		public TextField deviceIDSearch(){
			return new TextField(DeviceReportEnum.DEVICE_ID_SEARCH);
		}
		
		public TextField assignedVehicleSearch(){
			return new TextField(DeviceReportEnum.VEHICLE_SEARCH);
		}
		
		public TextField imeiSearch(){
			return new TextField(DeviceReportEnum.IMEI_SEARCH);
		}
		
		public TextField devicePhoneNumberSearch(){
			return new TextField(DeviceReportEnum.PHONE_SEARCH);
		}		
	}
    @Override
    public String getExpectedPath() {
        return DeviceReportEnum.DEFAULT_URL.getURL();
    }
}
