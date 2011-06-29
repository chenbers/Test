package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.Text;
import com.inthinc.pro.automation.elements.TextButton;
import com.inthinc.pro.automation.elements.TextFieldLabel;
import com.inthinc.pro.automation.elements.TextFieldSuggestions;
import com.inthinc.pro.automation.elements.TextLink;
import com.inthinc.pro.automation.elements.TextTable;
import com.inthinc.pro.automation.elements.TextTableLink;
import com.inthinc.pro.selenium.pageEnums.FuelStopsEnum;

public class PageFuelStops extends HOSBar {
    
    private static final String page = "fuelStopsTable";

    public class FuelStopsLinks extends HOSBarLinks {

	public TextLink sortDateTime() {
	    return new TextLink(FuelStopsEnum.SORT_DATE_TIME);
	}

	public TextLink sortVehicle() {
	    return new TextLink(FuelStopsEnum.SORT_VEHICLE);
	}

	public TextLink sortVehicleFuel() {
	    return new TextLink(FuelStopsEnum.SORT_VEHICLE_FUEL);
	}

	public TextLink sortTrailerFuel() {
	    return new TextLink(FuelStopsEnum.SORT_TRAILER_FUEL);
	}

	public TextLink sortOdometerBefore() {
	    return new TextLink(FuelStopsEnum.SORT_BEFORE);
	}

	public TextLink sortOdometerAfter() {
	    return new TextLink(FuelStopsEnum.SORT_AFTER);
	}

	public TextLink sortTrailer() {
	    return new TextLink(FuelStopsEnum.SORT_TRAILER);
	}

	public TextLink sortLocation() {
	    return new TextLink(FuelStopsEnum.SORT_LOCATION);
	}

	public TextLink sortEdited() {
	    return new TextLink(FuelStopsEnum.SORT_EDITED);
	}

	public TextTableLink valueEdit() {
	    return new TextTableLink(FuelStopsEnum.VALUE_EDIT);
	}
    }

    public class FuelStopsTexts extends HOSBarTexts {

	public TextTable sortDateTime() {
	    return new TextTable(FuelStopsEnum.VALUE_DATE_TIME);
	}

	public TextTable valueVehicle() {
	    return new TextTable(FuelStopsEnum.VALUE_VEHICLE);
	}

	public TextTable valueVehicleFuel() {
	    return new TextTable(FuelStopsEnum.VALUE_VEHICLE_FUEL);
	}

	public TextTable valueTrailerFuel() {
	    return new TextTable(FuelStopsEnum.VALUE_TRAILER_FUEL);
	}

	public TextTable valueOdometerBefore() {
	    return new TextTable(FuelStopsEnum.VALUE_BEFORE);
	}

	public TextTable valueOdometerAfter() {
	    return new TextTable(FuelStopsEnum.VALUE_AFTER);
	}

	public TextTable valueTrailer() {
	    return new TextTable(FuelStopsEnum.VALUE_TRAILER);
	}

	public TextTable valueLocation() {
	    return new TextTable(FuelStopsEnum.VALUE_LOCATION);
	}

	public TextTable valueEdited() {
	    return new TextTable(FuelStopsEnum.VALUE_EDITED);
	}

	public Text counter() {
	    return new Text(FuelStopsEnum.COUNTER);
	}

	public Text title() {
	    return new Text(FuelStopsEnum.TITLE);
	}

	public TextFieldLabel labelVehicleField() {
	    return new TextFieldLabel(FuelStopsEnum.VEHICLE_TEXT_FIELD);
	}

	public Text labelDateRange() {
	    return new Text(FuelStopsEnum.DATE_RANGE_LABEL);
	}
    }

    public class FuelStopsTextFields extends HOSBarTextFields {

	public TextFieldSuggestions vehicle() {
	    return new TextFieldSuggestions(FuelStopsEnum.VEHICLE_TEXT_FIELD,
		    FuelStopsEnum.VEHICLE_SUGGESTION_BOX);
	}
    }

    public class FuelStopsButtons extends HOSBarButtons {
	
	public TextButton refresh(){
	    return new TextButton(FuelStopsEnum.REFRESH);
	}
	
	public TextButton add(){
	    return new TextButton(FuelStopsEnum.ADD);
	}
    }

    public class FuelStopsDropDowns extends HOSBarDropDowns {
    }

    public class FuelStopsPopUps extends MastheadPopUps {
	public FuelStopsPopUps(){
	    super(page);
	}
	
	public EditColumns editColumns(){
	    return new EditColumns();
	}
    }

    public class FuelStopsPager {

	public Paging pageIndex() {
	    return new Paging();
	}
    }

    public FuelStopsLinks _link() {
	return new FuelStopsLinks();
    }

    public FuelStopsTexts _text() {
	return new FuelStopsTexts();
    }

    public FuelStopsButtons _button() {
	return new FuelStopsButtons();
    }

    public FuelStopsTextFields _textField() {
	return new FuelStopsTextFields();
    }

    public FuelStopsDropDowns _dropDown() {
	return new FuelStopsDropDowns();
    }

    public FuelStopsPopUps _popUp() {
	return new FuelStopsPopUps();
    }

    public FuelStopsPager _page() {
	return new FuelStopsPager();
    }

}
