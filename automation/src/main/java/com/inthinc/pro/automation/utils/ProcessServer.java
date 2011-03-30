package com.inthinc.pro.automation.utils;

import org.apache.log4j.Logger;

import com.inthinc.pro.automation.enums.Addresses;

public class ProcessServer {
	Addresses[] url = new Addresses[4];
	private final static Logger logger = Logger.getLogger(AutomationLogger.class);
	               
	public ProcessServer(String server){
		process(server);
	}
	public ProcessServer(){
		
	}
	
	private void process(String server){
		Addresses url = Addresses.QA_PORTAL;
		Addresses port = Addresses.QA_PORT;
		Addresses mcmurl = Addresses.QA_MCM;
	    Addresses mcmport = Addresses.QA_MCM_PORT;
	    
	    if (server.compareToIgnoreCase("dev")==0){
	    	url = Addresses.DEV_PORTAL;
	    	port = Addresses.DEV_PORT;
	    	mcmurl = Addresses.DEV_MCM;
	    	mcmport = Addresses.DEV_MCM_PORT;
	    }
	    
	    else if (server.compareToIgnoreCase("qa")==0){
	    	url = Addresses.QA_PORTAL;
	    	port = Addresses.QA_PORT;
	    	mcmurl = Addresses.QA_MCM;
	    	mcmport = Addresses.QA_MCM_PORT;
	    }
	    
	    else if (server.compareToIgnoreCase("qa2")==0){
	    	url = Addresses.QA2_PORTAL;
	    	port = Addresses.QA2_PORT;
	    	mcmurl = Addresses.QA2_MCM;
	    	mcmport = Addresses.QA2_MCM_PORT;
	    }
	    
	    else if (server.compareToIgnoreCase("qa stage")==0){
	    	url = Addresses.EC2_PORTAL;
	    	port = Addresses.EC2_PORT;
	    	mcmurl = Addresses.EC2_MCM;
	    	mcmport = Addresses.EC2_MCM_PORT;
	    }
	    
	    else if (server.compareToIgnoreCase("prod")==0){
	    	url = Addresses.PROD_PORTAL;
	    	port = Addresses.PROD_PORT;
	    	mcmurl = Addresses.PROD_MCM;
	    	mcmport = Addresses.PROD_MCM_PORT;
	        
	    }
	    
	    else if (server.compareToIgnoreCase("chevron")==0){
	    	url = Addresses.CHEVRON_PORTAL;
	    	port = Addresses.CHEVRON_PORT;
	    	mcmurl = Addresses.CHEVRON_MCM;
	    	mcmport = Addresses.CHEVRON_MCM_PORT;
	        
	    }
	    
	    else if (server.compareToIgnoreCase("schlumberger")==0){
	    	url = Addresses.SLB_PORTAL;
	    	port = Addresses.SLB_PORT;
	    	mcmurl = Addresses.SLB_MCM;
	    	mcmport = Addresses.SLB_MCM_PORT;
	    }
	    
//	    else if (server.compareToIgnoreCase("prod2")==0){
//	    	url = Addresses.PROD_PORTAL_EC2;
//	    	port = Addresses.PROD_PORT_EC2;
//	    	mcmurl = Addresses.PROD_MCM_EC2;
//	    	mcmport = Addresses.PROD_MCM_PORT_EC2;
//	        
//	    }
	    else if (server.compareToIgnoreCase("teen_qa")==0){
	    	url = Addresses.TEEN_PORTAL_QA;
	    	port = Addresses.TEEN_PORT_QA;
	    	mcmurl = Addresses.TEEN_MCM_QA;
	    	mcmport = Addresses.TEEN_MCM_PORT_QA;
	    }
	    
	//    else if (server.compareToIgnoreCase("teen_prod")==0){
	//  	  url = Addresses.;
	//        mcmport = Addresses.;
	//        
	//    }
	//    else if (server.compareToIgnoreCase("teen_dev")==0){
	//  	  url = Addresses.;
	//        mcmport = Addresses.;
	    
	    Addresses[] urlandport = {url, port, mcmurl, mcmport};
	    logger.debug("PortalProxy: " +url+":"+port+"  McmProxy: "+mcmurl+":"+mcmport);
	    this.url = urlandport;
	}
	
	public Addresses[] getUrl(String server){
		process(server);
		return url;
	}
	
	public Addresses[] getUrl(){
		return url;
	}
}
