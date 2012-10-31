package com.inthinc.pro.service.adapters;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.inthinc.pro.dao.GenericDAO;
import com.inthinc.pro.dao.RoadHazardDAO;
import com.inthinc.pro.dao.UserDAO;
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
    private RoadHazardDAO hazardDAO;

    @Override
    public List<Hazard> getAll() {
        // TODO Auto-generated method stub
        return hazardDAO.findAllInAccount(getAccountID());
    }

    @Override
    protected GenericDAO<Hazard, Integer> getDAO() {
        return hazardDAO;
    }

    @Override
    protected Integer getResourceID(Hazard resource) {
        return resource.getHazardID();
    }

	// Specialized methods ----------------------------------------------------
    public List<Hazard> findHazardsByUserAcct(User user, Double lat1, Double lng1, Double lat2, Double lng2) {
    	return hazardDAO.findHazardsByUserAcct(user, lat1, lng1, lat2, lng2);
    }	
    
    public List<Hazard> findHazardsByUserAcct(User user, BoundingBox box){
        return hazardDAO.findHazardsByUserAcct(user, box);
    }

    public List<Hazard> findAllInAccount(Integer accountID){
        return hazardDAO.findAllInAccount(accountID);
    }
    
    public List<Hazard> findAllInAccountRadius(Integer accountID, LatLng location, Integer distanceInMiles){
        return hazardDAO.findAllInAccountWithinDistance(accountID, location, distanceInMiles);
    }
	// Getters and setters -----------------------------------------------------

    public RoadHazardDAO getAdminRoadHazardJDBCDAO() {
        return hazardDAO;
    }

    public void setAdminRoadHazardJDBCDAO(RoadHazardDAO adminRoadHazardJDBCDAO) {
        this.hazardDAO = adminRoadHazardJDBCDAO;
    }

}
