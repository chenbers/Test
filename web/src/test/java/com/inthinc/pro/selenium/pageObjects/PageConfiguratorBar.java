package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.Button;
import com.inthinc.pro.selenium.pageEnums.ConfiguratorBarEnum;

public class PageConfiguratorBar {

	public class ConfiguratorBarButtons {
		
		public Button editSingleSettings() {
			return new Button(ConfiguratorBarEnum.EDIT_SINGLE_SETTINGS);		
		}
		
		public Button vehicleSettings() {
			return new Button(ConfiguratorBarEnum.VEHICLE_SETTINGS);
		}
		
		public Button settingsTemplates() {
			return new Button(ConfiguratorBarEnum.SETTING_TEMPLATE);
		}
		
		public Button applyTemplates() {
			return new Button(ConfiguratorBarEnum.APPLY_TEMPLATES);
		}
		
		public Button logout() {
			return new Button(ConfiguratorBarEnum.LOGOUT);
		}
	}
	
	public ConfiguratorBarButtons _button() {
		return new ConfiguratorBarButtons();
	}
}
