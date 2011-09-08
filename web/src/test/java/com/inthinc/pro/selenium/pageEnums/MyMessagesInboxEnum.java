package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.interfaces.SeleniumEnums;

public enum MyMessagesInboxEnum implements SeleniumEnums {
    
    TITLE("Text Message: Inbox", "//span[@class='textmsg']"),
    DEFAULT_URL("/app/messages"),
    
    TEAM_DROP_DOWN("Team", "//div[@class='dhx_combo_input']"),
    
    START_DATE("Start", "display-form:calendarStart"),
    STOP_DATE("End", "display-form:calendarEnd"),
    
    REFRESH_BUTTON("Refresh", "display-form:refresh"),
    
    REPLY_TO_SELECTED(null, "display-form:reply"),
    SELECT_ALL_CHECK_BOX(null, "display-form:messages:selectAll"),
    SENT_SORT("Sent", "display-form:messages:dateheader:sortDiv"),
    
    INBOX_BUTTON("Inbox","side-nav-form:inbox-vlt-inbox"),
    COMPOSE_BUTTON("Compose", "side-nav-form:inbox-vlt-sendMail"),
    SENT_BUTTON("Sent", "side-nav-form:inbox-vlt-sentMail")
    
    ;
    
    private String text, url;
    private String[] IDs;
    
    private MyMessagesInboxEnum(String url){
        this.url = url;
    }
    private MyMessagesInboxEnum(String text, String ...IDs){
        this.text=text;
        this.IDs = IDs;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public String[] getIDs() {
        return IDs;
    }

    @Override
    public String getURL() {
        return url;
    }
    
}
