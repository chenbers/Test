package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.TextButton;
import com.inthinc.pro.automation.elements.TextField;
import com.inthinc.pro.automation.elements.TextLink;
import com.inthinc.pro.automation.elements.TextTable;
import com.inthinc.pro.automation.elements.TextTableLink;
import com.inthinc.pro.selenium.pageEnums.AdminBarEnum;
import com.inthinc.pro.selenium.pageEnums.AdminTables.AdminUsersEntries;
import com.inthinc.pro.selenium.pageEnums.AdminUsersEnum;

public class PageAdminUsers extends AdminTables {

    public PageAdminUsers() {
        page = "person";
        url = AdminUsersEnum.DEFAULT_URL;
        checkMe.add(AdminUsersEnum.BATCH_EDIT);
        checkMe.add(AdminUsersEnum.EDIT_COLUMNS_LINK);
        checkMe.add(AdminUsersEnum.SEARCH_BUTTON);
    }

    public class AdminUsersButtons extends AdminTablesButtons {

        public TextButton delete() {
            return new TextButton(AdminBarEnum.DELETE, page);
        }

    }

    public class AdminUsersDropDowns extends AdminTablesDropDowns {}

    public class AdminUsersLinks extends AdminTablesLinks {

        public TextTableLink tableEntryUserFullName() {
            return new TextTableLink(AdminBarEnum.TABLE_ENTRIES, page, AdminUsersEntries.FULL_NAME);
        }
        public TextLink sortByColumn(AdminUsersEntries column) {
            return new TextLink(AdminBarEnum.TABLE_HEADERS, page, column);
        }

    }

    public class AdminUsersTextFields extends AdminTablesTextFields {

        public TextField search() {
            return new TextField(AdminBarEnum.SEARCH_TEXTFIELD, page);
        }

    }
    
    public class AdminUsersTexts extends AdminTablesTexts {

        public TextTable tableEntry(AdminUsersEntries column) {
            return new TextTable(AdminBarEnum.TABLE_ENTRIES, page, column);
        }
        
    }

    public AdminUsersButtons _button() {
        return new AdminUsersButtons();
    }

    public AdminUsersDropDowns _dropDown() {
        return new AdminUsersDropDowns();
    }

    public AdminUsersLinks _link() {
        return new AdminUsersLinks();
    }

    public AdminUsersTexts _text() {
        return new AdminUsersTexts();
    }

    public AdminUsersTextFields _textField() {
        return new AdminUsersTextFields();
    }
    
    public AdminUsersPopUps _popUp(){
        return new AdminUsersPopUps();
    }

    public class AdminUsersPopUps extends AdminTablesPopUps {
        public AdminDelete delete(){
            return new AdminDelete(true);
        }
    }
}
