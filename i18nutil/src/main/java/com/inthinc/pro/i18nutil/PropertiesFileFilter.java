package com.inthinc.pro.i18nutil;

import java.io.File;
import java.io.FilenameFilter;

public class PropertiesFileFilter  implements FilenameFilter {

    String lang;
    public PropertiesFileFilter(String lang) {
        this.lang = lang;
    }
    @Override
    public boolean accept(File dir, String name) {
        if (dir.getAbsolutePath().contains("target"))
            return false;
        if (lang == null) {
            if (name.toLowerCase().endsWith(".properties") && (!name.contains("_") || name.contains("_en")))
                return true;
            return false;
        }
            
        if (name.toLowerCase().endsWith("_"+lang.toLowerCase()+".properties"))
            return true;
        
        return false;
    }
    
}


