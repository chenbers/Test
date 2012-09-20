package com.inthinc.pro.dao.hessian;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.joda.time.Interval;

import com.inthinc.pro.dao.ReportDAO;
import com.inthinc.pro.dao.hessian.exceptions.EmptyResultSetException;
import com.inthinc.pro.dao.hessian.mapper.DriverPerformanceMapper;
import com.inthinc.pro.dao.util.DateUtil;
import com.inthinc.pro.model.DeviceReportItem;
import com.inthinc.pro.model.DriverReportItem;
import com.inthinc.pro.model.IdlingReportItem;
import com.inthinc.pro.model.VehicleReportItem;
import com.inthinc.pro.model.pagination.PageParams;
import com.inthinc.pro.model.pagination.TableFilterField;

public class ReportHessianDAO  extends GenericHessianDAO<Object, Integer> implements ReportDAO {
    private DriverPerformanceMapper driverPerformanceMapper;
//   private static final Logger logger = Logger.getLogger(ReportHessianDAO.class);
    @Override
    public Integer getDriverReportCount(Integer groupID, List<TableFilterField> filters) {
    
        List<TableFilterField> reportFilters = removeBlankFilters(filters); 

		try {
            setMapper(driverPerformanceMapper);
			Map<String, Object> map = getSiloService().getDriverReportCount(groupID, getMapper().convertList(reportFilters)); 
			return getCount(map);
		}
        catch (EmptyResultSetException e){
            return 0;
        }
        
	}

	public List<DriverReportItem> getDriverReportPage(Integer groupID, PageParams pageParams) {
		pageParams.setFilterList(removeBlankFilters(pageParams.getFilterList()));
		try {
            setMapper(driverPerformanceMapper);
			List<DriverReportItem> list = getMapper().convertToModelObject(getSiloService().getDriverReportPage(groupID, getMapper().convertToMap(pageParams)), DriverReportItem.class);
			return list;
		}
		catch (EmptyResultSetException e){
			return Collections.emptyList();
		}

	}


	@Override
	public Integer getDeviceReportCount(Integer groupID, List<TableFilterField> filters) {

		List<TableFilterField> reportFilters = removeBlankFilters(filters); 

		try {
			Map<String, Object> map = getSiloService().getDeviceReportCount(groupID, getMapper().convertList(reportFilters)); 
			return getCount(map);
		}
        catch (EmptyResultSetException e)
        {
            return 0;
        }
        
	}


	@Override
	public List<DeviceReportItem> getDeviceReportPage(Integer groupID, PageParams pageParams) {
		pageParams.setFilterList(removeBlankFilters(pageParams.getFilterList()));
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
		List<TableFilterField> reportFilters = removeBlankFilters(filters); 

		try {
			Map<String, Object> map = getSiloService().getIdlingReportCount(groupID, DateUtil.convertDateToSeconds(interval.getStart()), DateUtil.convertDateToSeconds(interval.getEnd()), 
							getMapper().convertList(reportFilters)); 
			return getCount(map);
		}
        catch (EmptyResultSetException e)
        {
            return 0;
        }
        
	}

	@Override
	public Integer getIdlingReportSupportsIdleStatsCount(Integer groupID, Interval interval, List<TableFilterField> filters) {
		if (filters == null)
           	filters = new ArrayList<TableFilterField>();
		List<TableFilterField> reportFilters = new ArrayList<TableFilterField>();
		for (TableFilterField filter : filters) 
			reportFilters.add(filter);
		reportFilters.add(new TableFilterField("hasRPM", Integer.valueOf(1)));
		
		try {
			Map<String, Object> map = getSiloService().getIdlingReportCount(groupID, DateUtil.convertDateToSeconds(interval.getStart()), DateUtil.convertDateToSeconds(interval.getEnd()), getMapper().convertList(reportFilters)); 
			return getCount(map);
		}
        catch (EmptyResultSetException e)
        {
            return 0;
        }
        
	}


	@Override
	public List<IdlingReportItem> getIdlingReportPage(Integer groupID, Interval interval, PageParams pageParams) {
		pageParams.setFilterList(removeBlankFilters(pageParams.getFilterList()));
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
		List<TableFilterField> reportFilters = removeBlankFilters(filters); 

		try {
			Map<String, Object> map = getSiloService().getVehicleReportCount(groupID, getMapper().convertList(reportFilters)); 
			return getCount(map);
		}
        catch (EmptyResultSetException e)
        {
            return 0;
        }
        
	}


	private List<TableFilterField> removeBlankFilters(
			List<TableFilterField> filters) {
		List<TableFilterField>  newFilters = new ArrayList<TableFilterField>();
		if (filters != null) {
			for (TableFilterField filter : filters) {
				if (filter.getFilter() == null || filter.getFilter().toString().isEmpty())
					continue;
				
				newFilters.add(filter);
			}
		}
		return newFilters;
	}

	@Override
	public List<VehicleReportItem> getVehicleReportPage(Integer groupID, PageParams pageParams) {
		pageParams.setFilterList(removeBlankFilters(pageParams.getFilterList()));
		try {
			return getMapper().convertToModelObject(getSiloService().getVehicleReportPage(groupID, getMapper().convertToMap(pageParams)), VehicleReportItem.class);
		}
		catch (EmptyResultSetException e)
		{
			return Collections.emptyList();
		}

	}

	@Override
	public Integer getIdlingVehicleReportCount(Integer groupID, Interval interval, List<TableFilterField> filters) {
		List<TableFilterField> reportFilters = removeBlankFilters(filters); 

		try {
			Map<String, Object> map = getSiloService().getIdlingVehicleReportCount(groupID, DateUtil.convertDateToSeconds(interval.getStart()), DateUtil.convertDateToSeconds(interval.getEnd()), 
							getMapper().convertList(reportFilters)); 
			return getCount(map);
		}
        catch (EmptyResultSetException e)
        {
            return 0;
        }
        
	}

	@Override
	public List<IdlingReportItem> getIdlingVehicleReportPage(Integer groupID, Interval interval, PageParams pageParams) {
		pageParams.setFilterList(removeBlankFilters(pageParams.getFilterList()));
		try {
			return getMapper().convertToModelObject(getSiloService().getIdlingVehicleReportPage(groupID, DateUtil.convertDateToSeconds(interval.getStart()), DateUtil.convertDateToSeconds(interval.getEnd()), getMapper().convertToMap(pageParams)), IdlingReportItem.class);
		}
		catch (EmptyResultSetException e)
		{
			return Collections.emptyList();
		}
	}
	
	@Override
	public Integer getIdlingVehicleReportSupportsIdleStatsCount(Integer groupID, Interval interval, List<TableFilterField> filters) {
		if (filters == null)
           	filters = new ArrayList<TableFilterField>();
		List<TableFilterField> reportFilters = new ArrayList<TableFilterField>();
		for (TableFilterField filter : filters) 
			reportFilters.add(filter);
		reportFilters.add(new TableFilterField("hasRPM", Integer.valueOf(1)));
		
		try {
			Map<String, Object> map = getSiloService().getIdlingVehicleReportCount(groupID, DateUtil.convertDateToSeconds(interval.getStart()), DateUtil.convertDateToSeconds(interval.getEnd()), getMapper().convertList(reportFilters)); 
			return getCount(map);
		}
        catch (EmptyResultSetException e)
        {
            return 0;
        }
        
	}

    public DriverPerformanceMapper getDriverPerformanceMapper() {
        return driverPerformanceMapper;
    }

    public void setDriverPerformanceMapper(DriverPerformanceMapper driverPerformanceMapper) {
        this.driverPerformanceMapper = driverPerformanceMapper;
    }

}
