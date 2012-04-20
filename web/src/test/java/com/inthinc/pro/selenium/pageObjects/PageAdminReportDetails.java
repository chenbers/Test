package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.Text;
import com.inthinc.pro.automation.elements.TextButton;
import com.inthinc.pro.automation.elements.TextLabel;
import com.inthinc.pro.automation.elements.TextLink;
import com.inthinc.pro.automation.enums.SeleniumEnumWrapper;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.selenium.pageEnums.AdminBarEnum;
import com.inthinc.pro.selenium.pageEnums.AdminReportDetailsEnum;
import com.inthinc.pro.selenium.pageEnums.AdminTables.ReportsColumns;

public class PageAdminReportDetails extends AdminBar {

    public PageAdminReportDetails() {
        checkMe.add(AdminReportDetailsEnum.NAME_STATUS_TIME);
        checkMe.add(AdminReportDetailsEnum.REPORT_OCCURANCE);
        checkMe.add(AdminReportDetailsEnum.REPORT_SETTINGS);
        checkMe.add(AdminReportDetailsEnum.EMAIL_SECTION);
    }

    public ReportDetailsPopUps _popUp() {
        return new ReportDetailsPopUps();
    }

    public class ReportDetailsPopUps extends AdminBarPopUps {

        public ReportDetailsPopUps() {
            super(page);
        }

        public AdminDelete deleteReport() {
            return new AdminDelete(false);
        }
    }

    public class ReportDetailsButtons extends AdminBarButtons {

        public TextButton delete() {
            return new TextButton(AdminBarEnum.DETAILS_DELETE, page);
        }

        public TextButton edit() {
            return new TextButton(AdminBarEnum.EDIT, page);
        }
    }

    public class ReportDetailsDropDowns extends AdminBarDropDowns {}

    public class ReportDetailsLinks extends AdminBarLinks {

        public TextLink backToReports() {
            return new TextLink(AdminBarEnum.GO_BACK, page);
        }
    }

    public class ReportDetailsTextFields extends AdminBarTextFields {}

    public class ReportDetailsTexts extends AdminBarTexts {

        public TextLabel labels(ReportsColumns value) {
            SeleniumEnumWrapper temp = new SeleniumEnumWrapper(value);
            temp.setID("display-form:"+temp.getIDs()[0]);
            return new TextLabel(temp);
        }

        public Text values(ReportsColumns value) {
            SeleniumEnumWrapper temp = new SeleniumEnumWrapper(value);
            temp.setID("display-form:"+temp.getIDs()[0]);
            return new Text(temp);
        }

    }

    private static String page = "person";

    public ReportDetailsButtons _button() {
        return new ReportDetailsButtons();
    }

    public ReportDetailsDropDowns _dropDown() {
        return new ReportDetailsDropDowns();
    }

    public ReportDetailsLinks _link() {
        return new ReportDetailsLinks();
    }

    public ReportDetailsTexts _text() {
        return new ReportDetailsTexts();
    }

    public ReportDetailsTextFields _textField() {
        return new ReportDetailsTextFields();
    }

    @Override
    public SeleniumEnums setUrl() {
        return AdminReportDetailsEnum.URL;
    }

    @Override
    protected boolean checkIsOnPage() {
        // TODO Auto-generated method stub
        return false;
    }
}
