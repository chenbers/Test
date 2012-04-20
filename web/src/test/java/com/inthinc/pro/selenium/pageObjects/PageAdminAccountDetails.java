package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.DropDown;
import com.inthinc.pro.automation.elements.Text;
import com.inthinc.pro.automation.elements.TextButton;
import com.inthinc.pro.automation.elements.TextDropDownLabel;
import com.inthinc.pro.automation.elements.TextField;
import com.inthinc.pro.automation.elements.TextFieldLabel;
import com.inthinc.pro.automation.elements.TextLink;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.selenium.pageEnums.AdminAccountDetailsEnum;
import com.inthinc.pro.selenium.pageEnums.AdminEditAccountEnum;

public class PageAdminAccountDetails extends AdminBar {
    
    

    @Override
    public SeleniumEnums setUrl() {
        return AdminAccountDetailsEnum.URL;
    }
    
    public class AdminAccountDetailsLinks extends AdminBarLinks {
        
        public TextLink example() {
            return new TextLink(AdminEditAccountEnum.EXAMPLE);
        }
    }

    public class AdminAccountDetailsTexts extends AdminBarTexts {
        

        public Text labelFirst(){
            return new TextFieldLabel(AdminEditAccountEnum.FIRST);
        }
        
        public Text labelLast() {
            return new TextFieldLabel(AdminEditAccountEnum.LAST);
        }
        
        public Text labelNoReplyEmail() {
            return new TextFieldLabel(AdminEditAccountEnum.NO_REPLY_EMAIL);
        }
        
        public Text labelAddress1() {
            return new TextFieldLabel(AdminEditAccountEnum.ADDRESS_1);
        }
        
        public Text labelZipCode() {
            return new TextFieldLabel(AdminEditAccountEnum.ZIP_CODE);
        }
        
        public Text labelAddress2() {
            return new TextFieldLabel(AdminEditAccountEnum.ADDRESS_2);
        }
        
        public Text labelUrl() {
            return new TextFieldLabel(AdminEditAccountEnum.URL);
        }
        
        public Text labelQuery() {
            return new TextFieldLabel(AdminEditAccountEnum.QUERY);
        }
        
        public Text labelLayers() {
            return new TextFieldLabel(AdminEditAccountEnum.LAYERS);
        }
        
        public Text labelQueryParameters() {
            return new TextFieldLabel(AdminEditAccountEnum.QUERY_PARAMETERS);
        }
        
        public Text labelContact1() {
            return new TextFieldLabel(AdminEditAccountEnum.CONTACT_1);
        }
        
        public Text labelContact2() {
            return new TextFieldLabel(AdminEditAccountEnum.CONTACT_2);
        }
        
        public Text labelContact3() {
            return new TextFieldLabel(AdminEditAccountEnum.CONTACT_3);
        }
        
        public Text labelContact4() {
            return new TextFieldLabel(AdminEditAccountEnum.CONTACT_4);
        }
        
        public Text labelContact5() {
            return new TextFieldLabel(AdminEditAccountEnum.CONTACT_5);
        }
        
        public Text labelTimeZone() {
            return new TextDropDownLabel(AdminEditAccountEnum.TIME_ZONE);
        }
        
        public Text labelPhoneAlertsActive() {
            return new TextDropDownLabel(AdminEditAccountEnum.PHONE_ALERTS_ACTIVE);
        }
        
        public Text labelState() {
            return new TextDropDownLabel(AdminEditAccountEnum.STATE);
        }
        
        public Text headerUnknownDriver() {
            return new Text(AdminEditAccountEnum.UNKNOWN_DRIVER_HEADER);
        }
        
        public Text headerMiscellaneous() {
            return new Text(AdminEditAccountEnum.MISCELLANEOUS_HEADER);
        }
        
        public Text headerAddress() {
            return new Text(AdminEditAccountEnum.ADDRESS_HEADER);
        }
        
        public Text headerWebMapService() {
            return new Text(AdminEditAccountEnum.WEB_MAP_SERVICE_HEADER);
        }
        
        public Text headerSupportContactInformation() {
            return new Text(AdminEditAccountEnum.SUPPORT_CONTACT_INFORMATION_HEADER);
        }
        
        public Text title(){
            return new Text(AdminEditAccountEnum.TITLE);
        }
        
    }
    

    public class AdminAccountDetailsTextFields extends AdminBarTextFields {
        
        public TextField first(){
            return new TextField(AdminEditAccountEnum.FIRST);
        }
        
        public TextField last() {
            return new TextField(AdminEditAccountEnum.LAST);
        }
        
        public TextField noReplyEmail() {
            return new TextField(AdminEditAccountEnum.NO_REPLY_EMAIL);
        }
        
        public TextField address1() {
            return new TextField(AdminEditAccountEnum.ADDRESS_1);
        }
        
        public TextField zipCode() {
            return new TextField(AdminEditAccountEnum.ZIP_CODE);
        }
        
        public TextField address2() {
            return new TextField(AdminEditAccountEnum.ADDRESS_2);
        }
        
        public TextField url() {
            return new TextField(AdminEditAccountEnum.URL);
        }
        
        public TextField query() {
            return new TextField(AdminEditAccountEnum.QUERY);
        }
        
        public TextField layers() {
            return new TextField(AdminEditAccountEnum.LAYERS);
        }
        
        public TextField queryParameters() {
            return new TextField(AdminEditAccountEnum.QUERY_PARAMETERS);
        }
        
        public TextField contact1() {
            return new TextField(AdminEditAccountEnum.CONTACT_1);
        }
        
        public TextField contact2() {
            return new TextField(AdminEditAccountEnum.CONTACT_2);
        }
        
        public TextField contact3() {
            return new TextField(AdminEditAccountEnum.CONTACT_3);
        }
        
        public TextField contact4() {
            return new TextField(AdminEditAccountEnum.CONTACT_4);
        }
        
        public TextField contact5() {
            return new TextField(AdminEditAccountEnum.CONTACT_5);
        }
    }

    public class AdminAccountDetailsButtons extends AdminBarButtons {
        
        public TextButton saveTop() {
            return new TextButton(AdminEditAccountEnum.SAVE_TOP);
        }
        
        public TextButton saveBottom() {
            return new TextButton(AdminEditAccountEnum.SAVE_BOTTOM);
        }
        
        public TextButton cancelTop() {
            return new TextButton(AdminEditAccountEnum.CANCEL_TOP);
        }
        
        public TextButton cancelBottom() {
            return new TextButton(AdminEditAccountEnum.CANCEL_BOTTOM);
        }
    }

    public class AdminAccountDetailsDropDowns extends AdminBarDropDowns {
        
        public DropDown timeZone() {
            return new DropDown(AdminEditAccountEnum.TIME_ZONE);
        }
        
        public DropDown phoneAlertsActive() {
            return new DropDown(AdminEditAccountEnum.PHONE_ALERTS_ACTIVE);
        }
        
        public DropDown state() {
            return new DropDown(AdminEditAccountEnum.STATE);
        }
        
    }

    public class AdminAccountDetailsPopUps extends MastheadPopUps {}

    public class AdminAccountDetailsPager {
        public Paging pageIndex() {
            return new Paging();
        }
    }

    public AdminAccountDetailsPager _page() {
        return new AdminAccountDetailsPager();
    }

    public AdminAccountDetailsLinks _link() {
        return new AdminAccountDetailsLinks();
    }

    public AdminAccountDetailsTexts _text() {
        return new AdminAccountDetailsTexts();
    }

    public AdminAccountDetailsButtons _button() {
        return new AdminAccountDetailsButtons();
    }

    public AdminAccountDetailsTextFields _textField() {
        return new AdminAccountDetailsTextFields();
    }

    public AdminAccountDetailsDropDowns _dropDown() {
        return new AdminAccountDetailsDropDowns();
    }

    public AdminAccountDetailsPopUps _popUp() {
        return new AdminAccountDetailsPopUps();
    }

    @Override
    protected boolean checkIsOnPage() {
        // TODO Auto-generated method stub
        return false;
    }

}
