package com.inthinc.pro.backing;

import org.apache.log4j.Logger;

import com.inthinc.pro.model.Driver;

public class NavigationBean extends BaseBean
{
    private static final Logger logger   = Logger.getLogger(NavigationBean.class);

    private Integer             groupID;
    private Driver              driver;

    public NavigationBean()
    {

    }

    public Integer getGroupID()
    {
        return groupID;
    }

    public void setGroupID(Integer groupID)
    {
        logger.debug("Navigation setGroupID: " + groupID);
        this.groupID = groupID;
    }


    public Driver getDriver()
    {
        return driver;
    }

    public void setDriver(Driver driver)
    {
        this.driver = driver;
    }
}
