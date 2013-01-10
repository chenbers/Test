package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.interfaces.SeleniumEnums;

public enum MyAccountEnum implements SeleniumEnums {

    MY_ACCOUNT_URL(appUrl + "/account"),

    /* Buttons and Title */
    CHANGE_PASSWORD_BUTTON("Change Password", "myAccountpassword"),
    EDIT_BUTTON("Edit", "myAccountEdit"),
    MAIN_TITLE("My Account", "//div[@class='account']"),
    MESSAGE(null, "//dt[@class='info']/span"),

    /* Account Information */
    ACCOUNT_HEADER("Account Information", "//td[1]/div[@class='add_section_title']"),
    NAME_TEXT("Name:", "//td[1]/div[@class='add_section_title']/../table/tbody/tr[1]/td[2]"),
    GROUP_TEXT("Group:", "//td[1]/div[@class='add_section_title']/../table/tbody/tr[2]/td[2]"),
    TEAM_TEXT("Team:", "//td[1]/div[@class='add_section_title']/../table/tbody/tr[3]/td[2]"),

    /* Login Information */
    LOGIN_HEADER("Login Information", "//td[1]/div[@class='add_section_title']"),
    USER_NAME_TEXT("User Name:", "myAccountUsername"),
    LOCALE_TEXT("Locale:", "myAccountLocale"),
    MEASUREMENT_TEXT("Measurement:", "myAccountMeasurementType"),
    FUEL_EFFICIENCY_RATIO_TEXT("Fuel Efficiency Ratio:", "myAccountFuelEfficiency"),

    /* Red Flag Preferences */
    RED_FLAGS_HEADER("Red Flag Preferences", "//span[@class='notify']"),
    INFORMATION_ICON(null, "//td[@id='myAccountInfo']/../td[1]"),
    INFORMATION_TEXT("Information:", "myAccountInfo"),
    WARNING_ICON(null, "///td[@id='myAccountInfo']/../td[1]"),
    WARNING_TEXT("Warning:", "myAccountWarn"),
    CRITICAL_ICON(null, "//td[@id='myAccountInfo']/../td[1]"),
    CRITICAL_TEXT("Critical", "myAccountCrit"),

    /* Map Preferences */
    MAP_PREFERENCES_HEADER("Map Preferences", "//span[@class='map']"),
    MAP_TYPE_TEXT("Map Type", "myAccountMapType"),
    MAP_LAYERS_TEXT("Map Layers", "myAccountMayLayers"),
    
    /* Contact Information */
    CONTACT_HEADER("Contact Information", "//span[@class='contact']", "//td[3]/div/div[@class='panel_nw']/div[@class='panel_title']/span"),
    EMAIL_SUB_HEADER("E-mail Addresses", "//div[@class='panel_content']/div[1]"),
    PHONE_SUB_HEADER("Phone Numbers", "//div[@class='panel_content']/div[2]"),
    TEXT_SUB_HEADER("Text Messages", "//div[@class='panel_content']/div[3]"),

    EMAIL1_TEXT(email + " 1:", "myAccountPriEmail"),
    EMAIL2_TEXT(email + " 2:", "myAccountSecEmail"),
    
    PHONE1_TEXT("Phone 1:", "myAccountPriPhone"),
    PHONE2_TEXT("Phone 2:", "myAccountSecPhone"),
    
    TEXT_MESSAGES1_TEXT("Text Message 1:", "myAccountPriText"),
    TEXT_MESSAGES2_TEXT("Text Message 2:", "myAccountSecText"),

    ;

    private String text, url;
    private String[] IDs;
    
    private MyAccountEnum(String url){
    	this.url = url;
    }
    private MyAccountEnum(String text, String ...IDs){
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
