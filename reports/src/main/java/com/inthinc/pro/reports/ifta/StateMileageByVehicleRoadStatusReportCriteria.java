package com.inthinc.pro.reports.ifta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import org.joda.time.Interval;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.inthinc.pro.dao.GroupDAO;
import com.inthinc.pro.dao.StateMileageDAO;
import com.inthinc.pro.model.StateMileage;
import com.inthinc.pro.reports.ReportCriteria;
import com.inthinc.pro.reports.ReportType;
import com.inthinc.pro.reports.ifta.model.StateMileageByVehicleRoadStatus;

/**
 * Report Criteria for MileageByVehicle report.
 */
public class StateMileageByVehicleRoadStatusReportCriteria extends ReportCriteria {
    protected DateTimeFormatter dateTimeFormatter; 
    private static final String UNITS_ENGLISH = "english";
    protected String units;
    protected GroupDAO groupDAO;
    protected StateMileageDAO stateMileageDAO;

    /**
     * Default constructor.
     * @param locale
     */
    public StateMileageByVehicleRoadStatusReportCriteria(Locale locale) {
        super(ReportType.STATE_MILEAGE_BY_VEHICLE_ROAD_STATUS, "", locale);
        dateTimeFormatter = DateTimeFormat.forPattern("MM/dd/yyyy").withLocale(locale);
        if (locale.getLanguage().equals(Locale.ENGLISH.getLanguage())) {
            units = UNITS_ENGLISH;
        } else {
            units = "metric";
        }
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
        List<StateMileage> list = new ArrayList<StateMileage>();
        addParameter(ReportCriteria.REPORT_START_DATE, dateTimeFormatter.print(interval.getStart()));
        addParameter(ReportCriteria.REPORT_END_DATE, dateTimeFormatter.print(interval.getEnd()));
        addParameter("units", units);
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
            if (units.equals(UNITS_ENGLISH))
                rec.setTotal(Double.valueOf(item.getMiles()));
            dataList.add(rec);
        }
        
        Collections.sort(dataList, new StateMileageByVehicleRoadStatusComparator());        
        setMainDataset(dataList);
    }

    private class StateMileageByVehicleRoadStatusComparator implements Comparator<StateMileageByVehicleRoadStatus> {

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
