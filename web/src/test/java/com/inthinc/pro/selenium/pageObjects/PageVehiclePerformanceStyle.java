package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.Button;
import com.inthinc.pro.automation.elements.Text;
import com.inthinc.pro.automation.elements.TextLink;
import com.inthinc.pro.automation.elements.TextTable;
import com.inthinc.pro.automation.elements.TextTableLink;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.selenium.pageEnums.PerformanceEnum;
import com.inthinc.pro.selenium.pageEnums.TAE.TimeDuration;
import com.inthinc.pro.selenium.pageEnums.VehiclePerformanceEnum;
import com.inthinc.pro.selenium.pageEnums.VehiclePerformanceStyleEnum;


public class PageVehiclePerformanceStyle extends NavigationBar {
	private static String page2 = "Style";

	public class VehicleStyleButtons extends NavigationBarButtons {

		public Button emailReport() {
			return new Button(VehiclePerformanceStyleEnum.OVERALL_EMAIL_TOOL);
		}

		public Button exportToExcel() {
			return new Button(VehiclePerformanceStyleEnum.OVERALL_EXCEL_TOOL);
		}

		public Button exportToPDF() {
			return new Button(VehiclePerformanceStyleEnum.OVERALL_PDF_TOOL);
		}

		public Button returnToPerformancePage() {
			return new Button(VehiclePerformanceStyleEnum.RETURN);
		}

		public Button tools() {
			return new Button(VehiclePerformanceStyleEnum.OVERALL_TOOLS);
		}
	}

	public class VehicleStyleDropDowns extends NavigationBarDropDowns {
	}

	public class VehicleStyleLinks extends NavigationBarLinks {
		public TextLink breadCrumb(Integer position) {
			return new TextLink(VehiclePerformanceEnum.EXPANDED_BREADCRUMB,
					"Style", position);
		}

		public TextLink dateTimeSort() {
			return new TextLink(VehiclePerformanceStyleEnum.DATE_TIME_HEADER);
		}

		public TextLink vehicleName() {
			return new TextLink(
					VehiclePerformanceEnum.EXPANDED_VEHICLE_NAME_LINK, page2);
		}

		public TextLink duration(TimeDuration timeFrame) {
			return new TextLink(
					VehiclePerformanceStyleEnum.OVERALL_TIME_FRAME_SELECTOR,
					timeFrame);
		}

		public TextLink eventSort() {
			return new TextLink(VehiclePerformanceStyleEnum.EVENT_HEADER);
		}

		public TextTableLink exclude() {
			return new TextTableLink(VehiclePerformanceEnum.EXCLUDE, page2);
		}

		public TextLink hardAccel() {
			return new TextLink(
					VehiclePerformanceStyleEnum.BREAKDOWN_HARD_ACCEL_LINK);
		}

		public TextLink hardBrake() {
			return new TextLink(
					VehiclePerformanceStyleEnum.BREAKDOWN_HARD_BRAKE_LINK);
		}

		public TextLink hardBump() {
			return new TextLink(
					VehiclePerformanceStyleEnum.BREAKDOWN_HARD_BUMP_LINK);
		}

		public TextTableLink include() {
			return new TextTableLink(VehiclePerformanceEnum.INCLUDE, page2);
		}

		public TextTableLink location() {
			return new TextTableLink(VehiclePerformanceStyleEnum.LOCATION_ENTRY);
		}

		public TextLink overallChart() {
			return new TextLink(
					VehiclePerformanceStyleEnum.BREAKDOWN_OVERALL_LINK);
		}

		public TextLink severitySort() {
			return new TextLink(VehiclePerformanceStyleEnum.SEVERITY_HEADER);
		}

		public TextLink speedSort() {
			return new TextLink(VehiclePerformanceStyleEnum.SPEED_HEADER);
		}

		public TextLink unsafeTurn() {
			return new TextLink(
					VehiclePerformanceStyleEnum.BREAKDOWN_UNSAFE_TURN_LINK);
		}
	}

	public class VehicleStylePopUps extends MastheadPopUps {
		public VehicleStylePopUps() {
			super("style", Types.SINGLE, 3);
		}

		public Email emailReport() {
			return new Email();
		}

	}

	public class VehicleStyleTextFields extends NavigationBarTextFields {
	}

	public class VehicleStyleTexts extends NavigationBarTexts {

		public Text counter() {
			return new Text(PerformanceEnum.DETAILS_X_OF_Y);
		}

		public TextTable dateTime() {
			return new TextTable(VehiclePerformanceStyleEnum.DATE_TIME_ENTRY);
		}

		public Text drivingStyleScoreLabel() {
			return new Text(VehiclePerformanceStyleEnum.OVERALL_SCORE_LABEL);
		}

		public Text drivingStyleScoreValue() {
			return new Text(VehiclePerformanceStyleEnum.OVERALL_SCORE_NUMBER);
		}

		public TextTable event() {
			return new TextTable(VehiclePerformanceStyleEnum.EVENT_ENTRY);
		}

		public Text hardAccellBreakdownScore() {
			return new Text(
					VehiclePerformanceStyleEnum.BREAKDOWN_HARD_ACCEL_SCORE);
		}

		public Text hardBreakBreakdownScore() {
			return new Text(
					VehiclePerformanceStyleEnum.BREAKDOWN_HARD_BRAKE_SCORE);
		}

		public Text hardBumpBreakdownScore() {
			return new Text(
					VehiclePerformanceStyleEnum.BREAKDOWN_HARD_BUMP_SCORE);
		}

		public Text overallBreakdownScore() {
			return new Text(VehiclePerformanceStyleEnum.BREAKDOWN_OVERALL_SCORE);
		}

		public TextTable severity() {
			return new TextTable(
					VehiclePerformanceStyleEnum.SEVERITY_ENTRY_SEVERITY);
		}

		public TextTable speed() {
			return new TextTable(VehiclePerformanceStyleEnum.SPEED_ENTRY);
		}

		public Text unsafeTurnBreakdownScore() {
			return new Text(
					VehiclePerformanceStyleEnum.BREAKDOWN_UNSAFE_TURN_SCORE);
		}
	}

	public VehicleStyleButtons _button() {
		return new VehicleStyleButtons();
	}

	public VehicleStyleDropDowns _dropDown() {
		return new VehicleStyleDropDowns();
	}

	public VehicleStyleLinks _link() {
		return new VehicleStyleLinks();
	}

	public VehicleStylePopUps _popUp() {
		return new VehicleStylePopUps();
	}

	public VehicleStyleTexts _text() {
		return new VehicleStyleTexts();
	}

	public VehicleStyleTextFields _textField() {
		return new VehicleStyleTextFields();
	}
	
	public class VehicleStylePager{
        public Paging pageIndex(){
            return new Paging();
        }
    }
    
    
    public VehicleStylePager _page(){
        return new VehicleStylePager();
    }

    @Override
    public SeleniumEnums setUrl() {
        return VehiclePerformanceStyleEnum.DEFAULT_URL;
    }

    @Override
    protected boolean checkIsOnPage() {
        return _button().emailReport().isPresent() &&
               _link().eventSort().isPresent();
    }
    
}
