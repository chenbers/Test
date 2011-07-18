package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.CheckBox;
import com.inthinc.pro.automation.elements.CheckBoxTable;
import com.inthinc.pro.automation.elements.DhxDropDown;
import com.inthinc.pro.automation.elements.DropDown;
import com.inthinc.pro.automation.elements.Text;
import com.inthinc.pro.automation.elements.TextButton;
import com.inthinc.pro.automation.elements.TextField;
import com.inthinc.pro.automation.elements.TextFieldLabel;
import com.inthinc.pro.automation.elements.TextLink;
import com.inthinc.pro.automation.elements.TextTable;
import com.inthinc.pro.automation.elements.TextTableLink;
import com.inthinc.pro.selenium.pageEnums.AdminBarEnum;
import com.inthinc.pro.selenium.pageEnums.AdminTables.AdminVehiclesEntries;
import com.inthinc.pro.selenium.pageEnums.AdminVehiclesEnum;

public class PageAdminVehicles extends AdminBar {

    private static final String page = "vehicles";

    private static final AdminVehiclesEnum[] enums = { AdminVehiclesEnum.STATUS_DHX, AdminVehiclesEnum.ZONE_TYPE_DHX, AdminVehiclesEnum.PRODUCT_DHX };

    public PageAdminVehicles() {
        url = AdminVehiclesEnum.DEFAULT_URL;
        checkMe.add(AdminVehiclesEnum.TITLE);
    }

    public AdminVehiclesPopUps _popUp() {
        return new AdminVehiclesPopUps();
    }

    public class AdminVehiclesPopUps extends AdminBarPopUps {

        public AdminVehiclesPopUps() {
            super(page);
        }

        public AdminDelete delete() {
            return new AdminDelete(true);
        }
        
        public EditColumns editColumns(){
            return new EditColumns();
        }
    }

    public class AdminVehiclesButtons extends AdminBarButtons {

        public TextButton tableSearch() {
            return new TextButton(AdminBarEnum.SEARCH_BUTTON, page);
        }

        public TextButton batchEdit() {
            return new TextButton(AdminBarEnum.BATCH_EDIT, page);
        }

        public TextButton delete() {
            return new TextButton(AdminBarEnum.DELETE, page);
        }
    }

    public class AdminVehiclesDropDowns extends AdminBarDropDowns {

        public DropDown filterByProductType() {
            return new DhxDropDown(AdminVehiclesEnum.PRODUCT_DHX, enums);
        }

        public DropDown filterByZoneType() {
            return new DhxDropDown(AdminVehiclesEnum.ZONE_TYPE_DHX, enums);
        }

        public DropDown filterByStatus() {
            return new DhxDropDown(AdminVehiclesEnum.STATUS_DHX);
        }

    }

    public class AdminVehiclesLinks extends AdminBarLinks {

        public TextTableLink entryVehicleId() {
            return new TextTableLink(AdminBarEnum.TABLE_ENTRIES, page, AdminVehiclesEntries.VEHICLE_ID);
        }

        public TextTableLink entryEditVehicle() {
            return new TextTableLink(AdminVehiclesEnum.EDIT_VEHICLE);
        }

        public TextLink editColumns() {
            return new TextLink(AdminBarEnum.EDIT_COLUMNS_LINK, page);
        }

        public TextLink sortBy(AdminVehiclesEntries column) {
            return new TextLink(AdminBarEnum.TABLE_HEADERS, page, column);
        }
    }

    public class AdminVehiclesTextFields extends AdminBarTextFields {
        public TextField tableSearch() {
            return new TextField(AdminBarEnum.SEARCH_TEXTFIELD, page);
        }
    }

    public class AdminVehiclesTexts extends AdminBarTexts {

        public Text counter() {
            return new Text(AdminBarEnum.COUNTER);
        }

        public TextTable entryTableValue(AdminVehiclesEntries column) {
            return new TextTable(AdminBarEnum.TABLE_ENTRIES, page, column);
        }

        public TextFieldLabel labelSearchBox() {
            return new TextFieldLabel(AdminBarEnum.SEARCH_TEXTFIELD, page);
        }

    }

    public class AdminVehiclesCheckBoxs {
        public CheckBox checkAll() {
            return new CheckBox(AdminBarEnum.SELECT_ALL, page);
        }

        public CheckBoxTable entryCheckRow() {
            return new CheckBoxTable(AdminBarEnum.SELECT_ROW, page);
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

    public AdminVehiclesCheckBoxs _checkBox() {
        return new AdminVehiclesCheckBoxs();
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
