/**
 * 
 */
package com.inthinc.pro.dao.jdbc;

import static org.junit.Assert.assertEquals;

import java.util.List;

import mockit.Deencapsulation;
import mockit.Mocked;
import mockit.NonStrictExpectations;

import org.junit.Test;

import com.inthinc.pro.dao.GroupDAO;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.GroupHierarchy;
import com.inthinc.pro.model.Person;

/**
 * @author anelson
 *
 */
public class AlertMessageJDBCDAOTest {
    
    // Mocks
    @Mocked Driver mockDriver;
    @Mocked Person mockPerson;
    @Mocked GroupDAO mockGroupDAO;
    @Mocked List<Group> mockList;
    @Mocked GroupHierarchy mockGroupHierarchy;
    
    @Test
    public final void testGetDriverOrgStructureWithValidDriver() {
        
        // Methods that may be called in the execution of the method we're testing
        new NonStrictExpectations() {{
            mockDriver.getPerson(); result = mockPerson;
            mockPerson.getAcctID(); result = 1;
            mockGroupDAO.getGroupsByAcctID(1); result = mockList;
            new GroupHierarchy();
            mockGroupHierarchy.getFullGroupName(anyInt, " > "); result = "Group Name";
        }};

        // Instantiate our parent class (necessary before instantiating nested class)
        AlertMessageJDBCDAO alertMessageJDBCDAO = new AlertMessageJDBCDAO();
        
        // Set the groupDAO to a mocked version
        alertMessageJDBCDAO.setGroupDAO(mockGroupDAO);
        
        // Instantiate the class we're testing
        AlertMessageJDBCDAO.ParameterList parameterList = alertMessageJDBCDAO.new ParameterList();
        
        // Run the method
        String result = Deencapsulation.invoke(parameterList, "getDriverOrgStructure", mockDriver);
        
        // Test the result
        assertEquals(result, "Group Name");
    }
    
    @Test
    public final void testGetDriverOrgStructureWithNullPerson() {
        
        new NonStrictExpectations() {{
             mockDriver.getPerson(); result = null;
        }};

        // Instantiate our parent class (necessary before instantiating nested class)
        AlertMessageJDBCDAO alertMessageJDBCDAO = new AlertMessageJDBCDAO();
        
        // Set the groupDAO to a mocked version
        alertMessageJDBCDAO.setGroupDAO(mockGroupDAO);
        
        // Instantiate the class we're testing
        AlertMessageJDBCDAO.ParameterList parameterList = alertMessageJDBCDAO.new ParameterList();
        
        // Run the method
        String result = Deencapsulation.invoke(parameterList, "getDriverOrgStructure", mockDriver);
        
        // Test the result
        assertEquals(result, "");
        
    }
    
}
