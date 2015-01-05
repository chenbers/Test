package com.inthinc.pro.reports.performance;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TreeMap;
import java.util.Map.Entry;

import com.inthinc.pro.reports.ReportCriteria;
import com.inthinc.pro.reports.performance.model.BreakData;
import com.inthinc.pro.reports.performance.model.BreakHOSRec;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Interval;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.inthinc.hos.model.HOSStatus;
import com.inthinc.pro.dao.AccountDAO;
import com.inthinc.pro.dao.AddressDAO;
import com.inthinc.pro.dao.GroupDAO;
import com.inthinc.pro.dao.HOSDAO;
import com.inthinc.pro.model.Account;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.GroupHierarchy;
import com.inthinc.pro.model.hos.HOSRecord;
import com.inthinc.pro.reports.GroupListReportCriteria;
import com.inthinc.pro.reports.ReportType;
import com.inthinc.pro.reports.hos.converter.Converter;
import com.inthinc.pro.reports.tabular.ColumnHeader;
import com.inthinc.pro.reports.tabular.Result;
import com.inthinc.pro.reports.tabular.Tabular;
import com.inthinc.pro.reports.util.DateTimeUtil;
import com.inthinc.pro.reports.util.MessageUtil;

public class BreakReportCriteria extends GroupListReportCriteria implements Tabular {
    private static final Integer NUM_COLS = 6;
    protected DateTimeFormatter dateTimeFormatter;
    protected AccountDAO accountDAO;
    protected GroupDAO groupDAO;
    protected HOSDAO hosDAO;
    protected AddressDAO addressDAO;

    public BreakReportCriteria(Locale locale) {
        super(ReportType.BREAK_REPORT, locale);
        dateTimeFormatter = DateTimeFormat.forPattern("MM/dd/yyyy").withLocale(locale);
        this.setIncludeInactiveDrivers(ReportCriteria.DEFAULT_EXCLUDE_INACTIVE_DRIVERS);
        this.setIncludeZeroMilesDrivers(ReportCriteria.DEFAULT_INCLUDE_ZERO_MILES_DRIVERS);
    }

    public BreakReportCriteria(ReportType reportType, Locale locale) {
        super(reportType, locale);
        dateTimeFormatter = DateTimeFormat.forPattern("MM/dd/yyyy").withLocale(locale);
    }

    public void init(GroupHierarchy accountGroupHierarchy, List<Integer> groupIDList, Interval interval) {
        Account account = accountDAO.findByID(accountGroupHierarchy.getTopGroup().getAccountID());
        List<Group> reportGroupList = this.getReportGroupList(groupIDList, accountGroupHierarchy);
        List<Driver> driverList = this.getReportDriverList(reportGroupList);

        Map<Driver, List<HOSRecord>> driverHOSRecordMap = new HashMap<Driver, List<HOSRecord>>();
        for (Driver driver : driverList) {
            if (includeDriver(getDriverDAO(), driver.getDriverID(), interval)) {
                if (driver.getDot() == null)
                    continue;
                DateTimeZone dateTimeZone = DateTimeZone.forTimeZone(driver.getPerson().getTimeZone());
                Interval queryInterval = DateTimeUtil.getExpandedInterval(interval, dateTimeZone, 1, 1);
                driverHOSRecordMap.put(driver, hosDAO.getHOSRecords(driver.getDriverID(), queryInterval, true));
            }
        }
        initDataSet(interval, account, accountGroupHierarchy, driverHOSRecordMap);
    }

    void initDataSet(Interval interval, Account account, GroupHierarchy accountGroupHierarchy, Map<Driver, List<HOSRecord>> driverHOSRecordMap) {
        addParameter("REPORT_START_DATE", dateTimeFormatter.print(interval.getStart()));
        addParameter("REPORT_END_DATE", dateTimeFormatter.print(interval.getEnd()));
        addParameter("CUSTOMER", account);
        addParameter("CUSTOMER_NAME", account.getAcctName());
        addParameter("CUSTOMER_ADDRESS", (account.getAddress() == null) ? "" : account.getAddress().getDisplayString());

        Date currentTime = new Date();
        List<BreakData> dataList = new ArrayList<BreakData>();

        for (Entry<Driver, List<HOSRecord>> entry : driverHOSRecordMap.entrySet()) {
            dataList.add(getDriverBreakData(interval, accountGroupHierarchy, currentTime, entry.getKey(), entry.getValue()));
        }

        Collections.sort(dataList);
        setMainDataset(dataList);
    }

    protected BreakData getDriverBreakData(Interval interval, GroupHierarchy groupHierarchy, Date currentTime, Driver driver, List<HOSRecord> hosRecordList) {
        final long MAX_SEC_BREAK = 2 * 60 * 60; // max 2 hour break
        DateTimeZone driverTimeZone = DateTimeZone.forTimeZone(driver.getPerson().getTimeZone());
        Interval driverInterval = DateTimeUtil.getStartEndIntervalInTimeZone(interval, driverTimeZone);
        List<BreakData> dataList = new ArrayList<BreakData>();
        Map<Integer, BreakData> dataMap = new HashMap<Integer, BreakData>();
        List<BreakHOSRec> compensatedRecList = getCompensatedRecordList(hosRecordList, driverInterval.getStart().toDate(), driverInterval.getEnd().toDate(), currentTime);
        Collections.reverse(compensatedRecList);

        Group group = groupHierarchy.getGroup(driver.getGroupID());
        String groupName = getFullGroupName(groupHierarchy, group.getGroupID());
        String groupAddress = getGroupAddressString(group);

        String driverName = driver.getPerson().getFullNameLastFirst();
        String employeeID = driver.getPerson().getEmpid();
        List<DateTime> dayList = DateTimeUtil.getDayList(interval, driverTimeZone);

        BreakData item = new BreakData(driver.getGroupID(), groupName, groupAddress, driver.getDriverID(), driverName, employeeID, driverTimeZone);

        long totalBreakTimeSeconds = 0l;
        long totalOnDutySeconds = 0l;
        long totalOffDutySeconds = 0l;
        int breaksTaken = 0;
        long lastTotalBreakTimeSeconds = 0l;

        for (DateTime day : dayList) {
            List<BreakHOSRec> dayLogList = getListForDay(compensatedRecList, day.toDate(), day.plusDays(1).toDate());

            for (BreakHOSRec log : dayLogList) {
                if (log.getTotalSeconds() > 0) {
                    Long seconds = log.getTotalSeconds();
                    if (log.getStatus().equals(HOSStatus.ON_DUTY)) {
                        totalOnDutySeconds += seconds;
                        if (lastTotalBreakTimeSeconds <= MAX_SEC_BREAK) {
                            breaksTaken += 1;
                            totalBreakTimeSeconds += lastTotalBreakTimeSeconds;
                            lastTotalBreakTimeSeconds = 0l;
                        }
                    } else {
                        totalOffDutySeconds += seconds;
                        lastTotalBreakTimeSeconds += seconds;
                    }
                }
            }

            item.setBreaksTaken(breaksTaken);
            item.setBreakTime(secondsToMinutes(totalBreakTimeSeconds));
            item.setOnDutyHours(secondsToMinutes(totalOnDutySeconds));
            item.setOffDutyHours(secondsToMinutes(totalOffDutySeconds));
        }

        return item;
    }

    public List<BreakHOSRec> getListForDay(List<BreakHOSRec> hosList, Date logDay, Date endOfDayCurrentDate) {
        final long MILLISECONDS_IN_SECOND = 1000;
        List<BreakHOSRec> listForDay = new ArrayList<BreakHOSRec>();

        if (hosList != null) {
            for (BreakHOSRec log : hosList) {
                Date logTime = log.getLogTimeDate();
                Date logEndTime = new Date(logTime.getTime() + (log.getTotalSeconds() * MILLISECONDS_IN_SECOND));
                Date startTime;
                Date endTime;
                long seconds;

                // log begin before or starts exactly on current day
                if ((logTime.before(logDay) || logTime.equals(logDay)) && logEndTime.after(logDay)) {
                    startTime = logDay;
                    endTime = (logEndTime.after(endOfDayCurrentDate)) ? endOfDayCurrentDate : logEndTime;
                    seconds = (endTime.getTime() - startTime.getTime()) / MILLISECONDS_IN_SECOND;
                    listForDay.add(new BreakHOSRec(log.getStatus(), startTime, seconds));
                }


                // log starts within current day
                if (logTime.after(logDay) && logTime.before(endOfDayCurrentDate)) {
                    startTime = logTime;
                    endTime = (logEndTime.after(endOfDayCurrentDate)) ? endOfDayCurrentDate : logEndTime;
                    seconds = (endTime.getTime() - startTime.getTime()) / MILLISECONDS_IN_SECOND;

                    listForDay.add(new BreakHOSRec(log.getStatus(), startTime, seconds));
                }
                // if log starts after end of current day, we are done
                else if (logTime.after(endOfDayCurrentDate)) {
                    break;
                }
            }
        }
        return listForDay;
    }

    protected List<BreakHOSRec> getCompensatedRecordList(List<HOSRecord> hosRecList, Date startDate, Date endDate, Date currentDate) {
        List<BreakHOSRec> compensatedRecList = new ArrayList<BreakHOSRec>();

        for (HOSRecord rec : hosRecList) {
            HOSStatus status = null;

            switch (rec.getStatus()) {
                case OFF_DUTY:
                case OFF_DUTY_AT_WELL:
                case OFF_DUTY_OCCUPANT:
                case HOS_PERSONALTIME:
                case SLEEPER:
                case HOS_ALTERNATE_SLEEPING:
                    status = HOSStatus.OFF_DUTY;
                    break;

                default:
                    status = HOSStatus.ON_DUTY;
            }

            compensatedRecList.add(new BreakHOSRec(status, rec.getLogTime(), 0l));
        }


        if (endDate.after(currentDate))
            endDate = currentDate;

        for (BreakHOSRec rec : compensatedRecList) {
            rec.setTotalSeconds(deltaSeconds(endDate, rec.getLogTimeDate()));
            endDate = rec.getLogTimeDate();
        }

        if (endDate.after(startDate))
            compensatedRecList.add(new BreakHOSRec(HOSStatus.OFF_DUTY, startDate, deltaSeconds(endDate, startDate)));

        return compensatedRecList;
    }

    public Long deltaSeconds(Date endTime, Date startTime) {
        Long delta = (endTime.getTime() - startTime.getTime()) / 1000l;
        if (delta < 0l) {
            return 0l;
        }
        return delta;
    }

    public static int secondsToMinutes(Long seconds) {
        long minutes = seconds / 60l;
        if (seconds % 60 >= 30)
            minutes++;

        return (int) minutes;
    }

    public static int minutesToHours(Long minutes) {
        if (minutes == null)
            minutes = 0l;
        return (int) Math.ceil(minutes / 60);
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

    public HOSDAO getHosDAO() {
        return hosDAO;
    }

    public void setHosDAO(HOSDAO hosDAO) {
        this.hosDAO = hosDAO;
    }

    @Override
    public List<String> getColumnHeaders() {
        return getColumnHeaders(ReportType.BREAK_REPORT, NUM_COLS);
    }

    public List<String> getColumnHeaders(ReportType reportType, int numberOfCols) {
        ResourceBundle resourceBundle = reportType.getResourceBundle(getLocale());

        List<String> columnHeaders = new ArrayList<String>();
        for (int i = 1; i <= numberOfCols; i++)
            columnHeaders.add(MessageUtil.getBundleString(resourceBundle, "column." + i + ".tabular"));

        return columnHeaders;
    }


    @Override
    public List<List<Result>> getTableRows() {
        List<BreakData> dataList = (List<BreakData>) getMainDataset();
        if (dataList == null || dataList.size() == 0)
            return null;

        List<List<Result>> resultList = new ArrayList<List<Result>>();
        for (BreakData data : dataList) {
            List<Result> indResList = new ArrayList<Result>();

            indResList.add(new Result(data.getEmployeeID(), data.getEmployeeID()));
            indResList.add(new Result(data.getDriverName(), data.getDriverName()));
            indResList.add(new Result(Converter.convertMinutes(data.getOnDutyHours()), Converter.convertMinutes(data.getOnDutyHours())));
            indResList.add(new Result(Converter.convertMinutes(data.getOffDutyHours()), Converter.convertMinutes(data.getOffDutyHours())));
            indResList.add(new Result(data.getBreaksTaken().toString(), data.getBreaksTaken()));
            indResList.add(new Result(Converter.convertMinutes(data.getBreakTime()), Converter.convertMinutes(data.getBreakTime())));

            resultList.add(indResList);
        }

        return resultList;
    }

    @Override
    public List<ColumnHeader> getColumnSummaryHeaders() {
        ResourceBundle resourceBundle = ReportType.BREAK_REPORT.getResourceBundle(getLocale());
        List<ColumnHeader> columnHeaders = new ArrayList<ColumnHeader>();

        return columnHeaders;
    }

    public String getGroupAddressString(Group group) {
        if (group.getAddress() == null && group.getAddressID() != null && addressDAO != null) {
            group.setAddress(addressDAO.findByID(group.getAddressID()));
        }
        return group.getAddress() == null ? "" : group.getAddress().getDisplayString();
    }

    public AddressDAO getAddressDAO() {
        return addressDAO;
    }

    public void setAddressDAO(AddressDAO addressDAO) {
        this.addressDAO = addressDAO;
    }
}
