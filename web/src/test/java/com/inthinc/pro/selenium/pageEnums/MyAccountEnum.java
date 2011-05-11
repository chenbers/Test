package com.inthinc.pro.selenium.pageEnums;



import com.inthinc.pro.automation.enums.SeleniumEnums;

import com.inthinc.pro.automation.utils.Xpath;
import com.inthinc.pro.automation.utils.Id;

public enum MyAccountEnum implements SeleniumEnums {

    MY_ACCOUNT_URL("account"),

    /* Buttons and Title */
    CHANGE_PASSWORD_BUTTON("Change Password", "myAccountPassword", Xpath.start().ul(Id.id("grid_nav")).li().button("1").toString(), Xpath.start().button(Id.type("submit")).toString()),
    EDIT_BUTTON("Edit", "myAccountEdit", Xpath.start().ul(Id.id("grid_nav")).li().button("2").toString(), Xpath.start().button(Id.type("submit")).toString()),
    MAIN_TITLE("My Account", Xpath.start().div(Id.clazz("account")).toString(), Xpath.start().div(Id.clazz("panel_title")).toString()),

    /* Account Information */
    ACCOUNT_TITLE("Account Information", "//td[1]/div[@class='add_section_title']"),
    NAME_TITLE("Name:", "//td[@style='vertical-align: top;'][1]/table/tbody/tr[1]/td[1]"),
    NAME_TEXT(null, "//td[@style='vertical-align: top;'][1]/table/tbody/tr[1]/td[2]"),
    GROUP_TITLE("Group:", "//td[@style='vertical-align: top;'][1]/table/tbody/tr[2]/td[1]"),
    GROUP_TEXT(null, "//td[@style='vertical-align: top;'][1]/table/tbody/tr[2]/td[2]"),
    TEAM_TITLE("Team:", "//td[@style='vertical-align: top;'][1]/table/tbody/tr[3]/td[1]"),
    TEAM_TEXT(null, "//td[@style='vertical-align: top;'][1]/table/tbody/tr[3]/td[2]"),

    /* Login Information */
    LOGIN_TITLE("Login Information", "//td[1]/div[@class='add_section_title']"),
    USER_NAME_TITLE("User Name:", Xpath.start().td(Id.valign("top")).table().tbody().tr("1").td("1").toString()),
    USER_NAME_TEXT(null, Xpath.start().td(Id.valign("top")).table().tbody().tr("1").td("2").toString()),
    LOCALE_TITLE("Locale:", Xpath.start().td(Id.valign("top")).table().tbody().tr("2").td("1").toString()),
    LOCALE_TEXT(null, Xpath.start().td(Id.valign("top")).table().tbody().tr("2").td("2").toString()),
    MEASUREMENT_TITLE("Measurement", Xpath.start().td(Id.valign("top")).table().tbody().tr("3").td("1").toString()),
    MEASUREMENT_TEXT(null, Xpath.start().td(Id.valign("top")).table().tbody().tr("3").td("2").toString()),
    FUEL_EFFICIENCY_RATIO_TITLE("Fuel Efficiency Ratio:", Xpath.start().td(Id.valign("top")).table().tbody().tr("4").td("1").toString()),
    FUEL_EFFICIENCY_RATIO_TEXT(null, Xpath.start().td(Id.valign("top")).table().tbody().tr("4").td("2").toString()),

    /* Red Flag Preferences */
    RED_FLAGS_TITLE("Red Flag Preferences", "//span[@class='notify']"),
    INFORMATION_ICON(null, "//tr[3]/td[1]/div/div[@class='panel_w']/div[@class='panel_content']/table/tbody/tr[1]/td[1]"),
    INFORMATION_TITLE("Information:", "//tr[3]/td[1]/div/div[@class='panel_w']/div[@class='panel_content']/table/tbody/tr[1]/td[2]"),
    INFORMATION_TEXT(null, "//tr[3]/td[1]/div/div[@class='panel_w']/div[@class='panel_content']/table/tbody/tr[1]/td[3]"),
    WARNING_ICON(null, "//tr[3]/td[1]/div/div[@class='panel_w']/div[@class='panel_content']/table/tbody/tr[2]/td[1]"),
    WARNING_TITLE("Warning:", "//tr[3]/td[1]/div/div[@class='panel_w']/div[@class='panel_content']/table/tbody/tr[2]/td[2]"),
    WARNING_TEXT(null, "//tr[3]/td[1]/div/div[@class='panel_w']/div[@class='panel_content']/table/tbody/tr[2]/td[3]"),
    CRITICAL_ICON(null, "//tr[3]/td[1]/div/div[@class='panel_w']/div[@class='panel_content']/table/tbody/tr[3]/td[1]"),
    CRITICAL_TITLE("Critical:", "//tr[3]/td[1]/div/div[@class='panel_w']/div[@class='panel_content']/table/tbody/tr[3]/td[2]"),
    CRITICAL_TEXT(null, "//tr[3]/td[1]/div/div[@class='panel_w']/div[@class='panel_content']/table/tbody/tr[3]/td[3]"),

    /* Contact Information */
    CONTACT_TITLE("Contact Information", "//span[@class='contact']", "//td[3]/div/div[@class='panel_nw']/div[@class='panel_title']/span"),
    EMAIL_TITLE("E-mail Addresses", "//div[@class='panel_content']/div[1]"),
    PHONE_TITLE("Phone Numbers", "//div[@class='panel_content']/div[2]"),
    TEXT_TITLE("Text Messages", "//div[@class='panel_content']/div[3]"),

    EMAIL1_TITLE("E-mail 1:", "//td[3]/div/div/div[@class='panel_content']/table[1]/tbody/tr[1]/td[1]"),
    EMAIL1_TEXT(null, "//td[3]/div/div/div[@class='panel_content']/table[1]/tbody/tr[1]/td[2]"),
    
    EMAIL2_TITLE("E-mail 2:", "//td[3]/div/div/div[@class='panel_content']/table[1]/tbody/tr[2]/td[1]"),
    EMAIL2_TEXT(null, "//td[3]/div/div/div[@class='panel_content']/table[1]/tbody/tr[2]/td[2]"),
    
    PHONE1_TITLE("Phone 1:", "//td[3]/div/div/div[@class='panel_content']/table[2]/tbody/tr[1]/td[1]"),
    PHONE1_TEXT(null, "//td[3]/div/div/div[@class='panel_content']/table[2]/tbody/tr[1]/td[2]"),
    
    PHONE2_TITLE("Phone 2:", "//td[3]/div/div/div[@class='panel_content']/table[2]/tbody/tr[2]/td[1]"),
    PHONE2_TEXT(null, "//td[3]/div/div/div[@class='panel_content']/table[2]/tbody/tr[2]/td[2]"),
    
    TEXT_MESSAGES1_TITLE("Text Message 1:", "//td[3]/div/div/div[@class='panel_content']/table[3]/tbody/tr[1]/td[1]"),
    TEXT_MESSAGES1_TEXT(null, "//td[3]/div/div/div[@class='panel_content']/table[3]/tbody/tr[1]/td[2]"),
    
    TEXT_MESSAGES2_TITLE("Text Message 2:", "//td[3]/div/div/div[@class='panel_content']/table[3]/tbody/tr[2]/td[1]"),
    TEXT_MESSAGES2_TEXT(null, "//td[3]/div/div/div[@class='panel_content']/table[3]/tbody/tr[2]/td[2]"),

    /* Edit Page Buttons */
    SAVE_BUTTON("Save", "my_form:editAccountSave", "//li/button[@type='submit']", "//li/button[@class='left'][1]"),
    CANCEL_BUTTON("Cancel", "editAccountCancel", "//li/button[@type='button']", "//li/button[@class='left'][2]"),

    /* Edit Page Selects */
    LOCALE_SELECT(null, "my_form:editAccountLocale", "//select[@name='my_form:editAccountLocale']"),
    MEASUREMENT_SELECT(null, "my_form:editAccountMeasurement", "//selct[@name='my_form:editAccountMeasuremnt']"),
    FUEL_EFFICIENCY_SELECT(null, "my_form:editAccountFuelEfficiency", "//select[@name='my_form:editAccountFuelEfficiency']"),
    INFORMATION_SELECT(null, "my_form:editAccount-info", "//select[@name='my_form:editAccount-info'"),
    WARNING_SELECT(null, "my_form:editAccount-warn", "//select[@name='my_form:editAccount-warn'"),
    CRITICAL_SELECT(null, "my_form:editAccount-crit", Xpath.start().select(Id.name("my_form:editAccount-crit")).toString()),

    /* Edit Page Error Messages */
    EMAIL1_ERROR(null, Xpath.start().table("1").tbody().tr("1").td().span(Id.contains(Id.id(""), "my_form:").toString(), Id.clazz("rich-message field-error field-msg")).span("1").toString()),
    EMAIL2_ERROR(null, Xpath.start().table("1").tbody().tr("2").td().span(Id.contains(Id.id(""), "my_form:").toString(), Id.clazz("rich-message field-error field-msg")).span("1").toString()),
    PHONE1_ERROR(null, Xpath.start().table("2").tbody().tr("1").td().span(Id.contains(Id.id(""), "my_form:").toString(), Id.clazz("rich-message field-error field-msg")).span("1").toString()),
    PHONE2_ERROR(null, Xpath.start().table("2").tbody().tr("2").td().span(Id.contains(Id.id(""), "my_form:").toString(), Id.clazz("rich-message field-error field-msg")).span("1").toString()),
    TEXT1_ERROR(null, Xpath.start().table("3").tbody().tr("1").td().span(Id.contains(Id.id(""), "my_form:").toString(), Id.clazz("rich-message field-error field-msg")).span("1").toString()),
    TEXT2_ERROR(null, Xpath.start().table("3").tbody().tr("2").td().span(Id.contains(Id.id(""), "my_form:").toString(), Id.clazz("rich-message field-error field-msg")).span("1").toString()),

    /* Edit Page text fields */
    EMAIL1_TEXTFIELD(null, "my_form:editAccount-priEmail", "//input[@name='my_form:editAccount-priEmail"),
    EMAIL2_TEXTFIELD(null, "my_form:editAccount-secEmail", "//input[@name='my_form:editAccount-secEmail"),

    PHONE1_TEXTFIELD(null, "my_form:editAccount-priPhone", "//input[@name='my_form:editAccount-priPhone"),
    PHONE2_TEXTFIELD(null, "my_form:editAccount-secPhone", "//input[@name='my_form:editAccount-secPhone"),

    TEXT_MESSAGES1_TEXTFIELD(null, "my_form:editAccount-priText", "//input[@name='my_form:editAccount-priText"),
    TEXT_MESSAGES2_TEXTFIELD(null, "my_form:editAccount-secText", "//input[@name='my_form:editAccount-secText"),

    /* Change Password items */
    CHANGE_PASSWORD_TITLE("Change Password", "//div[@id='changePasswordPanelHeader']/span", "//td[@class='rich-mpnl-header-cell']/div[@class='rich-mpnl-text rich-mpnl-header popupHeader']/span"),
    CHANGE_PASSWORD_X(null, "//div[@id='changePasswordPanelCDiv']/div[@id='changePasswordPanelContentDiv']/div/img[contains(@src,'modal_close')]"),
    CHANGE_PASSWORD_CANCEL_BUTTON("Cancel", "changePasswordForm:changePasswordCancel", "//button[@name='changePasswordForm:changePasswordCancel']", "//div/button[@class='left'][@type='button']"),
    CHANGE_PASSWORD_CHANGE_BUTTON("Change", "changePasswordForm:changePasswordSubmit", "//button[@name='changePasswordForm:changePasswordSubmit']", "//div/button[@class='left'][@type='submit']"),

    CURRENT_PASSWORD_TITLE("Current Password:", "//form[@name='changePasswordForm']/table/tbody/tr[1]/td[1]"),
    CURRENT_PASSWORD_TEXTFIELD(null, "changePasswordForm:oldPassword", "//inpute[@name='changePasswordForm:oldPassword", "//input[@type='password'][1]"),

    PASSWORD_STRENGTH_MSG("Begin Typing", "//form[@name='changePasswordForm']/table/tbody/tr[3]/td[1]"),
    PASSWORD_STRENGTH_METER_EMPTY(null, "changePasswordForm_meterEmpty", "//div[@id='pwdTest']/span"),
    PASSWORD_STRENGTH_METER_FULL(null, "changePasswordForm_meterFull", "//div[@id='pwdTest']/span/span"),

    NEW_PASSWORD_TITLE("New Password:", "//form[@name='changePasswordForm']/table/tbody/tr[2]/td[1]"),
    NEW_PASSWORD_TEXTFIELD(null, "changePasswordForm:newPassword", "//inpute[@name='changePasswordForm:newPassword", "//input[@type='password'][2]"),

    CONFIRM_PASSWORD_LABEL("Confirm New Password:", "//form[@name='changePasswordForm']/table/tbody/tr[3]/td[1]"),
    CONFIRM_PASSWORD_TEXTFIELD(null, "changePasswordForm:confirmPassword", "//inpute[@name='changePasswordForm:confirmPassword", "//input[@type='password'][3]"),

    /* Change Password Errors */
    CURRENT_PASSWORD_ERROR(null, "//tr[1]/td[2]/span/span[@class='rich-message-label']"),
    NEW_PASSWORD_ERROR(null, "//tr[2]/td[2]/span/span[@class='rich-message-label']"),
    CONFIRM_PASSWORD_ERROR(null, "//tr[3]/td[2]/span/span[@class='rich-message-label']"),

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
