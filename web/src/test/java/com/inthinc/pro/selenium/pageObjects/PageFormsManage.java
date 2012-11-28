package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.CheckBox;
import com.inthinc.pro.automation.elements.CheckBoxTable;
import com.inthinc.pro.automation.elements.DropDown;
import com.inthinc.pro.automation.elements.Text;
import com.inthinc.pro.automation.elements.TextButton;
import com.inthinc.pro.automation.elements.TextField;
import com.inthinc.pro.automation.elements.TextLink;
import com.inthinc.pro.automation.elements.TextTable;
import com.inthinc.pro.automation.elements.TextTableLink;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.selenium.pageEnums.FormsAddEnum;
import com.inthinc.pro.selenium.pageEnums.FormsManageEnum;

public class PageFormsManage extends FormsBar {

    public PageFormsManage() {
//        checkMe.add(FormsManageEnum.DELETE);
//        checkMe.add(FormsManageEnum.APPROVED);
//        checkMe.add(FormsManageEnum.SEARCH_TEXTFIELD);
//        checkMe.add(FormsManageEnum.SEARCH_BUTTON);
//        checkMe.add(FormsManageEnum.EDIT_COLUMNS);
//        checkMe.add(FormsManageEnum.PUBLISH);
//        checkMe.add(FormsManageEnum.FORMS_HEADER);
//        checkMe.add(FormsManageEnum.DESCRIPTION_HEADER);
//        checkMe.add(FormsManageEnum.APPROVED_HEADER);
//        checkMe.add(FormsManageEnum.STATUS_HEADER);
//        checkMe.add(FormsManageEnum.OWNER_HEADER);
//        checkMe.add(FormsManageEnum.EDIT_HEADER);
//        checkMe.add(FormsManageEnum.CHECKBOX);
//        checkMe.add(FormsManageEnum.FORMS_ENTRY);
//        checkMe.add(FormsManageEnum.DESCRIPTION_ENTRY);
//        checkMe.add(FormsManageEnum.APPROVED_ENTRY);
//        checkMe.add(FormsManageEnum.STATUS_ENTRY);
//        checkMe.add(FormsManageEnum.OWNER_ENTRY);
//        checkMe.add(FormsManageEnum.EDIT_ENTRY);        
        
    }

    public class FormsAdminButtons {
    	
    	public TextButton newForm() {
    		return new TextButton(FormsManageEnum.NEW_FORM_BUTTON);
    	}
    }

    public class FormsAdminDropDowns {
    	
    	public DropDown recordsPerPage() {
    		return new DropDown(FormsManageEnum.RECORDS_DROPDOWN);
    	}
    }
    
    public class FormsAdminLinks extends FormsBarLinks {
    	
//        public TextLink sortBySelectManage() {
//        	return new TextLink(FormsManageEnum.SELECT_MANAGE_LINK);
//        }
//        
//        public TextLink sortBySelectPublished() {
//        	return new TextLink(FormsManageEnum.SELECT_PUBLISHED_LINK);
//        }
        
        public TextLink sortByNameManage() {
        	return new TextLink(FormsManageEnum.NAME_LINK);
        }
        
        public TextLink sortByBaseFormIdManage() {
        	return new TextLink(FormsManageEnum.BASE_FORM_ID_LINK);
        }

        public TextLink sortByVersionManage() {
        	return new TextLink(FormsManageEnum.VERSION_LINK);
        }      

        public TextLink sortByDescriptionManage() {
        	return new TextLink(FormsManageEnum.DESCRIPTION_LINK);
        }
        
        public TextLink sortByStatusManage() {
        	return new TextLink(FormsManageEnum.STATUS_LINK);
        }

        public TextLink sortByTriggerManage() {
        	return new TextLink(FormsManageEnum.TRIGGER_LINK);
        }
        
        public TextLink sortByPublish() {
        	return new TextLink(FormsManageEnum.PUBLISH_LINK);
        }
        
        public TextLink sortByEdit() {
        	return new TextLink(FormsManageEnum.EDIT_LINK);
        }
        
        public TextTableLink entryEdit() {
        	return new TextTableLink(FormsManageEnum.EDIT_ENTRY_LINK);
        }
        
        public TextLink previousManage() {
        	return new TextLink(FormsManageEnum.PREVIOUS_MANAGE);
        }
        
        public TextLink pageNumberManage() {
        	return new TextLink(FormsManageEnum.PAGE_NUMBER_MANAGE);
        }
        
        public TextLink nextManage() {
        	return new TextLink(FormsManageEnum.NEXT_MANAGE);
        }
        
    }

    public class FormsAdminTextFields {

        public TextField searchManage() {
            return new TextField(FormsManageEnum.SEARCH_TEXTFIELD);
        }
        
    }
    
    public class FormsAdminTexts {
    	
    	public Text title() {
    		return new Text(FormsAddEnum.TITLE);
    	}
    	
    	public TextTable entryNameManage() {
    		return new TextTable(FormsManageEnum.NAME_MANAGE_ENTRY);
    	}
    	
    	public TextTable entryBaseFormIdManage() {
    		return new TextTable(FormsManageEnum.BASE_FORM_ID_MANAGE_ENTRY);
    	}
    	
    	public TextTable entryVersionManage() {
    		return new TextTable(FormsManageEnum.VERSION_MANAGE_ENTRY);
    	}
    	
    	public TextTable entryDescriptionManage() {
    		return new TextTable(FormsManageEnum.DESCRIPTION_MANAGE_ENTRY);
    	}
    	
    	public TextTable entryStatusManage() {
    		return new TextTable(FormsManageEnum.STATUS_MANAGE_ENTRY);
    	}

    	public TextTable entryTriggerManage() {
    		return new TextTable(FormsManageEnum.TRIGGER_MANAGE_ENTRY);
    	}
    	
    	public Text entriesManage() {
    		return new Text(FormsManageEnum.ENTRIES_MANAGE_TEXT);
    	}
    	
    	public Text noRecordsManageError() {
    		return new Text(FormsManageEnum.NO_RECORDS_FOUND_MANAGE_ERROR);
    	}

    }
    
    public class FormsTablesCheckBoxes {
        public CheckBox workingCheckAll() {
            return new CheckBox(FormsManageEnum.SELECT_ALL_CHECKBOX);
        }

        public CheckBoxTable workingEntryCheck() {
            return new CheckBoxTable(FormsManageEnum.CHECKBOX_ENTRY);
        }

    }
    
    public class FormsAdminPopUps extends MastheadPopUps {}

    public FormsAdminButtons _button() {
        return new FormsAdminButtons();
    }
    
    public FormsTablesCheckBoxes _checkBox() {
    	return new FormsTablesCheckBoxes();
    }

    public FormsAdminDropDowns _dropDown() {
        return new FormsAdminDropDowns();
    }

    public FormsAdminLinks _link() {
        return new FormsAdminLinks();
    }

    public FormsAdminTexts _text() {
        return new FormsAdminTexts();
    }

    public FormsAdminTextFields _textField() {
        return new FormsAdminTextFields();
    }
    
    public FormsAdminPopUps _popUp(){
        return new FormsAdminPopUps();
    }
   

    public SeleniumEnums setUrl() {
        return FormsManageEnum.DEFAULT_URL;
    }


    protected boolean checkIsOnPage() {
        return _link().sortByStatusManage().isPresent() && _button().newForm().isPresent();
    }
    
}
