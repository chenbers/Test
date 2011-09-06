package com.inthinc.pro.automation.selenium;

import java.io.IOException;
import java.io.StringWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.apache.tools.ant.filters.StringInputStream;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverBackedSelenium;
import org.openqa.selenium.WebElement;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import com.google.common.base.Supplier;
import com.inthinc.pro.automation.enums.SeleniumEnumWrapper;
import com.inthinc.pro.automation.utils.AutomationThread;
import com.inthinc.pro.automation.utils.Id;
import com.inthinc.pro.automation.utils.StackToString;
import com.inthinc.pro.automation.utils.MasterTest.ErrorLevel;
import com.inthinc.pro.automation.utils.Xpath;
import com.thoughtworks.selenium.DefaultSelenium;

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
    private final static Logger logger = Logger.getLogger(CoreMethodLib.class);
    private ErrorCatcher errors;
    private SeleniumEnumWrapper myEnum;
    private final WebDriver driver;

    public CoreMethodLib(Supplier<WebDriver> maker, String baseUrl) {
        super(maker, baseUrl);
        errors = new ErrorCatcher(this);
        driver = maker.get();
    }

    public CoreMethodLib(WebDriver baseDriver, String baseUrl) {
        super(baseDriver, baseUrl);
        errors = new ErrorCatcher(this);
        driver = baseDriver;
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
        AutomationThread.pause(2, "click(" + myEnum + ")");
        return this;
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
        // TODO Auto-generated method stub
        return null;
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
        String element = getLocator(myEnum);
        WebElement item = null;
        if (element.startsWith("//")) {
            item = getWrappedDriver().findElement(By.xpath(element));
        } else if (!element.contains("=")) {
            item = getWrappedDriver().findElement(By.id(element));
        } else {
            return this;
        }
        item.findElement(By.xpath("..")).click();

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
    public ErrorCatcher getErrors() {
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
            if (isElementPresent(s)) {
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
        logger.debug(" getText(" + myEnum.toString() + "\n" + myEnum.getLocatorsAsString() + ")");
        String element = getLocator(myEnum);
        
        WebElement parent = getWrappedDriver().findElement(By.xpath(idToXpath(element)));
        String innerHtml = (String) ((JavascriptExecutor) driver).executeScript("return arguments[0].innerHTML;", parent);
        if (innerHtml.contains("spacer.gif")){
            innerHtml = closeImgTags("<div>" + innerHtml + "</div>");
            try{
                return replaceSpacers(innerHtml);
            } catch (Exception e){
                logger.info(StackToString.toString(e));
            }
        }
        return getText(element);
    }
    
    
    private String replaceSpacers(String html) throws ParserConfigurationException, SAXException, IOException{
        StringWriter text = new StringWriter();
        
        Document doc;
        
        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        doc = builder.parse(new StringInputStream(html));
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
        logger.debug(" getValue(" + myEnum.toString() + "\n" + myEnum.getLocatorsAsString() + ")");
        return super.getValue(getLocator(myEnum));
    }

    @Override
    public boolean hasFocus(SeleniumEnumWrapper myEnum) {
        String element = getLocator(myEnum);
        WebElement item = null;
        if (element.startsWith("//")) {
            item = getWrappedDriver().findElement(By.xpath(element));
            focus(element);
        } else if (!element.contains("=")) {
            item = getWrappedDriver().findElement(By.id(element));
            element = "//" + item.getTagName() + "[@id='" + element + "']";
            focus(element);
        } else {
            return false;
        }

        WebElement hasFocus = getWrappedDriver().switchTo().activeElement();

        Point one = item.getLocation();
        Point two = hasFocus.getLocation();
        Boolean same = one.equals(two);
        // Boolean sameElement = hasFocus.equals(item);
        return same;

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

        return this;
    }

    @Override
    public CoreMethodLib selectDhx(SeleniumEnumWrapper myEnum, String option) {
        String element = getLocator(myEnum);
        String xpath = Xpath.start().body().div(Id.id(element)).div(option).toString();
        click(xpath);
        AutomationThread.pause(3, "Pause so the browser has a chance to catch up");
        return this;
    }
    
    @Override
    public String getDHXText(SeleniumEnumWrapper myEnum, String option){
        String element = getLocator(myEnum);
        String xpath = Xpath.start().body().div(Id.id(element)).div(option).toString();
        return getText(xpath);
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
        return this;
    }

    @Override
    public CoreMethodLib typeKeys(SeleniumEnumWrapper myEnum, String value) {
        String element = getLocator(myEnum);
        typeKeys(element, value);
        AutomationThread.pause(5, "Let the portal catch up");
        return this;
    }

    @Override
    public CoreMethodLib uncheck(SeleniumEnumWrapper myEnum) {
        String element = getLocator(myEnum);
        uncheck(element);
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
            AutomationThread.pause(5, "waitForElementPresent: " + element); // 5 seconds
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
        String xpath = select+"/option["+option+"]";
        return getMatches(xpath, matchNumber);
    }
    
    private WebElement getMatches(String xpath, Integer matchNumber){
        waitForElementPresent(xpath, 10);
        return getWrappedDriver().findElements(By.xpath(xpath)).get(matchNumber);
    }

    @Override
    public CoreMethodInterface click(String xpath, String desiredOption, Integer matchNumber) {
        getMatches(xpath, Id.text(desiredOption), matchNumber).click();
        return null;
    }

}
