package com.inthinc.pro.dao.jdbc;

import com.inthinc.pro.model.IdlingReportItem;
import it.config.ITDataSource;
import it.config.IntegrationConfig;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static junit.framework.Assert.assertEquals;

/**
 * Created by vali_enache on 2/21/14.
 */
public class ReportIdlingJDBCDAOTest extends SimpleJdbcDaoSupport {
    private static final Logger logger = Logger.getLogger(ReportIdlingJDBCDAOTest.class);
    static ReportIdlingJDBCDAO reportIdlingJDBCDAO;
    static int TEST_GROUP_ID = 999999999;
    static DateTime SEP_12_2013 = new DateTime(1378944000000l);// 1375401600000l);
    static Interval interval = new Interval(SEP_12_2013, SEP_12_2013.plusDays(2));
    static int TEST_DRIVER_ID = 88888888;
    static String TEST_DRIVER_NAME = "7777777";
    static int TEST_AGG_ID = 666666666;
    static int TEST_VEHICLE_ID = 555555;
    static int TEST_DEVICE_ID = 444444;


    @BeforeClass
    public static void setupOnce() {
        IntegrationConfig config = new IntegrationConfig();
        reportIdlingJDBCDAO = new ReportIdlingJDBCDAO();
        reportIdlingJDBCDAO.setDataSource(new ITDataSource().getRealDataSource());
    }

    /*
    * Test getIdlingReportData
    * */
    @Test
    public void testGetIdlingReportData() {
        // createTest
        insert(TEST_GROUP_ID, TEST_DRIVER_ID, TEST_DRIVER_NAME, TEST_AGG_ID, TEST_VEHICLE_ID, TEST_DEVICE_ID);

        List<IdlingReportItem> reportList = reportIdlingJDBCDAO.getIdlingReportData(TEST_GROUP_ID, interval);
        assertEquals(reportList.size(), 1);

        // deleteTest
        try {
            deleteTest(TEST_GROUP_ID, TEST_AGG_ID);
        } catch (Exception e) {
        }

    }

    private static final String INSERT_GROUP = "INSERT IGNORE INTO groups (`desc`,groupId, acctID, name, parentID,status,groupPath) VALUES('test-desc',?,1,'test-name',4,4,'/999999999/')";
    private static final String INSERT_DRIVERINFO = "INSERT IGNORE INTO driverInfo (groupId, driverID, driverName) VALUES(?,?,?)";
    private static final String INSERT_AGG = "INSERT IGNORE INTO agg (aggID, driverID, vehicleID,deviceID,odometer1,odometer2,odometer3,odometer4,odometer5,odometer6,coachingEvents,  " +
            "mpgOdometer1,mpgOdometer2,mpgOdometer3,idleHi,idleLo,driveTime,trips,seatbeltCoaching,speedCoaching1,speedCoaching2,speedCoaching3,speedCoaching4,speedCoaching5, " +
            "seatbeltEvents,seatbeltClicks,speedEvents1,speedEvents2,speedEvents3,speedEvents4,speedEvents5,speedEvents1To7MphOver, speedEvents8To14MphOver, speedEvents15PlusMphOver, speedEventsOver80Mph," +
            "aggressiveBrakeEvents,aggressiveAccelEvents,aggressiveLeftEvents,aggressiveRightEvents, aggressiveBumpEvents,speedOdometer1, speedOdometer2, speedOdometer3,speedOdometer4, speedOdometer5," +
            "idleLoEvents, idleHiEvents,rpmEvents,crashEvents, emuFeatureMask, emuRpmDriveTime, driverStartingOdometer, driverEndingOdometer, driverCrashOdometer, driverCrashDays, driverCrashTotal," +
            " vehicleStartingOdometer, vehicleEndingOdometer, vehicleCrashOdometer,vehicleCrashTotal, backingEvents,backingTime,aggDate,modified)" +
            " VALUES( ?, ? ,?,?,0,0,0,0,1,1,0, 3,0,0,0,0,4,7,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,800,1,2,3,0,0,0,0,0,0,'2013-09-13','2014-09-13')";

    public static void insert(Integer groupId, Integer driverId, String driverName, Integer aggID, Integer vehicleID, Integer deviceID) {

        Connection conn = null;
        PreparedStatement statement_group = null;
        PreparedStatement statement_driverInfo = null;
        PreparedStatement statement_agg = null;

        ResultSet resultSet = null;

        if (groupId != 0) {
            try {
                conn = new ITDataSource().getRealDataSource().getConnection();
                ;

                //insert into groups
                statement_group = (PreparedStatement) conn.prepareStatement(INSERT_GROUP);
                statement_group.setLong(1, groupId);
                statement_group.executeUpdate();

                //insert into driverInfo
                statement_driverInfo = (PreparedStatement) conn.prepareStatement(INSERT_DRIVERINFO);
                statement_driverInfo.setLong(1, groupId);
                statement_driverInfo.setLong(2, driverId);
                statement_driverInfo.setString(3, driverName);
                statement_driverInfo.executeUpdate();

                //insert into agg aggID, driverID, vehicleID,deviceID,
                statement_agg = (PreparedStatement) conn.prepareStatement(INSERT_AGG);
                statement_agg.setLong(1, aggID);
                statement_agg.setLong(2, driverId);
                statement_agg.setLong(3, vehicleID);
                statement_agg.setLong(4, deviceID);
                statement_agg.executeUpdate();

            } catch (Throwable e) { // handle database alerts in the usual manner
                logger.error("Exception: " + e);
                e.printStackTrace();

            }   // end catch
            finally { // clean up and release the connection
                //              socket.close();
                try {
                    if (conn != null)
                        conn.close();
                    if (statement_group != null)
                        statement_group.close();
                    if (statement_driverInfo != null)
                        statement_driverInfo.close();
                    if (statement_driverInfo != null)
                        statement_driverInfo.close();
                    if (resultSet != null)
                        resultSet.close();
                } catch (Exception e) {
                }
            }
        }
    }

    private static final String CLEAR_GROUP = "delete from groups where groupId =  ?";
    private static final String CLEAR_DRIVERINFO = "delete from driverInfo where groupId =  ?";
    private static final String CLEAR_AGG = "delete from agg where aggId =  ?";

    private static void deleteTest(Integer groupId, Integer aggId) throws SQLException {
        Connection conn = null;
        PreparedStatement statement_group = null;
        PreparedStatement statement_driver_info = null;
        PreparedStatement statement_agg = null;

        ResultSet resultSet = null;

        int delGroupId = (int) (groupId % 32);
        String groupSqlStatement = String.format(CLEAR_GROUP, delGroupId);
        int delAggId = (int) (aggId % 32);
        String aggSqlStatement = String.format(CLEAR_AGG, delAggId);
        String driverInfoSqlStatement = String.format(CLEAR_DRIVERINFO, delGroupId);

        try {
            conn = new ITDataSource().getRealDataSource().getConnection();

            // delete from groups
            statement_group = conn.prepareStatement(groupSqlStatement);
            statement_group.setInt(1, groupId);
            logger.debug(statement_group.toString());
            statement_group.executeUpdate();

            // delete from driverInfo
            statement_driver_info = conn.prepareStatement(driverInfoSqlStatement);
            statement_driver_info.setInt(1, groupId);
            logger.debug(statement_driver_info.toString());
            statement_driver_info.executeUpdate();

            // delete from agg
            statement_agg = conn.prepareStatement(aggSqlStatement);
            statement_agg.setInt(1, aggId);
            logger.debug(statement_agg.toString());
            statement_agg.executeUpdate();

        }   // end try
        catch (SQLException e) { // handle database hosLogs in the usual manner

            throw e;
        }   // end catch
        finally { // clean up and release the connection
            if (conn != null)
                conn.close();
            if (statement_group != null)
                statement_group.close();
            if (statement_driver_info != null)
                statement_driver_info.close();
            if (statement_agg != null)
                statement_agg.close();
            if (resultSet != null)
                resultSet.close();
        }
    }
}
