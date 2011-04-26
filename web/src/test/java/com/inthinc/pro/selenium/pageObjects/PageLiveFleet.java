package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.SelectBox;
import com.inthinc.pro.automation.elements.Text;
import com.inthinc.pro.automation.elements.TextButton;
import com.inthinc.pro.automation.elements.TextField;
import com.inthinc.pro.automation.elements.TextLink;
import com.inthinc.pro.selenium.pageEnums.LiveFleetEnum;

public class PageLiveFleet extends NavigationBar {
    public LiveFleetTextFields _textField = new LiveFleetTextFields();
    public LiveFleetTexts _text = new LiveFleetTexts();
    public LiveFleetLinks _link = new LiveFleetLinks();
    public LiveFleetButtons _button =  new LiveFleetButtons();
    public LiveFleetSelects _select = new LiveFleetSelects();
   
   
    public class LiveFleetLinks{
        public TextLink sortDispatchByNumber = new TextLink( LiveFleetEnum.LINK_SORT_DISPATCH_BY_NUMBER);
        public TextLink sortDispatchByDriver = new TextLink( LiveFleetEnum.LINK_SORT_DISPATCH_BY_DRIVER);
        public TextLink sortDispatchByVehicle = new TextLink( LiveFleetEnum.LINK_SORT_DISPATCH_BY_VEHICLE);
        public TextLink sortDispatchByGroup = new TextLink( LiveFleetEnum.LINK_SORT_DISPATCH_BY_GROUP);
      //TODO: jwimmer: discuss with team: additional instances of Link objects on this page would go here
    }
    public class LiveFleetTexts{
        public Text mapBubbleDefaultChangeView = new Text( LiveFleetEnum.TEXTFIELD_LIVE_FLEET_FIND_ADDRESS);
        public Text liveFleetHeader = new Text( LiveFleetEnum.HEADER_BOX_DISPATCH);
        public Text labelVehiclePhone1 = new Text( LiveFleetEnum.LABEL_MAP_BUBBLE_VEHICLE_PHONE1);
        public Text labelVehiclePhone2 = new Text( LiveFleetEnum.LABEL_MAP_BUBBLE_VEHICLE_PHONE2);
        public Text labelVehicleDriver = new Text( LiveFleetEnum.LABEL_MAP_BUBBLE_VEHICLE_DRIVER);
        public Text labelVehicleDevice = new Text( LiveFleetEnum.LABEL_MAP_BUBBLE_VEHICLE_DEVICE);
        public Text labelVehicleUpdated = new Text( LiveFleetEnum.LABEL_MAP_BUBBLE_VEHICLE_UPDATED);
        public Text labelVehicleLocation = new Text( LiveFleetEnum.LABEL_MAP_BUBBLE_VEHICLE_LOCATION);
        public Text labelVehicleDistToAddress = new Text( LiveFleetEnum.LABEL_MAP_BUBBLE_VEHICLE_DISTANCE_TO_ADDRESS);

        //TODO: jwimmer: discuss with team: this begs the question, is there a beneficial naming convention for a piece of text that we KNOW is going to be dynamic from the database?
        public Text valueVehiclePhone1 = new Text( LiveFleetEnum.VALUE_MAP_BUBBLE_VEHICLE_PHONE1);
        public Text valueVehiclePhone2 = new Text( LiveFleetEnum.VALUE_MAP_BUBBLE_VEHICLE_PHONE2);
        public Text valueVehicleDriver = new Text( LiveFleetEnum.VALUE_MAP_BUBBLE_VEHICLE_DRIVER);
        public Text valueVehicleDevice = new Text( LiveFleetEnum.VALUE_MAP_BUBBLE_VEHICLE_DEVICE);
        public Text valueVehicleUpdated = new Text( LiveFleetEnum.VALUE_MAP_BUBBLE_VEHICLE_UPDATED);
        public Text valueVehicleLocation = new Text( LiveFleetEnum.VALUE_MAP_BUBBLE_VEHICLE_LOCATION);
        public Text valueVehicleDistToAddress = new Text( LiveFleetEnum.VALUE_MAP_BUBBLE_VEHICLE_DISTANCE_TO_ADDRESS);
        
      //TODO: jwimmer: discuss with team: additional instances of Text objects on this page would go here
    }
    public class LiveFleetTextFields{
        public TextField findAddress = new TextField( LiveFleetEnum.TEXTFIELD_LIVE_FLEET_FIND_ADDRESS);
        //TODO: jwimmer: discuss with team: additional instances of TextField objects on this page would go here
    }
    public class LiveFleetButtons{
        public TextButton locate = new TextButton( LiveFleetEnum.BUTTON_LIVE_FLEET_LOCATE);
    }
    public class LiveFleetSelects{
        public SelectBox numNearestVehicles = new SelectBox( LiveFleetEnum.DROPDOWN_LIVE_FLEET_NUM_NEAREST_VEHICLES);
    }    
    
    public String getExpectedPath() {
        return LiveFleetEnum.DEFAULT_URL.getURL();
    }
}
