package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.Button;
import com.inthinc.pro.automation.elements.ButtonTable;
import com.inthinc.pro.automation.elements.CheckBox;
import com.inthinc.pro.automation.elements.CheckBoxTable;
import com.inthinc.pro.automation.elements.DropDown;
import com.inthinc.pro.automation.elements.Selector;
import com.inthinc.pro.automation.elements.Text;
import com.inthinc.pro.automation.elements.TextButton;
import com.inthinc.pro.automation.elements.TextDropDownLabel;
import com.inthinc.pro.automation.elements.TextField;
import com.inthinc.pro.automation.elements.TextFieldLabel;
import com.inthinc.pro.automation.elements.TextFieldSuggestions;
import com.inthinc.pro.automation.elements.TextFieldTable;
import com.inthinc.pro.automation.elements.TextTable;
import com.inthinc.pro.selenium.pageEnums.AdminRedFlagAddEditEnum;

public class PageAdminRedFlagAddEdit extends AdminBar {

    public PageAdminRedFlagAddEdit() {
        url = null;
        checkMe.add(AdminRedFlagAddEditEnum.TITLE);
        checkMe.add(AdminRedFlagAddEditEnum.TYPE);
    }

    public class PageAdminAddEditRedFlagLinks extends AdminBarLinks {}

    public class PageAdminAddEditRedFlagTexts extends AdminBarTexts {
        
        public Text title(){
            return new Text(AdminRedFlagAddEditEnum.TITLE);
        }
        
        public Text headerRedFlagType(){
            return new Text(AdminRedFlagAddEditEnum.HEADER_TYPE);
        }
        
        public Text headerNameDescriptionAndType(){
            return new Text(AdminRedFlagAddEditEnum.HEADER_NAME_DESCRIPTION_TYPE);
        }
        
        public Text headerAssignTo(){
            return new Text(AdminRedFlagAddEditEnum.HEADER_ASSIGN_TO);
        }
        
        public Text headerTypeSettings(){
            return new Text(AdminRedFlagAddEditEnum.HEADER_TYPE_SETTINGS);
        }
        
        public Text headerNotificationsByName(){
            return new Text(AdminRedFlagAddEditEnum.HEADER_NOTIFICATIONS_BY_NAME);
        }
        
        public Text headerPhoneCallEscalation(){
            return new Text(AdminRedFlagAddEditEnum.HEADER_PHONE_CALL_ESCALATION);
        }
        
        public Text labelName(){
            return new TextFieldLabel(AdminRedFlagAddEditEnum.NAME);
        }
        
        public Text labelDescription(){
            return new TextFieldLabel(AdminRedFlagAddEditEnum.DESCRIPTION);
        }
        
        public Text labelNotification(){
            return new TextDropDownLabel(AdminRedFlagAddEditEnum.NOTIFICATION);
        }
        
        public Text labelStatus(){
            return new TextDropDownLabel(AdminRedFlagAddEditEnum.STATUS);
        }
        
        public Text labelAssign(){
            return new TextDropDownLabel(AdminRedFlagAddEditEnum.ASSIGN);
        }
        
        public Text labelFilter(){
            return new TextFieldLabel(AdminRedFlagAddEditEnum.FILTER);
        }
        
        public Text labelAlertOwner(){
            return new TextDropDownLabel(AdminRedFlagAddEditEnum.ALERT_OWNER);
        }
        
        public Text notePhoneNumbers(){
            return new Text(AdminRedFlagAddEditEnum.PHONE_CALL_NOTE);
        }
        
        public Text noteEscalationEmail(){
            return new Text(AdminRedFlagAddEditEnum.ESCALATION_EMAIL_NOTE);
        }
        
        public Text noteNotificationsByName(){
            return new Text(AdminRedFlagAddEditEnum.NOTIFICATIONS_BY_NAME_NOTE);
        }
        
        public Text labelCallDelay(){
            return new TextDropDownLabel(AdminRedFlagAddEditEnum.CALL_DELAY);
        }
        
        public Text labelLimitBy(){
            return new TextDropDownLabel(AdminRedFlagAddEditEnum.LIMIT_BY_TYPE);
        }
        
        public Text noteAlertOwner(){
            return new Text(AdminRedFlagAddEditEnum.ALERT_OWNER_NOTE);
        }

        public Text labelSpeedingButtons() {
            return new Text(AdminRedFlagAddEditEnum.SPEEDING_BUTTONS_LABEL);
        }

        public Text labelComplianceSeatbelt() {
            return new Text(AdminRedFlagAddEditEnum.COMPLIANCE_SEATBELT_LABEL);
        }

        public Text labelCompliancenoDriver() {
            return new Text(AdminRedFlagAddEditEnum.COMPLIANCE_NO_DRIVER_LABEL);
        }

        public Text labelComplianceParkingBrake() {
            return new Text(
                    AdminRedFlagAddEditEnum.COMPLIANCE_PARKING_BRAKE_LABEL);
        }

        public Text labelVehicleTampering() {
            return new Text(AdminRedFlagAddEditEnum.VEHICLE_TAMPERING_LABEL);
        }

        public Text labelVehicleIgnitionOn() {
            return new Text(AdminRedFlagAddEditEnum.VEHICLE_IGNITION_ON_LABEL);
        }

        public Text labelVehicleLowBattery() {
            return new Text(AdminRedFlagAddEditEnum.VEHICLE_LOW_BATTERY_LABEL);
        }

        public Text labelVehicleIdlingLimit() {
            return new Text(AdminRedFlagAddEditEnum.VEHICLE_IDLING_LIMIT_LABEL);
        }

        public Text labelEmergencyPanic() {
            return new Text(AdminRedFlagAddEditEnum.EMERGENCY_PANIC_LABEL);
        }

        public Text labelEmergencyLoneWorker() {
            return new Text(AdminRedFlagAddEditEnum.EMERGENCY_LONE_WORKER_LABEL);
        }

        public Text labelEmergencyLoneWorkerCanceled() {
            return new Text(
                    AdminRedFlagAddEditEnum.EMERGENCY_LONE_WORKER_CANCELED_LABEL);
        }

        public Text labelEmergencyCrash() {
            return new Text(AdminRedFlagAddEditEnum.EMERGENCY_CRASH_LABEL);
        }

        public Text labelHOSStoppedByOfficer() {
            return new Text(
                    AdminRedFlagAddEditEnum.HOS_STOPPED_BY_OFFICER_LABEL);
        }

        public Text labelHOSNoHoursRemaining() {
            return new Text(
                    AdminRedFlagAddEditEnum.HOS_NO_HOURS_REMAINING_LABEL);
        }

        public Text labelInstallationNew() {
            return new Text(AdminRedFlagAddEditEnum.INSTALLATION_NEW_LABEL);
        }

        public Text labelInstallationFirmwareUpdated() {
            return new Text(
                    AdminRedFlagAddEditEnum.INSTALLATION_FIRMWARE_UPDATED_LABEL);
        }

        public Text labelInstallationManualLocation() {
            return new Text(
                    AdminRedFlagAddEditEnum.INSTALLATION_MANUAL_LOCATION_LABEL);
        }

        public Text labelInstallationCannotMountInternalStorage() {
            return new Text(
                    AdminRedFlagAddEditEnum.INSTALLATION_CANNOT_MOUNT_INTERNAL_STORAGE_LABEL);
        }

        public Text labelInstallationQSIFirmwareUpdated() {
            return new Text(
                    AdminRedFlagAddEditEnum.INSTALLATION_QSI_FIRMWARE_UPDATED_LABEL);
        }

        public Text labelInstallationCantGetHeartbeat() {
            return new Text(
                    AdminRedFlagAddEditEnum.INSTALLATION_CANT_GET_HEARTBEAT_LABEL);
        }

        public Text labelInstallationCrashFirmwareUpdated() {
            return new Text(
                    AdminRedFlagAddEditEnum.INSTALLATION_CRASH_FIRMWARE_UPDATED_LABEL);
        }

        public Text labelInstallationDownloadedZones() {
            return new Text(
                    AdminRedFlagAddEditEnum.INSTALLATION_DOWNLOADED_ZONES_LABEL);
        }

        public Text labelZonesArrival() {
            return new Text(AdminRedFlagAddEditEnum.ZONES_ARRIVAL_LABEL);
        }

        public Text labelZonesDeparture() {
            return new Text(AdminRedFlagAddEditEnum.ZONES_DEPARTURE_LABEL);
        }
        
        public TextTable speedingText(){
            return new TextTable(AdminRedFlagAddEditEnum.SPEEDING_CHECK_TEXT);
        }
        
        public Text styleAccelText(){
            return new Text(AdminRedFlagAddEditEnum.STYLE_ACCEL_CHECK_TEXT);
        }
        
        public Text styleBrakeText(){
            return new Text(AdminRedFlagAddEditEnum.STYLE_BRAKE_CHECK_TEXT);
        }
        
        public Text styleTurnText(){
            return new Text(AdminRedFlagAddEditEnum.STYLE_TURN_CHECK_TEXT);
        }
        
        public Text styleBumpText(){
            return new Text(AdminRedFlagAddEditEnum.STYLE_BUMP_CHECK_TEXT);
        }
        
        public Text labelStyleAccel(){
            return new Text(AdminRedFlagAddEditEnum.STYLE_ACCEL_TEXT);
        }
        
        public Text labelStyleBrake(){
            return new Text(AdminRedFlagAddEditEnum.STYLE_BRAKE_TEXT);
        }
        
        public Text labelStyleTurn(){
            return new Text(AdminRedFlagAddEditEnum.STYLE_TURN_TEXT);
        }
        
        public Text labelStyleBump(){
            return new Text(AdminRedFlagAddEditEnum.STYLE_BUMP_TEXT);
        }
        
        public Text noteFatigue(){
                return new Text(AdminRedFlagAddEditEnum.FATIGUE_NOTE);
        }
        
        public Text fatigueMicrosleep(){
            return new Text(AdminRedFlagAddEditEnum.FATIGUE_MICROSLEEP);
        }
        
        public Text wirelineMicrosleep(){
            return new Text(AdminRedFlagAddEditEnum.WIRELINE_MICROSLEEP);
        }

        public Text labelTextMessage(){
            return new Text(AdminRedFlagAddEditEnum.TEXT_MESSAGE);
        }
        
    }

    public class PageAdminAddEditRedFlagTextFields extends AdminBarTextFields {
        
        public TextField name(){
            return new TextField(AdminRedFlagAddEditEnum.NAME);
        }
        
        public TextField description(){
            return new TextField(AdminRedFlagAddEditEnum.DESCRIPTION);
        }
        
        public TextField filter(){
            return new TextField(AdminRedFlagAddEditEnum.FILTER);
        }
        
        public TextFieldSuggestions notificationsByName(){
            return new TextFieldSuggestions(AdminRedFlagAddEditEnum.NOTIFICATIONS_BY_NAME, AdminRedFlagAddEditEnum.NOTIFICATIONS_BY_NAME_SUGGESTION);
        }
        
        public TextFieldSuggestions phoneNumbers(Integer entry){
            return new TextFieldSuggestions(AdminRedFlagAddEditEnum.PHONE_NUMBERS, AdminRedFlagAddEditEnum.PHONE_NUMBERS_SUGGESTION, entry);
        }
        
        public TextFieldSuggestions escalationEmail(){
            return new TextFieldSuggestions(AdminRedFlagAddEditEnum.ESCALATION_EMAIL, AdminRedFlagAddEditEnum.ESCALATION_EMAIL_SUGGESTION);
        }
        
        public TextField vehicleIdlingLimitInMinutes(){
            return new TextField(AdminRedFlagAddEditEnum.VEHICLE_IDLING_BOX);
        }
        
        public TextFieldTable speedingBox(){
            return new TextFieldTable(AdminRedFlagAddEditEnum.SPEEDING_BOX);
        }
        
        public TextField vehicleIdlingBox(){
            return new TextField(AdminRedFlagAddEditEnum.VEHICLE_IDLING_BOX);
        }
    }

    public class PageAdminAddEditRedFlagButtons extends AdminBarButtons {
        
        public TextButton topSave(){
            return new TextButton(AdminRedFlagAddEditEnum.SAVE_TOP);
        }
        
        public TextButton topCancel(){
            return new TextButton(AdminRedFlagAddEditEnum.CANCEL_TOP);
        }
        
        public TextButton bottomSave(){
            return new TextButton(AdminRedFlagAddEditEnum.SAVE_BOTTOM);
        }
        
        public TextButton bottomCancel(){
            return new TextButton(AdminRedFlagAddEditEnum.CANCEL_BOTTOM);
        }
        
        public Button speedingPlus(){
            return new Button(AdminRedFlagAddEditEnum.SPEEDING_PLUS);
        }
        
        public Button speedingMinus(){
            return new Button(AdminRedFlagAddEditEnum.SPEEDING_MINUS);
        }
        
        public ButtonTable phoneNumberAddOrMinus(){
            return new ButtonTable(AdminRedFlagAddEditEnum.PHONE_NUMBER_BUTTON);
        }
        
        public Button moveLeft(){
            return new Button(AdminRedFlagAddEditEnum.MOVE_LEFT);
        }
        
        public Button moveRight(){
            return new Button(AdminRedFlagAddEditEnum.MOVE_RIGHT);
        }
        
        public Button moveAllLeft(){
            return new Button(AdminRedFlagAddEditEnum.MOVE_ALL_LEFT);
        }
        
        public Button moveAllRight(){
            return new Button(AdminRedFlagAddEditEnum.MOVE_ALL_RIGHT);
        }
    }

    public class PageAdminAddEditRedFlagDropDowns extends AdminBarDropDowns {
        
        public DropDown redFlagType(){
            return new DropDown(AdminRedFlagAddEditEnum.TYPE);
        }
        
        public DropDown notification(){
            return new DropDown(AdminRedFlagAddEditEnum.NOTIFICATION);
        }
        
        public DropDown status(){
            return new DropDown(AdminRedFlagAddEditEnum.STATUS);
        }
        
        public DropDown assign(){
            return new DropDown(AdminRedFlagAddEditEnum.ASSIGN);
        }
        
        public DropDown callDelay(){
            return new DropDown(AdminRedFlagAddEditEnum.CALL_DELAY);
        }
        
        public DropDown limitByType(){
            return new DropDown(AdminRedFlagAddEditEnum.LIMIT_BY_TYPE);
        }
        
        public DropDown limitByValue(){
            return new DropDown(AdminRedFlagAddEditEnum.LIMIT_BY_VALUE);
        }
        
        public DropDown zonesZone(){
            return new DropDown(AdminRedFlagAddEditEnum.ZONES_ZONE);
        }
    }
    
    public AddEditRedFlagSelects _select(){
        return new AddEditRedFlagSelects();
    }
    
    public class AddEditRedFlagSelects {
        
        public Selector assignToLeft(){
            return new Selector(AdminRedFlagAddEditEnum.ASSIGN_TO_LEFT);
        }
        
        public Selector assignToRight(){
            return new Selector(AdminRedFlagAddEditEnum.ASSIGN_TO_RIGHT);
        }
    }
    
    public AddEditRedFlagCheckBoxs _checkBox(){
        return new AddEditRedFlagCheckBoxs();
    }
    
    public class AddEditRedFlagCheckBoxs{
        
        public CheckBox styleAccel(){
            return new CheckBox(AdminRedFlagAddEditEnum.STYLE_ACCEL_CHECK);
        }
        
        public CheckBox styleBrake(){
            return new CheckBox(AdminRedFlagAddEditEnum.STYLE_BRAKE_CHECK);
        }
        
        public CheckBox styleTurn(){
            return new CheckBox(AdminRedFlagAddEditEnum.STYLE_TURN_CHECK);
        }
        
        public CheckBox styleBump(){
            return new CheckBox(AdminRedFlagAddEditEnum.STYLE_BUMP_CHECK);
        }
        
        public CheckBoxTable speeding(){
            return new CheckBoxTable(AdminRedFlagAddEditEnum.SPEEDING_CHECK);
        }
        
        public CheckBox complianceSeatbeltBoxs(){
            return new CheckBox(AdminRedFlagAddEditEnum.COMPLIANCE_SEATBELT_CHECKBOXS);
        }
        
        public CheckBox compliancenoDriverBoxs(){
            return new CheckBox(AdminRedFlagAddEditEnum.COMPLIANCE_NO_DRIVER_CHECKBOXS);
        }
        
        public CheckBox complianceParkingBrakeBoxs(){
            return new CheckBox(AdminRedFlagAddEditEnum.COMPLIANCE_PARKING_BRAKE_CHECKBOXS);
        }
        
        public CheckBox vehicleTampering(){
            return new CheckBox(AdminRedFlagAddEditEnum.VEHICLE_TAMPERING);
        }
        
        public CheckBox vehicleIgnitionOn(){
            return new CheckBox(AdminRedFlagAddEditEnum.VEHICLE_IGNITION_ON);
        }
        
        public CheckBox vehicleLowBattery(){
            return new CheckBox(AdminRedFlagAddEditEnum.VEHICLE_LOW_BATTERY);
        }
        
        public CheckBox vehicleIdlingLimit(){
            return new CheckBox(AdminRedFlagAddEditEnum.VEHICLE_IDLING_LIMIT);
        }
        
        public CheckBox emergencyPanic(){
            return new CheckBox(AdminRedFlagAddEditEnum.EMERGENCY_PANIC);
        }
        
        public CheckBox emergencyLoneWorker(){
            return new CheckBox(AdminRedFlagAddEditEnum.EMERGENCY_LONE_WORKER);
        }
        
        public CheckBox emergencyLoneWorkderCanceled(){
            return new CheckBox(AdminRedFlagAddEditEnum.EMERGENCY_LONE_WORKER_CANCELED);
        }
        
        public CheckBox emergencyCrash(){
            return new CheckBox(AdminRedFlagAddEditEnum.EMERGENCY_CRASH);
        }
        
        public CheckBox hosStoppedByOfficer(){
            return new CheckBox(AdminRedFlagAddEditEnum.HOS_STOPPED_BY_OFFICER);
        }
        
        public CheckBox hosNoHoursRemaining(){
            return new CheckBox(AdminRedFlagAddEditEnum.HOS_NO_HOURS_REMAINING);
        }
        
        public CheckBox installationNewInstall(){
            return new CheckBox(AdminRedFlagAddEditEnum.INSTALLATION_NEW);
        }
        
        public CheckBox installationFirmwareUpdated(){
            return new CheckBox(AdminRedFlagAddEditEnum.INSTALLATION_FIRMWARE_UPDATED);
        }
        
        public CheckBox installationManualLocation(){
            return new CheckBox(AdminRedFlagAddEditEnum.INSTALLATION_MANUAL_LOCATION);
        }
        
        public CheckBox installationCannotMountInternalStorage(){
            return new CheckBox(AdminRedFlagAddEditEnum.INSTALLATION_CANNOT_MOUNT_INTERNAL_STORAGE);
        }
        
        public CheckBox installationQSIFirmwareUpdated(){
            return new CheckBox(AdminRedFlagAddEditEnum.INSTALLATION_QSI_FIRMWARE_UPDATED);
        }
        
        public CheckBox installationCantGetHeartBeat(){
            return new CheckBox(AdminRedFlagAddEditEnum.INSTALLATION_CANT_GET_HEARTBEAT);
        }
        
        public CheckBox installationCrashFirmwareUpdated(){
            return new CheckBox(AdminRedFlagAddEditEnum.INSTALLATION_CRASH_FIRMWARE_UPDATED);
        }
        
        public CheckBox installationDownloadedZones(){
            return new CheckBox(AdminRedFlagAddEditEnum.INSTALLATION_DOWNLOADED_ZONES);
        }
        
        public CheckBox zonesArrival(){
            return new CheckBox(AdminRedFlagAddEditEnum.ZONES_ARRIVAL);
        }
        
        public CheckBox zonesDeparture(){
            return new CheckBox(AdminRedFlagAddEditEnum.ZONES_DEPARTURE);
        }
    }

    public class PageAdminAddEditRedFlagPopUps extends MastheadPopUps {}

    public class PageAdminAddEditRedFlagPager {
        public Paging pageIndex() {
            return new Paging();
        }
    }

    public PageAdminAddEditRedFlagPager _page() {
        return new PageAdminAddEditRedFlagPager();
    }

    public PageAdminAddEditRedFlagLinks _link() {
        return new PageAdminAddEditRedFlagLinks();
    }

    public PageAdminAddEditRedFlagTexts _text() {
        return new PageAdminAddEditRedFlagTexts();
    }

    public PageAdminAddEditRedFlagButtons _button() {
        return new PageAdminAddEditRedFlagButtons();
    }

    public PageAdminAddEditRedFlagTextFields _textField() {
        return new PageAdminAddEditRedFlagTextFields();
    }

    public PageAdminAddEditRedFlagDropDowns _dropDown() {
        return new PageAdminAddEditRedFlagDropDowns();
    }

    public PageAdminAddEditRedFlagPopUps _popUp() {
        return new PageAdminAddEditRedFlagPopUps();
    }
}
