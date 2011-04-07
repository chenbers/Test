package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.selenium.SeleniumEnums;

public enum MyAccountEnum implements SeleniumEnums {

    MY_ACCOUNT_URL("account"),

    /* Buttons and Title */
    CHANGE_PASSWORD("Change Password", "myAccountPassword", "//ul[@id='grid_nav']/li/button[1]", "//button[@type='submit']"),
    EDIT("Edit", "myAccountEdit", "//ul[@id='grid_nav']/li/button[2]", "//button[@type='submit']"),

    TITLE("My Account", null, "//div[@class='account']", "//div[@class='panel_title']"),

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

    USER_NAME("User Name:", null, "//td[@style='vertical-align: top;'][2]/table/tbody/tr[1]/td[1]", null),
    USER_NAME_TEXT(null, null, "//td[@style='vertical-align: top;'][2]/table/tbody/tr[1]/td[2]", null),

    LOCALE("Locale:", null, "//td[@style='vertical-align: top;'][2]/table/tbody/tr[2]/td[1]", null),
    LOCALE_TEXT(null, null, "//td[@style='vertical-align: top;'][2]/table/tbody/tr[2]/td[2]", null),

    MEASUREMENT("Measurement", null, "//td[@style='vertical-align: top;'][2]/table/tbody/tr[3]/td[1]", null),
    MEASUREMENT_TEXT(null, null, "//td[@style='vertical-align: top;'][2]/table/tbody/tr[3]/td[2]", null),

    FUEL_EFFICIENCY_RATIO("Fuel Efficiency Ratio:", null, "//td[@style='vertical-align: top;']/table/tbody/tr[4]/td[1]", null),
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

    EMAIL1("E-mail 1:", null, "//td[3]/div/div/div[@class='panel_content']/table[1]/tbody/tr[1]/td[1]", null),
    EMAIL1_TEXT(null, null, "//td[3]/div/div/div[@class='panel_content']/table[1]/tbody/tr[1]/td[2]", null),

    EMAIL2("E-mail 2:", null, "//td[3]/div/div/div[@class='panel_content']/table[1]/tbody/tr[2]/td[1]", null),
    EMAIL2_TEXT(null, null, "//td[3]/div/div/div[@class='panel_content']/table[1]/tbody/tr[2]/td[2]", null),

    PHONE1("Phone 1:", null, "//td[3]/div/div/div[@class='panel_content']/table[1]/tbody/tr[3]/td[1]", null),
    PHONE1_TEXT(null, null, "//td[3]/div/div/div[@class='panel_content']/table[1]/tbody/tr[3]/td[2]", null),

    PHONE2("Phone 2:", null, "//td[3]/div/div/div[@class='panel_content']/table[1]/tbody/tr[4]/td[1]", null),
    PHONE2_TEXT(null, null, "//td[3]/div/div/div[@class='panel_content']/table[1]/tbody/tr[4]/td[2]", null),

    TEXT_MESSAGES1("Text Message 1:", null, "//td[3]/div/div/div[@class='panel_content']/table[1]/tbody/tr[5]/td[1]", null),
    TEXT_MESSAGES1_TEXT(null, null, "//td[3]/div/div/div[@class='panel_content']/table[1]/tbody/tr[5]/td[2]", null),

    TEXT_MESSAGES2("Text Message 2:", null, "//td[3]/div/div/div[@class='panel_content']/table[1]/tbody/tr[6]/td[1]", null),
    TEXT_MESSAGES2_TEXT(null, null, "//td[3]/div/div/div[@class='panel_content']/table[1]/tbody/tr[6]/td[2]", null),

    /* Edit Page Buttons */

    SAVE("Save", "my_form:editAccountSave", "//li/button[@type='submit']", "//li/button[@class='left'][1]"),
    CANCEL("Cancel", "editAccountCancel", "//li/button[@type='button']", "//li/button[@class='left'][2]"),

    /* Edit Page Selects */
    INFORMATION_SELECT(null, "my_form:editAccountLocale", "//select[@name='my_form:editAccountLocale']", null),
    WARNING_SELECT(null, "my_form:editAccountMeasuremnt", "//selct[@name='my_form:editAccountMeasuremnt']", null),
    CRITICAL_SELECT(null, "my_form:editAccountFuelEfficiency", "//select[@name='my_form:editAccountFuelEfficiency']", null),

    LOCALE_SELECT(null, "my_form:editAccount-info", "//select[@name='my_form:editAccount-info'", null),
    MEASUREMENT_SELECT(null, "my_form:editAccount-warn", "//select[@name='my_form:editAccount-warn'", null),
    FUEL_EFFICIENCY_SELECT(null, "my_form:editAccount-crit", "//select[@name='my_form:editAccount-crit'", null),

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
    CHANGE_PASSWORD_CANCEL("Cancel", "changePasswordForm:changePasswordCancel", "//button[@name='changePasswordForm:changePasswordCancel']", "//div/button[@class='left'][@type='button']"),
    CHANGE_PASSWORD_CHANGE("Change", "changePasswordForm:changePasswordSubmit", "//button[@name='changePasswordForm:changePasswordSubmit']", "//div/button[@class='left'][@type='submit']"),

    CHANGE_PASSWORD_CURRENT_TITE("Current Password:", null, "//form[@name='changePasswordForm']/table/tbody/tr[1]/td[1]", null),
    CHANGE_PASSWORD_CURRENT_FIELD(null, "changePasswordForm:oldPassword", "//inpute[@name='changePasswordForm:oldPassword", "//input[@type='password'][1]"),

    CHANGE_PASSWORD_STRENGTH_MSG("Begin Typing", null, "//form[@name='changePasswordForm']/table/tbody/tr[3]/td[1]", null),
    CHANGE_PASSWORD_STRENGTH_METER_EMPTY(null, "changePasswordForm_meterEmpty", "//div[@id='pwdTest']/span", null),
    CHANGE_PASSWORD_STRENGTH_METER_FULL(null, "changePasswordForm_meterFull", "//div[@id='pwdTest']/span/span", null),

    CHANGE_PASSWORD_NEW_TITLE("Current Password:", null, "//form[@name='changePasswordForm']/table/tbody/tr[2]/td[1]", null),
    CHANGE_PASSWORD_NEW_FIELD(null, "changePasswordForm:newPassword", "//inpute[@name='changePasswordForm:newPassword", "//input[@type='password'][2]"),

    CHANGE_PASSWORD_CONFIRM_TITLE("Current Password:", null, "//form[@name='changePasswordForm']/table/tbody/tr[3]/td[1]", null),
    CHANGE_PASSWORD_CONFIRM_FIELD(null, "changePasswordForm:confirmPassword", "//inpute[@name='changePasswordForm:confirmPassword", "//input[@type='password'][3]"),

    ;

    private String ID, text, url, xpath, xpathAlt;

    private MyAccountEnum(String ID, String text, String xpath, String xpathAlt) {
        this.ID = ID;
        this.text = text;
        this.xpath = xpath;
        this.xpathAlt = xpathAlt;
        this.url = null;
    }

    private MyAccountEnum(String url) {
        this.url = url;
        this.ID = null;
        this.text = null;
        this.xpath = null;
        this.xpathAlt = null;
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
        return xpathAlt;
    }

    public void setText(String text) {
        this.text = text;
    }

}
