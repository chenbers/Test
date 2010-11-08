package com.inthinc.pro.reports.ifta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import org.joda.time.Interval;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.inthinc.pro.dao.util.MeasurementConversionUtil;
import com.inthinc.pro.model.StateMileage;
import com.inthinc.pro.reports.ReportType;
import com.inthinc.pro.reports.ifta.model.MileageByVehicle;

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
        List<MileageByVehicle> dataList = new ArrayList<MileageByVehicle>();
        for (StateMileage item : records) {
            MileageByVehicle rec = new MileageByVehicle();
            rec.setMonth(item.getMonth());
            rec.setState(item.getStateName());
            rec.setGroupName(getShortGroupName(item.getGroupID()));
            rec.setTotal(MeasurementConversionUtil.convertMilesToKilometers(
                        item.getMiles(), getMeasurementType()).doubleValue());
            dataList.add(rec);
        }
        Collections.sort(dataList, new StateMileageByMonthComparator());        
        setMainDataset(dataList);
    }

    /* Comparator implementation for this report */
    class StateMileageByMonthComparator implements Comparator<MileageByVehicle> {

        @Override
        public int compare(MileageByVehicle o1, MileageByVehicle o2) {
            int order = o1.getGroupName().compareTo(o2.getGroupName());
            if (order == 0) {
                /*
                int m1 = getMonthNumber(o1.getMonth());
                int m2 = getMonthNumber(o2.getMonth());                    
                if (m1 == m2) {*/
                order = o1.getMonth().compareTo(o2.getMonth());
                if (order == 0) {
                    return o1.getState().compareTo(o2.getState());
                }
                return order; //(m1 < m2 ? 1 : -1);
            }
            return order;
        }
        
        @SuppressWarnings("unused")
        private int getMonthNumber(String month) {
            return format.parseDateTime(month.toUpperCase()+" 2000").monthOfYear().get();
        }
    }
}
