package com.inthinc.pro.backing.ui;

import org.junit.Test;

import com.inthinc.pro.backing.BaseBeanTest;
import com.inthinc.pro.model.event.EventType;

public class EventTypeIconsTest extends BaseBeanTest {

	@Test
	public void EventIconsTest(){
		
		EventTypeIcons eti = new EventTypeIcons();
		eti.init();
		assertTrue(eti.getIconMap().get(EventType.IDLING).contains("/images/ico_idle.png"));
		assertTrue(eti.getIconMap().get(EventType.TAMPERING).contains("/images/ico_tampering.png"));
		assertTrue(eti.getIconMap().get(EventType.LOW_BATTERY).contains("/images/ico_violation.png"));
		assertTrue(eti.getIconMap().get(null).contains("/images/ico_violation.png"));		
	}
}
