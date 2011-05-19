package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.DhxDropDown;
import com.inthinc.pro.automation.elements.DropDown;
import com.inthinc.pro.automation.elements.TextField;
import com.inthinc.pro.automation.elements.TextFieldLabel;
import com.inthinc.pro.selenium.pageEnums.AdminAddEditUser;
import com.inthinc.pro.selenium.pageEnums.AdminBarEnum;
import com.inthinc.pro.selenium.pageEnums.LoginEnum;
import com.inthinc.pro.selenium.pageEnums.AdminTables.AdminUsersEnum;

public class PageAddEditUser extends AdminBar {
	
	public AddEditUserLinks _link(){
		return new AddEditUserLinks();
	}
	public class AddEditUserLinks extends AdminBarLinks{}
	
	public AddEditUserTexts _text(){
		return new AddEditUserTexts();
	}
	public class AddEditUserTexts extends AdminBarTexts{
		
		public TextFieldLabel personLabel(AdminUsersEnum label){
			return new TextFieldLabel(AdminAddEditUser.TEXT_FIELDS, label);
		}
		
		public TextFieldLabel driverLabel(AdminUsersEnum label){
			return new TextFieldLabel(AdminAddEditUser.TEXT_FIELDS,"driver_", label);
		}
		
		public TextFieldLabel userLabel(AdminUsersEnum label){
			return new TextFieldLabel(AdminAddEditUser.TEXT_FIELDS,"user_", label);
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
			return new TextField(AdminAddEditUser.TEXT_FIELDS, "driver_",textField);
		}
		
		public TextField userFields(AdminUsersEnum textField){
			return new TextField(AdminAddEditUser.TEXT_FIELDS, "user_", textField);
		}
	}
	
	public AddEditUserButtons _button(){
		return new AddEditUserButtons();
	}
	public class AddEditUserButtons extends AdminBarButtons{}
	
	public AddEditUserDropDowns _dropDown(){
		return new AddEditUserDropDowns();
	}
	public class AddEditUserDropDowns extends AdminBarDropDowns{
		
		public DropDown regularDropDowns(AdminUsersEnum dropDown){
			return new DropDown(AdminAddEditUser.DROP_DOWNS, dropDown);
		}
		
		public DhxDropDown driverTeam(){
			return new DhxDropDown(AdminAddEditUser.DRIVER_TEAM_DHX);
		}
		
		public DhxDropDown userGroup(){
			return new DhxDropDown(AdminAddEditUser.USER_GROUP_DHX);
		}
	}
    @Override
    public String getExpectedPath() {
        return AdminAddEditUser.DEFAULT_URL.getURL();
    }
}
