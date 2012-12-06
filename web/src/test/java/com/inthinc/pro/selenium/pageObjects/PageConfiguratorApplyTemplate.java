package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.Button;
import com.inthinc.pro.automation.elements.CheckBox;
import com.inthinc.pro.automation.elements.DropDown;
import com.inthinc.pro.automation.elements.Link;
import com.inthinc.pro.automation.elements.NavTree;
import com.inthinc.pro.automation.elements.Text;
import com.inthinc.pro.selenium.pageEnums.ApplyTemplatesEnum;

public class PageConfiguratorApplyTemplate {

	public class ConfiguratorApplyTemplateDropDown {
		
		public DropDown account() {
			return new DropDown(ApplyTemplatesEnum.ACCOUNT_DROPDOWN);
		}
	}
	
	public class ConfiguratorApplyTemplateNavTree {
		
		public NavTree group() {
			return new NavTree(ApplyTemplatesEnum.GROUP_NAV_TREE);
		}
	}
	
	public class ConfiguratorApplyTemplateButton {
		
		public Button template() {
			return new Button(ApplyTemplatesEnum.TEMPLATE_BUTTON);
		}
	}

	public class ConfiguratorApplyTemplateLink {
	
		public Link audition() {
			return new Link(ApplyTemplatesEnum.AUDITION_LINK);
		}
		
		public Link apply() {
			return new Link(ApplyTemplatesEnum.APPLY_LINK);
		}
	}

	public class ConfiguratorApplyTemplateCheckBox {
		
		public CheckBox acceptTemplateValue() {
			return new CheckBox(ApplyTemplatesEnum.ACCEPT_TEMPLATE_VALUE_CHECKBOX);
		}
	}

	public class ConfiguratorApplyTemplateText {
		
		public Text vehicle() {
			return new Text(ApplyTemplatesEnum.VEHICLE_TEXT);
		}
		
		public Text settingId() {
			return new Text(ApplyTemplatesEnum.SETTING_ID_TEXT);
		}
		
		public Text setting() {
			return new Text(ApplyTemplatesEnum.SETTING_TEXT);
		}
		
		public Text conflictingSettings() {
			return new Text(ApplyTemplatesEnum.CONFLICTING_SETTINGS_TEXT);
		}
		
		public Text desiredValue() {
			return new Text(ApplyTemplatesEnum.DESIRED_VALUE_TEXT);
		}
		
		public Text templateValue() {
			return new Text(ApplyTemplatesEnum.TEMPLATE_VALUE_TEXT);
		}
	}
	
	public ConfiguratorApplyTemplateDropDown _dropdown() {
		return new ConfiguratorApplyTemplateDropDown();
	}
	
	public ConfiguratorApplyTemplateNavTree _navtree() {
		return new ConfiguratorApplyTemplateNavTree();
	}
	
	public ConfiguratorApplyTemplateButton _button() {
		return new ConfiguratorApplyTemplateButton();
	}
	
	public ConfiguratorApplyTemplateLink _link() {
		return new ConfiguratorApplyTemplateLink();
	}
	
	public ConfiguratorApplyTemplateCheckBox _checkbox() {
		return new ConfiguratorApplyTemplateCheckBox();
	}
	
	public ConfiguratorApplyTemplateText _text() {
		return new ConfiguratorApplyTemplateText();
	}
}
