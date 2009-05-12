package com.inthinc.pro.backing;

import com.inthinc.pro.dao.GroupDAO;
import com.inthinc.pro.model.Group;
public class DashBoardBean extends BaseBean
{
    private NavigationBean navigationBean;
    private Integer groupID;
    private GroupDAO groupDAO;

    public String getViewPath()
    {
        if(groupID == null)
            groupID = getUser().getGroupID();
        
        Group group = groupDAO.findByID(groupID);
        
        switch (group.getType()) {
        case FLEET:
            navigationBean.setGroupID(groupID);
            return "pretty:fleet";
        case DIVISION:
            navigationBean.setGroupID(groupID);
            return "pretty:division";
        case TEAM:
            navigationBean.setGroupID(groupID);
            return "pretty:team";
        }
        
        return null;
    }

    public NavigationBean getNavigationBean()
    {
        return navigationBean;
    }

    public void setNavigationBean(NavigationBean navigationBean)
    {
        this.navigationBean = navigationBean;
    }

    public Integer getGroupID()
    {
        return groupID;
    }

    public void setGroupID(Integer groupID)
    {
        this.groupID = groupID;
    }

    public GroupDAO getGroupDAO()
    {
        return groupDAO;
    }

    public void setGroupDAO(GroupDAO groupDAO)
    {
        this.groupDAO = groupDAO;
    }
}
