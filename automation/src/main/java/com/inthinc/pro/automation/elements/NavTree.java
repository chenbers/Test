package com.inthinc.pro.automation.elements;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.inthinc.pro.automation.elements.ElementInterface.Clickable;
import com.inthinc.pro.automation.elements.ElementInterface.TextBased;
import com.inthinc.pro.automation.enums.SeleniumEnums;

public class NavTree extends TextLink implements Clickable, TextBased {
    public NavTree(SeleniumEnums anEnum) {
        super(anEnum);
    }
    
    @Override
    @Deprecated
    /**
     * Don't use click(), the Nav tree<br />
     * doesn't play well with selenium.<br />
     * Use click(groupNameToMatch, matchNumber)<br />
     * or click(groupName) instead.
     * 
     */
    public NavTree click(){
        return this;
    }
    
    public NavTree click(String groupNameToMatch) {
        return click(groupNameToMatch, 1);
    }
    
    /**
     * If there are multiple matches then <br />
     * this will let you pick which one you<br />
     * want in order.<br />
     * <br />
     * WARNING: This order may change.
     * 
     * @param groupNameToMatch
     * @param matchNumber index position +1
     * @return
     */
    public NavTree click(String groupNameToMatch, Integer matchNumber){
        matchNumber--;
        String xpath = "//a[text()='" + groupNameToMatch + "']";
        List<WebElement> elements = webDriver.findElements(By.xpath(xpath));
        String href = elements.get(matchNumber).getAttribute("href").replace("/tiwipro", "");//TODO: jwimmer: provide method to save the String higher up
        selenium.open(href);
        return this;
    }
}
