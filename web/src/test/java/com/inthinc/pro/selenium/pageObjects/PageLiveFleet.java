package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.SelectBox;
import com.inthinc.pro.automation.elements.Text;
import com.inthinc.pro.automation.elements.TextButton;
import com.inthinc.pro.automation.elements.TextField;
import com.inthinc.pro.automation.elements.TextLink;
import com.inthinc.pro.selenium.pageEnums.LiveFleetEnum;

public class PageLiveFleet extends NavigationBar {
    public LiveFleetTextFields _textField(){ return new LiveFleetTextFields();}
    public LiveFleetTexts _text(){return new LiveFleetTexts();}
    public LiveFleetLinks _link(){return new LiveFleetLinks();}
    public LiveFleetButtons _button() {return  new LiveFleetButtons();}
    public LiveFleetSelects _select() {return new LiveFleetSelects();}
   
   
    public class LiveFleetLinks{
        public TextLink sortDispatchByNumber(){ return new TextLink(selenium, LiveFleetEnum.LINK_SORT_DISPATCH_BY_NUMBER); }
        public TextLink sortDispatchByDriver(){ return new TextLink(selenium, LiveFleetEnum.LINK_SORT_DISPATCH_BY_DRIVER); }
        public TextLink sortDispatchByVehicle(){ return new TextLink(selenium, LiveFleetEnum.LINK_SORT_DISPATCH_BY_VEHICLE); }
        public TextLink sortDispatchByGroup(){ return new TextLink(selenium, LiveFleetEnum.LINK_SORT_DISPATCH_BY_GROUP); }
      //TODO: jwimmer: discuss with team: additional instances of Link objects on this page would go here
    }
    public class LiveFleetTexts{
        public Text mapBubbleDefaultChangeView(){return new Text(selenium, LiveFleetEnum.TEXTFIELD_LIVE_FLEET_FIND_ADDRESS);}
        public Text liveFleetHeader(){return new Text(selenium, LiveFleetEnum.HEADER_BOX_DISPATCH);}
        public Text labelVehiclePhone1(){return new Text(selenium, LiveFleetEnum.LABEL_MAP_BUBBLE_VEHICLE_PHONE1);};
        public Text labelVehiclePhone2(){return new Text(selenium, LiveFleetEnum.LABEL_MAP_BUBBLE_VEHICLE_PHONE2);};
        public Text labelVehicleDriver(){return new Text(selenium, LiveFleetEnum.LABEL_MAP_BUBBLE_VEHICLE_DRIVER);};
        public Text labelVehicleDevice(){return new Text(selenium, LiveFleetEnum.LABEL_MAP_BUBBLE_VEHICLE_DEVICE);};
        public Text labelVehicleUpdated(){return new Text(selenium, LiveFleetEnum.LABEL_MAP_BUBBLE_VEHICLE_UPDATED);};
        public Text labelVehicleLocation(){return new Text(selenium, LiveFleetEnum.LABEL_MAP_BUBBLE_VEHICLE_LOCATION);};
        public Text labelVehicleDistToAddress(){return new Text(selenium, LiveFleetEnum.LABEL_MAP_BUBBLE_VEHICLE_DISTANCE_TO_ADDRESS);};

        //TODO: jwimmer: discuss with team: this begs the question, is there a beneficial naming convention for a piece of text that we KNOW is going to be dynamic from the database?
        public Text valueVehiclePhone1(){return new Text(selenium, LiveFleetEnum.VALUE_MAP_BUBBLE_VEHICLE_PHONE1);};
        public Text valueVehiclePhone2(){return new Text(selenium, LiveFleetEnum.VALUE_MAP_BUBBLE_VEHICLE_PHONE2);};
        public Text valueVehicleDriver(){return new Text(selenium, LiveFleetEnum.VALUE_MAP_BUBBLE_VEHICLE_DRIVER);};
        public Text valueVehicleDevice(){return new Text(selenium, LiveFleetEnum.VALUE_MAP_BUBBLE_VEHICLE_DEVICE);};
        public Text valueVehicleUpdated(){return new Text(selenium, LiveFleetEnum.VALUE_MAP_BUBBLE_VEHICLE_UPDATED);};
        public Text valueVehicleLocation(){return new Text(selenium, LiveFleetEnum.VALUE_MAP_BUBBLE_VEHICLE_LOCATION);};
        public Text valueVehicleDistToAddress(){return new Text(selenium, LiveFleetEnum.VALUE_MAP_BUBBLE_VEHICLE_DISTANCE_TO_ADDRESS);};
        
      //TODO: jwimmer: discuss with team: additional instances of Text objects on this page would go here
    }
    public class LiveFleetTextFields{
        public TextField findAddress(){return new TextField(selenium, LiveFleetEnum.TEXTFIELD_LIVE_FLEET_FIND_ADDRESS);};
        //TODO: jwimmer: discuss with team: additional instances of TextField objects on this page would go here
    }
    public class LiveFleetButtons{
        public TextButton locate(){return new TextButton(selenium, LiveFleetEnum.BUTTON_LIVE_FLEET_LOCATE);};
    }
    public class LiveFleetSelects{
        public SelectBox numNearestVehicles(){return new SelectBox(selenium, LiveFleetEnum.DROPDOWN_LIVE_FLEET_NUM_NEAREST_VEHICLES);};
    }    
    
    public String getExpectedPath() {
        return LiveFleetEnum.DEFAULT_URL.getURL();
    }
}
