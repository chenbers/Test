package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.faces.model.SelectItem;

import com.inthinc.pro.dao.AccountDAO;
import com.inthinc.pro.dao.ZoneDAO;
import com.inthinc.pro.dao.ZonePublishDAO;
import com.inthinc.pro.model.Account;
import com.inthinc.pro.model.Zone;
import com.inthinc.pro.model.zone.ZonePublish;
import com.inthinc.pro.model.zone.ZonePublisher;
import com.inthinc.pro.model.zone.option.type.ZoneVehicleType;

public class ZonePublishBean extends BaseBean {
    
    private AccountDAO accountDAO;
    private ZoneDAO zoneDAO;
    private ZonePublishDAO zonePublishDAO;
    
    private List<SelectItem> accountSelectList;
    private Integer selectedAccountID;
    private Account account;
    private String publishActionMsg;
    private Boolean zonesNeedPublish;
    private List<Zone> zoneList;
    
    private static final Integer NONE = -1;
    
    public void init() {
        accountSelectList = new ArrayList<SelectItem>();
        
        List<Account> accountList = accountDAO.getAllAcctIDs();

        accountSelectList.add(new SelectItem(NONE, "<Select>"));
        for (Account account : accountList) {
            accountSelectList.add(new SelectItem(account.getAccountID(), account.getAccountID() + " - " + account.getAcctName()));
        }
        
        sort(accountSelectList);
    }
    
    protected void sort(List<SelectItem> selectItemList) {
        Collections.sort(selectItemList, new Comparator<SelectItem>() {
            @Override
            public int compare(SelectItem o1, SelectItem o2) {
                return ((Integer)o1.getValue()).compareTo((Integer)o2.getValue());
            }
        });
    }


    public void publishAction() {
        if (account != null) {
            // reinit in case data is stale
            account = initAccount(account.getAccountID());
            publishZones(account, zoneList);
            setPublishActionMsg("Success: Zones have been published for Account: " + account.getAcctName());
        }
    }

    public Integer getSelectedAccountID() {
        return selectedAccountID;
    }

    public void setSelectedAccountID(Integer selectedAccountID) {
        this.selectedAccountID = selectedAccountID;
        setPublishActionMsg(null);
        if (selectedAccountID == NONE)
            setAccount(null);
        else setAccount(initAccount(selectedAccountID));
    }

    private Account initAccount(Integer accountID) {
        Account accnt = accountDAO.findByID(accountID);
        zoneList = zoneDAO.getZones(accnt.getAccountID());
        zonesNeedPublish = initZonesNeedPublish(accnt, zoneList);
        return accnt;
    }
    private Boolean initZonesNeedPublish(Account account, List<Zone> zoneList) {
        Date lastPublishDate = (account.getZonePublishDate() == null) ? new Date(0) : account.getZonePublishDate();

        for (Zone zone : zoneList) {
            if ((zone.getCreated() != null && zone.getCreated().after(lastPublishDate)) || 
                (zone.getModified() != null && zone.getModified().after(lastPublishDate)))
                return true;
        }
        return false;
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


    public List<SelectItem> getAccountSelectList() {
        return accountSelectList;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }


    public void setAccountSelectList(List<SelectItem> accountSelectList) {
        this.accountSelectList = accountSelectList;
    }

    
    public AccountDAO getAccountDAO() {
        return accountDAO;
    }

    public void setAccountDAO(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    public String getPublishActionMsg() {
        return publishActionMsg;
    }

    public void setPublishActionMsg(String publishActionMsg) {
        this.publishActionMsg = publishActionMsg;
    }

    public Boolean getZonesNeedPublish() {
        return zonesNeedPublish;
    }

    public void setZonesNeedPublish(Boolean zonesNeedPublish) {
        this.zonesNeedPublish = zonesNeedPublish;
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

    public List<Zone> getZoneList() {
        return zoneList;
    }

    public void setZoneList(List<Zone> zoneList) {
        this.zoneList = zoneList;
    }

}
