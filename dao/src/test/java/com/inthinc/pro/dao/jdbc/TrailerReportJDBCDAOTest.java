package com.inthinc.pro.dao.jdbc;

import com.inthinc.pro.model.TrailerReportItem;
import com.inthinc.pro.model.pagination.PageParams;
import com.inthinc.pro.model.pagination.TableFilterField;
import it.config.ITDataSource;
import it.config.IntegrationConfig;
import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

/**
 * Created by infrasoft04 on 3/4/14.
 */
public class TrailerReportJDBCDAOTest {
    private static final Logger logger = Logger.getLogger(TrailerReportJDBCDAOTest.class);
    static TrailerReportJDBCDAO trailerReportJDBCDAO;
    static int TEST_ACCOUNT_ID = 9999999;
    static int TEST_TRAILER_ID = 9999998;
    static int TEST_DEVICE_ID = 9999997;
    static int TEST_VEHICLE_ID = 9999996;
    static int TEST_GROUP_ID = 9999995;
    static int TEST_STATE_ID = 9999994;
    static int TEST_PERSON_ID = 9999993;
    static int TEST_ADDR_ID = 9999992;
    static int TEST_PARENT_ID = 9999991;
    static int TEST_MANAGER_ID = 9999990;
    static int TEST_DRIVER_ID = 8888888;
    static int TEST_VDDLOG_ID = 7777777;

    @BeforeClass
    public static void setupOnce() {
        IntegrationConfig config = new IntegrationConfig();
        trailerReportJDBCDAO = new TrailerReportJDBCDAO();
        trailerReportJDBCDAO.setDataSource(new ITDataSource().getRealDataSource());
        // ensure that at least one device is in the system before the test
        //create
        createTest(TEST_ACCOUNT_ID, TEST_TRAILER_ID, TEST_DEVICE_ID, TEST_VEHICLE_ID,
                TEST_GROUP_ID, TEST_STATE_ID, TEST_PERSON_ID, TEST_ADDR_ID, TEST_PARENT_ID, TEST_MANAGER_ID, TEST_DRIVER_ID, TEST_VDDLOG_ID);
    }

    @AfterClass
    public static void tearDownOnce() {
        try {
            //delete
      deleteTest(TEST_TRAILER_ID, TEST_VEHICLE_ID, TEST_GROUP_ID, TEST_PERSON_ID, TEST_DRIVER_ID, TEST_VDDLOG_ID);
        } catch (Throwable t) {/*ignore*/}
    }

    @Test
    public void testTrailerReportItemAndCount(){
        List<Integer> groupList = new ArrayList<Integer>();
        groupList.add(new Integer(TEST_GROUP_ID));
        PageParams pp = new PageParams();
        pp.setStartRow(0);
        pp.setEndRow(20);
        List<TrailerReportItem> TrailerReportItemList = trailerReportJDBCDAO.getTrailerReportItemByGroupPaging(TEST_ACCOUNT_ID, groupList,pp);
        assertEquals(TrailerReportItemList.size(), 1);

        List<TableFilterField> filter = new ArrayList<TableFilterField>();
        int TrailerReportItemCount = trailerReportJDBCDAO.getTrailerReportCount(TEST_ACCOUNT_ID, groupList,filter);
        assertNotNull(TrailerReportItemCount);

    }

    private static final String INSERT_TRAILER = "INSERT IGNORE INTO trailer(trailerID,acctId,status,odometer,absOdometer,weight,year,stateId,modified,deviceID)" +
            " VALUES(?,?,1,300,1,800,2014,44,'2014-03-04',?)";
    private static final String INSERT_VEHICLE = "INSERT IGNORE INTO vehicle (vehicleID,groupID, status, vtype, hos, dot, ifta, zonetype, odometer, weight," +
            "year, stateID, modified ) VALUES(?,?,1,0,0,1,0,0,125,800,2014,?,'2014-03-04' )";
    private static final String INSERT_DRIVER = "INSERT IGNORE INTO driver(driverID, groupID, personID, status, stateID,dot, modified)" +
            " VALUES(?, ?,?,1,?,0,'2014-03-04')";
    private static final String INSERT_PERSON = "INSERT IGNORE INTO person(personID, acctID,tzID,status, measureType, fuelEffType, addrID, gender, height, weight, info, warn, crit, modified )" +
            " VALUES(?,?,547,1,1,1,?,1,60,80,1,1,1,'2014-03-04' )";
    private static final String INSERT_GROUPS = "INSERT IGNORE INTO groups(groupID, acctId, parentID, status, addrID, addrID2,level, managerID, mapZoom, zoneRev, dotOfficeType,name,`desc`) " +
            "VALUES(?,?,?,1,?,0,1,?,2,2,0,'test_name','test_desc')";
    private static final String INSERT_VDDLOG = "INSERT IGNORE INTO vddlog(vddlogID,acctID,deviceID,baseID,emuFeatureMask,vehicleID,vgroupID,vtype,driverID,dgroupID,tzID,start,imei)" +
            " VALUES(?,?,?,111 ,6173 , ?,?,0, ?, ?,33,'2014-04-06','test_imei' )";

    public static void createTest(Integer acctId,Integer trailerId,Integer deviceID,Integer vehicleId,Integer groupId,Integer stateId,Integer personId,Integer addrId,
                                  Integer parentId,Integer managerId,Integer driverId,Integer vddlogId) {

        Connection conn = null;
        PreparedStatement statement_trailer = null;
        PreparedStatement statement_vehicle = null;
        PreparedStatement statement_driver = null;
        PreparedStatement statement_person = null;
        PreparedStatement statement_groups = null;
        PreparedStatement statement_vddlog = null;

        ResultSet resultSet = null;

        if (( deviceID != 0) && ( acctId != 0) && ( trailerId != 0) && ( vehicleId != 0) && ( groupId != 0) && ( stateId != 0) &&
                ( personId != 0) &&( addrId != 0) &&( parentId != 0) &&( managerId != 0) &&( driverId != 0) && ( vddlogId != 0))
        {
            try {
                conn = new ITDataSource().getRealDataSource().getConnection();;

                //insert into trailer
                statement_trailer = (PreparedStatement) conn.prepareStatement(INSERT_TRAILER);
                statement_trailer.setLong(1, trailerId);
                statement_trailer.setLong(2, acctId);
                statement_trailer.setLong(3, deviceID);
                statement_trailer.executeUpdate();

                //insert into vehicle
                statement_vehicle = (PreparedStatement) conn.prepareStatement(INSERT_VEHICLE);
                statement_vehicle.setLong(1, vehicleId);
                statement_vehicle.setLong(2, groupId);
                statement_vehicle.setLong(3, stateId);
                statement_vehicle.executeUpdate();

                //insert into driver
                statement_driver = (PreparedStatement) conn.prepareStatement(INSERT_DRIVER);
                statement_driver.setLong(1, driverId);
                statement_driver.setLong(2, groupId);
                statement_driver.setLong(3, personId);
                statement_driver.setLong(4, stateId);
                statement_driver.executeUpdate();

                //insert into person
                statement_person = (PreparedStatement) conn.prepareStatement(INSERT_PERSON);
                statement_person.setLong(1, personId);
                statement_person.setLong(2, acctId);
                statement_person.setLong(3, addrId);
                statement_person.executeUpdate();

                //insert into groups
                statement_groups = (PreparedStatement) conn.prepareStatement(INSERT_GROUPS);
                statement_groups.setLong(1, groupId);
                statement_groups.setLong(2, acctId);
                statement_groups.setLong(3, parentId);
                statement_groups.setLong(4, addrId);
                statement_groups.setLong(5, managerId);
                statement_groups.executeUpdate();

                //insert into vddlog
                statement_vddlog = (PreparedStatement) conn.prepareStatement(INSERT_VDDLOG);
                statement_vddlog.setLong(1, vddlogId);
                statement_vddlog.setLong(2, acctId);
                statement_vddlog.setLong(3, deviceID);
                statement_vddlog.setLong(4, vehicleId);
                statement_vddlog.setLong(5, groupId);
                statement_vddlog.setLong(6, driverId);
                statement_vddlog.setLong(7, groupId);
                statement_vddlog.executeUpdate();
            }
            catch (Throwable e)
            { // handle database alerts in the usual manner
                logger.error("Exception: " + e);
                e.printStackTrace();

            }   // end catch
            finally
            { // clean up and release the connection
                //              socket.close();
                try {
                    if (conn != null)
                        conn.close();
                    if (statement_groups != null)
                        statement_groups.close();
                    if (statement_person != null)
                        statement_person.close();
                    if (statement_driver != null)
                        statement_driver.close();
                    if (statement_vehicle != null)
                        statement_vehicle.close();
                    if (statement_trailer != null)
                        statement_trailer.close();
                    if (statement_vddlog != null)
                        statement_vddlog.close();
                    if (resultSet != null)
                        resultSet.close();
                } catch (Exception e){}
            }
        }
    }

    private static final String CLEAR_TRAILER= "Delete from trailer where trailerId= ?";
    private static final String CLEAR_VEHICLE= "delete  from vehicle where vehicleID =  ?";
    private static final String CLEAR_DRIVER= "delete from driver where driverID = ?";
    private static final String CLEAR_PERSON= "delete from person where personID= ?";
    private static final String CLEAR_GROUPS= "delete from groups where groupID = ?";
    private static final String CLEAR_VDDLOG= "delete from vddlog  where vddlogID = ?";

    private static void deleteTest(Integer testTrailerId,Integer testVehicleId,Integer testGroupId, Integer testPersonId,
                                   Integer testDriverId,  Integer testVddlogId)  throws SQLException
    {
        Connection conn = null;
        PreparedStatement statement_trailer = null;
        PreparedStatement statement_vehicle = null;
        PreparedStatement statement_driver = null;
        PreparedStatement statement_person = null;
        PreparedStatement statement_groups = null;
        PreparedStatement statement_vddlog = null;
        ResultSet resultSet = null;

        int delTrailer = (int) (testTrailerId%32);
        String trailerSqlStatement = String.format(CLEAR_TRAILER, delTrailer);
        int delVehicle = (int) (testVehicleId%32);
        String vehicleSqlStatement = String.format(CLEAR_VEHICLE, delVehicle);
        int delDriver = (int) (testDriverId%32);
        String driverSqlStatement = String.format(CLEAR_DRIVER, delDriver);
        int delPerson = (int) (testPersonId%32);
        String personSqlStatement = String.format(CLEAR_PERSON, delPerson);
        int delGroup = (int) (testGroupId%32);
        String groupSqlStatement = String.format(CLEAR_GROUPS, delGroup);
        int delVddlog = (int) (testVddlogId%32);
        String vddSqlStatement = String.format(CLEAR_VDDLOG, delVddlog);

        try
        {

            conn = new ITDataSource().getRealDataSource().getConnection();
            //trailer
            statement_trailer = conn.prepareStatement(trailerSqlStatement);
            statement_trailer.setInt(1, testTrailerId);
            logger.debug(statement_trailer.toString());
            statement_trailer.executeUpdate();

            //vehicle
            statement_vehicle = conn.prepareStatement(vehicleSqlStatement);
            statement_vehicle.setInt(1, testVehicleId);
            logger.debug(statement_vehicle.toString());
            statement_vehicle.executeUpdate();

            //driver
            statement_driver = conn.prepareStatement(driverSqlStatement);
            statement_driver.setInt(1, testDriverId);
            logger.debug(statement_driver.toString());
            statement_driver.executeUpdate();

            //person
            statement_person = conn.prepareStatement(personSqlStatement);
            statement_person.setInt(1, testPersonId);
            logger.debug(statement_person.toString());
            statement_person.executeUpdate();

            //group
            statement_groups = conn.prepareStatement(groupSqlStatement);
            statement_groups.setInt(1, testGroupId);
            logger.debug(statement_groups.toString());
            statement_groups.executeUpdate();

            //vdd
            statement_vddlog = conn.prepareStatement(vddSqlStatement);
            statement_vddlog.setInt(1, testVddlogId);
            logger.debug(statement_vddlog.toString());
            statement_vddlog.executeUpdate();
            
        }   // end try
        catch (SQLException e)
        { // handle database hosLogs in the usual manner

            throw e;
        }   // end catch
        finally
        { // clean up and release the connection
            if (conn != null)
                conn.close();
            if (statement_vddlog != null)
                statement_vddlog.close();
            if (statement_groups != null)
                statement_groups.close();
            if (statement_person != null)
                statement_person.close();
            if (statement_driver != null)
                statement_driver.close();
            if (statement_vehicle != null)
                statement_vehicle.close();
            if (statement_trailer != null)
                statement_trailer.close();
            if (resultSet != null)
                resultSet.close();
        } // end finally
    }
}
