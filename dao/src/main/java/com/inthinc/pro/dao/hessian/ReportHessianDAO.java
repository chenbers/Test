package com.inthinc.pro.dao.hessian;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.joda.time.Interval;

import com.inthinc.pro.dao.ReportDAO;
import com.inthinc.pro.dao.hessian.exceptions.EmptyResultSetException;
import com.inthinc.pro.dao.util.DateUtil;
import com.inthinc.pro.model.DeviceReportItem;
import com.inthinc.pro.model.DriverReportItem;
import com.inthinc.pro.model.IdlingReportItem;
import com.inthinc.pro.model.VehicleReportItem;
import com.inthinc.pro.model.pagination.PageParams;
import com.inthinc.pro.model.pagination.TableFilterField;

public class ReportHessianDAO  extends GenericHessianDAO<Object, Integer> implements ReportDAO {
	
	@Override
	public Integer getDriverReportCount(Integer groupID, List<TableFilterField> filters) {
		

		if (filters == null)
           	filters = new ArrayList<TableFilterField>();

		try {
			Map<String, Object> map = getSiloService().getDriverReportCount(groupID, getMapper().convertList(filters)); 
			return getCount(map);
		}
        catch (EmptyResultSetException e)
        {
            return 0;
        }
        
	}

	
	public List<DriverReportItem> getDriverReportPage(Integer groupID, PageParams pageParams) {
		try {
			return getMapper().convertToModelObject(getSiloService().getDriverReportPage(groupID, getMapper().convertToMap(pageParams)), DriverReportItem.class);
		}
		catch (EmptyResultSetException e)
		{
			return Collections.emptyList();
		}

	}


	@Override
	public Integer getDeviceReportCount(Integer groupID, List<TableFilterField> filters) {
		if (filters == null)
           	filters = new ArrayList<TableFilterField>();

		try {
			Map<String, Object> map = getSiloService().getDeviceReportCount(groupID, getMapper().convertList(filters)); 
			return getCount(map);
		}
        catch (EmptyResultSetException e)
        {
            return 0;
        }
        
	}


	@Override
	public List<DeviceReportItem> getDeviceReportPage(Integer groupID, PageParams pageParams) {
		try {
			return getMapper().convertToModelObject(getSiloService().getDeviceReportPage(groupID, getMapper().convertToMap(pageParams)), DeviceReportItem.class);
		}
		catch (EmptyResultSetException e)
		{
			return Collections.emptyList();
		}
	}


	@Override
	public Integer getIdlingReportCount(Integer groupID, Interval interval, List<TableFilterField> filters) {
		if (filters == null)
           	filters = new ArrayList<TableFilterField>();

		try {
			Map<String, Object> map = getSiloService().getIdlingReportCount(groupID, DateUtil.convertDateToSeconds(interval.getStart()), DateUtil.convertDateToSeconds(interval.getEnd()), getMapper().convertList(filters)); 
			return getCount(map);
		}
        catch (EmptyResultSetException e)
        {
            return 0;
        }
        
	}


	@Override
	public List<IdlingReportItem> getIdlingReportPage(Integer groupID, Interval interval, PageParams pageParams) {
		try {
			return getMapper().convertToModelObject(getSiloService().getIdlingReportPage(groupID, DateUtil.convertDateToSeconds(interval.getStart()), DateUtil.convertDateToSeconds(interval.getEnd()), getMapper().convertToMap(pageParams)), IdlingReportItem.class);
		}
		catch (EmptyResultSetException e)
		{
			return Collections.emptyList();
		}
	}


	@Override
	public Integer getVehicleReportCount(Integer groupID, List<TableFilterField> filters) {
		if (filters == null)
           	filters = new ArrayList<TableFilterField>();

		try {
			Map<String, Object> map = getSiloService().getVehicleReportCount(groupID, getMapper().convertList(filters)); 
			return getCount(map);
		}
        catch (EmptyResultSetException e)
        {
            return 0;
        }
        
	}


	@Override
	public List<VehicleReportItem> getVehicleReportPage(Integer groupID, PageParams pageParams) {
		try {
			return getMapper().convertToModelObject(getSiloService().getVehicleReportPage(groupID, getMapper().convertToMap(pageParams)), VehicleReportItem.class);
		}
		catch (EmptyResultSetException e)
		{
			return Collections.emptyList();
		}

	}
	

}
