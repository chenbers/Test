package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.Button;
import com.inthinc.pro.automation.elements.CalendarObject;
import com.inthinc.pro.automation.elements.CheckBox;
import com.inthinc.pro.automation.elements.CheckBoxTable;
import com.inthinc.pro.automation.elements.DropDown;
import com.inthinc.pro.automation.elements.Text;
import com.inthinc.pro.automation.elements.TextButton;
import com.inthinc.pro.automation.elements.TextFieldLabel;
import com.inthinc.pro.automation.elements.TextFieldSuggestions;
import com.inthinc.pro.automation.elements.TextLink;
import com.inthinc.pro.automation.elements.TextTable;
import com.inthinc.pro.automation.elements.TextTableLink;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.selenium.pageEnums.HOSFuelStopsEnum;

public class PageHOSFuelStops extends HOSBar {

    public class FuelStopsButtons extends HOSBarButtons {

        public TextButton add() {
            return new TextButton(HOSFuelStopsEnum.ADD);
        }

        public TextButton delete() {
            return new TextButton(HOSFuelStopsEnum.DELETE_BUTTON);
        }

        public Button editColumns() {
            return new Button(HOSFuelStopsEnum.EDIT_COLUMNS);
        }

        public TextButton refresh() {
            return new TextButton(HOSFuelStopsEnum.REFRESH);
        }
    }

    public class FuelStopsCheckBoxs {
        public CheckBox checkAll() {
            return new CheckBox(HOSFuelStopsEnum.CHECK_ALL);
        }

        public CheckBoxTable entryCheckBox() {
            return new CheckBoxTable(HOSFuelStopsEnum.ENTRY_CHECKBOX);
        }
    }

    public class FuelStopsDropDowns extends HOSBarDropDowns {
        
        public DropDown startDate(){
            return new CalendarObject(HOSFuelStopsEnum.START_DATE_BOX);
        }
        
        public DropDown endDate(){
            return new CalendarObject(HOSFuelStopsEnum.END_DATE_BOX);
        }
        
    }

    public class FuelStopsLinks extends HOSBarLinks {

        public TextLink sortByDateTime() {
            return new TextLink(HOSFuelStopsEnum.SORT_DATE_TIME);
        }
        
        public TextLink sortByDriver() {
            return new TextLink(HOSFuelStopsEnum.SORT_DRIVER);
        }

        public TextLink sortByEdited() {
            return new TextLink(HOSFuelStopsEnum.SORT_EDITED);
        }

        public TextLink sortByLocation() {
            return new TextLink(HOSFuelStopsEnum.SORT_LOCATION);
        }

        public TextLink sortByOdometerAfter() {
            return new TextLink(HOSFuelStopsEnum.SORT_AFTER);
        }

        public TextLink sortByOdometerBefore() {
            return new TextLink(HOSFuelStopsEnum.SORT_BEFORE);
        }

        public TextLink sortByTrailer() {
            return new TextLink(HOSFuelStopsEnum.SORT_TRAILER);
        }

        public TextLink sortByTrailerFuel() {
            return new TextLink(HOSFuelStopsEnum.SORT_TRAILER_FUEL);
        }

        public TextLink sortByVehicle() {
            return new TextLink(HOSFuelStopsEnum.SORT_VEHICLE);
        }

        public TextLink sortByVehicleFuel() {
            return new TextLink(HOSFuelStopsEnum.SORT_VEHICLE_FUEL);
        }

        public TextTableLink entryEdit() {
            return new TextTableLink(HOSFuelStopsEnum.ENTRY_EDIT);
        }

    }

    public class FuelStopsPager {

        public Paging pageIndex() {
            return new Paging();
        }
    }

    public class FuelStopsPopUps extends MastheadPopUps {
        public FuelStopsPopUps() {
            super(page);
        }

        public FuelStopsDelete delete() {
            return new FuelStopsDelete();
        }

        public EditColumns editColumns() {
            return new EditColumns();
        }
    }

    public class FuelStopsTextFields extends HOSBarTextFields {

        public TextFieldSuggestions vehicle() {
            return new TextFieldSuggestions(HOSFuelStopsEnum.VEHICLE_TEXT_FIELD, HOSFuelStopsEnum.VEHICLE_SUGGESTION_BOX);
        }
    }

    public class FuelStopsTexts extends HOSBarTexts {

        public Text counter() {
            return new Text(HOSFuelStopsEnum.COUNTER);
        }

        public Text labelDateRange() {
            return new Text(HOSFuelStopsEnum.START_DATE_BOX);
        }

        public Text labelVehicleField() {
            return new TextFieldLabel(HOSFuelStopsEnum.VEHICLE_TEXT_FIELD);
        }

        public TextTable entryDateTime() {
            return new TextTable(HOSFuelStopsEnum.ENTRY_DATE_TIME);
        }
        
        public TextTable entryDriver() {
            return new TextTable(HOSFuelStopsEnum.ENTRY_DRIVER);
        }

        public Text title() {
            return new Text(HOSFuelStopsEnum.TITLE);
        }

        public TextTable entryEdited() {
            return new TextTable(HOSFuelStopsEnum.ENTRY_EDITED);
        }

        public TextTable entryLocation() {
            return new TextTable(HOSFuelStopsEnum.ENTRY_LOCATION);
        }

        public TextTable entryOdometerAfter() {
            return new TextTable(HOSFuelStopsEnum.ENTRY_AFTER);
        }

        public TextTable entryOdometerBefore() {
            return new TextTable(HOSFuelStopsEnum.ENTRY_BEFORE);
        }

        public TextTable entryTrailer() {
            return new TextTable(HOSFuelStopsEnum.ENTRY_TRAILER);
        }

        public TextTable entryTrailerFuel() {
            return new TextTable(HOSFuelStopsEnum.ENTRY_TRAILER_FUEL);
        }

        public TextTable entryVehicle() {
            return new TextTable(HOSFuelStopsEnum.ENTRY_VEHICLE);
        }

        public TextTable entryVehicleFuel() {
            return new TextTable(HOSFuelStopsEnum.ENTRY_VEHICLE_FUEL);
        }
    }

    private static final String page = "fuelStopsTable";

    public FuelStopsButtons _button() {
        return new FuelStopsButtons();
    }

    public FuelStopsCheckBoxs _checkBox() {
        return new FuelStopsCheckBoxs();
    }

    public FuelStopsDropDowns _dropDown() {
        return new FuelStopsDropDowns();
    }

    public FuelStopsLinks _link() {
        return new FuelStopsLinks();
    }

    public FuelStopsPager _page() {
        return new FuelStopsPager();
    }

    public FuelStopsPopUps _popUp() {
        return new FuelStopsPopUps();
    }

    public FuelStopsTexts _text() {
        return new FuelStopsTexts();
    }

    public FuelStopsTextFields _textField() {
        return new FuelStopsTextFields();
    }
    
    @Override
    public SeleniumEnums setUrl() {
        return HOSFuelStopsEnum.DEFAULT_URL;
    }

    @Override
    protected boolean checkIsOnPage() {
        return _button().refresh().isPresent() && _textField().vehicle().isPresent();
    }

}
