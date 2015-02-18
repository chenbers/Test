package com.inthinc.pro.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Interval;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.inthinc.pro.ProDAOException;
import com.inthinc.pro.dao.DriveTimeDAO;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.model.aggregation.DriveTimeRecord;
import com.inthinc.pro.model.event.VehicleEventData;

public class DriveTimeJDBCDAO extends GenericJDBCDAO implements DriveTimeDAO {

    private static final long serialVersionUID = 1L;
    
    private static final String FETCH_DRIVE_TIME = "select a.driverID, a.driveTime, a.aggDate, a.vehicleID, v.name from agg a, vehicle v where v.vehicleID = a.vehicleID and a.driverID=? and a.aggDate between ? and ? order by a.aggDate";
    
    private static final String FETCH_GROUP_DRIVE_TIME1 = "select a.driverID, a.driveTime, a.aggDate, a.vehicleID, v.name from agg a, vehicle v " +
                                                         "where v.vehicleID = a.vehicleID and a.driverID in " +
                                                         "(";
    private static final String FETCH_GROUP_DRIVE_TIME2 = ") and a.aggDate between ? and ? order by a.aggDate, a.driverID";
    
    private static final String FETCH_DRIVER_IDS = "select distinct driverID from groupDriverFlat g where g.groupID in (select groupID from groupGroupFlat where parentID=?)";

    private static final String REPORT_HOURS = "select sum(a.driveTime) drvt from agg a where a.vehicleID = ? order by a.aggDate desc";

    private static final String REPORT_ODOMETER6 = "select sum(a.odometer6) drvo from agg a where a.vehicleID = ?";

    //private static final String EVENT_PREV_DATE = "SELECT max(time) last_date FROM noteXX a WHERE a.vehicleID = ? and TYPE = ? AND attrs LIKE '%NCODE%' AND time < ?";
    private static final String EVENT_PREV_DATE = "select DATE(time) last_date, vehicleID from cachedNoteView  where vehicleID in (?) and TYPE in (?) and attribs like '%NCODE%' and time < ? group by vehicleID, DATE(time)";
    private static final String EVENT_PREV_DATE_MANY = "select DATE(time) last_date, vehicleID from cachedNoteView  where  time between ? and ? and vehicleID in (VEHICLEID_LIST) and TYPE in (NOTETYPE_LIST) and (ATTRIBS_SQL)   group by vehicleID, DATE(time)";

    private static final String REPORT_HOURS_AT_DATE = "select sum(a.driveTime) tdiff from agg a where a.vehicleID = ? and a.aggDate <= ?";

    private static final String REPORT_ODOMETER_AT_DATE = "select sum(a.odometer6) odiff from agg a where a.vehicleID = ? and a.aggDate <= ?";
    private static final String REPORT_ODOMETER_AT_DATE_MULT_VEHILCE = "select sum(a.odometer6) odiff from agg a where a.vehicleID in ( VEHICLEID_LIST ) and a.aggDate <= ?";

    private static final DateTimeFormatter dateFormatter = DateTimeFormat.forPattern("yyyy-MM-dd");

    public Long getEngineHoursAtDate(Vehicle vehicle, Date evDate) {
        java.sql.Date sqlEvDate = new java.sql.Date(evDate.getTime());

        Long hoursSince = 0L;
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try
        {
            conn = getConnection();
            statement = (PreparedStatement) conn.prepareStatement(REPORT_HOURS_AT_DATE);
            statement.setInt(1, vehicle.getVehicleID());
            statement.setDate(2, sqlEvDate);

            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                hoursSince = resultSet.getLong("tdiff");
            }
        }   // end try
        catch (SQLException e)
        { // handle database hosLogs in the usual manner
            throw new ProDAOException(statement.toString(), e);
        }   // end catch
        finally
        { // clean up and release the connection
            close(resultSet);
            close(statement);
            close(conn);
        } // end finally

        return hoursSince;
    }

    public Long getDriveOdometerAtDate(Vehicle vehicle, Date evDate) {
        java.sql.Date sqlEvDate = new java.sql.Date(evDate.getTime());

        Long driveSince = 0L;
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try
        {
            conn = getConnection();
            statement = (PreparedStatement) conn.prepareStatement(REPORT_ODOMETER_AT_DATE);
            statement.setInt(1, vehicle.getVehicleID());
            statement.setDate(2, sqlEvDate);

            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                driveSince = resultSet.getLong("odiff");
            }
        }   // end try
        catch (SQLException e)
        { // handle database hosLogs in the usual manner
            throw new ProDAOException(statement.toString(), e);
        }   // end catch
        finally
        { // clean up and release the connection
            close(resultSet);
            close(statement);
            close(conn);
        } // end finally

        return driveSince;
    }

    @Override
    public  Map<Integer, String> getDriveOdometersAtDates(VehicleEventData vehicleEventData) {
        Map<Integer, String> retMap = new HashMap<Integer, String>();
        List<Object> params = new ArrayList<Object>();
        StringBuilder sqlBuilder = new StringBuilder();
        for (Integer vehicleID: vehicleEventData.getDates().keySet()){
            java.sql.Date sqlDate = new java.sql.Date(vehicleEventData.getDates().get(vehicleID).getTime());
            Integer deviceID = vehicleEventData.getDeviceIDs().get(vehicleID);
            Integer evCode = vehicleEventData.getEventCodes().get(vehicleID);
            Integer noteCode = vehicleEventData.getNoteCodes().get(vehicleID);
            Vehicle vehicle = vehicleEventData.getVehicles().get(vehicleID);


            sqlBuilder.append("select sum(a.odometer6) odiff, vehicleID from agg a where a.vehicleID = ? and a.aggDate <= ? group by vehicleID").append("\n");
            sqlBuilder.append(" UNION ");

            params.add(vehicleID);
            params.add(sqlDate);
        }

        sqlBuilder.delete(sqlBuilder.lastIndexOf("UNION"),sqlBuilder.length());

        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            conn = getConnection();
            statement = (PreparedStatement) conn.prepareStatement(sqlBuilder.toString());
            //System.out.println("getDriveOdometersAtDates SQL: "+sqlBuilder.toString()); //TODO: jwimmer remove

            int i = 1;
            for (Object param: params){
                if (param instanceof Integer){
                    statement.setInt(i, (Integer)param);
                }else if (param instanceof java.sql.Date){
                    statement.setDate(i, (java.sql.Date)param);
                }
                i++;
            }

            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                retMap.put(resultSet.getInt("vehicleID"), resultSet.getLong("odiff") / 100 + "");
            }
        } catch (SQLException e) {
            throw new ProDAOException(statement.toString(), e);
        }
        finally {
            close(resultSet);
            close(statement);
            close(conn);
        }

        return retMap;
    }

    @Override
    public  Map<Integer, String> getDriveOdometersAtLastDates(VehicleEventData vehicleEventData) {
        Map<Integer, String> retMap = new HashMap<Integer, String>();
        List<Object> params = new ArrayList<Object>();
        StringBuilder sqlBuilder = new StringBuilder();
        for (Integer vehicleID: vehicleEventData.getDates().keySet()){
            java.sql.Date sqlDate = vehicleEventData.getPrevEventDates().get(vehicleID)!=null?new java.sql.Date(vehicleEventData.getPrevEventDates().get(vehicleID).getTime()):new java.sql.Date(vehicleEventData.getDates().get(vehicleID).getTime());
            Integer deviceID = vehicleEventData.getDeviceIDs().get(vehicleID);
            Integer evCode = vehicleEventData.getEventCodes().get(vehicleID);
            Integer noteCode = vehicleEventData.getNoteCodes().get(vehicleID);
            Vehicle vehicle = vehicleEventData.getVehicles().get(vehicleID);


            sqlBuilder.append("select sum(a.odometer6) odiff, vehicleID from agg a where a.vehicleID = ? and a.aggDate <= ? group by vehicleID").append("\n");
            sqlBuilder.append(" UNION ");

            params.add(vehicleID);
            params.add(sqlDate);
        }

        sqlBuilder.delete(sqlBuilder.lastIndexOf("UNION"),sqlBuilder.length());

        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            conn = getConnection();
            statement = (PreparedStatement) conn.prepareStatement(sqlBuilder.toString());
            //System.out.println("getDriveOdometersAtLastDates SQL: "+sqlBuilder.toString()); //TODO: jwimmer remove
            int i = 1;
            for (Object param: params){
                if (param instanceof Integer){
                    statement.setInt(i, (Integer)param);
                }else if (param instanceof java.sql.Date){
                    statement.setDate(i, (java.sql.Date)param);
                }
                i++;
            }

            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                retMap.put(resultSet.getInt("vehicleID"), resultSet.getLong("odiff") / 100 + "");
            }
        } catch (SQLException e) {
            throw new ProDAOException(statement.toString(), e);
        }
        finally {
            close(resultSet);
            close(statement);
            close(conn);
        }

        return retMap;
    }


    @Override
    public  Map<Integer, String> getEngineHoursAtDates(VehicleEventData vehicleEventData) {
        Map<Integer, String> retMap = new HashMap<Integer, String>();
        List<Object> params = new ArrayList<Object>();
        StringBuilder sqlBuilder = new StringBuilder();
        for (Integer vehicleID: vehicleEventData.getDates().keySet()){
            java.sql.Date sqlDate = new java.sql.Date(vehicleEventData.getDates().get(vehicleID).getTime());
            Integer deviceID = vehicleEventData.getDeviceIDs().get(vehicleID);
            Integer evCode = vehicleEventData.getEventCodes().get(vehicleID);
            Integer noteCode = vehicleEventData.getNoteCodes().get(vehicleID);
            Vehicle vehicle = vehicleEventData.getVehicles().get(vehicleID);


            sqlBuilder.append("select sum(a.driveTime) tdiff, vehicleID from agg a where a.vehicleID = ? and a.aggDate <= ? group by vehicleID").append("\n");
            sqlBuilder.append(" UNION ");

            params.add(vehicleID);
            params.add(sqlDate);
        }

        sqlBuilder.delete(sqlBuilder.lastIndexOf("UNION"),sqlBuilder.length());

        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            conn = getConnection();
            statement = (PreparedStatement) conn.prepareStatement(sqlBuilder.toString());
            //System.out.println("getEngineHoursAtDates SQL: "+sqlBuilder.toString()); //TODO: jwimmer remove
            int i = 1;
            for (Object param: params){
                if (param instanceof Integer){
                    statement.setInt(i, (Integer)param);
                }else if (param instanceof java.sql.Date){
                    statement.setDate(i, (java.sql.Date)param);
                }
                i++;
            }

            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                retMap.put(resultSet.getInt("vehicleID"), resultSet.getLong("tdiff") / 3600 + "");
            }
        } catch (SQLException e) {
            throw new ProDAOException(statement.toString(), e);
        }
        finally {
            close(resultSet);
            close(statement);
            close(conn);
        }

        return retMap;
    }

    @Override
    public  Map<Integer, String> getEngineHoursAtLastDates(VehicleEventData vehicleEventData) {
        Map<Integer, String> retMap = new HashMap<Integer, String>();
        List<Object> params = new ArrayList<Object>();
        StringBuilder sqlBuilder = new StringBuilder();
        for (Integer vehicleID: vehicleEventData.getDates().keySet()){
            java.sql.Date sqlDate = vehicleEventData.getPrevEventDates().get(vehicleID)!=null?new java.sql.Date(vehicleEventData.getPrevEventDates().get(vehicleID).getTime()):new java.sql.Date(vehicleEventData.getDates().get(vehicleID).getTime());
            Integer deviceID = vehicleEventData.getDeviceIDs().get(vehicleID);
            Integer evCode = vehicleEventData.getEventCodes().get(vehicleID);
            Integer noteCode = vehicleEventData.getNoteCodes().get(vehicleID);
            Vehicle vehicle = vehicleEventData.getVehicles().get(vehicleID);


            sqlBuilder.append("select sum(a.driveTime) tdiff, vehicleID from agg a where a.vehicleID = ? and a.aggDate <= ? group by vehicleID").append("\n");
            sqlBuilder.append(" UNION ");

            params.add(vehicleID);
            params.add(sqlDate);
        }

        sqlBuilder.delete(sqlBuilder.lastIndexOf("UNION"),sqlBuilder.length());

        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            conn = getConnection();
            statement = (PreparedStatement) conn.prepareStatement(sqlBuilder.toString());
            //System.out.println("getEngineHoursAtLastDates SQL: "+sqlBuilder.toString()); //TODO: jwimmer remove
            int i = 1;
            for (Object param: params){
                if (param instanceof Integer){
                    statement.setInt(i, (Integer)param);
                }else if (param instanceof java.sql.Date){
                    statement.setDate(i, (java.sql.Date)param);
                }
                i++;
            }
            //System.out.println(statement.getParameterMetaData());
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                retMap.put(resultSet.getInt("vehicleID"), resultSet.getLong("tdiff") / 3600 + "");
            }
        } catch (SQLException e) {
            throw new ProDAOException(statement.toString(), e);
        }
        finally {
            close(resultSet);
            close(statement);
            close(conn);
        }

        return retMap;
    }

    public Date getPrevEventDate(Vehicle vehicle, Integer nType, Integer eventCode, Date evDate, Integer deviceId) {
        java.sql.Date sqlEvDate = new java.sql.Date(evDate.getTime());

        Date date = new Date(0);
        java.sql.Date prevEventDate = new java.sql.Date(date.getTime());
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try
        {
            conn = getConnection();
            statement = (PreparedStatement) conn.prepareStatement(EVENT_PREV_DATE.replace("NCODE",eventCode.toString()));
            statement.setInt(1, vehicle.getVehicleID());
            statement.setInt(2, nType);
            statement.setDate(3, sqlEvDate); 

            resultSet = statement.executeQuery();
            System.out.println("getPrevEventDate SQL: "+statement.toString()); //TODO: jwimmer remove

            while (resultSet.next()) {
                prevEventDate = resultSet.getDate("last_date");
            }

            if (prevEventDate==null) prevEventDate= new java.sql.Date(date.getTime());
        }   // end try
        catch (SQLException e)
        { // handle database hosLogs in the usual manner
            throw new ProDAOException(statement.toString(), e);
        }   // end catch
        finally
        { // clean up and release the connection
            close(resultSet);
            close(statement);
            close(conn);
        } // end finally

        return prevEventDate;
    }

    /* (non-Javadoc)
     * @see com.inthinc.pro.dao.DriveTimeDAO#getPrevEventDates(com.inthinc.pro.model.event.VehicleEventData)
     */
    @Override
    public Map<Integer, Date> getPrevEventDates(VehicleEventData vehicleEventData) {
        Map<Integer, Date> retMap = new HashMap<Integer, Date>();
        List<Object> params = new ArrayList<Object>();
        StringBuilder sqlBuilder = new StringBuilder();
        StringBuilder vehicleInList = new StringBuilder();
        StringBuilder noteTypeInList = new StringBuilder();
        StringBuilder attribsSql = new StringBuilder();
        Set<Integer> noteTypeIDs = new HashSet<Integer>();
        Set<Integer> evCodes = new HashSet<Integer>();
        long minDate = System.currentTimeMillis();
        long maxDate = 0;
        for (Integer vehicleID: vehicleEventData.getDates().keySet()){
            //java.sql.Date sqlDate = new java.sql.Date(vehicleEventData.getDates().get(vehicleID).getTime());/// find max and min TIME
            if(vehicleEventData.getDates().get(vehicleID).getTime() < minDate) {
                minDate = vehicleEventData.getDates().get(vehicleID).getTime();
            } 
            if(vehicleEventData.getDates().get(vehicleID).getTime() > maxDate) {
                maxDate = vehicleEventData.getDates().get(vehicleID).getTime();
            } 
            vehicleInList.append(", "+vehicleID+" ");

            noteTypeIDs.add(vehicleEventData.getNoteCodes().get(vehicleID));
            evCodes.add(vehicleEventData.getEventCodes().get(vehicleID));
        }
        vehicleInList.delete(0, 1); //remove initial comma
        
        for(Integer noteTypeCode : noteTypeIDs) {
            noteTypeInList.append(", "+noteTypeCode+" ");
        }
        noteTypeInList.delete(0, 1); //remove initial comma
        
        for(Integer evCode: evCodes) {
            attribsSql.append("or attribs like '%"+evCode+"%' ");
        }
        attribsSql.delete(0, 2); //remove initial "or"
        
        //EVENT_PREV_DATE_MANY = "select DATE(time) last_date, vehicleID from cachedNoteView  where  
        //time between ? and ? 
        //and vehicleID in (VEHICLEID_LIST) // 
        //and TYPE in (NOTETYPE_LIST) //
        //and (ATTRIBS_SQL) and  group by vehicleID, DATE(time)";
        sqlBuilder.append(EVENT_PREV_DATE_MANY);
        sqlBuilder.replace(sqlBuilder.indexOf("VEHICLEID_LIST"), (sqlBuilder.indexOf("VEHICLEID_LIST") +"VEHICLEID_LIST".length()), vehicleInList.toString());
        sqlBuilder.replace(sqlBuilder.indexOf("NOTETYPE_LIST"), (sqlBuilder.indexOf("NOTETYPE_LIST") +"NOTETYPE_LIST".length()), noteTypeInList.toString());
        sqlBuilder.replace(sqlBuilder.indexOf("ATTRIBS_SQL"), (sqlBuilder.indexOf("ATTRIBS_SQL")+"ATTRIBS_SQL".length()), attribsSql.toString());

        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            conn = getConnection();
            statement = (PreparedStatement) conn.prepareStatement(sqlBuilder.toString());
            System.out.println("getPrevEventDates SQL: "+statement.toString()); //TODO: jwimmer remove

            statement.setDate(1, new java.sql.Date(minDate));
            statement.setDate(2, new java.sql.Date(maxDate));

            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                System.out.println(resultSet.getInt("vehicleID")+" , "+resultSet.getDate("last_date"));
                retMap.put(resultSet.getInt("vehicleID"), resultSet.getDate("last_date"));
            }
        } catch (SQLException e) {
            throw new ProDAOException(statement.toString(), e);
        }
        finally {
            close(resultSet);
            close(statement);
            close(conn);
        }

        return retMap;
    }

    public Long getDriveTimeAtDate(Vehicle vehicle, Integer nType, Integer eventCode, Date evDate) {
        //take previous last date for Event of same type
        //if not exists will be used a very old date 1970
        java.sql.Date sqlEvDate = new java.sql.Date(evDate.getTime());

        Long hourSince = 0L;
        Date date = new Date(0);
        java.sql.Date prevEventDate = new java.sql.Date(date.getTime());
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try
        {
            conn = getConnection();
            statement = (PreparedStatement) conn.prepareStatement(EVENT_PREV_DATE.replace("NCODE",eventCode.toString()));
            statement.setInt(1, vehicle.getVehicleID());
            statement.setInt(2, nType);
            statement.setDate(3, sqlEvDate);

            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                prevEventDate = resultSet.getDate("last_date");
            }
            statement = (PreparedStatement) conn.prepareStatement(REPORT_HOURS_AT_DATE);
            statement.setInt(1, vehicle.getVehicleID());
            statement.setDate(2, prevEventDate);

            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                hourSince = resultSet.getLong("tdiff");
            }
        }   // end try
        catch (SQLException e)
        { // handle database hosLogs in the usual manner
            throw new ProDAOException(statement.toString(), e);
        }   // end catch
        finally
        { // clean up and release the connection
            close(resultSet);
            close(statement);
            close(conn);
        } // end finally

        return hourSince;
    }

    public Long getDriveTimeSum(Vehicle vehicle) {

        Long driveTime = 0L;
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try
        {
            conn = getConnection();
            statement = (PreparedStatement) conn.prepareStatement(REPORT_HOURS);
            statement.setInt(1, vehicle.getVehicleID());
            resultSet = statement.executeQuery();

            while (resultSet.next()) {

                driveTime = resultSet.getLong("drvt");
            }
        }   // end try
        catch (SQLException e)
        { // handle database hosLogs in the usual manner
            throw new ProDAOException(statement.toString(), e);
        }   // end catch
        finally
        { // clean up and release the connection
            close(resultSet);
            close(statement);
            close(conn);
        } // end finally

        return driveTime;
    }

    public Long getDriveOdometerSum(Vehicle vehicle) {

        Long driveOdometer = 0L;
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try
        {
            conn = getConnection();
            statement = (PreparedStatement) conn.prepareStatement(REPORT_ODOMETER6);
            statement.setInt(1, vehicle.getVehicleID());
            resultSet = statement.executeQuery();

            while (resultSet.next()) {

                driveOdometer = resultSet.getLong("drvo");
            }
        }   // end try
        catch (SQLException e)
        { // handle database hosLogs in the usual manner
            throw new ProDAOException(statement.toString(), e);
        }   // end catch
        finally
        { // clean up and release the connection
            close(resultSet);
            close(statement);
            close(conn);
        } // end finally

        return driveOdometer;
    }

    @Override
    public List<DriveTimeRecord> getDriveTimeRecordList(Driver driver, Interval queryInterval) {
        
        DateTimeZone driverTimeZone = DateTimeZone.forTimeZone(driver.getPerson().getTimeZone());

        List<DriveTimeRecord> driverTimeRecordList = new ArrayList<DriveTimeRecord>();
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try
        {
            conn = getConnection();
            statement = (PreparedStatement) conn.prepareStatement(FETCH_DRIVE_TIME);
            statement.setInt(1, driver.getDriverID());
            statement.setDate(2, java.sql.Date.valueOf(dateFormatter.print(queryInterval.getStart())));
            statement.setDate(3, java.sql.Date.valueOf(dateFormatter.print(queryInterval.getEnd())));
System.out.println("statement:" + statement.toString());            
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                DriveTimeRecord driverTimeRecord = new DriveTimeRecord();
                driverTimeRecord.setDriverID(resultSet.getInt(1));
                driverTimeRecord.setDriveTimeSeconds(resultSet.getLong(2));
                driverTimeRecord.setDateTime(new DateMidnight(resultSet.getDate(3).getTime(), driverTimeZone).toDateTime());
                driverTimeRecord.setVehicleID(resultSet.getInt(4));
                driverTimeRecord.setVehicleName(resultSet.getString(5));
                driverTimeRecordList.add(driverTimeRecord);
            }
                

        }   // end try
        catch (SQLException e)
        { // handle database hosLogs in the usual manner
            throw new ProDAOException(statement.toString(), e);
        }   // end catch
        finally
        { // clean up and release the connection
            close(resultSet);
            close(statement);
            close(conn);
        } // end finally   
        
        return driverTimeRecordList;
    }

    
    @Override
    public List<DriveTimeRecord> getDriveTimeRecordListForGroup(Integer groupID, Interval queryInterval) {
        

        List<DriveTimeRecord> driverTimeRecordList = new ArrayList<DriveTimeRecord>();
        String driverIDsList = getDriverIDsForGroup(groupID);
        if (driverIDsList.isEmpty())
            return driverTimeRecordList;
        
        // for some reason, doing it this way executed much faster than embedding the select query on the driverIDs
        String queryString = FETCH_GROUP_DRIVE_TIME1 + driverIDsList + FETCH_GROUP_DRIVE_TIME2; 
        
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try
        {
            conn = getConnection();
            statement = (PreparedStatement) conn.prepareStatement(queryString);
            statement.setDate(1, java.sql.Date.valueOf(dateFormatter.print(queryInterval.getStart())));
            statement.setDate(2, java.sql.Date.valueOf(dateFormatter.print(queryInterval.getEnd())));
//System.out.println("statement:" + statement.toString());            
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                DriveTimeRecord driverTimeRecord = new DriveTimeRecord();
                driverTimeRecord.setDriverID(resultSet.getInt(1));
                driverTimeRecord.setDriveTimeSeconds(resultSet.getLong(2));
//                driverTimeRecord.setDateTime(new DateTime(resultSet.getDate(3).getTime(), DateTimeZone.UTC));
                driverTimeRecord.setDateTime(new DateTime(resultSet.getDate(3).getTime()));
                driverTimeRecord.setVehicleID(resultSet.getInt(4));
                driverTimeRecord.setVehicleName(resultSet.getString(5));
                driverTimeRecordList.add(driverTimeRecord);
            }
                

        }   // end try
        catch (SQLException e)
        { // handle database hosLogs in the usual manner
            throw new ProDAOException(statement.toString(), e);
        }   // end catch
        finally
        { // clean up and release the connection
            close(resultSet);
            close(statement);
            close(conn);
        } // end finally   
        
        return driverTimeRecordList;
    }

    private String getDriverIDsForGroup(Integer groupID) {
        

        StringBuffer driverIDs = new StringBuffer();
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try
        {
            conn = getConnection();
            statement = (PreparedStatement) conn.prepareStatement(FETCH_DRIVER_IDS);
            statement.setInt(1, groupID);
//System.out.println("statement:" + statement.toString());            
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                if (driverIDs.length() != 0)
                    driverIDs.append(",");
                driverIDs.append(resultSet.getInt(1));
            }
                

        }   // end try
        catch (SQLException e)
        { // handle database hosLogs in the usual manner
            throw new ProDAOException(statement.toString(), e);
        }   // end catch
        finally
        { // clean up and release the connection
            close(resultSet);
            close(statement);
            close(conn);
        } // end finally   
        
        return driverIDs.toString();
    }

}
