package com.inthinc.pro.automation.elements;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import com.inthinc.pro.automation.enums.AutomationEnum;
import com.inthinc.pro.automation.enums.SeleniumEnums;
import com.inthinc.pro.automation.enums.TextEnum;
import com.inthinc.pro.automation.selenium.CoreMethodLib;
import com.inthinc.pro.automation.utils.MasterTest;

public class ElementBase extends MasterTest implements ElementInterface {
	protected final static String parentXpath = "/..";
	
    protected final static Logger logger = Logger.getLogger(ElementBase.class);
    protected CoreMethodLib selenium;
    protected WebDriver webDriver;

    protected AutomationEnum myEnum;
    protected String text;
    
    protected HashMap<String, String> current;
    
    public ElementBase(){
        this(AutomationEnum.PLACE_HOLDER, null, null);
    }

    public ElementBase(SeleniumEnums anEnum) {
        this(anEnum, null, null);
    }

    public ElementBase(SeleniumEnums anEnum, String replaceWord) {
        this(anEnum, replaceWord, null);
    }

    public ElementBase(SeleniumEnums anEnum, TextEnum replaceWord){
    	this(anEnum, replaceWord.getText(), null);
    }
    
    public ElementBase(SeleniumEnums anEnum, Integer replaceNumber) {
        this(anEnum, null, replaceNumber);
    }

    public ElementBase(SeleniumEnums anEnum, String replaceWord, Integer replaceNumber) {
        this.text = anEnum.getText();
        setMyEnum(anEnum);
        if (replaceNumber!=null){
            myEnum.replaceNumber(replaceNumber.toString());
        }
        if (replaceWord!=null){
            myEnum.replaceWord(replaceWord);
        }
        selenium = super.getSelenium();
        webDriver = selenium.getWrappedDriver();
    }
    @Override
    public boolean isPresent() {
        return selenium.isElementPresent(myEnum);
    }
    @Override
    public boolean isVisible() {
        return selenium.isVisible(myEnum);
    }
    
    public boolean assertVisibility(Boolean visible) {  
        return assertTrue(visible == selenium.isVisible(myEnum), myEnum.toString());
    }

    
    protected Boolean isElementPresent(){
        return selenium.isElementPresent(myEnum);
    }

    @Override
    public ElementInterface focus() {
        selenium.focus(myEnum);
        return this;
    }

    /**
     * Will only work if the Enumerated Value<br />
     * has text for a default!!!<br />
     * Since this is not always the case<br />
     * use at your own risk of false positives.
     * 
     */
	public void assertEquals() {
		assertEquals(myEnum);
	}


    public SeleniumEnums getMyEnum() {
        return myEnum;
    }

    public void setMyEnum(SeleniumEnums anEnum) {
        this.myEnum = AutomationEnum.PLACE_HOLDER.setEnum(anEnum);
    }

    
    protected void setCurrentLocation(){
        String uri = getCurrentLocation();
        logger.debug(uri);
        String[] address = uri.split("/");
        current = new HashMap<String, String>();
        current.put("protocol", address[0]);
        String[] url = address[2].split(":");
        current.put("url", url[0]);
        current.put("port", url[1]);
        current.put("appPath", address[3]);
        current.put("page", address[address.length-1]);
        
        if (address.length == 7){
            current.put("label", address[4]);
            current.put("section", address[5]);
        }
    }
    
    public String getCurrentLocation() {
        return selenium.getLocation();
    }
    
    public Boolean validateElementsPresent(SeleniumEnums ...enums){
    	for (SeleniumEnums enumerated: enums){
            setMyEnum(enumerated);
            assertTrue(isElementPresent(), myEnum.toString());
        }
    	return true;
    }
    
    public Boolean validateElementsPresent(ArrayList<SeleniumEnums> enums){
    	return validateElementsPresent((SeleniumEnums[]) enums.toArray());
    }
    
    public void validateTextMatches(SeleniumEnums ...enums){
        for (SeleniumEnums enumerated: enums){
            assertEquals(AutomationEnum.CORE_ONLY.setEnum(enumerated));
        }
    }
    

    
    protected ElementBase replaceNumber(Integer number){
    	myEnum.replaceNumber(number.toString());
    	return this;
    }
    
    protected ElementBase replaceWord(String word){
    	myEnum.replaceWord(word);
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
    public ElementBase replaceSubStringInMyEnum(String replaceOld, String withNew){
    	myEnum.replaceOldWithNew(replaceOld, withNew);
    	return this;
    }
}
