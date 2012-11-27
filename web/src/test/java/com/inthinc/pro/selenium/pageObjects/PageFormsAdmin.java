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
import com.inthinc.pro.selenium.pageEnums.FormsAdminEnum;

public class PageFormsAdmin extends FormsBar {

    public PageFormsAdmin() {
//        checkMe.add(FormsAdminEnum.DELETE);
//        checkMe.add(FormsAdminEnum.APPROVED);
//        checkMe.add(FormsAdminEnum.SEARCH_TEXTFIELD);
//        checkMe.add(FormsAdminEnum.SEARCH_BUTTON);
//        checkMe.add(FormsAdminEnum.EDIT_COLUMNS);
//        checkMe.add(FormsAdminEnum.PUBLISH);
//        checkMe.add(FormsAdminEnum.FORMS_HEADER);
//        checkMe.add(FormsAdminEnum.DESCRIPTION_HEADER);
//        checkMe.add(FormsAdminEnum.APPROVED_HEADER);
//        checkMe.add(FormsAdminEnum.STATUS_HEADER);
//        checkMe.add(FormsAdminEnum.OWNER_HEADER);
//        checkMe.add(FormsAdminEnum.EDIT_HEADER);
//        checkMe.add(FormsAdminEnum.CHECKBOX);
//        checkMe.add(FormsAdminEnum.FORMS_ENTRY);
//        checkMe.add(FormsAdminEnum.DESCRIPTION_ENTRY);
//        checkMe.add(FormsAdminEnum.APPROVED_ENTRY);
//        checkMe.add(FormsAdminEnum.STATUS_ENTRY);
//        checkMe.add(FormsAdminEnum.OWNER_ENTRY);
//        checkMe.add(FormsAdminEnum.EDIT_ENTRY);        
        
    }

    public class FormsAdminButtons {
    	
    	public TextButton newForm() {
    		return new TextButton(FormsAdminEnum.NEW_FORM_LINK);
    	}
    }

    public class FormsAdminDropDowns {
    	
    	public DropDown recordsPerPageManage() {
    		return new DropDown(FormsAdminEnum.RECORDS_MANAGE_DROPDOWN);
    	}
    	
    	public DropDown recordsPerPagePublished() {
    		return new DropDown(FormsAdminEnum.RECORDS_PUBLISHED_DROPDOWN);
    	}
    }
    
    public class FormsAdminLinks {
    	
    	public TextLink working() {
    		return new TextLink(FormsAdminEnum.MANAGE_TAB);
    	}
    	
    	public TextLink published() {
    		return new TextLink(FormsAdminEnum.PUBLISHED_TAB);
    	}
    	
//        public TextLink sortBySelectManage() {
//        	return new TextLink(FormsAdminEnum.SELECT_MANAGE_LINK);
//        }
//        
//        public TextLink sortBySelectPublished() {
//        	return new TextLink(FormsAdminEnum.SELECT_PUBLISHED_LINK);
//        }
        
        public TextLink sortByNameManage() {
        	return new TextLink(FormsAdminEnum.NAME_MANAGE_LINK);
        }

        public TextLink sortByNamePublished() {
        	return new TextLink(FormsAdminEnum.NAME_PUBLISHED_LINK);
        }
        
        public TextLink sortByBaseFormIdManage() {
        	return new TextLink(FormsAdminEnum.BASE_FORM_ID_MANAGE_LINK);
        }
        
        public TextLink sortByBaseFormIdPublished() {
        	return new TextLink(FormsAdminEnum.BASE_FORM_ID_PUBLISHED_LINK);
        }

        public TextLink sortByVersionManage() {
        	return new TextLink(FormsAdminEnum.VERSION_MANAGE_LINK);
        }
        
        public TextLink sortByVersionPublished() {
        	return new TextLink(FormsAdminEnum.VERSION_PUBLISHED_LINK);
        }        

        public TextLink sortByDescriptionManage() {
        	return new TextLink(FormsAdminEnum.DESCRIPTION_MANAGE_LINK);
        }
        
        public TextLink sortByStatusManage() {
        	return new TextLink(FormsAdminEnum.STATUS_MANAGE_LINK);
        }

        public TextLink sortByDescriptionPublished() {
        	return new TextLink(FormsAdminEnum.DESCRIPTION_PUBLISHED_LINK);
        }

        public TextLink sortByTriggerManage() {
        	return new TextLink(FormsAdminEnum.TRIGGER_MANAGE_LINK);
        }
        
        public TextLink sortByTriggerPublished() {
        	return new TextLink(FormsAdminEnum.TRIGGER_PUBLISHED_LINK);
        }
        
        public TextLink sortByPublish() {
        	return new TextLink(FormsAdminEnum.PUBLISH_LINK);
        }
        
        public TextLink sortByEdit() {
        	return new TextLink(FormsAdminEnum.EDIT_LINK);
        }
        
        public TextTableLink entryPublish() {
        	return new TextTableLink(FormsAdminEnum.PUBLISH_ENTRY_LINK);
        }
        
        public TextTableLink entryEdit() {
        	return new TextTableLink(FormsAdminEnum.EDIT_ENTRY_LINK);
        }
        
        public TextLink previousManage() {
        	return new TextLink(FormsAdminEnum.PREVIOUS_MANAGE);
        }
        
        public TextLink pageNumberManage() {
        	return new TextLink(FormsAdminEnum.PAGE_NUMBER_MANAGE);
        }
        
        public TextLink nextManage() {
        	return new TextLink(FormsAdminEnum.NEXT_MANAGE);
        }
        
        public TextLink previousPublished() {
        	return new TextLink(FormsAdminEnum.PREVIOUS_PUBLISHED);
        }
        
        public TextLink pageNumberPublished() {
        	return new TextLink(FormsAdminEnum.PAGE_NUMBER_PUBLISHED);
        }
        
        public TextLink nextPublished() {
        	return new TextLink(FormsAdminEnum.NEXT_PUBLISHED);
        }
        
    }

    public class FormsAdminTextFields {

        public TextField searchManage() {
            return new TextField(FormsAdminEnum.SEARCH_MANAGE_TEXTFIELD);
        }

        public TextField searchPublished() {
            return new TextField(FormsAdminEnum.SEARCH_PUBLISHED_TEXTFIELD);
        }
        
    }
    
    public class FormsAdminTexts {
    	
    	public Text title() {
    		return new Text(FormsAddEnum.TITLE);
    	}
    	
    	public TextTable entryNameManage() {
    		return new TextTable(FormsAdminEnum.NAME_MANAGE_ENTRY);
    	}
    	
    	public TextTable entryNamePublished() {
    		return new TextTable(FormsAdminEnum.NAME_PUBLISHED_ENTRY);
    	}
    	
    	public TextTable entryBaseFormIdManage() {
    		return new TextTable(FormsAdminEnum.BASE_FORM_ID_MANAGE_ENTRY);
    	}
    	
    	public TextTable entryBaseFormIdPublished() {
    		return new TextTable(FormsAdminEnum.BASE_FORM_ID_PUBLISHED_ENTRY);
    	}
    	
    	public TextTable entryVersionManage() {
    		return new TextTable(FormsAdminEnum.VERSION_MANAGE_ENTRY);
    	}
    	
    	public TextTable entryVersionPublished() {
    		return new TextTable(FormsAdminEnum.VERSION_PUBLISHED_ENTRY);
    	}
    	
    	public TextTable entryDescriptionManage() {
    		return new TextTable(FormsAdminEnum.DESCRIPTION_MANAGE_ENTRY);
    	}
    	
    	public TextTable entryDescriptionPublished() {
    		return new TextTable(FormsAdminEnum.DESCRIPTION_PUBLISHED_ENTRY);
    	}
    	
    	public TextTable entryStatusManage() {
    		return new TextTable(FormsAdminEnum.STATUS_MANAGE_ENTRY);
    	}

    	public TextTable entryTriggerManage() {
    		return new TextTable(FormsAdminEnum.TRIGGER_MANAGE_ENTRY);
    	}
    	
    	public TextTable entryTriggerPublished() {
    		return new TextTable(FormsAdminEnum.TRIGGER_PUBLISHED_ENTRY);
    	}
    	
    	public Text entriesManage() {
    		return new Text(FormsAdminEnum.ENTRIES_MANAGE_TEXT);
    	}
    	
    	public Text entriesPublished() {
    		return new Text(FormsAdminEnum.ENTRIES_PUBLISHED_TEXT);
    	}
    	
    	public Text noRecordsManageError() {
    	return new Text(FormsAdminEnum.NO_RECORDS_FOUND_MANAGE_ERROR);
    	}
    	
    	public Text noRecordsPublishedError() {
    	return new Text(FormsAdminEnum.NO_RECORDS_FOUND_PUBLISHED_ERROR);
    	}
    }
    
    public class FormsTablesCheckBoxes {
        public CheckBox workingCheckAll() {
            return new CheckBox(FormsAdminEnum.SELECT_ALL_MANAGE_CHECKBOX);
        }

        public CheckBoxTable workingEntryCheck() {
            return new CheckBoxTable(FormsAdminEnum.MANAGE_CHECKBOX_ENTRY);
        }
        
        public CheckBox publishedCheckAll() {
            return new CheckBox(FormsAdminEnum.SELECT_ALL_PUBLISHED_CHECKBOX);
        }

        public CheckBoxTable publishedEntryCheck() {
            return new CheckBoxTable(FormsAdminEnum.PUBLISHED_CHECKBOX_ENTRY);
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
        return FormsAdminEnum.DEFAULT_URL;
    }


    protected boolean checkIsOnPage() {
        return _link().sortByEdit().isPresent() && _link().sortByPublish().isPresent();
    }
    
}
