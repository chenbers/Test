package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.Text;
import com.inthinc.pro.automation.elements.TextButton;
import com.inthinc.pro.automation.elements.TextLabel;
import com.inthinc.pro.automation.elements.TextLink;
import com.inthinc.pro.automation.enums.SeleniumEnumWrapper;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.selenium.pageEnums.AdminBarEnum;
import com.inthinc.pro.selenium.pageEnums.AdminTables.UserColumns;
import com.inthinc.pro.selenium.pageEnums.AdminUserDetailsEnum;

public class PageAdminUserDetails extends AdminBar {

    public PageAdminUserDetails() {
        checkMe.add(AdminUserDetailsEnum.FIRST_NAME);
        checkMe.add(AdminUserDetailsEnum.EMPLOYEE_INFORMATION);
        checkMe.add(AdminUserDetailsEnum.NOTIFICATIONS);
        checkMe.add(AdminUserDetailsEnum.USER_INFORMATION);
    }

    @Override
    public SeleniumEnums setUrl() {
        return AdminUserDetailsEnum.DEFAULT_URL;
    }

    public class UserDetailsPopUps extends AdminBarPopUps {

        public UserDetailsPopUps() {
            super(page);
        }

        public AdminDelete deleteUser() {
            return new AdminDelete(false);
        }
    }

    public class UserDetailsButtons extends AdminBarButtons {

        public TextButton delete() {
            return new TextButton(AdminBarEnum.DETAILS_DELETE, page);
        }

        public TextButton edit() {
            return new TextButton(AdminBarEnum.EDIT, page);
        }
    }

    public class UserDetailsDropDowns extends AdminBarDropDowns {}

    public class UserDetailsLinks extends AdminBarLinks {

        public TextLink backToUsers() {
            return new TextLink(AdminBarEnum.GO_BACK, page);
        }
        
        public TextLink emailOne() {
        	return new TextLink(AdminUserDetailsEnum.EMAIL_1);
        }
        
        public TextLink emailTwo() {
        	return new TextLink(AdminUserDetailsEnum.EMAIL_2);
        }
    }

    public class UserDetailsTextFields extends AdminBarTextFields {}

    public class UserDetailsTexts extends AdminBarTexts {

        public TextLabel labels(UserColumns value) {
            SeleniumEnumWrapper temp = new SeleniumEnumWrapper(value);
            temp.setID("display-form:"+temp.getIDs()[0]);
            return new TextLabel(temp);
        }

        public Text values(UserColumns value) {
            SeleniumEnumWrapper temp = new SeleniumEnumWrapper(value);
            temp.setID("display-form:"+temp.getIDs()[0]);
            return new Text(temp);
        }
        
        public Text firstName() {
        	return new Text(AdminUserDetailsEnum.FIRST_NAME);
        }
        
        public Text middleName() {
        	return new Text(AdminUserDetailsEnum.MIDDLE_NAME);
        }
        
        public Text lastName() {
        	return new Text(AdminUserDetailsEnum.LAST_NAME);
        }
        
        public Text suffix() {
            return new Text(AdminUserDetailsEnum.SUFFIX);
        }
        
        public Text dob() {
            return new Text(AdminUserDetailsEnum.DOB);
        }
        
        public Text gender() {
            return new Text(AdminUserDetailsEnum.GENDER);
        }
        
        public Text driverLicenseNumber() {
            return new Text(AdminUserDetailsEnum.DRIVER_LICENSE_NUMBER);
        }
        
        public Text Class() {
            return new Text(AdminUserDetailsEnum.CLASS);
        }
        
        public Text state() {
            return new Text(AdminUserDetailsEnum.STATE);
        }
        
        public Text expiration() {
            return new Text(AdminUserDetailsEnum.EXPIRATION);
        }
        
        public Text certifications() {
            return new Text(AdminUserDetailsEnum.CERTIFICATIONS);
        }
        
        public Text dot() {
            return new Text(AdminUserDetailsEnum.DOT);
        }
        
        public Text team() {
            return new Text(AdminUserDetailsEnum.TEAM);
        }
        
        public Text driverStatus() {
            return new Text(AdminUserDetailsEnum.DRIVER_STATUS);
        }
        
        public Text rFIDBarCode() {
            return new Text(AdminUserDetailsEnum.RFID_BAR_CODE);
        }
        
        public Text rFIDOne() {
            return new Text(AdminUserDetailsEnum.RFID_1);
        }
        
        public Text rFIDTwo() {
            return new Text(AdminUserDetailsEnum.RFID_2);
        }
        
        public Text oneWireID() {
            return new Text(AdminUserDetailsEnum.ONE_WIRE_ID);
        }
        
        public Text employeeID() {
            return new Text(AdminUserDetailsEnum.EMP_ID);
        }
        
        public Text reportsTo() {
            return new Text(AdminUserDetailsEnum.REPORTS_TO);
        }
        
        public Text title() {
            return new Text(AdminUserDetailsEnum.TITLE);
        }
        
        public Text locale() {
        	return new Text(AdminUserDetailsEnum.LOCALE);
        }
        
        public Text timeZone() {
            return new Text(AdminUserDetailsEnum.TIME_ZONE);
        }
        
        public Text measurement() {
        	return new Text(AdminUserDetailsEnum.MEASUREMENT);
        }
        
        public Text fuelEfficiencyRatio() {
        	return new Text(AdminUserDetailsEnum.FUEL_EFFICIENCY_RATIO);
        }
        
        public Text userName() {
        	return new Text(AdminUserDetailsEnum.USER_NAME);
        }
        
        public Text group() {
        	return new Text(AdminUserDetailsEnum.GROUP);
        }
        
        public Text roles() {
            return new Text(AdminUserDetailsEnum.ROLES);
        }
        
        public Text status() {
            return new Text(AdminUserDetailsEnum.USER_STATUS);
        }
        
        public Text textMessageOne() {
        	return new Text(AdminUserDetailsEnum.TEXT_1);
        }
        
        public Text textMessageTwo() {
        	return new Text(AdminUserDetailsEnum.TEXT_2);
        }
        
        public Text phoneOne() {
        	return new Text(AdminUserDetailsEnum.PHONE_1);
        }
        
        public Text phoneTwo() {
        	return new Text(AdminUserDetailsEnum.PHONE_2);
        }

        public Text redFlagInfo() {
        	return new Text(AdminUserDetailsEnum.INFORMATION);
        }
        
        public Text redFlagWarn() {
        	return new Text(AdminUserDetailsEnum.WARNING);
        }
        
        public Text redFlagCritical() {
        	return new Text(AdminUserDetailsEnum.CRITICAL);
        }
        
    }

    private static String page = "person";

    public UserDetailsButtons _button() {
        return new UserDetailsButtons();
    }

    public UserDetailsDropDowns _dropDown() {
        return new UserDetailsDropDowns();
    }

    public UserDetailsLinks _link() {
        return new UserDetailsLinks();
    }

    public UserDetailsTexts _text() {
        return new UserDetailsTexts();
    }

    public UserDetailsTextFields _textField() {
        return new UserDetailsTextFields();
    }
    
    public UserDetailsPopUps _popUp() {
        return new UserDetailsPopUps();
    }

    // Helper methods
    public String getFullName() {
        String firstname = this._text().values(UserColumns.FIRST_NAME).getText();
        String lastname = this._text().values(UserColumns.LAST_NAME).getText();
        String middlename = this._text().values(UserColumns.MIDDLE_NAME).getText();
        String suffix = this._text().values(UserColumns.SUFFIX).getText();
        return (firstname + " " + middlename + " " + lastname + " " + suffix).replace("  ", " ").trim();
    }

    @Override
    protected boolean checkIsOnPage() {
        return _link().backToUsers().isPresent();// && _text().labels(UserColumns.FIRST_NAME).isPresent();
    }

}
