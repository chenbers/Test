package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.CheckBox;
import com.inthinc.pro.automation.elements.DropDown;
import com.inthinc.pro.automation.elements.Link;
import com.inthinc.pro.automation.elements.Text;
import com.inthinc.pro.selenium.pageEnums.EditSingleSettingEnum;

public class PageConfiguratorEdit extends PageConfiguratorBar {
	
	public class ConfiguratorEditDropDowns {
		
		public DropDown account() {
			return new DropDown(EditSingleSettingEnum.ACCOUNT_DROPDOWN);
		}
		
		public DropDown group() {
			return new DropDown(EditSingleSettingEnum.GROUP_DROPDOWN);
		}
		
		public DropDown settings() {
			return new DropDown(EditSingleSettingEnum.SETTINGS_DROPDOWN);
		}
		
	}
	
	public class ConfiguratorEditLinks {
		
		public Link get() {
			return new Link(EditSingleSettingEnum.GET_SETTING_LINK);
		}
		
		public Link commit() {
			return new Link(EditSingleSettingEnum.COMMIT_SETTINGS_LINK);
		}
		
		public Link delete() {
			return new Link(EditSingleSettingEnum.DELETE_SETTINGS_LINK);
		}
		
	}
	
	public class ConfiguratorEditCheckBoxes {
		
		public CheckBox selectAll() {
			return new CheckBox(EditSingleSettingEnum.SELECT_ALL_CHECKBOX);
		}
		
		public CheckBox select() {
			return new CheckBox(EditSingleSettingEnum.SELECT_CHECKBOX);
		}
		
	}
	
	public class PageConfiguratorEditText {
		
		public Text vehicles() {
			return new Text(EditSingleSettingEnum.VEHICLES_TEXT);
		}
		
		public Text desiredValue() {
			return new Text(EditSingleSettingEnum.DESIRED_VALUES_TEXT);
		}
		
	}
	
	public ConfiguratorEditDropDowns _dropDown() {
		return new ConfiguratorEditDropDowns();
	}
	
	public ConfiguratorEditLinks _link() {
		return new ConfiguratorEditLinks();
	}
	
	public ConfiguratorEditCheckBoxes _checkBox() {
		return new ConfiguratorEditCheckBoxes();
	}
	
	public PageConfiguratorEditText _text() {
		return new PageConfiguratorEditText();
	}
}
