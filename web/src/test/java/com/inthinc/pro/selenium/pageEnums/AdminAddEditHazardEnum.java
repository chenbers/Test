package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.interfaces.SeleniumEnums;

public enum AdminAddEditHazardEnum implements SeleniumEnums {
    
    TITLE("Admin - Add Hazard", "//span[@class='editHazard']"),

    LABEL_FIND_ADDRESS("Find Address:", "//table[@id='grid_nav_search_box']/tbody/tr/td[1]"),
    LABEL_LATITUDE("Latitdue:", "//div[@id='hazards-form:editHazardDetails_body']/div/table/tbody/tr[1]/td[1]"),
    LABEL_LONGITUDE("Longitude:", "//div[@id='hazards-form:editHazardDetails_body']/div/table/tbody/tr[2]/td[1]"),
    LABEL_TYPE("Type:", "//div[@id='hazards-form:editHazardDetails_body']/div/table/tbody/tr[3]/td[1]"),
    LABEL_RADIUS("Radius:", "//div[@id='hazards-form:editHazardDetails_body']/div/table/tbody/tr[4]/td[1]"),
    LABEL_START_TIME("Start Time:", "//div[@id='hazards-form:editHazardDetails_body']/div/table/tbody/tr[5]/td[1]"),
    LABEL_EXP_TIME("Exp. Time:", "//div[@id='hazards-form:editHazardDetails_body']/div/table/tbody/tr[6]/td[1]"),
    LABEL_DESCRIPTION("Description:", "//div[@id='hazards-form:editHazardDetails_body']/div/table/tbody/tr[7]/td[1]"),
    
    LATITUDE_TEXT(null, "hazards-form:editHazard-lat"),
    LONGITUDE_TEXT(null, "hazards-form:editHazard-lng"),
    
    FIND_ADDRESS_TEXTFIELD("Find Address:", "hazards-form:hazardAddress"),
    RADIUS_TEXTFIELD("Radius:", "hazards-form:hazardRadiusInUnits"),
    DESCRIPTION_TEXTFIELD("Description", "hazards-form:msgText"),
    
    LOCATE_BUTTON("Locate", "hazardsSearch"),
    RESET_BUTTON("Reset", "hazards-form:reset"),
    SAVE_BUTTON("Save", "hazards-form:hazardsSave"),
    CANCEL_BUTTON("Cancel", "hazards-form:hazardsCancel"),
    
    TYPE_DROPDOWN(null, "hazards-form:hazardType"),
    RADIUS_DROPDOWN(null, "hazards-form:hazardRadiusUnits"),
    START_TIME_DROPDOWN(null, "hazards-form:startCalendarInputDate"),
    EXP_TIME_DROPDOWN(null, "hazards-form:endCalendarInputDate"),
    
    COLLAPSE(null, "hazards-form:editHazardDetails_switch_on"),
    
    URL(appUrl + "/admin/editHazard"),
    
    
    ;
    private String text, url;
    private String[] IDs;

    private AdminAddEditHazardEnum(String url) {
        this.url = url;
    }

    private AdminAddEditHazardEnum(String text, String... IDs) {
        this.text = text;
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
