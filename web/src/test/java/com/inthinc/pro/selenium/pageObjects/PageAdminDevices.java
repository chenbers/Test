package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.DHXDropDown;
import com.inthinc.pro.automation.elements.DropDown;
import com.inthinc.pro.automation.elements.TextLink;
import com.inthinc.pro.automation.elements.TextTable;
import com.inthinc.pro.automation.elements.TextTableLink;
import com.inthinc.pro.selenium.pageEnums.AdminBarEnum;
import com.inthinc.pro.selenium.pageEnums.AdminDevicesEnum;
import com.inthinc.pro.selenium.pageEnums.AdminTables.DeviceColumns;

public class PageAdminDevices extends AdminTables {
    

    private static final AdminDevicesEnum[] enums = { AdminDevicesEnum.STATUS_DHX, AdminDevicesEnum.PRODUCT_DHX };
    
    public PageAdminDevices() {
        page = "devices";
        url = AdminDevicesEnum.DEFAULT_URL;
        checkMe.add(AdminDevicesEnum.TITLE);
        
    }

    
    public class AdminDevicesLinks extends AdminTablesLinks{


        public TextTableLink entryDeviceID() {
            return new TextTableLink(AdminBarEnum.TABLE_ENTRIES, page, DeviceColumns.DEVICE_ID);
        }

        public TextLink sortByColumn(DeviceColumns column) {
            return new TextLink(AdminBarEnum.TABLE_HEADERS, page, column);
        }

    }
    public class AdminDevicesTexts extends AdminTablesTexts{

        public TextTable tableEntry(DeviceColumns column) {
            return new TextTable(AdminBarEnum.TABLE_ENTRIES, page, column);
        }
        
    }
    
    public class AdminDevicesTextFields extends AdminTablesTextFields{

    }
    
    public class AdminDevicesButtons extends AdminTablesButtons{
        
    }
    public class AdminDevicesDropDowns extends AdminTablesDropDowns{
        
        public DropDown filterByProductType() {
            return new DHXDropDown(AdminDevicesEnum.PRODUCT_DHX, enums);
        }

        public DropDown filterByStatus() {
            return new DHXDropDown(AdminDevicesEnum.STATUS_DHX);
        }
    }
    public class AdminDevicesPopUps extends MastheadPopUps{
        
        public AdminDevicesPopUps(){
            super(page);
        }
        
        public EditColumns editColumns(){
            return new EditColumns();
        }
        
    }
    
    
    public class AdminDevicesPager{
        public Paging pageIndex(){
            return new Paging(AdminDevicesEnum.PAGE_SCROLLER);
        }
    }
    
    
    public AdminDevicesPager _page(){
        return new AdminDevicesPager();
    }
    
    public AdminDevicesLinks _link(){
        return new AdminDevicesLinks();
    }
    
    public AdminDevicesTexts _text(){
        return new AdminDevicesTexts();
    }
        
    public AdminDevicesButtons _button(){
        return new AdminDevicesButtons();
    }
    
    public AdminDevicesTextFields _textField(){
        return new AdminDevicesTextFields();
    }
    
    public AdminDevicesDropDowns _dropDown(){
        return new AdminDevicesDropDowns();
    }
    
    public AdminDevicesPopUps _popUp(){
        return new AdminDevicesPopUps();
    }
    
    
}
