package it.util;

import it.com.inthinc.pro.dao.model.ITDataOneTeamExt;
import it.config.IntegrationConfig;

import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

import com.inthinc.pro.dao.hessian.proserver.SiloServiceCreator;
import com.inthinc.pro.dao.util.DateUtil;

public class DataGenForOneTeam  extends DataGenForTesting {

	public String xmlPath;
	
    @Override
    protected void createTestData() {
    	itData = new ITDataOneTeamExt();
        Date assignmentDate = new Date();
        ((ITDataOneTeamExt)itData).createTestDataExt(siloService, xml, assignmentDate);
    }

    @Override
    protected boolean parseTestData(){
    	return true;
    }

	
	protected void parseArguments(String[] args) {
		// Arguments:
		//		required
		//			0:		full path to xml file for storage/retrieval of current data set
		
		String usageErrorMsg = "Usage: DataGenForDriverLogoffTesting <xml file path>";
		
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
        
        DataGenForOneTeam  testData = new DataGenForOneTeam();
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
