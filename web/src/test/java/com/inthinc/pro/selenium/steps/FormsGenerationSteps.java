package com.inthinc.pro.selenium.steps;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;

import com.inthinc.pro.selenium.pageObjects.PageFormsAdd;
import com.inthinc.pro.selenium.pageObjects.PageFormsManage;
import com.inthinc.pro.selenium.pageObjects.PageFormsSubmissions;

public class FormsGenerationSteps extends LoginSteps {
    
    PageFormsManage manage = new PageFormsManage();
    PageFormsAdd add = new PageFormsAdd();
    PageFormsSubmissions submissions = new PageFormsSubmissions();
    
    @Given("I clean the forms database")
    public void cleanup() {
        // TODO: Call the DeleteAllAccountsFormsClass
    }
    
    @Then("I generate 100 forms for the manage page test")
    public void thenIGenerateOneHundredFormsForTheManagePageTest() {
        int i = 1;
        
        while (i < 5) {
            manage._button().newForm().click();
            add._textField().name().type("FormPreTrip" + i);
            add._dropDown().trigger().selectTheOptionContaining("Pre-Trip", 1);
            add._checkBox().groups().check();
            add._link().text().click();
            add._button().saveTop().click();
            System.out.println("FormPreTrip" + i + " created successfully.");
            i++;
        }
        
        while (i < 9) {
            manage._button().newForm().click();
            add._textField().name().type("FormPostTrip" + i);
            add._dropDown().trigger().selectTheOptionContaining("Post-Trip", 1);
            add._checkBox().groups().check();
            add._link().text().click();
            add._button().saveTop().click();
            System.out.println("FormPostTrip" + i + " created successfully.");
            i++;
        }
        
        while (i < 13) {
            manage._button().newForm().click();
            add._textField().name().type("FormInactive" + i);
            add._dropDown().status().selectTheOptionContaining("Inactive", 1);
            add._textField().description().type("Required");
            add._checkBox().groups().check();
            add._link().text().click();
            add._button().saveTop().click();
            System.out.println("FormInactive" + i + " created successfully.");
            i++;
        }
//        
//        while (i < 19) {
//            manage._button().gear().row(1).click();
//            manage._link().copy().row(1).click();
//            add._textField().name().type("FormMGeneric" + i);
//            add._button().saveTop().click();
//            System.out.println("FormMGeneric" + i + " created successfully.");
//            i++;
//        }
        
        System.out.println("FORMS MANAGE GENERATION SUCCESS!");
    }
    
    @Then("I generate 100 forms for the publish page test")
    public void thenIGenerateOneHundredFormsForThePublishPageTest() {
        int i = 1;
        int j = 1;
        int k = 1;
        int l = 1;
        
        while (l < 5) {
            manage._button().newForm().click();
            add._textField().name().type("FormPublish" + l);
            add._textField().description().type("Required");
            add._checkBox().groups().check();
            add._link().text().click();
            add._button().saveTop().click();
            manage._textField().search().type("FormPublish" + l);
            manage._button().gear().row(1).click();
            manage._link().publish().row(1).click();
            manage._link().manage().click();
            System.out.println("FormPublish" + l + " created successfully.");
            l++;
        }
        
        while (j < 5) {
            manage._button().newForm().click();
            add._textField().name().type("FormPostTrip" + j);
            add._dropDown().trigger().selectTheOptionContaining("Post-Trip", 1);
            add._checkBox().groups().check();
            add._link().text().click();
            add._button().saveTop().click();
            manage._textField().search().type("FormPostTrip" + j);
            manage._button().gear().row(1).click();
            manage._link().publish().row(1).click();
            manage._link().manage().click();
            System.out.println("FormPostTrip" + j + " created successfully.");
            j++;
        }
        
        while (k < 5) {
            manage._button().newForm().click();
            add._textField().name().type("FormPreTrip" + k);
            add._dropDown().trigger().selectTheOptionContaining("Pre-Trip", 1);
            add._checkBox().groups().check();
            add._link().text().click();
            add._button().saveTop().click();
            manage._textField().search().type("FormPreTrip" + k);
            manage._button().gear().row(1).click();
            manage._link().publish().row(1).click();
            manage._link().manage().click();
            System.out.println("FormPreTrip" + k + " created successfully.");
            k++;
        }
        
        manage._button().newForm().click();
        add._textField().name().type("FormGeneric" + i);
        add._checkBox().groups().check();
        add._link().text().click();
        add._button().saveTop().click();
        manage._textField().search().type("FormGeneric" + i);
        manage._button().gear().row(1).click();
        manage._link().publish().row(1).click();
        manage._link().manage().click();
        System.out.println("FormGeneric" + i + " created successfully.");
        i++;
        
        while (i < 90) {
            manage._button().gear().row(1).click();
            manage._link().copy().row(1).click();
            add._textField().name().type("FormGeneric" + i);
            add._button().saveTop().click();
            manage._textField().search().type("FormGeneric" + i);
            manage._button().gear().row(1).click();
            manage._link().publish().row(1).click();
            manage._link().manage().click();
            System.out.println("FormGeneric" + i + " created successfully.");
            i++;
        }
        
        System.out.println("FORMS PUBLISHED GENERATION SUCCESS!");
    }
    
    @Given("I generate submissions")
    public void givenIGenerateSubmissions() {
        // TODO: Call SubmissionsGen class
    }
    
}