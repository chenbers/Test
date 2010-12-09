package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.Date;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import javax.faces.model.SelectItem;

import org.junit.Ignore;

import com.inthinc.pro.backing.RedFlagOrZoneAlertsBean.RedFlagOrZoneAlertView;
import com.inthinc.pro.model.AlertMessageType;
import com.inthinc.pro.util.MiscUtil;
@Ignore
public class ZoneAlertsBeanTest extends BaseAdminBeanTest<RedFlagOrZoneAlertsBean.RedFlagOrZoneAlertView>
{
    @Override
    protected RedFlagOrZoneAlertsBean getAdminBean()
    {
        return (RedFlagOrZoneAlertsBean) applicationContext.getBean("zoneAlertsBean");
    }

    @Override
    protected String getFilterValue()
    {
        return "zone alert 60";
    }

    // TODO: the following test works about half the time, due to randomness in the IDs generated by MockData.
/*    
    @Ignore
    @Override
    @Test
    public void list()
    {
        super.list();
    }

    // TODO: TODO: the following test works when run standalone, but when run as part of the build, an item is added instead of deleted.
//    @Ignore
    @Override
    @Test
    public void delete()
    {
        super.delete();
    }
*/
    @Override
    protected void populate(RedFlagOrZoneAlertView editItem, BaseAdminBean<RedFlagOrZoneAlertsBean.RedFlagOrZoneAlertView> adminBean)
    {
        editItem.setCreated(new Date());
        editItem.setName("Zone Alert");
        editItem.setDescription("Toolin' around the zone");
        final ArrayList<Boolean> dayOfWeek = new ArrayList<Boolean>(7);
        for (int i = 0; i < 7; i++)
            dayOfWeek.add(new Boolean(MiscUtil.randomInt(0, 1) == 1));
        editItem.setDayOfWeek(dayOfWeek);
        Set<AlertMessageType> types = EnumSet.noneOf(AlertMessageType.class);
        if ( MiscUtil.randomInt(0, 1)==1) types.add(AlertMessageType.ALERT_TYPE_ENTER_ZONE);
        if (types.isEmpty()){
            types.add(AlertMessageType.ALERT_TYPE_EXIT_ZONE);
        }
        else{
            if ( MiscUtil.randomInt(0, 1)==1) types.add(AlertMessageType.ALERT_TYPE_EXIT_ZONE);
        }
        editItem.setTypesSet(types);
        final List<SelectItem> pickedGroups = new ArrayList<SelectItem>();
        pickedGroups.add(new SelectItem("group101"));
        ((RedFlagOrZoneAlertsBean) adminBean).getAssignPicker().setPicked(pickedGroups);
        editItem.setEmailToString("hello@dolly.com");
    }

    @Override
    protected String[] getBatchUpdateFields()
    {
        return new String[] { "name", "description", "assignTo" };//, "emailToString" };
    }
}
