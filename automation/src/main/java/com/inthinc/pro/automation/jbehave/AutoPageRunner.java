package com.inthinc.pro.automation.jbehave;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jbehave.core.configuration.Keywords;
import org.jbehave.core.embedder.Embedder;
import org.jbehave.core.model.Scenario;
import org.jbehave.core.steps.Step;
import org.jbehave.core.steps.StepCreator.PendingStep;
import org.jbehave.core.steps.StepType;

import com.inthinc.pro.automation.enums.JBehaveTermMatchers;
import com.inthinc.pro.automation.logging.Log;
import com.inthinc.pro.automation.selenium.AbstractPage;
import com.inthinc.pro.automation.selenium.CoreMethodInterface;
import com.inthinc.pro.automation.selenium.CoreMethodLib;
import com.inthinc.pro.automation.utils.AutoServers;

public class AutoPageRunner {
    

    private final Map<String, AbstractPage> pageMap;
    private static final Keywords keywords = new Keywords();
    
    private Object pageObject;
    private Class<?> pageClass;
    
    private AbstractPage currentPage;
    private Class<? extends AbstractPage> currentPageClass;
    
    private String workingOnStep;
    private String elementType;
    private String elementName;
    
    private final AutoActionFinder methodFinder;
    
    private AutoStepCreator stepCreator;
    
    private boolean inPopUp = false;

    
    public AutoPageRunner(List<AbstractPage> pages) {
        methodFinder = new AutoActionFinder();
        pageMap = new HashMap<String, AbstractPage>();
        for (AbstractPage page : pages){
            String className = page.getClass().getSimpleName().replace("Page", "").toLowerCase();
            pageMap.put(page.getUrl(), page);
            pageMap.put(className, page);
        }
    }
    
    public void beforeOrAfter(){
        inPopUp=false;
        pageObject = null;
        pageClass = null;
        workingOnStep = null;
        elementName = null;
        elementType = null;
        currentPage = null;
        currentPageClass = null;
    }
    
    public void setEmbedder(Embedder embedder){
        stepCreator = new AutoStepCreator(embedder);
        methodFinder.setStepCreator(stepCreator);
    }
    
    private void setCurrentPage(){
        if (inPopUp){
            return;
        }
        currentPage = null;
        currentPageClass = null;
        
        CoreMethodInterface selenium = CoreMethodLib.getSeleniumThread();
        String location = selenium.getLocation();
        AutoServers server = new AutoServers(); 
        location = location.replace(server.getWebAddress(), "");

        if (location.contains(";")){
            location = location.substring(0, location.indexOf(";"));
        } 
        
        Matcher mat = Pattern.compile("\\p{Digit}+").matcher(location); 
        while (mat.find()){
            String start = location.substring(0, mat.start());
            String end = location.substring(mat.end());
            location = start + end;
        }
        
        if (pageMap.containsKey(location) && pageMap.get(location).isOnPage()){ 
            currentPage = pageMap.get(location);    
            currentPageClass = currentPage.getClass();
        } else {
            for (AbstractPage page : pageMap.values()){
                if (page.isOnPage()){
                    currentPage = page;
                    currentPageClass = currentPage.getClass();
                    break;
                }
            }
            
        }
        pageObject = currentPage;
        pageClass = currentPageClass;
    }
    
    


    public Step tryStep(PendingStep step, Scenario scenario) {
        Step returnStep = step;
        try {
            StepType stepType = StepType.valueOf(keywords.startingWord(step.stepAsString()).toUpperCase());
            if (stepType.equals(StepType.AND)){
                stepType = StepType.valueOf(keywords.startingWord(step.previousNonAndStepAsString()).toUpperCase());
            } 
            
            workingOnStep = keywords.stepWithoutStartingWord(step.stepAsString(), stepType);
            if (popUpStep()){
                return stepCreator.createPopupStep(step.stepAsString());
            }
            
            if (!pageSpecificStep(step.stepAsString())){ 
                setCurrentPage();    
            }
            try {
                if (stepType == StepType.GIVEN){
                    returnStep = given(step);
                } else if (stepType == StepType.WHEN) { 
                    returnStep = when(step);
                } else {
                    returnStep = then(step);
                }
            } catch (StepException e){
                Log.info("Unable to finish step: %s\nError is: %s", workingOnStep, e.getError());
            } catch (NoSuchElementException e){
                Log.info("Unable to get a method for step: %s", step.stepAsString());
            }
        } catch (NullPointerException e){
            Log.info("Logging a nullPointer exception: %e", e);
            return stepCreator.createNullPointerStep(step.stepAsString(), e);
        }
        
        return returnStep;
    }
    
    private boolean popUpStep() {
        boolean setupStep = false;
        try {
            Pattern patOpen = Pattern.compile(RegexTerms.popupStep);
            Matcher mat = patOpen.matcher(workingOnStep);
            if (mat.find()){
                setupStep = true;
                String action = JBehaveTermMatchers.getTypeFromString(workingOnStep);
                if (JBehaveTermMatchers.openPopup.name().equals(action)){
                    String name = workingOnStep.substring(mat.start(), mat.end());
                    name = name.replace(" ", "").toLowerCase();
                    Object pagePopup = currentPageClass.getMethod(JBehaveTermMatchers._popUp.name()).invoke(currentPage);
                    Method[] methods = pagePopup.getClass().getMethods();
                    for (Method method : methods){
                        if (method.getName().toLowerCase().equals(name)){
                            pageObject = method.invoke(pagePopup);
                            pageClass = pageObject.getClass();
                            break;
                        }
                    }
                    
                    inPopUp = true;                    
                } else if (JBehaveTermMatchers.closePopup.name().equals(action)){
                    inPopUp = false;
                    pageObject = currentPage;
                    pageClass = currentPageClass;
                }
            } 
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        
        return setupStep;
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
                    String validate = step.stepAsString().contains("validate") ? "verify" : "assert";
                    if (AutoStepVariables.checkBoolean(step.stepAsString())){
                        return stepCreator.createPageStep(step, currentPage, currentPageClass.getMethod(validate + "OnPage"), true);
                    } else {
                        return stepCreator.createPageStep(step, currentPage, currentPageClass.getMethod(validate + "NotOnPage"), true);
                    }
                } else {
                    return step;
                }
            }
            return methodFinder.findAction(getElement(), elementType, elementName, step); 
        } catch (NullPointerException e){
            String message = String.format("step: %s\nstepCreator: %s\ncurrentPage: %s\nmethodFinder: %s", 
                    workingOnStep, stepCreator, currentPage, methodFinder);
                    
            return stepCreator.createNullPointerStep(message, e);
        } catch (NoSuchMethodException e) {
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
                return stepCreator.createPageStep(step, currentPage, currentPageClass.getMethod("verifyOnPage"), true);
            }
            return methodFinder.findAction(getElement(), elementType, elementName, step); 
        } catch (NullPointerException e){
            String message = String.format("step: %s\nstepCreator: %s\ncurrentPage: %s\nmethodFinder: %s", 
                    workingOnStep, stepCreator, currentPage, methodFinder);
                    
            return stepCreator.createNullPointerStep(message, e);
        } catch (NoSuchMethodException e) {
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
        } catch (NullPointerException e){
            String message = String.format("step: %s\nstepCreator: %s\ncurrentPage: %s\nmethodFinder: %s", 
                    workingOnStep, stepCreator, currentPage, methodFinder);
                    
            return stepCreator.createNullPointerStep(message, e);
        } catch (NoSuchMethodException e) {
            Log.info(e);
        } catch (IllegalArgumentException e) {
            Log.info(e);
        }
        
        return step;
    }
    
    private Object getElement() {
        Throwable err = null;
        try {
            elementType = JBehaveTermMatchers.getAlias(workingOnStep); 
            Object elementCategory = pageClass.getMethod(JBehaveTermMatchers.getTypeFromString(workingOnStep)).invoke(pageObject);
            return getElement(elementCategory);
        } catch (NullPointerException e){
            String message = String.format("step: %s\npageClass: %s\npageObject: %s", 
                    workingOnStep, pageClass, pageObject);
            throw new StepException(workingOnStep, message, e);
        } catch (IllegalArgumentException e) {
            err = e;
        } catch (NoSuchMethodException e) {
            err = e;
        } catch (IllegalAccessException e) {
            err = e;
        } catch (InvocationTargetException e) {
            err = e;
        }
        String currentError = String.format("Working on getting the elementType %s, with pageObject %s", 
                elementType, pageClass.getSimpleName());
        
        throw new StepException(workingOnStep, currentError, err);
    }
    
    private Object getElement(Object elementClass) {
        try {
            Map<String, Method> methods = new HashMap<String, Method>();
            for (Method method : elementClass.getClass().getMethods()){
                methods.put(method.getName().toLowerCase(), method);
            }
            
            elementName = RegexTerms.getMatch(RegexTerms.getElementName.replace("***",elementType), workingOnStep)
                    .replace(" ", "").toLowerCase();
            if (elementName != null){
                if (elementType.equals("label")){
                    elementName = "label" + elementName; 
                }
                
                if (methods.containsKey(elementName)){
                    return tryElementName(elementClass, methods.get(elementName));
                } else {
                    for (Map.Entry<String, Method> entry : methods.entrySet()){
                        if (entry.getKey().contains("column")){
                            try {
                                return tryElementName(elementClass, entry.getValue()); 
                            } catch (Exception e){
                                continue;
                            }
                        }
                    }
                }
            }
            throw new NoSuchMethodException("Could not find Element for " + workingOnStep);
    
        } catch (NullPointerException e){
            String message = String.format("elementClass: %s\telementType: %s", elementClass, elementType);
            throw new StepException(workingOnStep, message, e);
        } catch (Exception e) {
            String currentError = String.format("Working on getting the elementName %s, with pageObject %s", elementName, pageClass);    
            throw new StepException(workingOnStep, currentError, e);
        }
    }
    
    
    private Object matchEnum(Class<?> clazz){
        try {
            String step = workingOnStep.toLowerCase().replace(" ", "");
            for (Object obj : clazz.getEnumConstants()){
                String name = ((Enum<?>)obj).name().replace("_", "").toLowerCase();
                if (step.contains(name)){
                    return obj;
                }
            }
        } catch (NullPointerException e){
            String error = String.format("class: %s\tstep: %s", clazz, workingOnStep);
            throw new StepException(workingOnStep, error, e);
        }
        return null;
    }
    
    private Object tryElementName(Object elementClass, Method method) {
        try {
            Class<?>[] parameters = method.getParameterTypes();
            Object[] passParameters = new Object[parameters.length];
            
            for (int i=0;i<parameters.length;i++){
                Class<?> next = parameters[i];
                if (next.isEnum()){
                    passParameters[i] = matchEnum(next);
                }
                if (passParameters[i] == null){
                    throw new NoSuchMethodException("We are missing parameters for " 
                                + method.getName());
                }
            }
            return method.invoke(elementClass, passParameters); 
        } catch (NullPointerException e){
            String error = String.format("elementClass: %s\tmethod: %s", elementClass, method);
            throw new StepException(workingOnStep, error, e);
        } catch (Exception e){
        	Log.info(e);
            String error = String.format("Trying to execute %s on object %s.", 
                    method.getName(), elementClass.getClass().getSimpleName());
            throw new StepException(workingOnStep, error, e);
        }
    }

}
