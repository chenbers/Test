package com.inthinc.pro.automation.selenium;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.io.IOUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriverBackedSelenium;
import org.openqa.selenium.WebElement;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import com.inthinc.pro.automation.AutomationPropertiesBean;
import com.inthinc.pro.automation.enums.Browsers;
import com.inthinc.pro.automation.enums.ErrorLevel;
import com.inthinc.pro.automation.enums.SeleniumEnumWrapper;
import com.inthinc.pro.automation.logging.Log;
import com.inthinc.pro.automation.utils.AutoServers;
import com.inthinc.pro.automation.utils.AutomationStringUtil;
import com.inthinc.pro.automation.utils.AutomationThread;
import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.SeleniumException;

/****************************************************************************************
 * Extend the functionality of DefaultSelenium, but add some error handling around it
 * 
 * try{<br />
 * <br />
 * catch(Exception e){<br />
 * errors.Error(NameOfError, e)<br />
 * 
 * @see DefaultSelenium
 * @see ErrorCatcher
 * @author dtanner
 * 
 */
public class CoreMethodLib extends WebDriverBackedSelenium implements CoreMethodInterface {

    public static Integer PAGE_TIMEOUT = 30000;
    private ErrorCatcher errors;
    private SeleniumEnumWrapper myEnum;
    private final Browsers browser;
	int counter = 2;
    
    private static ThreadLocal<CoreMethodInterface> instance = new ThreadLocal<CoreMethodInterface>();
    
    public CoreMethodLib(Browsers browser, AutoServers silo) {
        super(browser.getDriver(), silo.getWebAddress());
        errors = new ErrorCatcher(this);
        this.browser = browser;
        this.setTimeout("999999999");
    }
    
    @Override
    public Browsers getBrowser(){
        return browser;
    }

    @Override
    public CoreMethodLib addSelection(SeleniumEnumWrapper myEnum, String item) {
        String element = getLocator(myEnum);
        addSelection(element, item);
        return this;
    }

    @Override
    public CoreMethodLib check(SeleniumEnumWrapper myEnum) {
        String element = getLocator(myEnum);
        check(element);
        loadPause();
        return this;
    }

    /**
     * @see {@link com.thoughtworks.selenium.DefaultSelenium#click(String)}
     * @param myEnum
     * @param replacement
     * @return
     */
    @Override
    public CoreMethodLib click(SeleniumEnumWrapper myEnum) {
        String element = getClickable(getLocator(myEnum));
        click(element);
        AutomationThread.pause(20, "click(" + myEnum + ")");
        loadPause();
        return this;
    }
    
    private void loadPause(){
        List<WebElement> pauseFor = getWrappedDriver().findElements(By.xpath("//span[contains(@id,'status.start')]"));
        int size=pauseFor.size();
        while(size>0){
            AutomationThread.pause(1, "Waiting for elements to load");
            Iterator<WebElement> itr = pauseFor.iterator();
            while(itr.hasNext()){
                WebElement next = itr.next();
                if(!next.isDisplayed()){
                    itr.remove();
                }
            }
            size=pauseFor.size();
        }
    }

    @Override
    public CoreMethodLib clickAt(SeleniumEnumWrapper myEnum, String coordString) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean compareLocation(String expected) {
        return expected.equals(getLocation());
    }

    @Override
    public CoreMethodLib contextMenu(SeleniumEnumWrapper myEnum) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public CoreMethodLib contextMenuAt(SeleniumEnumWrapper myEnum, String coordString) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public CoreMethodLib doubleClick(SeleniumEnumWrapper myEnum) {
        String element = getClickable(getLocator(myEnum));
        doubleClick(element);
        AutomationThread.pause(10, "double click(" + myEnum + ")");
        loadPause();
        return this;
    }

    @Override
    public CoreMethodLib doubleClickAt(SeleniumEnumWrapper myEnum, String coordString) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public CoreMethodLib fireEvent(SeleniumEnumWrapper myEnum, String eventName) {
        String element = getLocator(myEnum);
        fireEvent(element, eventName);
        return this;
    }

    /**
     * @param myEnum
     * @return
     */
    @Override
    public CoreMethodLib focus(SeleniumEnumWrapper myEnum) {
        By element = getLocator(myEnum.getLocatorsForWebDriver());

        WebElement elementToFocusOn = getWrappedDriver().findElement(element);
        ((JavascriptExecutor) getWrappedDriver()).executeScript("return arguments[0].focus();", elementToFocusOn);
        return this;
    }

    @Deprecated
    @Override
    public String[] getAllWindowIds() {
        return null;
    }

    @Deprecated
    @Override
    public String[] getAllWindowNames() {
        return null;
    }
    
    @Override
    public String getAttribute(SeleniumEnumWrapper myEnum, String attributeToGet){
        String element = getLocator(myEnum);
        if (element.startsWith("//")){
            element += "/@" + attributeToGet;
        }else if (!element.contains("=")){
            element += "@" + attributeToGet;
        }
        return getAttribute(element);
    }


    @Override
    public SeleniumEnumWrapper getEnum() {
        return myEnum;
    }

    @Override
    public ErrorCatcher getErrorCatcher() {
        return errors;
    }


    /**
     * Returns the best locator string to use for this element.
     * 
     * @param myEnum
     * @param replaceName
     * @param replaceNumber
     * @return the best locator string to use for this element, null if none are found in page
     */
    @Override
    public String getLocator(SeleniumEnumWrapper myEnum) {
        this.myEnum = myEnum;
        String last = "";
        for (String s : myEnum.getLocators()) {
            if (isElementPresent(s) && isVisible(s)) {
                return s;
            }
            last = s;
        }
        return last;
    }

    private By getLocator(String locator) {
        if (locator.startsWith("//")) {
            return By.xpath(locator);
        } else if (!locator.contains("=")) {
            return By.id(locator);
        } else {
            return null;
        }
    }

    @Override
    public String getSelectedIndex(SeleniumEnumWrapper myEnum) {
        String element = getLocator(myEnum);
        return getSelectedIndex(element);
    }

    /**
     * @see {@link com.thoughtworks.selenium.DefaultSelenium#getSelectedLabel(String)}
     * @param myEnum
     * @return
     */
    @Override
    public String getSelectedLabel(SeleniumEnumWrapper myEnum) {
        String element = getLocator(myEnum);
        return getSelectedLabel(element);
    }

    /**
     * @see {@link com.thoughtworks.selenium.DefaultSelenium#getTable(String)}
     * @param myEnum
     * @return
     */
    @Override
    public String getTable(SeleniumEnumWrapper myEnum) {
        return getTable(myEnum, null, null);
    }

    /**
     * @see {@link com.thoughtworks.selenium.DefaultSelenium#getTable(String)}
     * @param myEnum
     * @param row
     * @param col
     * @return
     */
    @Override
    public String getTable(SeleniumEnumWrapper myEnum, Integer row, Integer col) {
        StringBuffer element = new StringBuffer(getLocator(myEnum));
        String text = "";

        if (row != null && col != null)
            element.append("." + row + "." + col);
        text = getTable(element.toString());

        return text;
    }

    /**
     * @see {@link com.thoughtworks.selenium.DefaultSelenium#getText(String)}
     * @param myEnum
     * @param replacement
     * @param replacmentNumber
     * @return
     */
    @Override
    public String getText(SeleniumEnumWrapper myEnum) {
        Log.debug(" getText(" + myEnum.toString() + "\n" + myEnum.getLocatorsAsString() + ")");
        String element = getLocator(myEnum);
        
        WebElement parent = getWrappedDriver().findElement(By.xpath(idToXpath(element)));
        String innerHtml = (String) ((JavascriptExecutor) getWrappedDriver()).executeScript("return arguments[0].innerHTML;", parent);
        if (innerHtml.contains("spacer.gif")){
            innerHtml = closeImgTags("<div>" + innerHtml + "</div>");
            try{
                return replaceSpacers(innerHtml);
            } catch (Exception e){
                Log.info(AutomationStringUtil.toString(e));
            }
        }
        String text = getText(element);
        Log.debug("gotText = " + text);
        return text;
    }
    

    @Override
    public String getTextFromElementWithFocus() {
        WebElement activeElement = getActiveElement();
        String text = (String) ((JavascriptExecutor) getWrappedDriver()).executeScript("return arguments[0].value;", activeElement);
        return text;
    }
    
    private WebElement getActiveElement(){
        return (WebElement) ((JavascriptExecutor) getWrappedDriver()).executeScript("return document.activeElement;");
    }
    
    
    private String replaceSpacers(String html) throws ParserConfigurationException, SAXException, IOException{
        StringWriter text = new StringWriter();
        
        Document doc;
        
        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        doc = builder.parse(IOUtils.toInputStream(html));
        Node child = doc.getFirstChild();
        child = child.getFirstChild();
        while (child != null){
            if (child.getNodeName().contains("text")){
                text.write(child.getTextContent());
            } else if (child.getNodeName().contains("img")){
                Element node = (Element) child;
                if (node.getAttribute("src").contains("spacer.gif")){
                    text.write(" ");
                }
            }
            child = child.getNextSibling();
        }
        return text.toString();
    }
    
    private String closeImgTags(String html){
        Pattern pat = Pattern.compile("img.*\\\"\\>");
        Matcher match = pat.matcher(html);
        while (match.find()){
            html = html.substring(0, match.end()) + "</img>" + html.substring(match.end());
        }
        return html;
    }
    
    private String idToXpath(String element){
        if (element.startsWith("//") || element.contains("=")){
            return element;
        } 
        
        WebElement node = getWrappedDriver().findElement(By.id(element));
        String xpath = "//" + node.getTagName() + "[@id='" + element +"']";
        return xpath;
    }


    @Override
    public String getValue(SeleniumEnumWrapper myEnum) {
        Log.debug(" getValue(" + myEnum.toString() + "\n" + myEnum.getLocatorsAsString() + ")");
        return super.getValue(getLocator(myEnum));
    }

    @Override
    public boolean hasFocus(SeleniumEnumWrapper myEnum) {
        String element = getLocator(myEnum);
        WebElement item = getWrappedDriver().findElement(By.xpath(idToXpath(element)));
        WebElement hasFocus = getActiveElement();
        
        return item.equals(hasFocus);
    }

    /**
     * @see {@link com.thoughtworks.selenium.DefaultSelenium#isChecked(String)}
     * @param myEnum
     * @param condition
     * @return
     */
    @Override
    public boolean isChecked(SeleniumEnumWrapper myEnum) {
        String element = getLocator(myEnum);
        return isChecked(element);
    }

    @Override
    public Boolean isClickable(SeleniumEnumWrapper myEnum) {
        String element = getLocator(myEnum);
        WebElement item = getWrappedDriver().findElement(getLocator(element));
        String tag = item.getTagName();
        boolean results = false;
        if (tag.equals("button") || tag.equals("a")) {
            results = true;
        } else if (element.startsWith("//")) {
            results = isElementPresent(element + "/a");
        } else if (!element.contains("=")) {
            results = isElementPresent("//" + tag + "[@id='" + element + "']/a");
        }
        return results;
    }
    
    private String getClickable(String element){
        WebElement item;
        try{
            item = getWrappedDriver().findElement(getLocator(element));
        } catch (NullPointerException e ){
            return element;
        }
        String tag = item.getTagName();
        if (element.startsWith("//")) {
            if (isElementPresent(element + "/a")){
                element += "/a";
            }
        } else if (!element.contains("=")) {
            if (isElementPresent("//" + tag + "[@id='" + element + "']/a")){
                element = "//" + tag + "[@id='" + element + "']/a";
            }
        }
        return element;
    }

    @Override
    public boolean isEditable(SeleniumEnumWrapper myEnum) {
        String element = getLocator(myEnum);
        return isEditable(element);
    }

    /**
     * @see {@link com.thoughtworks.selenium.DefaultSelenium#isElementPresent(String)}
     * @param myEnum
     * @param replacementWord
     * @param replacementNumber
     * @return
     */
    @Override
    public boolean isElementPresent(SeleniumEnumWrapper myEnum) {
        String element = getLocator(myEnum);
        return isElementPresent(element);
    }

    /**
     * @see {@link com.thoughtworks.selenium.DefaultSelenium#isVisible(String)}
     * @param myEnum
     * @param replacementWord
     * @param replacementNumber
     * @return
     */
    @Override
    public boolean isVisible(SeleniumEnumWrapper myEnum) {
        String element = getLocator(myEnum);
        return isVisible(element);
    }

    @Override
    public CoreMethodLib keyDown(SeleniumEnumWrapper myEnum, String keySequence) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public CoreMethodLib keyPress(SeleniumEnumWrapper myEnum, String keySequence) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public CoreMethodLib keyUp(SeleniumEnumWrapper myEnum, String keySequence) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public CoreMethodLib mouseDown(SeleniumEnumWrapper myEnum) {
        String element = getLocator(myEnum);
        mouseDown(element);
        return this;
    }

    @Override
    public CoreMethodLib mouseDownAt(SeleniumEnumWrapper myEnum, String coordString) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public CoreMethodLib mouseDownRight(SeleniumEnumWrapper myEnum) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public CoreMethodLib mouseDownRightAt(SeleniumEnumWrapper myEnum, String coordString) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public CoreMethodLib mouseMove(SeleniumEnumWrapper myEnum) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public CoreMethodLib mouseMoveAt(SeleniumEnumWrapper myEnum, String coordString) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public CoreMethodLib mouseOut(SeleniumEnumWrapper myEnum) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @see {@link com.thoughtworks.selenium.DefaultSelenium#mouseDown(String)}
     * @param myEnum
     */
    @Override
    public CoreMethodLib mouseOver(SeleniumEnumWrapper myEnum) {
        String element = getLocator(myEnum);
        mouseOver(element);
        return this;
    }

    @Override
    public CoreMethodLib mouseUp(SeleniumEnumWrapper myEnum) {
        String element = getLocator(myEnum);
        mouseUp(element);
        return this;
    }

    @Override
    public CoreMethodLib mouseUpAt(SeleniumEnumWrapper myEnum, String coordString) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public CoreMethodLib mouseUpRight(SeleniumEnumWrapper myEnum) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public CoreMethodLib mouseUpRightAt(SeleniumEnumWrapper myEnum, String coordString) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @see {@link com.thoughtworks.selenium.DefaultSelenium#open(String)}
     * @param myEnum
     * @return
     */
    @Override
    public CoreMethodLib open(SeleniumEnumWrapper myEnum) {
        String element = myEnum.getURL();
        open(element);
        return this;
    }

    @Override
    public CoreMethodLib removeAllSelections(SeleniumEnumWrapper myEnum) {
        String element = getLocator(myEnum);
        removeAllSelections(element);
        return this;
    }

    @Override
    public CoreMethodLib removeSelection(SeleniumEnumWrapper myEnum, String item) {
        String element = getLocator(myEnum);
        removeSelection(element, item);
        return this;
    }

    /**
     * @see {@link com.thoughtworks.selenium.DefaultSelenium#select(String, String)}
     * @param myEnum
     * @param label
     * @return
     */
    @Override
    public CoreMethodLib select(SeleniumEnumWrapper myEnum, String label) {
        String element = getLocator(myEnum);

        select(element, label);
        AutomationThread.pause(5, "Pausing so browser has a chance to catch up");
        loadPause();
        return this;
    }

    /**
     * @see {@link com.thoughtworks.selenium.DefaultSelenium#selectWindow(String)}
     * @param ID
     * @return
     */
    @Override
    public CoreMethodLib selectWindowByID(String ID) {
        if (ID.equals("")) {
            ID = "null";
        }
        selectWindow(ID);
        return this;
    }

    /**
     * @see {@link com.thoughtworks.selenium.DefaultSelenium#selectWindow(String)}
     * @param name
     * @return
     */
    @Override
    public CoreMethodLib selectWindowByName(String name) {
        selectWindow("name=" + name);
        return this;
    }

    /**
     * @see {@link com.thoughtworks.selenium.DefaultSelenium#selectWindow(String)}
     * @param title
     * @return
     */
    @Override
    public CoreMethodLib selectWindowByTitle(String title) {
        selectWindow("title=" + title);
        return this;
    }

    @Override
    @Deprecated
    public void start() {
        throw new IllegalArgumentException("This method cannot be called.");
    }

    /**
     * @see {@link com.thoughtworks.selenium.DefaultSelenium#type(String, String)}
     * @param myEnum
     * @param text
     * @return
     */
    @Override
    public CoreMethodLib type(SeleniumEnumWrapper myEnum, String text) {
        String element = getLocator(myEnum);

        fireEvent(element, "keydown");
        type(element, text);
        fireEvent(element, "keyup");
        fireEvent(element, "blur");
        AutomationThread.pause(20, "Give the page a second to catch up if it has some refreshing to do");
        loadPause();
        return this;
    }

    @Override
    public CoreMethodLib typeKeys(SeleniumEnumWrapper myEnum, String value) {
        String element = getLocator(myEnum);
        typeKeys(element, value);
        AutomationThread.pause(5, "Let the portal catch up");
        loadPause();
        return this;
    }

    @Override
    public CoreMethodLib uncheck(SeleniumEnumWrapper myEnum) {
        String element = getLocator(myEnum);
        uncheck(element);
        loadPause();
        return this;
    }

    @Override
    public boolean verifyLocation(SeleniumEnumWrapper myEnum) {
        return myEnum.getURL().equals(getLocator(myEnum));
    }

    @Override
    public CoreMethodLib waitForElementPresent(Object element, Integer secondsToWait) {
        Integer x = 0;
        boolean found = false;
        boolean doneWaiting = false;
        while (!found && !doneWaiting) {
            boolean foundByString = ((element instanceof String) && (isElementPresent((String) element)));
            boolean foundByEnum = ((element instanceof SeleniumEnumWrapper) && (isElementPresent((SeleniumEnumWrapper) element)));
            found = foundByString || foundByEnum;
            AutomationThread.pause(5, "waitForElementPresent: " + element); //5 seconds
            x++;
            doneWaiting = x > secondsToWait;
        }
        if (!found)
            errors.addError("waitForElementPresent TIMEOUT", "while waiting for " + element, ErrorLevel.WARN);
        return this;
    }

    /**
     * @see {@link com.thoughtworks.selenium.DefaultSelenium#waitForPageToLoad(String)}
     * @return
     */
    @Override
    public CoreMethodLib waitForPageToLoad() {
        waitForPageToLoad(PAGE_TIMEOUT);
        return this;
    }

    /**
     * @see {@link com.thoughtworks.selenium.DefaultSelenium#waitForPageToLoad(String)}
     * @param timeout
     * @return
     */
    @Override
    public CoreMethodLib waitForPageToLoad(Integer timeout) {
        waitForPageToLoad(timeout.toString());
        return this;
    }

    @Override
    public String[] getSelectOptions(SeleniumEnumWrapper myEnum) {
        String element = getLocator(myEnum);
        return getSelectOptions(element);
    }

    @Override
    public CoreMethodInterface click(String xpath, Integer matchNumber) {
        getMatches(xpath, matchNumber).click();
        AutomationThread.pause(3, "Wait for elements to refresh");
        return this;
    }
    
    private WebElement getMatches(String select, String option, Integer matchNumber){
    	//MWEISS - I've added this catFilter method to handle the nested drop downs in the Notifications section
    	if (select.contains("catFilter")) {
    		counter = 2;
        	String xpath = select+"/optgroup[2]/option["+option+"]";
            return selectNested(select, xpath, option, matchNumber);
    	}
    	
    	else {
    		String xpath = select+"/option["+option+"]";
            return getMatches(xpath, matchNumber);
    	}

    }
    
    private WebElement getMatches(String xpath, Integer matchNumber){
        waitForElementPresent(xpath, 10);
        List<WebElement> allMatches= getWrappedDriver().findElements(By.xpath(xpath));
        if(matchNumber < allMatches.size())
            return allMatches.get(matchNumber);
        	

        errors.addError("getMatches insufficient matches found", "There is no matchNumber at index "+matchNumber+", there were only "+allMatches.size()+" matches found", ErrorLevel.WARN);
        throw new SeleniumException("There is no matchNumber at index "+matchNumber+", there were only "+allMatches.size()+" matches found");
    }
    
    private WebElement selectNested(String select, String xpath, String option, Integer matchNumber) {
        waitForElementPresent(xpath, 15);
        List<WebElement> allMatches= getWrappedDriver().findElements(By.xpath(xpath));
        if(matchNumber < allMatches.size()) {
            AutomationThread.pause(10, "Pausing so browser has a chance to catch up");
        	return allMatches.get(matchNumber);
        }
        	while (counter < 14) {
            	counter++;        		
            	xpath = select+"/optgroup["+counter+"]/option["+option+"]";
            	return selectNested(select, xpath, option, matchNumber);
    		}

        errors.addError("getMatches insufficient matches found", "There is no matchNumber at index "+matchNumber+", there were only "+allMatches.size()+" matches found", ErrorLevel.WARN);
        throw new SeleniumException("There is no matchNumber at index "+matchNumber+", there were only "+allMatches.size()+" matches found");	

    }

    @Override
    public CoreMethodInterface click(String xpath, String desiredOption, Integer matchNumber) {
        getMatches(xpath, desiredOption, matchNumber).click();
        return this;
    }

    @Override
    public CoreMethodInterface tabKey() {
        getActiveElement().sendKeys(Keys.TAB);
        waitForPageToLoad();
        return this;
    }

    @Override
    public CoreMethodInterface enterKey() {
        findElement(myEnum).sendKeys(Keys.ENTER);
    	//getActiveElement().sendKeys(Keys.ENTER);
        waitForPageToLoad();
        return this;
    }
    
    @Override
    public CoreMethodInterface spacebarKey() {
    	getActiveElement().sendKeys(Keys.SPACE);
        waitForPageToLoad();
        return this;
    }
    
    @Override
    public CoreMethodInterface periodKey() {
        getActiveElement().sendKeys(Keys.DECIMAL);
        waitForPageToLoad();
        return this;
    }   
    
    public static CoreMethodInterface getSeleniumThread() {
        CoreMethodInterface selenium = instance.get();
        //System.out.println("getSeleniumThread() currentThread: "+currentThread);
        if (selenium != null){
            return selenium;
        }
        
        AutomationPropertiesBean apb = AutomationPropertiesBean.getPropertyBean();
        selenium = new CoreMethodLib(Browsers.getBrowserByName(apb.getBrowserName()), new AutoServers());
        instance.set(selenium.getErrorCatcher().newInstanceOfSelenium());
        return instance.get();
    }

    public static void closeSeleniumThread() {
        try{
            instance.get().close();
            instance.get().stop();
        }catch(NullPointerException e){
            Log.info("Selenium already closed.");
        }catch(Exception e){
            if(!e.getMessage().contains("session") && !e.getMessage().contains("does not exist"))
                Log.info(e);
        }
        instance.set(null);
    }
    
    
    /**
     * Returns the best locator string to use for this element.
     * 
     * @param myEnum
     * @param replaceName
     * @param replaceNumber
     * @return the best locator string to use for this element, null if none are found in page
     */
    @Override
    public By getLocator(List<By> list) {
        for (By by: list){
            try {
                getWrappedDriver().findElement(by);
                return by;
            } catch (Exception e) {
                continue;
            }
        }
        
        return list.get(0);
    }

    @Override
    public List<WebElement> findElements(SeleniumEnumWrapper myEnum) {
        By by = getLocator(myEnum.getLocatorsForWebDriver());
        return getWrappedDriver().findElements(by);
    }

    @Override
    public WebElement findElement(SeleniumEnumWrapper myEnum) {
        By by = getLocator(myEnum.getLocatorsForWebDriver());
        return getWrappedDriver().findElement(by);
    }

	@Override
	public CoreMethodInterface hoverOver(SeleniumEnumWrapper myEnum) {
		String mouseOverScript = "if(document.createEvent){var evObj = document.createEvent('MouseEvents');evObj.initEvent('mouseover', true, false); arguments[0].dispatchEvent(evObj);} else if(document.createEventObject) { arguments[0].fireEvent('onmouseover');}";
		JavascriptExecutor js = (JavascriptExecutor) getWrappedDriver();
        By by = getLocator(myEnum.getLocatorsForWebDriver());
		WebElement someElem = getWrappedDriver().findElement(by); //replace with your own WebElement call/code here
		Object results = js.executeScript(mouseOverScript, someElem);
		Log.info(results);
		return this;
	}
}
