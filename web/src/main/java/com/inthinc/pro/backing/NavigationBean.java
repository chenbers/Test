package com.inthinc.pro.backing;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.apache.log4j.Logger;

import com.inthinc.pro.model.Driver;
import com.inthinc.pro.wrapper.ScoreableEntityPkg;

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
        this.groupID = groupID;
    }

    public void listener(ActionEvent evt)
    {
        FacesContext ctx = FacesContext.getCurrentInstance();
        String groupID = (String) ctx.getExternalContext().getRequestParameterMap().get("selectedGroup");

        logger.debug("#############selected groupID is: " + groupID);
        this.groupID = new Integer(groupID);
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
