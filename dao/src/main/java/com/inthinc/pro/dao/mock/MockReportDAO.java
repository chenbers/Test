package com.inthinc.pro.dao.mock;

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

    @Override
    public Object findByID(Integer id) {
        // TODO Auto-generated method stub
        return null;
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
    public Integer getVehicleReportCount(Integer groupID, List<TableFilterField> filters) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<VehicleReportItem> getVehicleReportPage(Integer groupID, PageParams pageParams) {
        // TODO Auto-generated method stub
        return null;
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
