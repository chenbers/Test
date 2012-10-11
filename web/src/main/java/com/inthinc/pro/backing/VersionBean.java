package com.inthinc.pro.backing;

import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;

public class VersionBean {
    private static final Logger logger = Logger.getLogger(VersionBean.class);
    ApplicationContext applicationContext;
    private Properties manifestProperties;
    private String version;

    public Properties getManifestProperties() {
        return manifestProperties;
    }

    public void setManifestProperties(Properties manifestProperties) {
        this.manifestProperties = manifestProperties;
        version = manifestProperties.getProperty("project-version", "Version |") + " " + manifestProperties.getProperty("project-build-date", "Time Stamp |") + " "
                + manifestProperties.getProperty("build-number", "Build Number");
    }

    public String getVersion() {
        return version;
    }

}
