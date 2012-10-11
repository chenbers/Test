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
    MAP_TYPE_SELECTOR(null, "my_form:editAccount-mapType"),
    MAP_LAYERS_TEXT("Map Layers", "myAccountMayLayers"),
    MAP_LAYERS_SELECTOR("Layers","my_form:myAccountMapLayersSelect"),
    MAP_LAYERS_ARROW(null, "//div[@class='ui-icon ui-icon-triangle-1-s']"),
    MAP_LAYERS_CHECKBOX(null, "ddcl-my_form:myAccountMapLayersSelect-i###"),
    
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

    /* Edit Page Buttons */
    SAVE_BUTTON("Save", "my_form:editAccountSave"),
    CANCEL_BUTTON("Cancel", "editAccountCancel"),

    /* Edit Page Selects */
    LOCALE_SELECT(null, "my_form:editAccountLocale"),
    MEASUREMENT_SELECT(null, "my_form:editAccountMeasurement", "//selct[@name='my_form:editAccountMeasurement']"),
    FUEL_EFFICIENCY_SELECT(null, "my_form:editAccountFuelEfficiency", "//select[@name='my_form:editAccountFuelEfficiency']"),
    INFORMATION_SELECT(null, "my_form:editAccount-info", "//select[@name='my_form:editAccount-info'"),
    WARNING_SELECT(null, "my_form:editAccount-warn", "//select[@name='my_form:editAccount-warn'"),
    CRITICAL_SELECT(null, "my_form:editAccount-crit", "//select[@name='my_form:editAccount-crit"),


    /* Edit Page text fields */
    EMAIL1_TEXTFIELD(email + " 1:", "my_form:editAccount-priEmail", "//input[@name='my_form:editAccount-priEmail"),
    EMAIL2_TEXTFIELD(email + " 2:", "my_form:editAccount-secEmail", "//input[@name='my_form:editAccount-secEmail"),

    PHONE1_TEXTFIELD("Phone 1:", "my_form:editAccount-priPhone", "//input[@name='my_form:editAccount-priPhone"),
    PHONE2_TEXTFIELD("Phone 2:", "my_form:editAccount-secPhone", "//input[@name='my_form:editAccount-secPhone"),

    TEXT_MESSAGES1_TEXTFIELD("Text Message 1:", "my_form:editAccount-priText", "//input[@name='my_form:editAccount-priText"),
    TEXT_MESSAGES2_TEXTFIELD("Text Message 2:", "my_form:editAccount-secText", "//input[@name='my_form:editAccount-secText"),

    
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
