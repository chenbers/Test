package com.inthinc.pro.dao.hessian;

import java.util.List;
import java.util.Map;

import com.inthinc.pro.ProDAOException;
import com.inthinc.pro.dao.SiloServiceDAO;
import com.inthinc.pro.dao.hessian.exceptions.EmptyResultSetException;
import com.inthinc.pro.dao.hessian.proserver.SiloService;

public class SiloServiceHessianDAO implements SiloServiceDAO {

    private SiloService siloService;
    
    
    public SiloService getSiloService() {
        return siloService;
    }

    public void setSiloService(SiloService siloService) {
        this.siloService = siloService;
    }

    @Override
    public Map<String, Object> getID(String name, String value) {
        try {
            return siloService.getID(name, value);
        }
        catch (EmptyResultSetException ex) {
            return null;
        }
    }

    @Override
    public Map<String, Object> getNextSilo() {
        try {
            return siloService.getNextSilo();
        }
        catch (EmptyResultSetException ex) {
            return null;
        }
    }

    @Override
    public List<Map<String, Object>> getTrips(Integer id, Integer reqType, Long startDate, Long endDate) throws ProDAOException {
        try {
            return siloService.getTrips(id, reqType, startDate, endDate);
        }
        catch (EmptyResultSetException ex) {
            return null;
        }
    }

    @Override
    public Map<String, Object> getLastTrip(Integer id, Integer reqType) throws ProDAOException {
        try {
            return siloService.getLastTrip(id, reqType);
        }
        catch (EmptyResultSetException ex) {
            return null;
        }
    }

    @Override
    public Map<String, Object> getLastLoc(Integer id, Integer reqType) {
        try {
            return siloService.getLastLoc(id, reqType);
        }
        catch (EmptyResultSetException ex) {
            return null;
        }
    }

    @Override
    public List<Map<String, Object>> getGroupsByGroupIDDeep(Integer groupID) {
        try {
            return siloService.getGroupsByGroupIDDeep(groupID);
        }
        catch (EmptyResultSetException ex) {
            return null;
        }
    }

    @Override
    public Map<String, Object> updateFwdCmd(Integer fwdID, Integer status) {
        try {
            return siloService.updateFwdCmd(fwdID, status);
        }
        catch (EmptyResultSetException ex) {
            return null;
        }
    }

}
