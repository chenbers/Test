package com.inthinc.pro.model.aggregation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import it.com.inthinc.pro.dao.Util;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.Status;

public class DriverVehicleScoreWrapperTest {
    private static final int GROUP_ID = 2;
    
    private static double expectedMpg;
    private static double milesForEachVehicle;
    private static double roundingMargin = 0.01;
    private static DriverVehicleScoreWrapper heavyOnlyDriver = makeDriverVehicleScoreWrapper(1);
    private static DriverVehicleScoreWrapper mediumOnlyDriver = makeDriverVehicleScoreWrapper(2);
    private static DriverVehicleScoreWrapper lightOnlyDriver = makeDriverVehicleScoreWrapper(3);
    
    @BeforeClass
    public static void setupSumarizeDrivers() {
        //
        expectedMpg = Util.randomInt(500, 4000) / 100;
        milesForEachVehicle = Util.randomInt(100, 10000) / 100;
        
        heavyOnlyDriver.getScore().setMpgHeavy(expectedMpg * 100);
        heavyOnlyDriver.getScore().setMpgMedium(null);
        heavyOnlyDriver.getScore().setMpgLight(null);
        heavyOnlyDriver.getScore().setOdometerHeavy(milesForEachVehicle);
        heavyOnlyDriver.getScore().setOdometerMedium(0);
        heavyOnlyDriver.getScore().setOdometerLight(0);
        
        mediumOnlyDriver.getScore().setMpgHeavy(0);
        mediumOnlyDriver.getScore().setMpgMedium(expectedMpg * 100);
        mediumOnlyDriver.getScore().setMpgLight(null);
        mediumOnlyDriver.getScore().setOdometerHeavy(null);
        mediumOnlyDriver.getScore().setOdometerMedium(milesForEachVehicle);
        mediumOnlyDriver.getScore().setOdometerLight(0);
        
        lightOnlyDriver.getScore().setMpgHeavy((expectedMpg * 100) / 3);// intentionally UNexpected mpg,
        lightOnlyDriver.getScore().setOdometerHeavy(null);// but with null miles
        lightOnlyDriver.getScore().setMpgMedium((expectedMpg * 100) / 2);// intentionally UNexpected mpg
        lightOnlyDriver.getScore().setOdometerMedium(0);// but with zero miles
        lightOnlyDriver.getScore().setMpgLight(expectedMpg * 100);
        lightOnlyDriver.getScore().setOdometerLight(milesForEachVehicle);
    }
    
    @Test
    public void testSummarize() {
        List<DriverVehicleScoreWrapper> list = new ArrayList<DriverVehicleScoreWrapper>();
        
        int total = Util.randomInt(5, 20);
        for (int i = 0; i < total; i++) {
            
            list.add(makeDriverVehicleScoreWrapper(i));
        }
        
        Group group = new Group(GROUP_ID, GROUP_ID, "test", 1);
        DriverVehicleScoreWrapper summary = DriverVehicleScoreWrapper.summarize(list, group);
        DriverVehicleScoreWrapper expected = makeDriverVehicleScoreWrapper(100);
        
        assertTrue("summary", summary.getSummary());
        
        // totals
        assertEquals("Trips", expected.getScore().getTrips().intValue() * total, summary.getScore().getTrips());
        assertEquals("IdleHi", expected.getScore().getIdleHi().intValue() * total, summary.getScore().getIdleHi());
        assertEquals("IdleLo", expected.getScore().getIdleLo().intValue() * total, summary.getScore().getIdleLo());
        assertEquals("IdleHiEvents", expected.getScore().getIdleHiEvents().intValue() * total, summary.getScore().getIdleHiEvents());
        assertEquals("IdleLoEvents", expected.getScore().getIdleLoEvents().intValue() * total, summary.getScore().getIdleLoEvents());
        assertEquals("DriveTime", expected.getScore().getDriveTime().intValue() * total, summary.getScore().getDriveTime());
        // assertEquals("EndingOdometer", expected.getScore().getOverall(), summary.getScore().getOverall());
        // assertEquals("StartingOdometer", expected.getScore().getOverall(), summary.getScore().getOverall());
        assertEquals("CrashEvents", expected.getScore().getCrashEvents().intValue() * total, summary.getScore().getCrashEvents());
        assertEquals("TotalSafetyEvents", expected.getScore().getSafetyTotal().longValue() * total, summary.getScore().getSafetyTotal());
        
        // averages
        assertEquals("Overall", expected.getScore().getOverall(), summary.getScore().getOverall());
        assertEquals("WeightedMPG", expected.getScore().getWeightedMpg(), summary.getScore().getWeightedMpg());
        
    }
    
    private static DriverVehicleScoreWrapper makeDriverVehicleScoreWrapper(int id) {
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
        wrapper.setDriver(new Driver(id, id, Status.ACTIVE, "", 0l, 0l, "1wireID", "", null, "", null, "", null, GROUP_ID));
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
    
    /**
     * Test summarize when drivers have MPG in different zones. Sets up 3 drivers each of whom drive the same distance, with the same MPG, BUT each in a different zone. After summarize it is expected
     * that all zones will have the same MPG, as well as Odometer, additionally weighted MPG should be within a rounding margin of the initial MPG that all vehicles drove.
     * 
     */
    @Test
    public void testSummarize_MpgInDifferentZones() {
        ArrayList<DriverVehicleScoreWrapper> listForSummarize = new ArrayList<DriverVehicleScoreWrapper>();
        listForSummarize.add(heavyOnlyDriver);
        listForSummarize.add(mediumOnlyDriver);
        listForSummarize.add(lightOnlyDriver);
        
        Group group = new Group(GROUP_ID, GROUP_ID, "test", 1);
        DriverVehicleScoreWrapper summary = DriverVehicleScoreWrapper.summarize(listForSummarize, group);
        
        assertTrue("weightedMPG was not within rounding margin; expected: <" + expectedMpg + "> but was: <" + summary.getScore().getWeightedMpg().doubleValue() + ">",
                        Math.abs(expectedMpg - summary.getScore().getWeightedMpg().doubleValue()) < roundingMargin);
        assertEquals(expectedMpg * 100, summary.getScore().getMpgHeavy());
        assertEquals(expectedMpg * 100, summary.getScore().getMpgMedium());
        assertEquals(expectedMpg * 100, summary.getScore().getMpgLight());
        
        assertEquals(milesForEachVehicle, summary.getScore().getOdometerHeavy());
        assertEquals(milesForEachVehicle, summary.getScore().getOdometerMedium());
        assertEquals(milesForEachVehicle, summary.getScore().getOdometerLight());
    }
    
    @Test
    public void testSummarize_notAllZones() {
        ArrayList<DriverVehicleScoreWrapper> listForSummarize = new ArrayList<DriverVehicleScoreWrapper>();
        listForSummarize.add(mediumOnlyDriver);
        
        Group group = new Group(GROUP_ID, GROUP_ID, "test", 1);
        DriverVehicleScoreWrapper summary = DriverVehicleScoreWrapper.summarize(listForSummarize, group);
        
        assertTrue("weightedMPG was not within rounding margin; expected: <" + expectedMpg + "> but was: <" + summary.getScore().getWeightedMpg().doubleValue() + ">",
                        Math.abs(expectedMpg - summary.getScore().getWeightedMpg().doubleValue()) < roundingMargin);
        assertEquals(0, summary.getScore().getMpgHeavy().intValue());
        assertEquals(expectedMpg * 100, summary.getScore().getMpgMedium());
        assertEquals(0, summary.getScore().getMpgLight().intValue());
        
        assertEquals(0, summary.getScore().getOdometerHeavy().intValue());
        assertEquals(milesForEachVehicle, summary.getScore().getOdometerMedium());
        assertEquals(0, summary.getScore().getOdometerLight().intValue());
    }
    
    @Test
    public void testSummarize_singleDriverInDifferentZones() {
        DriverVehicleScoreWrapper odoDriver = makeDriverVehicleScoreWrapper(4);
        odoDriver.getScore().setMpgHeavy((expectedMpg * 100));
        odoDriver.getScore().setOdometerHeavy(milesForEachVehicle);
        odoDriver.getScore().setMpgMedium((expectedMpg * 100));
        odoDriver.getScore().setOdometerMedium(milesForEachVehicle);
        odoDriver.getScore().setMpgLight(expectedMpg * 100);
        odoDriver.getScore().setOdometerLight(milesForEachVehicle);
        
        ArrayList<DriverVehicleScoreWrapper> listForSummarize = new ArrayList<DriverVehicleScoreWrapper>();
        
        listForSummarize.add(lightOnlyDriver);
        listForSummarize.add(mediumOnlyDriver);
        listForSummarize.add(heavyOnlyDriver);
        listForSummarize.add(odoDriver);
        
        Group group = new Group(GROUP_ID, GROUP_ID, "test", 1);
        DriverVehicleScoreWrapper summary = DriverVehicleScoreWrapper.summarize(listForSummarize, group);
        
        assertTrue("weightedMPG was not within rounding margin; expected: <" + expectedMpg + "> but was: <" + summary.getScore().getWeightedMpg().doubleValue() + "> for distance:"
                        + milesForEachVehicle, Math.abs(expectedMpg - summary.getScore().getWeightedMpg().doubleValue()) < roundingMargin);
        assertEquals(expectedMpg * 100, summary.getScore().getMpgHeavy());
        assertEquals(expectedMpg * 100, summary.getScore().getMpgMedium());
        assertEquals(expectedMpg * 100, summary.getScore().getMpgLight());
        
        assertEquals(milesForEachVehicle, summary.getScore().getOdometerHeavy());
        assertEquals(milesForEachVehicle, summary.getScore().getOdometerMedium());
        assertEquals(milesForEachVehicle, summary.getScore().getOdometerLight());
    }
    
    @Test
    public void testCompareTo() {
        // test boundary: null situation
        DriverVehicleScoreWrapper nullwrapper1 = new DriverVehicleScoreWrapper();
        DriverVehicleScoreWrapper nullwrapper2 = new DriverVehicleScoreWrapper();
        
        DriverVehicleScoreWrapper normalwrapper = new DriverVehicleScoreWrapper();
        Driver driver1 = new Driver();
        Person person1 = new Person();
        person1.setLast("last");
        person1.setFirst("first");
        person1.setMiddle("middle");
        driver1.setPerson(person1);
        normalwrapper.setDriver(driver1);
        
        DriverVehicleScoreWrapper normalAfter = new DriverVehicleScoreWrapper();
        Driver driver2 = new Driver();
        Person person2 = new Person();
        person2.setLast("last");
        person2.setFirst("first");
        person2.setMiddle("middlename");
        driver2.setPerson(person2);
        normalAfter.setDriver(driver2);
        
        DriverVehicleScoreWrapper normalBefore = new DriverVehicleScoreWrapper();
        Driver driver3 = new Driver();
        Person person3 = new Person();
        person3.setLast("alast");
        person3.setFirst("first");
        person3.setMiddle("middle");
        driver3.setPerson(person3);
        normalBefore.setDriver(driver3);
        
        DriverVehicleScoreWrapper normalEqual = new DriverVehicleScoreWrapper();
        Driver driver4 = new Driver();
        Person person4 = new Person();
        person4.setLast("last");
        person4.setFirst("first");
        person4.setMiddle("middle");
        driver4.setPerson(person4);
        normalEqual.setDriver(driver4);
        
        // null to null comparison
        assertEquals(1, nullwrapper1.compareTo(nullwrapper2));
        // null to normal comparison
        assertEquals(-1, nullwrapper1.compareTo(normalwrapper));
        // normal to null comparison
        assertEquals(1, normalwrapper.compareTo(nullwrapper1));
        // normal to normal comparisons
        assertTrue(normalwrapper.compareTo(normalAfter) < 0);
        assertTrue(normalwrapper.compareTo(normalBefore) > 0);
        assertTrue(normalwrapper.compareTo(normalEqual) == 0);
    }
    
}
