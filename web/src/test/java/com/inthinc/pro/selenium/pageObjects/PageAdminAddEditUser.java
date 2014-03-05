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
import com.inthinc.pro.selenium.pageEnums.AdminUsersEnum;
import com.inthinc.pro.selenium.pageEnums.AdminTables.UserColumns;

public class PageAdminAddEditUser extends AdminBar {

    private static String page = "Person";

    public PageAdminAddEditUser() {
        checkMe.add(AdminAddEditUserEnum.DRIVER_TEAM_DHX);
    }

    @Override
    public SeleniumEnums setUrl() {
        return AdminAddEditUserEnum.DEFAULT_URL;
    }

    public class AddEditUserCheckBoxs {

        public CheckBox driverInformation() {
            return new CheckBox(AdminAddEditUserEnum.TEXT_FIELDS, "isDriver");
        }

        public CheckBox loginInformation() {
            return new CheckBox(AdminAddEditUserEnum.TEXT_FIELDS, "isUser");
        }

    }

    public class AddEditUserSelects {

        public Selector rolesLeft() {
            return new Selector(AdminBarEnum.SELECTOR, page, "from");
        }

        public Selector rolesRight() {
            return new Selector(AdminBarEnum.SELECTOR, page, "picked");
        }
    }

    public class AddEditUserTexts extends AdminBarTexts {
        
        public Text title() {
            return new Text(AdminAddEditUserEnum.TITLE);
        }

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
        
        public TextField firstName() {
        	return new TextField(AdminAddEditUserEnum.FIRST_NAME_FIELD);
        }
        
        public TextField middleName() {
            return new TextField(AdminAddEditUserEnum.MIDDLE_NAME_FIELD);
        }
        
        public TextField lastName() {
        	return new TextField(AdminAddEditUserEnum.LAST_NAME_FIELD);
        }
        
        public TextField dOB() {
            return new TextField(AdminAddEditUserEnum.DOB_FIELD);
        }
        
        public TextField driverLicenseNumber() {
            return new TextField(AdminAddEditUserEnum.DRIVER_LICENSE_NUMBER_FIELD);
        }
        
        public TextField Class() {
            return new TextField(AdminAddEditUserEnum.CLASS_FIELD);
        }
        
        public TextField expiration() {
            return new TextField(AdminAddEditUserEnum.EXPIRATION_FIELD);
        }
        
        public TextField certifications() {
            return new TextField(AdminAddEditUserEnum.CERTIFICATIONS_FIELD);
        }
        
        public TextField rFIDBarCode() {
            return new TextField(AdminAddEditUserEnum.RFID_BAR_CODE_FIELD);
        }
        
        public TextField oneWireID() {
            return new TextField(AdminAddEditUserEnum.ONE_WIRE_ID_FIELD);
        }
        
        public TextField employeeID() {
            return new TextField(AdminAddEditUserEnum.EMPLOYEE_ID_FIELD);
        }
        
        public TextField reportsTo() {
            return new TextField(AdminAddEditUserEnum.REPORTS_TO_FIELD);
        }
        
        public TextField title() {
            return new TextField(AdminAddEditUserEnum.TITLE_FIELD);
        }
        
        public TextField userName() {
            return new TextField(AdminAddEditUserEnum.USER_NAME_FIELD);
        }
        
        public TextField password() {
            return new TextField(AdminAddEditUserEnum.PASSWORD_FIELD);
        }
        
        public TextField passwordAgain() {
            return new TextField(AdminAddEditUserEnum.PASSWORD_AGAIN_FIELD);
        }
        
        public TextField emailOne() {
            return new TextField(AdminAddEditUserEnum.EMAIL_ONE_FIELD);
        }
        
        public TextField emailTwo() {
            return new TextField(AdminAddEditUserEnum.EMAIL_TWO_FIELD);
        }
        
        public TextField textMessageOne() {
            return new TextField(AdminAddEditUserEnum.TEXT_MESSAGE_ONE_FIELD);
        }
        
        public TextField textMessageTwo() {
            return new TextField(AdminAddEditUserEnum.TEXT_MESSAGE_TWO_FIELD);
        }
        
        public TextField phoneOne() {
            return new TextField(AdminAddEditUserEnum.PHONE_ONE_FIELD);
        }
        
        public TextField phoneTwo() {
            return new TextField(AdminAddEditUserEnum.PHONE_TWO_FIELD);
        }
        
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

    public class AddEditUserDropDowns extends AdminBarDropDowns {

        private SeleniumEnums[] enums = { AdminAddEditUserEnum.USER_GROUP_DHX, AdminAddEditUserEnum.DRIVER_TEAM_DHX };

        public DropDown regularDropDowns(UserColumns dropDown) {
            return new DropDown(AdminAddEditUserEnum.DROP_DOWNS, dropDown);
        }

        public DHXDropDown team() {
            return new DHXDropDown(AdminAddEditUserEnum.DRIVER_TEAM_DHX, enums);
        }

        public DHXDropDown group() {
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
        
        public DropDown information(){
            return new DropDown(AdminAddEditUserEnum.INFORMATION);
        }
        
        public DropDown warning(){
            return new DropDown(AdminAddEditUserEnum.WARNING);
        }
        
        public DropDown critical(){
            return new DropDown(AdminAddEditUserEnum.CRITICAL);
        }
        
    }
    
    public class AddEditUserLinks extends AdminBarLinks {}
    public class AddEditUserPopUps extends MastheadPopUps {}
    
    public AddEditUserDropDowns _dropDown() {
        return new AddEditUserDropDowns();
    }
    
    public AddEditUserCheckBoxs _checkBox() {
        return new AddEditUserCheckBoxs();
    }
    
    public AddEditUserSelects _select() {
        return new AddEditUserSelects();
    }

    public AddEditUserLinks _link() {
        return new AddEditUserLinks();
    }
    
    public AddEditUserTexts _text() {
        return new AddEditUserTexts();
    }

    public AddEditUserPopUps _popUp() {
        return new AddEditUserPopUps();
    }

    public AddEditUserTextFields _textField() {
        return new AddEditUserTextFields();
    }

    public AddEditUserButtons _button() {
        return new AddEditUserButtons();
    }

    @Override
    public String getExpectedPath() {
        return AdminAddEditUserEnum.DEFAULT_URL.getURL();
    }

    @Override
    protected boolean checkIsOnPage() {
        return _button().cancelBottom().isPresent() && _text().title().isPresent();
    }
}
