package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.interfaces.SeleniumEnums;

public enum AdminCustomRolesEnum implements SeleniumEnums {
    
    URL(appUrl + "/admin/customRoles"), 
    ROLE_NAME("Role Name", "admin-table-form:customRolesTable:###:customRolesTableName"),
    
    TITLE("Admin - Custom Roles", "//span[@class='admin']"),
    
    ;
    private String text, url;
    private String[] IDs;
    
    private AdminCustomRolesEnum(String url){
        this.url = url;
    }
    private AdminCustomRolesEnum(String text, String ...IDs){
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
