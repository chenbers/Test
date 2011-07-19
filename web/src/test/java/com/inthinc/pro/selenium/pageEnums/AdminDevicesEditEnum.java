package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.enums.SeleniumEnums;

public enum AdminDevicesEditEnum implements SeleniumEnums {
    
    ;

    private String text, url;
    private String[] IDs;
    
    private AdminDevicesEditEnum(String url){
        this.url = url;
    }
    private AdminDevicesEditEnum(String text, String ...IDs){
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
