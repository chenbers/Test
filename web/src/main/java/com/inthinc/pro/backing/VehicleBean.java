package com.inthinc.pro.backing;

import org.springframework.security.AccessDeniedException;

import com.inthinc.pro.dao.VehicleDAO;
import com.inthinc.pro.model.EntityType;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.util.MessageUtil;



public class VehicleBean extends BaseBean implements IdentifiableEntityBean
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private Integer vehicleID;
    private Vehicle vehicle;
    private VehicleDAO vehicleDAO;

    public Integer getVehicleID()
    {
        return vehicleID;
    }

    public void setVehicleID(Integer vehicleID)
    {
        this.vehicle = vehicleDAO.findByID(vehicleID);
        if (vehicle == null || getGroupHierarchy().getGroup(vehicle.getGroupID()) == null)
            throw new AccessDeniedException(MessageUtil.getMessageString("exception_accessDenied", getUser().getLocale()));
        this.vehicleID = vehicleID;
    }

    public Vehicle getVehicle()
    {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle)
    {
        this.vehicle = vehicle;
    }

    public VehicleDAO getVehicleDAO()
    {
        return vehicleDAO;
    }

    public void setVehicleDAO(VehicleDAO vehicleDAO)
    {
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
        if(vehicle != null){
            return vehicle.getFullName();
        }else{
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
        if(vehicle != null){
            return vehicle.getFullName();
        }
        return null;
    }


}
