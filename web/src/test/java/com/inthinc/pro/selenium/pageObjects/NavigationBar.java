package com.inthinc.pro.selenium.pageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.inthinc.pro.selenium.pageEnums.NavigationBarEnum;

public abstract class NavigationBar extends Masthead {

    public NavigationBar link_admin_click() {
        selenium.click(NavigationBarEnum.ADMIN).waitForPageToLoad();
        return this;
    }

    public NavigationBar link_hos_click() {
        selenium.click(NavigationBarEnum.HOS).waitForPageToLoad();
        return this;
    }

    public NavigationBar link_liveFleet_click() {
        selenium.click(NavigationBarEnum.LIVE_FLEET).waitForPageToLoad();
        return this;
    }

    public NavigationBar link_notifications_click() {
        selenium.click(NavigationBarEnum.NOTIFICATIONS).waitForPageToLoad();
        return this;
    }

    public NavigationBar link_reports_click() {
        selenium.click(NavigationBarEnum.REPORTS).waitForPageToLoad();
        return this;
    }

    public NavigationBar text_searchBox_type(String searchString) {
        selenium.type(NavigationBarEnum.SEARCH_BOX, searchString);
        return this;
    }

    public NavigationBar dropDown_searchBox_select(String searchType) {
        selenium.select(NavigationBarEnum.SEARCH_DROP_DOWN, searchType);
        String selected = selenium.getSelectedLabel(NavigationBarEnum.SEARCH_DROP_DOWN);
        assertEquals(selected, searchType);
        return this;
    }

    public NavigationBar button_search_click() {
        selenium.click(NavigationBarEnum.SEARCH_BUTTON);
        return this;
    }

    public NavigationBar link_navTreeFirstMatch_click(String groupNameToMatch) {//TODO: jwimmer: question for DTanner: let's talk through these method names
        return link_navTreeMatchPosition_click(groupNameToMatch, 1);
    }

    public NavigationBar link_navTreeMatchPosition_click(String groupNameToMatch, Integer matchNumber) {//TODO: jwimmer: question for DTanner: let's talk through these method names
        matchNumber--;
        String xpath = "//a[text()='" + groupNameToMatch + "']";
        List<WebElement> elements = webDriver.findElements(By.xpath(xpath));
        String href = elements.get(matchNumber).getAttribute("href");
        selenium.open(href);
        return this;
    }
}
