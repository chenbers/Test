package com.inthinc.pro.model.adapter;

import java.util.Locale;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class LocaleXmlAdapter extends XmlAdapter<String, Locale> {

    @Override
    public String marshal(Locale locale) throws Exception {
        return locale.toString();
    }

    @Override
    public Locale unmarshal(String v) throws Exception {
        String[]tokens = v.split("_");
        if (tokens.length == 1)
            return new Locale(tokens[0]);
        if (tokens.length == 2)
            return new Locale(tokens[0],tokens[1]);
        return null;
    }

}
