package com.inthinc.pro.selenium.pageObjects;

import org.ajax4jsf.org.w3c.tidy.AttrCheckImpl.CheckAlign;

import com.inthinc.pro.automation.elements.CheckBox;
import com.inthinc.pro.automation.elements.ElementInterface.Checkable;
import com.inthinc.pro.automation.elements.ElementInterface.Clickable;
import com.inthinc.pro.automation.elements.CheckBoxTable;
import com.inthinc.pro.automation.elements.Text;
import com.inthinc.pro.automation.elements.TextButton;
import com.inthinc.pro.automation.elements.TextField;
import com.inthinc.pro.automation.elements.TextFieldLabel;
import com.inthinc.pro.automation.utils.MasterTest;
import com.inthinc.pro.model.GoogleMapType;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.selenium.pageEnums.AdminSpeedByStreetEnum;
import com.inthinc.pro.selenium.pageEnums.PopUpEnum;

public class PageAdminSpeedByStreet extends AdminBar {

    public PageAdminSpeedByStreet() {
        checkMe.add(AdminSpeedByStreetEnum.DELETE);
        checkMe.add(AdminSpeedByStreetEnum.TITLE);
        checkMe.add(AdminSpeedByStreetEnum.MESSAGE);
        checkMe.add(AdminSpeedByStreetEnum.HEADING_ADDRESS);
        checkMe.add(AdminSpeedByStreetEnum.HEADING_SPEED_LIMIT);
        checkMe.add(AdminSpeedByStreetEnum.HEADING_CURRENT);
        checkMe.add(AdminSpeedByStreetEnum.HEADING_NEW);
        checkMe.add(AdminSpeedByStreetEnum.HEADING_COMMENT);
        checkMe.add(AdminSpeedByStreetEnum.CHECKBOX);
        checkMe.add(AdminSpeedByStreetEnum.ADDRESS);
        checkMe.add(AdminSpeedByStreetEnum.SPEED_LIMIT_CURRENT);
        checkMe.add(AdminSpeedByStreetEnum.SPEED_LIMIT_NEW);
        checkMe.add(AdminSpeedByStreetEnum.COMMENT);
        checkMe.add(AdminSpeedByStreetEnum.MAP);
    }

    @Override
    public SeleniumEnums setUrl() {
        return AdminSpeedByStreetEnum.DEFAULT_URL;
    }

    
    public class AdminSpeedByStreetLinks extends AdminBarLinks {}

    public class AdminSpeedByStreetTexts extends AdminBarTexts {
        
        public Text title(){
            return new Text(AdminSpeedByStreetEnum.TITLE);
        }
        
        public Text search(){
            return new TextFieldLabel(AdminSpeedByStreetEnum.SEARCH_BOX);
        }
        
        public Text message(){
            return new TextFieldLabel(AdminSpeedByStreetEnum.MESSAGE);
        }
        
        public Text headingAddress(){
            return new Text(AdminSpeedByStreetEnum.HEADING_ADDRESS);
        }
        
        public Text headingSpeedLimit(){
            return new Text(AdminSpeedByStreetEnum.HEADING_SPEED_LIMIT);
        }
        
        public Text headingCurrent(){
            return new Text(AdminSpeedByStreetEnum.HEADING_CURRENT);
        }
        
        public Text headingNew(){
            return new Text(AdminSpeedByStreetEnum.HEADING_NEW);
        }
        
        public Text headingComment(){
            return new Text(AdminSpeedByStreetEnum.HEADING_COMMENT);
        }
        
        public Text address(){
            return new Text(AdminSpeedByStreetEnum.ADDRESS);
        }
        
        public Text speedLimitCurrent(){
            return new Text(AdminSpeedByStreetEnum.SPEED_LIMIT_CURRENT);
        }
    }

    public class AdminSpeedByStreetTextFields extends AdminBarTextFields {
        
        public TextField search(){
            return new TextField(AdminSpeedByStreetEnum.SEARCH_BOX);
        }
        
        public TextField speedLimitNew(){
            return new TextField(AdminSpeedByStreetEnum.SPEED_LIMIT_NEW);
        }
        
        public TextField comment(){
            return new TextField(AdminSpeedByStreetEnum.COMMENT);
        }
    }

    public class AdminSpeedByStreetButtons extends AdminBarButtons {
        
        public TextButton delete(){
            return new TextButton(AdminSpeedByStreetEnum.DELETE);
        }
        
        public TextButton clearAll(){
            return new TextButton(AdminSpeedByStreetEnum.CLEAR_ALL);
        }
        
        public TextButton findAddress(){
            return new TextButton(AdminSpeedByStreetEnum.FIND_ADDRESS);
        }
        
        public TextButton submitRequest(){
            return new TextButton(AdminSpeedByStreetEnum.SUBMIT_REQUEST);
        }
    }

    public class AdminSpeedByStreetDropDowns extends AdminBarDropDowns {}

    public class AdminSpeedByStreetPopUps extends MastheadPopUps {}

    public class AdminSpeedByStreetPager {
        public Paging pageIndex() {
            return new Paging();
        }
    }
    
    public class AdminSpeedByStreetCheckBox {   
        public CheckBoxTable checkBox() {
            return new CheckBoxTable(AdminSpeedByStreetEnum.CHECKBOX);
        }
    }
    /*  FOR FUTURE USE WITH MAPS
     * 
    public class AdminSpeedByStreetMap {
        public GoogleMapType googleMap() {
            return new GoogleMapType(AdminSpeedByStreetEnum.MAP);
        }
    }
    
    public AdminSpeedByStreetMap _map() {
        return new AdminSpeedByStreetMap();
    }
    */
    public AdminSpeedByStreetCheckBox _checkBox() {
        return new AdminSpeedByStreetCheckBox();
    }

    public AdminSpeedByStreetPager _page() {
        return new AdminSpeedByStreetPager();
    }

    public AdminSpeedByStreetLinks _link() {
        return new AdminSpeedByStreetLinks();
    }

    public AdminSpeedByStreetTexts _text() {
        return new AdminSpeedByStreetTexts();
    }

    public AdminSpeedByStreetButtons _button() {
        return new AdminSpeedByStreetButtons();
    }

    public AdminSpeedByStreetTextFields _textField() {
        return new AdminSpeedByStreetTextFields();
    }

    public AdminSpeedByStreetDropDowns _dropDown() {
        return new AdminSpeedByStreetDropDowns();
    }

    public AdminSpeedByStreetPopUps _popUp() {
        return new AdminSpeedByStreetPopUps();
    }

}
