package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.Button;
import com.inthinc.pro.automation.elements.CheckBox;
import com.inthinc.pro.automation.elements.DropDown;
import com.inthinc.pro.automation.elements.Selector;
import com.inthinc.pro.automation.elements.Text;
import com.inthinc.pro.automation.elements.TextButton;
import com.inthinc.pro.automation.elements.TextField;
import com.inthinc.pro.automation.elements.TextLink;
import com.inthinc.pro.automation.elements.TextTable;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.selenium.pageEnums.MyMessagesEnum;

public class PageMyMessages extends NavigationBar {
    
    public PageMyMessages() {
        checkMe.add(MyMessagesEnum.TEAM_DROP_DOWN);
        checkMe.add(MyMessagesEnum.TITLE);
    }
    
    public class MyMessagesLinks extends NavigationBarLinks {
        public TextLink replyToSelected() {
            return new TextLink(MyMessagesEnum.REPLY_TO_SELECTED);
        }   
    }
    
    public class MyMessagesTextFields extends NavigationBarTextFields{
        public TextField startDate(){
            return new TextField(MyMessagesEnum.START_DATE);
        }
    
        public TextField stopDate(){
            return new TextField(MyMessagesEnum.STOP_DATE);
        }
        
        public TextField composeMsgTextField(){
            return new TextField(MyMessagesEnum.COMPOSE_MSG_TEXT_FIELD);
        }
    }
    
    public class MyMessagesDropDowns extends NavigationBarDropDowns{
        public DropDown teamDropDown(){
            return new DropDown(MyMessagesEnum.TEAM_DROP_DOWN);
        }
    }
    
    public class MyMessagesInboxButtons extends NavigationBarButtons {
        public TextButton inbox(){
            return new TextButton(MyMessagesEnum.INBOX_BUTTON);
        }
        
        public TextButton compose(){
            return new TextButton(MyMessagesEnum.COMPOSE_BUTTON);
        }
        
        public TextButton send(){
            return new TextButton(MyMessagesEnum.SEND_BUTTON);
        }
        
        public TextButton sent(){
            return new TextButton(MyMessagesEnum.SENT_BUTTON);
        }
        
        public TextButton refresh(){
            return new TextButton(MyMessagesEnum.REFRESH_BUTTON);
        } 
        
        public Button driverRemoveAll(){
            return new Button(MyMessagesEnum.DRIVER_REMOVE_ALL);
        }
        
        public Button driverRemove(){
            return new Button(MyMessagesEnum.DRIVER_REMOVE);
        }
        
        public Button driverCopy(){
            return new Button(MyMessagesEnum.DRIVER_COPY);
        }
        
        public Button driverCopyAll(){
            return new Button(MyMessagesEnum.DRIVER_COPY_ALL);
        }
        
        public Button vehicleRemoveAll(){
            return new Button(MyMessagesEnum.VEHICLE_REMOVE_ALL);
        }
        
        public Button vehicleRemove(){
            return new Button(MyMessagesEnum.VEHICLE_REMOVE);
        }
        
        public Button vehicleCopy(){
            return new Button(MyMessagesEnum.VEHICLE_COPY);
        }
        
        public Button vehicleCopyAll(){
            return new Button(MyMessagesEnum.VEHICLE_COPY_ALL);
        }
        
        public Button groupRemoveAll(){
            return new Button(MyMessagesEnum.GROUP_REMOVE_ALL);
        }
        
        public Button groupRemove(){
            return new Button(MyMessagesEnum.GROUP_REMOVE);
        }
        
        public Button groupCopy(){
            return new Button(MyMessagesEnum.GROUP_COPY);
        }
        
        public Button groupCopyAll(){
            return new Button(MyMessagesEnum.GROUP_COPY_ALL);
        }
        
        public TextButton sortBySentDate(){
            return new TextButton(MyMessagesEnum.SENT_SORT);
        }
    }
    
    public class MyMessagesText {
        
        public Text headerDriver() {
            return new Text(MyMessagesEnum.DRIVER_HEADER);
        }
        
        public Text headerVehicle() {
            return new Text(MyMessagesEnum.VEHICLE_HEADER);
        }
        
        public Text headerGroup() {
            return new Text(MyMessagesEnum.GROUP_HEADER);
        }
        
        public Text headerComposeMsg() {
            return new Text(MyMessagesEnum.COMPOSE_MSG_HEADER);
        }
        
        public Text labelSentItems() {
            return new Text(MyMessagesEnum.SENT_ITEMS);
        }
        
        public Text numberOfSentMessages() {
            return new Text(MyMessagesEnum.NUMBER_MESSAGES);
        }
        
        public Text headerFrom(){
            return new Text(MyMessagesEnum.FROM_HEADER);
        }
        
        public Text headerMessage(){
            return new Text(MyMessagesEnum.MESSAGE_HEADER);
        }
        
        public Text headerTo(){
            return new Text(MyMessagesEnum.TO_HEADER);
        }
        
        public TextTable entryFrom(){
            return new TextTable(MyMessagesEnum.FROM_NAME_TABLE);
        }
        
        public TextTable entryTo(){
            return new TextTable(MyMessagesEnum.TO_NAME_TABLE);
        }
        
        public TextTable entryMessage(){
            return new TextTable(MyMessagesEnum.MESSAGE_TABLE);
        }
        
        public TextTable entrySent(){
            return new TextTable(MyMessagesEnum.SENT_DATE_TABLE);
        }
    }
    
    public class MyMessagesSelectors {
        
        public Selector leftDrivers(){
            return new Selector(MyMessagesEnum.DRIVER_LEFT);
        }
        
        public Selector leftVehicles(){
            return new Selector(MyMessagesEnum.VEHICLE_LEFT);
        }
        
        public Selector leftGroups(){
            return new Selector(MyMessagesEnum.GROUP_LEFT);
        }
        
        public Selector rightDrivers(){
            return new Selector(MyMessagesEnum.DRIVER_LEFT);
        }
        
        public Selector rightVehicles(){
            return new Selector(MyMessagesEnum.VEHICLE_LEFT);
        }
        
        public Selector rightGroups(){
            return new Selector(MyMessagesEnum.GROUP_LEFT);
        }
    }
    
    public class MyMessagesCheckBox {
        
        public CheckBox selectAllCheckBox(){
            return new CheckBox(MyMessagesEnum.SELECT_ALL_CHECK_BOX);
        }
        
        public CheckBox tableCheckBox(){
            return new CheckBox(MyMessagesEnum.TABLE_CHECK_BOX);
        }
    }
    
    public MyMessagesLinks _link() {
        return new MyMessagesLinks();
    }

    public MyMessagesTextFields _textField() {
        return new MyMessagesTextFields();
    }
    
    public MyMessagesDropDowns _dropDown() {
        return new MyMessagesDropDowns();
    }
    
    public MyMessagesInboxButtons _button() {
        return new MyMessagesInboxButtons();
    }
    
    public MyMessagesText _text() {
        return new MyMessagesText();
    }
    
    public MyMessagesCheckBox _checkBox() {
        return new MyMessagesCheckBox();
    }

    public MyMessagesSelectors _selector() {
        return new MyMessagesSelectors();
    }

    @Override
    public SeleniumEnums setUrl() {
        return MyMessagesEnum.DEFAULT_URL;
    }
}
