package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.selenium.pageEnums.MyAccountEnum;
import com.inthinc.pro.selenium.pageEnums.NotificationsRedFlags;


public class PageNotificationsRedFlags extends NotificationsBar {

        //TODO: jwimmer: to dTanner: ?  is this a placeholder or has it been replaced???
    @Override
    public String getExpectedPath() {
        return NotificationsRedFlags.DEFAULT_URL.getURL();
    }
}
