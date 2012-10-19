package com.inthinc.pro.reports.hos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import org.joda.time.Interval;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.inthinc.pro.dao.GroupDAO;
import com.inthinc.pro.dao.HOSDAO;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.GroupHierarchy;
import com.inthinc.pro.model.hos.HOSVehicleMileage;
import com.inthinc.pro.reports.GroupListReportCriteria;
import com.inthinc.pro.reports.ReportCriteria;
import com.inthinc.pro.reports.ReportType;
import com.inthinc.pro.reports.hos.converter.Converter;
import com.inthinc.pro.reports.hos.model.HosZeroMiles;
import com.inthinc.pro.reports.tabular.ColumnHeader;
import com.inthinc.pro.reports.tabular.Result;
import com.inthinc.pro.reports.tabular.Tabular;
import com.inthinc.pro.reports.util.MessageUtil;

public class HosZeroMilesReportCriteria extends GroupListReportCriteria implements Tabular {

    protected DateTimeFormatter dateTimeFormatter;

    private GroupDAO groupDAO;
    private HOSDAO hosDAO;
    

    public HosZeroMilesReportCriteria(Locale locale) {
        super(ReportType.HOS_ZERO_MILES, locale);
        dateTimeFormatter = DateTimeFormat.forPattern("MM/dd/yyyy").withLocale(locale);
        this.setIncludeInactiveDrivers(ReportCriteria.DEFAULT_INCLUDE_INACTIVE_DRIVERS);
        this.setIncludeZeroMilesDrivers(ReportCriteria.DEFAULT_INCLUDE_ZERO_MILES_DRIVERS);
    }
    
    public void init(GroupHierarchy accountGroupHierarchy, List<Integer>groupIDList, Interval interval){
        List<Group> reportGroupList = this.getReportGroupList(groupIDList, accountGroupHierarchy);
        List<HOSVehicleMileage>  groupNoDriverMileageList = new ArrayList<HOSVehicleMileage>(); 
            
        for (Group reportGroup : reportGroupList)
            groupNoDriverMileageList.addAll(hosDAO.getHOSVehicleMileage(reportGroup.getGroupID(), interval, true));

        initDataSet(interval, accountGroupHierarchy,  groupNoDriverMileageList);
    }

    
    void initDataSet(Interval interval, GroupHierarchy accountGroupHierarchy, 
            List<HOSVehicleMileage> groupUnitNoDriverMileageList)
    {
         
        List<HosZeroMiles> dataList = new ArrayList<HosZeroMiles>();
        for (HOSVehicleMileage zeroMileage : groupUnitNoDriverMileageList) {
            
            dataList.add(new HosZeroMiles(getFullGroupName(accountGroupHierarchy, zeroMileage.getGroupID()), zeroMileage.getVehicleName(), zeroMileage.getDistance().doubleValue()));
            
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
    


    @Override
    public List<String> getColumnHeaders() {
        ResourceBundle resourceBundle = ReportType.HOS_ZERO_MILES.getResourceBundle(getLocale());
        
        List<String> columnHeaders = new ArrayList<String>();
        columnHeaders.add(MessageUtil.getBundleString(resourceBundle, "column.1.tabular"));
        columnHeaders.add(MessageUtil.getBundleString(resourceBundle, "column.2.tabular"));
        columnHeaders.add(MessageUtil.getBundleString(resourceBundle, "column.3.tabular." + (getUseMetric() ? "metric" : "english")));
        
        return columnHeaders;
    }


    @Override
    public List<List<Result>> getTableRows() {
        List<HosZeroMiles> dataList = (List<HosZeroMiles>)getMainDataset();
        if (dataList == null)
            return null;
        List<List<Result>>records = new ArrayList<List<Result>>();
        
        for (HosZeroMiles detail : dataList) {
            List<Result> row = new ArrayList<Result>();
            row.add(new Result(detail.getGroupName(), detail.getGroupName()));
            row.add(new Result(detail.getUnitID(), detail.getUnitID()));
            String distanceStr = Converter.convertDistance(detail.getTotalMilesNoDriver(), getUseMetric(), getLocale()); 
            row.add(new Result(distanceStr, detail.getTotalMilesNoDriver()));
                
            records.add(row);
        }

        return records;
        
        
    }
    @Override
    public List<ColumnHeader> getColumnSummaryHeaders() {
        return null;
    }

}
