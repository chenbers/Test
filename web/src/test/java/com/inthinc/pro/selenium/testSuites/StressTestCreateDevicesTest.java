package com.inthinc.pro.selenium.testSuites;

import org.jbehave.core.annotations.UsingSteps;
import org.junit.Ignore;
import org.junit.Test;

import com.inthinc.pro.automation.annotations.AutomationAnnotations.PageObjects;
import com.inthinc.pro.automation.annotations.AutomationAnnotations.StoryPath;
import com.inthinc.pro.selenium.pageObjects.PageLogin;
import com.inthinc.pro.selenium.pageObjects.PageUtil;
import com.inthinc.pro.selenium.steps.LoginSteps;
import com.inthinc.pro.selenium.steps.StressTestSteps;
import com.inthinc.pro.selenium.steps.UtilSteps;

@UsingSteps(instances={LoginSteps.class, StressTestSteps.class, UtilSteps.class})
@PageObjects(list={PageLogin.class, PageUtil.class })
@StoryPath(path="StressTestCreateDevices.story")

public class StressTestCreateDevicesTest extends WebStories {
	@Ignore
    @Test
    public void test(){}
	
}
	
//	protected static SiloService siloService;
//	public static Account account;
//	private static RandomValues random = new RandomValues();
//
//	public static void main(String[] args) {
//
//        	int num = Util.randomInt(10, 25);
//        	for (int i = 0; i < num; i++)
//        	{
//        		Device device = createDevice(4564);
//        	}
//	}
//	
//    private static Device createDevice(Integer uniqueID)
//    {
//        DeviceHessianDAO deviceDAO = new DeviceHessianDAO();
//        deviceDAO.setSiloService(siloService);
//        
//        for (int retryCnt = 0; retryCnt < 100; retryCnt++) {
//	        try{
//		        Device device = new Device(1, 7956, DeviceStatus.ACTIVE, "Device_" + uniqueID, 
//		        		genNumericID(uniqueID, 15), genNumericID(uniqueID, 19), genNumericID(uniqueID, 10), 
//		        		genNumericID(uniqueID, 10));
//		        Integer deviceID = deviceDAO.create(account.getAccountID(), device);
//		        device.setDeviceID(deviceID);
//		        
//		        
//		        return device;
//	        }
//	        catch (DuplicateIMEIException ex) {
//	        	if (retryCnt == 99) {
//	        		System.out.println("duplicate after 99 attempts");
//	        		throw ex;
//	        	}
//	        }
//        }
//	    return null;
//    }
//    
//    protected static String genNumericID(Integer acctID, Integer len)
//    {
//        String id = "9" + acctID.toString();
//        
//        for (int i = id.length(); i < len; i++)
//        {
//            id += "" + Util.randomInt(0, 9);
//        }
//        
//        return id;
//    }
	
//    private static RandomValues random;
//    
//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//        
//
//        int i = 1;
//        Date activated = new Date();            
//        random = new RandomValues();
//         	Device device = new Device();
////        	while (i < 1000) {
//        		device.setDeviceID(i + 2);
//        		device.setAccountID(7956);
////        		device.setProductVersion(1);
//        		device.setStatus(DeviceStatus.ACTIVE);
//        		device.setName("DEVICE" + i);
//        		device.setImei(random.getCharIntString(20));
//        		device.setSim(random.getIntString(20));
//        		device.setPhone(random.getIntString(3) + "-" + random.getIntString(3) + "-" + 
//        		random.getIntString(4));
//        		device.setActivated(activated);
////        	i++;
//        	System.out.println("Device ID" + device.getDeviceID() + "Device Name" + device.getName() +
//        			"Device IMEI " + device.getImei() + "\n");
//        	return;
////        }
//	}
//


	

