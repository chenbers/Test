package dataExtractionUtil;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;

import org.apache.log4j.Logger;

public class ExtractHOSTestData {
    private static Logger logger = Logger.getLogger(ExtractHOSTestData.class);
    public static final int DEFAULT_DAYS_BACK = 30;
    public static final String DEST_PATH = "C:\\develop\\tiwipro\\root\\reports\\src\\test\\resources\\violations\\";
    DBUtilDataSource dataSource;
    DecimalFormat mileageFormat = new DecimalFormat("#");

    public ExtractHOSTestData(DBUtilDataSource dataSource) {
        this.dataSource = dataSource;
    }
    private void extractDriverGroupData(String groupID, Integer customerID, String startDate, String endDate, String baseName) throws SQLException, FileNotFoundException {
        PrintWriter csv = createCSVWriter(baseName);
        Connection conn = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<DriverIDDOT> drivers = new ArrayList<DriverIDDOT>();
        try {
            conn = dataSource.getConnection();
            String sqlStr = " SELECT e.pk_personId, " + " g.name groupName,  " + " e.groupId, " + " e.clientEmployeeID, "
                    + " e.dot, " + " e.lastName + ', ' + e.firstName as driverName, " + " tz.tzname " + " FROM  " + "    tableEmployee e, " + " TimeZoneName tz, "
                    + "  groups g  " + " WHERE " + " e.status = 'Active' " + " AND e.fk_customerId = " + customerID
                    + " AND e.groupId = g.id and g.companyId=fk_customerID"  
                    + " AND LEFT(e.groupId, LEN(g.id)) = g.id " + " AND tz.id = e.timezone " + " ORDER BY driverName ";
            statement = conn.createStatement();
            resultSet = statement.executeQuery(sqlStr);
            while (resultSet.next()) {
                String driverId = resultSet.getString(1);
                String groupName = resultSet.getString(2);
                String groupId = resultSet.getString(3);
                String employeeId = resultSet.getString(4);
                int dot = resultSet.getInt(5);
                String driverName = resultSet.getString(6);
                String timeZone = resultSet.getString(7);
                drivers.add(new DriverIDDOT(driverId, dot));
                csv.println(driverId + "," + groupName + "," + groupId + "," + employeeId + "," + dot + ",\"" + driverName + "\"," + timeZone);
            }
        } // end try
        catch (SQLException e) { // handle database hosLogs in the usual manner
            logger.error(e);
        } // end catch
        finally { // clean up and release the connection
            resultSet.close();
            statement.close();
            conn.close();
            csv.close();
        } // end finally
        
        for (DriverIDDOT driver : drivers) {
            extractDriverHOSData(baseName, driver, startDate, endDate);
        }
        
        
    }
    private PrintWriter createCSVWriter(String baseName) throws FileNotFoundException {
        PrintWriter csv = null;
        try {
            csv = new PrintWriter(DEST_PATH + baseName + ".csv");
        } catch (FileNotFoundException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
            throw e1;
        }
        return csv;
    }

    private void extractDriverHOSData(String baseName, DriverIDDOT driver, String startDate, String endDate) throws SQLException, FileNotFoundException {
        GregorianCalendar logCalendar = new GregorianCalendar();
        logCalendar.setTimeZone(TimeZone.getTimeZone("UTC"));

        PrintWriter csv = createCSVWriter(baseName + "_" + driver.id);
        Connection conn = null;
        CallableStatement statement = null;
        ResultSet resultSet = null;
        try {
            conn = dataSource.getConnection();
            statement = conn.prepareCall("{call hos_getHOSRecsInDateRange(?,?,?,?,?)}");
            statement.setString(1, driver.id);
            statement.setInt(2, driver.dot);
            statement.setString(3, startDate);
            statement.setString(4, endDate);
            statement.setInt(5, -1);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String id = resultSet.getString(1);
                int status = resultSet.getInt(2);
                int ruleType = resultSet.getInt(3); 
                Timestamp ts = resultSet.getTimestamp(4, logCalendar);
                String timezoneID = resultSet.getString(5);
                String vehicleID = resultSet.getString(6);
                boolean singleDriver = resultSet.getBoolean(7);
                boolean nonDOTDriverDrivingDOTVehicle = resultSet.getBoolean(8);

                csv.println(id + "," + status + "," + ruleType + "," + ts.getTime() + "," + timezoneID + "," + vehicleID + "," + singleDriver + "," + nonDOTDriverDrivingDOTVehicle);
            }
        } // end try
        catch (SQLException e) { // handle database hosLogs in the usual manner
            logger.error(e);
        } // end catch
        finally { // clean up and release the connection
            resultSet.close();
            statement.close();
            conn.close();
            csv.close();
        } // end finally
    }
    private void extractMileageData(String groupID, Integer customerID, String startDate, String endDate, String baseName, boolean zeroMiles) throws SQLException, FileNotFoundException {
        PrintWriter csv = null;
        if (zeroMiles)
                csv = createCSVWriter(baseName + "_mileageZero");
        else csv = createCSVWriter(baseName + "_mileage");
        Connection conn = null;
        CallableStatement statement = null;
        ResultSet resultSet = null;
        try {
            conn = dataSource.getConnection();
            if (zeroMiles)
                statement = conn.prepareCall("{call fetchHosZeroMilesByGroup(?,?,?,?,?)}");
            else statement = conn.prepareCall("{call fetchHosMilesByGroup(?,?,?,?,?)}");
            statement.setString(1, ""+customerID);
            statement.setString(2, groupID);
            statement.setString(3, startDate);
            statement.setString(4, endDate);
            statement.setString(5, "english");
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String groupId = resultSet.getString(1);
                Float distance = resultSet.getFloat(2);
                csv.println(groupId + "," + mileageFormat.format(distance));
            }
        } // end try
        catch (SQLException e) { // handle database hosLogs in the usual manner
            logger.error(e);
        } // end catch
        finally { // clean up and release the connection
            resultSet.close();
            statement.close();
            conn.close();
            csv.close();
        } // end finally
        
    }


    /**
     * @param args
     */
    public static void main(String[] args) {
        dataSet1();
    }
    
    private static void dataSet1() {
        // data set 1 from prod data
        String groupID = "00";
        Integer customerID = 195;
        String startDate = "2010-06-10 00:00:00";
        String endDate = "2010-07-09 23:59:59";
        String baseName = "vtest_00_07012010_07072010";
        String startDateMileage = "2010-07-01";
        String endDateMileage = "2010-07-07";

        ExtractHOSTestData extractHOSTestData = new ExtractHOSTestData(new DBUtilDataSource());
        try {
            extractHOSTestData.extractDriverGroupData(groupID, customerID, startDate, endDate, baseName);
            // mileage data
            extractHOSTestData.extractMileageData(groupID, customerID, startDateMileage, endDateMileage, baseName, false);
            extractHOSTestData.extractMileageData(groupID, customerID, startDateMileage, endDateMileage, baseName, true);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public class DriverIDDOT {
        String id;
        Integer dot;

        public DriverIDDOT(String id, Integer dot) {
            super();
            this.id = id;
            this.dot = dot;
        }
    }
}
