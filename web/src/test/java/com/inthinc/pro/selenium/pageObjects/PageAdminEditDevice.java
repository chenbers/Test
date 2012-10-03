package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.Button;
import com.inthinc.pro.automation.elements.DHXDropDown;
import com.inthinc.pro.automation.elements.SortHeader;
import com.inthinc.pro.automation.elements.Text;
import com.inthinc.pro.automation.elements.TextButton;
import com.inthinc.pro.automation.elements.TextFieldLabel;
import com.inthinc.pro.automation.elements.TextLabel;
import com.inthinc.pro.automation.elements.TextLink;
import com.inthinc.pro.automation.elements.TextTable;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.selenium.pageEnums.AdminDevicesEditEnum;

public class PageAdminEditDevice extends AdminBar {

    public PageAdminEditDevice() {
        checkMe.add(AdminDevicesEditEnum.TOP_SAVE);
        checkMe.add(AdminDevicesEditEnum.TOP_CANCEL);
    }
    

    @Override
    public SeleniumEnums setUrl() {
        return AdminDevicesEditEnum.URL;
    }

    public class AdminEditDeviceLinks extends AdminBarLinks {

        public TextLink showHideVehiclesForAssignment() {
            return new TextLink(AdminDevicesEditEnum.SHOW_HIDE_VEHICLES_LINK);
        }
        
        public TextLink assigned() {
        	return new TextLink(AdminDevicesEditEnum.ASSIGNED_LINK);
        }
    }

    public class AdminEditDeviceTexts extends AdminBarTexts {

        public Text valueProduct() {
            return new Text(AdminDevicesEditEnum.PRODUCT);
        }

        public Text valueDeviceID() {
            return new Text(AdminDevicesEditEnum.DEVICE_ID);
        }

        public Text valueSerialNumber() {
            return new Text(AdminDevicesEditEnum.SERIAL_NUMBER);
        }

        public Text valueIMEI() {
            return new Text(AdminDevicesEditEnum.IMEI);
        }

        public Text valueSIMCard() {
            return new Text(AdminDevicesEditEnum.SIM_CARD);
        }

        public Text valueDevicePhone() {
            return new Text(AdminDevicesEditEnum.DEVICE_PHONE);
        }

        public Text valueMCMID() {
            return new Text(AdminDevicesEditEnum.MCM_ID);
        }

        public Text valueAlternateIMEI() {
            return new Text(AdminDevicesEditEnum.ALTERNATE_IMEI);
        }

        public Text valueWitnessVersion() {
            return new Text(AdminDevicesEditEnum.WITNESS_VERSION);
        }

        public Text valueFirmwareVersionDate() {
            return new Text(AdminDevicesEditEnum.FIRMWARE_VERSION_DATE);
        }

        public Text valueSatIMEI() {
            return new Text(AdminDevicesEditEnum.SAT_IMEI);
        }

        public Text valueAssignedVehicle() {
            return new Text(AdminDevicesEditEnum.ASSIGNED_VEHICLE);
        }

        public Text labelProduct() {
            return new TextLabel(AdminDevicesEditEnum.PRODUCT);
        }

        public Text labelDeviceID() {
            return new TextLabel(AdminDevicesEditEnum.DEVICE_ID);
        }

        public Text labelSerialNumber() {
            return new TextLabel(AdminDevicesEditEnum.SERIAL_NUMBER);
        }

        public Text labelIMEI() {
            return new TextLabel(AdminDevicesEditEnum.IMEI);
        }

        public Text labelSIMCard() {
            return new TextLabel(AdminDevicesEditEnum.SIM_CARD);
        }

        public Text labelDevicePhone() {
            return new TextLabel(AdminDevicesEditEnum.DEVICE_PHONE);
        }

        public Text labelMCMID() {
            return new TextLabel(AdminDevicesEditEnum.MCM_ID);
        }

        public Text labelAlternateIMEI() {
            return new TextLabel(AdminDevicesEditEnum.ALTERNATE_IMEI);
        }

        public Text labelWitnessVersion() {
            return new TextLabel(AdminDevicesEditEnum.WITNESS_VERSION);
        }

        public Text labelFirmwareVersionDate() {
            return new TextLabel(AdminDevicesEditEnum.FIRMWARE_VERSION_DATE);
        }

        public Text labelSatIMEI() {
            return new TextLabel(AdminDevicesEditEnum.SAT_IMEI);
        }

        public Text labelAssignedVehicle() {
            return new Text(AdminDevicesEditEnum.ASSIGNED_VEHICLE_LABEL);
        }

        public Text headerInformation() {
            return new Text(AdminDevicesEditEnum.INFORMATION_HEADER);
        }

        public Text headerProfile() {
            return new Text(AdminDevicesEditEnum.PROFILE_HEADER);
        }

        public Text headerAssignment() {
            return new Text(AdminDevicesEditEnum.ASSIGNMENT_HEADER);
        }

        public Text labelStatus() {
            return new TextFieldLabel(AdminDevicesEditEnum.STATUS);
        }

        public Text title() {
            return new Text(AdminDevicesEditEnum.TITLE);
        }

        public Text details() {
            return new Text(AdminDevicesEditEnum.DETAILS_TAB);
        }

        public TextTable vehicleTableVehicleID() {
            return new TextTable(AdminDevicesEditEnum.VEHICLE_TABLE_VEHICLE_ID);
        }

        public TextTable vehicleTableSelect() {
            return new TextTable(AdminDevicesEditEnum.VEHICLE_TABLE_SELECT);
        }

        public TextTable vehicleTableDriver() {
            return new TextTable(AdminDevicesEditEnum.VEHICLE_TABLE_DRIVER);
        }

        public TextTable vehicleTabletTeam() {
            return new TextTable(AdminDevicesEditEnum.VEHICLE_TABLE_TEAM);
        }

        public TextTable vehicleTableStatus() {
            return new TextTable(AdminDevicesEditEnum.VEHICLE_TABLE_STATUS);
        }

        public TextTable vehicleTableAssigned() {
            return new TextTable(AdminDevicesEditEnum.VEHICLE_TABLE_ASSIGNED);
        }

        public TextTable vehicleTableProduct() {
            return new TextTable(AdminDevicesEditEnum.VEHICLE_TABLE_PRODUCT);
        }

        public TextTable vehicleTableDevice() {
            return new TextTable(AdminDevicesEditEnum.VEHICLE_TABLE_DEVICE);
        }

        public TextTable vehicleTableVIN() {
            return new TextTable(AdminDevicesEditEnum.VEHICLE_TABLE_VIN);
        }

        public TextTable vehicleTableYear() {
            return new TextTable(AdminDevicesEditEnum.VEHICLE_TABLE_YEAR);
        }

        public TextTable vehicleTableMake() {
            return new TextTable(AdminDevicesEditEnum.VEHICLE_TABLE_MAKE);
        }

        public TextTable vehicleTableModel() {
            return new TextTable(AdminDevicesEditEnum.VEHICLE_TABLE_MODEL);
        }
    }

    public class AdminEditDeviceTextFields extends AdminBarTextFields {}

    public class AdminEditDeviceButtons extends AdminBarButtons {
        
        public TextButton saveTop(){
            return new TextButton(AdminDevicesEditEnum.TOP_SAVE);
        }
        
        public TextButton saveBottom(){
            return new TextButton(AdminDevicesEditEnum.BOTTOM_SAVE);
        }
        
        public TextButton cancelTop(){
            return new TextButton(AdminDevicesEditEnum.TOP_CANCEL);
        }
        
        public TextButton cancelBottom(){
            return new TextButton(AdminDevicesEditEnum.BOTTOM_CANCEL);
        }

        public Button vehicleTableEntrySelector() {
            return new Button(AdminDevicesEditEnum.VEHICLE_TABLE_SELECT);
        }

        public TextButton vehicleTableSortByVehicleID() {
            return new SortHeader(AdminDevicesEditEnum.VEHICLE_TABLE_VEHICLE_ID);
        }

        public TextButton vehicleTableSortByDriver() {
            return new SortHeader(AdminDevicesEditEnum.VEHICLE_TABLE_DRIVER);
        }

        public TextButton vehicleTableSortByTeam() {
            return new SortHeader(AdminDevicesEditEnum.VEHICLE_TABLE_TEAM);
        }

        public TextButton vehicleTableSortByStatus() {
            return new SortHeader(AdminDevicesEditEnum.VEHICLE_TABLE_STATUS);
        }

        public TextButton vehicleTableSortByAssigned() {
            return new SortHeader(AdminDevicesEditEnum.VEHICLE_TABLE_ASSIGNED);
        }

        public TextButton vehicleTableSortByProduct() {
            return new SortHeader(AdminDevicesEditEnum.VEHICLE_TABLE_PRODUCT);
        }

        public TextButton vehicleTableSortByDevice() {
            return new SortHeader(AdminDevicesEditEnum.VEHICLE_TABLE_DEVICE);
        }

        public TextButton vehicleTableSortByVIN() {
            return new SortHeader(AdminDevicesEditEnum.VEHICLE_TABLE_VIN);
        }

        public TextButton vehicleTableSortByYear() {
            return new SortHeader(AdminDevicesEditEnum.VEHICLE_TABLE_YEAR);
        }

        public TextButton vehicleTableSortByMake() {
            return new SortHeader(AdminDevicesEditEnum.VEHICLE_TABLE_MAKE);
        }

        public TextButton vehicleTableSortByModel() {
            return new SortHeader(AdminDevicesEditEnum.VEHICLE_TABLE_MODEL);
        }

    }

    public class AdminEditDeviceDropDowns extends AdminBarDropDowns {
        private final AdminDevicesEditEnum[] enumerated = {
                AdminDevicesEditEnum.VEHICLE_TABLE_PRODUCT_FILTER,
                AdminDevicesEditEnum.VEHICLE_TABLE_STATUS_FILTER };

        public DHXDropDown vehicleTableStatusFilter() {
            return new DHXDropDown(
                    AdminDevicesEditEnum.VEHICLE_TABLE_STATUS_FILTER,
                    enumerated);
        }

        public DHXDropDown vehicleTableProductFilter() {
            return new DHXDropDown(
                    AdminDevicesEditEnum.VEHICLE_TABLE_PRODUCT_FILTER,
                    enumerated);
        }
    }

    public class AdminEditDevicePopUps extends MastheadPopUps {}

    public class AdminEditDevicePager {
        public Paging pageIndex() {
            return new Paging();
        }
    }

    public AdminEditDevicePager _page() {
        return new AdminEditDevicePager();
    }

    public AdminEditDeviceLinks _link() {
        return new AdminEditDeviceLinks();
    }

    public AdminEditDeviceTexts _text() {
        return new AdminEditDeviceTexts();
    }

    public AdminEditDeviceButtons _button() {
        return new AdminEditDeviceButtons();
    }

    public AdminEditDeviceTextFields _textField() {
        return new AdminEditDeviceTextFields();
    }

    public AdminEditDeviceDropDowns _dropDown() {
        return new AdminEditDeviceDropDowns();
    }

    public AdminEditDevicePopUps _popUp() {
        return new AdminEditDevicePopUps();
    }


    @Override
    protected boolean checkIsOnPage() {
        return _link().showHideVehiclesForAssignment().isPresent() && _button().cancelBottom().isPresent();
    }
}
