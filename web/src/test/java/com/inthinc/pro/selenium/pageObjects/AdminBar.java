package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.TextLinkContextSense;
import com.inthinc.pro.selenium.pageEnums.AdminBarEnum;

public abstract class AdminBar extends NavigationBar {

	protected class AdminBarButtons extends NavigationBarButtons {
	}

	protected class AdminBarDropDowns extends NavigationBarDropDowns {
	}

	protected class AdminBarLinks extends NavigationBarLinks {

		public TextLinkContextSense adminAccount() {
			return new TextLinkContextSense(AdminBarEnum.ACCOUNT);
		}

		public TextLinkContextSense adminAddCustomRole() {
			return new TextLinkContextSense(AdminBarEnum.ADD_CUSTOM_ROLE);
		}

		public TextLinkContextSense adminAddRedFlag() {
			return new TextLinkContextSense(AdminBarEnum.ADD_RED_FLAG);
		}

		public TextLinkContextSense adminAddReport() {
			return new TextLinkContextSense(AdminBarEnum.ADD_REPORT);
		}

		public TextLinkContextSense adminAddUser() {
			return new TextLinkContextSense(AdminBarEnum.ADD_USER);
		}

		public TextLinkContextSense adminAddVehicle() {
			return new TextLinkContextSense(AdminBarEnum.ADD_VEHICLE);
		}

		public TextLinkContextSense adminCustomRoles() {
			return new TextLinkContextSense(AdminBarEnum.CUSTOM_ROLES);
		}

		public TextLinkContextSense adminDevices() {
			return new TextLinkContextSense(AdminBarEnum.DEVICES);
		}

		public TextLinkContextSense adminOrganization() {
			return new TextLinkContextSense(AdminBarEnum.ORGANIZATION);
		}

		public TextLinkContextSense adminRedFlags() {
			return new TextLinkContextSense(AdminBarEnum.RED_FLAGS);
		}

		public TextLinkContextSense adminReports() {
			return new TextLinkContextSense(AdminBarEnum.REPORTS);
		}

		public TextLinkContextSense adminSpeedByStreet() {
			return new TextLinkContextSense(AdminBarEnum.SPEED_BY_STREET);
		}

		public TextLinkContextSense adminUsers() {
			return new TextLinkContextSense(AdminBarEnum.USERS);
		}

		public TextLinkContextSense adminVehicles() {
			return new TextLinkContextSense(AdminBarEnum.VEHICLES);
		}

		public TextLinkContextSense adminZones() {
			return new TextLinkContextSense(AdminBarEnum.ZONES);
		}
	}

	protected class AdminBarTextFields extends NavigationBarTextFields {
	}

	protected class AdminBarTexts extends NavigationBarTexts {
	}
	
	protected class AdminBarPopUps extends MastheadPopUps{

		public AdminBarPopUps(){
			
		}
		public AdminBarPopUps(String page) {
			super(page);
		}

		public AdminBarPopUps(String page, Types report, Integer i) {
			super(page, report, i);
		}
	}
	
}
