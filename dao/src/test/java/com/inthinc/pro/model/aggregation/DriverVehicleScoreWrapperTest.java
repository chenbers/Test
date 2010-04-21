package com.inthinc.pro.model.aggregation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import it.com.inthinc.pro.dao.Util;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.Status;


public class DriverVehicleScoreWrapperTest {
	private static final int GROUP_ID = 2;

	@Test
	public void testSummarize() {
		List<DriverVehicleScoreWrapper> list = new ArrayList<DriverVehicleScoreWrapper>();
		
		int total = Util.randomInt(5, 20);
		for (int i = 0; i < total; i++) {
			
			list.add(makeDriverVehicleScoreWrapper(i));
		}
		
		Group group = new Group(GROUP_ID, GROUP_ID,"test",1);
		DriverVehicleScoreWrapper summary = DriverVehicleScoreWrapper.summarize(list, group);
		DriverVehicleScoreWrapper expected = makeDriverVehicleScoreWrapper(100);
		
		assertTrue("summary", summary.getSummary());
		
		// totals
		assertEquals("Trips", expected.getScore().getTrips().intValue()*total, summary.getScore().getTrips());
		assertEquals("IdleHi", expected.getScore().getIdleHi().intValue()*total, summary.getScore().getIdleHi());
		assertEquals("IdleLo", expected.getScore().getIdleLo().intValue()*total, summary.getScore().getIdleLo());
		assertEquals("IdleHiEvents", expected.getScore().getIdleHiEvents().intValue()*total, summary.getScore().getIdleHiEvents());
		assertEquals("IdleLoEvents", expected.getScore().getIdleLoEvents().intValue()*total, summary.getScore().getIdleLoEvents());
		assertEquals("DriveTime", expected.getScore().getDriveTime().intValue()*total, summary.getScore().getDriveTime());
//		assertEquals("EndingOdometer", expected.getScore().getOverall(), summary.getScore().getOverall());
//		assertEquals("StartingOdometer", expected.getScore().getOverall(), summary.getScore().getOverall());
		assertEquals("CrashEvents", expected.getScore().getCrashEvents().intValue()*total, summary.getScore().getCrashEvents());
		assertEquals("TotalSafetyEvents", expected.getScore().getSafetyTotal().longValue()*total, summary.getScore().getSafetyTotal());

		// averages
		assertEquals("Overall", expected.getScore().getOverall(), summary.getScore().getOverall());
		assertEquals("WeightedMPG", expected.getScore().getWeightedMpg(), summary.getScore().getWeightedMpg());
		
	}

	private DriverVehicleScoreWrapper makeDriverVehicleScoreWrapper(int id) {
		DriverVehicleScoreWrapper wrapper = new DriverVehicleScoreWrapper();
		Score score = new Score();
		
        score.setOverall(26);
        score.setTrips(2);
        score.setIdleHi(8000);
        score.setIdleLo(1000);
        score.setIdleHiEvents(8);
        score.setIdleLoEvents(1);
        score.setDriveTime(30000);
        score.setEndingOdometer(100);
        score.setStartingOdometer(0); 
        score.setMpgHeavy(10);
        score.setMpgMedium(15);
        score.setMpgLight(20);
        score.setOdometerHeavy(1000);
        score.setOdometerMedium(1500);
        score.setOdometerLight(2000);
        
        score.setCrashEvents(1);       
        score.setSeatbeltEvents(1);        
        score.setSpeedEvents(1);
        score.setAggressiveAccelEvents(1);
        score.setAggressiveBrakeEvents(1);
        score.setAggressiveBumpEvents(1);
        score.setAggressiveLeftEvents(1);
        score.setAggressiveRightEvents(1);
        
        wrapper.setScore(score);
        wrapper.setDriver(new Driver(id, id, Status.ACTIVE, "", 0l,0l,"", null,"",null,"", "", GROUP_ID));
        wrapper.setVehicle(null);
		
		return wrapper;
	}
	
	@Test
	public void testWeightedMpg() {
		DriverVehicleScoreWrapper wrapper = new DriverVehicleScoreWrapper();
		Score score = new Score();
		wrapper.setScore(score);
		
        score.setOdometerHeavy(0l);
        score.setOdometerLight(0l);
        score.setOdometerMedium(0l);
        
        Number mpg = wrapper.getScore().getWeightedMpg();
        assertEquals("mpg", 0, mpg.longValue());
		
        score.setOdometerHeavy(0l);
        score.setOdometerLight(0l);
        score.setOdometerMedium(0l);
        
        mpg = wrapper.getScore().getWeightedMpg();
        
        assertEquals("mpg", 0, mpg.longValue());

	}

}
