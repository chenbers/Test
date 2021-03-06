package com.inthinc.pro.backing;

import org.ajax4jsf.model.KeepAlive;
import org.springframework.security.AccessDeniedException;

import com.inthinc.pro.dao.GroupDAO;
import com.inthinc.pro.model.Duration;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.util.MessageUtil;

@KeepAlive
public class DashBoardDetailBean extends BaseBean {
    public enum TabType {
        TREND,
        OVERALL,
        MAP,
        MPG
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
    private TrendBean trendBean;

    public Integer getGroupID() {
        return groupID;
    }

    public void setGroupID(Integer groupID) {
        this.groupID = groupID;
        group = getGroupHierarchy().getGroup(groupID);
        if (group == null)
            throw new AccessDeniedException(MessageUtil.getMessageString("exception_accessDenied", getLocale()));
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public TabType getTabType() {
        if (tabType == null) {
            tabType = TabType.OVERALL;
        }
        return tabType;
    }

    public void setTabType(TabType tabType) {
        this.tabType = tabType;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public MpgBean getMpgBean() {
        return mpgBean;
    }

    public void setMpgBean(MpgBean mpgBean) {
        this.mpgBean = mpgBean;
    }

    public DashBoardBean getDashBoardBean() {
        return dashBoardBean;
    }

    public void setDashBoardBean(DashBoardBean dashBoardBean) {
        this.dashBoardBean = dashBoardBean;
    }

    public OverallScoreBean getOverallScoreBean() {
        return overallScoreBean;
    }

    public void setOverallScoreBean(OverallScoreBean overallScoreBean) {
        this.overallScoreBean = overallScoreBean;
    }

    public NavigationBean getNavigationBean() {
        return navigationBean;
    }

    public void setNavigationBean(NavigationBean navigationBean) {
        this.navigationBean = navigationBean;
    }

    public GroupDAO getGroupDAO() {
        return groupDAO;
    }

    public void setGroupDAO(GroupDAO groupDAO) {
        this.groupDAO = groupDAO;
    }

    public void initAction() {
        mpgBean.setGroupID(groupID);
        dashBoardBean.setGroupID(groupID);
        overallScoreBean.setGroupID(groupID);
        navigationBean.setGroupID(groupID);
        trendBean.setMaximized(Boolean.TRUE);
        trendBean.setGroupID(groupID);
    }

    public TrendBean getTrendBean() {
        return trendBean;
    }

    public void setTrendBean(TrendBean trendBean) {
        this.trendBean = trendBean;
    }

}
