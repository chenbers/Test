package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.model.SelectItem;

import org.junit.Ignore;

import com.inthinc.pro.backing.RedFlagOrZoneAlertsBean.RedFlagOrZoneAlertView;
import com.inthinc.pro.util.MiscUtil;
@Ignore
public class RedFlagAlertsBeanTest extends BaseAdminBeanTest<RedFlagOrZoneAlertsBean.RedFlagOrZoneAlertView>
{
    @Override
    protected RedFlagOrZoneAlertsBean getAdminBean()
    {
        return (RedFlagOrZoneAlertsBean) applicationContext.getBean("redFlagAlertsBean");
    }

    @Override
    protected String getFilterValue()
    {
        return "RedFlagAlert10";
    }

    @Override
    protected void populate(RedFlagOrZoneAlertView editItem, BaseAdminBean<RedFlagOrZoneAlertView> adminBean)
    {
        editItem.setCreated(new Date());
        editItem.setName("RedFlagAlert");
        editItem.setDescription("New and cool alert");
        final ArrayList<Boolean> dayOfWeek = new ArrayList<Boolean>(7);
        for (int i = 0; i < 7; i++)
            dayOfWeek.add(new Boolean(MiscUtil.randomInt(0, 1) == 1));
        editItem.setDayOfWeek(dayOfWeek);
//        editItem.setSeatBeltLevel(RedFlagLevel.WARNING);
//        assertEquals("seatBelt", editItem.getType());
        final List<SelectItem> pickedGroups = new ArrayList<SelectItem>();
        pickedGroups.add(new SelectItem("group101"));
        ((RedFlagOrZoneAlertsBean) adminBean).getAssignPicker().setPicked(pickedGroups);
    }

    @Override
    protected String[] getBatchUpdateFields()
    {
        return new String[] { "name", "description", "assignTo"};
    }
}
