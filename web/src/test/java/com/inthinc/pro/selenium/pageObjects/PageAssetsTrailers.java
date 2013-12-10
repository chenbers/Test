package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.CheckBox;
import com.inthinc.pro.automation.elements.DropDown;
import com.inthinc.pro.automation.elements.Text;
import com.inthinc.pro.automation.elements.TextButton;
import com.inthinc.pro.automation.elements.TextField;
import com.inthinc.pro.automation.elements.TextLabel;
import com.inthinc.pro.automation.elements.TextLink;
import com.inthinc.pro.automation.elements.TextTable;
import com.inthinc.pro.automation.elements.TextTableLink;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.selenium.pageEnums.AssetsTrailersEnum;
import com.inthinc.pro.selenium.pageEnums.ReportsTrailersEnum;

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
        
        public TextTableLink teamEntry() {
            return new TextTableLink(AssetsTrailersEnum.TEAM_ENTRY);
        }
        
        public TextLink previous() {
            return new TextLink(AssetsTrailersEnum.PREVIOUS);
        }
        
        public TextLink next() {
            return new TextLink(AssetsTrailersEnum.NEXT);
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
        
        public TextField license() {
            return new TextField(AssetsTrailersEnum.LICENSE_TRAILER_INFORMATION_TEXTFIELD);
        }
        
        public TextField status() {
            return new TextField(AssetsTrailersEnum.STATUS_TEXTFIELD);
        }
    }
    
    public class AssetsTrailersTexts {
        
        public Text title() {
            return new Text(AssetsTrailersEnum.TITLE);
        }
        
        public TextLabel recordsPerPageLabel() {
            return new TextLabel(AssetsTrailersEnum.ENTRIES_LABEL);
        }
        
        public TextLabel searchLabel() {
            return new TextLabel(AssetsTrailersEnum.SEARCH_LABEL);
        }
        
        //SHOW/HIDE COLUMNS LABELS
        public TextLabel trailerIDCheckboxLabel() {
            return new TextLabel(AssetsTrailersEnum.TRAILER_ID_CHECKBOX_LABEL);
        }
                
        public TextLabel teamCheckboxLabel() {
            return new TextLabel(AssetsTrailersEnum.TEAM_CHECKBOX_LABEL);
        }
        
        public TextLabel statusCheckboxLabel() {
            return new TextLabel(AssetsTrailersEnum.STATUS_CHECKBOX_LABEL);
        }
                
        public TextLabel vinCheckboxLabel() {
            return new TextLabel(AssetsTrailersEnum.VIN_CHECKBOX_LABEL);
        }
        
        public TextLabel licenseNumberCheckboxLabel() {
            return new TextLabel(AssetsTrailersEnum.LICENSE_CHECKBOX_LABEL);
        }
                
        public TextLabel stateCheckboxLabel() {
            return new TextLabel(AssetsTrailersEnum.STATE_CHECKBOX_LABEL);
        }
        
        public TextLabel yearCheckboxLabel() {
            return new TextLabel(AssetsTrailersEnum.YEAR_CHECKBOX_LABEL);
        }
                
        public TextLabel makeCheckboxLabel() {
            return new TextLabel(AssetsTrailersEnum.MAKE_CHECKBOX_LABEL);
        }
        
        public TextLabel modelCheckboxLabel() {
            return new TextLabel(AssetsTrailersEnum.MODEL_CHECKBOX_LABEL);
        }
                
        public TextLabel colorCheckboxLabel() {
            return new TextLabel(AssetsTrailersEnum.COLOR_CHECKBOX_LABEL);
        }
        
        public TextLabel weightCheckboxLabel() {
            return new TextLabel(AssetsTrailersEnum.WEIGHT_CHECKBOX_LABEL);
        }
                
        public TextLabel odometerCheckboxLabel() {
            return new TextLabel(AssetsTrailersEnum.ODOMETER_CHECKBOX_LABEL);
        }
        //
        
        public TextLabel selectARowLabel() {
            return new TextLabel(AssetsTrailersEnum.SELECT_A_ROW_LABEL);
        }
        
        public TextLabel trailerInformationLabel() {
            return new TextLabel(AssetsTrailersEnum.TRAILER_INFORMATION_HEADER);
        }
        
        public TextLabel vinLabel() {
            return new TextLabel(AssetsTrailersEnum.VIN_TRAILER_INFORMATION_LABEL);
        }
        
        public TextLabel makeLabel() {
            return new TextLabel(AssetsTrailersEnum.MAKE_TRAILER_INFORMATION_LABEL);
        }
        
        public TextLabel yearLabel() {
            return new TextLabel(AssetsTrailersEnum.YEAR_TRAILER_INFORMATION_LABEL);
        }
        
        public TextLabel colorLabel() {
            return new TextLabel(AssetsTrailersEnum.COLOR_TRAILER_INFORMATION_LABEL);
        }
        
        public TextLabel weightLabel() {
            return new TextLabel(AssetsTrailersEnum.WEIGHT_TRAILER_INFORMATION_LABEL);
        }
        
        public TextLabel licenseNumberLabel() {
            return new TextLabel(AssetsTrailersEnum.LICENSE_TRAILER_INFORMATION_LABEL);
        }
        
        public TextLabel stateLabel() {
            return new TextLabel(AssetsTrailersEnum.STATE_TRAILER_INFORMATION_LABEL);
        }
        
        public TextLabel trailerProfileLabel() {
            return new TextLabel(AssetsTrailersEnum.TRAILER_PROFILE_HEADER);
        }
        
        public TextLabel trailerIDLabel() {
            return new TextLabel(AssetsTrailersEnum.TRAILER_ID_LABEL);
        }
        
        public TextLabel statusLabel() {
            return new TextLabel(AssetsTrailersEnum.STATUS_LABEL);
        }
        
        public TextLabel trailerAssignmentLabel() {
            return new TextLabel(AssetsTrailersEnum.TRAILER_ASSIGNMENT_HEADER);
        }
        
        public TextLabel teamLabel() {
            return new TextLabel(AssetsTrailersEnum.TEAM_TRAILER_ASSIGNMENT_LABEL);
        }
        
        public TextLabel deviceAssignmentLabel() {
            return new TextLabel(AssetsTrailersEnum.DEVICE_ASSIGNMENT_HEADER);
        }
        
        public TextLabel assignedDeviceLabel() {
            return new TextLabel(AssetsTrailersEnum.ASSIGNED_DEVICE_LABEL);
        }
        
        public TextLabel vehicleAssignmentLabel() {
            return new TextLabel(AssetsTrailersEnum.VEHICLE_ASSIGNMENT_HEADER);
        }
        
        public TextLabel assignedVehicleLabel() {
            return new TextLabel(AssetsTrailersEnum.ASSIGNED_VEHICLE_LABEL);
        }
        
        public TextLabel driverAssignmentLabel() {
            return new TextLabel(AssetsTrailersEnum.DRIVER_ASSIGNMENT_HEADER);
        }
        
        public TextLabel assignedDriverLabel() {
            return new TextLabel(AssetsTrailersEnum.ASSIGNED_DRIVER_LABEL);
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
            return new Text(AssetsTrailersEnum.ASSIGNED_DEVICE_TEXT);
        }
        
        public Text assignedVehicle() {
            return new Text(AssetsTrailersEnum.ASSIGNED_VEHICLE_TEXT);
        }
        
        public Text assignedDriver() {
            return new Text(AssetsTrailersEnum.ASSIGNED_DRIVER_TEXT);
        }
        
        public TextTable trailerIdEntry() {
            return new TextTable(AssetsTrailersEnum.TRAILER_ID_ENTRY);
        }
        
        public TextTable teamEntry() {
            return new TextTable(AssetsTrailersEnum.TEAM_ENTRY);
        }
        
        public TextTable statusEntry() {
            return new TextTable(AssetsTrailersEnum.STATUS_ENTRY);
        }
        
        public TextTable vinEntry() {
            return new TextTable(AssetsTrailersEnum.VIN_ENTRY);
        }
        
        public TextTable licenseEntry() {
            return new TextTable(AssetsTrailersEnum.LICENSE_ENTRY);
        }
        
        public TextTable stateEntry() {
            return new TextTable(AssetsTrailersEnum.STATE_ENTRY);
        }
        
        public TextTable yearEntry() {
            return new TextTable(AssetsTrailersEnum.YEAR_ENTRY);
        }
        
        public TextTable makeEntry() {
            return new TextTable(AssetsTrailersEnum.MAKE_ENTRY);
        }
        
        public TextTable modelEntry() {
            return new TextTable(AssetsTrailersEnum.MODEL_ENTRY);
        }
        
        public TextTable colorEntry() {
            return new TextTable(AssetsTrailersEnum.COLOR_ENTRY);
        }
        
        public TextTable weightEntry() {
            return new TextTable(AssetsTrailersEnum.WEIGHT_ENTRY);
        }
        
        public TextTable odometerEntry() {
            return new TextTable(AssetsTrailersEnum.ODOMETER_ENTRY);
        }
        
    }
    
    public class AssetsTrailersCheckboxes {
        
        public CheckBox trailerID() {
            return new CheckBox(AssetsTrailersEnum.TRAILER_ID_CHECKBOX);
        }
                
        public CheckBox team() {
            return new CheckBox(AssetsTrailersEnum.TEAM_CHECKBOX);
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