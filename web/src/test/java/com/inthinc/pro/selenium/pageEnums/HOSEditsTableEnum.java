package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.enums.SeleniumEnums;

public enum HOSEditsTableEnum implements SeleniumEnums {
    
    GROUP_NAME("Group Name", "hosReports_tableForm:hosReports_viewTable:###:column0"),
    DRIVER_NAME("Driver Name", "hosReports_tableForm:hosReports_viewTable:###:column1"),
    EMPLOYEE_ID("Employee Id", "hosReports_tableForm:hosReports_viewTable:###:column2"),
    STATUS("Status", "hosReports_tableForm:hosReports_viewTable:###:column3"),
    LOG_TIME("Log Time", "hosReports_tableForm:hosReports_viewTable:###:column4"),
    ADJUSTED_TIME("Adjusted Time", "hosReports_tableForm:hosReports_viewTable:###:column5"),
    USER_NAME("User Name", "hosReports_tableForm:hosReports_viewTable:###:column6"),
    ;
    
    private String text, url;
    private String[] IDs;
    
    private HOSEditsTableEnum(String url){
        this.url = url;
    }
    private HOSEditsTableEnum(String text, String ...IDs){
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
