package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.CalendarObject;
import com.inthinc.pro.automation.elements.DropDown;
import com.inthinc.pro.automation.elements.Link;
import com.inthinc.pro.automation.elements.Text;
import com.inthinc.pro.automation.elements.TextButton;
import com.inthinc.pro.automation.elements.TextField;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.selenium.pageEnums.AdminAddEditHazardEnum;

public class PageAdminAddEditHazard extends AdminBar {

    public PageAdminAddEditHazard() {
        checkMe.add(AdminAddEditHazardEnum.TITLE);
    }

    public class AdminAddEditHazardLinks extends AdminBarLinks {
    	
    	public Link collapse() {
    		return new Link(AdminAddEditHazardEnum.COLLAPSE);
    	}
    }

    public class AdminAddEditHazardTexts extends AdminBarTexts {
        
        public Text title(){
            return new Text(AdminAddEditHazardEnum.TITLE);
        }
        
        public Text labelFindAddress() {
        	return new Text(AdminAddEditHazardEnum.LABEL_FIND_ADDRESS);
        }
        
        public Text labelLatitude() {
        	return new Text(AdminAddEditHazardEnum.LABEL_LATITUDE);
        }
        
        public Text labelLongitude() {
        	return new Text(AdminAddEditHazardEnum.LABEL_LONGITUDE);
        }
        
        public Text labelType() {
        	return new Text(AdminAddEditHazardEnum.LABEL_TYPE);
        }
        
        public Text labelRadius() {
        	return new Text(AdminAddEditHazardEnum.LABEL_RADIUS);
        }
        
        public Text labelStartTime() {
        	return new Text(AdminAddEditHazardEnum.LABEL_START_TIME);
        }
        
        public Text labelExpTime() {
        	return new Text(AdminAddEditHazardEnum.LABEL_EXP_TIME);
        }
        
        public Text labelDescription() {
        	return new Text(AdminAddEditHazardEnum.LABEL_DESCRIPTION);
        }
        
        public Text latitude() {
        	return new Text(AdminAddEditHazardEnum.LATITUDE_TEXT);
        }
        
        public Text longitude() {
        	return new Text(AdminAddEditHazardEnum.LONGITUDE_TEXT);
        }

    }

    public class AdminAddEditHazardTextFields extends AdminBarTextFields {
        
        public TextField findAddress(){
            return new TextField(AdminAddEditHazardEnum.FIND_ADDRESS_TEXTFIELD);
        }
        
        public TextField radius(){
            return new TextField(AdminAddEditHazardEnum.RADIUS_TEXTFIELD);
        }
        
        public TextField description(){
            return new TextField(AdminAddEditHazardEnum.DESCRIPTION_TEXTFIELD);
        }

    }

    public class AdminAddEditHazardButtons extends AdminBarButtons {
        
        public TextButton locate(){
            return new TextButton(AdminAddEditHazardEnum.LOCATE_BUTTON);
        }
        
        public TextButton reset(){
            return new TextButton(AdminAddEditHazardEnum.RESET_BUTTON);
        }
        
        public TextButton save(){
            return new TextButton(AdminAddEditHazardEnum.SAVE_BUTTON);
        }
        
        public TextButton cancel(){
            return new TextButton(AdminAddEditHazardEnum.CANCEL_BUTTON);
        }
        
    }

    public class AdminAddEditHazardDropDowns extends AdminBarDropDowns {
        
        public DropDown type(){
            return new DropDown(AdminAddEditHazardEnum.TYPE_DROPDOWN);
        }
        
        public DropDown radius(){
            return new DropDown(AdminAddEditHazardEnum.RADIUS_DROPDOWN);
        }
        
        public DropDown startTime(){
            return new CalendarObject(AdminAddEditHazardEnum.START_TIME_DROPDOWN);
        }
        
        public DropDown expTime(){
            return new CalendarObject(AdminAddEditHazardEnum.EXP_TIME_DROPDOWN);
        }

    }
    
    public class AddEditHazardCheckBoxs{}
    
    public AddEditHazardCheckBoxs _checkBox(){
        return new AddEditHazardCheckBoxs();
    }
    
    public class AdminAddEditHazardPopUps extends MastheadPopUps {}

    public class AdminAddEditHazardPager {
        public Paging pageIndex() {
            return new Paging();
        }
    }

    public AdminAddEditHazardPager _page() {
        return new AdminAddEditHazardPager();
    }

    public AdminAddEditHazardLinks _link() {
        return new AdminAddEditHazardLinks();
    }

    public AdminAddEditHazardTexts _text() {
        return new AdminAddEditHazardTexts();
    }

    public AdminAddEditHazardButtons _button() {
        return new AdminAddEditHazardButtons();
    }

    public AdminAddEditHazardTextFields _textField() {
        return new AdminAddEditHazardTextFields();
    }

    public AdminAddEditHazardDropDowns _dropDown() {
        return new AdminAddEditHazardDropDowns();
    }

    public AdminAddEditHazardPopUps _popUp() {
        return new AdminAddEditHazardPopUps();
    }

    @Override
    public SeleniumEnums setUrl() {
        return AdminAddEditHazardEnum.URL;
    }

    @Override
    protected boolean checkIsOnPage() {
        return _button().locate().isPresent() && _dropDown().type().isPresent();
    }
}