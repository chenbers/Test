 package com.inthinc.pro.selenium.pageObjects;


import com.inthinc.pro.automation.elements.CheckBox;
import com.inthinc.pro.automation.elements.DropDown;
import com.inthinc.pro.automation.elements.Text;
import com.inthinc.pro.automation.elements.TextButton;
import com.inthinc.pro.automation.elements.TextField;
import com.inthinc.pro.automation.elements.TextFieldTable;
import com.inthinc.pro.automation.elements.TextLink;
import com.inthinc.pro.automation.elements.TextTableLink;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.selenium.pageEnums.FormsAddEnum;

public class PageFormsAdd extends FormsTables {

    public PageFormsAdd() {
//    	  checkMe.add(FormsAddEnum.TITLE);
//        checkMe.add(FormsAddEnum.SAVE_TOP);    
//        checkMe.add(FormsAddEnum.CANCEL_TOP);
//    	  checkMe.add(FormsAddEnum.SAVE_BOTTOM);    
//        checkMe.add(FormsAddEnum.CANCEL_BOTTOM);
//        checkMe.add(FormsAddEnum.NAME_FIELD);
//        checkMe.add(FormsAddEnum.DESCRIPTION_FIELD);
//        checkMe.add(FormsAddEnum.ROUTE_TRIGGER_EXPRESSION_FIELD);
//        checkMe.add(FormsAddEnum.VEHICLE_TAGS_DROPDOWN);
//        checkMe.add(FormsAddEnum.FILTER_GROUPS_FIELD);
//        checkMe.add(FormsAddEnum.DATANAME_FIELD);
//        checkMe.add(FormsAddEnum.NAME_LABEL);
//        checkMe.add(FormsAddEnum.DESCRIPTION_LABEL);
//        checkMe.add(FormsAddEnum.TRIGGER_LABEL);        
//        checkMe.add(FormsAddEnum.ROUTE_LABEL);          
//        checkMe.add(FormsAddEnum.VERSION_LABEL);         
//        checkMe.add(FormsAddEnum.HOS_LABEL);   
//        checkMe.add(FormsAddEnum.VEHICLE_TAGS_LABEL);   
//        checkMe.add(FormsAddEnum.FILTER_GROUPS_LABEL);   
//        checkMe.add(FormsAddEnum.PROPERTIES_LABEL);   
//        checkMe.add(FormsAddEnum.VIEW_PROPERTIES_LABEL);   
//        checkMe.add(FormsAddEnum.CAPTION_FIELD);
//        checkMe.add(FormsAddEnum.TRIGGER_DROPDOWN);
//        checkMe.add(FormsAddEnum.HOS_DROPDOWN);
//        checkMe.add(FormsAddEnum.RANGE_NUMERIC_CHECKBOX);
//        checkMe.add(FormsAddEnum.PREVIEW_AREA);
    }

    public class FormsAddButtons {

        public TextButton saveTop() {
            return new TextButton(FormsAddEnum.SAVE_TOP);
        }
        
        public TextButton cancelTop() {
        	return new TextButton(FormsAddEnum.CANCEL_TOP);
        }
        
        public TextButton saveBottom() {
            return new TextButton(FormsAddEnum.SAVE_BOTTOM);
        }
        
        public TextButton cancelBottom() {
        	return new TextButton(FormsAddEnum.CANCEL_BOTTOM);
        }
        
        public TextButton delete() {
        	return new TextButton(FormsAddEnum.DELETE_CONTROL);
        }
        
        public TextButton advancedArrow() {
        	return new TextButton(FormsAddEnum.ADVANCED_ARROW);
        }

    }
    
    public class FormsAddCheckBoxes {
    	
    	public CheckBox groups() {
    		return new CheckBox(FormsAddEnum.GROUPS_CHECKBOX);
    	}
    	
    	public CheckBox readOnly() {
    		return new CheckBox(FormsAddEnum.READ_ONLY_CHECKBOX);
    	}
    	
    	public CheckBox required() {
    		return new CheckBox(FormsAddEnum.REQUIRED_CHECKBOX);
    	}
    	
    	public CheckBox lengthEnable() {
    		return new CheckBox(FormsAddEnum.LENGTH_CHECKBOX);
    	}
    	
    	public CheckBox rangeEnableNumeric() {
    		return new CheckBox(FormsAddEnum.RANGE_NUMERIC_CHECKBOX);
    	}
    	
    	public CheckBox minimumTextInclusive() {
    		return new CheckBox(FormsAddEnum.MINIMUM_TEXT_INCLUSIVE_CHECKBOX);
    	}
    	
    	public CheckBox maximumTextInclusive() {
    		return new CheckBox(FormsAddEnum.MAXIMUM_TEXT_INCLUSIVE_CHECKBOX);
    	}
    	
    	public CheckBox minimumNumericInclusive() {
    		return new CheckBox(FormsAddEnum.MINIMUM_NUMERIC_INCLUSIVE_CHECKBOX);
    	}
    	
    	public CheckBox maximumNumericInclusive() {
    		return new CheckBox(FormsAddEnum.MAXIMUM_NUMERIC_INCLUSIVE_CHECKBOX);
    	}
    	
    	public CheckBox rangeEnableDate() {
    		return new CheckBox(FormsAddEnum.RANGE_DATE_CHECKBOX);
    	}
    	
    	public CheckBox minimumDateInclusive() {
    		return new CheckBox(FormsAddEnum.MINIMUM_DATE_INCLUSIVE_CHECKBOX);
    	}
    	
    	public CheckBox maximumDateInclusive() {
    		return new CheckBox(FormsAddEnum.MAXIMUM_DATE_INCLUSIVE_CHECKBOX);
    	}
    	
    	public CheckBox looped() {
    		return new CheckBox(FormsAddEnum.LOOPED_CHECKBOX);
    	}
    	
    	public CheckBox displayOnOneScreen() {
    		return new CheckBox(FormsAddEnum.DISPLAY_ON_ONE_SCREEN_CHECKBOX);
    	}
    	
    }

    public class FormsAddDropDowns {
    	
    	public DropDown trigger() {
    		return new DropDown(FormsAddEnum.TRIGGER_DROPDOWN);
    	}
    	
    	public DropDown HOS() {
    		return new DropDown(FormsAddEnum.HOS_DROPDOWN);
    	}
    	
    	public DropDown vehicleTags() {
    		return new DropDown(FormsAddEnum.VEHICLE_TAGS_DROPDOWN);
    	}
    	
    	public DropDown kind() {
    		return new DropDown(FormsAddEnum.KIND_DROPDOWN);
    	}
    	
    }
    
    public class FormsAddLinks {
    	
    	public TextLink text() {
    		return new TextLink(FormsAddEnum.TEXT_LINK);
    	}
    	
    	public TextLink numeric() {
    		return new TextLink(FormsAddEnum.NUMERIC_LINK);
    	}
    	
    	public TextLink date() {
    		return new TextLink(FormsAddEnum.DATE_LINK);
    	}
    	
    	public TextLink chooseOne() {
    		return new TextLink(FormsAddEnum.CHOOSE_ONE_LINK);
    	}
    	
    	public TextLink selectMultiple() {
    		return new TextLink(FormsAddEnum.SELECT_MULTIPLE_LINK);
    	}
    	
    	public TextLink group() {
    		return new TextLink(FormsAddEnum.GROUP_LINK);
    	}
    	
    	public TextLink addOption() {    	
    		return new TextLink(FormsAddEnum.ADD_OPTION_LINK);
    	}
    	
    	public TextLink bulkEdit() {    
    		return new TextLink(FormsAddEnum.BULK_EDIT_LINK);
		}
    	
    	public TextTableLink previewArea() {
    		return new TextTableLink(FormsAddEnum.PREVIEW_AREA);
    	}
    	
    	public TextTableLink controlFlowArrow() {
    		return new TextTableLink(FormsAddEnum.CONTROL_FLOW_ARROW);
    	}
    	
    }

    public class FormsAddTextFields {

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
    	
    	public TextField captionText() {
    		return new TextField(FormsAddEnum.CAPTION_FIELD);
    	}
    	
    	public TextField value() {
    		return new TextField(FormsAddEnum.DEFAULT_VALUE);
    	}
    	
    	public TextField hint() {
    		return new TextField(FormsAddEnum.HINT_FIELD);
    	}
    	
    	public TextField minimumTextLength() {
    		return new TextField(FormsAddEnum.MINIMUM_TEXT_FIELD);
    	}
    	
    	public TextField maximumTextLength() {
    		return new TextField(FormsAddEnum.MAXIMUM_TEXT_FIELD);
    	}
    	
    	public TextField minimumNumericRange() {
    		return new TextField(FormsAddEnum.MINIMUM_NUMERIC_FIELD);
    	}
    	
    	public TextField maximumNumericRange() {
    		return new TextField(FormsAddEnum.MAXIMUM_NUMERIC_FIELD);
    	}
    	
    	public TextField invalidText() {
    		return new TextField(FormsAddEnum.INVALID_TEXT_FIELD);
    	}
    	
    	public TextFieldTable optionEnglish() {
    		return new TextFieldTable(FormsAddEnum.OPTION_FIELD);
    	}
    	
    	public TextFieldTable optionUnderlyingValue() {
    		return new TextFieldTable(FormsAddEnum.OPTION_UNDERLYING_VALUE_FIELD);
    	}
    	public TextField relevance() {
    		return new TextField(FormsAddEnum.RELEVANCE_FIELD);
    	}
    	
    	public TextField constraint() {
    		return new TextField(FormsAddEnum.CONSTRAINT_FIELD);
    	}

    	public TextField instanceDestination() {
    		return new TextField(FormsAddEnum.INSTANCE_DESTINATION_FIELD);
    	}
    	
    	public TextField minimumDateRange() {
    		return new TextField(FormsAddEnum.MINIMUM_DATE_TEXTFIELD);
    	}
    	
    	public TextField maximumDateRange() {
    		return new TextField(FormsAddEnum.MAXIMUM_DATE_TEXTFIELD);
    	}

    }
    
    public class FormsAddTexts  {
    	
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
    	
    	public Text version() {
    		return new Text(FormsAddEnum.VERSION_TEXT);
    	}
    	
    	public Text nameError() {
    		return new Text(FormsAddEnum.NAME_TEXT_ERROR);
    	}
    	
    	public Text controlError() {
    		return new Text(FormsAddEnum.CONTROL_TEXT_ERROR);
    	}
    	
    	public Text dataNameError() {
    		return new Text(FormsAddEnum.DATA_NAME_ERROR);
    	}
    	
    }

    public FormsAddButtons _button() {
        return new FormsAddButtons();
    }
    
    public FormsAddCheckBoxes _checkBox() {
    	return new FormsAddCheckBoxes();
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
        return FormsAddEnum.DEFAULT_URL;
    }


    protected boolean checkIsOnPage() {
        return _textField().name().isPresent() && _dropDown().trigger().isPresent();
    }
    
}
