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
import com.inthinc.pro.selenium.pageEnums.MyAccountEditEnum;

public class PageMyAccountEdit extends NavigationBar {
	public PageMyAccountEdit() {
	}
	public class MyAccountEditPopUps extends MastheadPopUps{}
	
    public class MyAccountEditTablesCheckBoxes {

        public CheckBoxTable mapLayers() {
            return new CheckBoxTable(MyAccountEditEnum.MAP_LAYERS_CHECKBOX);
        }
    }
    
    public MyAccountEditTablesCheckBoxes _checkBox() {
        return new MyAccountEditTablesCheckBoxes();
    }    

	public class MyAccountEditTextFields extends NavigationBarTextFields {

		public TextField phoneOne() {
			return new TextField(MyAccountEditEnum.PHONE1_TEXTFIELD);
		}

		public TextField phoneTwo() {
			return new TextField(MyAccountEditEnum.PHONE2_TEXTFIELD);
		}

		public TextField emailOne() {
			return new TextField(MyAccountEditEnum.EMAIL1_TEXTFIELD);
		}

		public TextField EmailTwo() {
			return new TextField(MyAccountEditEnum.EMAIL2_TEXTFIELD);
		}

		public TextField textMessageOne() {
			return new TextField(MyAccountEditEnum.TEXT_MESSAGES1_TEXTFIELD);
		}

		public TextField textMessageTwo() {
			return new TextField(MyAccountEditEnum.TEXT_MESSAGES2_TEXTFIELD);
		}
	}
	
	public class MyAccountEditButtons extends NavigationBarButtons {

		public TextButton save() {
			return new TextButton(MyAccountEditEnum.SAVE_BUTTON);
		}

		public TextButton cancel() {
			return new TextButton(MyAccountEditEnum.CANCEL_BUTTON);
		}
		
		public Button downArrow() {
            return new Button(MyAccountEditEnum.MAP_LAYERS_ARROW);
		}
	}
    
	public class MyAccountEditDropDowns {
		
		public DropDown critical() {
			return new DropDown(MyAccountEditEnum.CRITICAL_DROPDOWN);
		}

		public DropDown information() {
			return new DropDown(MyAccountEditEnum.INFORMATION_DROPDOWN);
		}

		public DropDown warning() {
			return new DropDown(MyAccountEditEnum.WARNING_DROPDOWN);
		}
		
		public DropDown mapType() {
		    return new DropDown(MyAccountEditEnum.MAP_TYPE_DROPDOWN);
		}
		
		public DropDown mapLayers() {
		    return new DropDown(MyAccountEditEnum.MAP_LAYERS_DROPDOWN);
		}

		public DropDown fuelEfficiencyRatio() {
			return new DropDown(MyAccountEditEnum.FUEL_EFFICIENCY_SELECT);
		}

		public DropDown locale() {
			return new DropDown(MyAccountEditEnum.LOCALE_SELECT);
		}

		public DropDown measurement() {
			return new DropDown(MyAccountEditEnum.MEASUREMENT_SELECT);
		}
	}
	
	public class MyAccountEditTexts extends NavigationBarTexts {

		public Text group() {
			return new Text(MyAccountEditEnum.GROUP_TEXT);
		}

		public Text name() {
			return new Text(MyAccountEditEnum.NAME_TEXT);
		}

		public Text team() {
			return new Text(MyAccountEditEnum.TEAM_TEXT);
		}

		public Text userName() {
			return new Text(MyAccountEditEnum.USER_NAME_TEXT);
		}

		public Text errorEmailOne() {
			return new TextFieldError(MyAccountEditEnum.EMAIL1_TEXTFIELD);
		}

		public Text errorEmailTwo() {
			return new TextFieldError(MyAccountEditEnum.EMAIL2_TEXTFIELD);
		}

		public Text errorPhoneOne() {
			return new TextFieldError(MyAccountEditEnum.PHONE1_TEXTFIELD);
		}

		public Text errorPhoneTwo() {
			return new TextFieldError(MyAccountEditEnum.PHONE2_TEXTFIELD);
		}

		public Text errorTextOne() {
			return new TextFieldError(MyAccountEditEnum.TEXT_MESSAGES1_TEXTFIELD);
		}

		public Text errorText2() {
			return new TextFieldError(MyAccountEditEnum.TEXT_MESSAGES2_TEXTFIELD);
		}
		
		public Text labelTextFieldEmailOne() {
            return new TextFieldLabel(MyAccountEditEnum.EMAIL1_TEXTFIELD);
        }

        public Text labelTextFieldEmailTwo() {
            return new TextFieldLabel(MyAccountEditEnum.EMAIL2_TEXTFIELD);
        }

        public Text labelTextFieldPhoneOne() {
            return new TextFieldLabel(MyAccountEditEnum.PHONE1_TEXTFIELD);
        }

        public Text labelTextFieldPhoneTwo() {
            return new TextFieldLabel(MyAccountEditEnum.PHONE2_TEXTFIELD);
        }

        public Text labelTextFieldTextOne() {
            return new TextFieldLabel(MyAccountEditEnum.TEXT_MESSAGES1_TEXTFIELD);
        }

        public Text labelTextFieldTextTwo() {
            return new TextFieldLabel(MyAccountEditEnum.TEXT_MESSAGES2_TEXTFIELD);
        }

		public Text labelRedFlagCritical() {
			return new TextLabel(MyAccountEditEnum.CRITICAL_LABEL);
		}

		public Text labelRedFlagWarning() {
			return new TextLabel(MyAccountEditEnum.WARNING_LABEL);
		}

		public Text labelRedFlagInfo() {
			return new TextLabel(MyAccountEditEnum.INFORMATION_LABEL);
		}

		public Text labelEmailOne() {
			return new TextLabel(MyAccountEditEnum.EMAIL1_LABEL);
		}

		public Text labelEmailTwo() {
			return new TextLabel(MyAccountEditEnum.EMAIL2_LABEL);
		}

		public Text labelPhoneOne() {
			return new TextLabel(MyAccountEditEnum.PHONE1_LABEL);
		}

		public Text labelPhoneTwo() {
			return new TextLabel(MyAccountEditEnum.PHONE2_LABEL);
		}

		public Text labelTextMessageOne() {
			return new TextLabel(MyAccountEditEnum.TEXT_MESSAGES1_LABEL);
		}

		public Text labelTextMessageTwo() {
			return new TextLabel(MyAccountEditEnum.TEXT_MESSAGES2_LABEL);
		}

		public Text labelFuelEfficiencyRatio() {
			return new TextLabel(MyAccountEditEnum.FUEL_EFFICIENCY_LABEL);
		}

		public Text labelGroup() {
			return new TextLabel(MyAccountEditEnum.GROUP_TEXT);
		}

		public Text labelLocale() {
			return new TextLabel(MyAccountEditEnum.LOCALE_LABEL);
		}

		public Text labelMeasurement() {
			return new TextLabel(MyAccountEditEnum.MEASUREMENT_LABEL);
		}

		public Text labelName() {
			return new TextLabel(MyAccountEditEnum.NAME_TEXT);
		}

		public Text labelTeam() {
			return new TextLabel(MyAccountEditEnum.TEAM_TEXT);
		}

		public Text labelUserName() {
			return new TextLabel(MyAccountEditEnum.USER_NAME_TEXT);
		}

		public Text titleEmailAddresses() {
			return new Text(MyAccountEditEnum.EMAIL_SUB_HEADER);
		}

		public Text titleTextMessages() {
			return new Text(MyAccountEditEnum.TEXT_SUB_HEADER);
		}

		public Text titlePhoneNumbers() {
			return new Text(MyAccountEditEnum.PHONE_SUB_HEADER);
		}

		public Text titleAccountInformation() {
			return new Text(MyAccountEditEnum.ACCOUNT_HEADER);
		}

		public Text titleContactInformation() {
			return new Text(MyAccountEditEnum.CONTACT_HEADER);
		}

		public Text titleLoginInformation() {
			return new Text(MyAccountEditEnum.LOGIN_HEADER);
		}

		public Text titleAccountMain() {
			return new Text(MyAccountEditEnum.MAIN_TITLE);
		}

		public Text titleRedFlags() {
			return new Text(MyAccountEditEnum.RED_FLAGS_HEADER);
		}
		
		public Text titleMapPreferences() {
		    return new Text(MyAccountEditEnum.MAP_PREFERENCES_HEADER);
		}
	}
    
	public class MyAccountEditLinks extends NavigationBarLinks {}
	
	public MyAccountEditPopUps _popUp(){
		return new MyAccountEditPopUps();
	}
	
	public MyAccountEditTextFields _textField() {
		return new MyAccountEditTextFields();
	}

	public MyAccountEditTexts _text() {
		return new MyAccountEditTexts();
	}

	public MyAccountEditLinks _link() {
		return new MyAccountEditLinks();
	}

	public MyAccountEditButtons _button() {
		return new MyAccountEditButtons();
	}

	public MyAccountEditDropDowns _dropDown() {
		return new MyAccountEditDropDowns();
	}

	@Override
	public String getExpectedPath() {
		return MyAccountEditEnum.MY_ACCOUNT_URL.getURL();
	}

    @Override
    public SeleniumEnums setUrl() {
        return MyAccountEditEnum.MY_ACCOUNT_URL; 
    }

    @Override
    protected boolean checkIsOnPage() {
        return (_textField().emailOne().isPresent() && _textField().phoneOne().isPresent()) || 
               (_button().save().isPresent() && _button().cancel().isPresent());
    }

}
