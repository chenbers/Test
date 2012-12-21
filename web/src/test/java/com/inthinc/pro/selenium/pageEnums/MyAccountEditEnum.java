package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.interfaces.SeleniumEnums;

public enum MyAccountEditEnum implements SeleniumEnums {

    MY_ACCOUNT_URL(appUrl + "/account/edit"),

    /* Buttons and Title */
    SAVE_BUTTON("Save", "my_form:editAccountSave"),
    CANCEL_BUTTON("Cancel", "editAccountCancel"),
    MAIN_TITLE("My Account", "//span[@class='account']"),

    /* Account Information */
    ACCOUNT_HEADER("Account Information", "//td[1]/div[@class='add_section_title']"),
    NAME_LABEL("Name:", "//div/table/tbody/tr/td[1]/table/tbody/tr[1]/td[1]"),
    NAME_TEXT(null, "//td[1]/div[@class='add_section_title']/../table/tbody/tr[1]/td[2]"),
    GROUP_LABEL("Group:", "//div/table/tbody/tr/td[1]/table/tbody/tr[2]/td[1]"),
    GROUP_TEXT(null, "//td[1]/div[@class='add_section_title']/../table/tbody/tr[2]/td[2]"),
    TEAM_LABEL("Team:", "//div/table/tbody/tr/td[1]/table/tbody/tr[3]/td[1]"),
    TEAM_TEXT(null, "//td[1]/div[@class='add_section_title']/../table/tbody/tr[3]/td[2]"),

    /* Login Information */
    LOGIN_HEADER("Login Information", "//td[1]/div[@class='add_section_title']"),
    USER_NAME_LABEL("User Name:", "//div/table/tbody/tr/td[3]/table/tbody/tr[1]/td[1]"),
    USER_NAME_TEXT(null, "//td[3]/table/tbody/tr[1]/td[2]"),
    LOCALE_LABEL("Locale:", "//div/table/tbody/tr/td[3]/table/tbody/tr[2]/td[1]"),
    LOCALE_SELECT(null, "my_form:editAccountLocale"),
    MEASUREMENT_LABEL("Measurement:", "//div/table/tbody/tr/td[3]/table/tbody/tr[3]/td[1]"),
    MEASUREMENT_SELECT(null, "my_form:editAccountMeasurement"),
    FUEL_EFFICIENCY_LABEL("Fuel Efficiency Ratio:", "//div/table/tbody/tr/td[3]/table/tbody/tr[4]/td[1]"),    
    FUEL_EFFICIENCY_SELECT(null, "my_form:editAccountFuelEfficiency"),
        
    /* Red Flag Preferences */
    RED_FLAGS_HEADER("Red Flag Preferences", "//span[@class='notify']"),
    INFORMATION_ICON(null, "//tr[2]/td[1]/img"),
    INFORMATION_LABEL("Information:", "//tr[1]/td/div/div[2]/div/table/tbody/tr[2]/td[2]"),
    INFORMATION_DROPDOWN(null, "my_form:editAccount-info"),
    WARNING_ICON(null, "//tr[3]/td[1]/img"),
    WARNING_LABEL("Warning:", "//tr[1]/td/div/div[2]/div/table/tbody/tr[3]/td[2]"),
    WARNING_DROPDOWN(null, "my_form:editAccount-warn"),
    CRITICAL_ICON(null, "//tr[4]/td[1]/img"),
    CRITICAL_LABEL("Critical:", "//tr[1]/td/div/div[2]/div/table/tbody/tr[4]/td[2]"),
    CRITICAL_DROPDOWN(null, "my_form:editAccount-crit"),

    /* Map Preferences */
    MAP_PREFERENCES_HEADER("Map Preferences", "//span[@class='map']"),
    MAP_TYPE_LABEL("Map Type:", "//td[1]/table/tbody/tr[3]/td/div/div[2]/div/table/tbody/tr[1]/td[1]"),
    MAP_TYPE_DROPDOWN(null, "my_form:editAccount-mapType"),
    MAP_LAYERS_LABEL("Map Layers:", "//td[1]/table/tbody/tr[3]/td/div/div[2]/div/table/tbody/tr[2]/td[1]"),
    MAP_LAYERS_DROPDOWN(null, "my_form:myAccountMapLayersSelect"),
    MAP_LAYERS_ARROW(null, "//div[@class='ui-icon ui-icon-triangle-1-s']"),
    MAP_LAYERS_CHECKBOX(null, "ddcl-my_form:myAccountMapLayersSelect-i###"),
    
    /* Contact Information */
    CONTACT_HEADER("Contact Information", "//span[@class='contact']", "//td[3]/div/div[@class='panel_nw']/div[@class='panel_title']/span"),
    EMAIL_SUB_HEADER("E-mail Addresses", "//div[@class='panel_content']/div[1]"),
    EMAIL1_LABEL("E-mail 1:", "//td[3]/div/div[2]/div/table[1]/tbody/tr[1]/td[1]"),
    EMAIL1_TEXTFIELD(email + " 1:", "my_form:editAccount-priEmail"),
    EMAIL2_LABEL("E-mail 2:", "//td[3]/div/div[2]/div/table[1]/tbody/tr[2]/td[1]"),
    EMAIL2_TEXTFIELD(email + " 2:", "my_form:editAccount-secEmail"),
    PHONE_SUB_HEADER("Phone Numbers", "//div[@class='panel_content']/div[2]"),
    PHONE1_LABEL("Phone 1:", "//td[3]/div/div[2]/div/table[2]/tbody/tr[1]/td[1]"),
    PHONE1_TEXTFIELD("Phone 1:", "my_form:editAccount-priPhone"),
    PHONE2_LABEL("Phone 2:", "//td[3]/div/div[2]/div/table[2]/tbody/tr[2]/td[1]"),    
    PHONE2_TEXTFIELD("Phone 2:", "my_form:editAccount-secPhone"),
    TEXT_SUB_HEADER("Text Messages", "//div[@class='panel_content']/div[3]"),
    TEXT_MESSAGES1_LABEL("Text Message 1:", "//td[3]/div/div[2]/div/table[3]/tbody/tr[1]/td[1]"),
    TEXT_MESSAGES1_TEXTFIELD(null, "my_form:editAccount-priText"),
    TEXT_MESSAGES2_LABEL("Text Message 2:", "//td[3]/div/div[2]/div/table[3]/tbody/tr[2]/td[1]"),    
    TEXT_MESSAGES2_TEXTFIELD(null, "my_form:editAccount-secText")

    ;

    private String text, url;
    private String[] IDs;
    
    private MyAccountEditEnum(String url){
    	this.url = url;
    }
    private MyAccountEditEnum(String text, String ...IDs){
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
