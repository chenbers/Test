package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.DropDown;
import com.inthinc.pro.automation.elements.Link;
import com.inthinc.pro.automation.elements.Text;
import com.inthinc.pro.automation.elements.TextButton;
import com.inthinc.pro.automation.elements.TextField;
import com.inthinc.pro.automation.elements.TextLink;
import com.inthinc.pro.automation.elements.TextTable;
import com.inthinc.pro.automation.elements.TextTableLink;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.selenium.pageEnums.AdminBarEnum;
import com.inthinc.pro.selenium.pageEnums.AdminTables.VehicleColumns;
import com.inthinc.pro.selenium.pageEnums.AdminVehiclesEnum;

public class PageAdminVehicles extends AdminTables {


//    private static final AdminVehiclesEnum[] enums = { AdminVehiclesEnum.STATUS_DHX, AdminVehiclesEnum.ZONE_TYPE_DHX, AdminVehiclesEnum.PRODUCT_DHX };
    private static final AdminVehiclesEnum[] enums = { AdminVehiclesEnum.PRODUCT, AdminVehiclesEnum.ZONE_TYPE, AdminVehiclesEnum.STATUS };

    public PageAdminVehicles() {
        page = "vehicles";
        checkMe.add(AdminVehiclesEnum.TITLE);
    }
    

    @Override
    public SeleniumEnums setUrl() {
        return AdminVehiclesEnum.DEFAULT_URL;
    }

    public AdminVehiclesPopUps _popUp() {
        return new AdminVehiclesPopUps();
    }

    public class AdminVehiclesPopUps extends AdminTablesPopUps {
        public AdminDelete delete()						{return new AdminDelete(true);}
    }

    public class AdminVehiclesButtons extends AdminTablesButtons {
//        public TextButton delete() 						{return new TextButton(AdminBarEnum.DELETE, page);}
        public TextButton vehBatchEdit()				{return new TextButton(AdminVehiclesEnum.VEH_BATCH_EDIT, page);}
        public TextButton searchVehicle() 				{return new TextButton(AdminVehiclesEnum.SEARCH_VEHICLE_BUTTON);}
        public TextButton VehDelete()					{return new TextButton(AdminVehiclesEnum.VEH_DELETE_BUTTON);}
    }

    public class AdminVehiclesDropDowns extends AdminTablesDropDowns {
//        public DropDown filterByProductType()			{return new DHXDropDown(AdminVehiclesEnum.PRODUCT_DHX, enums);}
//        public DropDown filterByZoneType()			{return new DHXDropDown(AdminVehiclesEnum.ZONE_TYPE_DHX, enums);}
//        public DropDown filterByStatus()				{return new DHXDropDown(AdminVehiclesEnum.STATUS_DHX, enums);}
//        public DropDown filterByProductType()			{return new DropDown(AdminVehiclesEnum.PRODUCT);}
        public DropDown product()						{return new DropDown(AdminVehiclesEnum.PRODUCT);}
//        public DropDown filterByProductType()			{return new DHXDropDown(AdminVehiclesEnum.PRODUCT2, enums);}
        public DropDown filterByZoneType()				{return new DropDown(AdminVehiclesEnum.ZONE_TYPE);}
        public DropDown filterByStatus()				{return new DropDown(AdminVehiclesEnum.STATUS);}
    }

    public class AdminVehiclesLinks extends AdminTablesLinks {
    	public TextTableLink editVehicleLink()			{return new TextTableLink(AdminVehiclesEnum.EDIT, page, VehicleColumns.EDIT);}
    	public TextTableLink vehicleEditLink()			{return new TextTableLink(AdminVehiclesEnum.EDIT);}
        public TextTableLink entryVehicleId()			{return new TextTableLink(AdminBarEnum.TABLE_ENTRIES, page, VehicleColumns.VEHICLE_ID);}
        public TextTableLink vehicleId()				{return new TextTableLink(AdminVehiclesEnum.VEH_ID);}

        public TextLink entryEdit()						{return new TextLink(AdminVehiclesEnum.EDIT_LINK);}
        public TextLink sortBy(VehicleColumns column)	{return new TextLink(AdminBarEnum.TABLE_HEADERS, page, column);}
        public TextLink sortByDevice()					{return new TextLink(AdminVehiclesEnum.DEVICE_LINK);}
        public TextLink entryEdit(VehicleColumns col)	{return new TextLink(AdminVehiclesEnum.EDIT, page, col);}
        
        public Link vehAdminRefresh()					{return new Link(AdminVehiclesEnum.REFRESH_LINK);}
    }

    public class AdminVehiclesTextFields extends AdminTablesTextFields {
    	public TextField searchVehicle() 				{return new TextField(AdminVehiclesEnum.SEARCH_VEHICLE_FIELD);}
    }

    public class AdminVehiclesTexts extends AdminTablesTexts {
        public Text labelVehTitle()						{return new Text(AdminVehiclesEnum.VEH_TITLE, page);}
        public TextTable entryTableValue(VehicleColumns column) {return new TextTable(AdminBarEnum.TABLE_ENTRIES, page, column);}
    }

    public AdminVehiclesButtons _button() 				{return new AdminVehiclesButtons();}
    public AdminVehiclesDropDowns _dropDown() 			{return new AdminVehiclesDropDowns();}
    public AdminVehiclesLinks _link() 					{return new AdminVehiclesLinks();}
    public AdminVehiclesTexts _text() 					{return new AdminVehiclesTexts();}
    public AdminVehiclesTextFields _textField() 		{return new AdminVehiclesTextFields();}
    public AdminVehiclesPager _page() 					{return new AdminVehiclesPager();}
    public class AdminVehiclesPager {
        public Paging pageIndex() 						{return new Paging();}
    }

    @Override
    protected boolean checkIsOnPage() 					{
    	return _text().labelVehTitle().isPresent();
    }
}
