package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.Calendar;
import com.inthinc.pro.automation.elements.DropDown;
import com.inthinc.pro.automation.elements.Text;
import com.inthinc.pro.automation.elements.TextButton;
import com.inthinc.pro.automation.elements.TextDropDownError;
import com.inthinc.pro.automation.elements.TextField;
import com.inthinc.pro.automation.elements.TextFieldError;
import com.inthinc.pro.automation.elements.TextFieldLabel;
import com.inthinc.pro.automation.elements.TextFieldValue;
import com.inthinc.pro.automation.elements.TextLabel;
import com.inthinc.pro.automation.elements.TextLabelDropDown;
import com.inthinc.pro.automation.elements.TimeOfDay;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.selenium.pageEnums.FuelStopsEditEnum;

public class PageHOSAddEditFuelStops extends HOSBar {
    
    public class AddEditFuelStopsLinks extends HOSBarLinks{}
    public class AddEditFuelStopsTexts extends HOSBarTexts{
        
        public Text errorMaster(){
            return new Text(FuelStopsEditEnum.MASTER_ERROR);
        }
        
        public Text labelVehicle(){
            return new TextLabel(FuelStopsEditEnum.VEHICLE);
        }
        
        public Text valueVehicle(){
            return new Text(FuelStopsEditEnum.VEHICLE);
        }
        
        public Text errorBothVehicleAndTrailerFuel(){
            return new TextFieldError(FuelStopsEditEnum.BOTH_FUEL_FIELD);
        }
        
        public Text errorVehicleFuel(){
            return new TextFieldError(FuelStopsEditEnum.VEHICLE_FUEL_FIELD);
        }
        
        public Text errorDriver(){
            return new TextDropDownError(FuelStopsEditEnum.DRIVER_DROP_DOWN);
        }
        
        public Text title(){
            return new Text(FuelStopsEditEnum.TITLE);
        }
        
        public Text header(){
            return new Text(FuelStopsEditEnum.HEADER);
        }
        
        public Text labelDate(){
            return new TextFieldLabel(FuelStopsEditEnum.DATE_BOX);
        }
        
        public Text errorDate(){
            return new Text(FuelStopsEditEnum.DATE_ERROR);
        }
        
        public Text labelTrailer(){
            return new TextFieldLabel(FuelStopsEditEnum.TRAILER_FIELD);
        }
        
        public Text labelVehicleFuel(){
            return new TextFieldLabel(FuelStopsEditEnum.VEHICLE_FUEL_FIELD);
        }
        
        public Text unitsVehicleFuel(){
            return new TextFieldValue(FuelStopsEditEnum.VEHICLE_FUEL_FIELD);
        }
        
        public Text labelTrailerFuel(){
            return new TextFieldLabel(FuelStopsEditEnum.TRAILER_FUEL_FIELD);
        }
        
        public Text errorTrailerFuel(){
            return new TextFieldError(FuelStopsEditEnum.TRAILER_FUEL_FIELD);
        }
        
        public Text unitsTrailerFuel(){
            return new TextFieldValue(FuelStopsEditEnum.TRAILER_FUEL_FIELD);
        }
        
        public Text labelDriver(){
            return new TextLabelDropDown(FuelStopsEditEnum.DRIVER_DROP_DOWN);
        }
        
        
        public Text labelLocation(){
            return new Text(FuelStopsEditEnum.LOCATION_LABEL);
        }
        
        public Text valueLocation(){
            return new Text(FuelStopsEditEnum.LOCATION);
        }
        
        public Text timeMessage(){
            return new Text(FuelStopsEditEnum.TIME_MESSAGE);
        }
        
        
    }
    public class AddEditFuelStopsTextFields extends HOSBarTextFields{
        
        
        public TextField trailer(){
            return new TextField(FuelStopsEditEnum.TRAILER_FIELD);
        }
        
        public TextField vehicleFuel(){
            return new TextField(FuelStopsEditEnum.VEHICLE_FUEL_FIELD);
        }
        
        public TextField trailerFuel(){
            return new TextField(FuelStopsEditEnum.TRAILER_FUEL_FIELD);
        }
    }
    
    public FuelStopsAddEditDateSelectors _dateSelector(){
        return new FuelStopsAddEditDateSelectors();
    }
    
    public class FuelStopsAddEditDateSelectors{

        public Calendar date(){
            return new Calendar(FuelStopsEditEnum.DATE_BOX);
        }
    }
    public class AddEditFuelStopsButtons extends HOSBarButtons{
        
        public TextButton topSave(){
            return new TextButton(FuelStopsEditEnum.SAVE_TOP);
        }
        
        public TextButton bottomSave(){
            return new TextButton(FuelStopsEditEnum.SAVE_BOTTOM);
        }
        
        public TextButton topCancel(){
            return new TextButton(FuelStopsEditEnum.CANCEL_TOP);
        }
        
        public TextButton bottomCancel(){
            return new TextButton(FuelStopsEditEnum.CANCEL_BOTTOM);
        }
    }
    public class AddEditFuelStopsDropDowns extends HOSBarDropDowns{
        
        public DropDown driver(){
            return new DropDown(FuelStopsEditEnum.DRIVER_DROP_DOWN);
        }
        
    }
    public class AddEditFuelStopsPopUps extends MastheadPopUps{}
    

    public AddEditFuelStopsLinks _link(){
        return new AddEditFuelStopsLinks();
    }
    
    public AddEditFuelStopsTexts _text(){
        return new AddEditFuelStopsTexts();
    }
        
    public AddEditFuelStopsButtons _button(){
        return new AddEditFuelStopsButtons();
    }
    
    public AddEditFuelStopsTextFields _textField(){
        return new AddEditFuelStopsTextFields();
    }
    
    public AddEditFuelStopsDropDowns _dropDown(){
        return new AddEditFuelStopsDropDowns();
    }
    
    public AddEditFuelStopsPopUps _popUp(){
        return new AddEditFuelStopsPopUps();
    }
    
    public TimeOfDay _timeSelector(){
        return new TimeOfDay(FuelStopsEditEnum.TIME_CHANGER);
    }

    @Override
    public SeleniumEnums setUrl() {
        return FuelStopsEditEnum.DEFAULT_URL;
    }
    

}
