package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.interfaces.SeleniumEnums;

public enum DOTTimeRemainingHTMLEnum implements SeleniumEnums {
    
    TITLE("Driver HOS Log", "//span[@id='hosReports_htmlText']/table[***]/tbody/tr[6]/td[3]/span"),
    
    
    // TODO: Gotta figure out a good way to automate this.
    // TODO: jwimmer: can we add ID's to some of the HOS Reports?  It would make automating them 
    //      possible.  This one initially, not everything needs an ID, just the Group column header would work.
    
    ;

    private String text, url;
    private String[] IDs;
    
    private DOTTimeRemainingHTMLEnum(String url){
        this.url = url;
    }
    private DOTTimeRemainingHTMLEnum(String text, String ...IDs){
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
