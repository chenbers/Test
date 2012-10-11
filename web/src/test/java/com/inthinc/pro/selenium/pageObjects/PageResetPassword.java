package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.Text;
import com.inthinc.pro.automation.elements.TextButton;
import com.inthinc.pro.automation.elements.TextField;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.selenium.pageEnums.ResetPasswordEnum;

public class PageResetPassword extends NavigationBar {
	public PageResetPassword() {
	}
	public class ResetPasswordPopUps extends MastheadPopUps{}
	
    public class ResetPasswordTablesCheckBoxes {}
    
    public ResetPasswordTablesCheckBoxes _checkBox() {
        return new ResetPasswordTablesCheckBoxes();
    }

    public class ResetPasswordTablesTexts extends ResetPasswordTexts {}
	
	public ResetPasswordPopUps _popUp(){
		return new ResetPasswordPopUps();
	}
	
	public ResetPasswordTextFields _textField() {
		return new ResetPasswordTextFields();
	}

	public ResetPasswordTexts _text() {
		return new ResetPasswordTexts();
	}

	public ResetPasswordLinks _link() {
		return new ResetPasswordLinks();
	}

	public ResetPasswordButtons _button() {
		return new ResetPasswordButtons();
	}

	public ResetPasswordDropDowns _dropDown() {
		return new ResetPasswordDropDowns();
	}

	public class ResetPasswordLinks extends NavigationBarLinks {}

	public class ResetPasswordTextFields extends NavigationBarTextFields {

		public TextField emailAddress() {
			return new TextField(ResetPasswordEnum.EMAIL_ADDRESS_FIELD);
		}
	}

	public class ResetPasswordButtons extends NavigationBarButtons {

		public TextButton send() {
			return new TextButton(ResetPasswordEnum.SEND_BUTTON);
		}
	}

	public class ResetPasswordDropDowns {}

	public class ResetPasswordTexts extends NavigationBarTexts {

		public Text information() {
			return new Text(ResetPasswordEnum.INFORMATION_TEXT);
		}

		public Text header() {
			return new Text(ResetPasswordEnum.HEADER);
		}
		
		public Text error() {
			return new Text(ResetPasswordEnum.ERROR_TEXT);
		}

	}


	@Override
	public String getExpectedPath() {
		return ResetPasswordEnum.RESET_PASSWORD_URL.getURL();
	}

    @Override
    public SeleniumEnums setUrl() {
        return ResetPasswordEnum.RESET_PASSWORD_URL; 
    }

    @Override
    protected boolean checkIsOnPage() {
        return (_button().send().isPresent() && _text().information().isPresent());
    }

}
