package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.selenium.pageEnums.NotificationsRedFlags;


public class PageNotificationsRedFlags extends NotificationsBar {

    @Override
    public String getExpectedPath() {
        return NotificationsRedFlags.DEFAULT_URL.getURL();
    }
}
