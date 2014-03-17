package com.inthinc.pro.selenium.steps;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;

import com.inthinc.pro.automation.utils.Xpath;
import com.inthinc.pro.selenium.pageObjects.PageFormsAdd;
import com.inthinc.pro.selenium.pageObjects.PageFormsManage;
import com.inthinc.pro.selenium.pageObjects.PageFormsSubmissions;

public class FormsGenerationSteps extends LoginSteps {
    //TODO: Make it so you don't have to use the web interface to create the needed forms US7923
    PageFormsManage manage = new PageFormsManage();
    PageFormsAdd add = new PageFormsAdd();
    PageFormsSubmissions submissions = new PageFormsSubmissions();
    String publishedSuccessfully = " published successfully.";
    String createdSuccessfully = " created successfully.";
    String publishFailed = " publish failed.";
    
    @Given("I clean the forms database")
    public void cleanup() {
        // TODO: Call the DeleteAllAccountsFormsClass
    }
    
    @Then("I generate 13 forms for the manage page test")
    public void thenIGenerateOneHundredFormsForTheManagePageTest() {
        int i = 1;
        
        while (i < 13) {
            createForm(i, "No Trigger", "ManageFormInactive", "Inactive", "Required");
            i++;
        }
        System.out.println("FORMS MANAGE GENERATION SUCCESS!");
    }
    
    @Then("I generate 100 forms for the publish page test")
    public void thenIGenerateOneHundredFormsForThePublishPageTest() {
        int i = 1;
        int j = 1;
        int k = 1;
        int l = 1;
        
        while (l < 5) {
            createForm(l, "No Trigger", "FormPublish", "Active", "Required");
            manage._textField().search().type("FormPublish" + l);
            manage._button().gear().row(1).click();
            publishLinkCheck(l, "FormPublish");
            l++;
        }
        
        while (j < 5) {
            createForm(j, "Post-Trip", "FormPostTrip", "Active", "");
            manage._textField().search().type("FormPostTrip" + j);
            manage._button().gear().row(1).click();
            publishLinkCheck(j, "FormPostTrip");
            j++;
        }
        
        while (k < 5) {
            createForm(k, "Pre-Trip", "FormPreTrip", "Active", "");
            manage._textField().search().type("FormPreTrip" + k);
            manage._button().gear().row(1).click();
            publishLinkCheck(k, "FormPreTrip");
            k++;
        }
        
        createForm(i, "No Trigger", "PublishFormGeneric", "Active", "");
        manage._textField().search().type("PublishFormGeneric" + i);
        manage._button().gear().row(1).click();
        publishLinkCheck(i, "PublishFormGeneric");
        i++;
        
        while (i < 100) {
            copyForm(i, "");
            publishLinkCheck(i, "PublishFormGeneric");
            i++;
        }
        
        System.out.println("FORMS PUBLISHED GENERATION SUCCESS!");
    }
    
    public void createForm(int formNumber, String formTrigger, String formName, String formStatus, String formDescription) {
        manage._button().newForm().click();
        add._textField().name().type(formName + formNumber);
        add._textField().description().type(formDescription);
        add._dropDown().trigger().selectTheOptionContaining(formTrigger, 1);
        add._dropDown().status().selectTheOptionContaining(formStatus, 1);
        add._checkBox().groups().check();
        add._link().text().click();
        add._button().saveTop().click();
        System.out.println(formName + formNumber + createdSuccessfully);
    }
    
    public void copyForm(int formNumber, String formName) {
        manage._button().gear().row(1).click();
        manage._link().copy().row(1).click();
        add._textField().name().type(formName + formNumber);
        add._button().saveTop().click();
        manage._textField().search().type(formName + formNumber);
        manage._button().gear().row(1).click();
    }
    
    public void publishLinkCheck(int formNumber, String formName) {
        if ((manage._link().publish().row(1).getAttribute("disabled") == "disabled")) {
            manage._link().manage().click();
            System.out.println(formName + formNumber + publishFailed);
        }
        else {
            manage._link().publish().row(1).click();
            manage._link().manage().click();
            System.out.println(formName + formNumber + publishedSuccessfully);
        }
    }
    
    @Given("I generate submissions")
    public void givenIGenerateSubmissions() {
        // TODO: Call SubmissionsGen class linked to US7827
    }
    
}