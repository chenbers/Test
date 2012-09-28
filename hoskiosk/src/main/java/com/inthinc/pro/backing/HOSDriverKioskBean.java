package com.inthinc.pro.backing;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.inthinc.hos.model.HOSStatus;
import com.inthinc.hos.model.RuleSetType;
import com.inthinc.pro.backing.report.ReportRenderer;
import com.inthinc.pro.backing.ui.DateRange;
import com.inthinc.pro.dao.HOSDAO;
import com.inthinc.pro.hos.HOSCurrentStatus;
import com.inthinc.pro.hos.HOSLogExporter;
import com.inthinc.pro.hos.HOSLogReport;
import com.inthinc.pro.model.hos.HOSRecord;
import com.inthinc.pro.reports.ReportCriteria;
import com.inthinc.pro.reports.service.ReportCriteriaService;


public class HOSDriverKioskBean extends BaseBean {
    
    private static final long serialVersionUID = 1L;
    private String location;
    private Integer hosStatusCode;
    private HOSDAO hosDAO;
    private HOSCurrentStatus hosCurrentStatus;    
    private DateRange dateRange;
    

    private ReportCriteriaService reportCriteriaService;
    private ReportRenderer reportRenderer;
    private static final Logger logger = Logger.getLogger(HOSDriverKioskBean.class);
    
    
    public void setStatusOnDuty() {
        addHosLog(HOSStatus.ON_DUTY);
    }

    public void setStatusOffDuty() {
        addHosLog(HOSStatus.OFF_DUTY);
    }

    private void addHosLog(HOSStatus status) {
        
        HOSRecord hosRecord = new HOSRecord();
        hosRecord.setStatus(status);
        hosRecord.setTimeZone(getPerson().getTimeZone());
        hosRecord.setLogTime(new Date());
        hosRecord.setDriverID(getDriver().getDriverID());
        hosRecord.setDriverDotType(getDriver().getDot() == null ? RuleSetType.NON_DOT : getDriver().getDot());
        hosRecord.setEditUserID(0);
        hosRecord.setLocation(location);
        hosDAO.create(0l, hosRecord);
        setHosCurrentStatus(null);
        
    }
    
    public void printDailyDriverLogReport() {
        if (dateRange.getBadDates() == null) {
            List<ReportCriteria> reportCriteriaList = new HOSLogReport(hosDAO, getProUser().getAccountGroupHierarchy()).generateReport(getReportCriteriaService(), getDriver(), dateRange);
            reportRenderer.exportReportToPDF(reportCriteriaList, FacesContext.getCurrentInstance());
        }
        
    }
    
    public HOSCurrentStatus getHosCurrentStatus() {
        if (hosCurrentStatus == null)
            initHOSCurrentStatus();
        return hosCurrentStatus;
    }


    public void setHosCurrentStatus(HOSCurrentStatus hosCurrentStatus) {
        this.hosCurrentStatus = hosCurrentStatus;
    }

    private void initHOSCurrentStatus() {
        hosCurrentStatus = new HOSCurrentStatus(hosDAO);
        hosCurrentStatus.init(getDriver());
    }


    public HOSDAO getHosDAO() {
        return hosDAO;
    }
    public void setHosDAO(HOSDAO hosDAO) {
        this.hosDAO = hosDAO;
    }
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public Integer getHosStatusCode() {
        return hosStatusCode;
    }
    public void setHosStatusCode(Integer hosStatusCode) {
        this.hosStatusCode = hosStatusCode;
    }

    public DateRange getDateRange() {
        if (dateRange == null) {
            dateRange = new DateRange(getPerson().getLocale(), getPerson().getTimeZone());
        }
        return dateRange;
    }

    public void setDateRange(DateRange dateRange) {
        this.dateRange = dateRange;
    }

    public ReportCriteriaService getReportCriteriaService() {
        return reportCriteriaService;
    }

    public void setReportCriteriaService(ReportCriteriaService reportCriteriaService) {
        this.reportCriteriaService = reportCriteriaService;
    }
    public ReportRenderer getReportRenderer() {
        return reportRenderer;
    }

    public void setReportRenderer(ReportRenderer reportRenderer) {
        this.reportRenderer = reportRenderer;
    }
    
    public void exportLogs() {

        FacesContext facesContext = FacesContext.getCurrentInstance();
        
        HttpServletResponse response = (HttpServletResponse)facesContext.getExternalContext().getResponse();
        
        response.setContentType("application/octet-stream");
        response.addHeader("Content-Disposition", "attachment; filename=\"driver_" + getPerson().getEmpid() + ".log\"");

        OutputStream out = null;

        try
        {
            out = response.getOutputStream();
            new HOSLogExporter(hosDAO).writeDriverStateHistoryToStream(out, getDriver());
            out.flush();
            facesContext.responseComplete();
        }
        catch (IOException e)
        {
            logger.error(e);
        }
        finally
        {
            try
            {
                out.close();
            }
            catch (IOException e)
            {
                logger.error(e);
            }
        }
        
    }
    

}
