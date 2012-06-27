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
import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.selenium.pageEnums.PopUpEnum;
import com.inthinc.pro.selenium.pageEnums.ReportsBarEnum;
import com.inthinc.pro.selenium.pageEnums.ReportsVehiclesEnum;

public class PageReportsVehicles extends ReportsBar {
	
	public PageReportsVehicles(){
		checkMe.add(ReportsVehiclesEnum.TITLE);
	}
	
	public class VehicleReportButtons extends ReportsBarButtons {

		public TextButton emailReport() {
			return new TextButton(ReportsBarEnum.TOOL_EMAIL, page);
		}

		public TextButton exportExcel() {
			return new TextButton(ReportsBarEnum.TOOL_EXCEL, page);
		}

		public TextButton exportPdf() {
			return new TextButton(ReportsBarEnum.TOOL_PDF, page);
		}

		public Button tools() {
			return new Button(PopUpEnum.TOOL_BUTTON, page);
		}
	}

	public class VehicleReportDropDowns extends ReportsBarDropDowns {

		public DHXDropDown overallFilter() {
			return new ReportsBarDropDowns().score(ReportsBarEnum.OVERALL_SCORE_DHX, page);
		}

		public DHXDropDown speedFilter() {
			return new ReportsBarDropDowns().score(ReportsBarEnum.SPEED_SCORE_DHX, page);
		}

		public DHXDropDown styleFilter() {
			return new ReportsBarDropDowns().score(ReportsBarEnum.STYLE_SCORE_DHX, page);
		}

	}

	public class VehicleReportLinks extends ReportsBarLinks {
	    
	    public TextLink editColumns() {
	        return new TextLink(PopUpEnum.EDIT_COLUMNS, page);
	    }

		public TextLink distanceDrivenSort() {
			return new TextLink(ReportsVehiclesEnum.DISTANCE_DRIVEN_SORT);
		}

		public TextLink driverSort() {
			return new TextLink(ReportsVehiclesEnum.DRIVER_SORT);
		}

		public TextLink driverValue() {
			return new TextLink(ReportsVehiclesEnum.DRIVER_VALUE);
		}

		public TextLink groupSort() {
			return new TextLink(ReportsVehiclesEnum.GROUP_SORT);
		}

		public TextTableLink groupValue() {
			return new TextTableLink(ReportsVehiclesEnum.GROUP_VALUE);
		}

		public TextLink odometerSort() {
			return new TextLink(ReportsVehiclesEnum.ODOMETER_SORT);
		}

		public TextLink overallSort() {
			return new TextLink(ReportsVehiclesEnum.OVERALL_SCORE_SORT);
		}

		public TextLinkScoreTable overallValue() {
			return new TextLinkScoreTable(ReportsVehiclesEnum.OVERALL_SCORE_VALUE);
		}

		public TextLink speedSort() {
			return new TextLink(ReportsVehiclesEnum.SPEED_SCORE_SORT);
		}

		public TextLinkScoreTable speedValue() {
			return new TextLinkScoreTable(ReportsVehiclesEnum.SPEED_SCORE_VALUE);
		}

		public TextLink styleSort() {
			return new TextLink(ReportsVehiclesEnum.STYLE_SCORE_SORT);
		}

		public TextLinkScoreTable styleValue() {
			return new TextLinkScoreTable(ReportsVehiclesEnum.STYLE_SCORE_VALUE);
		}

		public TextLink vehicleIDSort() {
			return new TextLink(ReportsVehiclesEnum.VEHICLE_SORT);
		}

		public TextTableLink vehicleValue() {
			return new TextTableLink(ReportsVehiclesEnum.VEHICLE_VALUE);
		}

		public TextLink yearMakeModelSort() {
			return new TextLink(ReportsVehiclesEnum.YEAR_MAKE_MODEL);
		}

	}

	public class VehicleReportTextFields extends ReportsBarTextFields {

		public TextField driverSearch() {
			return new TextField(ReportsVehiclesEnum.DRIVER_SEARCH);
		}

		public TextField groupSearch() {
			return new TextField(ReportsVehiclesEnum.GROUP_SEARCH);
		}

		public TextField vehicleSearch() {
			return new TextField(ReportsVehiclesEnum.VEHICLE_SEARCH);
		}

		public TextField yearMakeModelSearch() {
			return new TextField(ReportsVehiclesEnum.YEAR_MAKE_MODEL_SEARCH);
		}
	}

	public class VehicleReportTexts extends ReportsBarTexts {
		
		public Text title(){
			return new Text(ReportsVehiclesEnum.TITLE);
		}
		
		public Text counter(){
			return new Text(ReportsBarEnum.COUNTER, page);
		}
		
		public TextTable distanceDrivenValue() {
			return new TextTable(ReportsVehiclesEnum.DISTANCE_DRIVEN_VALUE);
		}
		
		public TextTable odometerValue() {
			return new TextTable(ReportsVehiclesEnum.ODOMETER_VALUE);
		}

		public TextTable yearMakeModelValue() {
			return new TextTable(ReportsVehiclesEnum.YEAR_MAKE_MODEL_VALUE);
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

    @Override
    public SeleniumEnums setUrl() {
        return ReportsVehiclesEnum.DEFAULT_URL;
    }

    @Override
    protected boolean checkIsOnPage() {
        return _link().editColumns().isPresent();
    }
}