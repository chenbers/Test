package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.Button;
import com.inthinc.pro.automation.elements.DHXDropDown;
import com.inthinc.pro.automation.elements.Text;
import com.inthinc.pro.automation.elements.TextButton;
import com.inthinc.pro.automation.elements.TextField;
import com.inthinc.pro.automation.elements.TextLink;
import com.inthinc.pro.automation.elements.TextTable;
import com.inthinc.pro.automation.elements.TextTableLink;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.selenium.pageEnums.DeviceReportEnum;
import com.inthinc.pro.selenium.pageEnums.PopUpEnum;
import com.inthinc.pro.selenium.pageEnums.ReportsBarEnum;
import com.inthinc.pro.selenium.pageEnums.ReportsDriversEnum;

public class PageReportsDevices extends ReportsBar {
	private String page = "devices";
	public PageReportsDevices() {
//		checkMe.add(e) //TODO: add verifyOnPage elements.
	}

	public class DeviceReportPopUps extends MastheadPopUps{
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
		
		public DHXDropDown status() {
			return new DHXDropDown(DeviceReportEnum.STATUS_DHX);
		}
	}
	public DeviceReportsLinks _link(){
		return new DeviceReportsLinks();
	}
	
	public class DeviceReportsLinks extends ReportsBarLinks{
		
	    public TextLink editColumns() {
	        return new TextLink(PopUpEnum.EDIT_COLUMNS, page);
	    }
	    
		public TextTableLink vehicleValue(){
			return new TextTableLink(ReportsDriversEnum.VEHICLE_VALUE);
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
	
	public class DeviceReportsPager{
        public Paging pageIndex(){
            return new Paging();
        }
    }
    
    
    public DeviceReportsPager _page(){
        return new DeviceReportsPager();
    }

    @Override
    public SeleniumEnums setUrl() {
        return DeviceReportEnum.DEFAULT_URL;
    }

    @Override
    protected boolean checkIsOnPage() {
        return _link().editColumns().isPresent();
    }
}
