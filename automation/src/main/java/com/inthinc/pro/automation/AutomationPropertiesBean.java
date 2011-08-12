package com.inthinc.pro.automation;

import java.lang.reflect.Constructor;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.android.AndroidDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.iphone.IPhoneDriver;

public class AutomationPropertiesBean {
    private String serverProtocol;
    private String serverName;
    private String serverPort;
    private String appPath;
    private String defaultWebDriverName;
    private String defaultWebDriverVersion;
    private String operatingSystem;
    private Boolean sendToRally;
    private List<String> browsers;
    
    
    public WebDriver getDefaultWebDriver() {        
        return Browser.findByName(defaultWebDriverName);
    }
    public String getBaseURL() {
        return serverProtocol+"://"+serverName+":"+serverPort+"/"+appPath+"/";
    }
    public String getServerName() {
        return serverName;
    }
    public void setServerName(String serverName) {
        this.serverName = serverName;
    }
    public String getServerPort() {
        return serverPort;
    }
    public void setServerPort(String serverPort) {
        this.serverPort = serverPort;
    }
    public String getAppPath() {
        return appPath;
    }
    public void setAppPath(String appPath) {
        this.appPath = appPath;
    }
    public String getDefaultWebDriverName() {
        return defaultWebDriverName;
    }
    public void setDefaultWebDriverName(String defaultWebDriverName) {
        this.defaultWebDriverName = defaultWebDriverName;
    }
    private boolean hasBrowser(String test) {
        for(String browser: browsers){
            if(browser.startsWith(test))
                return true;
        }
        return false;
    }
    public String getServerProtocol() {
        return serverProtocol;
    }
    public void setServerProtocol(String serverProtocol) {
        this.serverProtocol = serverProtocol;
    }
    public String getOperatingSystem() {
        return operatingSystem;
    }
    public void setOperatingSystem(String operatingSystem) {
        this.operatingSystem = operatingSystem;
    }
    public Boolean getSendToRally() {
        return sendToRally;
    }
    public void setSendToRally(Boolean sendToRally) {
        this.sendToRally = sendToRally;
    }
    public List<String> getBrowsers() {
        return browsers;
    }
    public void setBrowsers(List<String> browsers) {
        this.browsers = browsers;
    }
    public String getDefaultWebDriverVersion() {
        return defaultWebDriverVersion;
    }
    public void setDefaultWebDriverVersion(String defaultWebDriverVersion) {
        this.defaultWebDriverVersion = defaultWebDriverVersion;
    }
    
    public enum Browser {

        ANDROID("android", AndroidDriver.class),
        CHROME("chrome", ChromeDriver.class),
        FIREFOX("ff", FirefoxDriver.class),
        INTERNET_EXPLORER("ie", InternetExplorerDriver.class),
        IPHONE("iphone", IPhoneDriver.class),
        ;

        private String name;
        private WebDriver webDriver;
        private Class<? extends WebDriver> webDriverClass;
        
        private Browser(String name, Class<? extends WebDriver> clazz){
            this.name=name;
            this.webDriverClass = clazz;
        }
        private Browser(String name, WebDriver webDriver){
            this.name=name;
            this.webDriver = webDriver;
        }
        private static HashMap<String, Browser> lookupByName = new HashMap<String, Browser>();

        static {
            for (Browser b : EnumSet.allOf(Browser.class)) {
                lookupByName.put(b.getName(), b);
            }
        }

        public String getName(){
            return name;
        }
        public WebDriver getWebDriver() {
            return webDriver;
        }
        public Class<? extends WebDriver> getWebDriverClass() {
            return webDriverClass;
        }
        public static WebDriver findByName(String name) {
            String nameWithoutVersion = name.replaceAll("[0-9._]", "");
            Class[] paramTypes = new Class[] {};
            Object[] params = new Object[] {}; 
            Class clazz = lookupByName.get(nameWithoutVersion).getWebDriverClass();
            Constructor con;
            try {
                con = clazz.getConstructor(paramTypes);
                return (WebDriver)con.newInstance(params);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                System.out.println("defaulting to FirefoxDriver... name: "+name);//TODO: jwimmer: replace with logger or remove?
                return new FirefoxDriver();
            }
            
            
            //return lookupByName.get(nameWithoutVersion);
        }
    }
}
