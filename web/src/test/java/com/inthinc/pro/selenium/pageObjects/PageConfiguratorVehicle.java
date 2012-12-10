package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.Button;
import com.inthinc.pro.automation.elements.Text;
import com.inthinc.pro.automation.elements.TextField;
import com.inthinc.pro.selenium.pageEnums.VehicleSettingsEnum;

public class PageConfiguratorVehicle {

	public class ConfiguratorVehicleTextField {
		
		public TextField vehicleIdSelect() {
			return new TextField(VehicleSettingsEnum.VEHICLE_ID_SELECT_TEXTFIELD);
		}
		
	}
	
	public class ConfiguratorVehicleButton {
		
		public Button getSettings() {
			return new Button(VehicleSettingsEnum.GET_SETTINGS_BUTTON);
		}
		
		public Button getSettingsAction() {
			return new Button(VehicleSettingsEnum.GET_SETTINGS_ACTION_BUTTON);
		}
		
	}
	
	public class ConfiguratorVehicleText {
		
		public Text id() {
			return new Text(VehicleSettingsEnum.COLUMN_ID_TEXT);
		}
		
		public Text name() {
			return new Text(VehicleSettingsEnum.COLUMN_NAME_TEXT);
		}
		
		public Text category() {
			return new Text(VehicleSettingsEnum.COLUMN_CATEGORY_TEXT);
		}
		
		public Text unit() {
			return new Text(VehicleSettingsEnum.COLUMN_UNIT_TEXT);
		}
		
		public Text actual() {
			return new Text(VehicleSettingsEnum.COLUMN_ACTUAL_TEXT);
		}
		
		public Text actualValues() {
			return new Text(VehicleSettingsEnum.COLUMN_ACTUAL_VALUES_TEXT);
		}
		
		public Text actualModified() {
			return new Text(VehicleSettingsEnum.COLUMN_ACTUAL_MODIFIED_TEXT);
		}
		
		public Text desired() {
			return new Text(VehicleSettingsEnum.COLUMN_DESIRED_TEXT);
		}
		
		public Text desiredValues() {
			return new Text(VehicleSettingsEnum.COLUMN_DESIRED_VALUE_TEXT);
		}
		
		public Text desiredModified() {
			return new Text(VehicleSettingsEnum.COLUMN_DESIRED_VALUE_MODIFIED_TEXT);
		}
	}
	
	public ConfiguratorVehicleTextField _textFields() {
		return new ConfiguratorVehicleTextField();
	}
	
	public ConfiguratorVehicleButton _button() {
		return new ConfiguratorVehicleButton();
	}
	
	public ConfiguratorVehicleText _text() {
		return new ConfiguratorVehicleText();
	}
	
}
