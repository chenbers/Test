package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.Button;
import com.inthinc.pro.automation.elements.CheckBox;
import com.inthinc.pro.automation.elements.ClickableObject;
import com.inthinc.pro.automation.elements.TableText;
import com.inthinc.pro.automation.elements.Text;
import com.inthinc.pro.automation.elements.TextButton;
import com.inthinc.pro.automation.elements.TextField;
import com.inthinc.pro.automation.enums.TextEnum;
import com.inthinc.pro.selenium.pageEnums.PopUpEnum;

public class PopUps {
	private String page, type;
	private Integer number;

	public enum Types implements TextEnum {
		SINGLE("_singleEmail"), POPUP("_emailPopup"), REPORT(
				"_reportEmailModal"), PERFORMANCE("ReportEmailModal"), ;
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
		this.number = number;
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
						assertTrue(selenium.isVisible(myEnum));
						return this;
					}
				};
			}

			public TextButton close() {
				return new TextButton(PopUpEnum.X){
					@Override
					public ClickableObject click(){
						super.click();
						assertTrue(selenium.isVisible(myEnum));
						return this;
					}
				};
			}
		}

		public class EditColumnsText {

			public TableText entry() {
				return new TableText(PopUpEnum.EDIT_LABEL, page);
			}

			public Text header() {
				return new Text(PopUpEnum.EDIT_HEADER, page);
			}

			public Text title() {
				return new Text(PopUpEnum.TITLE, page);
			}

		}

		public CheckBox _checkBox() {
			return new CheckBox(PopUpEnum.EDIT_CHECKBOX, page);
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
						assertTrue(selenium.isVisible(myEnum));
						return this;
					}
				};
			}

			public Button close() {
				return new Button(PopUpEnum.X, page + type){
					@Override
					public ClickableObject click(){
						super.click();
						assertTrue(selenium.isVisible(myEnum));
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
						assertTrue(selenium.isVisible(myEnum));
						return this;
					}
				};
			}
			
			public Button close(){
				return new Button(PopUpEnum.FORGOT_CLOSE){
					@Override
					public ClickableObject click(){
						super.click();
						assertTrue(selenium.isVisible(myEnum));
						return this;
					}
				};
			}
		}
	}
	
	public class ChangePassword{
		public ChangeButtons _button(){
			return new ChangeButtons();
		}
		public class ChangeButtons{
			public TextButton ok(){
				return new TextButton(PopUpEnum.CHANGE_OK);
			}
		}
		public ChangeText _text(){
			return new ChangeText();
		}
		public class ChangeText{
			public Text header(){
				return new Text(PopUpEnum.CHANGE_SUCCESS);
			}
			public Text message(){
				return new Text(PopUpEnum.CHANGE_MESSAGE);
			}
		}
	}
}
