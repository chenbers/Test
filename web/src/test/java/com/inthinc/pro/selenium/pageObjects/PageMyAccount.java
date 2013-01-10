package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.Text;
import com.inthinc.pro.automation.elements.TextButton;
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

    public class MyAccountTablesTexts extends MyAccountTexts {
        
        public Text mapText() {
            return new Text(MyAccountEnum.MAP_LAYERS_TEXT);
        }
        
    }
    
	public class MyAccountButtons extends NavigationBarButtons {

		public TextButton changePassword() {
			return new TextButton(MyAccountEnum.CHANGE_PASSWORD_BUTTON);
		}

		public TextButton edit() {
			return new TextButton(MyAccountEnum.EDIT_BUTTON);
		}
	}

	public class MyAccountTexts extends NavigationBarTexts {

		public Text emailOne() {
			return new Text(MyAccountEnum.EMAIL1_TEXT);
		}

		public Text EmailTwo() {
			return new Text(MyAccountEnum.EMAIL2_TEXT);
		}

		public Text phoneOne() {
			return new Text(MyAccountEnum.PHONE1_TEXT);
		}

		public Text phoneTwo() {
			return new Text(MyAccountEnum.PHONE2_TEXT);
		}

		public Text textMessageOne() {
			return new Text(MyAccountEnum.TEXT_MESSAGES1_TEXT);
		}

		public Text textMessageTwo() {
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

		public Text fuelEfficiencyRatio() {
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

		public Text labelRedFlagCritical() {
			return new TextLabel(MyAccountEnum.CRITICAL_TEXT);
		}

		public Text labelRedFlagWarning() {
			return new TextLabel(MyAccountEnum.WARNING_TEXT);
		}

		public Text labelRedFlagInfo() {
			return new TextLabel(MyAccountEnum.INFORMATION_TEXT);
		}

		public Text labelEmailOne() {
			return new TextLabel(MyAccountEnum.EMAIL1_TEXT);
		}

		public Text labelEmailTwo() {
			return new TextLabel(MyAccountEnum.EMAIL2_TEXT);
		}

		public Text labelPhoneOne() {
			return new TextLabel(MyAccountEnum.PHONE1_TEXT);
		}

		public Text labelPhoneTwo() {
			return new TextLabel(MyAccountEnum.PHONE2_TEXT);
		}

		public Text labelTextMessageOne() {
			return new TextLabel(MyAccountEnum.TEXT_MESSAGES1_TEXT);
		}

		public Text labelTextMessageTwo() {
			return new TextLabel(MyAccountEnum.TEXT_MESSAGES2_TEXT);
		}

		public Text labelFuelEfficiencyRatio() {
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
	
    public MyAccountTablesCheckBoxes _checkBox() {
        return new MyAccountTablesCheckBoxes();
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

	public class MyAccountLinks extends NavigationBarLinks {}

	public class MyAccountTextFields extends NavigationBarTextFields {}
	
    public class MyAccountTablesCheckBoxes {}
    
	public class MyAccountDropDowns {}

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
               (_text().mapType().isPresent() && _text().mapLayers().isPresent());
    }

}
