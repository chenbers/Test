package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.model.SelectItem;

import com.inthinc.pro.backing.RedFlagAlertsBean.RedFlagAlertView;
import com.inthinc.pro.model.RedFlagLevel;
import com.inthinc.pro.util.MiscUtil;

public class RedFlagAlertsBeanTest extends BaseAdminBeanTest<RedFlagAlertsBean.RedFlagAlertView>
{
    @Override
    protected RedFlagAlertsBean getAdminBean()
    {
        return (RedFlagAlertsBean) applicationContext.getBean("redFlagAlertsBean");
    }

    @Override
    protected String getFilterValue()
    {
        return "RedFlagAlert10";
    }

    @Override
    protected void populate(RedFlagAlertView editItem, BaseAdminBean<RedFlagAlertsBean.RedFlagAlertView> adminBean)
    {
        editItem.setCreated(new Date());
        editItem.setName("RedFlagAlert");
        editItem.setDescription("New and cool alert");
        final ArrayList<Boolean> dayOfWeek = new ArrayList<Boolean>(7);
        for (int i = 0; i < 7; i++)
            dayOfWeek.add(new Boolean(MiscUtil.randomInt(0, 1) == 1));
        editItem.setDayOfWeek(dayOfWeek);
        editItem.setSeatBeltLevel(RedFlagLevel.WARNING);
        assertEquals("safety", editItem.getType());
        final List<SelectItem> pickedGroups = new ArrayList<SelectItem>();
        pickedGroups.add(new SelectItem("group101"));
        ((RedFlagAlertsBean) adminBean).getAssignPicker().setPicked(pickedGroups);
        editItem.setEmailToString("hello@dolly.com");
    }

    @Override
    protected String[] getBatchUpdateFields()
    {
        return new String[] { "name", "description", "assignTo", "emailToString" };
    }
}
