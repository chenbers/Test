package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.TextLink;
import com.inthinc.pro.selenium.pageEnums.AdminBarEnum;

public abstract class AdminBar extends NavigationBar {
    
    public class AdminBarLinks {

        public TextLink adminAccount() {
            return new TextLink(AdminBarEnum.ACCOUNT);
        }

        public TextLink adminAddCustomRole() {
            return new TextLink(AdminBarEnum.ADD_CUSTOM_ROLE);
        }

        public TextLink adminAddRedFlag() {
            return new TextLink(AdminBarEnum.ADD_RED_FLAG);
        }

        public TextLink adminAddReport() {
            return new TextLink(AdminBarEnum.ADD_REPORT);
        }

        public TextLink adminAddUser() {
            return new TextLink(AdminBarEnum.ADD_USER);
        }

        public TextLink adminAddVehicle() {
            return new TextLink(AdminBarEnum.ADD_VEHICLE);
        }

        public TextLink adminCustomRoles() {
            return new TextLink(AdminBarEnum.CUSTOM_ROLES);
        }

        public TextLink adminDevices() {
            return new TextLink(AdminBarEnum.DEVICES);
        }

        public TextLink adminOrganization() {
            return new TextLink(AdminBarEnum.ORGANIZATION);
        }

        public TextLink adminRedFlags() {
            return new TextLink(AdminBarEnum.RED_FLAGS);
        }

        public TextLink adminReports() {
            return new TextLink(AdminBarEnum.REPORTS);
        }

        public TextLink adminSpeedByStreet() {
            return new TextLink(AdminBarEnum.SPEED_BY_STREET);
        }

        public TextLink adminUsers() {
            return new TextLink(AdminBarEnum.USERS);
        }

        public TextLink adminVehicles() {
            return new TextLink(AdminBarEnum.VEHICLES);
        }

        public TextLink adminZones_click() {
            return new TextLink(AdminBarEnum.ZONES);
        }
    }

    public AdminBarLinks _link() {
        return new AdminBarLinks();
    }
}
