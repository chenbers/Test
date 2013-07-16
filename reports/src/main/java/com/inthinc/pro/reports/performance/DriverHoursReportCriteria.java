package com.inthinc.pro.reports.performance;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import org.joda.time.Interval;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.inthinc.hos.model.HOSStatus;
import com.inthinc.pro.model.Account;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.GroupHierarchy;
import com.inthinc.pro.model.hos.HOSRecord;
import com.inthinc.pro.reports.ReportCriteria;
import com.inthinc.pro.reports.ReportType;
import com.inthinc.pro.reports.performance.model.DriverHours;
import com.inthinc.pro.reports.performance.model.PayrollData;

public class DriverHoursReportCriteria extends PayrollSummaryReportCriteria {
    private static final String DAY_FORMAT = "MM/dd/yy";
	private static final String START_DATE_PARAM = "startDate";
	private static final String END_DATE_PARAM = "endDate";
	protected DateTimeFormatter dateTimeFormatter;
	protected DateTimeFormatter dayFormatter;


    /**
	 * Constructor to initiate the report type and locale.
	 * @param locale
	 */
    public DriverHoursReportCriteria(Locale locale) {
        super(ReportType.DRIVER_HOURS, locale);
        dateTimeFormatter = DateTimeFormat.forPattern(ReportCriteria.DATE_FORMAT).withLocale(locale);
        dayFormatter = DateTimeFormat.forPattern(DriverHoursReportCriteria.DAY_FORMAT).withLocale(locale);
    }

	class DriverHoursComparator implements Comparator<DriverHours> {

		@Override
		public int compare(DriverHours o1, DriverHours o2) {
			int groupNamesComparison = compareValues(o1.getGroupName(),o2.getGroupName());
			
			// If Group Names are equal, then we compare the Driver Names
			if (groupNamesComparison == 0)
				return compareValues(o1.getDriverName(),o2.getDriverName());
			return groupNamesComparison;
		}
        @SuppressWarnings("unchecked")
        private int compareValues(Comparable o1, Object o2) {
            if (o1 == null) {
                if (o2 != null) {
                    return 1;
                } else {
                    return 0;
                }
            } else {
                if (o2 == null) {
                    return -1;
                } else {
                    return o1.compareTo(o2);
                }
            }
        }

	}
	
	@Override
	void initDataSet(Interval interval, Account account, GroupHierarchy accountGroupHierarchy, Map<Driver, List<HOSRecord>> driverHOSRecordMap) {

        Date currentTime = new Date();
        
        List<PayrollData> payrollDataList = new ArrayList<PayrollData>();
        
        for (Entry<Driver, List<HOSRecord>> entry : driverHOSRecordMap.entrySet()) {
            payrollDataList.addAll(getDriverPayrollData(interval, accountGroupHierarchy, currentTime, entry.getKey(), entry.getValue() == null ? new ArrayList<HOSRecord>() : entry.getValue()));

        }
        
        Collections.sort(payrollDataList);
        
        List<DriverHours> driverHoursList = this.getDriverHourList(payrollDataList);
        Collections.sort(driverHoursList, new DriverHoursComparator());
        
        setMainDataset(driverHoursList);
	}

    
    @Override
    public void init(GroupHierarchy accountGroupHierarchy, List<Integer> groupIDList, Interval interval) {
        super.init(accountGroupHierarchy, groupIDList, interval);
        
        addParameter(DriverHoursReportCriteria.START_DATE_PARAM,dateTimeFormatter.print(interval.getStart()));
        addParameter(DriverHoursReportCriteria.END_DATE_PARAM,  dateTimeFormatter.print(interval.getEnd()));
    }
    
    private List<DriverHours> getDriverHourList(List<PayrollData> payrollDataList) {
        
        List<DriverHours> driverHoursList = new ArrayList<DriverHours>();
        
        for (PayrollData pd : payrollDataList) {
            DriverHours driverHours = new DriverHours();
            driverHours.setDate(dayFormatter.print(pd.getDateTime()));
            driverHours.setDriverName(pd.getDriverName());
            driverHours.setGroupName(pd.getGroupName());
            double hoursSpentDriving = 0;
            if(pd.getStatus() == HOSStatus.DRIVING) {
                hoursSpentDriving = pd.getTotalAdjustedMinutes() / 60d; // divide minutes by 60 to get hours and cast it as double.
            }
            driverHours.setHours(hoursSpentDriving);
            driverHoursList.add(driverHours);
        }
        
        return driverHoursList;
        
    }

}
