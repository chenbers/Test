package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;

import com.inthinc.pro.backing.model.GroupHierarchy;
import com.inthinc.pro.backing.model.GroupLevel;
import com.inthinc.pro.dao.MpgDAO;
import com.inthinc.pro.model.MpgEntity;
import com.inthinc.pro.reports.ReportCriteria;
import com.inthinc.pro.reports.ReportRenderer;
import com.inthinc.pro.reports.service.ReportCriteriaService;
import com.inthinc.pro.util.GraphicUtil;
import com.inthinc.pro.wrapper.MpgEntityPkg;

public class MpgBean extends BaseBean
{

    private static final Logger logger = Logger.getLogger(MpgBean.class);

    private MpgDAO mpgDAO;
    private NavigationBean navigation;
    private DurationBean durationBean;
    private ReportRenderer reportRenderer;
    private ReportCriteriaService reportCriteriaService;
    private List<MpgEntityPkg> mpgEntities;

    private Boolean ascending;
    private String sortColumn;

    private String barDef;

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
        logger.debug("getting scores for groupID: " + this.navigation.getGroupID());
        sb.append(GraphicUtil.createMpgXML(getMpgEntities()));
        sb.append("</chart>");

        return sb.toString();

        // Bar parameters
        // MAKE SURE YOU LOAD REAL DATA SO, IF THERE IS FEWER OBSERVATIONS
        // THAN THE REQUESTED INTERVAL I.E. 22 DAYS WHEN YOU NEED 30, YOU
        // PAD THE FRONT WITH ZEROES
        // sb.append(GraphicUtil.createFakeBarData());
    }

    public NavigationBean getNavigation()
    {
        return navigation;
    }

    public void setNavigation(NavigationBean navigation)
    {
        this.navigation = navigation;
    }

    public DurationBean getDurationBean()
    {
        return durationBean;
    }

    public void setDurationBean(DurationBean durationBean)
    {
        this.durationBean = durationBean;
    }

    public Boolean getAscending()
    {
        return ascending;
    }

    public void setAscending(Boolean ascending)
    {
        this.ascending = ascending;
    }

    public String getSortColumn()
    {
        return sortColumn;
    }

    public void setSortColumn(String sortColumn)
    {
        this.sortColumn = sortColumn;
        sort(sortColumn);
    }

    public void sort(String column)
    {
        if (column.equals(""))
        {
            // Collections.sort(mpgEntities);
        }
    }

    public MpgDAO getMpgDAO()
    {
        return mpgDAO;
    }

    public void setMpgDAO(MpgDAO mpgDAO)
    {
        this.mpgDAO = mpgDAO;
    }

    public Integer getGroupID()
    {
        return this.getNavigation().getGroupID();
    }

    public List<MpgEntityPkg> getMpgEntities()
    {
        if (mpgEntities == null)
        {
            List<MpgEntity> tempEntities = mpgDAO.getEntities(navigation.getGroup(), durationBean.getDuration());
            Collections.sort(tempEntities);
            mpgEntities = new ArrayList<MpgEntityPkg>();

            // Populate the table
            String contextPath = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
            for (MpgEntity entity : tempEntities)
            {
                MpgEntityPkg pkg = new MpgEntityPkg();
                pkg.setEntity(entity);
                GroupHierarchy groupHierarchy = getGroupHierarchy();
                GroupLevel groupLevel = groupHierarchy.getGroupLevel(entity.getEntityID());
                if (groupLevel == null)
                {
                    groupLevel = groupHierarchy.getGroupLevel(entity.getGroupID());
                }
                if (groupLevel != null)
                {
                    pkg.setGoTo(contextPath + groupLevel.getUrl() + "?groupID=" + entity.getEntityID());
                    this.mpgEntities.add(pkg);
                }
            }
        }
        return this.mpgEntities;
    }

    public void setMpgEntities(List<MpgEntityPkg> mpgEntities)
    {
        this.mpgEntities = mpgEntities;
    }

    // public List<MpgEntityPkg> getPagedMpgEntities()
    // {
    // List<MpgEntityPkg> pagedMpgEntities = new ArrayList<MpgEntityPkg>();
    // getMpgEntities(); // make sure list is initialized
    // for (int i = this.start; i <= this.end; i++)
    // {
    // if (mpgEntities.size() < i)
    // break;
    // pagedMpgEntities.add(mpgEntities.get(i - 1));
    // }
    // return pagedMpgEntities;
    // }

    public String exportToPDF()
    {

        ReportCriteria reportCriteria = buildReportCriteria();
        reportRenderer.exportSingleReportToPDF(reportCriteria, getFacesContext());

        return null;
    }

    public ReportCriteria buildReportCriteria()
    {
        ReportCriteria reportCriteria = reportCriteriaService.getMpgReportCriteria(navigation.getGroupID(), durationBean.getDuration());
        reportCriteria.setReportDate(new Date(), getUser().getPerson().getTimeZone());
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
