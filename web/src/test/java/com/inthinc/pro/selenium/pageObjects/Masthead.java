/****************************************************************************************
 * Purpose: Used to process links selected on the Master Heading screen<br /> 
 * most functions are used regardless of what Main menu item is selected<br />
 * as Master Heading screen does not change.<br /> 
 * 
 */

package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.ElementBase;
import com.inthinc.pro.automation.elements.Text;
import com.inthinc.pro.automation.elements.TextLink;
import com.inthinc.pro.automation.elements.TextLinkNewWindow;
import com.inthinc.pro.automation.selenium.AbstractPage;
import com.inthinc.pro.selenium.pageEnums.MastheadEnum;

public abstract class Masthead extends AbstractPage {

	protected class MastheadButtons {
	}

	protected class MastheadDropDowns {
	}

	protected class MastheadLinks {
		public TextLinkNewWindow help() {
			return new TextLinkNewWindow(MastheadEnum.HELP);
		}

		public TextLinkNewWindow legalNotice() {
			return new TextLinkNewWindow(MastheadEnum.LEGAL_NOTICE);
		}

		public TextLink logout() {
			return new TextLink(MastheadEnum.LOGOUT);
		}

		public TextLink myAccount() {
			return new TextLink(MastheadEnum.MY_ACCOUNT);
		}

		public TextLink myMessages() {
			return new TextLink(MastheadEnum.MY_MESSAGES);
		}

		public TextLinkNewWindow privacyPolicy() {
			return new TextLinkNewWindow(MastheadEnum.PRIVACY_POLICY);
		}

		public TextLinkNewWindow support() {
			return new TextLinkNewWindow(MastheadEnum.SUPPORT);
		}
	}

	protected class MastheadTextFields {
	}

	protected class MastheadTexts {
		public Text copyright() {
			return new Text(MastheadEnum.COPYRIGHT);
		}

		public Text version() {
			return new Text(MastheadEnum.VERSION);
		}
	}

	public class MastheadValidation {
		public void footer() {
			ElementBase test = new ElementBase();
			test.validateElementsPresent(MastheadEnum.COPYRIGHT,
					MastheadEnum.PRIVACY, MastheadEnum.LEGAL,
					MastheadEnum.SUPPORT, MastheadEnum.VERSION);
			test.validateTextMatches(MastheadEnum.COPYRIGHT,
					MastheadEnum.PRIVACY, MastheadEnum.LEGAL,
					MastheadEnum.SUPPORT, MastheadEnum.VERSION);
		}

		public void header() {
			ElementBase test = new ElementBase();
			test.validateElementsPresent(MastheadEnum.LOGO, MastheadEnum.HELP,
					MastheadEnum.MY_MESSAGES, MastheadEnum.MY_ACCOUNT,
					MastheadEnum.LOGOUT);
			test.validateTextMatches(MastheadEnum.LOGO, MastheadEnum.HELP,
					MastheadEnum.MY_MESSAGES, MastheadEnum.MY_ACCOUNT,
					MastheadEnum.LOGOUT);
		}
	}
}
