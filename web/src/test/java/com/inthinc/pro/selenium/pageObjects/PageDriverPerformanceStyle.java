package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.Button;
import com.inthinc.pro.automation.elements.Text;
import com.inthinc.pro.automation.elements.TextLink;
import com.inthinc.pro.selenium.pageEnums.DriverPerformanceEnum;
import com.inthinc.pro.selenium.pageEnums.DriverPerformanceStyleEnum;
import com.inthinc.pro.selenium.pageEnums.TAE.TimeDuration;

public class PageDriverPerformanceStyle extends NavigationBar {

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
					DriverPerformanceEnum.EXPANDED_DRIVER_NAME_LINK, "Style");
		}

		public TextLink duration(TimeDuration timeFrame) {
			return new TextLink(
					DriverPerformanceStyleEnum.OVERALL_TIME_FRAME_SELECTOR,
					timeFrame);
		}

		public TextLink eventSort() {
			return new TextLink(DriverPerformanceStyleEnum.EVENT_HEADER);
		}

		public TextLink exclude(Integer row) {
			return new TextLink(DriverPerformanceStyleEnum.EXCLUDE, row);
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

		public TextLink include(Integer row) {
			return new TextLink(DriverPerformanceStyleEnum.INCLUDE, row);
		}

		public TextLink location(Integer row) {
			return new TextLink(DriverPerformanceStyleEnum.LOCATION_ENTRY, row);
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

	public class DriverStylePopUps extends PopUps {
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
			return new Text(DriverPerformanceEnum.COUNTER);
		}

		public Text dateTime(Integer row) {
			return new Text(DriverPerformanceStyleEnum.DATE_TIME_ENTRY, row);
		}

		public Text drivingStyleScoreLabel() {
			return new Text(DriverPerformanceStyleEnum.OVERALL_SCORE_LABEL);
		}

		public Text drivingStyleScoreValue() {
			return new Text(DriverPerformanceStyleEnum.OVERALL_SCORE_NUMBER);
		}

		public Text event(Integer row) {
			return new Text(DriverPerformanceStyleEnum.EVENT_ENTRY, row);
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

		public Text severity(Integer row) {
			return new Text(DriverPerformanceStyleEnum.SEVERITY_ENTRY_SEVERITY,
					row);
		}

		public Text speed(Integer row) {
			return new Text(DriverPerformanceStyleEnum.SPEED_ENTRY, row);
		}

		public Text unsafeTurnBreakdownScore() {
			return new Text(
					DriverPerformanceStyleEnum.BREAKDOWN_UNSAFE_TURN_SCORE);
		}
	}

	public DriverStyleLinks _button() {
		return new DriverStyleLinks();
	}

	public DriverStyleLinks _dropDown() {
		return new DriverStyleLinks();
	}

	public DriverStyleLinks _link() {
		return new DriverStyleLinks();
	}

	public DriverStylePopUps _popUp() {
		return new DriverStylePopUps();
	}

	public DriverStyleLinks _text() {
		return new DriverStyleLinks();
	}

	public DriverStyleLinks _textField() {
		return new DriverStyleLinks();
	}

}
