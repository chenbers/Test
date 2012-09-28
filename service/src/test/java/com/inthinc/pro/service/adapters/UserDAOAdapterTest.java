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

import com.inthinc.pro.dao.UserDAO;
import com.inthinc.pro.model.User;

/**
 * Test for the UserDAOAdapter class
 * 
 * @author dcueva
 */
public class UserDAOAdapterTest {

	private final Integer GROUP_ID = new Integer(1);
	private final Integer PERSON_ID = new Integer(2);
	private final Integer USER_ID = new Integer(3);
	private final User user = new User();
	private final String USER_NAME = "USER_NAME";
	private final String PASSWORD = "PASSWORD";

	@Mocked(methods = {"getGroupID"})	
	private UserDAOAdapter adapterSUT; 
	
	@Mocked private UserDAO userDAOMock;	
    
	@Before
	public void beforeMethod() {
		adapterSUT.setUserDAO(userDAOMock);
	}
	
	@After
	public void afterMethod(){
		tearDownMocks();
	}
	
	@Test
	public void testGetAll(){
		final List<User> userList = new ArrayList<User>();
		userList.add(user);
		
		new Expectations(){{
			adapterSUT.getGroupID(); returns(GROUP_ID);
			userDAOMock.getUsersInGroupHierarchy(GROUP_ID); returns(userList);
		}};
		assertEquals(adapterSUT.getAll(), userList);
	}	
	
	@Test
	public void testGetDAO(){
		assertEquals(adapterSUT.getDAO(), userDAOMock);
	}		

	@Test
	public void testGetResourceCreationID(){
		user.setPersonID(PERSON_ID);
		
		assertEquals(adapterSUT.getResourceCreationID(user), PERSON_ID);
	}	
	
	@Test
	public void testGetResourceID(){
		user.setUserID(USER_ID);
		
		assertEquals(adapterSUT.getResourceID(user), USER_ID);
	}

	@Test
	public void testFindByUserName(){
		new Expectations(){{
			userDAOMock.findByUserName(USER_NAME); returns(user);
		}};
		assertEquals(adapterSUT.findByUserName(USER_NAME), user);
	}
	
	@Test
	public void testLogin(){
		new Expectations(){{
			userDAOMock.findByUserName(USER_NAME); returns(user);
		}};
		assertEquals(adapterSUT.login(USER_NAME, PASSWORD), user);
	}	
}
