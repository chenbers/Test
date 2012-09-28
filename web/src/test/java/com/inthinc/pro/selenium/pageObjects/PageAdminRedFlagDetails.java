package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.CheckBox;
import com.inthinc.pro.automation.elements.Text;
import com.inthinc.pro.automation.elements.TextButton;
import com.inthinc.pro.automation.elements.TextLabel;
import com.inthinc.pro.automation.elements.TextLink;
import com.inthinc.pro.automation.elements.TextTable;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.selenium.pageEnums.AdminRedFlagsDetailsEnum;
import com.inthinc.pro.selenium.pageEnums.RedFlagGenericDetails;

public class PageAdminRedFlagDetails extends AdminBar {

    public PageAdminRedFlagDetails() {
        checkMe.add(AdminRedFlagsDetailsEnum.DELETE);
        checkMe.add(AdminRedFlagsDetailsEnum.EDIT);
        checkMe.add(AdminRedFlagsDetailsEnum.BACK_TO_RED_FLAGS);
    }

    public class AdminRedFlagDetailsLinks extends AdminBarLinks {

        public TextLink backToRedFlags() {
            return new TextLink(AdminRedFlagsDetailsEnum.BACK_TO_RED_FLAGS);
        }
    }

    public class AdminRedFlagDetailsTexts extends AdminBarTexts {

        public Text valueTitle() {
            return new Text(AdminRedFlagsDetailsEnum.TITLE);
        }

        public Text headerNameDescriptionType() {
            return new Text(
                    AdminRedFlagsDetailsEnum.NAME_DESCRIPTION_TYPE_HEADER);
        }

        public Text headerAssignTo() {
            return new Text(AdminRedFlagsDetailsEnum.ASSIGN_TO_HEADER);
        }

        public Text headerRedFlagType() {
            return new Text(AdminRedFlagsDetailsEnum.RED_FLAG_TYPE_HEADER);
        }

        public Text headerNotificationsByName() {
            return new Text(
                    AdminRedFlagsDetailsEnum.NOTIFICATIONS_BY_NAME_HEADER);
        }

        public Text headerPhoneCallEscalation() {
            return new Text(
                    AdminRedFlagsDetailsEnum.PHONE_CALL_ESCALATION_HEADER);
        }

        public Text valueName() {
            return new Text(AdminRedFlagsDetailsEnum.NAME);
        }

        public Text valueDescription() {
            return new Text(AdminRedFlagsDetailsEnum.DESCRIPTION);
        }

        public Text valueStatus() {
            return new Text(AdminRedFlagsDetailsEnum.STATUS);
        }

        public Text valueWhichDaysDays() {
            return new Text(AdminRedFlagsDetailsEnum.WHICH_DAYS_DAYS);
        }

        public Text labelWhichDays() {
            return new Text(AdminRedFlagsDetailsEnum.WHICH_DAYS_LABEL);
        }

        public Text valueTimeFrame() {
            return new Text(AdminRedFlagsDetailsEnum.TIMEFRAME);
        }

        public Text valueType() {
            return new Text(AdminRedFlagsDetailsEnum.TYPE);
        }

        public Text valueNotification() {
            return new Text(AdminRedFlagsDetailsEnum.NOTIFICATION);
        }

        public Text valueAlertGroups() {
            return new Text(AdminRedFlagsDetailsEnum.ALERT_GROUPS);
        }

        public Text valueAlertOwner() {
            return new Text(AdminRedFlagsDetailsEnum.ALERT_OWNER);
        }

        public TextTable valueNotificationsByNameList() {
            return new TextTable(
                    AdminRedFlagsDetailsEnum.NOTIFICATIONS_BY_NAME_LIST);
        }

        public TextTable valuePhoneCallEscalationNumberList() {
            return new TextTable(
                    AdminRedFlagsDetailsEnum.PHONE_CALL_ESCALATION_NUMBER_LIST);
        }

        public TextTable valuePhoneCallEscalationEmail() {
            return new TextTable(
                    AdminRedFlagsDetailsEnum.PHONE_CALL_ESCALATION_EMAIL);
        }

        public Text valueCallDelay() {
            return new Text(AdminRedFlagsDetailsEnum.CALL_DELAY);
        }

        public Text valueLimitBy() {
            return new Text(AdminRedFlagsDetailsEnum.LIMIT_BY);
        }

        public Text labelName() {
            return new TextLabel(AdminRedFlagsDetailsEnum.NAME);
        }

        public Text labelDescription() {
            return new TextLabel(AdminRedFlagsDetailsEnum.DESCRIPTION);
        }

        public Text labelStatus() {
            return new TextLabel(AdminRedFlagsDetailsEnum.STATUS);
        }

        public Text labelTimeFrame() {
            return new TextLabel(AdminRedFlagsDetailsEnum.TIMEFRAME);
        }

        public Text labelType() {
            return new TextLabel(AdminRedFlagsDetailsEnum.TYPE);
        }

        public Text labelNotification() {
            return new TextLabel(AdminRedFlagsDetailsEnum.NOTIFICATION);
        }

        public Text labelalertowner() {
            return new TextLabel(AdminRedFlagsDetailsEnum.ALERT_OWNER);
        }

        public Text labelCallDelay() {
            return new TextLabel(AdminRedFlagsDetailsEnum.CALL_DELAY);
        }

        public Text labelLimitBy() {
            return new TextLabel(AdminRedFlagsDetailsEnum.LIMIT_BY);
        }

        public Text detailsHeaderSetting() {
            return new Text(RedFlagGenericDetails.SETTING_HEADER);
        }

        public TextTable detailsEntrySetting() {
            return new TextTable(RedFlagGenericDetails.SETTING_ENTRY);
        }

        public Text detailsVehicleSettingtTampering() {
            return new Text(RedFlagGenericDetails.VEHICLE_SETTING_TAMPERING);
        }

        public Text detailsVehicleSettingIgnitionon() {
            return new Text(RedFlagGenericDetails.VEHICLE_SETTING_IGNITION_ON);
        }

        public Text detailsVehicleSettingLowBattery() {
            return new Text(RedFlagGenericDetails.VEHICLE_SETTING_LOW_BATTERY);
        }

        public Text detailsLabelVehicleSettingIdling() {
            return new Text(RedFlagGenericDetails.VEHICLE_SETTING_IDLING_LABEL);
        }

        public Text detailsVehicleSettingIdlingSlider() {
            return new Text(RedFlagGenericDetails.VEHICLE_SETTING_IDLING_SLIDER);
        }

        public Text detailsVehicleSettingIdlingValue() {
            return new Text(RedFlagGenericDetails.VEHICLE_SETTING_IDLING_VALUE);
        }

        public Text detailsHeaderSpeedingLimit() {
            return new Text(RedFlagGenericDetails.SPEEDING_LIMIT_HEADER);
        }

        public Text detailsHeaderSpeedingExceedingBy() {
            return new Text(RedFlagGenericDetails.SPEEDING_EXCEEDING_BY_HEADER);
        }

        public Text detailsHeaderSpeedingNotification() {
            return new Text(RedFlagGenericDetails.SPEEDING_NOTIFICATION_HEADER);
        }

        public Text detailsLabelSpeedingLimit() {
            return new Text(RedFlagGenericDetails.SPEEDING_LIMIT_LABEL);
        }

        public Text detailsSpeedingLimitSlider() {
            return new Text(RedFlagGenericDetails.SPEEDING_LIMIT_SLIDER);
        }

        public Text detailsSpeedingLimitValue() {
            return new Text(RedFlagGenericDetails.SPEEDING_LIMIT_VALUE);
        }

        public Text detailsHeaderStyleSetting() {
            return new Text(RedFlagGenericDetails.STYLE_SETTING_HEADER);
        }

        public Text detailsHeaderStyleSeverity() {
            return new Text(RedFlagGenericDetails.STYLE_SEVERITY_HEADER);
        }

        public Text detailsStyleHardAccelerate() {
            return new Text(RedFlagGenericDetails.STYLE_HARD_ACCELERATE);
        }

        public Text detailsStyleHardBrake() {
            return new Text(RedFlagGenericDetails.STYLE_HARD_BRAKE);
        }

        public Text detailsStyleUnsafeTurn() {
            return new Text(RedFlagGenericDetails.STYLE_UNSAFE_TURN);
        }

        public Text detailsStyleHardBump() {
            return new Text(RedFlagGenericDetails.STYLE_HARD_BUMP);
        }

        public Text detailsStyleHardAccelerateSlider() {
            return new Text(
                    RedFlagGenericDetails.STYLE_HARD_ACCELERATE_SLIDER_POSITION);
        }

        public Text detailsStyleHardBrakeSlider() {
            return new Text(
                    RedFlagGenericDetails.STYLE_HARD_BRAKE_SLIDER_POSITION);
        }

        public Text detailsStyleUnsafeTurnSlider() {
            return new Text(
                    RedFlagGenericDetails.STYLE_UNSAFE_TURN_SLIDER_POSITION);
        }

        public Text detailsStyleHardBumpSlider() {
            return new Text(
                    RedFlagGenericDetails.STYLE_HARD_BUMP_SLIDER_POSITION);
        }

        public Text detailsStyleHardAccelerateSeverity() {
            return new Text(
                    RedFlagGenericDetails.STYLE_HARD_ACCELERATE_SEVERITY);
        }

        public Text detailsStyleHardBrakeSeverity() {
            return new Text(RedFlagGenericDetails.STYLE_HARD_BRAKE_SEVERITY);
        }

        public Text detailsStyleUnsafeTurnSeverity() {
            return new Text(RedFlagGenericDetails.STYLE_UNSAFE_TURN_SEVERITY);
        }

        public Text detailsStyleHardBumpSeverity() {
            return new Text(RedFlagGenericDetails.STYLE_HARD_BUMP_SEVERITY);
        }

    }

    public class AdminRedFlagDetailsTextFields extends AdminBarTextFields {}

    public class AdminRedFlagDetailsButtons extends AdminBarButtons {
        
        public TextButton edit(){
            return new TextButton(AdminRedFlagsDetailsEnum.EDIT);
        }
        
        public TextButton delete(){
            return new TextButton(AdminRedFlagsDetailsEnum.DELETE);
        }
    }

    public class AdminRedFlagDetailsDropDowns extends AdminBarDropDowns {}

    public class AdminRedFlagDetailsPopUps extends MastheadPopUps {}

    public class AdminRedFlagsCheckBoxs {

        public CheckBox whichDaysCheckBox() {
            return new CheckBox(AdminRedFlagsDetailsEnum.WHICH_DAYS_CHECK_BOX);
        }
    }

    public AdminRedFlagsCheckBoxs _checkBox() {
        return new AdminRedFlagsCheckBoxs();
    }

    public class AdminRedFlagDetailsPager {
        public Paging pageIndex() {
            return new Paging();
        }
    }

    public AdminRedFlagDetailsPager _page() {
        return new AdminRedFlagDetailsPager();
    }

    public AdminRedFlagDetailsLinks _link() {
        return new AdminRedFlagDetailsLinks();
    }

    public AdminRedFlagDetailsTexts _text() {
        return new AdminRedFlagDetailsTexts();
    }

    public AdminRedFlagDetailsButtons _button() {
        return new AdminRedFlagDetailsButtons();
    }

    public AdminRedFlagDetailsTextFields _textField() {
        return new AdminRedFlagDetailsTextFields();
    }

    public AdminRedFlagDetailsDropDowns _dropDown() {
        return new AdminRedFlagDetailsDropDowns();
    }

    public AdminRedFlagDetailsPopUps _popUp() {
        return new AdminRedFlagDetailsPopUps();
    }

    @Override
    public SeleniumEnums setUrl() {
        return AdminRedFlagsDetailsEnum.URL;
    }

    @Override
    protected boolean checkIsOnPage() {
        return _button().edit().isPresent() && _text().labelName().isPresent();
    }
}
