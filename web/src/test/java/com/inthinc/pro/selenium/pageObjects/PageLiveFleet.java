package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.DropDown;
import com.inthinc.pro.automation.elements.Text;
import com.inthinc.pro.automation.elements.TextButton;
import com.inthinc.pro.automation.elements.TextField;
import com.inthinc.pro.automation.elements.TextLink;
import com.inthinc.pro.selenium.pageEnums.GenericWebEnum;
import com.inthinc.pro.selenium.pageEnums.LiveFleetEnum;

public class PageLiveFleet extends NavigationBar {
    public PageLiveFleet() {
        url = LiveFleetEnum.DEFAULT_URL;
    }

    public LiveFleetPopUps _popUp() {
        return new LiveFleetPopUps();
    }

    public class LiveFleetPopUps extends MastheadPopUps {}

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
        public TextLink sortDispatchByNumber() {
            return new TextLink(LiveFleetEnum.LINK_SORT_DISPATCH_BY_NUMBER);
        }

        public TextLink sortDispatchByDriver() {
            return new TextLink(LiveFleetEnum.LINK_SORT_DISPATCH_BY_DRIVER);
        }

        public TextLink sortDispatchByVehicle() {
            return new TextLink(LiveFleetEnum.LINK_SORT_DISPATCH_BY_VEHICLE);
        }

        public TextLink sortDispatchByGroup() {
            return new TextLink(LiveFleetEnum.LINK_SORT_DISPATCH_BY_GROUP);
        }

        public TextLink driverByName(String driverName) {
            return new TextLink(GenericWebEnum.FIND_ANCHOR_BY_CONTAINS_TEXT, driverName, null);
        }

        public TextLink driverByListPosition(Integer position) {
            return new TextLink(LiveFleetEnum.LINK_DISPATCH_DRIVER_NEED_INDEX, null, position);
        }

        public TextLink vehicleByName(String vehicleName) {
            return new TextLink(GenericWebEnum.FIND_ANCHOR_BY_CONTAINS_TEXT, vehicleName, null);
        }

        public TextLink vehicleByListPosition(Integer position) {
            return new TextLink(LiveFleetEnum.LINK_DISPATCH_VEHICLE_NEED_INDEX, null, position);
        }

        public TextLink mapBubbleDefaultChangeView() {
            return new TextLink(LiveFleetEnum.LINK_MAP_BUBBLE_DEFAULT_CHANGE_VIEW);
        }
    }

    public class LiveFleetTexts extends NavigationBarTexts {
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

    @Override
    public String getExpectedPath() {
        return LiveFleetEnum.DEFAULT_URL.getURL();
    }

    public LiveFleetPager _page() {
        return new LiveFleetPager();
    }
    
    public class LiveFleetPager{
        public Paging pageIndex(){
            return new Paging();
        }
    }

}
