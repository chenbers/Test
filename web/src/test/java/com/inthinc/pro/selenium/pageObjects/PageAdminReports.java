package com.inthinc.pro.selenium.pageObjects;

import java.util.Iterator;

import com.inthinc.pro.automation.elements.ElementInterface.Checkable;
import com.inthinc.pro.automation.elements.ElementInterface.TextBased;
import com.inthinc.pro.automation.elements.TextButton;
import com.inthinc.pro.automation.elements.TextField;
import com.inthinc.pro.automation.elements.TextLink;
import com.inthinc.pro.automation.elements.TextTable;
import com.inthinc.pro.automation.elements.TextTableLink;
import com.inthinc.pro.automation.enums.ErrorLevel;
import com.inthinc.pro.selenium.pageEnums.AdminBarEnum;
import com.inthinc.pro.selenium.pageEnums.AdminReportsEnum;
import com.inthinc.pro.selenium.pageEnums.AdminTables.ReportsColumns;

public class PageAdminReports extends AdminTables {

    public PageAdminReports() {
        page = "person";
        url = AdminReportsEnum.DEFAULT_URL;
        checkMe.add(AdminReportsEnum.BATCH_EDIT);
        checkMe.add(AdminReportsEnum.EDIT_COLUMNS_LINK);
        checkMe.add(AdminReportsEnum.SEARCH_BUTTON);
    }

    public class AdminReportsButtons extends AdminTablesButtons {

        public TextButton delete() {
            return new TextButton(AdminBarEnum.DELETE, page);
        }

    }

    public class AdminReportsDropDowns extends AdminTablesDropDowns {}

    public class AdminReportsLinks extends AdminTablesLinks {

        public TextTableLink tableEntryReportName() {
            return new TextTableLink(AdminBarEnum.TABLE_ENTRIES, page, ReportsColumns.NAME);
        }
        public TextLink sortByColumn(ReportsColumns column) {
            return new TextLink(AdminBarEnum.TABLE_HEADERS, page, column);
        }

    }

    public class AdminReportsTextFields extends AdminTablesTextFields {

        public TextField search() {
            return new TextField(AdminBarEnum.SEARCH_TEXTFIELD, page);
        }

    }
    
    public class AdminReportsTexts extends AdminTablesTexts {

        public TextTable tableEntry(ReportsColumns column) {
            return new TextTable(AdminBarEnum.TABLE_ENTRIES, page, column);
        }
        
    }

    public AdminReportsButtons _button() {
        return new AdminReportsButtons();
    }

    public AdminReportsDropDowns _dropDown() {
        return new AdminReportsDropDowns();
    }

    public AdminReportsLinks _link() {
        return new AdminReportsLinks();
    }

    public AdminReportsTexts _text() {
        return new AdminReportsTexts();
    }

    public AdminReportsTextFields _textField() {
        return new AdminReportsTextFields();
    }
    
    public AdminReportsPopUps _popUp(){
        return new AdminReportsPopUps();
    }

    public class AdminReportsPopUps extends AdminTablesPopUps {
        public AdminDelete delete(){
            return new AdminDelete(true);
        }
    }
    
    //Helper methods
    public PageAdminReports showAllColumns() {
        this._link().editColumns().click();
        Iterator<Checkable> colCheckBoxes = this._popUp().editColumns()._checkBox().iterator();
        while(colCheckBoxes.hasNext()){
            colCheckBoxes.next().check();
        }
        this._popUp().editColumns()._button().save().click();
        return this;
    }
    public PageAdminReports search(String searchString) {
        this._textField().search().type(searchString);
        this._button().search().click();
        return this;
    }
    public PageAdminReports clickNameMatching(ReportsColumns column, String value){
        this.showAllColumns();
        this.search(value);
        Iterator<TextBased> rowIterator = this._text().tableEntry(column).iterator();
        boolean clicked = false;
        int rowNumber = 0;
        while(rowIterator.hasNext()&&!clicked){
            TextBased rowCol = rowIterator.next();
            rowNumber++;
            if(rowCol.getText().equals(value)){
                this._link().tableEntryReportName().row(rowNumber).click();
                return this;
            }
        }
        addError("clickNameMatching("+column+", "+value+")", ErrorLevel.FATAL);
        return this;
    }
    
}
