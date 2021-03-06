package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.Button;
import com.inthinc.pro.automation.elements.Text;
import com.inthinc.pro.automation.elements.TextLink;
import com.inthinc.pro.automation.elements.TextTable;
import com.inthinc.pro.automation.elements.TextTableLink;
import com.inthinc.pro.automation.enums.ErrorLevel;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.automation.selenium.AbstractPage;
import com.inthinc.pro.selenium.pageEnums.AdminVehicleDetailsEnum;
import com.inthinc.pro.selenium.pageEnums.DriverPerformanceEnum;
import com.inthinc.pro.selenium.pageEnums.DriverPerformanceStyleEnum;
import com.inthinc.pro.selenium.pageEnums.PerformanceEnum;
import com.inthinc.pro.selenium.pageEnums.TAE.TimeDuration;

public class PageDriverPerformanceStyle extends NavigationBar {
	private static String page2 = "Style";

	
	public PageDriverPerformanceStyle(){
		checkMe.add(DriverPerformanceStyleEnum.BREAKDOWN_HARD_ACCEL_LINK);
		checkMe.add(DriverPerformanceStyleEnum.BREAKDOWN_HARD_ACCEL_SCORE);
		checkMe.add(DriverPerformanceStyleEnum.BREAKDOWN_HARD_BRAKE_LINK);
		checkMe.add(DriverPerformanceStyleEnum.BREAKDOWN_HARD_BUMP_LINK);
		
	}
	public class DriverStyleButtons extends NavigationBarButtons {

		public Button emailReport() {
			return new Button(DriverPerformanceStyleEnum.OVERALL_EMAIL_TOOL);
		}

		public Button exportToExcel() {
			return new Button(DriverPerformanceStyleEnum.OVERALL_EXCEL_TOOL);
		}

		public Button exportToPDF() {
			return new Button(DriverPerformanceStyleEnum.OVERALL_PDF_TOOL);
		}

		public Button returnToPerformancePage() {
			return new Button(DriverPerformanceStyleEnum.RETURN);
		}

		public Button tools() {
			return new Button(DriverPerformanceStyleEnum.OVERALL_TOOLS);
		}
	}

	public class DriverStyleDropDowns extends NavigationBarDropDowns {
	}

	public class DriverStyleLinks extends NavigationBarLinks {
		public TextLink breadCrumb(Integer position) {
			return new TextLink(DriverPerformanceEnum.EXPANDED_BREADCRUMB,
					"Style", position);
		}

		public TextLink dateTimeSort() {
			return new TextLink(DriverPerformanceStyleEnum.DATE_TIME_HEADER);
		}

		public TextLink driverName() {
			return new TextLink(
					DriverPerformanceEnum.EXPANDED_DRIVER_NAME_LINK, page2);
		}

		public TextLink duration(TimeDuration duration) {
			return new TextLink(
					DriverPerformanceStyleEnum.OVERALL_TIME_FRAME_SELECTOR,
					duration);
		}

		public TextLink eventSort() {
			return new TextLink(DriverPerformanceStyleEnum.EVENT_HEADER);
		}

		public TextTableLink exclude() {
			return new TextTableLink(DriverPerformanceStyleEnum.EXCLUDE, page2);
		}

		public TextLink hardAccel() {
			return new TextLink(
					DriverPerformanceStyleEnum.BREAKDOWN_HARD_ACCEL_LINK);
		}

		public TextLink hardBrake() {
			return new TextLink(
					DriverPerformanceStyleEnum.BREAKDOWN_HARD_BRAKE_LINK);
		}

		public TextLink hardBump() {
			return new TextLink(
					DriverPerformanceStyleEnum.BREAKDOWN_HARD_BUMP_LINK);
		}

		public TextTableLink include() {
			return new TextTableLink(DriverPerformanceStyleEnum.INCLUDE, page2);
		}

		public TextTableLink location() {
			return new TextTableLink(DriverPerformanceStyleEnum.LOCATION_ENTRY);
		}

		public TextLink overallChart() {
			return new TextLink(
					DriverPerformanceStyleEnum.BREAKDOWN_OVERALL_LINK);
		}

		public TextLink severitySort() {
			return new TextLink(DriverPerformanceStyleEnum.SEVERITY_HEADER);
		}

		public TextLink speedSort() {
			return new TextLink(DriverPerformanceStyleEnum.SPEED_HEADER);
		}

		public TextLink unsafeTurn() {
			return new TextLink(
					DriverPerformanceStyleEnum.BREAKDOWN_UNSAFE_TURN_LINK);
		}
	}

	public class DriverStylePopUps extends MastheadPopUps {
		public DriverStylePopUps() {
			super("style", Types.SINGLE, 3);
		}

		public Email emailReport() {
			return new Email();
		}

	}

	public class DriverStyleTextFields extends NavigationBarTextFields {
	}

	public class DriverStyleTexts extends NavigationBarTexts {

		public Text counter() {
			return new Text(PerformanceEnum.DETAILS_X_OF_Y);
		}

		public TextTable dateTime() {
			return new TextTable(DriverPerformanceStyleEnum.DATE_TIME_ENTRY);
		}

		public Text drivingStyleScoreLabel() {
			return new Text(DriverPerformanceStyleEnum.OVERALL_SCORE_LABEL);
		}

		public Text drivingStyleScoreValue() {
			return new Text(DriverPerformanceStyleEnum.OVERALL_SCORE_NUMBER);
		}

		public TextTable event() {
			return new TextTable(DriverPerformanceStyleEnum.EVENT_ENTRY);
		}

		public Text hardAccellBreakdownScore() {
			return new Text(
					DriverPerformanceStyleEnum.BREAKDOWN_HARD_ACCEL_SCORE);
		}

		public Text hardBreakBreakdownScore() {
			return new Text(
					DriverPerformanceStyleEnum.BREAKDOWN_HARD_BRAKE_SCORE);
		}

		public Text hardBumpBreakdownScore() {
			return new Text(
					DriverPerformanceStyleEnum.BREAKDOWN_HARD_BUMP_SCORE);
		}

		public Text overallBreakdownScore() {
			return new Text(DriverPerformanceStyleEnum.BREAKDOWN_OVERALL_SCORE);
		}

		public TextTable severity() {
			return new TextTable(
					DriverPerformanceStyleEnum.SEVERITY_ENTRY_SEVERITY);
		}

		public TextTable speed() {
			return new TextTable(DriverPerformanceStyleEnum.SPEED_ENTRY);
		}

		public Text unsafeTurnBreakdownScore() {
			return new Text(
					DriverPerformanceStyleEnum.BREAKDOWN_UNSAFE_TURN_SCORE);
		}
	}

	public DriverStyleButtons _button() {
		return new DriverStyleButtons();
	}

	public DriverStyleDropDowns _dropDown() {
		return new DriverStyleDropDowns();
	}

	public DriverStyleLinks _link() {
		return new DriverStyleLinks();
	}

	public DriverStylePopUps _popUp() {
		return new DriverStylePopUps();
	}

	public DriverStyleTexts _text() {
		return new DriverStyleTexts();
	}

	public DriverStyleTextFields _textField() {
		return new DriverStyleTextFields();
	}
    @Override
    @Deprecated
	/**
	 * PageAdminVehicleView's .load method cannot be used without specifying a vehicleID
	 * 
	 * @deprecated use {@link com.inthinc.pro.selenium.pageObjects.PageAdminVehicleView#load(Integer)}
	 */
    public AbstractPage load(){
    	addError("PageDriverPerformanceStyle.load()", "This page cannot be loaded without more information.  Please supply an (Integer vehicleID)", ErrorLevel.FATAL);
		return null;
    }
    public PageDriverPerformanceStyle load(Integer vehicleID) {
    	open(AdminVehicleDetailsEnum.DEFAULT_URL, vehicleID);
    	return this;
    }
    
    public class DriverStylePager{
        public Paging pageIndex(){
            return new Paging();
        }
    }
    
    
    public DriverStylePager _page(){
        return new DriverStylePager();
    }

    @Override
    public SeleniumEnums setUrl() {
        return DriverPerformanceStyleEnum.DEFAULT_URL;
    }

    @Override
    protected boolean checkIsOnPage() {
        return _link().eventSort().isPresent() && 
               _link().hardAccel().isPresent();
    }
    
}
