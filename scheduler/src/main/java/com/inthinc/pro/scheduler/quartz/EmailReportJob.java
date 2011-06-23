package com.inthinc.pro.scheduler.quartz;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.inthinc.pro.dao.AccountDAO;
import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.dao.GroupDAO;
import com.inthinc.pro.dao.ReportScheduleDAO;
import com.inthinc.pro.dao.UserDAO;
import com.inthinc.pro.model.Account;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.EntityType;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.GroupHierarchy;
import com.inthinc.pro.model.MeasurementType;
import com.inthinc.pro.model.Occurrence;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.ReportSchedule;
import com.inthinc.pro.model.Status;
import com.inthinc.pro.model.TimeFrame;
import com.inthinc.pro.model.User;
import com.inthinc.pro.model.aggregation.DriverPerformance;
import com.inthinc.pro.reports.FormatType;
import com.inthinc.pro.reports.Report;
import com.inthinc.pro.reports.ReportCreator;
import com.inthinc.pro.reports.ReportCriteria;
import com.inthinc.pro.reports.ReportGroup;
import com.inthinc.pro.reports.ReportType;
import com.inthinc.pro.reports.service.ReportCriteriaService;
import com.inthinc.pro.scheduler.i18n.LocalizedMessage;

/**
 * 
 * @author mstrong
 * 
 */

public class EmailReportJob extends QuartzJobBean {
    private static final Logger logger = Logger.getLogger(EmailReportJob.class);

//    @SuppressWarnings("unchecked")
    private ReportCreator<Report> reportCreator;
    private ReportCriteriaService reportCriteriaService;
    private ReportScheduleDAO reportScheduleDAO;
    private UserDAO userDAO;
    private GroupDAO groupDAO;
    private AccountDAO accountDAO;
    private DriverDAO driverDAO;

    private String webContextPath;
    private String encryptPassword;
    private StandardPBEStringEncryptor textEncryptor = new StandardPBEStringEncryptor();

    private Map<Integer, GroupHierarchy> accountGroupHierarchyMap;
    private Map<Integer, User> userMap;
    private static final String DEFAULT_NO_REPLY_EMAIL_ADDRESS = "noreply@inthinc.com";


    @Override
    protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
        logger.debug("START PROCCESSING SCHEDULED REPORTS");
        List<ReportSchedule> reportSchedules = getReportSchedules();
        dispatchReports(reportSchedules);
        logger.debug("END PROCESSING SCHEDULED REPORTS");

    }

    private List<ReportSchedule> getReportSchedules(){
        
        List<Account> accounts = accountDAO.getAllAcctIDs();
        logger.debug("Account Count: " + accounts.size());

        textEncryptor.setPassword(encryptPassword);
        textEncryptor.setStringOutputType("hexadecimal");

        List<ReportSchedule> reportSchedules = new ArrayList<ReportSchedule>();
        for (Account account : accounts) {
            if (isValidAccount(account)){
                reportSchedules.addAll(reportScheduleDAO.getReportSchedulesByAccountID(account.getAccountID()));
            }
        }
        return reportSchedules;
    }
    private boolean isValidAccount(Account account){
        Account a = accountDAO.findByID(account.getAccountID());
        if (a == null) {
            logger.info("no account found for account.getAcctID()");
        }
        if ( logger.isDebugEnabled() && (a == null || account.getStatus() == null || account.getStatus().equals(Status.DELETED))) {
            logger.debug("Account ID Deleted: " + account.getAccountID());
        }
        return a != null && a.getStatus() != null && !a.getStatus().equals(Status.DELETED);
    }
    private void dispatchReports(List<ReportSchedule> reportSchedules){
        userMap = new HashMap<Integer,User>();
        for (ReportSchedule reportSchedule : reportSchedules) {
            logger.debug("Begin Validation: " + reportSchedule.getName());
            if(reportSchedule.getStatus().equals(Status.ACTIVE)){// If the reports status is not active, then the reports will no longer go out.
                User user = getUser(reportSchedule.getUserID());
                if (user != null && user.getStatus().equals(Status.ACTIVE)) { // If the users status is not active, then the reports will no longer go out for that user
                    if (isTimeToEmailReport(reportSchedule, user.getPerson())) {
                        if (logger.isDebugEnabled()) {
                            logger.debug("-------BEGIN PROCESSING REPORT-------");
                            logger.debug(reportSchedule.toString());
                        }
                        try {
                            ReportGroup reportGroup = ReportGroup.valueOf(reportSchedule.getReportID());
                            if (reportGroup == null) {
                                logger.error("null reportGroup for schedule ID " + reportSchedule.getReportID());
                                continue;
                            }
                            if (reportGroup.getEntityType() == EntityType.ENTITY_INDIVIDUAL_DRIVER) {
                                
                                processIndividualDriverReportSchedule(reportSchedule, user.getPerson());
                                
                            }
                            else {
                                List<ReportCriteria> reportCriteriaList = reportCriteriaService.getReportCriteria(reportSchedule, getAccountGroupHierarchy(reportSchedule.getAccountID()),  user.getPerson());
                                emailReport(reportSchedule, user.getPerson(), reportCriteriaList, null);
                            }
                            reportSchedule.setLastDate(new DateTime(DateTimeZone.forID(user.getPerson().getTimeZone().getID())).toDate());
                            Integer modified = reportScheduleDAO.update(reportSchedule);
                            if (logger.isDebugEnabled()) {
                                logger.debug("UPDATE RESULT " + modified);
                                logger.debug("-------------END REPORT-------------");
                            }
                        }
                        catch (Throwable t) {
                            // log the exception, but keep processing the rest of the the reports
                            logger.error(t);
                        }
                    }
                }
            }
        }
    }
    
    private void processIndividualDriverReportSchedule(ReportSchedule reportSchedule, Person person) {
        List<Integer> driverIDList = reportSchedule.getDriverIDList();
        List<Driver> driverList = driverDAO.getAllDrivers(reportSchedule.getGroupID());
        ReportGroup reportGroup = ReportGroup.valueOf(reportSchedule.getReportID());
        for (int i = 0; i < reportGroup.getReports().length; i++) {
            TimeFrame timeFrame = reportSchedule.getReportTimeFrame();
            if (timeFrame == null) {
                timeFrame = TimeFrame.TODAY;
            }
            
            Person owner = null;
            if (reportSchedule.getUserID() != null) {
                User user = userDAO.findByID(reportSchedule.getUserID());
                if (user != null)
                    owner = user.getPerson();
                
            }
            switch (reportGroup.getReports()[i]) {
                case DRIVER_PERFORMANCE_INDIVIDUAL:
                case DRIVER_PERFORMANCE_RYG_INDIVIDUAL:
                    Boolean ryg = (reportGroup.getReports()[i] == ReportType.DRIVER_PERFORMANCE_RYG_INDIVIDUAL);
                    
                    List<ReportCriteria> rcList = getReportCriteriaService().getDriverPerformanceIndividualReportCriteria(
                            getAccountGroupHierarchy(reportSchedule.getAccountID()), 
                            reportSchedule.getGroupID(), driverIDList,
                            timeFrame.getInterval(), person.getLocale(), ryg);
                    
                    for (Integer driverID : driverIDList) {
                        Driver driver = findDriver(driverList, driverID);
                        if (driver.getPerson().getPriEmail() == null || driver.getPerson().getPriEmail().isEmpty())
                            logger.info("Skipping driver with no Primary E-Mail address: " + driver.getPerson().getFullName());
                        else {
                            List<ReportCriteria> driverReportCriteriaList = new ArrayList<ReportCriteria>();
                            for (ReportCriteria rc : rcList) {
                                if (rc.getMainDataset() == null || rc.getMainDataset().isEmpty())
                                    continue;
                                DriverPerformance dp = (DriverPerformance)rc.getMainDataset().get(0);
                                if (dp.getDriverID().equals(driverID)) {
                                    driverReportCriteriaList.add(rc);
                                    break;
                                }
                            }
                            if (!driverReportCriteriaList.isEmpty()) {
                                List<String> emailToList = new ArrayList<String>();
                                emailToList.add(driver.getPerson().getPriEmail());
                                reportSchedule.setDriverID(driver.getDriverID());
                                reportSchedule.setEmailTo(emailToList);
                                emailReport(reportSchedule, driver.getPerson(), driverReportCriteriaList, owner);
                            }
                        }
                    }
                    break;
            }
        }
        reportSchedule.setDriverID(null);
        reportSchedule.setEmailTo(null);
    }
    
    
    private Driver findDriver(List<Driver> driverList, Integer driverID) {
        
        for (Driver driver : driverList) {
            if (driver.getDriverID().equals(driverID))
                return driver;
        }
        return null;
    }

    private void emailReport(ReportSchedule reportSchedule, Person person, List<ReportCriteria> reportCriteriaList, Person owner) {
        // Set the current date of the reports
        for (ReportCriteria reportCriteria : reportCriteriaList) {
            reportCriteria.setReportDate(new DateTime().toDate(), person.getTimeZone());
            reportCriteria.setLocale(person.getLocale());
            reportCriteria.setUseMetric((person.getMeasurementType() != null && person.getMeasurementType().equals(MeasurementType.METRIC)));
            reportCriteria.setMeasurementType(person.getMeasurementType());
            reportCriteria.setFuelEfficiencyType(person.getFuelEfficiencyType());
        }

        Report report = reportCreator.getReport(reportCriteriaList);
        for (String address : reportSchedule.getEmailTo()) {
            String subject = LocalizedMessage.getString("reportSchedule.emailSubject", person.getLocale()) + reportSchedule.getName();
            String unsubscribeURL = buildUnsubscribeURL(address, reportSchedule.getReportScheduleID());
            String message = LocalizedMessage.getStringWithValues("reportSchedule.emailMessage", person.getLocale(), 
                    (owner == null) ? person.getFullName() : owner.getFullName(), 
                    (owner == null) ? person.getPriEmail() : owner.getPriEmail(),
                    unsubscribeURL);
            
            // Change noreplyemail address based on account
            String noReplyEmailAddress = DEFAULT_NO_REPLY_EMAIL_ADDRESS;
            Account acct = accountDAO.findByID(reportSchedule.getAccountID());
            if (    (acct.getProps() != null) && 
                    (acct.getProps().getNoReplyEmail() != null) && 
                    (acct.getProps().getNoReplyEmail().trim().length() > 0) ) {
                noReplyEmailAddress = acct.getProps().getNoReplyEmail();
            }
            
            report.exportReportToEmail(address, FormatType.PDF, message, subject, noReplyEmailAddress);
        }
    }

    private String buildUnsubscribeURL(final String emailAddress, final Integer reportScheduleID) {
        StringBuilder unsubscribeURLBuilder = new StringBuilder(webContextPath);
        unsubscribeURLBuilder.append("unsubscribe/report/");
        unsubscribeURLBuilder.append(textEncryptor.encrypt(emailAddress));
        unsubscribeURLBuilder.append("/");
        unsubscribeURLBuilder.append(reportScheduleID);
        return unsubscribeURLBuilder.toString();
    }

    /*
     * To determine if we should send a report out the following must be met.
     * 
     * Rule 1: Must be between the start date and end date; Rule 2: OCCURRENCE.Weekly - Must be scheduled to run on the day of week it currently is. Rule 3: The Schduled Time
     * cannot be greater than the current time. Rule 4: The scheduled time has to be within the last hour from the current time. If not, it will not occurr till the next scheduled
     * date. Rule 5: Report Schedule must be active. Rule 7: OCCURRENCE.Monthly - Make sure currenty day of month is the same ans the start date day of month.
     */
    protected boolean isTimeToEmailReport(ReportSchedule reportSchedule, Person person) {
        if (person == null) {
            logger.error("Unable to get Person record for userID" + reportSchedule.getUserID());
            return false;
        }
        DateTime currentDateTime = new DateTime(DateTimeZone.forID(person.getTimeZone().getID()));
        int dayOfWeek = currentDateTime.getDayOfWeek();
//        Calendar currentDateTime = Calendar.getInstance(person.getTimeZone());
//        int dayOfWeek = currentDateTime.get(Calendar.DAY_OF_WEEK);
//        currentDateTime.setTimeZone(person.getTimeZone());

        DateTime startDateTime = new DateTime(reportSchedule.getStartDate(),DateTimeZone.forID(person.getTimeZone().getID()));
//        Calendar startCalendar = Calendar.getInstance(person.getTimeZone());
//        startCalendar.setTime(reportSchedule.getStartDate());

        DateTime endDateTime;
//        Calendar endCalendar = Calendar.getInstance(person.getTimeZone());
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
//        if (compareDates(currentDateTime, startCalendar) < 0) {
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
//        Integer currentTimeInMinutes = currentDateTime.get(Calendar.HOUR_OF_DAY) * 60;
//        currentTimeInMinutes += currentDateTime.get(Calendar.MINUTE);
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

        // Rule 5: Tested for active status before calling this
//        if (reportSchedule.getStatus() != null && !reportSchedule.getStatus().equals(Status.ACTIVE)) {
//            if (logger.isDebugEnabled()) {
//                logger.debug("Report Not Sent: Report is not active");
//                logger.debug("Name: " + reportSchedule.getName());
//            }
//            return false;
//        }

        // Rule 7:
        if (reportSchedule.getOccurrence().equals(Occurrence.MONTHLY) && startDateTime.getDayOfMonth() != currentDateTime.getDayOfMonth()) {
            if (logger.isDebugEnabled()) {
                logger.debug("Report Not Sent: Report is not scheduled to run on current day of month");
                logger.debug("Name: " + reportSchedule.getName());
            }
            return false;
        }

        return true;
    }

    private boolean isValidDayOfWeek(List<Boolean> daysOfWeek, int dayOfWeek) {
        Boolean returnBoolean = false;
        if (daysOfWeek != null) {
            for (int i = 0; i < daysOfWeek.size(); i++) {
                Boolean dayBoolean = daysOfWeek.get(i);
                if (i == (dayOfWeek - 1) && dayBoolean) {
                    returnBoolean = true;
                }
            }
        }

        return returnBoolean;
    }

    public void setReportCreator(ReportCreator<Report> reportCreator) {
        this.reportCreator = reportCreator;
    }

    public ReportCreator<Report> getReportCreator() {
        return reportCreator;
    }

    public void setReportCriteriaService(ReportCriteriaService reportCriteriaService) {
        this.reportCriteriaService = reportCriteriaService;
    }

    public ReportCriteriaService getReportCriteriaService() {
        return reportCriteriaService;
    }

    public void setReportScheduleDAO(ReportScheduleDAO reportScheduleDAO) {
        this.reportScheduleDAO = reportScheduleDAO;
    }

    public ReportScheduleDAO getReportScheduleDAO() {
        return reportScheduleDAO;
    }

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public UserDAO getUserDAO() {
        return userDAO;
    }

    public AccountDAO getAccountDAO() {
        return accountDAO;
    }

    public void setAccountDAO(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    public GroupDAO getGroupDAO() {
        return groupDAO;
    }

    public void setGroupDAO(GroupDAO groupDAO) {
        this.groupDAO = groupDAO;
    }


    public void setWebContextPath(String webContextPath) {
        this.webContextPath = webContextPath;
    }

    public String getWebContextPath() {
        return webContextPath;
    }

    public void setEncryptPassword(String encryptPassword) {
        this.encryptPassword = encryptPassword;
    }

    public String getEncryptPassword() {
        return encryptPassword;
    }
    public Map<Integer, GroupHierarchy> getAccountGroupHierarchyMap() {
        if (accountGroupHierarchyMap == null)
            accountGroupHierarchyMap = new HashMap<Integer, GroupHierarchy>();
        return accountGroupHierarchyMap;
    }

    public void setAccountGroupHierarchyMap(Map<Integer, GroupHierarchy> accountGroupHierarchyMap) {
        this.accountGroupHierarchyMap = accountGroupHierarchyMap;
    }
    
    public GroupHierarchy getAccountGroupHierarchy(Integer accountID) {
        Map<Integer, GroupHierarchy> map = getAccountGroupHierarchyMap();
        if (map.get(accountID) == null) {
            List<Group> groupList = groupDAO.getGroupsByAcctID(accountID);
            map.put(accountID, new GroupHierarchy(groupList));
        }
        
        return map.get(accountID);
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

    public DriverDAO getDriverDAO() {
        return driverDAO;
    }

    public void setDriverDAO(DriverDAO driverDAO) {
        this.driverDAO = driverDAO;
    }

}
