package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.Button;
import com.inthinc.pro.automation.elements.CheckBox;
import com.inthinc.pro.automation.elements.DropDown;
import com.inthinc.pro.automation.elements.Selector;
import com.inthinc.pro.automation.elements.Text;
import com.inthinc.pro.automation.elements.TextField;
import com.inthinc.pro.automation.elements.TextLink;
import com.inthinc.pro.selenium.pageEnums.MyMessagesEnum;

public class PageMyMessages extends NavigationBar {
    
    public PageMyMessages() {
        url = MyMessagesEnum.DEFAULT_URL;
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
        public Button inboxButton(){
            return new Button(MyMessagesEnum.INBOX_BUTTON);
        }
        
        public Button composeButton(){
            return new Button(MyMessagesEnum.COMPOSE_BUTTON);
        }
        
        public Button sentButton(){
            return new Button(MyMessagesEnum.SENT_BUTTON);
        }
        
        public Button refreshButton(){
            return new Button(MyMessagesEnum.REFRESH_BUTTON);
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
    }
    
    public class MyMessagesText {
        
        public Text driverHeader() {
            return new Text(MyMessagesEnum.DRIVER_HEADER);
        }
        
        public Text vehicleHeader() {
            return new Text(MyMessagesEnum.VEHICLE_HEADER);
        }
        
        public Text groupHeader() {
            return new Text(MyMessagesEnum.GROUP_HEADER);
        }
        
        public Text composeMsgHeader() {
            return new Text(MyMessagesEnum.COMPOSE_MSG_HEADER);
        }
        
        public Text sentItems() {
            return new Text(MyMessagesEnum.SENT_ITEMS);
        }
        
        public Text numberOfMessages() {
            return new Text(MyMessagesEnum.NUMBER_MESSAGES);
        }
    }
    
    public class MyMessagesSelectors {
        public Selector drivers(){
            return new Selector(MyMessagesEnum.DRIVER_LEFT);
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
}
