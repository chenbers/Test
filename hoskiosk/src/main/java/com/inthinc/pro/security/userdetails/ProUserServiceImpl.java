package com.inthinc.pro.security.userdetails;



import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.GrantedAuthorityImpl;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.security.userdetails.UserDetailsService;
import org.springframework.security.userdetails.UsernameNotFoundException;

import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.dao.PersonDAO;
import com.inthinc.pro.dao.hessian.exceptions.EmptyResultSetException;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.Status;


public class ProUserServiceImpl implements UserDetailsService
{
    private static final Logger logger = Logger.getLogger(ProUserServiceImpl.class);
    
    private PersonDAO personDAO;
    private DriverDAO driverDAO;
    
    @Override
    public UserDetails loadUserByUsername(String employeeID) throws UsernameNotFoundException, DataAccessException
    {
        logger.info("ProUserServiceImpl:loadUserByUsername " + employeeID);
        
        try
        {
            Driver driver = lookup(employeeID);
            if (driver == null || driver.getStatus() == null || !driver.getStatus().equals(Status.ACTIVE))
              {
                  throw new UsernameNotFoundException("Driver could not be found");
              } 
            logger.info("driver is found");
            return new ProUser(driver, getGrantedAuthorities(driver) );
        }
        catch (EmptyResultSetException ex)
        {
            throw new UsernameNotFoundException("Username could not be found");
        }
    }

    private Driver lookup(String employeeID)
    {
        logger.debug("lookup: " + employeeID);

        // TODO:  should be looking up by employeeID (not personID)
        Integer personID = null;
        try {
            personID = Integer.valueOf(employeeID);
        }
        catch (NumberFormatException ex) {
            return null;
        }
        Person person = personDAO.findByID(personID);
        if (person == null)
            return null;
            
        Driver driver = driverDAO.findByPersonID(personID);
        if (driver == null)
            return null;
        driver.setPerson(person);
        return driver;
        
    }


    private GrantedAuthority[] getGrantedAuthorities(Driver driver){
		
        List<GrantedAuthorityImpl> grantedAuthoritiesList = new ArrayList<GrantedAuthorityImpl>();      
        grantedAuthoritiesList.add(new GrantedAuthorityImpl("ROLE_DRIVER"));
        
        GrantedAuthority[] grantedAuthorities = new GrantedAuthorityImpl[grantedAuthoritiesList.size()];
        
        return grantedAuthoritiesList.toArray(grantedAuthorities);
	}


    public PersonDAO getPersonDAO() {
        return personDAO;
    }

    public void setPersonDAO(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    public DriverDAO getDriverDAO() {
        return driverDAO;
    }

    public void setDriverDAO(DriverDAO driverDAO) {
        this.driverDAO = driverDAO;
    }

}
