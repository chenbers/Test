 package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.DropDown;
import com.inthinc.pro.automation.elements.Text;
import com.inthinc.pro.automation.elements.TextButton;
import com.inthinc.pro.automation.elements.TextField;
import com.inthinc.pro.automation.elements.TextLink;
import com.inthinc.pro.automation.elements.TextTable;
import com.inthinc.pro.automation.elements.TextTableLink;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.selenium.pageEnums.FormsSubmissionsEnum;

public class PageFormsSubmissions extends FormsTables {

    public PageFormsSubmissions() {
        checkMe.add(FormsSubmissionsEnum.SAVE);    
        checkMe.add(FormsSubmissionsEnum.APPROVE);
        checkMe.add(FormsSubmissionsEnum.DATE_SORT);
        checkMe.add(FormsSubmissionsEnum.GROUP_SORT);
        checkMe.add(FormsSubmissionsEnum.DRIVER_SORT);
        checkMe.add(FormsSubmissionsEnum.VEHICLE_SORT);
        checkMe.add(FormsSubmissionsEnum.FORM_SORT);
        checkMe.add(FormsSubmissionsEnum.EDITED_SORT);
        checkMe.add(FormsSubmissionsEnum.STATUS_SORT);
        checkMe.add(FormsSubmissionsEnum.DATE_START_FIELD);
        checkMe.add(FormsSubmissionsEnum.DATE_END_FIELD);
        checkMe.add(FormsSubmissionsEnum.GROUP_FIELD);
        checkMe.add(FormsSubmissionsEnum.DRIVER_FIELD);
        checkMe.add(FormsSubmissionsEnum.VEHICLE_FIELD);
        checkMe.add(FormsSubmissionsEnum.FORM_FIELD);
        checkMe.add(FormsSubmissionsEnum.STATUS_DROPDOWN);
        checkMe.add(FormsSubmissionsEnum.EDITED_DROPDOWN);
        checkMe.add(FormsSubmissionsEnum.DATE_ENTRY);
        checkMe.add(FormsSubmissionsEnum.GROUP_ENTRY);
        checkMe.add(FormsSubmissionsEnum.DRIVER_ENTRY);
        checkMe.add(FormsSubmissionsEnum.VEHICLE_ENTRY);
        checkMe.add(FormsSubmissionsEnum.FORM_ENTRY);
        checkMe.add(FormsSubmissionsEnum.ENTRIES_TEXT);
        checkMe.add(FormsSubmissionsEnum.PREVIOUS);
        checkMe.add(FormsSubmissionsEnum.NEXT);
        
    }

    public class FormsSubmissionsButtons extends NavigationBarButtons {

        public TextButton save() {
            return new TextButton(FormsSubmissionsEnum.SAVE);
        }
        
        public TextButton approve() {
        	return new TextButton(FormsSubmissionsEnum.APPROVE);
        }

    }

    public class FormsSubmissionsDropDowns extends NavigationBarDropDowns {
    	
    	public DropDown status() {
    		return new DropDown(FormsSubmissionsEnum.STATUS_DROPDOWN);
    	}
    	
    	public DropDown edited() {
    		return new DropDown(FormsSubmissionsEnum.EDITED_DROPDOWN);
    	}
    	
    }
    
    public class FormsSubmissionsLinks extends NavigationBarLinks {
    	
    	public TextLink sortByDate() {
    		return new TextLink(FormsSubmissionsEnum.DATE_SORT);
    	}
    	
    	public TextLink sortByGroup() {
    		return new TextLink(FormsSubmissionsEnum.GROUP_SORT);
    	}
    	
    	public TextLink sortByDriver() {
    		return new TextLink(FormsSubmissionsEnum.DRIVER_SORT);
    	}
    	
    	public TextLink sortByVehicle() {
    		return new TextLink(FormsSubmissionsEnum.VEHICLE_SORT);
    	}
    	
    	public TextLink sortByForm() {
    		return new TextLink(FormsSubmissionsEnum.FORM_SORT);
    	}
    	
    	public TextLink sortByEdited() {
    		return new TextLink(FormsSubmissionsEnum.EDITED_SORT);
    	}
    	
    	public TextLink sortByStatus() {
    		return new TextLink(FormsSubmissionsEnum.STATUS_SORT);
    	}
    	
    	public TextTableLink groupEntry() {
    		return new TextTableLink(FormsSubmissionsEnum.GROUP_ENTRY);
    	}
    	
    	public TextTableLink driverEntry() {
    		return new TextTableLink(FormsSubmissionsEnum.DRIVER_ENTRY);
    	}
    	
    	public TextTableLink vehicleEntry() {
    		return new TextTableLink(FormsSubmissionsEnum.VEHICLE_ENTRY);
    	}
    	
    	public TextTableLink formEntry() {
    		return new TextTableLink(FormsSubmissionsEnum.FORM_ENTRY);
    	}
    	
    	public TextTableLink editedEntry() {
    		return new TextTableLink(FormsSubmissionsEnum.EDITED_ENTRY);
    	}
    	
    	public TextTableLink statusEntry() {
    		return new TextTableLink(FormsSubmissionsEnum.STATUS_ENTRY);
    	}
    	
    	public TextLink previous() {
    		return new TextLink(FormsSubmissionsEnum.PREVIOUS);
    	}
    	
    	public TextLink next() {
    		return new TextLink(FormsSubmissionsEnum.NEXT);
    	}
    	
    }

    public class FormsSubmissionsTextFields extends NavigationBarTextFields {

    	public TextField group() {
    		return new TextField(FormsSubmissionsEnum.GROUP_FIELD);
    	}
    	
    	public TextField driver() {
    		return new TextField(FormsSubmissionsEnum.DRIVER_FIELD);
    	}
    	
    	public TextField vehicle() {
    		return new TextField(FormsSubmissionsEnum.VEHICLE_FIELD);
    	}
    	
    	public TextField formSearch() {
    		return new TextField(FormsSubmissionsEnum.FORM_FIELD);
    	}
    	
    }
    
    public class FormsSubmissionsTexts extends NavigationBarTexts {
    	
    	public TextTable dateTime() {
    		return new TextTable(FormsSubmissionsEnum.DATE_ENTRY);
    	}
    	
    	public TextTable entries() {
    		return new TextTable(FormsSubmissionsEnum.ENTRIES_TEXT);
    	}
    	
    	public Text title() {
    		return new Text(FormsSubmissionsEnum.TITLE);
    	}
    }

    public FormsSubmissionsButtons _button() {
        return new FormsSubmissionsButtons();
    }

    public FormsSubmissionsDropDowns _dropDown() {
        return new FormsSubmissionsDropDowns();
    }

    public FormsSubmissionsLinks _link() {
        return new FormsSubmissionsLinks();
    }

    public FormsSubmissionsTexts _text() {
        return new FormsSubmissionsTexts();
    }

    public FormsSubmissionsTextFields _textField() {
        return new FormsSubmissionsTextFields();
    }
    
    public FormsSubmissionsPopUps _popUp(){
        return new FormsSubmissionsPopUps();
    }
    
    public FormsTablesCheckBoxes _checkBox(){
    	return new FormsTablesCheckBoxes();
    }


    public class FormsSubmissionsPopUps extends NavigationBarPopUps {}
    
    @Override
    public SeleniumEnums setUrl() {
        return FormsSubmissionsEnum.DEFAULT_URL;
    }

    @Override
    protected boolean checkIsOnPage() {
        return _dropDown().edited().isPresent() && _dropDown().status().isPresent();
    }
    
}
