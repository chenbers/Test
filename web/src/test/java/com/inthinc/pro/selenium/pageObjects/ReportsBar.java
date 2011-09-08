package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.DHXDropDown;
import com.inthinc.pro.automation.elements.TextLink;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;
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

		protected DHXDropDown score(SeleniumEnums DHX, String page) {
			return new DHXDropDown(DHX, page, enums);
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
