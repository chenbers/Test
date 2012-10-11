package com.inthinc.pro.service.adapters;

import static mockit.Mockit.tearDownMocks;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import mockit.Expectations;
import mockit.Mocked;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.inthinc.pro.dao.PersonDAO;
import com.inthinc.pro.model.Person;

/**
 * Test for the PersonDAOAdapter class
 * 
 * @author dcueva
 */
public class PersonDAOAdapterTest {

	private final Integer GROUP_ID = new Integer(1);
	private final Integer PERSON_ID = new Integer(2);
	private final Integer ACCOUNT_ID = new Integer(3);
	private final Person person = new Person();
	
	@Mocked(methods = {"getGroupID"})
	private PersonDAOAdapter adapterSUT; 
	
	@Mocked private PersonDAO personDAOMock;	
    
	@Before
	public void beforeMethod() {
		adapterSUT.setPersonDAO(personDAOMock);
	}
	
	@After
	public void afterMethod(){
		tearDownMocks();
	}
	
	@Test
	public void testGetAll(){
		final List<Person> personList = new ArrayList<Person>();
		personList.add(person);
		
		new Expectations(){{
			adapterSUT.getGroupID(); returns(GROUP_ID);
			personDAOMock.getPeopleInGroupHierarchy(GROUP_ID); returns(personList);
		}};
		assertEquals(adapterSUT.getAll(), personList);
	}	
	
	@Test
	public void testGetDAO(){
		assertEquals(adapterSUT.getDAO(), personDAOMock);
	}		

	@Test
	public void testGetResourceID(){
		person.setPersonID(PERSON_ID);
		
		assertEquals(adapterSUT.getResourceID(person), PERSON_ID);
	}

	@Test
	public void testCreate(){
		new Expectations(){{
			personDAOMock.create(ACCOUNT_ID, person); returns(PERSON_ID);
		}};
		assertEquals(adapterSUT.create(ACCOUNT_ID, person), PERSON_ID);
		
		
	}
	
	
}
