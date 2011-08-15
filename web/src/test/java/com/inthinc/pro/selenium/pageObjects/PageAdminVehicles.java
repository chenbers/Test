package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.DhxDropDown;
import com.inthinc.pro.automation.elements.DropDown;
import com.inthinc.pro.automation.elements.TextButton;
import com.inthinc.pro.automation.elements.TextLink;
import com.inthinc.pro.automation.elements.TextTable;
import com.inthinc.pro.automation.elements.TextTableLink;
import com.inthinc.pro.selenium.pageEnums.AdminBarEnum;
import com.inthinc.pro.selenium.pageEnums.AdminTables.AdminVehiclesEntries;
import com.inthinc.pro.selenium.pageEnums.AdminVehiclesEnum;

public class PageAdminVehicles extends AdminTables {


    private static final AdminVehiclesEnum[] enums = { AdminVehiclesEnum.STATUS_DHX, AdminVehiclesEnum.ZONE_TYPE_DHX, AdminVehiclesEnum.PRODUCT_DHX };

    public PageAdminVehicles() {
        page = "vehicles";
        url = AdminVehiclesEnum.DEFAULT_URL;
        checkMe.add(AdminVehiclesEnum.TITLE);
    }

    public AdminVehiclesPopUps _popUp() {
        return new AdminVehiclesPopUps();
    }

    public class AdminVehiclesPopUps extends AdminTablesPopUps {
        public AdminDelete delete(){
            return new AdminDelete(true);
        }
    }

    public class AdminVehiclesButtons extends AdminTablesButtons {


        public TextButton delete() {
            return new TextButton(AdminBarEnum.DELETE, page);
        }
    }

    public class AdminVehiclesDropDowns extends AdminTablesDropDowns {

        public DropDown filterByProductType() {
            return new DhxDropDown(AdminVehiclesEnum.PRODUCT_DHX, enums);
        }

        public DropDown filterByZoneType() {
            return new DhxDropDown(AdminVehiclesEnum.ZONE_TYPE_DHX, enums);
        }

        public DropDown filterByStatus() {
            return new DhxDropDown(AdminVehiclesEnum.STATUS_DHX, enums);
        }

    }

    public class AdminVehiclesLinks extends AdminTablesLinks {

        public TextTableLink entryVehicleId() {
            return new TextTableLink(AdminBarEnum.TABLE_ENTRIES, page, AdminVehiclesEntries.VEHICLE_ID);
        }

        public TextLink sortBy(AdminVehiclesEntries column) {
            return new TextLink(AdminBarEnum.TABLE_HEADERS, page, column);
        }
    }

    public class AdminVehiclesTextFields extends AdminTablesTextFields {
    }

    public class AdminVehiclesTexts extends AdminTablesTexts {

        public TextTable entryTableValue(AdminVehiclesEntries column) {
            return new TextTable(AdminBarEnum.TABLE_ENTRIES, page, column);
        }
    }

    public AdminVehiclesButtons _button() {
        return new AdminVehiclesButtons();
    }

    public AdminVehiclesDropDowns _dropDown() {
        return new AdminVehiclesDropDowns();
    }

    public AdminVehiclesLinks _link() {
        return new AdminVehiclesLinks();
    }

    public AdminVehiclesTexts _text() {
        return new AdminVehiclesTexts();
    }

    public AdminVehiclesTextFields _textField() {
        return new AdminVehiclesTextFields();
    }

    public class AdminVehiclesPager {
        public Paging pageIndex() {
            return new Paging();
        }
    }

    public AdminVehiclesPager _page() {
        return new AdminVehiclesPager();
    }

}
