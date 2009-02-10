package com.inthinc.pro.scheduler.quartz;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.inthinc.pro.dao.AccountDAO;
import com.inthinc.pro.dao.ReportScheduleDAO;
import com.inthinc.pro.dao.UserDAO;
import com.inthinc.pro.model.Account;
import com.inthinc.pro.model.Occurrence;
import com.inthinc.pro.model.ReportSchedule;
import com.inthinc.pro.model.Status;
import com.inthinc.pro.model.User;
import com.inthinc.pro.reports.FormatType;
import com.inthinc.pro.reports.Report;
import com.inthinc.pro.reports.ReportCreator;
import com.inthinc.pro.reports.ReportCriteria;
import com.inthinc.pro.reports.ReportGroup;
import com.inthinc.pro.reports.service.ReportCriteriaService;

/**
 * 
 * @author mstrong
 *
 */

public class EmailReportJob extends QuartzJobBean
{
    private static final Logger logger = Logger.getLogger(EmailReportJob.class);
  
    @SuppressWarnings("unchecked")
    private ReportCreator reportCreator;
    private ReportCriteriaService reportCriteriaService;
    private ReportScheduleDAO reportScheduleDAO;
    private UserDAO userDAO;
    private AccountDAO accountDAO;
    
   

    @Override
    protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException
    {
        logger.debug("EmailReportJob: START");
        processReportSchedules();
        logger.debug("EmailReportJob: END");
        
    }
    
    
    private void processReportSchedules()
    {
        
        List<Account> accounts = accountDAO.getAllAcctIDs();
        logger.debug("Account Count: " + accounts.size());
        
        List<ReportSchedule> reportSchedules = new ArrayList<ReportSchedule>();
        for(Account account : accounts)
        {
            reportSchedules.addAll(reportScheduleDAO.getReportSchedulesByAccountID(account.getAcctID()));
        }
            
        Calendar todaysDate = Calendar.getInstance();
        for(ReportSchedule reportSchedule:reportSchedules)
        {
            logger.debug("EmailReportJob: Begin Validation: " + reportSchedule.getName());
            if(emailReport(reportSchedule))
            {
                logger.debug("EmailReportJob: BEGIN REPORT " + reportSchedule.getName());
                processReportSchedule(reportSchedule);
                reportSchedule.setLastDate(todaysDate.getTime());
                Integer modified = reportScheduleDAO.update(reportSchedule);
                logger.debug("EmailReportJob: UPDATE RESULT " + modified);
                logger.debug("EmailReportJob: END REPORT");
            }
        }
        
    }
    
    @SuppressWarnings("unchecked")
    private void processReportSchedule(ReportSchedule reportSchedule)
    {
        ReportGroup reportGroup = ReportGroup.valueOf(reportSchedule.getReportID());
        List<ReportCriteria> reportCriteriaList = new ArrayList<ReportCriteria>();
        
        for(int i= 0;i < reportGroup.getReports().length;i++)
        {
            switch(reportGroup.getReports()[i])
            {
            case OVERALL_SCORE: 
                reportCriteriaList.add(reportCriteriaService.getOverallScoreReportCriteria(reportSchedule.getGroupID(), reportSchedule.getReportDuration()));
                break;
            case TREND:
                reportCriteriaList.add(reportCriteriaService.getTrendChartReportCriteria(reportSchedule.getGroupID(), reportSchedule.getReportDuration()));
                break;
            case MPG_GROUP:
                reportCriteriaList.add(reportCriteriaService.getMpgReportCriteria(reportSchedule.getGroupID(), reportSchedule.getReportDuration()));
                break;
            case DEVICES_REPORT:
                reportCriteriaList.add(reportCriteriaService.getDevicesReportCriteria(reportSchedule.getGroupID()));
                break;
            case DRIVER_REPORT:
                reportCriteriaList.add(reportCriteriaService.getDriverReportCriteria(reportSchedule.getGroupID(), reportSchedule.getReportDuration()));
                break;
            case VEHICLE_REPORT:
                reportCriteriaList.add(reportCriteriaService.getVehicleReportCriteria(reportSchedule.getGroupID(), reportSchedule.getReportDuration()));
                break;
            case IDLING_REPORT:
                final Calendar endDate = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
                Calendar startDate = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
                startDate.roll(Calendar.DATE, -7);  //Roll back 7 days
                reportCriteriaList.add(reportCriteriaService.getIdlingReportCriteria(reportSchedule.getGroupID(), startDate.getTime(), endDate.getTime()));
                break;
            default:
                break;
               
            }
        }
        
        
        Report report =  reportCreator.getReport(reportCriteriaList);
        report.exportReportToEmail(reportSchedule.getEmailToAsString(), FormatType.PDF);
    }
    
    
    /*
     * To determine if we should send a report out the following must be met.
     * 
     * Rule 1: Must be between the start date and end date;
     * Rule 2: OCCURRENCE.Daily - Must be scheduled to run on the day of week it currently is. 
     * Rule 3: The Schduled Time cannot be greater than the current time.
     * Rule 4: Report Schedule must be active.
     * Rule 5: OCCURRENCE.Weekly - Check to see if currenty day of week is the same as the start date day of week.
     * Rule 6: OCCURRENCE.Monthly - Make sure currenty day of month is the same ans the start date day of month.
     * 
     */
    private boolean emailReport(ReportSchedule reportSchedule)
    {
        User user = userDAO.findByID(reportSchedule.getUserID());
        Calendar currentTimeDate = Calendar.getInstance(user.getPerson().getTimeZone());
        int dayOfWeek = currentTimeDate.get(Calendar.DAY_OF_WEEK);
        currentTimeDate.setTimeZone(user.getPerson().getTimeZone());
        
        Calendar startCalendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        startCalendar.setTime(reportSchedule.getStartDate());
        
        Calendar endCalendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        if(reportSchedule.getEndDate() != null)
        {
            endCalendar.setTime(reportSchedule.getEndDate());
        }
        
        SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss a z");
        df.setTimeZone(user.getPerson().getTimeZone());
        
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss a z");
        sdf2.setTimeZone(TimeZone.getTimeZone("UTC"));
        
        //Rule 1: 
        if(currentTimeDate.compareTo(startCalendar) < 0)
        {
            logger.debug("Report Not Sent: Current date is before start date");
            logger.debug("Name: " + reportSchedule.getName());
            logger.debug("Current Date Time: " + df.format(currentTimeDate.getTime()));
            logger.debug("Start Date Time " + sdf2.format(startCalendar.getTime()));
            return false;
        }
        
        if(reportSchedule.getEndDate() != null && currentTimeDate.compareTo(endCalendar) > 0)
        {
            logger.debug("Report Not Sent: Current date is after end date");
            logger.debug("Name: " + reportSchedule.getName());
            logger.debug("Current Date Time: " + df.format(currentTimeDate.getTime()));
            logger.debug("End Date Time " + sdf2.format(endCalendar.getTime()));
            return false;
        }
        
        if(reportSchedule.getOccurrence() == null)
        {
            return false;
        }
        
        //Rule 2:
        if(reportSchedule.getOccurrence().equals(Occurrence.DAILY) && !isValidDayOfWeek(reportSchedule.getDayOfWeek(), dayOfWeek))
        {
            logger.debug("Report Not Sent: Report is not scheduled to run on current day of week");
            logger.debug("Name: " + reportSchedule.getName());
            return false;
        }
        
        //Rule 3:
        Calendar timeToRunReport = Calendar.getInstance();
        timeToRunReport.set(Calendar.HOUR, 0);
        timeToRunReport.set(Calendar.MINUTE, 0);
        timeToRunReport.set(Calendar.SECOND, 0);
        timeToRunReport.set(Calendar.MILLISECOND, 0);
        if(reportSchedule.getTimeOfDay() != null)
        {
            timeToRunReport.add(Calendar.MINUTE, reportSchedule.getTimeOfDay());
        }
        timeToRunReport.setTimeZone(user.getPerson().getTimeZone());
        if(currentTimeDate.compareTo(timeToRunReport) < 0)
        {
            logger.debug("Report Not Sent: Current time is less than schedule time");
            logger.debug("Name: " + reportSchedule.getName());
            logger.debug("Current Date Time: " + df.format(currentTimeDate.getTime()));
            logger.debug("Time To Run " + sdf2.format(timeToRunReport.getTime()));
            return false;
        }
        
        //Rule 4:
        if(reportSchedule.getStatus() != null && !reportSchedule.getStatus().equals(Status.ACTIVE))
        {
            logger.debug("Report Not Sent: Report is not scheduled to run on current day of week");
            logger.debug("Name: " + reportSchedule.getName());
            return false;
        }
        
        //Rule 5:
        if(reportSchedule.getOccurrence().equals(Occurrence.WEEKLY) && 
                startCalendar.get(Calendar.DAY_OF_WEEK) != currentTimeDate.get(Calendar.DAY_OF_WEEK))
        {
            logger.debug("Report Not Sent: Report is not scheduled to run on current day of week");
            logger.debug("Name: " + reportSchedule.getName());
            return false;
        }
        
        //Rule 6:
        if(reportSchedule.getOccurrence().equals(Occurrence.MONTHLY) && 
                startCalendar.get(Calendar.DAY_OF_MONTH) != currentTimeDate.get(Calendar.DAY_OF_MONTH))
        {
            logger.debug("Report Not Sent: Report is not scheduled to run on current day of month");
            logger.debug("Name: " + reportSchedule.getName());
            return false;
        }
        
        return true;
    }
    
    private boolean isValidDayOfWeek(List<Boolean> daysOfWeek, int dayOfWeek){
        Boolean returnBoolean = false;
        if(daysOfWeek != null)
        {
            for(int i = 0;i < daysOfWeek.size();i++)
            {
                Boolean dayBoolean = daysOfWeek.get(i);
                if(i == (dayOfWeek-1) && dayBoolean)
                {
                    returnBoolean = true;
                }
            }
        }
        
        return returnBoolean;
    }
    

    public void setReportCreator(ReportCreator reportCreator)
    {
        this.reportCreator = reportCreator;
    }

    public ReportCreator getReportCreator()
    {
        return reportCreator;
    }



    public void setReportCriteriaService(ReportCriteriaService reportCriteriaService)
    {
        this.reportCriteriaService = reportCriteriaService;
    }



    public ReportCriteriaService getReportCriteriaService()
    {
        return reportCriteriaService;
    }

    public void setReportScheduleDAO(ReportScheduleDAO reportScheduleDAO)
    {
        this.reportScheduleDAO = reportScheduleDAO;
    }

    public ReportScheduleDAO getReportScheduleDAO()
    {
        return reportScheduleDAO;
    }

    public void setUserDAO(UserDAO userDAO)
    {
        this.userDAO = userDAO;
    }

    public UserDAO getUserDAO()
    {
        return userDAO;
    }
    
    public AccountDAO getAccountDAO()
    {
        return accountDAO;
    }


    public void setAccountDAO(AccountDAO accountDAO)
    {
        this.accountDAO = accountDAO;
    }

}
