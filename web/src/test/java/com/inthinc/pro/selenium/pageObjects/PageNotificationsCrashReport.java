package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.Text;
import com.inthinc.pro.automation.elements.TextButton;
import com.inthinc.pro.automation.elements.TextLink;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.selenium.pageEnums.NotificationsCrashReportEnum;

public class PageNotificationsCrashReport extends NavigationBar {

    public PageNotificationsCrashReport() {
        // TODO Auto-generated constructor stub
    }

    public class NotificationsCrashReportLinks extends NavigationBarLinks {
    	
    	public TextLink back() {
    		return new TextLink(NotificationsCrashReportEnum.BACK);
    	}
    	
        public TextLink vehicle() {
            return new TextLink(NotificationsCrashReportEnum.VEHICLE);
        }

        public TextLink driver() {
            return new TextLink(NotificationsCrashReportEnum.DRIVER);
        }
    }

    public class NotificationsCrashReportTexts extends NavigationBarTexts {

        public Text headerCrashDetails() {
            return new Text(NotificationsCrashReportEnum.CRASH_DETAILS_HEADER);
        }

        public Text headerCrashRoute() {
            return new Text(NotificationsCrashReportEnum.CRASH_ROUTE_HEADER);
        }

        public Text headerCrashEvents() {
            return new Text(NotificationsCrashReportEnum.CRASH_EVENTS_HEADER);
        }

        public Text labelCrashReportStatus() {
            return new Text(NotificationsCrashReportEnum.CRASH_REPORT_STATUS);
        }

        public Text labelDateTime() {
            return new Text(NotificationsCrashReportEnum.DATE_TIME);
        }
        
        public Text labelVehicle() {
            return new Text(NotificationsCrashReportEnum.VEHICLE);
        }

        public Text labelDriver() {
            return new Text(NotificationsCrashReportEnum.DRIVER);
        }

        public Text labelWeather() {
            return new Text(NotificationsCrashReportEnum.WEATHER);
        }

        public Text labelOccupantCount() {
            return new Text(NotificationsCrashReportEnum.OCCUPANT_COUNT);
        }

        public Text messageDescription() {
            return new Text(NotificationsCrashReportEnum.DESCRIPTION_MESSAGE);
        }
        
        public Text crashReportStatus() {
        	return new Text(NotificationsCrashReportEnum.CRASH_REPORT_STATUS);
        }
        
        public Text dateTime() {
        	return new Text(NotificationsCrashReportEnum.DATE_TIME);
        }
        
        public Text weather() {
        	return new Text(NotificationsCrashReportEnum.WEATHER);
        }

        public Text occupantCount() {
        	return new Text(NotificationsCrashReportEnum.OCCUPANT_COUNT);
        }

        public Text description() {
        	return new Text(NotificationsCrashReportEnum.DESCRIPTION_MESSAGE);
        }
    }

    public class NotificationsCrashReportTextFields  extends NavigationBarTextFields {}

    public class NotificationsCrashReportButtons  extends NavigationBarButtons {

        public TextButton edit() {
            return new TextButton(NotificationsCrashReportEnum.EDIT);
        }

    }

    public class NotificationsCrashReportDropDowns  extends NavigationBarDropDowns {}

    public class NotificationsCrashReportPopUps extends MastheadPopUps {}

    public class NotificationsCrashReportPager {
        public Paging pageIndex() {
            return new Paging();
        }
    }

    public NotificationsCrashReportPager _page() {
        return new NotificationsCrashReportPager();
    }

    public NotificationsCrashReportLinks _link() {
        return new NotificationsCrashReportLinks();
    }

    public NotificationsCrashReportTexts _text() {
        return new NotificationsCrashReportTexts();
    }

    public NotificationsCrashReportButtons _button() {
        return new NotificationsCrashReportButtons();
    }

    public NotificationsCrashReportTextFields _textField() {
        return new NotificationsCrashReportTextFields();
    }

    public NotificationsCrashReportDropDowns _dropDown() {
        return new NotificationsCrashReportDropDowns();
    }

    public SeleniumEnums setUrl() {
        return NotificationsCrashReportEnum.DEFAULT_URL;
    }

    protected boolean checkIsOnPage() {
        return _link().back().isPresent()  &&
                _text().occupantCount().isPresent();
    }

}
