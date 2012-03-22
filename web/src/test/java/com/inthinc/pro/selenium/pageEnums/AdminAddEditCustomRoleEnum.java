package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.interfaces.SeleniumEnums;

public enum AdminAddEditCustomRoleEnum implements SeleniumEnums {
    
    URL("app/admin/editCustomRole"),
    
    TITLE("Admin - Add/Edit Custom Role", "//span[@class='admin']"),
    
    TOP_SAVE(save, "edit-form:editCustomRoleSave1"),
    TOP_CANCEL(cancel, "edit-form:editCustomRoleCancel1"),
    BOTTOM_SAVE(save, "edit-form:editCustomRoleSave2"),
    BOTTOM_CANCEL(cancel, "edit-form:editCustomRoleCancel2"),
    
    NAME("Name", "edit-form:editCustomRole-name"),
    
    HEADER("Role Name", "//div[@class='add_section_title']"),
    
    CHECK_ALL(null, "edit-form:accessPoints:selectAllCustomRoles"),
    CHECK_ENTRY(null, "edit-form:accessPoints:###:select"),
    ACCESS_POINT(null, "//tbody[@id='edit-form:accessPoints:tb']/tr[###]/td[2]"),
    
    ACCESS_POINTS_HEADER("Access Points", "//table[@id='edit-form:accessPoints']/thead/tr/th[2]/div")
    
    ;
    private String text, url;
    private String[] IDs;
    
    private AdminAddEditCustomRoleEnum(String url){
        this.url = url;
    }
    private AdminAddEditCustomRoleEnum(String text, String ...IDs){
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
