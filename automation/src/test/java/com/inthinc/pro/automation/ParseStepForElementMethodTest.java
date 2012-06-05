package com.inthinc.pro.automation;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.jbehave.core.steps.StepCandidateBehaviour.candidateMatchingStep;
import static org.junit.Assert.assertEquals;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jbehave.core.configuration.MostUsefulConfiguration;
import org.jbehave.core.steps.Step;
import org.jbehave.core.steps.StepCandidate;
import org.junit.Ignore;
import org.junit.Test;

import com.inthinc.pro.automation.jbehave.AutoSteps;
import com.inthinc.pro.automation.logging.Log;
import com.inthinc.pro.automation.selenium.AutomationProperties;
import com.inthinc.pro.automation.utils.MasterTest;

@Ignore
public class ParseStepForElementMethodTest {
    
    private MasterTest test;
    private AutomationPropertiesBean apb;
    
    public ParseStepForElementMethodTest(){
        test = new MasterTest(){};
        apb = AutomationProperties.getPropertyBean();
    }
    
    /**
     * Test that we can get the default user and password from a step string.
     */
    @Test
    public void testDefaultCreds(){
        test.useParamsToSetDefaultUser(MasterTest.getMainuser());
        assertEquals("Unable to get the default user from the string", 
                apb.getMainAutomation().get(1), getUsername());
        assertEquals("Unable to get the default user from the string", 
                apb.getPassword(), getPassword());
        
        test.useParamsToSetDefaultUser(MasterTest.getEditableaccountuser());
        assertEquals("Unable to get the default user from the string", 
                apb.getEditableAccount().get(1), getUsername());
        assertEquals("Unable to get the default user from the string", 
                apb.getPassword(), getPassword());
    }
    
    private String getPassword(){
        return MasterTest.getComparator("When I type my password into the Password field");
    }
    
    private String getUsername(){
        return MasterTest.getComparator("When I type my user name into the Username field");
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
        String mainAutomationStep = "Given I am logged in";
        MasterTestSteps steps = new MasterTestSteps();
        List<StepCandidate> candidates = steps.listCandidates();
        StepCandidate candidate = candidateMatchingStep(candidates, mainAutomationStep);
        Map<String, String> noNamedParameters = new HashMap<String, String>();
        List<Step> composedSteps = new ArrayList<Step>();
        candidate.addComposedSteps(composedSteps, mainAutomationStep, noNamedParameters, candidates);
        for (Step step : composedSteps) {
            Log.info(step);
            Log.info(step.perform(null));
            
        }
        
        assertThat("The Usernames didn't match", test.getMyUser().getUsername(), is(apb.getMainAutomation().get(1)));
        assertThat("The Usernames didn't match", getUsername(), is(apb.getMainAutomation().get(1)));
        assertEquals("The password didn't match, if it is changed it should be fixed", true, 
                test.getMyUser().doesPasswordMatch(apb.getPassword()));
        assertThat("The password didn't match, if it is changed it should be fixed", getPassword(), is(apb.getPassword()));
    }
    
    static class MasterTestSteps extends AutoSteps{
        public MasterTestSteps(){
            super(new MostUsefulConfiguration(), new MasterTest(){});
        }
    }
    
    @Test
    public void testParameterParser(){
        int first=1, second=43;
        String firstString = "Testing if this works", secondString = "Why should this work";
        String falseStep = "Then I am not getting " + first + " field as my \"" + firstString + "\"";
        
        
        for (Method method : parseMethods){
            try {
                Object[] params = test.getParameters(falseStep, method);
                checkParams(params, false, first, firstString);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        
        String trueStep = "Then I will get " + second + " field as my \"" + secondString + "\"";
        
        for (Method method : parseMethods){
            try {
                Object[] params = test.getParameters(trueStep, method);
                checkParams(params, true, second, secondString);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
    }
    
    private void checkParams(Object[] params, Boolean bool, Integer integer, String string){
        for (Object param : params){
            if (param instanceof Integer){
                assertEquals("Parameter Parser didn't parse the Integer correctly", integer, param);
            }  else if (param instanceof Boolean){
                assertEquals("Parameter Parser didn't parse the Boolean correctly", bool, param);
            } else {
                assertEquals("Parameter Parser didn't parse the String correctly", string, param);
            }
        }
    }
        
    public void getBoolean(boolean bool){}
    public void getStringByObj(Object obj){}
    public void getString(String str){}
    public void getInt(int integer){}
    public void getInteger(Integer integer){}
    public void getOneOfEach(Boolean bool, Integer integer, Object string){}
    public void getOneOfEach(boolean bool, int integer, String string){}
    
    private static final List<Method> parseMethods = new ArrayList<Method>();
    static {
        Class<ParseStepForElementMethodTest> clazz = ParseStepForElementMethodTest.class;
        try {
            parseMethods.add(clazz.getMethod("getBoolean", boolean.class));
            parseMethods.add(clazz.getMethod("getStringByObj", Object.class));
            parseMethods.add(clazz.getMethod("getString", String.class));
            parseMethods.add(clazz.getMethod("getInteger", Integer.class));
            parseMethods.add(clazz.getMethod("getInt", int.class));
            parseMethods.add(clazz.getMethod("getOneOfEach", Boolean.class, Integer.class, Object.class));
            parseMethods.add(clazz.getMethod("getOneOfEach", boolean.class, int.class, String.class));
        } catch (SecurityException e) {
            Log.error(e);
            throw new NullPointerException("Could not add all of the methods: " + e.getLocalizedMessage());
        } catch (NoSuchMethodException e) {
            Log.error(e);
            throw new NullPointerException("Could not add all of the methods: " + e.getLocalizedMessage());
        }
    }
    
    
}
