package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.CheckBox;
import com.inthinc.pro.automation.elements.CheckBoxTable;
import com.inthinc.pro.automation.elements.DropDown;
import com.inthinc.pro.automation.elements.Text;
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

    public class FormsAdminButtons {}

    public class FormsAdminDropDowns {
    	
    	public DropDown recordsPerPageWorking() {
    		return new DropDown(FormsAdminEnum.RECORDS_WORKING_DROPDOWN);
    	}
    	
    	public DropDown recordsPerPagePublished() {
    		return new DropDown(FormsAdminEnum.RECORDS_PUBLISHED_DROPDOWN);
    	}
    }
    
    public class FormsAdminLinks {
    	
    	public TextLink addFormTop() {
    		return new TextLink(FormsAdminEnum.CREATE_FORM_TOP_LINK);
    	}
    	
    	public TextLink addFormBottom() {
    		return new TextLink(FormsAdminEnum.CREATE_FORM_BOTTOM_LINK);
    	}
    	
    	public TextLink working() {
    		return new TextLink(FormsAdminEnum.WORKING_TAB);
    	}
    	
    	public TextLink published() {
    		return new TextLink(FormsAdminEnum.PUBLISHED_TAB);
    	}
    	
        public TextLink sortBySelectWorking() {
        	return new TextLink(FormsAdminEnum.SELECT_WORKING_LINK);
        }
        
        public TextLink sortBySelectPublished() {
        	return new TextLink(FormsAdminEnum.SELECT_PUBLISHED_LINK);
        }
        
        public TextLink sortByNameWorking() {
        	return new TextLink(FormsAdminEnum.NAME_WORKING_LINK);
        }

        public TextLink sortByNamePublished() {
        	return new TextLink(FormsAdminEnum.NAME_PUBLISHED_LINK);
        }
        
        public TextLink sortByBaseFormIdWorking() {
        	return new TextLink(FormsAdminEnum.BASE_FORM_ID_WORKING_LINK);
        }
        
        public TextLink sortByBaseFormIdPublished() {
        	return new TextLink(FormsAdminEnum.BASE_FORM_ID_PUBLISHED_LINK);
        }

        public TextLink sortByVersionWorking() {
        	return new TextLink(FormsAdminEnum.VERSION_WORKING_LINK);
        }
        
        public TextLink sortByVersionPublished() {
        	return new TextLink(FormsAdminEnum.VERSION_PUBLISHED_LINK);
        }        

        public TextLink sortByDescriptionWorking() {
        	return new TextLink(FormsAdminEnum.DESCRIPTION_WORKING_LINK);
        }
        
        public TextLink sortByStatusWorking() {
        	return new TextLink(FormsAdminEnum.STATUS_WORKING_LINK);
        }

        public TextLink sortByDescriptionPublished() {
        	return new TextLink(FormsAdminEnum.DESCRIPTION_PUBLISHED_LINK);
        }

        public TextLink sortByTriggerWorking() {
        	return new TextLink(FormsAdminEnum.TRIGGER_WORKING_LINK);
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
        
        public TextLink previousWorking() {
        	return new TextLink(FormsAdminEnum.PREVIOUS_WORKING);
        }
        
        public TextLink pageNumberWorking() {
        	return new TextLink(FormsAdminEnum.PAGE_NUMBER_WORKING);
        }
        
        public TextLink nextWorking() {
        	return new TextLink(FormsAdminEnum.NEXT_WORKING);
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

        public TextField searchWorking() {
            return new TextField(FormsAdminEnum.SEARCH_WORKING_TEXTFIELD);
        }

        public TextField searchPublished() {
            return new TextField(FormsAdminEnum.SEARCH_PUBLISHED_TEXTFIELD);
        }
        
    }
    
    public class FormsAdminTexts {
    	
    	public Text title() {
    		return new Text(FormsAddEnum.TITLE);
    	}
    	
    	public TextTable entryNameWorking() {
    		return new TextTable(FormsAdminEnum.NAME_WORKING_ENTRY);
    	}
    	
    	public TextTable entryNamePublished() {
    		return new TextTable(FormsAdminEnum.NAME_PUBLISHED_ENTRY);
    	}
    	
    	public TextTable entryBaseFormIdWorking() {
    		return new TextTable(FormsAdminEnum.BASE_FORM_ID_WORKING_ENTRY);
    	}
    	
    	public TextTable entryBaseFormIdPublished() {
    		return new TextTable(FormsAdminEnum.BASE_FORM_ID_PUBLISHED_ENTRY);
    	}
    	
    	public TextTable entryVersionWorking() {
    		return new TextTable(FormsAdminEnum.VERSION_WORKING_ENTRY);
    	}
    	
    	public TextTable entryVersionPublished() {
    		return new TextTable(FormsAdminEnum.VERSION_PUBLISHED_ENTRY);
    	}
    	
    	public TextTable entryDescriptionWorking() {
    		return new TextTable(FormsAdminEnum.DESCRIPTION_WORKING_ENTRY);
    	}
    	
    	public TextTable entryDescriptionPublished() {
    		return new TextTable(FormsAdminEnum.DESCRIPTION_PUBLISHED_ENTRY);
    	}
    	
    	public TextTable entryStatusWorking() {
    		return new TextTable(FormsAdminEnum.STATUS_WORKING_ENTRY);
    	}

    	public TextTable entryTriggerWorking() {
    		return new TextTable(FormsAdminEnum.TRIGGER_WORKING_ENTRY);
    	}
    	
    	public TextTable entryTriggerPublished() {
    		return new TextTable(FormsAdminEnum.TRIGGER_PUBLISHED_ENTRY);
    	}
    	
    	public Text entriesWorking() {
    		return new Text(FormsAdminEnum.ENTRIES_WORKING_TEXT);
    	}
    	
    	public Text entriesPublished() {
    		return new Text(FormsAdminEnum.ENTRIES_PUBLISHED_TEXT);
    	}
    	
    	public Text noRecordsWorkingError() {
    	return new Text(FormsAdminEnum.NO_RECORDS_FOUND_WORKING_ERROR);
    	}
    	
    	public Text noRecordsPublishedError() {
    	return new Text(FormsAdminEnum.NO_RECORDS_FOUND_PUBLISHED_ERROR);
    	}
    }
    
    public class FormsTablesCheckBoxes {
        public CheckBox workingCheckAll() {
            return new CheckBox(FormsAdminEnum.SELECT_ALL_WORKING_CHECKBOX);
        }

        public CheckBoxTable workingEntryCheck() {
            return new CheckBoxTable(FormsAdminEnum.WORKING_CHECKBOX_ENTRY);
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
