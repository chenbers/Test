package com.inthinc.pro.backing;

import java.io.Serializable;

import com.inthinc.pro.dao.GroupDAO;
import com.inthinc.pro.model.Group;
public class DashBoardBean extends BaseBean implements Serializable
{
    /**
     * 
     */
    private static final long serialVersionUID = -9102890511072356183L;
    
    private NavigationBean navigationBean;
    private MpgBean mpgBean;
    private OverallScoreBean overallScoreBean;
    private Integer groupID;
    private GroupDAO groupDAO;

    public String getViewPath()
    {
        if(groupID == null)
            groupID = getUser().getGroupID();
        //TODO: try to pull the group from the group hierarchy before looking it up
        Group group = groupDAO.findByID(groupID);

        navigationBean.setGroupID(groupID);
        mpgBean.setGroupID(groupID);
        overallScoreBean.setGroupID(groupID);
        
        switch (group.getType()) {
        case FLEET:
            return "pretty:fleet";
        case DIVISION:
            return "pretty:division";
        case TEAM:
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

    public MpgBean getMpgBean()
    {
        return mpgBean;
    }

    public void setMpgBean(MpgBean mpgBean)
    {
        this.mpgBean = mpgBean;
    }

    public OverallScoreBean getOverallScoreBean()
    {
        return overallScoreBean;
    }

    public void setOverallScoreBean(OverallScoreBean overallScoreBean)
    {
        this.overallScoreBean = overallScoreBean;
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
