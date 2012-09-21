package com.inthinc.pro.scheduler.quartz;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.inthinc.pro.dao.AccountDAO;
import com.inthinc.pro.dao.ZoneDAO;
import com.inthinc.pro.dao.ZonePublishDAO;
//import com.inthinc.pro.dao.HazardDAO;
import com.inthinc.pro.model.Account;
import com.inthinc.pro.model.Status;
import com.inthinc.pro.model.Zone;
import com.inthinc.pro.model.zone.ZonePublish;
import com.inthinc.pro.model.zone.ZonePublisher;
import com.inthinc.pro.model.zone.option.type.ZoneVehicleType;


public class HazardExpireJob extends QuartzJobBean {
    private static final Logger logger = Logger.getLogger(ZonePublishJob.class);

    private AccountDAO accountDAO;
    private ZoneDAO zoneDAO;
    private ZonePublishDAO zonePublishDAO;
//    private HazardDAO hazardDAO;


    @Override
    protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
//        logger.setLevel(Level.DEBUG);
        logger.debug("START PROCESSING ZONE PUBLISH");
        processExpireHazards();
        logger.debug("END PROCESSING ZONE PUBLISH");

    }
    
    

    private void processExpireHazards() {
        List<Account> accounts = accountDAO.getAllAcctIDs();
        logger.debug("Account Count: " + accounts.size());

        for (Account account : accounts) {
            Account a = accountDAO.findByID(account.getAccountID());
            if (a != null && a.getStatus() != null && !a.getStatus().equals(Status.DELETED)) {
                logger.debug("Account: " + a.getAcctName());
                List<Zone> zoneList = zoneDAO.getZones(account.getAccountID());
                if (zonesNeedPublish(a)) {
                    logger.debug("Publishing zones -- numZones: " + zoneList.size());
                    publishZones(a, zoneList);
                }
                else {
                    logger.debug("NOT Publishing zones -- numZones: " + zoneList.size());
                }
            }
        }
    }
    
    private boolean zonesNeedPublish(Account account) {
		return zonePublishDAO.zonesNeedPublish(account.getAccountID());
	}

    private void publishZones(Account account, List<Zone> zoneList) {
        ZonePublisher zonePublisher = new ZonePublisher();

        for (ZoneVehicleType zoneVehicleType : ZoneVehicleType.values()) {
            
            ZonePublish zonePublish = new ZonePublish();
            zonePublish.setAcctID(account.getAccountID());
            zonePublish.setZoneVehicleType(zoneVehicleType);
            zonePublish.setPublishZoneData(zonePublisher.publish(zoneList,  zoneVehicleType));
            zonePublishDAO.publishZone(zonePublish);
        }
        zoneDAO.publishZones(account.getAccountID());
    }

    public AccountDAO getAccountDAO() {
        return accountDAO;
    }

    public void setAccountDAO(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    public ZoneDAO getZoneDAO() {
        return zoneDAO;
    }

    public void setZoneDAO(ZoneDAO zoneDAO) {
        this.zoneDAO = zoneDAO;
    }

    public ZonePublishDAO getZonePublishDAO() {
        return zonePublishDAO;
    }

    public void setZonePublishDAO(ZonePublishDAO zonePublishDAO) {
        this.zonePublishDAO = zonePublishDAO;
    }


}
