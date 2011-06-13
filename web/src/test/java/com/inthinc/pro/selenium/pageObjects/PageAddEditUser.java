package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.Button;
import com.inthinc.pro.automation.elements.CheckBox;
import com.inthinc.pro.automation.elements.DhxDropDown;
import com.inthinc.pro.automation.elements.DropDown;
import com.inthinc.pro.automation.elements.Selector;
import com.inthinc.pro.automation.elements.Text;
import com.inthinc.pro.automation.elements.TextButton;
import com.inthinc.pro.automation.elements.TextField;
import com.inthinc.pro.automation.elements.TextFieldError;
import com.inthinc.pro.automation.elements.TextFieldLabel;
import com.inthinc.pro.selenium.pageEnums.AdminAddEditUserEnum;
import com.inthinc.pro.selenium.pageEnums.AdminBarEnum;
import com.inthinc.pro.selenium.pageEnums.AdminTables.AdminUsersEnum;

public class PageAddEditUser extends AdminBar {
	private static String page = "Person";

	public PageAddEditUser(){
		url = AdminAddEditUserEnum.DEFAULT_URL;
		checkMe.add(AdminAddEditUserEnum.DRIVER_TEAM_DHX);
	}

	public AddEditUserPopUps _popUp(){
        return new AddEditUserPopUps();
    }
    
	public class AddEditUserPopUps extends MastheadPopUps{}

	
	public class AddEditUserCheckBoxs{
		
		public CheckBox driverInformation(){
			return new CheckBox(AdminAddEditUserEnum.TEXT_FIELDS, "isDriver");
		}
		public CheckBox userInformation(){
			return new CheckBox(AdminAddEditUserEnum.TEXT_FIELDS, "isUser");
		}
		
	}
	
	public AddEditUserCheckBoxs _checkBox(){
		return new AddEditUserCheckBoxs();
	}
	
	public class AddEditUserSelects{
		public Selector rolesLeft(){
			return new Selector(AdminBarEnum.SELECTOR, page, "from");
		}
		public Selector rolesRight(){
			return new Selector(AdminBarEnum.SELECTOR, page, "picked");
		}
	}
	
	public AddEditUserSelects _select(){
		return new AddEditUserSelects();
	}
	
	public AddEditUserLinks _link(){
		return new AddEditUserLinks();
	}
	public class AddEditUserLinks extends AdminBarLinks{}
	
	public AddEditUserTexts _text(){
		return new AddEditUserTexts();
	}
	public class AddEditUserTexts extends AdminBarTexts{
		
		public Text masterError(){
			return new Text(AdminBarEnum.MASTER_ERROR);
		}
		
		public TextFieldLabel personLabel(AdminUsersEnum label){
			return new TextFieldLabel(AdminAddEditUserEnum.TEXT_FIELDS, label);
		}
		
		public TextFieldLabel driverLabel(AdminUsersEnum label){
			return new TextFieldLabel(AdminAddEditUserEnum.TEXT_FIELDS,"driver_", label);
		}
		
		public TextFieldLabel userLabel(AdminUsersEnum label){
			return new TextFieldLabel(AdminAddEditUserEnum.TEXT_FIELDS,"user_", label);
		}
		
		public TextFieldError personError(AdminUsersEnum label){
			return new TextFieldError(AdminAddEditUserEnum.TEXT_FIELDS, label);
		}
		
		public TextFieldError driverError(AdminUsersEnum label){
			return new TextFieldError(AdminAddEditUserEnum.TEXT_FIELDS,"driver_", label);
		}
		
		public TextFieldError userError(AdminUsersEnum label){
			return new TextFieldError(AdminAddEditUserEnum.TEXT_FIELDS,"user_", label);
		}
		
		
	}
	
	public AddEditUserTextFields _textField(){
		return new AddEditUserTextFields();
	}
	public class AddEditUserTextFields extends AdminBarTextFields{
		public TextField personFields(AdminUsersEnum textField){
			return new TextField(AdminBarEnum.TABLE_ENTRIES, textField);
		}
		
		public TextField driverFields(AdminUsersEnum textField){
			return new TextField(AdminAddEditUserEnum.TEXT_FIELDS, "driver_",textField);
		}
		
		public TextField userFields(AdminUsersEnum textField){
			return new TextField(AdminAddEditUserEnum.TEXT_FIELDS, "user_", textField);
		}
	}
	
	public AddEditUserButtons _button(){
		return new AddEditUserButtons();
	}
	public class AddEditUserButtons extends AdminBarButtons{
		
		public TextButton saveTop(){
			return new TextButton(AdminAddEditUserEnum.SAVE,"1");
		}
		public TextButton saveBottom(){
			return new TextButton(AdminAddEditUserEnum.SAVE,"2");
		}
		
		public TextButton cancelTop(){
			return new TextButton(AdminAddEditUserEnum.CANCEL,"1");
		}
		public TextButton cancelBottom(){
			return new TextButton(AdminAddEditUserEnum.CANCEL,"2");
		}
		
		public Button moveRight(){
			return new Button(AdminBarEnum.MOVE_RIGHT, page);
		}
		public Button moveLeft(){
			return new Button(AdminBarEnum.MOVE_LEFT, page);
		}
		public Button moveAllRight(){
			return new Button(AdminBarEnum.MOVE_ALL_RIGHT, page);
		}
		public Button moveAllLeft(){
			return new Button(AdminBarEnum.MOVE_ALL_LEFT, page);
		}
		
	}
	
	public AddEditUserDropDowns _dropDown(){
		return new AddEditUserDropDowns();
	}
	public class AddEditUserDropDowns extends AdminBarDropDowns{
		
		public DropDown regularDropDowns(AdminUsersEnum dropDown){
			return new DropDown(AdminAddEditUserEnum.DROP_DOWNS, dropDown);
		}
		
		public DhxDropDown driverTeam(){
			return new DhxDropDown(AdminAddEditUserEnum.DRIVER_TEAM_DHX);
		}
		
		public DhxDropDown userGroup(){
			return new DhxDropDown(AdminAddEditUserEnum.USER_GROUP_DHX);
		}
	}
    @Override
    public String getExpectedPath() {
        return AdminAddEditUserEnum.DEFAULT_URL.getURL();
    }
}
