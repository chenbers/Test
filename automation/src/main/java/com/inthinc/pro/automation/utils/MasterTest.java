package com.inthinc.pro.automation.utils;

import static java.util.Arrays.asList;

import java.awt.event.KeyEvent;
import java.io.StringWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.ClassUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.jbehave.core.annotations.AfterScenario;
import org.jbehave.core.annotations.Aliases;
import org.jbehave.core.annotations.Composite;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Named;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jbehave.core.steps.StepCreator.PendingStep;
import org.openqa.selenium.WebDriver;

import com.inthinc.pro.automation.AutomationPropertiesBean;
import com.inthinc.pro.automation.annotations.AutomationAnnotations.Assert;
import com.inthinc.pro.automation.annotations.AutomationAnnotations.Validate;
import com.inthinc.pro.automation.enums.ErrorLevel;
import com.inthinc.pro.automation.enums.JBehaveTermMatchers;
import com.inthinc.pro.automation.enums.SeleniumEnumWrapper;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.automation.interfaces.TextEnum;
import com.inthinc.pro.automation.jbehave.RegexTerms;
import com.inthinc.pro.automation.logging.Log;
import com.inthinc.pro.automation.models.Account;
import com.inthinc.pro.automation.models.User;
import com.inthinc.pro.automation.rest.RestCommands;
import com.inthinc.pro.automation.selenium.AutomationProperties;
import com.inthinc.pro.automation.selenium.CoreMethodInterface;
import com.inthinc.pro.automation.selenium.CoreMethodLib;
import com.inthinc.pro.automation.selenium.ErrorCatcher;
import com.inthinc.pro.automation.selenium.Page;

public abstract class MasterTest {
    
    private static final String editableAccountUser = "an account that can be edited";
    private static final String editableUser = "as a user that can be edited";
    private static final String mainUser = "as the default user";
    
    private static ThreadLocal<User> myUser = new ThreadLocal<User>();

    private static ThreadLocal<Map<String, String>> variables = new ThreadLocal<Map<String, String>>();
    static {
        variables.set(new HashMap<String, String>());
    }
    

    private final AutomationPropertiesBean apb;
    private static ThreadLocal<RestCommands> rest = new ThreadLocal<RestCommands>();
    
    private static ThreadLocal<String> savedPage = new ThreadLocal<String>();
    private static ThreadLocal<Account> account = new ThreadLocal<Account>();

    
    public MasterTest(){
        apb = AutomationProperties.getPropertyBean(); 
        if (variables.get()==null){
            variables.set(new HashMap<String, String>());
        }
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

    public static boolean checkBoolean(String stepAsString){
        if (stepAsString.contains("not")){
            return false;
        }
        return true;
    }


    public static String escapeHtml(String original) {
        return StringEscapeUtils.escapeHtml(original);
    }
    private static Annotation getAnnotation(Method method, Class<? extends Annotation> annotation){
        Set<Annotation> set = getAnnotations(method);
        for (Annotation ann : set){
            if (annotation.equals(ann.annotationType())){
                return ann;
            }
        }
        return null;
    }
    
    private static Set<Annotation> getAnnotations(Method method){
        Set<Annotation> ann = new HashSet<Annotation>();
        ann.addAll(asList(method.getAnnotations()));
        for (Object interfaces : ClassUtils.getAllInterfaces(method.getDeclaringClass())){
            try {
                Method superMethod = ((Class<?>)interfaces).getMethod(method.getName(), method.getParameterTypes());
                ann.addAll(asList(superMethod.getAnnotations()));
            } catch (SecurityException e) {
                Log.debug(e);
            } catch (NoSuchMethodException e) {
                Log.debug(e);
            }
        }
        return ann;
    }
    
    public static String getComparator(String stepAsString){
        String elementType = JBehaveTermMatchers.getAlias(stepAsString);
        String variable = RegexTerms.getMatch(RegexTerms.getVariable.replace("***", elementType), stepAsString);
        Map<String, String> temp = variables.get();
        if (variable.isEmpty()){
            if (stepAsString.contains("\"")){
                int quote = stepAsString.indexOf("\"") + 1;
                return stepAsString.substring(quote, stepAsString.indexOf("\"", quote));
            } else {
                for (Map.Entry<String, String> entry : temp.entrySet()){
                    if (stepAsString.contains(entry.getKey())){
                        return entry.getValue();
                    }
                }
            }
        }
        if (variable.contains("\"")){
            return variable.replace("\"", "");
        } else {
            return temp.get(variable);
        }
    }
        
    public static String getEditableaccountuser() {
        return editableAccountUser;
    }
    
    public static String getEditableuser() {
        return editableUser;
    }
    
    
    public static String getMainuser() {
        return mainUser;
    }
    
    public static Map<String, List<Method>> getMethods(Class<?> clazz, Class<? extends Annotation> filter) throws SecurityException, NoSuchMethodException{
        Map<String, List<Method>> methods = new HashMap<String, List<Method>>();
        Set<String> deprecated = new HashSet<String>();
        for (Method method : clazz.getMethods()){ 

            String methodName = method.getName().toLowerCase();
            
            if (getAnnotation(method, Deprecated.class)!=null && !deprecated.contains(methodName)){
                deprecated.add(methodName);
                if (methods.containsKey(methodName)){
                    methods.remove(methodName);
                }
                continue;
            }
            
            
            if (filter != null){
                Annotation ann = getAnnotation(method, filter);
                if (ann != null){
                    if (ann != null){
                        String englishName = "";
                        String testName = "";
                        if (ann instanceof Validate){
                            englishName = ((Validate)ann).englishName();
                            testName = ((Validate)ann).testName();
                        } else if (ann instanceof Assert){
                            englishName = ((Assert)ann).englishName();
                            testName = ((Assert)ann).testName();
                        } else {
                            continue;
                        }
                        englishName = testName + englishName.replace(" ", "").toLowerCase();
                        if (!methods.containsKey(englishName)){
                            methods.put(englishName, new ArrayList<Method>());
                        }  
                        methods.get(englishName).add(method);
                    }
                    if (!methods.containsKey(methodName)){
                        methods.put(methodName, new ArrayList<Method>());
                    } 
                    methods.get(methodName).add(method);
                    continue;
                } else {
                    continue;
                }
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
    
    public static Map<String, String> getVariables(){
        return variables.get();
    }
    
    @Given("I hit the Period key")
    @When("I hit the Period key")
    public static void keyPeriod() {
        KeyCommands.typeKey(KeyEvent.VK_PERIOD);
    }
    
    public static void setComparator(String stepAsString, Object value){
        String elementType = JBehaveTermMatchers.getAlias(stepAsString);
        String key = RegexTerms.getMatch(RegexTerms.setVariable.replace("***", elementType), stepAsString);
        
        if (!key.isEmpty()){
            variables.get().put(key, value.toString());
        }
    }
    
    @Given("I hit the Spacebar")
    @When("I hit the Spacebar")
    public static void spaceBar() {
        KeyCommands.typeKey(KeyEvent.VK_SPACE);
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
    
    public static String unescapeHtml(String original) {
        return StringEscapeUtils.unescapeHtml(original);
    }
    

    

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
            Log.info("your expected: '" + expected + "'" + " does not equal: '" + actual + "'");
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

    @Then("I assert \"$lookfor\" is on the page")
    public boolean assertIsTextOnPage(String lookfor) { 
        return assertTrue(getSelenium().isTextPresent(lookfor), lookfor + " was not found on this page.");
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

    public Boolean assertTrue(Boolean test, String error) {
        return assertTrue(test, error, ErrorLevel.FATAL);
    }

    public Boolean assertTrue(Boolean test, String error, ErrorLevel level) {
        if (!test) {
            addError(error, level);
            return false;
        }
        return true;   
    }

    @AfterScenario
    public void clearUser(){
        if (rest.get() == null || myUser == null){
            return;
        }
        User isUpdated = rest.get().getObject(User.class, myUser.get().getUserID());
        if (isUpdated.doesPasswordMatch(myUser.get().getPassword())){
            return;
        } 
        User update = new User();
        update.setPassword(apb.getPassword());
        update.setUsername(isUpdated.getUsername());
        update.setUserID(isUpdated.getUserID());
        update.setRoles(myUser.get().getRoles());
        rest.get().putObject(User.class, update, null);
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
        Log.info("Expected: " + expected + " == Actual: " + actual + " is " + results);
        return results;
    }

    @Given("I hit the Enter Key")
    @When("I hit the Enter Key")
    public void enterKey() {
        getSelenium().enterKey();
    }
    
    public String getCurrentLocation() {
        return getSelenium().getLocation();
    }

    public ErrorCatcher getErrors() {
        return getSelenium().getErrorCatcher();
    }

    public User getMyUser() {
        return myUser.get();
    }

    public Object[] getParameters(String stepAsString, Method method) throws NoSuchMethodException{
        try {
            Class<?>[] parameters = method.getParameterTypes();
            Object[] passParameters = new Object[parameters.length];
            
            for (int i=0;i<parameters.length;i++){
                Class<?> next = parameters[i];
                if (next.isPrimitive()){
                    next = ClassUtils.primitiveToWrapper(next);
                }
                if (next.equals(Boolean.class)) {
                    passParameters[i] = checkBoolean(stepAsString);
                } else if (next.equals(Integer.class)) {
                    Integer param = AutomationNumberManager.extractXNumber(stepAsString, 1);
                    passParameters[i] = param == null || param == 0 ? 1 : param;
                } else {
                    next = String.class;
                    passParameters[i] = getComparator(stepAsString);    
                }
                if (passParameters[i] == null || !passParameters[i].getClass().equals(next)){
                    throw new NoSuchMethodException("We are missing parameters for " 
                                + method.getName() + ", working on step " + stepAsString);
                }
            }
            return passParameters;
        } catch (NullPointerException e){
            throw new NoSuchMethodException("Could not find a method for step: " + stepAsString);
        }
    }

    public CoreMethodInterface getSelenium() {
        return CoreMethodLib.getSeleniumThread();
    }

    public String getTextFromElementWithFocus() {// TODO: jwimmer please check this again against new code.
        return getSelenium().getTextFromElementWithFocus();
    }

    @Given("I am logged in $params")
    @Composite(steps = {
            "Given I am using <params> for my user",
            "Given I log in"})
    public void givenIAmLoggedInAs(@Named("params")String params){
    }
    
    @Given("I am logged in")
    @Composite(steps = {
            "Given I am using as the default user for my user",
            "Given I log in"})
    public void givenIAmLoggedIn(){}
    
    @Given("I log in")
    @When("I log in")
    @Composite(steps = {
            "Given I am on the Login page", 
            "When I type my user name into the Username field", 
            "When I type my password into the Password field", 
            "When I click the Login button"})
    public void loginProcess(){}
    
    public void killSelenium() {
    	CoreMethodLib.closeSeleniumThread();
    }

    public void open(Page page){
        getSelenium().open(page.getExpectedPath());
    }
    
    public void open(SeleniumEnums pageToOpen) {
        getSelenium().open(new SeleniumEnumWrapper(pageToOpen));
    }

    public void open(SeleniumEnums pageToOpen, Integer replaceNumber) {
        SeleniumEnumWrapper urlWithNumber = new SeleniumEnumWrapper(pageToOpen);
        urlWithNumber.updateURL(replaceNumber);
        getSelenium().open(urlWithNumber);
    }

    public void open(String url) {
        getSelenium().open(url);
    }

    @Given("I click the bookmark I just added")
    @When("I click the bookmark I just added")
    public void openSavedPage() {
        open(savedPage.get());
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
            } 
            regex += RegexTerms.addLowercaseWordSpaceBefore;
            pat = Pattern.compile(regex);
            mat = pat.matcher(stepAsString);
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
    
    public Map<Method, Object[]> parseValidationStep(PendingStep step, String elementType) throws NoSuchMethodException {
        String stepAsString = step.stepAsString();
        Map<String, List<Method>> methods = null;
        Map<Method, Object[]> validateMethod = new HashMap<Method, Object[]>(2);
        String validationType = "validate";
        Class<? extends Annotation> ann = Validate.class;
        if (stepAsString.contains("assert")) {
            ann = Assert.class;
            validationType = "assert";
        }
        methods = getMethods(this.getClass(), ann);
        if (methods == null){ 
            throw new NoSuchMethodException("Could not find a validation method for: " + stepAsString);
        }
        boolean trueFalse = checkBoolean(stepAsString);
        Set<String> names = methods.keySet();
        String variable = getComparator(stepAsString);
        List<Method> methodList = new ArrayList<Method>();
        for (String name : names) {
            String shorter = name.replace(validationType, "");
            if (stepAsString.contains(shorter) && shorter.length() > 0){
                methodList = methods.get(name);
                break;
            }
        }
        if (methodList.isEmpty()){
            if (methods.containsKey(validationType)) {
                methodList = methods.get(validationType);
            }
        }
        
        if (methodList != null){
            for (Method match : methodList){
                Class<?>[] params = match.getParameterTypes();
                if (params.length > 0){
                    if (variable != null && params[0].equals(variable.getClass())){
                        validateMethod.put(match, new Object[]{variable});
                    } else if (params[0].equals(Boolean.class)){
                        validateMethod.put(match, new Object[]{trueFalse});
                    }
                    return validateMethod;
                }
            }
        }
        throw new NoSuchMethodException("Could not find a validation step for " + stepAsString);
    }

    public MasterTest pause(Integer timeout_in_secs, String reasonForPause) {
        System.out.println("pausing for: "+reasonForPause);
        StackTraceElement element = Thread.currentThread().getStackTrace()[2];
        AutomationThread.pause(timeout_in_secs, reasonForPause, element);
        return this;
    }

    @Given("I bookmark the page")
    @When("I bookmark the page")
    public void savePageLink() {
        savedPage.set(getCurrentLocation());
    }

    @Given("I hit the Tab Key")
    @When("I hit the Tab Key")
    public void tabKey() {
        getSelenium().tabKey();
    }

    @Given("I type to the active field")
    @When("I type to the active field")
    @Aliases(values={"I type to the element with focus"})
    public void typeToElementWithFocus(String type) {
        WebDriver web = getSelenium().getWrappedDriver();
        web.switchTo().activeElement().sendKeys(type);
    }
    
    

    @Given("I am using $params for my user")
    public void useParamsToSetDefaultUser(@Named("params")String params){
        if (params.equalsIgnoreCase(mainUser)){
            List<String> users = apb.getMainAutomation();
            rest.set(new RestCommands(users.get(0), apb.getPassword()));
            myUser.set(rest.get().getObject(User.class, users.get(1)));
        } else if (params.equalsIgnoreCase(editableUser)){
            
        } else if (params.equalsIgnoreCase(editableAccountUser)){
            List<String> users = apb.getEditableAccount();
            rest.set(new RestCommands(users.get(0), apb.getPassword()));
            myUser.set(rest.get().getObject(User.class, users.get(1)));
            account.set(rest.get().getObject(Account.class, null));
        }
        if (myUser == null){
            throw new NullPointerException("Type of user was bad: " + params);
        }
        variables.get().put("my user name", myUser.get().getUsername());
        variables.get().put("my password", apb.getPassword());
    }
    
    @AfterScenario
    public void afterScenario(){
        if (account.get()!= null){
            rest.get().putObject(Account.class, account.get(), null);
        }
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
            Log.info("your expected: '" + expected + "'" + " and actual is: '" + actual + "' they " + match, ErrorLevel.FAIL);
            addError("your expected: '" + expected + "'" + " and actual is: '" + actual + "' they " + match, ErrorLevel.FAIL);
        }
        return result;
    }
    
    public Boolean validateFalse(Boolean test, String error) {
        if (test) {
            addError(error, ErrorLevel.FAIL);
            return false;
        }
        return true;
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
    
    @Then("I verify \"$lookfor\" is on the page")
    public boolean verifyIsTextOnPage(String lookfor) { 
        return validateTrue(getSelenium().isTextPresent(lookfor), lookfor + " was not found on this page.");
    }


}
