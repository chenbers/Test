package com.inthinc.pro.dao.hessian;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.sql.DataSource;

import com.inthinc.pro.ProDAOException;
import com.inthinc.pro.dao.jdbc.GenericJDBCDAO;
import com.inthinc.pro.model.event.Event;
import com.inthinc.pro.model.event.EventAttr;
import com.inthinc.pro.model.event.EventSubCategory;
import com.inthinc.pro.model.event.NoteType;

public class EventHessianJdbcHybridDAO extends EventHessianDAO<Event, Integer> {
    
    private static final long serialVersionUID = -3078428677565491569L;
    private DataSource dataSource;
    private static List<NoteType> loginNoteType = new ArrayList<NoteType>();
    private static SimpleDateFormat dfUTC = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    private static String driverQuery = "SELECT p.first,p.last FROM driver d,person p WHERE d.personID=p.personID AND d.driverID=?";
    static {
        dfUTC.setTimeZone(TimeZone.getTimeZone("UTC"));
        // tiwipro
        loginNoteType.add(NoteType.NEW_DRIVER);
        // waysmart
        loginNoteType.add(NoteType.INVALID_DRIVER);
        loginNoteType.add(NoteType.INVALID_OCCUPANT);
        loginNoteType.add(NoteType.VALID_OCCUPANT);
        loginNoteType.add(NoteType.NEWDRIVER_HOSRULE);
    }
    
    public DataSource getDataSource() {
        return dataSource;
    }
    
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    
    public EventHessianJdbcHybridDAO() {
        super();
    }
    
    @Override
    public List<Event> getEventsForDriver(Integer driverID, Date startDate, Date endDate, List<NoteType> noteTypes, Integer includeForgiven) {
        
        if (noteTypes.size() == 0) {
            return Collections.emptyList();
        } else {
            Connection conn = null;
            PreparedStatement statement = null;
            ResultSet rs = null;
            HashMap<Integer, Object> attrMap = null;
            HashMap<String, Object> eventmap = null;
            List<Map<String, Object>> eventlist = new ArrayList<Map<String, Object>>();
            String driverEventsquery = getQueryString(startDate, endDate, noteTypes, includeForgiven, dfUTC);
            int severity = 1;
            try {
                conn = getConnection();
                if (loginNoteType.contains(noteTypes.get(0))) {
                    statement = (PreparedStatement) conn.prepareStatement(driverQuery);
                    statement.setInt(1, driverID);
                    rs = statement.executeQuery();
                    while (rs.next()) {
                        attrMap = new HashMap<Integer, Object>();
                        attrMap.put(EventAttr.DRIVER_STR.getIndex(), rs.getString("last") + rs.getString("first").substring(0, 1));
                    }
                    GenericJDBCDAO.close(rs);
                    GenericJDBCDAO.close(statement);
                }
                statement = (PreparedStatement) conn.prepareStatement(driverEventsquery);
                statement.setInt(1, driverID);
                rs = statement.executeQuery();

                while (rs.next()) {
                    eventmap = new HashMap<String, Object>();
                    if (isAggressiveDriving(rs.getInt("type"))) {
                        severity = getSeverity(rs.getInt("deltaX"), rs.getInt("deltaY"), rs.getInt("deltaZ"));
                        eventmap.put("severity", severity);
                    }
                    eventmap.put("noteID", rs.getLong("noteID"));
                    eventmap.put("driverID", rs.getInt("driverID"));
                    eventmap.put("vehicleID", rs.getInt("vehicleID"));
                    eventmap.put("type", rs.getInt("type"));
                    eventmap.put("time", getUTCDate(rs.getTimestamp("time")));
                    eventmap.put("xtime", getUTCDate(rs.getTimestamp("xtime")));
                    eventmap.put("speed", rs.getInt("speed"));
                    eventmap.put("flags", rs.getInt("flags"));
                    eventmap.put("latitude", rs.getDouble("latitude"));
                    eventmap.put("longitude", rs.getDouble("longitude"));
                    eventmap.put("topSpeed", rs.getInt("topSpeed"));
                    eventmap.put("avgSpeed", rs.getInt("avgSpeed"));
                    eventmap.put("speedLimit", rs.getInt("speedLimit"));
                    eventmap.put("status", rs.getInt("status"));
                    eventmap.put("distance", rs.getInt("distance"));
                    eventmap.put("deltaX", rs.getInt("deltaX"));
                    eventmap.put("deltaY", rs.getInt("deltaY"));
                    eventmap.put("deltaZ", rs.getInt("deltaZ"));
                    eventmap.put("forgiven", rs.getInt("forgiven"));
                    eventmap.put("flagged", rs.getInt("flagged"));
                    eventmap.put("level", rs.getInt("level"));
                    eventmap.put("sent", rs.getInt("sent"));
                    eventmap.put("idleLo", rs.getInt("idleLo"));
                    eventmap.put("idleHi", rs.getInt("idleHi"));
                    eventmap.put("zoneID", rs.getInt("zoneID"));
                    eventmap.put("textId", rs.getInt("textId"));
                    eventmap.put("textMsg", rs.getString("textMsg"));
                    eventmap.put("hazmatFlag", rs.getInt("hazmatFlag"));
                    eventmap.put("serviceId", rs.getString("serviceId"));
                    eventmap.put("trailerId", rs.getString("trailerId"));
                    eventmap.put("inspectionType", rs.getInt("inspectionType"));
                    eventmap.put("vehicleSafeToOperate", rs.getString("vehicleSafeToOperate"));
                    if (attrMap != null) {
                        eventmap.put("attrMap", attrMap);
                    }
                    eventlist.add(eventmap);
                }
                
            } catch (SQLException e) {
                throw new ProDAOException(statement.toString(), e);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                GenericJDBCDAO.close(rs);
                GenericJDBCDAO.close(statement);
                GenericJDBCDAO.close(conn);
            }
            List<Event> elist = getMapper().convertToModelObject(eventlist, Event.class);
            return Event.cleanEvents(elist);
        }
    }
    
    private String getQueryString(Date startDate, Date endDate, List<NoteType> noteTypes, Integer includeForgiven, final SimpleDateFormat df) {
        StringBuilder drivereventbuilder = new StringBuilder("SELECT  noteID, driverID, vehicleID,type, time, xtime, speed, flags, latitude, longitude,")
                        .append(" topSpeed, avgSpeed, speedLimit, status, distance, deltaX, deltaY, deltaZ, forgiven, flagged,")
                        .append(" level,sent, idleLo,idleHi, zoneID, textId, textMsg, hazmatFlag, serviceId, trailerId, inspectionType,").append(" vehicleSafeToOperate    FROM  cachedNote")
                        .append(" WHERE driverID  = ?  AND (time BETWEEN '").append(df.format(startDate)).append("' AND '").append(df.format(endDate)).append("') ").append(" AND type IN (");
        for (int i = 0; i < noteTypes.size(); i++) {
            drivereventbuilder.append(noteTypes.get(i).getCode());
            if (i < noteTypes.size() - 1) {
                drivereventbuilder.append(",");
            }
        }
        drivereventbuilder.append(") ");
        if (includeForgiven == 0) {
            drivereventbuilder.append("AND forgiven<>1 ");
        }
        drivereventbuilder.append(" ORDER BY time DESC");
        return drivereventbuilder.toString();
    }
    
    private boolean isAggressiveDriving(int type) {
        List<NoteType> noteTypes = EventSubCategory.DRIVING_STYLE.getNoteTypesInSubCategory();
        for (int i = 0; i < noteTypes.size(); i++) {
            if (noteTypes.get(i).getCode() == type)
                return true;
        }
        return false;
    }
    
    private Date getUTCDate(Timestamp date) throws ParseException {
        return dfUTC.parse(date.toString());
    }
    
    private Integer getSeverity(Integer deltax, Integer deltay, Integer deltaz) {
        Integer severity = 1;
        Integer delta = Math.max(Math.max(Math.abs(deltax), Math.abs(deltay)), Math.abs(deltaz));
        if (delta == deltax) {
            // Accel 50..225
            if (delta < 50) {
                severity = 1;
            } else if (delta > 224) {
                severity = 5;
            } else {
                severity = ((delta - 50) / 35) + 1;
            }
        } else if (delta == Math.abs(deltax)) {
            // Brake 70..225
            if (delta < 70) {
                severity = 1;
            } else if (delta > 224) {
                severity = 5;
            } else {
                severity = ((delta - 70) / 31) + 1;
            }
        } else if (delta == Math.abs(deltay)) {
            // Turn 60..225
            if (delta < 60) {
                severity = 1;
            } else if (delta > 224) {
                severity = 5;
            } else {
                severity = ((delta - 60) / 33) + 1;
            }
        } else if (delta == Math.abs(deltaz)) {
            // Vert 50..300
            if (delta < 50) {
                severity = 1;
            } else if (delta > 299) {
                severity = 5;
            } else {
                severity = ((delta - 50) / 50) + 1;
            }
        }
        return severity;
    }
    
    private Connection getConnection() throws NullPointerException, SQLException {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new ProDAOException(e);
        }
    }
    
}
