package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.enums.SeleniumValueEnums;
import com.inthinc.pro.selenium.pageEnums.MyAccountEnum;

public class PageMyAccount extends NavigationBar {

    public static enum RedFlagPrefs implements SeleniumValueEnums {
        EMAIL1("1", MyAccountEnum.EMAIL1_TEXTFIELD, MyAccountEnum.EMAIL1_TITLE),
        EMAIL2("2", MyAccountEnum.EMAIL2_TEXTFIELD, MyAccountEnum.EMAIL2_TITLE),
        PHONE1("3", MyAccountEnum.PHONE1_TEXTFIELD, MyAccountEnum.PHONE1_TITLE),
        PHONE2("4", MyAccountEnum.PHONE2_TEXTFIELD, MyAccountEnum.PHONE2_TITLE),
        TEXT1("5", MyAccountEnum.TEXT_MESSAGES1_TEXTFIELD, MyAccountEnum.TEXT_MESSAGES1_TITLE),
        TEXT2("6", MyAccountEnum.TEXT_MESSAGES2_TEXTFIELD, MyAccountEnum.TEXT_MESSAGES2_TITLE);

        private String value;
        private MyAccountEnum ID, prefix;

        private RedFlagPrefs(String value, MyAccountEnum ID, MyAccountEnum prefix) {
            this.value = value;
            this.ID = ID;
            this.prefix = prefix;
        }

        public MyAccountEnum getID() {
            return ID;
        }

        public MyAccountEnum getPrefix() {
            return prefix;
        }

        public String getValue() {
            return value;
        }
    };

    public PageMyAccount button_cancel_click() {
        selenium.click(MyAccountEnum.CANCEL_BUTTON);
        return this;
    }

    public PageMyAccount button_change_click() {
        selenium.click(MyAccountEnum.CANCEL_BUTTON);
        return this;
    }

    public PageMyAccount button_changePassword_click() {
        selenium.click(MyAccountEnum.CHANGE_PASSWORD_BUTTON);
        return this;
    }

    public PageMyAccount button_changePasswordX_click() {
        selenium.click(MyAccountEnum.CHANGE_PASSWORD_X);
        return this;
    }

    public PageMyAccount button_edit_click() {
        selenium.click(MyAccountEnum.EDIT_BUTTON);
        return this;
    }

    public PageMyAccount button_save_click() {
        selenium.click(MyAccountEnum.SAVE_BUTTON);
        return this;
    }

    public PageMyAccount dropDown_critical_selectPartialMatch(String partial){
        selectPartialMatch(partial, MyAccountEnum.CRITICAL_SELECT);
        return this;
    }

    public PageMyAccount dropDown_critical_selectText(String selection) {
        selectOption(selection, MyAccountEnum.CRITICAL_SELECT);
        return this;
    }
    
    public PageMyAccount dropDown_critical_selectValue(RedFlagPrefs selection) {
        selectValue(selection, MyAccountEnum.CRITICAL_SELECT);
        return this;
    }

    public PageMyAccount dropDown_fuelEfficiency_selectText(String selection) {
        selectOption(selection, MyAccountEnum.FUEL_EFFICIENCY_SELECT);
        return this;
    }

    public PageMyAccount dropDown_information_selectPartialMatch(String partial) {
        selectPartialMatch(partial, MyAccountEnum.INFORMATION_SELECT);
        return this;
    }

    public PageMyAccount dropDown_information_selectText(String selection) {
        selectOption(selection, MyAccountEnum.INFORMATION_SELECT);
        return this;
    }

    public PageMyAccount dropDown_information_selectValue(RedFlagPrefs selection) {
        selectValue(selection, MyAccountEnum.INFORMATION_SELECT);
        return this;
    }

    public PageMyAccount dropDown_locale_selectText(String selection) {
        selectOption(selection, MyAccountEnum.LOCALE_SELECT);
        return this;
    }

    public PageMyAccount dropDown_measurement_selectText(String selection) {
        selectOption(selection, MyAccountEnum.MEASUREMENT_SELECT);
        return this;
    }

    public PageMyAccount dropDown_warning_selectPartialMatch(String partial){
        selectPartialMatch(partial, MyAccountEnum.WARNING_SELECT);
        return this;
    }

    public PageMyAccount dropDown_warning_selectText(String selection) {
        selectOption(selection, MyAccountEnum.WARNING_SELECT);
        return this;
    }
    
    public PageMyAccount dropDown_warning_selectValue(RedFlagPrefs selection) {
        selectValue(selection, MyAccountEnum.WARNING_SELECT);
        return this;
    }

    public String errorMsg_confirmPassword_getText() {
        return selenium.getText(MyAccountEnum.CONFIRM_PASSWORD_ERROR);
    }

    public String errorMsg_currentPassword_getText() {
        return selenium.getText(MyAccountEnum.CURRENT_PASSWORD_ERROR);
    }

    public String errorMsg_email1_getText() {
        return selenium.getText(MyAccountEnum.EMAIL1_ERROR);
    }

    public String errorMsg_email2_getText() {
        return selenium.getText(MyAccountEnum.EMAIL2_ERROR);
    }

    public String errorMsg_newPassword_getText() {
        return selenium.getText(MyAccountEnum.NEW_PASSWORD_ERROR);
    }

    public String errorMsg_phone1_getText() {
        return selenium.getText(MyAccountEnum.PHONE1_ERROR);
    }

    public String errorMsg_phone2_getText() {
        return selenium.getText(MyAccountEnum.PHONE2_ERROR);
    }

    public String errorMsg_text1_getText() {
        return selenium.getText(MyAccountEnum.TEXT1_ERROR);
    }

    public String errorMsg_text2_getText() {
        return selenium.getText(MyAccountEnum.TEXT2_ERROR);
    }

    public String getExpectedPath() {
        return MyAccountEnum.MY_ACCOUNT_URL.getURL();
    }

    public String label_confirmPassword_getText() {
        return selenium.getText(MyAccountEnum.CONFIRM_PASSWORD_LABEL);
    }

    public String label_criticalRedFlag_getText() {
        return selenium.getText(MyAccountEnum.CRITICAL_TITLE);
    }

    public String label_currentPassword_getText() {
        return selenium.getText(MyAccountEnum.CURRENT_PASSWORD_TITLE);
    }

    public String label_emailAddress1_getText() {
        return selenium.getText(MyAccountEnum.EMAIL1_TITLE);
    }

    public String label_emailAddress2_getText() {
        return selenium.getText(MyAccountEnum.EMAIL2_TITLE);
    }

    public String label_fuelEfficiency_getText() {
        return selenium.getText(MyAccountEnum.FUEL_EFFICIENCY_RATIO_TITLE);
    }

    public String label_group_getText() {
        return selenium.getText(MyAccountEnum.GROUP_TITLE);
    }

    public String label_informationRedFlag_getText() {
        return selenium.getText(MyAccountEnum.INFORMATION_TITLE);
    }

    public String label_locale_getText() {
        return selenium.getText(MyAccountEnum.LOCALE_TITLE);
    }

    public String label_measurement_getText() {
        return selenium.getText(MyAccountEnum.MEASUREMENT_TITLE);
    }

    public String label_name_getText() {
        return selenium.getText(MyAccountEnum.NAME_TITLE);
    }

    public String label_newPassword_getText() {
        return selenium.getText(MyAccountEnum.NEW_PASSWORD_TITLE);
    }

    public String label_phoneNumber1_getText() {
        return selenium.getText(MyAccountEnum.PHONE1_TITLE);
    }

    public String label_phoneNumber2_getText() {
        return selenium.getText(MyAccountEnum.PHONE2_TITLE);
    }

    public String label_team_getText() {
        return selenium.getText(MyAccountEnum.TEAM_TITLE);
    }

    public String label_textMessage1_getText() {
        return selenium.getText(MyAccountEnum.TEXT_MESSAGES1_TITLE);
    }

    public String label_textMessage2_getText() {
        return selenium.getText(MyAccountEnum.TEXT_MESSAGES2_TITLE);
    }

    public String label_userName_getText() {
        return selenium.getText(MyAccountEnum.USER_NAME_TITLE);
    }

    public String label_warningRedFlag_getText() {
        return selenium.getText(MyAccountEnum.WARNING_TITLE);
    }

    public PageMyAccount page_titlesAndLabels_validate() {
        /* Buttons on the main page */
        assertEquals(selenium.getText(MyAccountEnum.CHANGE_PASSWORD_BUTTON), MyAccountEnum.CHANGE_PASSWORD_BUTTON);
        assertEquals(selenium.getText(MyAccountEnum.EDIT_BUTTON), MyAccountEnum.EDIT_BUTTON);

        /* Titles for the separate sections */
        assertEquals(title_myAccountMain_getText(), MyAccountEnum.MAIN_TITLE);
        assertEquals(title_accountInformation_getText(), MyAccountEnum.ACCOUNT_TITLE);
        assertEquals(title_loginInformation_getText(), MyAccountEnum.LOGIN_TITLE);
        assertEquals(title_redFlagPreferences_getText(), MyAccountEnum.RED_FLAGS_TITLE);
        assertEquals(title_contactInformation_getText(), MyAccountEnum.CONTACT_TITLE);

        /* Labels for the seperate rows */
        /* Account Information */
        assertEquals(label_name_getText(), MyAccountEnum.NAME_TITLE);
        assertEquals(label_group_getText(), MyAccountEnum.GROUP_TITLE);
        assertEquals(label_team_getText(), MyAccountEnum.TEAM_TITLE);

        /* Login Information */
        assertEquals(label_userName_getText(), MyAccountEnum.USER_NAME_TITLE);
        assertEquals(label_locale_getText(), MyAccountEnum.LOCALE_TITLE);
        assertEquals(label_measurement_getText(), MyAccountEnum.MEASUREMENT_TITLE);
        assertEquals(label_fuelEfficiency_getText(), MyAccountEnum.FUEL_EFFICIENCY_RATIO_TITLE);

        /* Red Flag Preferences */
        assertEquals(label_informationRedFlag_getText(), MyAccountEnum.INFORMATION_TITLE);
        assertEquals(label_warningRedFlag_getText(), MyAccountEnum.WARNING_TITLE);
        assertEquals(label_criticalRedFlag_getText(), MyAccountEnum.CRITICAL_TITLE);

        /* Contact Information */
        assertEquals(title_emailAddresses_getText(), MyAccountEnum.EMAIL_TITLE);
        assertEquals(label_emailAddress1_getText(), MyAccountEnum.EMAIL1_TITLE);
        assertEquals(label_emailAddress2_getText(), MyAccountEnum.EMAIL2_TITLE);

        assertEquals(title_phoneNumbers_getText(), MyAccountEnum.EMAIL_TITLE);
        assertEquals(label_phoneNumber1_getText(), MyAccountEnum.PHONE1_TITLE);
        assertEquals(label_phoneNumber2_getText(), MyAccountEnum.PHONE2_TITLE);

        assertEquals(title_textMessages_getText(), MyAccountEnum.EMAIL_TITLE);
        assertEquals(label_textMessage1_getText(), MyAccountEnum.TEXT_MESSAGES1_TITLE);
        assertEquals(label_textMessage2_getText(), MyAccountEnum.TEXT_MESSAGES2_TITLE);

        return this;
    }

    public Boolean popup_changePassword_isVisible() {
        return selenium.isVisible(MyAccountEnum.CHANGE_PASSWORD_CHANGE_BUTTON);
    }

    public String text_criticalRedFlag_getText() {
        return selenium.getText(MyAccountEnum.CRITICAL_TEXT);
    }

    public String text_emailAddress1_getText() {
        return selenium.getText(MyAccountEnum.EMAIL1_TEXT);
    }

    public String text_emailAddress2_getText() {
        return selenium.getText(MyAccountEnum.EMAIL2_TEXT);
    }

    public String text_fuelEfficiency_getText() {
        return selenium.getText(MyAccountEnum.FUEL_EFFICIENCY_RATIO_TEXT);
    }

    public String text_group_getText() {
        return selenium.getText(MyAccountEnum.GROUP_TEXT);
    }

    public String text_informationRedFlag_getText() {
        return selenium.getText(MyAccountEnum.INFORMATION_TEXT);
    }

    public String text_locale_getText() {
        return selenium.getText(MyAccountEnum.LOCALE_TEXT);
    }

    public String text_measurement_getText() {
        return selenium.getText(MyAccountEnum.MEASUREMENT_TEXT);
    }

    public String text_name_getText() {
        return selenium.getText(MyAccountEnum.NAME_TEXT);
    }

    public String text_passwordStrength_getText() {
        return selenium.getText(MyAccountEnum.PASSWORD_STRENGTH_MSG);
    }

    public String text_phoneNumber1_getText() {
        return selenium.getText(MyAccountEnum.PHONE1_TEXT);
    }

    public String text_phoneNumber2_getText() {
        return selenium.getText(MyAccountEnum.PHONE2_TEXT);
    }

    public String text_team_getText() {
        return selenium.getText(MyAccountEnum.TEAM_TEXT);
    }

    public String text_textMessage1_getText() {
        return selenium.getText(MyAccountEnum.TEXT_MESSAGES1_TEXT);
    }

    public String text_textMessage2_getText() {
        return selenium.getText(MyAccountEnum.TEXT_MESSAGES2_TEXT);
    }

    public String text_userName_getText() {
        return selenium.getText(MyAccountEnum.USER_NAME_TEXT);
    }

    public String text_warningRedFlag_getText() {
        return selenium.getText(MyAccountEnum.WARNING_TEXT);
    }

    public PageMyAccount textField_confirmPassword_type(String textToSend) {
        selenium.type(MyAccountEnum.CONFIRM_PASSWORD_TEXTFIELD, textToSend);
        return this;
    }

    public PageMyAccount textField_currentPassword_type(String textToSend) {
        selenium.type(MyAccountEnum.CHANGE_PASSWORD_TITLE, textToSend);
        return this;
    }

    public PageMyAccount textField_emailAddress1_type(String textToType) {
        selenium.type(MyAccountEnum.EMAIL1_TEXTFIELD, textToType);
        return this;
    }

    public PageMyAccount textField_emailAddress2_type(String textToType) {
        selenium.type(MyAccountEnum.EMAIL2_TEXTFIELD, textToType);
        return this;
    }

    public PageMyAccount textField_newPassword_type(String textToSend) {
        selenium.type(MyAccountEnum.NEW_PASSWORD_TEXTFIELD, textToSend);
        return this;
    }

    public PageMyAccount textField_phoneNumber1_type(String textToType) {
        selenium.type(MyAccountEnum.PHONE1_TEXTFIELD, textToType);
        return this;
    }

    public PageMyAccount textField_phoneNumber2_type(String textToType) {
        selenium.type(MyAccountEnum.PHONE2_TEXTFIELD, textToType);
        return this;
    }

    public PageMyAccount textField_textMessage1_type(String textToType) {
        selenium.type(MyAccountEnum.TEXT_MESSAGES1_TEXTFIELD, textToType);
        return this;
    }

    public PageMyAccount textField_textMessage2_type(String textToType) {
        selenium.type(MyAccountEnum.TEXT_MESSAGES2_TEXTFIELD, textToType);
        return this;
    }

    public String title_accountInformation_getText() {
        return selenium.getText(MyAccountEnum.ACCOUNT_TITLE);
    }

    public String title_contactInformation_getText() {
        return selenium.getText(MyAccountEnum.CONTACT_TITLE);
    }

    public String title_emailAddresses_getText() {
        return selenium.getText(MyAccountEnum.EMAIL_TITLE);
    }

    public String title_loginInformation_getText() {
        return selenium.getText(MyAccountEnum.LOGIN_TITLE);
    }

    public String title_myAccountMain_getText() {
        return selenium.getText(MyAccountEnum.MAIN_TITLE);
    }

    public String title_phoneNumbers_getText() {
        return selenium.getText(MyAccountEnum.PHONE_TITLE);
    }

    public String title_redFlagPreferences_getText() {
        return selenium.getText(MyAccountEnum.RED_FLAGS_TITLE);
    }

    public String title_textMessages_getText() {
        return selenium.getText(MyAccountEnum.TEXT_TITLE);
    }

    public PageMyAccount validate() {
        page_titlesAndLabels_validate();
        return this;
    }

}
