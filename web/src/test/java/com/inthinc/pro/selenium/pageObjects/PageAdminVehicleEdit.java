package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.CheckBox;
import com.inthinc.pro.automation.elements.DHXDropDown;
import com.inthinc.pro.automation.elements.DropDown;
import com.inthinc.pro.automation.elements.Text;
import com.inthinc.pro.automation.elements.TextButton;
import com.inthinc.pro.automation.elements.TextDropDownLabel;
import com.inthinc.pro.automation.elements.TextField;
import com.inthinc.pro.automation.elements.TextFieldError;
import com.inthinc.pro.automation.elements.TextFieldLabel;
import com.inthinc.pro.automation.elements.TextFieldWithSpinner;
import com.inthinc.pro.automation.elements.TextLabel;
import com.inthinc.pro.automation.elements.TextLink;
import com.inthinc.pro.selenium.pageEnums.AdminVehicleEdit;


public class PageAdminVehicleEdit extends AdminBar {
    String currentPage = "vehicle";
    String view = "edit";
    
	public PageAdminVehicleEdit(){
	    
	}
	
	public AdminVehicleEditPopUps _popUp(){
        return new AdminVehicleEditPopUps();
    }
    
	public class AdminVehicleEditPopUps extends AdminBarPopUps{
	    
	}

	
	public class AdminVehicleEditButtons extends AdminBarButtons {
	    public TextButton cancelTop() {    return new TextButton(AdminVehicleEdit.BTN_CANCEL_TOP); }
        public TextButton saveTop() {      return new TextButton(AdminVehicleEdit.BTN_SAVE_TOP);   }
        public TextButton cancelBottom() { return new TextButton(AdminVehicleEdit.BTN_CANCEL_BOTTOM); }
        public TextButton saveBottom() {   return new TextButton(AdminVehicleEdit.BTN_SAVE_BOTTOM);   }
        public TextButton killButton() {   return new TextButton(AdminVehicleEdit.KILL_MOTOR_SEND);   }
        public TextButton doorButton() {   return new TextButton(AdminVehicleEdit.DOOR_ALARM_SEND);   }
        
	}

	public class AdminVehicleEditDropDowns extends AdminBarDropDowns {
        public DHXDropDown team() { return new DHXDropDown(AdminVehicleEdit.DHXDROP_TEAM);}
        public DropDown year(){     return new DropDown(AdminVehicleEdit.DROPDOWN_YEAR);       }
        public DropDown state(){    return new DropDown(AdminVehicleEdit.DROPDOWN_STATE);      }
        public DropDown zone(){     return new DropDown(AdminVehicleEdit.DROPDOWN_ZONE);       }
        public DropDown status(){   return new DropDown(AdminVehicleEdit.DROPDOWN_STATUS);     }
        public DropDown DOT(){      return new DropDown(AdminVehicleEdit.DOT_DROPDOWN);        }
        public DropDown killMotor(){return new DropDown(AdminVehicleEdit.KILL_MOTOR_DROPDOWN); }
        public DropDown doorAlarm(){return new DropDown(AdminVehicleEdit.DOOR_ALARM_DROPDOWN); }

	}

	public class AdminVehicleEditLinks extends AdminBarLinks {
	    public TextLink detailsTab() {             return new TextLink(AdminVehicleEdit.TAB_DETAILS);}
	    public TextLink speedAndSensitivityTab() { return new TextLink(AdminVehicleEdit.TAB_SPEED_AND_SENSITIVITY);}
	    public TextLink assignDriver() {           return new TextLink(AdminVehicleEdit.LINK_ASSIGN_DRIVER);}
	}
	
	
	public class AdminVehicleCheckBoxes {
	    public CheckBox idleMentoring() {return new CheckBox(AdminVehicleEdit.CHECKBOX_IDLE_MENTOR);}
	    public CheckBox iftaCheckbox()  {return new CheckBox(AdminVehicleEdit.IFTA_CHECKBOX);}
	}

	public class AdminVehicleEditTextFields extends AdminBarTextFields {

		public TextField notifyWhenExceedingBy(Integer limit) {return new TextField(AdminVehicleEdit.TXT_ZONE_LIMIT_EXPECTSROWNUM, (limit/5));}
		public TextField notifyWhenExceeding05MPHBy() {return new TextField(AdminVehicleEdit.TXT_ZONE_LIMIT_EXPECTSROWNUM, 1);}
		public TextField notifyWhenExceeding10MPHBy() {return new TextField(AdminVehicleEdit.TXT_ZONE_LIMIT_EXPECTSROWNUM, 2);}
		public TextField notifyWhenExceeding15MPHBy() {return new TextField(AdminVehicleEdit.TXT_ZONE_LIMIT_EXPECTSROWNUM, 3);}
		public TextField notifyWhenExceeding20MPHBy() {return new TextField(AdminVehicleEdit.TXT_ZONE_LIMIT_EXPECTSROWNUM, 4);}
		public TextField notifyWhenExceeding25MPHBy() {return new TextField(AdminVehicleEdit.TXT_ZONE_LIMIT_EXPECTSROWNUM, 5);}
		public TextField notifyWhenExceeding30MPHBy() {return new TextField(AdminVehicleEdit.TXT_ZONE_LIMIT_EXPECTSROWNUM, 6);}
		public TextField notifyWhenExceeding35MPHBy() {return new TextField(AdminVehicleEdit.TXT_ZONE_LIMIT_EXPECTSROWNUM, 7);}
		public TextField notifyWhenExceeding40MPHBy() {return new TextField(AdminVehicleEdit.TXT_ZONE_LIMIT_EXPECTSROWNUM, 8);}
		public TextField notifyWhenExceeding45MPHBy() {return new TextField(AdminVehicleEdit.TXT_ZONE_LIMIT_EXPECTSROWNUM, 9);}
		public TextField notifyWhenExceeding50MPHBy() {return new TextField(AdminVehicleEdit.TXT_ZONE_LIMIT_EXPECTSROWNUM, 10);}
		public TextField notifyWhenExceeding55MPHBy() {return new TextField(AdminVehicleEdit.TXT_ZONE_LIMIT_EXPECTSROWNUM, 11);}
		public TextField notifyWhenExceeding60MPHBy() {return new TextField(AdminVehicleEdit.TXT_ZONE_LIMIT_EXPECTSROWNUM, 12);}
		public TextField notifyWhenExceeding65MPHBy() {return new TextField(AdminVehicleEdit.TXT_ZONE_LIMIT_EXPECTSROWNUM, 13);}
		public TextField notifyWhenExceeding70MPHBy() {return new TextField(AdminVehicleEdit.TXT_ZONE_LIMIT_EXPECTSROWNUM, 14);}
		public TextField notifyWhenExceeding75MPHBy() {return new TextField(AdminVehicleEdit.TXT_ZONE_LIMIT_EXPECTSROWNUM, 15);}
		
		public TextField notifyOnHardAccel(){         return new TextField(AdminVehicleEdit.TXTFIELD_HARD_ACCEL);}
		public TextField notifyOnHardBrake(){         return new TextField(AdminVehicleEdit.TXTFIELD_HARD_BRAKE);}
		public TextField notifyOnHardBump() {         return new TextField(AdminVehicleEdit.TXTFIELD_HARD_BUMP);}
		public TextField notifyOnUnsafeTurn() {       return new TextField(AdminVehicleEdit.TXTFIELD_UNSAFE_TURN);}
		public TextField notifyOnIdlingTimeout() {    return new TextField(AdminVehicleEdit.TXTFIELD_IDLING_THRESHOLD);}
		
		public TextField VIN(){          return new TextField(AdminVehicleEdit.TXTFIELD_VIN);        }
        public TextField make(){         return new TextField(AdminVehicleEdit.TXTFIELD_MAKE);       }
        public TextField model(){        return new TextField(AdminVehicleEdit.TXTFIELD_MODEL);      }
        public TextField color(){        return new TextField(AdminVehicleEdit.TXTFIELD_COLOR);      }
        public TextField weight(){       return new TextField(AdminVehicleEdit.TXTFIELD_WEIGHT);     }
        public TextField licence(){      return new TextField(AdminVehicleEdit.TXTFIELD_LICENCE);    }
        public TextField odometer(){     return new TextField(AdminVehicleEdit.TXTFIELD_ODO);        }
        public TextField eCallPhone(){   return new TextField(AdminVehicleEdit.TXTFIELD_ECALLPHONE); }
        public TextField autoLogOff(){   return new TextField(AdminVehicleEdit.TXTFIELD_AUTOLOGOFF); }
        
        public TextField vehicleID(){    return new TextField(AdminVehicleEdit.TXTFIELD_VEHICLEID);  }  
        
        public TextField killMotorPass(){   return new TextField(AdminVehicleEdit.KILL_MOTOR_PASS);  }
        public TextField doorAlarmPass(){   return new TextField(AdminVehicleEdit.DOOR_ALARM_PASS);  }
        
        public TextFieldWithSpinner autoArmTime(){ return new TextFieldWithSpinner(AdminVehicleEdit.AUTO_ARM_TIME);} 
        
        public TextField maxSpeed(){   return new TextField(AdminVehicleEdit.MAX_SPEED); }
        public TextField speedBuffer(){return new TextField(AdminVehicleEdit.SPEED_BUFFER);}
        public TextField severeSpeeding(){return new TextField(AdminVehicleEdit.SEVERE_SPEEDING);}
		
	}

	public class AdminVehicleEditTexts extends AdminBarTexts {
		public Text titleWindow(){	    return new Text(AdminVehicleEdit.TITLE);				}
		public Text errorVIN(){         return new TextFieldError(AdminVehicleEdit.TXTFIELD_VIN);     }
		public Text labelVIN(){			return new TextFieldLabel(AdminVehicleEdit.TXTFIELD_VIN);		}
		public Text labelMake(){		return new TextFieldLabel(AdminVehicleEdit.TXTFIELD_MAKE);		}
		public Text labelModel(){		return new TextFieldLabel(AdminVehicleEdit.TXTFIELD_MODEL);		}
		public Text labelYear(){		return new TextDropDownLabel(AdminVehicleEdit.DROPDOWN_YEAR);		}
		public Text labelColor(){		return new TextFieldLabel(AdminVehicleEdit.TXTFIELD_COLOR);		}
		public Text labelWeight(){		return new TextFieldLabel(AdminVehicleEdit.TXTFIELD_WEIGHT);		}
		public Text labelLicence(){		return new TextFieldLabel(AdminVehicleEdit.TXTFIELD_LICENCE);	}
		public Text labelState(){		return new TextDropDownLabel(AdminVehicleEdit.DROPDOWN_STATE);		}
		public Text lableOdometer(){	return new TextFieldLabel(AdminVehicleEdit.TXTFIELD_ODO);		}
		public Text labelZone(){		return new TextDropDownLabel(AdminVehicleEdit.DROPDOWN_ZONE);		}
		public Text labelECallPhone(){	return new TextFieldLabel(AdminVehicleEdit.TXTFIELD_ECALLPHONE);	}
		public Text labelAutoLogOff(){	return new Text(AdminVehicleEdit.LABEL_AUTOLOGOFF);	}
	    
		public Text labelVehicleID(){   return new TextFieldLabel(AdminVehicleEdit.TXTFIELD_VEHICLEID);  }
		public Text labelStatus(){      return new TextDropDownLabel(AdminVehicleEdit.DROPDOWN_STATUS);     }
		public Text labelTeam(){        return new TextDropDownLabel(AdminVehicleEdit.DHXDROP_TEAM);       }
		public Text labelAssignedDriver(){      return new Text(AdminVehicleEdit.LABEL_ASSIGN_DRIVER);     }
		public Text labelProduct(){     return new TextLabel(AdminVehicleEdit.TEXT_VALUE_PRODUCT);    }
		public Text labelDevice(){      return new TextLabel(AdminVehicleEdit.TEXT_VALUE_ASSIGNED_DEVICE);     }
		
		public Text valueAssignedDriver(){return new Text(AdminVehicleEdit.TEXT_ASSIGN_DRIVER); }
        public Text valueProduct(){     return new Text(AdminVehicleEdit.TEXT_VALUE_PRODUCT);    }
        public Text valueDevice(){      return new Text(AdminVehicleEdit.TEXT_VALUE_ASSIGNED_DEVICE);     }
	    
		public Text labelHardAccel(){   return new Text(AdminVehicleEdit.TXT_HARD_ACCEL);       }
		public Text labelHardBrake(){   return new Text(AdminVehicleEdit.TXT_HARD_BRAKE);       }
		public Text labelHardBump(){    return new Text(AdminVehicleEdit.TXT_HARD_BUMP);        }
		public Text labelUnsafeTurn(){  return new Text(AdminVehicleEdit.TXT_UNSAFE_TURN);      }
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
	public AdminVehicleCheckBoxes _checkBox() {
	    return new AdminVehicleCheckBoxes();
	}
    @Override
    public String getExpectedPath() {
        return AdminVehicleEdit.DEFAULT_URL.getURL();
    }
}
