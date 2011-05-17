package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.DhxDropDown;
import com.inthinc.pro.automation.elements.DropDown;
import com.inthinc.pro.selenium.pageEnums.AdminAddEditUser;
import com.inthinc.pro.selenium.pageEnums.AdminTables.AdminUsersEnum;

public class PageAddEditUser extends AdminBar {
	
	public AddEditUserLinks _link(){
		return new AddEditUserLinks();
	}
	public class AddEditUserLinks extends AdminBarLinks{}
	
	public AddEditUserTexts _text(){
		return new AddEditUserTexts();
	}
	public class AddEditUserTexts extends AdminBarTexts{}
	
	public AddEditUserTextFields _textField(){
		return new AddEditUserTextFields();
	}
	public class AddEditUserTextFields extends AdminBarTextFields{}
	
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
		
		public DhxDropDown teamOrGroup(){
			return new DhxDropDown(AdminAddEditUser.DRIVER_TEAM_DHX	);
		}
		
	}
}
