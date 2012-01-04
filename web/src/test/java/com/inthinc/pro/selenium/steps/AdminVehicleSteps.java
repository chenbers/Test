package com.inthinc.pro.selenium.steps;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Pending;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import com.inthinc.pro.selenium.pageEnums.AdminTables.UserColumns;
import com.inthinc.pro.selenium.pageObjects.PageAdminUserAddEdit;
import com.inthinc.pro.selenium.pageObjects.PageAdminUserDetails;
import com.inthinc.pro.selenium.pageObjects.PageAdminUsers;
import com.inthinc.pro.selenium.pageObjects.PageAdminVehicleEdit;
import com.inthinc.pro.selenium.pageObjects.PageAdminVehicleView;
import com.inthinc.pro.selenium.pageObjects.PageAdminVehicles;

public class AdminVehicleSteps extends AdminSteps { 
    
    private PageAdminVehicles vehicles = new PageAdminVehicles();
    private PageAdminVehicleView vehicleDetails = new PageAdminVehicleView();
    private PageAdminVehicleEdit vehicleAddEdit = new PageAdminVehicleEdit();
    
    

}
