package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.DhxDropDown;
import com.inthinc.pro.automation.elements.DropDown;
import com.inthinc.pro.automation.elements.Text;
import com.inthinc.pro.automation.elements.TextButton;
import com.inthinc.pro.automation.elements.TextField;
import com.inthinc.pro.automation.elements.TextLink;
import com.inthinc.pro.automation.selenium.AbstractPage;
import com.inthinc.pro.selenium.pageEnums.AdminVehicleViewEnum;
import com.inthinc.pro.selenium.pageEnums.AdminVehiclesEnum;

public class PageAdminVehicleEdit extends AdminBar {
	
	public PageAdminVehicleEdit(){
		url = AdminVehicleViewEnum.DEFAULT_URL;
//		checkMe.add(AdminVehiclesEnum.BATCH_EDIT);
//		checkMe.add(AdminVehiclesEnum.DELETE);
//		checkMe.add(AdminVehiclesEnum.EDIT_COLUMNS_LINK);
	}
	
	public AdminVehicleEditPopUps _popUp(){
        return new AdminVehicleEditPopUps();
    }
    
	public class AdminVehicleEditPopUps extends AdminBarPopUps{
	    
	}

	
	public class AdminVehicleEditButtons extends AdminBarButtons {
	    public TextButton cancelTop() {    return new TextButton(AdminVehicleViewEnum.BTN_CANCEL_TOP); }
        public TextButton saveTop() {      return new TextButton(AdminVehicleViewEnum.BTN_SAVE_TOP);   }
        public TextButton cancelBottom() { return new TextButton(AdminVehicleViewEnum.BTN_CANCEL_BOTTOM); }
        public TextButton saveBottom() {   return new TextButton(AdminVehicleViewEnum.BTN_SAVE_BOTTOM);   }
        
	}

	public class AdminVehicleEditDropDowns extends AdminBarDropDowns {
        public DhxDropDown team() { return new DhxDropDown(AdminVehicleViewEnum.DHXDROP_TEAM);}
        public DropDown year(){     return new DropDown(AdminVehicleViewEnum.DROPDOWN_YEAR);       }
        public DropDown state(){    return new DropDown(AdminVehicleViewEnum.DROPDOWN_STATE);      }
        public DropDown zone(){     return new DropDown(AdminVehicleViewEnum.DROPDOWN_ZONE);       }
        public DropDown status(){   return new DropDown(AdminVehicleViewEnum.DROPDOWN_STATUS);     }

	}

	public class AdminVehicleEditLinks extends AdminBarLinks {
	    public TextLink detailsTab() {             return new TextLink(AdminVehicleViewEnum.TAB_DETAILS);}
	    public TextLink speedAndSensitivityTab() { return new TextLink(AdminVehicleViewEnum.TAB_SPEED_AND_SENSITIVITY);}
	    public TextLink backToVehicles() {         return new TextLink(AdminVehicleViewEnum.LINK_BACK_TO_VEHICLES);}
	    public TextLink assignDriver() {           return new TextLink(AdminVehicleViewEnum.LINK_ASSIGN_DRIVER);}
	}

	public class AdminVehicleEditTextFields extends AdminBarTextFields {
		public TextField tableSearch() {return new TextField(AdminVehiclesEnum.SEARCH_TEXT_FIELD);}
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
		
		public TextField notifyOnHardAccel(){         return new TextField(AdminVehicleViewEnum.TXTFIELD_HARD_ACCEL);}
		public TextField notifyOnHardBrake(){         return new TextField(AdminVehicleViewEnum.TXTFIELD_HARD_BRAKE);}
		public TextField notifyOnHardBump() {         return new TextField(AdminVehicleViewEnum.TXTFIELD_HARD_BUMP);}
		public TextField notifyOnUnsafeTurn() {       return new TextField(AdminVehicleViewEnum.TXTFIELD_UNSAFE_TURN);}
		public TextField notifyOnIdlingTimeout() {    return new TextField(AdminVehicleViewEnum.TXTFIELD_IDLING_THRESHOLD);}
		
		public TextField VIN(){          return new TextField(AdminVehicleViewEnum.TXTFIELD_VIN);        }
        public TextField make(){         return new TextField(AdminVehicleViewEnum.TXTFIELD_MAKE);       }
        public TextField model(){        return new TextField(AdminVehicleViewEnum.TXTFIELD_MODEL);      }
        public TextField color(){        return new TextField(AdminVehicleViewEnum.TXTFIELD_COLOR);      }
        public TextField weight(){       return new TextField(AdminVehicleViewEnum.TXTFIELD_WEIGHT);     }
        public TextField licence(){      return new TextField(AdminVehicleViewEnum.TXTFIELD_LICENCE);    }
        public TextField odometer(){     return new TextField(AdminVehicleViewEnum.TXTFIELD_ODO);        }
        public TextField eCallPhone(){   return new TextField(AdminVehicleViewEnum.TXTFIELD_ECALLPHONE); }
        public TextField autoLogOff(){   return new TextField(AdminVehicleViewEnum.TXTFIELD_AUTOLOGOFF); }
        
        public TextField vehicleID(){  return new TextField(AdminVehicleViewEnum.TXTFIELD_VEHICLEID);  }
        
		
	}

	public class AdminVehicleEditTexts extends AdminBarTexts {
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

	public AdminVehicleEditButtons _button() {
		return new AdminVehicleEditButtons();
	}

	public AdminVehicleEditDropDowns _dropDown() {
		return new AdminVehicleEditDropDowns();
	}

	public AdminVehicleEditLinks _link() {
		return new AdminVehicleEditLinks();
	}

	public AdminVehicleEditTexts _text() {
		return new AdminVehicleEditTexts();
	}

	public AdminVehicleEditTextFields _textField() {
		return new AdminVehicleEditTextFields();
	}
    @Override
    public String getExpectedPath() {
        return AdminVehicleViewEnum.DEFAULT_URL.getURL();
    }
    @Override
    @Deprecated
	/**
	 * PageAdminVehicleEdit's .load method should not be used.  Please use UI elements to navigate to the Admin - Edit Vehicle page.
	 * 
	 * @deprecated 
	 */
    public AbstractPage load(){
    	addError("PageAdminVehicleView.load()", "This page cannot be loaded without more information.  Please supply an (Integer vehicleID)", ErrorLevel.FAIL);
		return null;
    }
}
