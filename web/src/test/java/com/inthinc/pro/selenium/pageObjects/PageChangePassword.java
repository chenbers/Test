package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.Text;
import com.inthinc.pro.automation.elements.TextButton;
import com.inthinc.pro.automation.elements.TextField;
import com.inthinc.pro.automation.elements.TextFieldError;
import com.inthinc.pro.automation.elements.TextFieldLabel;
import com.inthinc.pro.selenium.pageEnums.ChangePasswordEnum;

public class PageChangePassword extends Masthead {
	
	public PageChangePassword(){
		url = null;
		checkMe.add(ChangePasswordEnum.CHANGE_TITLE);
		checkMe.add(ChangePasswordEnum.CHANGE_PASSWORD_BUTTON);
		checkMe.add(ChangePasswordEnum.CONFIRM_PASSWORD);
	}
	

	public PageChangePassword openChangePasswordPage(String url){
		open(url);
		return this;
	}
	
	
	public class LoginPopUps extends MastheadPopUps{
    	public ForgotChangePassword success(){
    		return new ForgotChangePassword();
    	}
    }
    
    public LoginPopUps _popUp(){
    	return new LoginPopUps();
    }
	
	public ChangeTexts _text(){
		return new ChangeTexts();
	}
	public class ChangeTexts extends MastheadTexts{
		public Text header(){
			return new Text(ChangePasswordEnum.CHANGE_TITLE);
		}
		
		public Text message(){
			return new Text(ChangePasswordEnum.MESSAGE);
		}
		
		public TextFieldLabel usernameLabel(){
			return new TextFieldLabel(ChangePasswordEnum.USERNAME_TEXT);
		}
		
		public Text usernameValue(){
			return new Text(ChangePasswordEnum.USERNAME_TEXT);
		}
		
		public TextFieldLabel newPasswordLabel(){
			return new TextFieldLabel(ChangePasswordEnum.NEW_PASSWORD);
		}
		
		public TextFieldError newPasswordError(){
			return new TextFieldError(ChangePasswordEnum.NEW_PASSWORD);
		}
		
		public TextFieldError confirmNewPasswordError(){
			return new TextFieldError(ChangePasswordEnum.CONFIRM_PASSWORD);
		}
		public TextFieldLabel confirmNewPasswordLabel(){
			return new TextFieldLabel(ChangePasswordEnum.CONFIRM_PASSWORD);
		}
		
		public Text passwordStrengthLabel(){
			return new Text(ChangePasswordEnum.PASSWORD_STRENGTH);
		}
		
		public Text passwordStrengthMessage(){
			return new Text(ChangePasswordEnum.PASSWORD_STRENGTH_MESSAGE);
		}
	}
	
	
	public ChangeTextFields _textField(){
		return new ChangeTextFields();
	}
	public class ChangeTextFields extends MastheadTextFields{
		
		public TextField newPassword(){
			return new TextField(ChangePasswordEnum.NEW_PASSWORD);
		}
		
		public TextField confirmNewPassword(){
			return new TextField(ChangePasswordEnum.CONFIRM_PASSWORD);
		}
	}
	
	
	public ChangeLinks _link(){
		return new ChangeLinks();
	}
	public class ChangeLinks extends MastheadLinks{
		
	}
	
	
	public ChangeButtons _button(){
		return new ChangeButtons();
	}
	public class ChangeButtons extends MastheadButtons{
		
		public TextButton change(){
			return new TextButton(ChangePasswordEnum.CHANGE_PASSWORD_BUTTON);
		}
		
		public TextButton cancel(){
			return new TextButton(ChangePasswordEnum.CANCEL_CHANGE);
		}
	}
	
	
	public ChangeDropDowns _dropDown(){
		return new ChangeDropDowns();
	}
	public class ChangeDropDowns extends MastheadDropDowns{
		
	}
}
