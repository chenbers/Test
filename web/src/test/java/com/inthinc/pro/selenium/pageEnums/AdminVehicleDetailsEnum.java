package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.automation.utils.Id;
import com.inthinc.pro.automation.utils.Xpath;

public enum AdminVehicleDetailsEnum implements SeleniumEnums {
    
    DEFAULT_URL(appUrl + "/admin/vehicle/"),
    
    TITLE("Admin - *** Details", Xpath.start().span(Id.clazz("admin")).toString()),
    
    LINK_BACK_TO_VEHICLES("< Back to Vehicles", "vehicleForm:vehicleCancel1"),
    
    BTN_DELETE_TOP("Delete"/*, "vehicleForm:vehicleDelete1"*/, "vehicleForm:vehicleDelete2"), //TODO: determine WHY failover is NOT working (getting a SeleniumException: Element is not currently visible and so may not be interacted with... on the FIRST id... should SKIP that and try the second, but it is NOT)
    BTN_EDIT_TOP("Edit", "vehicleForm:vehicleEdit1", "vehicleForm:vehicleEdit2"),
    
    //DETAILS
    TAB_DETAILS("Details", "vehicleForm:details_lbl"),

    TXT_LABEL_VIN("VIN:"				,"//td[@id='vehicleForm:details']/table/tbody/tr/td/table/tbody/tr/td[1]/table/tbody/tr[1]/td[1]"),
    TXT_VALUE_VIN(""					,"//td[@id='vehicleForm:details']/table/tbody/tr/td/table/tbody/tr/td[1]/table/tbody/tr[1]/td[2]"),
    TXT_LABEL_MAKE("Make:"				,"//td[@id='vehicleForm:details']/table/tbody/tr/td/table/tbody/tr/td[1]/table/tbody/tr[2]/td[1]"),
    TXT_VALUE_MAKE(""					,"//td[@id='vehicleForm:details']/table/tbody/tr/td/table/tbody/tr/td[1]/table/tbody/tr[2]/td[2]"),
    TXT_LABEL_MODEL("Model:"			,"//td[@id='vehicleForm:details']/table/tbody/tr/td/table/tbody/tr/td[1]/table/tbody/tr[3]/td[1]"),
    TXT_VALUE_MODEL(""					,"//td[@id='vehicleForm:details']/table/tbody/tr/td/table/tbody/tr/td[1]/table/tbody/tr[3]/td[2]"),
    TXT_LABEL_YEAR("Year:"				,"//td[@id='vehicleForm:details']/table/tbody/tr/td/table/tbody/tr/td[1]/table/tbody/tr[4]/td[1]"),
    TXT_VALUE_YEAR(""					,"//td[@id='vehicleForm:details']/table/tbody/tr/td/table/tbody/tr/td[1]/table/tbody/tr[4]/td[2]"),
    TXT_LABEL_COLOR("Color:"			,"//td[@id='vehicleForm:details']/table/tbody/tr/td/table/tbody/tr/td[1]/table/tbody/tr[5]/td[1]"),
    TXT_VALUE_COLOR(""					,"//td[@id='vehicleForm:details']/table/tbody/tr/td/table/tbody/tr/td[1]/table/tbody/tr[5]/td[2]"),
    TXT_LABEL_WEIGHT("Weight:"			,"//td[@id='vehicleForm:details']/table/tbody/tr/td/table/tbody/tr/td[1]/table/tbody/tr[6]/td[1]"),
    TXT_VALUE_WEIGHT(""					,"//td[@id='vehicleForm:details']/table/tbody/tr/td/table/tbody/tr/td[1]/table/tbody/tr[6]/td[2]"),
    TXT_LABEL_LICENCE("License #:"		,"//td[@id='vehicleForm:details']/table/tbody/tr/td/table/tbody/tr/td[1]/table/tbody/tr[7]/td[1]"),
    TXT_VALUE_LICENCE(""				,"//td[@id='vehicleForm:details']/table/tbody/tr/td/table/tbody/tr/td[1]/table/tbody/tr[7]/td[2]"),
    TXT_LABEL_STATE("State:"			,"//td[@id='vehicleForm:details']/table/tbody/tr/td/table/tbody/tr/td[1]/table/tbody/tr[8]/td[1]"),
    TXT_VALUE_STATE(""					,"//td[@id='vehicleForm:details']/table/tbody/tr/td/table/tbody/tr/td[1]/table/tbody/tr[8]/td[2]"),
    TXT_LABEL_ODO("Odometer"			,"//td[@id='vehicleForm:details']/table/tbody/tr/td/table/tbody/tr/td[1]/table/tbody/tr[9]/td[1]"),
    TXT_VALUE_ODO(""					,"//td[@id='vehicleForm:details']/table/tbody/tr/td/table/tbody/tr/td[1]/table/tbody/tr[9]/td[2]"),
    TXT_LABEL_ZONE("Zone Type:"			,"//td[@id='vehicleForm:details']/table/tbody/tr/td/table/tbody/tr/td[1]/table/tbody/tr[10]/td[1]"),
    TXT_VALUE_ZONE(""					,"//td[@id='vehicleForm:details']/table/tbody/tr/td/table/tbody/tr/td[1]/table/tbody/tr[10]/td[2]"),
    TXT_LABEL_ECALLPHONE("E-Call Phone:","//td[@id='vehicleForm:details']/table/tbody/tr/td/table/tbody/tr/td[1]/table/tbody/tr[11]/td[1]"),
    TXT_VALUE_ECALLPHONE(""				,"//td[@id='vehicleForm:details']/table/tbody/tr/td/table/tbody/tr/td[1]/table/tbody/tr[11]/td[2]"),
    TXT_LABEL_AUTOLOGOFF("Auto Log Off:","//td[@id='vehicleForm:details']/table/tbody/tr/td/table/tbody/tr/td[1]/table/tbody/tr[12]/td[1]"),
    TXT_VALUE_AUTOLOGOFF(""				,"//td[@id='vehicleForm:details']/table/tbody/tr/td/table/tbody/tr/td[1]/table/tbody/tr[12]/td[2]"),
    
    TXT_LABEL_SPEED(null				, "//*[@id='edit-form:speeedSettingsPanel']/table/tbody/tr/td[1]/div[1]"),
    TXT_LABEL_SENSITIVITY(null			, "//*[@id='edit-form:speeedSettingsPanel']/table/tbody/tr/td[3]/div[1]"),
    
    TXT_LABEL_VEHICLEID("Vehicle ID:"	,"//td[@id='vehicleForm:details']/table/tbody/tr/td/table/tbody/tr/td[3]/table[1]/tbody/tr[1]/td[1]"),
    TXT_VALUE_VEHICLEID(null			,"//td[@id='vehicleForm:details']/table/tbody/tr/td/table/tbody/tr/td[3]/table[1]/tbody/tr[1]/td[2]"),
    TXT_LABEL_STATUS("Status:"			,"//td[@id='vehicleForm:details']/table/tbody/tr/td/table/tbody/tr/td[3]/table[1]/tbody/tr[2]/td[1]"),
    TXT_VALUE_STATUS(""					,"//td[@id='vehicleForm:details']/table/tbody/tr/td/table/tbody/tr/td[3]/table[1]/tbody/tr[2]/td[2]"),
    TXT_LABEL_TEAM("Team"				,"//td[@id='vehicleForm:details']/table/tbody/tr/td/table/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[1]"),
    TXT_VALUE_TEAM(""					,"//td[@id='vehicleForm:details']/table/tbody/tr/td/table/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]"),
    TXT_LABEL_DRIVER("Assigned Driver:"	,"//td[@id='vehicleForm:details']/table/tbody/tr/td/table/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[1]"),
    TXT_VALUE_DRIVER(""					,"//td[@id='vehicleForm:details']/table/tbody/tr/td/table/tbody/tr/td[3]/table[2]/tbody/tr[2]/td[2]"),
    TXT_LABEL_PRODUCT("Product:"		,"//td[@id='vehicleForm:details']/table/tbody/tr/td/table/tbody/tr/td[3]/table[3]/tbody/tr[1]/td[1]"),
    TXT_VALUE_PRODUCT(""				,"//td[@id='vehicleForm:details']/table/tbody/tr/td/table/tbody/tr/td[3]/table[3]/tbody/tr[1]/td[2]"),
    TXT_LABEL_DEVICE("Assigned Device:"	,"//td[@id='vehicleForm:details']/table/tbody/tr/td/table/tbody/tr/td[3]/table[3]/tbody/tr[2]/td[1]"),
    TXT_VALUE_DEVICE(""					,"//td[@id='vehicleForm:details']/table/tbody/tr/td/table/tbody/tr/td[3]/table[3]/tbody/tr[2]/td[2]"),
    

    //SPEED AND SENSITIVITY
    TXT_ZONE_LIMIT_EXPECTSROWNUM(""
    		,"vehicleForm:speed###Input" //insert 0 to get first row, 14 to get 15th row
    		,"edit-form:speed###Input"
    		//,"id('vehicleForm:speeedSettingsPanel')/table/tbody/tr/td[1]/table/tbody/tr[###]/td[2]/table/tbody/tr/td[3]/input"//insert 2 to get first row, 16 to get 15th row
    		),
    TXT_HARD_ACCEL(""					, "hardAccelerationLevel"),
    TXT_HARD_BRAKE(""					, "hardBrakeLevel"),
    TXT_HARD_BUMP(""					, "hardVerticalLevel"),
    TXT_UNSAFE_TURN(""					, "hardTurnLevel"),
    TXT_IDLING_THRESHOLD(""				, "idlingThreshold"),

    TXTFIELD_VIN(null          ,"edit-form:editVehicle-vin"),
    TXTFIELD_MAKE(""           ,"edit-form:editVehicle-make"),
    TXTFIELD_MODEL(""          ,"edit-form:editVehicle-model"),
    TXTFIELD_COLOR(""          ,"edit-form:editVehicle-color"),
    TXTFIELD_WEIGHT(""         ,"edit-form:editVehicle-weight"),
    TXTFIELD_LICENCE(""        ,"edit-form:editVehicle-license"),
    TXTFIELD_ODO(""            ,"edit-form:editVehicle-odometer"),
    TXTFIELD_ECALLPHONE(""     ,"edit-form:editVehicle-ephone"),
    TXTFIELD_AUTOLOGOFF(""     ,"edit-form:editVehicle-autoLogoffInput"),
    TXTFIELD_VEHICLEID(null    ,"edit-form:editVehicle-name"),
    
    DROPDOWN_YEAR(""           ,"edit-form:editVehicle-year"),
    DROPDOWN_STATE(""          ,"edit-form:editVehicle-state"),
    DROPDOWN_STATUS(""         ,"edit-form:editVehicle-status"),
    DROPDOWN_ZONE(null         ,"//*[@id='edit-form:editVehicle-type']"),
    DROPDOWN_DOT(null 		   ,"//*[@id='edit-form:editVehicle-DOT']"),
    
    DHXDROP_TEAM(""            ,"edit-form:editVehicle-groupID"),

    LINK_ASSIGN_DRIVER(""      ,"editVehicle-chooseDriver"),

    CHECKBOX_IFTA(null		   ,"edit-form:editVehicle-ifta"),
    CHECKBOX_IDLE_MENTOR(""    ,"vehicleForm:vehicle-idleBuzzer","edit-form:editVehicle-idleBuzzer"),
    
    TAB_SPEED_AND_SENSITIVITY("Speed & Sensitivity", "//td[@id='vehicleForm:speedSensitivity_lbl']", "//td[@id='edit-form:speedSensitivity_lbl']"),
  	TAB_WIRELINE("Wireline", "//td[@id='vehicleForm:wirelineCmds_lbl']"),
    
    SLIDER_AUTOLOGOFF(""       ,"edit-form:editVehicle-autoLogoff"),
    SLIDER_IDLING_THRESHOLD("", "vehicleForm:vehicle-idlingThresholdTrack"),
    ;

    private String text, url;
    private String[] IDs;
    
    private AdminVehicleDetailsEnum(String url){
    	this.url = url;
    }
    private AdminVehicleDetailsEnum(String text, String ...IDs){
        this.text=text;
    	this.IDs = IDs;
    }

    @Override
    public String[] getIDs() {
        return IDs;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public String getURL() {
        return url;
    }
}
