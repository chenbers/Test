package com.inthinc.pro.backing;

import java.io.Serializable;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.richfaces.model.Ordering;

import com.inthinc.pro.dao.MpgDAO;
import com.inthinc.pro.model.Duration;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.MeasurementType;
import com.inthinc.pro.model.MpgEntity;
import com.inthinc.pro.reports.ReportCriteria;
import com.inthinc.pro.reports.ReportRenderer;
import com.inthinc.pro.reports.service.ReportCriteriaService;
import com.inthinc.pro.util.GraphicUtil;
import com.inthinc.pro.util.MessageUtil;
public class MpgBean extends BaseBean implements Serializable
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private static final Logger logger = Logger.getLogger(MpgBean.class);
    private MpgDAO mpgDAO;
    private DurationBean durationBean;
    private ReportRenderer reportRenderer;
    private ReportCriteriaService reportCriteriaService;
    private List<MpgEntity> mpgEntities;
    private String barDef;
    private Ordering sortOrder = Ordering.ASCENDING;
    private Integer groupID;
    private Group group;


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
        int yAxisName = sb.indexOf("yAxisName");
        sb.replace(yAxisName+10, yAxisName+11, "'"+MessageUtil.getMessageString(getFuelEfficiencyType()+"_Miles_Per_Gallon"));
         logger.debug("getting scores for groupID: " + groupID);
        sb.append(GraphicUtil.createMpgXML(getMpgEntities(), getMeasurementType(), getFuelEfficiencyType()));
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
        group = getGroupHierarchy().getGroup(groupID);
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
    
    public List<MpgEntity> getMpgEntities()
    {
        if(mpgEntities == null)
            mpgEntities = mpgDAO.getEntities(group, durationBean.getDuration());
        	sortEntitiesByGroup();
        return mpgEntities;
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
        ReportCriteria reportCriteria = reportCriteriaService.getMpgReportCriteria(getGroupID(), durationBean.getDuration());
        reportCriteria.setReportDate(new Date(), getPerson().getTimeZone());
        reportCriteria.setLocale(getLocale());
        reportCriteria.setMeasurementType(getMeasurementType());
        reportCriteria.setFuelEfficiencyType(getFuelEfficiencyType());
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
    public void setDuration(Duration duration)
    {
        durationBean.setDuration(duration);
        mpgEntities = null;
    }
    
    private void sortEntitiesByGroup(){
    	
    	Collections.sort(mpgEntities);
    }
}
