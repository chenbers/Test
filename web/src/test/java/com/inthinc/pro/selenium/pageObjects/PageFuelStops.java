package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.Button;
import com.inthinc.pro.automation.elements.CheckBox;
import com.inthinc.pro.automation.elements.CheckBoxTable;
import com.inthinc.pro.automation.elements.Text;
import com.inthinc.pro.automation.elements.TextButton;
import com.inthinc.pro.automation.elements.TextField;
import com.inthinc.pro.automation.elements.TextFieldLabel;
import com.inthinc.pro.automation.elements.TextFieldSuggestions;
import com.inthinc.pro.automation.elements.TextLink;
import com.inthinc.pro.automation.elements.TextTable;
import com.inthinc.pro.automation.elements.TextTableLink;
import com.inthinc.pro.selenium.pageEnums.FuelStopsEnum;

public class PageFuelStops extends HOSBar {

    public class FuelStopsButtons extends HOSBarButtons {

        public TextButton add() {
            return new TextButton(FuelStopsEnum.ADD);
        }

        public TextButton delete() {
            return new TextButton(FuelStopsEnum.DELETE_BUTTON);
        }

        public Button editColumns() {
            return new Button(FuelStopsEnum.EDIT_COLUMNS);
        }

        public TextButton refresh() {
            return new TextButton(FuelStopsEnum.REFRESH);
        }
    }

    public class FuelStopsCheckBoxs {
        public CheckBox checkAll() {
            return new CheckBox(FuelStopsEnum.CHECK_ALL);
        }

        public CheckBoxTable entryCheckBox() {
            return new CheckBoxTable(FuelStopsEnum.VALUE_CHECK);
        }
    }

    public class FuelStopsDropDowns extends HOSBarDropDowns {}

    public class FuelStopsLinks extends HOSBarLinks {

        public TextLink sortDateTime() {
            return new TextLink(FuelStopsEnum.SORT_DATE_TIME);
        }

        public TextLink sortEdited() {
            return new TextLink(FuelStopsEnum.SORT_EDITED);
        }

        public TextLink sortLocation() {
            return new TextLink(FuelStopsEnum.SORT_LOCATION);
        }

        public TextLink sortOdometerAfter() {
            return new TextLink(FuelStopsEnum.SORT_AFTER);
        }

        public TextLink sortOdometerBefore() {
            return new TextLink(FuelStopsEnum.SORT_BEFORE);
        }

        public TextLink sortTrailer() {
            return new TextLink(FuelStopsEnum.SORT_TRAILER);
        }

        public TextLink sortTrailerFuel() {
            return new TextLink(FuelStopsEnum.SORT_TRAILER_FUEL);
        }

        public TextLink sortVehicle() {
            return new TextLink(FuelStopsEnum.SORT_VEHICLE);
        }

        public TextLink sortVehicleFuel() {
            return new TextLink(FuelStopsEnum.SORT_VEHICLE_FUEL);
        }

        public TextTableLink valueEdit() {
            return new TextTableLink(FuelStopsEnum.VALUE_EDIT);
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

        public TextField dateStart() {
            return new TextField(FuelStopsEnum.DATE_START_BOX);
        }

        public TextField dateStop() {
            return new TextField(FuelStopsEnum.DATE_STOP_BOX);
        }

        public TextFieldSuggestions vehicle() {
            return new TextFieldSuggestions(FuelStopsEnum.VEHICLE_TEXT_FIELD, FuelStopsEnum.VEHICLE_SUGGESTION_BOX);
        }
    }

    public class FuelStopsTexts extends HOSBarTexts {

        public Text counter() {
            return new Text(FuelStopsEnum.COUNTER);
        }

        public Text labelDateRange() {
            return new Text(FuelStopsEnum.DATE_RANGE_LABEL);
        }

        public TextFieldLabel labelVehicleField() {
            return new TextFieldLabel(FuelStopsEnum.VEHICLE_TEXT_FIELD);
        }

        public TextTable sortDateTime() {
            return new TextTable(FuelStopsEnum.VALUE_DATE_TIME);
        }

        public Text title() {
            return new Text(FuelStopsEnum.TITLE);
        }

        public TextTable valueEdited() {
            return new TextTable(FuelStopsEnum.VALUE_EDITED);
        }

        public TextTable valueLocation() {
            return new TextTable(FuelStopsEnum.VALUE_LOCATION);
        }

        public TextTable valueOdometerAfter() {
            return new TextTable(FuelStopsEnum.VALUE_AFTER);
        }

        public TextTable valueOdometerBefore() {
            return new TextTable(FuelStopsEnum.VALUE_BEFORE);
        }

        public TextTable valueTrailer() {
            return new TextTable(FuelStopsEnum.VALUE_TRAILER);
        }

        public TextTable valueTrailerFuel() {
            return new TextTable(FuelStopsEnum.VALUE_TRAILER_FUEL);
        }

        public TextTable valueVehicle() {
            return new TextTable(FuelStopsEnum.VALUE_VEHICLE);
        }

        public TextTable valueVehicleFuel() {
            return new TextTable(FuelStopsEnum.VALUE_VEHICLE_FUEL);
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

}
