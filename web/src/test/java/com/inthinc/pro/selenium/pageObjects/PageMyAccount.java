package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.DropDown;
import com.inthinc.pro.automation.elements.Text;
import com.inthinc.pro.automation.elements.TextButton;
import com.inthinc.pro.automation.elements.TextField;
import com.inthinc.pro.selenium.pageEnums.MyAccountEnum;

public class PageMyAccount extends NavigationBar {
	
	public class MyAccountPopUps extends MastheadPopUps{
		public MyAccountChangePassword changeMyPassword(){
			return new MyAccountChangePassword();
		}
	}
	
	public MyAccountPopUps _popUp(){
		return new MyAccountPopUps();
	}
	
	public MyAccountTextFields _textField() {
		return new MyAccountTextFields();
	}

	public MyAccountTexts _text() {
		return new MyAccountTexts();
	}

	public MyAccountLinks _link() {
		return new MyAccountLinks();
	}

	public MyAccountButtons _button() {
		return new MyAccountButtons();
	}

	public MyAccountSelects _select() {
		return new MyAccountSelects();
	}

	public class MyAccountLinks extends NavigationBarLinks {

	}

	public class MyAccountTextFields extends NavigationBarTextFields {

		public TextField phone1() {
			return new TextField(MyAccountEnum.PHONE1_TEXTFIELD);
		}

		public TextField phone2() {
			return new TextField(MyAccountEnum.PHONE2_TEXTFIELD);
		}

		public TextField email1() {
			return new TextField(MyAccountEnum.EMAIL1_TEXTFIELD);
		}

		public TextField email2() {
			return new TextField(MyAccountEnum.EMAIL2_TEXTFIELD);
		}

		public TextField textMessage1() {
			return new TextField(MyAccountEnum.TEXT_MESSAGES1_TEXTFIELD);
		}

		public TextField textMessage2() {
			return new TextField(MyAccountEnum.TEXT_MESSAGES2_TEXTFIELD);
		}
	}

	public class MyAccountButtons extends NavigationBarButtons {

		public TextButton change() {
			return new TextButton(MyAccountEnum.CHANGE_PASSWORD_BUTTON);
		}

		public TextButton edit() {
			return new TextButton(MyAccountEnum.EDIT_BUTTON);
		}

		public TextButton save() {
			return new TextButton(MyAccountEnum.SAVE_BUTTON);
		}

		public TextButton cancel() {
			return new TextButton(MyAccountEnum.CANCEL_BUTTON);
		}
	}

	public class MyAccountSelects {
		public DropDown critical() {
			return new DropDown(MyAccountEnum.CRITICAL_SELECT);
		}

		public DropDown information() {
			return new DropDown(MyAccountEnum.INFORMATION_SELECT);
		}

		public DropDown warning() {
			return new DropDown(MyAccountEnum.WARNING_SELECT);
		}

		public DropDown fuelEfficiency() {
			return new DropDown(MyAccountEnum.FUEL_EFFICIENCY_SELECT);
		}

		public DropDown locale() {
			return new DropDown(MyAccountEnum.LOCALE_SELECT);
		}

		public DropDown measurement() {
			return new DropDown(MyAccountEnum.MEASUREMENT_SELECT);
		}
	}

	public class MyAccountTexts extends NavigationBarTexts {

		public Text email1() {
			return new Text(MyAccountEnum.EMAIL1_TEXT);
		}

		public Text email2() {
			return new Text(MyAccountEnum.EMAIL2_TEXT);
		}

		public Text phone1() {
			return new Text(MyAccountEnum.PHONE1_TEXT);
		}

		public Text phone2() {
			return new Text(MyAccountEnum.PHONE2_TEXT);
		}

		public Text textMessage1() {
			return new Text(MyAccountEnum.TEXT_MESSAGES1_TEXT);
		}

		public Text textMessage2() {
			return new Text(MyAccountEnum.TEXT_MESSAGES2_TEXT);
		}

		public Text redFlagCritical() {
			return new Text(MyAccountEnum.CRITICAL_TEXT);
		}

		public Text redFlagWarn() {
			return new Text(MyAccountEnum.WARNING_TEXT);
		}

		public Text redFlagInfo() {
			return new Text(MyAccountEnum.INFORMATION_TEXT);
		}

		public Text fuelEfficiency() {
			return new Text(MyAccountEnum.FUEL_EFFICIENCY_RATIO_TEXT);
		}

		public Text group() {
			return new Text(MyAccountEnum.GROUP_TEXT);
		}

		public Text locale() {
			return new Text(MyAccountEnum.LOCALE_TEXT);
		}

		public Text measurement() {
			return new Text(MyAccountEnum.MEASUREMENT_TEXT);
		}

		public Text name() {
			return new Text(MyAccountEnum.NAME_TEXT);
		}

		public Text team() {
			return new Text(MyAccountEnum.TEAM_TEXT);
		}

		public Text userName() {
			return new Text(MyAccountEnum.USER_NAME_TEXT);
		}

		public Text errorEmail1() {
			return new Text(MyAccountEnum.EMAIL1_ERROR);
		}

		public Text errorEmail2() {
			return new Text(MyAccountEnum.EMAIL2_ERROR);
		}


		public Text errorPhone1() {
			return new Text(MyAccountEnum.PHONE1_ERROR);
		}

		public Text errorPhone2() {
			return new Text(MyAccountEnum.PHONE2_ERROR);
		}

		public Text errorText1() {
			return new Text(MyAccountEnum.TEXT1_ERROR);
		}

		public Text errorText2() {
			return new Text(MyAccountEnum.TEXT2_ERROR);
		}

		public Text labelRedFlagCritical() {
			return new Text(MyAccountEnum.CRITICAL_TITLE);
		}

		public Text labelRedFlagWarning() {
			return new Text(MyAccountEnum.WARNING_TITLE);
		}

		public Text labelRedFlagInfo() {
			return new Text(MyAccountEnum.INFORMATION_TITLE);
		}

		public Text labelEmail1() {
			return new Text(MyAccountEnum.EMAIL1_TITLE);
		}

		public Text labelEmail2() {
			return new Text(MyAccountEnum.EMAIL2_TITLE);
		}

		public Text labelPhone1() {
			return new Text(MyAccountEnum.PHONE1_TITLE);
		}

		public Text labelPhone2() {
			return new Text(MyAccountEnum.PHONE2_TITLE);
		}

		public Text labelTextMessage1() {
			return new Text(MyAccountEnum.TEXT_MESSAGES1_TITLE);
		}

		public Text labelTextMessage2() {
			return new Text(MyAccountEnum.TEXT_MESSAGES2_TITLE);
		}

		public Text labelFuelEfficiency() {
			return new Text(MyAccountEnum.FUEL_EFFICIENCY_RATIO_TITLE);
		}

		public Text labelGroup() {
			return new Text(MyAccountEnum.GROUP_TITLE);
		}

		public Text labelLocale() {
			return new Text(MyAccountEnum.LOCALE_TITLE);
		}

		public Text labelMeasurement() {
			return new Text(MyAccountEnum.MEASUREMENT_TITLE);
		}

		public Text labelName() {
			return new Text(MyAccountEnum.NAME_TITLE);
		}

		public Text labelTeam() {
			return new Text(MyAccountEnum.TEAM_TITLE);
		}

		public Text labelUserName() {
			return new Text(MyAccountEnum.USER_NAME_TITLE);
		}

		public Text titleEmailAddresses() {
			return new Text(MyAccountEnum.EMAIL_TITLE);
		}

		public Text titleTextMessages() {
			return new Text(MyAccountEnum.TEXT_TITLE);
		}

		public Text titlePhoneNumbers() {
			return new Text(MyAccountEnum.PHONE_TITLE);
		}

		public Text titleAccountInfo() {
			return new Text(MyAccountEnum.ACCOUNT_TITLE);
		}

		public Text titleContactInfo() {
			return new Text(MyAccountEnum.CONTACT_TITLE);
		}

		public Text titleLoginInfo() {
			return new Text(MyAccountEnum.LOGIN_TITLE);
		}

		public Text titleAccountMain() {
			return new Text(MyAccountEnum.MAIN_TITLE);
		}

		public Text titleRedFlags() {
			return new Text(MyAccountEnum.RED_FLAGS_TITLE);
		}
		
		public Text infoMessage(){
			return new Text(MyAccountEnum.MESSAGE);
		}

	}

	public PageMyAccount page_titlesAndLabels_validate() {
		/* Buttons on the main page */
//		assertEquals(selenium.getText(MyAccountEnum.CHANGE_PASSWORD_BUTTON), //TODO: dtanner: selenium shouldn't be called from page
//				MyAccountEnum.CHANGE_PASSWORD_BUTTON);
//		assertEquals(selenium.getText(MyAccountEnum.EDIT_BUTTON),
//				MyAccountEnum.EDIT_BUTTON);

		/* Titles for the separate sections */
		_text().titleAccountMain().validate();
		_text().titleLoginInfo().validate();
		_text().titleAccountInfo().validate();
		_text().titleRedFlags().validate();
		_text().titleContactInfo().validate();

		/* Labels for the seperate rows */
		/* Account Information */
		_text().labelName().validate();
		_text().labelGroup().validate();
		_text().labelTeam().validate();

		/* Login Information */
		_text().labelUserName().validate();
		_text().labelLocale().validate();
		_text().labelMeasurement().validate();
		_text().labelFuelEfficiency().validate();

		/* Red Flag Preferences */
		_text().labelRedFlagInfo().validate();
		_text().labelRedFlagWarning().validate();
		_text().labelRedFlagCritical().validate();

		/* Contact Information */
		_text().titleEmailAddresses().validate();
		_text().labelEmail1().validate();
		_text().labelEmail2().validate();

		_text().titlePhoneNumbers().validate();
		_text().labelPhone1().validate();
		_text().labelPhone2().validate();

		_text().titleTextMessages().validate();
		_text().labelTextMessage1().validate();
		_text().labelTextMessage2().validate();

		return this;
	}

	public PageMyAccount validate() {
		page_titlesAndLabels_validate();
		return this;
	}

	public String getExpectedPath() {
		return MyAccountEnum.MY_ACCOUNT_URL.getURL();
	}

}
