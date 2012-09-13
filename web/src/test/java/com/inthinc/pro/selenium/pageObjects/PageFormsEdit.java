 package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.CalendarObject;
import com.inthinc.pro.automation.elements.CheckBox;
import com.inthinc.pro.automation.elements.DropDown;
import com.inthinc.pro.automation.elements.Text;
import com.inthinc.pro.automation.elements.TextButton;
import com.inthinc.pro.automation.elements.TextField;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.selenium.pageEnums.FormsAddEnum;
import com.inthinc.pro.selenium.pageEnums.FormsEditEnum;
import com.inthinc.pro.selenium.pageObjects.Masthead.MastheadPopUps;
import com.inthinc.pro.selenium.pageObjects.PopUps.MyAccountChangePassword;

public class PageFormsEdit extends FormsTables {

    public PageFormsEdit() {
//    	  checkMe.add(FormsEditEnum.TITLE);
//        checkMe.add(FormsEditEnum.SAVE_TOP);    
//        checkMe.add(FormsEditEnum.CANCEL_TOP);
//    	  checkMe.add(FormsEditEnum.SAVE_BOTTOM);    
//        checkMe.add(FormsEditEnum.CANCEL_BOTTOM);
//        checkMe.add(FormsEditEnum.NAME_FIELD);
//        checkMe.add(FormsEditEnum.DESCRIPTION_FIELD);
//        checkMe.add(FormsEditEnum.ROUTE_TRIGGER_EXPRESSION_FIELD);
//        checkMe.add(FormsEditEnum.VEHICLE_TAGS_DROPDOWN);
//        checkMe.add(FormsEditEnum.FILTER_GROUPS_FIELD);
//        checkMe.add(FormsEditEnum.DATANAME_FIELD);
//        checkMe.add(FormsEditEnum.NAME_LABEL);
//        checkMe.add(FormsEditEnum.DESCRIPTION_LABEL);
//        checkMe.add(FormsEditEnum.TRIGGER_LABEL);        
//        checkMe.add(FormsEditEnum.ROUTE_LABEL);          
//        checkMe.add(FormsEditEnum.VERSION_LABEL);         
//        checkMe.add(FormsEditEnum.HOS_LABEL);   
//        checkMe.add(FormsEditEnum.VEHICLE_TAGS_LABEL);   
//        checkMe.add(FormsEditEnum.FILTER_GROUPS_LABEL);   
//        checkMe.add(FormsEditEnum.PROPERTIES_LABEL);   
//        checkMe.add(FormsEditEnum.VIEW_PROPERTIES_LABEL);   
//        checkMe.add(FormsEditEnum.CAPTION_FIELD);
//        checkMe.add(FormsEditEnum.TRIGGER_DROPDOWN);
//        checkMe.add(FormsEditEnum.HOS_DROPDOWN);
//        checkMe.add(FormsEditEnum.RANGE_NUMERIC_CHECKBOX);
//        checkMe.add(FormsEditEnum.PREVIEW_AREA);
    }

    public class FormsAddButtons {

        public TextButton saveAsNewTop() {
            return new TextButton(FormsEditEnum.SAVE_AS_NEW_TOP);
        }
    	
        public TextButton saveTop() {
            return new TextButton(FormsEditEnum.SAVE_TOP);
        }
        
        public TextButton cancelTop() {
        	return new TextButton(FormsEditEnum.CANCEL_TOP);
        }
        
        public TextButton saveAsNewBottom() {
            return new TextButton(FormsEditEnum.SAVE_AS_NEW_BOTTOM);
        }
        
        public TextButton saveBottom() {
            return new TextButton(FormsEditEnum.SAVE_BOTTOM);
        }
        
        public TextButton cancelBottom() {
        	return new TextButton(FormsEditEnum.CANCEL_BOTTOM);
        }
        
        public TextButton delete() {
        	return new TextButton(FormsEditEnum.DELETE_CONTROL);
        }
        
        public TextButton advancedArrow() {
        	return new TextButton(FormsEditEnum.ADVANCED_ARROW);
        }

    }
    
    public class FormsAddCheckBoxes extends FormsTablesCheckBoxes {
    	
    	public CheckBox groups() {
    		return new CheckBox(FormsEditEnum.GROUPS_CHECKBOX);
    	}
    	
    	public CheckBox readOnly() {
    		return new CheckBox(FormsEditEnum.READ_ONLY_CHECKBOX);
    	}
    	
    	public CheckBox required() {
    		return new CheckBox(FormsEditEnum.REQUIRED_CHECKBOX);
    	}
    	
    	public CheckBox length() {
    		return new CheckBox(FormsEditEnum.LENGTH_CHECKBOX);
    	}
    	
    	public CheckBox rangeNumeric() {
    		return new CheckBox(FormsEditEnum.RANGE_NUMERIC_CHECKBOX);
    	}
    	
    	public CheckBox minimumNumericInclusive() {
    		return new CheckBox(FormsEditEnum.MINIMUM_NUMERIC_INCLUSIVE_CHECKBOX);
    	}
    	
    	public CheckBox maximumNumericInclusive() {
    		return new CheckBox(FormsEditEnum.MAXIMUM_NUMERIC_INCLUSIVE_CHECKBOX);
    	}
    	
    	public CheckBox dateRange() {
    		return new CheckBox(FormsEditEnum.RANGE_DATE_CHECKBOX);
    	}
    	
    	public CheckBox minimumDateInclusive() {
    		return new CheckBox(FormsEditEnum.MINIMUM_DATE_INCLUSIVE_CHECKBOX);
    	}
    	
    	public CheckBox maximumDateInclusive() {
    		return new CheckBox(FormsEditEnum.MAXIMUM_DATE_INCLUSIVE_CHECKBOX);
    	}
    	
    	public CheckBox looped() {
    		return new CheckBox(FormsEditEnum.LOOPED_CHECKBOX);
    	}
    	
    	public CheckBox displayOnOneScreen() {
    		return new CheckBox(FormsEditEnum.DISPLAY_ON_ONE_SCREEN_CHECKBOX);
    	}
    	
    }

    public class FormsAddDropDowns {
    	
    	public DropDown trigger() {
    		return new DropDown(FormsEditEnum.TRIGGER_DROPDOWN);
    	}
    	
    	public DropDown HOS() {
    		return new DropDown(FormsEditEnum.HOS_DROPDOWN);
    	}
    	
    	public DropDown vehicleTags() {
    		return new DropDown(FormsEditEnum.VEHICLE_TAGS_DROPDOWN);
    	}
    	
    	public DropDown dateMinimum() {
    		return new CalendarObject(FormsEditEnum.MINIMUM_DATE_DROPDOWN);
    	}
    	
    	public DropDown dateMaximum() {
    		return new CalendarObject(FormsEditEnum.MAXIMUM_DATE_DROPDOWN);
    	}
    	
    }
    
    public class FormsAddLinks {}

    public class FormsAddTextFields {

    	public TextField name() {
    		return new TextField(FormsEditEnum.NAME_FIELD);
    	}
    	
    	public TextField description() {
    		return new TextField(FormsEditEnum.DESCRIPTION_FIELD);
    	}
    	
    	public TextField routeTriggerExpression() {
    		return new TextField(FormsEditEnum.ROUTE_TRIGGER_EXPRESSION_FIELD);
    	}
    	
    	public TextField filterGroups() {
    		return new TextField(FormsEditEnum.FILTER_GROUPS_FIELD);
    	}
    	
    	public TextField dataName() {
    		return new TextField(FormsEditEnum.DATANAME_FIELD);
    	}
    	
    	public TextField minimumRange() {
    		return new TextField(FormsEditEnum.MINIMUM_NUMERIC_FIELD);
    	}
    	
    	public TextField maximumRange() {
    		return new TextField(FormsEditEnum.MAXIMUM_NUMERIC_FIELD);
    	}
    	
    	public TextField invalidText() {
    		return new TextField(FormsEditEnum.INVALID_TEXT_FIELD);
    	}
    	
    	public TextField optionEnglish() {
    		return new TextField(FormsEditEnum.OPTION_FIELD);
    	}
    	
    	public TextField optionUnderlyingValue() {
    		return new TextField(FormsEditEnum.OPTION_UNDERLYING_VALUE_FIELD);
    	}
    	public TextField relevance() {
    		return new TextField(FormsEditEnum.RELEVANCE_FIELD);
    	}
    	
    	public TextField constraint() {
    		return new TextField(FormsEditEnum.CONSTRAINT_FIELD);
    	}

    	public TextField instanceDestination() {
    		return new TextField(FormsEditEnum.INSTANCE_DESTINATION_FIELD);
    	}

    }
    
    public class FormsAddTexts  {
    	
    	public Text nameLabel() {
    		return new Text(FormsEditEnum.NAME_LABEL);
    	}
    	
    	public Text descriptionLabel() {
    		return new Text(FormsEditEnum.DESCRIPTION_LABEL);
    	}
    	
    	public Text triggerLabel() {
    		return new Text(FormsEditEnum.TRIGGER_LABEL);
    	}
    	
    	public Text routeTriggerExpressionLabel () {
    		return new Text(FormsEditEnum.ROUTE_LABEL);
    	}
    	
    	public Text versionLabel() {
    		return new Text(FormsEditEnum.VERSION_LABEL);
    	}
    	
    	public Text HOSLabel() {
    		return new Text(FormsEditEnum.HOS_LABEL);
    	}
    	
    	public Text vehicleTagsLabel() {
    		return new Text(FormsEditEnum.VEHICLE_TAGS_LABEL);
    	}
    	
    	public Text filterGroupsLabel() {
    		return new Text(FormsEditEnum.FILTER_GROUPS_LABEL);
    	}
    	
    	public Text propertiesLabel() {
    		return new Text(FormsEditEnum.PROPERTIES_LABEL);
    	}
    	
    	public Text viewPropertiesLabel() {
    		return new Text(FormsEditEnum.VIEW_PROPERTIES_LABEL);
    	}
    	
    	public Text nameError() {
    		return new Text(FormsEditEnum.ERROR_NAME_LABEL);
    	}
    	
    	public Text controlError() {
    		return new Text(FormsEditEnum.ERROR_CONTROL_LABEL);
    	}
    	
    	public Text dataNameError() {
    		return new Text(FormsEditEnum.DATA_NAME_ERROR);
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


    public class FormsAddPopUps extends MastheadPopUps {
    	public OptionsEditor optionsEditor() {
    		return new OptionsEditor();
    		}
    	}

    

    public SeleniumEnums setUrl() {
        return FormsEditEnum.DEFAULT_URL;
    }


    protected boolean checkIsOnPage() {
        return _textField().name().isPresent() && _dropDown().trigger().isPresent();
    }
    
}
