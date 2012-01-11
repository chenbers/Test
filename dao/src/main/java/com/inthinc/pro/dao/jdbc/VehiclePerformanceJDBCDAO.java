package com.inthinc.pro.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.Interval;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.inthinc.pro.ProDAOException;
import com.inthinc.pro.dao.VehiclePerformanceDAO;
import com.inthinc.pro.model.aggregation.VehiclePerformance;
import com.inthinc.pro.scoring.ScoreCalculator;

public class VehiclePerformanceJDBCDAO  extends GenericJDBCDAO implements VehiclePerformanceDAO {

    
    
    
    private static final String FETCH_VEHICLE_DATA = "select " + 
    "sum(a.speedPenalty1 + a.speedPenalty2 + a.speedPenalty3 + a.speedPenalty4 + a.speedPenalty5) as speedPenalty, " + 
    "sum(a.aggressiveBrakePenalty + a.aggressiveAccelPenalty + a.aggressiveLeftPenalty + a.aggressiveRightPenalty + a.aggressiveBumpPenalty) as stylePenalty, " + 
    "sum(a.seatBeltPenalty) as seatBeltPenalty, " +
    "sum(a.odometer6) as totalMiles," + 
    "sum(a.seatbeltEvents) as seatbeltEventCnt,  sum(a.speedEvents1To7MphOver) as speed1Cnt, sum(a.speedEvents8To14MphOver) as speed2Cnt, sum(a.speedEvents15PlusMphOver) as speed3Cnt, " + 
    "sum(a.aggressiveBrakeEvents) as hardBrakeCnt, sum(a.aggressiveAccelEvents) as hardAccelCnt, sum(a.aggressiveLeftEvents+a.aggressiveRightEvents) as hardTurnCnt, sum(a.aggressiveBumpEvents) as hardBumpCnt, " +
    "v.name from agg a, vehicle v where v.vehicleID = a.vehicleID and a.driverID=? and a.aggDate between ? and ? group by a.vehicleID";
    
    private static final DateTimeFormatter dateFormatter = DateTimeFormat.forPattern("yyyy-MM-dd");

    @Override
    public List<VehiclePerformance> getVehiclePerformance(Integer driverID, Interval interval) {
        ScoreCalculator scoreCalculator = new ScoreCalculator();
        
        List<VehiclePerformance> vehiclePerformanceRecordList = new ArrayList<VehiclePerformance>();
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try
        {
            conn = getConnection();
            statement = (PreparedStatement) conn.prepareStatement(FETCH_VEHICLE_DATA);
            statement.setInt(1, driverID);
            statement.setDate(2, java.sql.Date.valueOf(dateFormatter.print(interval.getStart())));
            statement.setDate(3, java.sql.Date.valueOf(dateFormatter.print(interval.getEnd())));
            //System.out.println("statement:" + statement.toString());            
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                VehiclePerformance vehiclePerformanceRecord = new VehiclePerformance();
                Double speedPenalty = resultSet.getDouble(1);
                Double stylePenalty = resultSet.getDouble(2);
                Double seatbeltPenalty = resultSet.getDouble(3);
                vehiclePerformanceRecord.setTotalMiles(resultSet.getInt(4));
                vehiclePerformanceRecord.setSeatbeltCount(resultSet.getInt(5));
                vehiclePerformanceRecord.setSpeedCount0to7Over(resultSet.getInt(6));
                vehiclePerformanceRecord.setSpeedCount8to14Over(resultSet.getInt(7));
                vehiclePerformanceRecord.setSpeedCount15Over(resultSet.getInt(8));
                vehiclePerformanceRecord.setHardBrakeCount(resultSet.getInt(9));
                vehiclePerformanceRecord.setHardAccelCount(resultSet.getInt(10));
                vehiclePerformanceRecord.setHardTurnCount(resultSet.getInt(11));
                vehiclePerformanceRecord.setHardVerticalCount(resultSet.getInt(12));
                vehiclePerformanceRecord.setVehicleName(resultSet.getString(13));
                
                double totalMiles = vehiclePerformanceRecord.getTotalMiles();
                if (totalMiles > 0.0) {
                    double score = scoreCalculator.getOverallFromPenalty(totalMiles, seatbeltPenalty, stylePenalty, speedPenalty);
                    vehiclePerformanceRecord.setScore((int)score);
                    vehiclePerformanceRecordList.add(vehiclePerformanceRecord);
                }
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
        
        return vehiclePerformanceRecordList;
    }
}
