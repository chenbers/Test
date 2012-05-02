package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.DropDown;
import com.inthinc.pro.automation.elements.Text;
import com.inthinc.pro.automation.elements.TextButton;
import com.inthinc.pro.automation.elements.TextDropDownLabel;
import com.inthinc.pro.automation.elements.TextField;
import com.inthinc.pro.automation.elements.TextFieldLabel;
import com.inthinc.pro.automation.elements.TextLabel;
import com.inthinc.pro.automation.elements.TextLink;
import com.inthinc.pro.automation.elements.organization.OrganizationBase;
import com.inthinc.pro.automation.elements.organization.OrganizationBase.OrganizationType;
import com.inthinc.pro.automation.elements.organization.OrganizationLevels;
import com.inthinc.pro.automation.elements.organization.OrganizationLevels.DivisionLevel;
import com.inthinc.pro.automation.elements.organization.OrganizationLevels.FleetLevel;
import com.inthinc.pro.automation.elements.organization.OrganizationLevels.GroupLevels;
import com.inthinc.pro.automation.elements.organization.OrganizationLevels.TeamLevel;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.selenium.pageEnums.AdminOrganizationEnum;

public class PageAdminOrganization extends AdminBar{

    public PageAdminOrganization() {
        checkMe.add(AdminOrganizationEnum.TITLE);
    }
    
    public class PageAdminOrganizationLinks extends AdminBarLinks {
        
        public TextLink edit(){
            return new TextLink(AdminOrganizationEnum.EDIT_LINK);
        }
    }

    public class PageAdminOrganizationTexts extends AdminBarTexts {
        
        public Text editName(){
            return new TextFieldLabel(AdminOrganizationEnum.EDIT_NAME);
        }
        
        public Text editDescription(){
            return new TextFieldLabel(AdminOrganizationEnum.EDIT_DESCRIPTION);
        }
        
        public Text editAddress1(){
            return new TextFieldLabel(AdminOrganizationEnum.EDIT_ADDRESS_1);
        }
        
        public Text editAddress2(){
            return new TextFieldLabel(AdminOrganizationEnum.EDIT_ADDRESS_2);
        }
        
        public Text editCity(){
            return new TextFieldLabel(AdminOrganizationEnum.EDIT_CITY);
        }
        
        public Text editZipCode(){
            return new TextFieldLabel(AdminOrganizationEnum.EDIT_ZIP_CODE);
        }
        
        public Text editFindAddress(){
            return new TextFieldLabel(AdminOrganizationEnum.EDIT_FIND_ADDRESS);
        }
        
        public Text editParentGroup(){
            return new TextDropDownLabel(AdminOrganizationEnum.EDIT_PARENT_GROUP);
        }
        
        public Text editGroupType(){
            return new TextDropDownLabel(AdminOrganizationEnum.EDIT_GROUP_TYPE);
        }
        
        public Text editManager(){
            return new TextDropDownLabel(AdminOrganizationEnum.EDIT_MANAGER);
        }
        
        public Text editState(){
            return new TextDropDownLabel(AdminOrganizationEnum.EDIT_STATE);
        }
        
        public Text title(){
            return new Text(AdminOrganizationEnum.TITLE);
        }
        
        public Text error(){
            return new Text(AdminOrganizationEnum.TEXT_ERROR);
        }
        
        public Text headerTopGroup(){
            return new Text(AdminOrganizationEnum.HEADER_TOP_GROUP);
        }

        public Text headerSummary(){
            return new Text(AdminOrganizationEnum.HEADER_SUMMARY);
        }

        public Text headerAddress(){
            return new Text(AdminOrganizationEnum.HEADER_ADDRESS);
        }

        public Text headerDefaultMapView(){
            return new Text(AdminOrganizationEnum.HEADER_DEFAULT_MAP_VIEW);
        }

        public Text headerUserInformation(){
            return new Text(AdminOrganizationEnum.HEADER_USER_INFORMATION);
        }

        public Text headerDriverInformation(){
            return new Text(AdminOrganizationEnum.HEADER_DRIVER_INFORMATION);
        }

        public Text headerDriverAssignments(){
            return new Text(AdminOrganizationEnum.HEADER_DRIVER_ASSIGNMENTS);
        }

        public Text headerVehicleInformation(){
            return new Text(AdminOrganizationEnum.HEADER_VEHICLE_INFORMATION);
        }

        public Text headerVehicleProfile(){
            return new Text(AdminOrganizationEnum.HEADER_VEHICLE_PROFILE);
        }

        public Text headerVehicleAssignments(){
            return new Text(AdminOrganizationEnum.HEADER_VEHICLE_ASSIGNMENTS);
        }

        public Text headerDeviceAssignments(){
            return new Text(AdminOrganizationEnum.HEADER_DEVICE_ASSIGNMENTS);
        }

        public Text valueGroupType(){
            return new Text(AdminOrganizationEnum.GROUP_TYPE);
        }

        public Text valueName(){
            return new Text(AdminOrganizationEnum.NAME);
        }

        public Text valueDescription(){
            return new Text(AdminOrganizationEnum.DESCRIPTION);
        }

        public Text valueManager(){
            return new Text(AdminOrganizationEnum.MANAGER);
        }

        public Text valueNumOfVehicles(){
            return new Text(AdminOrganizationEnum.NUM_OF_VEHICLES);
        }

        public Text valueNumOfDrivers(){
            return new Text(AdminOrganizationEnum.NUM_OF_DRIVERS);
        }

        public Text valueAddressOne(){
            return new Text(AdminOrganizationEnum.ADDRESS_ONE);
        }

        public Text valueAddressTwo(){
            return new Text(AdminOrganizationEnum.ADDRESS_TWO);
        }

        public Text valueCity(){
            return new Text(AdminOrganizationEnum.CITY);
        }

        public Text valueState(){
            return new Text(AdminOrganizationEnum.STATE);
        }

        public Text valueZipCode(){
            return new Text(AdminOrganizationEnum.ZIP_CODE);
        }

        public Text valueFirstName(){
            return new Text(AdminOrganizationEnum.FIRST_NAME);
        }

        public Text valueMiddleName(){
            return new Text(AdminOrganizationEnum.MIDDLE_NAME);
        }

        public Text valueLastName(){
            return new Text(AdminOrganizationEnum.LAST_NAME);
        }

        public Text valueSuffix(){
            return new Text(AdminOrganizationEnum.SUFFIX);
        }

        public Text valueDob(){
            return new Text(AdminOrganizationEnum.DOB);
        }

        public Text valueGender(){
            return new Text(AdminOrganizationEnum.GENDER);
        }

        public Text valueDriverLicense(){
            return new Text(AdminOrganizationEnum.DRIVER_LICENSE);
        }

        public Text valueDriverClass(){
            return new Text(AdminOrganizationEnum.DRIVER_CLASS);
        }

        public Text valueDriverState(){
            return new Text(AdminOrganizationEnum.DRIVER_STATE);
        }

        public Text valueDriverExpiration(){
            return new Text(AdminOrganizationEnum.DRIVER_EXPIRATION);
        }

        public Text valueDriverStatus(){
            return new Text(AdminOrganizationEnum.DRIVER_STATUS);
        }

        public Text valueDriverVehicle(){
            return new Text(AdminOrganizationEnum.DRIVER_VEHICLE);
        }

        public Text valueDriverDevice(){
            return new Text(AdminOrganizationEnum.DRIVER_DEVICE);
        }

        public Text valueVehicleProduct(){
            return new Text(AdminOrganizationEnum.VEHICLE_PRODUCT);
        }

        public Text valueVehicleVin(){
            return new Text(AdminOrganizationEnum.VEHICLE_VIN);
        }

        public Text valueVehicleMake(){
            return new Text(AdminOrganizationEnum.VEHICLE_MAKE);
        }

        public Text valueVehicleModel(){
            return new Text(AdminOrganizationEnum.VEHICLE_MODEL);
        }

        public Text valueVehicleYear(){
            return new Text(AdminOrganizationEnum.VEHICLE_YEAR);
        }

        public Text valueVehicleColor(){
            return new Text(AdminOrganizationEnum.VEHICLE_COLOR);
        }

        public Text valueVehicleZoneType(){
            return new Text(AdminOrganizationEnum.VEHICLE_ZONE_TYPE);
        }

        public Text valueVehicleWeight(){
            return new Text(AdminOrganizationEnum.VEHICLE_WEIGHT);
        }

        public Text valueVehicleLicense(){
            return new Text(AdminOrganizationEnum.VEHICLE_LICENSE);
        }

        public Text valueVehicleState(){
            return new Text(AdminOrganizationEnum.VEHICLE_STATE);
        }

        public Text valueVehicleEcallPhone(){
            return new Text(AdminOrganizationEnum.VEHICLE_ECALL_PHONE);
        }

        public Text valueVehicleId(){
            return new Text(AdminOrganizationEnum.VEHICLE_ID);
        }

        public Text valueVehicleStatus(){
            return new Text(AdminOrganizationEnum.VEHICLE_STATUS);
        }

        public Text valueVehicleDriver(){
            return new Text(AdminOrganizationEnum.VEHICLE_DRIVER);
        }

        public Text valueVehicleDeviceProduct(){
            return new Text(AdminOrganizationEnum.VEHICLE_DEVICE_PRODUCT);
        }

        public Text valueVehicleAssignedDevice(){
            return new Text(AdminOrganizationEnum.VEHICLE_ASSIGNED_DEVICE);
        }
        
        
        public Text labelGroupType(){
            return new TextLabel(AdminOrganizationEnum.GROUP_TYPE);
        }

        public Text labelBame(){
            return new TextLabel(AdminOrganizationEnum.NAME);
        }

        public Text labelDescription(){
            return new TextLabel(AdminOrganizationEnum.DESCRIPTION);
        }

        public Text labelManager(){
            return new TextLabel(AdminOrganizationEnum.MANAGER);
        }

        public Text labelNumOfVehicles(){
            return new TextLabel(AdminOrganizationEnum.NUM_OF_VEHICLES);
        }

        public Text labelNumOfDrivers(){
            return new TextLabel(AdminOrganizationEnum.NUM_OF_DRIVERS);
        }

        public Text labelAddressOne(){
            return new TextLabel(AdminOrganizationEnum.ADDRESS_ONE);
        }

        public Text labelAddressTwo(){
            return new TextLabel(AdminOrganizationEnum.ADDRESS_TWO);
        }

        public Text labelCity(){
            return new TextLabel(AdminOrganizationEnum.CITY);
        }

        public Text labelState(){
            return new TextLabel(AdminOrganizationEnum.STATE);
        }

        public Text labelZipCode(){
            return new TextLabel(AdminOrganizationEnum.ZIP_CODE);
        }

        public Text labelFirstName(){
            return new TextLabel(AdminOrganizationEnum.FIRST_NAME);
        }

        public Text labelMiddleName(){
            return new TextLabel(AdminOrganizationEnum.MIDDLE_NAME);
        }

        public Text labelLastName(){
            return new TextLabel(AdminOrganizationEnum.LAST_NAME);
        }

        public Text labelSuffix(){
            return new TextLabel(AdminOrganizationEnum.SUFFIX);
        }

        public Text labelDob(){
            return new TextLabel(AdminOrganizationEnum.DOB);
        }

        public Text labelGender(){
            return new TextLabel(AdminOrganizationEnum.GENDER);
        }

        public Text labelDriverLicense(){
            return new TextLabel(AdminOrganizationEnum.DRIVER_LICENSE);
        }

        public Text labelDriverClass(){
            return new TextLabel(AdminOrganizationEnum.DRIVER_CLASS);
        }

        public Text labelDriverState(){
            return new TextLabel(AdminOrganizationEnum.DRIVER_STATE);
        }

        public Text labelDriverExpiration(){
            return new TextLabel(AdminOrganizationEnum.DRIVER_EXPIRATION);
        }

        public Text labelDriverStatus(){
            return new TextLabel(AdminOrganizationEnum.DRIVER_STATUS);
        }

        public Text labeldDriverVehicle(){
            return new TextLabel(AdminOrganizationEnum.DRIVER_VEHICLE);
        }

        public Text labelDriverDevice(){
            return new TextLabel(AdminOrganizationEnum.DRIVER_DEVICE);
        }

        public Text labelVehicleProduct(){
            return new TextLabel(AdminOrganizationEnum.VEHICLE_PRODUCT);
        }

        public Text labelVehicleVin(){
            return new TextLabel(AdminOrganizationEnum.VEHICLE_VIN);
        }

        public Text labelVehicleMake(){
            return new TextLabel(AdminOrganizationEnum.VEHICLE_MAKE);
        }

        public Text labelVehicleModel(){
            return new TextLabel(AdminOrganizationEnum.VEHICLE_MODEL);
        }

        public Text labelVehicleYear(){
            return new TextLabel(AdminOrganizationEnum.VEHICLE_YEAR);
        }

        public Text labelVehicleColor(){
            return new TextLabel(AdminOrganizationEnum.VEHICLE_COLOR);
        }

        public Text labelVehicleZoneType(){
            return new TextLabel(AdminOrganizationEnum.VEHICLE_ZONE_TYPE);
        }

        public Text labelVehicleWeight(){
            return new TextLabel(AdminOrganizationEnum.VEHICLE_WEIGHT);
        }

        public Text labelVehicleLicense(){
            return new TextLabel(AdminOrganizationEnum.VEHICLE_LICENSE);
        }

        public Text labelVehicleState(){
            return new TextLabel(AdminOrganizationEnum.VEHICLE_STATE);
        }

        public Text labelVehicleEcallPhone(){
            return new TextLabel(AdminOrganizationEnum.VEHICLE_ECALL_PHONE);
        }

        public Text labelVehicleId(){
            return new TextLabel(AdminOrganizationEnum.VEHICLE_ID);
        }

        public Text labelVehicleStatus(){
            return new TextLabel(AdminOrganizationEnum.VEHICLE_STATUS);
        }

        public Text labelVehicleDriver(){
            return new TextLabel(AdminOrganizationEnum.VEHICLE_DRIVER);
        }

        public Text labelVehicleDeviceProduct(){
            return new TextLabel(AdminOrganizationEnum.VEHICLE_DEVICE_PRODUCT);
        }

        public Text labelVehicleAssignedDevice(){
            return new TextLabel(AdminOrganizationEnum.VEHICLE_ASSIGNED_DEVICE);
        }
    }

    public class PageAdminOrganizationTextFields extends AdminBarTextFields {
        
        public TextField editName(){
            return new TextField(AdminOrganizationEnum.EDIT_NAME);
        }
        
        public TextField editDescription(){
            return new TextField(AdminOrganizationEnum.EDIT_DESCRIPTION);
        }
        
        public TextField editAddress1(){
            return new TextField(AdminOrganizationEnum.EDIT_ADDRESS_1);
        }
        
        public TextField editAddress2(){
            return new TextField(AdminOrganizationEnum.EDIT_ADDRESS_2);
        }
        
        public TextField editCity(){
            return new TextField(AdminOrganizationEnum.EDIT_CITY);
        }
        
        public TextField editZipCode(){
            return new TextField(AdminOrganizationEnum.EDIT_ZIP_CODE);
        }
        
        public TextField editFindAddress(){
            return new TextField(AdminOrganizationEnum.EDIT_FIND_ADDRESS);
        }
    }

    public class PageAdminOrganizationButtons extends AdminBarButtons {
        
        public TextButton edit(){
            return new TextButton(AdminOrganizationEnum.EDIT_BUTTON);
        }
        
        public TextButton add(){
            return new TextButton(AdminOrganizationEnum.ADD);
        }
        
        public TextButton delete(){
            return new TextButton(AdminOrganizationEnum.DELETE);
        }
        
        public TextButton topSave(){
            return new TextButton(AdminOrganizationEnum.TOP_SAVE);
        }
        
        public TextButton topCancel(){
            return new TextButton(AdminOrganizationEnum.TOP_CANCEL);
        }
        
        public TextButton bottomSave(){
            return new TextButton(AdminOrganizationEnum.BOTTOM_SAVE);
        }
        
        public TextButton bottomCancel(){
            return new TextButton(AdminOrganizationEnum.BOTTOM_CANCEL);
        }
        
        public TextButton locate(){
            return new TextButton(AdminOrganizationEnum.EDIT_LOCATE);
        }
    }

    public class PageAdminOrganizationDropDowns extends AdminBarDropDowns {
        
        public DropDown parentGroup(){
            return new DropDown(AdminOrganizationEnum.EDIT_PARENT_GROUP);
        }
        
        public DropDown groupType(){
            return new DropDown(AdminOrganizationEnum.EDIT_GROUP_TYPE);
        }
        
        public DropDown manager(){
            return new DropDown(AdminOrganizationEnum.EDIT_MANAGER);
        }
        
        public DropDown state(){
            return new DropDown(AdminOrganizationEnum.EDIT_STATE);
        }
        
        public DropDown DOT(){
            return new DropDown(AdminOrganizationEnum.EDIT_DOT_ADDRESS_TYPE);
        }
    }

    public class PageAdminOrganizationPopUps extends MastheadPopUps {}

    public class PageAdminOrganizationPager {
        public Paging pageIndex() {
            return new Paging();
        }
    }

    public PageAdminOrganizationPager _page() {
        return new PageAdminOrganizationPager();
    }

    public PageAdminOrganizationLinks _link() {
        return new PageAdminOrganizationLinks();
    }

    public PageAdminOrganizationTexts _text() {
        return new PageAdminOrganizationTexts();
    }

    public PageAdminOrganizationButtons _button() {
        return new PageAdminOrganizationButtons();
    }

    public PageAdminOrganizationTextFields _textField() {
        return new PageAdminOrganizationTextFields();
    }

    public PageAdminOrganizationDropDowns _dropDown() {
        return new PageAdminOrganizationDropDowns();
    }

    public PageAdminOrganizationPopUps _popUp() {
        return new PageAdminOrganizationPopUps();
    }
    
    public DivisionLevel getDivision(){
        return OrganizationLevels.getDivision();
    }
    
    public TeamLevel getTeam(){
        return OrganizationLevels.getTeam();
    }
    
    public FleetLevel getFleet(){
        return OrganizationLevels.getFleet();
    }
    
    public boolean isFleetTop(){
        return getSelenium().isElementPresent("//td[@id='display-form:tree:0::fleet:text");
    }
    
    public boolean isDivisionTop(){
        return getSelenium().isElementPresent("//td[@id='display-form:tree:0::division:text");
    }
    
    public boolean isTeamTop(){
        return getSelenium().isElementPresent("//td[@id='display-form:tree:0::team:text");
    }
    
    public GroupLevels getTopLevel(){
        GroupLevels returnable = null;
        if (isFleetTop()){
            returnable = OrganizationLevels.getFleet();
        } else if (isDivisionTop()){
            returnable = OrganizationLevels.getDivision();
        } else if (isTeamTop()){
            returnable = OrganizationLevels.getTeam();
        }
        return returnable;
    }
    
    public GroupLevels clickToGroup(String groups){
        return new OrganizationBase(OrganizationType.FLEET).goToGroup(groups);
    }

    @Override
    public SeleniumEnums setUrl() {
        return AdminOrganizationEnum.DEFAULT_URL;
    }

    @Override
    protected boolean checkIsOnPage() {
        return _text().title().isPresent();
    }
}
