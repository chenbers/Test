package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.CheckBox;
import com.inthinc.pro.automation.elements.DropDown;
import com.inthinc.pro.automation.elements.Text;
import com.inthinc.pro.automation.elements.TextButton;
import com.inthinc.pro.automation.elements.TextField;
import com.inthinc.pro.automation.elements.TextLink;
import com.inthinc.pro.automation.elements.TextTableLink;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.selenium.pageEnums.AssetsTrailersEnum;

public class PageAssetsTrailers extends Masthead {
    
    public PageAssetsTrailers() {}
    
    public class AssetsTrailersButtons {
        
        public TextButton new_() {
            return new TextButton(AssetsTrailersEnum.NEW_BUTTON);
        }
        
        public TextButton edit() {
            return new TextButton(AssetsTrailersEnum.EDIT_BUTTON);
        }
        
        public TextButton save() {
            return new TextButton(AssetsTrailersEnum.SAVE_BUTTON);
        }
        
        public TextButton cancel() {
            return new TextButton(AssetsTrailersEnum.CANCEL_BUTTON);
        }
        
    }
    
    public class AssetsTrailersDropDowns {
        
        public DropDown recordsPerPage() {
            return new DropDown(AssetsTrailersEnum.ENTRIES_DROPDOWN);
        }
                
        public DropDown state() {
            return new DropDown(AssetsTrailersEnum.STATE_TRAILER_INFORMATION_DROPDOWN);
        }
        
        public DropDown status() {
            return new DropDown(AssetsTrailersEnum.STATUS_DROPDOWN);
        }
        
        public DropDown assignedDevice() {
            return new DropDown(AssetsTrailersEnum.ASSIGNED_DEVICE_DROPDOWN);
        }
        
    }
    
    public class AssetsTrailersLinks {
        
        public TextLink showHideColumns() {
            return new TextLink(AssetsTrailersEnum.SHOW_HIDE_COLUMNS_LINK);
        }
        
        public TextLink restoreOriginal() {
            return new TextLink(AssetsTrailersEnum.RESTORE_ORIGINAL_LINK);
        }
        
        public TextLink sortByTrailerID() {
            return new TextLink(AssetsTrailersEnum.SORT_BY_TRAILERID_LINK);
        }
        
        public TextLink sortByTeam() {
            return new TextLink(AssetsTrailersEnum.SORT_BY_TEAM_LINK);
        }
        
        public TextLink sortByDevice() {
            return new TextLink(AssetsTrailersEnum.SORT_BY_DEVICE_LINK);
        }
        
        public TextLink sortByVehicle() {
            return new TextLink(AssetsTrailersEnum.SORT_BY_VEHICLE_LINK);
        }
        
        public TextLink sortByDriver() {
            return new TextLink(AssetsTrailersEnum.SORT_BY_DRIVER_LINK);
        }
        
        public TextLink sortByStatus() {
            return new TextLink(AssetsTrailersEnum.SORT_BY_STATUS);
        }
        
        public TextLink sortByVin() {
            return new TextLink(AssetsTrailersEnum.SORT_BY_VIN);
        }
        
        public TextLink sortByLicenseNumber() {
            return new TextLink(AssetsTrailersEnum.SORT_BY_LICENSE_NUMBER);
        }
        
        public TextLink sortByState() {
            return new TextLink(AssetsTrailersEnum.SORT_BY_STATE);
        }
        
        public TextLink sortByYear() {
            return new TextLink(AssetsTrailersEnum.SORT_BY_YEAR);
        }
        
        public TextLink sortByMake() {
            return new TextLink(AssetsTrailersEnum.SORT_BY_MAKE);
        }
        
        public TextLink sortByModel() {
            return new TextLink(AssetsTrailersEnum.SORT_BY_MODEL);
        }
        
        public TextLink sortByColor() {
            return new TextLink(AssetsTrailersEnum.SORT_BY_COLOR);
        }
        
        public TextLink sortByWeight() {
            return new TextLink(AssetsTrailersEnum.SORT_BY_WEIGHT);
        }
        
        public TextLink sortByOdometer() {
            return new TextLink(AssetsTrailersEnum.SORT_BY_ODOMETER);
        }
        
        public TextTableLink trailerIdEntry() {
            return new TextTableLink(AssetsTrailersEnum.TRAILER_ID_ENTRY);
        }
        
        public TextTableLink teamEntry() {
            return new TextTableLink(AssetsTrailersEnum.TEAM_ENTRY);
        }
        
        public TextTableLink deviceEntry() {
            return new TextTableLink(AssetsTrailersEnum.DEVICE_ENTRY);
        }
        
        public TextTableLink vehicleEntry() {
            return new TextTableLink(AssetsTrailersEnum.VEHICLE_ENTRY);
        }
        
        public TextTableLink driverEntry() {
            return new TextTableLink(AssetsTrailersEnum.DRIVER_ENTRY);
        }
        
        public TextTableLink statusEntry() {
            return new TextTableLink(AssetsTrailersEnum.STATUS_ENTRY);
        }
        
        public TextTableLink vinEntry() {
            return new TextTableLink(AssetsTrailersEnum.VIN_ENTRY);
        }
        
        public TextTableLink licenseEntry() {
            return new TextTableLink(AssetsTrailersEnum.LICENSE_ENTRY);
        }
        
        public TextTableLink stateEntry() {
            return new TextTableLink(AssetsTrailersEnum.STATE_ENTRY);
        }
        
        public TextTableLink yearEntry() {
            return new TextTableLink(AssetsTrailersEnum.YEAR_ENTRY);
        }
        
        public TextTableLink makeEntry() {
            return new TextTableLink(AssetsTrailersEnum.MAKE_ENTRY);
        }
        
        public TextTableLink modelEntry() {
            return new TextTableLink(AssetsTrailersEnum.MODEL_ENTRY);
        }
        
        public TextTableLink colorEntry() {
            return new TextTableLink(AssetsTrailersEnum.COLOR_ENTRY);
        }
        
        public TextTableLink weightEntry() {
            return new TextTableLink(AssetsTrailersEnum.WEIGHT_ENTRY);
        }
        
        public TextTableLink odometerEntry() {
            return new TextTableLink(AssetsTrailersEnum.ODOMETER_ENTRY);
        }
        
        public TextLink previous() {
            return new TextLink(AssetsTrailersEnum.PREVIOUS);
        }
        
        public TextLink previousDisabled() {
            return new TextLink(AssetsTrailersEnum.PREVIOUS_DISABLED);
        }
        
        public TextLink next() {
            return new TextLink(AssetsTrailersEnum.NEXT);
        }
        
        public TextLink nextDisabled() {
            return new TextLink(AssetsTrailersEnum.NEXT_DISABLED);
        }
        
    }
    
    public class AssetsTrailersTextFields {
        
        public TextField search() {
            return new TextField(AssetsTrailersEnum.SEARCH_TEXTFIELD);
        }
        
        public TextField vin() {
            return new TextField(AssetsTrailersEnum.VIN_TRAILER_INFORMATION_TEXTFIELD);
        }
        
        public TextField make() {
            return new TextField(AssetsTrailersEnum.MAKE_TRAILER_INFORMATION_TEXTFIELD);
        }
        
        public TextField year() {
            return new TextField(AssetsTrailersEnum.YEAR_TRAILER_INFORMATION_TEXTFIELD);
        }
        
        public TextField color() {
            return new TextField(AssetsTrailersEnum.COLOR_TRAILER_INFORMATION_TEXTFIELD);
        }
        
        public TextField weight() {
            return new TextField(AssetsTrailersEnum.WEIGHT_TRAILER_INFORMATION_TEXTFIELD);
        }
        
        public TextField licenseNumber() {
            return new TextField(AssetsTrailersEnum.LICENSE_TRAILER_INFORMATION_TEXTFIELD);
        }
        
        public TextField trailerID() {
            return new TextField(AssetsTrailersEnum.TRAILER_ID_TEXTFIELD);
        }
    }
    
    public class AssetsTrailersTexts {
        
        public Text title() {
            return new Text(AssetsTrailersEnum.TITLE);
        }
        
        public Text recordsPerPageLabel() {
            return new Text(AssetsTrailersEnum.ENTRIES_LABEL);
        }
        
        public Text searchLabel() {
            return new Text(AssetsTrailersEnum.SEARCH_LABEL);
        }
        
        //SHOW/HIDE COLUMNS LABELS
        public Text trailerIDCheckboxLabel() {
            return new Text(AssetsTrailersEnum.TRAILER_ID_CHECKBOX_LABEL);
        }
                
        public Text teamCheckboxLabel() {
            return new Text(AssetsTrailersEnum.TEAM_CHECKBOX_LABEL);
        }
        
        public Text deviceCheckboxLabel() {
            return new Text(AssetsTrailersEnum.DEVICE_CHECKBOX_LABEL);
        }
        
        public Text vehicleCheckboxLabel() {
            return new Text(AssetsTrailersEnum.VEHICLE_CHECKBOX_LABEL);
        }
        
        public Text driverCheckboxLabel() {
            return new Text(AssetsTrailersEnum.DRIVER_CHECKBOX_LABEL);
        }
        
        public Text statusCheckboxLabel() {
            return new Text(AssetsTrailersEnum.STATUS_CHECKBOX_LABEL);
        }
                
        public Text vinCheckboxLabel() {
            return new Text(AssetsTrailersEnum.VIN_CHECKBOX_LABEL);
        }
        
        public Text licenseNumberCheckboxLabel() {
            return new Text(AssetsTrailersEnum.LICENSE_CHECKBOX_LABEL);
        }
                
        public Text stateCheckboxLabel() {
            return new Text(AssetsTrailersEnum.STATE_CHECKBOX_LABEL);
        }
        
        public Text yearCheckboxLabel() {
            return new Text(AssetsTrailersEnum.YEAR_CHECKBOX_LABEL);
        }
                
        public Text makeCheckboxLabel() {
            return new Text(AssetsTrailersEnum.MAKE_CHECKBOX_LABEL);
        }
        
        public Text modelCheckboxLabel() {
            return new Text(AssetsTrailersEnum.MODEL_CHECKBOX_LABEL);
        }
                
        public Text colorCheckboxLabel() {
            return new Text(AssetsTrailersEnum.COLOR_CHECKBOX_LABEL);
        }
        
        public Text weightCheckboxLabel() {
            return new Text(AssetsTrailersEnum.WEIGHT_CHECKBOX_LABEL);
        }
                
        public Text odometerCheckboxLabel() {
            return new Text(AssetsTrailersEnum.ODOMETER_CHECKBOX_LABEL);
        }
        //
        
        public Text selectARowLabel() {
            return new Text(AssetsTrailersEnum.SELECT_A_ROW_LABEL);
        }
        
        public Text trailerInformationLabel() {
            return new Text(AssetsTrailersEnum.TRAILER_INFORMATION_HEADER);
        }
        
        public Text vinLabel() {
            return new Text(AssetsTrailersEnum.VIN_TRAILER_INFORMATION_LABEL);
        }
        
        public Text makeLabel() {
            return new Text(AssetsTrailersEnum.MAKE_TRAILER_INFORMATION_LABEL);
        }
        
        public Text yearLabel() {
            return new Text(AssetsTrailersEnum.YEAR_TRAILER_INFORMATION_LABEL);
        }
        
        public Text colorLabel() {
            return new Text(AssetsTrailersEnum.COLOR_TRAILER_INFORMATION_LABEL);
        }
        
        public Text weightLabel() {
            return new Text(AssetsTrailersEnum.WEIGHT_TRAILER_INFORMATION_LABEL);
        }
        
        public Text licenseNumberLabel() {
            return new Text(AssetsTrailersEnum.LICENSE_TRAILER_INFORMATION_LABEL);
        }
        
        public Text stateLabel() {
            return new Text(AssetsTrailersEnum.STATE_TRAILER_INFORMATION_LABEL);
        }
        
        public Text trailerProfileLabel() {
            return new Text(AssetsTrailersEnum.TRAILER_PROFILE_HEADER);
        }
        
        public Text trailerIDLabel() {
            return new Text(AssetsTrailersEnum.TRAILER_ID_LABEL);
        }
        
        public Text statusLabel() {
            return new Text(AssetsTrailersEnum.STATUS_LABEL);
        }
        
        public Text trailerAssignmentLabel() {
            return new Text(AssetsTrailersEnum.TRAILER_ASSIGNMENT_HEADER);
        }
        
        public Text teamLabel() {
            return new Text(AssetsTrailersEnum.TEAM_TRAILER_ASSIGNMENT_LABEL);
        }
        
        public Text deviceAssignmentLabel() {
            return new Text(AssetsTrailersEnum.DEVICE_ASSIGNMENT_HEADER);
        }
        
        public Text assignedDeviceLabel() {
            return new Text(AssetsTrailersEnum.ASSIGNED_DEVICE_LABEL);
        }
        
        public Text vehicleAssignmentLabel() {
            return new Text(AssetsTrailersEnum.VEHICLE_ASSIGNMENT_HEADER);
        }
        
        public Text assignedVehicleLabel() {
            return new Text(AssetsTrailersEnum.ASSIGNED_VEHICLE_LABEL);
        }
        
        public Text driverAssignmentLabel() {
            return new Text(AssetsTrailersEnum.DRIVER_ASSIGNMENT_HEADER);
        }
        
        public Text assignedDriverLabel() {
            return new Text(AssetsTrailersEnum.ASSIGNED_DRIVER_LABEL);
        }
        
        public Text vin() {
            return new Text(AssetsTrailersEnum.VIN_TRAILER_INFORMATION_TEXT);
        }
        
        public Text make() {
            return new Text(AssetsTrailersEnum.MAKE_TRAILER_INFORMATION_TEXT);
        }
        
        public Text year() {
            return new Text(AssetsTrailersEnum.YEAR_TRAILER_INFORMATION_TEXT);
        }
        
        public Text color() {
            return new Text(AssetsTrailersEnum.COLOR_TRAILER_INFORMATION_TEXT);
        }
        
        public Text weight() {
            return new Text(AssetsTrailersEnum.WEIGHT_TRAILER_INFORMATION_TEXT);
        }
        
        public Text licenseNumber() {
            return new Text(AssetsTrailersEnum.LICENSE_TRAILER_INFORMATION_TEXT);
        }
        
        public Text state() {
            return new Text(AssetsTrailersEnum.STATE_TRAILER_INFORMATION_TEXT);
        }
        
        public Text trailerID() {
            return new Text(AssetsTrailersEnum.TRAILER_ID_TEXT);
        }
        
        public Text status() {
            return new Text(AssetsTrailersEnum.STATUS_TEXT);
        }
        
        public Text team() {
            return new Text(AssetsTrailersEnum.TEAM_TRAILER_ASSIGNMENT_TEXT);
        }
        
        public Text assignedDevice() {
            return new Text(AssetsTrailersEnum.DEVICE_TEXT);
        }
        
        public Text assignedVehicle() {
            return new Text(AssetsTrailersEnum.VEHICLE_TEXT);
        }
        
        public Text assignedDriver() {
            return new Text(AssetsTrailersEnum.DRIVER_TEXT);
        }
        
        public Text noMatchingRecords() {
            return new Text(AssetsTrailersEnum.NO_MATCHING_RECORDS_ERROR);
        }
        
        public Text entries() {
            return new Text(AssetsTrailersEnum.ENTRIES_TEXT);
        }

    }
    
    public class AssetsTrailersCheckboxes {
        
        public CheckBox trailerID() {
            return new CheckBox(AssetsTrailersEnum.TRAILER_ID_CHECKBOX);
        }
                
        public CheckBox team() {
            return new CheckBox(AssetsTrailersEnum.TEAM_CHECKBOX);
        }
        
        public CheckBox device() {
            return new CheckBox(AssetsTrailersEnum.DEVICE_CHECKBOX);
        }
        
        public CheckBox vehicle() {
            return new CheckBox(AssetsTrailersEnum.VEHICLE_CHECKBOX);
        }
        
        public CheckBox driver() {
            return new CheckBox(AssetsTrailersEnum.DRIVER_CHECKBOX);
        }
        
        public CheckBox status() {
            return new CheckBox(AssetsTrailersEnum.STATUS_CHECKBOX);
        }
                
        public CheckBox vin() {
            return new CheckBox(AssetsTrailersEnum.VIN_CHECKBOX);
        }
        
        public CheckBox licenseNumber() {
            return new CheckBox(AssetsTrailersEnum.LICENSE_CHECKBOX);
        }
                
        public CheckBox state() {
            return new CheckBox(AssetsTrailersEnum.STATE_CHECKBOX);
        }
        
        public CheckBox year() {
            return new CheckBox(AssetsTrailersEnum.YEAR_CHECKBOX);
        }
                
        public CheckBox make() {
            return new CheckBox(AssetsTrailersEnum.MAKE_CHECKBOX);
        }
        
        public CheckBox model() {
            return new CheckBox(AssetsTrailersEnum.MODEL_CHECKBOX);
        }
                
        public CheckBox color() {
            return new CheckBox(AssetsTrailersEnum.COLOR_CHECKBOX);
        }
        
        public CheckBox weight() {
            return new CheckBox(AssetsTrailersEnum.WEIGHT_CHECKBOX);
        }
                
        public CheckBox odometer() {
            return new CheckBox(AssetsTrailersEnum.ODOMETER_CHECKBOX);
        }
        
    }
    
    public AssetsTrailersButtons _button() {
        return new AssetsTrailersButtons();
    }
    
    public AssetsTrailersDropDowns _dropDown() {
        return new AssetsTrailersDropDowns();
    }
    
    public AssetsTrailersLinks _link() {
        return new AssetsTrailersLinks();
    }
    
    public class AssetsTrailersPopUps {}
    
    public AssetsTrailersTexts _text() {
        return new AssetsTrailersTexts();
    }
    
    public AssetsTrailersTextFields _textField() {
        return new AssetsTrailersTextFields();
    }
    
    public AssetsTrailersCheckboxes _checkBox(){
        return new AssetsTrailersCheckboxes();
    }
    
    public AssetsTrailersPager _page() {
        return new AssetsTrailersPager();
    }
    
    public class AssetsTrailersPager {
        public Paging pageIndex() {
            return new Paging();
        }
    }
    
    @Override
    public SeleniumEnums setUrl() {
        return AssetsTrailersEnum.DEFAULT_URL;
    }
    
    @Override
    protected boolean checkIsOnPage() {
        return _link().showHideColumns().isPresent();
    }
}