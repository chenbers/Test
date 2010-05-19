package com.inthinc.pro.dao.hessian;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;

import com.inthinc.pro.dao.RedFlagAlertDAO;
import com.inthinc.pro.dao.hessian.exceptions.EmptyResultSetException;
import com.inthinc.pro.dao.hessian.exceptions.RemoteServerException;
import com.inthinc.pro.dao.hessian.mapper.RedFlagsAlertMapper;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.RedFlagAlert;
import com.inthinc.pro.model.User;


public class RedFlagAlertHessianDAO extends GenericHessianDAO<RedFlagAlert, Integer> implements RedFlagAlertDAO
{
    private static final Logger logger = Logger.getLogger(RedFlagAlertHessianDAO.class);
    public RedFlagAlertHessianDAO ()
    {
        super();
        super.setMapper(new RedFlagsAlertMapper());
    }
    @Override
    public List<RedFlagAlert> getRedFlagAlerts(Integer accountID)
    {
        try
        {
            return getMapper().convertToModelObject(getSiloService().getRedFlagAlertsByAcctID(accountID), RedFlagAlert.class);
        }
        catch (EmptyResultSetException e)
        {
            return Collections.emptyList();
        }
    }

    @Override
    public List<RedFlagAlert> getRedFlagAlertsByUserID(Integer userID) {
        try {
            return getMapper().convertToModelObject(getSiloService().getRedFlagAlertsByUserID(userID), RedFlagAlert.class);
        } catch (EmptyResultSetException e) {
            return Collections.emptyList();
        }
    }

    @Override
    public List<RedFlagAlert> getRedFlagAlertsByUserIDDeep(Integer userID) {
        try {
            return getMapper().convertToModelObject(getSiloService().getRedFlagAlertsByUserIDDeep(userID), RedFlagAlert.class);
        } catch (EmptyResultSetException e) {
            return Collections.emptyList();
        }
        // TODO: THIS IS A KLUDGE SO THIS WILL WORK UNTIL DS GETS BACK AND CAN FIX IT!!!!!!
        catch (RemoteServerException ex) {
            if (ex.getErrorCode() == 307) {
logger.error("getRedFlagAlertsByUserIDDeep(" + userID +") return error 307 -- applying KLUDGE!!!!");                
                User user = getMapper().convertToModelObject(getSiloService().getUser(userID), User.class);
                List<Group> groupList = getMapper().convertToModelObject(getSiloService().getGroupsByGroupIDDeep(user.getGroupID()), Group.class);
                List<RedFlagAlert> alertList = getRedFlagAlerts(user.getPerson().getAcctID());
                List<RedFlagAlert> returnAlertList = new ArrayList<RedFlagAlert>(); 
                for (RedFlagAlert alert : alertList) {
                    if (alert.getUserID() == null) {
                        alert.setUserID(userID);
                        alert.setUsername(user.getPerson().getFullName());
                        returnAlertList.add(alert);
                    }
                    else {
                        
                        User alertUser = getMapper().convertToModelObject(getSiloService().getUser(alert.getUserID()), User.class);
                        for (Group group : groupList) {
                            if (group.getGroupID().equals(alertUser.getGroupID())) {
                                if (alert.getUsername() == null) {
                                    alert.setUsername(alertUser.getPerson().getFullName());
                                }
                                returnAlertList.add(alert);
                                break;
                            }
                        }
                    }
                }
                return returnAlertList;
            }
            else {
                throw ex;
            }
        }
        
    }
}
