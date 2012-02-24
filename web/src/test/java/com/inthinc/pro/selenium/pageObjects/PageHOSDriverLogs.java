package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.Button;
import com.inthinc.pro.automation.elements.Calendar;
import com.inthinc.pro.automation.elements.Text;
import com.inthinc.pro.automation.elements.TextButton;
import com.inthinc.pro.automation.elements.TextDateFieldLabel;
import com.inthinc.pro.automation.elements.TextFieldSuggestions;
import com.inthinc.pro.automation.elements.TextLinkTableHeader;
import com.inthinc.pro.automation.elements.TextTable;
import com.inthinc.pro.selenium.pageEnums.HOSDriverLogsEnum;

public class PageHOSDriverLogs extends HOSBar {

    private final static String page = "hosTable";
    

    public PageHOSDriverLogs() {
        url = HOSDriverLogsEnum.DEFAULT_URL;
        
        checkMe.add(HOSDriverLogsEnum.TITLE);
        checkMe.add(HOSDriverLogsEnum.REFRESH);
        checkMe.add(HOSDriverLogsEnum.ADD);
        checkMe.add(HOSDriverLogsEnum.BATCH_EDIT);
    }
    
    public class HOSDriverLogsLinks extends HOSBarLinks {
        
        public TextLinkTableHeader entryDateTime() {
            return new TextLinkTableHeader(HOSDriverLogsEnum.ENTRY_DATE_TIME);
        }
        
        public TextLinkTableHeader entryDriver() {
            return new TextLinkTableHeader(HOSDriverLogsEnum.ENTRY_DRIVER);
        }
        
        public TextLinkTableHeader entryVehicle() {
            return new TextLinkTableHeader(HOSDriverLogsEnum.ENTRY_VEHICLE);
        }
        
        public TextLinkTableHeader entryService() {
            return new TextLinkTableHeader(HOSDriverLogsEnum.ENTRY_SERVICE);
        }
        
        public TextLinkTableHeader entryTrailer() {
            return new TextLinkTableHeader(HOSDriverLogsEnum.ENTRY_LOCATION);
        }
        
        public TextLinkTableHeader entryStatus() {
            return new TextLinkTableHeader(HOSDriverLogsEnum.ENTRY_STATUS);
        }
        
        public TextLinkTableHeader entryEdited() {
            return new TextLinkTableHeader(HOSDriverLogsEnum.ENTRY_EDITED);
        }
    }

    public class HOSDriverLogsTexts extends HOSBarTexts {
        
        public TextTable entryDateTime() {
            return new TextTable(HOSDriverLogsEnum.ENTRY_DATE_TIME);
        }
        
        public TextTable entryDriver() {
            return new TextTable(HOSDriverLogsEnum.ENTRY_DRIVER);
        }
        
        public TextTable entryVehicle() {
            return new TextTable(HOSDriverLogsEnum.ENTRY_VEHICLE);
        }
        
        public TextTable entryService() {
            return new TextTable(HOSDriverLogsEnum.ENTRY_SERVICE);
        }
        
        public TextTable entryTrailer() {
            return new TextTable(HOSDriverLogsEnum.ENTRY_TRAILER);
        }
        
        public TextTable entryStatus() {
            return new TextTable(HOSDriverLogsEnum.ENTRY_STATUS);
        }
        
        public TextTable entryEdited() {
            return new TextTable(HOSDriverLogsEnum.ENTRY_EDITED);
        }
        
        public Text sendLogsMessage() {
            return new Text(HOSDriverLogsEnum.SHIP_LOGS_MESSAGE);
        }
        
        public Text dateError() {
            return new Text(HOSDriverLogsEnum.DATE_ERROR);
        }
        
        public Text counter() {
            return new Text(HOSDriverLogsEnum.COUNTER);
        }
        
        public TextDateFieldLabel labelDateRange() {
            return new TextDateFieldLabel(HOSDriverLogsEnum.START_FIELD);
        }
        
        public Text title() {
            return new Text(HOSDriverLogsEnum.TITLE);
        }

		public TextTable entryLocation() {
			return new TextTable(HOSDriverLogsEnum.ENTRY_LOCATION);
		}
    }

    public class HOSDriverLogsTextFields extends HOSBarTextFields {
        
        public TextFieldSuggestions driver() {
            return new TextFieldSuggestions(HOSDriverLogsEnum.DRIVER_FIELD, HOSDriverLogsEnum.DRIVER_SUGGESTION);
        }
    }
    
    
    public DriverLogsSelectors _dateSelector(){
        return new DriverLogsSelectors();
    }
    
    public class DriverLogsSelectors{
        public Calendar startDate() {
            return new Calendar(HOSDriverLogsEnum.START_FIELD);
        }
        
        public Calendar stopDate() {
            return new Calendar(HOSDriverLogsEnum.STOP_FIELD);
        }

    }

    public class HOSDriverLogsButtons extends HOSBarButtons {
        
        public TextButton refresh() {
            return new TextButton(HOSDriverLogsEnum.REFRESH);
        }
        
        public TextButton add() {
            return new TextButton(HOSDriverLogsEnum.ADD);
        }
        
        public TextButton batchEdit() {
            return new TextButton(HOSDriverLogsEnum.BATCH_EDIT);
        }
        
        public Button editColumns() {
            return new Button(HOSDriverLogsEnum.EDIT_COLUMNS);
        }
        
        public Button shipLogs() {
            return new Button(HOSDriverLogsEnum.SHIP_LOGS);
        }
    }

    public class HOSDriverLogsDropDowns extends HOSBarDropDowns {}

    public class HOSDriverLogsPopUps extends MastheadPopUps {
        
        public HOSDriverLogsPopUps(){
            super(page);
        }
        
        public EditColumns editColumns(){
            return new EditColumns();
        }
    }

    public class HOSDriverLogsPager {
        public Paging pageIndex() {
            return new Paging();
        }
    }

    public HOSDriverLogsPager _page() {
        return new HOSDriverLogsPager();
    }

    public HOSDriverLogsLinks _link() {
        return new HOSDriverLogsLinks();
    }

    public HOSDriverLogsTexts _text() {
        return new HOSDriverLogsTexts();
    }

    public HOSDriverLogsButtons _button() {
        return new HOSDriverLogsButtons();
    }

    public HOSDriverLogsTextFields _textField() {
        return new HOSDriverLogsTextFields();
    }

    public HOSDriverLogsDropDowns _dropDown() {
        return new HOSDriverLogsDropDowns();
    }

    public HOSDriverLogsPopUps _popUp() {
        return new HOSDriverLogsPopUps();
    }

}
