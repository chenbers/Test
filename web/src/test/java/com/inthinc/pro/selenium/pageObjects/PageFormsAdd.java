 package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.CheckBox;
import com.inthinc.pro.automation.elements.DropDown;
import com.inthinc.pro.automation.elements.TextButton;
import com.inthinc.pro.automation.elements.TextField;
import com.inthinc.pro.automation.elements.Text;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.selenium.pageEnums.FormsAddEnum;

public class PageFormsAdd extends FormsTables {

    public PageFormsAdd() {
    	checkMe.add(FormsAddEnum.TITLE);
        checkMe.add(FormsAddEnum.SAVE_TOP);    
        checkMe.add(FormsAddEnum.CANCEL_TOP);
    	checkMe.add(FormsAddEnum.SAVE_BOTTOM);    
        checkMe.add(FormsAddEnum.CANCEL_BOTTOM);
        checkMe.add(FormsAddEnum.NAME_FIELD);
        checkMe.add(FormsAddEnum.DESCRIPTION_FIELD);
        checkMe.add(FormsAddEnum.ROUTE_TRIGGER_EXPRESSION_FIELD);
        checkMe.add(FormsAddEnum.VEHICLE_TAGS_DROPDOWN);
        checkMe.add(FormsAddEnum.FILTER_GROUPS_FIELD);
        checkMe.add(FormsAddEnum.DATANAME_FIELD);
        checkMe.add(FormsAddEnum.NAME_LABEL);
        checkMe.add(FormsAddEnum.DESCRIPTION_LABEL);
        checkMe.add(FormsAddEnum.TRIGGER_LABEL);        
        checkMe.add(FormsAddEnum.ROUTE_LABEL);          
        checkMe.add(FormsAddEnum.VERSION_LABEL);         
        checkMe.add(FormsAddEnum.HOS_LABEL);   
        checkMe.add(FormsAddEnum.VEHICLE_TAGS_LABEL);   
        checkMe.add(FormsAddEnum.FILTER_GROUPS_LABEL);   
        checkMe.add(FormsAddEnum.PROPERTIES_LABEL);   
        checkMe.add(FormsAddEnum.VIEW_PROPERTIES_LABEL);   
        checkMe.add(FormsAddEnum.CAPTION_FIELD);
        checkMe.add(FormsAddEnum.TRIGGER_DROPDOWN);
        checkMe.add(FormsAddEnum.HOS_DROPDOWN);
        checkMe.add(FormsAddEnum.CHECKBOX);
        checkMe.add(FormsAddEnum.PREVIEW_AREA);
    }

    public class FormsAddButtons extends NavigationBarButtons {

        public TextButton deleteTop() {
            return new TextButton(FormsAddEnum.SAVE_TOP);
        }
        
        public TextButton cancelTop() {
        	return new TextButton(FormsAddEnum.CANCEL_TOP);
        }
        
        public TextButton delete() {
            return new TextButton(FormsAddEnum.SAVE_BOTTOM);
        }
        
        public TextButton cancel() {
        	return new TextButton(FormsAddEnum.CANCEL_BOTTOM);
        }

    }
    
    public class FormsAddCheckBoxes extends FormsTablesCheckBoxes {
    	
    	public CheckBox groups() {
    		return new CheckBox(FormsAddEnum.CHECKBOX);
    	}
    }

    public class FormsAddDropDowns extends NavigationBarDropDowns {
    	
    	public DropDown trigger() {
    		return new DropDown(FormsAddEnum.TRIGGER_DROPDOWN);
    	}
    	
    	public DropDown HOS() {
    		return new DropDown(FormsAddEnum.HOS_DROPDOWN);
    	}
    	
    	public DropDown vehicleTags() {
    		return new DropDown(FormsAddEnum.VEHICLE_TAGS_DROPDOWN);
    	}
    	
    }
    
    public class FormsAddLinks extends NavigationBarLinks {}

    public class FormsAddTextFields extends NavigationBarTextFields {

    	public TextField name() {
    		return new TextField(FormsAddEnum.NAME_FIELD);
    	}
    	
    	public TextField description() {
    		return new TextField(FormsAddEnum.DESCRIPTION_FIELD);
    	}
    	
    	public TextField routeTriggerExpression() {
    		return new TextField(FormsAddEnum.ROUTE_TRIGGER_EXPRESSION_FIELD);
    	}
    	
    	public TextField filterGroups() {
    		return new TextField(FormsAddEnum.FILTER_GROUPS_FIELD);
    	}
    	
    	public TextField dataName() {
    		return new TextField(FormsAddEnum.DATANAME_FIELD);
    	}
    }
    
    public class FormsAddTexts extends NavigationBarTexts {
    	
    	public Text nameLabel() {
    		return new Text(FormsAddEnum.NAME_LABEL);
    	}
    	
    	public Text descriptionLabel() {
    		return new Text(FormsAddEnum.DESCRIPTION_LABEL);
    	}
    	
    	public Text triggerLabel() {
    		return new Text(FormsAddEnum.TRIGGER_LABEL);
    	}
    	
    	public Text routeTriggerExpressionLabel () {
    		return new Text(FormsAddEnum.ROUTE_LABEL);
    	}
    	
    	public Text versionLabel() {
    		return new Text(FormsAddEnum.VERSION_LABEL);
    	}
    	
    	public Text HOSLabel() {
    		return new Text(FormsAddEnum.HOS_LABEL);
    	}
    	
    	public Text vehicleTagsLabel() {
    		return new Text(FormsAddEnum.VEHICLE_TAGS_LABEL);
    	}
    	
    	public Text filterGroupsLabel() {
    		return new Text(FormsAddEnum.FILTER_GROUPS_LABEL);
    	}
    	
    	public Text propertiesLabel() {
    		return new Text(FormsAddEnum.PROPERTIES_LABEL);
    	}
    	
    	public Text viewPropertiesLabel() {
    		return new Text(FormsAddEnum.VIEW_PROPERTIES_LABEL);
    	}
    	
    }

    public FormsAddButtons _button() {
        return new FormsAddButtons();
    }
    
    public FormsTablesCheckBoxes _checkBox() {
    	return new FormsTablesCheckBoxes();
    }

    public FormsAddDropDowns _dropDown() {
        return new FormsAddDropDowns();
    }

    public FormsAddLinks _link() {
        return new FormsAddLinks();
    }

    public FormsAddTexts _text() {
        return new FormsAddTexts();
    }

    public FormsAddTextFields _textField() {
        return new FormsAddTextFields();
    }
    
    public FormsAddPopUps _popUp(){
        return new FormsAddPopUps();
    }


    public class FormsAddPopUps extends NavigationBarPopUps {}
    
    @Override
    public SeleniumEnums setUrl() {
        return FormsAddEnum.DEFAULT_URL;
    }

    @Override
    protected boolean checkIsOnPage() {
        return _textField().name().isPresent() && _textField().routeTriggerExpression().isPresent();
    }
    
}
