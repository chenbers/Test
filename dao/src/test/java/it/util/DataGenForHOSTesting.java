package it.util;

import it.com.inthinc.pro.dao.model.GroupData;
import it.com.inthinc.pro.dao.model.ITData;
import it.config.ITDataSource;
import it.config.IntegrationConfig;
import it.config.ReportTestConst;

import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import javax.sql.DataSource;

import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import com.inthinc.hos.model.HOSStatus;
import com.inthinc.pro.dao.hessian.proserver.SiloServiceCreator;
import com.inthinc.pro.dao.util.DateUtil;
import com.inthinc.pro.model.hos.HOSRecord;

public class DataGenForHOSTesting extends DataGenForTesting {
    public String xmlPath;
    
    public static Integer LOGIN_ODOMETER = 1000;
    public static Integer LOGOUT_ODOMETER = 1100;
    

    @Override
    protected void createTestData() {
        itData = new ITData();
        Date assignmentDate = DateUtil.convertTimeInSecondsToDate(DateUtil.getDaysBackDate(DateUtil.getTodaysDate(), 2, ReportTestConst.TIMEZONE_STR));
        ((ITData)itData).createTestData(siloService, xml, assignmentDate, false, false);
    }

    @Override
    protected boolean parseTestData() {
        return true;
    }
    
    protected void parseArguments(String[] args) {
        // Arguments:
        //      required
        //          0:      full path to xml file for storage/retrieval of current data set
        
        String usageErrorMsg = "Usage: DataGenForHOSTesting <xml file path>";
        
        if (args.length < 1)
        {
            System.out.println(usageErrorMsg);
            System.exit(1);
        }
        xmlPath = args[0];
    }
    
    private void loginDriver(DataSource dataSource, Integer driverID, Integer vehicleID, Date loginDate) throws SQLException {
        
        Connection conn = null;
        CallableStatement statement = null;
        ResultSet resultSet = null;
        
        try
        {
            conn = dataSource.getConnection();
            statement = conn.prepareCall("{call hos_loginDriver(?, ?, ?, ?, ?, ?)}");
            statement.setInt(1, driverID);
            statement.setInt(2, vehicleID);
            statement.setInt(3, 0); // deviceID
            statement.setTimestamp(4, new Timestamp(loginDate.getTime()), Calendar.getInstance(TimeZone.getTimeZone("UTC")));
            statement.setBoolean(5, false);
            statement.setInt(6, LOGIN_ODOMETER);

            resultSet = statement.executeQuery();
        }   // end try
        catch (SQLException ex) {
            ex.printStackTrace();
            throw ex;
        }
        finally
        { // clean up and release the connection
            resultSet.close();
            statement.close();
            conn.close();
        } // end finally
    }

    private void logoutDriver(DataSource dataSource, Integer driverID, Integer vehicleID, Date logoutDate) throws SQLException {
        
        Connection conn = null;
        CallableStatement statement = null;
        ResultSet resultSet = null;
        
        try
        {
            conn = dataSource.getConnection();
            statement = conn.prepareCall("{call hos_logoutDriver(?, ?, ?, ?, ?)}");
            statement.setInt(1, driverID);
            statement.setInt(2, vehicleID);
            statement.setTimestamp(3, new Timestamp(logoutDate.getTime()), Calendar.getInstance(TimeZone.getTimeZone("UTC")));
            statement.setBoolean(4, false);
            statement.setInt(5, LOGOUT_ODOMETER);

            resultSet = statement.executeQuery();
        }   // end try
        catch (SQLException ex) {
            ex.printStackTrace();
            throw ex;
        }
        finally
        { // clean up and release the connection
            resultSet.close();
            statement.close();
            conn.close();
        } // end finally
    }

    private void addHOSLog(DataSource dataSource, HOSRecord hosRecord) throws SQLException {
        
        Connection conn = null;
        CallableStatement statement = null;
        ResultSet resultSet = null;
        
        try
        {
            conn = dataSource.getConnection();
            statement = conn.prepareCall("{call hos_createFromNote(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)}");
            statement.setInt(1, hosRecord.getDriverID());
            statement.setInt(2, hosRecord.getVehicleID());
            statement.setLong(3, 0l);
            statement.setInt(4, hosRecord.getStatus().getCode());
            statement.setTimestamp(5, new Timestamp(hosRecord.getLogTime().getTime()), Calendar.getInstance(TimeZone.getTimeZone("UTC")));
            statement.setInt(6, hosRecord.getVehicleID().intValue());
            statement.setFloat(7, hosRecord.getLat());
            statement.setFloat(8, hosRecord.getLng());
            statement.setString(9, hosRecord.getLocation());
            statement.setBoolean(10, false);
            statement.setInt(11, 0); //flags
            statement.setString(12, hosRecord.getTrailerID()); 
            statement.setString(13, hosRecord.getServiceID()); 
            statement.setFloat(14, hosRecord.getTruckGallons());
            statement.setFloat(15, hosRecord.getTrailerGallons());
            statement.setString(16, hosRecord.getEmployeeID());
System.out.println(statement.toString());
            resultSet = statement.executeQuery();
        }   // end try
        catch (SQLException ex) {
            ex.printStackTrace();
            throw ex;
        }
        finally
        { // clean up and release the connection
            try {
                resultSet.close();
                statement.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } // end finally
        
    }


    private void genHOSTestData(Date currentDate) throws SQLException {
        
        GroupData testGroupData = ((ITData)itData).teamGroupData.get(ITData.INTERMEDIATE);
        Integer testDeviceID = testGroupData.device.getDeviceID();
        Integer testDriverID = testGroupData.driver.getDriverID();
        Integer testVehicleID = testGroupData.vehicle.getVehicleID();
        String employeeID = testGroupData.driver.getPerson().getEmpid();
System.out.println("testDeviceID = " + testDeviceID + " vehicleID = " + testVehicleID + " emp id " + employeeID);        
        
        DateTime dayStartUTC = new DateMidnight(currentDate, DateTimeZone.UTC).toDateTime();
        
        DataSource dataSource = new ITDataSource().getRealDataSource();
        
        // driver (from INTERMEDIATE group)
        loginDriver(dataSource, testDriverID, testVehicleID, dayStartUTC.toDate());
        
        for (int i = 0; i < 4; i++) {
            HOSRecord rec = constructHosRecord(testDeviceID, testVehicleID, HOSStatus.values()[i], dayStartUTC.plusMinutes(i*15).toDate(), employeeID);
            addHOSLog(dataSource, rec);
        }
        
        logoutDriver(dataSource, testDriverID, testVehicleID, dayStartUTC.plusHours(1).toDate());
        
        // occupant (from GOOD group)
        testGroupData = ((ITData)itData).teamGroupData.get(ITData.GOOD);
        testDriverID = testGroupData.driver.getDriverID();
        loginDriver(dataSource, testDriverID, testVehicleID, dayStartUTC.toDate());
        
        for (int i = 0; i < 4; i++) {
            HOSRecord rec = constructHosRecord(testDeviceID, testVehicleID, HOSStatus.ON_DUTY_OCCUPANT, dayStartUTC.plusMinutes(i*15).toDate(), employeeID);
            addHOSLog(dataSource, rec);
        }
        
        logoutDriver(dataSource, testDriverID, testVehicleID, dayStartUTC.plusHours(1).toDate());
        
        
        
    }

    private HOSRecord constructHosRecord(Integer driverID, Integer vehicleID, HOSStatus status, Date logTime, String employeeID) {
        HOSRecord rec = new HOSRecord();
        rec.setDriverID(driverID);
        rec.setLat(0f);
        rec.setLng(0f);
        rec.setLocation("GENERATED - " + status.getName());
        rec.setLogTime(logTime);
        rec.setServiceID("SERVICE - " + driverID);
        rec.setStatus(status);
        rec.setTrailerGallons(0f);
        rec.setTrailerID("TRAILER - " + driverID);
        rec.setTruckGallons(0f);
        rec.setVehicleID(vehicleID);
        rec.setVehicleOdometer(0l);
        rec.setEmployeeID(employeeID);
        return rec;
    }

    public static void main(String[] args)
    {
        
        DataGenForHOSTesting  testData = new DataGenForHOSTesting();
        testData.parseArguments(args);

        IntegrationConfig config = new IntegrationConfig();
        String host = config.get(IntegrationConfig.SILO_HOST).toString();
        Integer port = Integer.valueOf(config.get(IntegrationConfig.SILO_PORT).toString());
        siloService = new SiloServiceCreator(host, port).getService();
        
        
        
        try
        {
            System.out.println(" -- test data generation start -- ");
            xml = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(testData.xmlPath)));
            System.out.println(" saving output to " + testData.xmlPath);
            testData.createTestData();
            int dateInSec = DateUtil.getTodaysDate();
            xml.writeObject(dateInSec);

            
            if (xml != null)
            {
                xml.close();
            }
            testData.genHOSTestData(new Date((long)dateInSec * 1000l));
            System.out.println(" -- test data generation complete -- ");
        }
        catch (SQLException ex) 
        {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
            
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.exit(1);
        }
        System.exit(0);
    }



}
