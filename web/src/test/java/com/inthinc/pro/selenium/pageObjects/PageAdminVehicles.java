package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.TextButton;
import com.inthinc.pro.automation.elements.TextField;
import com.inthinc.pro.selenium.pageEnums.AdminVehiclesEnum;

public class PageAdminVehicles extends AdminBar {
	
	public PageAdminVehicles(){
		url = AdminVehiclesEnum.DEFAULT_URL;
		checkMe.add(AdminVehiclesEnum.BATCH_EDIT);
		checkMe.add(AdminVehiclesEnum.DELETE);
		checkMe.add(AdminVehiclesEnum.EDIT_COLUMNS_LINK);
	}
	
	public AdminVehiclesPopUps _popUp(){
        return new AdminVehiclesPopUps();
    }
    
	public class AdminVehiclesPopUps extends AdminBarPopUps{}

	
	public class AdminVehiclesButtons extends AdminBarButtons {
		public TextButton tableSearch() {
			return new TextButton(AdminVehiclesEnum.SEARCH_BUTTON);
		}
	}

	public class AdminVehiclesDropDowns extends AdminBarDropDowns {
	}

	public class AdminVehiclesLinks extends AdminBarLinks {//TODO: jwimmer: to dTanner: this page doesn't appear to be complete yet either?  I'd expect to see a bunch of table links here?
	    
	}

	public class AdminVehiclesTextFields extends AdminBarTextFields {
		public TextField tableSearch() {
			return new TextField(AdminVehiclesEnum.SEARCH_TEXT_FIELD);
		}
	}

	public class AdminVehiclesTexts extends AdminBarTexts {
	}

	public AdminVehiclesButtons _button() {
		return new AdminVehiclesButtons();
	}

	public AdminVehiclesDropDowns _dropDown() {
		return new AdminVehiclesDropDowns();
	}

	public AdminVehiclesLinks _link() {
		return new AdminVehiclesLinks();
	}

	public AdminVehiclesTexts _text() {
		return new AdminVehiclesTexts();
	}

	public AdminVehiclesTextFields _textField() {
		return new AdminVehiclesTextFields();
	}
    @Override
    public String getExpectedPath() {
        return AdminVehiclesEnum.DEFAULT_URL.getURL();
    }
}
