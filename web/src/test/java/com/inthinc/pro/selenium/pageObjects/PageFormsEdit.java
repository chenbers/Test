 package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.CheckBox;
import com.inthinc.pro.automation.elements.CheckBoxTable;
import com.inthinc.pro.automation.elements.DropDown;
import com.inthinc.pro.automation.elements.Text;
import com.inthinc.pro.automation.elements.TextButton;
import com.inthinc.pro.automation.elements.TextField;
import com.inthinc.pro.automation.elements.TextFieldTable;
import com.inthinc.pro.automation.elements.TextLink;
import com.inthinc.pro.automation.elements.TextTableLink;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.selenium.pageEnums.FormsEditEnum;

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

    public class FormsEditButtons {

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
    
    public class FormsEditCheckBoxes {
    	
    	public CheckBoxTable groups() {
    		return new CheckBoxTable(FormsEditEnum.GROUPS_CHECKBOX);
    	}
    	
    	public CheckBox readOnly() {
    		return new CheckBox(FormsEditEnum.READ_ONLY_CHECKBOX);
    	}
    	
    	public CheckBox required() {
    		return new CheckBox(FormsEditEnum.REQUIRED_CHECKBOX);
    	}
    	
    	public CheckBox lengthEnable() {
    		return new CheckBox(FormsEditEnum.LENGTH_CHECKBOX);
    	}
    	
    	public CheckBox rangeEnableNumeric() {
    		return new CheckBox(FormsEditEnum.RANGE_NUMERIC_CHECKBOX);
    	}
    	
    	public CheckBox minimumTextInclusive() {
    		return new CheckBox(FormsEditEnum.MINIMUM_TEXT_INCLUSIVE_CHECKBOX);
    	}
    	
    	public CheckBox maximumTextInclusive() {
    		return new CheckBox(FormsEditEnum.MAXIMUM_TEXT_INCLUSIVE_CHECKBOX);
    	}
    	
    	public CheckBox minimumNumericInclusive() {
    		return new CheckBox(FormsEditEnum.MINIMUM_NUMERIC_INCLUSIVE_CHECKBOX);
    	}
    	
    	public CheckBox maximumNumericInclusive() {
    		return new CheckBox(FormsEditEnum.MAXIMUM_NUMERIC_INCLUSIVE_CHECKBOX);
    	}
    	
    	public CheckBox rangeEnableDate() {
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

    public class FormsEditDropDowns {
    	
    	public DropDown trigger() {
    		return new DropDown(FormsEditEnum.TRIGGER_DROPDOWN);
    	}
    	
    	public DropDown status() {
    		return new DropDown(FormsEditEnum.STATUS_DROPDOWN);
    	}
    	
    	public DropDown HOS() {
    		return new DropDown(FormsEditEnum.HOS_DROPDOWN);
    	}
    	
    	public DropDown vehicleTags() {
    		return new DropDown(FormsEditEnum.VEHICLE_TAGS_DROPDOWN);
    	}
    	
    	public DropDown kind() {
    		return new DropDown(FormsEditEnum.KIND_DROPDOWN);
    	}
    	
    }
    
    public class FormsEditLinks {
    	
    	public TextTableLink groupsArrow() {
    		return new TextTableLink(FormsEditEnum.GROUPS_ARROW);
    	}
    	
    	public TextLink text() {
    		return new TextLink(FormsEditEnum.TEXT_LINK);
    	}
    	
    	public TextLink numeric() {
    		return new TextLink(FormsEditEnum.NUMERIC_LINK);
    	}
    	
    	public TextLink date() {
    		return new TextLink(FormsEditEnum.DATE_LINK);
    	}
    	
    	public TextLink chooseOne() {
    		return new TextLink(FormsEditEnum.CHOOSE_ONE_LINK);
    	}
    	
    	public TextLink selectMultiple() {
    		return new TextLink(FormsEditEnum.SELECT_MULTIPLE_LINK);
    	}
    	
    	public TextLink group() {
    		return new TextLink(FormsEditEnum.GROUP_LINK);
    	}
    	
    	public TextLink addOption() {    	
    		return new TextLink(FormsEditEnum.ADD_OPTION_LINK);
    	}
    	
    	public TextLink bulkEdit() {    
    		return new TextLink(FormsEditEnum.BULK_EDIT_LINK);
		}
    	
    	public TextTableLink previewArea() {
    		return new TextTableLink(FormsEditEnum.PREVIEW_AREA);
    	}
    	
    	public TextTableLink controlFlowArrow() {
    		return new TextTableLink(FormsEditEnum.CONTROL_FLOW_ARROW);
    	}
    	
    }

    public class FormsEditTextFields {

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
    	
    	public TextField captionText() {
    		return new TextField(FormsEditEnum.CAPTION_FIELD);
    	}
    	
    	public TextField value() {
    		return new TextField(FormsEditEnum.DEFAULT_VALUE);
    	}
    	
    	public TextField hint() {
    		return new TextField(FormsEditEnum.HINT_FIELD);
    	}
    	
    	public TextField minimumTextLength() {
    		return new TextField(FormsEditEnum.MINIMUM_TEXT_FIELD);
    	}
    	
    	public TextField maximumTextLength() {
    		return new TextField(FormsEditEnum.MAXIMUM_TEXT_FIELD);
    	}
    	
    	public TextField minimumNumericRange() {
    		return new TextField(FormsEditEnum.MINIMUM_NUMERIC_FIELD);
    	}
    	
    	public TextField maximumNumericRange() {
    		return new TextField(FormsEditEnum.MAXIMUM_NUMERIC_FIELD);
    	}
    	
    	public TextField invalidText() {
    		return new TextField(FormsEditEnum.INVALID_TEXT_FIELD);
    	}
    	
    	public TextFieldTable optionEnglish() {
    		return new TextFieldTable(FormsEditEnum.OPTION_FIELD);
    	}
    	
    	public TextFieldTable optionUnderlyingValue() {
    		return new TextFieldTable(FormsEditEnum.OPTION_UNDERLYING_VALUE_FIELD);
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
    	
    	public TextField minimumDateRange() {
    		return new TextField(FormsEditEnum.MINIMUM_DATE_TEXTFIELD);
    	}
    	
    	public TextField maximumDateRange() {
    		return new TextField(FormsEditEnum.MAXIMUM_DATE_TEXTFIELD);
    	}

    }
    
    public class FormsEditTexts  {
    	
    	public Text title() {
    		return new Text(FormsEditEnum.TITLE);
    	}
    	
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
    	
    	public Text statusLabel() {
    		return new Text(FormsEditEnum.STATUS_LABEL);
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
    	
    	public Text version() {
    		return new Text(FormsEditEnum.VERSION_TEXT);
    	}
    	
    	public Text nameError() {
    		return new Text(FormsEditEnum.NAME_TEXT_ERROR);
    	}
    	
    	public Text controlError() {
    		return new Text(FormsEditEnum.CONTROL_TEXT_ERROR);
    	}
    	
    	public Text groupError() {
    		return new Text(FormsEditEnum.GROUP_TEXT_ERROR);
    	}
    	
    	public Text dataNameError() {
    		return new Text(FormsEditEnum.DATA_NAME_ERROR);
    	}
    	
    }

    public FormsEditButtons _button() {
        return new FormsEditButtons();
    }
    
    public FormsEditCheckBoxes _checkBox() {
    	return new FormsEditCheckBoxes();
    }

    public FormsEditDropDowns _dropDown() {
        return new FormsEditDropDowns();
    }

    public FormsEditLinks _link() {
        return new FormsEditLinks();
    }

    public FormsEditTexts _text() {
        return new FormsEditTexts();
    }

    public FormsEditTextFields _textField() {
        return new FormsEditTextFields();
    }
    
    public FormsEditPopUps _popUp(){
        return new FormsEditPopUps();
    }


    public class FormsEditPopUps extends MastheadPopUps {
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