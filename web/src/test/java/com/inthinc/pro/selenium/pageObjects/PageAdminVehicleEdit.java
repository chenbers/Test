package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.CheckBox;
import com.inthinc.pro.automation.elements.DHXDropDown;
import com.inthinc.pro.automation.elements.DropDown;
import com.inthinc.pro.automation.elements.Text;
import com.inthinc.pro.automation.elements.TextButton;
import com.inthinc.pro.automation.elements.TextField;
import com.inthinc.pro.automation.elements.TextLink;
import com.inthinc.pro.automation.enums.ErrorLevel;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.automation.selenium.AbstractPage;
import com.inthinc.pro.selenium.pageEnums.AdminBarEnum;
import com.inthinc.pro.selenium.pageEnums.AdminVehicleEditEnum;
//import com.inthinc.pro.selenium.pageEnums.AdminVehiclesEnum;

public class PageAdminVehicleEdit extends AdminBar {
    String currentPage = "vehicleEdit";

    public PageAdminVehicleEdit() 						{url = AdminVehicleEditEnum.DEFAULT_URL;}

    public class AdminVehicleViewPopUps extends AdminBarPopUps {
        public AdminVehicleViewPopUps() 				{super(currentPage);}
        public AdminDelete delete() 					{return new AdminDelete(false);}
    }

    public class AdminVehicleEditCheckBoxes {
        public CheckBox idleMentoring() 				{return new CheckBox(AdminVehicleEditEnum.CHECKBOX_IDLE_MENTOR);}
        public CheckBox ifta()							{return new CheckBox(AdminVehicleEditEnum.CHECKBOX_IFTA);}
    }

    public class AdminVehicleEditButtons extends AdminBarButtons {
        public TextButton save() 						{return new TextButton(AdminVehicleEditEnum.BTN_SAVE_TOP);}
        public TextButton cancel() 						{return new TextButton(AdminVehicleEditEnum.BTN_CANCEL_TOP);}
        public TextButton saveBottom() 					{return new TextButton(AdminVehicleEditEnum.BTN_SAVE_BOTTOM);}
        public TextButton cancelBottom() 				{return new TextButton(AdminVehicleEditEnum.BTN_CANCEL_BOTTOM);}
    }

    public class AdminVehicleEditDropDowns extends AdminBarDropDowns {
    	public DropDown zoneType()						{return new DropDown(AdminVehicleEditEnum.DROPDOWN_ZONE);}
    	public DropDown dot()							{return new DropDown(AdminVehicleEditEnum.DROPDOWN_DOT);}
    	public DropDown year()							{return new DropDown(AdminVehicleEditEnum.DROPDOWN_YEAR);}
        public DropDown state() 						{return new DropDown(AdminVehicleEditEnum.DROPDOWN_STATE);}
        public DropDown status() 						{return new DropDown(AdminVehicleEditEnum.DROPDOWN_STATUS);}
        public DHXDropDown team() 						{return new DHXDropDown(AdminVehicleEditEnum.DHXDROP_TEAM);}
    }

    public class AdminVehicleEditLinks extends AdminBarLinks {
        public TextLink detailsTab() 					{return new TextLink(AdminVehicleEditEnum.TAB_DETAILS);}
        public TextLink speedAndSensitivityTab() 		{return new TextLink(AdminVehicleEditEnum.TAB_SPEED_AND_SENSITIVITY);}
        public TextLink backToVehicles() 				{return new TextLink(AdminBarEnum.GO_BACK, currentPage);}
    }

    public class AdminVehicleEditTextFields extends AdminBarTextFields {
        public TextField notifyWhenExceedingBy(Integer limit) {return new TextField(AdminVehicleEditEnum.TXT_ZONE_LIMIT_EXPECTSROWNUM, (limit / 5));}
        public TextField notifyWhenExceeding05MPHBy()	{return new TextField(AdminVehicleEditEnum.TXT_ZONE_LIMIT_EXPECTSROWNUM, 1);}
        public TextField notifyWhenExceeding10MPHBy()	{return new TextField(AdminVehicleEditEnum.TXT_ZONE_LIMIT_EXPECTSROWNUM, 2);}
        public TextField notifyWhenExceeding15MPHBy()	{return new TextField(AdminVehicleEditEnum.TXT_ZONE_LIMIT_EXPECTSROWNUM, 3);}
        public TextField notifyWhenExceeding20MPHBy()	{return new TextField(AdminVehicleEditEnum.TXT_ZONE_LIMIT_EXPECTSROWNUM, 4);}
        public TextField notifyWhenExceeding25MPHBy()	{return new TextField(AdminVehicleEditEnum.TXT_ZONE_LIMIT_EXPECTSROWNUM, 5);}
        public TextField notifyWhenExceeding30MPHBy()	{return new TextField(AdminVehicleEditEnum.TXT_ZONE_LIMIT_EXPECTSROWNUM, 6);}
        public TextField notifyWhenExceeding35MPHBy()	{return new TextField(AdminVehicleEditEnum.TXT_ZONE_LIMIT_EXPECTSROWNUM, 7);}
        public TextField notifyWhenExceeding40MPHBy()	{return new TextField(AdminVehicleEditEnum.TXT_ZONE_LIMIT_EXPECTSROWNUM, 8);}
        public TextField notifyWhenExceeding45MPHBy()	{return new TextField(AdminVehicleEditEnum.TXT_ZONE_LIMIT_EXPECTSROWNUM, 9);}
        public TextField notifyWhenExceeding50MPHBy()	{return new TextField(AdminVehicleEditEnum.TXT_ZONE_LIMIT_EXPECTSROWNUM, 10);}
        public TextField notifyWhenExceeding55MPHBy()	{return new TextField(AdminVehicleEditEnum.TXT_ZONE_LIMIT_EXPECTSROWNUM, 11);}
        public TextField notifyWhenExceeding60MPHBy()	{return new TextField(AdminVehicleEditEnum.TXT_ZONE_LIMIT_EXPECTSROWNUM, 12);}
        public TextField notifyWhenExceeding65MPHBy()	{return new TextField(AdminVehicleEditEnum.TXT_ZONE_LIMIT_EXPECTSROWNUM, 13);}
        public TextField notifyWhenExceeding70MPHBy()	{return new TextField(AdminVehicleEditEnum.TXT_ZONE_LIMIT_EXPECTSROWNUM, 14);}
        public TextField notifyWhenExceeding75MPHBy()	{return new TextField(AdminVehicleEditEnum.TXT_ZONE_LIMIT_EXPECTSROWNUM, 15);}
        public TextField VIN() 							{return new TextField(AdminVehicleEditEnum.TXTFIELD_VIN);}
        public TextField make() 						{return new TextField(AdminVehicleEditEnum.TXTFIELD_MAKE);}
        public TextField model() 						{return new TextField(AdminVehicleEditEnum.TXTFIELD_MODEL);}
        public TextField color() 						{return new TextField(AdminVehicleEditEnum.TXTFIELD_COLOR);}
        public TextField weight() 						{return new TextField(AdminVehicleEditEnum.TXTFIELD_WEIGHT);}
        public TextField licence() 						{return new TextField(AdminVehicleEditEnum.TXTFIELD_LICENCE);}
        public TextField odometer() 					{return new TextField(AdminVehicleEditEnum.TXTFIELD_ODO);}
        public TextField eCallPhone() 					{return new TextField(AdminVehicleEditEnum.TXTFIELD_ECALLPHONE);}
        public TextField autoLogOff() 					{return new TextField(AdminVehicleEditEnum.TXTFIELD_AUTOLOGOFF);}
        public TextField vehicleID() 					{return new TextField(AdminVehicleEditEnum.TXTFIELD_VEHICLEID);}
        public TextField zeroToTwentyFive()				{return new TextField(AdminVehicleEditEnum.TXTFIELD_ZERO_TO_25);}
        public TextField twentySixToFifty()				{return new TextField(AdminVehicleEditEnum.TXTFIELD_26_TO_50);}
        public TextField fiftyOneToSeventyFive()		{return new TextField(AdminVehicleEditEnum.TXTFIELD_51_TO_75);}
        public TextField maxSpeed()						{return new TextField(AdminVehicleEditEnum.TXTFIELD_MAX_SPEED);}
    }

    public class AdminVehicleEditTexts extends AdminBarTexts {
        public Text titleWindow() 						{return new Text(AdminVehicleEditEnum.TITLE);}
        public Text driver() 							{return new Text(AdminVehicleEditEnum.TXT_ASSIGNED_DRIVER);}
        public Text product() 							{return new Text(AdminVehicleEditEnum.TXT_PRODUCT);}
        public Text device() 							{return new Text(AdminVehicleEditEnum.TXT_DEVICE);}
        public Text hardAccel() 						{return new Text(AdminVehicleEditEnum.TXT_HARD_ACCEL);}
        public Text hardBrake() 						{return new Text(AdminVehicleEditEnum.TXT_HARD_BRAKE);}
        public Text hardBump() 							{return new Text(AdminVehicleEditEnum.TXT_HARD_BUMP);}
        public Text unsafeTurn() 						{return new Text(AdminVehicleEditEnum.TXT_UNSAFE_TURN);}
        public Text idlingThreshold() 					{return new Text(AdminVehicleEditEnum.TXT_IDLING_THRESHOLD);}
    }

    public AdminVehicleEditLinks _link()				{return new AdminVehicleEditLinks();}

    public AdminVehicleEditTexts _text()				{return new AdminVehicleEditTexts();}

    public AdminVehicleEditButtons _button()			{return new AdminVehicleEditButtons();}

    public AdminVehicleEditDropDowns _dropDown()		{return new AdminVehicleEditDropDowns();}

    public AdminVehicleEditCheckBoxes _checkBox() 		{return new AdminVehicleEditCheckBoxes();}

    public AdminVehicleEditTextFields _textField()		{return new AdminVehicleEditTextFields();}

    public PageAdminVehicleEdit load(Integer vehicleID) {open(AdminVehicleEditEnum.DEFAULT_URL, vehicleID);return this;}

    @Override
    public SeleniumEnums setUrl() 						{return AdminVehicleEditEnum.DEFAULT_URL;}

    @Override
    protected boolean checkIsOnPage() 					{return _text().titleWindow().isPresent();}
}
