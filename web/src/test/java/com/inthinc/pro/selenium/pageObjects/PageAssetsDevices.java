package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.CheckBox;
import com.inthinc.pro.automation.elements.CheckBoxTable;
import com.inthinc.pro.automation.elements.DropDown;
import com.inthinc.pro.automation.elements.Text;
import com.inthinc.pro.automation.elements.TextButton;
import com.inthinc.pro.automation.elements.TextField;
import com.inthinc.pro.automation.elements.TextLink;
import com.inthinc.pro.automation.elements.TextTableLink;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.selenium.pageEnums.AssetsDevicesEnum;

public class PageAssetsDevices extends Masthead {
    
    public PageAssetsDevices() {}
    
    public class AssetsDevicesButtons extends MastheadButtons {
        
        public TextButton edit() {
            return new TextButton(AssetsDevicesEnum.EDIT_BUTTON);
        }
        
        public TextButton save() {
            return new TextButton(AssetsDevicesEnum.SAVE_BUTTON);
        }
        
        public TextButton cancel() {
            return new TextButton(AssetsDevicesEnum.CANCEL_BUTTON);
        }
        
        public TextButton delete() {
            return new TextButton(AssetsDevicesEnum.DELETE_BUTTON);
        }
        
    }
    
    public class AssetsDevicesDropDowns extends MastheadDropDowns {
        
        public DropDown recordsPerPage() {
            return new DropDown(AssetsDevicesEnum.ENTRIES_DROPDOWN);
        }
        
        public DropDown status() {
            return new DropDown(AssetsDevicesEnum.STATUS_DROPDOWN);
        }
        
        public DropDown assignedVehicle() {
            return new DropDown(AssetsDevicesEnum.ASSIGNED_VEHICLE_DROPDOWN);
        }
        
    }
    
    public class AssetsDevicesLinks extends MastheadLinks {
        
        public TextLink showHideColumns() {
            return new TextLink(AssetsDevicesEnum.SHOW_HIDE_COLUMNS_LINK);
        }
        
        public TextLink restoreOriginal() {
            return new TextLink(AssetsDevicesEnum.RESTORE_ORIGINAL_LINK);
        }
        
        public TextLink sortByDevice() {
            return new TextLink(AssetsDevicesEnum.SORT_BY_DEVICE_LINK);
        }
        
        public TextLink sortByVehicle() {
            return new TextLink(AssetsDevicesEnum.SORT_BY_VEHICLE_LINK);
        }
        
        public TextLink sortByProduct() {
            return new TextLink(AssetsDevicesEnum.SORT_BY_PRODUCT_LINK);
        }
        
        public TextLink sortByStatus() {
            return new TextLink(AssetsDevicesEnum.SORT_BY_STATUS_LINK);
        }
        
        public TextLink sortByIMEI() {
            return new TextLink(AssetsDevicesEnum.SORT_BY_IMEI_LINK);
        }
        
        public TextLink sortByAlternateIMEI() {
            return new TextLink(AssetsDevicesEnum.SORT_BY_ALTERNATE_IMEI_LINK);
        }
        
        public TextLink sortBySIMCard() {
            return new TextLink(AssetsDevicesEnum.SORT_BY_SIM_CARD_LINK);
        }
        
        public TextLink sortBySerialNumber() {
            return new TextLink(AssetsDevicesEnum.SORT_BY_SERIAL_NUMBER_LINK);
        }
        
        public TextLink sortByPhone() {
            return new TextLink(AssetsDevicesEnum.SORT_BY_PHONE_LINK);
        }
        
        public TextLink sortByMCMID() {
            return new TextLink(AssetsDevicesEnum.SORT_BY_MCM_ID_LINK);
        }
        
        public TextTableLink productEntry() {
            return new TextTableLink(AssetsDevicesEnum.PRODUCT_ENTRY);
        }
        
        public TextTableLink deviceEntry() {
            return new TextTableLink(AssetsDevicesEnum.DEVICE_ENTRY);
        }
        
        public TextTableLink vehicleEntry() {
            return new TextTableLink(AssetsDevicesEnum.VEHICLE_ENTRY);
        }
        
        public TextTableLink iMEIEntry() {
            return new TextTableLink(AssetsDevicesEnum.IMEI_ENTRY);
        }
        
        public TextTableLink statusEntry() {
            return new TextTableLink(AssetsDevicesEnum.STATUS_ENTRY);
        }
        
        public TextTableLink alternateIMEIEntry() {
            return new TextTableLink(AssetsDevicesEnum.ALTERNATE_IMEI_ENTRY);
        }
        
        public TextTableLink simCardEntry() {
            return new TextTableLink(AssetsDevicesEnum.SIM_CARD_ENTRY);
        }
        
        public TextTableLink serialNumberEntry() {
            return new TextTableLink(AssetsDevicesEnum.SERIAL_NUMBER_ENTRY);
        }
        
        public TextTableLink phoneEntry() {
            return new TextTableLink(AssetsDevicesEnum.PHONE_ENTRY);
        }
        
        public TextTableLink mCMIDEntry() {
            return new TextTableLink(AssetsDevicesEnum.MCM_ID_ENTRY);
        }
        
        public TextLink previous() {
            return new TextLink(AssetsDevicesEnum.PREVIOUS);
        }
        
        public TextLink previousDisabled() {
            return new TextLink(AssetsDevicesEnum.PREVIOUS_DISABLED);
        }
        
        public TextLink next() {
            return new TextLink(AssetsDevicesEnum.NEXT);
        }
        
        public TextLink nextDisabled() {
            return new TextLink(AssetsDevicesEnum.NEXT_DISABLED);
        }
        
    }
    
    public class AssetsDevicesTextFields extends MastheadTextFields {
        
        public TextField search() {
            return new TextField(AssetsDevicesEnum.SEARCH_TEXTFIELD);
        }
    }
    
    public class AssetsDevicesTexts extends MastheadTexts {
        
        public Text title() {
            return new Text(AssetsDevicesEnum.TITLE);
        }
        
        public Text recordsPerPageLabel() {
            return new Text(AssetsDevicesEnum.ENTRIES_LABEL);
        }
        
        public Text searchLabel() {
            return new Text(AssetsDevicesEnum.SEARCH_LABEL);
        }

        //SHOW/HIDE COLUMNS LABELS               
        public Text vehicleCheckboxLabel() {
            return new Text(AssetsDevicesEnum.VEHICLE_CHECKBOX_LABEL);
        }
        
        public Text productCheckboxLabel() {
            return new Text(AssetsDevicesEnum.PRODUCT_CHECKBOX_LABEL);
        }
        
        public Text iMEICheckboxLabel() {
            return new Text(AssetsDevicesEnum.IMEI_CHECKBOX_LABEL);
        }
        
        public Text alternateIMEICheckboxLabel() {
            return new Text(AssetsDevicesEnum.ALTERNATE_IMEI_CHECKBOX_LABEL);
        }
        
        public Text sIMCardCheckboxLabel() {
            return new Text(AssetsDevicesEnum.SIM_CARD_CHECKBOX_LABEL);
        }
                
        public Text serialNumberCheckboxLabel() {
            return new Text(AssetsDevicesEnum.SERIAL_NUMBER_CHECKBOX_LABEL);
        }
        
        public Text phoneCheckboxLabel() {
            return new Text(AssetsDevicesEnum.PHONE_CHECKBOX_LABEL);
        }
                
        public Text statusCheckboxLabel() {
            return new Text(AssetsDevicesEnum.STATUS_CHECKBOX_LABEL);
        }
        
        public Text mCMIDCheckboxLabel() {
            return new Text(AssetsDevicesEnum.MCM_ID_CHECKBOX_LABEL);
        }

        //DEVICE SECTION        
        public Text deviceLabel() {
            return new Text(AssetsDevicesEnum.DEVICE_LABEL);
        }
        
        public Text productLabel() {
            return new Text(AssetsDevicesEnum.PRODUCT_LABEL);
        }
        
        public Text statusLabel() {
            return new Text(AssetsDevicesEnum.STATUS_LABEL);
        }
        
        public Text mCMIDLabel() {
            return new Text(AssetsDevicesEnum.MCM_ID_LABEL);
        }
        
        public Text iMEILabel() {
            return new Text(AssetsDevicesEnum.IMEI_LABEL);
        }
        
        public Text simCardLabel() {
            return new Text(AssetsDevicesEnum.SIM_CARD_LABEL);
        }
        
        public Text phoneLabel() {
            return new Text(AssetsDevicesEnum.PHONE_LABEL);
        }
        
        public Text alternateIMEILabel() {
            return new Text(AssetsDevicesEnum.ALTERNATE_IMEI_LABEL);
        }
        
        public Text serialNumberLabel() {
            return new Text(AssetsDevicesEnum.SERIAL_NUMBER_LABEL);
        }
        
        public Text profileLabel() {
            return new Text(AssetsDevicesEnum.PROFILE_LABEL);
        }
        
        public Text activatedLabel() {
            return new Text(AssetsDevicesEnum.ACTIVATED_LABEL);
        }
        
        public Text assignmentLabel() {
            return new Text(AssetsDevicesEnum.ASSIGNMENT_HEADER);
        }
        
        public Text assignedVehicleLabel() {
            return new Text(AssetsDevicesEnum.ASSIGNED_VEHICLE_LABEL);
        }
        
        public Text device() {
            return new Text(AssetsDevicesEnum.DEVICE_TEXT);
        }
        
        public Text product() {
            return new Text(AssetsDevicesEnum.PRODUCT_TEXT);
        }
        
        public Text status() {
            return new Text(AssetsDevicesEnum.STATUS_TEXT);
        }
        
        public Text mCMID() {
            return new Text(AssetsDevicesEnum.MCM_ID_TEXT);
        }
        
        public Text iMEI() {
            return new Text(AssetsDevicesEnum.IMEI_TEXT);
        }
        
        public Text simCard() {
            return new Text(AssetsDevicesEnum.SIM_CARD_TEXT);
        }
        
        public Text phone() {
            return new Text(AssetsDevicesEnum.PHONE_TEXT);
        }
        
        public Text alternateIMEI() {
            return new Text(AssetsDevicesEnum.ALTERNATE_IMEI_TEXT);
        }
        
        public Text serialNumber() {
            return new Text(AssetsDevicesEnum.SERIAL_NUMBER_TEXT);
        }
        
        public Text activated() {
            return new Text(AssetsDevicesEnum.ACTIVATED_TEXT);
        }
        
        public Text assignedVehicle() {
            return new Text(AssetsDevicesEnum.VEHICLE_TEXT);
        }
        
        public Text noMatchingRecords() {
            return new Text(AssetsDevicesEnum.NO_MATCHING_RECORDS_ERROR);
        }
        
        public Text entries() {
            return new Text(AssetsDevicesEnum.ENTRIES_TEXT);
        }
        
    }
    
    public class AssetsDevicesCheckboxes {
        
        public CheckBox selectAll() {
            return new CheckBox(AssetsDevicesEnum.SELECT_ALL_CHECKBOX);
        }
        
        public CheckBoxTable deviceEntry() {
            return new CheckBoxTable(AssetsDevicesEnum.DEVICE_ENTRY_CHECKBOX);
        }
    
        public CheckBox vehicle() {
            return new CheckBox(AssetsDevicesEnum.VEHICLE_CHECKBOX);
        }
        
        public CheckBox product() {
            return new CheckBox(AssetsDevicesEnum.PRODUCT_CHECKBOX);
        }
        
        public CheckBox imei() {
            return new CheckBox(AssetsDevicesEnum.IMEI_CHECKBOX);
        }
        
        public CheckBox alternateImei() {
            return new CheckBox(AssetsDevicesEnum.ALTERNATE_IMEI_CHECKBOX);
        }
        
        public CheckBox simCard() {
            return new CheckBox(AssetsDevicesEnum.SIM_CARD_CHECKBOX);
        }
                
        public CheckBox serialNumber() {
            return new CheckBox(AssetsDevicesEnum.SERIAL_NUMBER_CHECKBOX);
        }
        
        public CheckBox phone() {
            return new CheckBox(AssetsDevicesEnum.PHONE_CHECKBOX);
        }
                
        public CheckBox status() {
            return new CheckBox(AssetsDevicesEnum.STATUS_CHECKBOX);
        }
        
        public CheckBox mcmId() {
            return new CheckBox(AssetsDevicesEnum.MCM_ID_CHECKBOX);
        }
        
    }
    
    public AssetsDevicesButtons _button() {
        return new AssetsDevicesButtons();
    }
    
    public AssetsDevicesDropDowns _dropDown() {
        return new AssetsDevicesDropDowns();
    }
    
    public AssetsDevicesLinks _link() {
        return new AssetsDevicesLinks();
    }
    
    public class AssetsDevicesPopUps {}
    
    public AssetsDevicesTexts _text() {
        return new AssetsDevicesTexts();
    }
    
    public AssetsDevicesTextFields _textField() {
        return new AssetsDevicesTextFields();
    }
    
    public AssetsDevicesCheckboxes _checkBox(){
        return new AssetsDevicesCheckboxes();
    }
    
    public AssetsDevicesPager _page() {
        return new AssetsDevicesPager();
    }
    
    public class AssetsDevicesPager {
        public Paging pageIndex() {
            return new Paging();
        }
    }
    
    @Override
    public SeleniumEnums setUrl() {
        return AssetsDevicesEnum.DEFAULT_URL;
    }
    
    @Override
    protected boolean checkIsOnPage() {
        return _text().device().isPresent() && _text().profileLabel().isPresent();
    }
}