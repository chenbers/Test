package it.util;

import it.com.inthinc.pro.dao.model.GroupData;
import it.com.inthinc.pro.dao.model.ITData;
import it.config.ITDataSource;
import it.config.ReportTestConst;

import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.net.MalformedURLException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import javax.sql.DataSource;

import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import com.inthinc.hos.model.HOSStatus;
import com.inthinc.pro.dao.util.DateUtil;
import com.inthinc.pro.model.hos.HOSRecord;

public class DataGenForHOSTesting extends DataGenForTesting {
    public String xmlPath;
    
    public static Integer LOGIN_ODOMETER = 0;
    public static Integer LOGOUT_ODOMETER = 9700;
    

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
    
    private void loginDriver(DataSource dataSource, String deviceAddress, String empID, Date loginDate) throws SQLException {
        login(dataSource, deviceAddress, empID, loginDate, false);
    }
    private void loginOccupant(DataSource dataSource, String deviceAddress, String empID, Date loginDate) throws SQLException {
        login(dataSource, deviceAddress, empID, loginDate, true);
    }
    private void login(DataSource dataSource, String deviceAddress, String empID, Date loginDate, boolean isOccupant) throws SQLException {
        
        Connection conn = null;
        CallableStatement statement = null;
        ResultSet resultSet = null;
        
        try
        {
            conn = dataSource.getConnection();
            statement = conn.prepareCall("{call hos_isValidLogin(?, ?, ?, ?, ?)}");
            statement.setString(1, deviceAddress);
            statement.setString(2, empID);
            statement.setLong(3, loginDate.getTime());
            statement.setBoolean(4, isOccupant);
            statement.setInt(5, LOGIN_ODOMETER);
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
            statement = conn.prepareCall("{call hos_createFromNote(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?, ?,?,?)}");
            statement.setInt(1, hosRecord.getDeviceID());
            statement.setInt(2, hosRecord.getVehicleID());
            statement.setLong(3, 0l);
            statement.setInt(4, hosRecord.getStatus().getCode());
            statement.setLong(5, hosRecord.getLogTime().getTime());
            statement.setInt(6, hosRecord.getVehicleOdometer().intValue());
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
            statement.setInt(17, hosRecord.getStateID());
            statement.setInt(18, 0);
            statement.setInt(19, 0);
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


    private void genHOSTestData(DateTime startDateTime) throws SQLException {
        
System.out.println("DATE: " + startDateTime);        
        GroupData testGroupData = ((ITData)itData).teamGroupData.get(ITData.INTERMEDIATE);
        String testIMEI = testGroupData.device.getImei();
        Integer testDeviceID = testGroupData.device.getDeviceID();
        Integer testDriverID = testGroupData.driver.getDriverID();
        Integer testVehicleID = testGroupData.vehicle.getVehicleID();
        String employeeID = testGroupData.driver.getPerson().getEmpid();
System.out.println("testDeviceID = " + testIMEI + " vehicleID = " + testVehicleID + " DriverID: " + testDriverID + " emp id " + employeeID);        
        
        
        DataSource dataSource = new ITDataSource().getRealDataSource();
        
        // driver (from INTERMEDIATE group)
        loginDriver(dataSource, testIMEI, employeeID, startDateTime.toDate());
        int odometer[] = {
                0,
                4000,
                7000,
                9700
        };
        HOSStatus driverStatus[] = {
                HOSStatus.ON_DUTY,
                HOSStatus.DRIVING,
                HOSStatus.SLEEPER,
                HOSStatus.OFF_DUTY  // logs the driver out
        };
        for (int i = 0; i < 4; i++) {
            
            HOSRecord rec = constructHosRecord(testDeviceID, testDriverID, testVehicleID, driverStatus[i], startDateTime.plusMinutes(i*15).toDate(), employeeID, odometer[i]);
            addHOSLog(dataSource, rec);
        }
        
        
        // occupant (from GOOD group)
        // note: the occupant and driver login time (on duty) cannot be exactly the same as the driver's, hence the 5 second offset
        testGroupData = ((ITData)itData).teamGroupData.get(ITData.GOOD);
        testDriverID = testGroupData.driver.getDriverID();
        employeeID = testGroupData.driver.getPerson().getEmpid();
        loginOccupant(dataSource, testIMEI, employeeID, startDateTime.plusSeconds(5).toDate());
        
        HOSStatus occupantStatus[] = {
                HOSStatus.ON_DUTY_OCCUPANT,
                HOSStatus.ON_DUTY_OCCUPANT,
                HOSStatus.ON_DUTY_OCCUPANT,
                HOSStatus.OFF_DUTY_OCCUPANT  // logs the OCCUPANT out
        };
        for (int i = 0; i < 4; i++) {
            HOSRecord rec = constructHosRecord(testDeviceID, testDriverID, testVehicleID, occupantStatus[i], startDateTime.plusMinutes(i*15).plusSeconds(5).toDate(), employeeID, odometer[i]);
            addHOSLog(dataSource, rec);
        }
    }

    private HOSRecord constructHosRecord(Integer deviceID, Integer driverID, Integer vehicleID, HOSStatus status, Date logTime, String employeeID, int odometer) {
        HOSRecord rec = new HOSRecord();
        rec.setDeviceID(deviceID);
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
        rec.setVehicleOdometer(Long.valueOf(odometer));
        rec.setEmployeeID(employeeID);
        rec.setStateID(5);
        return rec;
    }

    public static void main(String[] args)
    {
        
        DataGenForHOSTesting  testData = new DataGenForHOSTesting();
        testData.parseArguments(args);

        try {
            initServices();
        } catch (MalformedURLException e1) {
            e1.printStackTrace();
            System.exit(1);
        }
        
        try
        {
            System.out.println(" -- test data generation start -- ");
            xml = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(testData.xmlPath)));
            System.out.println(" saving output to " + testData.xmlPath);
            testData.createTestData();
            
            // get start of day today in driver's time zone
            DateTime startOfDriverDay = new DateMidnight(new Date(), DateTimeZone.forTimeZone(ReportTestConst.timeZone)).toDateTime();
            int startOfDriverDayInSec = DateUtil.getDaysBackDate(startOfDriverDay.toDate().getTime()/1000l, 1, ReportTestConst.TIMEZONE_STR);

            xml.writeObject(startOfDriverDayInSec);
            if (xml != null)
            {
                xml.close();
            }
            testData.genHOSTestData(startOfDriverDay);
            
            testData.waitForIMEIs(startOfDriverDayInSec + 60, ((ITData)testData.itData).teamGroupData);
            // generate some notes so we get mileage data
            for (int teamType = ITData.GOOD; teamType <= ITData.BAD; teamType++)
            {
                testData.generateDayData(startOfDriverDay.toDate(), teamType, ((ITData)testData.itData).teamGroupData);
            }
         
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
