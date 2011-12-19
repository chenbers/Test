package com.inthinc.pro.automation.utils;

import java.net.MalformedURLException;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.inthinc.pro.automation.enums.Addresses;
import com.inthinc.pro.dao.hessian.extension.HessianTCPProxyFactory;
import com.inthinc.pro.dao.hessian.proserver.MCMService;
import com.inthinc.pro.dao.hessian.proserver.SiloService;

public class AutomationHessianFactory {

    private final static Logger logger = Logger.getLogger(AutomationHessianFactory.class);

    private SiloService portalProxy;
    private MCMService mcmProxy;

    private Addresses server;

    public void setUrls(Addresses server, Boolean portal) {
        this.server = server;
        if (portal) {
            createPortalProxy();
        } else {
            createMcmProxy();
        }
    }

    public void setUrls(String url, int port, Boolean portal) {
        server = Addresses.USER_CREATED.setUrlAndPort(url, port, url, port);
        if (portal) {
            createPortalProxy();
        } else {
            createMcmProxy();
        }
    }

    public void setUrls(String url, int port, int waysPort, Boolean portal) {
        server = Addresses.USER_CREATED.setUrlAndPort(url, port, url, port, waysPort);
        if (portal) {
            createPortalProxy();
        } else {
            createMcmProxy();
        }
    }

    private void createPortalProxy() {
        logger.debug(server.toString());
        HessianTCPProxyFactory factory = new HessianTCPProxyFactory();
        try {
            portalProxy = (SiloService) factory.create(SiloService.class, server.getPortalUrl(), server.getPortalPort());
        } catch (NumberFormatException e) {
            logger.debug(StackToString.toString(e));
        } catch (MalformedURLException e) {
            logger.debug(StackToString.toString(e));
        }
    }

    private void createMcmProxy() {
    	MasterTest.print("%s:%d", Level.INFO, server.getMCMUrl(), server.getMCMPort());
        HessianTCPProxyFactory factory = new HessianTCPProxyFactory();
        try {
            mcmProxy = (MCMService) factory.create(MCMService.class, server.getMCMUrl(), server.getMCMPort());
        } catch (NumberFormatException e) {
        	MasterTest.print(e, Level.FATAL);
        } catch (MalformedURLException e) {
        	MasterTest.print(e, Level.FATAL);
        }
    }

    public MCMService getMcmProxy() {
        if (mcmProxy == null)
            createMcmProxy();
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

    public SiloService getPortalProxy(Addresses server) {
        setUrls(server, true);
        return portalProxy;
    }

//    public String getUrl(Boolean portal) {
//        if (portal)
//            return server.getPortalUrl();
//        return server.getMCMUrl();
//    }
//
//    public Integer getPort(Boolean portal) {
//        if (portal)
//            return server.getPortalPort();
//        return server.getMCMPort();
//    }

}
