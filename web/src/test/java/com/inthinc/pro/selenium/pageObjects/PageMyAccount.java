package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.Button;
import com.inthinc.pro.automation.elements.CheckBoxTable;
import com.inthinc.pro.automation.elements.DropDown;
import com.inthinc.pro.automation.elements.Text;
import com.inthinc.pro.automation.elements.TextButton;
import com.inthinc.pro.automation.elements.TextField;
import com.inthinc.pro.automation.elements.TextFieldError;
import com.inthinc.pro.automation.elements.TextFieldLabel;
import com.inthinc.pro.automation.elements.TextLabel;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.selenium.pageEnums.MyAccountEnum;

public class PageMyAccount extends NavigationBar {
	public PageMyAccount() {
	}
	public class MyAccountPopUps extends MastheadPopUps{
		public MyAccountChangePassword changeMyPassword(){
			return new MyAccountChangePassword();
		}
	}
	
    public class MyAccountTablesCheckBoxes {

        public CheckBoxTable mapLayers() {
            return new CheckBoxTable(MyAccountEnum.MAP_LAYERS_CHECKBOX);
        }
    }
    
    public MyAccountTablesCheckBoxes _checkBox() {
        return new MyAccountTablesCheckBoxes();
    }

    public class MyAccountTablesTexts extends MyAccountTexts {
        
        public Text mapText() {
            return new Text(MyAccountEnum.MAP_LAYERS_TEXT);
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

	public MyAccountDropDowns _dropDown() {
		return new MyAccountDropDowns();
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

		public TextButton changePassword() {
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
		
		public Button downArrow() {
            return new Button(MyAccountEnum.MAP_LAYERS_ARROW);
		}
	}

	public class MyAccountDropDowns {
		public DropDown critical() {
			return new DropDown(MyAccountEnum.CRITICAL_SELECT);
		}

		public DropDown information() {
			return new DropDown(MyAccountEnum.INFORMATION_SELECT);
		}

		public DropDown warning() {
			return new DropDown(MyAccountEnum.WARNING_SELECT);
		}
		
		public DropDown mapType() {
		    return new DropDown(MyAccountEnum.MAP_TYPE_SELECTOR);
		}
		
		public DropDown mapLayers() {
		    return new DropDown(MyAccountEnum.MAP_LAYERS_SELECTOR);
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
		
		public Text mapType() {
		    return new Text(MyAccountEnum.MAP_TYPE_TEXT);
		}
		
	    public Text mapLayers() {
	        return new Text(MyAccountEnum.MAP_LAYERS_TEXT);
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
			return new TextFieldError(MyAccountEnum.EMAIL1_TEXTFIELD);
		}

		public Text errorEmail2() {
			return new TextFieldError(MyAccountEnum.EMAIL2_TEXTFIELD);
		}

		public Text errorPhone1() {
			return new TextFieldError(MyAccountEnum.PHONE1_TEXTFIELD);
		}

		public Text errorPhone2() {
			return new TextFieldError(MyAccountEnum.PHONE2_TEXTFIELD);
		}

		public Text errorText1() {
			return new TextFieldError(MyAccountEnum.TEXT_MESSAGES1_TEXTFIELD);
		}

		public Text errorText2() {
			return new TextFieldError(MyAccountEnum.TEXT_MESSAGES2_TEXTFIELD);
		}
		
		public Text labelTextFieldEmail1() {
            return new TextFieldLabel(MyAccountEnum.EMAIL1_TEXTFIELD);
        }

        public Text labelTextFieldEmail2() {
            return new TextFieldLabel(MyAccountEnum.EMAIL2_TEXTFIELD);
        }


        public Text labelTextFieldPhone1() {
            return new TextFieldLabel(MyAccountEnum.PHONE1_TEXTFIELD);
        }

        public Text labelTextFieldPhone2() {
            return new TextFieldLabel(MyAccountEnum.PHONE2_TEXTFIELD);
        }

        public Text labelTextFieldText1() {
            return new TextFieldLabel(MyAccountEnum.TEXT_MESSAGES1_TEXTFIELD);
        }

        public Text labelTextFieldText2() {
            return new TextFieldLabel(MyAccountEnum.TEXT_MESSAGES2_TEXTFIELD);
        }

		public Text labelRedFlagCritical() {
			return new TextLabel(MyAccountEnum.CRITICAL_TEXT);
		}

		public Text labelRedFlagWarning() {
			return new TextLabel(MyAccountEnum.WARNING_TEXT);
		}

		public Text labelRedFlagInfo() {
			return new TextLabel(MyAccountEnum.INFORMATION_TEXT);
		}

		public Text labelEmail1() {
			return new TextLabel(MyAccountEnum.EMAIL1_TEXT);
		}

		public Text labelEmail2() {
			return new TextLabel(MyAccountEnum.EMAIL2_TEXT);
		}

		public Text labelPhone1() {
			return new TextLabel(MyAccountEnum.PHONE1_TEXT);
		}

		public Text labelPhone2() {
			return new TextLabel(MyAccountEnum.PHONE2_TEXT);
		}

		public Text labelTextMessage1() {
			return new TextLabel(MyAccountEnum.TEXT_MESSAGES1_TEXT);
		}

		public Text labelTextMessage2() {
			return new TextLabel(MyAccountEnum.TEXT_MESSAGES2_TEXT);
		}

		public Text labelFuelEfficiency() {
			return new TextLabel(MyAccountEnum.FUEL_EFFICIENCY_RATIO_TEXT);
		}

		public Text labelGroup() {
			return new TextLabel(MyAccountEnum.GROUP_TEXT);
		}

		public Text labelLocale() {
			return new TextLabel(MyAccountEnum.LOCALE_TEXT);
		}

		public Text labelMeasurement() {
			return new TextLabel(MyAccountEnum.MEASUREMENT_TEXT);
		}

		public Text labelName() {
			return new TextLabel(MyAccountEnum.NAME_TEXT);
		}

		public Text labelTeam() {
			return new TextLabel(MyAccountEnum.TEAM_TEXT);
		}

		public Text labelUserName() {
			return new TextLabel(MyAccountEnum.USER_NAME_TEXT);
		}

		public Text titleEmailAddresses() {
			return new Text(MyAccountEnum.EMAIL_SUB_HEADER);
		}

		public Text titleTextMessages() {
			return new Text(MyAccountEnum.TEXT_SUB_HEADER);
		}

		public Text titlePhoneNumbers() {
			return new Text(MyAccountEnum.PHONE_SUB_HEADER);
		}

		public Text titleAccountInfo() {
			return new Text(MyAccountEnum.ACCOUNT_HEADER);
		}

		public Text titleContactInfo() {
			return new Text(MyAccountEnum.CONTACT_HEADER);
		}

		public Text titleLoginInfo() {
			return new Text(MyAccountEnum.LOGIN_HEADER);
		}

		public Text titleAccountMain() {
			return new Text(MyAccountEnum.MAIN_TITLE);
		}

		public Text titleRedFlags() {
			return new Text(MyAccountEnum.RED_FLAGS_HEADER);
		}
		
		public Text titleMapPreferences() {
		    return new Text(MyAccountEnum.MAP_PREFERENCES_HEADER);
		}
		
		public Text infoMessage(){
			return new Text(MyAccountEnum.MESSAGE);
		}

	}

	public PageMyAccount page_titlesAndLabels_validate() {

		/* Titles for the separate sections */
		_text().titleAccountMain().validateTheDefaultValue();
		_text().titleLoginInfo().validateTheDefaultValue();
		_text().titleAccountInfo().validateTheDefaultValue();
		_text().titleRedFlags().validateTheDefaultValue();
		_text().titleMapPreferences().validateTheDefaultValue();
		_text().titleContactInfo().validateTheDefaultValue();

		/* Labels for the seperate rows */
		/* Account Information */
		_text().labelName().validateTheDefaultValue();
		_text().labelGroup().validateTheDefaultValue();
		_text().labelTeam().validateTheDefaultValue();

		/* Login Information */
		_text().labelUserName().validateTheDefaultValue();
		_text().labelLocale().validateTheDefaultValue();
		_text().labelMeasurement().validateTheDefaultValue();
		_text().labelFuelEfficiency().validateTheDefaultValue();

		/* Red Flag Preferences */
		_text().labelRedFlagInfo().validateTheDefaultValue();
		_text().labelRedFlagWarning().validateTheDefaultValue();
		_text().labelRedFlagCritical().validateTheDefaultValue();

		/* Contact Information */
		_text().titleEmailAddresses().validateTheDefaultValue();
		_text().labelEmail1().validateTheDefaultValue();
		_text().labelEmail2().validateTheDefaultValue();

		_text().titlePhoneNumbers().validateTheDefaultValue();
		_text().labelPhone1().validateTheDefaultValue();
		_text().labelPhone2().validateTheDefaultValue();

		_text().titleTextMessages().validateTheDefaultValue();
		_text().labelTextMessage1().validateTheDefaultValue();
		_text().labelTextMessage2().validateTheDefaultValue();

		return this;
	}

	@Override
	public PageMyAccount validate() {
		page_titlesAndLabels_validate();
		return this;
	}

	@Override
	public String getExpectedPath() {
		return MyAccountEnum.MY_ACCOUNT_URL.getURL();
	}

    @Override
    public SeleniumEnums setUrl() {
        return MyAccountEnum.MY_ACCOUNT_URL; 
    }

    @Override
    protected boolean checkIsOnPage() {
        return (_button().edit().isPresent() && _button().changePassword().isPresent()) || 
               (_button().save().isPresent() && _button().cancel().isPresent());
    }

}
