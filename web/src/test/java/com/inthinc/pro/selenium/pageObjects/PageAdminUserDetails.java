package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.Text;
import com.inthinc.pro.automation.elements.TextButton;
import com.inthinc.pro.automation.elements.TextLabel;
import com.inthinc.pro.automation.elements.TextLink;
import com.inthinc.pro.selenium.pageEnums.AdminBarEnum;
import com.inthinc.pro.selenium.pageEnums.AdminUserDetailsEnum;

public class PageAdminUserDetails extends AdminBar {
	public class UserDetailsButtons extends AdminBarButtons {

		public TextButton delete() {
			return new TextButton(AdminBarEnum.DETAILS_DELETE, page);
		}

		public TextButton edit() {
			return new TextButton(AdminBarEnum.EDIT, page);
		}
	}

	public class UserDetailsDropDowns extends AdminBarDropDowns {
	}

	public class UserDetailsLinks extends AdminBarLinks {

		public TextLink backToUsers() {
			return new TextLink(AdminBarEnum.GO_BACK, page);
		}
	}

	public class UserDetailsTextFields extends AdminBarTextFields {
	}

	public class UserDetailsTexts extends AdminBarTexts {

		public TextLabel labels(AdminUserDetailsEnum value) {
			return new TextLabel(value);
		}

		public Text values(AdminUserDetailsEnum value) {
			return new TextLabel(value);
		}

	}

	private String page = "person";

	public UserDetailsButtons _button() {
		return new UserDetailsButtons();
	}

	public UserDetailsDropDowns _dropDown() {
		return new UserDetailsDropDowns();
	}

	public UserDetailsLinks _link() {
		return new UserDetailsLinks();
	}

	public UserDetailsTexts _text() {
		return new UserDetailsTexts();
	}

	public UserDetailsTextFields _textField() {
		return new UserDetailsTextFields();
	}

}
