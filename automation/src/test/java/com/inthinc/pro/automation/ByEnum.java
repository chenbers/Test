package com.inthinc.pro.automation;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.FindsByName;
import org.openqa.selenium.internal.FindsByXPath;

import com.inthinc.pro.automation.SeleniumEnum;

public class ByEnum extends By {

    private By idFinder;
    private By nameFinder;
    private By xpathFinder;
    private By xpathAltFinder;
    private SeleniumEnum theEnum;

    public ByEnum() {
        super();
    }

    public ByEnum(SeleniumEnum theEnum) {
        this.theEnum = theEnum;
        this.idFinder = By.id(theEnum.getID());
        this.nameFinder = By.name(theEnum.getID()); // TODO: jwimmer: ask DT if we anticipate needing/wanting to find by name???
        this.xpathFinder = By.xpath(theEnum.getXpath());
        this.xpathAltFinder = By.xpath(theEnum.getXpath_alt());
    }

    /**
     * @param name
     *            The value of the "name" attribute to search for
     * @return a By which locates elements by the value of the "name" attribute.
     */
    public static By seleniumEnum(final SeleniumEnum enumVal) {
        if (enumVal == null)
            throw new IllegalArgumentException("Cannot find elements when enumVal is null.");

        return new ByEnum(enumVal) {
            @Override
            public String toString() {
                return "ByEnum.seleniumEnum: " + enumVal;
            }
        };
    }

    @Override
    public WebElement findElement(SearchContext context) {
        try {
            return idFinder.findElement(context);
        } catch (NoSuchElementException e1) {
            try {
                return xpathFinder.findElement(context);
            } catch (NoSuchElementException e2) {
                return xpathAltFinder.findElement(context);
            }
        }
    }

    @Override
    public List<WebElement> findElements(SearchContext context) {
        // WebElement element = driver.findElement(By.id(getSelector()));
        // if (element == null)
        // element = driver.findElement(By.name(getSelector());
        // return element;

        List<WebElement> elements = new ArrayList<WebElement>();

        // First: Find by id ...
        elements.addAll(idFinder.findElements(context));
        // Second: Find by name ... skipping name until I verify from DT that name and ID should be different???
        // elements.addAll(nameFinder.findElements(context));
        // Third: Find by xpath
        elements.addAll(xpathFinder.findElements(context));
        // Fourth: find by xpath alt
        elements.addAll(xpathAltFinder.findElements(context));

        return elements;
    }

}
