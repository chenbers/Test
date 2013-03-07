package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.interfaces.SeleniumEnums;

public enum RouteGetEndpointEnum implements SeleniumEnums {

    URL("forms_service/route/"),
    RESPONSE(null, "//body/pre");
    
    private String text, url;
    private String[] IDs;
    
    private RouteGetEndpointEnum(String url){
    	this.url = url;
    }
    private RouteGetEndpointEnum(String text, String ...IDs){
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
