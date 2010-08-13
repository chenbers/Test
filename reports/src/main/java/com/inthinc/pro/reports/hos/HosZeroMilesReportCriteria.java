package com.inthinc.pro.reports.hos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import org.apache.log4j.Logger;
import org.joda.time.Interval;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.inthinc.pro.dao.GroupDAO;
import com.inthinc.pro.dao.HOSDAO;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.hos.HOSVehicleMileage;
import com.inthinc.pro.reports.ReportCriteria;
import com.inthinc.pro.reports.ReportType;
import com.inthinc.pro.reports.hos.model.GroupHierarchy;
import com.inthinc.pro.reports.hos.model.HosZeroMiles;

public class HosZeroMilesReportCriteria extends ReportCriteria {

    private static final Logger logger = Logger.getLogger(HosZeroMilesReportCriteria.class);
    protected DateTimeFormatter dateTimeFormatter;

    private GroupDAO groupDAO;
    private HOSDAO hosDAO;
    

    public HosZeroMilesReportCriteria(Locale locale) 
    {
        super(ReportType.HOS_ZERO_MILES, "", locale);
        dateTimeFormatter = DateTimeFormat.forPattern("MM/dd/yyyy").withLocale(locale);

    }
    
    public void init(Integer groupID, Interval interval)
    {
        Group topGroup = groupDAO.findByID(groupID);
        List<Group> groupList = groupDAO.getGroupHierarchy(topGroup.getAccountID(), topGroup.getGroupID());

        List<HOSVehicleMileage>  groupNoDriverMileageList = hosDAO.getHOSVehicleMileage(groupID, interval, true);

        initDataSet(interval, topGroup, groupList,  groupNoDriverMileageList);
    }
    
    void initDataSet(Interval interval, Group topGroup,  List<Group> groupList, 
            List<HOSVehicleMileage> groupUnitNoDriverMileageList)
    {
        GroupHierarchy groupHierarchy = new GroupHierarchy(topGroup, groupList);
         
        List<HosZeroMiles> dataList = new ArrayList<HosZeroMiles>();
        for (HOSVehicleMileage zeroMileage : groupUnitNoDriverMileageList) {
            
            dataList.add(new HosZeroMiles(groupHierarchy.getFullName(groupHierarchy.getGroup(zeroMileage.getGroupID())), zeroMileage.getVehicleName(), zeroMileage.getDistance().doubleValue()));
            
        }
        Collections.sort(dataList);
        setMainDataset(dataList);
        
        addParameter("REPORT_START_DATE", dateTimeFormatter.print(interval.getStart()));
        addParameter("REPORT_END_DATE", dateTimeFormatter.print(interval.getEnd()));
        
//        setUseMetric(true);
        
    }

    public GroupDAO getGroupDAO() {
        return groupDAO;
    }

    public void setGroupDAO(GroupDAO groupDAO) {
        this.groupDAO = groupDAO;
    }

    public HOSDAO getHosDAO() {
        return hosDAO;
    }

    public void setHosDAO(HOSDAO hosDAO) {
        this.hosDAO = hosDAO;
    }
}
