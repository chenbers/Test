package com.inthinc.pro.dao.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.inthinc.pro.model.Account;
import com.inthinc.pro.model.AlertEscalationItem;
import com.inthinc.pro.model.Person;

public class FieldExtractorTest {
	
	@Test
	public void testGetID(){
		
		Account account = new Account();
		account.setAccountID(1);
		Integer id = FieldExtractor.getID(account);
		assertEquals(new Integer(1), id);
	}
	@Test
	public void testGetIDNoIDAnnotation(){
		
		AlertEscalationItem alertEscalationItem = new AlertEscalationItem();
		Integer id = FieldExtractor.getID(alertEscalationItem);
		assertEquals(null, id);
	}

	@Test
	public void testGetAcctID(){
	    Person person = new Person();
	    person.setAcctID(4);
	    person.setPersonID(100);
	    Object acctID = FieldExtractor.getProperty("acctID", person);
        assertEquals(new Integer(4), acctID);
        
        person.setAcctID(null);
        acctID = FieldExtractor.getProperty("acctID", person);
        assertEquals(null, acctID);
        
        NonIntegerAccountID nonIntegerAccountID = new NonIntegerAccountID();
        nonIntegerAccountID.setAcctID(45);
        acctID = FieldExtractor.getProperty("acctID", nonIntegerAccountID);
        assertEquals(new Integer(45), acctID);

        StringAccountID stringAccountID = new StringAccountID();
        stringAccountID.setAcctID("accountID");
        Object accountID = FieldExtractor.getProperty("acctID", stringAccountID);
        assertEquals("accountID", accountID);
}
	private class NonIntegerAccountID{

        private String acctID;
        
        public Integer getAcctID() {
            return Integer.parseInt(acctID);
        }

        public void setAcctID(Integer acctID) {
            this.acctID = acctID+"";
        }
	    
	}
    private class StringAccountID{

        private String acctID;
        
        
        public String getAcctID() {
            return acctID;
        }

        public void setAcctID(String acctID) {
            this.acctID = acctID;
        }

    }
}
