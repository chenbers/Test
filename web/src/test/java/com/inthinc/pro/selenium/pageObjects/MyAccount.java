package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.selenium.pageEnums.MyAccountEnum;

public class MyAccount extends Masthead {

    public static enum redFlagPreferences {
        EMAIL1("1", MyAccountEnum.EMAIL1_TEXTFIELD),
        EMAIL2("2", MyAccountEnum.EMAIL2_TEXTFIELD),
        PHONE1("3", MyAccountEnum.PHONE1_TEXTFIELD),
        PHONE2("4", MyAccountEnum.PHONE2_TEXTFIELD),
        TEXT1("5", MyAccountEnum.TEXT_MESSAGES1_TEXTFIELD),
        TEXT2("6", MyAccountEnum.TEXT_MESSAGES2_TEXTFIELD);

        private String value;
        private MyAccountEnum ID;

        private redFlagPreferences(String value, MyAccountEnum ID) {
            this.value = value;
            this.ID = ID;
        }

        public MyAccountEnum getID() {
            return ID;
        }

        public String getValue() {
            return value;
        }
    };

    public MyAccount button_cancel_click(){
        selenium.click(MyAccountEnum.CANCEL_BUTTON);
        return this;
    }

    public MyAccount button_change_click(){
        selenium.click(MyAccountEnum.CANCEL_BUTTON);
        return this;
    }

    public MyAccount button_changePassword_click() {
        selenium.click(MyAccountEnum.CHANGE_PASSWORD_BUTTON);
        return this;
    }

    public MyAccount button_changePasswordX_click(){
        selenium.click(MyAccountEnum.CHANGE_PASSWORD_X);
        return this;
    }

    public MyAccount button_edit_click() {
        selenium.click(MyAccountEnum.EDIT_BUTTON);
        return this;
    }

    public MyAccount button_save_click(){
        selenium.click(MyAccountEnum.SAVE_BUTTON);
        return this;
    }

    public MyAccount dropDown_critical_selectText(String selection) {
        selenium.select(MyAccountEnum.CRITICAL_SELECT, selection);
        String selected = selenium.getSelectedLabel(MyAccountEnum.CRITICAL_SELECT);
        assertEquals(selection, selected);
        return this;
    }

    public MyAccount dropDown_critical_selectValue(redFlagPreferences selection) {
        selenium.select(MyAccountEnum.CRITICAL_SELECT, selection.getValue());
        String selected = selenium.getSelectedLabel(MyAccountEnum.CRITICAL_SELECT);
        assertEquals(getSelectedValue(selection), selected);
        return this;
    }

    public MyAccount dropDown_fuelEfficiency_selectText(String selection){
        selenium.select(MyAccountEnum.FUEL_EFFICIENCY_SELECT, selection);
        String selected = selenium.getSelectedLabel(MyAccountEnum.FUEL_EFFICIENCY_SELECT);
        assertEquals(selection, selected);
        return this;
    }

    public MyAccount dropDown_information_selectText(String selection) {
        selenium.select(MyAccountEnum.INFORMATION_SELECT, selection);
        String selected = selenium.getSelectedLabel(MyAccountEnum.INFORMATION_SELECT);
        assertEquals(selection, selected);
        return this;
    }

    public MyAccount dropDown_information_selectValue(redFlagPreferences selection) {
        selenium.select(MyAccountEnum.INFORMATION_SELECT, selection.getValue());
        String selected = selenium.getSelectedLabel(MyAccountEnum.INFORMATION_SELECT);
        assertEquals(getSelectedValue(selection), selected);
        return this;
    }

    public MyAccount dropDown_locale_selectText(String selection){
        selenium.select(MyAccountEnum.LOCALE_SELECT, selection);
        String selected = selenium.getSelectedLabel(MyAccountEnum.LOCALE_SELECT);
        assertEquals(selection, selected);
        return this;
    }

    public MyAccount dropDown_measurement_selectText(String selection){
        selenium.select(MyAccountEnum.MEASUREMENT_SELECT, selection);
        String selected = selenium.getSelectedLabel(MyAccountEnum.MEASUREMENT_SELECT);
        assertEquals(selection, selected);
        return this;
    }

    public MyAccount dropDown_warning_selectText(String selection) {
        selenium.select(MyAccountEnum.WARNING_SELECT, selection);
        String selected = selenium.getSelectedLabel(MyAccountEnum.WARNING_SELECT);
        assertEquals(selection, selected);
        return this;
    }

    public MyAccount dropDown_warning_selectValue(redFlagPreferences selection) {
        selenium.select(MyAccountEnum.WARNING_SELECT, selection.getValue());
        String selected = selenium.getSelectedLabel(MyAccountEnum.WARNING_SELECT);
        assertEquals(getSelectedValue(selection), selected);
        return this;
    }

    public String getExpectedPath() {
        return MyAccountEnum.MY_ACCOUNT_URL.getURL();
    }

    private String getSelectedValue(redFlagPreferences selection) {
        return selenium.getSelectedLabel(selection.getID());
    }

    public String label_confirmPassword_getText(){
        return selenium.getText(MyAccountEnum.CHANGE_PASSWORD_CONFIRM_TITLE);
    }

    public String label_criticalRedFlag_getText() {
        return selenium.getText(MyAccountEnum.CRITICAL_TITLE);
    }

    public String label_currentPassword_getText(){
        return selenium.getText(MyAccountEnum.CHANGE_PASSWORD_CURRENT_TITLE);
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

    public String label_newPassword_getText(){
        return selenium.getText(MyAccountEnum.CHANGE_PASSWORD_NEW_TITLE);
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


    public MyAccount page_titlesAndLabels_validate(){
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

    public MyAccount textField_confirmPassword_type(String textToSend){
        selenium.type(MyAccountEnum.CHANGE_PASSWORD_CONFIRM_TEXTFIELD, textToSend);
        return this;
    }

    public String textField_criticalRedFlag_getText() {
        return selenium.getText(MyAccountEnum.CRITICAL_TEXT);
    }

    public MyAccount textField_currentPassword_click(String textToSend){
        selenium.type(MyAccountEnum.CHANGE_PASSWORD_TITLE, textToSend);
        return this;
    }

    public String textField_emailAddress1_getText() {
        return selenium.getText(MyAccountEnum.EMAIL1_TEXT);
    }

    public MyAccount textField_emailAddress1_type(String textToType) {
        selenium.type(MyAccountEnum.EMAIL1_TEXTFIELD, textToType);
        return this;
    }

    public String textField_emailAddress2_getText() {
        return selenium.getText(MyAccountEnum.EMAIL2_TEXT);
    }

    public MyAccount textField_emailAddress2_type(String textToType) {
        selenium.type(MyAccountEnum.EMAIL2_TEXTFIELD, textToType);
        return this;
    }

    public String textField_fuelEfficiency_getText() {
        return selenium.getText(MyAccountEnum.USER_NAME_TEXT);
    }

    public String textField_group_getText() {
        return selenium.getText(MyAccountEnum.GROUP_TEXT);
    }

    public String textField_informationRedFlag_getText() {
        return selenium.getText(MyAccountEnum.INFORMATION_TEXT);
    }

    public String textField_locale_getText() {
        return selenium.getText(MyAccountEnum.USER_NAME_TEXT);
    }

    public String textField_measurement_getText() {
        return selenium.getText(MyAccountEnum.USER_NAME_TEXT);
    }

    public String textField_name_getText() {
        return selenium.getText(MyAccountEnum.NAME_TEXT);
    }

    public MyAccount textField_newPassword_click(String textToSend){
        selenium.type(MyAccountEnum.CHANGE_PASSWORD_NEW_TEXTFIELD, textToSend);
        return this;
    }

    public String textField_passwordStrength_getText(){
        return selenium.getText(MyAccountEnum.CHANGE_PASSWORD_STRENGTH_MSG);
    }

    public String textField_phoneNumber1_getText() {
        return selenium.getText(MyAccountEnum.PHONE1_TEXT);
    }

    public MyAccount textField_phoneNumber1_type(String textToType) {
        selenium.type(MyAccountEnum.PHONE1_TEXTFIELD, textToType);
        return this;
    }

    public String textField_phoneNumber2_getText() {
        return selenium.getText(MyAccountEnum.PHONE2_TEXT);
    }

    public MyAccount textField_phoneNumber2_type(String textToType) {
        selenium.type(MyAccountEnum.PHONE2_TEXTFIELD, textToType);
        return this;
    }

    public String textField_team_getText() {
        return selenium.getText(MyAccountEnum.TEAM_TEXT);
    }

    public String textField_textMessage1_getText() {
        return selenium.getText(MyAccountEnum.TEXT_MESSAGES1_TEXT);
    }
    
    public MyAccount textField_textMessage1_type(String textToType) {
        selenium.type(MyAccountEnum.TEXT_MESSAGES1_TEXTFIELD, textToType);
        return this;
    }
    
    public String textField_textMessage2_getText() {
        return selenium.getText(MyAccountEnum.TEXT_MESSAGES2_TEXT);
    }
    
    public MyAccount textField_textMessage2_type(String textToType) {
        selenium.type(MyAccountEnum.TEXT_MESSAGES2_TEXTFIELD, textToType);
        return this;
    }
    
    public String textField_userName_getText() {
        return selenium.getText(MyAccountEnum.USER_NAME_TEXT);
    }
    
    public String textField_warningRedFlag_getText() {
        return selenium.getText(MyAccountEnum.WARNING_TEXT);
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
    
    public boolean validate() {
        page_titlesAndLabels_validate();
        return true;
    }
    
    public Boolean popup_changePassword_isVisible(){
        return selenium.isVisible(MyAccountEnum.CHANGE_PASSWORD_CHANGE);
    }
    
}
