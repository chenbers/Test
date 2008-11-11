package com.inthinc.pro.dao.hessian;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.inthinc.pro.dao.mock.proserver.CentralServiceCreator;
import com.inthinc.pro.dao.mock.proserver.SiloServiceCreator;
import com.inthinc.pro.model.Person;

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
        personHessianDAO.setSiloServiceCreator(new SiloServiceCreator());
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
}
