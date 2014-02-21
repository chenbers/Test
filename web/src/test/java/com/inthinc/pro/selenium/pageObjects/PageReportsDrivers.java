package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.Button;
import com.inthinc.pro.automation.elements.DHXDropDown;
import com.inthinc.pro.automation.elements.Text;
import com.inthinc.pro.automation.elements.TextButton;
import com.inthinc.pro.automation.elements.TextField;
import com.inthinc.pro.automation.elements.TextLink;
import com.inthinc.pro.automation.elements.TextLinkScoreTable;
import com.inthinc.pro.automation.elements.TextTable;
import com.inthinc.pro.automation.elements.TextTableLink;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.selenium.pageEnums.PopUpEnum;
import com.inthinc.pro.selenium.pageEnums.ReportsBarEnum;
import com.inthinc.pro.selenium.pageEnums.ReportsDriversEnum;

public class PageReportsDrivers extends ReportsBar {

    public PageReportsDrivers() {
        checkMe.add(ReportsDriversEnum.TITLE);
    }

    public class DriverReportButtons extends ReportsBarButtons {

        public TextButton exportEmail() {
            return new TextButton(ReportsBarEnum.TOOL_EMAIL, page);
        }

        public TextButton exportExcel() {
            return new TextButton(ReportsBarEnum.TOOL_EXCEL, page);
        }

        public TextButton exportPDF() {
            return new TextButton(ReportsBarEnum.TOOL_PDF, page);
        }

        public Button tools() {
            return new Button(PopUpEnum.TOOL_BUTTON, page);
        }

    }

    public class DriverReportDropDowns extends ReportsBarDropDowns {

        public DHXDropDown overall() {
            return new ReportsBarDropDowns().score(ReportsBarEnum.OVERALL_SCORE_DHX, page);
        }

        public DHXDropDown seatBelt() {
            return new ReportsBarDropDowns().score(ReportsBarEnum.SEATBELT_SCORE_DHX, page);
        }

        public DHXDropDown speed() {
            return new ReportsBarDropDowns().score(ReportsBarEnum.SPEED_SCORE_DHX, page);
        }

        public DHXDropDown style() {
            return new ReportsBarDropDowns().score(ReportsBarEnum.STYLE_SCORE_DHX, page);
        }

    }

    public class DriverReportLinks extends ReportsBarLinks {

        public TextLink editColumns() {
            return new TextLink(PopUpEnum.EDIT_COLUMNS, page);
        }
        
        public TextLink sortByGroup() {
            return new TextLink(ReportsDriversEnum.GROUP_SORT);
        }
        
        public TextLink sortByEmployeeID() {
            return new TextLink(ReportsDriversEnum.EMPLOYEE_ID_SORT);
        }
        
        public TextLink sortByDriver() {
            return new TextLink(ReportsDriversEnum.DRIVER_SORT);
        }
        
        public TextLink sortByVehicle() {
            return new TextLink(ReportsDriversEnum.VEHICLE_SORT);
        }
        
        public TextLink sortByDistanceDriven() {
            return new TextLink(ReportsDriversEnum.DISTANCE_DRIVEN_SORT);
        }

        public TextLink sortByOverall() {
            return new TextLink(ReportsDriversEnum.OVERALL_SCORE_SORT);
        }
        
        public TextLink sortBySpeed() {
            return new TextLink(ReportsDriversEnum.SPEED_SCORE_SORT);
        }
        
        public TextLink sortByStyle() {
            return new TextLink(ReportsDriversEnum.STYLE_SCORE_SORT);
        }
        
        public TextLink sortBySeatBelt() {
            return new TextLink(ReportsDriversEnum.SEATBELT_SCORE_SORT);
        }

        public TextTableLink groupValue() {
            return new TextTableLink(ReportsDriversEnum.GROUP_VALUE);
        }
        
        public TextTableLink driverValue() {
            return new TextTableLink(ReportsDriversEnum.DRIVER_VALUE);
        }

        public TextTableLink vehicleValue() {
            return new TextTableLink(ReportsDriversEnum.VEHICLE_VALUE);
        }

        public TextLinkScoreTable overallValue() {
            return new TextLinkScoreTable(ReportsDriversEnum.OVERALL_SCORE_VALUE);
        }

        public TextLinkScoreTable speedValue() {
            return new TextLinkScoreTable(ReportsDriversEnum.SPEED_SCORE_VALUE);
        }

        public TextLinkScoreTable styleValue() {
            return new TextLinkScoreTable(ReportsDriversEnum.STYLE_SCORE_VALUE);
        }
        
        public TextLinkScoreTable seatBeltValue() {
            return new TextLinkScoreTable(ReportsDriversEnum.SEATBELT_SCORE_VALUE);
        }
    }

    public class DriverReportTextFields extends ReportsBarTextFields {

        public TextField driver() {
            return new TextField(ReportsDriversEnum.DRIVER_TEXTFIELD);
        }

        public TextField employee() {
            return new TextField(ReportsDriversEnum.EMPLOYEE_TEXTFIELD);
        }

        public TextField group() {
            return new TextField(ReportsDriversEnum.GROUP_TEXTFIELD);
        }

        public TextField vehicle() {
            return new TextField(ReportsDriversEnum.VEHICLE_TEXTFIELD);
        }
    }

    public class DriverReportTexts extends ReportsBarTexts {

        public Text counter() {
            return new Text(ReportsBarEnum.COUNTER, page);
        }

        public TextTable distanceDrivenValue() {
            return new TextTable(ReportsDriversEnum.DISTANCE_DRIVEN_VALUE);
        }

        public TextTable employeeIDValue() {
            return new TextTable(ReportsDriversEnum.EMPLOYEE_ID_VALUE);
        }
    }

    private final String page = "drivers";

    public DriverReportButtons _button() {
        return new DriverReportButtons();
    }

    public DriverReportDropDowns _dropDown() {
        return new DriverReportDropDowns();
    }

    public DriverReportLinks _link() {
        return new DriverReportLinks();
    }

    public class DriverReportPopUps extends MastheadPopUps {

        public DriverReportPopUps() {
            super(page, Types.REPORT, 3);
        }

        public Email emailReport() {
            return new Email();
        }

        public EditColumns editColumns() {
            return new EditColumns();
        }
    }

    public DriverReportPopUps _popUp() {
        return new DriverReportPopUps();
    }

    public DriverReportTexts _text() {
        return new DriverReportTexts();
    }

    public DriverReportTextFields _textField() {
        return new DriverReportTextFields();
    }
    
    public class DriverReportPager{
        public Paging pageIndex(){
            return new Paging();
        }
    }
    
    
    public DriverReportPager _page(){
        return new DriverReportPager();
    }

    @Override
    public SeleniumEnums setUrl() {
        return ReportsDriversEnum.DEFAULT_URL;
    }

    @Override
    protected boolean checkIsOnPage() {
        return _link().editColumns().isPresent();
    }

}
