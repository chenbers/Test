package com.inthinc.pro.backing;

import com.inthinc.pro.dao.GroupDAO;
import com.inthinc.pro.model.Duration;
import com.inthinc.pro.model.Group;
public class DashBoardDetailBean extends BaseBean
{
    public enum TabType
    {
        TREND, OVERALL, MAP, MPG
    }

    private Integer groupID;
    private Group group;
    private TabType tabType;
    private Duration duration;
    
    private MpgBean mpgBean;
    private DashBoardBean dashBoardBean;
    private OverallScoreBean overallScoreBean;
    private NavigationBean navigationBean;
    
    private GroupDAO groupDAO;

    public Integer getGroupID()
    {
        return groupID;
    }

    public void setGroupID(Integer groupID)
    {
        this.groupID = groupID;
        group = getGroupHierarchy().getGroup(groupID);
    }

    public Group getGroup()
    {
        return group;
    }

    public void setGroup(Group group)
    {
        this.group = group;
    }

    public TabType getTabType()
    {
        return tabType;
    }

    public void setTabType(TabType tabType)
    {
        this.tabType = tabType;
    }

    public Duration getDuration()
    {
        return duration;
    }

    public void setDuration(Duration duration)
    {
        this.duration = duration;
    }

    public MpgBean getMpgBean()
    {
        return mpgBean;
    }

    public void setMpgBean(MpgBean mpgBean)
    {
        this.mpgBean = mpgBean;
    }
    
    public DashBoardBean getDashBoardBean()
    {
        return dashBoardBean;
    }

    public void setDashBoardBean(DashBoardBean dashBoardBean)
    {
        this.dashBoardBean = dashBoardBean;
    }

    public OverallScoreBean getOverallScoreBean()
    {
        return overallScoreBean;
    }

    public void setOverallScoreBean(OverallScoreBean overallScoreBean)
    {
        this.overallScoreBean = overallScoreBean;
    }

    public NavigationBean getNavigationBean()
    {
        return navigationBean;
    }

    public void setNavigationBean(NavigationBean navigationBean)
    {
        this.navigationBean = navigationBean;
    }

    public GroupDAO getGroupDAO()
    {
        return groupDAO;
    }

    public void setGroupDAO(GroupDAO groupDAO)
    {
        this.groupDAO = groupDAO;
    }

    public void initAction()
    {        
        mpgBean.setGroupID(groupID);
        dashBoardBean.setGroupID(groupID);
        overallScoreBean.setGroupID(groupID);
        navigationBean.setGroupID(groupID);
    }
}
