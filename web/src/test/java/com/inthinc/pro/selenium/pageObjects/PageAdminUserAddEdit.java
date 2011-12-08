package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.Button;
import com.inthinc.pro.automation.elements.CheckBox;
import com.inthinc.pro.automation.elements.DHXDropDown;
import com.inthinc.pro.automation.elements.DropDown;
import com.inthinc.pro.automation.elements.Selector;
import com.inthinc.pro.automation.elements.Text;
import com.inthinc.pro.automation.elements.TextButton;
import com.inthinc.pro.automation.elements.TextField;
import com.inthinc.pro.automation.elements.TextFieldError;
import com.inthinc.pro.automation.elements.TextFieldLabel;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.selenium.pageEnums.AdminAddEditUserEnum;
import com.inthinc.pro.selenium.pageEnums.AdminBarEnum;
import com.inthinc.pro.selenium.pageEnums.AdminTables.UserColumns;

public class PageAdminUserAddEdit extends AdminBar {

    private static String page = "Person";

    public PageAdminUserAddEdit() {
        url = AdminAddEditUserEnum.DEFAULT_URL;
        checkMe.add(AdminAddEditUserEnum.DRIVER_TEAM_DHX);
    }

    public AddEditUserPopUps _popUp() {
        return new AddEditUserPopUps();
    }

    public class AddEditUserPopUps extends MastheadPopUps {}

    public class AddEditUserCheckBoxs {

        public CheckBox driverInformation() {
            return new CheckBox(AdminAddEditUserEnum.TEXT_FIELDS, "isDriver");
        }

        public CheckBox userInformation() {
            return new CheckBox(AdminAddEditUserEnum.TEXT_FIELDS, "isUser");
        }

    }

    public AddEditUserCheckBoxs _checkBox() {
        return new AddEditUserCheckBoxs();
    }

    public class AddEditUserSelects {

        public Selector rolesLeft() {
            return new Selector(AdminBarEnum.SELECTOR, page, "from");
        }

        public Selector rolesRight() {
            return new Selector(AdminBarEnum.SELECTOR, page, "picked");
        }
    }

    public AddEditUserSelects _select() {
        return new AddEditUserSelects();
    }

    public AddEditUserLinks _link() {
        return new AddEditUserLinks();
    }

    public class AddEditUserLinks extends AdminBarLinks {}

    public AddEditUserTexts _text() {
        return new AddEditUserTexts();
    }

    public class AddEditUserTexts extends AdminBarTexts {

        public Text masterError() {
            return new Text(AdminBarEnum.MASTER_ERROR);
        }

        public TextFieldLabel personLabel(UserColumns label) {
            return new TextFieldLabel(AdminAddEditUserEnum.TEXT_FIELDS, label);
        }

        public TextFieldLabel driverLabel(UserColumns label) {
            return new TextFieldLabel(AdminAddEditUserEnum.TEXT_FIELDS, "driver_", label);
        }

        public TextFieldLabel userLabel(UserColumns label) {
            return new TextFieldLabel(AdminAddEditUserEnum.TEXT_FIELDS, "user_", label);
        }

        public TextFieldError personError(UserColumns label) {
            return new TextFieldError(AdminAddEditUserEnum.TEXT_FIELDS, label);
        }

        public TextFieldError driverError(UserColumns label) {
            return new TextFieldError(AdminAddEditUserEnum.TEXT_FIELDS, "driver_", label);
        }

        public TextFieldError userError(UserColumns label) {
            return new TextFieldError(AdminAddEditUserEnum.TEXT_FIELDS, "user_", label);
        }

    }

    public AddEditUserTextFields _textField() {
        return new AddEditUserTextFields();
    }

    public class AddEditUserTextFields extends AdminBarTextFields {

        //TODO: question: as an automation script writer, how do I know the difference between (person/driver/user)Fields?
        public TextField personFields(UserColumns textField) {
            return new TextField(AdminAddEditUserEnum.TEXT_FIELDS, textField);
        }

        public TextField driverFields(UserColumns textField) {
            return new TextField(AdminAddEditUserEnum.TEXT_FIELDS, "driver_", textField);
        }

        public TextField userFields(UserColumns textField) { 
            return new TextField(AdminAddEditUserEnum.TEXT_FIELDS, "user_", textField);
        }
    }

    public AddEditUserButtons _button() {
        return new AddEditUserButtons();
    }

    public class AddEditUserButtons extends AdminBarButtons {

        public TextButton saveTop() {
            return new TextButton(AdminAddEditUserEnum.SAVE, "1");
        }

        public TextButton saveBottom() {
            return new TextButton(AdminAddEditUserEnum.SAVE, "2");
        }

        public TextButton cancelTop() {
            return new TextButton(AdminAddEditUserEnum.CANCEL, "1");
        }

        public TextButton cancelBottom() {
            return new TextButton(AdminAddEditUserEnum.CANCEL, "2");
        }

        public Button moveRight() {
            return new Button(AdminBarEnum.MOVE_RIGHT, page);
        }

        public Button moveLeft() {
            return new Button(AdminBarEnum.MOVE_LEFT, page);
        }

        public Button moveAllRight() {
            return new Button(AdminBarEnum.MOVE_ALL_RIGHT, page);
        }

        public Button moveAllLeft() {
            return new Button(AdminBarEnum.MOVE_ALL_LEFT, page);
        }

    }

    public AddEditUserDropDowns _dropDown() {
        return new AddEditUserDropDowns();
    }

    public class AddEditUserDropDowns extends AdminBarDropDowns {

        private SeleniumEnums[] enums = { AdminAddEditUserEnum.USER_GROUP_DHX, AdminAddEditUserEnum.DRIVER_TEAM_DHX };

        public DropDown regularDropDowns(UserColumns dropDown) {
            return new DropDown(AdminAddEditUserEnum.DROP_DOWNS, dropDown);
        }

        public DHXDropDown driverTeam() {
            return new DHXDropDown(AdminAddEditUserEnum.DRIVER_TEAM_DHX, enums);
        }

        public DHXDropDown userGroup() {
            return new DHXDropDown(AdminAddEditUserEnum.USER_GROUP_DHX, enums);
        }
        
        public DropDown locale(){
            return new DropDown(AdminAddEditUserEnum.LOCALE);
        }
        
        public DropDown timeZone(){
            return new DropDown(AdminAddEditUserEnum.TIME_ZONE);
        }
        
        public DropDown measurement(){
            return new DropDown(AdminAddEditUserEnum.MEASUREMENT);
        }
        
        public DropDown fuelEfficiencyRatio(){
            return new DropDown(AdminAddEditUserEnum.FUEL_EFFICIENCY);
        }
        
        public DropDown userStatus(){
            return new DropDown(AdminAddEditUserEnum.USER_STATUS);
        }
        
        public DropDown driverStatus(){
            return new DropDown(AdminAddEditUserEnum.DRIVER_STATUS);
        }
        
        public DropDown suffix(){
            return new DropDown(AdminAddEditUserEnum.SUFFIX);
        }
        
        public DropDown gender(){
            return new DropDown(AdminAddEditUserEnum.GENDER);
        }
        
        public DropDown dot(){
            return new DropDown(AdminAddEditUserEnum.DOT);
        }
        
        public DropDown driverState(){
            return new DropDown(AdminAddEditUserEnum.STATE);
        }
    }

    @Override
    public String getExpectedPath() {
        return AdminAddEditUserEnum.DEFAULT_URL.getURL();
    }
}
