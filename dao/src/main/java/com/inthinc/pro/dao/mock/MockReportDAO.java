package com.inthinc.pro.dao.mock;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.Interval;

import com.inthinc.pro.dao.ReportDAO;
import com.inthinc.pro.model.DeviceReportItem;
import com.inthinc.pro.model.DriverReportItem;
import com.inthinc.pro.model.IdlingReportItem;
import com.inthinc.pro.model.VehicleReportItem;
import com.inthinc.pro.model.pagination.PageParams;
import com.inthinc.pro.model.pagination.TableFilterField;

public class MockReportDAO implements ReportDAO {
    private int numOfResults = 10;
    @Override
    public Object findByID(Integer id) {
        // TODO Auto-generated method stub
        return null;
    }

    public int getNumOfResults() {
        return numOfResults;
    }

    @Override
    public Integer create(Integer id, Object entity) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Integer update(Object entity) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Integer deleteByID(Integer id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Integer getDriverReportCount(Integer groupID, List<TableFilterField> filters) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<DriverReportItem> getDriverReportPage(Integer groupID, PageParams pageParams) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Integer getVehicleReportCount(Integer groupID, List<TableFilterField> filters){

        return numOfResults;
    }



    @Override
    public List<VehicleReportItem> getVehicleReportPage(Integer groupID, PageParams pageParams) {
        List<VehicleReportItem> retVal = new ArrayList<VehicleReportItem>();
        for(int i = 0; i < numOfResults; i++){
            VehicleReportItem item = new VehicleReportItem();
            item.setVehicleID(i);
            item.setVehicleName("vName_" + i);
            item.setDriverID(i);
            item.setDriverName("dName_"+i);
            item.setGroupID(groupID);
            item.setGroupName("GroupName");
            retVal.add(item);
        }

        return retVal;
    }

    @Override
    public Integer getDeviceReportCount(Integer groupID, List<TableFilterField> filters) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<DeviceReportItem> getDeviceReportPage(Integer groupID, PageParams pageParams) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Integer getIdlingReportCount(Integer groupID, Interval interval, List<TableFilterField> filters) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Integer getIdlingReportSupportsIdleStatsCount(Integer groupID, Interval interval, List<TableFilterField> filters) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<IdlingReportItem> getIdlingReportPage(Integer groupID, Interval interval, PageParams pageParams) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Integer getIdlingVehicleReportCount(Integer groupID, Interval interval, List<TableFilterField> filters) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<IdlingReportItem> getIdlingVehicleReportPage(Integer groupID, Interval interval, PageParams pageParams) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Integer getIdlingVehicleReportSupportsIdleStatsCount(Integer groupID, Interval interval, List<TableFilterField> filters) {
        // TODO Auto-generated method stub
        return null;
    }

}
