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
import com.inthinc.pro.selenium.pageEnums.ReportsTrailersEnum;

public class PageReportsTrailers extends ReportsBar {
    
    public PageReportsTrailers() {
        checkMe.add(ReportsTrailersEnum.TITLE);
    }
    
    public class VehicleReportButtons extends ReportsBarButtons {
        
        public TextButton emailReport() {
            return new TextButton(ReportsBarEnum.TOOL_EMAIL, page);
        }
        
        public TextButton exportExcel() {
            return new TextButton(ReportsBarEnum.TOOL_EXCEL, page);
        }
        
        public TextButton exportPdf() {
            return new TextButton(ReportsBarEnum.TOOL_PDF, page);
        }
        
        public Button tools() {
            return new Button(PopUpEnum.TOOL_BUTTON, page);
        }
    }
    
    public class VehicleReportDropDowns extends ReportsBarDropDowns {
        
        public DHXDropDown overallFilter() {
            return new ReportsBarDropDowns().score(ReportsBarEnum.OVERALL_SCORE_DHX, page);
        }
        
        public DHXDropDown speedFilter() {
            return new ReportsBarDropDowns().score(ReportsBarEnum.SPEED_SCORE_DHX, page);
        }
        
        public DHXDropDown styleFilter() {
            return new ReportsBarDropDowns().score(ReportsBarEnum.STYLE_SCORE_DHX, page);
        }
        
    }
    
    public class VehicleReportLinks extends ReportsBarLinks {
        
        public TextLink editColumns() {
            return new TextLink(PopUpEnum.EDIT_COLUMNS, page);
        }
        
        public TextLink sortByTrailer() {
            return new TextLink(ReportsTrailersEnum.TRAILERID_SORT);
        }
        
        public TextLink sortByDistanceDriven() {
            return new TextLink(ReportsTrailersEnum.DISTANCE_DRIVEN_SORT);
        }
        
        public TextLink sortByDriver() {
            return new TextLink(ReportsTrailersEnum.DRIVER_SORT);
        }
        
        public TextLink driverValue() {
            return new TextLink(ReportsTrailersEnum.DRIVER_VALUE);
        }
        
        public TextLink sortByGroup() {
            return new TextLink(ReportsTrailersEnum.GROUP_SORT);
        }
        
        public TextTableLink groupValue() {
            return new TextTableLink(ReportsTrailersEnum.GROUP_VALUE);
        }
        
        public TextLink sortByOdometer() {
            return new TextLink(ReportsTrailersEnum.ODOMETER_SORT);
        }
        
        public TextLink sortByOverall() {
            return new TextLink(ReportsTrailersEnum.OVERALL_SCORE_SORT);
        }
        
        public TextLinkScoreTable overallValue() {
            return new TextLinkScoreTable(ReportsTrailersEnum.OVERALL_SCORE_VALUE);
        }
        
        public TextLink sortBySpeed() {
            return new TextLink(ReportsTrailersEnum.SPEED_SCORE_SORT);
        }
        
        public TextLinkScoreTable speedValue() {
            return new TextLinkScoreTable(ReportsTrailersEnum.SPEED_SCORE_VALUE);
        }
        
        public TextLink sortByStyle() {
            return new TextLink(ReportsTrailersEnum.STYLE_SCORE_SORT);
        }
        
        public TextLinkScoreTable styleValue() {
            return new TextLinkScoreTable(ReportsTrailersEnum.STYLE_SCORE_VALUE);
        }
        
        public TextLink sortByVehicleID() {
            return new TextLink(ReportsTrailersEnum.VEHICLE_SORT);
        }
        
        public TextTableLink vehicleValue() {
            return new TextTableLink(ReportsTrailersEnum.VEHICLE_VALUE);
        }
        
        public TextLink sortByYearMakeModel() {
            return new TextLink(ReportsTrailersEnum.YEAR_MAKE_MODEL);
        }
        
    }
    
    public class VehicleReportTextFields extends ReportsBarTextFields {
        
        public TextField groupFilter() {
            return new TextField(ReportsTrailersEnum.GROUP_FILTER);
        }
        
        public TextField trailerFilter() {
            return new TextField(ReportsTrailersEnum.TRAILER_FILTER);
        }
        
        public TextField vehicleFilter() {
            return new TextField(ReportsTrailersEnum.VEHICLE_FILTER);
        }
        
        public TextField yearMakeModelFilter() {
            return new TextField(ReportsTrailersEnum.YEAR_MAKE_MODEL_FILTER);
        }
        
        public TextField driverFilter() {
            return new TextField(ReportsTrailersEnum.DRIVER_FILTER);
        }
    }
    
    public class VehicleReportTexts extends ReportsBarTexts {
        
        public Text title() {
            return new Text(ReportsTrailersEnum.TITLE);
        }
        
        public Text counter() {
            return new Text(ReportsBarEnum.COUNTER, page);
        }
        
        public TextTable trailerValue() {
            return new TextTable(ReportsTrailersEnum.TRAILERID_VALUE);
        }
        
        public TextTable distanceDrivenValue() {
            return new TextTable(ReportsTrailersEnum.DISTANCE_DRIVEN_VALUE);
        }
        
        public TextTable odometerValue() {
            return new TextTable(ReportsTrailersEnum.ODOMETER_VALUE);
        }
        
        public TextTable yearMakeModelValue() {
            return new TextTable(ReportsTrailersEnum.YEAR_MAKE_MODEL_VALUE);
        }
        
    }
    
    private final String page = "vehicles";
    
    public VehicleReportButtons _button() {
        return new VehicleReportButtons();
    }
    
    public VehicleReportDropDowns _dropDown() {
        return new VehicleReportDropDowns();
    }
    
    public VehicleReportLinks _link() {
        return new VehicleReportLinks();
    }
    
    public class VehicleReportPopUps extends MastheadPopUps {
        public VehicleReportPopUps() {
            super(page, Types.REPORT, 3);
        }
        
        public Email emailReport() {
            return new Email();
        }
        
        public EditColumns editColumns() {
            return new EditColumns();
        }
    }
    
    public VehicleReportPopUps _popUp() {
        return new VehicleReportPopUps();
    }
    
    public VehicleReportTexts _text() {
        return new VehicleReportTexts();
    }
    
    public VehicleReportTextFields _textField() {
        return new VehicleReportTextFields();
    }
    
    public VehicleReportPager _page() {
        return new VehicleReportPager();
    }
    
    public class VehicleReportPager {
        public Paging pageIndex() {
            return new Paging();
        }
    }
    
    @Override
    public SeleniumEnums setUrl() {
        return ReportsTrailersEnum.DEFAULT_URL;
    }
    
    @Override
    protected boolean checkIsOnPage() {
        return _link().editColumns().isPresent();
    }
}