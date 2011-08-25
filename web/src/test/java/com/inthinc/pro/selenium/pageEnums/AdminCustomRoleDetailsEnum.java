package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.enums.SeleniumEnums;

public enum AdminCustomRoleDetailsEnum implements SeleniumEnums {
    
    TITLE("Admin - NAME_OF_ROLE Details", "//span[@class='admin']"),
    
    BACK_TO_TABLE("Back to Custom Roles", "roleForm:roleCancel1"),
    DELETE(delete, "roleForm:roleDelete1"),
    EDIT("Edit", "roleForm:roleEdit1"),
    NAME("Name", "//form[@id='roleForm']/div/div/div/table/tbody[1]/tr/td[1]/table/tbody/tr[1]/td[2]"),
    ACCESS_POINTS_ITEM(null, "//form[@id='roleForm']/div/div/div/table/tbody[1]/tr/td[1]/table/tbody/tr[2]/td[2]/ul/li[###]"),
    ACCESS_POINTS_LABEL("Access Points", "//form[@id='roleForm']/div/div/div/table/tbody[1]/tr/td[1]/table/tbody/tr[2]/td[1]"),
    
    ;
    private String text, url;
    private String[] IDs;
    
    private AdminCustomRoleDetailsEnum(String url){
        this.url = url;
    }
    private AdminCustomRoleDetailsEnum(String text, String ...IDs){
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
