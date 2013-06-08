package it.util.hos;

import it.config.ITDataSource;
import it.config.IntegrationConfig;

import java.net.MalformedURLException;
import java.util.List;
import java.util.TimeZone;

import com.inthinc.hos.model.HOSRec;
import com.inthinc.pro.dao.hessian.DriverHessianDAO;
import com.inthinc.pro.dao.hessian.GroupHessianDAO;
import com.inthinc.pro.dao.hessian.proserver.SiloService;
import com.inthinc.pro.dao.hessian.proserver.SiloServiceCreator;
import com.inthinc.pro.dao.jdbc.HOSJDBCDAO;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.GroupHierarchy;
import com.inthinc.pro.model.hos.HOSRecord;

public class HOSTestDataUtil {
    private Integer driverID;
    private Integer editUserID;
    private String xmlPath;
    private String testCaseID;
    private String currentDay;
    private SiloService siloService;
    
    protected void parseArguments(String[] args) {
        // Arguments:
        //      required
        //          0:      full path to xml file for storage/retrieval of current data set
        
        String usageErrorMsg = "Usage: HOSTestDataUtil <driverID> <xml file path> <testcase ID> [<current_day yyyy-mm-dd>]";
        
        if (args.length < 3)
        {
            System.out.println(usageErrorMsg);
            System.exit(1);
        }
        driverID = Integer.valueOf(args[0]);
        xmlPath = args[1];
        testCaseID = args[2];
        if (args.length > 3) {
            currentDay = args[3];
        }
    }
    
    protected  void initServices() throws MalformedURLException {
        IntegrationConfig config = new IntegrationConfig();
        initServices(config);
    }
    protected void initServices(IntegrationConfig config) throws MalformedURLException {
        String host = config.get(IntegrationConfig.SILO_HOST).toString();
        Integer port = Integer.valueOf(config.get(IntegrationConfig.SILO_PORT).toString());
        siloService = new SiloServiceCreator(host, port).getService();

    }

    private void populateHOSLogs() {
        Driver driver = initDriver();
        if (driver == null) {
            System.out.println("Unable to find driver for driverID: " + driverID);
            System.exit(1);
        }
        
        editUserID = determineEditUserID(driver);
        
        List<HOSRec> driverLog = initDriverLog(driver.getPerson().getTimeZone());
        if (driverLog == null) {
            System.out.println("Unable to find driverLog for driverID: " + driverID + " xml: " + xmlPath + " testCaseID:" + testCaseID);
            System.exit(1);
        }


        HOSJDBCDAO hosDAO = new HOSJDBCDAO();
        hosDAO.setDataSource(new ITDataSource().getRealDataSource());
        
        for (HOSRec hosRec : driverLog) {
            HOSRecord hosRecord = createHOSRecord(hosRec, driver);
            hosDAO.create(0l, hosRecord);
        }
        
    }
    
    
    private Integer determineEditUserID(Driver driver) {
        // if there is a manager anywhere up the group Hierarchy use that.
        GroupHessianDAO groupDAO = new GroupHessianDAO();
        groupDAO.setSiloService(siloService);
        Group driverGroup = groupDAO.findByID(driver.getGroupID());
        GroupHierarchy groupHierarchy = new GroupHierarchy(groupDAO.getGroupHierarchy(driver.getPerson().getAccountID(), driver.getGroupID()));
        Group group = driverGroup;
        do {
            if (group.getManagerID() != null && group.getManagerID() != 0)
                return group.getManagerID();
            group = groupHierarchy.getParentGroup(group);
        } while (group != null);
            

        return 0;
    }

    private HOSRecord createHOSRecord(HOSRec hosRec, Driver driver) {
        HOSRecord hosRecord = new HOSRecord();
        hosRecord.setTimeZone(driver.getPerson().getTimeZone());
        hosRecord.setDriverID(driver.getDriverID());
        hosRecord.setDriverDotType(driver.getDot());
        
        hosRecord.setLogTime(hosRec.getLogTimeDate());
        hosRecord.setStatus(hosRec.getStatus());
        hosRecord.setLocation(testCaseID);
        hosRecord.setEditUserID(editUserID);
        
        return hosRecord;
    }

    private List<HOSRec> initDriverLog(TimeZone driverTimeZone) {
        try {
            TestCaseXML testCaseXML = new TestCaseXML(xmlPath, currentDay, driverTimeZone);
            
            for (TestCase testCase : testCaseXML.testCases) {
                if (testCase.getId().equalsIgnoreCase(testCaseID)) {
                    return testCase.getDriverLog();
                }
            }
            
            
            return null;
        }
        catch (Throwable t) {
            t.printStackTrace();
            return null;
        }
    }

    private Driver initDriver() {
        DriverHessianDAO driverDAO = new DriverHessianDAO();
        driverDAO.setSiloService(siloService);
        
        try {
            Driver driver = driverDAO.findByID(driverID);
            return driver;
        }
        catch (Throwable t) {
            t.printStackTrace();
        }
        
        return null;
    }

    public static void main(String[] args)
    {
        
        HOSTestDataUtil  testData = new HOSTestDataUtil();
        testData.parseArguments(args);

        try {
            testData.initServices();
        } catch (MalformedURLException e1) {
            e1.printStackTrace();
            System.exit(1);
        }
        
        testData.populateHOSLogs();
        
//        try
//        {
//            // get start of day today in driver's time zone
//            DateTime startOfDriverDay = new DateMidnight(new Date(), DateTimeZone.forTimeZone(ReportTestConst.timeZone)).toDateTime();
//            int startOfDriverDayInSec = DateUtil.getDaysBackDate(startOfDriverDay.toDate().getTime()/1000l, 1, ReportTestConst.TIMEZONE_STR);
//        }
//        catch (SQLException ex) 
//        {
//            System.out.println(ex.getMessage());
//            ex.printStackTrace();
//            
//        }
//        catch (Exception e)
//        {
//            e.printStackTrace();
//            System.exit(1);
//        }
        System.exit(0);
    }




}
