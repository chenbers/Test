package com.inthinc.pro.service.adapters;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.inthinc.pro.dao.GenericDAO;
import com.inthinc.pro.dao.RoadHazardDAO;
import com.inthinc.pro.model.BoundingBox;
import com.inthinc.pro.model.Hazard;
import com.inthinc.pro.model.LatLng;
import com.inthinc.pro.model.User;

/**
 * Adapter for the Hazard resources.
 */
@Component
public class HazardDAOAdapter extends BaseDAOAdapter<Hazard> {

    @Autowired
    private RoadHazardDAO roadHazardDAO;

    @Override
    public List<Hazard> getAll() {
        return roadHazardDAO.findAllInAccount(getAccountID());
    }

    @Override
    protected GenericDAO<Hazard, Integer> getDAO() {
        return roadHazardDAO;
    }

    @Override
    protected Integer getResourceID(Hazard resource) {
        return resource.getHazardID();
    }

    // Specialized methods ----------------------------------------------------
    public List<Hazard> findHazardsByUserAcct(User user, Double lat1, Double lng1, Double lat2, Double lng2) {
    	return roadHazardDAO.findHazardsByUserAcct(user, lat1, lng1, lat2, lng2);
    }	
    
    public List<Hazard> findHazardsByUserAcct(User user, BoundingBox box){
        return roadHazardDAO.findHazardsByUserAcct(user, box);
    }

    public List<Hazard> findAllInAccount(Integer accountID){
        return roadHazardDAO.findAllInAccount(accountID);
    }
    
    public List<Hazard> findAllInAccountRadius(Integer accountID, LatLng location, Integer distanceInMiles){
        return roadHazardDAO.findAllInAccountWithinDistance(accountID, location, distanceInMiles);
    }
    // Getters and setters -----------------------------------------------------

    public RoadHazardDAO getHazardDAO() {
        return roadHazardDAO;
    }

    public void setHazardDAO(RoadHazardDAO hazardDAO) {
        this.roadHazardDAO = hazardDAO;
    }

}
