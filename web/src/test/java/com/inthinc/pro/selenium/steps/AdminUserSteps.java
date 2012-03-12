package com.inthinc.pro.selenium.steps;

import org.jbehave.core.annotations.Alias;
import org.jbehave.core.annotations.Composite;
import org.jbehave.core.annotations.Pending;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import com.inthinc.pro.selenium.pageEnums.AdminTables.UserColumns;
import com.inthinc.pro.selenium.pageObjects.PageAdminUserAddEdit;
import com.inthinc.pro.selenium.pageObjects.PageAdminUserDetails;
import com.inthinc.pro.selenium.pageObjects.PageAdminUsers;

public class AdminUserSteps extends AdminSteps { 
    
    private static final PageAdminUsers adminUsers = new PageAdminUsers();
    private static final PageAdminUserDetails adminDetails = new PageAdminUserDetails();
    private static final PageAdminUserAddEdit adminEditAccount = new PageAdminUserAddEdit();
    public String fullname, firstName, middleName, lastName;
    
    @When("I select users")
    @Alias("I click the Users tab")
    public void whenISelectUsers() {
        adminUsers._link().adminUsers().click();
    }
    
    @When("I select a valid user")
    public void whenISelectAValidUser() {
        if(adminUsers._link().sortByName().validatePresence(false))
        {
            adminUsers._link().editColumns().click();
            adminUsers._popUp().editColumns()._checkBox().row(1).check();
            adminUsers._popUp().editColumns()._button().save().click();
        }
        adminUsers._link().tableEntryUserFullName().row(1).click();
    }
    
    @When("I record a users name from the table")
    public void whenIRecordAUsersNameFromTheTable(){
        fullname = adminUsers._link().tableEntryUserFullName().row(1).getText();   
    }
    
    @When("I click the edit link")
    public void whenIClickTheEditLink() {
        adminDetails._button().edit().click();
    }
    
    @When("I change the user's login status to inactive")
    public void whenIChangeTheUsersLoginStatusToInactive() {
        adminEditAccount._dropDown().userStatus().focus();
        adminEditAccount._dropDown().userStatus().select("Inactive"); 
    }
    
    @When("I click the save button on the Edit User page")
    public void whenIClickTheSaveButtonOnTheEditUserPage() {
        adminEditAccount._button().saveBottom().click();
    }
    
    @When("I click the save button on the Edit Columns page")
    public void whenIClickTheSaveButtonOnTheEditColumnsPage() {
        adminUsers._popUp().editColumns()._button().save().click();
    }
    
    @When("I click the Edit Columns button")
    public void whenIClickTheEditColumnsButton(){
        adminUsers._link().editColumns().click();
    }

    @When("I checkmark Email 1")
    public void whenICheckmarkEmail1(){
        if(!adminUsers._popUp().editColumns()._checkBox().row(4).check().isChecked())
        {
            adminUsers._popUp().editColumns()._checkBox().row(4).check();
        }
    }

    @When("I checkmark Email 2")
    public void whenICheckmarkEmail2(){
        if(!adminUsers._popUp().editColumns()._checkBox().row(5).check().isChecked())
        {
            adminUsers._popUp().editColumns()._checkBox().row(5).check();
        }
    }
    
    @When("I checkmark all boxes in the Edit Columns box")
    public void whenICheckmarkAllBoxesInTheEditColumnsBox(){
        
        if(!adminUsers._popUp().editColumns()._checkBox().row(1).check().isChecked())
        {
            adminUsers._popUp().editColumns()._checkBox().row(1).check();
        }
        
        if(!adminUsers._popUp().editColumns()._checkBox().row(2).check().isChecked())
        {
            adminUsers._popUp().editColumns()._checkBox().row(2).check();
        }
        
        if(!adminUsers._popUp().editColumns()._checkBox().row(3).check().isChecked())
        {
            adminUsers._popUp().editColumns()._checkBox().row(3).check();
        }
        
        if(!adminUsers._popUp().editColumns()._checkBox().row(4).check().isChecked())
        {
            adminUsers._popUp().editColumns()._checkBox().row(4).check();
        }
        
        if(!adminUsers._popUp().editColumns()._checkBox().row(5).check().isChecked())
        {
            adminUsers._popUp().editColumns()._checkBox().row(5).check();
        }
        
        if(!adminUsers._popUp().editColumns()._checkBox().row(6).check().isChecked())
        {
            adminUsers._popUp().editColumns()._checkBox().row(6).check();
        }
        
        if(!adminUsers._popUp().editColumns()._checkBox().row(7).check().isChecked())
        {
            adminUsers._popUp().editColumns()._checkBox().row(7).check();
        }
        
        if(!adminUsers._popUp().editColumns()._checkBox().row(8).check().isChecked())
        {
            adminUsers._popUp().editColumns()._checkBox().row(8).check();
        }
        
        if(!adminUsers._popUp().editColumns()._checkBox().row(9).check().isChecked())
        {
            adminUsers._popUp().editColumns()._checkBox().row(9).check();
        }
        
        if(!adminUsers._popUp().editColumns()._checkBox().row(10).check().isChecked())
        {
            adminUsers._popUp().editColumns()._checkBox().row(10).check();
        }
        
        if(!adminUsers._popUp().editColumns()._checkBox().row(11).check().isChecked())
        {
            adminUsers._popUp().editColumns()._checkBox().row(11).check();
        }
        
        if(!adminUsers._popUp().editColumns()._checkBox().row(12).check().isChecked())
        {
            adminUsers._popUp().editColumns()._checkBox().row(12).check();
        }
        
        if(!adminUsers._popUp().editColumns()._checkBox().row(13).check().isChecked())
        {
            adminUsers._popUp().editColumns()._checkBox().row(13).check();
        }
        
        if(!adminUsers._popUp().editColumns()._checkBox().row(14).check().isChecked())
        {
            adminUsers._popUp().editColumns()._checkBox().row(14).check();
        }
        
        if(!adminUsers._popUp().editColumns()._checkBox().row(5).check().isChecked())
        {
            adminUsers._popUp().editColumns()._checkBox().row(5).check();
        }
        
        if(!adminUsers._popUp().editColumns()._checkBox().row(15).check().isChecked())
        {
            adminUsers._popUp().editColumns()._checkBox().row(15).check();
        }
        
        if(!adminUsers._popUp().editColumns()._checkBox().row(16).check().isChecked())
        {
            adminUsers._popUp().editColumns()._checkBox().row(16).check();
        }
        
        if(!adminUsers._popUp().editColumns()._checkBox().row(17).check().isChecked())
        {
            adminUsers._popUp().editColumns()._checkBox().row(17).check();
        }
        
        if(!adminUsers._popUp().editColumns()._checkBox().row(18).check().isChecked())
        {
            adminUsers._popUp().editColumns()._checkBox().row(18).check();
        }
        
        if(!adminUsers._popUp().editColumns()._checkBox().row(19).check().isChecked())
        {
            adminUsers._popUp().editColumns()._checkBox().row(19).check();
        }
        
        if(!adminUsers._popUp().editColumns()._checkBox().row(20).check().isChecked())
        {
            adminUsers._popUp().editColumns()._checkBox().row(20).check();
        }
        
        if(!adminUsers._popUp().editColumns()._checkBox().row(21).check().isChecked())
        {
            adminUsers._popUp().editColumns()._checkBox().row(21).check();
        }
        
        if(!adminUsers._popUp().editColumns()._checkBox().row(22).check().isChecked())
        {
            adminUsers._popUp().editColumns()._checkBox().row(22).check();
        }
        
        if(!adminUsers._popUp().editColumns()._checkBox().row(23).check().isChecked())
        {
            adminUsers._popUp().editColumns()._checkBox().row(23).check();
        }
        
        if(!adminUsers._popUp().editColumns()._checkBox().row(24).check().isChecked())
        {
            adminUsers._popUp().editColumns()._checkBox().row(24).check();
        }
        
        if(!adminUsers._popUp().editColumns()._checkBox().row(25).check().isChecked())
        {
            adminUsers._popUp().editColumns()._checkBox().row(25).check();
        }
        
        if(!adminUsers._popUp().editColumns()._checkBox().row(26).check().isChecked())
        {
            adminUsers._popUp().editColumns()._checkBox().row(26).check();
        }
        
        if(!adminUsers._popUp().editColumns()._checkBox().row(27).check().isChecked())
        {
            adminUsers._popUp().editColumns()._checkBox().row(27).check();
        }
        
        if(!adminUsers._popUp().editColumns()._checkBox().row(28).check().isChecked())
        {
            adminUsers._popUp().editColumns()._checkBox().row(28).check();
        }
        
        if(!adminUsers._popUp().editColumns()._checkBox().row(29).check().isChecked())
        {
            adminUsers._popUp().editColumns()._checkBox().row(29).check();
        }
        
        if(!adminUsers._popUp().editColumns()._checkBox().row(30).check().isChecked())
        {
            adminUsers._popUp().editColumns()._checkBox().row(30).check();
        }
        
        if(!adminUsers._popUp().editColumns()._checkBox().row(31).check().isChecked())
        {
            adminUsers._popUp().editColumns()._checkBox().row(31).check();
        }
        
        if(!adminUsers._popUp().editColumns()._checkBox().row(32).check().isChecked())
        {
            adminUsers._popUp().editColumns()._checkBox().row(32).check();
        }
        
        if(!adminUsers._popUp().editColumns()._checkBox().row(33).check().isChecked())
        {
            adminUsers._popUp().editColumns()._checkBox().row(33).check();
        }
        
        if(!adminUsers._popUp().editColumns()._checkBox().row(34).check().isChecked())
        {
            adminUsers._popUp().editColumns()._checkBox().row(34).check();
        }

    }    
    
    @When("I uncheckmark all boxes in the Edit Columns box")
    public void whenIUncheckmarkAllBoxesInTheEditColumnsBox(){
        
        if(!adminUsers._popUp().editColumns()._checkBox().row(1).check().isChecked())
        {
            adminUsers._popUp().editColumns()._checkBox().row(1).uncheck();
        }
        
        if(!adminUsers._popUp().editColumns()._checkBox().row(2).check().isChecked())
        {
            adminUsers._popUp().editColumns()._checkBox().row(2).uncheck();
        }
        
        if(!adminUsers._popUp().editColumns()._checkBox().row(3).check().isChecked())
        {
            adminUsers._popUp().editColumns()._checkBox().row(3).uncheck();
        }
        
        if(!adminUsers._popUp().editColumns()._checkBox().row(4).check().isChecked())
        {
            adminUsers._popUp().editColumns()._checkBox().row(4).uncheck();
        }
        
        if(!adminUsers._popUp().editColumns()._checkBox().row(5).check().isChecked())
        {
            adminUsers._popUp().editColumns()._checkBox().row(5).uncheck();
        }
        
        if(!adminUsers._popUp().editColumns()._checkBox().row(6).check().isChecked())
        {
            adminUsers._popUp().editColumns()._checkBox().row(6).uncheck();
        }
        
        if(!adminUsers._popUp().editColumns()._checkBox().row(7).check().isChecked())
        {
            adminUsers._popUp().editColumns()._checkBox().row(7).uncheck();
        }
        
        if(!adminUsers._popUp().editColumns()._checkBox().row(8).check().isChecked())
        {
            adminUsers._popUp().editColumns()._checkBox().row(8).uncheck();
        }
        
        if(!adminUsers._popUp().editColumns()._checkBox().row(9).check().isChecked())
        {
            adminUsers._popUp().editColumns()._checkBox().row(9).uncheck();
        }
        
        if(!adminUsers._popUp().editColumns()._checkBox().row(10).check().isChecked())
        {
            adminUsers._popUp().editColumns()._checkBox().row(10).uncheck();
        }
        
        if(!adminUsers._popUp().editColumns()._checkBox().row(11).check().isChecked())
        {
            adminUsers._popUp().editColumns()._checkBox().row(11).uncheck();
        }
        
        if(!adminUsers._popUp().editColumns()._checkBox().row(12).check().isChecked())
        {
            adminUsers._popUp().editColumns()._checkBox().row(12).uncheck();
        }
        
        if(!adminUsers._popUp().editColumns()._checkBox().row(13).check().isChecked())
        {
            adminUsers._popUp().editColumns()._checkBox().row(13).uncheck();
        }
        
        if(!adminUsers._popUp().editColumns()._checkBox().row(14).check().isChecked())
        {
            adminUsers._popUp().editColumns()._checkBox().row(14).uncheck();
        }
        
        if(!adminUsers._popUp().editColumns()._checkBox().row(5).check().isChecked())
        {
            adminUsers._popUp().editColumns()._checkBox().row(5).uncheck();
        }
        
        if(!adminUsers._popUp().editColumns()._checkBox().row(15).check().isChecked())
        {
            adminUsers._popUp().editColumns()._checkBox().row(15).uncheck();
        }
        
        if(!adminUsers._popUp().editColumns()._checkBox().row(16).check().isChecked())
        {
            adminUsers._popUp().editColumns()._checkBox().row(16).uncheck();
        }
        
        if(!adminUsers._popUp().editColumns()._checkBox().row(17).check().isChecked())
        {
            adminUsers._popUp().editColumns()._checkBox().row(17).uncheck();
        }
        
        if(!adminUsers._popUp().editColumns()._checkBox().row(18).check().isChecked())
        {
            adminUsers._popUp().editColumns()._checkBox().row(18).uncheck();
        }
        
        if(!adminUsers._popUp().editColumns()._checkBox().row(19).check().isChecked())
        {
            adminUsers._popUp().editColumns()._checkBox().row(19).uncheck();
        }
        
        if(!adminUsers._popUp().editColumns()._checkBox().row(20).check().isChecked())
        {
            adminUsers._popUp().editColumns()._checkBox().row(20).uncheck();
        }
        
        if(!adminUsers._popUp().editColumns()._checkBox().row(21).check().isChecked())
        {
            adminUsers._popUp().editColumns()._checkBox().row(21).uncheck();
        }
        
        if(!adminUsers._popUp().editColumns()._checkBox().row(22).check().isChecked())
        {
            adminUsers._popUp().editColumns()._checkBox().row(22).uncheck();
        }
        
        if(!adminUsers._popUp().editColumns()._checkBox().row(23).check().isChecked())
        {
            adminUsers._popUp().editColumns()._checkBox().row(23).uncheck();
        }
        
        if(!adminUsers._popUp().editColumns()._checkBox().row(24).check().isChecked())
        {
            adminUsers._popUp().editColumns()._checkBox().row(24).uncheck();
        }
        
        if(!adminUsers._popUp().editColumns()._checkBox().row(25).check().isChecked())
        {
            adminUsers._popUp().editColumns()._checkBox().row(25).uncheck();
        }
        
        if(!adminUsers._popUp().editColumns()._checkBox().row(26).check().isChecked())
        {
            adminUsers._popUp().editColumns()._checkBox().row(26).uncheck();
        }
        
        if(!adminUsers._popUp().editColumns()._checkBox().row(27).check().isChecked())
        {
            adminUsers._popUp().editColumns()._checkBox().row(27).uncheck();
        }
        
        if(!adminUsers._popUp().editColumns()._checkBox().row(28).check().isChecked())
        {
            adminUsers._popUp().editColumns()._checkBox().row(28).uncheck();
        }
        
        if(!adminUsers._popUp().editColumns()._checkBox().row(29).check().isChecked())
        {
            adminUsers._popUp().editColumns()._checkBox().row(29).uncheck();
        }
        
        if(!adminUsers._popUp().editColumns()._checkBox().row(30).check().isChecked())
        {
            adminUsers._popUp().editColumns()._checkBox().row(30).uncheck();
        }
        
        if(!adminUsers._popUp().editColumns()._checkBox().row(31).check().isChecked())
        {
            adminUsers._popUp().editColumns()._checkBox().row(31).uncheck();
        }
        
        if(!adminUsers._popUp().editColumns()._checkBox().row(32).check().isChecked())
        {
            adminUsers._popUp().editColumns()._checkBox().row(32).uncheck();
        }
        
        if(!adminUsers._popUp().editColumns()._checkBox().row(33).check().isChecked())
        {
            adminUsers._popUp().editColumns()._checkBox().row(33).uncheck();
        }
        
        if(!adminUsers._popUp().editColumns()._checkBox().row(34).check().isChecked())
        {
            adminUsers._popUp().editColumns()._checkBox().row(34).uncheck();
        }

    }  
    
    @When("I click on each of the headings")
    public void whenIClickOnEachOfTheHeadings() {
        adminUsers._link().sortByName().click();
        adminUsers._link().sortByEmail().click();
    }
    
    @When("I click on a users Name link")
    public void whenIClickOnAUsersNameLink() {
        adminUsers._link().tableEntryUserFullName().row(1).click();       
    }
    
    @When("I uncheckmark a checkmarked box")
    public void whenIUncheckmarkACheckmarkedBox() {
        if(adminUsers._popUp().editColumns()._checkBox().row(1).check().isChecked())
        {
            adminUsers._popUp().editColumns()._checkBox().row(1).uncheck();
        }
    }
    
    @When("I uncheckmark a selection")
    public void whenIUncheckmarkASelection(){
        if(adminUsers._popUp().editColumns()._checkBox().row(1).check().isChecked())
        {
            adminUsers._popUp().editColumns()._checkBox().row(1).uncheck();
        }
    }

    @When("I checkmark a selection")
    public void whenICheckmarkASelection(){
        if(!adminUsers._popUp().editColumns()._checkBox().row(30).check().isChecked())
        {
            adminUsers._popUp().editColumns()._checkBox().row(30).check();
        }
    }

    @Then("the name column is no longer in the table")
    public void thenTheNameColumnIsNoLongerInTheTable(){
        adminUsers._link().sortByName().validatePresence(false);
    }

    @Then("the DOT column is in the table")
    public void thenTheDOTColumnIsInTheTable(){
        adminUsers._link().sortByDOT().validatePresence(true);
    }
    
    @Then("the box becomes unchecked")
    public void thenTheBoxBecomesUnchecked() {
        adminUsers._popUp().editColumns()._checkBox().row(1).assertChecked(false);
    }
    
    @Then("I checkmark a uncheckmarked box")
    public void thenICheckmarkAUncheckmarkedBox() {
        if(!adminUsers._popUp().editColumns()._checkBox().row(1).check().isChecked())
        {
            adminUsers._popUp().editColumns()._checkBox().row(1).check();
        }
    }

    @Then("the box becomes checked")
    public void thenTheBoxBecomesChecked() {
        adminUsers._popUp().editColumns()._checkBox().row(1).assertChecked(true);
    }

    @Then("the Edit User page information matches the user name from the table")
    public void thenTheEditUserPageInformationMatchesTheUserNameFromTheTable() {
        String firstName = adminDetails._text().values(UserColumns.FIRST_NAME)
                .getText();
        String middleName = adminDetails._text()
                .values(UserColumns.MIDDLE_NAME).getText();
        String lastName = adminDetails._text().values(UserColumns.LAST_NAME)
                .getText();
        String suffix = adminDetails._text().values(UserColumns.SUFFIX)
                .getText();
        adminDetails.assertEquals(fullname, (firstName + " " + middleName + " " + lastName
                + " " + suffix).replace("  ", " ").trim());
    }

    @Then("the Edit User page renders correctly")
    public void thenTheEditUserPageRendersCorrectly() {
         adminDetails.verifyOnPage();
    }
    
    
/*
    @When("I record the users email address")
    public void whenIRecordTheUsersEmailAddress(){
      // PENDING
    }
*/    //Mweiss: Cannot be implemented yet due to launching an external program
    
/*
    @When("I click on the email address link")
    public void whenIClickOnTheEmailAddressLink(){
        
        adminUsers._link().sortByEmail().click();
        adminUsers._link().sortByEmail().click();        
        adminUsers._link().tableEntryUserEmail1().row(1).click(); 
    }
*/    //Mweiss: Cannot be implemented yet due to launching an external program  

/*
    @Then("my email program opens and the email address is in the To field")
    @Pending
    public void thenMyEmailProgramOpensAndTheEmailAddressIsInTheToField(){
      // PENDING
    }
*/    //Mweiss: Cannot be implemented yet due to launching an external program        

    
    
    
    //TODO: steps that apply to any ADMIN tab tests ???
    
    
 
    
    //ROLES section (custom roles apply to most if not all admin sections)
//    private PageAdminCustomRoles roles = new PageAdminCustomRoles();
//    private PageAdminCustomRoles roleDetails = new PageAdminCustomRoles();
//    private PageAdminCustomRoleAddEdit roleAddEdit = new PageAdminCustomRoleAddEdit();
//    
//    @When("I input the role name as $roleName")
//    @Pending
//    public void whenIInputTheRoleNameAs(String roleName){
//        if(roleAddEdit.isOnPage()){
//            roleAddEdit._textField().name().type(roleName);
//        } else {
//            addError("not on the correct page to call whenIInputTheRoleNameAs("+roleName+")", ErrorLevel.ERROR);
//        }
//    }
    

}
