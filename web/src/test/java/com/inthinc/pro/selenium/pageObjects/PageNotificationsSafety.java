package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.Button;
import com.inthinc.pro.automation.elements.ButtonTable;
import com.inthinc.pro.automation.elements.DhxDropDown;
import com.inthinc.pro.automation.elements.DropDown;
import com.inthinc.pro.automation.elements.Text;
import com.inthinc.pro.automation.elements.TextButton;
import com.inthinc.pro.automation.elements.TextField;
import com.inthinc.pro.automation.elements.TextLink;
import com.inthinc.pro.automation.elements.TextTable;
import com.inthinc.pro.selenium.pageEnums.NotificationsBarEnum;
import com.inthinc.pro.selenium.pageEnums.NotificationsRedFlagsEnum;
import com.inthinc.pro.selenium.pageEnums.NotificationsSafetyEnum;

public class PageNotificationsSafety extends NotificationsBar {

    private static String page = "safety";

    public PageNotificationsSafety() {
	super.setPage(page);
	url = NotificationsRedFlagsEnum.DEFAULT_URL;
    }

    public class NotificationsSafetyLinks extends NotificationsBarLinks {

	public TextLink editColumns() {
	    return new TextLink(NotificationsBarEnum.EDIT_COLUMNS, page);
	}

	public TextLink alertDetailsEntry() {
	    return new TextLink(NotificationsBarEnum.DETAILS_ENTRY, page);
	}

	public TextLink groupEntry() {
	    return new TextLink(NotificationsBarEnum.GROUP_ENTRY, page);
	}

	public TextLink driverEntry() {
	    return new TextLink(NotificationsBarEnum.DRIVER_ENTRY, page);
	}

	public TextLink vehicleEntry() {
	    return new TextLink(NotificationsBarEnum.VEHICLE_ENTRY, page);
	}

	public TextLink statusEntry() {
	    return new TextLink(NotificationsBarEnum.STATUS_ENTRY, page);
	}

	public TextLink sortByGroup(){
	    return new TextLink(NotificationsBarEnum.SORT_GROUP, page);
	}
	
	public TextLink sortByDriver(){
	    return new TextLink(NotificationsBarEnum.SORT_DRIVER, page);
	}
	
	public TextLink sortByVehicle(){
	    return new TextLink(NotificationsBarEnum.SORT_VEHICLE, page);
	}
	
	public TextLink sortByDateTime(){
	    return new TextLink(NotificationsBarEnum.SORT_DATE_TIME, page);
	}
    }

    public class NotificationsSafetyTexts extends NotificationsBarTexts {
	
	
	public Text headerAlertDetails(){
	    return new Text(NotificationsBarEnum.HEADER_DETAILS, page);
	}
	
	public Text headerDetail(){
	    return new Text(NotificationsBarEnum.HEADER_DETAIL, page);
	}
	
	public Text headerStatus(){
	    return new Text(NotificationsBarEnum.HEADER_STATUS, page);
	}
	
	public Text headerCategory(){
	    return new Text(NotificationsBarEnum.HEADER_CATEGORY, page);
	}

	public TextTable dateTimeEntry() {
	    return new TextTable(NotificationsBarEnum.DATE_TIME_ENTRY, page);
	}

	public TextTable categoryEntry() {
	    return new TextTable(NotificationsBarEnum.CATEGORY_ENTRY, page);
	}

	public TextTable detailEntry() {
	    return new TextTable(NotificationsBarEnum.DETAIL_ENTRY, page);
	}

	public Text counter() {
	    return new Text(NotificationsBarEnum.COUNTER, page);
	}

	public Text title() {
	    return new Text(NotificationsSafetyEnum.MAIN_TITLE);
	}

	public Text note() {
	    return new Text(NotificationsSafetyEnum.MAIN_TITLE_COMMENT);
	}

    }

    public class NotificationsSafetyTextFields extends
	    NotificationsBarTextFields {

	public TextField group() {
	    return new TextField(NotificationsBarEnum.GROUP_FILTER, page);
	}

	public TextField driver() {
	    return new TextField(NotificationsBarEnum.DRIVER_FILTER, page);
	}

	public TextField vehicle() {
	    return new TextField(NotificationsBarEnum.VEHICLE_FILTER, page);
	}
    }

    public class NotificationsSafetyButtons extends NotificationsBarButtons {

	public TextButton refresh() {
	    return new TextButton(NotificationsBarEnum.REFRESH, page);
	}

	public Button tools() {
	    return new Button(NotificationsBarEnum.TOOLS, page);
	}

	public Button exportToPDF() {
	    return new Button(NotificationsBarEnum.EXPORT_TO_PDF, page);
	}

	public Button emailReport() {
	    return new Button(NotificationsBarEnum.EMAIL_REPORT, page);
	}

	public Button exportToExcel() {
	    return new Button(NotificationsBarEnum.EXPORT_TO_EXCEL, page);
	}

	public ButtonTable eventLocationEntry() {
	    return new ButtonTable(NotificationsBarEnum.LOCATION, page);
	}

    }

    public class NotificationsSafetyDropDowns extends NotificationsBarDropDowns {

	public DhxDropDown team() {
	    return new DhxDropDown(NotificationsBarEnum.TEAM_SELECTION_DHX,
		    page);
	}

	public DhxDropDown timeFrame() {
	    return new DhxDropDown(NotificationsBarEnum.TIME_FRAME_DHX, page);
	}

	public DhxDropDown levelFilter() {
	    return new DhxDropDown(NotificationsBarEnum.LEVEL_FILTER_DHX, page);
	}

	public DropDown category() {
	    return new DropDown(NotificationsBarEnum.CATEGORY_FILTER, page);
	}

	public DropDown statusFilter() {
	    return new DropDown(NotificationsBarEnum.STATUS_FILTER, page);
	}

    }

    public class NotificationsSafetyPopUps extends MastheadPopUps {
	public NotificationsSafetyPopUps(){
	    super(page, Types.REPORT, 3 );
	}
	
	public EditColumns editColumns(){
	    return new EditColumns();
	}
	
	public Email emailReport(){
	    return new Email();
	}
    }

    public NotificationsSafetyLinks _link() {
	return new NotificationsSafetyLinks();
    }

    public NotificationsSafetyTexts _text() {
	return new NotificationsSafetyTexts();
    }

    public NotificationsSafetyButtons _button() {
	return new NotificationsSafetyButtons();
    }

    public NotificationsSafetyTextFields _textField() {
	return new NotificationsSafetyTextFields();
    }

    public NotificationsSafetyDropDowns _dropDown() {
	return new NotificationsSafetyDropDowns();
    }

    public NotificationsSafetyPopUps _popUp() {
	return new NotificationsSafetyPopUps();
    }

    public NotificationsSafetyPager _page() {
	return new NotificationsSafetyPager();
    }

    public class NotificationsSafetyPager {

	public Paging pageIndex() {
	    return new Paging();
	}
    }

}
