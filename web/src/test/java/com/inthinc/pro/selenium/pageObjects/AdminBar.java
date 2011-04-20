package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.selenium.pageEnums.AdminBarEnum;

public abstract class AdminBar extends NavigationBar {

    public void link_adminAccount_click() {
        selenium.click(AdminBarEnum.ACCOUNT).waitForPageToLoad();
        setCurrentLocation();
    }

    public void link_adminAddCustomRole_click() {
        selenium.click(AdminBarEnum.ADD_CUSTOM_ROLE).waitForPageToLoad();
        setCurrentLocation();
    }

    public void link_adminAddRedFlag_click() {
        selenium.click(AdminBarEnum.ADD_RED_FLAG).waitForPageToLoad();
        setCurrentLocation();
    }

    public void link_adminAddReport_click() {
        selenium.click(AdminBarEnum.ADD_REPORT).waitForPageToLoad();
        setCurrentLocation();
    }

    public void link_adminAddUser_click() {
        selenium.click(AdminBarEnum.ADD_USER).waitForPageToLoad();
        setCurrentLocation();
    }

    public void link_adminAddVehicle_click() {
        selenium.click(AdminBarEnum.ADD_VEHICLE).waitForPageToLoad();
        setCurrentLocation();
    }

    public void link_adminCustomRoles_click() {
        selenium.click(AdminBarEnum.CUSTOM_ROLES).waitForPageToLoad();
        setCurrentLocation();
    }

    public void link_adminDevices_click() {
        selenium.click(AdminBarEnum.DEVICES).waitForPageToLoad();
        setCurrentLocation();
    }

    public void link_adminOrganization_click() {
        selenium.click(AdminBarEnum.ORGANIZATION).waitForPageToLoad();
        setCurrentLocation();
    }

    public void link_adminRedFlags_click() {
        selenium.click(AdminBarEnum.RED_FLAGS).waitForPageToLoad();
        setCurrentLocation();
    }

    public void link_adminReports_click() {
        selenium.click(AdminBarEnum.REPORTS).waitForPageToLoad();
        setCurrentLocation();
    }

    public void link_adminSpeedByStreet_click() {
        selenium.click(AdminBarEnum.SPEED_BY_STREET).waitForPageToLoad();
        setCurrentLocation();
    }

    public void link_adminUsers_click() {
        selenium.click(AdminBarEnum.USERS).waitForPageToLoad();
        setCurrentLocation();
    }

    public void link_adminVehicles_click() {
        selenium.click(AdminBarEnum.VEHICLES).waitForPageToLoad();
        setCurrentLocation();
    }

    public void link_adminZones_click() {
        selenium.click(AdminBarEnum.ZONES).waitForPageToLoad();
        setCurrentLocation();
    }

}
