package com.inthinc.pro.automation.utils;

import java.net.MalformedURLException;

import org.apache.log4j.Logger;

import com.inthinc.pro.automation.device_emulation.MCMProxy;
import com.inthinc.pro.automation.enums.Addresses;
import com.inthinc.pro.dao.hessian.extension.HessianTCPProxyFactory;
import com.inthinc.pro.dao.hessian.proserver.SiloService;

public class CreateHessian {
	
	private final static Logger logger = Logger.getLogger(AutomationLogger.class);

	private ProcessServer process;
	private SiloService portalProxy;
	private MCMProxy mcmProxy;

	private String pUrl, mcmUrl;
	private Integer pPort, mcmPort;
	
	public CreateHessian(){
		process = new ProcessServer();
	}
	
	public void setUrls(String server, Boolean portal){
		Addresses[] address = process.getUrl(server);
		
		pUrl = address[0].getCode();
		pPort = Integer.parseInt(address[1].getCode());
		mcmUrl = address[2].getCode();
		mcmPort = Integer.parseInt(address[3].getCode());
		if (portal){ createPortalProxy();}
		else{createMcmProxy();}
	}
	
	public void setUrls(String url, Integer port, Boolean portal){
		if (portal){
			pUrl = url;
			pPort = port;
			createPortalProxy();
		}
		else{
			mcmUrl = url;
			mcmPort = port;
			createMcmProxy();
		}
	}

	private void createPortalProxy() {
		logger.info("Portal Proxy = " + pUrl +":"+pPort);
		HessianTCPProxyFactory factory = new HessianTCPProxyFactory();
		try {
			portalProxy = (SiloService)factory.create( SiloService.class, pUrl, pPort);
		} catch (NumberFormatException e) {
			logger.debug(StackToString.toString(e));
		} catch (MalformedURLException e) {
			logger.debug(StackToString.toString(e));
		}
	}
	
	private void createMcmProxy() {
		logger.info("MCM Proxy    = "+mcmUrl + ":" +mcmPort);
		HessianTCPProxyFactory factory = new HessianTCPProxyFactory();
		try {
			mcmProxy = (MCMProxy)factory.create( MCMProxy.class, mcmUrl, mcmPort);
		} catch (NumberFormatException e) {
			logger.fatal(StackToString.toString(e));
		} catch (MalformedURLException e) {
			StackToString.toString(e);
		}
	}

	public MCMProxy getMcmProxy(){
		if (mcmProxy==null)createMcmProxy();
		return mcmProxy;
	}
	public MCMProxy getMcmProxy(String server){
		setUrls(server, false);
		return mcmProxy;
	}
	
	public SiloService getPortalProxy(){
		if (portalProxy==null)getPortalProxy();
		return portalProxy;
	}
	public SiloService getPortalProxy(String server){
		setUrls(server, true);
		return portalProxy;
	}
	
	public String getUrl(Boolean portal){
		if(portal) return pUrl;
		return mcmUrl;
	}
	
	public Integer getPort(Boolean portal){
		if (portal) return pPort;
		return mcmPort;
	}

}
