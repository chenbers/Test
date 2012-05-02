package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.Button;
import com.inthinc.pro.automation.elements.DropDown;
import com.inthinc.pro.automation.elements.Text;
import com.inthinc.pro.automation.elements.TextButton;
import com.inthinc.pro.automation.elements.TextField;
import com.inthinc.pro.automation.elements.TextFieldLabel;
import com.inthinc.pro.automation.elements.TextLabel;
import com.inthinc.pro.automation.elements.TextLink;
import com.inthinc.pro.automation.elements.TextTable;
import com.inthinc.pro.automation.elements.TextTableLink;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.selenium.pageEnums.LiveFleetEnum;

public class PageLiveFleet extends NavigationBar {
    public PageLiveFleet() {
        checkMe.add(LiveFleetEnum.HEADER_BOX_DISPATCH);
        checkMe.add(LiveFleetEnum.HEADER_BOX_FLEET_LEGEND);
        checkMe.add(LiveFleetEnum.HEADER_BOX_LIVE_FLEET);
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

    public LiveFleetDropDowns _dropDown() {
        return new LiveFleetDropDowns();
    }

    public class LiveFleetLinks extends NavigationBarLinks {
        
        public TextLink sortByNumber() {
            return new TextLink(LiveFleetEnum.LINK_SORT_DISPATCH_BY_NUMBER);
        }

        public TextLink sortByDriver() {
            return new TextLink(LiveFleetEnum.LINK_SORT_DISPATCH_BY_DRIVER);
        }

        public TextLink sortByVehicle() {
            return new TextLink(LiveFleetEnum.LINK_SORT_DISPATCH_BY_VEHICLE);
        }

        public TextLink sortByGroup() {
            return new TextLink(LiveFleetEnum.LINK_SORT_DISPATCH_BY_GROUP);
        }

        public TextTableLink entryDriverByPosition() {
            return new TextTableLink(LiveFleetEnum.LINK_DISPATCH_DRIVER_NEED_INDEX);
        }

        public TextTableLink entryVehicleByPosition() {
            return new TextTableLink(LiveFleetEnum.LINK_DISPATCH_VEHICLE_NEED_INDEX);
        }
        
        public TextLink mapBubbleChangeDefaultView() {
            return new TextLink(LiveFleetEnum.LINK_MAP_BUBBLE_DEFAULT_CHANGE_VIEW);
        }
        
        public TextTableLink entryFleetLegend(){
            return new TextTableLink(LiveFleetEnum.LINK_BOX_FLEET_LEGEND_GROUP_NEED_INDEX);
        }
        
        public TextTableLink entryGroupIconByPosition(){
            return new TextTableLink(LiveFleetEnum.LINK_DISPATCH_GROUP_ICON_NEED_INDEX);
        }
        
        public TextLink valueMapBubbleVehicleName(){
            return new TextLink(LiveFleetEnum.VALUE_MAP_BUBBLE_VEHICLE_NAME);
        }
        
        public TextLink valueMapBubbleDriver(){
            return new TextLink(LiveFleetEnum.VALUE_MAP_BUBBLE_VEHICLE_DRIVER);
        }
    }

    public class LiveFleetTexts extends NavigationBarTexts {
        
        public Text headerLiveFleet() {
            return new Text(LiveFleetEnum.HEADER_BOX_LIVE_FLEET);
        }
        
        public Text headerDispatch(){
            return new Text(LiveFleetEnum.HEADER_BOX_DISPATCH);
        }
        
        public Text headerFleetLegend(){
            return new Text(LiveFleetEnum.HEADER_BOX_FLEET_LEGEND);
        }
        
        public TextTable entryPositionByPosition(){
            return new TextTable(LiveFleetEnum.TEXT_POSITION_NEED_INDEX);
        }

        public Text labelMapBubblePhone1() {
            return new TextLabel(LiveFleetEnum.VALUE_MAP_BUBBLE_VEHICLE_PHONE1);
        }

        public Text labelMapBubblePhone2() {
            return new TextLabel(LiveFleetEnum.VALUE_MAP_BUBBLE_VEHICLE_PHONE2);
        }

        public Text labelMapBubbleDriver() {
            return new TextLabel(LiveFleetEnum.VALUE_MAP_BUBBLE_VEHICLE_DRIVER);
        }

        public Text labelMapBubbleDevice() {
            return new TextLabel(LiveFleetEnum.VALUE_MAP_BUBBLE_VEHICLE_DEVICE);
        }

        public Text labelMapBubbleUpdated() {
            return new TextLabel(LiveFleetEnum.VALUE_MAP_BUBBLE_VEHICLE_UPDATED);
        }

        public Text labelMapBubbleLocation() {
            return new TextLabel(LiveFleetEnum.VALUE_MAP_BUBBLE_VEHICLE_LOCATION);
        }

        public Text valueMapBubblePhone1() {
            return new Text(LiveFleetEnum.VALUE_MAP_BUBBLE_VEHICLE_PHONE1);
        }

        public Text valueMapBubblePhone2() {
            return new Text(LiveFleetEnum.VALUE_MAP_BUBBLE_VEHICLE_PHONE2);
        }

        public Text valueMapBubbleDevice() {
            return new Text(LiveFleetEnum.VALUE_MAP_BUBBLE_VEHICLE_DEVICE);
        }

        public Text valueMapBubbleUpdated() {
            return new Text(LiveFleetEnum.VALUE_MAP_BUBBLE_VEHICLE_UPDATED);
        }

        public Text valueMapBubbleLocation() {
            return new Text(LiveFleetEnum.VALUE_MAP_BUBBLE_VEHICLE_LOCATION);
        }

        public Text valueMapBubbleDistToAddress() {
            return new Text(LiveFleetEnum.VALUE_MAP_BUBBLE_VEHICLE_DISTANCE_TO_ADDRESS);
        }
        
        public Text labelFindAddress(){
            return new TextFieldLabel(LiveFleetEnum.TEXTFIELD_LIVE_FLEET_FIND_ADDRESS);
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
        
        public Button refresh(){
            return new Button(LiveFleetEnum.BUTTON_REFRESH);
        }
    }

    public class LiveFleetDropDowns {
        public DropDown numNearestVehicles() {
            return new DropDown(LiveFleetEnum.DROPDOWN_LIVE_FLEET_NUM_NEAREST_VEHICLES);
        }
    }

    public LiveFleetPager _page() {
        return new LiveFleetPager();
    }
    
    public class LiveFleetPager{
        public Paging pageIndex(){
            return new Paging(LiveFleetEnum.PAGER);
        }
    }

    @Override
    public SeleniumEnums setUrl() {
        return LiveFleetEnum.DEFAULT_URL;
    }

    @Override
    protected boolean checkIsOnPage() {
        return _button().refresh().isPresent();
    }

}
