package com.inthinc.pro.backing;


import com.inthinc.pro.dao.hessian.proserver.SiloService;
import com.inthinc.pro.dao.hessian.proserver.SiloServiceCreator;
import com.inthinc.pro.model.RedFlagAlert;
import com.inthinc.pro.util.MiscUtil;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

import javax.faces.model.SelectItem;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class RedFlagOrZoneAlertsBeanTest  {
    RedFlagOrZoneAlertsBean.RedFlagOrZoneAlertView redFlagOrZoneAlertView= new RedFlagOrZoneAlertsBean.RedFlagOrZoneAlertView();


    @Test
    public void isSatelliteSwith(){
        redFlagOrZoneAlertView.setSatelliteSelected(true);
        redFlagOrZoneAlertView.setSatellite(true);
        redFlagOrZoneAlertView.setAnytime(true);
        redFlagOrZoneAlertView.setHardBrakeSelected(true);
        redFlagOrZoneAlertView.setHardAccelerationSelected(true);
        redFlagOrZoneAlertView.setHardTurnSelected(true);
        redFlagOrZoneAlertView.setSelected(true);
        Boolean test= redFlagOrZoneAlertView.isSatelliteSelected();
        assertTrue(test);

    }
    @Test
    public void testCreateRedFlagOrZoneAlertView() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        RedFlagOrZoneAlertsBean.RedFlagOrZoneAlertView redFlagOrZoneAlertView1 = new RedFlagOrZoneAlertsBean.RedFlagOrZoneAlertView();
        RedFlagOrZoneAlertsBean redFlagOrZoneAlertsBean2 = new RedFlagOrZoneAlertsBean();
        Class params[] = new Class[1];
        params[0] = RedFlagAlert.class;

        RedFlagAlert redFlagAlert = new RedFlagAlert();
        redFlagAlert.setSatellite(true);
        redFlagAlert.setAlertID(1);
        redFlagAlert.setDescription("test_desc");
        redFlagAlert.setFullName("test_name");
        redFlagAlert.setUserID(1);
        redFlagAlert.setZoneID(1);

        Method method = RedFlagOrZoneAlertsBean.class.getDeclaredMethod("createRedFlagOrZoneAlertView", params);
        method.setAccessible(true);

        redFlagOrZoneAlertView1 = (RedFlagOrZoneAlertsBean.RedFlagOrZoneAlertView) method.invoke(redFlagOrZoneAlertsBean2, redFlagAlert);
        assertNotNull(redFlagOrZoneAlertView1);

    }
}
