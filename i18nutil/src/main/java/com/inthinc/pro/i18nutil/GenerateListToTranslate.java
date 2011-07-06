package com.inthinc.pro.i18nutil;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;



public class GenerateListToTranslate extends BaseTranslationUtil {
    
    public GenerateListToTranslate()
    {
        
    }
    
    public void genList(String saveFilePath, String root, String lang, String all) 
    {
        PrintWriter toTranslateWriter = null;
        try {
            File toTranslateFile = new File(saveFilePath);
            toTranslateFile.createNewFile();
            toTranslateWriter = new PrintWriter(toTranslateFile);
        } catch (IOException e) {
            System.out.println("Cannot create file: " + saveFilePath);
            e.printStackTrace();
            return;
        }
        
       
        
        List<File> propFileList = new ArrayList<File>();
        File baseDir = new File(root);
        propFileList.addAll(getFiles(baseDir, new PropertiesFileFilter(null)));
        
        
        for (File propFile : propFileList) {
            try {
                String keyPath = getKeyPath(baseDir.getAbsolutePath(), propFile.getAbsolutePath());
                List<String> nonTranslatedList = NonTranslated.getList(lang, keyPath);
                
                List<String> toTranslateList = null;
                if (all == null) 
                    toTranslateList = getToTranslateList(propFile, getLangPropFile(propFile, lang), lang, nonTranslatedList);
                else toTranslateList = getAllToTranslateList(propFile);
                if (toTranslateList.size() > 0) {
                    toTranslateWriter.println(BaseTranslationUtil.PROPERTIES_MARKER + " " + keyPath + " " + BaseTranslationUtil.PROPERTIES_MARKER);
                    for (String prop : toTranslateList) {
                        toTranslateWriter.println(prop);
                    }
                    
                    toTranslateWriter.println();
                    System.out.println("SUCCESS: added " + toTranslateList.size() + " strings for " + propFile.getAbsolutePath());
                }
                
            } catch (IOException e) {
                System.out.println("Error processing file " + propFile.getAbsolutePath());
                e.printStackTrace();
            }
            
        }

        toTranslateWriter.close();
        
    }
    

    public List<String> getAllToTranslateList(File enPropFile) throws IOException{

        List<String> toTranslate = new ArrayList<String>();
        String name = enPropFile.getAbsolutePath().toLowerCase();
        if (name.contains("log4j") || 
                name.contains("it.properties") || 
                name.contains("automation.properties") || 
                name.contains("qa.properties") ||
                name.contains("tiwipro.properties") ||
                name.contains("totranslate") ||
                name.contains("googlemapkeys") ||
                name.contains("helpconfig") ||
                name.contains("integrationtest") ||
                name.contains("dbutil.properties"))
            return toTranslate;
        
        
        Properties english = readPropertiesFile(enPropFile);

        List<String> propNameList = new ArrayList<String>();
        Enumeration<?> propNames = english.propertyNames();
        while (propNames.hasMoreElements()) {
            propNameList.add(propNames.nextElement().toString());
        }
        Collections.sort(propNameList);
        for (String key : propNameList) {
            String value = english.getProperty(key);
            if (value != null)
                value = value.replaceAll("\\r", " ").replaceAll("\\n", " ");
            toTranslate.add(key + " = " + value);
        }
        return toTranslate;
        
        
    }

    public List<String> getToTranslateList(File enPropFile, File langPropFile, String lang, List<String> nonTranslatedList) throws IOException{

        List<String> toTranslate = new ArrayList<String>();
        
        Properties english = readPropertiesFile(enPropFile);
        Properties translated = null;
        try {
            translated = readPropertiesFile(langPropFile);
        }
        catch (IOException ex) {
  //          System.out.println("WARNING: no " + lang + " file exists for " + enPropFile.getAbsolutePath() + " SKIPPING");
            return toTranslate;
        }


        List<String> propNameList = new ArrayList<String>();
        Enumeration<?> propNames = english.propertyNames();
        while (propNames.hasMoreElements()) {
            propNameList.add(propNames.nextElement().toString());
        }
        Collections.sort(propNameList);
        for (String key : propNameList) {
            if (nonTranslatedList != null && nonTranslatedList.contains(key))
                continue;
            
            String value = english.getProperty(key);
            String langValue = translated.getProperty(key);

            
            if (langValue == null) {
                toTranslate.add(key + " = " + getValueString(value));
            }
            else if (langValue.trim().isEmpty() && value.trim().isEmpty())
                continue;
            else if (langValue.trim().isEmpty()) {
                toTranslate.add(key + " = " + getValueString(value));
            }
            else if (langValue.trim().equalsIgnoreCase(value.trim()) || langValue.contains("(" + lang+ ")")) { 
                toTranslate.add(key + " = " + getValueString(value));
            }
        }
        return toTranslate;
        
        
    }


    public static void main(String[] args)
    {
        String usageErrorMsg = "Usage: GenerateListToTranslate <translations list file path (created)> <root dir of files to check translate> <language code> <ALL(optional)>";
        
        if (args.length < 3)
        {
            System.out.println(usageErrorMsg);
            System.exit(1);
        }
        
        new GenerateListToTranslate().genList(args[0], args[1], args[2], args.length > 3 ? args[3] : null);
        System.exit(0);
        
    }

}
