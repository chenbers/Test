package com.inthinc.pro.automation.utils;

import static java.util.Arrays.asList;

import java.awt.event.KeyEvent;
import java.io.StringWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringEscapeUtils;
import org.jbehave.core.annotations.Aliases;
import org.jbehave.core.annotations.When;
import org.jbehave.core.steps.StepCreator.PendingStep;
import org.openqa.selenium.WebDriver;
import org.springframework.util.ClassUtils;

import com.inthinc.pro.automation.enums.ErrorLevel;
import com.inthinc.pro.automation.enums.SeleniumEnumWrapper;
import com.inthinc.pro.automation.interfaces.AutoElementTags.Assert;
import com.inthinc.pro.automation.interfaces.AutoElementTags.Validate;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.automation.interfaces.TextEnum;
import com.inthinc.pro.automation.jbehave.RegexTerms;
import com.inthinc.pro.automation.logging.Log;
import com.inthinc.pro.automation.selenium.CoreMethodInterface;
import com.inthinc.pro.automation.selenium.CoreMethodLib;
import com.inthinc.pro.automation.selenium.ErrorCatcher;
import com.inthinc.pro.automation.selenium.Page;

public abstract class MasterTest {
    
    private final Long threadID = Thread.currentThread().getId();

    
    public static final Map<Long, Map<String, String>> variables = new HashMap<Long, Map<String, String>>();
    protected Map<String, String> localVariables;
    
    public MasterTest(){
        if (variables.containsKey(threadID)) {
            localVariables = variables.get(threadID);
        } else {
            localVariables = new HashMap<String, String>();
            variables.put(threadID, localVariables);
        }
    }
    
    public static String getComparator(String stepAsString){
        String variable = getLastTerms(stepAsString);
        if (variable == null){
            return null;
        }
        if (variable.contains("\"")){
            return variable.replace("\"", "");
        } else {
            return variables.get(Thread.currentThread().getId()).get(variable);
        }
    }
    
    public static String getLastTerms(String stepAsString){
        Pattern pat = Pattern.compile(RegexTerms.getVariable);
        Matcher mat = pat.matcher(stepAsString);
        if (mat.find()){
            return stepAsString.substring(mat.start(), mat.end());
        }
        return null;
    }
    
    public static void setComparator(String stepAsString, Object value){
        Pattern pat = Pattern.compile(RegexTerms.setVariable);
        Matcher mat = pat.matcher(stepAsString);
        if (mat.find()){
            String key = stepAsString.substring(mat.start(), mat.end());
            variables.get(Thread.currentThread().getId()).put(key, value.toString());
        }
    }
    
    public Map<Method, Object[]> parseValidationStep(PendingStep step, String elementType) throws NoSuchMethodException {
        String stepAsString = step.stepAsString();
        Map<String, List<Method>> methods = null;
        Map<Method, Object[]> validateMethod = new HashMap<Method, Object[]>(2);
        String validationType = "validate";
        if (stepAsString.contains("validate")){
            methods = getMethods(this.getClass(), Validate.class);
        } else if (stepAsString.contains("assert")) {
            methods = getMethods(this.getClass(), Assert.class);
            validationType = "assert";
        }
        if (methods == null){
            throw new NoSuchMethodException("Could not find a validation method for: " + stepAsString);
        }
        boolean trueFalse = checkBoolean(stepAsString);
        Set<String> names = methods.keySet();
        String variable = getComparator(stepAsString);
        
        if (names.contains(validationType)){
            List<Method> matches = methods.get(validationType);
            for (Method match : matches){
                Class<?>[] params = match.getParameterTypes();
                if (params.length > 0){
                    if (params[0].equals(variable.getClass())){
                        validateMethod.put(match, new Object[]{variable});
                    } else if (params[0].equals(boolean.class)){
                        validateMethod.put(match, new Object[]{trueFalse});
                    }
                    return validateMethod;
                }
            }
        }
        
        String additional = validationType + getLastTerms(stepAsString).toLowerCase().replace(" ", "");// TODO;
        if (names.contains(additional)){
            List<Method> matches = methods.get(validationType);
            for (Method match : matches){
                Class<?>[] params = match.getParameterTypes();
                if (params.length > 0){
                    if (params[0].equals(variable.getClass())){
                        validateMethod.put(match, new Object[]{variable});
                    } else if (params[0].equals(boolean.class)){
                        validateMethod.put(match, new Object[]{trueFalse});
                    }
                    return validateMethod;
                }
            }
        }
        
        
        
        return null;
    }
    
    
    public Method parseStep(String stepAsString, String elementType) throws NoSuchMethodException{        
        Map<String, List<Method>> methods = getMethods(this.getClass(), null);
        String regex = RegexTerms.getMethod;
        Pattern pat = Pattern.compile(regex);
        Matcher mat = pat.matcher(stepAsString);
        String potentialMethod;
        List<Method> matchingMethods = null;
        while (mat.find()){
            potentialMethod = stepAsString.substring(mat.start(), mat.end()).replace(" ", "");
            if (methods.containsKey(potentialMethod)){ 
                matchingMethods = methods.get(potentialMethod);
                
            } else {
                regex += RegexTerms.addLowercaseWord;
                pat = Pattern.compile(regex);
                mat = pat.matcher(stepAsString);
            }
        }
        
        if (matchingMethods == null ){
            throw new NoSuchMethodException("Could not find a method for : " + stepAsString);
        }
        
        if (matchingMethods.size() == 1){
            return matchingMethods.get(0);
        } else {
            String name = getComparator(stepAsString);
            if (name == null){
                
            } else {
                for (Method method : matchingMethods){
                    List<Class<?>> classes = asList(method.getParameterTypes());
                    if (classes.contains(String.class) || classes.contains(Object.class)){
                        return method;
                    }
                }
            }
            return matchingMethods.get(0);
        }
    }
    
    public static Map<String, List<Method>> getMethods(Class<?> clazz, Class<? extends Annotation> filter) throws SecurityException, NoSuchMethodException{
        Map<String, List<Method>> methods = new HashMap<String, List<Method>>();
        for (Method method : clazz.getMethods()){ 
            
            
            String methodName = method.getName().toLowerCase();
            
            if (filter != null){
                Annotation ann = (Annotation) method.getAnnotation(filter);
                if (ann == null){
                    Class<?>[] interfaces = ClassUtils.getAllInterfacesForClass(clazz);
                    for (Class<?> implementation : interfaces){
                        try {
                            Method superMethod = implementation.getMethod(methodName, method.getParameterTypes());
                            ann = superMethod.getAnnotation(filter);
                            if (ann != null){
                                String englishName = "";
                                if (ann instanceof Validate){
                                    englishName = ((Validate)ann).englishName();
                                } else if (ann instanceof Assert){
                                    englishName = ((Assert)ann).englishName();
                                }
                                if (englishName.isEmpty()){
                                    break;
                                }
                                englishName = methodName + englishName.replace(" ", "").toLowerCase();
                                if (!methods.containsKey(englishName)){
                                    methods.put(englishName, new ArrayList<Method>());
                                }  
                                methods.get(englishName).add(method);
                                break;
                            }
                        } catch (NoSuchMethodException e){
                            continue;
                        }
                    }
                    if (ann == null){
                        continue;
                    }
                } 

                if (!methods.containsKey(methodName)){
                    methods.put(methodName, new ArrayList<Method>());
                }   
                
                methods.get(methodName).add(method);
                continue;
                
            }
            
            if (!methods.containsKey(methodName)){
                methods.put(methodName, new ArrayList<Method>());
            }
            
            if (!method.getReturnType().isInterface()) {
                methods.get(methodName).add(method);
            }
        }
        return methods;
    }

    public static boolean checkBoolean(String stepAsString){
        if (stepAsString.contains("not")){
            return false;
        }
        return true;
    }
    
    public Object[] getParameters(PendingStep step, Method method) throws IllegalArgumentException, SecurityException, IllegalAccessException, InvocationTargetException, NoSuchMethodException{
        try {
            return (Object[]) method.getDeclaringClass()
                    .getMethod("getParametersS", step.getClass(), method.getClass())
                    .invoke(null, step, method);
        } catch (NullPointerException e){
            throw new NoSuchMethodException("Could not find a method for step: " + step.stepAsString());
        }
    }

    public static Object[] getParametersS(PendingStep step, Method method) {
        Class<?>[] parameters = method.getParameterTypes();
        Object[] passParameters = new Object[parameters.length];
        
        for (int i=0;i<parameters.length;i++){
            Class<?> next = parameters[i];
            if (next.isAssignableFrom(Boolean.class)){
                passParameters[i] = checkBoolean(step.stepAsString());
            }
            if (passParameters[i] == null){
                throw new NoSuchMethodError("We are missing parameters for " 
                            + method.getName() + ", working on step " + step.stepAsString());
            }
        }
        return passParameters;
    }
    
    @When("I press the enter key on my keyboard")
    public void enterKey() {
        getSelenium().enterKey();
    }

    public static String escapeHtml(String original) {
        return StringEscapeUtils.escapeHtml(original);
    }

    @When("I hit the Tab Key")
    public void tabKey() {
        getSelenium().tabKey();
    }

    @When("I hit the Spacebar")
    public static void spaceBar() {
        KeyCommands.typeKey(KeyEvent.VK_SPACE);
    }
    
    @When("I hit the Period key")
    public static void keyPeriod() {
        KeyCommands.typeKey(KeyEvent.VK_PERIOD);
    }

    public static String unescapeHtml(String original) {
        return StringEscapeUtils.unescapeHtml(original);
    }

    private String savedPage;

    /**
     * Adds an error for this test, WARNING: default ErrorLevel is set to FAIL.
     * @param errorName
     */
    public void addError(String errorName){
        addError(errorName, ErrorLevel.FAIL);
    }
    public void addError(String errorName, ErrorLevel level) {
        getSelenium().getErrorCatcher().addError(errorName, Thread.currentThread().getStackTrace(), level);
    }

    public void addError(String errorName, String error, ErrorLevel level) {
        getSelenium().getErrorCatcher().addError(errorName, error, level);
    }

    public void addError(String errorName, Throwable stackTrace, ErrorLevel level) {
        getSelenium().getErrorCatcher().addError(errorName, stackTrace, level);
    }

    public Boolean assertEquals(Object expected, Object actual) {
        return assertEquals(expected, actual, true);
    }

    private Boolean assertEquals(Object expected, Object actual, Boolean areObjectsEqual) {
        if (compare(expected, actual) != areObjectsEqual) {
            Log.debug("your expected: '" + expected + "'" + " does not equal: '" + actual + "'");
            addError("your expected: '" + expected + "'" + " does not equal: '" + actual + "'", ErrorLevel.FATAL);
            return false;
        }
        return true;
    }

    public Boolean assertEquals(Object expected, Object actual, SeleniumEnumWrapper myEnum) {
        if (!compare(expected, actual)) {
            addError(myEnum.toString() + "\n" + myEnum.getLocatorsAsString(), "\t\tExpected = " + expected + "\n\t\tActual = " + actual, ErrorLevel.FATAL);
            return false;
        }
        return true;
    }

    public Boolean assertEquals(Object expected, SeleniumEnumWrapper actual) {
        return assertEquals(expected, getSelenium().getText(actual));
    }

    public Boolean assertEquals(SeleniumEnumWrapper anEnum) {
        return assertEquals(anEnum.getText(), getSelenium().getText(anEnum), anEnum);
    }

    public Boolean assertFalse(Boolean test, String error) {
        if (test) {
            addError(error, ErrorLevel.FATAL);
            return false;
        }
        return true;
    }

    public Boolean assertNotEquals(Object expected, Object actual) {
        return assertEquals(expected, actual, false);
    }

    public Boolean assertNotEquals(Object expected, Object actual, SeleniumEnumWrapper myEnum) {
        if (compare(expected, actual)) {
            addError(myEnum.toString() + "\n" + myEnum.getLocatorsAsString(), "\t\tExpected = " + expected + "\n\t\tActual = " + actual, ErrorLevel.FATAL);
            return false;
        }
        return true;
    }

    public Boolean assertNotEquals(Object expected, SeleniumEnumWrapper anEnum) {
        return assertNotEquals(anEnum.getText(), getSelenium().getText(anEnum), anEnum);
    }

    public Boolean assertStringContains(String partialString, String fullString) {
        if (!fullString.contains(partialString)) {
            addError(partialString + " not in " + fullString, ErrorLevel.FATAL);
            return false;
        }
        return true;
    }
    
    public Boolean assertTrue(Boolean test, String error, ErrorLevel level) {
        if (!test) {
            addError(error, level);
            return false;
        }
        return true;   
    }

    public Boolean assertTrue(Boolean test, String error) {
        return assertTrue(test, error, ErrorLevel.FATAL);
    }

    public Boolean compare(Object expected, Object actual) {
        Boolean results = false;
        if (actual instanceof SeleniumEnumWrapper) {
            results = compare(expected, getSelenium().getText((SeleniumEnumWrapper) actual));
        } else if (expected instanceof TextEnum) {
            results = compare(((TextEnum) expected).getText(), actual);
        } else {
            results = actual.equals(expected);
        }
        Log.debug("Expected: " + expected + " == Actual: " + actual + " is " + results);
        return results;
    }

    public String getCurrentLocation() {
        return getSelenium().getLocation();
    }

    public ErrorCatcher getErrors() {
        return getSelenium().getErrorCatcher();
    }

    public CoreMethodInterface getSelenium() {
        return CoreMethodLib.getSeleniumThread();
    }

    public String getTextFromElementWithFocus() {// TODO: jwimmer please check this again against new code.
        return getSelenium().getTextFromElementWithFocus();
    }

    public void open(SeleniumEnums pageToOpen) {
        getSelenium().open(new SeleniumEnumWrapper(pageToOpen));
    }
    
    public void open(Page page){
        getSelenium().open(page.getExpectedPath());
    }

    public void open(SeleniumEnums pageToOpen, Integer replaceNumber) {
        SeleniumEnumWrapper urlWithNumber = new SeleniumEnumWrapper(pageToOpen);
        urlWithNumber.updateURL(replaceNumber);
        getSelenium().open(urlWithNumber);
    }

    public void open(String url) {
        getSelenium().open(url);
    }

    public void openSavedPage() {
        open(savedPage);
    }
    
    public MasterTest pause(Integer timeout_in_secs, String reasonForPause) {
        System.out.println("pausing for: "+reasonForPause);
        StackTraceElement element = Thread.currentThread().getStackTrace()[2];
        AutomationThread.pause(timeout_in_secs, reasonForPause, element);
        return this;
    }

    public void savePageLink() {
        savedPage = getCurrentLocation();
    }

    public void killSelenium() {
    	CoreMethodLib.closeSeleniumThread();
    }

    @When("I type to the active field")
    @Aliases(values={"I type to the element with focus"})
    public void typeToElementWithFocus(String type) {
        WebDriver web = getSelenium().getWrappedDriver();
        web.switchTo().activeElement().sendKeys(type);
    }

    public Boolean validateEquals(Object expected, Object actual) {
        return validateEquals(expected, actual, true);
    }

    private Boolean validateEquals(Object expected, Object actual, Boolean areObjectsEqual) {
        Boolean result = compare(expected, actual);
        if (areObjectsEqual != result) {
            String match = "shouldn't match";
            if (areObjectsEqual) {
                match = "should match";
            }
            addError("your expected: '" + expected + "'" + " and actual is: '" + actual + "' they " + match, ErrorLevel.FAIL);
        }
        return result;
    }
    
    

    public Boolean validateNotEquals(Object expected, Object actual) {
        return validateEquals(expected, actual, false);
    }

    public Boolean validateStringContains(String partialString, String fullString) {
        if (!fullString.contains(partialString)) {
            addError(partialString + " not in " + fullString, ErrorLevel.FAIL);
            return false;
        }
        return true;
    }
    
    public Boolean validateTrue(Boolean test, String error) {
        if (!test) {
            addError(error, ErrorLevel.FAIL);
            return false;
        }
        return true;
    }
    
    public Boolean validateFalse(Boolean test, String error) {
        if (test) {
            addError(error, ErrorLevel.FAIL);
            return false;
        }
        return true;
    }
    
    public static String capitalizeFirstLettersTokenizer ( final String s ) {
        return capitalizeString(s, " ");
    }
    
    public static String capitalizeString(final String s, final String split){
        final StringTokenizer st = new StringTokenizer( s, split, true );
        final StringBuilder sb = new StringBuilder();
         
        while ( st.hasMoreTokens() ) {
            String token = st.nextToken();
            token = String.format( "%s%s",
                                    Character.toUpperCase(token.charAt(0)),
                                    token.substring(1).toLowerCase() );
            sb.append( token );
        }
            
        return sb.toString();
    }
    
    public static String captalizeEnumName(final String s){
        String formatted = capitalizeString(s, "_").replace("_", "");
        return Character.toLowerCase(formatted.charAt(0)) + formatted.substring(1);
        
    }
    
    public static String switchCase(final String s){
        StringWriter writer = new StringWriter();
        for (Character c : s.toCharArray()){
            if(Character.isUpperCase(c)){
                writer.write(Character.toLowerCase(c));
            } else {
                writer.write(Character.toUpperCase(c));
            }
        }
        return writer.toString();
    }


}
