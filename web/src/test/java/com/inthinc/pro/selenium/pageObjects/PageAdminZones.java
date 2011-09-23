package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.DropDown;
import com.inthinc.pro.automation.elements.Text;
import com.inthinc.pro.automation.elements.TextButton;
import com.inthinc.pro.automation.elements.TextDropDownLabel;
import com.inthinc.pro.automation.elements.TextField;
import com.inthinc.pro.automation.elements.TextFieldLabel;
import com.inthinc.pro.automation.elements.TextFieldWithSpinner;
import com.inthinc.pro.automation.elements.TextLabel;
import com.inthinc.pro.automation.elements.TextLink;
import com.inthinc.pro.selenium.pageEnums.AdminZonesEnum;

public class PageAdminZones extends AdminBar {

    public PageAdminZones() {
        url = AdminZonesEnum.DEFAULT_URL;
        checkMe.add(AdminZonesEnum.TITLE);
    }

    
    public class PageAdminZonesLinks extends AdminBarLinks {
        
        public TextLink download(){
            return new TextLink(AdminZonesEnum.DOWNLOAD_LINK);
        }
    }

    public class PageAdminZonesTexts extends AdminBarTexts {
        
        public Text title(){
            return new Text(AdminZonesEnum.TITLE);
        }
        
        public Text lastPublished(){
            return new Text(AdminZonesEnum.LAST_PUBLISHED);
        }
        
        public Text headerOptionColumn(){
            return new Text(AdminZonesEnum.OPTION_HEADER);
        }
        
        public Text headerSettingColumn(){
            return new Text(AdminZonesEnum.SETTING_HEADER);
        }
        
        public Text entryOption(){
            return new TextLabel(AdminZonesEnum.OPTION_ENTRY);
        }
        
        public Text entrySetting(){
            return new Text(AdminZonesEnum.OPTION_ENTRY);
        }
        
        public Text editZoneName(){
            return new TextDropDownLabel(AdminZonesEnum.ZONE_NAME);
        }
        
        public Text zoneName(){
            return new TextFieldLabel(AdminZonesEnum.ZONE_NAME);
        }
        
        public Text editLabelVehicleType(){
            return new TextDropDownLabel(AdminZonesEnum.EDIT_VEHICLE_TYPE);
        }
        
        public Text editLabelSpeedLimit(){
            return new Text(AdminZonesEnum.EDIT_SPEED_LIMIT_LABEL);
        }
        
        public Text editLabelReportOnArrivalDeparture(){
            return new TextDropDownLabel(AdminZonesEnum.EDIT_REPORT_ON_ARRIVAL_DEPARTURE);
        }
        
        public Text editLabelMonitorIdle(){
            return new TextDropDownLabel(AdminZonesEnum.EDIT_MONITOR_IDLE);
        }
        
        public Text editLabelSeatBeltViolations(){
            return new TextDropDownLabel(AdminZonesEnum.EDIT_SEAT_BELT_VIOLATION);
        }
        
        public Text editLabelSpeedingViolation(){
            return new TextDropDownLabel(AdminZonesEnum.EDIT_SPEEDING_VIOLATION);
        }
        
        public Text editLabelDriverIDViolation(){
            return new TextDropDownLabel(AdminZonesEnum.EDIT_DRIVER_ID_VIOLATION);
        }
        
        public Text editLabelMasterBuzzer(){
            return new TextDropDownLabel(AdminZonesEnum.EDIT_MASTER_BUZZER);
        }
        
        public Text editLabelHardTurnEvent(){
            return new TextDropDownLabel(AdminZonesEnum.EDIT_HARD_TURN_EVENT);
        }
        
        public Text editHardBrakeEvent(){
            return new TextDropDownLabel(AdminZonesEnum.EDIT_HARD_BRAKE_EVENT);
        }
        
        public Text editHardDipBumpEvent(){
            return new TextDropDownLabel(AdminZonesEnum.EDIT_HARD_DIP_BUMP_EVENT);
        }
        
        public Text messageAfterAction(){
            return new Text(AdminZonesEnum.ACTION_MESSAGE);
        }
    }

    public class PageAdminZonesTextFields extends AdminBarTextFields {
        

        public TextFieldWithSpinner editLabelSpeedLimit(){
            return new TextFieldWithSpinner(AdminZonesEnum.EDIT_SPEED_LIMIT);
        }
        
        public TextField findAddress(){
            return new TextField(AdminZonesEnum.EDIT_FIND_ADDRESS);
        }
        
        public TextField zoneName(){
            return new TextField(AdminZonesEnum.EDIT_ZONE_NAME);
        }
        
    }

    public class PageAdminZonesButtons extends AdminBarButtons {
        
        public TextButton edit(){
            return new TextButton(AdminZonesEnum.EDIT);
        }
        
        public TextButton clone(){
            return new TextButton(AdminZonesEnum.CLONE);
        }
        
        public TextButton delete(){
            return new TextButton(AdminZonesEnum.DELETE);
        }
        
        public TextButton addZone(){
            return new TextButton(AdminZonesEnum.ADD_ZONE);
        }
        
        public TextButton addAlert(){
            return new TextButton(AdminZonesEnum.ADD_ALERT);
        }
        
        public TextButton reset(){
            return new TextButton(AdminZonesEnum.EDIT_RESET);
        }
        
        public TextButton saveZone(){
            return new TextButton(AdminZonesEnum.EDIT_SAVE_ZONE);
        }
        
        public TextButton cancel(){
            return new TextButton(AdminZonesEnum.EDIT_CANCEL);
        }
        
    }

    public class PageAdminZonesDropDowns extends AdminBarDropDowns {
        
        public DropDown zoneName(){
            return new DropDown(AdminZonesEnum.ZONE_NAME);
        }
        
        public DropDown download(){
            return new DropDown(AdminZonesEnum.DOWNLOAD_DROP_DOWN);
        }
        
        public DropDown editLabelVehicleType(){
            return new DropDown(AdminZonesEnum.EDIT_VEHICLE_TYPE);
        }
        
        public DropDown editLabelReportOnArrivalDeparture(){
            return new DropDown(AdminZonesEnum.EDIT_REPORT_ON_ARRIVAL_DEPARTURE);
        }
        
        public DropDown editLabelMonitorIdle(){
            return new DropDown(AdminZonesEnum.EDIT_MONITOR_IDLE);
        }
        
        public DropDown editLabelSeatBeltViolations(){
            return new DropDown(AdminZonesEnum.EDIT_SEAT_BELT_VIOLATION);
        }
        
        public DropDown editLabelSpeedingViolation(){
            return new DropDown(AdminZonesEnum.EDIT_SPEEDING_VIOLATION);
        }
        
        public DropDown editLabelDriverIDViolation(){
            return new DropDown(AdminZonesEnum.EDIT_DRIVER_ID_VIOLATION);
        }
        
        public DropDown editLabelMasterBuzzer(){
            return new DropDown(AdminZonesEnum.EDIT_MASTER_BUZZER);
        }
        
        public DropDown editLabelHardTurnEvent(){
            return new DropDown(AdminZonesEnum.EDIT_HARD_TURN_EVENT);
        }
        
        public DropDown editHardBrakeEvent(){
            return new DropDown(AdminZonesEnum.EDIT_HARD_BRAKE_EVENT);
        }
        
        public DropDown editHardDipBumpEvent(){
            return new DropDown(AdminZonesEnum.EDIT_HARD_DIP_BUMP_EVENT);
        }
    }

    public class PageAdminZonesPopUps extends MastheadPopUps {}

    public class PageAdminZonesPager {
        public Paging pageIndex() {
            return new Paging();
        }
    }

    public PageAdminZonesPager _page() {
        return new PageAdminZonesPager();
    }

    public PageAdminZonesLinks _link() {
        return new PageAdminZonesLinks();
    }

    public PageAdminZonesTexts _text() {
        return new PageAdminZonesTexts();
    }

    public PageAdminZonesButtons _button() {
        return new PageAdminZonesButtons();
    }

    public PageAdminZonesTextFields _textField() {
        return new PageAdminZonesTextFields();
    }

    public PageAdminZonesDropDowns _dropDown() {
        return new PageAdminZonesDropDowns();
    }

    public PageAdminZonesPopUps _popUp() {
        return new PageAdminZonesPopUps();
    }
}
