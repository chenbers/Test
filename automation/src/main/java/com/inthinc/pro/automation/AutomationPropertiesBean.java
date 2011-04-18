package com.inthinc.pro.automation;

import java.util.ArrayList;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;


public class AutomationPropertiesBean {
    private String serverName;
    private String serverPort;
    private String appPath;
    private String defaultWebDriverName;
    private String hasFirefox;
    private String hasIE;
    private String hasChrome;

    public WebDriver getDefaultWebDriver() {
        //TODO: jwimmer: replace with switch/case
        if(getDefaultWebDriverName().equalsIgnoreCase("ie"))
            return new InternetExplorerDriver();
        else if(getDefaultWebDriverName().equalsIgnoreCase("chrome"))
            return new ChromeDriver();
        else if(getDefaultWebDriverName().equalsIgnoreCase("firefox"))
            return new FirefoxDriver();
        else
            return null; //TODO: jwimmer: how do we want to handle boxes with NO default browser set!
    }
    public ArrayList<WebDriver> getAvailableWebDrivers() {
       ArrayList<WebDriver> results = new ArrayList<WebDriver>();
       //TODO: jwimmer: use switch/case
       if(getHasIE().equalsIgnoreCase("true"))
           results.add( new InternetExplorerDriver());
       else if(getDefaultWebDriverName().equalsIgnoreCase("chrome"))
           results.add( new ChromeDriver());
       else if(getDefaultWebDriverName().equalsIgnoreCase("firefox"))
           results.add( new FirefoxDriver());
       
       return results;
    }
    public String getBaseURL() {
        return "https://"+serverName+":"+serverPort+"/"+appPath+"/";
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
}
