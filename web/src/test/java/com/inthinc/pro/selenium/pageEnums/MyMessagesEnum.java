package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.interfaces.SeleniumEnums;

public enum MyMessagesEnum implements SeleniumEnums {
    
    TITLE(null, "//span[@class='textmsg']"),
    DEFAULT_URL("/app/messages"),
    
    TEAM_DROP_DOWN("Team:", "//div[@class='dhx_combo_input']"),
    
    START_DATE("Start:", "display-form:calendarStart"),
    STOP_DATE("End:", "display-form:calendarEnd"),
    
    REFRESH_BUTTON("Refresh", "display-form:refresh"),
    
    REPLY_TO_SELECTED(null, "display-form:reply"),
    
    SELECT_ALL_CHECK_BOX(null, "display-form:messages:selectAll"),
    
    SENT_SORT("Sent", "display-form:messages:dateheader:sortDiv"),
    
    TO_HEADER("To", "display-form:messages:toheader:sortDiv"),
    FROM_HEADER("From", "display-form:messages:fromheader:sortDiv"),
    MESSAGE_HEADER("Messages", "display-form:messages:msgheader:sortDiv"),
    
    TABLE_CHECK_BOX(null, "display-form:messages:###:select"),
    SENT_DATE_TABLE(null, "display-form:messages:###:date"),
    TO_NAME_TABLE(null, "display-form:messages:###:to"),
    FROM_NAME_TABLE(null, "display-form:messages:###:from"),
    MESSAGE_TABLE(null, "display-form:messages:###:msg"),
    
    INBOX_BUTTON("Inbox","side-nav-form:inbox-vlt-inbox"),
    COMPOSE_BUTTON("Compose", "side-nav-form:inbox-vlt-sendMail"),
    SENT_BUTTON("Sent", "side-nav-form:inbox-vlt-sentMail"),
    
    SENT_ITEMS("Sent Items", "//tr[1]/td[@class='inbox'][1]"),
    NUMBER_MESSAGES("# Messages", "//tr[1]/td[@class='inbox'][2]"),
    
    DRIVER_REMOVE_ALL(null, "display-form:driverListremoveAlllink"),
    DRIVER_REMOVE(null, "display-form:driverListremovelink"),
    DRIVER_COPY(null, "display-form:driverListcopylink"),
    DRIVER_COPY_ALL(null, "display-form:driverListcopyAlllink"),
    VEHICLE_REMOVE_ALL(null, "display-form:vehicleListremoveAlllink"),
    VEHICLE_REMOVE(null, "display-form:vehicleListremovelink"),
    VEHICLE_COPY(null, "display-form:vehicleListcopylink"),
    VEHICLE_COPY_ALL(null, "display-form:vehicleListcopyAlllink"),
    GROUP_REMOVE_ALL(null, "display-form:groupListremoveAlllink"),
    GROUP_REMOVE(null, "display-form:groupListremovelink"),
    GROUP_COPY(null, "display-form:groupListcopylink"),
    GROUP_COPY_ALL(null, "display-form:groupListcopyAlllink"),
    
    //TODO DTANNER TO FILL IN
    DRIVER_HEADER("Drivers", ""),
    DRIVER_LEFT(null, "display-form:driverListinternal_tab"),
    DRIVER_RIGHT(null, "display-form:driverListtlcontentBox"),
    VEHICLE_HEADER("Vehicles", ""),
    VEHICLE_LEFT(null, ""),
    VEHICLE_RIGHT(null, ""),
    GROUP_HEADER("Groups", ""),
    GROUP_LEFT(null, ""),
    GROUP_RIGHT(null, ""),
    COMPOSE_MSG_HEADER("Compose Message", ""),
    COMPOSE_MSG_TEXT_FIELD(null, "display-form:msgText")
    ;
    
    private String text, url;
    private String[] IDs;
    
    private MyMessagesEnum(String url){
        this.url = url;
    }
    private MyMessagesEnum(String text, String ...IDs){
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
