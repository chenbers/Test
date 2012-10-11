package com.inthinc.pro.selenium.pageEnums;



import com.inthinc.pro.automation.interfaces.SeleniumEnums;

public enum ResetPasswordEnum implements SeleniumEnums {

    RESET_PASSWORD_URL(appUrl + "/resetPassword"),
    
    HEADER("Forgot User Name or Password?", "//ul[@id='grid_nav']/li[2]"),
    INFORMATION_TEXT("Enter the e-mail address from your tiwiPro account. Information pertaining to your user name and password will be sent to this e-mail account.", "//div/div[@style='padding:0 20px']"),
    ERROR_TEXT(null, "//span[@class='rich-message-label']"),
    
    EMAIL_ADDRESS_FIELD("E-mail Address:", "changePasswordForm:email"),
    SEND_BUTTON("Send", "changePasswordForm:PasswordSubmitButton")
    ;

    private String text, url;
    private String[] IDs;
    
    private ResetPasswordEnum(String url){
    	this.url = url;
    }
    private ResetPasswordEnum(String text, String ...IDs){
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
