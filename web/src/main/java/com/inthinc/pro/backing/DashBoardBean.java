package com.inthinc.pro.backing;

import java.io.Serializable;

import org.ajax4jsf.model.KeepAlive;
import org.springframework.security.AccessDeniedException;

import com.inthinc.pro.dao.GroupDAO;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.util.MessageUtil;

@KeepAlive
public class DashBoardBean extends BaseBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private NavigationBean navigationBean;
    private TreeNavigationBean treeNavigationBean;
    private MpgBean mpgBean;
    private OverallScoreBean overallScoreBean;
    private Integer groupID;
    private GroupDAO groupDAO;
    private TrendBean trendBean;
    private IdlePercentageBean idlePercentageBean;
    private SpeedPercentageBean speedPercentageBean;
    private TeamCommonBean teamCommonBean;

    public String getViewPath() {
        if (groupID == null)
            groupID = getUser().getGroupID();
        Group group = getGroupHierarchy().getGroup(groupID);
        if (group == null)
            throw new AccessDeniedException(MessageUtil.getMessageString("exception_accessDenied", getLocale()));
        
        treeNavigationBean.setCurrentGroupID(groupID);
        navigationBean.setGroupID(groupID);
        trendBean.setMaximized(Boolean.FALSE);
        trendBean.setGroupID(groupID);
        mpgBean.setGroupID(groupID);
        overallScoreBean.setGroupID(groupID);
        idlePercentageBean.setGroupID(groupID);
        speedPercentageBean.setGroupID(groupID);
        switch (group.getType()) {
            case FLEET:
            case DIVISION:
                return "pretty:fleet";
            case TEAM:
                teamCommonBean.setGroupID(groupID);
                return "pretty:team";
        }
        return null;
    }

    public NavigationBean getNavigationBean() {
        return navigationBean;
    }

    public void setNavigationBean(NavigationBean navigationBean) {
        this.navigationBean = navigationBean;
    }

    public MpgBean getMpgBean() {
        return mpgBean;
    }

    public void setMpgBean(MpgBean mpgBean) {
        this.mpgBean = mpgBean;
    }

    public OverallScoreBean getOverallScoreBean() {
        return overallScoreBean;
    }

    public void setOverallScoreBean(OverallScoreBean overallScoreBean) {
        this.overallScoreBean = overallScoreBean;
    }

    public Integer getGroupID() {
        return groupID;
    }

    public void setGroupID(Integer groupID) {
        this.groupID = groupID;
    }

    public GroupDAO getGroupDAO() {
        return groupDAO;
    }

    public void setGroupDAO(GroupDAO groupDAO) {
        this.groupDAO = groupDAO;
    }

    public TrendBean getTrendBean() {
        return trendBean;
    }

    public void setTrendBean(TrendBean trendBean) {
        this.trendBean = trendBean;
    }

    public IdlePercentageBean getIdlePercentageBean() {
        return idlePercentageBean;
    }

    public void setIdlePercentageBean(IdlePercentageBean idlePercentageBean) {
        this.idlePercentageBean = idlePercentageBean;
    }

    public SpeedPercentageBean getSpeedPercentageBean() {
        return speedPercentageBean;
    }

    public void setSpeedPercentageBean(SpeedPercentageBean speedPercentageBean) {
        this.speedPercentageBean = speedPercentageBean;
    }

    public TreeNavigationBean getTreeNavigationBean() {
        return treeNavigationBean;
    }

    public void setTreeNavigationBean(TreeNavigationBean treeNavigationBean) {
        this.treeNavigationBean = treeNavigationBean;
    }

    public TeamCommonBean getTeamCommonBean() {
        return teamCommonBean;
    }

    public void setTeamCommonBean(TeamCommonBean teamCommonBean) {
        this.teamCommonBean = teamCommonBean;
    }

}
