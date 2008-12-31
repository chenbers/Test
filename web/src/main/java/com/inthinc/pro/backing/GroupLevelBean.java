package com.inthinc.pro.backing;

import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;


public class GroupLevelBean extends BaseBean
{
    private static final Logger logger = Logger.getLogger(TestBean.class);
    private String              ping;
    private NavigationBean      navigationBean;

    public GroupLevelBean()
    {

    }


    public String getPing()
    {
        return ping;
    }


    public void setPing(String ping)
    {
        this.ping = ping;
    }


    public void initGroupID()
    {
//        DebugUtil.dumpRequestParameterMap();

        FacesContext ctx = FacesContext.getCurrentInstance();
        String groupID = (String) ctx.getExternalContext().getRequestParameterMap().get("groupID");
        if (groupID != null)
        {
            logger.debug("initGroupID from request: " + groupID);
            navigationBean.setGroupID(Integer.valueOf(groupID));
        }
        else
        {
            logger.debug("initGroupID from user bean: " + groupID);
            navigationBean.setGroupID(getUser().getPerson().getGroupID());
        }
    }
    
    public NavigationBean getNavigationBean()
    {
        return navigationBean;
    }

    public void setNavigationBean(NavigationBean navigationBean)
    {
        this.navigationBean = navigationBean;
    }


}
