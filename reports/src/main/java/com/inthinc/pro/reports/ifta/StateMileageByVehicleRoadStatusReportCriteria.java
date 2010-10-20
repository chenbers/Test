package com.inthinc.pro.reports.ifta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import org.joda.time.Interval;
import com.inthinc.pro.dao.GroupDAO;
import com.inthinc.pro.dao.StateMileageDAO;
import com.inthinc.pro.dao.util.MeasurementConversionUtil;
import com.inthinc.pro.model.StateMileage;
import com.inthinc.pro.reports.ReportCriteria;
import com.inthinc.pro.reports.ReportType;
import com.inthinc.pro.reports.ifta.model.StateMileageByVehicleRoadStatus;

/**
 * Report Criteria for MileageByVehicle report.
 */
public class StateMileageByVehicleRoadStatusReportCriteria extends DOTReportCriteria {

    /**
     * Default constructor.
     * @param locale
     */
    public StateMileageByVehicleRoadStatusReportCriteria(Locale locale) {
        super(ReportType.STATE_MILEAGE_BY_VEHICLE_ROAD_STATUS, locale);
    }

    /**
     * Setter for Group DAO. 
     * @param groupDAO
     */
    public void setGroupDAO(GroupDAO groupDAO) {
        this.groupDAO = groupDAO;
    }

    /**
     * Initiate the DataSet and the parameters for the report.
     * @param groupId the groupId chosen by the user
     * @param interval the date period
     * @param iftaOnly the flag to consider only IFTA 
     */
    public void init(List<Integer> groupIDList, Interval interval, boolean dotOnly) {
        super.init(groupIDList, interval, dotOnly);
        List<StateMileage> list = new ArrayList<StateMileage>();
        for (Integer groupID : groupIDList) {
            List<StateMileage> listTmp = stateMileageDAO.getStateMileageByVehicleRoad(groupID, interval, dotOnly);
            if (listTmp != null)
                list.addAll(listTmp);
        }      
        initDataSet(interval, list);
    }

    /**
     * Populate the dataset with data.
     * @param interval
     * @param recordMap
     */
    void initDataSet(Interval interval, List<StateMileage> records)
    {   
        List<StateMileageByVehicleRoadStatus> dataList = new ArrayList<StateMileageByVehicleRoadStatus>();
        String roadStatus = "";
        for (StateMileage item : records) {
            StateMileageByVehicleRoadStatus rec = new StateMileageByVehicleRoadStatus();
            rec.setVehicle(item.getVehicleName());
            if(item.isOnRoadFlag())
                 roadStatus = "On-Road";
            else
                roadStatus = "Off-Road";  
            
            rec.setRoadStatus(roadStatus);
            rec.setState(item.getStateName());
            
            rec.setGroupName(item.getGroupName());
            rec.setTotal(MeasurementConversionUtil.convertMilesToKilometers(
                    item.getMiles(), getMeasurementType()).doubleValue());
            dataList.add(rec);
        }
        
        Collections.sort(dataList, new StateMileageByVehicleRoadStatusComparator());        
        setMainDataset(dataList);
    }

    // Sorting done based on Group name and Vehicle ID
    class StateMileageByVehicleRoadStatusComparator implements Comparator<StateMileageByVehicleRoadStatus> {

        @Override
       public int compare(StateMileageByVehicleRoadStatus o1, StateMileageByVehicleRoadStatus o2) {
           int comparaison = o1.getGroupName().compareTo(o2.getGroupName());     
           
           if( comparaison == 0)        
               comparaison = o1.getVehicle().compareTo(o2.getVehicle());
           
           return comparaison;
       }
   }

    /**
     * The StateMileageDAO setter.
     * @param stateMileageDAO the DAO to set
     */
    public void setStateMileageDAO(StateMileageDAO stateMileageDAO) {
        this.stateMileageDAO = stateMileageDAO;
    }   
}
