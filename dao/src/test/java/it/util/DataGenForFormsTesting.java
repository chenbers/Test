package it.util;

import it.com.inthinc.pro.dao.model.ITData;
import it.config.IntegrationConfig;
import it.config.ReportTestConst;

import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.inthinc.pro.dao.hessian.RoleHessianDAO;
import com.inthinc.pro.dao.hessian.UserHessianDAO;
import com.inthinc.pro.dao.hessian.proserver.SiloServiceCreator;
import com.inthinc.pro.dao.util.DateUtil;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.Status;
import com.inthinc.pro.model.User;
import com.inthinc.pro.model.security.AccessPoint;
import com.inthinc.pro.model.security.Role;

public class DataGenForFormsTesting  extends DataGenForTesting {

	public String xmlPath;
	
    @Override
    protected void createTestData() {
    	itData = new ITData();
        Date assignmentDate = DateUtil.convertTimeInSecondsToDate(DateUtil.getDaysBackDate(DateUtil.getTodaysDate(), 2, ReportTestConst.TIMEZONE_STR));
        ((ITData)itData).createTestData(siloService, xml, assignmentDate, true, true, true);
    }

    @Override
    protected boolean parseTestData(){
    	return true;
    }

	
	protected void parseArguments(String[] args) {
		// Arguments:
		//		required
		//			0:		full path to xml file for storage/retrieval of current data set
		
		String usageErrorMsg = "Usage: DataGenForFormsTest <xml file path>";
		
        if (args.length < 1)
        {
        	System.out.println(usageErrorMsg);
        	System.exit(1);
        }
        
        xmlPath = args[0];

        File file = new File(xmlPath); 
        if (!file.exists()) {
            
            String dir = file.getParent();
            new File(dir).mkdirs();
        }

        
	}

    public static void main(String[] args)
    {
        
    	DataGenForFormsTesting  testData = new DataGenForFormsTesting();
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
            xml.writeObject(DateUtil.getTodaysDate());
            ((ITData)testData.itData).createFormsUser(testData.itData.account.getAccountID(), testData.itData.fleetGroup.getGroupID());
            ((ITData)testData.itData).createNormalUser(testData.itData.account.getAccountID(), testData.itData.fleetGroup.getGroupID());

	        
            if (xml != null)
            {
                xml.close();
            }
	            
            System.out.println(" -- test data generation complete -- ");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.exit(1);
        }
        System.exit(0);
    }



}
