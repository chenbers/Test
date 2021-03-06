 package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.Button;
import com.inthinc.pro.automation.elements.CheckBoxTable;
import com.inthinc.pro.automation.elements.DropDown;
import com.inthinc.pro.automation.elements.DropDownDateRangePicker;
import com.inthinc.pro.automation.elements.Text;
import com.inthinc.pro.automation.elements.TextField;
import com.inthinc.pro.automation.elements.TextLink;
import com.inthinc.pro.automation.elements.TextTableLink;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.selenium.pageEnums.FormsSubmissionsEnum;

public class PageFormsSubmissions extends FormsTables {

    public PageFormsSubmissions() {
//        checkMe.add(FormsSubmissionsEnum.DATE_SORT);
//        checkMe.add(FormsSubmissionsEnum.GROUP_SORT);
//        checkMe.add(FormsSubmissionsEnum.DRIVER_SORT);
//        checkMe.add(FormsSubmissionsEnum.VEHICLE_SORT);
//        checkMe.add(FormsSubmissionsEnum.FORM_SORT);
//        checkMe.add(FormsSubmissionsEnum.EDITED_SORT);
//        checkMe.add(FormsSubmissionsEnum.STATUS_SORT);
//        checkMe.add(FormsSubmissionsEnum.DATE_START_FIELD);
//        checkMe.add(FormsSubmissionsEnum.DATE_END_FIELD);
//        checkMe.add(FormsSubmissionsEnum.GROUP_FIELD);
//        checkMe.add(FormsSubmissionsEnum.DRIVER_FIELD);
//        checkMe.add(FormsSubmissionsEnum.VEHICLE_FIELD);
//        checkMe.add(FormsSubmissionsEnum.FORM_FIELD);
//        checkMe.add(FormsSubmissionsEnum.STATUS_DROPDOWN);
//        checkMe.add(FormsSubmissionsEnum.EDITED_DROPDOWN);
//        checkMe.add(FormsSubmissionsEnum.DATE_ENTRY);
//        checkMe.add(FormsSubmissionsEnum.GROUP_ENTRY);
//        checkMe.add(FormsSubmissionsEnum.DRIVER_ENTRY);
//        checkMe.add(FormsSubmissionsEnum.VEHICLE_ENTRY);
//        checkMe.add(FormsSubmissionsEnum.FORM_ENTRY);
//        checkMe.add(FormsSubmissionsEnum.ENTRIES_TEXT);
//        checkMe.add(FormsSubmissionsEnum.PREVIOUS);
//        checkMe.add(FormsSubmissionsEnum.NEXT);
        
    }

    public class FormsSubmissionsButtons extends FormsBarButtons {

    	public Button refresh() {
    		return new Button(FormsSubmissionsEnum.REFRESH);
    	}
    	
    	public Button save() {
    		return new Button(FormsSubmissionsEnum.SAVE_BUTTON);
    	}
    	
    	public Button cancel() {
    		return new Button(FormsSubmissionsEnum.CANCEL_BUTTON);
    	}
        
    }

    public class FormsSubmissionsDropDowns {
    	
    	public DropDown form() {
    		return new DropDown(FormsSubmissionsEnum.FORM_DROPDOWN);
    	}
    	
    	public DropDown date() {
    		return new DropDownDateRangePicker(FormsSubmissionsEnum.DATE_DROPDOWN);
    	}
    	
    	public DropDown recordsPerPage() {
    		return new DropDown(FormsSubmissionsEnum.RECORDS_DROPDOWN);
    	}
    	
    	public DropDown edited() {
    		return new DropDown(FormsSubmissionsEnum.EDITED_DROPDOWN);
    	}
    	
    	public DropDown approved() {
    		return new DropDown(FormsSubmissionsEnum.APPROVED_DROPDOWN);
    	}
    	//INLINE EDIT ITEMS
    	public DropDown dateEntry() {
    		return new DropDownDateRangePicker(FormsSubmissionsEnum.DATE_DROPDOWN_ENTRY);
    	}
    	
    	public DropDown chooseoneEntry() {
    		return new DropDown(FormsSubmissionsEnum.CHOOSEONE_DROPDOWN_ENTRY);
    	}
        
    }
    
    public class FormsSubmissionsLinks extends FormsBarLinks {
    	
    	public TextLink sortByDateTime() {
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
    	
    	public TextLink sortByApproved() {
    		return new TextLink(FormsSubmissionsEnum.APPROVED_SORT);
    	}
    	
    	public TextTableLink groupEntry() {
    		return new TextTableLink(FormsSubmissionsEnum.GROUP_LINK_ENTRY);
    	}
    	
    	public TextTableLink driverEntry() {
    		return new TextTableLink(FormsSubmissionsEnum.DRIVER_LINK_ENTRY);
    	}
    	
    	public TextTableLink vehicleEntry() {
    		return new TextTableLink(FormsSubmissionsEnum.VEHICLE_LINK_ENTRY);
    	}
    	
    	public TextLink previous() {
    		return new TextLink(FormsSubmissionsEnum.PREVIOUS);
    	}
    	
    	public TextLink next() {
    		return new TextLink(FormsSubmissionsEnum.NEXT);
    	}
    	
    }

    public class FormsSubmissionsTextFields {
    	
    	public TextField group() {
    		return new TextField(FormsSubmissionsEnum.GROUP_FIELD);
    	}
    	
    	public TextField driver() {
    		return new TextField(FormsSubmissionsEnum.DRIVER_FIELD);
    	}
    	
    	public TextField vehicle() {
    		return new TextField(FormsSubmissionsEnum.VEHICLE_FIELD);
    	}
    	//INLINE EDIT:
    	public TextField textEntry() {
    		return new TextField(FormsSubmissionsEnum.TEXT_TEXTFIELD_ENTRY);
    	}
    	
    	public TextField numericEntry() {
    		return new TextField(FormsSubmissionsEnum.NUMERIC_TEXTFIELD_ENTRY);
    	}
    	
    	public TextField decimalEntry() {
    		return new TextField(FormsSubmissionsEnum.DECIMAL_TEXTFIELD_ENTRY);
    	} 
    	
    }
    
    public class FormsSubmissionsTexts {
    	
    	public TextTableLink dateTimeEntry() {
    		return new TextTableLink(FormsSubmissionsEnum.DATE_TIME_TEXT_ENTRY);
    	}
    	
    	public Text noRecordsFoundError() {
    		return new Text(FormsSubmissionsEnum.NO_RECORDS_ERROR);
    	}
    	
    	public Text entries() {
    		return new Text(FormsSubmissionsEnum.ENTRIES_TEXT);
    	}
//NO LONGER ON THIS PAGE    	
//    	public Text title() {
//    		return new Text(FormsSubmissionsEnum.TITLE);
//    	}
    	
    	public TextTableLink formEntry() {
    		return new TextTableLink(FormsSubmissionsEnum.FORM_TEXT_ENTRY);
    	}
    	
    	public TextTableLink editedEntry() {
    		return new TextTableLink(FormsSubmissionsEnum.EDITED_TEXT_ENTRY);
    	}
    	
    	//INLINE EDIT TEXTS:
    	public TextTableLink textEntry() {
    		return new TextTableLink(FormsSubmissionsEnum.TEXT_ENTRY);
    	}
    	
    	public TextTableLink numericEntry() {
    		return new TextTableLink(FormsSubmissionsEnum.NUMERIC_ENTRY);
    	}
    	
    	public TextTableLink decimalEntry() {
    		return new TextTableLink(FormsSubmissionsEnum.DECIMAL_ENTRY);
    	}
    	
    	public TextTableLink dateEntry() {
    		return new TextTableLink(FormsSubmissionsEnum.DATE_ENTRY);
    	}
    	
    	public TextTableLink chooseoneEntry() {
    		return new TextTableLink(FormsSubmissionsEnum.CHOOSEONE_ENTRY);
    	}
    	
    	public TextTableLink choosemanyEntry() {
    		return new TextTableLink(FormsSubmissionsEnum.CHOOSEMANY_ENTRY);
    	}
    		
    	public Text invalidTextError() {
    		return new Text(FormsSubmissionsEnum.INVALID_TEXT_ERROR);
    	}
    	
    	public Text invalidNumericError() {
    		return new Text(FormsSubmissionsEnum.INVALID_NUMERIC_ERROR);
    	}
    	
    	public Text invalidDecimalError() {
    		return new Text(FormsSubmissionsEnum.INVALID_DECIMAL_ERROR);
    	}
    	
    	public Text invalidDateError() {
    		return new Text(FormsSubmissionsEnum.INVALID_DATE_ERROR);
    	}
    	
    }
    
    public class FormsSubmissionsCheckboxes {
    	
    	public CheckBoxTable approvedEntry() {
    		return new CheckBoxTable(FormsSubmissionsEnum.APPROVED_CHECKBOX_ENTRY);
    	}
    	    	
    	public CheckBoxTable choosemanyEntry() {
    		return new CheckBoxTable(FormsSubmissionsEnum.CHOOSEMANY_CHECKBOX_ENTRY);
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
    
    public FormsSubmissionsCheckboxes _checkBox(){
    	return new FormsSubmissionsCheckboxes();
    }


    public class FormsSubmissionsPopUps extends MastheadPopUps {}
    
    
    public SeleniumEnums setUrl() {
        return FormsSubmissionsEnum.DEFAULT_URL;
    }

    
    protected boolean checkIsOnPage() {
        return _dropDown().form().isPresent() && _button().refresh().isPresent();
    }
    
}

