package com.inthinc.pro.scheduler.quartz;

import com.amazonaws.AmazonClientException;
import com.inthinc.pro.dao.AccountDAO;
import com.inthinc.pro.dao.ReportScheduleDAO;
import com.inthinc.pro.dao.UserDAO;
import com.inthinc.pro.model.*;
import com.inthinc.pro.scheduler.amazonaws.sqs.AmazonQueueImpl;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.DateTimeZone;
import org.joda.time.Interval;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: michaelreinicke
 * Date: 6/20/13
 * Time: 1:49 PM
 * To change this template use File | Settings | File Templates.
 */
public class EmailReportAmazonPushJob extends QuartzJobBean {
    private static final Logger logger = Logger.getLogger(EmailReportAmazonPushJob.class);

    private AmazonQueueImpl amazonEmailReportQueue;
    private AccountDAO accountDAO;
    private ReportScheduleDAO reportScheduleDAO;
    private UserDAO userDAO;

    private Map<Integer, User> userMap;

    public AmazonQueueImpl getAmazonEmailReportQueue() {
        return amazonEmailReportQueue;
    }

    public void setAmazonEmailReportQueue(AmazonQueueImpl amazonEmailReportQueue) {
        this.amazonEmailReportQueue = amazonEmailReportQueue;
    }

    public AccountDAO getAccountDAO() {
        return accountDAO;
    }

    public void setAccountDAO(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    public ReportScheduleDAO getReportScheduleDAO() {
        return reportScheduleDAO;
    }

    public void setReportScheduleDAO(ReportScheduleDAO reportScheduleDAO) {
        this.reportScheduleDAO = reportScheduleDAO;
    }

    public UserDAO getUserDAO() {
        return userDAO;
    }

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        Long startExecutionTime = System.currentTimeMillis();

        for(ReportSchedule reportSchedule : this.getReportSchedules()) {
            logger.debug("Preparing reportSchedule " + reportSchedule.getName() + " ID: " + reportSchedule.getReportScheduleID() +
                    " for Amazon queue.");

            try {
                amazonEmailReportQueue.init();
                amazonEmailReportQueue.pushToQueue(String.valueOf(reportSchedule.getReportScheduleID()));

                User user = getUser(reportSchedule.getUserID());
                reportSchedule.setLastDate(new DateTime(DateTimeZone.forID(user.getPerson().getTimeZone().getID())).toDate());
                reportScheduleDAO.update(reportSchedule);

                logger.debug("Updated reportSchedule lastDate to " + reportSchedule.getLastDate());

            } catch (AmazonClientException e) {
                logger.error("Job was unable to add " + reportSchedule.getName() + " ID: " + reportSchedule.getReportScheduleID() + " to Amazon queue: " + e);
            }

            logger.info("Completed adding reportSchedule " + reportSchedule.getName() + " ID: " + reportSchedule.getReportScheduleID() +
                    " to the Amazon queue.");
        }

        Long endExecutionTime = System.currentTimeMillis();
        logger.debug("Time to add messages to Amazon queue: " + (endExecutionTime - startExecutionTime));
    }

    private List<ReportSchedule> getReportSchedules(){
        List<ReportSchedule> reportSchedules = new ArrayList<ReportSchedule>();

        Long startTime = System.currentTimeMillis();

        List<Account> accounts = accountDAO.getAllAcctIDs();
        logger.debug("Account Count: " + accounts.size());
        //initTextEncryptor();


        for (Account account : accounts) {
            if (isValidAccount(account)){
                reportSchedules.addAll(reportScheduleDAO.getReportSchedulesByAccountID(account.getAccountID()));
            }
        }

        Long endTime = System.currentTimeMillis();

        String timeInMillis = String.valueOf(endTime - startTime);

        logger.info("Time to get all the reportSchedules " + timeInMillis);

        return this.filterReportSchedules(reportSchedules);
    }

    private boolean isValidAccount(Account account){
        Account a = accountDAO.findByID(account.getAccountID());
        return a != null && a.getStatus() != null && !a.getStatus().equals(Status.DELETED);
    }

    private List<ReportSchedule> filterReportSchedules(List<ReportSchedule> reportScheduleList) {
        userMap = new HashMap<Integer,User>();


        List<ReportSchedule> retVal = new ArrayList<ReportSchedule>();

        long startTime = System.currentTimeMillis();

        for (ReportSchedule reportSchedule : reportScheduleList) {

            if(reportSchedule.getStatus().equals(Status.ACTIVE)){// If the reports status is not active, then the reports will no longer go out.
                User user = getUser(reportSchedule.getUserID());

                if (user != null && user.getStatus().equals(Status.ACTIVE)) { // If the users status is not active, then the reports will no longer go out for that user
                    if (isTimeToEmailReport(reportSchedule, user.getPerson())) {
                        retVal.add(reportSchedule);
                    }
                }
            }
        }

        long endTime = System.currentTimeMillis();

        String timeInMillis = String.valueOf(endTime - startTime);

        logger.info("Time to filter the reportschedules " + timeInMillis);

        return retVal;
    }

    public User getUser(Integer userID) {

        User user= userMap.get(userID);
        if(user == null){
            user = userDAO.findByID(userID);
            if (user != null){
                userMap.put(userID, user);
            }
        }
        return user;
    }

    /*
     * To determine if we should send a report out the following must be met.
     *
     * Rule 1a: Last time report was set is within the same interval.
     * Rule 1: Must be between the start date and end date;
     * Rule 2: OCCURRENCE.Weekly - Must be scheduled to run on the day of week it currently is.
     * Rule 3: The Scheduled Time cannot be greater than the current time.
     * Rule 4: The scheduled time has to be within the last hour from the current time. If not, it will not occur till the next scheduled date.
     * Rule 5: OCCURRENCE.Monthly - Make sure current day of month is the same as the start date day of month.
     * Rule 6: if the report was sent out within the past 24 hours, don't send again.
     */
    protected boolean isTimeToEmailReport(ReportSchedule reportSchedule, Person person) {
        DateTime currentDateTime = new DateTime(DateTimeZone.forID(person.getTimeZone().getID()));
        return isTimeToEmailReport(reportSchedule, person, currentDateTime);
    }
    protected boolean isTimeToEmailReport(ReportSchedule reportSchedule, Person person, DateTime currentDateTime ) {
        if (person == null) {
            logger.error("Unable to get Person record for userID" + reportSchedule.getUserID());
            return false;
        }

        int dayOfWeek = currentDateTime.getDayOfWeek();

        DateTime lastSentDateTime = reportSchedule.getLastDate() == null ? null : new DateTime(reportSchedule.getLastDate(),DateTimeZone.forID(person.getTimeZone().getID()));

        Integer scheduleDayOfMonth = new DateTime(reportSchedule.getStartDate(),DateTimeZone.forID("UTC")).getDayOfMonth();
        Integer currentDayOfMonth = new DateTime(currentDateTime,DateTimeZone.forID("UTC")).getDayOfMonth();
        DateTime startDateTime = new DateTime(reportSchedule.getStartDate(),DateTimeZone.forID(person.getTimeZone().getID()));
        DateTime endDateTime;
        if (reportSchedule.getEndDate() != null && reportSchedule.getEndDate().getTime() != 0l) {
            endDateTime = new DateTime(reportSchedule.getEndDate(),DateTimeZone.forID(person.getTimeZone().getID()));
        }
        else {
            endDateTime = new DateTime(currentDateTime);
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss a z");
        df.setTimeZone(person.getTimeZone());

        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss a z");
        sdf2.setTimeZone(person.getTimeZone());


        // Rule 1:
        if(currentDateTime.isBefore(startDateTime)){
            if (logger.isDebugEnabled()) {
                logger.debug("Report Not Sent: Current date is before start date");
                logger.debug("Name: " + reportSchedule.getName());
                logger.debug("Current Date Time: " + df.format(currentDateTime.toDate()));
                logger.debug("Start Date Time " + sdf2.format(startDateTime.toDate()));
            }
            return false;
        }

        if (endDateTime.isBefore(currentDateTime)) {
            if (logger.isDebugEnabled()) {
                logger.debug("Report Not Sent: Current date is after end date");
                logger.debug("Name: " + reportSchedule.getName());
                logger.debug("Current Date Time: " + df.format(currentDateTime.toDate()));
                logger.debug("End Date Time " + sdf2.format(endDateTime.toDate()));
            }
            return false;
        }

        if (reportSchedule.getOccurrence() == null) {
            return false;
        }

        // Rule 2:
        if (reportSchedule.getOccurrence().equals(Occurrence.WEEKLY) && !isValidDayOfWeek(reportSchedule.getDayOfWeek(), dayOfWeek)) {
            if (logger.isDebugEnabled()) {
                logger.debug("Report Not Sent: Report is not scheduled to run on current day of week");
                logger.debug("Name: " + reportSchedule.getName());
            }
            return false;
        }

        // Rule 3:
        Integer minutesInDay = currentDateTime.getMinuteOfDay();

        if (reportSchedule.getTimeOfDay() == null) {
            reportSchedule.setTimeOfDay(0);

        }

        if (minutesInDay < reportSchedule.getTimeOfDay()) {
            if (logger.isDebugEnabled()) {
                logger.debug("Report Not Sent: Report time is greater than current time");
                logger.debug("Name: " + reportSchedule.getName());
                logger.debug("Current Time: Hour " + (Integer) (minutesInDay / 60) + " Minutes " + (Integer) minutesInDay % 60);
                logger.debug("Time To Run Hour " + (Integer) (reportSchedule.getTimeOfDay() / 60) + " Minutes " + (Integer) reportSchedule.getTimeOfDay() % 60);
            }
            return false;
        }

        // Rule 4:
        if ((minutesInDay - 60) >= reportSchedule.getTimeOfDay()) {
            if (logger.isDebugEnabled()) {
                logger.debug("Report Not Sent: Report time was more than an hour before the current time");
                logger.debug("Name: " + reportSchedule.getName() + " ID: " + reportSchedule.getReportScheduleID());
                logger.debug("Current Time: Hour " + (Integer) (minutesInDay / 60) + " Minutes " + (Integer) minutesInDay % 60);
                logger.debug("Time To Run Hour " + (Integer) (reportSchedule.getTimeOfDay() / 60) + " Minutes " + (Integer) reportSchedule.getTimeOfDay() % 60);
            }
            return false;
        }


        // Rule 5:
        if (reportSchedule.getOccurrence().equals(Occurrence.MONTHLY) && !scheduleDayOfMonth.equals(currentDayOfMonth)) {
            if (logger.isDebugEnabled()) {
                logger.debug("Report Not Sent: Report is not scheduled to run on current day of month");
                logger.debug("Name: " + reportSchedule.getName());
            }
            return false;
        }

        // Rule 6:
        if (lastSentDateTime != null) {
            Interval interval = new Interval(currentDateTime.minusHours(23), currentDateTime);
            if(interval.contains(lastSentDateTime)) {
                logger.debug("Report Not Sent: Last time report sent was within past day (23 hours).");
                logger.debug("Name: " + reportSchedule.getName() + " ID: " + reportSchedule.getReportScheduleID());
                return false;
            }

        }
        return true;
    }

    private static Map<Integer, Integer> dayOfWeekMap = new HashMap<Integer, Integer>();
    static {
        dayOfWeekMap.put(0, DateTimeConstants.SUNDAY);
        dayOfWeekMap.put(1,DateTimeConstants.MONDAY);
        dayOfWeekMap.put(2,DateTimeConstants.TUESDAY);
        dayOfWeekMap.put(3,DateTimeConstants.WEDNESDAY);
        dayOfWeekMap.put(4,DateTimeConstants.THURSDAY);
        dayOfWeekMap.put(5,DateTimeConstants.FRIDAY);
        dayOfWeekMap.put(6,DateTimeConstants.SATURDAY);
    }

    boolean isValidDayOfWeek(List<Boolean> scheduledDaysOfWeek, int dayOfWeek) {
        Boolean returnBoolean = false;
        if (scheduledDaysOfWeek != null) {
            for (int scheduledDay = 0; scheduledDay < scheduledDaysOfWeek.size(); scheduledDay++) {
                Boolean isScheduledDay = scheduledDaysOfWeek.get(scheduledDay);
                if (isScheduledDay && dayOfWeekMap.get(scheduledDay) == dayOfWeek) {
                    returnBoolean = true;
                }
            }
        }

        return returnBoolean;
    }
}
