package com.inthinc.pro.dao.jdbc;

import com.inthinc.pro.model.Device;
import com.inthinc.pro.model.DeviceStatus;
import com.inthinc.pro.model.configurator.ProductType;
import com.inthinc.pro.model.pagination.FilterOp;
import com.inthinc.pro.model.pagination.PageParams;
import com.inthinc.pro.model.pagination.SortOrder;
import com.inthinc.pro.model.pagination.TableFilterField;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminDeviceJDBCDAO extends SimpleJdbcDaoSupport {

    private static final Logger logger = Logger.getLogger(AdminDeviceJDBCDAO.class);

    private static final String PAGED_DEVICE_COLUMNS_STRING = "d.deviceID, d.acctID, d.baseID, d.status, d.autoLogoff," +
            " d.productVer, d.firmVer, d.witnessVer, d.emuFeatureMask, d.serialNum, d.name, d.imei, d.mcmid, d.altImei, " +
            " d.sim, d.phone, d.ephone, d.emuMd5, d.speedSet, d.accel, d.brake, d.turn, d.vert, d.modified, d.activated, " +
            " d.vehicleID, d.vehicleName ";

    private static final String PAGED_DEVICE_SUFFIX = "FROM (select d.*, vdd.vehicleID, veh.name vehicleName from device d " +
            " LEFT OUTER JOIN vddlog vdd ON (d.deviceID = vdd.deviceID and vdd.stop is null)" +
            " LEFT OUTER JOIN vehicle veh on (veh.vehicleID = vdd.vehicleID)" +
            " ) d where d.acctID = :acctID and d.status != 3";

    private static final String PAGED_DEVICE_SELECT = "SELECT " + PAGED_DEVICE_COLUMNS_STRING + " " + PAGED_DEVICE_SUFFIX;

    private static final String PAGED_DEVICE_COUNT = "SELECT COUNT(*) nr " + PAGED_DEVICE_SUFFIX;

    private static final Map<String, String> pagedColumnMap = new HashMap<String, String>();

    static {
        pagedColumnMap.put("name", "d.name");
        pagedColumnMap.put("vehicleID", "d.vehicleName");
        pagedColumnMap.put("productVersion", "d.productVer");
        pagedColumnMap.put("imei", "d.imei");
        pagedColumnMap.put("phone", "d.phone");
        pagedColumnMap.put("status", "d.status");
    }


    public Integer getCount(Integer acctID, List<TableFilterField> filters) {
        String deviceCount = PAGED_DEVICE_COUNT;
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("acctID", acctID);
        deviceCount = addFiltersToQuery(filters, deviceCount, params);
        List<Integer> cntList = getSimpleJdbcTemplate().query(deviceCount, pagedDeviceCountRowMapper, params);
        Integer cnt = 0;
        if (cntList!=null && !cntList.isEmpty())
            cnt = cntList.get(0);

        return cnt;
    }

    public List<Device> getDevices(Integer acctID, PageParams pageParams) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("acctID", acctID);

        StringBuilder deviceSelect = new StringBuilder();
        deviceSelect.append(PAGED_DEVICE_SELECT);

        /***FILTERING***/
        deviceSelect = new StringBuilder(addFiltersToQuery(pageParams.getFilterList(), deviceSelect.toString(), params));

        /***SORTING***/
        if (pageParams.getSort() != null && !pageParams.getSort().getField().isEmpty())
            deviceSelect.append(" ORDER BY " + pagedColumnMap.get(pageParams.getSort().getField()) + " " + (pageParams.getSort().getOrder() == SortOrder.ASCENDING ? "ASC" : "DESC"));

        /***PAGING***/
        if (pageParams.getStartRow() != null && pageParams.getEndRow() != null)
            deviceSelect.append(" LIMIT " + pageParams.getStartRow() + ", " + ((pageParams.getEndRow() - pageParams.getStartRow()) + 1));

        List<Device> deviceList = getSimpleJdbcTemplate().query(deviceSelect.toString(), pagedDeviceRowMapper, params);
        return deviceList;
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

    private TableFilterField treatCustomFilters(TableFilterField filter) {

        if (filter.getFilter() == null || filter.getFilter().toString().trim().isEmpty())
            return filter;

        String filterVal = filter.getFilter().toString();

        // status
        if (filter.getField().equals("status") && !isNumeric(filterVal)) {
            filter.setFilter(DeviceStatus.valueOf(filterVal).getCode());
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

    private String getStringOrNullFromRS(ResultSet rs, String columnName) throws SQLException {
        return rs.getObject(columnName) == null ? null : rs.getString(columnName);
    }

    private Integer getIntOrNullFromRS(ResultSet rs, String columnName) throws SQLException {
        return rs.getObject(columnName) == null ? null : (int) rs.getLong(columnName);
    }

    private Date getDateOrNullFromRS(ResultSet rs, String columnName) throws SQLException {
        return rs.getObject(columnName) == null ? null : rs.getDate(columnName);
    }

    private ParameterizedRowMapper<Integer> pagedDeviceCountRowMapper = new ParameterizedRowMapper<Integer>() {
        @Override
        public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
            return getIntOrNullFromRS(rs, "nr");
        }
    };

    private ParameterizedRowMapper<Device> pagedDeviceRowMapper = new ParameterizedRowMapper<Device>() {
        @Override
        public Device mapRow(ResultSet rs, int rowNum) throws SQLException {
            Device device = new Device();

            device.setDeviceID(getIntOrNullFromRS(rs, "deviceID"));

            Integer vehicleId = getIntOrNullFromRS(rs, "vehicleID");
            if (vehicleId != null && vehicleId.intValue() == 0)
                vehicleId = null;
            device.setVehicleID(vehicleId);

            device.setVehicleName(getStringOrNullFromRS(rs, "vehicleName"));
            device.setAccountID(getIntOrNullFromRS(rs, "acctID"));
            device.setStatus(rs.getObject("status") == null ? null : DeviceStatus.valueOf(rs.getInt("status")));
            device.setName(getStringOrNullFromRS(rs, "name"));
            device.setSim(getStringOrNullFromRS(rs, "sim"));
            device.setPhone(getStringOrNullFromRS(rs, "phone"));
            device.setActivated(getDateOrNullFromRS(rs, "activated"));
            device.setImei(getStringOrNullFromRS(rs, "imei"));
            device.setSerialNum(getStringOrNullFromRS(rs, "serialNum"));
            device.setBaseID(getIntOrNullFromRS(rs, "baseID"));
            device.setFirmwareVersion(getIntOrNullFromRS(rs, "firmVer"));
            device.setAltimei(getStringOrNullFromRS(rs, "altImei"));
            device.setProductVer(getIntOrNullFromRS(rs, "productVer"));
            device.setMcmid(getStringOrNullFromRS(rs, "mcmid"));
            device.setWitnessVersion(getIntOrNullFromRS(rs, "witnessVer"));
            device.setEmuMd5(getStringOrNullFromRS(rs, "emuMd5"));

            return device;
        }
    };

    public void createTestDevice(int testAccountId, int testDeviceId) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("deviceID", String.valueOf(testDeviceId));
        params.put("acctID", String.valueOf(testAccountId));
        getSimpleJdbcTemplate().update("insert into device (deviceID, acctID, imei, name, modified, activated) values (:deviceID, :acctID, 'test-imei', 'test-name', NOW(), NOW())", params);
    }

    public void deleteTestDevice(int testDeviceId) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("deviceID", String.valueOf(testDeviceId));
        getSimpleJdbcTemplate().update("delete from device where deviceID = :deviceID", params);
    }
}
