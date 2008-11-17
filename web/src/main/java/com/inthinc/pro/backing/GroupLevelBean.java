package com.inthinc.pro.backing;

import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;

import com.inthinc.pro.util.DebugUtil;

public class GroupLevelBean extends BaseBean
{
    private static final Logger logger = Logger.getLogger(TestBean.class);
    private String              testNumber;
    private NavigationBean      navigationBean;

    public GroupLevelBean()
    {

    }

    public String getTestNumber()
    {

        logger.debug("Test Bean - returning testNumber");
        return testNumber;
    }

    public void setTestNumber(String testNumber)
    {
        this.testNumber = testNumber;
    }

    public void initGroupID()
    {
        DebugUtil.dumpRequestParameterMap();

        FacesContext ctx = FacesContext.getCurrentInstance();
        String groupID = (String) ctx.getExternalContext().getRequestParameterMap().get("groupID");
        if (groupID != null)
        {
            logger.debug("initGroupID from request: " + groupID);
            navigationBean.setGroupID(new Integer(groupID));
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
