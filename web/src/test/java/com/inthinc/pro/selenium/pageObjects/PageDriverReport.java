package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.Button;
import com.inthinc.pro.automation.elements.DhxDropDown;
import com.inthinc.pro.automation.elements.TableText;
import com.inthinc.pro.automation.elements.TableTextLink;
import com.inthinc.pro.automation.elements.Text;
import com.inthinc.pro.automation.elements.TextButton;
import com.inthinc.pro.automation.elements.TextField;
import com.inthinc.pro.automation.elements.TextLink;
import com.inthinc.pro.selenium.pageEnums.DriverReportEnum;
import com.inthinc.pro.selenium.pageEnums.LoginEnum;
import com.inthinc.pro.selenium.pageEnums.PopUpEnum;
import com.inthinc.pro.selenium.pageEnums.ReportsBarEnum;

public class PageDriverReport extends ReportsBar {
	public class DriverReportButtons extends ReportsBarButtons {

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

	public class DriverReportDropDowns extends ReportsBarDropDowns {
		public DhxDropDown overallFilter() {
			return new ReportsBarDropDowns().score(
					ReportsBarEnum.OVERALL_SCORE_DHX, page);
		}

		public DhxDropDown seatBeltFilter() {
			return new ReportsBarDropDowns().score(
					ReportsBarEnum.SEATBELT_SCORE_DHX, page);
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

	public class DriverReportLinks extends ReportsBarLinks {

		public TextLink distanceDrivenSort() {
			return new TextLink(DriverReportEnum.DISTANCE_DRIVEN_SORT);
		}

		public TextLink driverSort() {
			return new TextLink(DriverReportEnum.DRIVER_SORT);
		}

		public TableTextLink driverValue() {
			return new TableTextLink(DriverReportEnum.DRIVER_VALUE);
		}

		public TextLink employeeIDSort() {
			return new TextLink(DriverReportEnum.EMPLOYEE_ID_SORT);
		}

		public TextLink groupSort() {
			return new TextLink(DriverReportEnum.GROUP_SORT);
		}

		public TableTextLink groupValue() {
			return new TableTextLink(DriverReportEnum.GROUP_VALUE);
		}

		public TextLink overallSort() {
			return new TextLink(DriverReportEnum.OVERALL_SCORE_SORT);
		}

		public TableTextLink overallValue() {
			return new TableTextLink(DriverReportEnum.OVERALL_SCORE_VALUE);
		}

		public TextLink seatBeltSort() {
			return new TextLink(DriverReportEnum.SEATBELT_SCORE_SORT);
		}

		public TableTextLink seatbeltValue() {
			return new TableTextLink(DriverReportEnum.SEATBELT_SCORE_VALUE);
		}

		public TextLink speedSort() {
			return new TextLink(DriverReportEnum.SPEED_SCORE_SORT);
		}

		public TableTextLink speedValue() {
			return new TableTextLink(DriverReportEnum.SPEED_SCORE_VALUE);
		}

		public TextLink styleSort() {
			return new TextLink(DriverReportEnum.STYLE_SCORE_SORT);
		}

		public TableTextLink styleValue() {
			return new TableTextLink(DriverReportEnum.STYLE_SCORE_VALUE);
		}

		public TextLink vehicleSort() {
			return new TextLink(DriverReportEnum.VEHICLE_SORT);
		}

		public TableTextLink vehicleValue() {
			return new TableTextLink(DriverReportEnum.VEHICLE_VALUE);
		}
	}

	public class DriverReportTextFields extends ReportsBarTextFields {

		public TextField driverSearch() {
			return new TextField(DriverReportEnum.DRIVER_SEARCH);
		}

		public TextField employeeSearch() {
			return new TextField(DriverReportEnum.EMPLOYEE_SEARCH);
		}

		public TextField groupSearch() {
			return new TextField(DriverReportEnum.GROUP_SEARCH);
		}

		public TextField vehicleSearch() {
			return new TextField(DriverReportEnum.VEHICLE_SEARCH);
		}
	}

	public class DriverReportTexts extends ReportsBarTexts {
		
		public Text counter() {
			return new Text(ReportsBarEnum.COUNTER, page);
		}

		public TableText distanceDriven() {
			return new TableText(DriverReportEnum.DISTANCE_DRIVEN_VALUE);
		}

		public TableText employeeID() {
			return new TableText(DriverReportEnum.EMPLOYEE_ID_VALUE);
		}
	}

	private final String page = "drivers";

	public DriverReportButtons _button() {
		return new DriverReportButtons();
	}

	public DriverReportDropDowns _dropDown() {
		return new DriverReportDropDowns();
	}

	public DriverReportLinks _link() {
		return new DriverReportLinks();
	}
	
	public class DriverReportPopUps extends PopUps{
    	public DriverReportPopUps(){
    		super(page, Types.REPORT, 3);
    	}
    	
    	public Email emailReport(){
    		return new Email();
    	}
    	
    	public EditColumns editColumns(){
    		return new EditColumns();
    	}
    }

	public DriverReportPopUps _popUp() {
		return new DriverReportPopUps();
	}
	
	public DriverReportTexts _text() {
		return new DriverReportTexts();
	}

	public DriverReportTextFields _textField() {
		return new DriverReportTextFields();
	}
	
    @Override
    public String getExpectedPath() {
        return DriverReportEnum.DEFAULT_URL.getURL();
    }
	
}
