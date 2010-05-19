package com.inthinc.pro.dao.hessian;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;

import com.inthinc.pro.dao.ZoneAlertDAO;
import com.inthinc.pro.dao.hessian.exceptions.EmptyResultSetException;
import com.inthinc.pro.dao.hessian.exceptions.RemoteServerException;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.User;
import com.inthinc.pro.model.ZoneAlert;

public class ZoneAlertHessianDAO extends GenericHessianDAO<ZoneAlert, Integer> implements ZoneAlertDAO
{
    
    private static final Logger logger = Logger.getLogger(ZoneAlertHessianDAO.class);
    
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
        // TODO: THIS IS A KLUDGE SO THIS WILL WORK UNTIL DS GETS BACK AND CAN FIX IT!!!!!!
        catch (RemoteServerException ex) {
            if (ex.getErrorCode() == 307) {
logger.error("getZoneAlertsByUserIDDeep(" + userID +") return error 307 -- applying KLUDGE!!!!");                
                User user = getMapper().convertToModelObject(getSiloService().getUser(userID), User.class);
                List<Group> groupList = getMapper().convertToModelObject(getSiloService().getGroupsByGroupIDDeep(user.getGroupID()), Group.class);
                List<ZoneAlert> zoneAlertList = getZoneAlerts(user.getPerson().getAcctID());
                List<ZoneAlert> returnZoneAlertList = new ArrayList<ZoneAlert>(); 
                for (ZoneAlert zoneAlert : zoneAlertList) {
                    if (zoneAlert.getUserID() == null) {
                        zoneAlert.setUserID(userID);
                        zoneAlert.setUsername(user.getPerson().getFullName());
                        returnZoneAlertList.add(zoneAlert);
                    }
                    else {
                        
                        User alertUser = getMapper().convertToModelObject(getSiloService().getUser(zoneAlert.getUserID()), User.class);
                        for (Group group : groupList) {
                            if (group.getGroupID().equals(alertUser.getGroupID())) {
                                if (zoneAlert.getUsername() == null) {
                                    zoneAlert.setUsername(alertUser.getPerson().getFullName());
                                }
                                returnZoneAlertList.add(zoneAlert);
                                break;
                            }
                        }
                    }
                }
                return returnZoneAlertList;
            }
            else {
                throw ex;
            }
        }
    }

    @Override
    public Integer deleteByZoneID(Integer zoneID)
    {
        return getChangedCount(getSiloService().deleteZoneAlertsByZoneID(zoneID));
    }
}
