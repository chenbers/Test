package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.Text;
import com.inthinc.pro.automation.elements.TextButton;
import com.inthinc.pro.automation.elements.TextLabel;
import com.inthinc.pro.automation.elements.TextLink;
import com.inthinc.pro.selenium.pageEnums.AdminDevicesDetailsEnum;

public class PageAdminDevicesDetails extends AdminBar {

    public class AdminDeviceDetailsButtons extends AdminBarButtons {

        public TextButton edit() {
            return new TextButton(AdminDevicesDetailsEnum.EDIT_BUTTON);
        }
    }

    public class AdminDeviceDetailsDropDowns extends AdminBarDropDowns {}

    public class AdminDeviceDetailsLinks extends AdminBarLinks {
        public TextLink backToDevices() {
            return new TextLink(AdminDevicesDetailsEnum.BACK_TO_DEVICES);
        }
    }

    public class AdminDeviceDetailsPager {
        public Paging pageIndex() {
            return new Paging();
        }
    }

    public class AdminDeviceDetailsPopUps extends MastheadPopUps {}

    public class AdminDeviceDetailsTextFields extends AdminBarTextFields {}

    public class AdminDeviceDetailsTexts extends AdminBarTexts {
        public TextLabel labelAlternateIMEI() {
            return new TextLabel(AdminDevicesDetailsEnum.ALTERNATE_IMEI);
        }

        public TextLabel labelAssignedVehicle() {
            return new TextLabel(AdminDevicesDetailsEnum.ASSIGNED_VEHICLE);
        }

        public TextLabel labelDeviceID() {
            return new TextLabel(AdminDevicesDetailsEnum.DEVICE_ID);
        }

        public TextLabel labelDevicePhone() {
            return new TextLabel(AdminDevicesDetailsEnum.DEVICE_PHONE);
        }

        public TextLabel labelFirmwareVersion() {
            return new TextLabel(AdminDevicesDetailsEnum.FIRMWARE_VERSION_DATE);
        }

        public TextLabel labelIMEI() {
            return new TextLabel(AdminDevicesDetailsEnum.IMEI);
        }

        public TextLabel labelMCMID() {
            return new TextLabel(AdminDevicesDetailsEnum.MCM_ID);
        }

        public TextLabel labelProductType() {
            return new TextLabel(AdminDevicesDetailsEnum.PRODUCT_TYPE);
        }

        public TextLabel labelSatIMEI() {
            return new TextLabel(AdminDevicesDetailsEnum.SAT_IMEI);
        }

        public TextLabel labelSerialNumber() {
            return new TextLabel(AdminDevicesDetailsEnum.SERIAL_NUMBER);
        }

        public TextLabel labelSimCard() {
            return new TextLabel(AdminDevicesDetailsEnum.SIM_CARD);
        }

        public TextLabel labelStatus() {
            return new TextLabel(AdminDevicesDetailsEnum.STATUS);
        }

        public TextLabel labelWitnessVersion() {
            return new TextLabel(AdminDevicesDetailsEnum.WITNESS_VERSION);
        }

        public Text title() {
            return new Text(AdminDevicesDetailsEnum.TITLE);
        }

        public Text titleDeviceAssignment() {
            return new Text(AdminDevicesDetailsEnum.DEVICE_ASSIGNMENT);
        }

        public Text titleDeviceInformation() {
            return new Text(AdminDevicesDetailsEnum.DEVICE_INFORMATION);
        }

        public Text titleDeviceProfile() {
            return new Text(AdminDevicesDetailsEnum.DEVICE_PROFILE);
        }

        public Text valueAlternateIMEI() {
            return new Text(AdminDevicesDetailsEnum.ALTERNATE_IMEI);
        }

        public Text valueAssignedVehicle() {
            return new Text(AdminDevicesDetailsEnum.ASSIGNED_VEHICLE);
        }

        public Text valueDeviceID() {
            return new Text(AdminDevicesDetailsEnum.DEVICE_ID);
        }

        public Text valueDevicePhone() {
            return new Text(AdminDevicesDetailsEnum.DEVICE_PHONE);
        }

        public Text valueFirmwareVersion() {
            return new Text(AdminDevicesDetailsEnum.FIRMWARE_VERSION_DATE);
        }

        public Text valueIMEI() {
            return new Text(AdminDevicesDetailsEnum.IMEI);
        }

        public Text valueMCMID() {
            return new Text(AdminDevicesDetailsEnum.MCM_ID);
        }

        public Text valueProductType() {
            return new Text(AdminDevicesDetailsEnum.PRODUCT_TYPE);
        }

        public Text valueSatIMEI() {
            return new Text(AdminDevicesDetailsEnum.SAT_IMEI);
        }

        public Text valueSerialNumber() {
            return new Text(AdminDevicesDetailsEnum.SERIAL_NUMBER);
        }

        public Text valueSimCard() {
            return new Text(AdminDevicesDetailsEnum.SIM_CARD);
        }

        public Text valueStatus() {
            return new Text(AdminDevicesDetailsEnum.STATUS);
        }

        public Text valueWitnessVersion() {
            return new Text(AdminDevicesDetailsEnum.WITNESS_VERSION);
        }

    }

    public PageAdminDevicesDetails() {
        checkMe.add(AdminDevicesDetailsEnum.BACK_TO_DEVICES);
        checkMe.add(AdminDevicesDetailsEnum.TITLE);
        checkMe.add(AdminDevicesDetailsEnum.EDIT_BUTTON);
    }

    public AdminDeviceDetailsButtons _button() {
        return new AdminDeviceDetailsButtons();
    }

    public AdminDeviceDetailsDropDowns _dropDown() {
        return new AdminDeviceDetailsDropDowns();
    }

    public AdminDeviceDetailsLinks _link() {
        return new AdminDeviceDetailsLinks();
    }

    public AdminDeviceDetailsPager _page() {
        return new AdminDeviceDetailsPager();
    }

    public AdminDeviceDetailsPopUps _popUp() {
        return new AdminDeviceDetailsPopUps();
    }

    public AdminDeviceDetailsTexts _text() {
        return new AdminDeviceDetailsTexts();
    }

    public AdminDeviceDetailsTextFields _textField() {
        return new AdminDeviceDetailsTextFields();
    }

}
