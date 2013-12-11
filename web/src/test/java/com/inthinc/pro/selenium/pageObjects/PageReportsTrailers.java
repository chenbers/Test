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
import com.inthinc.pro.selenium.pageObjects.ReportsBar.ReportsBarDropDowns;

public class PageReportsTrailers extends ReportsBar {
    
    public PageReportsTrailers() {
        checkMe.add(ReportsTrailersEnum.TITLE);
    }
    
    public class TrailerReportButtons extends ReportsBarButtons {
        
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
    
    public class TrailerReportDropDowns extends ReportsBarDropDowns {
        
        public DHXDropDown statusFilter() {
            return new ReportsBarDropDowns().score(ReportsBarEnum.STATUS_SCORE_DHX, page);
        }
        
        
        public DHXDropDown assignedStatusFilter() {
            return new ReportsBarDropDowns().score(ReportsBarEnum.ASSIGNED_STATUS_DHX, page);
        }
        
        public DHXDropDown entryMethodFilter() {
            return new ReportsBarDropDowns().score(ReportsBarEnum.ENTRY_METHOD_DHX, page);
        }
        
    }
    
    public class TrailerReportLinks extends ReportsBarLinks {
        
        public TextLink editColumns() {
            return new TextLink(PopUpEnum.EDIT_COLUMNS, page);
        }
        
        public TextLink sortByStatus() {
            return new TextLink(ReportsTrailersEnum.STATUS_SORT);
        }
        
        public TextLink sortByGroup() {
            return new TextLink(ReportsTrailersEnum.GROUP_SORT);
        }
        
        public TextLink sortByTrailerID() {
            return new TextLink(ReportsTrailersEnum.TRAILERID_SORT);
        }

        public TextLink sortByVehicleID() {
            return new TextLink(ReportsTrailersEnum.VEHICLE_SORT);
        }        

        public TextLink sortByDriver() {
            return new TextLink(ReportsTrailersEnum.DRIVER_SORT);
        }
        
        public TextLink sortByAssignedStatus() {
            return new TextLink(ReportsTrailersEnum.ASSIGNED_STATUS_SORT);
        }
        
        public TextLink sortByEntryMethod() {
            return new TextLink(ReportsTrailersEnum.ENTRY_METHOD_SORT);
        }

        public TextTableLink groupValue() {
            return new TextTableLink(ReportsTrailersEnum.GROUP_VALUE);
        }

        public TextLink driverValue() {
            return new TextLink(ReportsTrailersEnum.DRIVER_VALUE);
        }        
        
        public TextTableLink vehicleValue() {
            return new TextTableLink(ReportsTrailersEnum.VEHICLE_VALUE);
        }
        
    }
    
    public class TrailerReportTextFields extends ReportsBarTextFields {
        
        public TextField groupFilter() {
            return new TextField(ReportsTrailersEnum.GROUP_FILTER);
        }
        
        public TextField trailerFilter() {
            return new TextField(ReportsTrailersEnum.TRAILER_FILTER);
        }
        
        public TextField vehicleFilter() {
            return new TextField(ReportsTrailersEnum.VEHICLE_FILTER);
        }
        
        public TextField driverFilter() {
            return new TextField(ReportsTrailersEnum.DRIVER_FILTER);
        }
    }
    
    public class TrailerReportTexts extends ReportsBarTexts {
        
        public Text title() {
            return new Text(ReportsTrailersEnum.TITLE);
        }
        
        public Text counter() {
            return new Text(ReportsBarEnum.COUNTER, page);
        }
        
        public TextTable trailerValue() {
            return new TextTable(ReportsTrailersEnum.TRAILERID_VALUE);
        }
        
    }
    
    private final String page = "trailers";
    
    public TrailerReportButtons _button() {
        return new TrailerReportButtons();
    }
    
    public TrailerReportDropDowns _dropDown() {
        return new TrailerReportDropDowns();
    }
    
    public TrailerReportLinks _link() {
        return new TrailerReportLinks();
    }
    
    public class TrailerReportPopUps extends MastheadPopUps {
        public TrailerReportPopUps() {
            super(page, Types.REPORT, 3);
        }
        
        public Email emailReport() {
            return new Email();
        }
        
        public EditColumns editColumns() {
            return new EditColumns();
        }
    }
    
    public TrailerReportPopUps _popUp() {
        return new TrailerReportPopUps();
    }
    
    public TrailerReportTexts _text() {
        return new TrailerReportTexts();
    }
    
    public TrailerReportTextFields _textField() {
        return new TrailerReportTextFields();
    }
    
    public TrailerReportPager _page() {
        return new TrailerReportPager();
    }
    
    public class TrailerReportPager {
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
        return _link().editColumns().isPresent() && _link().sortByStatus().isPresent();
    }
}