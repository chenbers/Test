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
import com.inthinc.pro.reports.ifta.model.StateMileageByVehicleRoadStatus;

/**
 * Report Criteria for MileageByVehicle report.
 */
public class StateMileageByVehicleRoadStatusReportCriteria extends DOTReportCriteria {

    private static final String ON_ROAD_STATUS = "On-Road";
    private static final String OFF_ROAD_STATUS = "Off-Road";

    /**
     * Default constructor.
     * 
     * @param locale
     */
    public StateMileageByVehicleRoadStatusReportCriteria(Locale locale) {
        super(ReportType.STATE_MILEAGE_BY_VEHICLE_ROAD_STATUS, locale);
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inthinc.pro.reports.ifta.DOTReportCriteria#getDataByGroup(java.lang.Integer, org.joda.time.Interval, boolean)
     */
    @Override
    List<StateMileage> getDataByGroup(Integer groupID, Interval interval, boolean dotOnly) {
        return stateMileageDAO.getStateMileageByVehicleRoad(groupID, interval, dotOnly);
    }

    /**
     * Populate the DataSet with data.
     * 
     * @param interval
     * @param recordMap
     */
    void initDataSet(List<StateMileage> backendBeanList) {
        List<StateMileageByVehicleRoadStatus> dataList = new ArrayList<StateMileageByVehicleRoadStatus>();
        String roadStatus = "";
        for (StateMileage backendBean : backendBeanList) {
            if (backendBean.getMiles() != null && backendBean.getMiles() > 0) {
                StateMileageByVehicleRoadStatus reportBean = new StateMileageByVehicleRoadStatus();
                reportBean.setVehicle(backendBean.getVehicleName());
                if (backendBean.isOnRoadFlag())
                    roadStatus = ON_ROAD_STATUS;
                else
                    roadStatus = OFF_ROAD_STATUS;

                reportBean.setRoadStatus(roadStatus);
                reportBean.setState(backendBean.getStateName());
                reportBean.setGroupName(getShortGroupName(backendBean.getGroupID()));
                reportBean.setTotal(MeasurementConversionUtil.convertMilesToKilometers(backendBean.getMiles(), getMeasurementType()).doubleValue());
                dataList.add(reportBean);
            }
        }

        Collections.sort(dataList, new StateMileageByVehicleRoadStatusComparator());
        setMainDataset(dataList);
    }

    class StateMileageByVehicleRoadStatusComparator implements Comparator<StateMileageByVehicleRoadStatus> {

        private static final int COMPARISON_SAME = 0;
        private static final int COMPARISON_BEFORE = -1;
        private static final int COMPARISON_AFTER = 1;

        @Override
        public int compare(StateMileageByVehicleRoadStatus o1, StateMileageByVehicleRoadStatus o2) {

            // Checking for nulls on properties. Null values always goes at the end.
            int comparison = compareValues(o1.getGroupName(), o2.getGroupName());

            if (comparison == 0) {
                comparison = compareValues(o1.getVehicle(), o2.getVehicle());
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
