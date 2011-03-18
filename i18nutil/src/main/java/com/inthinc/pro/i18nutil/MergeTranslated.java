package com.inthinc.pro.i18nutil;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class MergeTranslated extends BaseTranslationUtil {
    
    
    public MergeTranslated() {
        
    }
    public void mergeProperties(String translations, String destDir, String lang) {
        Map<String, Properties> translationMap;
        try {
            translationMap = initTranslations(translations);
        } catch (IOException e1) {
            System.out.println("Error initialize translations" + translations );
            e1.printStackTrace();
            return;
        }

//        System.out.println("retrieving File list...");
        List<File> propFileList = new ArrayList<File>();
        File baseDir = new File(destDir);
        propFileList.addAll(getFiles(baseDir, new PropertiesFileFilter(lang)));
        
        for (File destFile : propFileList) {
            System.out.println("File: " + destFile.getAbsolutePath());
            Properties transProps = findTranslationProperties(translationMap, destFile.getAbsolutePath());
            if (transProps == null) {
                System.out.println("Error Merging: Cannot find translations for " + destFile.getAbsolutePath());
            }
            else {
                try {
                    mergeOneProperties(transProps, destFile);
                } catch (IOException e) {
                    System.out.println("Error Merging: " + destFile.getAbsolutePath());
                    e.printStackTrace();
                }
            }
        }
        
        System.out.println("Done...");
    }
    
    
    private Properties findTranslationProperties(Map<String, Properties> translationMap, String path) {
        
        for (String key : translationMap.keySet()) {
            if (removeSeparators(path).contains(removeSeparators(key))) 
                return translationMap.get(key);
        }
        return null;
    }
    private String removeSeparators(String path) {
        
        String returnPath = path.toLowerCase().replace("/", "").replace("\\", "");
//System.out.println(returnPath);        
        return returnPath;
        
    }
    
    private Map<String, Properties> initTranslations(String translationsFile) throws IOException {
        
        Map<String, Properties> translationMap = new HashMap<String, Properties>();
        
        BufferedReader reader = new BufferedReader(new FileReader(new File(translationsFile)));
        String line = null;
        String propPath = null; 
        PrintWriter propWriter = null;
        ByteArrayOutputStream out = null;
        
        while ((line = reader.readLine()) != null) {
        
            if (line.startsWith(PROPERTIES_MARKER)) {
                if (propWriter != null) {
                    out.flush();
                    out.close();
                    propWriter.close();
                    Properties properties = new Properties();
                    properties.load(new ByteArrayInputStream(out.toByteArray()));
                    translationMap.put(propPath.toLowerCase(), properties);
                }
                
                propPath = line.substring(4, line.length()-4 );
                out = new ByteArrayOutputStream();
                
                propWriter = new PrintWriter(out);
            }
            else if (propWriter != null) {
                propWriter.println(line);
            }
            
        }
        
        if (propWriter != null) {
            out.flush();
            out.close();
            propWriter.close();
            Properties properties = new Properties();
            properties.load(new ByteArrayInputStream(out.toByteArray()));
            translationMap.put(propPath.toLowerCase(), properties);
        }        
        return translationMap;
    }
    


    public void mergeOneProperties(Properties fromProp , File to) throws IOException {
        Properties toProp = readPropertiesFile(to);
        
        for (Object key : fromProp.keySet()) {
            Object value = fromProp.get(key);
            toProp.put(key, value);
        }
        
        savePropertiesFile(to, toProp);
    }

    private void savePropertiesFile(File to, Properties toProp) throws IOException {
        OutputStream out = new FileOutputStream(to);
        toProp.store(out, "");
        
    }
    
    public static void main(String[] args)
    {
        String usageErrorMsg = "Usage: MergeTranslationsUtil <translations file path> <root dir of files to translate> <language code>";
        
        if (args.length < 3)
        {
            System.out.println(usageErrorMsg);
            System.exit(1);
        }
        
        new MergeTranslated().mergeProperties(args[0], args[1], args[2]);
        System.exit(0);
        
    }
}
