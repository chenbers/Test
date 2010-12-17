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
import com.inthinc.pro.reports.ifta.model.StateMileageCompareByGroup;

/**
 * Report Criteria for StateMileageFuelByVehicle report.
 */
public class StateMileageCompareByGroupReportCriteria extends DOTReportCriteria {

    public static final int POSITIVE = 1;
    public static final int NEGATIVE = -1;
    public static final int EQUAL = 0;

    /**
     * Default constructor.
     * 
     * @param locale
     */
    public StateMileageCompareByGroupReportCriteria(Locale locale) {
        super(ReportType.STATE_MILEAGE_COMPARE_BY_GROUP, locale);
    }

    /**
     * {@inheritDoc}
     * @see com.inthinc.pro.reports.ifta.DOTReportCriteria#getDataByGroup(java.lang.Integer, org.joda.time.Interval, boolean)
     */
    @Override
    List<StateMileage> getDataByGroup(Integer groupID, Interval interval, boolean dotOnly) {
        return stateMileageDAO.getStateMileageByGroup(groupID, interval, dotOnly);
    }

    /**
     * Populate the data set with data. Copy the fields from the record returned from the Back End to the fields in the beans to be used by Jasper.
     * 
     * @param records
     *            The records retrieved from the Back End
     */
    void initDataSet(List<StateMileage> records) {
        List<StateMileageCompareByGroup> dataList = new ArrayList<StateMileageCompareByGroup>();
        for (StateMileage item : records) {
            StateMileageCompareByGroup rec = new StateMileageCompareByGroup();
            rec.setGroupName(getShortGroupName(item.getGroupID()));
            rec.setState(item.getStateName());
            rec.setMonth(item.getMonth());
            rec.setTotal(MeasurementConversionUtil.convertMilesToKilometers(item.getMiles(), getMeasurementType()).doubleValue());
            dataList.add(rec);
        }
        Collections.sort(dataList, new StateMileageCompareByGroupComparator());
        setMainDataset(dataList);
    }

    /* Comparator for StateMileageByVehicle report */
    class StateMileageCompareByGroupComparator implements Comparator<StateMileageCompareByGroup> {

        @Override
        public int compare(StateMileageCompareByGroup o1, StateMileageCompareByGroup o2) {
            int sortOrder;
            
            if( o1.getGroupName() == null && o2.getGroupName() ==  null ) {
                sortOrder = EQUAL;
            }
            else if(o1.getGroupName() == null && o2.getGroupName() !=  null ) {
                sortOrder = POSITIVE;
            }
            else {
                sortOrder = o1.getGroupName().compareTo(o2.getGroupName());
            }
            
            if (sortOrder == EQUAL) {
                if( o1.getMonth() == null && o2.getMonth() ==  null ) {
                    sortOrder = EQUAL;
                }
                else if(o1.getMonth() == null && o2.getMonth() !=  null ) {
                    sortOrder = POSITIVE;
                }
                else {
                    sortOrder = o1.getMonth().compareTo(o2.getMonth());
                }

                if (sortOrder == EQUAL) {
                    if( o1.getState() == null && o2.getState() ==  null ) {
                        sortOrder = EQUAL;
                    }
                    else if(o1.getState() == null && o2.getState() !=  null ) {
                        sortOrder = POSITIVE;
                    }
                    else {
                        sortOrder = o1.getState().compareTo(o2.getState());
                    }
                }
            }

            return sortOrder;
        }
    }
}
