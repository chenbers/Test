package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.model.SelectItem;

import com.inthinc.pro.backing.ZoneAlertsBean.ZoneAlertView;
import com.inthinc.pro.util.MiscUtil;

public class ZoneAlertsBeanTest extends BaseAdminBeanTest<ZoneAlertsBean.ZoneAlertView>
{
    @Override
    protected ZoneAlertsBean getAdminBean()
    {
        return (ZoneAlertsBean) applicationContext.getBean("zoneAlertsBean");
    }

    @Override
    protected String getFilterValue()
    {
        return "zone alert 60";
    }

    @Override
    protected void populate(ZoneAlertView editItem, BaseAdminBean<ZoneAlertsBean.ZoneAlertView> adminBean)
    {
        editItem.setCreated(new Date());
        editItem.setName("Zone Alert");
        editItem.setDescription("Toolin' around the zone");
        editItem.setArrival(MiscUtil.randomInt(0, 1) == 1 ? Boolean.TRUE : Boolean.FALSE);
        editItem.setDeparture(MiscUtil.randomInt(0, 1) == 1 ? Boolean.TRUE : Boolean.FALSE);
        editItem.setDriverIDViolation(MiscUtil.randomInt(0, 1) == 1 ? Boolean.TRUE : Boolean.FALSE);
        editItem.setIgnitionOn(MiscUtil.randomInt(0, 1) == 1 ? Boolean.TRUE : Boolean.FALSE);
        editItem.setIgnitionOff(MiscUtil.randomInt(0, 1) == 1 ? Boolean.TRUE : Boolean.FALSE);
        editItem.setPosition(MiscUtil.randomInt(0, 1) == 1 ? Boolean.TRUE : Boolean.FALSE);
        editItem.setSeatbeltViolation(MiscUtil.randomInt(0, 1) == 1 ? Boolean.TRUE : Boolean.FALSE);
        if (MiscUtil.randomInt(0, 1) == 1)
            editItem.setSpeedLimit(MiscUtil.randomInt(5, 70));
        editItem.setSpeedViolation(MiscUtil.randomInt(0, 1) == 1 ? Boolean.TRUE : Boolean.FALSE);
        editItem.setMasterBuzzer(MiscUtil.randomInt(0, 1) == 1 ? Boolean.TRUE : Boolean.FALSE);
        editItem.setCautionArea(MiscUtil.randomInt(0, 1) == 1 ? Boolean.TRUE : Boolean.FALSE);
        editItem.setDisableRF(MiscUtil.randomInt(0, 1) == 1 ? Boolean.TRUE : Boolean.FALSE);
        editItem.setMonitorIdle(MiscUtil.randomInt(0, 1) == 1 ? Boolean.TRUE : Boolean.FALSE);
        final List<SelectItem> pickedGroups = new ArrayList<SelectItem>();
        pickedGroups.add(new SelectItem("group101"));
        ((ZoneAlertsBean) adminBean).getAssignPicker().setPicked(pickedGroups );
        editItem.setEmailToString("hello@dolly.com");
    }

    @Override
    protected String[] getBatchUpdateFields()
    {
        return new String[] { "name", "description", "assignTo", "emailToString" };
    }
}
