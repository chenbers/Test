/**
 * 
 */
package com.inthinc.pro.service.adapters;

import static mockit.Mockit.tearDownMocks;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import mockit.Expectations;
import mockit.Mocked;
import mockit.NonStrictExpectations;

import org.junit.Before;
import org.junit.Test;

import com.inthinc.pro.dao.GenericDAO;
import com.inthinc.pro.model.Account;
import com.inthinc.pro.model.BaseEntity;

/**
 * Unit Test for the BaseDAOAdapter class. </br>
 * Tests all the non-abstract public methods. </br>
 * Users partial mocking of the abstract class. 
 * This way we do not need to instantiate any of the child classes,
 * nor the creation of an anonymous class. 
 * 
 * @author dcueva
 *
 */
public class BaseDAOAdapterTest {
	
	// Partial mock: the methods in the list are not mocked and will be tested 
	@Mocked(methods = {"create", "update", "delete", "findByID"}, inverse = true)
	private BaseDAOAdapter<BaseEntity> adapterSUT;
	
	@Mocked
	private GenericDAO<BaseEntity, Integer> daoMock;
	
	private final BaseEntity resource = new Account(); 
	
	private final Integer RESOURCE_ID = new Integer(1);
	private final Integer ONE_ROW_CHANGED = new Integer(1);
	private final Integer ZERO_ROWS_CHANGED = new Integer(0);
	
	
	@Before
	public void beforeMethod(){
		tearDownMocks();

		new NonStrictExpectations() {{
				// Whenever we ask for the DAO, the adapter returns daoMock
				adapterSUT.getDAO(); returns(daoMock); 
		}};
	}
	
	/**
	 * Tests the default create method.
	 */
	@Test
	public void testCreate(){
		
		// We expect these calls to be made in this strict order
		new Expectations() {{
				adapterSUT.getResourceCreationID(resource); returns(RESOURCE_ID);
				daoMock.create(RESOURCE_ID, resource); returns(RESOURCE_ID);
		}};
		
		// Run the test
		assertEquals(adapterSUT.create(resource), RESOURCE_ID);
	}

	/**
	 * Tests the default update method, when update succeeds.
	 */
	@Test
	public void testUpdateSuccess(){
		
		new Expectations() {{
			daoMock.update(resource); returns(ONE_ROW_CHANGED);
			adapterSUT.getResourceID(resource); returns(RESOURCE_ID);
			daoMock.findByID(RESOURCE_ID); returns(resource);
		}};
		
		assertNotNull(adapterSUT.update(resource));
	}

	/**
	 * Tests the default update method, when update fails.
	 */
	@Test
	public void testUpdateFailure(){	
		new Expectations() {{
			daoMock.update(resource); returns(ZERO_ROWS_CHANGED);
		}};
		
		assertNull(adapterSUT.update(resource));		
	}
	
	/**
	 * Tests the default delete method.
	 */
	@Test
	public void testDelete(){	
		new Expectations() {{
			daoMock.deleteByID(RESOURCE_ID); returns(ONE_ROW_CHANGED);
		}};
		
		assertEquals(adapterSUT.delete(RESOURCE_ID), ONE_ROW_CHANGED);		
	}	
	
	/**
	 * Tests the default delete method.
	 */
	@Test
	public void testFindByID(){	
		new Expectations() {{
			daoMock.findByID(RESOURCE_ID); returns(resource);
		}};
		
		assertEquals(adapterSUT.findByID(RESOURCE_ID), resource);		
	}

}
