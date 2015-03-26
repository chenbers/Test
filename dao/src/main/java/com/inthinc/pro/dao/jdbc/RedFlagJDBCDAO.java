package com.inthinc.pro.dao.jdbc;

import com.inthinc.pro.dao.EventDAO;
import com.inthinc.pro.dao.RedFlagDAO;
import com.inthinc.pro.model.AlertSentStatus;
import com.inthinc.pro.model.RedFlag;
import com.inthinc.pro.model.RedFlagLevel;
import com.inthinc.pro.model.Status;
import com.inthinc.pro.model.configurator.ProductType;
import com.inthinc.pro.model.event.AggressiveDrivingEvent;
import com.inthinc.pro.model.event.BackingMultipleEvent;
import com.inthinc.pro.model.event.DOTStoppedEvent;
import com.inthinc.pro.model.event.DOTStoppedState;
import com.inthinc.pro.model.event.DVIREvent;
import com.inthinc.pro.model.event.Event;
import com.inthinc.pro.model.event.EventAttr;
import com.inthinc.pro.model.event.FullEvent;
import com.inthinc.pro.model.event.HOSNoHoursEvent;
import com.inthinc.pro.model.event.HOSNoHoursState;
import com.inthinc.pro.model.event.HardVertical820Event;
import com.inthinc.pro.model.event.IdleEvent;
import com.inthinc.pro.model.event.IgnitionOffMaintenanceEvent;
import com.inthinc.pro.model.event.InvalidDriverEvent;
import com.inthinc.pro.model.event.InvalidOccupantEvent;
import com.inthinc.pro.model.event.MaintenanceEvent;
import com.inthinc.pro.model.event.NoteType;
import com.inthinc.pro.model.event.ParkingBrakeEvent;
import com.inthinc.pro.model.event.ParkingBrakeState;
import com.inthinc.pro.model.event.QSIVersionEvent;
import com.inthinc.pro.model.event.SeatBeltEvent;
import com.inthinc.pro.model.event.SpeedingEvent;
import com.inthinc.pro.model.event.TextMessageEvent;
import com.inthinc.pro.model.event.TrailerDataEvent;
import com.inthinc.pro.model.event.TrailerProgrammedEvent;
import com.inthinc.pro.model.event.ValidDriverEvent;
import com.inthinc.pro.model.event.ValidOccupantEvent;
import com.inthinc.pro.model.event.WitnessVersionEvent;
import com.inthinc.pro.model.event.ZoneArrivalEvent;
import com.inthinc.pro.model.event.ZoneDepartureEvent;
import com.inthinc.pro.model.pagination.FilterOp;
import com.inthinc.pro.model.pagination.PageParams;
import com.inthinc.pro.model.pagination.SortOrder;
import com.inthinc.pro.model.pagination.TableFilterField;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TimeZone;

/**
 * RedFlag DAO.
 */
public class RedFlagJDBCDAO extends SimpleJdbcDaoSupport implements RedFlagDAO {
    private static String RED_FLAG_BASE_QUERY = "SELECT cn.attribs, cnv.noteID,cnv.driverID,cnv.vehicleID,cnv.type,cnv.aggType,cnv.time," +
            " cnv.speed, cnv.flags, cnv.latitude, cnv.longitude, cnv.topSpeed, cnv.avgSpeed, cnv.speedLimit, cnv.status," +
            " cnv.distance, cnv.deltaX, cnv.deltaY, cnv.deltaZ, cnv.forgiven, cnv.flagged, cnv.level, cnv.sent, cnv.idleLo, cnv.idleHi, cnv.zoneID, cnv.textId," +
            " cnv.textMsg, cnv.hazmatFlag, cnv.serviceId, cnv.trailerId, cnv.trailerIdOld, cnv.inspectionType, cnv.vehicleSafeToOperate, cnv.duration, cnv.groupID," +
            " cnv.driverGroupID, cnv.vehicleGroupID, cnv.personID, cnv.driverName, cnv.groupName, cnv.vehicleName, cnv.tzID, cnv.tzName  FROM cachedNoteView cnv, cachedNote cn " +
            " WHERE cnv.noteID = cn.noteID ";
    private static String RED_FLAG_QUERY = RED_FLAG_BASE_QUERY+ " and cnv.groupId in (select groupID from groups where groupPath like :groupID) and cnv.flagged=1 and cnv.time between :startDate and :endDate ";
    private static String RED_FLAG_QUERY_COUNT = "SELECT count(*) as nr FROM cachedNoteView WHERE groupId in (select groupID from groups where groupPath like :groupID) and flagged=1 and time between :startDate and :endDate ";
    private static String RED_FLAG_SINGLE_QUERY = RED_FLAG_BASE_QUERY + " and cnv.noteID = :noteID ";

    private static final Map<String, String> pagedColumnMapRedFlag = new HashMap<String, String>();

    static {
        pagedColumnMapRedFlag.put("level", "cnv.level");
        pagedColumnMapRedFlag.put("time", "cnv.time");
        pagedColumnMapRedFlag.put("groupName", "cnv.groupName");
        pagedColumnMapRedFlag.put("driverName", "cnv.driverName");
        pagedColumnMapRedFlag.put("vehicleName", "cnv.vehicleName");
        pagedColumnMapRedFlag.put("status", "cnv.status");
        pagedColumnMapRedFlag.put("type", "cnv.type");
        pagedColumnMapRedFlag.put("aggType", "cnv.aggType");
        pagedColumnMapRedFlag.put("forgiven", "cnv.forgiven");

    }

    private static final Map<String, String> pagedColumnMapRedFlagCount = new HashMap<String, String>();

    static {
        pagedColumnMapRedFlagCount.put("level", "level");
        pagedColumnMapRedFlagCount.put("time", "time");
        pagedColumnMapRedFlagCount.put("groupName", "groupName");
        pagedColumnMapRedFlagCount.put("driverName", "driverName");
        pagedColumnMapRedFlagCount.put("vehicleName", "vehicleName");
        pagedColumnMapRedFlagCount.put("status", "status");
        pagedColumnMapRedFlagCount.put("type", "type");
        pagedColumnMapRedFlagCount.put("aggType", "aggType");

    }

    private EventDAO eventDAO;

    private ParameterizedRowMapper<RedFlag> redFlagParameterizedRowMapper = new ParameterizedRowMapper<RedFlag>() {
        @Override
        public RedFlag mapRow(ResultSet rs, int rowNum) throws SQLException {
            SimpleDateFormat dateFormat = getDateFormat(TimeZone.getTimeZone("UTC"));

            String strTime = rs.getString("cnv.time");
            Date time = null;
            try {
                time = dateFormat.parse(strTime);
            } catch (Exception e) {
                logger.error(e);
            }

            Map<Object, Object> attrMap = new HashMap<Object, Object>();

            String attribs = rs.getString("cn.attribs");
            if (attribs != null && attribs.isEmpty()) {
                StringTokenizer stok = new StringTokenizer(attribs, ";");
                while (stok.hasMoreTokens()) {
                    String token = stok.nextToken();
                    String[] tok = token.split("=");
                    if (tok.length > 1) {
                        Integer key = Integer.valueOf(tok[0]);
                        Integer value = Integer.valueOf(tok[1]);

                        EventAttr eventAttr = EventAttr.valueOf(key);
                        if (eventAttr != null)
                            attrMap.put(eventAttr, value);
                    }
                }
            }


            NoteType noteType = NoteType.valueOf(rs.getInt("cnv.type"));
            Event event = null;

            if (noteType != null && noteType.getClass() != null) {

                Class clazz = noteType.getEventClass();
                if (clazz.equals(FullEvent.class)) {
                    FullEvent fullEvent = new FullEvent();
                    fullEvent.setDeltaX(rs.getInt("cnv.deltaX"));
                    fullEvent.setDeltaY(rs.getInt("cnv.deltaY"));
                    fullEvent.setDeltaZ(rs.getInt("cnv.deltaZ"));

                    event = (Event) fullEvent;
                } else if (clazz.equals(AggressiveDrivingEvent.class)) {
                    AggressiveDrivingEvent aggressiveDrivingEvent = new AggressiveDrivingEvent();
                    aggressiveDrivingEvent.setDeltaX(rs.getInt("cnv.deltaX"));
                    aggressiveDrivingEvent.setDeltaY(rs.getInt("cnv.deltaY"));
                    aggressiveDrivingEvent.setDeltaZ(rs.getInt("cnv.deltaZ"));
                    aggressiveDrivingEvent.setSeverity(rs.getInt("cnv.level"));

                    event = (Event) aggressiveDrivingEvent;
                } else if (clazz.equals(SeatBeltEvent.class)) {
                    SeatBeltEvent seatBeltEvent = new SeatBeltEvent();
                    seatBeltEvent.setAvgSpeed(rs.getInt("cnv.avgSpeed"));
                    seatBeltEvent.setDistance(rs.getInt("cnv.distance"));
                    seatBeltEvent.setTopSpeed(rs.getInt("cnv.topSpeed"));
                    if (attrMap.containsKey(EventAttr.MAX_RPM))
                        seatBeltEvent.setMaxRPM((Integer) attrMap.get(EventAttr.MAX_RPM));

                    event = (Event) seatBeltEvent;
                } else if (clazz.equals(IgnitionOffMaintenanceEvent.class)) {
                    IgnitionOffMaintenanceEvent ignitionOffMaintenanceEvent = new IgnitionOffMaintenanceEvent();
                    ignitionOffMaintenanceEvent.setAttrMap(attrMap);

                    if (attrMap.containsKey(EventAttr.ATTR_CHECK_ENGINE))
                        ignitionOffMaintenanceEvent.setCheckEngine((Integer) attrMap.get(EventAttr.ATTR_CHECK_ENGINE));

                    if (attrMap.containsKey(EventAttr.TRIP_DURATION))
                        ignitionOffMaintenanceEvent.setDriveTime((Integer) attrMap.get(EventAttr.TRIP_DURATION));

                    if (attrMap.containsKey(EventAttr.GGA_GPS_QUALITY))
                        ignitionOffMaintenanceEvent.setGpsQuality((Integer) attrMap.get(EventAttr.GGA_GPS_QUALITY));

                    if (attrMap.containsKey(EventAttr.ATTR_MALFUNCTION_INDICATOR_LAMP))
                        ignitionOffMaintenanceEvent.setMalfunctionIndicatorLamp((Integer) attrMap.get(EventAttr.ATTR_MALFUNCTION_INDICATOR_LAMP));

                    if (attrMap.containsKey(EventAttr.MPG))
                        ignitionOffMaintenanceEvent.setMpg((Integer) attrMap.get(EventAttr.MPG));

                    if (attrMap.containsKey(EventAttr.MPG_DISTANCE))
                        ignitionOffMaintenanceEvent.setMpgDistance((Integer) attrMap.get(EventAttr.MPG_DISTANCE));

                    event = (Event) ignitionOffMaintenanceEvent;
                } else if (clazz.equals(DOTStoppedEvent.class)) {
                    DOTStoppedEvent dotStoppedEvent = new DOTStoppedEvent();

                    dotStoppedEvent.setStatus(DOTStoppedState.valueOf(rs.getInt("cnv.status")));

                    event = (Event) dotStoppedEvent;
                } else if (clazz.equals(TrailerDataEvent.class)) {
                    TrailerDataEvent trailerDataEvent = new TrailerDataEvent();
                    if (attrMap.containsKey(EventAttr.DATA_LENGTH))
                        trailerDataEvent.setDataLength((Integer) attrMap.get(EventAttr.DATA_LENGTH));

                    if (attrMap.containsKey(EventAttr.HAZMAT_FLAG))
                        trailerDataEvent.setHazmatFlag((Integer) attrMap.get(EventAttr.HAZMAT_FLAG));

                    if (attrMap.containsKey(EventAttr.SERVICE_ID)) {
                        Integer serviceID = (Integer) attrMap.get(EventAttr.SERVICE_ID);
                        if (serviceID != null) {
                            trailerDataEvent.setServiceId(serviceID.toString());
                        }
                    }

                    if (attrMap.containsKey(EventAttr.TRAILER_ID)) {
                        Integer trailerID = (Integer) attrMap.get(EventAttr.TRAILER_ID);
                        if (trailerID != null) {
                            trailerDataEvent.setTrailerId(trailerID.toString());
                        }
                    }


                    event = (Event) trailerDataEvent;
                } else if (clazz.equals(HOSNoHoursEvent.class)) {
                    HOSNoHoursEvent hosNoHoursEvent = new HOSNoHoursEvent();

                    hosNoHoursEvent.setStatus(HOSNoHoursState.valueOf(rs.getInt("cnv.status")));

                    event = (Event) hosNoHoursEvent;
                } else if (clazz.equals(SpeedingEvent.class)) {
                    SpeedingEvent speedingEvent = new SpeedingEvent();

                    if (attrMap.containsKey(EventAttr.AVG_RPM))
                        speedingEvent.setAvgRPM((Integer) attrMap.get(EventAttr.AVG_RPM));

                    speedingEvent.setAvgSpeed(rs.getInt("cnv.avgSpeed"));
                    speedingEvent.setDistance(rs.getInt("cnv.distance"));
                    speedingEvent.setTopSpeed(rs.getInt("cnv.topSpeed"));

                    event = (Event) speedingEvent;
                } else if (clazz.equals(InvalidDriverEvent.class)) {
                    InvalidDriverEvent invalidDriverEvent = new InvalidDriverEvent();

                    event = (Event) invalidDriverEvent;
                } else if (clazz.equals(InvalidOccupantEvent.class)) {
                    InvalidOccupantEvent invalidOccupantEvent = new InvalidOccupantEvent();

                    event = (Event) invalidOccupantEvent;
                } else if (clazz.equals(ValidOccupantEvent.class)) {
                    ValidOccupantEvent validOccupantEvent = new ValidOccupantEvent();

                    event = (Event) validOccupantEvent;
                } else if (clazz.equals(DVIREvent.class)) {
                    DVIREvent dvirEvent = new DVIREvent();

                    if (attrMap.containsKey(EventAttr.DVIR_INSPECTION_TYPE))
                        dvirEvent.setInspectionType((Integer) attrMap.get(EventAttr.DVIR_INSPECTION_TYPE));

                    if (attrMap.containsKey(EventAttr.DVIR_VEHICLE_SAFE_TO_OPERATE))
                        dvirEvent.setVehicleSafeToOperate((Integer) attrMap.get(EventAttr.DVIR_VEHICLE_SAFE_TO_OPERATE));

                    event = (Event) dvirEvent;
                } else if (clazz.equals(ValidDriverEvent.class)) {
                    ValidDriverEvent validDriverEvent = new ValidDriverEvent();

                    if (attrMap.containsKey(EventAttr.MCM_RULESET))
                        validDriverEvent.setDotType((Integer) attrMap.get(EventAttr.MCM_RULESET));

                    event = (Event) validDriverEvent;
                } else if (clazz.equals(ZoneArrivalEvent.class)) {
                    ZoneArrivalEvent zoneArrivalEvent = new ZoneArrivalEvent();

                    event = (Event) zoneArrivalEvent;
                } else if (clazz.equals(ZoneDepartureEvent.class)) {
                    ZoneDepartureEvent zoneDepartureEvent = new ZoneDepartureEvent();

                    event = (Event) zoneDepartureEvent;
                } else if (clazz.equals(QSIVersionEvent.class)) {
                    QSIVersionEvent qsiVersionEvent = new QSIVersionEvent();

                    event = (Event) qsiVersionEvent;
                } else if (clazz.equals(WitnessVersionEvent.class)) {
                    WitnessVersionEvent witnessVersionEvent = new WitnessVersionEvent();

                    event = (Event) witnessVersionEvent;
                } else if (clazz.equals(HardVertical820Event.class)) {
                    HardVertical820Event hardVertical820Event = new HardVertical820Event();

                    if (attrMap.containsKey(EventAttr.SEVERITY))
                        hardVertical820Event.setSeverity((Integer) attrMap.get(EventAttr.SEVERITY));

                    event = (Event) hardVertical820Event;
                } else if (clazz.equals(ParkingBrakeEvent.class)) {
                    ParkingBrakeEvent parkingBrakeEvent = new ParkingBrakeEvent();

                    parkingBrakeEvent.setStatus(ParkingBrakeState.valueOf(rs.getInt("cnv.status")));

                    event = (Event) parkingBrakeEvent;
                } else if (clazz.equals(IdleEvent.class)) {
                    IdleEvent idleEvent = new IdleEvent();

                    if (attrMap.containsKey(EventAttr.HIGH_IDLE))
                        idleEvent.setHighIdle((Integer) attrMap.get(EventAttr.HIGH_IDLE));

                    if (attrMap.containsKey(EventAttr.LOW_IDLE))
                        idleEvent.setLowIdle((Integer) attrMap.get(EventAttr.LOW_IDLE));

                    event = (Event) idleEvent;
                } else if (clazz.equals(BackingMultipleEvent.class)) {
                    BackingMultipleEvent backingMultipleEvent = new BackingMultipleEvent();
                    backingMultipleEvent.setAttrMap(attrMap);
                    backingMultipleEvent.setDuration(rs.getInt("cnv.duration"));

                    event = (Event) backingMultipleEvent;
                } else if (clazz.equals(TrailerProgrammedEvent.class)) {
                    TrailerProgrammedEvent trailerProgrammedEvent = new TrailerProgrammedEvent();

                    if (attrMap.containsKey(EventAttr.TRAILER_ID)) {
                        Integer trailerID = (Integer) attrMap.get(EventAttr.TRAILER_ID);
                        if (trailerID != null) {
                            trailerProgrammedEvent.setTrailerId(trailerID.toString());
                        }
                    }

                    if (attrMap.containsKey(EventAttr.TRAILERID_OLD)) {
                        Integer trailerID = (Integer) attrMap.get(EventAttr.TRAILERID_OLD);
                        if (trailerID != null) {
                            trailerProgrammedEvent.setTrailerIdOld(trailerID.toString());
                        }
                    }

                    event = (Event) trailerProgrammedEvent;
                } else if (clazz.equals(MaintenanceEvent.class)) {
                    MaintenanceEvent maintenanceEvent = new MaintenanceEvent();

                    maintenanceEvent.setAttrMap(attrMap);

                    if (attrMap.containsKey(EventAttr.ATTR_BATTERY_VOLTAGE))
                        maintenanceEvent.setBatteryVoltage((Integer) attrMap.get(EventAttr.ATTR_BATTERY_VOLTAGE));

                    if (attrMap.containsKey(EventAttr.ATTR_DPF_FLOW_RATE))
                        maintenanceEvent.setDpfFlowRate((Integer) attrMap.get(EventAttr.ATTR_DPF_FLOW_RATE));

                    if (attrMap.containsKey(EventAttr.ENGINE_HOURS_X100))
                        maintenanceEvent.setEngineHours((Integer) attrMap.get(EventAttr.ENGINE_HOURS_X100));

                    if (attrMap.containsKey(EventAttr.ATTR_ENGINE_TEMP))
                        maintenanceEvent.setEngineTemp((Integer) attrMap.get(EventAttr.ATTR_ENGINE_TEMP));

                    if (attrMap.containsKey(EventAttr.ODOMETER))
                        maintenanceEvent.setOdometer((Integer) attrMap.get(EventAttr.ODOMETER));

                    if (attrMap.containsKey(EventAttr.ATTR_OIL_PRESSURE))
                        maintenanceEvent.setOilPresure((Integer) attrMap.get(EventAttr.ATTR_OIL_PRESSURE));

                    if (attrMap.containsKey(EventAttr.ATTR_TRANSMISSION_TEMP))
                        maintenanceEvent.setTransmissionTemp((Integer) attrMap.get(EventAttr.ATTR_TRANSMISSION_TEMP));

                    event = (Event) maintenanceEvent;
                } else {
                    event = new Event();
                }
            } else {
                event = new Event();
            }

            event.setAttribs(attribs);
            event.setAttrMap(attrMap);

            event.setNoteID(rs.getLong("cnv.noteID"));
            event.setForgiven(rs.getInt("cnv.forgiven"));
            event.setFlags(rs.getInt("cnv.flags"));
            event.setLatitude(rs.getDouble("cnv.latitude"));
            event.setLongitude(rs.getDouble("cnv.longitude"));
            event.setSpeed(rs.getInt("cnv.speed"));
            event.setTime(time);
            event.setType(NoteType.valueOf(rs.getInt("cnv.type")));
            event.setVehicleID(rs.getInt("cnv.vehicleID"));
            event.setDriverID(rs.getInt("cnv.driverID"));
            event.setGroupID(rs.getInt("cnv.groupID"));
            event.setDriverName(rs.getString("cnv.driverName"));
            event.setVehicleName(rs.getString("cnv.vehicleName"));
            event.setGroupName(rs.getString("cnv.groupName"));
            event.setDriverTimeZone(TimeZone.getTimeZone(rs.getString("cnv.tzName")));
            event.setSpeedLimit(rs.getInt("cnv.speedLimit"));


            List<Integer> msgIDList = new ArrayList<Integer>();
            msgIDList.add(rs.getInt("cnv.textId"));

            RedFlag redFlagItem = new RedFlag();
            redFlagItem.setEvent(event);
//            redFlagItem.setModified(modified);
//            redFlagItem.setCreated(created);
            redFlagItem.setLevel(RedFlagLevel.valueOf(rs.getInt("cnv.level")));
            redFlagItem.setMsgIDList(msgIDList);
            redFlagItem.setSent(AlertSentStatus.valueOf(rs.getInt("cnv.sent")));
            redFlagItem.setTimezone(TimeZone.getTimeZone(rs.getString("cnv.tzName")));

            return redFlagItem;
        }
    };

    @Override
    public Integer getRedFlagCount(Integer groupID, Date startDate, Date endDate, Integer includeForgiven, List<TableFilterField> filterList) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("groupID", "%/" + groupID + "/%");
        params.put("startDate", startDate);
        params.put("endDate", endDate);

        /***FILTERING***/
        StringBuilder redFlagSelectCount = new StringBuilder(addFiltersToQuery(filterList, RED_FLAG_QUERY_COUNT, params, pagedColumnMapRedFlagCount));

        List<Integer> cntRedFlag = getSimpleJdbcTemplate().query(redFlagSelectCount.toString(), redFlagCountRowMapper, params);

        Integer cnt = 0;
        if (cntRedFlag != null && !cntRedFlag.isEmpty())
            cnt = cntRedFlag.get(0);
        return cnt;
    }

    @Override
    public List<RedFlag> getRedFlagPage(Integer groupID, Date startDate, Date endDate, Integer includeForgiven, PageParams pageParams) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("groupID", "%/" + groupID + "/%");
        params.put("startDate", startDate);
        params.put("endDate", endDate);

        /***FILTERING***/
        StringBuilder redFlagSelect = new StringBuilder(addFiltersToQuery(pageParams.getFilterList(), RED_FLAG_QUERY, params, pagedColumnMapRedFlag));

        /***SORTING***/
        if (pageParams.getSort() != null && !pageParams.getSort().getField().isEmpty())
            redFlagSelect.append(" ORDER BY " + pagedColumnMapRedFlag.get(pageParams.getSort().getField()) + " " + (pageParams.getSort().getOrder() == SortOrder.ASCENDING ? "ASC" : "DESC"));

        /***PAGING***/
        if (pageParams.getStartRow() != null && pageParams.getEndRow() != null)
            redFlagSelect.append(" LIMIT " + pageParams.getStartRow() + ", " + ((pageParams.getEndRow() - pageParams.getStartRow()) + 1));

        List<RedFlag> redFlagList = getSimpleJdbcTemplate().query(redFlagSelect.toString(), redFlagParameterizedRowMapper, params);

        return redFlagList;
    }

    public RedFlag findByID(Long noteID) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("noteID", noteID);
        return getSimpleJdbcTemplate().queryForObject(RED_FLAG_SINGLE_QUERY, redFlagParameterizedRowMapper, params);
    }


    private TableFilterField treatCustomFilters(TableFilterField filter) {

        if (filter.getFilter() == null || filter.getFilter().toString().trim().isEmpty())
            return filter;

        String filterVal = filter.getFilter().toString();

        // status
        if (filter.getField().equals("status") && !isNumeric(filterVal)) {
            filter.setFilter(Status.valueOf(filterVal).getCode());
        }

        if (filter.getField().equals("type")) {
            if(filter.getFilter() instanceof String){
                return filter; // filter already processed
            } else if (filter.getFilter() instanceof ArrayList){
                filter.setFilterOp(FilterOp.IN);
                List<NoteType> noteTypes = (List<NoteType>) filter.getFilter();
                filterVal = "";
                for (NoteType noteType: noteTypes){
                    filterVal += noteType.getCode() + ",";
                }
                if (filterVal.contains(",")){
                    filterVal = filterVal.substring(0, filterVal.lastIndexOf(","));
                }
                filter.setFilter(filterVal);
            }else {
                filter.setFilter(NoteType.valueOf(filterVal).getCode());
            }
        }

        if (filter.getField().equals("aggType")) {
            if(filter.getFilter() instanceof String){
                return filter; // filter already processed
            } else if (filter.getFilter() instanceof ArrayList){
                filter.setFilterOp(FilterOp.IN);
                List<Integer> aggTypes = (List<Integer>) filter.getFilter();
                filterVal = "";
                for (Integer aggType: aggTypes){
                    filterVal += aggType + ",";
                }
                if (filterVal.contains(",")){
                    filterVal = filterVal.substring(0, filterVal.lastIndexOf(","));
                }
                filter.setFilter(filterVal);
            }else {
                filter.setFilter(NoteType.valueOf(filterVal).getCode());
            }
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

    private ParameterizedRowMapper<Integer> redFlagCountRowMapper = new ParameterizedRowMapper<Integer>() {
        @Override
        public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
            return getIntOrNullFromRS(rs, "nr");
        }
    };

    private Integer getIntOrNullFromRS(ResultSet rs, String columnName) throws SQLException {
        return rs.getObject(columnName) == null ? null : (int) rs.getLong(columnName);
    }


    @Override
    public RedFlag findByID(Integer integer) {
        throw new NotImplementedException();
    }

    @Override
    public Integer create(Integer integer, RedFlag entity) {
        throw new NotImplementedException();
    }

    @Override
    public Integer update(RedFlag entity) {
        throw new NotImplementedException();
    }

    @Override
    public Integer deleteByID(Integer integer) {
        throw new NotImplementedException();
    }

    public static SimpleDateFormat getDateFormat(TimeZone timeZone) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setTimeZone(timeZone);
        return dateFormat;
    }

    public EventDAO getEventDAO() {
        return eventDAO;
    }

    public void setEventDAO(EventDAO eventDAO) {
        this.eventDAO = eventDAO;
    }
}
