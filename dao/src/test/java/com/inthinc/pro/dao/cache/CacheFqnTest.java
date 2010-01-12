package com.inthinc.pro.dao.cache;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.inthinc.pro.dao.cache.model.MockGroup;
import com.inthinc.pro.dao.cache.model.MockPerson;


public class CacheFqnTest {
	
	@Test
	public void cacheFqn() {
	
		assertEquals("root fqn" , "/root", CacheFqn.root.toString());
		assertEquals("entity fqn" , "/root/MockPerson/333", CacheFqn.getFqn(MockPerson.class, 333).toString());
		assertEquals("entity list fqn" , "/root/MockGroup/333/MockPersonList", CacheFqn.getListFqn(MockGroup.class, 333, MockPerson.class).toString());
	}
}
