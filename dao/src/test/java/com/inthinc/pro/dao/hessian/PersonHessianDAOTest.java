package com.inthinc.pro.dao.hessian;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.inthinc.pro.dao.hessian.exceptions.EmptyResultSetException;
import com.inthinc.pro.dao.mock.proserver.SiloServiceCreator;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.User;

public class PersonHessianDAOTest
{
    PersonHessianDAO personHessianDAO;

    @BeforeClass
    public static void runOnceBeforeAllTests() throws Exception
    {

    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception
    {
    }

    @Before
    public void setUp() throws Exception
    {
        personHessianDAO = new PersonHessianDAO();
        personHessianDAO.setSiloService(new SiloServiceCreator().getService());
    }

    @Test
    public void findInGroupHierarchy()
    {
        try
        {
            List<Person> people = personHessianDAO.getPeopleInGroupHierarchy(-1);

            assertTrue("expected no people to be returned", people.size() == 0);

            people = personHessianDAO.getPeopleInGroupHierarchy(101);

            assertTrue("expected to retrieve Person records", people.size() > 0);
        }
        catch (RuntimeException t)
        {
            t.printStackTrace();
            throw t;
        }
    }
    
    @Test
    public void findByEmail()
    {
        
        Person person = personHessianDAO.findByEmail("hello@yahoo.com");

        assertNull("expected no user to be returned" , person);
        
        person = personHessianDAO.findByEmail("custom101@example.com");
        
        assertNotNull("expected to retrieve a user record", person);
    }
    
   
    
}
