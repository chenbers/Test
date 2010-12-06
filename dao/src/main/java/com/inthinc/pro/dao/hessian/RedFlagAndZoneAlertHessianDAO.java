package com.inthinc.pro.dao.hessian;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.inthinc.pro.dao.RedFlagAlertDAO;
import com.inthinc.pro.dao.RedFlagAndZoneAlertsDAO;
import com.inthinc.pro.dao.ZoneAlertDAO;
import com.inthinc.pro.dao.hessian.exceptions.EmptyResultSetException;
import com.inthinc.pro.model.AlertEscalationItem;
import com.inthinc.pro.model.RedFlagAlert;
import com.inthinc.pro.model.RedFlagOrZoneAlert;
import com.inthinc.pro.model.ZoneAlert;

@SuppressWarnings("serial")
public class RedFlagAndZoneAlertHessianDAO extends GenericHessianDAO<RedFlagOrZoneAlert, Integer>implements RedFlagAndZoneAlertsDAO {

    private RedFlagAlertDAO redFlagAlertDAO;
    private ZoneAlertDAO zoneAlertDAO;
    
    @Override
    public List<AlertEscalationItem> getAlertEscalationItemsByAlert(Integer alertID) {
        return null;
    }

    @Override
    public List<RedFlagOrZoneAlert> getRedFlagAndZoneAlerts(Integer accountID) {

        List<RedFlagAlert> redFlagAlerts = redFlagAlertDAO.getRedFlagAlerts(accountID);
        List<ZoneAlert> zoneAlerts = zoneAlertDAO.getZoneAlerts(accountID);
        
        return combineAndSort(redFlagAlerts,zoneAlerts);
    }

    public void setRedFlagAlertDAO(RedFlagAlertDAO redFlagAlertDAO) {
        this.redFlagAlertDAO = redFlagAlertDAO;
    }

    public void setZoneAlertDAO(ZoneAlertDAO zoneAlertDAO) {
        this.zoneAlertDAO = zoneAlertDAO;
    }

    @Override
    public List<RedFlagOrZoneAlert> getRedFlagAndZoneAlertsByUserID(Integer userID) {

        List<RedFlagAlert> redFlagAlerts = redFlagAlertDAO.getRedFlagAlertsByUserID(userID);
        List<ZoneAlert> zoneAlerts = zoneAlertDAO.getZoneAlertsByUserID(userID);
        
        return combineAndSort(redFlagAlerts,zoneAlerts);
    }

    @Override
    public List<RedFlagOrZoneAlert> getRedFlagAndZoneAlertsByUserIDDeep(Integer userID) {

        List<RedFlagAlert> redFlagAlerts = redFlagAlertDAO.getRedFlagAlertsByUserIDDeep(userID);
        List<ZoneAlert> zoneAlerts = zoneAlertDAO.getZoneAlertsByUserIDDeep(userID);
                
        return combineAndSort(redFlagAlerts,zoneAlerts);
    }

    @Override
    public List<ZoneAlert> getZoneAlerts(Integer accountID)
    {
        try
        {
            return getMapper().convertToModelObject(getSiloService().getZoneAlertsByAcctID(accountID), ZoneAlert.class);
        }
        catch (EmptyResultSetException e)
        {
            return Collections.emptyList();
        }
        
    }

    @Override
    public List<ZoneAlert> getZoneAlertsByUserID(Integer userID) {
        try {
            return getMapper().convertToModelObject(getSiloService().getZoneAlertsByUserID(userID), ZoneAlert.class);
        } catch (EmptyResultSetException e) {
            return Collections.emptyList();
        }
    }

    @Override
    public List<ZoneAlert> getZoneAlertsByUserIDDeep(Integer userID) {
        try {
            return getMapper().convertToModelObject(getSiloService().getZoneAlertsByUserIDDeep(userID), ZoneAlert.class);
        } catch (EmptyResultSetException e) {
            return Collections.emptyList();
        }
    }

    @Override
    public Integer deleteByZoneID(Integer zoneID)
    {
        return getChangedCount(getSiloService().deleteZoneAlertsByZoneID(zoneID));
    }

    
    @Override
    public RedFlagOrZoneAlert findByID(Integer ID)
    {
        RedFlagAlert redFlagAlert = redFlagAlertDAO.findByID(ID);
        if (redFlagAlert == null)
            return zoneAlertDAO.findByID(ID);
        
        return redFlagAlert;
    }

    private List<RedFlagOrZoneAlert> combineAndSort(List<RedFlagAlert> redFlagAlerts,List<ZoneAlert> zoneAlerts){

        List<RedFlagOrZoneAlert> redFlagAndZoneAlerts = new ArrayList<RedFlagOrZoneAlert>(redFlagAlerts);
        redFlagAndZoneAlerts.addAll(zoneAlerts);
        
        Collections.sort(redFlagAndZoneAlerts);
        
        return redFlagAndZoneAlerts;

    }

    @Override
    public Integer create(Integer id, RedFlagOrZoneAlert entity) {
        
        if(entity.getType() != null){
            
            return redFlagAlertDAO.create(id, (RedFlagAlert)entity);
        }
        else{
            
            return zoneAlertDAO.create(id, (ZoneAlert)entity);
        }
    }


 
    @Override
    public Integer update(RedFlagOrZoneAlert entity) {
        if(entity instanceof RedFlagAlert){
            
            return redFlagAlertDAO.update((RedFlagAlert)entity);
        }
        if(entity instanceof ZoneAlert){
            
            return zoneAlertDAO.update((ZoneAlert)entity);
        }
        return null;
    }
    public Integer deleteByEntity(RedFlagOrZoneAlert redFlagOrZoneAlert){
        
        if(redFlagOrZoneAlert instanceof RedFlagAlert){
            
            return redFlagAlertDAO.deleteByID(((RedFlagAlert)redFlagOrZoneAlert).getAlertID());
        }
        if(redFlagOrZoneAlert instanceof ZoneAlert){
            
            return zoneAlertDAO.deleteByID(((ZoneAlert)redFlagOrZoneAlert).getAlertID());
        }
        return null;
        
    }
    
}
