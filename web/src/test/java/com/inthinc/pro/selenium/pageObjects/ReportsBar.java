package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.DhxDropDown;
import com.inthinc.pro.automation.elements.TextLink;
import com.inthinc.pro.automation.enums.SeleniumEnums;
import com.inthinc.pro.selenium.pageEnums.ReportsBarEnum;

public abstract class ReportsBar extends NavigationBar {

	protected class ReportsBarTexts extends NavigationBarTexts {
	}

	protected class ReportsBarButtons extends NavigationBarButtons {
	}

	protected class ReportsBarTextFields extends NavigationBarTextFields {
	}

	protected class ReportsBarDropDowns extends NavigationBarDropDowns {

		private SeleniumEnums[] enums = { ReportsBarEnum.SEATBELT_SCORE_DHX,
				ReportsBarEnum.STYLE_SCORE_DHX, ReportsBarEnum.SPEED_SCORE_DHX,
				ReportsBarEnum.OVERALL_SCORE_DHX };

		protected DhxDropDown score(SeleniumEnums DHX, SeleniumEnums arrow,
				String page) {
			return new DhxDropDown(DHX, page, enums).dropDownButton(arrow);
		}
	}

	protected class ReportsBarLinks extends NavigationBarLinks {

		public TextLink drivers() {
			return new TextLink(ReportsBarEnum.DRIVERS);
		}

		public TextLink vehicles() {
			return new TextLink(ReportsBarEnum.VEHICLES);
		}

		public TextLink idling() {
			return new TextLink(ReportsBarEnum.IDLING);
		}

		public TextLink devices() {
			return new TextLink(ReportsBarEnum.DEVICES);
		}

		public TextLink waySmart() {
			return new TextLink(ReportsBarEnum.WAYSMART);
		}
	}

}
