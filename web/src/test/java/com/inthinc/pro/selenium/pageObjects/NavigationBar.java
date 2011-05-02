package com.inthinc.pro.selenium.pageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.inthinc.pro.automation.elements.Button;
import com.inthinc.pro.automation.elements.SelectBox;
import com.inthinc.pro.automation.elements.Text;
import com.inthinc.pro.automation.elements.TextField;
import com.inthinc.pro.automation.elements.TextLink;
import com.inthinc.pro.selenium.pageEnums.LiveFleetEnum;
import com.inthinc.pro.selenium.pageEnums.NavigationBarEnum;

public abstract class NavigationBar extends Masthead {

    public class NavigationBarTexts extends MastheadTexts{
        public Text liveFleetHeader() {return new Text( LiveFleetEnum.HEADER_BOX_DISPATCH);}
    }
    public class NavigationBarTextFields extends MastheadTextFields{
        public TextField searchBox() {return new TextField(NavigationBarEnum.SEARCH_BOX);}
    }
    public class NavigationBarLinks extends MastheadLinks{
        public TextLink admin() {return new TextLink(NavigationBarEnum.ADMIN);}
        public TextLink hos() {return new TextLink(NavigationBarEnum.HOS);}
        public TextLink liveFleet() {return new TextLink(NavigationBarEnum.LIVE_FLEET);}
        public TextLink notifications() {return new TextLink(NavigationBarEnum.NOTIFICATIONS);}
        public TextLink reports() {return new TextLink(NavigationBarEnum.REPORTS);}
    }
    public class NavigationBarButtons extends MastheadButtons{
        public Button search() {return new Button(NavigationBarEnum.SEARCH_BUTTON);}
    }
    public class NavigationBarSelects extends MastheadSelects{
        public SelectBox searchDrop() {return new SelectBox(NavigationBarEnum.SEARCH_BOX);}
    }

    public NavigationBar dropDown_searchBox_select(String searchType) {
        selenium.select(NavigationBarEnum.SEARCH_DROP_DOWN, searchType);
        String selected = selenium.getSelectedLabel(NavigationBarEnum.SEARCH_DROP_DOWN);
        assertEquals(selected, searchType);
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
        selenium.open(href); //TODO: jwimmer: question for dTanner: are you using selenium.open(href) because selenium.click(...) won't work on hidden elements?
        return this;
    }
}
