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
     * @param locale
     */
    public StateMileageByVehicleRoadStatusReportCriteria(Locale locale) {
        super(ReportType.STATE_MILEAGE_BY_VEHICLE_ROAD_STATUS, locale);
    }

    /**
     * {@inheritDoc}
     * @see com.inthinc.pro.reports.ifta.DOTReportCriteria#getDataByGroup(java.lang.Integer, org.joda.time.Interval, boolean)
     */
    @Override
    List<StateMileage> getDataByGroup(Integer groupID, Interval interval, boolean dotOnly) {
        return stateMileageDAO.getStateMileageByVehicleRoad(groupID, interval, dotOnly);
    }

    /**
     * Populate the DataSet with data.
     * @param interval
     * @param recordMap
     */
    void initDataSet(List<StateMileage> backendBeanList)
    {   
        List<StateMileageByVehicleRoadStatus> dataList = new ArrayList<StateMileageByVehicleRoadStatus>();
        String roadStatus = "";
        for (StateMileage backendBean : backendBeanList) {
            StateMileageByVehicleRoadStatus reportBean = new StateMileageByVehicleRoadStatus();
            reportBean.setVehicle(backendBean.getVehicleName());
            if(backendBean.isOnRoadFlag())
                roadStatus = ON_ROAD_STATUS;
            else
                roadStatus = OFF_ROAD_STATUS;  
            
            reportBean.setRoadStatus(roadStatus);
            reportBean.setState(backendBean.getStateName());
            reportBean.setGroupName(getShortGroupName(backendBean.getGroupID()));
            reportBean.setTotal(MeasurementConversionUtil.convertMilesToKilometers(
                    backendBean.getMiles(), getMeasurementType()).doubleValue());
            dataList.add(reportBean);
        }
        
        Collections.sort(dataList, new StateMileageByVehicleRoadStatusComparator());        
        setMainDataset(dataList);
    }

 //    Sorting done based on Group name and Vehicle ID
    class StateMileageByVehicleRoadStatusComparator implements Comparator<StateMileageByVehicleRoadStatus> {

        @Override
       public int compare(StateMileageByVehicleRoadStatus o1, StateMileageByVehicleRoadStatus o2) {
           int comparaison = o1.getGroupName().compareTo(o2.getGroupName());     
           
           if( comparaison == 0)        
               comparaison = o1.getVehicle().compareTo(o2.getVehicle());
          
           return comparaison;
       }
   }
}
