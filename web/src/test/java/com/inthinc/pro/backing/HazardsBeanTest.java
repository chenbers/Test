package com.inthinc.pro.backing;

import com.inthinc.pro.model.HazardType;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;

/**
 * Tests {@link HazardsBean}.
 */
public class HazardsBeanTest {
    private static Integer TEN_THOUSAND_FEET_IN_METERS = 3084;

    @Test
    public void testUnitsRadius() {
        // Create a hazard bean
        HazardsBean hazardsBean = new HazardsBean();

        // do add and set radius in meters + mandatory stuff
        hazardsBean.add();
        hazardsBean.getItem().setRadiusMeters(TEN_THOUSAND_FEET_IN_METERS);
        hazardsBean.getItem().setType(HazardType.ROADRESTRICTIONS_BAN);
        hazardsBean.getItem().setStartTime(new Date());
        hazardsBean.getItem().setEndTime(new Date());

        // do edit and expect 1 MILE
        hazardsBean.edit();
        assertEquals(hazardsBean.getItem().getRadiusInUnits().intValue(), 1);
    }
}
