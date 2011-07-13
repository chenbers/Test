package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.enums.SeleniumEnums;

public enum PopUpEnum implements SeleniumEnums {

    EMAIL_TEXTFIELD(null, "***_form:***_email"),
    EMAIL_HEADER(null, "***Header"),
    EMAIL_CANCEL(cancel, "emailReportPopUpSubmit"),
    EMAIL_SUBMIT(email, "***_form:emailReportPopupEmail###"),
    EMAIL_SUBTITLE(addresses, "//form[@id='***_form']/span/table/tbody/tr[1]/td"),
    X(null, "//div[@id='***ContentDiv']/div/img"),
    TITLE("E-mail this report to the following e-mail addresses.", "//table[@id='***ContentTable']/tbody/tr/td/div[@class='popupsubtitle']"),

    EDIT_HEADER("Edit Columns", "editColumnsHeader"),
    EDIT_LABEL(null,    "editColumnsForm:***Table-editColumnsGrid:###"),
    EDIT_CHECKBOX(null, "editColumnsForm:***Table-editColumnsGrid:###:***Table-col"),
    EDIT_SAVE(save, "editColumnsForm:***Table-editColumnsPopupSave"),
    EDIT_CANCEL(cancel, "editColumnsForm:***Table-editColumnsPopupCancel"),

    EDIT_COLUMNS("Edit Columns", "***-form:***EditColumns"),
    TOOL_BUTTON(null, "***-form:***_reportToolImageId"),

    /* Forgot Username/Password pop-up */
    FORGOT_ERROR(null, "//form[@id='changePasswordForm']/div[1]/div/span/span", "//span[@class='rich-message field-error field-msg']"),

    FORGOT_HEADER("Forgot User Name or Password?", "forgotPasswordPanelHeader"),
    FORGOT_EMAIL_FIELD(null, "changePasswordForm:email", "//form[@id='changePasswordForm']/div[1]/input", "//input[@id='changePasswordForm:email']"),
    FORGOT_EMAIL_LABEL("E-mail Address:", "//form[@id='changePasswordForm']/div[1]/text()[3]"),
    FORGOT_CANCEL_BUTTON("Cancel", "changePasswordForm:PasswordCancelButton", "//div[@class='popupactions']/buton[@type='button']/span", "//span[@class='cancel']"),
    FORGOT_SEND("Send", "changePasswordForm:PasswordSubmitButton", "//div[@class='popupactions']/buton[@type='submit']/span", "//span[@class='retrieve_password']"),
    FORGOT_CLOSE(cancel, "//div[@id='forgotPasswordPanelContentDiv']/div/img", "//img[@onclick=\"Richfaces.hideModalPanel('forgotPasswordPanel')\"]"),

    FORGOT_TITLE("Enter the e-mail address from your tiwiPro account. Information pertaining to your user name and password will be sent to this e-mail account.",
            "//table[@id='forgotPasswordPanelContentTable']/tbody/tr[2]/td/div"),

    /* Success with forgot Username/Password */
    MESSAGE_SENT_BULLET_1("Be patient, it may take a few minutes for the message to arrive in your Inbox.", "//table[@id='fd-table-1']/tbody/tr/td/div/ul/li[1]"),
    MESSAGE_SENT_BULLET_2("Check your Junk folder.", "//table[@id='fd-table-1']/tbody/tr/td/div/ul/li[2]"),
    MESSAGE_SENT_BULLET_3("If problems still exist, perform the process again.", "//table[@id='fd-table-1']/tbody/tr/td/div/ul/li[3]"),
    MESSAGE_SENT_BOLD("Didn't get the message?", "//table[@id='fd-table-1']/tbody/tr/td/div/h2"),
    MESSAGE_SENT_HEADER("Message Sent Successfully", "//li[@class='l grid_title']"),

    MESSAGE_SENT_MESSAGE("A message containing information about your user name and password has been sent to the e-mail address provided.", "//table[@id='fd-table-1']/tbody/tr/td/div/div"),

    /* Error pop-up for the Forgot Username/Password pop-up */
    ERROR_BUTTON_OK("OK", "loginErrorForm:loginOk", "//form[@id='loginErrorForm']/div/button", "//form[@id='loginErrorForm']/div/button/span"),
    ERROR_CLOSE("", "Richfaces.hideModalPanel('errorPanel')", "//div[@id='errorPanelContentDiv']/div", "//div[@id='errorPanelContentDiv']/div/img"), // id('forgotPasswordPanelContentDiv')/x:div/x:img
    ERROR_HEADER("Log In Error", "errorPanelHeader", "//table[@id='errorPanelContentTable']/tbody/tr[1]/td/div"),
    ERROR_MESSAGE("Incorrect user name or password.\n\nPlease try again.", "//p"),

    /* Forgot Password Change popUp */
    FORGOT_CHANGE_SUCCESS("Success", "successPanelHeader"),
    FORGOT_CHANGE_MESSAGE("Your password has been changed.", "//table[@id='successPanelContentTable']/tbody/tr[2]/td/p"),
    FORGOT_CHANGE_OK("OK", "ok"),

    /* My Account Change Password */
    MY_CHANGE_TITLE("Change Password", "changePasswordPanelHeader", "//td[@class='rich-mpnl-header-cell']/div[@class='rich-mpnl-text rich-mpnl-header popupHeader']/span"),
    MY_CHANGE_TITLE_REQUIRED("Change PasswordRequired", "changePasswordPanelHeader", "//td[@class='rich-mpnl-header-cell']/div[@class='rich-mpnl-text rich-mpnl-header popupHeader']/span"),
    MY_CHANGE_X(null, "//div[@id='changePasswordPanelCDiv']/div[@id='changePasswordPanelContentDiv']/div/img[contains(@src,'modal_close')]"),
    MY_CHANGE_CANCEL(cancel, "changePasswordForm:changePasswordCancel", "//button[@name='changePasswordForm:changePasswordCancel']", "//div/button[@class='left'][@type='button']"),
    MY_CHANGE_CHANGE("Change", "changePasswordForm:changePasswordSubmit", "//button[@name='changePasswordForm:changePasswordSubmit']", "//div/button[@class='left'][@type='submit']"),

    MY_CURRENT_TEXTFIELD(null, "changePasswordForm:oldPassword", "//inpute[@name='changePasswordForm:oldPassword", "//input[@type='password'][1]"),

    MY_STRENGTH_MSG("Begin Typing", "//form[@name='changePasswordForm']/table/tbody/tr[3]/td[1]"),
    MY_STRENGTH_METER_EMPTY(null, "changePasswordForm_meterEmpty", "//div[@id='pwdTest']/span"),
    MY_STRENGTH_METER_FULL(null, "changePasswordForm_meterFull", "//div[@id='pwdTest']/span/span"),

    MY_NEW_TEXTFIELD(null, "changePasswordForm:newPassword", "//inpute[@name='changePasswordForm:newPassword", "//input[@type='password'][2]"),

    MY_CONFIRM_TEXTFIELD(null, "changePasswordForm:confirmPassword", "//input[@name='changePasswordForm:confirmPassword", "//input[@type='password'][3]"),

    /* Admin Delete PopUp */
    DELETE_CONFIRM("Delete", "confirmDeleteForm:***Table-deleteButton"),
    DELETE_CANCEL("Cancel", "***Table-confirmDeleteCancel"),
    DELETE_HEADER(null, "confirmDeleteHeader"),
    DELETE_MESSAGE("Delete the following items? This action is irreversible.", "//table[@id='confirmDeleteContentTable']/tbody/tr[2]/td/div[@class='popupsubtitle']"),
    DELETE_CLOSE(null, "//div[@id='confirmDeleteContentDiv']/div/img"),

    /* Admin Details Delet PopUp */

    DETAILS_DELETE_CONFIRM("Delete", "confirmDeleteForm:***-deleteButtonDirect"),
    DETAILS_DELETE_CANCEL("Cancel", "***-confirmDeleteCancel"),

    /* Update Password Reminder */
    UPDATE_PASSWORD_HEADER("Update Password Reminder", "passwordReminderPanelHeader"),
    UPDATE_PASSWORD_MESSAGE("Welcome ***. Your password is going to expire in ### days. Please update your password before it expires. ",
            "//table[@id='passwordReminderPanelContentTable']/tbody/tr[2]/td/p[2]"),
    UPDATE_PASSWORD_CLOSE(null, "//a[contains(@id,':passwordWarningClose')]/img"),
    UPDATE_PASSWORD_CHANGE_PASSWORD("Change Password", "//a[contains(@id,':changePasswordLink')]"),

    /* Change Password Form */
    CHANGE_PASSWORD_FORM_CHANGE_X(null, "//a[contains(@id,':updateCredentialsSubmit')]"),
    CHANGE_PASSWORD_FORM_STRENGTH_MSG("Begin Typing", "changePasswordForm_strengthMsg"),
    
    /* Assign Driver to Vehicle */
    
    TXTLINKSORT_D2V_EMPID("", "chooseDriverForm:editVehicle-chooseDriverTable:empIdheader"),
    TXTLINKSORT_D2V_NAME("", "chooseDriverForm:editVehicle-chooseDriverTable:fullNameheader"),
    TXTLINKSORT_D2V_TEAM("", "chooseDriverForm:editVehicle-chooseDriverTable:groupIdheader"),
    TXTLINKSORT_D2V_STATUS("", "chooseDriverForm:editVehicle-chooseDriverTable:statusheader"),
    TXTLINKSORT_D2V_ASSIGNED("", "chooseDriverForm:editVehicle-chooseDriverTable:driverIdheader"),
    
    TXTTABLE_D2V_EMPID("", "chooseDriverForm:editVehicle-chooseDriverTable:###:empId"),
    TXTTABLE_D2V_NAME("", "chooseDriverForm:editVehicle-chooseDriverTable:###:fullName"),
    TXTTABLE_D2V_TEAM("", "chooseDriverForm:editVehicle-chooseDriverTable:###:groupId"),
    TXTTABLE_D2V_STATUS("", "chooseDriverForm:editVehicle-chooseDriverTable:###:status"),
    TXTTABLE_D2V_ASSIGNED("", "chooseDriverForm:editVehicle-chooseDriverTable:###:assignedDriver"),
    TXTTABLELINK_D2V_ASSIGN("", "chooseDriverForm:editVehicle-chooseDriverTable:###:driverId"),
    
    
	    
    /* Exclude events */
    EXCLUDE_NOTIFICATIONS_TITLE(null, "clear***PanelHeader"),
    EXCLUDE_NOTIFICATIONS_MESSAGE(null, "***_clearItemForm:clearItem"),
    EXCLUDE_NOTIFICATIONS_HEADER(null, "excludePopupSubtitle"),
    EXCLUDE_NOTIFICATIONS_CONFIRM("Yes", "***_clearItemForm:***_yes"),
    EXCLUDE_NOTIFICATIONS_CANCEL("No", "***_no"),
    EXCLUDE_NOTIFICATIONS_CLOSE(null, "closeExcludePopup"),
    
    EXCLUDE_PERFORMANCE_TITLE(null, "clear***PanelHeader"),
    EXCLUDE_PERFORMANCE_MESSAGE(null, "***clearItemForm:clearItem"),
    EXCLUDE_PERFORMANCE_HEADER(null, "excludePopupSubtitle"),
    EXCLUDE_PERFORMANCE_CONFIRM("Yes", "clearItemForm:***Confirm"),
    EXCLUDE_PERFORMANCE_CANCEL("No", "***No"),
    EXCLUDE_PERFORMANCE_CLOSE(null, "closeExcludePopup"),
    
    

    ;

    private String text, url;
    private String[] IDs;

    private PopUpEnum(String url) {
        this.url = url;
    }

    private PopUpEnum(String text, String... IDs) {
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
