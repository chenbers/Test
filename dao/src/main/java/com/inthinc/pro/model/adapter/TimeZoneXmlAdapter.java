package com.inthinc.pro.model.adapter;

import java.util.TimeZone;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class TimeZoneXmlAdapter extends XmlAdapter<String, TimeZone> {

    @Override
    public String marshal(TimeZone timeZone) throws Exception {
        return timeZone.getID();
    }

    @Override
    public TimeZone unmarshal(String ID) throws Exception {
        return TimeZone.getTimeZone(ID);
    }

}
