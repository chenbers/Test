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
import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.selenium.pageEnums.AdminAddEditRedFlagEnum;

public class PageAdminAddEditRedFlag extends AdminBar {

    public PageAdminAddEditRedFlag() {
        checkMe.add(AdminAddEditRedFlagEnum.TITLE);
        checkMe.add(AdminAddEditRedFlagEnum.TYPE);
    }

    public class AdminAddEditRedFlagLinks extends AdminBarLinks {}

    public class AdminAddEditRedFlagTexts extends AdminBarTexts {
        
        public Text title(){
            return new Text(AdminAddEditRedFlagEnum.TITLE);
        }
        
        public Text headerRedFlagType(){
            return new Text(AdminAddEditRedFlagEnum.HEADER_TYPE);
        }
        
        public Text headerNameDescriptionAndType(){
            return new Text(AdminAddEditRedFlagEnum.HEADER_NAME_DESCRIPTION_TYPE);
        }
        
        public Text headerAssignTo(){
            return new Text(AdminAddEditRedFlagEnum.HEADER_ASSIGN_TO);
        }
        
        public Text headerTypeSettings(){
            return new Text(AdminAddEditRedFlagEnum.HEADER_TYPE_SETTINGS);
        }
        
        public Text headerNotificationsByName(){
            return new Text(AdminAddEditRedFlagEnum.HEADER_NOTIFICATIONS_BY_NAME);
        }
        
        public Text headerPhoneCallEscalation(){
            return new Text(AdminAddEditRedFlagEnum.HEADER_PHONE_CALL_ESCALATION);
        }
        
        public Text labelName(){
            return new TextFieldLabel(AdminAddEditRedFlagEnum.NAME);
        }
        
        public Text labelDescription(){
            return new TextFieldLabel(AdminAddEditRedFlagEnum.DESCRIPTION);
        }
        
        public Text labelNotification(){
            return new TextDropDownLabel(AdminAddEditRedFlagEnum.NOTIFICATION);
        }
        
        public Text labelStatus(){
            return new TextDropDownLabel(AdminAddEditRedFlagEnum.STATUS);
        }
        
        public Text labelAssign(){
            return new TextDropDownLabel(AdminAddEditRedFlagEnum.ASSIGN);
        }
        
        public Text labelFilter(){
            return new TextFieldLabel(AdminAddEditRedFlagEnum.FILTER);
        }
        
        public Text labelAlertOwner(){
            return new TextDropDownLabel(AdminAddEditRedFlagEnum.ALERT_OWNER);
        }
        
        public Text notePhoneNumbers(){
            return new Text(AdminAddEditRedFlagEnum.PHONE_CALL_NOTE);
        }
        
        public Text noteEscalationEmail(){
            return new Text(AdminAddEditRedFlagEnum.ESCALATION_EMAIL_NOTE);
        }
        
        public Text noteNotificationsByName(){
            return new Text(AdminAddEditRedFlagEnum.NOTIFICATIONS_BY_NAME_NOTE);
        }
        
        public Text labelCallDelay(){
            return new TextDropDownLabel(AdminAddEditRedFlagEnum.CALL_DELAY);
        }
        
        public Text labelLimitBy(){
            return new TextDropDownLabel(AdminAddEditRedFlagEnum.LIMIT_BY_TYPE);
        }
        
        public Text noteAlertOwner(){
            return new Text(AdminAddEditRedFlagEnum.ALERT_OWNER_NOTE);
        }

        public Text labelSpeedingButtons() {
            return new Text(AdminAddEditRedFlagEnum.SPEEDING_BUTTONS_LABEL);
        }

        public Text labelComplianceSeatbelt() {
            return new Text(AdminAddEditRedFlagEnum.COMPLIANCE_SEATBELT_LABEL);
        }

        public Text labelCompliancenoDriver() {
            return new Text(AdminAddEditRedFlagEnum.COMPLIANCE_NO_DRIVER_LABEL);
        }

        public Text labelComplianceParkingBrake() {
            return new Text(
                    AdminAddEditRedFlagEnum.COMPLIANCE_PARKING_BRAKE_LABEL);
        }

        public Text labelVehicleTampering() {
            return new Text(AdminAddEditRedFlagEnum.VEHICLE_TAMPERING_LABEL);
        }

        public Text labelVehicleIgnitionOn() {
            return new Text(AdminAddEditRedFlagEnum.VEHICLE_IGNITION_ON_LABEL);
        }

        public Text labelVehicleLowBattery() {
            return new Text(AdminAddEditRedFlagEnum.VEHICLE_LOW_BATTERY_LABEL);
        }

        public Text labelVehicleIdlingLimit() {
            return new Text(AdminAddEditRedFlagEnum.VEHICLE_IDLING_LIMIT_LABEL);
        }

        public Text labelEmergencyPanic() {
            return new Text(AdminAddEditRedFlagEnum.EMERGENCY_PANIC_LABEL);
        }

        public Text labelEmergencyLoneWorker() {
            return new Text(AdminAddEditRedFlagEnum.EMERGENCY_LONE_WORKER_LABEL);
        }

        public Text labelEmergencyLoneWorkerCanceled() {
            return new Text(
                    AdminAddEditRedFlagEnum.EMERGENCY_LONE_WORKER_CANCELED_LABEL);
        }

        public Text labelEmergencyCrash() {
            return new Text(AdminAddEditRedFlagEnum.EMERGENCY_CRASH_LABEL);
        }

        public Text labelHOSStoppedByOfficer() {
            return new Text(
                    AdminAddEditRedFlagEnum.HOS_STOPPED_BY_OFFICER_LABEL);
        }

        public Text labelHOSNoHoursRemaining() {
            return new Text(
                    AdminAddEditRedFlagEnum.HOS_NO_HOURS_REMAINING_LABEL);
        }

        public Text labelInstallationNew() {
            return new Text(AdminAddEditRedFlagEnum.INSTALLATION_NEW_LABEL);
        }

        public Text labelInstallationFirmwareUpdated() {
            return new Text(
                    AdminAddEditRedFlagEnum.INSTALLATION_FIRMWARE_UPDATED_LABEL);
        }

        public Text labelInstallationManualLocation() {
            return new Text(
                    AdminAddEditRedFlagEnum.INSTALLATION_MANUAL_LOCATION_LABEL);
        }

        public Text labelInstallationCannotMountInternalStorage() {
            return new Text(
                    AdminAddEditRedFlagEnum.INSTALLATION_CANNOT_MOUNT_INTERNAL_STORAGE_LABEL);
        }

        public Text labelInstallationQSIFirmwareUpdated() {
            return new Text(
                    AdminAddEditRedFlagEnum.INSTALLATION_QSI_FIRMWARE_UPDATED_LABEL);
        }

        public Text labelInstallationCantGetHeartbeat() {
            return new Text(
                    AdminAddEditRedFlagEnum.INSTALLATION_CANT_GET_HEARTBEAT_LABEL);
        }

        public Text labelInstallationCrashFirmwareUpdated() {
            return new Text(
                    AdminAddEditRedFlagEnum.INSTALLATION_CRASH_FIRMWARE_UPDATED_LABEL);
        }

        public Text labelInstallationDownloadedZones() {
            return new Text(
                    AdminAddEditRedFlagEnum.INSTALLATION_DOWNLOADED_ZONES_LABEL);
        }

        public Text labelZonesArrival() {
            return new Text(AdminAddEditRedFlagEnum.ZONES_ARRIVAL_LABEL);
        }

        public Text labelZonesDeparture() {
            return new Text(AdminAddEditRedFlagEnum.ZONES_DEPARTURE_LABEL);
        }
        
        public TextTable speedingText(){
            return new TextTable(AdminAddEditRedFlagEnum.SPEEDING_CHECK_TEXT);
        }
        
        public Text styleAccelText(){
            return new Text(AdminAddEditRedFlagEnum.STYLE_ACCEL_CHECK_TEXT);
        }
        
        public Text styleBrakeText(){
            return new Text(AdminAddEditRedFlagEnum.STYLE_BRAKE_CHECK_TEXT);
        }
        
        public Text styleTurnText(){
            return new Text(AdminAddEditRedFlagEnum.STYLE_TURN_CHECK_TEXT);
        }
        
        public Text styleBumpText(){
            return new Text(AdminAddEditRedFlagEnum.STYLE_BUMP_CHECK_TEXT);
        }
        
        public Text labelStyleAccel(){
            return new Text(AdminAddEditRedFlagEnum.STYLE_ACCEL_TEXT);
        }
        
        public Text labelStyleBrake(){
            return new Text(AdminAddEditRedFlagEnum.STYLE_BRAKE_TEXT);
        }
        
        public Text labelStyleTurn(){
            return new Text(AdminAddEditRedFlagEnum.STYLE_TURN_TEXT);
        }
        
        public Text labelStyleBump(){
            return new Text(AdminAddEditRedFlagEnum.STYLE_BUMP_TEXT);
        }
        
        public Text noteFatigue(){
                return new Text(AdminAddEditRedFlagEnum.FATIGUE_NOTE);
        }
        
        public Text fatigueMicrosleep(){
            return new Text(AdminAddEditRedFlagEnum.FATIGUE_MICROSLEEP);
        }
        
        public Text wirelineMicrosleep(){
            return new Text(AdminAddEditRedFlagEnum.WIRELINE_MICROSLEEP);
        }

        public Text labelTextMessage(){
            return new Text(AdminAddEditRedFlagEnum.TEXT_MESSAGE);
        }
        
    }

    public class AdminAddEditRedFlagTextFields extends AdminBarTextFields {
        
        public TextField name(){
            return new TextField(AdminAddEditRedFlagEnum.NAME);
        }
        
        public TextField description(){
            return new TextField(AdminAddEditRedFlagEnum.DESCRIPTION);
        }
        
        public TextField filter(){
            return new TextField(AdminAddEditRedFlagEnum.FILTER);
        }
        
        public TextFieldSuggestions notificationsByName(){
            return new TextFieldSuggestions(AdminAddEditRedFlagEnum.NOTIFICATIONS_BY_NAME, AdminAddEditRedFlagEnum.NOTIFICATIONS_BY_NAME_SUGGESTION);
        }
        
        public TextFieldSuggestions phoneNumbers(Integer entry){
            return new TextFieldSuggestions(AdminAddEditRedFlagEnum.PHONE_NUMBERS, AdminAddEditRedFlagEnum.PHONE_NUMBERS_SUGGESTION, entry);
        }
        
        public TextFieldSuggestions escalationEmail(){
            return new TextFieldSuggestions(AdminAddEditRedFlagEnum.ESCALATION_EMAIL, AdminAddEditRedFlagEnum.ESCALATION_EMAIL_SUGGESTION);
        }
        
        public TextField vehicleIdlingLimitInMinutes(){
            return new TextField(AdminAddEditRedFlagEnum.VEHICLE_IDLING_BOX);
        }
        
        public TextFieldTable speedingBox(){
            return new TextFieldTable(AdminAddEditRedFlagEnum.SPEEDING_BOX);
        }
        
        public TextField vehicleIdlingBox(){
            return new TextField(AdminAddEditRedFlagEnum.VEHICLE_IDLING_BOX);
        }
    }

    public class AdminAddEditRedFlagButtons extends AdminBarButtons {
        
        public TextButton saveTop(){
            return new TextButton(AdminAddEditRedFlagEnum.SAVE_TOP);
        }
        
        public TextButton cancelTop(){
            return new TextButton(AdminAddEditRedFlagEnum.CANCEL_TOP);
        }
        
        public TextButton saveBottom(){
            return new TextButton(AdminAddEditRedFlagEnum.SAVE_BOTTOM);
        }
        
        public TextButton cancelBottom(){
            return new TextButton(AdminAddEditRedFlagEnum.CANCEL_BOTTOM);
        }
        
        public Button speedingPlus(){
            return new Button(AdminAddEditRedFlagEnum.SPEEDING_PLUS);
        }
        
        public Button speedingMinus(){
            return new Button(AdminAddEditRedFlagEnum.SPEEDING_MINUS);
        }
        
        public ButtonTable phoneNumberAddOrMinus(){
            return new ButtonTable(AdminAddEditRedFlagEnum.PHONE_NUMBER_BUTTON);
        }
        
        public Button moveLeft(){
            return new Button(AdminAddEditRedFlagEnum.MOVE_LEFT);
        }
        
        public Button moveRight(){
            return new Button(AdminAddEditRedFlagEnum.MOVE_RIGHT);
        }
        
        public Button moveAllLeft(){
            return new Button(AdminAddEditRedFlagEnum.MOVE_ALL_LEFT);
        }
        
        public Button moveAllRight(){
            return new Button(AdminAddEditRedFlagEnum.MOVE_ALL_RIGHT);
        }
    }

    public class AdminAddEditRedFlagDropDowns extends AdminBarDropDowns {
        
        public DropDown redFlagType(){
            return new DropDown(AdminAddEditRedFlagEnum.TYPE);
        }
        
        public DropDown notification(){
            return new DropDown(AdminAddEditRedFlagEnum.NOTIFICATION);
        }
        
        public DropDown status(){
            return new DropDown(AdminAddEditRedFlagEnum.STATUS);
        }
        
        public DropDown assign(){
            return new DropDown(AdminAddEditRedFlagEnum.ASSIGN);
        }
        
        public DropDown callDelay(){
            return new DropDown(AdminAddEditRedFlagEnum.CALL_DELAY);
        }
        
        public DropDown limitByType(){
            return new DropDown(AdminAddEditRedFlagEnum.LIMIT_BY_TYPE);
        }
        
        public DropDown limitByValue(){
            return new DropDown(AdminAddEditRedFlagEnum.LIMIT_BY_VALUE);
        }
        
        public DropDown zonesZone(){
            return new DropDown(AdminAddEditRedFlagEnum.ZONES_ZONE);
        }
    }
    
    public AddEditRedFlagSelects _select(){
        return new AddEditRedFlagSelects();
    }
    
    public class AddEditRedFlagSelects {
        
        public Selector assignToLeft(){
            return new Selector(AdminAddEditRedFlagEnum.ASSIGN_TO_LEFT);
        }
        
        public Selector assignToRight(){
            return new Selector(AdminAddEditRedFlagEnum.ASSIGN_TO_RIGHT);
        }
    }
    
    public AddEditRedFlagCheckBoxs _checkBox(){
        return new AddEditRedFlagCheckBoxs();
    }
    
    public class AddEditRedFlagCheckBoxs{
        
        public CheckBox styleAccel(){
            return new CheckBox(AdminAddEditRedFlagEnum.STYLE_ACCEL_CHECK);
        }
        
        public CheckBox styleBrake(){
            return new CheckBox(AdminAddEditRedFlagEnum.STYLE_BRAKE_CHECK);
        }
        
        public CheckBox styleTurn(){
            return new CheckBox(AdminAddEditRedFlagEnum.STYLE_TURN_CHECK);
        }
        
        public CheckBox styleBump(){
            return new CheckBox(AdminAddEditRedFlagEnum.STYLE_BUMP_CHECK);
        }
        
        public CheckBoxTable speeding(){
            return new CheckBoxTable(AdminAddEditRedFlagEnum.SPEEDING_CHECK);
        }
        
        public CheckBox complianceSeatbeltBoxs(){
            return new CheckBox(AdminAddEditRedFlagEnum.COMPLIANCE_SEATBELT_CHECKBOXS);
        }
        
        public CheckBox compliancenoDriverBoxs(){
            return new CheckBox(AdminAddEditRedFlagEnum.COMPLIANCE_NO_DRIVER_CHECKBOXS);
        }
        
        public CheckBox complianceParkingBrakeBoxs(){
            return new CheckBox(AdminAddEditRedFlagEnum.COMPLIANCE_PARKING_BRAKE_CHECKBOXS);
        }
        
        public CheckBox vehicleTampering(){
            return new CheckBox(AdminAddEditRedFlagEnum.VEHICLE_TAMPERING);
        }
        
        public CheckBox vehicleIgnitionOn(){
            return new CheckBox(AdminAddEditRedFlagEnum.VEHICLE_IGNITION_ON);
        }
        
        public CheckBox vehicleLowBattery(){
            return new CheckBox(AdminAddEditRedFlagEnum.VEHICLE_LOW_BATTERY);
        }
        
        public CheckBox vehicleIdlingLimit(){
            return new CheckBox(AdminAddEditRedFlagEnum.VEHICLE_IDLING_LIMIT);
        }
        
        public CheckBox emergencyPanic(){
            return new CheckBox(AdminAddEditRedFlagEnum.EMERGENCY_PANIC);
        }
        
        public CheckBox emergencyLoneWorker(){
            return new CheckBox(AdminAddEditRedFlagEnum.EMERGENCY_LONE_WORKER);
        }
        
        public CheckBox emergencyLoneWorkderCanceled(){
            return new CheckBox(AdminAddEditRedFlagEnum.EMERGENCY_LONE_WORKER_CANCELED);
        }
        
        public CheckBox emergencyCrash(){
            return new CheckBox(AdminAddEditRedFlagEnum.EMERGENCY_CRASH);
        }
        
        public CheckBox hosStoppedByOfficer(){
            return new CheckBox(AdminAddEditRedFlagEnum.HOS_STOPPED_BY_OFFICER);
        }
        
        public CheckBox hosNoHoursRemaining(){
            return new CheckBox(AdminAddEditRedFlagEnum.HOS_NO_HOURS_REMAINING);
        }
        
        public CheckBox installationNewInstall(){
            return new CheckBox(AdminAddEditRedFlagEnum.INSTALLATION_NEW);
        }
        
        public CheckBox installationFirmwareUpdated(){
            return new CheckBox(AdminAddEditRedFlagEnum.INSTALLATION_FIRMWARE_UPDATED);
        }
        
        public CheckBox installationManualLocation(){
            return new CheckBox(AdminAddEditRedFlagEnum.INSTALLATION_MANUAL_LOCATION);
        }
        
        public CheckBox installationCannotMountInternalStorage(){
            return new CheckBox(AdminAddEditRedFlagEnum.INSTALLATION_CANNOT_MOUNT_INTERNAL_STORAGE);
        }
        
        public CheckBox installationQSIFirmwareUpdated(){
            return new CheckBox(AdminAddEditRedFlagEnum.INSTALLATION_QSI_FIRMWARE_UPDATED);
        }
        
        public CheckBox installationCantGetHeartBeat(){
            return new CheckBox(AdminAddEditRedFlagEnum.INSTALLATION_CANT_GET_HEARTBEAT);
        }
        
        public CheckBox installationCrashFirmwareUpdated(){
            return new CheckBox(AdminAddEditRedFlagEnum.INSTALLATION_CRASH_FIRMWARE_UPDATED);
        }
        
        public CheckBox installationDownloadedZones(){
            return new CheckBox(AdminAddEditRedFlagEnum.INSTALLATION_DOWNLOADED_ZONES);
        }
        
        public CheckBox zonesArrival(){
            return new CheckBox(AdminAddEditRedFlagEnum.ZONES_ARRIVAL);
        }
        
        public CheckBox zonesDeparture(){
            return new CheckBox(AdminAddEditRedFlagEnum.ZONES_DEPARTURE);
        }
    }

    public class AdminAddEditRedFlagPopUps extends MastheadPopUps {}

    public class AdminAddEditRedFlagPager {
        public Paging pageIndex() {
            return new Paging();
        }
    }

    public AdminAddEditRedFlagPager _page() {
        return new AdminAddEditRedFlagPager();
    }

    public AdminAddEditRedFlagLinks _link() {
        return new AdminAddEditRedFlagLinks();
    }

    public AdminAddEditRedFlagTexts _text() {
        return new AdminAddEditRedFlagTexts();
    }

    public AdminAddEditRedFlagButtons _button() {
        return new AdminAddEditRedFlagButtons();
    }

    public AdminAddEditRedFlagTextFields _textField() {
        return new AdminAddEditRedFlagTextFields();
    }

    public AdminAddEditRedFlagDropDowns _dropDown() {
        return new AdminAddEditRedFlagDropDowns();
    }

    public AdminAddEditRedFlagPopUps _popUp() {
        return new AdminAddEditRedFlagPopUps();
    }

    @Override
    public SeleniumEnums setUrl() {
        return AdminAddEditRedFlagEnum.URL;
    }

    @Override
    protected boolean checkIsOnPage() {
        return _button().saveTop().isPresent() && _dropDown().redFlagType().isPresent();
    }
}