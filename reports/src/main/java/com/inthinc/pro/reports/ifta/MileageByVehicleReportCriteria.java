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
 * Report Criteria for MileageByVehicle report.
 */
public class MileageByVehicleReportCriteria extends DOTReportCriteria {

    /**
     * Default constructor.
     * @param locale
     */
    public MileageByVehicleReportCriteria(Locale locale) {
        super(ReportType.MILEAGE_BY_VEHICLE, locale);
    }

    /**
     * {@inheritDoc}
     * @see com.inthinc.pro.reports.ifta.DOTReportCriteria#getDataByGroup(java.lang.Integer, org.joda.time.Interval, boolean)
     */
    @Override
    List<StateMileage> getDataByGroup(Integer groupID, Interval interval, boolean dotOnly) {
        return stateMileageDAO.getMileageByVehicle(groupID, interval, dotOnly);
    }

    /**
     * Populate the data set with data.
     * @param interval
     * @param recordMap
     */
    void initDataSet(List<StateMileage> records)
    {   
        List<MileageByVehicle> dataList = new ArrayList<MileageByVehicle>();
        for (StateMileage item : records) {
            MileageByVehicle rec = new MileageByVehicle();
            rec.setVehicleName(item.getVehicleName());
            //rec.setState(item.getStateName()); not used
            rec.setGroupName(getShortGroupName(item.getGroupID()));
            rec.setTotal(MeasurementConversionUtil.convertMilesToKilometers(
                        item.getMiles(), getMeasurementType()).doubleValue());
            dataList.add(rec);
        }
        Collections.sort(dataList, new MileageByVehicleComparator());        
        setMainDataset(dataList);
    }

    class MileageByVehicleComparator implements Comparator<MileageByVehicle> {

        @Override
        public int compare(MileageByVehicle o1, MileageByVehicle o2) {
            int order = o1.getGroupName().compareTo(o2.getGroupName());
            if (order == 0) {
                return o1.getVehicleName().compareTo(o2.getVehicleName());
            }
            return order;
        }
    }
}
