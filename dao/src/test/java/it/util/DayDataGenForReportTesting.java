package it.util;

import it.config.IntegrationConfig;

import java.beans.XMLDecoder;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

import com.inthinc.pro.dao.hessian.AccountHessianDAO;
import com.inthinc.pro.dao.hessian.GroupHessianDAO;
import com.inthinc.pro.dao.hessian.extension.HessianTCPProxyFactory;
import com.inthinc.pro.dao.hessian.proserver.SiloService;
import com.inthinc.pro.dao.hessian.proserver.SiloServiceCreator;
import com.inthinc.pro.dao.util.DateUtil;
import com.inthinc.pro.model.Account;
import com.inthinc.pro.model.Address;
import com.inthinc.pro.model.Device;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.GroupType;
import com.inthinc.pro.model.User;
import com.inthinc.pro.model.Vehicle;

public class DayDataGenForReportTesting {

    public static SiloService siloService;
	
    Account account;
    Address address;
    Group fleetGroup;
    Group districtGroup;
    List<GroupData> teamGroupData;
    User fleetUser;
    
    class GroupData {
    	Integer driverType;
    	Group group;
    	User user;
    	Device device;
    	Vehicle vehicle;
    	Driver driver;
    	
    }
    public static int GOOD = 0;
    public static int INTERMEDIATE = 1;
    public static int BAD = 2;
    
    private boolean parseTestData(String xmlPath) {
        try {
//            InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(xmlPath);
            InputStream stream = new FileInputStream(xmlPath);
            XMLDecoder xml = new XMLDecoder(new BufferedInputStream(stream));
            account = getNext(xml, Account.class);
            Address address = getNext(xml, Address.class);
            fleetGroup = getNext(xml, Group.class);
            districtGroup = getNext(xml, Group.class);
            teamGroupData = new ArrayList<GroupData>();
            for (int i = GOOD; i <= BAD; i++)
            {
            	Group group = getNext(xml, Group.class);
            	GroupData groupData = new GroupData();
            	groupData.group = group;
            	groupData.driverType = i;
            	teamGroupData.add(groupData);
            }
            fleetUser = getNext(xml, User.class);
            for (int i = GOOD; i <= BAD; i++)
            {
            	GroupData groupData = teamGroupData.get(i);
            	groupData.user  = getNext(xml, User.class);
            	groupData.device  = getNext(xml, Device.class);
            	groupData.driver  = getNext(xml, Driver.class);
            	groupData.vehicle  = getNext(xml, Vehicle.class);
            }
            xml.close();
            return dataExists();
        }
        catch (Exception ex) {
            System.out.println("error reading " + xmlPath);
            ex.printStackTrace();
            return false;
        }
    }
    
    private static <T> T getNext(XMLDecoder xml, Class<T> expectedType) throws Exception {
        Object result = xml.readObject();
        if (expectedType.isInstance(result)) {
            return (T) result;
        }
        else {
            throw new Exception("Expected " + expectedType.getName());
        }
    }


    private boolean dataExists() {
        // just spot check that account and team exist (this could be more comprehensive)
        AccountHessianDAO accountDAO = new AccountHessianDAO();
        accountDAO.setSiloService(siloService);
        Account existingAccount = accountDAO.findByID(account.getAcctID());
        boolean dataExists = (existingAccount != null);
        if (dataExists) {
            GroupHessianDAO groupDAO = new GroupHessianDAO();
            groupDAO.setSiloService(siloService);
            Group existingTeam = groupDAO.findByID(teamGroupData.get(0).group.getGroupID());
            dataExists = (existingTeam != null && existingTeam.getType().equals(GroupType.TEAM));
        }
        if (!dataExists)
        {
        	System.out.println("TEST DATA is missing: regenerate the base test data set");
        }
        return dataExists;
    }

	private void generateDayData(MCMSimulator mcmSim, Date date, Integer driverType) throws Exception 
	{
		for (GroupData groupData : teamGroupData)
		{
			if (groupData.driverType.equals(driverType))
			{
				
				EventGenerator eventGenerator = new EventGenerator();
				switch (driverType.intValue()) {
				case 0:			// good
					eventGenerator.generateTrip(groupData.device.getImei(), mcmSim, date, new EventGeneratorData(0,0,0,0,30));
					break;
				case 1:			// intermediate
					eventGenerator.generateTrip(groupData.device.getImei(), mcmSim, date, new EventGeneratorData(1,1,1,1,25));
				break;
				case 2:			// bad
					eventGenerator.generateTrip(groupData.device.getImei(), mcmSim, date, new EventGeneratorData(5,5,5,5,20));
				break;
				
				}
			}
		}
			
		
	}

    public static void main(String[] args)
    {
        String xmlPath = null;
        String dateStr = null;
        String driverTypeStr = null;
        String numDaysStr = null;
        if (args.length == 4)
        {
            xmlPath = args[0];
            dateStr = args[1];
            driverTypeStr = args[2];
            numDaysStr = args[3];
        }
        else
        {
        	System.out.println("Command Line Args: <full path to the xml file test data> <date of events mm/dd/yyyy> <0 - GOOD, 1 - INTERMEDIATE, 2 - BAD> <num days>");
        	System.exit(0);
        }
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Date date = null;
		try {
			date = dateFormat.parse(dateStr);
			
			
			TimeZone tz = SimpleTimeZone.getTimeZone("GMT");
			dateFormat.setTimeZone(tz);
			Date test = dateFormat.parse("12/07/1959");
			System.out.println(""+ test.getTime());
			
		} catch (ParseException e1) {
        	System.out.println("Command Line Args: expected day format mm/dd/yyyy");
        	System.exit(0);
		}
//		tzFormat.setTimeZone(TimeZone.getTimeZone(timeZone));

		Integer driverType = null;
		try
		{
			driverType = Integer.valueOf(driverTypeStr);
			if (driverType < 0 || driverType > 2)
				throw new NumberFormatException("");
		}
		catch (NumberFormatException ne)
		{
        	System.out.println("Command Line Args: expected driver Type of 0, 1 or 2");
        	System.exit(0);
		}
        
		Integer numDays = null;
		try
		{
			numDays = Integer.valueOf(numDaysStr);
			if (numDays < 1)
				throw new NumberFormatException("");
		}
		catch (NumberFormatException ne)
		{
        	System.out.println("Command Line Args: expected numDays greater than 0");
        	System.exit(0);
		}
        

        IntegrationConfig config = new IntegrationConfig();

        String host = config.get(IntegrationConfig.SILO_HOST).toString();
        Integer port = Integer.valueOf(config.get(IntegrationConfig.SILO_PORT).toString());
        
        siloService = new SiloServiceCreator(host, port).getService();
        
        
        DayDataGenForReportTesting  testData = new DayDataGenForReportTesting();
        try
        {
            if (!testData.parseTestData(xmlPath))
            {
            	System.out.println("Parse of xml data file failed.  File: " + xmlPath);
            	System.exit(0);
            }
            
            
            HessianTCPProxyFactory factory = new HessianTCPProxyFactory();
            MCMSimulator mcmSim = (MCMSimulator) factory.create(MCMSimulator.class, config.getProperty(IntegrationConfig.MCM_HOST), config.getIntegerProp(IntegrationConfig.MCM_PORT));
            for (int i = 0; i < numDays; i++)
            {
            	testData.generateDayData(mcmSim, date, driverType);
            	date = new Date(date.getTime() + DateUtil.MILLISECONDS_IN_DAY);
            }
         
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.exit(0);
    }


}
