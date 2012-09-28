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

import com.inthinc.pro.dao.AccountDAO;
import com.inthinc.pro.model.Account;

/**
 * Test for the AccountDAOAdapter class.
 * 
 * @author dcueva
 */
public class AccountDAOAdapterTest {

	private final Integer ACCOUNT_ID = new Integer(1);
	private final Account account = new Account();
	
	private static final AccountDAOAdapter adapterSUT = new AccountDAOAdapter(); 
	
	@Mocked AccountDAO accountDAOMock;
	
	@Before
	public void beforeMethod() {
		adapterSUT.setAccountDAO(accountDAOMock);
	}
	
	@After
	public void afterMethod(){
		tearDownMocks();
	}
	
	@Test
	public void testCreate(){
		new Expectations(){{
			accountDAOMock.create(account); returns(ACCOUNT_ID);
		}};
		
		assertEquals(adapterSUT.create(account),ACCOUNT_ID);
	}

	
	@Test
	public void testGetAll(){
		final List<Account> accountList = new ArrayList<Account>(); 
		accountList.add(account);
		
		new Expectations(){{
			accountDAOMock.getAllAcctIDs(); returns(accountList);
		}};
		
		assertEquals(adapterSUT.getAll(), accountList);
	}	
	
	@Test
	public void testGetDAO(){
		assertEquals(adapterSUT.getDAO(), accountDAOMock);
	}		

	@Test
	public void testGetResourceID(){
		account.setAccountID(ACCOUNT_ID); 
		
		assertEquals(adapterSUT.getResourceID(account), ACCOUNT_ID);
	}		
	
}
