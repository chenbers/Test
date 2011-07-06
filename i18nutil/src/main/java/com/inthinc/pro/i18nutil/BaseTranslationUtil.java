package com.inthinc.pro.i18nutil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

public class BaseTranslationUtil {
    
    public static final String PROPERTIES_MARKER = "***";
    public static final int PROPERTY_EXT_LEN = 11;
    
    public Collection<? extends File> getFiles(File baseDir, FilenameFilter filter) {
        List<File> fileList = new ArrayList<File>();
        fileList.addAll(Arrays.asList(baseDir.listFiles(filter)));
        for (File subDir : baseDir.listFiles()) {
            if (subDir.isDirectory())
                fileList.addAll(getFiles(subDir, filter));
        }
        
        return fileList;
    }

    protected Properties readPropertiesFile(String propFilePath) throws IOException {
        return readPropertiesFile(new File(propFilePath));
    }
    protected Properties readPropertiesFile(File propFile) throws IOException {

        InputStream stream;
        stream = new FileInputStream(propFile);
        Properties properties = new Properties();
        properties.load(stream);
        return properties;
    }
    
    public String getKeyPath(String root, String absolutePath) {
        if (absolutePath.startsWith(root))
            return absolutePath.substring(root.length(), absolutePath.length() - PROPERTY_EXT_LEN).replace("\\", "/");
        return absolutePath;
    }

    public File getLangPropFile(File propFile, String lang) {

        String path = propFile.getAbsolutePath();
        
        String langPath = path.substring(0, path.length()-PROPERTY_EXT_LEN) + "_" + lang + ".properties";
        
        return new File(langPath);
    }

    protected String getValueString(String value) {
        if (value == null)
            return "";
        String[] lines = value.split("\\r\\n");
        if (lines.length == 1)
            return lines[0];
        StringBuffer returnBuffer = new StringBuffer();
        for (String line : lines) {
            if (returnBuffer.length() > 0)
                returnBuffer.append("\\r\\n");
            returnBuffer.append(line);
        }
        return returnBuffer.toString();
    }
    
    
}
