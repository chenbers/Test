package com.inthinc.pro.backing;

import java.text.NumberFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import com.inthinc.pro.model.app.SupportedTimeZones;

public class TimeZonesBean extends BaseBean {
    
    private static final long serialVersionUID = 1L;
    private Map<String, TimeZone> timeZones;

    
    public void initBean() {
        timeZones = initTimeZones();
    }
    public Map<String, TimeZone> getTimeZones() {
        return timeZones;
    }

    public void setTimeZones(Map<String, TimeZone> timeZones) {
        this.timeZones = timeZones;
    }


    public Map<String, TimeZone> initTimeZones() {  
        final List<String> timeZones = SupportedTimeZones.getSupportedTimeZones();
        Collections.sort(timeZones, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                final TimeZone t1 = TimeZone.getTimeZone(o1);
                final TimeZone t2 = TimeZone.getTimeZone(o2);
                return t1.getRawOffset() - t2.getRawOffset();
            }
        });
        Map<String, TimeZone> timeZonesList= new LinkedHashMap<String, TimeZone>();
        final NumberFormat format = NumberFormat.getIntegerInstance();
        format.setMinimumIntegerDigits(2);
        for (final String id : timeZones) {
            final TimeZone timeZone = TimeZone.getTimeZone(id);
            timeZonesList.put(getTimeZoneDisplayName(timeZone), timeZone);
        }
        return timeZonesList;
    }
}
