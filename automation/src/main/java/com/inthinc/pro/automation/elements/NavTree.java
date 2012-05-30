package com.inthinc.pro.automation.elements;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.inthinc.pro.automation.elements.ElementInterface.NavigationTree;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;

public class NavTree extends TextLink implements NavigationTree {
	
    public NavTree(SeleniumEnums anEnum, Object ...objects) {
        super(anEnum, objects);
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
        throw new IllegalAccessError("Click doesn't work with a NavTree element");
    }
    
    public NavTree clickGroup(String groupNameToMatch) {
        return clickThe(groupNameToMatch, 1);
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
    public NavTree clickThe(String groupNameToMatch, Integer matchNumber){
        matchNumber--;
        String xpath = "//a[text()='" + groupNameToMatch + "']";
        List<WebElement> elements = getSelenium().getWrappedDriver().findElements(By.xpath(xpath));
        String href = elements.get(matchNumber).getAttribute("href");//.replace("/tiwipro", "");//TODO: jwimmer: provide method to save the String higher up
        getSelenium().open(href);
        return this;
    }
}
