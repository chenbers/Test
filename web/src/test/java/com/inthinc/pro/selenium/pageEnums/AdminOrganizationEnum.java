package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.interfaces.SeleniumEnums;

public enum AdminOrganizationEnum implements SeleniumEnums {
    
    TITLE("Admin - Organization", "//span[@class='admin']"),
    DEFAULT_URL("/app/admin/organization"),
    
    HEADER_TOP_GROUP(null, "//ul[@id='grid_nav']/li[2]"),
    
    DELETE(delete, "display-form:organizationView-groupViewDelete"),
    EDIT_BUTTON("Edit", "display-form:organizationView-groupViewEdit"),
    ADD("Add", "display-form:organizationView-groupViewAdd"),
    
    TOP_SAVE(save,"display-form:groupEditPanelEdit"),
    TOP_CANCEL(cancel,"display-form:groupEditPanelView"),
    BOTTOM_SAVE(save,"display-form:organization-groupEditEditEdit"),
    BOTTOM_CANCEL(cancel,"display-form:organization-groupEditEditView"),
    
    EDIT_LINK("edit", "display-form:organization-vehicleSummaryEdit"),
    
    EDIT_PARENT_GROUP("Parent Group:", "display-form:parent_group"),
    EDIT_GROUP_TYPE("Group Type:", "display-form:organization-group_type"),
    EDIT_NAME("Name:","display-form:organization-group_name"),
    EDIT_DESCRIPTION("Description:","//input[@id='display-form:organization-group_name']/../../../tr[2]/td[2]"),
    EDIT_MANAGER("Manager:","display-form:organization-group_manager"),
    EDIT_ADDRESS_1("Address 1:","display-form:organization-addr1"),
    EDIT_ADDRESS_2("Address 2:","display-form:organization-addr2"),
    EDIT_CITY("City:","display-form:organization-city"),
    EDIT_STATE("State:","display-form:organization-state"),
    EDIT_ZIP_CODE("Zip Code:","display-form:organization-zip"),
    EDIT_FIND_ADDRESS("Find Address:","organization-groupEdit-addressTextBox"),
    EDIT_LOCATE("Locate","display-form:organization-groupEdit-groupMapViewSearch"),
    EDIT_DOT_ADDRESS_TYPE("DOT:","display-form:editgroup-addr-type"),
    
    TEXT_ERROR("ERROR - The address is required when the DOT Address Type is set","display-form:message"),
    
    HEADER_SUMMARY(null, "//tr[1]/td/div[@class='add_section_title']"),
    HEADER_ADDRESS("Address", "//tr[2]/td/div[@class='add_section_title']"),
    HEADER_DEFAULT_MAP_VIEW("Default Map View", "//span/div[@class='add_section_title']"),
    HEADER_USER_INFORMATION("User Information", "//div[@class='add_section_title'][1]"),
    HEADER_DRIVER_INFORMATION("Driver Information", "//div[@class='add_section_title'][4]"),
    HEADER_DRIVER_ASSIGNMENTS("Driver Assignments", "//div[@class='add_section_title'][6]"),
    HEADER_VEHICLE_INFORMATION("Vehicle Information", "//div[@class='add_section_title'][1]"),
    HEADER_VEHICLE_PROFILE("Vehicle Profile", "//div[@class='add_section_title'][3]"),
    HEADER_VEHICLE_ASSIGNMENTS("Vehicle Assignments", "//div[@class='add_section_title'][5]"),
    HEADER_DEVICE_ASSIGNMENTS("Device Assignments", "//div[@class='add_section_title'][6]"),
    
    GROUP_TYPE("Group Type:", "//span[@id='display-form:contentPanel']/div/table/tbody/tr[1]/td/table/tbody/tr[1]/td[2]"),
    NAME("Name:", "//span[@id='display-form:contentPanel']/div/table/tbody/tr[1]/td/table/tbody/tr[2]/td[2]"),
    DESCRIPTION("Description:", "//span[@id='display-form:contentPanel']/div/table/tbody/tr[1]/td/table/tbody/tr[3]/td[2]"),
    MANAGER("Manager:", "//span[@id='display-form:contentPanel']/div/table/tbody/tr[1]/td/table/tbody/tr[4]/td[2]"),
    NUM_OF_VEHICLES("# of Vehicles:", "//span[@id='display-form:contentPanel']/div/table/tbody/tr[1]/td/table/tbody/tr[5]/td[2]"),
    NUM_OF_DRIVERS("# of Drivers:", "//span[@id='display-form:contentPanel']/div/table/tbody/tr[1]/td/table/tbody/tr[6]/td[2]"),
    
    ADDRESS_ONE("Address 1:", "//span[@id='display-form:contentPanel']/div/table/tbody/tr[2]/td/table/tbody/tr[1]/td[2]"),
    ADDRESS_TWO("Address 2:", "//span[@id='display-form:contentPanel']/div/table/tbody/tr[2]/td/table/tbody/tr[2]/td[2]"),
    CITY("City:", "//span[@id='display-form:contentPanel']/div/table/tbody/tr[2]/td/table/tbody/tr[3]/td[2]"),
    STATE("State:", "//span[@id='display-form:contentPanel']/div/table/tbody/tr[2]/td/table/tbody/tr[4]/td[2]"),
    ZIP_CODE("Zip Code:", "//span[@id='display-form:contentPanel']/div/table/tbody/tr[2]/td/table/tbody/tr[5]/td[2]"),
    
    FIRST_NAME("First Name:", "//span[@id='display-form:contentPanel']/div/table/tbody/tr[1]/td/table[1]/tbody/tr[1]/td[2]"),
    MIDDLE_NAME("Middle Name:", "//span[@id='display-form:contentPanel']/div/table/tbody/tr[1]/td/table[1]/tbody/tr[2]/td[2]"),
    LAST_NAME("Last Name:", "//span[@id='display-form:contentPanel']/div/table/tbody/tr[1]/td/table[1]/tbody/tr[3]/td[2]"),
    SUFFIX("Suffix:", "//span[@id='display-form:contentPanel']/div/table/tbody/tr[1]/td/table[1]/tbody/tr[4]/td[2]"),
    DOB("DOB:", "//span[@id='display-form:contentPanel']/div/table/tbody/tr[1]/td/table[1]/tbody/tr[5]/td[2]"),
    GENDER("Gender:", "//span[@id='display-form:contentPanel']/div/table/tbody/tr[1]/td/table[1]/tbody/tr[6]/td[2]"),
    
    DRIVER_LICENSE("Driver License #:", "//span[@id='display-form:contentPanel']/div/table/tbody/tr[1]/td/table[2]/tbody/tr[1]/td[2]"),
    DRIVER_CLASS("Class:", "//span[@id='display-form:contentPanel']/div/table/tbody/tr[1]/td/table[2]/tbody/tr[2]/td[2]"),
    DRIVER_STATE("State:", "//span[@id='display-form:contentPanel']/div/table/tbody/tr[1]/td/table[2]/tbody/tr[3]/td[2]"),
    DRIVER_EXPIRATION("Expiration:", "//span[@id='display-form:contentPanel']/div/table/tbody/tr[1]/td/table[2]/tbody/tr[4]/td[2]"),
    DRIVER_STATUS("Status:", "//span[@id='display-form:contentPanel']/div/table/tbody/tr[1]/td/table[2]/tbody/tr[5]/td[2]"),
    
    DRIVER_VEHICLE("Assigned Vehicle:", "//span[@id='display-form:contentPanel']/div/table/tbody/tr[1]/td/table[3]/tbody/tr[1]/td[2]"),
    DRIVER_DEVICE("Assigned Device:", "//span[@id='display-form:contentPanel']/div/table/tbody/tr[1]/td/table[3]/tbody/tr[2]/td[2]"),
    
    VEHICLE_PRODUCT("Product:", "//span[@id='display-form:contentPanel']/div/table/tbody/tr[1]/td/table[1]/tbody/tr[1]/td[2]"),
    VEHICLE_VIN("VIN:", "//span[@id='display-form:contentPanel']/div/table/tbody/tr[1]/td/table[1]/tbody/tr[2]/td[2]"),
    VEHICLE_MAKE("Make:", "//span[@id='display-form:contentPanel']/div/table/tbody/tr[1]/td/table[1]/tbody/tr[3]/td[2]"),
    VEHICLE_MODEL("Model:", "//span[@id='display-form:contentPanel']/div/table/tbody/tr[1]/td/table[1]/tbody/tr[4]/td[2]"),
    VEHICLE_YEAR("Year:", "//span[@id='display-form:contentPanel']/div/table/tbody/tr[1]/td/table[1]/tbody/tr[5]/td[2]"),
    VEHICLE_COLOR("Color:", "//span[@id='display-form:contentPanel']/div/table/tbody/tr[1]/td/table[1]/tbody/tr[6]/td[2]"),
    VEHICLE_ZONE_TYPE("Zone Type:", "//span[@id='display-form:contentPanel']/div/table/tbody/tr[1]/td/table[1]/tbody/tr[7]/td[2]"),
    VEHICLE_WEIGHT("Weight: (lbs)", "//span[@id='display-form:contentPanel']/div/table/tbody/tr[1]/td/table[1]/tbody/tr[8]/td[2]"),
    VEHICLE_LICENSE("License #:", "//span[@id='display-form:contentPanel']/div/table/tbody/tr[1]/td/table[1]/tbody/tr[9]/td[2]"),
    VEHICLE_STATE("State:", "//span[@id='display-form:contentPanel']/div/table/tbody/tr[1]/td/table[1]/tbody/tr[10]/td[2]"),
    VEHICLE_ECALL_PHONE("E-Call Phone:", "//span[@id='display-form:contentPanel']/div/table/tbody/tr[1]/td/table[11]/tbody/tr[1]/td[2]"),
    
    VEHICLE_ID("Vehicle ID:", "//span[@id='display-form:contentPanel']/div/table/tbody/tr[1]/td/table[2]/tbody/tr[1]/td[2]"),
    VEHICLE_STATUS("Status:", "//span[@id='display-form:contentPanel']/div/table/tbody/tr[1]/td/table[2]/tbody/tr[2]/td[2]"),
    
    
    VEHICLE_DRIVER("Assigned Driver:", "//span[@id='display-form:contentPanel']/div/table/tbody/tr[1]/td/table[3]/tbody/tr[1]/td[2]"),
    
    VEHICLE_DEVICE_PRODUCT("Product:", "//span[@id='display-form:contentPanel']/div/table/tbody/tr[1]/td/table[4]/tbody/tr[1]/td[2]"),
    VEHICLE_ASSIGNED_DEVICE("Assigned Device:","//span[@id='display-form:contentPanel']/div/table/tbody/tr[1]/td/table[4]/tbody/tr[2]/td[2]"),
    
    ;
    private String text, url;
    private String[] IDs;
    
    private AdminOrganizationEnum(String url){
        this.url = url;
    }
    private AdminOrganizationEnum(String text, String ...IDs){
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
