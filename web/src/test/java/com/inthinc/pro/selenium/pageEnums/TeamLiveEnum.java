package com.inthinc.pro.selenium.pageEnums;



import com.inthinc.pro.automation.interfaces.SeleniumEnums;

import com.inthinc.pro.automation.utils.Id;
import com.inthinc.pro.automation.utils.Xpath;

public enum TeamLiveEnum implements SeleniumEnums {
    
    LEGEND_TITLE("Team Legend",null, Xpath.start().span(Id.clazz("legend")).toString()),
    LEGEND_ENTRY(null,Xpath.start().td(Id.id("icos1:###")).div().span().toString()), 
    REFRESH(null, "refresh:team-liveFleetMapRefresh"),
    
    ;
    private String text, url;
    private String[] IDs;
    
    private TeamLiveEnum(String url){
    	this.url = url;
    }
    private TeamLiveEnum(String text, String ...IDs){
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
