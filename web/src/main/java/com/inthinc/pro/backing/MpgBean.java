package com.inthinc.pro.backing;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.richfaces.model.Ordering;

import com.inthinc.pro.dao.GroupDAO;
import com.inthinc.pro.dao.MpgDAO;
import com.inthinc.pro.model.Duration;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.MpgEntity;
import com.inthinc.pro.reports.ReportCriteria;
import com.inthinc.pro.reports.ReportRenderer;
import com.inthinc.pro.reports.service.ReportCriteriaService;
import com.inthinc.pro.util.GraphicUtil;
public class MpgBean extends BaseBean
{
    private static final Logger logger = Logger.getLogger(MpgBean.class);
    private MpgDAO mpgDAO;
    private GroupDAO groupDAO;
    // private NavigationBean navigation;
    //private DurationBean durationBean;
    private Duration duration = Duration.DAYS;
    private ReportRenderer reportRenderer;
    private ReportCriteriaService reportCriteriaService;
    // private List<MpgEntityPkg> mpgEntities;
    private List<MpgEntity> mpgEntities;
    private String barDef;
    private Ordering sortOrder = Ordering.ASCENDING;
    private Integer groupID;
    private Group group;

    public void init()
    {
    }

    public Ordering getSortOrder()
    {
        return sortOrder;
    }

    public void setSortOrder(Ordering sortOrder)
    {
        this.sortOrder = sortOrder;
    }

    private boolean isAcending()
    {
        if (sortOrder.equals(Ordering.ASCENDING))
            return true;
        else
            return false;
    }

    public String getBarDef()
    {
        if (barDef == null)
        {
            barDef = createBarDef();
        }
        return barDef;
    }

    public void setBarDef(String barDef)
    {
        this.barDef = barDef;
    }

    public String createBarDef()
    {
        StringBuffer sb = new StringBuffer();
        // Control parameters
        sb.append(GraphicUtil.getBarControlParameters());
        logger.debug("getting scores for groupID: " + groupID);
        sb.append(GraphicUtil.createMpgXML(getMpgEntities()));
        sb.append("</chart>");
        return sb.toString();
        // Bar parameters
        // MAKE SURE YOU LOAD REAL DATA SO, IF THERE IS FEWER OBSERVATIONS
        // THAN THE REQUESTED INTERVAL I.E. 22 DAYS WHEN YOU NEED 30, YOU
        // PAD THE FRONT WITH ZEROES
        // sb.append(GraphicUtil.createFakeBarData());
    }

    public Integer getGroupID()
    {
        return groupID;
    }

    public void setGroupID(Integer groupID)
    {
        this.groupID = groupID;
        //TODO: pull group from group hierarchy
        group = groupDAO.findByID(groupID);
    }

    public Group getGroup()
    {
        return group;
    }

    public void setGroup(Group group)
    {
        this.group = group;
    }

    // public NavigationBean getNavigation()
    // {
    // return navigation;
    // }
    //
    // public void setNavigation(NavigationBean navigation)
    // {
    // this.navigation = navigation;
    // }
//    public DurationBean getDurationBean()
//    {
//        return durationBean;
//    }
//
//    public void setDurationBean(DurationBean durationBean)
//    {
//        this.durationBean = durationBean;
//    }
    public Duration getDuration()
    {
        return duration;
    }

    public void setDuration(Duration duration)
    {
        this.duration = duration;
    }

    public MpgDAO getMpgDAO()
    {
        return mpgDAO;
    }

    public void setMpgDAO(MpgDAO mpgDAO)
    {
        this.mpgDAO = mpgDAO;
    }

    public GroupDAO getGroupDAO()
    {
        return groupDAO;
    }

    public void setGroupDAO(GroupDAO groupDAO)
    {
        this.groupDAO = groupDAO;
    }

    // public List<MpgEntityPkg> getMpgEntities()
    // {
    //
    // // If the mpgEntities list is null, then populate it
    // if (mpgEntities == null)
    // {
    // mpgEntities = new ArrayList<MpgEntityPkg>();
    //
    // // Populate the table
    // String contextPath = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
    //
    // List<MpgEntity> list = mpgDAO.getEntities(navigation.getGroup(), duration);
    // Collections.sort(list);
    // for (MpgEntity entity : list)
    // {
    // MpgEntityPkg pkg = new MpgEntityPkg();
    // pkg.setEntity(entity);
    // GroupHierarchy groupHierarchy = getGroupHierarchy();
    // GroupLevel groupLevel = groupHierarchy.getGroupLevel(entity.getEntityID());
    // if (groupLevel == null)
    // {
    // groupLevel = groupHierarchy.getGroupLevel(entity.getGroupID());
    // }
    // if (groupLevel != null)
    // {
    // pkg.setGoTo(contextPath + groupLevel.getUrl() + "?groupID=" + entity.getEntityID());
    // this.mpgEntities.add(pkg);
    // }
    // }
    // }
    //
    // return mpgEntities;
    // }
    public List<MpgEntity> getMpgEntities()
    {
        return mpgDAO.getEntities(group, duration);
    }

    public void setMpgEntities(List<MpgEntity> mpgEntities)
    {
        this.mpgEntities = mpgEntities;
    }

    public String exportToPDF()
    {
        ReportCriteria reportCriteria = buildReportCriteria();
        reportRenderer.exportSingleReportToPDF(reportCriteria, getFacesContext());
        return null;
    }

    public ReportCriteria buildReportCriteria()
    {
        ReportCriteria reportCriteria = reportCriteriaService.getMpgReportCriteria(getGroupID(), getDuration());
        reportCriteria.setReportDate(new Date(), getUser().getPerson().getTimeZone());
        reportCriteria.setLocale(getUser().getLocale());
        return reportCriteria;
    }

    public void setReportRenderer(ReportRenderer reportRenderer)
    {
        this.reportRenderer = reportRenderer;
    }

    public ReportRenderer getReportRenderer()
    {
        return reportRenderer;
    }

    public void setReportCriteriaService(ReportCriteriaService reportCriteriaService)
    {
        this.reportCriteriaService = reportCriteriaService;
    }

    public ReportCriteriaService getReportCriteriaService()
    {
        return reportCriteriaService;
    }
}
