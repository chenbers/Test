package com.inthinc.pro.model.adapter;

import java.util.Locale;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class LocaleXmlAdapter extends XmlAdapter<String, Locale> {

    @Override
    public String marshal(Locale locale) throws Exception {
        return locale.getLanguage();
    }

    @Override
    public Locale unmarshal(String language) throws Exception {
        return new Locale(language);
    }

}
