package com.inthinc.pro.backing;

import org.springframework.security.AccessDeniedException;

import com.inthinc.pro.dao.VehicleDAO;
import com.inthinc.pro.model.EntityType;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.util.MessageUtil;

public class VehicleBean extends BaseBean implements IdentifiableEntityBean {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private Integer vehicleID;
    private Vehicle vehicle;
    private VehicleDAO vehicleDAO;

    public VehicleBean(){}
    
    public VehicleBean(Vehicle vehicle){
        this.vehicleID = vehicle.getVehicleID();
        this.vehicle = vehicle;
    }
    
    public Integer getVehicleID() {
        return vehicleID;
    }

    public void setVehicleID(Integer vehicleID) {
    	if(this.vehicleID == null || !this.vehicleID.equals(vehicleID)){
	        this.vehicle = vehicleDAO.findByID(vehicleID);
	        if (vehicle == null || getGroupHierarchy().getGroup(vehicle.getGroupID()) == null)
	            throw new AccessDeniedException(MessageUtil.getMessageString("exception_accessDenied", getLocale()));
	        this.vehicleID = vehicleID;
    	}
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public VehicleDAO getVehicleDAO() {
        return vehicleDAO;
    }

    public void setVehicleDAO(VehicleDAO vehicleDAO) {
        this.vehicleDAO = vehicleDAO;
    }

    @Override
    public EntityType getEntityType() {
        return EntityType.ENTITY_VEHICLE;
    }

    @Override
    public Integer getId() {
        return getVehicleID();
    }

    @Override
    public String getName() {
        if (vehicle != null) {
            return vehicle.getName();
        }
        else {
            return null;
        }
    }

    @Override
    public void setId(Integer id) {
        setVehicleID(id);
    }

    @Override
    public Object getEntity() {
        return vehicle;
    }

    @Override
    public String getLongName() {
        if (vehicle != null) {
            return vehicle.getName() + " - " + vehicle.getFullName();
        }
        return null;
    }
    
    @Override
    public int compareTo(IdentifiableEntityBean o) {
        return o!=null?this.getLongName().compareTo(o.getLongName()):0;
    }
}
