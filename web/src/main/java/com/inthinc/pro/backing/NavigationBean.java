package com.inthinc.pro.backing;

import org.apache.log4j.Logger;

import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Duration;

public class NavigationBean extends BaseBean
{
    private static final Logger logger   = Logger.getLogger(NavigationBean.class);

    private Integer             groupID;
    private Driver              driver;
    private Duration            duration = Duration.DAYS;
    private Integer             start = 0;
    private Integer             end = 0;

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

    public Duration getDuration()
    {
        return duration;
    }

    public void setDuration(Duration duration)
    {
        this.duration = duration;
    }

    public Integer getStart()
    {
        return start;
    }

    public void setStart(Integer start)
    {
        this.start = start;
    }

    public Integer getEnd()
    {
        return end;
    }

    public void setEnd(Integer end)
    {
        this.end = end;
    }

}
