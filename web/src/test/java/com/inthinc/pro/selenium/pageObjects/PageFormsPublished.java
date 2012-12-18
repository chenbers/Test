package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.DropDown;
import com.inthinc.pro.automation.elements.Text;
import com.inthinc.pro.automation.elements.TextField;
import com.inthinc.pro.automation.elements.TextLink;
import com.inthinc.pro.automation.elements.TextTable;
import com.inthinc.pro.automation.elements.TextTableLink;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.selenium.pageEnums.FormsAddEnum;
import com.inthinc.pro.selenium.pageEnums.FormsPublishedEnum;

public class PageFormsPublished extends FormsBar {

    public PageFormsPublished() {
//        checkMe.add(FormsPublishedEnum.DELETE);
//        checkMe.add(FormsPublishedEnum.APPROVED);
//        checkMe.add(FormsPublishedEnum.SEARCH_TEXTFIELD);
//        checkMe.add(FormsPublishedEnum.SEARCH_BUTTON);
//        checkMe.add(FormsPublishedEnum.EDIT_COLUMNS);
//        checkMe.add(FormsPublishedEnum.PUBLISH);
//        checkMe.add(FormsPublishedEnum.FORMS_HEADER);
//        checkMe.add(FormsPublishedEnum.DESCRIPTION_HEADER);
//        checkMe.add(FormsPublishedEnum.APPROVED_HEADER);
//        checkMe.add(FormsPublishedEnum.STATUS_HEADER);
//        checkMe.add(FormsPublishedEnum.OWNER_HEADER);
//        checkMe.add(FormsPublishedEnum.EDIT_HEADER);
//        checkMe.add(FormsPublishedEnum.CHECKBOX);
//        checkMe.add(FormsPublishedEnum.FORMS_ENTRY);
//        checkMe.add(FormsPublishedEnum.DESCRIPTION_ENTRY);
//        checkMe.add(FormsPublishedEnum.APPROVED_ENTRY);
//        checkMe.add(FormsPublishedEnum.STATUS_ENTRY);
//        checkMe.add(FormsPublishedEnum.OWNER_ENTRY);
//        checkMe.add(FormsPublishedEnum.EDIT_ENTRY);        
        
    }

    public class FormsPublishedButtons {}

    public class FormsPublishedDropDowns {

    	public DropDown recordsPerPage() {
    		return new DropDown(FormsPublishedEnum.RECORDS_DROPDOWN);
    	}
    }
    
    public class FormsPublishedLinks extends FormsBarLinks{
    	    
//        public TextLink sortBySelect() {
//        	return new TextLink(FormsPublishedEnum.SELECT_PUBLISHED_LINK);
//        }

        public TextLink sortByName() {
        	return new TextLink(FormsPublishedEnum.NAME_LINK);
        }
        
        public TextLink sortByBaseFormId() {
        	return new TextLink(FormsPublishedEnum.BASE_FORM_ID_LINK);
        }
        
        public TextLink sortByVersion() {
        	return new TextLink(FormsPublishedEnum.VERSION_LINK);
        }        
        
        public TextLink sortByDescription() {
        	return new TextLink(FormsPublishedEnum.DESCRIPTION_LINK);
        }
        
        public TextLink sortByTrigger() {
        	return new TextLink(FormsPublishedEnum.TRIGGER_LINK);
        }
        
        public TextLink previous() {
        	return new TextLink(FormsPublishedEnum.PREVIOUS);
        }
        
        public TextTableLink pageNumber() {
        	return new TextTableLink(FormsPublishedEnum.PAGE_NUMBER);
        }
        
        public TextLink next() {
        	return new TextLink(FormsPublishedEnum.NEXT);
        }
        
    }

    public class FormsPublishedTextFields {

        public TextField search() {
            return new TextField(FormsPublishedEnum.SEARCH_TEXTFIELD);
        }
        
    }
    
    public class FormsPublishedTexts {
    	
    	public Text title() {
    		return new Text(FormsAddEnum.TITLE);
    	}

    	public TextTable entryName() {
    		return new TextTable(FormsPublishedEnum.NAME_ENTRY);
    	}

    	public TextTable entryBaseFormId() {
    		return new TextTable(FormsPublishedEnum.BASE_FORM_ID_ENTRY);
    	}

    	public TextTable entryVersion() {
    		return new TextTable(FormsPublishedEnum.VERSION_ENTRY);
    	}

    	public TextTable entryDescription() {
    		return new TextTable(FormsPublishedEnum.DESCRIPTION_ENTRY);
    	}
    	
    	public TextTable entryTrigger() {
    		return new TextTable(FormsPublishedEnum.TRIGGER_ENTRY);
    	}
    	
    	public Text entries() {
    		return new Text(FormsPublishedEnum.ENTRIES_TEXT);
    	}
    	
    	public Text noRecordsError() {
    		return new Text(FormsPublishedEnum.NO_RECORDS_FOUND_ERROR);
    	}
    }
    
    public class FormsTablesCheckBoxes {
// THESE ARE NOT CURRENTLY ON THE PAGE ANYMORE        
//        public CheckBox checkAll() {
//            return new CheckBox(FormsPublishedEnum.SELECT_ALL_CHECKBOX);
//        }
//
//        public CheckBoxTable entryCheck() {
//            return new CheckBoxTable(FormsPublishedEnum.CHECKBOX_ENTRY);
//        }
    }
    
    public class FormsPublishedPopUps extends MastheadPopUps {}

    public FormsPublishedButtons _button() {
        return new FormsPublishedButtons();
    }
    
    public FormsTablesCheckBoxes _checkBox() {
    	return new FormsTablesCheckBoxes();
    }

    public FormsPublishedDropDowns _dropDown() {
        return new FormsPublishedDropDowns();
    }

    public FormsPublishedLinks _link() {
        return new FormsPublishedLinks();
    }

    public FormsPublishedTexts _text() {
        return new FormsPublishedTexts();
    }

    public FormsPublishedTextFields _textField() {
        return new FormsPublishedTextFields();
    }
    
    public FormsPublishedPopUps _popUp(){
        return new FormsPublishedPopUps();
    }
   

    public SeleniumEnums setUrl() {
        return FormsPublishedEnum.DEFAULT_URL;
    }


    protected boolean checkIsOnPage() {
        return _textField().search().isPresent() && _dropDown().recordsPerPage().isPresent();
    }
    
}
