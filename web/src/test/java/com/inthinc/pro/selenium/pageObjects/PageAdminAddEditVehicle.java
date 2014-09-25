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
import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.selenium.pageEnums.AdminVehicleEditEnum;
import com.inthinc.pro.selenium.pageObjects.PopUps.Email;


public class PageAdminAddEditVehicle extends AdminBar {
    String currentPage = "vehicle";
    String view = "edit";
    
	public PageAdminAddEditVehicle(){
	    
	}
	
	public AdminVehicleEditPopUps _popUp(){
        return new AdminVehicleEditPopUps();
    }
    
	public class AdminVehicleEditPopUps extends AdminBarPopUps{
		
        public AssignDriverToVehicle assign() {
            return new AssignDriverToVehicle();
        }
	    
	}

	
	public class AdminVehicleEditButtons extends AdminBarButtons {
	    public TextButton cancelTop() {    return new TextButton(AdminVehicleEditEnum.BTN_CANCEL_TOP); }
        public TextButton saveTop() {      return new TextButton(AdminVehicleEditEnum.BTN_SAVE_TOP);   }
        public TextButton cancelBottom() { return new TextButton(AdminVehicleEditEnum.BTN_CANCEL_BOTTOM); }
        public TextButton saveBottom() {   return new TextButton(AdminVehicleEditEnum.BTN_SAVE_BOTTOM);   }
        public TextButton killButton() {   return new TextButton(AdminVehicleEditEnum.KILL_MOTOR_SEND);   }
        public TextButton doorButton() {   return new TextButton(AdminVehicleEditEnum.DOOR_ALARM_SEND);   }
	}

	public class AdminVehicleEditDropDowns extends AdminBarDropDowns {
        public DHXDropDown team() { return new DHXDropDown(AdminVehicleEditEnum.DHXDROP_TEAM);}
        public DropDown year(){     return new DropDown(AdminVehicleEditEnum.DROPDOWN_YEAR);       }
        public DropDown state(){    return new DropDown(AdminVehicleEditEnum.DROPDOWN_STATE);      }
        public DropDown zone(){     return new DropDown(AdminVehicleEditEnum.DROPDOWN_ZONE);       }
        public DropDown status(){   return new DropDown(AdminVehicleEditEnum.DROPDOWN_STATUS);     }
        public DropDown DOT(){      return new DropDown(AdminVehicleEditEnum.DROPDOWN_DOT);        }
        public DropDown killMotor(){return new DropDown(AdminVehicleEditEnum.DROPDOWN_KILL_MOTOR); }
        public DropDown doorAlarm(){return new DropDown(AdminVehicleEditEnum.DROPDOWN_DOOR_ALARM); }
	}

	public class AdminVehicleEditLinks extends AdminBarLinks {
	    public TextLink detailsTab() {             return new TextLink(AdminVehicleEditEnum.TAB_DETAILS);}
	    public TextLink speedAndSensitivityTab() { return new TextLink(AdminVehicleEditEnum.TAB_SPEED_AND_SENSITIVITY);}
	    public TextLink assignDriver() {           return new TextLink(AdminVehicleEditEnum.LINK_ASSIGN_DRIVER);}
	}
	
	
	public class AdminVehicleCheckBoxes {
	    public CheckBox idleMentoring() {return new CheckBox(AdminVehicleEditEnum.CHECKBOX_IDLE_MENTOR);}
	    public CheckBox iftaCheckbox()  {return new CheckBox(AdminVehicleEditEnum.CHECKBOX_IFTA);}
	}

	public class AdminVehicleEditTextFields extends AdminBarTextFields {

		public TextField notifyWhenExceedingBy(Integer limit) {return new TextField(AdminVehicleEditEnum.TXT_ZONE_LIMIT_EXPECTSROWNUM, (limit/5));}
		public TextField notifyWhenExceeding05MPHBy() {return new TextField(AdminVehicleEditEnum.TXT_ZONE_LIMIT_EXPECTSROWNUM, 1);}
		public TextField notifyWhenExceeding10MPHBy() {return new TextField(AdminVehicleEditEnum.TXT_ZONE_LIMIT_EXPECTSROWNUM, 2);}
		public TextField notifyWhenExceeding15MPHBy() {return new TextField(AdminVehicleEditEnum.TXT_ZONE_LIMIT_EXPECTSROWNUM, 3);}
		public TextField notifyWhenExceeding20MPHBy() {return new TextField(AdminVehicleEditEnum.TXT_ZONE_LIMIT_EXPECTSROWNUM, 4);}
		public TextField notifyWhenExceeding25MPHBy() {return new TextField(AdminVehicleEditEnum.TXT_ZONE_LIMIT_EXPECTSROWNUM, 5);}
		public TextField notifyWhenExceeding30MPHBy() {return new TextField(AdminVehicleEditEnum.TXT_ZONE_LIMIT_EXPECTSROWNUM, 6);}
		public TextField notifyWhenExceeding35MPHBy() {return new TextField(AdminVehicleEditEnum.TXT_ZONE_LIMIT_EXPECTSROWNUM, 7);}
		public TextField notifyWhenExceeding40MPHBy() {return new TextField(AdminVehicleEditEnum.TXT_ZONE_LIMIT_EXPECTSROWNUM, 8);}
		public TextField notifyWhenExceeding45MPHBy() {return new TextField(AdminVehicleEditEnum.TXT_ZONE_LIMIT_EXPECTSROWNUM, 9);}
		public TextField notifyWhenExceeding50MPHBy() {return new TextField(AdminVehicleEditEnum.TXT_ZONE_LIMIT_EXPECTSROWNUM, 10);}
		public TextField notifyWhenExceeding55MPHBy() {return new TextField(AdminVehicleEditEnum.TXT_ZONE_LIMIT_EXPECTSROWNUM, 11);}
		public TextField notifyWhenExceeding60MPHBy() {return new TextField(AdminVehicleEditEnum.TXT_ZONE_LIMIT_EXPECTSROWNUM, 12);}
		public TextField notifyWhenExceeding65MPHBy() {return new TextField(AdminVehicleEditEnum.TXT_ZONE_LIMIT_EXPECTSROWNUM, 13);}
		public TextField notifyWhenExceeding70MPHBy() {return new TextField(AdminVehicleEditEnum.TXT_ZONE_LIMIT_EXPECTSROWNUM, 14);}
		public TextField notifyWhenExceeding75MPHBy() {return new TextField(AdminVehicleEditEnum.TXT_ZONE_LIMIT_EXPECTSROWNUM, 15);}
		
		public TextField notifyOnHardAccel()	{return new TextField(AdminVehicleEditEnum.TXTFIELD_HARD_ACCEL);}
		public TextField notifyOnHardBrake()	{return new TextField(AdminVehicleEditEnum.TXTFIELD_HARD_BRAKE);}
		public TextField notifyOnHardBump() 	{return new TextField(AdminVehicleEditEnum.TXTFIELD_HARD_BUMP);}
		public TextField notifyOnUnsafeTurn() 	{return new TextField(AdminVehicleEditEnum.TXTFIELD_UNSAFE_TURN);}
		public TextField notifyOnIdlingTimeout(){return new TextField(AdminVehicleEditEnum.TXTFIELD_IDLING_THRESHOLD);}
		
		public TextField VIN()				{return new TextField(AdminVehicleEditEnum.TXTFIELD_VIN);        }
        public TextField make()				{return new TextField(AdminVehicleEditEnum.TXTFIELD_MAKE);       }
        public TextField model()			{return new TextField(AdminVehicleEditEnum.TXTFIELD_MODEL);      }
        public TextField color()			{return new TextField(AdminVehicleEditEnum.TXTFIELD_COLOR);      }
        public TextField weight()			{return new TextField(AdminVehicleEditEnum.TXTFIELD_WEIGHT);     }
        public TextField licence()			{return new TextField(AdminVehicleEditEnum.TXTFIELD_LICENCE);    }
        public TextField odometer()			{return new TextField(AdminVehicleEditEnum.TXTFIELD_ODO);        }
        public TextField eCallPhone()		{return new TextField(AdminVehicleEditEnum.TXTFIELD_ECALLPHONE); }
        public TextField autoLogOff()		{return new TextField(AdminVehicleEditEnum.TXTFIELD_AUTOLOGOFF); }
        
        public TextField vehicleID()		{return new TextField(AdminVehicleEditEnum.TXTFIELD_VEHICLEID);  }  
        
        public TextField killMotorPass()	{return new TextField(AdminVehicleEditEnum.KILL_MOTOR_PASS);  }
        public TextField doorAlarmPass()	{return new TextField(AdminVehicleEditEnum.DOOR_ALARM_PASS);  }
        
        public TextFieldWithSpinner autoArmTime(){ return new TextFieldWithSpinner(AdminVehicleEditEnum.AUTO_ARM_TIME);} 
        
        public TextField maxSpeed()			{return new TextField(AdminVehicleEditEnum.MAX_SPEED); }
        public TextField speedBuffer()		{return new TextField(AdminVehicleEditEnum.SPEED_BUFFER);}
        public TextField severeSpeeding()	{return new TextField(AdminVehicleEditEnum.SEVERE_SPEEDING);}
		
	}

	public class AdminVehicleEditTexts extends AdminBarTexts {
		public Text titleWindow()			{return new Text(AdminVehicleEditEnum.TITLE);}
		public Text errorVIN()				{return new TextFieldError(AdminVehicleEditEnum.TXTFIELD_VIN);}
		public Text labelVIN()				{return new TextFieldLabel(AdminVehicleEditEnum.TXTFIELD_VIN);}
		public Text labelMake()				{return new TextFieldLabel(AdminVehicleEditEnum.TXTFIELD_MAKE);}
		public Text labelModel()			{return new TextFieldLabel(AdminVehicleEditEnum.TXTFIELD_MODEL);}
		public Text labelYear()				{return new TextDropDownLabel(AdminVehicleEditEnum.DROPDOWN_YEAR);}
		public Text labelColor()			{return new TextFieldLabel(AdminVehicleEditEnum.TXTFIELD_COLOR);}
		public Text labelWeight()			{return new TextFieldLabel(AdminVehicleEditEnum.TXTFIELD_WEIGHT);}
		public Text labelLicence()			{return new TextFieldLabel(AdminVehicleEditEnum.TXTFIELD_LICENCE);}
		public Text labelState()			{return new TextDropDownLabel(AdminVehicleEditEnum.DROPDOWN_STATE);}
		public Text lableOdometer()			{return new TextFieldLabel(AdminVehicleEditEnum.TXTFIELD_ODO);}
		public Text labelZone()				{return new TextDropDownLabel(AdminVehicleEditEnum.DROPDOWN_ZONE);}
		public Text labelECallPhone()		{return new TextFieldLabel(AdminVehicleEditEnum.TXTFIELD_ECALLPHONE);}
		public Text labelAutoLogOff()		{return new Text(AdminVehicleEditEnum.LABEL_AUTOLOGOFF);}
	    
		public Text labelVehicleID()		{return new TextFieldLabel(AdminVehicleEditEnum.TXTFIELD_VEHICLEID);}
		public Text labelStatus()			{return new TextDropDownLabel(AdminVehicleEditEnum.DROPDOWN_STATUS);}
		public Text labelTeam()				{return new TextDropDownLabel(AdminVehicleEditEnum.DHXDROP_TEAM);}
		public Text labelAssignedDriver()	{return new Text(AdminVehicleEditEnum.LABEL_ASSIGN_DRIVER);}
		
		public Text valueAssignedDriver()	{return new Text(AdminVehicleEditEnum.TXT_ASSIGNED_DRIVER);}
        public Text valueProduct()			{return new Text(AdminVehicleEditEnum.TXT_PRODUCT);}
        public Text valueDevice()			{return new Text(AdminVehicleEditEnum.TXT_DEVICE);}
	    
		public Text labelHardAccel()		{return new Text(AdminVehicleEditEnum.TXT_HARD_ACCEL);}
		public Text labelHardBrake()		{return new Text(AdminVehicleEditEnum.TXT_HARD_BRAKE);}
		public Text labelHardBump()			{return new Text(AdminVehicleEditEnum.TXT_HARD_BUMP);}
		public Text labelUnsafeTurn()		{return new Text(AdminVehicleEditEnum.TXT_UNSAFE_TURN);}
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
    public SeleniumEnums setUrl() {
        return AdminVehicleEditEnum.DEFAULT_URL;
    }

    @Override
    protected boolean checkIsOnPage() {
        return _button().cancelBottom().isPresent();
    }
}
