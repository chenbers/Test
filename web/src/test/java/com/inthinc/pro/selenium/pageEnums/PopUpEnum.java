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
