package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.Button;
import com.inthinc.pro.automation.elements.DhxDropDown;
import com.inthinc.pro.automation.elements.TableText;
import com.inthinc.pro.automation.elements.TableTextLink;
import com.inthinc.pro.automation.elements.Text;
import com.inthinc.pro.automation.elements.TextButton;
import com.inthinc.pro.automation.elements.TextField;
import com.inthinc.pro.automation.elements.TextLink;
import com.inthinc.pro.selenium.pageEnums.AdminAddEditUserEnum;
import com.inthinc.pro.selenium.pageEnums.PopUpEnum;
import com.inthinc.pro.selenium.pageEnums.ReportsBarEnum;
import com.inthinc.pro.selenium.pageEnums.VehicleReportEnum;

public class PageVehicleReport extends ReportsBar {
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

		public DhxDropDown overallFilter() {
			return new ReportsBarDropDowns().score(
					ReportsBarEnum.OVERALL_SCORE_DHX, page);
		}

		public DhxDropDown speedFilter() {
			return new ReportsBarDropDowns().score(
					ReportsBarEnum.SPEED_SCORE_DHX, page);
		}

		public DhxDropDown styleFilter() {
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

		public TableTextLink driverValue() {
			return new TableTextLink(VehicleReportEnum.DRIVER_VALUE);
		}

		public TextLink groupSort() {
			return new TextLink(VehicleReportEnum.GROUP_SORT);
		}

		public TableTextLink groupValue() {
			return new TableTextLink(VehicleReportEnum.GROUP_VALUE);
		}

		public TextLink odometerSort() {
			return new TextLink(VehicleReportEnum.ODOMETER_SORT);
		}


		public TextLink overallSort() {
			return new TextLink(VehicleReportEnum.OVERALL_SCORE_SORT);
		}

		public TableTextLink overallValue() {
			return new TableTextLink(VehicleReportEnum.OVERALL_SCORE_VALUE);
		}

		public TextLink speedSort() {
			return new TextLink(VehicleReportEnum.SPEED_SCORE_SORT);
		}

		public TableTextLink speedValue() {
			return new TableTextLink(VehicleReportEnum.SPEED_SCORE_VALUE);
		}

		public TextLink styleSort() {
			return new TextLink(VehicleReportEnum.STYLE_SCORE_SORT);
		}

		public TableTextLink styleValue() {
			return new TableTextLink(VehicleReportEnum.STYLE_SCORE_VALUE);
		}

		public TextLink vehicleIDSort() {
			return new TextLink(VehicleReportEnum.VEHICLE_SORT);
		}

		public TableTextLink vehicleValue() {
			return new TableTextLink(VehicleReportEnum.VEHICLE_VALUE);
		}

		public TextLink yearMakeModelSort() {
			return new TextLink(VehicleReportEnum.YEAR_MAKE_MODEL_SORT);
		}

	}

	public class VehicleReportTextFields extends ReportsBarTextFields {

		public TextField driverSearch(String driver) {
			return new TextField(VehicleReportEnum.DRIVER_SEARCH);
		}

		public TextField groupSearch(String driver) {
			return new TextField(VehicleReportEnum.GROUP_SEARCH);
		}

		public TextField vehicleSearch(String driver) {
			return new TextField(VehicleReportEnum.VEHICLE_SEARCH);
		}

		public TextField yearMakeModelSearch(String driver) {
			return new TextField(VehicleReportEnum.YEAR_MAKE_MODEL_SEARCH);
		}
	}

	public class VehicleReportTexts extends ReportsBarTexts {
		
		public Text counter(){
			return new Text(ReportsBarEnum.COUNTER, page);
		}
		
		public TableText distanceDrivenValue() {
			return new TableText(VehicleReportEnum.DISTANCE_DRIVEN_VALUE);
		}
		
		public TableText odometerValue() {
			return new TableText(VehicleReportEnum.ODOMETER_VALUE);
		}

		public TableText yearMakeModelValue() {
			return new TableText(VehicleReportEnum.YEAR_MAKE_MODEL_VALUE);
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

	public class VehicleReportPopUps extends PopUps{
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
    @Override
    public String getExpectedPath() {
        return VehicleReportEnum.DEFAULT_URL.getURL();
    }
}
