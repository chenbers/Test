package com.inthinc.pro.selenium.pageObjects;

import java.util.Iterator;

import com.inthinc.pro.automation.elements.ElementInterface.Checkable;
import com.inthinc.pro.automation.elements.ElementInterface.TextBased;
import com.inthinc.pro.automation.elements.CheckBoxTable;
import com.inthinc.pro.automation.elements.TextButton;
import com.inthinc.pro.automation.elements.TextField;
import com.inthinc.pro.automation.elements.TextLink;
import com.inthinc.pro.automation.elements.TextTable;
import com.inthinc.pro.automation.elements.TextTableLink;
import com.inthinc.pro.automation.enums.ErrorLevel;
import com.inthinc.pro.selenium.pageEnums.AdminBarEnum;
import com.inthinc.pro.selenium.pageEnums.LoginEnum;
import com.inthinc.pro.selenium.pageEnums.PopUpEnum;
import com.inthinc.pro.selenium.pageEnums.AdminTables.UserColumns;
import com.inthinc.pro.selenium.pageEnums.AdminUsersEnum;
import com.inthinc.pro.selenium.pageObjects.Masthead.MastheadLinks;

public class PageAdminUsers extends AdminTables {

    public PageAdminUsers() {
        page = "person";
        url = AdminUsersEnum.DEFAULT_URL;
        checkMe.add(AdminUsersEnum.BATCH_EDIT);
        checkMe.add(AdminUsersEnum.EDIT_COLUMNS_LINK);
        checkMe.add(AdminUsersEnum.SEARCH_BUTTON);
        checkMe.add(AdminUsersEnum.SELECT_ALL_CHECKBOX);
        checkMe.add(AdminUsersEnum.NAME);
        checkMe.add(AdminUsersEnum.USER_STATUS);
        checkMe.add(AdminUsersEnum.USER_NAME);
        checkMe.add(AdminUsersEnum.USER_GROUP);
        checkMe.add(AdminUsersEnum.ROLES);
        checkMe.add(AdminUsersEnum.PHONE_1);
        checkMe.add(AdminUsersEnum.PHONE_2);                                                         
        checkMe.add(AdminUsersEnum.EMAIL_1);
        checkMe.add(AdminUsersEnum.EMAIL_2);
        checkMe.add(AdminUsersEnum.TEXT_MESSAGE_1);
        checkMe.add(AdminUsersEnum.TEXT_MESSAGE_2);   
        checkMe.add(AdminUsersEnum.INFORMATION_ALERTS);
        checkMe.add(AdminUsersEnum.WARNING_ALERTS);
        checkMe.add(AdminUsersEnum.CRITICAL_ALERTS);
        checkMe.add(AdminUsersEnum.TIME_ZONE);
        checkMe.add(AdminUsersEnum.EMPLOYEE_ID);
        checkMe.add(AdminUsersEnum.REPORTS_TO);
        checkMe.add(AdminUsersEnum.JOB_TITLE);
        checkMe.add(AdminUsersEnum.DOB);
        checkMe.add(AdminUsersEnum.GENDER);
        checkMe.add(AdminUsersEnum.BAR_CODE);
        checkMe.add(AdminUsersEnum.RFID_1);
        checkMe.add(AdminUsersEnum.RFID_2);
        checkMe.add(AdminUsersEnum.LOCALE);
        checkMe.add(AdminUsersEnum.MEASUREMENT_TYPE);
        checkMe.add(AdminUsersEnum.FUEL_EFFICIENCY_RATIO);
        checkMe.add(AdminUsersEnum.DRIVER_STATUS);
        checkMe.add(AdminUsersEnum.DRIVER_LICENSE);
        checkMe.add(AdminUsersEnum.LICENSE_CLASS);
        checkMe.add(AdminUsersEnum.LICENSE_STATE);
        checkMe.add(AdminUsersEnum.LICENSE_EXPIRATION);
        checkMe.add(AdminUsersEnum.CERTIFICATIONS);
        checkMe.add(AdminUsersEnum.DOT);        
        checkMe.add(AdminUsersEnum.DRIVER_TEAM);
        
    }

    public class AdminUsersButtons extends AdminTablesButtons {

        public TextButton delete() {
            return new TextButton(AdminBarEnum.DELETE, page);
        }

    }

    public class AdminUsersDropDowns extends AdminTablesDropDowns {}
    
    public class AdminUsersLinks extends AdminTablesLinks {

        public TextTableLink tableEntryUserFullName() {
            return new TextTableLink(AdminBarEnum.TABLE_ENTRIES, page, UserColumns.FULL_NAME);
        }
        public TextTableLink tableEntryUserEmail1() {
            return new TextTableLink(AdminBarEnum.TABLE_ENTRIES, page, UserColumns.EMAIL_1);
        }
        public TextLink sortByColumn(UserColumns column) {
            return new TextLink(AdminBarEnum.TABLE_HEADERS, page, column);
        }
        
        public TextLink sortByEmail(){
            return new TextLink(AdminUsersEnum.EMAIL_1);
        }
        
        public TextLink sortByName(){
            return new TextLink(AdminUsersEnum.NAME);
        }
        
        public TextLink sortByUserStatus(){
            return new TextLink(AdminUsersEnum.USER_STATUS);
        }
        
        public TextLink sortByUserName(){
            return new TextLink(AdminUsersEnum.USER_NAME);
        }
        
        public TextLink sortByUserGroup(){
            return new TextLink(AdminUsersEnum.USER_GROUP);
        }
        
        public TextLink sortByRoles(){
            return new TextLink(AdminUsersEnum.ROLES);
        }
        
        public TextLink sortByPhone1(){
            return new TextLink(AdminUsersEnum.PHONE_1);
        }
        
        public TextLink sortByPhone2(){
            return new TextLink(AdminUsersEnum.PHONE_2);
        }
        
        public TextLink sortByEmail1(){
            return new TextLink(AdminUsersEnum.EMAIL_1);
        }
        
        public TextLink sortByEmail2(){
            return new TextLink(AdminUsersEnum.EMAIL_2);
        }
        
        public TextLink sortByTextMessage1(){
            return new TextLink(AdminUsersEnum.TEXT_MESSAGE_1);
        }
        
        public TextLink sortByTextMessage2(){
            return new TextLink(AdminUsersEnum.TEXT_MESSAGE_2);
        }
        
        public TextLink sortByInformationAlerts(){
            return new TextLink(AdminUsersEnum.INFORMATION_ALERTS);
        }
        
        public TextLink sortByWarningAlerts(){
            return new TextLink(AdminUsersEnum.WARNING_ALERTS);
        }
        
        public TextLink sortByCriticalALerts(){
            return new TextLink(AdminUsersEnum.CRITICAL_ALERTS);
        }
        
        public TextLink sortByTimeZone(){
            return new TextLink(AdminUsersEnum.TIME_ZONE);
        }
        
        public TextLink sortByEmployeeID(){
            return new TextLink(AdminUsersEnum.EMPLOYEE_ID);
        }
        
        public TextLink sortByReportsTo(){
            return new TextLink(AdminUsersEnum.REPORTS_TO);
        }
        
        public TextLink sortByJobTitle(){
            return new TextLink(AdminUsersEnum.JOB_TITLE);
        }
        
        public TextLink sortByDOB(){
            return new TextLink(AdminUsersEnum.DOB);
        }
        
        public TextLink sortByGender(){
            return new TextLink(AdminUsersEnum.GENDER);
        }
        public TextLink sortByBarCode(){
            return new TextLink(AdminUsersEnum.BAR_CODE);
        }
        
        public TextLink sortByRFID1(){
            return new TextLink(AdminUsersEnum.RFID_1);
        }
        
        public TextLink sortByRFID2(){
            return new TextLink(AdminUsersEnum.RFID_2);
        }
        
        public TextLink sortByLocale(){
            return new TextLink(AdminUsersEnum.LOCALE);
        }
        
        public TextLink sortByMeasurementType(){
            return new TextLink(AdminUsersEnum.MEASUREMENT_TYPE);
        }
        
        public TextLink sortByFuelEfficiencyRatio(){
            return new TextLink(AdminUsersEnum.FUEL_EFFICIENCY_RATIO);
        }
        
        public TextLink sortByDriverStatus(){
            return new TextLink(AdminUsersEnum.DRIVER_STATUS);
        }
        
        public TextLink sortByDriverLicense(){
            return new TextLink(AdminUsersEnum.DRIVER_LICENSE);
        }
        
        public TextLink sortByLicenseClass(){
            return new TextLink(AdminUsersEnum.LICENSE_CLASS);
        }
        
        public TextLink sortByLicenseState(){
            return new TextLink(AdminUsersEnum.LICENSE_STATE);
        }
        
        public TextLink sortByLicenseExpiration(){
            return new TextLink(AdminUsersEnum.LICENSE_EXPIRATION);
        }
        
        public TextLink sortByCertifications(){
            return new TextLink(AdminUsersEnum.CERTIFICATIONS);
        }
        
        public TextLink sortByDOT(){
            return new TextLink(AdminUsersEnum.DOT);
        }
        
        public TextLink sortByDriverTeam(){
            return new TextLink(AdminUsersEnum.DRIVER_TEAM);
        }

    }

    public class AdminUsersTextFields extends AdminTablesTextFields {

        public TextField search() {
            return new TextField(AdminBarEnum.SEARCH_TEXTFIELD, page);
        }

    }
    
    public class AdminUsersTexts extends AdminTablesTexts {

        public TextTable tableEntry(UserColumns column) {
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
    
    /* NEED TO IMPLEMENT CLICKING THE SELECT ALL BOX
    
    public CheckBoxTable _checkBox() {
        return new CheckBoxTable(PopUpEnum.SELECT_ALL_CHECKBOX, page);
    }
    
    */

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
    public PageAdminUsers clickFullNameMatching(UserColumns column, String value){
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
        addError("clickFullNameMatching("+column+", "+value+")", ErrorLevel.FATAL);
        return this;
    }
    
}
