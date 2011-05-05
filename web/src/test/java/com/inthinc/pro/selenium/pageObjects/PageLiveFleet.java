package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.DropDown;
import com.inthinc.pro.automation.elements.Text;
import com.inthinc.pro.automation.elements.TextButton;
import com.inthinc.pro.automation.elements.TextField;
import com.inthinc.pro.automation.elements.TextLink;
import com.inthinc.pro.automation.enums.AutomationEnum;
import com.inthinc.pro.selenium.pageEnums.LiveFleetEnum;

public class PageLiveFleet extends NavigationBar {
    public LiveFleetTextFields _textField() {
        return new LiveFleetTextFields();
    }

    public LiveFleetTexts _text() {
        return new LiveFleetTexts();
    }

    public LiveFleetLinks _link() {
        return new LiveFleetLinks();
    }

    public LiveFleetButtons _button() {
        return new LiveFleetButtons();
    }

    public LiveFleetSelects _select() {
        return new LiveFleetSelects();
    }

    public class LiveFleetLinks extends NavigationBarLinks {
        public TextLink sortDispatchByNumber() {return new TextLink(LiveFleetEnum.LINK_SORT_DISPATCH_BY_NUMBER);}
        public TextLink sortDispatchByDriver() {return new TextLink(LiveFleetEnum.LINK_SORT_DISPATCH_BY_DRIVER);}
        public TextLink sortDispatchByVehicle() {return new TextLink(LiveFleetEnum.LINK_SORT_DISPATCH_BY_VEHICLE);}
        public TextLink sortDispatchByGroup() {return new TextLink(LiveFleetEnum.LINK_SORT_DISPATCH_BY_GROUP);}


        public TextLink driverByName(String driverName) {
            return new TextLink(AutomationEnum.FIND_ANCHOR_BY_CONTAINS_TEXT, driverName, null);
        }

        public TextLink driverByListPosition(Integer position) {
            return new TextLink(LiveFleetEnum.LINK_DISPATCH_DRIVER_NEED_INDEX, null, position);
        }

        public TextLink vehicleByName(String vehicleName) {
            return new TextLink(AutomationEnum.FIND_ANCHOR_BY_CONTAINS_TEXT, vehicleName, null);
        }

        public TextLink vehicleByListPosition(Integer position) {
            // TODO: jwimmer: discuss with team: is there a beneficial naming convention for a piece of text that we KNOW is going to be dynamic from the database? i.e. *_NEED_*
            // indicates that it expects something?
            return new TextLink(LiveFleetEnum.LINK_DISPATCH_VEHICLE_NEED_INDEX, null, position);
        }
    }

    public class LiveFleetTexts extends NavigationBarTexts {
        public Text mapBubbleDefaultChangeView() {
            return new Text(LiveFleetEnum.TEXTFIELD_LIVE_FLEET_FIND_ADDRESS);
        }

        public Text liveFleetHeader() {
            return new Text(LiveFleetEnum.HEADER_BOX_DISPATCH);
        }

        public Text labelVehiclePhone1() {
            return new Text(LiveFleetEnum.LABEL_MAP_BUBBLE_VEHICLE_PHONE1);
        }

        public Text labelVehiclePhone2() {
            return new Text(LiveFleetEnum.LABEL_MAP_BUBBLE_VEHICLE_PHONE2);
        }

        public Text labelVehicleDriver() {
            return new Text(LiveFleetEnum.LABEL_MAP_BUBBLE_VEHICLE_DRIVER);
        }

        public Text labelVehicleDevice() {
            return new Text(LiveFleetEnum.LABEL_MAP_BUBBLE_VEHICLE_DEVICE);
        }

        public Text labelVehicleUpdated() {
            return new Text(LiveFleetEnum.LABEL_MAP_BUBBLE_VEHICLE_UPDATED);
        }

        public Text labelVehicleLocation() {
            return new Text(LiveFleetEnum.LABEL_MAP_BUBBLE_VEHICLE_LOCATION);
        }

        public Text labelVehicleDistToAddress() {
            return new Text(LiveFleetEnum.LABEL_MAP_BUBBLE_VEHICLE_DISTANCE_TO_ADDRESS);
        }

        public Text valueVehiclePhone1() {
            return new Text(LiveFleetEnum.VALUE_MAP_BUBBLE_VEHICLE_PHONE1);
        }

        public Text valueVehiclePhone2() {
            return new Text(LiveFleetEnum.VALUE_MAP_BUBBLE_VEHICLE_PHONE2);
        }

        public Text valueVehicleDriver() {
            return new Text(LiveFleetEnum.VALUE_MAP_BUBBLE_VEHICLE_DRIVER);
        }

        public Text valueVehicleDevice() {
            return new Text(LiveFleetEnum.VALUE_MAP_BUBBLE_VEHICLE_DEVICE);
        }

        public Text valueVehicleUpdated() {
            return new Text(LiveFleetEnum.VALUE_MAP_BUBBLE_VEHICLE_UPDATED);
        }

        public Text valueVehicleLocation() {
            return new Text(LiveFleetEnum.VALUE_MAP_BUBBLE_VEHICLE_LOCATION);
        }

        public Text valueVehicleDistToAddress() {
            return new Text(LiveFleetEnum.VALUE_MAP_BUBBLE_VEHICLE_DISTANCE_TO_ADDRESS);
        }
    }

    public class LiveFleetTextFields extends NavigationBarTextFields {
        public TextField findAddress() {
            return new TextField(LiveFleetEnum.TEXTFIELD_LIVE_FLEET_FIND_ADDRESS);
        }
    }

    public class LiveFleetButtons extends NavigationBarButtons {
        public TextButton locate() {
            return new TextButton(LiveFleetEnum.BUTTON_LIVE_FLEET_LOCATE);
        }
    }

    public class LiveFleetSelects {
        public DropDown numNearestVehicles() {
            return new DropDown(LiveFleetEnum.DROPDOWN_LIVE_FLEET_NUM_NEAREST_VEHICLES);
        }
    }
//TODO: jwimmer: I would prefer page.validate() {...} ??? AND page._type.elementName.validate()  
//    public class MastheadValidation{
//        public void footer(){
//            ElementBase test =  new ElementBase();
//            test.validateElementsPresent(MastheadEnum.COPYRIGHT,MastheadEnum.PRIVACY,MastheadEnum.LEGAL,
//                    MastheadEnum.SUPPORT,MastheadEnum.VERSION);
//            test.validateTextMatches(MastheadEnum.COPYRIGHT,MastheadEnum.PRIVACY,MastheadEnum.LEGAL,
//                    MastheadEnum.SUPPORT,MastheadEnum.VERSION);
//        }
//        
//        public void header(){
//            ElementBase test =  new ElementBase();
//            test.validateElementsPresent(MastheadEnum.LOGO,MastheadEnum.HELP, MastheadEnum.MY_MESSAGES,
//                    MastheadEnum.MY_ACCOUNT, MastheadEnum.LOGOUT);
//            test.validateTextMatches(MastheadEnum.LOGO,MastheadEnum.HELP, MastheadEnum.MY_MESSAGES,
//                    MastheadEnum.MY_ACCOUNT, MastheadEnum.LOGOUT);
//        }
//    }
    

    public String getExpectedPath() {
        return LiveFleetEnum.DEFAULT_URL.getURL();
    }
}
