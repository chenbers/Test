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
        //DebugUtil.dumpRequestParameterMap();

        //Initially the site was set up so that if no groupID was sent in the request, then the group would be
        // set to the top level group. This is how I believe it should be, but there are many requests that are not including the groupID.
        // Because of this, this bean was changed to leave the group alone if the groupID is not passed... A quick fix is included to set the group to the
        // top level if it is the home page. This needs to be changed in the future.
        FacesContext ctx = FacesContext.getCurrentInstance();
        String groupID = (String) ctx.getExternalContext().getRequestParameterMap().get("groupID");
       
        if (groupID != null)
        {
            logger.debug("initGroupID from request: " + groupID);
            navigationBean.setGroupID(Integer.valueOf(groupID));
        }
        else if(navigationBean.getGroupID() == null)
        {
            logger.debug("initGroupID from user bean: " + groupID);
            navigationBean.setGroupID(getUser().getGroupID());
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
