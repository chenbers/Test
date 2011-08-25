package com.inthinc.pro.selenium.testSuites;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.inthinc.pro.automation.enums.TextEnum;
import com.inthinc.pro.automation.selenium.AbstractPage;
import com.inthinc.pro.selenium.pageEnums.AdminTables.AdminUsersEntries;
import com.inthinc.pro.selenium.pageObjects.PageAddEditUser;
import com.inthinc.pro.selenium.pageObjects.PageAdminUserDetails;
import com.inthinc.pro.selenium.pageObjects.PageAdminUsers;
import com.inthinc.pro.selenium.pageObjects.PageAdminVehicleView;
import com.inthinc.pro.selenium.pageObjects.PageAdminVehicles;
import com.inthinc.pro.selenium.pageObjects.PageChangePassword;
import com.inthinc.pro.selenium.pageObjects.PageDriverPerformance;
import com.inthinc.pro.selenium.pageObjects.PageDriverPerformanceSeatBelt;
import com.inthinc.pro.selenium.pageObjects.PageDriverPerformanceSpeed;
import com.inthinc.pro.selenium.pageObjects.PageDriverPerformanceStyle;
import com.inthinc.pro.selenium.pageObjects.PageDriverPerformanceTrips;
import com.inthinc.pro.selenium.pageObjects.PageReportsDrivers;
import com.inthinc.pro.selenium.pageObjects.PageExecutiveDashboard;
import com.inthinc.pro.selenium.pageObjects.PageReportsIdling;
import com.inthinc.pro.selenium.pageObjects.PageLiveFleet;
import com.inthinc.pro.selenium.pageObjects.PageLogin;
import com.inthinc.pro.selenium.pageObjects.PageMyAccount;
import com.inthinc.pro.selenium.pageObjects.PageNotificationsRedFlags;
import com.inthinc.pro.selenium.pageObjects.PageNotificationsSafety;
import com.inthinc.pro.selenium.pageObjects.PageReportsDevices;
import com.inthinc.pro.selenium.pageObjects.PageTeamDashboardStatistics;
import com.inthinc.pro.selenium.pageObjects.PageTeamLiveTeam;
import com.inthinc.pro.selenium.pageObjects.PageTeamOverallScore;
import com.inthinc.pro.selenium.pageObjects.PageTeamStops;
import com.inthinc.pro.selenium.pageObjects.PageTeamTrips;
import com.inthinc.pro.selenium.pageObjects.PageUserDetails;
import com.inthinc.pro.selenium.pageObjects.PageVehiclePerformance;
import com.inthinc.pro.selenium.pageObjects.PageVehiclePerformanceSeatBelt;
import com.inthinc.pro.selenium.pageObjects.PageVehiclePerformanceSpeed;
import com.inthinc.pro.selenium.pageObjects.PageVehiclePerformanceStyle;
import com.inthinc.pro.selenium.pageObjects.PageVehiclePerformanceTrips;
import com.inthinc.pro.selenium.pageObjects.PageReportsVehicles;
import com.thoughtworks.selenium.SeleniumException;

public class JwimmerSandboxTest extends WebTest {
    
    private PageLogin l;
    private PageLiveFleet liveFleet;
    private String username = "jwimmer";
    private String password = "password";
    private String NONADMIN_USERNAME = "jwimmerCASE"; //TODO: jwimmer: dtanner: users/password The problem is with test cases using the same user they CAN end up stomping on each other and inadvertently fail/pass the test
    private String NONADMIN_PASSWORD = "password";
    private String ADMIN_USERNAME = "mraby";
    private String ADMIN_PASSWORD = "password";	
    private String EMAIL_KNOWN = "jwimmer@inthinc.com";
    private String EMAIL_UNKNOWN = "jaaacen@gmail.com";
    private String EMAIL_INVALID = "username_at_domain_dot_tld";
    PageAdminUsers users = new PageAdminUsers();
    private String param;
    
    @Before
    public void setupPage() {

    }
}
