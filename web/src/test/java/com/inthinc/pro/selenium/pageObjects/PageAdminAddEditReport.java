package com.inthinc.pro.selenium.pageObjects;

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
import com.inthinc.pro.selenium.pageEnums.AdminBarEnum;
import com.inthinc.pro.selenium.pageEnums.AdminReportAddEditEnum;
import com.inthinc.pro.selenium.pageEnums.AdminTables.ReportsColumns;

public class PageAdminAddEditReport extends AdminBar {

    private static String page = "Report";

    public PageAdminAddEditReport() {
        checkMe.add(AdminReportAddEditEnum.NAME);
        checkMe.add(AdminReportAddEditEnum.STATUS);
        checkMe.add(AdminReportAddEditEnum.TIME_OF_DAY);
        checkMe.add(AdminReportAddEditEnum.TIME_AM_PM);
        checkMe.add(AdminReportAddEditEnum.OCCURANCE);
        checkMe.add(AdminReportAddEditEnum.REPORT);
        checkMe.add(AdminReportAddEditEnum.EMAIL);
    }

    public AddEditReportPopUps _popUp() {
        return new AddEditReportPopUps();
    }

    public class AddEditReportPopUps extends MastheadPopUps {}

    public class AddEditReportCheckBoxs {

        public CheckBox driverInformation() {
            return new CheckBox(AdminReportAddEditEnum.TEXT_FIELDS, "isDriver");
        }

        public CheckBox ReportInformation() {
            return new CheckBox(AdminReportAddEditEnum.TEXT_FIELDS, "isReport");
        }

    }

    public AddEditReportCheckBoxs _checkBox() {
        return new AddEditReportCheckBoxs();
    }

    public class AddEditReportSelects {

        public Selector rolesLeft() {
            return new Selector(AdminBarEnum.SELECTOR, page, "from");
        }

        public Selector rolesRight() {
            return new Selector(AdminBarEnum.SELECTOR, page, "picked");
        }
    }

    public AddEditReportSelects _select() {
        return new AddEditReportSelects();
    }

    public AddEditReportLinks _link() {
        return new AddEditReportLinks();
    }

    public class AddEditReportLinks extends AdminBarLinks {}

    public AddEditReportTexts _text() {
        return new AddEditReportTexts();
    }

    public class AddEditReportTexts extends AdminBarTexts {

        public Text masterError() {
            return new Text(AdminBarEnum.MASTER_ERROR);
        }

        public TextFieldLabel personLabel(ReportsColumns label) {
            return new TextFieldLabel(AdminReportAddEditEnum.TEXT_FIELDS, label);
        }

        public TextFieldLabel driverLabel(ReportsColumns label) {
            return new TextFieldLabel(AdminReportAddEditEnum.TEXT_FIELDS, "driver_", label);
        }

        public TextFieldLabel ReportLabel(ReportsColumns label) {
            return new TextFieldLabel(AdminReportAddEditEnum.TEXT_FIELDS, "Report_", label);
        }

        public TextFieldError personError(ReportsColumns label) {
            return new TextFieldError(AdminReportAddEditEnum.TEXT_FIELDS, label);
        }

        public TextFieldError driverError(ReportsColumns label) {
            return new TextFieldError(AdminReportAddEditEnum.TEXT_FIELDS, "driver_", label);
        }

        public TextFieldError ReportError(ReportsColumns label) {
            return new TextFieldError(AdminReportAddEditEnum.TEXT_FIELDS, "Report_", label);
        }

    }

    public AddEditReportTextFields _textField() {
        return new AddEditReportTextFields();
    }

    public class AddEditReportTextFields extends AdminBarTextFields {

        public TextField personFields(ReportsColumns textField) {
            return new TextField(AdminReportAddEditEnum.TEXT_FIELDS, textField);
        }

        public TextField driverFields(ReportsColumns textField) {
            return new TextField(AdminReportAddEditEnum.TEXT_FIELDS, "driver_", textField);
        }

        public TextField ReportFields(ReportsColumns textField) { 
            return new TextField(AdminReportAddEditEnum.TEXT_FIELDS, "Report_", textField);
        }
    }

    public AddEditReportButtons _button() {
        return new AddEditReportButtons();
    }

    public class AddEditReportButtons extends AdminBarButtons {

        public TextButton saveTop() {
            return new TextButton(AdminReportAddEditEnum.SAVE, "1");
        }

        public TextButton saveBottom() {
            return new TextButton(AdminReportAddEditEnum.SAVE, "2");
        }

        public TextButton cancelTop() {
            return new TextButton(AdminReportAddEditEnum.CANCEL, "1");
        }

        public TextButton cancelBottom() {
            return new TextButton(AdminReportAddEditEnum.CANCEL, "2");
        }
    }

    public AddEditReportDropDowns _dropDown() {
        return new AddEditReportDropDowns();
    }

    public class AddEditReportDropDowns extends AdminBarDropDowns {

        private SeleniumEnums[] enums = { AdminReportAddEditEnum.GROUP_DHX};

        public DropDown regularDropDowns(ReportsColumns dropDown) {
            return new DropDown(AdminReportAddEditEnum.DROP_DOWNS, dropDown);
        }

        public DHXDropDown ReportGroup() {
            return new DHXDropDown(AdminReportAddEditEnum.GROUP_DHX, enums);
        }
        
        public DropDown ReportStatus(){
            return new DropDown(AdminReportAddEditEnum.STATUS);
        }
        
        
    }

    @Override
    public SeleniumEnums setUrl() {
        return AdminReportAddEditEnum.DEFAULT_URL;
    }
}
