package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.DropDown;
import com.inthinc.pro.automation.elements.Text;
import com.inthinc.pro.automation.elements.TextButton;
import com.inthinc.pro.automation.elements.TextDropDownError;
import com.inthinc.pro.automation.elements.TextField;
import com.inthinc.pro.automation.elements.TextFieldError;
import com.inthinc.pro.automation.elements.TextFieldLabel;
import com.inthinc.pro.automation.elements.TextLabel;
import com.inthinc.pro.automation.elements.TextLabelDropDown;
import com.inthinc.pro.selenium.pageEnums.FuelStopsEditEnum;

public class PageFuelStopsAddEdit extends HOSBar {
    
    public class FuelStopsAddEditLinks extends HOSBarLinks{}
    public class FuelStopsAddEditTexts extends HOSBarTexts{
        
        public Text errorMaster(){
            return new Text(FuelStopsEditEnum.MASTER_ERROR);
        }
        
        public Text labelVehicle(){
            return new TextLabel(FuelStopsEditEnum.VEHICLE);
        }
        
        public Text valueVehicle(){
            return new Text(FuelStopsEditEnum.VEHICLE);
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
        
        public Text labelTrailer(){
            return new TextFieldLabel(FuelStopsEditEnum.TRAILER_FIELD);
        }
        
        public Text labelVehicleFuel(){
            return new TextFieldLabel(FuelStopsEditEnum.VEHICLE_FUEL_FIELD);
        }
        
        public Text labelTrailerFuel(){
            return new TextFieldLabel(FuelStopsEditEnum.TRAILER_FUEL_FIELD);
        }
        
        public Text labelDriver(){
            return new TextLabelDropDown(FuelStopsEditEnum.DRIVER_DROP_DOWN);
        }
        
        
        public Text labelLocation(){
            return new Text(FuelStopsEditEnum.LOCATION_LABEL);
        }
        
        
    }
    public class FuelStopsAddEditTextFields extends HOSBarTextFields{
        
        public TextField date(){
            return new TextField(FuelStopsEditEnum.DATE_BOX);
        }
        
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
    public class FuelStopsAddEditButtons extends HOSBarButtons{
        
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
    public class FuelStopsAddEditDropDowns extends HOSBarDropDowns{
        
        public DropDown driver(){
            return new DropDown(FuelStopsEditEnum.DRIVER_DROP_DOWN);
        }
        
    }
    public class FuelStopsAddEditPopUps extends MastheadPopUps{}
    

    public FuelStopsAddEditLinks _link(){
        return new FuelStopsAddEditLinks();
    }
    
    public FuelStopsAddEditTexts _text(){
        return new FuelStopsAddEditTexts();
    }
        
    public FuelStopsAddEditButtons _button(){
        return new FuelStopsAddEditButtons();
    }
    
    public FuelStopsAddEditTextFields _textField(){
        return new FuelStopsAddEditTextFields();
    }
    
    public FuelStopsAddEditDropDowns _dropDown(){
        return new FuelStopsAddEditDropDowns();
    }
    
    public FuelStopsAddEditPopUps _popUp(){
        return new FuelStopsAddEditPopUps();
    }
    

}
