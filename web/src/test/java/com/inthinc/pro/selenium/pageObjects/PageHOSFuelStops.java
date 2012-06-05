package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.Button;
import com.inthinc.pro.automation.elements.CheckBox;
import com.inthinc.pro.automation.elements.CheckBoxTable;
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
            return new CheckBoxTable(HOSFuelStopsEnum.VALUE_CHECK);
        }
    }

    public class FuelStopsDropDowns extends HOSBarDropDowns {}

    public class FuelStopsLinks extends HOSBarLinks {

        public TextLink sortDateTime() {
            return new TextLink(HOSFuelStopsEnum.SORT_DATE_TIME);
        }
        
        public TextLink sortDriver() {
            return new TextLink(HOSFuelStopsEnum.SORT_DRIVER);
        }

        public TextLink sortEdited() {
            return new TextLink(HOSFuelStopsEnum.SORT_EDITED);
        }

        public TextLink sortLocation() {
            return new TextLink(HOSFuelStopsEnum.SORT_LOCATION);
        }

        public TextLink sortOdometerAfter() {
            return new TextLink(HOSFuelStopsEnum.SORT_AFTER);
        }

        public TextLink sortOdometerBefore() {
            return new TextLink(HOSFuelStopsEnum.SORT_BEFORE);
        }

        public TextLink sortTrailer() {
            return new TextLink(HOSFuelStopsEnum.SORT_TRAILER);
        }

        public TextLink sortTrailerFuel() {
            return new TextLink(HOSFuelStopsEnum.SORT_TRAILER_FUEL);
        }

        public TextLink sortVehicle() {
            return new TextLink(HOSFuelStopsEnum.SORT_VEHICLE);
        }

        public TextLink sortVehicleFuel() {
            return new TextLink(HOSFuelStopsEnum.SORT_VEHICLE_FUEL);
        }

        public TextTableLink valueEdit() {
            return new TextTableLink(HOSFuelStopsEnum.VALUE_EDIT);
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
            return new Text(HOSFuelStopsEnum.DATE_RANGE_LABEL);
        }

        public Text labelVehicleField() {
            return new TextFieldLabel(HOSFuelStopsEnum.VEHICLE_TEXT_FIELD);
        }

        public TextTable dateTime() {
            return new TextTable(HOSFuelStopsEnum.VALUE_DATE_TIME);
        }
        
        public TextTable valueDriver() {
            return new TextTable(HOSFuelStopsEnum.VALUE_DRIVER);
        }

        public Text title() {
            return new Text(HOSFuelStopsEnum.TITLE);
        }

        public TextTable valueEdited() {
            return new TextTable(HOSFuelStopsEnum.VALUE_EDITED);
        }

        public TextTable valueLocation() {
            return new TextTable(HOSFuelStopsEnum.VALUE_LOCATION);
        }

        public TextTable valueOdometerAfter() {
            return new TextTable(HOSFuelStopsEnum.VALUE_AFTER);
        }

        public TextTable valueOdometerBefore() {
            return new TextTable(HOSFuelStopsEnum.VALUE_BEFORE);
        }

        public TextTable valueTrailer() {
            return new TextTable(HOSFuelStopsEnum.VALUE_TRAILER);
        }

        public TextTable valueTrailerFuel() {
            return new TextTable(HOSFuelStopsEnum.VALUE_TRAILER_FUEL);
        }

        public TextTable valueVehicle() {
            return new TextTable(HOSFuelStopsEnum.VALUE_VEHICLE);
        }

        public TextTable valueVehicleFuel() {
            return new TextTable(HOSFuelStopsEnum.VALUE_VEHICLE_FUEL);
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
