package com.inthinc.pro.dao.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.inthinc.pro.dao.util.IdExtractor;
import com.inthinc.pro.model.Account;
import com.inthinc.pro.model.AlertEscalationItem;

public class IdExtractorTest {
	
	@Test
	public void testGetID(){
		
		Account account = new Account();
		account.setAccountID(1);
		Integer id = IdExtractor.getID(account);
		assertEquals(new Integer(1), id);
	}
	@Test
	public void testGetIDNoIDAnnotation(){
		
		AlertEscalationItem alertEscalationItem = new AlertEscalationItem();
		Integer id = IdExtractor.getID(alertEscalationItem);
		assertEquals(null, id);
	}

}
