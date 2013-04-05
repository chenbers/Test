package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.Button;
import com.inthinc.pro.automation.elements.TextField;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.selenium.pageEnums.UtilLoginEnum;

public class PageUtilLogin extends AdminBar {
	
    public class UtilLoginCheckBoxes {}
    
	public class UtilLoginLinks {}

	public class UtilLoginTextFields {
		
		public TextField username() {
			return new TextField(UtilLoginEnum.USERNAME);
		}
		
		public TextField password() {
			return new TextField(UtilLoginEnum.PASSWORD);
		}		
	}

	public class UtilLoginButtons {
		
    	public Button login() {
    		return new Button(UtilLoginEnum.LOGIN);
    	}
	}

	public class UtilLoginDropDowns {}

	public class UtilLoginTexts {}
	
    public UtilLoginCheckBoxes _checkBox() {
        return new UtilLoginCheckBoxes();
    }
	
	public UtilLoginTextFields _textField() {
		return new UtilLoginTextFields();
	}

	public UtilLoginTexts _text() {
		return new UtilLoginTexts();
	}

	public UtilLoginLinks _link() {
		return new UtilLoginLinks();
	}

	public UtilLoginButtons _button() {
		return new UtilLoginButtons();
	}

	public UtilLoginDropDowns _dropDown() {
		return new UtilLoginDropDowns();
	}


	public String getExpectedPath() {
		return UtilLoginEnum.MY_ACCOUNT_URL.getURL();
	}

    public SeleniumEnums setUrl() {
        return UtilLoginEnum.MY_ACCOUNT_URL; 
    }

    protected boolean checkIsOnPage() {
    	return _textField().username().isPresent();
    }

}
