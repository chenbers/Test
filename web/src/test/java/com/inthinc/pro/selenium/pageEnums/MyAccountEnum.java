package com.inthinc.pro.selenium.pageEnums;



import com.inthinc.pro.automation.enums.SeleniumEnums;

import com.inthinc.pro.automation.utils.Xpath;
import com.inthinc.pro.automation.utils.Id;

public enum MyAccountEnum implements SeleniumEnums {

    MY_ACCOUNT_URL("account"),

    /* Buttons and Title */
    CHANGE_PASSWORD_BUTTON("Change Password", "myAccountPassword", Xpath.start().ul(Id.id("grid_nav")).li().button("1").toString(), Xpath.start().button(Id.type("submit")).toString()),
    EDIT_BUTTON("Edit", "myAccountEdit", Xpath.start().ul(Id.id("grid_nav")).li().button("2").toString(), Xpath.start().button(Id.type("submit")).toString()),
    MAIN_TITLE("My Account", null, Xpath.start().div(Id.clazz("account")).toString(), Xpath.start().div(Id.clazz("panel_title")).toString()),

    /* Account Information */
    ACCOUNT_TITLE("Account Information", null, "//td[1]/div[@class='add_section_title']", null),
    NAME_TITLE("Name:", null, "//td[@style='vertical-align: top;'][1]/table/tbody/tr[1]/td[1]", null),
    NAME_TEXT(null, null, "//td[@style='vertical-align: top;'][1]/table/tbody/tr[1]/td[2]", null),
    GROUP_TITLE("Group:", null, "//td[@style='vertical-align: top;'][1]/table/tbody/tr[2]/td[1]", null),
    GROUP_TEXT(null, null, "//td[@style='vertical-align: top;'][1]/table/tbody/tr[2]/td[2]", null),
    TEAM_TITLE("Team:", null, "//td[@style='vertical-align: top;'][1]/table/tbody/tr[3]/td[1]", null),
    TEAM_TEXT(null, null, "//td[@style='vertical-align: top;'][1]/table/tbody/tr[3]/td[2]", null),

    /* Login Information */
    LOGIN_TITLE("Login Information", null, "//td[1]/div[@class='add_section_title']", null),
    USER_NAME_TITLE("User Name:", null, "//td[@style='vertical-align: top;'][2]/table/tbody/tr[1]/td[1]", null),
    USER_NAME_TEXT(null, null, Xpath.start().td(Id.valign("top")).table().tbody().tr("1").td("2").toString(), null),
    LOCALE_TITLE("Locale:", null, "//td[@style='vertical-align: top;'][2]/table/tbody/tr[2]/td[1]", null),
    LOCALE_TEXT(null, null, "//td[@style='vertical-align: top;'][2]/table/tbody/tr[2]/td[2]", null),
    MEASUREMENT_TITLE("Measurement", null, "//td[@style='vertical-align: top;'][2]/table/tbody/tr[3]/td[1]", null),
    MEASUREMENT_TEXT(null, null, "//td[@style='vertical-align: top;'][2]/table/tbody/tr[3]/td[2]", null),
    FUEL_EFFICIENCY_RATIO_TITLE("Fuel Efficiency Ratio:", null, "//td[@style='vertical-align: top;']/table/tbody/tr[4]/td[1]", null),
    FUEL_EFFICIENCY_RATIO_TEXT(null, null, "//td[@style='vertical-align: top;']/table/tbody/tr[4]/td[2]", null),

    /* Red Flag Preferences */
    RED_FLAGS_TITLE("Red Flag Preferences", null, "//span[@class='notify']", null),
    INFORMATION_ICON(null, null, "//tr[3]/td[1]/div/div[@class='panel_w']/div[@class='panel_content']/table/tbody/tr[1]/td[1]", null),
    INFORMATION_TITLE("Information:", null, "//tr[3]/td[1]/div/div[@class='panel_w']/div[@class='panel_content']/table/tbody/tr[1]/td[2]", null),
    INFORMATION_TEXT(null, null, "//tr[3]/td[1]/div/div[@class='panel_w']/div[@class='panel_content']/table/tbody/tr[1]/td[3]", null),
    WARNING_ICON(null, null, "//tr[3]/td[1]/div/div[@class='panel_w']/div[@class='panel_content']/table/tbody/tr[2]/td[1]", null),
    WARNING_TITLE("Warning:", null, "//tr[3]/td[1]/div/div[@class='panel_w']/div[@class='panel_content']/table/tbody/tr[2]/td[2]", null),
    WARNING_TEXT(null, null, "//tr[3]/td[1]/div/div[@class='panel_w']/div[@class='panel_content']/table/tbody/tr[2]/td[3]", null),
    CRITICAL_ICON(null, null, "//tr[3]/td[1]/div/div[@class='panel_w']/div[@class='panel_content']/table/tbody/tr[3]/td[1]", null),
    CRITICAL_TITLE("Critical:", null, "//tr[3]/td[1]/div/div[@class='panel_w']/div[@class='panel_content']/table/tbody/tr[3]/td[2]", null),
    CRITICAL_TEXT(null, null, "//tr[3]/td[1]/div/div[@class='panel_w']/div[@class='panel_content']/table/tbody/tr[3]/td[3]", null),

    /* Contact Information */
    CONTACT_TITLE("Contact Information", null, "//span[@class='contact']", "//td[3]/div/div[@class='panel_nw']/div[@class='panel_title']/span"),
    EMAIL_TITLE("E-mail Addresses", null, "//div[@class='panel_content']/div[1]", null),
    PHONE_TITLE("Phone Numbers", null, "//div[@class='panel_content']/div[2]", null),
    TEXT_TITLE("Text Messages", null, "//div[@class='panel_content']/div[3]", null),
    EMAIL1_TITLE("E-mail 1:", null, "//td[3]/div/div/div[@class='panel_content']/table[1]/tbody/tr[1]/td[1]", null),
    EMAIL1_TEXT(null, null, "//td[3]/div/div/div[@class='panel_content']/table[1]/tbody/tr[1]/td[2]", null),
    EMAIL2_TITLE("E-mail 2:", null, "//td[3]/div/div/div[@class='panel_content']/table[1]/tbody/tr[2]/td[1]", null),
    EMAIL2_TEXT(null, null, "//td[3]/div/div/div[@class='panel_content']/table[1]/tbody/tr[2]/td[2]", null),
    PHONE1_TITLE("Phone 1:", null, "//td[3]/div/div/div[@class='panel_content']/table[2]/tbody/tr[1]/td[1]", null),
    PHONE1_TEXT(null, null, "//td[3]/div/div/div[@class='panel_content']/table[2]/tbody/tr[1]/td[2]", null),
    PHONE2_TITLE("Phone 2:", null, "//td[3]/div/div/div[@class='panel_content']/table[2]/tbody/tr[2]/td[1]", null),
    PHONE2_TEXT(null, null, "//td[3]/div/div/div[@class='panel_content']/table[2]/tbody/tr[2]/td[2]", null),
    TEXT_MESSAGES1_TITLE("Text Message 1:", null, "//td[3]/div/div/div[@class='panel_content']/table[3]/tbody/tr[1]/td[1]", null),
    TEXT_MESSAGES1_TEXT(null, null, "//td[3]/div/div/div[@class='panel_content']/table[3]/tbody/tr[1]/td[2]", null),
    TEXT_MESSAGES2_TITLE("Text Message 2:", null, "//td[3]/div/div/div[@class='panel_content']/table[3]/tbody/tr[2]/td[1]", null),
    TEXT_MESSAGES2_TEXT(null, null, "//td[3]/div/div/div[@class='panel_content']/table[3]/tbody/tr[2]/td[2]", null),

    /* Edit Page Buttons */
    SAVE_BUTTON("Save", "my_form:editAccountSave", "//li/button[@type='submit']", "//li/button[@class='left'][1]"),
    CANCEL_BUTTON("Cancel", "editAccountCancel", "//li/button[@type='button']", "//li/button[@class='left'][2]"),

    /* Edit Page Selects */
    LOCALE_SELECT(null, "my_form:editAccountLocale", "//select[@name='my_form:editAccountLocale']", null),
    MEASUREMENT_SELECT(null, "my_form:editAccountMeasurement", "//selct[@name='my_form:editAccountMeasuremnt']", null),
    FUEL_EFFICIENCY_SELECT(null, "my_form:editAccountFuelEfficiency", "//select[@name='my_form:editAccountFuelEfficiency']", null),
    INFORMATION_SELECT(null, "my_form:editAccount-info", "//select[@name='my_form:editAccount-info'", null),
    WARNING_SELECT(null, "my_form:editAccount-warn", "//select[@name='my_form:editAccount-warn'", null),
    CRITICAL_SELECT(null, "my_form:editAccount-crit", Xpath.start().select(Id.name("my_form:editAccount-crit")).toString(), null),

    /* Edit Page Error Messages */
    EMAIL1_ERROR(null, null, Xpath.start().table("1").tbody().tr("1").td().span(Id.contains(Id.id(""), "my_form:").toString(), Id.clazz("rich-message field-error field-msg")).span("1").toString(), null),
    EMAIL2_ERROR(null, null, Xpath.start().table("1").tbody().tr("2").td().span(Id.contains(Id.id(""), "my_form:").toString(), Id.clazz("rich-message field-error field-msg")).span("1").toString(), null),
    PHONE1_ERROR(null, null, Xpath.start().table("2").tbody().tr("1").td().span(Id.contains(Id.id(""), "my_form:").toString(), Id.clazz("rich-message field-error field-msg")).span("1").toString(), null),
    PHONE2_ERROR(null, null, Xpath.start().table("2").tbody().tr("2").td().span(Id.contains(Id.id(""), "my_form:").toString(), Id.clazz("rich-message field-error field-msg")).span("1").toString(), null),
    TEXT1_ERROR(null, null, Xpath.start().table("3").tbody().tr("1").td().span(Id.contains(Id.id(""), "my_form:").toString(), Id.clazz("rich-message field-error field-msg")).span("1").toString(), null),
    TEXT2_ERROR(null, null, Xpath.start().table("3").tbody().tr("2").td().span(Id.contains(Id.id(""), "my_form:").toString(), Id.clazz("rich-message field-error field-msg")).span("1").toString(), null),

    /* Edit Page text fields */
    EMAIL1_TEXTFIELD(null, "my_form:editAccount-priEmail", "//input[@name='my_form:editAccount-priEmail", null),
    EMAIL2_TEXTFIELD(null, "my_form:editAccount-secEmail", "//input[@name='my_form:editAccount-secEmail", null),

    PHONE1_TEXTFIELD(null, "my_form:editAccount-priPhone", "//input[@name='my_form:editAccount-priPhone", null),
    PHONE2_TEXTFIELD(null, "my_form:editAccount-secPhone", "//input[@name='my_form:editAccount-secPhone", null),

    TEXT_MESSAGES1_TEXTFIELD(null, "my_form:editAccount-priText", "//input[@name='my_form:editAccount-priText", null),
    TEXT_MESSAGES2_TEXTFIELD(null, "my_form:editAccount-secText", "//input[@name='my_form:editAccount-secText", null),

    /* Change Password items */
    CHANGE_PASSWORD_TITLE("Change Password", null, "//div[@id='changePasswordPanelHeader']/span", "//td[@class='rich-mpnl-header-cell']/div[@class='rich-mpnl-text rich-mpnl-header popupHeader']/span"),
    CHANGE_PASSWORD_X(null, null, "//div[@id='changePasswordPanelCDiv']/div[@id='changePasswordPanelContentDiv']/div/img[contains(@src,'modal_close')]", null),
    CHANGE_PASSWORD_CANCEL_BUTTON("Cancel", "changePasswordForm:changePasswordCancel", "//button[@name='changePasswordForm:changePasswordCancel']", "//div/button[@class='left'][@type='button']"),
    CHANGE_PASSWORD_CHANGE_BUTTON("Change", "changePasswordForm:changePasswordSubmit", "//button[@name='changePasswordForm:changePasswordSubmit']", "//div/button[@class='left'][@type='submit']"),

    CURRENT_PASSWORD_TITLE("Current Password:", null, "//form[@name='changePasswordForm']/table/tbody/tr[1]/td[1]", null),
    CURRENT_PASSWORD_TEXTFIELD(null, "changePasswordForm:oldPassword", "//inpute[@name='changePasswordForm:oldPassword", "//input[@type='password'][1]"),

    PASSWORD_STRENGTH_MSG("Begin Typing", null, "//form[@name='changePasswordForm']/table/tbody/tr[3]/td[1]", null),
    PASSWORD_STRENGTH_METER_EMPTY(null, "changePasswordForm_meterEmpty", "//div[@id='pwdTest']/span", null),
    PASSWORD_STRENGTH_METER_FULL(null, "changePasswordForm_meterFull", "//div[@id='pwdTest']/span/span", null),

    NEW_PASSWORD_TITLE("New Password:", null, "//form[@name='changePasswordForm']/table/tbody/tr[2]/td[1]", null),
    NEW_PASSWORD_TEXTFIELD(null, "changePasswordForm:newPassword", "//inpute[@name='changePasswordForm:newPassword", "//input[@type='password'][2]"),

    CONFIRM_PASSWORD_LABEL("Confirm New Password:", null, "//form[@name='changePasswordForm']/table/tbody/tr[3]/td[1]", null),
    CONFIRM_PASSWORD_TEXTFIELD(null, "changePasswordForm:confirmPassword", "//inpute[@name='changePasswordForm:confirmPassword", "//input[@type='password'][3]"),

    /* Change Password Errors */
    CURRENT_PASSWORD_ERROR(null, null, "//tr[1]/td[2]/span/span[@class='rich-message-label']", null),
    NEW_PASSWORD_ERROR(null, null, "//tr[2]/td[2]/span/span[@class='rich-message-label']", null),
    CONFIRM_PASSWORD_ERROR(null, null, "//tr[3]/td[2]/span/span[@class='rich-message-label']", null),

    ;

    private String ID, text, url, xpath, xpath_alt;

    private MyAccountEnum(String text, String ID, String xpath, String xpath_alt) {
        this.text = text;
        this.ID = ID;
        this.xpath = xpath;
        this.xpath_alt = xpath_alt;
    }

    private MyAccountEnum(String url) {
        this.url = url;
    }

    public String getID() {
        return ID;
    }

    public String getText() {
        return text;
    }

    public String getURL() {
        return url;
    }

    public String getXpath() {
        return xpath;
    }

    public String getXpath_alt() {
        return xpath_alt;
    }

    public void setText(String text) {
        this.text = text;
    };

}
