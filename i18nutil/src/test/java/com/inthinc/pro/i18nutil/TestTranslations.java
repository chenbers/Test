package com.inthinc.pro.i18nutil;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class TestTranslations {
    
    
    @Test
    public void roTest() throws IOException {
        
        String lang = "ro";
        List<File> propFileList = new ArrayList<File>();
        File baseDir = new File("..");
        BaseTranslationUtil baseUtil = new BaseTranslationUtil();
        propFileList.addAll(baseUtil.getFiles(baseDir, new PropertiesFileFilter(null)));
        
        for (File enFile : propFileList) {
            
            File roFile = baseUtil.getLangPropFile(enFile, lang);
            if (!roFile.exists()) {
                System.out.println("WARNING FILE DOES NOT EXIST " + roFile.getAbsolutePath());
                continue;
            }
            
            String keyPath = baseUtil.getKeyPath(baseDir.getAbsolutePath(), enFile.getAbsolutePath());
            List<String> nonTranslatedList = NonTranslated.getList(lang, keyPath);
            
            Properties english = baseUtil.readPropertiesFile(enFile);
            Properties translated = baseUtil.readPropertiesFile(roFile);

            System.out.println("Checking: " + roFile.getAbsolutePath());
            List<String> missing = new ArrayList<String>();
            int errorCount =  0;
            Enumeration<?> propNames = english.propertyNames(); 
            while (propNames.hasMoreElements()) {
                String key = propNames.nextElement().toString();
                String value = english.getProperty(key);
                String langValue = translated.getProperty(key);
                
                
                if (langValue == null) {
                    System.out.println(key + " MISSING");
                    errorCount++;
                }
                else if (langValue.isEmpty() && value.isEmpty())
                    continue;
                else if (langValue.isEmpty()) {
                    System.out.println(key + " MISSING");
                    errorCount++;
                }
                else if (nonTranslatedList != null && nonTranslatedList.contains(key))
                    continue;
                else if (langValue.trim().equalsIgnoreCase(value.trim()) || langValue.contains("(" + lang+ ")")) { 
                    System.out.println(key + " NEEDS TRANS");
                    missing.add(key);
                    errorCount++;
                }
            }

//            Collections.sort(missing);
//            for (String key : missing)
//                System.out.println("nonTranslatedList.add(\"" + key + "\");");
                    
            assertEquals("ro messages need translations", 0, errorCount);
        }
    }
}
