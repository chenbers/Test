package com.inthinc.pro.automation;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.jbehave.core.steps.StepCandidateBehaviour.candidateMatchingStep;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jbehave.core.configuration.MostUsefulConfiguration;
import org.jbehave.core.steps.Step;
import org.jbehave.core.steps.StepCandidate;
import org.junit.Test;

import com.inthinc.pro.automation.jbehave.AutoSteps;
import com.inthinc.pro.automation.logging.Log;
import com.inthinc.pro.automation.selenium.AutomationProperties;
import com.inthinc.pro.automation.utils.MasterTest;

public class ParseStepForElementMethodTest {
    
    private MasterTest test;
    
    public ParseStepForElementMethodTest(){
        test = new MasterTest(){};
    }
    
    /**
     * Test that we can get the default user and password from a step string.
     */
    @Test
    public void testDefaultCreds(){
        AutomationPropertiesBean apb = AutomationProperties.getPropertyBean();
        test.useParamsToSetDefaultUser(MasterTest.getMainuser());
        assertEquals("Unable to get the default user from the string", 
                apb.getMainAutomation().get(1), MasterTest.getComparator("When I type my user name into the Username field"));
        assertEquals("Unable to get the default user from the string", 
                apb.getPassword(), MasterTest.getComparator("When I type my password into the Password field"));
        
        test.useParamsToSetDefaultUser(MasterTest.getEditableaccountuser());
        assertEquals("Unable to get the default user from the string", 
                apb.getEditableAccount().get(1), MasterTest.getComparator("When I type my user name into the Username field"));
        assertEquals("Unable to get the default user from the string", 
                apb.getPassword(), MasterTest.getComparator("When I type my password into the Password field"));
    }
    
    /**
     * Test that we can get a string from a step with a quoted value
     */
    @Test
    public void testQuotedValue(){
        String value = "This is my test value 123 *(&";
        String stepAsString = "I type \"" + value +"\" into the Username field";
        assertEquals("Was unable to get the value from the step", value, MasterTest.getComparator(stepAsString));
    }
    
    /**
     * Test that we can save any variable and get it again
     */
    @Test
    public void testSavingVariable(){
        String value = "This is my test value 321 !@#$";
        String variableName = "my stinking variable name";
        String saveStep = "Then I save the Login button as " + variableName;
        MasterTest.setComparator(saveStep, value);
        String useStep = "I type " + variableName + " into the Login field";
        assertEquals("Was unable to get the value from the step", value, MasterTest.getComparator(useStep));
    }
    
    /**
     * Test that we can save any variable and get it again
     */
    @Test
    public void testGettingSavedVariable(){
        String value = "This is my test value 321 !@#$";
        String variableName = "my stinking variable name";
        String saveStep = "Then I save the Login button as " + variableName;
        MasterTest.setComparator(saveStep, value);
        String useStep = "I validate the Login button is " + variableName;
        assertEquals("Was unable to get the value from the step", value, MasterTest.getComparator(useStep));
    }
    
    /**
     * Test that we can use a quoted string as a comparator
     */
    @Test
    public void testComparingToQuoted(){
        String value = "This is my test value 321 !@#$";
        String useStep = "I validate the Login button is \"" + value + "\"";
        assertEquals("Was unable to get the value from the step", value, MasterTest.getComparator(useStep));
    }
    
    /**
     * Create a step from the MasterTest methods and test them.
     */
    @Test
    public void testCompositeLoginStep(){
        AutomationPropertiesBean apb = AutomationProperties.getPropertyBean();
        String mainAutomationStep = "Given I am logged in";
        MasterTestSteps steps = new MasterTestSteps();
        List<StepCandidate> candidates = steps.listCandidates();
        StepCandidate candidate = candidateMatchingStep(candidates, mainAutomationStep);
        Map<String, String> noNamedParameters = new HashMap<String, String>();
        List<Step> composedSteps = new ArrayList<Step>();
        candidate.addComposedSteps(composedSteps, mainAutomationStep, noNamedParameters, candidates);
        for (Step step : composedSteps) {
            Log.info(step.perform(null));
            
        }
        assertThat(test.getMyUser().getUsername(), is(apb.getMainAutomation().get(1)));
        assertEquals("The password didn't match, if it is changed it should be fixed", true, 
                test.getMyUser().doesPasswordMatch(apb.getPassword()));
    }
    
    static class MasterTestSteps extends AutoSteps{
        public MasterTestSteps(){
            super(new MostUsefulConfiguration(), new MasterTest(){});
        }
    }
    
    
}
