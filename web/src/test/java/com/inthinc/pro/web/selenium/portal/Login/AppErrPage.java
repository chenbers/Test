package com.inthinc.pro.web.selenium.portal.Login;

import static junit.framework.Assert.assertTrue;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.inthinc.pro.web.selenium.ByEnum;
import com.inthinc.pro.web.selenium.CoreMethodLib;
import com.inthinc.pro.web.selenium.GlobalSelenium;
import com.inthinc.pro.web.selenium.Page;

public class AppErrPage implements Page {

    protected CoreMethodLib selenium; //TODO: jwimmer: should? this be static?
    private final WebDriver driver;
    @FindBy(name = "j_username")
    private WebElement fieldUsername;
    @FindBy(name = "j_password")
    private WebElement fieldPassword;
    @FindBy(id = "loginLogin")

    
    //TODO: jwimmer: these need to be in .properties or someplace they can be dynamically populated/injected
    private String  server = "qa-pro.inthinc.com";
    private Integer port = 8082;
    private String  appName = "tiwipro";
    private String  pagePath = "/login";

    public void field_password_type(String input) {
        fieldPassword.clear();
        fieldPassword.sendKeys(input);
    }
    public void field_username_type(String input) {
        fieldUsername.clear();
        fieldUsername.sendKeys(input);
    }
    public void btn_login_click() {
        getBtnLogin().click();
    }
    public void page_action_login(String username, String password) {
        fieldUsername.clear();
        fieldUsername.sendKeys(username);
        
        fieldPassword.clear();
        fieldPassword.sendKeys(password);
        
        getBtnLogin().click();
    }
    public void page_validate_loginSuccess() {
        selenium.wait_for_element_present("Admin", "link");//TODO:jwimmer: probably in NavigationBarEnum
    }
    /*
     * Constructors
     */
    public AppErrPage(WebDriver driver) {
        this.driver = driver;
        this.selenium = GlobalSelenium.getYourOwn();
        load();
        PageFactory.initElements(driver, this);
    }
    
    @Override
    public void isLoaded() {
        // TODO Auto-generated method stub
        String url = driver.getCurrentUrl();
        assertTrue("ERROR: "+LoginEnum.URL.name(), url.endsWith(LoginEnum.URL.getID()));
        
    }

    @Override
    public void load() {
        // TODO Auto-generated method stub
        driver.get("http://"+server+":"+port+"/"+appName+pagePath);
    }

    public WebElement getBtnLogin() {
        return driver.findElement(ByEnum.seleniumEnum(LoginEnum.LOGIN_BUTTON));
    }
    @Override
    public WebDriver getDriver() {
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public String getPath() {
        // TODO Auto-generated method stub
        return null;
    }
}
