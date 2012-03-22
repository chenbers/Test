package com.inthinc.pro.automation.elements;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.jbehave.core.steps.StepCreator.PendingStep;

import com.inthinc.pro.automation.enums.ErrorLevel;
import com.inthinc.pro.automation.enums.SeleniumEnumWrapper;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.automation.utils.MasterTest;

public abstract class ElementBase extends MasterTest implements ElementInterface {
    

    protected final static Logger logger = Logger.getLogger(ElementBase.class);

    protected final static String parentXpath = "/..";

    protected HashMap<String, String> current;

    protected SeleniumEnumWrapper myEnum;

    public ElementBase() {
    }

    public ElementBase(SeleniumEnums anEnum, Object ...objects){
    	setMyEnum(anEnum);
    	myEnum.makeReplacements(objects);
    }
    
    @Override
    public Boolean assertPresence(Boolean present) {
        return assertEquals(present, isPresent());
    }

    @Override
    public Boolean assertVisibility(Boolean visible) {
        return assertTrue(visible == getSelenium().isVisible(myEnum), myEnum.toString());
    }

    
    /**
     * checkElementsPresent is a helper method to encapsulate checking to see if an element is present. This presents options to check for an element without requiring an error
     * getting logged if the element is not present (many valid tests can be built using the fact that an element should NOT be present yet/anymore)
     * 
     * @param errorLevel
     *            NOTE: if errorLevel is null, this is not an error
     * @param enums
     *            locators for the element in question
     * @return true only if the element(s) are currently present
     */
    private Boolean checkElementsPresent(ErrorLevel errorLevel, Object... enums) {
        SeleniumEnumWrapper temp = myEnum;
        Boolean result = true;
        for (Object enumerated : enums) {
            setMyEnum((SeleniumEnums) enumerated);
            result &= isPresent();
//            if (errorLevel != null)
//                assertTrue(isPresent(), myEnum.toString(), errorLevel);
        }
        myEnum = temp;
        return result;
    }

    @Override
    public ElementBase focus() {
        getSelenium().focus(myEnum);
        return this;
    }
    
    @Override
    public String getAttribute(String attributeToGet) {
        return getSelenium().getAttribute(myEnum, attributeToGet);
    }

    @Override
    public String getCurrentLocation() {
        return getSelenium().getLocation();
    }

    @Override
    public SeleniumEnums getMyEnum() {
        return myEnum;
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

    @Override
    public Boolean hasFocus() {
        return getSelenium().hasFocus(myEnum);
    }

    @Override
    public Boolean isEditable() {
        return getSelenium().isEditable(myEnum);
    }

    public Boolean isElementsPresent(ArrayList<SeleniumEnums> enums){
        return checkElementsPresent(ErrorLevel.COMPARE, enums.toArray());
    }

    @Override
    public Boolean isPresent() {
        return getSelenium().isElementPresent(myEnum);
    }
    
    @Override
    public Boolean isVisible() {
        return getSelenium().isVisible(myEnum);
    }
    

    protected ElementBase replaceNumber(Integer number) {
        myEnum.replaceNumber(number);
        return this;
    }

    /**
     * This method should only be used by the page objects to make a replacement.<br />
     * If you have to use it, we accept no responsibility for the outcome.<br />
     * You have been warned.<br />
     * 
     * @param replaceOld
     * @param withNew
     * @return
     */
    public ElementBase replaceSubStringInMyEnum(String replaceOld, String withNew) {
        myEnum.replaceOldWithNew(replaceOld, withNew);
        return this;
    }

    protected ElementBase replaceWord(String word) {
        myEnum.replaceWord(word);
        return this;
    }

    protected void setCurrentLocation() {
        String uri = getCurrentLocation();
        logger.debug(uri);
        String[] address = uri.split("/");
        current = new HashMap<String, String>();
        current.put("protocol", address[0]);
        String[] url = address[2].split(":");
        current.put("url", url[0]);
        if(url.length>1)
            current.put("port", url[1]);
        current.put("appPath", address[3]);
        current.put("page", address[address.length - 1]);

        if (address.length == 7) {
            current.put("label", address[4]);
            current.put("section", address[5]);
        }
    }
    
    @Override
    public void setMyEnum(SeleniumEnums anEnum) {
        this.myEnum = new SeleniumEnumWrapper(anEnum);
    }
    
    @Override
    public Boolean validateElementsPresent(ArrayList<SeleniumEnums> enums) {
        return validateElementsPresent(enums.toArray());
    }

    @Override
    public Boolean validateElementsPresent(Object... enums) {
        return checkElementsPresent(ErrorLevel.FATAL, enums);
    }

    @Override
    public Boolean validatePresence(Boolean present) {
        return validateEquals(present, isPresent());
    }
    
    @Override
    public Boolean validateVisibility(Boolean visible) {
        return validateEquals(visible, isVisible());
    }
    
    @Override
    public void waitForElement(){
        waitForElement(60);
    }
    

    
    @Override
    public void waitForElement(int i){
        getSelenium().waitForElementPresent(myEnum, i);
    }

}
