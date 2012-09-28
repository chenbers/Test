package com.inthinc.device.hessian.tcp;

import java.net.MalformedURLException;

import com.inthinc.device.emulation.interfaces.HessianService;
import com.inthinc.device.emulation.interfaces.MCMService;
import com.inthinc.device.emulation.interfaces.SiloService;
import com.inthinc.pro.automation.enums.AutoSilos;
import com.inthinc.pro.automation.logging.Log;
import com.inthinc.pro.automation.utils.AutoServers;

public class AutomationHessianFactory {


    private SiloService portalProxy;
    private MCMService mcmProxy;

    private Server server;
    
    private class Server{
    	private String portalUrl;
    	private String mcmUrl;
    	private int portalPort;
    	private int mcmPort;
    	
    	private Server(String portalUrl, int portalPort, String mcmUrl, int mcmPort){
    		this.portalUrl = portalUrl;
    		this.portalPort = portalPort;
    		this.mcmUrl = mcmUrl;
    		this.mcmPort = mcmPort;
    	}
    	
    	@Override
    	public String toString(){
    		return String.format("\tPortal URI: %s:%d\nMcm URI: %s:%d",portalUrl, portalPort, mcmUrl, mcmPort);
    	}
    }
    
    public AutomationHessianFactory(){
    	setUrls(new AutoServers());
    	
    }
    
    private void setUrls(AutoServers server) {
    	this.server = new Server(server.getPortalUrl(), server.getPortalPort(), server.getMcmUrl(), server.getMcmPort());
	}

	public AutomationHessianFactory(AutoServers server){
    	setUrls(server);
    }
    
    public AutomationHessianFactory(AutoSilos silo){
    	AutoServers server = new AutoServers();
    	server.setBySilo(silo);
    	setUrls(server);
    }
    
    public void setUrls(String portalUrl, int portalPort, String mcmUrl, int mcmPort){
    	server = new Server(portalUrl, portalPort, mcmUrl, mcmPort);
    	createPortalProxy();
    	createMcmProxy();
    }

    public void setUrls(AutoServers server, Boolean portal) {
        setUrls(server);
        if (portal) {
            createPortalProxy();
        } else {
            createMcmProxy();
        }
    }

    public void setUrls(String url, int port, Boolean portal) {
        server = new Server(url, port, url, port);
        if (portal) {
            createPortalProxy();
        } else {
            createMcmProxy();
        }
    }
    
    public HessianService createProxy(Class<?> proxy, String url, int port){
        Log.debug("createProxy for %s, %s", proxy.getClass().getSimpleName(), server);
        HessianTCPProxyFactory factory = new HessianTCPProxyFactory();
        try {
            return (HessianService) factory.create(proxy, url, port); 
        } catch (NumberFormatException e) {
            Log.error("%s", e);
        } catch (MalformedURLException e) {
            Log.error("%s", e);
        }
        return null;
    }

    private void createPortalProxy() {
        portalProxy = (SiloService) createProxy(SiloService.class, server.portalUrl, server.portalPort);
    }

    private void createMcmProxy() {
        mcmProxy = (MCMService) createProxy(MCMService.class, server.mcmUrl, server.mcmPort);
    }
    
    public MCMService getMcmProxy() {
        if (mcmProxy == null)
            createMcmProxy();
        return mcmProxy;
    }
    
    public MCMService getMcmProxy(String server, int port){
    	setUrls(server, port, false);
    	return mcmProxy;
    }

    public MCMService getMcmProxy(AutoSilos server) {
        setUrls(server, false);
        return mcmProxy;
    }
    
    public MCMService getMcmProxy(AutoServers server){
    	setUrls(server, false);
    	return mcmProxy;
    }

    public SiloService getPortalProxy() {
        return portalProxy == null ? getPortalProxy(AutoSilos.QA) : portalProxy;
    }
    
    public SiloService getPortalProxy(String server, int port){
    	setUrls(server, port, true);
    	return portalProxy;
    }

    public SiloService getPortalProxy(AutoSilos server) {
        setUrls(server, true);
        return portalProxy;
    }
    
    public MCMService getPortalProxy(AutoServers server){
    	setUrls(server, true);
    	return mcmProxy;
    }

    private void setUrls(AutoSilos silo, boolean portal) {
    	AutoServers server = new AutoServers();
    	server.setBySilo(silo);
    	setUrls(server, portal);
	}

	public Server getServer(){
    	return server;
    }
}
