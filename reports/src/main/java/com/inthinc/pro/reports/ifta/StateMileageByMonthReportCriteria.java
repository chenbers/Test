package com.inthinc.pro.reports.ifta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.inthinc.pro.dao.util.MeasurementConversionUtil;
import com.inthinc.pro.model.StateMileage;
import com.inthinc.pro.reports.ReportType;
import com.inthinc.pro.reports.ifta.model.StateMileageByMonth;

/**
 * ReportCriteria for StateMileageByMonth report.
 */
public class StateMileageByMonthReportCriteria extends DOTReportCriteria {
    private DateTimeFormatter format = DateTimeFormat.forPattern("MMM yyyy");

    /**
     * Default constructor.
     * @param locale
     */
    public StateMileageByMonthReportCriteria(Locale locale) {
        super(ReportType.STATE_MILEAGE_BY_MONTH, locale);
    }

    /**
     * {@inheritDoc}
     * @see com.inthinc.pro.reports.ifta.DOTReportCriteria#getDataByGroup(java.lang.Integer, org.joda.time.Interval, boolean)
     */
    @Override
    List<StateMileage> getDataByGroup(Integer groupID, Interval interval, boolean dotOnly) {
        return stateMileageDAO.getStateMileageByGroupAndMonth(groupID, interval, dotOnly);
    }

    /**
     * Populate the data set with data.
     * Copy the fields from the record returned from the Back End to the fields 
     * in the beans to be used by Jasper.
     * 
     * @param records The records retrieved from the Back End
     */
    void initDataSet(List<StateMileage> records)
    {   
        List<StateMileageByMonth> dataList = new ArrayList<StateMileageByMonth>();
        for (StateMileage item : records) {
            if (ZERO_DATA.equals(item.getMiles())) {
                continue;
            }
            StateMileageByMonth rec = new StateMileageByMonth();
            rec.setMonth(item.getMonth());
            // TODO to be revised when setDate() is implemented
            if (item.getDate()==null) {
                DateTime date = new DateTime(2010, getMonthNumber(item.getMonth()),1,0,0,0,0);
                rec.setDate(date.toDate());
            } else {
                rec.setDate(item.getDate());
            }

            rec.setState(item.getStateName());
            rec.setGroupName(getShortGroupName(item.getGroupID()));
            rec.setTotal(MeasurementConversionUtil.convertMilesToKilometers(
                        item.getMiles(), getMeasurementType()).doubleValue());
            dataList.add(rec);
        }
        Collections.sort(dataList, new StateMileageByMonthComparator());        
        setMainDataset(dataList);
    }

    private int getMonthNumber(String month) {
        if (month == null) return 0;
        return format.parseDateTime(month.toUpperCase()+" 2000").monthOfYear().get();
    }
    
    /* Comparator implementation for this report */
    class StateMileageByMonthComparator implements Comparator<StateMileageByMonth> {

        private static final int COMPARISON_SAME = 0;
        private static final int COMPARISON_BEFORE = -1;
        private static final int COMPARISON_AFTER = 1;

        @Override
        public int compare(StateMileageByMonth o1, StateMileageByMonth o2) {
            int order = compareValues(o1.getGroupName(),o2.getGroupName());
            if (order == 0) {
                //order = compareValues(o1.getMonth(),o2.getMonth());
                order = compareValues(o1.getDate(), o2.getDate());
                if (order == 0) {
                    return compareValues(o1.getState(),o2.getState());
                }
                return order;
            }
            return order;
        }
        
        @SuppressWarnings("unchecked")
        private int compareValues(Comparable o1, Object o2) {
            if (o1 == null) {
                if (o2 != null) {
                    return COMPARISON_AFTER;
                } else {
                    return COMPARISON_SAME;
                }
            } else {
                if (o2 == null) {
                    return COMPARISON_BEFORE;
                } else {
                    return o1.compareTo(o2);
                }
            }
        }

    }
}
