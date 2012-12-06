package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.Button;
import com.inthinc.pro.automation.elements.DropDown;
import com.inthinc.pro.automation.elements.Link;
import com.inthinc.pro.automation.elements.Text;
import com.inthinc.pro.automation.elements.TextField;
import com.inthinc.pro.selenium.pageEnums.SettingsTemplatesEnum;

public class PageConfiguratorSettingsTemplates {

	public class ConfiguratorSettingsTemplatesDropdowns {
		
		public DropDown account() {
			return new DropDown(SettingsTemplatesEnum.ACCOUNT_DROPDOWN);
		}
		
	}
	
	public class ConfiguratorSettingsTemplatesButtons {
		
		public Button template() {
			return new Button(SettingsTemplatesEnum.TEMPLATE_BUTTON); 
		}
		
	}
	
	public class ConfiguratorSettingsTemplatesLinks {
		
		public Link newTemplate() {
			return new Link(SettingsTemplatesEnum.NEW_TEMPLATE_LINK);
		}
		
		public Link openTemplate() {
			return new Link(SettingsTemplatesEnum.OPEN_TEMPLATE_LINK);
		}
		
		public Link cloneTemplate() {
			return new Link(SettingsTemplatesEnum.CLONE_TEMPLATE_LINK);
		}
		
		public Link resetTemplate() {
			return new Link(SettingsTemplatesEnum.RESET_TEMPLATE_LINK);
		}
		
		public Link deleteTemplate() {
			return new Link(SettingsTemplatesEnum.DELETE_TEMPLATE_LINK);
		}
		
		public Link commitTemplate() {
			return new Link(SettingsTemplatesEnum.COMMIT_TEMPLATE_LINK);
		}
		
	}
	
	public class ConfiguratorSettingsTemplatesTextFields {
		
		public TextField name() {
			return new TextField(SettingsTemplatesEnum.TEMPLATE_NAME_TEXTFIELD);
		}
		
		public TextField description() {
			return new TextField(SettingsTemplatesEnum.TEMPLATE_DESCRIPTION_TEXTFIELD);
		}
		
	}
	
	public class ConfiguratorSettingsTemplatesTexts {
		
		public Text vehicleSettingId() {
			return new Text(SettingsTemplatesEnum.VEHICLE_SETTING_ID_TEXT);
		}
		
		public Text vehicleName() {
			return new Text(SettingsTemplatesEnum.VEHICLE_NAME_TEXT);
		}
		
		public Text vehicleCategory() {
			return new Text(SettingsTemplatesEnum.VEHICLE_CATEGORY_TEXT);
		}
		
		public Text vehicleUnit() {
			return new Text(SettingsTemplatesEnum.VEHICLE_UNIT_TEXT);
		}
		
		public Text desiredValue() {
			return new Text(SettingsTemplatesEnum.DESIRED_VALUES_TEXT);
		}
		
		public Text desiredModified() {
			return new Text(SettingsTemplatesEnum.DESIRED_MODIFIED_TEXT);
		}
		
	}
	
	public ConfiguratorSettingsTemplatesButtons _button() {
		return new ConfiguratorSettingsTemplatesButtons();
	}
	
	public ConfiguratorSettingsTemplatesLinks _link() {
		return new ConfiguratorSettingsTemplatesLinks();
	}
	
	public ConfiguratorSettingsTemplatesTextFields _textField() {
		return new ConfiguratorSettingsTemplatesTextFields();
	}
	
	public ConfiguratorSettingsTemplatesTexts _text() {
		return new ConfiguratorSettingsTemplatesTexts();
	}
	
}
