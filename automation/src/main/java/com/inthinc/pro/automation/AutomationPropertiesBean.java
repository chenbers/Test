package com.inthinc.pro.automation;

import java.util.ArrayList;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;


public class AutomationPropertiesBean {
    private String serverProtocol;
    private String serverName;
    private String serverPort;
    private String appPath;
    private String defaultWebDriverName;
    private String hasFirefox;
    private String hasIE;
    private String hasChrome;
    private String operatingSystem;

    public WebDriver getDefaultWebDriver() {
        //TODO: jwimmer: I'd rather pull these from some .properties file
        if(getDefaultWebDriverName().startsWith("ie"))
            return new InternetExplorerDriver();
        else if(getDefaultWebDriverName().startsWith("chrome"))
            return new ChromeDriver();
        else if(getDefaultWebDriverName().startsWith("ff"))
            return new FirefoxDriver();
        else
            return new FirefoxDriver(); 
    }
    public ArrayList<WebDriver> getAvailableWebDrivers() {
       ArrayList<WebDriver> results = new ArrayList<WebDriver>();
       if(getHasIE().equalsIgnoreCase("true"))
           results.add( new InternetExplorerDriver());
       else if(getHasChrome().equalsIgnoreCase("true"))
           results.add( new ChromeDriver());
       else if(getHasFirefox().equalsIgnoreCase("true"))
           results.add( new FirefoxDriver());
       
       return results;
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
    public String getHasFirefox() {
        return hasFirefox;
    }
    public void setHasFirefox(String hasFirefox) {
        this.hasFirefox = hasFirefox;
    }
    public String getHasIE() {
        return hasIE;
    }
    public void setHasIE(String hasIE) {
        this.hasIE = hasIE;
    }
    public String getHasChrome() {
        return hasChrome;
    }
    public void setHasChrome(String hasChrome) {
        this.hasChrome = hasChrome;
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
}
