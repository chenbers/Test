package com.inthinc.pro.scheduler.quartz;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.apache.log4j.Logger;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Interval;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.inthinc.pro.dao.AccountDAO;
import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.dao.GroupDAO;
import com.inthinc.pro.dao.ReportScheduleDAO;
import com.inthinc.pro.dao.UserDAO;
import com.inthinc.pro.model.aggregation.DriverPerformance;
import com.inthinc.pro.model.Account;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Duration;
import com.inthinc.pro.model.EntityType;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.GroupHierarchy;
import com.inthinc.pro.model.MeasurementType;
import com.inthinc.pro.model.Occurrence;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.ReportParamType;
import com.inthinc.pro.model.ReportSchedule;
import com.inthinc.pro.model.Status;
import com.inthinc.pro.model.TimeFrame;
import com.inthinc.pro.model.User;
import com.inthinc.pro.reports.FormatType;
import com.inthinc.pro.reports.Report;
import com.inthinc.pro.reports.ReportCreator;
import com.inthinc.pro.reports.ReportCriteria;
import com.inthinc.pro.reports.ReportGroup;
import com.inthinc.pro.reports.ReportType;
import com.inthinc.pro.reports.performance.DriverPerformanceReportCriteria;
import com.inthinc.pro.reports.service.ReportCriteriaService;
import com.inthinc.pro.scheduler.i18n.LocalizedMessage;

/**
 * 
 * @author mstrong
 * 
 */

public class EmailReportJob extends QuartzJobBean {
    private static final Logger logger = Logger.getLogger(EmailReportJob.class);

    @SuppressWarnings("unchecked")
    private ReportCreator reportCreator;
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

    private static final long ONE_MINUTE = 60000L;
    private static final DateTimeZone dateTimeZone = DateTimeZone.forTimeZone(TimeZone.getTimeZone("GMT"));
    private static final String DEFAULT_NO_REPLY_EMAIL_ADDRESS = "noreply@inthinc.com";


    @Override
    protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
        logger.debug("START PROCCESSING SCHEDULED REPORTS");
        processReportSchedules();
        logger.debug("END PROCESSING SCHEDULED REPORTS");

    }

    private void processReportSchedules() {
        List<Account> accounts = accountDAO.getAllAcctIDs();
        logger.debug("Account Count: " + accounts.size());

        textEncryptor.setPassword(encryptPassword);
        textEncryptor.setStringOutputType("hexadecimal");

        List<ReportSchedule> reportSchedules = new ArrayList<ReportSchedule>();
        for (Account account : accounts) {
            Account a = accountDAO.findByID(account.getAcctID());
            if (a != null && a.getStatus() != null && !a.getStatus().equals(Status.DELETED))
                reportSchedules.addAll(reportScheduleDAO.getReportSchedulesByAccountID(account.getAcctID()));
            else {
                if (a == null) {
                    logger.info("no account found for account.getAcctID()");
                }
                if (logger.isDebugEnabled()) {
                    logger.debug("Account ID Deleted: " + account.getAcctID());
                }
            }
        }

        for (ReportSchedule reportSchedule : reportSchedules) {
            logger.debug("Begin Validation: " + reportSchedule.getName());
            User user = userDAO.findByID(reportSchedule.getUserID());
            if (user != null && user.getStatus().equals(Status.ACTIVE) && reportSchedule.getStatus().equals(Status.ACTIVE)) { // If the users status is not active, then the reports will no
                if (emailReport(reportSchedule, user.getPerson())) {
                    // longer go out for that user.
                    Calendar todaysDate = Calendar.getInstance(user.getPerson().getTimeZone());
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
                            processReportSchedule(reportSchedule, user.getPerson());
                        }
                        reportSchedule.setLastDate(todaysDate.getTime());
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

                            List<String> emailToList = new ArrayList<String>();
                            emailToList.add(driver.getPerson().getPriEmail());
                            reportSchedule.setDriverID(driver.getDriverID());
                            reportSchedule.setEmailTo(emailToList);
                            emailReport(reportSchedule, driver.getPerson(), driverReportCriteriaList, owner);
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

    @SuppressWarnings("unchecked")
    private void processReportSchedule(ReportSchedule reportSchedule, Person person) {
        ReportGroup reportGroup = ReportGroup.valueOf(reportSchedule.getReportID());
        if (reportGroup == null) {
        	logger.error("null reportGroup for schedule ID " + reportSchedule.getReportID());
        	return;
        }
        List<ReportCriteria> reportCriteriaList = new ArrayList<ReportCriteria>();
        for (int i = 0; i < reportGroup.getReports().length; i++) {
            Duration duration = reportSchedule.getReportDuration();
            if (duration == null) {
                duration = Duration.DAYS;
            }
            TimeFrame timeFrame = reportSchedule.getReportTimeFrame();
            if (timeFrame == null) {
                timeFrame = TimeFrame.TODAY;
            }
            if (reportGroup.getEntityType() == EntityType.ENTITY_GROUP && reportSchedule.getGroupID() == null ) {
                logger.error("no group id specified so skipping report id: " + reportSchedule.getReportScheduleID());
                continue;
            }
            switch (reportGroup.getReports()[i]) {
                case OVERALL_SCORE:
                    reportCriteriaList.add(reportCriteriaService.getOverallScoreReportCriteria(reportSchedule.getGroupID(), duration, person.getLocale()));
                    break;
                case TREND:
                    reportCriteriaList.add(reportCriteriaService.getTrendChartReportCriteria(reportSchedule.getGroupID(), duration, person.getLocale()));
                    break;
                case MPG_GROUP:
                    reportCriteriaList.add(reportCriteriaService.getMpgReportCriteria(reportSchedule.getGroupID(), duration, person.getLocale()));
                    break;
                case DEVICES_REPORT:
                    reportCriteriaList.add(reportCriteriaService.getDevicesReportCriteria(reportSchedule.getGroupID(), person.getLocale(), true));
                    break;
                case DRIVER_REPORT:
                    reportCriteriaList.add(reportCriteriaService.getDriverReportCriteria(reportSchedule.getGroupID(), duration, person.getLocale(), true));
                    break;
                case VEHICLE_REPORT:
                    reportCriteriaList.add(reportCriteriaService.getVehicleReportCriteria(reportSchedule.getGroupID(), duration, person.getLocale(), true));
                    break;
                case IDLING_REPORT:
                    Interval interval = new Interval(new DateMidnight(new DateTime().minusWeeks(1), dateTimeZone), new DateMidnight(new DateTime(), dateTimeZone).toDateTime().plusDays(1).minus(ONE_MINUTE));
                    reportCriteriaList.add(reportCriteriaService.getIdlingReportCriteria(reportSchedule.getGroupID(), interval, person.getLocale(), true));
                    break;
                case TEAM_STATISTICS_REPORT:
                    reportCriteriaList.add(reportCriteriaService.getTeamStatisticsReportCriteria(reportSchedule.getGroupID(), timeFrame, 
                    		DateTimeZone.forTimeZone(person.getTimeZone()), person.getLocale(), true));
                	break;
                case TEAM_STOPS_REPORT:
                    reportCriteriaList.add(reportCriteriaService.getTeamStopsReportCriteria(reportSchedule.getGroupID(), timeFrame, 
                            DateTimeZone.forTimeZone(person.getTimeZone()), person.getLocale(), true));
                    break;     
                case HOS_DAILY_DRIVER_LOG_REPORT:
                    if (reportSchedule.getParamType() == ReportParamType.DRIVER )
                        reportCriteriaList.addAll(reportCriteriaService.getHosDailyDriverLogReportCriteria(getAccountGroupHierarchy(reportSchedule.getAccountID()), reportSchedule.getDriverID(), 
                            timeFrame.getInterval(), person.getLocale(), person.getMeasurementType() == MeasurementType.METRIC));
                    else 
                        reportCriteriaList.addAll(getReportCriteriaService().getHosDailyDriverLogReportCriteria(getAccountGroupHierarchy(reportSchedule.getAccountID()),  
                                reportSchedule.getGroupIDList(),timeFrame.getInterval(),  person.getLocale(), person.getMeasurementType() == MeasurementType.METRIC));
                    break;
                case HOS_VIOLATIONS_SUMMARY_REPORT:
                    reportCriteriaList.add(reportCriteriaService.getHosViolationsSummaryReportCriteria(getAccountGroupHierarchy(reportSchedule.getAccountID()), 
                            reportSchedule.getGroupIDList(), timeFrame.getInterval(), 
                            person.getLocale()));
                    break;
                case HOS_VIOLATIONS_DETAIL_REPORT:
                    if (reportSchedule.getParamType() == ReportParamType.DRIVER )
                        reportCriteriaList.add(reportCriteriaService.getHosViolationsDetailReportCriteria(getAccountGroupHierarchy(reportSchedule.getAccountID()), reportSchedule.getDriverID(), timeFrame.getInterval(), 
                            person.getLocale()));
                    else
                        reportCriteriaList.add(reportCriteriaService.getHosViolationsDetailReportCriteria(getAccountGroupHierarchy(reportSchedule.getAccountID()), reportSchedule.getGroupIDList(), timeFrame.getInterval(), 
                                person.getLocale()));
                    break;
                case HOS_DRIVER_DOT_LOG_REPORT:
                    reportCriteriaList.add(reportCriteriaService.getHosDriverDOTLogReportCriteria(reportSchedule.getDriverID(), timeFrame.getInterval(), 
                            person.getLocale()));
                    break;
                case DOT_HOURS_REMAINING:
                    reportCriteriaList.add(reportCriteriaService.getDotHoursRemainingReportCriteria(getAccountGroupHierarchy(reportSchedule.getAccountID()), 
                            reportSchedule.getGroupIDList(),  
                            person.getLocale()));
                    break;
                case HOS_ZERO_MILES:
                    reportCriteriaList.add(reportCriteriaService.getHosZeroMilesReportCriteria(getAccountGroupHierarchy(reportSchedule.getAccountID()), 
                            reportSchedule.getGroupIDList(), timeFrame.getInterval(),  
                            person.getLocale()));
                    break;
                case HOS_EDITS:
                    reportCriteriaList.add(reportCriteriaService.getHosEditsReportCriteria(getAccountGroupHierarchy(reportSchedule.getAccountID()), 
                            reportSchedule.getGroupIDList(), timeFrame.getInterval(),  
                            person.getLocale()));
                    break;
                case PAYROLL_DETAIL:
                    reportCriteriaList.add(reportCriteriaService.getPayrollDetailReportCriteria(getAccountGroupHierarchy(reportSchedule.getAccountID()), 
                            reportSchedule.getGroupIDList(), timeFrame.getInterval(),  
                            person.getLocale()));
                    break;
                case PAYROLL_SIGNOFF:
                    reportCriteriaList.add(reportCriteriaService.getPayrollSignoffReportCriteria(getAccountGroupHierarchy(reportSchedule.getAccountID()), reportSchedule.getDriverID(), timeFrame.getInterval(),  
                            person.getLocale()));
                    break;
                case PAYROLL_SUMMARY:
                    reportCriteriaList.add(reportCriteriaService.getPayrollSummaryReportCriteria(getAccountGroupHierarchy(reportSchedule.getAccountID()), 
                            reportSchedule.getGroupIDList(), timeFrame.getInterval(),  
                            person.getLocale()));
                    break;
                    
                    
                case TEN_HOUR_DAY_VIOLATIONS:
                	reportCriteriaList.add(reportCriteriaService.getTenHoursDayViolationsCriteria(getAccountGroupHierarchy(reportSchedule.getAccountID()),reportSchedule.getGroupID(), timeFrame.getInterval(), person.getLocale()));
                	break;
                	
                case DRIVER_HOURS:
                	reportCriteriaList.add(reportCriteriaService.getDriverHoursReportCriteria(getAccountGroupHierarchy(reportSchedule.getAccountID()),reportSchedule.getGroupID(), timeFrame.getInterval(), person.getLocale()));
                	break;
                	
                case MILEAGE_BY_VEHICLE:
                	reportCriteriaList.add(reportCriteriaService.getMileageByVehicleReportCriteria(getAccountGroupHierarchy(reportSchedule.getAccountID()),
                			reportSchedule.getGroupIDList(), timeFrame.getInterval(),  
                			person.getLocale(), person.getMeasurementType(), reportSchedule.getIftaOnly()));
                	break;
                case STATE_MILEAGE_BY_VEHICLE:
                	reportCriteriaList.add(reportCriteriaService.getStateMileageByVehicleReportCriteria(getAccountGroupHierarchy(reportSchedule.getAccountID()),
                			reportSchedule.getGroupIDList(), timeFrame.getInterval(),  
                			person.getLocale(), person.getMeasurementType(), reportSchedule.getIftaOnly()));
                	break;
                case STATE_MILEAGE_BY_VEHICLE_ROAD_STATUS:
                	reportCriteriaList.add(reportCriteriaService.getStateMileageByVehicleRoadStatusReportCriteria(getAccountGroupHierarchy(reportSchedule.getAccountID()),
                			reportSchedule.getGroupIDList(), timeFrame.getInterval(),  
                			person.getLocale(), person.getMeasurementType(), reportSchedule.getIftaOnly()));
                	break;
                case STATE_MILEAGE_COMPARE_BY_GROUP:
                	reportCriteriaList.add(reportCriteriaService.getStateMileageCompareByGroupReportCriteria(getAccountGroupHierarchy(reportSchedule.getAccountID()),
                			reportSchedule.getGroupIDList(), timeFrame.getInterval(),  
                			person.getLocale(), person.getMeasurementType(), reportSchedule.getIftaOnly()));
                	break;
                case STATE_MILEAGE_BY_MONTH:
                	reportCriteriaList.add(reportCriteriaService.getStateMileageByMonthReportCriteria(getAccountGroupHierarchy(reportSchedule.getAccountID()),
                			reportSchedule.getGroupIDList(), timeFrame.getInterval(),  
                			person.getLocale(), person.getMeasurementType(), reportSchedule.getIftaOnly()));
                	break;
                case STATE_MILEAGE_FUEL_BY_VEHICLE:
                	reportCriteriaList.add(reportCriteriaService.getStateMileageFuelByVehicleReportCriteria(getAccountGroupHierarchy(reportSchedule.getAccountID()),
                			reportSchedule.getGroupIDList(), timeFrame.getInterval(),  
                			person.getLocale(), person.getMeasurementType(), reportSchedule.getIftaOnly()));
                	break;
                case DRIVING_TIME_VIOLATIONS_SUMMARY_REPORT:
                    reportCriteriaList.add(getReportCriteriaService().getDrivingTimeViolationsSummaryReportCriteria(getAccountGroupHierarchy(reportSchedule.getAccountID()), 
                            reportSchedule.getGroupIDList(), timeFrame.getInterval(),  
                            person.getLocale()));
                    break;
                case DRIVING_TIME_VIOLATIONS_DETAIL_REPORT:
                    if (reportSchedule.getParamType() == ReportParamType.DRIVER )
                        reportCriteriaList.add(getReportCriteriaService().getDrivingTimeViolationsDetailReportCriteria(getAccountGroupHierarchy(reportSchedule.getAccountID()), 
                                reportSchedule.getDriverID(), timeFrame.getInterval(), 
                                person.getLocale()));
                    else
                        reportCriteriaList.add(getReportCriteriaService().getDrivingTimeViolationsDetailReportCriteria(getAccountGroupHierarchy(reportSchedule.getAccountID()), 
                                reportSchedule.getGroupIDList(), timeFrame.getInterval(),  
                                person.getLocale()));
                    break;

                
                case NON_DOT_VIOLATIONS_SUMMARY_REPORT:
                    reportCriteriaList.add(getReportCriteriaService().getNonDOTViolationsSummaryReportCriteria(getAccountGroupHierarchy(reportSchedule.getAccountID()),
                            reportSchedule.getGroupIDList(), timeFrame.getInterval(),  
                            person.getLocale()));
                    break;
                case NON_DOT_VIOLATIONS_DETAIL_REPORT:
                    if (reportSchedule.getParamType() == ReportParamType.DRIVER )
                        reportCriteriaList.add(getReportCriteriaService().getNonDOTViolationsDetailReportCriteria(getAccountGroupHierarchy(reportSchedule.getAccountID()), 
                                reportSchedule.getDriverID(), timeFrame.getInterval(), 
                                person.getLocale()));
                    else
                        reportCriteriaList.add(getReportCriteriaService().getNonDOTViolationsDetailReportCriteria(getAccountGroupHierarchy(reportSchedule.getAccountID()), 
                                reportSchedule.getGroupIDList(), timeFrame.getInterval(),  
                                person.getLocale()));
                    break;
                
                case DRIVER_PERFORMANCE_TEAM:
                case DRIVER_PERFORMANCE_RYG_TEAM:
                    Boolean ryg = (reportGroup.getReports()[i] == ReportType.DRIVER_PERFORMANCE_RYG_TEAM);
                    reportCriteriaList.add(getReportCriteriaService().getDriverPerformanceReportCriteria(getAccountGroupHierarchy(reportSchedule.getAccountID()), 
                            reportSchedule.getGroupID(), timeFrame.getInterval(),  
                            person.getLocale(), ryg));
                    break;

                default:
                    break;

            }
        }
        emailReport(reportSchedule, person, reportCriteriaList, null);

    }

    private void emailReport(ReportSchedule reportSchedule, Person person, List<ReportCriteria> reportCriteriaList, Person owner) {
        // Set the current date of the reports
        for (ReportCriteria reportCriteria : reportCriteriaList) {
            reportCriteria.setReportDate(new Date(), person.getTimeZone());
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
    protected boolean emailReport(ReportSchedule reportSchedule, Person person) {
        if (person == null) {
            logger.error("Unable to get Person record for userID" + reportSchedule.getUserID());
            return false;
        }
        Calendar currentDateTime = Calendar.getInstance(person.getTimeZone());
        int dayOfWeek = currentDateTime.get(Calendar.DAY_OF_WEEK);
        currentDateTime.setTimeZone(person.getTimeZone());

        Calendar startCalendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        startCalendar.setTime(reportSchedule.getStartDate());

        Calendar endCalendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        if (reportSchedule.getEndDate() != null && reportSchedule.getEndDate().getTime() != 0l) {
            endCalendar.setTime(reportSchedule.getEndDate());
        }

        SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss a z");
        df.setTimeZone(person.getTimeZone());

        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss a z");
        sdf2.setTimeZone(TimeZone.getTimeZone("www."));

        // Rule 1:
        if (compareDates(currentDateTime, startCalendar) < 0) {
            if (logger.isDebugEnabled()) {
                logger.debug("Report Not Sent: Current date is before start date");
                logger.debug("Name: " + reportSchedule.getName());
                logger.debug("Current Date Time: " + df.format(currentDateTime.getTime()));
                logger.debug("Start Date Time " + sdf2.format(startCalendar.getTime()));
            }
            return false;
        }

        if (reportSchedule.getEndDate() != null && reportSchedule.getEndDate().getTime() != 0l && compareDates(currentDateTime, endCalendar) > 0) {
            if (logger.isDebugEnabled()) {
                logger.debug("Report Not Sent: Current date is after end date");
                logger.debug("Name: " + reportSchedule.getName());
                logger.debug("Current Date Time: " + df.format(currentDateTime.getTime()));
                logger.debug("End Date Time " + sdf2.format(endCalendar.getTime()));
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
        Integer currentTimeInMinutes = currentDateTime.get(Calendar.HOUR_OF_DAY) * 60;
        currentTimeInMinutes += currentDateTime.get(Calendar.MINUTE);

        if (reportSchedule.getTimeOfDay() == null) {
            reportSchedule.setTimeOfDay(0);

        }

        if (currentTimeInMinutes < reportSchedule.getTimeOfDay()) {
            if (logger.isDebugEnabled()) {
                logger.debug("Report Not Sent: Report time is greater than current time");
                logger.debug("Name: " + reportSchedule.getName());
                logger.debug("Current Time: Hour " + (Integer) (currentTimeInMinutes / 60) + " Minutes " + (Integer) currentTimeInMinutes % 60);
                logger.debug("Time To Run Hour " + (Integer) (reportSchedule.getTimeOfDay() / 60) + " Minutes " + (Integer) reportSchedule.getTimeOfDay() % 60);
            }
            return false;
        }

        // Rule 4:
        if ((currentTimeInMinutes - 60) >= reportSchedule.getTimeOfDay()) {
            if (logger.isDebugEnabled()) {
                logger.debug("Report Not Sent: Report time was more than an hour before the current time");
                logger.debug("Name: " + reportSchedule.getName() + " ID: " + reportSchedule.getReportScheduleID());
                logger.debug("Current Time: Hour " + (Integer) (currentTimeInMinutes / 60) + " Minutes " + (Integer) currentTimeInMinutes % 60);
                logger.debug("Time To Run Hour " + (Integer) (reportSchedule.getTimeOfDay() / 60) + " Minutes " + (Integer) reportSchedule.getTimeOfDay() % 60);
            }
            return false;
        }

        // Rule 5:
        if (reportSchedule.getStatus() != null && !reportSchedule.getStatus().equals(Status.ACTIVE)) {
            if (logger.isDebugEnabled()) {
                logger.debug("Report Not Sent: Report is not active");
                logger.debug("Name: " + reportSchedule.getName());
            }
            return false;
        }

        // Rule 7:
        if (reportSchedule.getOccurrence().equals(Occurrence.MONTHLY) && startCalendar.get(Calendar.DAY_OF_MONTH) != currentDateTime.get(Calendar.DAY_OF_MONTH)) {
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

    private int compareDates(Calendar date1, Calendar date2) {
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

    public void setReportCreator(ReportCreator reportCreator) {
        this.reportCreator = reportCreator;
    }

    public ReportCreator getReportCreator() {
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


    public DriverDAO getDriverDAO() {
        return driverDAO;
    }

    public void setDriverDAO(DriverDAO driverDAO) {
        this.driverDAO = driverDAO;
    }

}
