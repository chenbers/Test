package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.Text;
import com.inthinc.pro.automation.elements.TextButton;
import com.inthinc.pro.automation.elements.TextLabel;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.selenium.pageEnums.AdminAccountDetailsEnum;

public class PageAdminAccountDetails extends AdminBar {

    public class AdminAccountDetailsLinks extends AdminBarLinks {}

    public class AdminAccountDetailsTexts extends AdminBarTexts {

        public Text title() {
            return new Text(AdminAccountDetailsEnum.TITLE);
        }

        public Text unknownDriverHeader() {
            return new Text(AdminAccountDetailsEnum.UNKNOWN_DRIVER_HEADER);
        }

        public Text valueFirst() {
            return new Text(AdminAccountDetailsEnum.FIRST);
        }

        public Text valueLast() {
            return new Text(AdminAccountDetailsEnum.LAST);
        }

        public Text valueTimeZone() {
            return new Text(AdminAccountDetailsEnum.TIME_ZONE);
        }

        public Text miscellaneousHeader() {
            return new Text(AdminAccountDetailsEnum.MISCELLANEOUS_HEADER);
        }

        public Text valuePhoneAlertsActive() {
            return new Text(AdminAccountDetailsEnum.PHONE_ALERTS_ACTIVE);
        }

        public Text valueNoReplyEmail() {
            return new Text(AdminAccountDetailsEnum.NO_REPLY_EMAIL);
        }

        public Text addressHeader() {
            return new Text(AdminAccountDetailsEnum.ADDRESS_HEADER);
        }

        public Text valueAddress1() {
            return new Text(AdminAccountDetailsEnum.ADDRESS_1);
        }

        public Text valueAddress2() {
            return new Text(AdminAccountDetailsEnum.ADDRESS_2);
        }

        public Text valueCity() {
            return new Text(AdminAccountDetailsEnum.CITY);
        }

        public Text valueState() {
            return new Text(AdminAccountDetailsEnum.STATE);
        }

        public Text valueZipCode() {
            return new Text(AdminAccountDetailsEnum.ZIP_CODE);
        }


        public Text supportContactInformationHeader() {
            return new Text(
                    AdminAccountDetailsEnum.SUPPORT_CONTACT_INFORMATION_HEADER);
        }

        public Text valueContact1() {
            return new Text(AdminAccountDetailsEnum.CONTACT_1);
        }

        public Text valueContact2() {
            return new Text(AdminAccountDetailsEnum.CONTACT_2);
        }

        public Text valueContact3() {
            return new Text(AdminAccountDetailsEnum.CONTACT_3);
        }

        public Text valueContact4() {
            return new Text(AdminAccountDetailsEnum.CONTACT_4);
        }

        public Text valueContact5() {
            return new Text(AdminAccountDetailsEnum.CONTACT_5);
        }

        public Text labelFirst() {
            return new TextLabel(AdminAccountDetailsEnum.FIRST);
        }

        public Text labelLast() {
            return new TextLabel(AdminAccountDetailsEnum.LAST);
        }

        public Text labelTimeZone() {
            return new TextLabel(AdminAccountDetailsEnum.TIME_ZONE);
        }

        public Text labelPhoneAlertsActive() {
            return new TextLabel(AdminAccountDetailsEnum.PHONE_ALERTS_ACTIVE);
        }

        public Text labelNoReplyEmail() {
            return new TextLabel(AdminAccountDetailsEnum.NO_REPLY_EMAIL);
        }


        public Text labelAddress1() {
            return new TextLabel(AdminAccountDetailsEnum.ADDRESS_1);
        }

        public Text labelAddress2() {
            return new TextLabel(AdminAccountDetailsEnum.ADDRESS_2);
        }

        public Text labelCity() {
            return new TextLabel(AdminAccountDetailsEnum.CITY);
        }

        public Text labelState() {
            return new TextLabel(AdminAccountDetailsEnum.STATE);
        }

        public Text labelZipCode() {
            return new TextLabel(AdminAccountDetailsEnum.ZIP_CODE);
        }

        public Text labelContact1() {
            return new TextLabel(AdminAccountDetailsEnum.CONTACT_1);
        }

        public Text labelContact2() {
            return new TextLabel(AdminAccountDetailsEnum.CONTACT_2);
        }

        public Text labelContact3() {
            return new TextLabel(AdminAccountDetailsEnum.CONTACT_3);
        }

        public Text labelContact4() {
            return new TextLabel(AdminAccountDetailsEnum.CONTACT_4);
        }

        public Text labelContact5() {
            return new TextLabel(AdminAccountDetailsEnum.CONTACT_5);
        }
    }

    public class AdminAccountDetailsTextFields extends AdminBarTextFields {}

    public class AdminAccountDetailsButtons extends AdminBarButtons {

        public TextButton valueEditButton() {
            return new TextButton(AdminAccountDetailsEnum.EDIT_BUTTON);
        }
    }

    public class AdminAccountDetailsDropDowns extends AdminBarDropDowns {}

    public class AdminAccountDetailsPopUps extends MastheadPopUps {}

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
    public SeleniumEnums setUrl() {
        return AdminAccountDetailsEnum.DEFAULT_URL;
    }

    @Override
    protected boolean checkIsOnPage() {
        // TODO Auto-generated method stub
        return false;
    }

}
