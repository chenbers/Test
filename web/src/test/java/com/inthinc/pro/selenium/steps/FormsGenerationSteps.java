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
    	//TODO: Add code to clear out the forms database before running all tests
    }
    
    @Then("I generate 100 forms for the manage page test")
    public void thenIGenerateOneHundredFormsForTheManagePageTest() {
    	int i = 1;
    	
    	while (i < 6) {
    	manage._button().newForm().click();
    	add._textField().name().type("FormPreTrip"+i);
    	add._dropDown().trigger().selectTheOptionContaining("Pre Trip", 1);
    	add._checkBox().groups().click();
    	add._link().text().click();
    	add._button().saveTop().click();
    	i++;
    	}
    	
    	while (i < 12) {
    	manage._button().newForm().click();
    	add._textField().name().type("FormPostTrip"+i);
    	add._dropDown().trigger().selectTheOptionContaining("Post Trip", 1);
    	add._checkBox().groups().click();
    	add._link().text().click();
    	add._button().saveTop().click();
    	i++;
    	}
    	
    	while (i < 18) {
    	manage._button().newForm().click();
    	add._textField().name().type("FormInactive"+i);
    	add._dropDown().status().selectTheOptionContaining("Inactive", 1);
    	add._textField().description().type("Required");
    	add._checkBox().groups().click();
    	add._link().text().click();
    	add._button().saveTop().click();
    	i++;
    	}
    	
    	while (i < 101) {
    	manage._button().newForm().click();
    	add._textField().name().type("FormGeneric"+i);
    	add._checkBox().groups().click();
    	add._link().text().click();
    	add._button().saveTop().click();
    	i++;
    	}
    	
    	System.out.println("SUCCESS!");
    }
    
    @Then("I generate 100 forms for the publish page test")
    public void thenIGenerateOneHundredFormsForThePublishPageTest() {
    	int i = 10;
    	int h = 1;
    	int j = 1;
    	int k = 1;
    	int l = 1;
    			
    	
    	while (i < 96) {
        	manage._button().newForm().click();
        	add._textField().name().type("FormGeneric"+i);
        	add._checkBox().groups().click();
        	add._link().text().click();
        	add._button().saveTop().click();
        	manage._dropDown().recordsPerPage().select("100");
    		manage._button().gear().row(h).click();
        	manage._link().publish().row(h).click();
        	i++; h++;
    	}
    	
    	while (j < 6) {
        	manage._button().newForm().click();
        	add._textField().name().type("FormPostTrip"+j);
        	add._dropDown().trigger().selectTheOptionContaining("Post Trip", 1);
        	add._checkBox().groups().click();
        	add._link().text().click();
        	add._button().saveTop().click();
        	manage._dropDown().recordsPerPage().select("100");
    		manage._button().gear().row(h).click();
        	manage._link().publish().row(h).click();
        	j++; h++;
    	}
    	
    	while (k < 6) {
    		manage._button().newForm().click();
        	add._textField().name().type("FormPreTrip"+k);
        	add._dropDown().trigger().selectTheOptionContaining("Pre Trip", 1);
        	add._checkBox().groups().click();
        	add._link().text().click();
        	add._button().saveTop().click();
        	manage._dropDown().recordsPerPage().select("100");
    		manage._button().gear().row(h).click();
        	manage._link().publish().row(h).click();
        	k++; h++;
    	}
    	
    	while (l < 6) {
    		manage._button().newForm().click();
	    	add._textField().name().type("FormPublish"+l);
	    	add._textField().description().type("Required");
	    	add._checkBox().groups().click();
	    	add._link().text().click();
	    	add._button().saveTop().click();
	    	manage._dropDown().recordsPerPage().select("100");
			manage._button().gear().row(h).click();
	    	manage._link().publish().row(h).click();
	    	l++; h++;
    	}
    	
    	System.out.println("SUCCESS!");
    }
    
     
     @Given("I generate submissions")
    public void givenIGenerateSubmissions() {
    	 //TODO: Call SubmissionsGen class
     }

}