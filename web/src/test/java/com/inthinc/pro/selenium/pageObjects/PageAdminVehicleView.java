package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.Text;
import com.inthinc.pro.automation.elements.TextButton;
import com.inthinc.pro.automation.elements.TextField;
import com.inthinc.pro.automation.elements.TextLink;
import com.inthinc.pro.automation.selenium.AbstractPage;
import com.inthinc.pro.selenium.pageEnums.AdminVehicleViewEnum;
import com.inthinc.pro.selenium.pageEnums.AdminVehiclesEnum;
import com.inthinc.pro.selenium.pageObjects.PopUps.AdminDelete;

public class PageAdminVehicleView extends AdminBar {
	
	public PageAdminVehicleView(){
		url = AdminVehicleViewEnum.DEFAULT_URL;
//		checkMe.add(AdminVehiclesEnum.BATCH_EDIT);
//		checkMe.add(AdminVehiclesEnum.DELETE);
//		checkMe.add(AdminVehiclesEnum.EDIT_COLUMNS_LINK);
	}
	
	public AdminVehicleViewPopUps _popUp(){
        return new AdminVehicleViewPopUps();
    }
    
	public class AdminVehicleViewPopUps extends AdminBarPopUps{
	    public AdminDelete delete() {return new AdminDelete(false);}
	}

	
	public class AdminVehicleViewButtons extends AdminBarButtons {
		public TextButton delete() {  return new TextButton(AdminVehicleViewEnum.BTN_DELETE_TOP); }
		public TextButton edit() {    return new TextButton(AdminVehicleViewEnum.BTN_EDIT_TOP);   }
	}

	public class AdminVehicleViewDropDowns extends AdminBarDropDowns {
	}

	public class AdminVehicleViewLinks extends AdminBarLinks {
	    public TextLink detailsTab() {return new TextLink(AdminVehicleViewEnum.TAB_DETAILS);}
	    public TextLink SpeedAndSensitivityTab() {return new TextLink(AdminVehicleViewEnum.TAB_SPEED_AND_SENSITIVITY);}
	}

	public class AdminVehicleViewTextFields extends AdminBarTextFields {
		public TextField notifyWhenExceedingBy(Integer limit) {return new TextField(AdminVehicleViewEnum.TXT_ZONE_LIMIT_EXPECTSROWNUM, (limit/5)-1);}
		public TextField notifyWhenExceeding05MPHBy() {return new TextField(AdminVehicleViewEnum.TXT_ZONE_LIMIT_EXPECTSROWNUM, 0);}
		public TextField notifyWhenExceeding10MPHBy() {return new TextField(AdminVehicleViewEnum.TXT_ZONE_LIMIT_EXPECTSROWNUM, 1);}
		public TextField notifyWhenExceeding15MPHBy() {return new TextField(AdminVehicleViewEnum.TXT_ZONE_LIMIT_EXPECTSROWNUM, 2);}
		public TextField notifyWhenExceeding20MPHBy() {return new TextField(AdminVehicleViewEnum.TXT_ZONE_LIMIT_EXPECTSROWNUM, 3);}
		public TextField notifyWhenExceeding25MPHBy() {return new TextField(AdminVehicleViewEnum.TXT_ZONE_LIMIT_EXPECTSROWNUM, 4);}
		public TextField notifyWhenExceeding30MPHBy() {return new TextField(AdminVehicleViewEnum.TXT_ZONE_LIMIT_EXPECTSROWNUM, 5);}
		public TextField notifyWhenExceeding35MPHBy() {return new TextField(AdminVehicleViewEnum.TXT_ZONE_LIMIT_EXPECTSROWNUM, 6);}
		public TextField notifyWhenExceeding40MPHBy() {return new TextField(AdminVehicleViewEnum.TXT_ZONE_LIMIT_EXPECTSROWNUM, 7);}
		public TextField notifyWhenExceeding45MPHBy() {return new TextField(AdminVehicleViewEnum.TXT_ZONE_LIMIT_EXPECTSROWNUM, 8);}
		public TextField notifyWhenExceeding50MPHBy() {return new TextField(AdminVehicleViewEnum.TXT_ZONE_LIMIT_EXPECTSROWNUM, 9);}
		public TextField notifyWhenExceeding55MPHBy() {return new TextField(AdminVehicleViewEnum.TXT_ZONE_LIMIT_EXPECTSROWNUM, 10);}
		public TextField notifyWhenExceeding60MPHBy() {return new TextField(AdminVehicleViewEnum.TXT_ZONE_LIMIT_EXPECTSROWNUM, 11);}
		public TextField notifyWhenExceeding65MPHBy() {return new TextField(AdminVehicleViewEnum.TXT_ZONE_LIMIT_EXPECTSROWNUM, 12);}
		public TextField notifyWhenExceeding70MPHBy() {return new TextField(AdminVehicleViewEnum.TXT_ZONE_LIMIT_EXPECTSROWNUM, 13);}
		public TextField notifyWhenExceeding75MPHBy() {return new TextField(AdminVehicleViewEnum.TXT_ZONE_LIMIT_EXPECTSROWNUM, 14);}
		
		
		
	}

	public class AdminVehicleViewTexts extends AdminBarTexts {
		public Text titleWindow(){	return new Text(AdminVehicleViewEnum.TITLE);				}
		public Text VIN(){			return new Text(AdminVehicleViewEnum.TXT_VALUE_VIN);		}
		public Text make(){			return new Text(AdminVehicleViewEnum.TXT_VALUE_MAKE);		}
		public Text model(){		return new Text(AdminVehicleViewEnum.TXT_VALUE_MODEL);		}
		public Text year(){			return new Text(AdminVehicleViewEnum.TXT_VALUE_YEAR);		}
		public Text color(){		return new Text(AdminVehicleViewEnum.TXT_VALUE_COLOR);		}
		public Text weight(){		return new Text(AdminVehicleViewEnum.TXT_VALUE_WEIGHT);		}
		public Text licence(){		return new Text(AdminVehicleViewEnum.TXT_VALUE_LICENCE);	}
		public Text state(){		return new Text(AdminVehicleViewEnum.TXT_VALUE_STATE);		}
		public Text odometer(){		return new Text(AdminVehicleViewEnum.TXT_VALUE_ODO);		}
		public Text zone(){			return new Text(AdminVehicleViewEnum.TXT_VALUE_ZONE);		}
		public Text eCallPhone(){	return new Text(AdminVehicleViewEnum.TXT_VALUE_ECALLPHONE);	}
		public Text autoLogOff(){	return new Text(AdminVehicleViewEnum.TXT_VALUE_AUTOLOGOFF);	}
	    
		public Text vehicleID(){  return new Text(AdminVehicleViewEnum.TXT_VALUE_VEHICLEID);  }
		public Text status(){     return new Text(AdminVehicleViewEnum.TXT_VALUE_STATUS);     }
		public Text team(){       return new Text(AdminVehicleViewEnum.TXT_VALUE_TEAM);       }
		public Text driver(){     return new Text(AdminVehicleViewEnum.TXT_VALUE_DRIVER);     }
		public Text product(){    return new Text(AdminVehicleViewEnum.TXT_VALUE_PRODUCT);    }
		public Text device(){     return new Text(AdminVehicleViewEnum.TXT_VALUE_DEVICE);     }
	    
		public Text hardAccel(){  return new Text(AdminVehicleViewEnum.TXT_HARD_ACCEL);       }
		public Text hardBrake(){  return new Text(AdminVehicleViewEnum.TXT_HARD_BRAKE);       }
		public Text hardBump(){   return new Text(AdminVehicleViewEnum.TXT_HARD_BUMP);        }
		public Text unsafeTurn(){ return new Text(AdminVehicleViewEnum.TXT_UNSAFE_TURN);      }
	}

	public AdminVehicleViewButtons _button() {
		return new AdminVehicleViewButtons();
	}

	public AdminVehicleViewDropDowns _dropDown() {
		return new AdminVehicleViewDropDowns();
	}

	public AdminVehicleViewLinks _link() {
		return new AdminVehicleViewLinks();
	}

	public AdminVehicleViewTexts _text() {
		return new AdminVehicleViewTexts();
	}

	public AdminVehicleViewTextFields _textField() {
		return new AdminVehicleViewTextFields();
	}
    @Override
    public String getExpectedPath() {
        return AdminVehicleViewEnum.DEFAULT_URL.getURL();
    }
    @Override
    @Deprecated
	/**
	 * PageAdminVehicleView's .load method cannot be used without specifying a vehicleID
	 * 
	 * @deprecated use {@link com.inthinc.pro.selenium.pageObjects.PageAdminVehicleView#load(Integer)}
	 */
    public AbstractPage load(){
    	addError("PageAdminVehicleView.load()", "This page cannot be loaded without more information.  Please supply an (Integer vehicleID)", ErrorLevel.FATAL);
		return null;
    }
    public PageAdminVehicleView load(Integer vehicleID) {
    	open(AdminVehicleViewEnum.DEFAULT_URL, vehicleID);
    	return this;
    }
}
