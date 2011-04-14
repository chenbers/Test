package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.selenium.AbstractPage;
import com.inthinc.pro.selenium.pageEnums.NotificationsBarEnum;

public abstract class NotificationsBar extends AbstractPage {

    protected String current = "redFlags";

    protected NotificationsBar setCurrentLocation() {
        current = browser_location_getCurrent();
        if (current.equals("")) {
            current = "redFlags";
        }
        return this;
    }

    protected NotificationsBar click_link(NotificationsBarEnum clickMe) {
        clickMe.setCurrent(current);
        selenium.click(clickMe);
        selenium.waitForPageToLoad();
        setCurrentLocation();
        return this;
    }

    public NotificationsBar link_redFlags_click() {
        return click_link(NotificationsBarEnum.RED_FLAGS);
    }

    public NotificationsBar link_safety_click() {
        return click_link(NotificationsBarEnum.SAFETY);
    }

    public NotificationsBar link_diagnostics_click() {
        return click_link(NotificationsBarEnum.DIAGNOSTICS);
    }

    public NotificationsBar link_zones_click() {
        return click_link(NotificationsBarEnum.ZONES);
    }

    public NotificationsBar link_hosExceptions_click() {
        return click_link(NotificationsBarEnum.HOS_EXCEPTIONS);
    }

    public NotificationsBar link_emergency_click() {
        return click_link(NotificationsBarEnum.SAFETY);
    }

    public NotificationsBar link_crashHistory_click() {
        return click_link(NotificationsBarEnum.SAFETY);
    }
}
