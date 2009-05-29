package com.inthinc.pro.backing;

import com.inthinc.pro.dao.GroupDAO;
import com.inthinc.pro.model.Duration;
public class DashBoardDetailBean extends BaseBean
{
    public enum TabType
    {
        TREND, OVERALL, MAP, MPG
    }

    private Integer groupID;
    private TabType tabType;
    private Duration duration;
    
    private MpgBean mpgBean;
    private OverallScoreBean overallScoreBean;
    
    private GroupDAO groupDAO;

    public Integer getGroupID()
    {
        return groupID;
    }

    public void setGroupID(Integer groupID)
    {
        this.groupID = groupID;
//        mpgBean.setGroupID(groupID);
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
    
    public OverallScoreBean getOverallScoreBean()
    {
        return overallScoreBean;
    }

    public void setOverallScoreBean(OverallScoreBean overallScoreBean)
    {
        this.overallScoreBean = overallScoreBean;
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
        overallScoreBean.setGroupID(groupID);
    }
}
