package com.inthinc.pro.reports.ifta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import org.joda.time.Interval;

import com.inthinc.pro.dao.util.MeasurementConversionUtil;
import com.inthinc.pro.model.GroupHierarchy;
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
     * Initiate the DataSet and the parameters for the report.
     * @param groupId the groupId chosen by the user
     * @param interval the date period
     * @param iftaOnly the flag to consider only IFTA 
     */
    @Override
    public void init(GroupHierarchy accountGroupHierarchy, List<Integer> groupIDList, Interval interval, boolean dotOnly) {
        super.init(accountGroupHierarchy, groupIDList, interval, dotOnly);
        
        List<StateMileage> dataList = new ArrayList<StateMileage>();
        for (Integer groupID : groupIDList) {
            List<StateMileage> list = stateMileageDAO.getMileageByVehicle(groupID, interval, dotOnly);
            if (list != null) {
                dataList.addAll(list);
            }
        }      
        initDataSet(interval, dataList);
    }

    /**
     * Populate the data set with data.
     * @param interval
     * @param recordMap
     */
    void initDataSet(Interval interval, List<StateMileage> records)
    {   
        List<MileageByVehicle> dataList = new ArrayList<MileageByVehicle>();
        for (StateMileage item : records) {
            MileageByVehicle rec = new MileageByVehicle();
            rec.setVehicle(item.getVehicleName());
            rec.setState(item.getStateName());
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
                return o1.getVehicle().compareTo(o2.getVehicle());
        }
    }
}
