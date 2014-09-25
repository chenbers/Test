package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.CheckBox;
import com.inthinc.pro.automation.elements.DropDown;
import com.inthinc.pro.automation.elements.Text;
import com.inthinc.pro.automation.elements.TextButton;
import com.inthinc.pro.automation.elements.TextField;
import com.inthinc.pro.automation.elements.TextLink;
import com.inthinc.pro.automation.enums.ErrorLevel;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.automation.selenium.AbstractPage;
import com.inthinc.pro.selenium.pageEnums.AdminBarEnum;
import com.inthinc.pro.selenium.pageEnums.AdminVehicleDetailsEnum;

public class PageAdminVehicleDetails extends AdminBar {
    String currentPage = "vehicle";
    String view = "view";

    public PageAdminVehicleDetails() 					{url = AdminVehicleDetailsEnum.DEFAULT_URL;}

    public AdminVehicleViewPopUps _popUp() 				{return new AdminVehicleViewPopUps();}

    public AdminVehicleViewCheckBoxes _checkbox() 		{return new AdminVehicleViewCheckBoxes();}

    public class AdminVehicleViewPopUps extends AdminBarPopUps {
        public AdminVehicleViewPopUps() 				{super(currentPage);}
        public AdminDelete delete() 					{return new AdminDelete(false);}
    }

    public class AdminVehicleViewCheckBoxes {
        public CheckBox idleMentoring() 				{return new CheckBox(AdminVehicleDetailsEnum.CHECKBOX_IDLE_MENTOR);}
        public CheckBox ifta()							{return new CheckBox(AdminVehicleDetailsEnum.CHECKBOX_IFTA);}
    }

    public class AdminVehicleDetailsButtons extends AdminBarButtons {
        public TextButton delete() 						{return new TextButton(AdminVehicleDetailsEnum.BTN_DELETE_TOP);}
        public TextButton edit() 						{return new TextButton(AdminVehicleDetailsEnum.BTN_EDIT_TOP);}
    }

    public class AdminVehicleDetailsDropDowns extends AdminBarDropDowns {
    	public DropDown zoneType()						{return new DropDown(AdminVehicleDetailsEnum.DROPDOWN_ZONE);}
    	public DropDown dot()							{return new DropDown(AdminVehicleDetailsEnum.DROPDOWN_DOT);}
    }

    public class AdminVehicleDetailsLinks extends AdminBarLinks {
        public TextLink detailsTab() 					{return new TextLink(AdminVehicleDetailsEnum.TAB_DETAILS);}
        public TextLink speedAndSensitivityTab() 		{return new TextLink(AdminVehicleDetailsEnum.TAB_SPEED_AND_SENSITIVITY);}
        public TextLink backToVehicles() 				{return new TextLink(AdminBarEnum.GO_BACK, currentPage);}
    }

    public class AdminVehicleDetailsTextFields extends AdminBarTextFields {
        public TextField notifyWhenExceedingBy(Integer limit) {return new TextField(AdminVehicleDetailsEnum.TXT_ZONE_LIMIT_EXPECTSROWNUM, (limit / 5));}
        public TextField notifyWhenExceeding05MPHBy()	{return new TextField(AdminVehicleDetailsEnum.TXT_ZONE_LIMIT_EXPECTSROWNUM, 1);}
        public TextField notifyWhenExceeding10MPHBy()	{return new TextField(AdminVehicleDetailsEnum.TXT_ZONE_LIMIT_EXPECTSROWNUM, 2);}
        public TextField notifyWhenExceeding15MPHBy()	{return new TextField(AdminVehicleDetailsEnum.TXT_ZONE_LIMIT_EXPECTSROWNUM, 3);}
        public TextField notifyWhenExceeding20MPHBy()	{return new TextField(AdminVehicleDetailsEnum.TXT_ZONE_LIMIT_EXPECTSROWNUM, 4);}
        public TextField notifyWhenExceeding25MPHBy()	{return new TextField(AdminVehicleDetailsEnum.TXT_ZONE_LIMIT_EXPECTSROWNUM, 5);}
        public TextField notifyWhenExceeding30MPHBy()	{return new TextField(AdminVehicleDetailsEnum.TXT_ZONE_LIMIT_EXPECTSROWNUM, 6);}
        public TextField notifyWhenExceeding35MPHBy()	{return new TextField(AdminVehicleDetailsEnum.TXT_ZONE_LIMIT_EXPECTSROWNUM, 7);}
        public TextField notifyWhenExceeding40MPHBy()	{return new TextField(AdminVehicleDetailsEnum.TXT_ZONE_LIMIT_EXPECTSROWNUM, 8);}
        public TextField notifyWhenExceeding45MPHBy()	{return new TextField(AdminVehicleDetailsEnum.TXT_ZONE_LIMIT_EXPECTSROWNUM, 9);}
        public TextField notifyWhenExceeding50MPHBy()	{return new TextField(AdminVehicleDetailsEnum.TXT_ZONE_LIMIT_EXPECTSROWNUM, 10);}
        public TextField notifyWhenExceeding55MPHBy()	{return new TextField(AdminVehicleDetailsEnum.TXT_ZONE_LIMIT_EXPECTSROWNUM, 11);}
        public TextField notifyWhenExceeding60MPHBy()	{return new TextField(AdminVehicleDetailsEnum.TXT_ZONE_LIMIT_EXPECTSROWNUM, 12);}
        public TextField notifyWhenExceeding65MPHBy()	{return new TextField(AdminVehicleDetailsEnum.TXT_ZONE_LIMIT_EXPECTSROWNUM, 13);}
        public TextField notifyWhenExceeding70MPHBy()	{return new TextField(AdminVehicleDetailsEnum.TXT_ZONE_LIMIT_EXPECTSROWNUM, 14);}
        public TextField notifyWhenExceeding75MPHBy()	{return new TextField(AdminVehicleDetailsEnum.TXT_ZONE_LIMIT_EXPECTSROWNUM, 15);}
    }

    public class AdminVehicleDetailsTexts extends AdminBarTexts {
        public Text titleWindow() 						{return new Text(AdminVehicleDetailsEnum.TITLE);}
        public Text VIN() 								{return new Text(AdminVehicleDetailsEnum.TXT_VALUE_VIN);}
        public Text VinLabel()							{return new Text(AdminVehicleDetailsEnum.TXT_LABEL_VIN);}
        public Text make() 								{return new Text(AdminVehicleDetailsEnum.TXT_VALUE_MAKE);}
        public Text model() 							{return new Text(AdminVehicleDetailsEnum.TXT_VALUE_MODEL);}
        public Text year() 								{return new Text(AdminVehicleDetailsEnum.TXT_VALUE_YEAR);}
        public Text color() 							{return new Text(AdminVehicleDetailsEnum.TXT_VALUE_COLOR);}
        public Text weight() 							{return new Text(AdminVehicleDetailsEnum.TXT_VALUE_WEIGHT);}
        public Text licence() 							{return new Text(AdminVehicleDetailsEnum.TXT_VALUE_LICENCE);}
        public Text state() 							{return new Text(AdminVehicleDetailsEnum.TXT_VALUE_STATE);}
        public Text odometer() 							{return new Text(AdminVehicleDetailsEnum.TXT_VALUE_ODO);}
        public Text zone() 								{return new Text(AdminVehicleDetailsEnum.TXT_VALUE_ZONE);}
        public Text eCallPhone() 						{return new Text(AdminVehicleDetailsEnum.TXT_VALUE_ECALLPHONE);}
        public Text autoLogOff() 						{return new Text(AdminVehicleDetailsEnum.TXT_VALUE_AUTOLOGOFF);}
        public Text vehicleID() 						{return new Text(AdminVehicleDetailsEnum.TXT_VALUE_VEHICLEID);}
        public Text status() 							{return new Text(AdminVehicleDetailsEnum.TXT_VALUE_STATUS);}
        public Text team() 								{return new Text(AdminVehicleDetailsEnum.TXT_VALUE_TEAM);}
        public Text driver() 							{return new Text(AdminVehicleDetailsEnum.TXT_VALUE_DRIVER);}
        public Text product() 							{return new Text(AdminVehicleDetailsEnum.TXT_VALUE_PRODUCT);}
        public Text device() 							{return new Text(AdminVehicleDetailsEnum.TXT_VALUE_DEVICE);}
        public Text hardAccel() 						{return new Text(AdminVehicleDetailsEnum.TXT_HARD_ACCEL);}
        public Text hardBrake() 						{return new Text(AdminVehicleDetailsEnum.TXT_HARD_BRAKE);}
        public Text hardBump() 							{return new Text(AdminVehicleDetailsEnum.TXT_HARD_BUMP);}
        public Text unsafeTurn() 						{return new Text(AdminVehicleDetailsEnum.TXT_UNSAFE_TURN);}
        public Text idlingThreshold() 					{return new Text(AdminVehicleDetailsEnum.TXT_IDLING_THRESHOLD);}
        public Text lbl_SpeedSettings()					{return new Text(AdminVehicleDetailsEnum.TXT_LABEL_SPEED);}
        public Text lbl_SensitivitySettings()			{return new Text(AdminVehicleDetailsEnum.TXT_LABEL_SENSITIVITY);}
        public Text lbl_VIN()							{return new Text(AdminVehicleDetailsEnum.TXT_LABEL_VIN);}
        public Text lbl_vehicleID()						{return new Text(AdminVehicleDetailsEnum.TXT_LABEL_VEHICLEID);}
    }

    public AdminVehicleDetailsButtons _button()			{return new AdminVehicleDetailsButtons();}

    public AdminVehicleDetailsDropDowns _dropDown()		{return new AdminVehicleDetailsDropDowns();}

    public AdminVehicleDetailsLinks _link()				{return new AdminVehicleDetailsLinks();}

    public AdminVehicleDetailsTexts _text()				{return new AdminVehicleDetailsTexts();}

    public AdminVehicleDetailsTextFields _textField()	{return new AdminVehicleDetailsTextFields();}

    public PageAdminVehicleDetails load(Integer vehicleID) {open(AdminVehicleDetailsEnum.DEFAULT_URL, vehicleID);return this;}

    @Override
    public SeleniumEnums setUrl() {return AdminVehicleDetailsEnum.DEFAULT_URL;}

    @Override
    protected boolean checkIsOnPage() {
    	return _text().titleWindow().isPresent();
    }

//  @Override
//  @Deprecated
//  /**
//   * PageAdminVehicleView's .load method cannot be used without specifying a vehicleID
//   * 
//   * @deprecated use {@link com.inthinc.pro.selenium.pageObjects.PageAdminVehicleView#load(Integer)}
//   */
//  public AbstractPage load() {
//      addError("PageAdminVehicleView.load()", "This page cannot be loaded without more information.  Please supply an (Integer vehicleID)", ErrorLevel.FATAL);
//      return null;
//  }    
}
