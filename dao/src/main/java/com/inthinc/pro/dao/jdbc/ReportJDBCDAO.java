package com.inthinc.pro.dao.jdbc;

import com.inthinc.pro.dao.ReportDAO;
import com.inthinc.pro.model.DeviceReportItem;
import com.inthinc.pro.model.DeviceStatus;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ReportJDBCDAO extends SimpleJdbcDaoSupport implements ReportDAO {
    private static final Logger logger = Logger.getLogger(ReportJDBCDAO.class);

    private static final String SELECT_DRIVERS = "Select  dp.driverID," +
            " dp.driverName,dp.employeeID,dp.groupID,dp.groupName,dp.vehicleID," +
            " dp.vehicleName,dp.milesDriven,dp.overallScore,dp.styleScore, dp.speedScore," +
            " dp.seatbeltScore, d.status from driverPerformance dp join driver d on dp.driverID = d.driverID";

    private static final String WHERE_DRIVERS = " WHERE dp.groupID in (select groupID from groups where groupPath like :groupID)";

    private static final Map<String, String> pagedColumnMapDriver = new HashMap<String, String>();

    static {
        pagedColumnMapDriver.put("groupName", "dp.groupName");
        pagedColumnMapDriver.put("groupID", "dp.groupID");
        pagedColumnMapDriver.put("driverName", "dp.driverName");
        pagedColumnMapDriver.put("driverID", "dp.driverID");
        pagedColumnMapDriver.put("vehicleName", "dp.vehicleName");
        pagedColumnMapDriver.put("vehicleID", "dp.vehicleID");
        pagedColumnMapDriver.put("employeeID", "dp.employeeID");
        pagedColumnMapDriver.put("milesDriven", "dp.milesDriven");
        pagedColumnMapDriver.put("overallScore", "dp.overallScore");
        pagedColumnMapDriver.put("speedScore", "dp.speedScore");
        pagedColumnMapDriver.put("styleScore", "dp.styleScore");
        pagedColumnMapDriver.put("seatbeltScore", "dp.seatbeltScore");

    }

    private static final String SELECT_VEHICLES = "Select vp.vehicleID, vp.vehicleName, vp.vehicleYMM, vp.driverID, vp.driverName," +
            " vp.groupID, vp.groupName, vp.milesDriven, vp.odometer, vp.overallScore, vp.speedScore, vp.styleScore, vp.seatbeltScore from vehiclePerformance vp";

    private static final Map<String, String> pagedColumnMapVehicle = new HashMap<String, String>();

    static {
        pagedColumnMapVehicle.put("vehicleID", "vp.vehicleID");
        pagedColumnMapVehicle.put("vehicleName", "vp.vehicleName");
        pagedColumnMapVehicle.put("groupName", "vp.groupName");
        pagedColumnMapVehicle.put("groupID", "vp.groupID");
        pagedColumnMapVehicle.put("driverID", "vp.driverID");
        pagedColumnMapVehicle.put("vehicleYMM", "vp.vehicleYMM");
        pagedColumnMapVehicle.put("milesDriven", "vp.milesDriven");
        pagedColumnMapVehicle.put("odometer", "vp.odometer");
        pagedColumnMapVehicle.put("overallScore", "vp.overallScore");
        pagedColumnMapVehicle.put("speedScore", "vp.speedScore");
        pagedColumnMapVehicle.put("styleScore", "vp.styleScore");
    }

    private static final String SELECT_DEVICES = "Select dvv.vehicleID , dvv.vehicleName, dvv.deviceName, dvv.deviceIMEI," +
            " dvv.devicePhone, dvv.deviceEPhone, dvv.deviceStatus from deviceVehicleView dvv";

    private static final Map<String, String> pagedColumnMapDevice = new HashMap<String, String>();

    static {
        pagedColumnMapDevice.put("deviceName", "dvv.deviceName");
        pagedColumnMapDevice.put("vehicleName", "dvv.vehicleName");
        pagedColumnMapDevice.put("vehicleID", "dvv.vehicleID");
        pagedColumnMapDevice.put("deviceIMEI", "dvv.deviceIMEI");
        pagedColumnMapDevice.put("devicePhone", "dvv.devicePhone");
        pagedColumnMapDevice.put("deviceStatus", "dvv.deviceStatus");
    }

    private static final String SELECT_IDLING_DRIVERS = "SELECT di.driverID ," +
            " di.driverName ," +
            " di.groupID , " +
            "di.groupName ," +
            " sum(agg.driveTime) as driveTime," +
            " sum(agg.idleLo) as lowIdleTime," +
            " sum(agg.idleHi) as highIdleTime," +
            " (BIT_OR(agg.emuFeatureMask) & 4 != 0) as hasRPM," +
            " d.status" +
            " FROM driverInfo di LEFT JOIN driver d ON (d.driverID = di.driverID) LEFT JOIN agg on agg.driverID=di.driverID WHERE" +
            " di.groupId in (select g.groupID from groups g where g.groupPath like :groupID) AND agg.aggDate between :intervalStart AND :intervalEnd";

    private static final Map<String, String> pagedColumnMapIdleReport = new HashMap<String, String>();

    static {
        pagedColumnMapIdleReport.put("groupName", "di.groupName");
        pagedColumnMapIdleReport.put("groupID", "di.groupID");
        pagedColumnMapIdleReport.put("driverName", "di.driverName");
        pagedColumnMapIdleReport.put("driverID", "di.driverID");
        pagedColumnMapIdleReport.put("driveTime", "driveTime");
        pagedColumnMapIdleReport.put("lowIdleTime", "lowIdleTime");
        pagedColumnMapIdleReport.put("highIdleTime", "highIdleTime");
        pagedColumnMapIdleReport.put("hasRPM", "hasRPM");

    }

    private static final String SELECT_IDLING_VEHICLES = "SELECT di.driverID as driverID," +
            " di.driverName as driverName," +
            " vi.groupID as groupID," +
            " g.name as groupName, " +
            "agg.vehicleID as vehicleID," +
            " vi.name as vehicleName, " +
            "sum(agg.driveTime) as driveTime, " +
            "sum(agg.idleLo) as lowIdleTime, " +
            "sum(agg.idleHi) as highIdleTime," +
            " (BIT_OR(agg.emuFeatureMask) & 4 != 0) as hasRPM," +
            " vi.status" +
            " FROM driverInfo di LEFT JOIN agg on agg.driverID=di.driverID " +
            "LEFT JOIN vehicle vi on agg.vehicleID=vi.vehicleID" +
            " LEFT JOIN groups g on vi.groupID = g.groupID " +
            "WHERE di.groupId in (select g.groupID from groups g where g.groupPath like :groupID)" +
            " AND agg.aggDate between :intervalStart AND :intervalEnd ";

    private static final Map<String, String> pagedColumnMapIdleVehicleReport = new HashMap<String, String>();

    static {
        pagedColumnMapIdleVehicleReport.put("driverID", "driverID");
        pagedColumnMapIdleVehicleReport.put("driverName", "driverName");
        pagedColumnMapIdleVehicleReport.put("groupID", "groupID");
        pagedColumnMapIdleVehicleReport.put("groupName", "groupName");
        pagedColumnMapIdleVehicleReport.put("vehicleID", "vehicleID");
        pagedColumnMapIdleVehicleReport.put("vehicleName", "vehicleName");
        pagedColumnMapIdleVehicleReport.put("driveTime", "driveTime");
        pagedColumnMapIdleVehicleReport.put("lowIdleTime", "lowIdleTime");
        pagedColumnMapIdleVehicleReport.put("highIdleTime", "highIdleTime");
        pagedColumnMapIdleVehicleReport.put("hasRPM", "hasRPM");

    }


    @Override
    public Integer getDriverReportCount(Integer groupID, List<TableFilterField> filters) {

        StringBuilder driverReportQuery = new StringBuilder("SELECT count(*) as nr FROM driverPerformance dp join driver d on (dp.driverID = d.driverID) WHERE " +
                "d.groupId in (select groupID from groups where groupPath like '%/" + groupID + "/%')");
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("groupID", groupID);
        driverReportQuery = new StringBuilder(addFiltersToQuery(filters, driverReportQuery.toString(), params, pagedColumnMapDriver));
        List<Integer> cntDriver = getSimpleJdbcTemplate().query(driverReportQuery.toString(), driverCountRowMapper, params);
        Integer cnt = 0;
        if (cntDriver != null && !cntDriver.isEmpty())
            cnt = cntDriver.get(0);

        return cnt;
    }

    @Override
    public List<DriverReportItem> getDriverReportPage(Integer groupID, PageParams pageParams) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("groupID", "%/" + groupID + "/%");

        String reportSelectDriver = SELECT_DRIVERS + WHERE_DRIVERS;

        /***FILTERING***/
        StringBuilder reportSelect = new StringBuilder(addFiltersToQuery(pageParams.getFilterList(), reportSelectDriver, params, pagedColumnMapDevice));

        /***SORTING***/
        if (pageParams.getSort() != null && !pageParams.getSort().getField().isEmpty())
            reportSelect.append(" ORDER BY " + pagedColumnMapDriver.get(pageParams.getSort().getField()) + " " + (pageParams.getSort().getOrder() == SortOrder.ASCENDING ? "ASC" : "DESC"));

        /***PAGING***/
        if (pageParams.getStartRow() != null && pageParams.getEndRow() != null)
            reportSelect.append(" LIMIT " + pageParams.getStartRow() + ", " + ((pageParams.getEndRow() - pageParams.getStartRow()) + 1));

        List<DriverReportItem> reportItemList = getSimpleJdbcTemplate().query(reportSelect.toString(), DriverReportRowMapper, params);

        return reportItemList;


    }

    @Override
    public Integer getVehicleReportCount(Integer groupID, List<TableFilterField> filters) {
        String SELECT_VEHICLE_COUNT = "SELECT count(*) as nr FROM vehiclePerformance vp WHERE vp.groupId in (select g.groupID from groups g where g.groupPath like '%/" + groupID + "/%')";
        StringBuilder vehicleReportSelect = new StringBuilder();
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("groupID", groupID);
        vehicleReportSelect.append(SELECT_VEHICLE_COUNT);
        vehicleReportSelect = new StringBuilder(addFiltersToQuery(filters, vehicleReportSelect.toString(), params, pagedColumnMapVehicle));
        List<Integer> cntVehicle = getSimpleJdbcTemplate().query(vehicleReportSelect.toString(), VehicleCountRowMapper, params);
        Integer cnt = 0;
        if (cntVehicle != null && !cntVehicle.isEmpty())
            cnt = cntVehicle.get(0);

        return cnt;
    }

    @Override
    public List<VehicleReportItem> getVehicleReportPage(Integer groupID, PageParams pageParams) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("groupID", "%/" + groupID + "/%");

        StringBuilder vehicleReportPageSelect = new StringBuilder();
        vehicleReportPageSelect.append(SELECT_VEHICLES + " WHERE groupId in (select groupID from groups where groupPath like :groupID) ");

        /***FILTERING***/
        vehicleReportPageSelect = new StringBuilder(addFiltersToQuery(pageParams.getFilterList(), vehicleReportPageSelect.toString(), params, pagedColumnMapVehicle));

        /***SORTING***/
        if (pageParams.getSort() != null && !pageParams.getSort().getField().isEmpty())
            vehicleReportPageSelect.append(" ORDER BY " + pagedColumnMapVehicle.get(pageParams.getSort().getField()) + " " + (pageParams.getSort().getOrder() == SortOrder.ASCENDING ? "ASC" : "DESC"));

        /***PAGING***/
        if (pageParams.getStartRow() != null && pageParams.getEndRow() != null)
            vehicleReportPageSelect.append(" LIMIT " + pageParams.getStartRow() + ", " + ((pageParams.getEndRow() - pageParams.getStartRow()) + 1));

        List<VehicleReportItem> vehicleReportPageItemList = getSimpleJdbcTemplate().query(vehicleReportPageSelect.toString(), VehicleReportRowMapper, params);

        return vehicleReportPageItemList;
    }

    @Override
    public Integer getDeviceReportCount(Integer groupID, List<TableFilterField> filters) {
        String SELECT_DEVICE_COUNT = "SELECT count(*) as nr FROM deviceVehicleView dvv WHERE dvv.groupId in (select g.groupID from groups g where g.groupPath like '%/" + groupID + "/%')";
        StringBuilder deviceReportSelect = new StringBuilder();
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("groupID", groupID);
        deviceReportSelect.append(SELECT_DEVICE_COUNT);
        deviceReportSelect = new StringBuilder(addFiltersToQuery(filters, deviceReportSelect.toString(), params, pagedColumnMapDevice));
        List<Integer> cntDevice = getSimpleJdbcTemplate().query(deviceReportSelect.toString(), DeviceCountRowMapper, params);
        Integer cnt = 0;
        if (cntDevice != null && !cntDevice.isEmpty())
            cnt = cntDevice.get(0);

        return cnt;
    }

    @Override
    public List<DeviceReportItem> getDeviceReportPage(Integer groupID, PageParams pageParams) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("groupID", "%/" + groupID + "/%");

        StringBuilder deviceReportPageSelect = new StringBuilder();
        deviceReportPageSelect.append(SELECT_DEVICES + " where dvv.groupId in (select g.groupID from groups g where g.groupPath like :groupID) ");

        /***FILTERING***/
        deviceReportPageSelect = new StringBuilder(addFiltersToQuery(pageParams.getFilterList(), deviceReportPageSelect.toString(), params, pagedColumnMapDevice));

        /***SORTING***/
        if (pageParams.getSort() != null && !pageParams.getSort().getField().isEmpty())
            deviceReportPageSelect.append(" ORDER BY " + pagedColumnMapDevice.get(pageParams.getSort().getField()) + " " + (pageParams.getSort().getOrder() == SortOrder.ASCENDING ? "ASC" : "DESC"));

        /***PAGING***/
        if (pageParams.getStartRow() != null && pageParams.getEndRow() != null)
            deviceReportPageSelect.append(" LIMIT " + pageParams.getStartRow() + ", " + ((pageParams.getEndRow() - pageParams.getStartRow()) + 1));

        List<DeviceReportItem> deviceReportPageItemList = getSimpleJdbcTemplate().query(deviceReportPageSelect.toString(), DeviceReportRowMapper, params);

        return deviceReportPageItemList;

    }

    @Override
    public Integer getIdlingReportCount(Integer groupID, Interval interval, List<TableFilterField> filters) {
        SimpleDateFormat dbFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");


        Map<String, Object> params = new HashMap<String, Object>();
        params.put("groupID", "%/" + groupID + "/%");
        params.put("intervalStart", dbFormat.format(interval.getStart().toDate()));
        params.put("intervalEnd", dbFormat.format(interval.getEnd().toDate()));

        StringBuilder idlingReportCountSelect = new StringBuilder(addIdlingFilter(filters, SELECT_IDLING_DRIVERS, params, pagedColumnMapIdleReport));
        String idlingQueryCount = "SELECT count(*) as nr from (" + idlingReportCountSelect.toString() + " GROUP BY di.driverID ) as x;";

        List<Integer> cntDevice = getSimpleJdbcTemplate().query(idlingQueryCount, idlingReportRowMapperCount, params);
        Integer cnt = 0;
        if (cntDevice != null && !cntDevice.isEmpty())
            cnt = cntDevice.get(0);

        return cnt;
    }

    @Override
    public List<IdlingReportItem> getIdlingReportPage(Integer groupID, Interval interval, PageParams pageParams) {
        SimpleDateFormat dbFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("groupID", "%/" + groupID + "/%");
        params.put("intervalStart", dbFormat.format(interval.getStart().toDate()));
        params.put("intervalEnd", dbFormat.format(interval.getEnd().toDate()));

        StringBuilder idlingReportPageSelect = new StringBuilder();
        idlingReportPageSelect.append(SELECT_IDLING_DRIVERS);

        /***FILTERING***/
        idlingReportPageSelect = new StringBuilder(addFiltersToQuery(pageParams.getFilterList(), idlingReportPageSelect.toString(), params, pagedColumnMapIdleReport));

        /***SORTING***/
        if (pageParams.getSort() != null && !pageParams.getSort().getField().isEmpty())
            idlingReportPageSelect.append(" GROUP BY di.driverID ");
        idlingReportPageSelect.append(" ORDER BY " + pagedColumnMapIdleReport.get(pageParams.getSort().getField()) + " " + (pageParams.getSort().getOrder() == SortOrder.ASCENDING ? "ASC" : "DESC"));

        /***PAGING***/
        if (pageParams.getStartRow() != null && pageParams.getEndRow() != null)
            idlingReportPageSelect.append(" LIMIT " + pageParams.getStartRow() + ", " + ((pageParams.getEndRow() - pageParams.getStartRow()) + 1));

        List<IdlingReportItem> idlingReportItemList = getSimpleJdbcTemplate().query(idlingReportPageSelect.toString(), idlingReportRowMapper, params);

        return idlingReportItemList;
    }

    @Override
    public Integer getIdlingVehicleReportCount(Integer groupID, Interval interval, List<TableFilterField> filters) {
        SimpleDateFormat dbFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("groupID", "%/" + groupID + "/%");
        params.put("intervalStart", dbFormat.format(interval.getStart().toDate()));
        params.put("intervalEnd", dbFormat.format(interval.getEnd().toDate()));

        StringBuilder idlingVehicleReportCountSelect = new StringBuilder(addIdlingFilter(filters, SELECT_IDLING_VEHICLES, params, pagedColumnMapIdleVehicleReport));
        String idlingVehicleQueryCount = "SELECT count(*) as nr from (" + idlingVehicleReportCountSelect.toString() + " GROUP BY agg.vehicleID) as x";

        List<Integer> cntDevice = getSimpleJdbcTemplate().query(idlingVehicleQueryCount, idlingVehicleRowMapperCount, params);
        Integer cnt = 0;
        if (cntDevice != null && !cntDevice.isEmpty())
            cnt = cntDevice.get(0);

        return cnt;
    }

    @Override
    public List<IdlingReportItem> getIdlingVehicleReportPage(Integer groupID, Interval interval, PageParams pageParams) {
        SimpleDateFormat dbFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("groupID", "%/" + groupID + "/%");
        params.put("intervalStart", dbFormat.format(interval.getStart().toDate()));
        params.put("intervalEnd", dbFormat.format(interval.getEnd().toDate()));

        StringBuilder idlingVehiclePageSelect = new StringBuilder();
        idlingVehiclePageSelect.append(SELECT_IDLING_VEHICLES);

        /***FILTERING***/
        idlingVehiclePageSelect = new StringBuilder(addFiltersToQuery(pageParams.getFilterList(), idlingVehiclePageSelect.toString(), params, pagedColumnMapIdleVehicleReport));

        /***SORTING***/
        if (pageParams.getSort() != null && !pageParams.getSort().getField().isEmpty())
            idlingVehiclePageSelect.append(" GROUP BY agg.vehicleID ");
        idlingVehiclePageSelect.append(" ORDER BY " + pagedColumnMapIdleVehicleReport.get(pageParams.getSort().getField()) + " " + (pageParams.getSort().getOrder() == SortOrder.ASCENDING ? "ASC" : "DESC"));

        /***PAGING***/
        if (pageParams.getStartRow() != null && pageParams.getEndRow() != null)
            idlingVehiclePageSelect.append(" LIMIT " + pageParams.getStartRow() + ", " + ((pageParams.getEndRow() - pageParams.getStartRow()) + 1));

        List<IdlingReportItem> idlingVehicleReportItemList = getSimpleJdbcTemplate().query(idlingVehiclePageSelect.toString(), idlingVehicleRowMapper, params);

        return idlingVehicleReportItemList;
    }

    @Override
    public Integer getIdlingVehicleReportSupportsIdleStatsCount(Integer groupID, Interval interval, List<TableFilterField> filters) {
        //copy getIdlingVehicleReportCount
        if (filters == null)
            filters = new ArrayList<TableFilterField>();
        List<TableFilterField> reportFilters = new ArrayList<TableFilterField>();
        for (TableFilterField filter : filters)
            reportFilters.add(filter);
        reportFilters.add(new TableFilterField("hasRPM", Integer.valueOf(1)));

        SimpleDateFormat dbFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("groupID", "%/" + groupID + "/%");
        params.put("intervalStart", dbFormat.format(interval.getStart().toDate()));
        params.put("intervalEnd", dbFormat.format(interval.getEnd().toDate()));


        StringBuilder idlingVehicleReportCountSelect = new StringBuilder(addIdlingFilter(reportFilters, SELECT_IDLING_VEHICLES, params, pagedColumnMapIdleVehicleReport));
        String idlingVehicleQueryCount = "SELECT count(*) as nr from (" + idlingVehicleReportCountSelect.toString() + " GROUP BY vi.vehicleID) as x";

        List<Integer> cntDevice = getSimpleJdbcTemplate().query(idlingVehicleQueryCount, idlingVehicleRowMapperCount, params);
        Integer cnt = 0;
        if (cntDevice != null && !cntDevice.isEmpty())
            cnt = cntDevice.get(0);

        return cnt;
    }

    @Override
    public Integer getIdlingReportSupportsIdleStatsCount(Integer groupID, Interval interval, List<TableFilterField> filters) {
        //copy getIdlingReportCount
        if (filters == null) filters = new ArrayList<TableFilterField>();
        List<TableFilterField> reportFilters = new ArrayList<TableFilterField>();
        for (TableFilterField filter : filters)
            reportFilters.add(filter);
        reportFilters.add(new TableFilterField("hasRPM", Integer.valueOf(1)));

        SimpleDateFormat dbFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("groupID", "%/" + groupID + "/%");
        params.put("intervalStart", dbFormat.format(interval.getStart().toDate()));
        params.put("intervalEnd", dbFormat.format(interval.getEnd().toDate()));

        StringBuilder idlingReportSupportsCountSelect = new StringBuilder(addIdlingFilter(reportFilters, SELECT_IDLING_DRIVERS, params, pagedColumnMapIdleReport));
        String idlingSupportsQueryCount = "SELECT count(*) as nr from (" + idlingReportSupportsCountSelect.toString() + ") as x;";

        List<Integer> cntDevice = getSimpleJdbcTemplate().query(idlingSupportsQueryCount, idlingReportRowMapperCount, params);
        Integer cnt = 0;
        if (cntDevice != null && !cntDevice.isEmpty())
            cnt = cntDevice.get(0);

        return cnt;
    }

    @Override
    public Object findByID(Integer integer) {
        return null;
    }

    @Override
    public Integer create(Integer integer, Object entity) {
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
            DeviceReportItem deviceReportItem = new DeviceReportItem();
            deviceReportItem.setDeviceIMEI(rs.getString("dvv.deviceIMEI"));
            deviceReportItem.setDeviceName(rs.getString("dvv.deviceName"));
            deviceReportItem.setDevicePhone(rs.getString("dvv.devicePhone"));
            deviceReportItem.setVehicleID(rs.getInt("dvv.vehicleID"));
            deviceReportItem.setVehicleName(rs.getString("dvv.vehicleName"));
            deviceReportItem.setDeviceStatus(rs.getObject("dvv.deviceStatus") == null ? null : DeviceStatus.valueOf(rs.getInt("dvv.deviceStatus")));
            return deviceReportItem;
        }
    };
    private ParameterizedRowMapper<DriverReportItem> DriverReportRowMapper = new ParameterizedRowMapper<DriverReportItem>() {
        @Override
        public DriverReportItem mapRow(ResultSet rs, int rowNum) throws SQLException {
            DriverReportItem driverReportItem = new DriverReportItem();
            driverReportItem.setDriverID(rs.getInt("dp.driverID"));
            driverReportItem.setDriverName(rs.getString("dp.driverName"));
            driverReportItem.setVehicleName(rs.getString("dp.vehicleName"));
            driverReportItem.setVehicleID(rs.getInt("dp.vehicleID") == 0 ? null : rs.getInt("dp.vehicleID"));
            driverReportItem.setEmployeeID(rs.getString("dp.employeeID"));
            driverReportItem.setGroupID(rs.getInt("dp.groupID"));
            driverReportItem.setGroupName(rs.getString("dp.groupName"));
            driverReportItem.setMilesDriven(rs.getDouble("dp.milesDriven")); //verify it should be odometer
            driverReportItem.setOverallScore(rs.getInt("dp.overallScore") == 0 ? null : rs.getInt("dp.overallScore"));
            driverReportItem.setSeatbeltScore(rs.getInt("dp.seatbeltScore") == 0 ? null : rs.getInt("dp.seatbeltScore"));
            driverReportItem.setSpeedScore(rs.getInt("dp.speedScore") == 0 ? null : rs.getInt("dp.speedScore"));
            driverReportItem.setStyleScore(rs.getInt("dp.styleScore") == 0 ? null : rs.getInt("dp.styleScore"));
            return driverReportItem;
        }
    };
    private ParameterizedRowMapper<VehicleReportItem> VehicleReportRowMapper = new ParameterizedRowMapper<VehicleReportItem>() {
        @Override
        public VehicleReportItem mapRow(ResultSet rs, int rowNum) throws SQLException {
            VehicleReportItem vehicleReportItem = new VehicleReportItem();
            vehicleReportItem.setDriverID(rs.getInt("vp.driverID") == 0 ? null : rs.getInt("vp.driverID"));
            vehicleReportItem.setDriverName(rs.getString("vp.driverName"));
            vehicleReportItem.setGroupID(rs.getInt("vp.groupID"));
            vehicleReportItem.setGroupName(rs.getString("vp.groupName"));
            vehicleReportItem.setStyleScore(rs.getInt("vp.styleScore") == 0 ? null : rs.getInt("vp.styleScore"));
            vehicleReportItem.setSpeedScore(rs.getInt("vp.speedScore") == 0 ? null : rs.getInt("vp.speedScore"));
            vehicleReportItem.setOverallScore(rs.getInt("vp.overallScore") == 0 ? null : rs.getInt("vp.overallScore"));
            vehicleReportItem.setMilesDriven(rs.getInt("vp.milesDriven"));
            vehicleReportItem.setOdometer(rs.getInt("vp.odometer"));
            vehicleReportItem.setVehicleID(rs.getInt("vp.vehicleID"));
            vehicleReportItem.setVehicleName(rs.getString("vp.vehicleName"));
            vehicleReportItem.setVehicleYMM(rs.getString("vp.vehicleYMM"));
            return vehicleReportItem;
        }
    };

    private ParameterizedRowMapper<IdlingReportItem> idlingReportRowMapper = new ParameterizedRowMapper<IdlingReportItem>() {
        @Override
        public IdlingReportItem mapRow(ResultSet rs, int rowNum) throws SQLException {
            IdlingReportItem idlingReportItem = new IdlingReportItem();
            idlingReportItem.setDriverID(rs.getInt("di.driverID"));
            idlingReportItem.setDriverName(rs.getString("di.driverName"));
            idlingReportItem.setDriveTime(rs.getInt("driveTime"));
            idlingReportItem.setGroupID(rs.getInt("di.groupID"));
            idlingReportItem.setGroupName(rs.getString("di.groupName"));
            idlingReportItem.setHasRPM(rs.getInt("hasRPM"));
            idlingReportItem.setHighIdleTime(rs.getInt("highIdleTime"));
            idlingReportItem.setLowIdleTime(rs.getInt("lowIdleTime"));
            idlingReportItem.setStatus(Status.valueOf(rs.getInt("status")));
            return idlingReportItem;
        }
    };

    private ParameterizedRowMapper<IdlingReportItem> idlingVehicleRowMapper = new ParameterizedRowMapper<IdlingReportItem>() {
        @Override
        public IdlingReportItem mapRow(ResultSet rs, int rowNum) throws SQLException {
            IdlingReportItem idlingVehicleReportItem = new IdlingReportItem();
            idlingVehicleReportItem.setDriverID(rs.getInt("driverID"));
            idlingVehicleReportItem.setDriverName(rs.getString("driverName"));
            idlingVehicleReportItem.setDriveTime(rs.getInt("driveTime"));
            idlingVehicleReportItem.setGroupID(rs.getInt("groupID"));
            idlingVehicleReportItem.setGroupName(rs.getString("groupName"));
            idlingVehicleReportItem.setHasRPM(rs.getInt("hasRPM"));
            idlingVehicleReportItem.setHighIdleTime(rs.getInt("highIdleTime"));
            idlingVehicleReportItem.setLowIdleTime(rs.getInt("lowIdleTime"));
            idlingVehicleReportItem.setStatus(Status.valueOf(rs.getInt("status")));
            idlingVehicleReportItem.setVehicleID(rs.getInt("vehicleID"));
            idlingVehicleReportItem.setVehicleName(rs.getString("vehicleName"));
            return idlingVehicleReportItem;
        }
    };

    //IDLING FILTERS
    private String addIdlingFilter(final List<TableFilterField> filters, String queryStr, Map<String, Object> params, Map<String, String> pagedIdleColumnMap) {
        if (filters != null && !filters.isEmpty()) {
            StringBuilder countFilter = new StringBuilder();
            for (TableFilterField filter : filters) {
                filter = treatCustomFilters(filter);
                String paramName = "filter_" + filter.getField();
                if (filter.getField().equals("hasRPM")) {
                    if (filter.getFilter().toString().equals("1")) {
                        countFilter.append(" AND (agg.emuFeatureMask & 4) != 0");
                    } else {
                        countFilter.append(" AND (agg.emuFeatureMask & 4) = 0");
                    }
                } else if (filter.getField() != null && pagedIdleColumnMap.containsKey(filter.getField()) && filter.getFilter() != null) {
                    if (filter.getFilter().toString().isEmpty())
                        continue;
                    if (filter.getFilterOp() == FilterOp.IN) {
                        countFilter.append(" AND " + pagedIdleColumnMap.get(filter.getField()) + " in (:" + paramName + ")");
                        params.put(paramName, filter.getFilter());

                    } else if (filter.getFilterOp() == FilterOp.IN_OR_NULL) {
                        countFilter.append(" AND (" + pagedIdleColumnMap.get(filter.getField()) + " in (:" + paramName + ") OR " + pagedIdleColumnMap.get(filter.getField()) + " IS NULL)");
                        params.put(paramName, filter.getFilter());

                    } else {
                        countFilter.append(" AND " + pagedIdleColumnMap.get(filter.getField()) + " LIKE :" + paramName);
                        params.put(paramName, "%" + filter.getFilter().toString() + "%");
                    }

                }


            }
            queryStr = queryStr + countFilter.toString();
        }
        return queryStr;
    }

    private String addFiltersToQuery(final List<TableFilterField> filters,
                                     String queryStr, Map<String, Object> params, Map<String, String> pagedColumnMap) {
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

    private ParameterizedRowMapper<Integer> idlingReportRowMapperCount = new ParameterizedRowMapper<Integer>() {
        @Override
        public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
            return getIntOrNullFromRS(rs, "nr");
        }
    };
    private ParameterizedRowMapper<Integer> driverCountRowMapper = new ParameterizedRowMapper<Integer>() {
        @Override
        public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
            return getIntOrNullFromRS(rs, "nr");
        }
    };
    private ParameterizedRowMapper<Integer> idlingVehicleRowMapperCount = new ParameterizedRowMapper<Integer>() {
        @Override
        public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
            return getIntOrNullFromRS(rs, "nr");
        }
    };

    private Integer getIntOrNullFromRS(ResultSet rs, String columnName) throws SQLException {
        return rs.getObject(columnName) == null ? null : (int) rs.getLong(columnName);
    }
}
