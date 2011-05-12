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
	EDIT_LABEL(null, "editColumnsForm:***-editColumnsGrid:###"),
	EDIT_CHECKBOX(null, "editColumnsForm:***-editColumnsGrid:###:***-col"),
	EDIT_SAVE(save, "editColumnsForm:***-editColumnsPopupSave"),
	EDIT_CANCEL(cancel, "editColumnsForm:***-editColumnsPopupCancel"),
	
	
	EDIT_COLUMNS("Edit Columns", "***-form:***EditColumns"),
	TOOL_BUTTON(null,"***-form:***_reportToolImageId"),
	
	
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

    MESSAGE_SENT_MESSAGE("A message containing information about your user name and password has been sent to the e-mail address provided.",
            "//table[@id='fd-table-1']/tbody/tr/td/div/div"),
            
            
            
    /* Error pop-up for the Forgot Username/Password pop-up */
    ERROR_BUTTON_OK("OK", "loginErrorForm:loginOk", "//form[@id='loginErrorForm']/div/button", "//form[@id='loginErrorForm']/div/button/span"),
    ERROR_CLOSE("", "Richfaces.hideModalPanel('errorPanel')", "//div[@id='errorPanelContentDiv']/div", "//div[@id='errorPanelContentDiv']/div/img"),//id('forgotPasswordPanelContentDiv')/x:div/x:img
    ERROR_HEADER("Log In Error", "errorPanelHeader", "//table[@id='errorPanelContentTable']/tbody/tr[1]/td/div"),
    ERROR_MESSAGE("Incorrect user name or password.\n\nPlease try again.", "//p"),
    
    
    
    /* Password Change popUp */
    CHANGE_SUCCESS("Success", "successPanelHeader"),
    CHANGE_MESSAGE("Your password has been changed.", "//table[@id='successPanelContentTable']/tbody/tr[2]/td/p"),
    CHANGE_OK("OK", "ok"),

	;

    private String text, url;
    private String[] IDs;
    
    private PopUpEnum(String url){
    	this.url = url;
    }
    private PopUpEnum(String text, String ...IDs){
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
