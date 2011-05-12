package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.Button;
import com.inthinc.pro.automation.elements.CheckBox;
import com.inthinc.pro.automation.elements.TableText;
import com.inthinc.pro.automation.elements.Text;
import com.inthinc.pro.automation.elements.TextButton;
import com.inthinc.pro.automation.elements.TextField;
import com.inthinc.pro.automation.enums.TextEnum;
import com.inthinc.pro.selenium.pageEnums.PopUpEnum;

public class PopUps {
	private String page, type;
	private Integer number;
	
	public enum Types implements TextEnum{
		SINGLE("_singleEmail"),
		POPUP("_emailPopup"),
		REPORT("_reportEmailModal"),
		PERFORMANCE("ReportEmailModal"),
		;
		private String type;
		private Types(String type){
			this.type = type;
		}
		
		public String getText(){
			return type;
		}
	}
	
	public PopUps(String page, Types type, Integer number){
		this.page = page;
		this.type = type.getText();
		this.number = number;
	}
	public PopUps(String page){
		this.page = page;
	}
	public PopUps(Integer number){
		this.number = number;
	}
	
	public class EditColumns{
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
				return new TextButton(PopUpEnum.EDIT_CANCEL, page);
			}

			public TextButton close() {
				return new TextButton(PopUpEnum.X);
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
				return new TextButton(PopUpEnum.EMAIL_SUBMIT, page+type, number);
			}

			public TextButton cancel() {
				return new TextButton(PopUpEnum.EMAIL_CANCEL);
			}

			public Button close() {
				return new Button(PopUpEnum.X, page+type);
			}
		}

		public class EmailText {
			public Text title() {
				return new Text(PopUpEnum.TITLE, page+type);
			}

			public Text subTitle() {
				return new Text(PopUpEnum.EMAIL_SUBTITLE, page+type);
			}

			public Text header() {
				return new Text(PopUpEnum.EMAIL_HEADER, page+type);
			}
		}

		public class EmailTextField {
			public TextField emailAddresses() {
				return new TextField(PopUpEnum.EMAIL_TEXTFIELD, page+type);
			}
		}
	}

}
