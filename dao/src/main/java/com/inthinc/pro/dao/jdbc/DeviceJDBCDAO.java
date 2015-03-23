package com.inthinc.pro.dao.jdbc;

import com.inthinc.pro.dao.DeviceDAO;
import com.inthinc.pro.dao.VehicleDAO;
import com.inthinc.pro.dao.hessian.mapper.Mapper;
import com.inthinc.pro.dao.hessian.mapper.SimpleMapper;
import com.inthinc.pro.model.*;
import com.inthinc.pro.model.pagination.SortOrder;
import com.mysql.jdbc.Statement;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

/**
 * Device jdbc dao.
 */
public class DeviceJDBCDAO extends SimpleJdbcDaoSupport implements DeviceDAO{
    private static final String DEVICE_COLUMNS_STRING = "d.deviceID, d.acctID, d.baseID, d.status, d.autoLogoff," +
            " d.productVer, d.firmVer, d.witnessVer, d.emuFeatureMask, d.serialNum, d.name, d.imei, d.mcmid, d.altImei, " +
            " d.sim, d.phone, d.ephone, d.emuMd5, d.speedSet, d.accel, d.brake, d.turn, d.vert, d.modified, d.activated, " +
            " d.vehicleID, d.vehicleName, d.glcode ";

    private static final String DEVICE_SUFFIX = "FROM (select d.*, vdd.vehicleID, veh.name vehicleName from device d " +
            " LEFT OUTER JOIN vddlog vdd ON (d.deviceID = vdd.deviceID and vdd.stop is null)" +
            " LEFT OUTER JOIN vehicle veh on (veh.vehicleID = vdd.vehicleID)" +
            " ) d where d.deviceID = :deviceID ";

    private static final String DEVICE_SECOND = "FROM (select d.*, vdd.vehicleID, veh.name vehicleName from device d " +
            " LEFT OUTER JOIN vddlog vdd ON (d.deviceID = vdd.deviceID and vdd.stop is null)" +
            " LEFT OUTER JOIN vehicle veh on (veh.vehicleID = vdd.vehicleID)" +
            " ) d ";

    private static final String GET_DEVICE_IN = "select " + DEVICE_COLUMNS_STRING + " " + DEVICE_SECOND + "where d.acctID=:acctID" ;

    private static final String FIND_BY_IMEI = "select " + DEVICE_COLUMNS_STRING + " " + DEVICE_SECOND + "where imei like :imei";

    private static final String FIND_BY_SERIALNUM = "select " + DEVICE_COLUMNS_STRING + " " + DEVICE_SECOND + " where serialNum like :serialNum";

    private static final String INSERT_DEVICE = "INSERT INTO device" +
                    "(acctID, baseID, status, productVer, firmVer, witnessVer, serialNum, name, imei, mcmid, altImei, sim, phone, emuMd5, modified)" +
                    "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)" ;

    private static final String UPDATE_DEVICE = "UPDATE device set acctID=?, baseID=?, status=?, productVer=?, firmVer=?, witnessVer=?, serialNum=?, name=?, imei=?, mcmid=?, altImei=?, sim=?, phone=?, emuMd5=?, modified=? where deviceID=?";

    private static final String DEL_DEVICE_BY_ID = "DELETE FROM device WHERE deviceID = ?";
    private static final Integer PRODVER_WS820 = 2;
    private static final Integer PRODVER_WSAND = 12;
    private Mapper mapper = new SimpleMapper();

    private static final String INSERT_FWD = "INSERT INTO fwd (deviceID, driverID, vehicleID, personID, fwdCmd, fwdint, fwdStr, tries, status, created, modified)"
                                             + "VALUES(? ,? ,? ,? ,? ,? ,? ,? ,?, ?, ?)" ;

    private static final String INSERT_FWD_SIRIDIUM = "INSERT INTO Fwd_WSiridium"
                    + " (data, created, modified, processing, status, iridiumStatus, command, datatype, personID, driverID, vehicleID, deviceID)"
                    + " VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    //get ForwardCommand
    private static final String GET_FWD = "select * from fwd ";
    private static final String GET_FWD_LIST = GET_FWD + "where deviceID=:deviceID and status=:status";

    private VehicleDAO vehicleDAO;

    private ParameterizedRowMapper<Device> deviceMapper = new ParameterizedRowMapper<Device>() {
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
            device.setGlcode(getStringOrNullFromRS(rs, "glcode"));
            device.setAltimei(getStringOrNullFromRS(rs, "altImei"));

            return device;
        }
    };

    private String getStringOrNullFromRS(ResultSet rs, String columnName) throws SQLException {
        return rs.getObject(columnName) == null ? null : rs.getString(columnName);
    }

    private Integer getIntOrNullFromRS(ResultSet rs, String columnName) throws SQLException {
        return rs.getObject(columnName) == null ? null : (int) rs.getLong(columnName);
    }

    private Date getDateOrNullFromRS(ResultSet rs, String columnName) throws SQLException {
        return rs.getObject(columnName) == null ? null : rs.getDate(columnName);
    }


    @Override
    public Device findByID(Integer deviceID) {
        String deviceSelect =  "select "+ DEVICE_COLUMNS_STRING +" " + DEVICE_SUFFIX;
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("deviceID", deviceID);

        return getSimpleJdbcTemplate().queryForObject(deviceSelect, deviceMapper, params);
    }

    @Override
    public List<Device> getDevicesByAcctID(Integer accountID) {
        try {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("acctID", accountID);
            StringBuilder deviceSelect = new StringBuilder(GET_DEVICE_IN);
            List<Device> deviceList = getSimpleJdbcTemplate().query(deviceSelect.toString(), deviceMapper, params);

            return  deviceList;
        }
        catch (EmptyResultDataAccessException e){
         return Collections.emptyList();
        }
    }

    @Override
    public Device findByIMEI(String imei) {
        try {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("imei", "" + imei + "");
            StringBuilder findbyIMEI = new StringBuilder(FIND_BY_IMEI);
            Device finByImei = getSimpleJdbcTemplate().queryForObject(findbyIMEI.toString(), deviceMapper, params);

            return finByImei;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public Device findBySerialNum(String serialNum) {
        try {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("serialNum", "" + serialNum + "");
            StringBuilder serialNumber = new StringBuilder(FIND_BY_SERIALNUM);
            Device findBySerialNum = getSimpleJdbcTemplate().queryForObject(serialNumber.toString(), deviceMapper, params);

            return findBySerialNum;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<ForwardCommand> getForwardCommands(Integer deviceID, ForwardCommandStatus status) {
        try {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("deviceID", deviceID);
            params.put("status", status.getCode());
            StringBuilder fwdCommandList = new StringBuilder(GET_FWD_LIST);
            List<ForwardCommand> fwdList = getSimpleJdbcTemplate().query(fwdCommandList.toString(), forwardCommandParameterizedRowMapper, params);

            return fwdList;
        } catch (EmptyResultDataAccessException e) {
            return Collections.emptyList();
        }
    }

    @Override
    public Integer queueForwardCommand(Integer deviceID, ForwardCommand forwardCommand) {
        Integer fwdIdcreated = 0;
        Device device = findByID(deviceID);
        Map <String, Object> forwardCommandMap = getMapper().convertToMap(forwardCommand);
        if (forwardCommandMap.containsKey("fwdID"))
            forwardCommandMap.remove("fwdID");
        ForwardCommand fwd = new ForwardCommand();
        ForwardCommandSpool fwds = new ForwardCommandSpool();
        if (device.getProductVer() == PRODVER_WS820 || device.getProductVer() == PRODVER_WSAND) {
            if (device.getImei().equals(device.getMcmid())) {
                // no satellite
                fwd.setStatus(ForwardCommandStatus.valueOf(1));
                fwd.setDeviceID(deviceID);
                fwd.setCmd(0);
                fwd.setTries(1);

            } else {
                fwds.setStatus(ForwardCommandStatus.valueOf(1));
                fwds.setDeviceID(deviceID);
                fwds.setCommand(0);

            }
        } else {
            fwd.setStatus(ForwardCommandStatus.valueOf(1));
            fwd.setDeviceID(deviceID);
            fwd.setCmd(0);
            fwd.setTries(1);
        }


        if (fwd!=null){
            if (forwardCommandMap.containsKey("fwdID"))
                fwd.setFwdID((Integer)forwardCommandMap.get("fwdID"));
            if(forwardCommandMap.containsKey("deviceID"))
                fwd.setDeviceID((Integer) forwardCommandMap.get("deviceID"));
            if(forwardCommandMap.containsKey("driverID"))
                fwd.setDriverID((Integer) forwardCommandMap.get("driverID"));
            if(forwardCommandMap.containsKey("vehicleID"))
                fwd.setVehicleID((Integer) forwardCommandMap.get("vehicleID"));
            if(forwardCommandMap.containsKey("personID"))
                fwd.setPersonID((Integer) forwardCommandMap.get("personID"));
            if(forwardCommandMap.containsKey("cmd"))
                fwd.setCmd((Integer) forwardCommandMap.get("cmd"));
            if(forwardCommandMap.containsKey("fwdInt"))
                fwd.setFwdInt((Integer) forwardCommandMap.get("fwdInt"));
            if(forwardCommandMap.containsKey("fwdStr"))
                fwd.setFwdStr((String) forwardCommandMap.get("fwdInt"));
            if(forwardCommandMap.containsKey("data"))
                fwd.setData(forwardCommandMap.get("data"));
            if(forwardCommandMap.containsKey("tries"))
                fwd.setTries((Integer) forwardCommandMap.get("tries"));
            if(forwardCommandMap.containsKey("status"))
                fwd.setStatus(ForwardCommandStatus.valueOf((Integer) forwardCommandMap.get("status")));
            if(forwardCommandMap.containsKey("created"))
                fwd.setCreated((Date) forwardCommandMap.get("created"));
            if (forwardCommandMap.containsKey("modified"))
                fwd.setModified((Date) forwardCommandMap.get("modified"));

            fwdIdcreated = createFwd(fwd);

        }else {
            if (forwardCommandMap.containsKey("fwdID"))
                fwds.setFwdID((Integer)forwardCommandMap.get("fwdID"));
            if(forwardCommandMap.containsKey("deviceID"))
                fwds.setDeviceID((Integer) forwardCommandMap.get("deviceID"));
            if(forwardCommandMap.containsKey("driverID"))
                fwd.setDriverID((Integer) forwardCommandMap.get("driverID"));
            if(forwardCommandMap.containsKey("vehicleID"))
                fwd.setVehicleID((Integer) forwardCommandMap.get("vehicleID"));
            if(forwardCommandMap.containsKey("personID"))
                fwd.setPersonID((Integer) forwardCommandMap.get("personID"));
            if(forwardCommandMap.containsKey("cmd"))
                fwds.setCommand((Integer) forwardCommandMap.get("cmd"));
            if(forwardCommandMap.containsKey("data"))
                fwds.setData((byte[]) forwardCommandMap.get("data"));
            if(forwardCommandMap.containsKey("status"))
                fwds.setStatus(ForwardCommandStatus.valueOf((Integer)forwardCommandMap.get("status")));
            if(forwardCommandMap.containsKey("created"))
                fwds.setCreated((Date) forwardCommandMap.get("created"));
            if (forwardCommandMap.containsKey("modified"))
                fwds.setModified((Date) forwardCommandMap.get("modified"));

            fwdIdcreated = createFwdSiridium(fwds);

        }

        return   fwdIdcreated;
    }

    @Override
    public Integer create(Integer integer, final Device entity) {
        JdbcTemplate jdbcTemplate = getJdbcTemplate();
        KeyHolder keyHolder = new GeneratedKeyHolder();
        PreparedStatementCreator psc = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(INSERT_DEVICE, Statement.RETURN_GENERATED_KEYS);

                ps.setInt(1, entity.getAccountID());

                if (entity.getBaseID() == null) {
                    ps.setNull(2, Types.NULL);
                } else {
                    ps.setInt(2, entity.getBaseID());
                }

                ps.setInt(3, entity.getStatus().getCode());
                ps.setInt(4, entity.getProductVer());

                if (entity.getFirmwareVersion() == null) {
                    ps.setNull(5, Types.NULL);
                } else {
                    ps.setInt(5, entity.getFirmwareVersion());
                }

                if (entity.getWitnessVersion() == null) {
                    ps.setNull(6, Types.NULL);
                } else {
                    ps.setInt(6, entity.getWitnessVersion());
                }

                ps.setString(7, entity.getSerialNum());
                ps.setString(8, entity.getName());
                ps.setString(9, entity.getImei());
                ps.setString(10, entity.getMcmid());

                ps.setString(11, entity.getAltimei());

                if (entity.getSim() == null) {
                    ps.setNull(12, Types.NULL);
                } else {
                    ps.setString(12, entity.getSim());
                }

                ps.setString(13, entity.getPhone());

                if (entity.getEmuMd5() == null) {
                    ps.setNull(14, Types.NULL);
                } else {
                    ps.setString(14, entity.getEmuMd5());
                }

                DateFormat dfa = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                dfa.setTimeZone(TimeZone.getTimeZone("UTC"));
                String modified = dfa.format(toUTC(new Date()));

                ps.setString(15, modified);

                logger.debug(ps.toString());
                return ps;
            }
        };
        jdbcTemplate.update(psc, keyHolder);
        return keyHolder.getKey().intValue();
    }

    @Override
    public Integer update(final Device entity) {
        JdbcTemplate jdbcTemplate = getJdbcTemplate();
        PreparedStatementCreator psc = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(UPDATE_DEVICE);

                ps.setInt(1, entity.getAccountID());

                if (entity.getBaseID() == null) {
                    ps.setNull(2, Types.NULL);
                } else {
                    ps.setInt(2, entity.getBaseID());
                }

                ps.setInt(3, entity.getStatus().getCode());
                ps.setInt(4, entity.getProductVer());

                if (entity.getFirmwareVersion() == null) {
                    ps.setNull(5, Types.NULL);
                } else {
                    ps.setInt(5, entity.getFirmwareVersion());
                }

                if (entity.getWitnessVersion() == null) {
                    ps.setNull(6, Types.NULL);
                } else {
                    ps.setInt(6, entity.getWitnessVersion());
                }

                ps.setString(7, entity.getSerialNum());
                ps.setString(8, entity.getName());
                ps.setString(9, entity.getImei());
                ps.setString(10, entity.getMcmid());

                ps.setString(11, entity.getAltimei());

                if (entity.getSim() == null) {
                    ps.setNull(12, Types.NULL);
                } else {
                    ps.setString(12, entity.getSim());
                }

                ps.setString(13, entity.getPhone());

                if (entity.getEmuMd5() == null) {
                    ps.setNull(14, Types.NULL);
                } else {
                    ps.setString(14, entity.getEmuMd5());
                }

                DateFormat dfm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                dfm.setTimeZone(TimeZone.getTimeZone("UTC"));
                String modified = dfm.format(toUTC(new Date()));

                ps.setString(15, modified);

                ps.setInt(16, entity.getDeviceID());

                logger.debug(ps.toString());
                return ps;
            }
        };
        jdbcTemplate.update(psc);
        return entity.getDeviceID();
    }

    @Override
    public Integer deleteByID(Integer deviceID) {
        Device device = findByID(deviceID);
        if(device.getVehicleID() != null)
            vehicleDAO.clearVehicleDevice(device.getVehicleID(), deviceID);

        return getJdbcTemplate().update(DEL_DEVICE_BY_ID, new Object[]{deviceID});
    }

    private ParameterizedRowMapper<ForwardCommand> forwardCommandParameterizedRowMapper = new ParameterizedRowMapper<ForwardCommand>() {
        @Override
        public ForwardCommand mapRow(ResultSet rs, int rowNum) throws SQLException {

            ForwardCommand fwdCommand = new ForwardCommand();
            fwdCommand.setFwdID(rs.getInt("fwdID"));
            fwdCommand.setCmd(rs.getInt("fwdCmd"));
            fwdCommand.setStatus(rs.getObject("status") == null ? null : ForwardCommandStatus.valueOf(rs.getInt("status")));
            fwdCommand.setPersonID(rs.getObject("personID") == null ? null : rs.getInt("personID"));
            fwdCommand.setDriverID(rs.getObject("driverID") == null ? null : rs.getInt("driverID"));
            fwdCommand.setVehicleID(rs.getObject("vehicleID") == null ? null : rs.getInt("vehicleID"));
            fwdCommand.setCreated(rs.getObject("created") == null ? null : rs.getDate("created"));
            fwdCommand.setModified(rs.getObject("modified") == null ? null : rs.getDate("modified"));

            return fwdCommand;
        }
    };

    public Integer createFwd (final ForwardCommand fwd) {
        JdbcTemplate jdbcTemplate = getJdbcTemplate();
        KeyHolder keyHolder = new GeneratedKeyHolder();
        PreparedStatementCreator psc = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(INSERT_FWD, Statement.RETURN_GENERATED_KEYS);

                ps.setInt(1, fwd.getDeviceID());

                if (fwd.getDriverID() == null) {
                    ps.setNull(2, Types.NULL);
                } else {
                    ps.setInt(2, fwd.getDriverID());
                }


                if (fwd.getVehicleID() == null) {
                    ps.setNull(3, Types.NULL);
                } else {
                    ps.setInt(3, fwd.getVehicleID());
                }

                if (fwd.getPersonID() == null) {
                    ps.setNull(4, Types.NULL);
                } else {
                    ps.setInt(4, fwd.getPersonID());
                }

                if (fwd.getCmd() == null) {
                    ps.setNull(5, Types.NULL);
                } else {
                    ps.setInt(5, fwd.getCmd());
                }

                if (fwd.getFwdInt() == null) {
                    ps.setNull(6, Types.NULL);
                } else {
                    ps.setInt(6, fwd.getFwdInt());
                }

                if (fwd.getFwdStr() == null) {
                    ps.setNull(7, Types.NULL);
                } else {
                    ps.setString(7, fwd.getFwdStr());
                }
                if (fwd.getTries() == null) {
                    ps.setNull(8, Types.NULL);
                } else {
                    ps.setInt(8, fwd.getTries());
                }

                if (fwd.getStatus() == null) {
                    ps.setNull(9, Types.NULL);
                } else {
                    ps.setInt(9, fwd.getStatus().getCode());
                }

                DateFormat dfa = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                dfa.setTimeZone(TimeZone.getTimeZone("UTC"));
                String created = dfa.format(toUTC(new Date()));

                ps.setString(10, created);
                ps.setString(11, created);

                logger.debug(ps.toString());
                return ps;
            }
        };
        jdbcTemplate.update(psc, keyHolder);
        return keyHolder.getKey().intValue();
    }

    public Integer createFwdSiridium (final ForwardCommandSpool fwdSpool) {
        JdbcTemplate jdbcTemplate = getJdbcTemplate();
        KeyHolder keyHolder = new GeneratedKeyHolder();
        PreparedStatementCreator psc = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(INSERT_FWD_SIRIDIUM, Statement.RETURN_GENERATED_KEYS);

                if (fwdSpool.getData() == null) {
                    ps.setNull(1, Types.NULL);
                } else {
                    ps.setBytes(1, fwdSpool.getData());
                }

                DateFormat dfa = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                dfa.setTimeZone(TimeZone.getTimeZone("UTC"));
                String created = dfa.format(toUTC(new Date()));

                ps.setString(2, created);
                ps.setString(3, created);

                if (fwdSpool.getProcessed() == null) {
                    ps.setNull(4, Types.NULL);
                } else {
                    ps.setInt(4, fwdSpool.getProcessed());
                }

                if (fwdSpool.getStatus() == null) {
                    ps.setNull(5, Types.NULL);
                } else {
                    ps.setInt(5, fwdSpool.getStatus().getCode());
                }

                if (fwdSpool.getIridiumStatus() == null) {
                    ps.setNull(6, Types.NULL);
                } else {
                    ps.setInt(6, fwdSpool.getIridiumStatus().getCode());
                }

                if (fwdSpool.getCommand() == null) {
                    ps.setNull(7, Types.NULL);
                } else {
                    ps.setInt(7, fwdSpool.getCommand());
                }

                if (fwdSpool.getDataType() == null) {
                    ps.setNull(8, Types.NULL);
                } else {
                    ps.setInt(8, fwdSpool.getDataType().getCode());
                }

                if (fwdSpool.getPersonID() == null) {
                    ps.setNull(9, Types.NULL);
                } else {
                    ps.setInt(9, fwdSpool.getPersonID());
                }

                if (fwdSpool.getDriverID() == null) {
                    ps.setNull(10, Types.NULL);
                } else {
                    ps.setInt(10, fwdSpool.getDriverID());
                }

                if (fwdSpool.getVehicleID() == null) {
                    ps.setNull(11, Types.NULL);
                } else {
                    ps.setInt(11, fwdSpool.getVehicleID());
                }

                if (fwdSpool.getDeviceID() == null) {
                    ps.setNull(12, Types.NULL);
                } else {
                    ps.setInt(12, fwdSpool.getDeviceID());
                }

                logger.debug(ps.toString());
                return ps;
            }
        };
        jdbcTemplate.update(psc, keyHolder);
        return keyHolder.getKey().intValue();
    }

    public VehicleDAO getVehicleDAO() {
        return vehicleDAO;
    }

    public void setVehicleDAO(VehicleDAO vehicleDAO) {
        this.vehicleDAO = vehicleDAO;
    }

    public Mapper getMapper() {
        return mapper;
    }

    public void setMapper(Mapper mapper) {
        this.mapper = mapper;
    }

    private Date toUTC(Date date) {
        DateTime dt = new DateTime(date.getTime()).toDateTime(DateTimeZone.UTC);
        return dt.toDate();

    }


}


