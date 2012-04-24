package com.inthinc.pro.automation.jbehave;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jbehave.core.configuration.Keywords;
import org.jbehave.core.embedder.Embedder;
import org.jbehave.core.steps.Step;
import org.jbehave.core.steps.StepCreator.PendingStep;
import org.jbehave.core.steps.StepType;

import com.inthinc.pro.automation.AutomationPropertiesBean;
import com.inthinc.pro.automation.elements.ElementBase;
import com.inthinc.pro.automation.enums.Addresses;
import com.inthinc.pro.automation.enums.JBehaveTermMatchers.ElementTypes;
import com.inthinc.pro.automation.logging.Log;
import com.inthinc.pro.automation.selenium.AbstractPage;
import com.inthinc.pro.automation.selenium.AutomationProperties;
import com.inthinc.pro.automation.selenium.CoreMethodInterface;
import com.inthinc.pro.automation.selenium.CoreMethodLib;

public class AutoPageRunner {
    

    private final Map<String, AbstractPage> pageMap;
    private static final Keywords keywords = new Keywords();
    private AbstractPage currentPage;
    private Class<? extends AbstractPage> currentPageClass;
    private Map<String, Method> classMethods;
    
    private String workingOnStep;
    private String elementType;
    private String elementName;
    
    private final AutoActionFinder methodFinder;
    
    private AutoStepCreator stepCreator;
    
    

    
    
    
    public AutoPageRunner(List<AbstractPage> pages) {
        methodFinder = new AutoActionFinder();
        pageMap = new HashMap<String, AbstractPage>();
        for (AbstractPage page : pages){
            String className = page.getClass().getSimpleName().replace("Page", "").toLowerCase();
            pageMap.put(page.getUrl(), page);
            pageMap.put(className, page);
        }
        Log.info(pageMap);
    }
    
    public void setEmbedder(Embedder embedder){
        stepCreator = new AutoStepCreator(embedder, currentPage, currentPageClass);
        methodFinder.setStepCreator(stepCreator);
    }
    
    private void setCurrentPage(){
        CoreMethodInterface selenium = CoreMethodLib.getSeleniumThread();
        String location = selenium.getLocation();
        AutomationPropertiesBean apb = AutomationProperties.getPropertyBean();
        Addresses server = Addresses.getSilo(apb.getSilo());
        location = location.replace(server.getWebAddress(), "");

        if (location.contains(";")){
            location = location.substring(0, location.indexOf(";"));
        } 
        
        Matcher mat = Pattern.compile("[0-9]").matcher(location); 
        while (mat.find()){
            String start = location.substring(0, mat.end()-2);
            String end;
            if (mat.end() != location.length()){
                end = location.substring(mat.end()+1);
            } else {
                end = "";
            }
            location = start + end;
        }
        
        if (pageMap.containsKey(location)){
            currentPage = pageMap.get(location);    
            currentPageClass = currentPage.getClass();
        } else {
            for (AbstractPage page : pageMap.values()){
                if (page.isOnPage()){
                    currentPage = page;
                }
            }
        }
    }
    
    private void setClassMethods(Class<?> clazz){
        classMethods = new HashMap<String, Method>();
        for (Method method : clazz.getMethods()){
            classMethods.put(method.getName().toLowerCase(), method);
        }
    }
    


    public Step tryStep(PendingStep step) {
        Step returnStep = step;
        
        StepType stepType = StepType.valueOf(keywords.startingWord(step.stepAsString()).toUpperCase());
        if (stepType.equals(StepType.AND)){
            stepType = StepType.valueOf(keywords.startingWord(step.previousNonAndStepAsString()).toUpperCase());
        } 
        
        workingOnStep = keywords.stepWithoutStartingWord(step.stepAsString(), stepType);
        
        if (!pageSpecificStep(step.stepAsString())){
            setCurrentPage();    
        }
        
        if (stepType == StepType.GIVEN){
            returnStep = given(step);
        } else if (stepType == StepType.WHEN) { 
            returnStep = when(step);
        } else {
            returnStep = then(step);
        }
        
        return returnStep;
    }
    
    private void findPageFromStep(){
        Pattern pat = Pattern.compile(RegexTerms.getPageName);
        Matcher mat = pat.matcher(workingOnStep);
        while (mat.find()){
            String pageName = workingOnStep.substring(mat.start(), mat.end()).replace(" ", "").toLowerCase();
            if (pageMap.containsKey(pageName)){
                currentPage = pageMap.get(pageName);
                currentPageClass = currentPage.getClass();
                return;
            }
        }
    }
    
    private Step then(PendingStep step){
        try {
            if (pageSpecificStep(workingOnStep)){
                findPageFromStep();
                if (currentPage!=null){
                    setClassMethods(currentPageClass);
                    return stepCreator.createPageStep(step, currentPage, currentPageClass.getMethod("assertOnPage"), true);
                } else {
                    return step;
                }
            }
            return methodFinder.findAction(getElement(), elementType, elementName, step); 
        } catch (NoSuchMethodException e) {
            Log.info(e);
        } catch (IllegalArgumentException e) {
            Log.info(e);
        } catch (SecurityException e) {
            Log.info(e);
        } catch (IllegalAccessException e) {
            Log.info(e);
        } catch (InvocationTargetException e) {
            Log.info(e);
        } 
        
        return step;
    }
    
    private boolean pageSpecificStep(String step){
        if (Pattern.compile(RegexTerms.onPage).matcher(step).find()){
            findPageFromStep();
            return true;
        }
        return false;
    }
    
    private Step when(PendingStep step){
        try {
            if (pageSpecificStep(workingOnStep)){       
                setClassMethods(currentPageClass);
                if (classMethods.containsKey("verifyOnPage".toLowerCase())){
                    return stepCreator.createPageStep(step, currentPage, currentPageClass.getMethod("verifyOnPage"), true);
                } else {
                    throw new NoSuchMethodException("Could not verify on page");
                }
            }
            return methodFinder.findAction(getElement(), elementType, elementName, step); 
        } catch (NoSuchMethodException e) {
            Log.info(e);
        } catch (IllegalArgumentException e) {
            Log.info(e);
        } catch (SecurityException e) {
            Log.info(e);
        } catch (IllegalAccessException e) {
            Log.info(e);
        } catch (InvocationTargetException e) {
            Log.info(e);
        } 
        
        return step;
    }
    
    private Step given(PendingStep step){
        try {
            if (pageSpecificStep(workingOnStep)) {
                return stepCreator.createPageStep(step, currentPage, currentPageClass.getMethod("load"));
            }
            return methodFinder.findAction(getElement(), elementType, elementName, step); 
        } catch (NoSuchMethodException e) {
            Log.info(e);
        } catch (IllegalArgumentException e) {
            Log.info(e);
        } catch (IllegalAccessException e) {
            Log.info(e);
        } catch (InvocationTargetException e) {
            Log.info(e);
        }
        
        return step;
    }
    
    private ElementBase getElement() throws NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException{
        elementType = ElementTypes.getAlias(workingOnStep); 
        Object elementCategory = currentPageClass.getMethod(ElementTypes.getTypeFromString(workingOnStep)).invoke(currentPage);
        return (ElementBase) getElement(elementCategory);
        
    }
    
    private Object getElement(Object elementClass) throws NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException{
        Map<String, Method> methods = new HashMap<String, Method>();
        for (Method method : elementClass.getClass().getMethods()){
            methods.put(method.getName().toLowerCase(), method);
        }
        
        elementName = getParameter(RegexTerms.getElementName.replace("***",elementType), workingOnStep)
                .replace(" ", "").toLowerCase();
        if (elementType.equals("label")){
            elementName = "label" + elementName;
        }
        if (elementName != null && methods.containsKey(elementName)){
            return tryElementName(elementClass, methods.get(elementName));
        }
        
        throw new NoSuchMethodError("Could not find Element for " + workingOnStep);
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    private Object tryElementName(Object elementClass, Method method) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException{
        Class<?>[] parameters = method.getParameterTypes();
        String columnName = getParameter(RegexTerms.getColumnName, workingOnStep);
        String rowName = getParameter(RegexTerms.getRowName, workingOnStep);
        Object[] passParameters = new Object[parameters.length];
        
        for (int i=0;i<parameters.length;i++){
            Class<?> next = parameters[i];
            if (columnName != null && next.isAssignableFrom(Enum.class)){
                try {
                    passParameters[i] = Enum.valueOf((Class<Enum>)next, columnName);
                } catch (IllegalArgumentException e){
                    Log.warning("Column: %s enum does not contain %",next.getSimpleName(), columnName);
                }
            }
            if (rowName != null && next.isAssignableFrom(Enum.class)){
                try {
                    passParameters[i] = Enum.valueOf((Class<Enum>)next, columnName);
                } catch (IllegalArgumentException e2){
                    Log.warning("Row: %s enum does not contain %",next.getSimpleName(), rowName);
                }                
            }
            if (passParameters[i] == null){
                throw new NoSuchMethodError("We are missing parameters for " 
                            + method.getName() + ", working on step " + workingOnStep);
            }
        }
        return method.invoke(elementClass, passParameters);
    }

    private String getParameter(String regex, String toMatch) {
        Pattern pat = Pattern.compile(regex);
        Matcher mat = pat.matcher(toMatch);
        if (mat.find()){
            return toMatch.substring(mat.start(), mat.end());
        }
        return null;
    }
    
    
}
