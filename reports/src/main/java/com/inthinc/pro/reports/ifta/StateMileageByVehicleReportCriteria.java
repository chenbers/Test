package com.inthinc.pro.reports.ifta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import org.joda.time.Interval;

import com.inthinc.pro.dao.util.MeasurementConversionUtil;
import com.inthinc.pro.model.StateMileage;
import com.inthinc.pro.reports.ReportType;
import com.inthinc.pro.reports.ifta.model.MileageByVehicle;

/**
 * Report Criteria for StateMileageByVehicle report.
 */
public class StateMileageByVehicleReportCriteria extends DOTReportCriteria {

    /**
     * Default constructor.
     * 
     * @param locale
     */
    public StateMileageByVehicleReportCriteria(Locale locale) {
        super(ReportType.STATE_MILEAGE_BY_VEHICLE, locale);
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inthinc.pro.reports.ifta.DOTReportCriteria#getDataByGroup(java.lang.Integer, org.joda.time.Interval, boolean)
     */
    @Override
    List<StateMileage> getDataByGroup(Integer groupID, Interval interval, boolean dotOnly) {
        return stateMileageDAO.getStateMileageByVehicle(groupID, interval, dotOnly);
    }

    /**
     * Populate the data set with data. Copy the fields from the record returned from the Back End to the fields in the beans to be used by Jasper.
     * 
     * @param records
     *            The records retrieved from the Back End
     */
    void initDataSet(List<StateMileage> records) {
        List<MileageByVehicle> dataList = new ArrayList<MileageByVehicle>();
        for (StateMileage item : records) {
            if (item.getMiles() != null && item.getMiles() > 0) {
                MileageByVehicle rec = new MileageByVehicle();
                rec.setVehicleName(item.getVehicleName());
                rec.setState(item.getStateName());
                rec.setGroupName(getShortGroupName(item.getGroupID()));
                rec.setTotal(MeasurementConversionUtil.convertMilesToKilometers(item.getMiles(), getMeasurementType()).doubleValue());
                dataList.add(rec);
            }
        }
        Collections.sort(dataList, new StateMileageByVehicleComparator());
        setMainDataset(dataList);
    }

    /* Comparator for StateMileageByVehicle report */
    class StateMileageByVehicleComparator implements Comparator<MileageByVehicle> {

        private static final int COMPARISON_SAME = 0;
        private static final int COMPARISON_BEFORE = -1;
        private static final int COMPARISON_AFTER = 1;

        @Override
        public int compare(MileageByVehicle o1, MileageByVehicle o2) {

            // Checking for nulls on properties. Null values always goes at the end.
            int comparison = compareValues(o1.getGroupName(), o2.getGroupName());

            if (comparison == 0) {
                comparison = compareValues(o1.getVehicleName(), o2.getVehicleName());
            }

            return comparison;
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
