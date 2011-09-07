package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.Button;
import com.inthinc.pro.automation.elements.CheckBox;
import com.inthinc.pro.automation.elements.DropDown;
import com.inthinc.pro.automation.elements.TextField;
import com.inthinc.pro.automation.elements.TextLink;
import com.inthinc.pro.selenium.pageEnums.MyMessagesInboxEnum;

public class PageMyMessagesInbox extends NavigationBar {
    
    public PageMyMessagesInbox() {
        url = MyMessagesInboxEnum.DEFAULT_URL;
        checkMe.add(MyMessagesInboxEnum.TEAM_DROP_DOWN);
        checkMe.add(MyMessagesInboxEnum.TITLE);
    }
    
    public class MyMessagesInboxLinks extends NavigationBarLinks {
        public TextLink replyToSelected() {
            return new TextLink(MyMessagesInboxEnum.REPLY_TO_SELECTED);
        }   
    }
    
    public class MyMessagesInboxTextFields extends NavigationBarTextFields{
        public TextField startDate(){
            return new TextField(MyMessagesInboxEnum.START_DATE);
        }
    
        public TextField stopDate(){
            return new TextField(MyMessagesInboxEnum.STOP_DATE);
        }
    }
    
    public class MyMessagesInboxDropDowns extends NavigationBarDropDowns{
        public DropDown teamDropDown(){
            return new DropDown(MyMessagesInboxEnum.TEAM_DROP_DOWN);
        }
    }
    
    public class MyMessagesInboxButtons extends NavigationBarButtons {
        public Button inboxButton(){
            return new Button(MyMessagesInboxEnum.INBOX_BUTTON);
        }
        
        public Button composeButton(){
            return new Button(MyMessagesInboxEnum.COMPOSE_BUTTON);
        }
        
        public Button sentButton(){
            return new Button(MyMessagesInboxEnum.SENT_BUTTON);
        }
        
        public Button refreshButton(){
            return new Button(MyMessagesInboxEnum.REFRESH_BUTTON);
        }      
    }
    
    public class MyMessagesInboxCheckBox {
        
        public CheckBox selectAllCheckBox(){
            return new CheckBox(MyMessagesInboxEnum.SELECT_ALL_CHECK_BOX);
        }
    }
    
    public MyMessagesInboxLinks _link() {
        return new MyMessagesInboxLinks();
    }

    public MyMessagesInboxTextFields _textField() {
        return new MyMessagesInboxTextFields();
    }
    
    public MyMessagesInboxDropDowns _dropDown() {
        return new MyMessagesInboxDropDowns();
    }
    
    public MyMessagesInboxButtons _button() {
        return new MyMessagesInboxButtons();
    }
    
    public MyMessagesInboxCheckBox _checkBox() {
        return new MyMessagesInboxCheckBox();
    }
}
