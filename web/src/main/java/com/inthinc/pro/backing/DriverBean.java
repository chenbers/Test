package com.inthinc.pro.backing;

import org.springframework.security.AccessDeniedException;

import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.EntityType;
import com.inthinc.pro.util.MessageUtil;

public class DriverBean extends BaseBean implements IdentifiableEntityBean {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private Integer driverID;
    private Driver driver;
    private DriverDAO driverDAO;
    
    public DriverBean(){}
    
    public DriverBean(Driver driver){
        this.driverID = driver.getDriverID();
        this.driver = driver;
    }

    public Integer getDriverID() {
        if(driver != null && driver.getDriverID() != null)
            return driver.getDriverID();
        return driverID;
    }

    public void setDriverID(Integer driverID) {
    	if((this.driverID == null) || !this.driverID.equals(driverID)){
	        driver = driverDAO.findByID(driverID);
	        if (driver == null || getGroupHierarchy().getGroup(driver.getGroupID()) == null)
	            throw new AccessDeniedException(MessageUtil.getMessageString("exception_accessDenied", getLocale()));
	        this.driverID = driverID;
    	}
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public DriverDAO getDriverDAO() {
        return driverDAO;
    }

    public void setDriverDAO(DriverDAO driverDAO) {
        this.driverDAO = driverDAO;
    }

    @Override
    public EntityType getEntityType() {
        return EntityType.ENTITY_DRIVER;
    }

    @Override
    public Integer getId() {
        return getDriverID();
    }

    @Override
    public String getName() {
        if (driver != null) {
            return driver.getPerson().getFullName();
        }
        else
            return null;
    }

    @Override
    public void setId(Integer id) {
        setDriverID(id);
    }

    @Override
    public Object getEntity() {
        return driver;
    }

    @Override
    public String getLongName() {
        if (driver != null) {
            return driver.getPerson().getFullName();
        }
        else {
            return null;
        }
    }
    
    @Override
    public int compareTo(IdentifiableEntityBean o) {
        return o!=null?this.getLongName().compareTo(o.getLongName()):0;
    }
}
