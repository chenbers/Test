package com.inthinc.pro.selenium.pageObjects;

import java.util.Iterator;

import com.inthinc.pro.automation.elements.TextButton;
import com.inthinc.pro.automation.elements.TextField;
import com.inthinc.pro.automation.elements.TextLink;
import com.inthinc.pro.automation.elements.TextTable;
import com.inthinc.pro.automation.elements.TextTableLink;
import com.inthinc.pro.automation.elements.ElementInterface.Checkable;
import com.inthinc.pro.automation.elements.ElementInterface.TextBased;
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
    
    //Helper methods
    public PageAdminUsers showAllColumns() {//TODO: davidTanner: is there already something tying the checkbox and it's label together?  it could be very handy to be able to ensure that a specific column was visible instead of having to cycle through them ALL?
        this._link().editColumns().click();
        Iterator<Checkable> colCheckBoxes = this._popUp().editColumns()._checkBox().iterator();
        while(colCheckBoxes.hasNext()){
            colCheckBoxes.next().check();
        }
        this._popUp().editColumns()._button().save().click();
        return this;
    }
    public PageAdminUsers search(String searchString) {
        this._textField().search().type(searchString);
        this._button().search().click();
        return this;
    }
    public PageAdminUsers clickFullNameMatching(AdminUsersEntries column, String value){
        this.showAllColumns();
        this.search(value);
        Iterator<TextBased> rowIterator = this._text().tableEntry(column).iterator();
        boolean clicked = false;
        int rowNumber = 0;
        while(rowIterator.hasNext()&&!clicked){
            TextBased rowCol = rowIterator.next();
            rowNumber++;
            if(rowCol.getText().equals(value)){
                this._link().tableEntryUserFullName().row(rowNumber).click();
                return this;
            }
        }
        addError("clickFullNameMatching("+column+", "+value+")", ErrorLevel.ERROR);
        return this;
    }
    
}
