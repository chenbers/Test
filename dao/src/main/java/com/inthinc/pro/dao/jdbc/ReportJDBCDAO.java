package com.inthinc.pro.dao.jdbc;

import com.inthinc.pro.dao.ReportDAO;
import com.inthinc.pro.dao.service.dto.Device;
import com.inthinc.pro.model.DeviceReportItem;
import com.inthinc.pro.model.DriverReportItem;
import com.inthinc.pro.model.IdlingReportItem;
import com.inthinc.pro.model.Status;
import com.inthinc.pro.model.VehicleReportItem;
import com.inthinc.pro.model.configurator.ProductType;
import com.inthinc.pro.model.pagination.FilterOp;
import com.inthinc.pro.model.pagination.PageParams;
import com.inthinc.pro.model.pagination.SortOrder;
import com.inthinc.pro.model.pagination.TableFilterField;
import org.apache.log4j.Logger;
import org.joda.time.Interval;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by infrasoft04 on 2/20/14.
 */
public class ReportJDBCDAO extends SimpleJdbcDaoSupport implements ReportDAO {
    private static final Logger logger = Logger.getLogger(ReportJDBCDAO.class);

    private static final String SELECT_ALL_getDriverReportPage="Select  driverPerformance. driverID,\n" +
            "driverPerformance. driverName,\n" +
            "driverPerformance. employeeID,\n" +
            "driverPerformance. groupID,\n" +
            "driverPerformance. groupName,\n" +
            "driverPerformance. vehicleID,\n" +
            "driverPerformance. vehicleName,\n" +
            "driverPerformance. milesDriven,\n" +
            "driverPerformance. overallScore,\n" +
            "driverPerformance. styleScore,\n" +
            "driverPerformance. speedScore,\n" +
            "driverPerformance. seatbeltScore, driver.status from siloDB.driverPerformance join siloDB.driver on driverPerformance.driverID = driver.driverID \n";
    private static final String WHERE_getDriverReportPage  = "WHERE  driverPerformance.groupID in (select groupID from groups where groupPath like :groupID)";
    private static final String SELECT_getVehicleReportPage="Select  vehicleID,\n" +
            " vehicleName,\n" +
            " vehicleYMM,\n" +
            " driverID,\n" +
            " driverName,\n" +
            " groupID,\n" +
            " groupName,\n" +
            " milesDriven,\n" +
            " odometer,\n" +
            " overallScore,\n" +
            " speedScore,\n" +
            " styleScore,\n" +
            " seatbeltScore from siloDB.vehiclePerformance";

    private static final String SELECT_getDeviceReportPage="Select vehicleID,  vehicleName,  deviceName,  deviceIMEI,  devicePhone,  deviceEPhone,  deviceStatus from siloDB.deviceVehicleView";
    private static final String SELECT_getIdlingReportCount="SELECT di.driverID as driverID, di.driverName as driverName, di.groupID as groupID, di.groupName as groupName, sum(agg.driveTime) as driveTime, sum(agg.idleLo) as lowIdleTime, sum(agg.idleHi) as highIdleTime, (BIT_OR(agg.emuFeatureMask) & 4 != 0) as hasRPM, driver.status FROM driverInfo di LEFT JOIN driver ON (driver.driverID = di.driverID) LEFT JOIN agg on agg.driverID=di.driverID WHERE" +
            "groupId in (select groupID from groups where groupPath like :groupId) AND agg.aggDate between :intervalStart AND :intervalEnd GROUP BY di.driverID; ";
    private static final Map<String, String> pagedColumnMap = new HashMap<String, String>();

    static {
        pagedColumnMap.put("name", "d.name");
        pagedColumnMap.put("vehicleID", "d.vehicleName");
        pagedColumnMap.put("productVersion", "d.productVer");
        pagedColumnMap.put("imei", "d.imei");
        pagedColumnMap.put("phone", "d.phone");
        pagedColumnMap.put("status", "d.status");
    }




    @Override
    public Integer getDriverReportCount(Integer groupID, List<TableFilterField> filters) {
        return null;
    }

    @Override
    public List<DriverReportItem> getDriverReportPage(Integer groupID, PageParams pageParams) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("groupID", "%/"+groupID+"/%");

        StringBuilder ReportSelect = new StringBuilder();
        ReportSelect.append(SELECT_ALL_getDriverReportPage+WHERE_getDriverReportPage);

        /***FILTERING***/
        ReportSelect = new StringBuilder(addFiltersToQuery(pageParams.getFilterList(), ReportSelect.toString(), params));

        /***SORTING***/
        if (pageParams.getSort() != null && !pageParams.getSort().getField().isEmpty())
            ReportSelect.append(" ORDER BY " + pagedColumnMap.get(pageParams.getSort().getField()) + " " + (pageParams.getSort().getOrder() == SortOrder.ASCENDING ? "ASC" : "DESC"));

        /***PAGING***/
        if (pageParams.getStartRow() != null && pageParams.getEndRow() != null)
            ReportSelect.append(" LIMIT " + pageParams.getStartRow() + ", " + ((pageParams.getEndRow() - pageParams.getStartRow()) + 1));

        List<DriverReportItem> ReportItemList = getSimpleJdbcTemplate().query(ReportSelect.toString(), ReportRowMapper, params);

        return ReportItemList;


    }

    @Override
    public Integer getVehicleReportCount(Integer groupID, List<TableFilterField> filters) {
         String SELECT_VEHICLE_COUNT="SELECT count(*) FROM vehiclePerformance WHERE groupId in (select groupID from groups where groupPath like "+groupID+")";
        StringBuilder vehicleReportSelect = new StringBuilder();
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("groupID", groupID);
        vehicleReportSelect.append(SELECT_VEHICLE_COUNT);
        vehicleReportSelect =new StringBuilder( addFiltersToQuery(filters, vehicleReportSelect.toString(), params));
        List<Integer> cntVehicle = getSimpleJdbcTemplate().query(vehicleReportSelect.toString(), VehicleCountRowMapper, params);
        Integer cnt = 0;
        if (cntVehicle!=null && !cntVehicle.isEmpty())
            cnt = cntVehicle.get(0);

        return cnt;
    }

    @Override
    public List<VehicleReportItem> getVehicleReportPage(Integer groupID, PageParams pageParams) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("groupID", "%/"+groupID+"/%");

        StringBuilder VehicleReportPageSelect = new StringBuilder();
        VehicleReportPageSelect.append(SELECT_getVehicleReportPage+" where groupId in (select groupID from groups where groupPath like "+groupID+") ");

        /***FILTERING***/
        VehicleReportPageSelect = new StringBuilder(addFiltersToQuery(pageParams.getFilterList(), VehicleReportPageSelect.toString(), params));

        /***SORTING***/
        if (pageParams.getSort() != null && !pageParams.getSort().getField().isEmpty())
            VehicleReportPageSelect.append(" ORDER BY " + pagedColumnMap.get(pageParams.getSort().getField()) + " " + (pageParams.getSort().getOrder() == SortOrder.ASCENDING ? "ASC" : "DESC"));

        /***PAGING***/
        if (pageParams.getStartRow() != null && pageParams.getEndRow() != null)
            VehicleReportPageSelect.append(" LIMIT " + pageParams.getStartRow() + ", " + ((pageParams.getEndRow() - pageParams.getStartRow()) + 1));

        List<VehicleReportItem> VehicleReportPageItemList = getSimpleJdbcTemplate().query(VehicleReportPageSelect.toString(), VehicleReportRowMapper, params);

        return VehicleReportPageItemList;
    }

    @Override
    public Integer getDeviceReportCount(Integer groupID, List<TableFilterField> filters) {
        String SELECT_DEVICE_COUNT="SELECT count(*) FROM deviceVehicleView WHERE groupId in (select groupID from groups where groupPath like "+groupID+")";
        StringBuilder deviceReportSelect = new StringBuilder();
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("groupID", groupID);
        deviceReportSelect.append(SELECT_DEVICE_COUNT);
        deviceReportSelect =new StringBuilder( addFiltersToQuery(filters, deviceReportSelect.toString(), params));
        List<Integer> cntDevice = getSimpleJdbcTemplate().query(deviceReportSelect.toString(), DeviceCountRowMapper, params);
        Integer cnt = 0;
        if (cntDevice!=null && !cntDevice.isEmpty())
            cnt = cntDevice.get(0);

        return cnt;
    }

    @Override
    public List<DeviceReportItem> getDeviceReportPage(Integer groupID, PageParams pageParams) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("groupID", "%/"+groupID+"/%");

        StringBuilder DeviceReportPageSelect = new StringBuilder();
        DeviceReportPageSelect.append(SELECT_getDeviceReportPage+" where groupId in (select groupID from groups where groupPath like "+groupID+") ");

        /***FILTERING***/
        DeviceReportPageSelect = new StringBuilder(addFiltersToQuery(pageParams.getFilterList(), DeviceReportPageSelect.toString(), params));

        /***SORTING***/
        if (pageParams.getSort() != null && !pageParams.getSort().getField().isEmpty())
            DeviceReportPageSelect.append(" ORDER BY " + pagedColumnMap.get(pageParams.getSort().getField()) + " " + (pageParams.getSort().getOrder() == SortOrder.ASCENDING ? "ASC" : "DESC"));

        /***PAGING***/
        if (pageParams.getStartRow() != null && pageParams.getEndRow() != null)
            DeviceReportPageSelect.append(" LIMIT " + pageParams.getStartRow() + ", " + ((pageParams.getEndRow() - pageParams.getStartRow()) + 1));

        List<DeviceReportItem> DeviceReportPageItemList = getSimpleJdbcTemplate().query(DeviceReportPageSelect.toString(), DeviceReportRowMapper, params);

        return DeviceReportPageItemList;

    }

    @Override
    public Integer getIdlingReportCount(Integer groupID, Interval interval, List<TableFilterField> filters) {
        //resolve later
        SimpleDateFormat dbFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("groupID", "%/"+groupID+"/%");
        params.put("intervalStart", dbFormat.format(interval.getStart().toDate()));
        params.put("intervalEnd", dbFormat.format(interval.getEnd().toDate()));

        StringBuilder IdlingReportCountSelect = new StringBuilder();
        IdlingReportCountSelect.append(SELECT_getIdlingReportCount+" where groupId in (select groupID from groups where groupPath like "+groupID+") ");

        return null;
    }

    @Override
    public Integer getIdlingReportSupportsIdleStatsCount(Integer groupID, Interval interval, List<TableFilterField> filters) {
        //copy getIdlingReportCount
        return null;
    }

    @Override
    public List<IdlingReportItem> getIdlingReportPage(Integer groupID, Interval interval, PageParams pageParams) {
        return null;
    }

    @Override
    public Integer getIdlingVehicleReportCount(Integer groupID, Interval interval, List<TableFilterField> filters) {
        return null;
    }

    @Override
    public List<IdlingReportItem> getIdlingVehicleReportPage(Integer groupID, Interval interval, PageParams pageParams) {
        return null;
    }

    @Override
    public Integer getIdlingVehicleReportSupportsIdleStatsCount(Integer groupID, Interval interval, List<TableFilterField> filters) {
        return null;
    }

    @Override
    public Object findByID(Integer integer) {
        return null;
    }

    @Override
    public Integer create(Integer integer, Object entity) {
//        Map<String, String> params = new HashMap<String, String>();
//        params.put("deviceID", String.valueOf(entity));
//        params.put("acctID", String.valueOf(integer));
//        getSimpleJdbcTemplate().update("insert into device (deviceID, acctID, imei, name, modified, activated) values (:deviceID, :acctID, 'test-imei', 'test-name', NOW(), NOW())", params);

        return null;
    }

    @Override
    public Integer update(Object entity) {
        return null;
    }

    @Override
    public Integer deleteByID(Integer integer) {
        return null;
    }

    private ParameterizedRowMapper<DeviceReportItem> DeviceReportRowMapper = new ParameterizedRowMapper<DeviceReportItem>() {
        @Override
        public DeviceReportItem mapRow(ResultSet rs, int rowNum) throws SQLException {
            DeviceReportItem DeviceReportItem = new DeviceReportItem();
//            VehicleReportItem.setDriverID(rs.getInt("driverID"));
//            DriverReportItem.setDriverName(rs.getString("driverName"));
//            DriverReportItem.setDriveTime(rs.getInt("driveTime"));
//            DriverReportItem.setGroupID(rs.getInt("groupID"));
//            DriverReportItem.setGroupName(rs.getString("groupName"));
//            DriverReportItem.setHasRPM(rs.getInt("hasRPM"));
//            DriverReportItem.setHighIdleTime(rs.getInt("highIdleTime"));
//            DriverReportItem.setLowIdleTime(rs.getInt("lowIdleTime"));
//            DriverReportItem.setStatus(Status.valueOf(rs.getInt("status")));
            return DeviceReportItem;
        }
    };
    private ParameterizedRowMapper<DriverReportItem> ReportRowMapper = new ParameterizedRowMapper<DriverReportItem>() {
        @Override
        public DriverReportItem mapRow(ResultSet rs, int rowNum) throws SQLException {
            DriverReportItem DriverReportItem = new DriverReportItem();
            DriverReportItem.setDriverID(rs.getInt("driverID"));
//            DriverReportItem.setDriverName(rs.getString("driverName"));
//            DriverReportItem.setDriveTime(rs.getInt("driveTime"));
//            DriverReportItem.setGroupID(rs.getInt("groupID"));
//            DriverReportItem.setGroupName(rs.getString("groupName"));
//            DriverReportItem.setHasRPM(rs.getInt("hasRPM"));
//            DriverReportItem.setHighIdleTime(rs.getInt("highIdleTime"));
//            DriverReportItem.setLowIdleTime(rs.getInt("lowIdleTime"));
//            DriverReportItem.setStatus(Status.valueOf(rs.getInt("status")));
            return DriverReportItem;
        }
    };
    private ParameterizedRowMapper<VehicleReportItem> VehicleReportRowMapper = new ParameterizedRowMapper<VehicleReportItem>() {
        @Override
        public VehicleReportItem mapRow(ResultSet rs, int rowNum) throws SQLException {
            VehicleReportItem VehicleReportItem = new VehicleReportItem();
//            VehicleReportItem.setDriverID(rs.getInt("driverID"));
//            DriverReportItem.setDriverName(rs.getString("driverName"));
//            DriverReportItem.setDriveTime(rs.getInt("driveTime"));
//            DriverReportItem.setGroupID(rs.getInt("groupID"));
//            DriverReportItem.setGroupName(rs.getString("groupName"));
//            DriverReportItem.setHasRPM(rs.getInt("hasRPM"));
//            DriverReportItem.setHighIdleTime(rs.getInt("highIdleTime"));
//            DriverReportItem.setLowIdleTime(rs.getInt("lowIdleTime"));
//            DriverReportItem.setStatus(Status.valueOf(rs.getInt("status")));
            return VehicleReportItem;
        }
    };

    private String addFiltersToQuery(final List<TableFilterField> filters,
                                     String queryStr, Map<String, Object> params) {
        if (filters != null && !filters.isEmpty()) {
            StringBuilder countFilter = new StringBuilder();
            for (TableFilterField filter : filters) {
                filter = treatCustomFilters(filter);

                if (filter.getField() != null && pagedColumnMap.containsKey(filter.getField()) && filter.getFilter() != null) {
                    String paramName = "filter_" + filter.getField();
                    if (filter.getFilter().toString().isEmpty())
                        continue;
                    if (filter.getFilterOp() == FilterOp.IN) {
                        countFilter.append(" AND " + pagedColumnMap.get(filter.getField()) + " in (:" + paramName + ")");
                        params.put(paramName, filter.getFilter());

                    } else if (filter.getFilterOp() == FilterOp.IN_OR_NULL) {
                        countFilter.append(" AND (" + pagedColumnMap.get(filter.getField()) + " in (:" + paramName + ") OR " + pagedColumnMap.get(filter.getField()) + " IS NULL)");
                        params.put(paramName, filter.getFilter());

                    } else {
                        countFilter.append(" AND " + pagedColumnMap.get(filter.getField()) + " LIKE :" + paramName);
                        params.put(paramName, "%" + filter.getFilter().toString() + "%");
                    }

                }
            }
            queryStr = queryStr + countFilter.toString();
        }
        return queryStr;
    }
    private TableFilterField treatCustomFilters(TableFilterField filter) {

        if (filter.getFilter() == null || filter.getFilter().toString().trim().isEmpty())
            return filter;

        String filterVal = filter.getFilter().toString();

        // status
        if (filter.getField().equals("status") && !isNumeric(filterVal)) {
            filter.setFilter(Status.valueOf(filterVal).getCode());
        }

        //product version
        if (filter.getField().equals("productVersion")) {
            if (filter.getFilter() instanceof List) {
                filter.setFilterOp(FilterOp.IN);
            } else if (!isNumeric(filterVal)) {
                ProductType productType = ProductType.valueOf(filterVal);
                List<Integer> versionList = (List<Integer>) productType.getFilter();
                if (versionList != null && !versionList.isEmpty()) {
                    filter.setFilterOp(FilterOp.IN);
                    filter.setFilter(versionList);
                }
            }
        }

        return filter;
    }
    private boolean isNumeric(String str) {
        boolean ret = true;
        try {
            Integer.valueOf(str.trim());
        } catch (NumberFormatException nf) {
            ret = false;
        }
        return ret;
    }
    private ParameterizedRowMapper<Integer> VehicleCountRowMapper = new ParameterizedRowMapper<Integer>() {
        @Override
        public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
            return getIntOrNullFromRS(rs, "nr");
        }
    };
    private ParameterizedRowMapper<Integer> DeviceCountRowMapper = new ParameterizedRowMapper<Integer>() {
        @Override
        public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
            return getIntOrNullFromRS(rs, "nr");
        }
    };
    private Integer getIntOrNullFromRS(ResultSet rs, String columnName) throws SQLException {
        return rs.getObject(columnName) == null ? null : (int) rs.getLong(columnName);
    }
}
