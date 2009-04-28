package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.richfaces.model.Ordering;

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
    private String barDef;
    private Ordering sortOrder = Ordering.ASCENDING;

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

    public MpgDAO getMpgDAO()
    {
        return mpgDAO;
    }

    public void setMpgDAO(MpgDAO mpgDAO)
    {
        this.mpgDAO = mpgDAO;
    }

    public List<MpgEntityPkg> getMpgEntities()
    {

        // If the mpgEntities list is null, then populate it
        if (mpgEntities == null)
        {
            mpgEntities = new ArrayList<MpgEntityPkg>();

            // Populate the table
            String contextPath = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();

            List<MpgEntity> list = mpgDAO.getEntities(navigation.getGroup(), durationBean.getDuration());
            Collections.sort(list);
            for (MpgEntity entity : list)
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

        return mpgEntities;
    }

    public void setMpgEntities(List<MpgEntityPkg> mpgEntities)
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
        ReportCriteria reportCriteria = reportCriteriaService.getMpgReportCriteria(navigation.getGroupID(), durationBean.getDuration());
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

    // public String sortAction()
    // {
    // Collections.sort(mpgEntities, new Comparator<MpgEntityPkg>()
    // {
    // public int compare(MpgEntityPkg mpg1, MpgEntityPkg mpg2)
    // {
    // MpgEntity e1 = mpg1.getEntity();
    // MpgEntity e2 = mpg2.getEntity();
    // if (sortColumnName == null)
    // {
    // return 0;
    // }
    // if (sortColumnName.equals(nameColumnName))
    // {
    // if (e1.getEntityName() == null && e2.getEntityName() != null)
    // return -1;
    // if (e2.getEntityName() == null && e1.getEntityName() != null)
    // return 1;
    // if (e1.getEntityName() == null && e2.getEntityName() == null)
    // return 0;
    //
    // if (e1.getEntityName().equalsIgnoreCase("Hardware"))
    // return isAcending() ? -1 : 1;
    // if (e2.getEntityName().equalsIgnoreCase("Hardware"))
    // return isAcending() ? 1 : -1;
    //
    // return isAcending() ? e1.getEntityName().toUpperCase().compareTo(e2.getEntityName().toUpperCase()) : e2.getEntityName().toUpperCase().compareTo(
    // e1.getEntityName().toUpperCase());
    // }
    // if (sortColumnName.equals(lightColumnName))
    // {
    // if (e1.getLightValue() == null && e2.getLightValue() != null)
    // return -1;
    // if (e2.getLightValue() == null && e1.getLightValue() != null)
    // return 1;
    // if (e1.getLightValue() == null && e2.getLightValue() == null)
    // return 0;
    //
    // return isAcending() ? e1.getLightValue().compareTo(e2.getLightValue()) : e2.getLightValue().compareTo(e1.getLightValue());
    // }
    // if (sortColumnName.equals(mediumColumnName))
    // {
    // if (e1.getMediumValue() == null && e2.getMediumValue() != null)
    // return -1;
    // if (e2.getMediumValue() == null && e1.getMediumValue() != null)
    // return 1;
    // if (e1.getMediumValue() == null && e2.getMediumValue() == null)
    // return 0;
    //
    // return isAcending() ? e1.getMediumValue().compareTo(e2.getMediumValue()) : e2.getMediumValue().compareTo(e1.getMediumValue());
    // }
    // if (sortColumnName.equals(heavyColumnName))
    // {
    // if (e1.getHeavyValue() == null && e2.getHeavyValue() != null)
    // return -1;
    // if (e2.getHeavyValue() == null && e1.getHeavyValue() != null)
    // return 1;
    // if (e1.getHeavyValue() == null && e2.getHeavyValue() == null)
    // return 0;
    //
    // return isAcending() ? e1.getHeavyValue().compareTo(e2.getHeavyValue()) : e2.getHeavyValue().compareTo(e1.getHeavyValue());
    // }
    // return 0;
    // }
    // });
    // table.setValue(mpgEntities);
    // return null;
    // }
}
