package com.inthinc.device.hessian.tcp;

import java.net.MalformedURLException;

import android.util.Log;

import com.inthinc.device.emulation.interfaces.MCMService;
import com.inthinc.device.emulation.interfaces.SiloService;
import com.inthinc.pro.automation.enums.Addresses;

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
    
    public void setUrls(String portalUrl, int portalPort, String mcmUrl, int mcmPort){
    	server = new Server(portalUrl, portalPort, mcmUrl, mcmPort);
    	createPortalProxy();
    	createMcmProxy();
    }

    public void setUrls(Addresses server, Boolean portal) {
        this.server = new Server(server.getPortalUrl(), server.getPortalPort(), server.getMCMUrl(), server.getMCMPort());
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

    private void createPortalProxy() {
    	Log.d("%s", server);
        HessianTCPProxyFactory factory = new HessianTCPProxyFactory();
        try {
            portalProxy = (SiloService) factory.create(SiloService.class, server.portalUrl, server.portalPort);
        } catch (NumberFormatException e) {
            Log.wtf("%s", e);
        } catch (MalformedURLException e) {
        	Log.wtf("%s", e);
        }
    }

    private void createMcmProxy() {
    	Log.d("%s", server);
        HessianTCPProxyFactory factory = new HessianTCPProxyFactory();
        try {
            mcmProxy = (MCMService) factory.create(MCMService.class, server.mcmUrl, server.mcmPort);
        } catch (NumberFormatException e) {
            Log.wtf("%s", e);
        } catch (MalformedURLException e) {
            Log.wtf("%s", e);
        }
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

    public MCMService getMcmProxy(Addresses server) {
        setUrls(server, false);
        return mcmProxy;
    }

    public SiloService getPortalProxy() {
        if (portalProxy == null)
            getPortalProxy();
        return portalProxy;
    }
    
    public SiloService getPortalProxy(String server, int port){
    	setUrls(server, port, true);
    	return portalProxy;
    }

    public SiloService getPortalProxy(Addresses server) {
        setUrls(server, true);
        return portalProxy;
    }

    public Server getServer(){
    	return server;
    }
}
