package com.inthinc.pro.automation.enums;

import java.util.ArrayList;
import java.util.List;

public class SeleniumEnumUtil {
    /**
     * Returns a List of Strings representing the non-null locators for anEnum
     * @param anEnum
     * @return non-null element locator strings
     */
    public static List<String> getLocators(SeleniumEnums anEnum) {
        ArrayList<String> locators = new ArrayList<String>();
        if(anEnum.getID() != null)
            locators.add(anEnum.getID());
        if(anEnum.getXpath()!= null)
            locators.add(anEnum.getXpath());
        if(anEnum.getXpath_alt() != null)
            locators.add(anEnum.getXpath_alt());
        return locators;
    }
}
