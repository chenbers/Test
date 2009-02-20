package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.richfaces.event.DataScrollerEvent;

import com.inthinc.pro.backing.model.GroupHierarchy;
import com.inthinc.pro.backing.model.GroupLevel;
import com.inthinc.pro.dao.MpgDAO;
import com.inthinc.pro.model.Group;
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
    private List<MpgEntityPkg> mpgEntities = new ArrayList<MpgEntityPkg>();

    private Integer numRowsPerPg = 5;
    private Integer maxCount = 0;
    private Integer start = 1;
    private Integer end = numRowsPerPg;

    private String sortOnName = "";
    private String sortOnLight = "";
    private String sortOnMedium = "";
    private String sortOnHeavy = "";

    private String countString = null;

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

        // get the data
        List<MpgEntityPkg> entities = null;
        try
        {
            logger.debug("getting scores for groupID: " + this.navigation.getGroupID());
            entities = getPagedMpgEntities();

            sb.append(GraphicUtil.createMpgXML(entities));
        }
        catch (Exception e)
        {
            logger.error("mpgDAO error", e);
        }

        // Bar parameters
        // MAKE SURE YOU LOAD REAL DATA SO, IF THERE IS FEWER OBSERVATIONS
        // THAN THE REQUESTED INTERVAL I.E. 22 DAYS WHEN YOU NEED 30, YOU
        // PAD THE FRONT WITH ZEROES
        // sb.append(GraphicUtil.createFakeBarData());

        sb.append("</chart>");

        return sb.toString();
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

    public void scrollerListener(DataScrollerEvent se)
    {
        this.start = (se.getPage() - 1) * this.numRowsPerPg + 1;
        this.end = (se.getPage()) * this.numRowsPerPg;

        // Partial page
        if (this.end > this.mpgEntities.size())
        {
            this.end = this.mpgEntities.size();
        }

        navigation.setStart(this.start);
        navigation.setEnd(this.end);
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
        if (mpgEntities.size() == 0)
        {
            Group parentGroup = this.getNavigation().getGroup();
            List<MpgEntity> tempEntities = mpgDAO.getEntities(parentGroup, durationBean.getDuration());

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
            this.maxCount = mpgEntities.size();
        }
        return this.mpgEntities;
    }

    public List<MpgEntityPkg> getPagedMpgEntities()
    {
        List<MpgEntityPkg> pagedMpgEntities = new ArrayList<MpgEntityPkg>();
        getMpgEntities(); // make sure list is initialized
        for (int i = this.start; i <= this.end; i++)
        {
            if (mpgEntities.size() < i)
                break;
            pagedMpgEntities.add(mpgEntities.get(i - 1));
        }
        return pagedMpgEntities;
    }

    public String getCountString()
    {
        countString = "Showing " + this.start + " to " + Math.min(this.maxCount, this.end) + " of " + this.maxCount + " records";
        return countString;
    }

    public void setCountString(String countString)
    {
        this.countString = countString;
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

    public Integer getNumRowsPerPg()
    {
        return numRowsPerPg;
    }

    public void setNumRowsPerPg(Integer numRowsPerPg)
    {
        this.numRowsPerPg = numRowsPerPg;
    }

    public Integer getMaxCount()
    {
        return maxCount;
    }

    public void setMaxCount(Integer maxCount)
    {
        this.maxCount = maxCount;
    }

    public Integer getStart()
    {
        return start;
    }

    public void setStart(Integer start)
    {
        this.start = start;
    }

    public Integer getEnd()
    {
        return end;
    }

    public void setEnd(Integer end)
    {
        this.end = end;
    }

    public String getSortOnName()
    {
        return sortOnName;
    }

    public void setSortOnName(String sortOnName)
    {
        this.sortOnName = sortOnName;
    }

    public String getSortOnLight()
    {
        return sortOnLight;
    }

    public void setSortOnLight(String sortOnLight)
    {
        this.sortOnLight = sortOnLight;
    }

    public String getSortOnMedium()
    {
        return sortOnMedium;
    }

    public void setSortOnMedium(String sortOnMedium)
    {
        this.sortOnMedium = sortOnMedium;
    }

    public String getSortOnHeavy()
    {
        return sortOnHeavy;
    }

    public void setSortOnHeavy(String sortOnHeavy)
    {
        this.sortOnHeavy = sortOnHeavy;
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
