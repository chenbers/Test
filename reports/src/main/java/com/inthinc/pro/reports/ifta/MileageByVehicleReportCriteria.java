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
import com.inthinc.pro.reports.ifta.model.MileageByVehicle;

/**
 * Report Criteria for MileageByVehicle report.
 */
public class MileageByVehicleReportCriteria extends ReportCriteria {
    protected DateTimeFormatter dateTimeFormatter; 

    protected GroupDAO groupDAO;
    protected StateMileageDAO stateMileageDAO;

    /**
     * Default constructor.
     * @param locale
     */
    public MileageByVehicleReportCriteria(Locale locale) {
        super(ReportType.MILEAGE_BY_VEHICLE, "", locale);
        dateTimeFormatter = DateTimeFormat.forPattern("MM/dd/yyyy").withLocale(locale);
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
    public void init(Integer groupId, Interval interval, boolean dotOnly) {
        addParameter(ReportCriteria.REPORT_START_DATE, dateTimeFormatter.print(interval.getStart()));
        addParameter(ReportCriteria.REPORT_END_DATE, dateTimeFormatter.print(interval.getEnd()));
        List<StateMileage> list = stateMileageDAO.getMileageByVehicle(groupId, interval, dotOnly);
        initDataSet(interval, list);
    }

    /**
     * Populate the dataset with data.
     * @param interval
     * @param recordMap
     */
    void initDataSet(Interval interval, List<StateMileage> records)
    {   
        List<MileageByVehicle> dataList = new ArrayList<MileageByVehicle>();
        for (StateMileage item : records) {
            MileageByVehicle rec = new MileageByVehicle();
            rec.setVehicle(item.getVehicleName());
            rec.setDistance(Double.valueOf(item.getMiles()));
            rec.setGroupName(item.getGroupName());
            dataList.add(rec);
        }
        Collections.sort(dataList, new MileageByVehicleComparator());        
        setMainDataset(dataList);
    }

    private class MileageByVehicleComparator implements Comparator<MileageByVehicle> {

        @Override
        public int compare(MileageByVehicle o1, MileageByVehicle o2) {
                return o1.getVehicle().compareTo(o2.getVehicle());
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
