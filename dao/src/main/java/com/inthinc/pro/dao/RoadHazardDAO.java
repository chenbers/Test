package com.inthinc.pro.dao;

import java.util.List;

import com.inthinc.pro.model.BoundingBox;
import com.inthinc.pro.model.Hazard;
import com.inthinc.pro.model.LatLng;
import com.inthinc.pro.model.User;

public interface RoadHazardDAO extends GenericDAO<Hazard, Integer> {
    /**
     * Returns all Road Hazards for a user's account within the defined bounding box.
     * @param user The user defining the account which will be used for filtering.
     * @param lat1 north //TODO: jwimmer: verify that these are documented correctly
     * @param lng1 west
     * @param lat2 south
     * @param lng2 east
     * @return the list of Road Hazards in this account and bounding box.
     */
    List<Hazard> findHazardsByUserAcct(User user, Double lat1, Double lng1, Double lat2, Double lng2);

    /**
     * Returns all Road Hazards for a user's account within the defined bounding box.
     * @param user The user defining the account which will be used for filtering
     * @param box The bounding box used for filtering
     * @return the list of Road Hazards in this account and bounding box.
     */
    List<Hazard> findHazardsByUserAcct(User user, BoundingBox box);
    
    /**
     * Returns all Road Hazards in a user's account.
     * @param accountID The accountID which will be used for filtering.
     * @return the list of Road Hazards in this account.
     */
    List<Hazard> findAllInAccount(Integer accountID);
    
    /**
     * Returns all Road Hazards in a users account but restricted to a boundingbox
     * @param accountID
     * @param box
     * @return all Road Hazards meeting the accoundID and box restrictions
     */
    List<Hazard> findInAccount(Integer accountID, BoundingBox box);
    
    /**
     * Returns all Road Hazards in a user's account within a given radius
     * @param accountID the accountID which will be used for filtering.
     * @param location the LatLng representation of where the device queried from.
     * @param kilometers the given radius
     * @return the list of Road Hazards in this account within a given radius.
     */
    List<Hazard> findAllInAccountWithinDistance(Integer accountID, LatLng location, Integer kilometers);
    
    /**
     * Returns Road Hazards for a device, given: 
     * @param mcmID the mcmID 
     * @param location the LatLng representation of where the device queried from.
     * @param kilometers the given radius
     * @return
     */
    List<Hazard> findByDeviceLocationRadius(String mcmID, LatLng location, Integer kilometers);
}
