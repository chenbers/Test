package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.Button;
import com.inthinc.pro.automation.elements.CheckBoxTable;
import com.inthinc.pro.automation.elements.ClickableObject;
import com.inthinc.pro.automation.elements.Text;
import com.inthinc.pro.automation.elements.TextButton;
import com.inthinc.pro.automation.elements.TextField;
import com.inthinc.pro.automation.elements.TextFieldError;
import com.inthinc.pro.automation.elements.TextFieldLabel;
import com.inthinc.pro.automation.elements.TextLink;
import com.inthinc.pro.automation.elements.TextTable;
import com.inthinc.pro.automation.enums.TextEnum;
import com.inthinc.pro.automation.utils.MasterTest;
import com.inthinc.pro.selenium.pageEnums.PopUpEnum;

public class PopUps extends MasterTest{
	private String page;
	private String type;
	private Integer number;

	public enum Types implements TextEnum {
		SINGLE("_singleEmail"), 
		POPUP("_emailPopup"), 
		REPORT("_reportEmailModal"), 
		PERFORMANCE("ReportEmailModal"), ;
		private String type;

		private Types(String type) {
			this.type = type;
		}

		public String getText() {
			return type;
		}
	}
	

	public PopUps(String page, Types type, Integer number) {
		this.page = page;
		this.type = type.getText();
		this.number = ++number; //TODO: dtanner: better way?
	}

	public PopUps(String page) {
		this.page = page;
	}

	public PopUps(Integer number) {
		this.number = number;
	}

	public PopUps() {
	}

	public class EditColumns {
		public EditColumnsText _text() {
			return new EditColumnsText();
		}

		public EditColumnsButton _button() {
			return new EditColumnsButton();
		}

		public class EditColumnsButton {

			public TextButton save() {
				return new TextButton(PopUpEnum.EDIT_SAVE, page);
			}

			public TextButton cancel() {
				return new TextButton(PopUpEnum.EDIT_CANCEL, page){
					@Override
					public ClickableObject click(){
						super.click();
						assertVisibility(false);
						return this;
					}
				};
			}

			public TextButton close() {
				return new TextButton(PopUpEnum.X){
					@Override
					public ClickableObject click(){
						super.click();
						assertVisibility(false);
						return this;
					}
				};
			}
		}

		public class EditColumnsText {

			public TextTable entry() {
				return new TextTable(PopUpEnum.EDIT_LABEL, page);
			}

			public Text header() {
				return new Text(PopUpEnum.EDIT_HEADER, page);
			}

			public Text title() {
				return new Text(PopUpEnum.TITLE, page);
			}

		}

		public CheckBoxTable _checkBox() {//TODO: jwimmer: to dTanner: this seems to break with the convention of ...elementName().action();  is there a way to provide that, if not should we be providing enums full of values i.e. DriverEditColumns.GROUP = 1, DriverEditColumns.DRIVER = 3?
			return new CheckBoxTable(PopUpEnum.EDIT_CHECKBOX, page);
		}
		
	}

	public class Email {

		public EmailButton _button() {
			return new EmailButton();
		}

		public EmailTextField _textField() {
			return new EmailTextField();
		}

		public EmailText _text() {
			return new EmailText();
		}

		public class EmailButton {
			public TextButton email() {
				return new TextButton(PopUpEnum.EMAIL_SUBMIT, page + type,
						number);
			}

			public TextButton cancel() {
				return new TextButton(PopUpEnum.EMAIL_CANCEL){
					@Override
					public ClickableObject click(){
						super.click();
						assertVisibility(false);
						return this;
					}
				};
			}

			public Button close() {
				return new Button(PopUpEnum.X, page + type){
					@Override
					public ClickableObject click(){
						super.click();
						assertVisibility(false);
						return this;
					}
				};
			}
		}

		public class EmailText {
			public Text title() {
				return new Text(PopUpEnum.TITLE, page + type);
			}

			public Text subTitle() {
				return new Text(PopUpEnum.EMAIL_SUBTITLE, page + type);
			}

			public Text header() {
				return new Text(PopUpEnum.EMAIL_HEADER, page + type);
			}
		}

		public class EmailTextField {
			public TextField emailAddresses() {
				return new TextField(PopUpEnum.EMAIL_TEXTFIELD, page + type);
			}
		}
	}
	
	public class LoginError{
		public ErrorTexts _text(){
			return new ErrorTexts();
		}
		
		public class ErrorTexts{
			public Text header(){
				return new Text(PopUpEnum.ERROR_HEADER);
			}
			
			public Text message(){
				return new Text(PopUpEnum.ERROR_MESSAGE);
			}
		}
		public ErrorButtons _button(){
			return new ErrorButtons();
		}
		public class ErrorButtons{
			public TextButton ok(){
				return new TextButton(PopUpEnum.ERROR_BUTTON_OK);
			}
			
			public Button close(){
				return new Button(PopUpEnum.ERROR_CLOSE);
			}
		}
	}
	
	public class MessageSent{
		public MessageSentText _text(){
			return new MessageSentText();
		}
		public class MessageSentText{
			public Text header(){
				return new Text(PopUpEnum.MESSAGE_SENT_HEADER);
			}
			
			public Text title(){
				return new Text(PopUpEnum.MESSAGE_SENT_MESSAGE);
			}
			
			public Text boldTitle(){
				return new Text(PopUpEnum.MESSAGE_SENT_BOLD);
			}
			
			public Text bullet1(){
				return new Text(PopUpEnum.MESSAGE_SENT_BULLET_1);
			}
			public Text bullet2(){
				return new Text(PopUpEnum.MESSAGE_SENT_BULLET_2);
			}
			public Text bullet3(){
				return new Text(PopUpEnum.MESSAGE_SENT_BULLET_3);
			}
		}
	}
	
	public class ForgotPassword{
		
		public ForgotText _text(){
			return new ForgotText();
		}
		public class ForgotText{
			
			public Text header(){
				return new Text(PopUpEnum.FORGOT_HEADER);
			}
			
			public Text error(){
				return new Text(PopUpEnum.FORGOT_ERROR);
			}
			
			public Text title(){
				return new Text(PopUpEnum.FORGOT_TITLE);
			}
			
			public Text label(){
				return new Text(PopUpEnum.FORGOT_EMAIL_LABEL);
			}
		}
		
		public ForgotTextField _textField(){
			return new ForgotTextField();
		}
		public class ForgotTextField{
			public TextField email(){
				return new TextField(PopUpEnum.FORGOT_EMAIL_FIELD);
			}
		}
		
		public ForgotButtons _button(){
			return new ForgotButtons();
		}
		public class ForgotButtons{
			public TextButton send(){
				return new TextButton(PopUpEnum.FORGOT_SEND);
			}
			
			public TextButton cancel(){
				return new TextButton(PopUpEnum.FORGOT_CANCEL_BUTTON){
					@Override
					public ClickableObject click(){
						super.click();
						assertVisibility(false);
						return this;
					}
				};
			}
			
			public Button close(){
				return new Button(PopUpEnum.FORGOT_CLOSE){
					@Override
					public ClickableObject click(){
						super.click();
						assertVisibility(false);
						return this;
					}
				};
			}
		}
	}
	
	public class ForgotChangePassword{
		public ChangeButtons _button(){
			return new ChangeButtons();
		}
		public class ChangeButtons{
			public TextButton ok(){
				return new TextButton(PopUpEnum.FORGOT_CHANGE_OK);
			}
		}
		public ChangeText _text(){
			return new ChangeText();
		}
		public class ChangeText{
			public Text header(){
				return new Text(PopUpEnum.FORGOT_CHANGE_SUCCESS);
			}
			public Text message(){
				return new Text(PopUpEnum.FORGOT_CHANGE_MESSAGE);
			}
		}
	}
	
	
	public class MyAccountChangePassword{
		public MyChangeTexts _text(){
			return new MyChangeTexts();
		}
		
		public class MyChangeTexts{
			public Text title(){
				return new Text(PopUpEnum.MY_CHANGE_TITLE);
			}
			public TextFieldLabel currentPasswordLabel(){
				return new TextFieldLabel(PopUpEnum.MY_CURRENT_TEXTFIELD);
			}
			
			public TextFieldLabel newPasswordLabel(){
				return new TextFieldLabel(PopUpEnum.MY_NEW_TEXTFIELD);
			}
			
			public TextFieldLabel confirmPasswordLabel(){
				return new TextFieldLabel(PopUpEnum.MY_CONFIRM_TEXTFIELD);
			}
			
			public TextFieldError currentPasswordError(){
				return new TextFieldError(PopUpEnum.MY_CURRENT_TEXTFIELD);
			}
			
			public TextFieldError newPasswordError(){
				return new TextFieldError(PopUpEnum.MY_NEW_TEXTFIELD);
			}
			
			public TextFieldError confirmPasswordError(){
				return new TextFieldError(PopUpEnum.MY_CONFIRM_TEXTFIELD);
			}
			
			public Text passwordStrength(){
				return new Text(PopUpEnum.MY_STRENGTH_MSG);
			}
			
			
		}
		
		public MyChangeTextFields _textField(){
			return new MyChangeTextFields();
		}
		
		public class MyChangeTextFields{
			public TextField currentPassword(){
				return new TextField(PopUpEnum.MY_CURRENT_TEXTFIELD);
			}
			
			public TextField newPassword(){
				return new TextField(PopUpEnum.MY_NEW_TEXTFIELD);
			}
			
			public TextField confirmNewPassword(){
				return new TextField(PopUpEnum.MY_CONFIRM_TEXTFIELD);
			}
		}
		
		
		
		public MyChangeButtons _button(){
			return new MyChangeButtons();
		}
		public class MyChangeButtons{
			public TextButton change(){
				return new TextButton(PopUpEnum.MY_CHANGE_CHANGE);
			}
			
			public TextButton cancel(){
				return new TextButton(PopUpEnum.MY_CHANGE_CANCEL);
			}
			
			public Button close(){
				return new Button(PopUpEnum.MY_CHANGE_X);
			}
		}
	}
	
	
	public class AdminDelete{
	    private PopUpEnum delete;
	    private PopUpEnum cancel;
	    
	    public AdminDelete(Boolean table){
		if (table){
		    delete = PopUpEnum.DELETE_CONFIRM;
		    cancel = PopUpEnum.DELETE_CANCEL;
		}else {
		    delete = PopUpEnum.DETAILS_DELETE_CONFIRM;
		    cancel = PopUpEnum.DETAILS_DELETE_CANCEL;
		}
	    }
	    
		public AdminDeleteButtons _button(){
			return new AdminDeleteButtons();
		}
		public class AdminDeleteButtons{
			public TextButton delete(){
				return new TextButton(delete,page);
			}
			
			public TextButton cancel(){
				return new TextButton(cancel,page);
			}
			
			public Button close(){
				return new Button(PopUpEnum.DELETE_CLOSE);
			}
		}
		
		public AdminDeleteText _text(){
			return new AdminDeleteText();
		}
		public class AdminDeleteText{
			public Text header(){
				return new Text(PopUpEnum.DELETE_HEADER);
			}
			
			public Text message(){
				return new Text(PopUpEnum.DELETE_MESSAGE);
			}
		}
	}
	
	public class PasswordChangeRequired{
		public PasswordChangeRequiredButtons _button(){
			return new PasswordChangeRequiredButtons();
		}
		
		public PasswordChangeRequiredTexts _text(){
			return new PasswordChangeRequiredTexts();
		}
		
		public PasswordChangeRequiredTextFields _textField(){
			return new PasswordChangeRequiredTextFields();
		}
		
		public class PasswordChangeRequiredTextFields{
			
			public TextField currentPassword(){
				return new TextField(PopUpEnum.MY_CURRENT_TEXTFIELD);
			}
			
			public TextField newPassword(){
				return new TextField(PopUpEnum.MY_NEW_TEXTFIELD);
			}
			
			public TextField confirmNewPassword(){
				return new TextField(PopUpEnum.MY_CONFIRM_TEXTFIELD);
			}
		}
		
		
		public class PasswordChangeRequiredButtons{
			public TextButton change(){
				return new TextButton(PopUpEnum.MY_CHANGE_CHANGE);
			}
		}
		
		public class PasswordChangeRequiredTexts{
			public TextFieldLabel currentPassword(){
				return new TextFieldLabel(PopUpEnum.MY_CURRENT_TEXTFIELD);
			}
			
			public TextFieldLabel newPassword(){
				return new TextFieldLabel(PopUpEnum.MY_NEW_TEXTFIELD);
			}
			
			public TextFieldLabel confirmPassword(){
				return new TextFieldLabel(PopUpEnum.MY_CURRENT_TEXTFIELD);
			}
			
			public Text header(){
				return new Text(PopUpEnum.MY_CHANGE_TITLE_REQUIRED);
			}

			public Text currentPasswordError(){
				return new Text(PopUpEnum.MY_CURRENT_TEXTFIELD);
			}
			
			public Text newPasswordError(){
				return new Text(PopUpEnum.MY_NEW_TEXTFIELD);
			}
			
			public Text confirmPasswordError(){
				return new Text(PopUpEnum.MY_CONFIRM_TEXTFIELD);
			}
			
			public Text passwordStrength(){
				return new Text(PopUpEnum.MY_STRENGTH_MSG);
			}
		}
	}
	
	public class UpdatePasswordReminder{
		public UpdateLinks _link(){
			return new UpdateLinks();
		}
		
		public UpdateButtons _button(){
			return new UpdateButtons();
		}
		
		public UpdateTexts _text(){
			return new UpdateTexts();
		}
		
		public class UpdateButtons{
			public Button close(){
				return new Button(PopUpEnum.UPDATE_PASSWORD_CLOSE);
			}
		}
		
		public class UpdateTexts{
			public Text header(){
				return new Text(PopUpEnum.UPDATE_PASSWORD_HEADER);
			}
			
			public Text message(){
				return new Text(PopUpEnum.UPDATE_PASSWORD_MESSAGE);
			}
		}
		
		public class UpdateLinks{
			public TextLink changePassword(){
				return new TextLink(PopUpEnum.UPDATE_PASSWORD_CHANGE_PASSWORD);
			}
		}
	}
	
	public class PasswordChange{
		public PasswordChangeTexts _text(){
			return new PasswordChangeTexts();
		}
		
		public class PasswordChangeTexts{
			public Text title(){
				return new Text(PopUpEnum.MY_CHANGE_TITLE);
			}
			public TextFieldLabel currentPasswordLabel(){
				return new TextFieldLabel(PopUpEnum.MY_CURRENT_TEXTFIELD);
			}
			
			public TextFieldLabel newPasswordLabel(){
				return new TextFieldLabel(PopUpEnum.MY_NEW_TEXTFIELD);
			}
			
			public TextFieldLabel confirmPasswordLabel(){
				return new TextFieldLabel(PopUpEnum.MY_CONFIRM_TEXTFIELD);
			}
			
			public TextFieldError currentPasswordError(){
				return new TextFieldError(PopUpEnum.MY_CURRENT_TEXTFIELD);
			}
			
			public TextFieldError newPasswordError(){
				return new TextFieldError(PopUpEnum.MY_NEW_TEXTFIELD);
			}
			
			public TextFieldError confirmPasswordError(){
				return new TextFieldError(PopUpEnum.MY_CONFIRM_TEXTFIELD);
			}
			
			public Text passwordStrength(){
				return new Text(PopUpEnum.CHANGE_PASSWORD_FORM_STRENGTH_MSG);
			}
			
			
		}
		
		public PasswordChangeTextFields _textField(){
			return new PasswordChangeTextFields();
		}
		
		public class PasswordChangeTextFields{
			public TextField currentPassword(){
				return new TextField(PopUpEnum.MY_CURRENT_TEXTFIELD);
			}
			
			public TextField newPassword(){
				return new TextField(PopUpEnum.MY_NEW_TEXTFIELD);
			}
			
			public TextField confirmNewPassword(){
				return new TextField(PopUpEnum.MY_CONFIRM_TEXTFIELD);
			}
		}
		
		
		
		public PasswordChangeButtons _button(){
			return new PasswordChangeButtons();
		}
		public class PasswordChangeButtons{
			public TextButton change(){
				return new TextButton(PopUpEnum.MY_CHANGE_CHANGE);
			}
			
			public TextButton cancel(){
				return new TextButton(PopUpEnum.MY_CHANGE_CANCEL);
			}
			
			public Button close(){
				return new Button(PopUpEnum.CHANGE_PASSWORD_FORM_CHANGE_X);
			}
		}
	}
}
