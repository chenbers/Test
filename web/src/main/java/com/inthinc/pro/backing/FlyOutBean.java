package com.inthinc.pro.backing;

import org.apache.log4j.Logger;

public class FlyOutBean 
{
    private String navigationRule;
    private String returnNavRule;
    private String selectedTab;
    private Integer groupID;
    
    public Integer getGroupID()
    {
        return groupID;
    }

    public void setGroupID(Integer groupID)
    {
        this.groupID = groupID;
    }
    private static final Logger logger = Logger.getLogger(DriverSeatBeltBean.class);
    
    //FLY OUT-IN  ACTION
    public String flyNavRuleAction()
    {
        logger.debug("ACTION: " + returnNavRule);
        return this.navigationRule;
    }
    
    public String returnNavRuleAction()
    {
        logger.debug("ACTION: " + returnNavRule);
        return returnNavRule;
        
    }
    
    public String getNavigationRule()
    {
        return navigationRule;
    }
    public void setNavigationRule(String navigationRule)
    {
        logger.debug("SETTING: " + navigationRule);
        this.navigationRule = navigationRule;
    }
    
    //SELECTED TAB PROPERTIES
    public String getSelectedTab()
    {
        return selectedTab;
    }
    public void setSelectedTab(String selectedTab)
    {
        this.selectedTab = selectedTab;
    }

    //RETURN NAV RULE PROPERTIES
    public String getReturnNavRule()
    {
        return returnNavRule;
    }
    public void setReturnNavRule(String returnNavRule)
    {
        logger.debug("SETTING: " + returnNavRule);
        this.returnNavRule = returnNavRule;
    }
        
}
