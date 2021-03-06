package com.inthinc.pro.scheduler.quartz;

import com.amazonaws.AmazonClientException;
import com.amazonaws.services.sqs.model.Message;
import com.inthinc.pro.dao.AccountDAO;
import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.dao.GroupDAO;
import com.inthinc.pro.dao.PersonDAO;
import com.inthinc.pro.dao.ReportScheduleDAO;
import com.inthinc.pro.dao.UserDAO;
import com.inthinc.pro.model.Account;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.EntityType;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.GroupHierarchy;
import com.inthinc.pro.model.GroupType;
import com.inthinc.pro.model.MeasurementType;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.ReportManagerDeliveryType;
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
import com.inthinc.pro.scheduler.amazonaws.sqs.AmazonQueue;
import com.inthinc.pro.scheduler.data.JSONReportLogData;
import com.inthinc.pro.scheduler.data.ReportLogData;
import com.inthinc.pro.scheduler.i18n.LocalizedMessage;
import org.apache.log4j.Logger;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.BeanUtils;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: michaelreinicke
 * Date: 6/20/13
 * Time: 4:10 PM
 * To change this template use File | Settings | File Templates.
 */
public class EmailReportAmazonPullJob extends QuartzJobBean {
    private static final Logger logger = Logger.getLogger(EmailReportAmazonPullJob.class);

    private static final String DEFAULT_NO_REPLY_EMAIL_ADDRESS = "noreply@inthinc.com";

    private AmazonQueue amazonQueue;
    private ReportCreator<Report> reportCreator;
    private ReportCriteriaService reportCriteriaService;
    private AccountDAO accountDAO;
    private DriverDAO driverDAO;
    private GroupDAO groupDAO;
    private PersonDAO personDAO;
    private ReportScheduleDAO reportScheduleDAO;
    private UserDAO userDAO;
    private String webContextPath;
    private String encryptPassword;
    private StandardPBEStringEncryptor textEncryptor = new StandardPBEStringEncryptor();
    private Map<Integer, User> userMap;
    private Map<Integer, GroupHierarchy> accountGroupHierarchyMap;
    private ReportLogData reportLogData;

    public AmazonQueue getAmazonQueue() {
        return amazonQueue;
    }

    public void setAmazonQueue(AmazonQueue amazonQueue) {
        this.amazonQueue = amazonQueue;
    }

    public ReportCreator<Report> getReportCreator() {
        return reportCreator;
    }

    public void setReportCreator(ReportCreator<Report> reportCreator) {
        this.reportCreator = reportCreator;
    }

    public ReportCriteriaService getReportCriteriaService() {
        return reportCriteriaService;
    }

    public void setReportCriteriaService(ReportCriteriaService reportCriteriaService) {
        this.reportCriteriaService = reportCriteriaService;
    }

    public AccountDAO getAccountDAO() {
        return accountDAO;
    }

    public void setAccountDAO(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    public ReportLogData getReportLogData() {
        return reportLogData;
    }

    public void setReportLogData(ReportLogData reportLogData) {
        this.reportLogData = reportLogData;
    }

    public DriverDAO getDriverDAO() {
        return driverDAO;
    }

    public void setDriverDAO(DriverDAO driverDAO) {
        this.driverDAO = driverDAO;
    }

    public GroupDAO getGroupDAO() {
        return groupDAO;
    }

    public void setGroupDAO(GroupDAO groupDAO) {
        this.groupDAO = groupDAO;
    }

    public PersonDAO getPersonDAO() {
        return personDAO;
    }

    public void setPersonDAO(PersonDAO personDAO) {
        this.personDAO = personDAO;
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

    public String getWebContextPath() {
        return webContextPath;
    }

    public void setWebContextPath(String webContextPath) {
        this.webContextPath = webContextPath;
    }

    public String getEncryptPassword() {
        return encryptPassword;
    }

    public void setEncryptPassword(String encryptPassword) {
        this.encryptPassword = encryptPassword;
    }

    public StandardPBEStringEncryptor getTextEncryptor() {
        return textEncryptor;
    }

    public void setTextEncryptor(StandardPBEStringEncryptor textEncryptor) {
        this.textEncryptor = textEncryptor;
    }

    public Map<Integer, User> getUserMap() {
        return userMap;
    }

    public void setUserMap(Map<Integer, User> userMap) {
        this.userMap = userMap;
    }

    public Map<Integer, GroupHierarchy> getAccountGroupHierarchyMap() {
        if (accountGroupHierarchyMap == null)
            accountGroupHierarchyMap = new HashMap<Integer, GroupHierarchy>();
        return accountGroupHierarchyMap;
    }

    public void setAccountGroupHierarchyMap(Map<Integer, GroupHierarchy> accountGroupHierarchyMap) {
        this.accountGroupHierarchyMap = accountGroupHierarchyMap;
    }

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        initTextEncryptor();

        try {
            List<Message> messageList = (List<Message>) this.amazonQueue.popFromQueue();
            boolean hasMessages = messageList != null && messageList.size() > 0;

            while (hasMessages) {
                reportLogData.clear();
                for (Message message : messageList) {
                    Long startMilis = System.currentTimeMillis();
                    try {
                        logger.info("Pulled message " + message.getBody() + " from the Amazon Queue.");
                        ReportSchedule reportSchedule = getReportScheduleFromMessage(message);

                        // save all information to log data
                        reportLogData.setSuccess(true);
                        if (reportSchedule.getAccountID() != null) {
                            Account account = accountDAO.findByID(reportSchedule.getAccountID());
                            reportLogData.setAccountId(reportSchedule.getAccountID());
                            reportLogData.setAccountName(account.getAcctName());
                        }
                        reportLogData.setReportID(reportSchedule.getReportID());
                        reportLogData.setReportType(reportSchedule.getParamType().toString());
                        reportLogData.setIdUserRequestingReport(reportSchedule.getUserID());
                        reportLogData.setRecipientUserIds(reportSchedule.getDriverIDList());
                        reportLogData.setRecipientEmailAddresses(reportSchedule.getEmailTo());
                        reportLogData.setScheduledTime(reportSchedule.getTimeOfDay());

                        logger.info("Dispatching reportSchedlue " + reportSchedule.getName() + " ID: " + reportSchedule.getReportScheduleID());
                        try {
                            dispatchReport(reportSchedule);
                            logger.info("Successfully dispatched reportSchedule " + reportSchedule.getName() + " ID: " + reportSchedule.getReportScheduleID());
                            reportLogData.setActualTimeSent(new DateTime());
                        } catch (Throwable t) {
                            reportLogData.setSuccess(false);
                            reportLogData.getErrors().add(t);
                            logger.error("Unable to dispatch ReportSchedule " + reportSchedule.getName() + " ID: " + reportSchedule.getReportScheduleID());
                            logger.error("Report error: " + t);
                            t.printStackTrace();
                        }

                        logger.info("Deleting ReportSchedule " + reportSchedule.getName() + " ID: " + reportSchedule.getReportScheduleID() + " message from the Amazon Queue.");
                        if (!this.amazonQueue.deleteMessageFromQueue(message.getReceiptHandle())) {
                            String failToDelete = "Failed to delete Message " + message.getReceiptHandle() + " from the queue.";
                            reportLogData.getErrors().add(new Exception(failToDelete));
                            logger.error(failToDelete);
                        }
                        logger.info("Completed processing reportSchedule " + reportSchedule.getName() + " ID: " + reportSchedule.getReportScheduleID() + " from the Amazon queue.");
                    } catch (Throwable t) {
                        reportLogData.setSuccess(false);
                        reportLogData.getErrors().add(t);
                        logger.error("Report error: " + t);
                        t.printStackTrace();
                    } finally {
                        reportLogData.setProcessMilis(System.currentTimeMillis() - startMilis);
                        if (reportLogData.getSuccess()) {
                            logger.info(reportLogData.formatForStash());
                        }else{
                            logger.error(reportLogData.formatForStash());
                        }
                    }
                }

                messageList = (List<Message>) this.amazonQueue.popFromQueue();
                hasMessages = messageList != null && messageList.size() > 0;
            }
        } catch (AmazonClientException e) {
            logger.error("Could not pull from the Amazon queue. " + e);
        }
    }

    protected void initTextEncryptor() {
        textEncryptor.setPassword(encryptPassword);
        textEncryptor.setStringOutputType("hexadecimal");
    }

    private ReportSchedule getReportScheduleFromMessage(Message message) {
        return reportScheduleDAO.findByID(Integer.valueOf(message.getBody()));
    }

    protected void dispatchReport(ReportSchedule reportSchedule) throws Throwable {
        userMap = new HashMap<Integer, User>();

        User user = getUser(reportSchedule.getUserID());
        ReportGroup reportGroup = ReportGroup.valueOf(reportSchedule.getReportID());

        if (reportGroup == null) {
            String err = "null reportGroup for schedule ID " + reportSchedule.getReportID();
            logger.error(err);
            throw new Exception(err);
        }

        if (reportGroup.getEntityType() == EntityType.ENTITY_INDIVIDUAL_DRIVER) {

            Date lastSuccessDate = reportSchedule.getLastDate();
            reportSchedule.setLastDate(new DateTime(DateTimeZone.forID(user.getPerson().getTimeZone().getID())).toDate());
            reportScheduleDAO.update(reportSchedule);

            if (!processIndividualDriverReportSchedule(reportSchedule, user.getPerson())) {
                reportSchedule.setLastDate(lastSuccessDate);
                reportScheduleDAO.update(reportSchedule);
            }

        } else if (reportSchedule.getDeliverToManagers() != null && reportSchedule.getDeliverToManagers().equals(Boolean.TRUE)) {
            deliverReportToGroupManagers(reportSchedule, user);
        } else {
            List<ReportCriteria> reportCriteriaList = reportCriteriaService.getReportCriteria(reportSchedule, getAccountGroupHierarchy(reportSchedule.getAccountID()), user.getPerson());
            emailReport(reportSchedule, user.getPerson(), reportCriteriaList, null);
            reportSchedule.setLastDate(new DateTime(DateTimeZone.forID(user.getPerson().getTimeZone().getID())).toDate());
            reportScheduleDAO.update(reportSchedule);
        }
    }

    private void emailReport(ReportSchedule reportSchedule, Person person, List<ReportCriteria> reportCriteriaList, Person owner) {
        emailReport(reportSchedule, person, reportCriteriaList, owner, true);
    }

    private void emailReport(ReportSchedule reportSchedule, Person person, List<ReportCriteria> reportCriteriaList, Person owner, boolean allowUnsubscribe) {
        // Set the current date of the reports
        FormatType formatType = FormatType.PDF;
        try{
            formatType = Enum.valueOf(FormatType.class, reportSchedule.getFormat());
        } catch (Throwable t){
            logger.info("Failed to read format type for report schedule: "+reportSchedule.getReportScheduleID()+". Keeping PDF as default.");
        }

        if (reportSchedule == null) {
            logger.error("Email reportSchedule is null");
        }

        if (person == null) {
            logger.error("Email person is null for reportSchedule " + reportSchedule.getName() + " ID: " + reportSchedule.getReportScheduleID());
        }

        if (reportCriteriaList == null || reportCriteriaList.size() < 1) {
            logger.error("Email reportCriteria is not available for reportSchedule " + reportSchedule.getName() + " ID: " + reportSchedule.getReportScheduleID());
        }

        for (ReportCriteria reportCriteria : reportCriteriaList) {
            reportCriteria.setReportDate(new DateTime().toDate(), person.getTimeZone());
            reportCriteria.setLocale(person.getLocale());
            reportCriteria.setUseMetric((person.getMeasurementType() != null && person.getMeasurementType().equals(MeasurementType.METRIC)));
            reportCriteria.setMeasurementType(person.getMeasurementType());
            reportCriteria.setFuelEfficiencyType(person.getFuelEfficiencyType());
            reportCriteria.setTimeZone(person.getTimeZone());
        }
        Report report = reportCreator.getReport(reportCriteriaList);

        //Create the subject/message for emails sent to group managers.

        if (reportSchedule.getDeliverToManagers() != null && reportSchedule.getDeliverToManagers().equals(Boolean.TRUE)) {
            Person groupManager = person;
            String unsubscribeURL = buildUnsubscribeURL(groupManager.getPriEmail(), reportSchedule.getReportScheduleID());
            
            String subject = LocalizedMessage.getString("reportSchedule.emailSubject", person.getLocale()) + reportSchedule.getName();
            String message = LocalizedMessage.getStringWithValues("reportSchedule.emailMessage.groupManager", person.getLocale(),
                    (owner == null) ? person.getFullName() : owner.getFullName(),
                    (owner == null) ? person.getPriEmail() : owner.getPriEmail(),
                                    unsubscribeURL);

            // Change noreplyemail address based on account
            String noReplyEmailAddress = DEFAULT_NO_REPLY_EMAIL_ADDRESS;
            Account acct = accountDAO.findByID(reportSchedule.getAccountID());
            if ((acct.getProps() != null) &&
                    (acct.getProps().getNoReplyEmail() != null) &&
                    (acct.getProps().getNoReplyEmail().trim().length() > 0)) {
                noReplyEmailAddress = acct.getProps().getNoReplyEmail();
            }
            report.exportReportToEmail(groupManager.getPriEmail(), formatType, message, subject, noReplyEmailAddress);
        } else {
            for (String address : reportSchedule.getEmailTo()) {
                String subject = LocalizedMessage.getString("reportSchedule.emailSubject", person.getLocale()) + reportSchedule.getName();
                String message = null;
                if (allowUnsubscribe) {
                    String unsubscribeURL = buildUnsubscribeURL(address, reportSchedule.getReportScheduleID());
                    message = LocalizedMessage.getStringWithValues("reportSchedule.emailMessage", person.getLocale(),
                            (owner == null) ? person.getFullName() : owner.getFullName(),
                            (owner == null) ? person.getPriEmail() : owner.getPriEmail(),
                            unsubscribeURL);
                } else {
                    message = LocalizedMessage.getStringWithValues("reportSchedule.emailMessage.groupManager", person.getLocale(),
                            (owner == null) ? person.getFullName() : owner.getFullName(),
                            (owner == null) ? person.getPriEmail() : owner.getPriEmail());
                }

                // Change noreplyemail address based on account
                String noReplyEmailAddress = DEFAULT_NO_REPLY_EMAIL_ADDRESS;
                Account acct = accountDAO.findByID(reportSchedule.getAccountID());
                if ((acct.getProps() != null) &&
                        (acct.getProps().getNoReplyEmail() != null) &&
                        (acct.getProps().getNoReplyEmail().trim().length() > 0)) {
                    noReplyEmailAddress = acct.getProps().getNoReplyEmail();
                }

                logger.debug("report.exportReportToEmail(" + address + ", " + formatType + ", " + message + ", " + subject + ", " + noReplyEmailAddress + ");");
                report.exportReportToEmail(address, formatType, message, subject, noReplyEmailAddress);
            }
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

    private void deliverReportToGroupManagers(ReportSchedule source, User user) {
        //For each group, we're going to build a ReportSchedule if there is a group manager.
        List<Integer> groupIds = getAccountGroupHierarchy(source.getAccountID()).getGroupIDList(source.getGroupID());

        //The groupIds can contain duplicates so we're going to put them in a set. This takes time and needs
        //to be refactored TODO
        Set<Integer> groupIdSet = new HashSet<Integer>();
        for (Integer groupID : groupIds) {
            groupIdSet.add(groupID);
        }

        ReportManagerDeliveryType managerDeliveryType = source.getManagerDeliveryType() == null ? ReportManagerDeliveryType.ALL : source.getManagerDeliveryType();

        for (Integer groupID : groupIdSet) {
            Group group = getAccountGroupHierarchy(source.getAccountID()).getGroup(groupID);
            if (managerDeliveryType == ReportManagerDeliveryType.EXCLUDE_DIVISIONS && group.getType() == GroupType.DIVISION ||
                    managerDeliveryType == ReportManagerDeliveryType.EXCLUDE_TEAMS && group.getType() == GroupType.TEAM) {
                if (logger.isDebugEnabled()) {
                    logger.debug(String.format("Skipping group [%d] due to manager delivery time of [%s].", group.getGroupID(), managerDeliveryType.name()));
                }
                continue;
            }
            if (group.getManagerID() != null && group.getManagerID() > 0) {
                Person manager = personDAO.findByID(group.getManagerID());
                if (manager.getStatus().equals(Status.ACTIVE)) {
                    ReportSchedule reportSchedule = new ReportSchedule();
                    BeanUtils.copyProperties(source, reportSchedule);
                    reportSchedule.setGroupID(groupID);
                    if (logger.isDebugEnabled()) {
                        logger.debug(String.format("Creating report [%d] for group [%d] to be sent to manager [%d]", source.getReportID(), group.getGroupID(), manager.getPersonID()));
                    }
                    List<ReportCriteria> reportCriteriaList = reportCriteriaService.getReportCriteria(reportSchedule, getAccountGroupHierarchy(reportSchedule.getAccountID()), user.getPerson());
                    emailReport(reportSchedule, manager, reportCriteriaList, user.getPerson());
                }
            }
        }

        source.setLastDate(new DateTime(DateTimeZone.forID(user.getPerson().getTimeZone().getID())).toDate());
        reportScheduleDAO.update(source);
    }

    private boolean processIndividualDriverReportSchedule(ReportSchedule reportSchedule, Person person) {

        try {
            List<Integer> teamList = getTeamList(reportSchedule.getAccountID(), reportSchedule.getGroupID());
            List<Driver> driverList = getAllDrivers(reportSchedule.getGroupID());
            Map<Integer, List<Integer>> teamDriverIDMap = getReportDriverIDs(teamList, driverList);
            ReportGroup reportGroup = ReportGroup.valueOf(reportSchedule.getReportID());
            Person owner = null;
            if (reportSchedule.getUserID() != null) {
                User user = userDAO.findByID(reportSchedule.getUserID());
                if (user != null)
                    owner = user.getPerson();

            }

            List<IndividualReportEmail> individualReportEmailList = new ArrayList<IndividualReportEmail>();
            for (int i = 0; i < reportGroup.getReports().length; i++) {
                TimeFrame timeFrame = reportSchedule.getReportTimeFrame();

                if (timeFrame == null) {
                    timeFrame = TimeFrame.TODAY;
                }

                switch (reportGroup.getReports()[i]) {
                    case DRIVER_PERFORMANCE_INDIVIDUAL:
                    case DRIVER_PERFORMANCE_RYG_INDIVIDUAL:
                        Boolean ryg = (reportGroup.getReports()[i] == ReportType.DRIVER_PERFORMANCE_RYG_INDIVIDUAL);
                        for (Integer teamID : teamDriverIDMap.keySet()) {
                            List<ReportCriteria> rcList = getReportCriteriaService().getDriverPerformanceIndividualReportCriteria(
                                    getAccountGroupHierarchy(reportSchedule.getAccountID()),
                                    teamID, teamDriverIDMap.get(teamID),
                                    timeFrame.getInterval(), person.getLocale(), ryg, reportSchedule.getIncludeInactiveDrivers(), reportSchedule.getIncludeZeroMilesDrivers());

                            for (Integer driverID : teamDriverIDMap.get(teamID)) {
                                Driver driver = findDriver(driverList, driverID);
                                if (driver == null) {
                                    logger.info("Driver may have been moved off this team.  DriverID is :" + driverID);
                                    continue;
                                } else {
                                    logger.info("Sending to driver with Primary E-Mail address: " + driver.getPerson().getPriEmail());
                                    List<ReportCriteria> driverReportCriteriaList = new ArrayList<ReportCriteria>();
                                    for (ReportCriteria rc : rcList) {
                                        if (rc.getMainDataset() == null || rc.getMainDataset().isEmpty())
                                            continue;
                                        DriverPerformance dp = (DriverPerformance) rc.getMainDataset().get(0);
                                        if (dp.getDriverID().equals(driverID)) {
                                            driverReportCriteriaList.add(rc);
                                            break;
                                        }
                                    }
                                    if (!driverReportCriteriaList.isEmpty()) {
                                        IndividualReportEmail individualReportEmail = new IndividualReportEmail();
                                        List<String> emailToList = new ArrayList<String>();
                                        emailToList.add(driver.getPerson().getPriEmail());
                                        ReportSchedule driverReportSchedule = reportSchedule.clone();

                                        driverReportSchedule.setDriverID(driver.getDriverID());
                                        driverReportSchedule.setEmailTo(emailToList);
                                        individualReportEmail.reportSchedule = driverReportSchedule;
                                        individualReportEmail.driverPerson = driver.getPerson();
                                        individualReportEmail.driverReportCriteriaList = driverReportCriteriaList;
                                        individualReportEmail.owner = owner;
                                        individualReportEmailList.add(individualReportEmail);
                                    }
                                }
                            }
                        }
                        break;
                }
            }

            // send all the e-mails only if we make it though without errors
            boolean allowUnsubscribe = true;
            for (IndividualReportEmail individualReportEmail : individualReportEmailList) {
                logger.info("sending to driver " + individualReportEmail.driverPerson.getPriEmail());
                emailReport(individualReportEmail.reportSchedule, individualReportEmail.driverPerson, individualReportEmail.driverReportCriteriaList, individualReportEmail.owner, allowUnsubscribe);
            }

        } catch (Throwable ex) {
            logger.error("Exception:", ex);
            return false;

        }

        return true;
    }

    private Driver findDriver(List<Driver> driverList, Integer driverID) {

        for (Driver driver : driverList) {
            if (driver.getDriverID().equals(driverID))
                return driver;
        }
        return null;
    }

    public User getUser(Integer userID) {

        User user = userMap.get(userID);
        if (user == null) {
            user = userDAO.findByID(userID);
            if (user != null) {
                userMap.put(userID, user);
            }
        }
        return user;
    }

    public GroupHierarchy getAccountGroupHierarchy(Integer accountID) {
        Map<Integer, GroupHierarchy> map = getAccountGroupHierarchyMap();
        if (map.get(accountID) == null) {
            List<Group> groupList = groupDAO.getGroupsByAcctID(accountID);
            map.put(accountID, new GroupHierarchy(groupList));
        }

        return map.get(accountID);
    }

    private List<Integer> getTeamList(Integer acctID, Integer groupID) {
        List<Integer> teamIDList = new ArrayList<Integer>();
        List<Group> groups = groupDAO.getGroupHierarchy(acctID, groupID);
        for (Group group : groups) {
            if (group.getType() == GroupType.TEAM) {
                boolean found = false;
                for (Integer teamID : teamIDList) {
                    if (group.getGroupID().equals(teamID)) {
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    teamIDList.add(group.getGroupID());
                }
            }
        }
        return teamIDList;
    }

    private List<Driver> getAllDrivers(Integer groupID) {
        List<Driver> allDriverList = new ArrayList<Driver>();
        List<Driver> driverList = driverDAO.getAllDrivers(groupID);
        for (Driver driver : driverList) {
            boolean found = false;
            for (Driver rptDriver : allDriverList) {
                if (rptDriver.getDriverID().equals(driver.getDriverID())) {
                    found = true;
                    break;
                }
            }
            if (!found)
                allDriverList.add(driver);
        }
        return allDriverList;
    }

    private Map<Integer, List<Integer>> getReportDriverIDs(List<Integer> teamList, List<Driver> driverList) {
        Map<Integer, List<Integer>> teamDriverListMap = new HashMap<Integer, List<Integer>>();
        for (Integer teamID : teamList) {
            List<Integer> driverIDList = new ArrayList<Integer>();
            for (Driver driver : driverList) {
                if (driver.getGroupID() != null && driver.getGroupID().equals(teamID)) {
                    if (driver.getPerson() == null || driver.getPerson().getPriEmail() == null || driver.getPerson().getPriEmail().isEmpty())
                        logger.info("Skipping driver with no Primary E-Mail address: " + driver.getPerson().getFullName());
                    else {
                        driverIDList.add(driver.getDriverID());
                    }
                }
            }
            teamDriverListMap.put(teamID, driverIDList);
        }
        return teamDriverListMap;
    }

    class IndividualReportEmail {
        ReportSchedule reportSchedule;
        Person driverPerson;
        List<ReportCriteria> driverReportCriteriaList;
        Person owner;

    }
}
