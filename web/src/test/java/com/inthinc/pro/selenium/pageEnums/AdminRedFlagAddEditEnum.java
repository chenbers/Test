package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.interfaces.SeleniumEnums;

public enum AdminRedFlagAddEditEnum implements SeleniumEnums {
    
    TITLE("Red Flag Type", "//span[@class='admin']"),
    SAVE_TOP(save, "edit-form:editRedFlagSave1"),
    CANCEL_TOP(cancel, "edit-form:editRedFlagCancel1"),
    SAVE_BOTTOM(save, "edit-form:editRedFlagSave2"),
    CANCEL_BOTTOM(cancel, "edit-form:editRedFlagCancel2"),
    
    HEADER_TYPE("Red Flag Type", "//table[1]/tbody/tr/td[1]/div[@class='add_section_title']"),
    HEADER_NAME_DESCRIPTION_TYPE("Name, Description & Type", "//table[2]/tbody/tr/td[1]/div[@class='add_section_title']"),
    HEADER_ASSIGN_TO("Assign To *", "//table[2]/tbody/tr/td[3]/div[@class='add_section_title']"),
    HEADER_TYPE_SETTINGS("Driving Style", "//span/div[@class='add_section_title']"),
    HEADER_NOTIFICATIONS_BY_NAME("Notifications By Name", "//table[4]/tbody/tr/td[1]/div[@class='add_section_title']"),
    HEADER_PHONE_CALL_ESCALATION("Phone Call Escalation", "//table[4]/tbody/tr/td[3]/div[@class='add_section_title']"),
    
    TYPE(null, "edit-form:alertType"),
    
    NAME("Name:", "edit-form:editRedFlag-name"),
    DESCRIPTION("Description:", "edit-form:editRedFlag-description"),
    NOTIFICATION("Notification:", "edit-form:editRedFlag-severityLevel"),
    STATUS("Status:", "edit-form:redFlagAlertsStatus"),
    WHICH_DAYS("Which Days:", "//table[@class='days']"),
    TIME_FRAME_ANYTIME("Anytime", "edit-form:editRedFlag-timeframe:0"),
    TIME_FRAME_BETWEEN("Between", "edit-form:editRedFlag-timeframe:1"),
    
    ASSIGN("Assign:", "edit-form:editRedFlag-assignType"),
    FILTER("Filter:", "edit-form:editAlert-filterKeyword"),
    ASSIGN_TO_LEFT(null, "edit-form:editRedFlag-from"),
    ASSIGN_TO_RIGHT(null, "edit-form:editRedFlag-picked"),
    ALERT_OWNER("Alert Owner:", "edit-form:editRedFlag-userID"),
    ALERT_OWNER_NOTE("Select the name of the user who is the owner of this alert. The owner will be able to modify or delete the alert.", "//tbody/tr[2]/td/div[@class='instructions']"),
    
    MOVE_RIGHT(null, "edit-form:editRedFlag-move_right"),
    MOVE_LEFT(null, "edit-form:editRedFlag-move_left"),
    MOVE_ALL_RIGHT(null, "edit-form:editRedFlag-move_all_right"),
    MOVE_ALL_LEFT(null, "edit-form:editRedFlag-move_all_left"),
    
    NOTIFICATIONS_BY_NAME_NOTE("Type the names of the employees you would like to notify. Names will appear as you type. Select names from the list.", "//table[4]/tbody/tr/td[1]/div[@class='instructions']"),
    NOTIFICATIONS_BY_NAME(null, "edit-form:editRedFlag-userID"),
    NOTIFICATIONS_BY_NAME_SUGGESTION(null, "edit-form:editRedFlag-suggestionBoxId:0:nameSuggestion"),
    
    PHONE_CALL_NOTE("Type the names of the employees you would like to call. Names will appear as you type. Select names from list. Add one name per field in the order in which you would like them to be called. Finally add a person who will be emailed in the case of none of the calls being acknowledged.", "//td[3]/div[@class='instructions']"),
    PHONE_NUMBERS("Phone Numbers:", "edit-form:phNumbers:###:phNumInput"),
    PHONE_NUMBERS_SUGGESTION(null, "edit-form:phNumbers:###:suggestionBoxId:suggest"),
    PHONE_NUMBER_BUTTON(null, "edit-form:phNumbers:###:phNumInputBtns"),
    
    CALL_DELAY("Call Delay:", "edit-form:redFlagAlertsPhoneDelay"),
    LIMIT_BY_TYPE("Limit By:", "edit-form:redFlagAlertsLimitType"),
    LIMIT_BY_VALUE(null, "edit-form:redFlagAlertsLimitValue"),
    
    ESCALATION_EMAIL("Escalation " + email, "edit-form:escEmailAddressInput"),
    ESCALATION_EMAIL_SUGGESTION(null, "edit-form:suggestionBoxId:suggest"),
    ESCALATION_EMAIL_NOTE("Escalation Email will be sent when all the phone numbers have been called with no answer.", "//td[3]/div[@class='instructions'][2]"),
    
    
    STYLE_ACCEL_CHECK(null, "edit-form:hardAccelerationSelected"),
    STYLE_BRAKE_CHECK(null, "edit-form:hardBrakeSelected"),
    STYLE_TURN_CHECK(null, "edit-form:hardTurnSelected"),
    STYLE_BUMP_CHECK(null, "edit-form:hardVerticalSelected"),
    
    STYLE_ACCEL_CHECK_TEXT("Hard Accelerate", "//input[@id='edit-form:hardAccelerationSelected']/.."),
    STYLE_BRAKE_CHECK_TEXT("Hard Brake", "//input[@id='edit-form:hardBrakeSelected']/.."),
    STYLE_TURN_CHECK_TEXT("Unsafe Turn", "//input[@id='edit-form:hardTurnSelected']/.."),
    STYLE_BUMP_CHECK_TEXT("Hard Bump", "//input[@id='edit-form:hardVerticalSelected']/.."),
    
    STYLE_ACCEL_BAR(null, ""),
    STYLE_BRAKE_BAR(null, ""),
    STYLE_TURN_BAR(null, ""),
    STYLE_BUMP_BAR(null, ""),
    
    STYLE_ACCEL_TEXT(null, "edit-form:hardAccelerationLevel"),
    STYLE_BRAKE_TEXT(null, "edit-form:hardBrakeLevel"),
    STYLE_TURN_TEXT(null, "edit-form:hardTurnLevel"),
    STYLE_BUMP_TEXT(null, "edit-form:hardVerticalLevel"),
    
    SPEEDING_CHECK(null, "edit-form:speed###Selected"),
    SPEEDING_CHECK_TEXT("5 mph", "//input[@id='edit-form:speed###Selected']/.."),
    SPEEDING_BAR(null, "edit-form:speed###Handle"),
    SPEEDING_BOX(null, "edit-form:speed###Input"),
    
    SPEEDING_BUTTONS_LABEL("Adjust all:", "//a[@id='adjustAllMinus']/.."),
    SPEEDING_PLUS(null, "adjustAllPlus"),
    SPEEDING_MINUS(null, "adjustAllMinus"),
    
    COMPLIANCE_SEATBELT_CHECKBOXS(null, "edit-form:seatbeltSelected"),
    COMPLIANCE_SEATBELT_LABEL(null, "//input[@id='edit-form:seatbeltSelected']/.."),
    
    COMPLIANCE_NO_DRIVER_CHECKBOXS(null, "edit-form:noDriverSelected"),
    COMPLIANCE_NO_DRIVER_LABEL(null, "//input[@id='edit-form:noDriverSelected']/.."),
    
    COMPLIANCE_PARKING_BRAKE_CHECKBOXS(null, "edit-form:parkingBrakeSelected"),
    COMPLIANCE_PARKING_BRAKE_LABEL(null, "//input[@id='edit-form:parkingBrakeSelected']/.."),
    
    VEHICLE_TAMPERING(null, "edit-form:tamperingSelected"),
    VEHICLE_TAMPERING_LABEL("Tampering", "//input[@id='edit-form:tamperingSelected']/.."),
    
    VEHICLE_IGNITION_ON(null, "edit-form:ignitionOnSelected"),
    VEHICLE_IGNITION_ON_LABEL("Vehicle Ignition ON", "//input[@id='edit-form:ignitionOnSelected']/.."),
    
    VEHICLE_LOW_BATTERY(null, "edit-form:lowBatterySelected"),
    VEHICLE_LOW_BATTERY_LABEL("Vehicle battery is low", "//input[@id='edit-form:lowBatterySelected']/.."),
    
    VEHICLE_IDLING_LIMIT(null, "edit-form:idlingThresholdSelected"),
    VEHICLE_IDLING_LIMIT_LABEL("Idling Limit (minutes)", "//input[@id='edit-form:idlingThresholdSelected']/.."),
    VEHICLE_IDLING_SELECTOR(null, "edit-form:idlingThresholdHandle"),
    VEHICLE_IDLING_BOX(null, "edit-form:idlingThresholdInput"),
    
    EMERGENCY_PANIC(null, "edit-form:panicSelected"),
    EMERGENCY_PANIC_LABEL("Panic button", "//input[@id='edit-form:panicSelected']/.."),
    
    EMERGENCY_LONE_WORKER(null, "edit-form:automaticManDownSelected"),
    EMERGENCY_LONE_WORKER_LABEL("Lone worker alarm", "//input[@id='edit-form:automaticManDownSelected']/.."),
    
    EMERGENCY_LONE_WORKER_CANCELED(null, "edit-form:automaticManDownOKSelected"),
    EMERGENCY_LONE_WORKER_CANCELED_LABEL("Lone worker alarm - worker OK or alarm canceled", "//input[@id='edit-form:automaticManDownOKSelected']/.."),
    
    EMERGENCY_CRASH(null, "edit-form:crashSelected"),
    EMERGENCY_CRASH_LABEL("Crash", "//input[@id='edit-form:crashSelected']/.."),
    
    HOS_STOPPED_BY_OFFICER(null, "edit-form:dotStoppedSelected"),
    HOS_STOPPED_BY_OFFICER_LABEL("", "//input[@id='edit-form:dotStoppedSelected']/.."),
    
    HOS_NO_HOURS_REMAINING(null, "edit-form:noHoursSelected"),
    HOS_NO_HOURS_REMAINING_LABEL("", "//input[@id='edit-form:noHoursSelected']/.."),
    
    
    FATIGUE_NOTE("Note: Red flags for Fatigue only work in properly equipped units.", "//span[@id='edit-form:editRedFlag-fatigue']/div[2]"),
    FATIGUE_MICROSLEEP("Driver in microsleep", "//span[@id='edit-form:editRedFlag-fatigue']/table/tbody/tr[2]/td"),
    
    WIRELINE_MICROSLEEP("Driver in microsleep", "//span[@id='edit-form:editRedFlag-wireline']/table/tbody/tr[2]/td"),
    
    INSTALLATION_NEW(null, "edit-form:installSelected"),
    INSTALLATION_NEW_LABEL("New Installation", "//input[@id='edit-form:installSelected']/.."),
    
    INSTALLATION_FIRMWARE_UPDATED(null, "edit-form:firmwareCurrentSelected"),
    INSTALLATION_FIRMWARE_UPDATED_LABEL("The device firmware is updated", "//input[@id='edit-form:firmwareCurrentSelected']/.."),
    
    INSTALLATION_MANUAL_LOCATION(null, "edit-form:locationDebugSelected"),
    INSTALLATION_MANUAL_LOCATION_LABEL("Manual location command", "//input[@id='edit-form:locationDebugSelected']/.."),
    
    INSTALLATION_CANNOT_MOUNT_INTERNAL_STORAGE(null, "edit-form:noThumbDriveSelected"),
    INSTALLATION_CANNOT_MOUNT_INTERNAL_STORAGE_LABEL("The device cannot mount the internal storage", "//input[@id='edit-form:noThumbDriveSelected']/.."),
    
    INSTALLATION_QSI_FIRMWARE_UPDATED(null, "edit-form:qsiUpdatedSelected"),
    INSTALLATION_QSI_FIRMWARE_UPDATED_LABEL("QSI firmware has been updated", "//input[@id='edit-form:qsiUpdatedSelected']/.."),
    
    INSTALLATION_CANT_GET_HEARTBEAT(null, "edit-form:witnessHeartbeatViolationSelected"),
    INSTALLATION_CANT_GET_HEARTBEAT_LABEL("The device can't get a heartbeat message from the crash detector", "//input[@id='edit-form:witnessHeartbeatViolationSelected']/.."),
    
    INSTALLATION_CRASH_FIRMWARE_UPDATED(null, "edit-form:witnessUpdatedSelected"),
    INSTALLATION_CRASH_FIRMWARE_UPDATED_LABEL("The crash detector firmware has been updated", "//input[@id='edit-form:witnessUpdatedSelected']/.."),
    
    INSTALLATION_DOWNLOADED_ZONES(null, "edit-form:zonesCurrentSelected"),
    INSTALLATION_DOWNLOADED_ZONES_LABEL("The device successfully downloaded the latest zones", "//input[@id='edit-form:zonesCurrentSelected']/.."),
    
    TEXT_MESSAGE("Text message received", "//span[@id='edit-form:editRedFlag-textMessage']/table/tbody/tr[2]/td"),
    
    ZONES_ZONE("Zone:", "edit-form:editZoneAlert-zoneID"),
    
    ZONES_ARRIVAL(null, "edit-form:editZoneAlert-arrival"),
    ZONES_ARRIVAL_LABEL("Report on Arrival", "//input[@id='edit-form:editZoneAlert-arrival']/.."),
    
    ZONES_DEPARTURE(null, "edit-form:editZoneAlert-departure"),
    ZONES_DEPARTURE_LABEL("Report on Departure", "//input[@id='edit-form:editZoneAlert-departure']/.."),
    
    
    ;
    private String text, url;
    private String[] IDs;

    private AdminRedFlagAddEditEnum(String url) {
        this.url = url;
    }

    private AdminRedFlagAddEditEnum(String text, String... IDs) {
        this.text = text;
        this.IDs = IDs;
    }

    @Override
    public String[] getIDs() {
        return IDs;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public String getURL() {
        return url;
    }

}
