package com.inthinc.pro.automation;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.jbehave.core.steps.StepCandidateBehaviour.candidateMatchingStep;
import static org.junit.Assert.assertEquals;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringEscapeUtils;
import org.jbehave.core.configuration.MostUsefulConfiguration;
import org.jbehave.core.steps.Step;
import org.jbehave.core.steps.StepCandidate;
import org.junit.Ignore;
import org.junit.Test;

import com.inthinc.pro.automation.elements.CalendarObject;
import com.inthinc.pro.automation.enums.SeleniumEnumWrapper;
import com.inthinc.pro.automation.enums.WebDateFormat;
import com.inthinc.pro.automation.jbehave.AutoCustomSteps;
import com.inthinc.pro.automation.jbehave.AutoMethodParser;
import com.inthinc.pro.automation.jbehave.AutoStepVariables;
import com.inthinc.pro.automation.jbehave.AutoSteps;
import com.inthinc.pro.automation.logging.Log;
import com.inthinc.pro.automation.objects.AutomationCalendar;
import com.inthinc.pro.automation.selenium.AutomationProperties;
import com.inthinc.pro.automation.utils.RandomValues;

@Ignore
public class ParseStepForElementMethodTest {
    
    private static final int numOfTimesToLoop = 1000;
    
    private AutoCustomSteps test;
    private AutomationPropertiesBean apb;
    
    public ParseStepForElementMethodTest(){
        test = new AutoCustomSteps();
        apb = AutomationProperties.getPropertyBean();
    }
    
    /**
     * Test that we can get the default user and password from a step string.
     */
    @Test
    public void testDefaultCreds(){
        test.useParamsToSetDefaultUser(AutoCustomSteps.getMainuser());
        assertEquals("Unable to get the default user from the string", 
                apb.getMainAutomation().get(1), getUsername());
        assertEquals("Unable to get the default user from the string", 
                apb.getPassword(), getPassword());
        
        test.useParamsToSetDefaultUser(AutoCustomSteps.getEditableaccountuser());
        assertEquals("Unable to get the default user from the string", 
                apb.getEditableAccount().get(1), getUsername());
        assertEquals("Unable to get the default user from the string", 
                apb.getPassword(), getPassword());
    }
    
    private String getPassword(){
        return AutoStepVariables.getComparator("When I type my password into the Password field");
    }
    
    private String getUsername(){
        return AutoStepVariables.getComparator("When I type my user name into the Username field");
    }
    
    /**
     * Test that we can get a string from a step with a quoted value
     */
    @Test
    public void testQuotedValue(){
        String value = "This is my test value 123 *(&";
        String stepAsString = "I type \"" + value +"\" into the Username field";
        assertEquals("Was unable to get the value from the step", value, AutoStepVariables.getComparator(stepAsString));
    }
    
    private final static String[] variableStrings = {"This is my test value 321 !@#$", "Idle &#37;", 
        "Incorrect user name or password.\n\nPlease try again.", "!@\t#$%\n^&*() &#37;", "New and Confirm New Password do not match"};
    private final static String[] variableNames = {"my stinking variable name"};
    
    /**
     * Test that we can save any variable and get it again
     */
    @Test
    public void testSavingVariable(){
        for (String value : variableStrings){
            String unescaped = StringEscapeUtils.unescapeJava(value);
            unescaped = StringEscapeUtils.unescapeHtml(value);
            for (String variableName : variableNames){
                String saveStep = "Then I save the Login button as " + variableName;
                AutoStepVariables.setComparator(saveStep, value);
                String useStep = "I type " + variableName + " into the Login field";
                assertEquals("Was unable to get the value from the step", unescaped, AutoStepVariables.getComparator(useStep));
            }
        }
    }
    
    /**
     * Test that we can save any variable and get it again
     */
    @Test
    public void testGettingSavedVariable(){
        for (String value : variableStrings){

            String unescaped = StringEscapeUtils.unescapeJava(value);
            unescaped = StringEscapeUtils.unescapeHtml(value);
            for (String variableName : variableNames){
            String saveStep = "Then I save the Login button as " + variableName;
            AutoStepVariables.setComparator(saveStep, value);
            String useStep = "I validate the Login button is " + variableName;
            assertEquals("Was unable to get the value from the step", unescaped, AutoStepVariables.getComparator(useStep));
            }
        }
    }
    
    /**
     * Test that we can use a quoted string as a comparator
     */
    @Test
    public void testComparingToQuoted(){

        for (String value : variableStrings){
            String unescaped = StringEscapeUtils.unescapeJava(value);
            unescaped = StringEscapeUtils.unescapeHtml(value);
            String useStep = "I validate the Login button is \"" + value + "\"";
            assertEquals("Was unable to get the value from the step", unescaped, AutoStepVariables.getComparator(useStep));
        }
    }
    
    /**
     * Create a step from the MasterTest methods and test them.
     */
    @Test
    public void testCompositeDefaultLoginStep(){
        String mainAutomationStep = "Given I am logged in";
        CustomAutomationSteps steps = new CustomAutomationSteps();
        List<StepCandidate> candidates = steps.listCandidates();
        StepCandidate candidate = candidateMatchingStep(candidates, mainAutomationStep);
        Map<String, String> noNamedParameters = new HashMap<String, String>();
        List<Step> composedSteps = new ArrayList<Step>();
        candidate.addComposedSteps(composedSteps, mainAutomationStep, noNamedParameters, candidates);
        for (Step step : composedSteps) {
            Log.info(step.perform(null));
            
        }
        
        assertThat("The Usernames didn't match", test.getMyUser().getUsername(), is(apb.getMainAutomation().get(1)));
        assertThat("The Usernames didn't match", getUsername(), is(apb.getMainAutomation().get(1)));
        assertEquals("The password didn't match, if it is changed it should be fixed", true, 
                test.getMyUser().doesPasswordMatch(apb.getPassword()));
        assertThat("The password didn't match, if it is changed it should be fixed", getPassword(), is(apb.getPassword()));
    }
    
    /**
     * Create a step from the MasterTest methods and test them.
     */
    @Test
    public void testCompositeEditAccountLoginStep(){
        String editAccountStep = "Given I am logged in " + AutoCustomSteps.getEditableaccountuser();
        CustomAutomationSteps steps = new CustomAutomationSteps();
        List<StepCandidate> candidates = steps.listCandidates();
        StepCandidate candidate = candidateMatchingStep(candidates, editAccountStep);
        Map<String, String> namedParameters = new HashMap<String, String>();
        namedParameters.put("params", AutoCustomSteps.getEditableaccountuser());
        List<Step> composedSteps = new ArrayList<Step>();
        candidate.addComposedSteps(composedSteps, editAccountStep, namedParameters, candidates);
        for (Step step : composedSteps) {
            Log.info(step.perform(null));
            
        }
        
        assertThat("The Usernames didn't match", test.getMyUser().getUsername(), is(apb.getEditableAccount().get(1)));
        assertThat("The Usernames didn't match", getUsername(), is(apb.getEditableAccount().get(1)));
        assertEquals("The password didn't match, if it is changed it should be fixed", true, 
                test.getMyUser().doesPasswordMatch(apb.getPassword()));
        assertThat("The password didn't match, if it is changed it should be fixed", getPassword(), is(apb.getPassword()));
    }
    
    static class CustomAutomationSteps extends AutoSteps{
        public CustomAutomationSteps(){
            super(new MostUsefulConfiguration(), new AutoCustomSteps());
        }
    }
    
    @Test
    public void testParameterParser(){
        RandomValues rand = new RandomValues();
        int randInt;
        String string;
        
        for (int i=0; i<numOfTimesToLoop; i++){
            randInt = rand.getInt(Integer.MAX_VALUE);
            string = rand.getCharString(15);
            String falseStep = "Then I am not getting " + randInt + " field as my \"" + string + "\"";
        
            for (Method method : parseMethods){
                try {
                    Object[] params = AutoMethodParser.getParameters(falseStep, method);
                    checkParams(params, false, randInt, string);
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
            }
        }
        
        for (int i=0; i<numOfTimesToLoop;i++){
            randInt = rand.getInt(Integer.MAX_VALUE);
            string = rand.getCharString(15);
            String trueStep = "Then I will get " + randInt + " field as my \"" + string + "\"";
            
            for (Method method : parseMethods){
                try {
                    Object[] params = AutoMethodParser.getParameters(trueStep, method);
                    checkParams(params, true, randInt, string);
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
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
    
    
    @Test
    public void testParsingCalendar() throws SecurityException, NoSuchMethodException{
        RandomValues rand = new RandomValues();
        SeleniumEnumWrapper id = new SeleniumEnumWrapper("TestingParsing","someFakeID");
        CalendarObject co = new CalendarObject(id);
        Method method = co.getClass().getMethod("select", AutomationCalendar.class);
        for (int i=0;i<numOfTimesToLoop;i++){
            int epochStamp = Integer.parseInt(rand.getIntString(9)) + 1000000000;
            for (WebDateFormat format : EnumSet.allOf(WebDateFormat.class)) {
                AutomationCalendar time = new AutomationCalendar(epochStamp, format);
                String step = "And I select \"" + time + "\" in the Start dropdown";
                Object[] params = AutoMethodParser.getParameters(step, method);
                assertEquals("We should only have gotten 1 parameter", 1, params.length);
                assertEquals("The date was not parsed correctly", time, params[0]);
            }
        }
    }
    
    @Test
    public void testParsingCalendarFromVariable() throws SecurityException, NoSuchMethodException{
        RandomValues rand = new RandomValues();
        String variableName = "my big long variable that you will not guess";
        String saveStep = "I want to save this param for Start field as " + variableName;
        String step = "And I select " + variableName + " in the Start dropdown";
        
        SeleniumEnumWrapper id = new SeleniumEnumWrapper("TestingParsing","someFakeID");
        CalendarObject co = new CalendarObject(id);
        Method method = co.getClass().getMethod("select", AutomationCalendar.class);
        for (int i=0;i<numOfTimesToLoop;i++){
            int epochStamp = Integer.parseInt(rand.getIntString(9)) + 1000000000;
            for (WebDateFormat format : EnumSet.allOf(WebDateFormat.class)) {
                AutomationCalendar time = new AutomationCalendar(epochStamp, format);
                AutoStepVariables.setComparator(saveStep, time);
                Object[] params = AutoMethodParser.getParameters(step, method);
                assertEquals("We should only have gotten 1 parameter", 1, params.length);
                assertEquals("The date was not parsed correctly", time, params[0]);
            }
        }
    }
    
}
