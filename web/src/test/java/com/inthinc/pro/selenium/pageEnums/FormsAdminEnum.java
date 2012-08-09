package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.automation.utils.Id;
import com.inthinc.pro.automation.utils.Xpath;

public enum FormsAdminEnum implements SeleniumEnums {
    DEFAULT_URL(appUrl + "/forms"),
    TITLE("Forms", Xpath.start().span(Id.clazz("forms")).toString()),

    DELETE("Delete", ""),
    APPROVED("Approved", ""),
    SEARCH_TEXTFIELD(null, ""),
    SEARCH_BUTTON("Search", ""),
    EDIT_COLUMNS("Edit Columns", ""),
    PUBLISH("Publish", ""),
    
    FORMS_HEADER("Forms", ""),
    DESCRIPTION_HEADER("Description", ""),
    APPROVED_HEADER("Approved", ""),
    STATUS_HEADER("Status", ""),
    OWNER_HEADER("Owner", ""),
    EDIT_HEADER("Edit", ""),
    
    CHECKBOX(null, ""),
    FORMS_ENTRY(null, ""),
    DESCRIPTION_ENTRY(null, ""),
    APPROVED_ENTRY(null, ""),
    STATUS_ENTRY(null, ""),
    OWNER_ENTRY(null, ""),
    EDIT_ENTRY(null, ""), 
    ;

    private String text, url;
    private String[] IDs;
    
    private FormsAdminEnum(String url){
    	this.url = url;
    }
    private FormsAdminEnum(String text, String ...IDs){
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
