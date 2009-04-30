package com.inthinc.pro.scheduler.quartz;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import com.inthinc.pro.scheduler.i18n.LocalizedMessage;

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
        for (Account account : accounts)
        {
            reportSchedules.addAll(reportScheduleDAO.getReportSchedulesByAccountID(account.getAcctID()));
        }

        for (ReportSchedule reportSchedule : reportSchedules)
        {
            logger.debug("EmailReportJob: Begin Validation: " + reportSchedule.getName());
            if (emailReport(reportSchedule))
            {
                User user = userDAO.findByID(reportSchedule.getUserID());
                Calendar todaysDate = Calendar.getInstance(user.getPerson().getTimeZone());
                logger.debug("EmailReportJob: BEGIN PROCESSING REPORT ");
                logger.debug(reportSchedule.toString());
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
        User user = userDAO.findByID(reportSchedule.getUserID());
        for (int i = 0; i < reportGroup.getReports().length; i++)
        {
            switch (reportGroup.getReports()[i]) {
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
                final Calendar endDate = Calendar.getInstance(user.getPerson().getTimeZone());
                Calendar startDate = Calendar.getInstance(user.getPerson().getTimeZone());
                startDate.add(Calendar.DATE, -7);
                if(logger.isDebugEnabled())
                {
                    SimpleDateFormat sdf = new SimpleDateFormat("MMM dd yyyy");
                    logger.debug("Start Time: " + sdf.format(startDate.getTime()));
                    logger.debug("End Time: " + sdf.format(endDate.getTime()));
                }
                reportCriteriaList.add(reportCriteriaService.getIdlingReportCriteria(reportSchedule.getGroupID(), startDate.getTime(), endDate.getTime()));
                break;
            default:
                break;

            }
        }
        // Set the current date of the reports
        for (ReportCriteria reportCriteria : reportCriteriaList)
        {
            reportCriteria.setReportDate(new Date(), user.getPerson().getTimeZone());
            reportCriteria.setLocale(user.getLocale());
        }
        
        Report report = reportCreator.getReport(reportCriteriaList);
        String subject = LocalizedMessage.getString("reportSchedule.emailSubject");
        report.exportReportToEmail(reportSchedule.getEmailToString(), FormatType.PDF, null, subject + reportSchedule.getName());
    }

    /*
     * To determine if we should send a report out the following must be met.
     * 
     * Rule 1: Must be between the start date and end date; 
     * Rule 2: OCCURRENCE.Weekly - Must be scheduled to run on the day of week it currently is. 
     * Rule 3: The Schduled Time cannot be greater than the current time. 
     * Rule 4: The scheduled time has to be within the last hour from the current time. If not, it will not occure till the next scheduled
     * date. 
     * Rule 5: Report Schedule must be active. 
     * Rule 7: OCCURRENCE.Monthly - Make sure currenty day of month is the same ans the start date day of month.
     */
    protected boolean emailReport(ReportSchedule reportSchedule)
    {
        User user = userDAO.findByID(reportSchedule.getUserID());
        Calendar currentDateTime = Calendar.getInstance(user.getPerson().getTimeZone());
        int dayOfWeek = currentDateTime.get(Calendar.DAY_OF_WEEK);
        currentDateTime.setTimeZone(user.getPerson().getTimeZone());

        Calendar startCalendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        startCalendar.setTime(reportSchedule.getStartDate());

        Calendar endCalendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        if (reportSchedule.getEndDate() != null)
        {
            endCalendar.setTime(reportSchedule.getEndDate());
        }

        SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss a z");
        df.setTimeZone(user.getPerson().getTimeZone());

        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss a z");
        sdf2.setTimeZone(TimeZone.getTimeZone("www."));

        // Rule 1:
        if (compareDates(currentDateTime, startCalendar) < 0)
        {
            if (logger.isDebugEnabled())
            {
                logger.debug("Report Not Sent: Current date is before start date");
                logger.debug("Name: " + reportSchedule.getName());
                logger.debug("Current Date Time: " + df.format(currentDateTime.getTime()));
                logger.debug("Start Date Time " + sdf2.format(startCalendar.getTime()));
            }
            return false;
        }

        if (reportSchedule.getEndDate() != null && compareDates(currentDateTime, endCalendar) > 0)
        {
            if (logger.isDebugEnabled())
            {
                logger.debug("Report Not Sent: Current date is after end date");
                logger.debug("Name: " + reportSchedule.getName());
                logger.debug("Current Date Time: " + df.format(currentDateTime.getTime()));
                logger.debug("End Date Time " + sdf2.format(endCalendar.getTime()));
            }
            return false;
        }

        if (reportSchedule.getOccurrence() == null)
        {
            return false;
        }

        // Rule 2:
        if (reportSchedule.getOccurrence().equals(Occurrence.WEEKLY) && !isValidDayOfWeek(reportSchedule.getDayOfWeek(), dayOfWeek))
        {
            if (logger.isDebugEnabled())
            {
                logger.debug("Report Not Sent: Report is not scheduled to run on current day of week");
                logger.debug("Name: " + reportSchedule.getName());
            }
            return false;
        }

        // Rule 3:
        Integer currentTimeInMinutes = currentDateTime.get(Calendar.HOUR_OF_DAY) * 60;
        currentTimeInMinutes += currentDateTime.get(Calendar.MINUTE);

        if (reportSchedule.getTimeOfDay() == null)
        {
            reportSchedule.setTimeOfDay(0);

        }

        if (currentTimeInMinutes < reportSchedule.getTimeOfDay())
        {
            if (logger.isDebugEnabled())
            {
                logger.debug("Report Not Sent: Report time is greater than current time");
                logger.debug("Name: " + reportSchedule.getName());
                logger.debug("Current Time: Hour " + (Integer) (currentTimeInMinutes / 60) + " Minutes " + (Integer) currentTimeInMinutes % 60);
                logger.debug("Time To Run Hour " + (Integer) (reportSchedule.getTimeOfDay() / 60) + " Minutes " + (Integer) reportSchedule.getTimeOfDay() % 60);
            }
            return false;
        }

        // Rule 4:
        if ((currentTimeInMinutes - 60) >= reportSchedule.getTimeOfDay())
        {
            if (logger.isDebugEnabled())
            {
                logger.debug("Report Not Sent: Report time was more than an hour before the current time");
                logger.debug("Name: " + reportSchedule.getName());
                logger.debug("Current Time: Hour " + (Integer) (currentTimeInMinutes / 60) + " Minutes " + (Integer) currentTimeInMinutes % 60);
                logger.debug("Time To Run Hour " + (Integer) (reportSchedule.getTimeOfDay() / 60) + " Minutes " + (Integer) reportSchedule.getTimeOfDay() % 60);
            }
            return false;
        }

        // Rule 5:
        if (reportSchedule.getStatus() != null && !reportSchedule.getStatus().equals(Status.ACTIVE))
        {
            if (logger.isDebugEnabled())
            {
                logger.debug("Report Not Sent: Report is not active");
                logger.debug("Name: " + reportSchedule.getName());
            }
            return false;
        }

        // Rule 7:
        if (reportSchedule.getOccurrence().equals(Occurrence.MONTHLY) && startCalendar.get(Calendar.DAY_OF_MONTH) != currentDateTime.get(Calendar.DAY_OF_MONTH))
        {
            if (logger.isDebugEnabled())
            {
                logger.debug("Report Not Sent: Report is not scheduled to run on current day of month");
                logger.debug("Name: " + reportSchedule.getName());
            }
            return false;
        }

        return true;
    }

    private boolean isValidDayOfWeek(List<Boolean> daysOfWeek, int dayOfWeek)
    {
        Boolean returnBoolean = false;
        if (daysOfWeek != null)
        {
            for (int i = 0; i < daysOfWeek.size(); i++)
            {
                Boolean dayBoolean = daysOfWeek.get(i);
                if (i == (dayOfWeek - 1) && dayBoolean)
                {
                    returnBoolean = true;
                }
            }
        }

        return returnBoolean;
    }

    private int compareDates(Calendar date1, Calendar date2)
    {
        // Grab the integer values.. These values will be based on the time zones
        int day1 = date1.get(Calendar.DATE);
        int month1 = date1.get(Calendar.MONTH);
        int year1 = date1.get(Calendar.YEAR);

        int day2 = date2.get(Calendar.DATE);
        int month2 = date2.get(Calendar.MONTH);
        int year2 = date2.get(Calendar.YEAR);

        // Construct new calendars using the same time zone.
        Calendar cal1 = Calendar.getInstance();
        cal1.set(Calendar.MILLISECOND, 0);
        cal1.set(Calendar.MINUTE, 0);
        cal1.set(Calendar.SECOND, 0);
        cal1.set(Calendar.HOUR, 0);
        cal1.set(year1, month1, day1);

        Calendar cal2 = Calendar.getInstance();
        cal2.set(Calendar.MILLISECOND, 0);
        cal2.set(Calendar.MINUTE, 0);
        cal2.set(Calendar.SECOND, 0);
        cal2.set(Calendar.HOUR, 0);
        cal2.set(year2, month2, day2);

        return cal1.compareTo(cal2);
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
