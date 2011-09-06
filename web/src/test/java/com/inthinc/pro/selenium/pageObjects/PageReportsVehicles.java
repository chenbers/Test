package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.Button;
import com.inthinc.pro.automation.elements.DHXDropDown;
import com.inthinc.pro.automation.elements.Text;
import com.inthinc.pro.automation.elements.TextButton;
import com.inthinc.pro.automation.elements.TextField;
import com.inthinc.pro.automation.elements.TextLink;
import com.inthinc.pro.automation.elements.TextLinkScoreTable;
import com.inthinc.pro.automation.elements.TextTable;
import com.inthinc.pro.automation.elements.TextTableLink;
import com.inthinc.pro.selenium.pageEnums.PopUpEnum;
import com.inthinc.pro.selenium.pageEnums.ReportsBarEnum;
import com.inthinc.pro.selenium.pageEnums.VehicleReportEnum;
import com.inthinc.pro.selenium.pageObjects.Masthead.Paging;
import com.inthinc.pro.selenium.pageObjects.PageNotificationsEmergency.NotificationsEmergencyPager;

public class PageReportsVehicles extends ReportsBar {
	
	public PageReportsVehicles(){
		url = VehicleReportEnum.DEFAULT_URL;
		checkMe.add(VehicleReportEnum.TITLE);
	}
	
	public class VehicleReportButtons extends ReportsBarButtons {

		public TextButton editColumns() {
			return new TextButton(PopUpEnum.EDIT_COLUMNS, page);
		}

		public TextButton emailReport() {
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

	public class VehicleReportDropDowns extends ReportsBarDropDowns {

		public DHXDropDown overallFilter() {
			return new ReportsBarDropDowns().score(
					ReportsBarEnum.OVERALL_SCORE_DHX, page);
		}

		public DHXDropDown speedFilter() {
			return new ReportsBarDropDowns().score(
					ReportsBarEnum.SPEED_SCORE_DHX, page);
		}

		public DHXDropDown styleFilter() {
			return new ReportsBarDropDowns().score(
					ReportsBarEnum.STYLE_SCORE_DHX, page);
		}

	}

	public class VehicleReportLinks extends ReportsBarLinks {

		public TextLink distanceDrivenSort() {
			return new TextLink(VehicleReportEnum.DISTANCE_DRIVEN_SORT);
		}

		public TextLink driverSort() {
			return new TextLink(VehicleReportEnum.DRIVER_SORT);
		}

		public TextTableLink driverValue() {
			return new TextTableLink(VehicleReportEnum.DRIVER_VALUE);
		}

		public TextLink groupSort() {
			return new TextLink(VehicleReportEnum.GROUP_SORT);
		}

		public TextTableLink groupValue() {
			return new TextTableLink(VehicleReportEnum.GROUP_VALUE);
		}

		public TextLink odometerSort() {
			return new TextLink(VehicleReportEnum.ODOMETER_SORT);
		}


		public TextLink overallSort() {
			return new TextLink(VehicleReportEnum.OVERALL_SCORE_SORT);
		}

		public TextLinkScoreTable overallValue() {
			return new TextLinkScoreTable(VehicleReportEnum.OVERALL_SCORE_VALUE);
		}

		public TextLink speedSort() {
			return new TextLink(VehicleReportEnum.SPEED_SCORE_SORT);
		}

		public TextLinkScoreTable speedValue() {
			return new TextLinkScoreTable(VehicleReportEnum.SPEED_SCORE_VALUE);
		}

		public TextLink styleSort() {
			return new TextLink(VehicleReportEnum.STYLE_SCORE_SORT);
		}

		public TextLinkScoreTable styleValue() {
			return new TextLinkScoreTable(VehicleReportEnum.STYLE_SCORE_VALUE);
		}

		public TextLink vehicleIDSort() {
			return new TextLink(VehicleReportEnum.VEHICLE_SORT);
		}

		public TextTableLink vehicleValue() {
			return new TextTableLink(VehicleReportEnum.VEHICLE_VALUE);
		}

		public TextLink yearMakeModelSort() {
			return new TextLink(VehicleReportEnum.YEAR_MAKE_MODEL_SORT);
		}

	}

	public class VehicleReportTextFields extends ReportsBarTextFields {

		public TextField driverSearch() {
			return new TextField(VehicleReportEnum.DRIVER_SEARCH);
		}

		public TextField groupSearch() {
			return new TextField(VehicleReportEnum.GROUP_SEARCH);
		}

		public TextField vehicleSearch() {
			return new TextField(VehicleReportEnum.VEHICLE_SEARCH);
		}

		public TextField yearMakeModelSearch() {
			return new TextField(VehicleReportEnum.YEAR_MAKE_MODEL_SEARCH);
		}
	}

	public class VehicleReportTexts extends ReportsBarTexts {
		
		public Text title(){
			return new Text(VehicleReportEnum.TITLE);
		}
		
		public Text counter(){
			return new Text(ReportsBarEnum.COUNTER, page);
		}
		
		public TextTable distanceDrivenValue() {
			return new TextTable(VehicleReportEnum.DISTANCE_DRIVEN_VALUE);
		}
		
		public TextTable odometerValue() {
			return new TextTable(VehicleReportEnum.ODOMETER_VALUE);
		}

		public TextTable yearMakeModelValue() {
			return new TextTable(VehicleReportEnum.YEAR_MAKE_MODEL_VALUE);
		}

	}

	private final String page = "vehicles";

	public VehicleReportButtons _button() {
		return new VehicleReportButtons();
	}

	public VehicleReportDropDowns _dropDown() {
		return new VehicleReportDropDowns();
	}

	public VehicleReportLinks _link() {
		return new VehicleReportLinks();
	}

	public class VehicleReportPopUps extends MastheadPopUps{
    	public VehicleReportPopUps(){
    		super(page, Types.REPORT, 3);
    	}
    	
    	public Email emailReport(){
    		return new Email();
    	}
    	
    	public EditColumns editColumns(){
    		return new EditColumns();
    	}
    }

	public VehicleReportPopUps _popUp() {
		return new VehicleReportPopUps();
	}

	public VehicleReportTexts _text() {
		return new VehicleReportTexts();
	}

	public VehicleReportTextFields _textField() {
		return new VehicleReportTextFields();
	}
	
	public VehicleReportPager _page() {
        return new VehicleReportPager();
    }

    public class VehicleReportPager {

        public Paging pageIndex() {
            return new Paging();
        }
    }
}
