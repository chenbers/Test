package com.inthinc.pro.automation.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.inthinc.pro.automation.enums.SeleniumEnums;
import com.inthinc.pro.automation.enums.SeleniumValueEnums;


public abstract class AbstractPage implements VerbosePage {
    protected static CoreMethodLib selenium;
    protected static WebDriver webDriver;
    protected String currentPage;

    public AbstractPage() {
        selenium = GlobalSelenium.getSelenium();
        webDriver = selenium.getWrappedDriver();
    }

    public static void addError(String errorName) {
        selenium.getErrors().addError(errorName, Thread.currentThread().getStackTrace());
    }

    public static void addError(String errorName, String error) {
        selenium.getErrors().addError(errorName, error);
    }

    public static void addErrorWithExpected(String errorName, String error, String expected) {
        selenium.getErrors().addError(errorName, error);
        selenium.getErrors().addExpected(errorName, expected);
    }

    public static void assertEquals(Object actual, Object expected) {
        if (!actual.equals(expected)) {
            addError(actual + " != " + expected);
        }
    }

    public static void assertEquals(Object expected, SeleniumEnums actual) {
        assertEquals(expected, actual.getText());
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inthinc.pro.web.selenium.Page#load()
     */
    public AbstractPage page_directURL_load() {
        return (AbstractPage) load();
    }
    
    public static void assertEquals(SeleniumEnums anEnum) {
        assertEquals(selenium.getText(anEnum), anEnum.getText());
    }
    
//    /*
//     * (non-Javadoc)
//     * 
//     * @see com.inthinc.pro.web.selenium.Page#page_loginLoad()
//     */
//    public AbstractPage page_directURL_loginThenLoad() {
//        return (AbstractPage) loginLoad()
//    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inthinc.pro.web.selenium.Page#validateURL()
     */
    public AbstractPage page_URL_validate() {
        return validateURL();
    }
    
    public static void assertNotEquals(Object actual, Object expected) {
        if (actual.equals(expected)) {
            addError(actual + " == " + expected);
        }
    }

    public static void assertNotEquals(Object expected, SeleniumEnums actual) {
        assertNotEquals(expected, actual.getText());
    }
    
    public static void assertContains(String fullString, String partialString){
        if(!fullString.contains(partialString)){
            addError(partialString + " not in " + fullString);
        }
    }


    public String browser_location_getCurrent() {
        String[] url = webDriver.getCurrentUrl().split("/");//TODO: jwimmer: seems like this doesn't capture ALL of the pertinent location info for some pages? i.e. https://my.inthinc.com/tiwipro/app/driver/214
        return url[url.length-1];
    }


    protected void clickNewWindowLink(SeleniumEnums link, SeleniumEnums text){
        selenium.click(link);
        String[] handles = webDriver.getWindowHandles().toArray(new String[2]);
        webDriver.switchTo().window(handles[handles.length-1]);
        selenium.getText(text);
        selenium.close();
        webDriver.switchTo().window(handles[0]);
    }

    public ErrorCatcher get_errors() {
        return selenium.getErrors();
    }
    
    @Override
    public String getCurrentLocation() {
        return selenium.getLocation();
    }
    
    @Override
    public String getExpectedPath() {
        // TODO Auto-generated method stub
        return null;
    }

    public CoreMethodLib getSelenium() {
        return selenium;
    }
    
    protected String setCurrentLocation(){
        String[] address = getCurrentLocation().split("/"); //TODO: jwimmer: doesn't seem very robust on pages where MORE than the last portion of the URL is significant?  
        currentPage = address[address.length-1];
        return currentPage;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inthinc.pro.web.selenium.Page#validateURL()
     */
    public AbstractPage validateURL() {
        boolean results = getCurrentLocation().contains(getExpectedPath());
        // System.out.println("about to return: "+results);
        return this;
    }
    
    @Override
    public Page load() {
        selenium.open(this.getExpectedPath());
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inthinc.pro.web.selenium.Page#page_getExpectedPath()
     */
    public String page_path_getExpected() {
        return getExpectedPath();
    }

    public AbstractPage page_bareMinimum_validate() {
        return validate();
    }

    @Override
    public AbstractPage validate() {
        // TODO Auto-generated method stub
        return this;
    }
    
    protected void selectPartialMatch(String partial, SeleniumEnums selector){
        String xpath="";
        if (selector.getID()!=null){
            String id = selector.getID();
            xpath = "//select[@id='"+id+"']/option[contains(text(),'"+partial+"')]";
        }else {
            xpath = selector.getXpath() + "/option[contains(text(),'"+partial+"')]";
        }
        webDriver.findElement(By.xpath(xpath)).setSelected();
    }
    
    protected void selectOption(String selection, SeleniumEnums selector){
        selenium.select(selector, selection);
        String selected = selenium.getSelectedLabel(selector);
        assertEquals(selection, selected);
    }
    
    protected void selectValue(SeleniumValueEnums selection, SeleniumEnums selector){
        selenium.select(selector, "index=" + selection.getValue());
        String selected = selenium.getSelectedLabel(selector);
        assertEquals(getTextValue(selection), selected);
    }
    

    protected String getTextValue(SeleniumValueEnums selection) {
        String textValue = selenium.getText(selection.getID());
        if (textValue.isEmpty()) {
            return selection.getPrefix().getText().replace(":", "");
        } else {
            return selection.getPrefix().getText() + selenium.getText(selection.getID());
        }
    }
}
