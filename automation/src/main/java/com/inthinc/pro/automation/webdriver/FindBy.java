package com.inthinc.pro.automation.webdriver;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

import com.inthinc.pro.automation.enums.SeleniumEnums;


public class FindBy extends By {

    @Override
    public List<WebElement> findElements(SearchContext context) {
        return null;
    }
    
    public static FindBy id(SeleniumEnums enumID){
        return (FindBy) id(enumID.getID());
    }
    
    public static FindBy xpath(SeleniumEnums enumXpath){
        if (enumXpath.getXpath()!=null){
            return (FindBy) xpath(enumXpath.getXpath());
        }else{
            return (FindBy) xpath(enumXpath.getXpath_alt());
        }
    }
    

}
