package com.inthinc.pro.automation.utils;

import org.apache.log4j.Logger;

import com.inthinc.pro.automation.enums.Addresses;

public class ProcessServer {
	Addresses[] url = new Addresses[4];
	private final static Logger logger = Logger.getLogger(AutomationLogger.class);
	               
	public ProcessServer(Addresses server){
		process(server);
	}
	public ProcessServer(){
		
	}
	
	private void process(Addresses server){
		Addresses url = Addresses.QA_PORTAL;
		Addresses port = Addresses.QA_PORT;
		Addresses mcmurl = Addresses.QA_MCM;
	    Addresses mcmport = Addresses.QA_MCM_PORT;
	    
	    if (server==Addresses.DEV){
	    	url = Addresses.DEV_PORTAL;
	    	port = Addresses.DEV_PORT;
	    	mcmurl = Addresses.DEV_MCM;
	    	mcmport = Addresses.DEV_MCM_PORT;
	    }
	    
	    else if (server==Addresses.QA){
	    	url = Addresses.QA_PORTAL;
	    	port = Addresses.QA_PORT;
	    	mcmurl = Addresses.QA_MCM;
	    	mcmport = Addresses.QA_MCM_PORT;
	    }
	    
	    else if (server==Addresses.QA2){
	    	url = Addresses.QA2_PORTAL;
	    	port = Addresses.QA2_PORT;
	    	mcmurl = Addresses.QA2_MCM;
	    	mcmport = Addresses.QA2_MCM_PORT;
	    }
	    
	    else if (server==Addresses.EC2){
	    	url = Addresses.EC2_PORTAL;
	    	port = Addresses.EC2_PORT;
	    	mcmurl = Addresses.EC2_MCM;
	    	mcmport = Addresses.EC2_MCM_PORT;
	    }
	    
	    else if (server==Addresses.PROD){
	    	url = Addresses.PROD_PORTAL;
	    	port = Addresses.PROD_PORT;
	    	mcmurl = Addresses.PROD_MCM;
	    	mcmport = Addresses.PROD_MCM_PORT;
	        
	    }
	    
	    else if (server==Addresses.CHEVRON){
	    	url = Addresses.CHEVRON_PORTAL;
	    	port = Addresses.CHEVRON_PORT;
	    	mcmurl = Addresses.CHEVRON_MCM;
	    	mcmport = Addresses.CHEVRON_MCM_PORT;
	        
	    }
	    
	    else if (server==Addresses.SCHLUMBERGER){
	    	url = Addresses.SLB_PORTAL;
	    	port = Addresses.SLB_PORT;
	    	mcmurl = Addresses.SLB_MCM;
	    	mcmport = Addresses.SLB_MCM_PORT;
	    }
	    
//	    else if (server==Addresses.DEV){
//	    	url = Addresses.PROD_PORTAL_EC2;
//	    	port = Addresses.PROD_PORT_EC2;
//	    	mcmurl = Addresses.PROD_MCM_EC2;
//	    	mcmport = Addresses.PROD_MCM_PORT_EC2;
//	        
//	    }
	    else if (server==Addresses.TEEN_QA){
	    	url = Addresses.TEEN_PORTAL_QA;
	    	port = Addresses.TEEN_PORT_QA;
	    	mcmurl = Addresses.TEEN_MCM_QA;
	    	mcmport = Addresses.TEEN_MCM_PORT_QA;
	    }
	    
	//    else if (server==Addresses.DEV){
	//  	  url = Addresses.;
	//        mcmport = Addresses.;
	//        
	//    }
	//    else if (server==Addresses.DEV){
	//  	  url = Addresses.;
	//        mcmport = Addresses.;
	    
	    Addresses[] urlandport = {url, port, mcmurl, mcmport};
	    logger.debug("PortalProxy: " +url+":"+port+"  McmProxy: "+mcmurl+":"+mcmport);
	    this.url = urlandport;
	}
	
	public Addresses[] getUrl(Addresses server){
		process(server);
		return url;
	}
	
	public Addresses[] getUrl(){
		return url;
	}
}
