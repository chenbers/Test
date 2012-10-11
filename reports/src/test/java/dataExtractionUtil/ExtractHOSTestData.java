package dataExtractionUtil;

import java.io.FileNotFoundException;
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
import java.util.List;
import java.util.TimeZone;

import org.apache.log4j.Logger;

public class ExtractHOSTestData {
    private static Logger logger = Logger.getLogger(ExtractHOSTestData.class);
    public static final int DEFAULT_DAYS_BACK = 30;
    public static final String DEST_PATH = "C:\\develop\\tiwipro\\root\\reports\\src\\test\\resources\\hos\\";
    DBUtilDataSource dataSource;
    DecimalFormat mileageFormat = new DecimalFormat("#");

    public ExtractHOSTestData(DBUtilDataSource dataSource) {
        this.dataSource = dataSource;
    }
    private void extractDriverGroupDataExt(String groupID, Integer customerID, String startDate, String endDate, String baseName) throws SQLException, FileNotFoundException {
        List<DriverIDDOT> drivers = extractDriverData(groupID, customerID, baseName);
        
        for (DriverIDDOT driver : drivers) {
            extractDriverHOSDataExt(baseName, driver, startDate, endDate);
        }
        
    }
    private List<DriverIDDOT> extractDriverData(String groupID, Integer customerID, String baseName) throws FileNotFoundException, SQLException {
        PrintWriter csv = createCSVWriter(baseName);
        Connection conn = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<DriverIDDOT> drivers = new ArrayList<DriverIDDOT>();
        try {
            conn = dataSource.getConnection();
            String sqlStr = " SELECT e.pk_personId, " + " g.name groupName,  " + " e.groupId, " + " e.clientEmployeeID, "
                + " e.dot, " + " e.lastName + ', ' + e.firstName as driverName, " + " tz.tzname " + " FROM  " + "    tableEmployee e, " + " TimeZoneName tz, "
                + " dbo.groupTable('" + groupID + "') gt, "
                + "  groups g  " + " WHERE " + " e.status = 'Active' " + " AND e.fk_customerId = " + customerID
                + " AND e.groupId = g.id and g.companyId=fk_customerID"  
                + " AND LEFT(e.groupId, LEN(gt.id)) = gt.id " + " AND tz.id = e.timezone " + " ORDER BY driverName ";
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
        return drivers;
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

    private void extractGroupData(String groupID, Integer customerID, String baseName) throws FileNotFoundException, SQLException {
        PrintWriter csv = createCSVWriter(baseName + "_groups");
        Connection conn = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            conn = dataSource.getConnection();
            String sqlStr = " select id, name from groups where id like '" + groupID +  "%' and companyID = " + customerID;
System.out.println(sqlStr);            
            statement = conn.createStatement();
            resultSet = statement.executeQuery(sqlStr);
            while (resultSet.next()) {
                String groupId = resultSet.getString(1);
                String groupName = resultSet.getString(2);
                csv.println(groupId + "," + groupName);
            }
        } // end try
        catch (SQLException e) { // handle database hosLogs in the usual manner
            e.printStackTrace();
            logger.error(e);
        } // end catch
        finally { // clean up and release the connection
            resultSet.close();
            statement.close();
            conn.close();
            csv.close();
        } // end finally
        
        
    }
    private void extractDriverHOSDataExt(String baseName, DriverIDDOT driver, String startDate, String endDate) throws SQLException, FileNotFoundException {
        System.out.println(" " + driver.id
                + " " + driver.dot
                + " " + startDate
                + " " + endDate);
        GregorianCalendar logCalendar = new GregorianCalendar();
        logCalendar.setTimeZone(TimeZone.getTimeZone("UTC"));

        PrintWriter csv = createCSVWriter(baseName + "_" + driver.id);
        Connection conn = null;
        CallableStatement statement = null;
        ResultSet resultSet = null;
        try {
            conn = dataSource.getConnection();
            statement = conn.prepareCall("{call cj_getHOSRecsInDateRange(?,?,?,?,?)}");
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
                boolean vehicleDOT = resultSet.getBoolean(8);
                boolean isDeleted = resultSet.getBoolean(9);
                boolean isEdited = resultSet.getBoolean(10);
                int origin = resultSet.getInt(11); 
                Timestamp dateAdded = resultSet.getTimestamp(12, logCalendar);
                String trailerID = resultSet.getString(13);
                String serviceID = resultSet.getString(14);
                String userID = resultSet.getString(15);
                String location = resultSet.getString(16);
                String originalLocation = resultSet.getString(17);


                csv.println(id + "," + status + "," + ruleType + "," + ts.getTime() + "," + timezoneID + "," + vehicleID + "," + singleDriver + "," + vehicleDOT + "," +
                        isDeleted + "," + 
                        isEdited + "," + 
                        origin + "," + 
                        dateAdded.getTime() + "," + 
                        trailerID +"," +   
                        serviceID + "," + 
                        userID + "," + 
                        "\"" + location + "\"" + "," +   
                        "\"" + originalLocation + "\"" + ","  
                        );
            }
        } // end try
        catch (SQLException e) { // handle database hosLogs in the usual manner
            e.printStackTrace();
            logger.error(e);
        } // end catch
        finally { // clean up and release the connection
            resultSet.close();
            statement.close();
            conn.close();
            csv.close();
        } // end finally
    }
    private void extractMileageData(String groupID, Integer customerID, String startDate, String endDate, String baseName) throws SQLException, FileNotFoundException {
        PrintWriter csv = createCSVWriter(baseName + "_mileage");
        Connection conn = null;
        CallableStatement statement = null;
        ResultSet resultSet = null;
        try {
            conn = dataSource.getConnection();
            statement = conn.prepareCall("{call fetchHosMilesByGroup(?,?,?,?,?)}");
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

    
    private void extractZeroMileageData(String groupID, Integer customerID, String startDate, String endDate, String baseName) throws SQLException, FileNotFoundException {
        PrintWriter csv = createCSVWriter(baseName + "_mileageZero");
        Connection conn = null;
        CallableStatement statement = null;
        ResultSet resultSet = null;
        try {
            conn = dataSource.getConnection();
            statement = conn.prepareCall("{call cj_hosZeroMiles(?,?,?,?,?)}");
            statement.setString(1, ""+customerID);
            statement.setString(2, groupID);
            statement.setString(3, startDate);
            statement.setString(4, endDate);
            statement.setString(5, "english");
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String groupId = resultSet.getString(1);
                String unitId = resultSet.getString(2);
                Float distance = resultSet.getFloat(3);
                csv.println(groupId + "," + unitId + "," + mileageFormat.format(distance));
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
        
        // this is not really intended to be something that is run all of the time
        // it was just an easy way to extract the data from gain for unit tests in tiwipro
//        dataSet1();
        dataSet2();
        
        // lists of HOSRecords used by DDL, HosLog
//        dataSet3();
        
        
        
    }
    
    private static void dataSet1() {
        // data set 1 from prod data
        String groupID = "00";
        Integer customerID = 195;
        String startDate = "2010-06-10";
        String endDate = "2010-07-09";
        String baseName = "vtest_00_07012010_07072010";
        String startDateMileage = "2010-07-01";
        String endDateMileage = "2010-07-07";

        ExtractHOSTestData extractHOSTestData = new ExtractHOSTestData(new DBUtilDataSource());
        try {
//            extractHOSTestData.extractGroupData(groupID, customerID, baseName);
//            extractHOSTestData.extractDriverGroupDataExt(groupID, customerID, startDate, endDate, baseName);
//            // mileage data
//            extractHOSTestData.extractMileageData(groupID, customerID, startDateMileage, endDateMileage, baseName);
            extractHOSTestData.extractZeroMileageData(groupID, customerID, startDateMileage, endDateMileage, baseName);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    private static void dataSet2() {
        // data set 2 from SINET data
        String groupID = "01H1";
        Integer customerID = 148;
        String startDate = "2010-06-10";
        String endDate = "2010-07-09";
        String baseName = "vtest_01H1_07012010_07072010";
        String startDateMileage = "2010-07-01";
        String endDateMileage = "2010-07-07";

        ExtractHOSTestData extractHOSTestData = new ExtractHOSTestData(new DBUtilDataSource());
        try {
//            extractHOSTestData.extractGroupData(groupID, customerID, baseName);
//            extractHOSTestData.extractDriverGroupDataExt(groupID, customerID, startDate, endDate, baseName);
//            // mileage data
//            extractHOSTestData.extractMileageData(groupID, customerID, startDateMileage, endDateMileage, baseName);
            extractHOSTestData.extractZeroMileageData(groupID, customerID, startDateMileage, endDateMileage, baseName);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void dataSet3() {
        // data sets for dotMinutes remaining
        String groupID = "000";
        Integer customerID = 195;
        String startDate = "2010-07-17";
        String endDate = "2010-08-06";
        String baseName = "vtest_000_07172010_08062010";

        ExtractHOSTestData extractHOSTestData = new ExtractHOSTestData(new DBUtilDataSource());
        try {
            extractHOSTestData.extractGroupData(groupID, customerID, baseName);
            extractHOSTestData.extractDriverGroupDataExt(groupID, customerID, startDate, endDate, baseName);
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
/*
alter  PROCEDURE [dbo].[cj_getHOSRecsInDateRange](@driverId varchar(50), @driverDotType int, @startTime varchar(50), @endTime varchar(50), @overrideDotType int = -1) AS


    DECLARE @isDOTDriver int
    SELECT @isDOTDriver = coalesce(@driverDotType, 0) 

    DECLARE @extraDriveStatus int
    DECLARE @extraDriveStatus2 int
    IF @overrideDOTType = 15    begin
        select @extraDriveStatus=25
        select @extraDriveStatus2=26
    end
    else
    begin
        select @extraDriveStatus=-1
        select @extraDriveStatus2=-1
    end

        

                        DECLARE @newStartTime datetime
    select top 1 @newStartTime=COALESCE(logTimeUTC, dbo.udf_Timezone_Conversion_datetime(l.timezone, 24, localTime)) from hosLog l with (nolock)
    where COALESCE(logTimeUTC, dbo.udf_Timezone_Conversion_datetime(l.timezone, 24, localTime)) <= @startTime
    AND driverId = @driverId
    AND (status in (0,1,2,3,4,7,8,24) or status = @extraDriveStatus)
    AND deleted != 1
    order by COALESCE(logTimeUTC, dbo.udf_Timezone_Conversion_datetime(l.timezone, 24, localTime)) desc

    SELECT @startTime=COALESCE(@newStartTime, @startTime)

    SELECT @endTime=convert(varchar(20), @endTime, 101) + ' 23:59:59'


        SELECT
        l.id,
        l.status,
        ruleId hosRuleType,
        COALESCE(logTimeUTC, dbo.udf_Timezone_Conversion_datetime(l.timezone, 24, localTime)) logTime,
        tz.tzname,
        v.unitId,
        dbo.hos_isSingleDriver(l.id, @driverId, l.status, ruleId, @driverDotType, COALESCE(logTimeUTC, dbo.udf_Timezone_Conversion_datetime(l.timezone, 24, localTime))) singleDriver,
        v.dot,
        l.deleted,
        l.edited,
        l.origin,
        l.dateadded,
        l.trailerId,
        l.serviceId,
            coalesce(userId, '') editUserId,
            l.location,
            coalesce((SELECT TOP 1 location FROM hosLog_changeLog WHERE id = l.id AND l.location != location ORDER BY dateUpdated), '') originalLocation
    FROM hosLog l with (nolock), 
    tableClientVehicle v with (nolock), 
    TimeZoneName tz
    where  COALESCE(logTimeUTC, dbo.udf_Timezone_Conversion_datetime(l.timezone, 24, localTime)) >=  @startTime
    AND  COALESCE(logTimeUTC, dbo.udf_Timezone_Conversion_datetime(l.timezone, 24, localTime)) <=  @endTime
    AND l.driverId = @driverId
    and l.vehicleId *= v.pk_vehicleId
    AND l.timeZone = tz.id
    ORDER BY COALESCE(logTimeUTC, dbo.udf_Timezone_Conversion_datetime(l.timezone, 24, localTime)) DESC
GO

CREATE         PROCEDURE [dbo].[cj_hosZeroMiles](@customerId int, @groupId varchar(50), @startDate varchar(50), @endDate varchar(50), @units varchar(50)) AS
DECLARE @metric float
SELECT @metric = 1

if @units = 'metric'
    SELECT @metric = 1.609344

SELECT @startDate = convert(varchar(20), @startDate, 101) + ' 00:00:00'
SELECT @endDate = convert(varchar(20), @endDate, 101) + ' 23:59:59'

-- Note: the zero miles counts must match those in the hos violations summary report
SELECT 
    v.groupId, 
    v.UnitID,
    ROUND(SUM(distance) * @metric, 0) AS totalMilesNoDriver
FROM notification n, tableClientVehicle v, dbo.groupTable(@groupId) g
WHERE n.customerId = @customerId 
AND n.type=46
AND n.localTime between @startDate AND @endDate
and n.id not in (select notificationId from nodriver)
AND v.fk_customerId = @customerId
AND LEFT(v.groupId, LEN(g.id)) = g.id
AND n.vehicleId = v.pk_vehicleId
group by v.groupId, v.unitId
having SUM(distance) > 0
order by 1



*/