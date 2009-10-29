package com.inthinc.pro.backing;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.log4j.Logger;
import org.apache.shale.test.base.AbstractJsfTestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.security.context.SecurityContext;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.context.SecurityContextImpl;
import org.springframework.security.providers.ProviderManager;
import org.springframework.security.providers.TestingAuthenticationProvider;
import org.springframework.security.providers.TestingAuthenticationToken;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.inthinc.pro.backing.model.GroupHierarchy;
import com.inthinc.pro.dao.GroupDAO;
import com.inthinc.pro.dao.ScoreDAO;
import com.inthinc.pro.dao.UserDAO;
import com.inthinc.pro.dao.mock.data.MockData;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.User;
import com.inthinc.pro.security.userdetails.ProUser;

@RunWith(SpringJUnit4ClassRunner.class)

// any classes that extend this one will also have access to these configurations
@ContextConfiguration(locations={"classpath:spring/applicationContext-mockdao.xml",
                                 "classpath:spring/applicationContext-daoBeans.xml",
                                 "classpath:spring/applicationContext-appBeans.xml",
                                 "classpath:spring/applicationContext-beans.xml",
                                 "classpath:spring/applicationContext-security.xml",
                                 "classpath:spring/applicationContext-reports.xml"},
                                 loader=com.inthinc.pro.spring.test.WebSessionContextLoader.class)

                                 
// implementing ApplicationContextAware give the test class access to the ApplicationContext                            
public class BaseBeanTest extends AbstractJsfTestCase implements ApplicationContextAware
{
    public BaseBeanTest()
    {
        super(null);
    }

    public BaseBeanTest(String name)
    {
        super(name);
    }

    private static final Logger logger = Logger.getLogger(BaseBeanTest.class);
    protected ApplicationContext applicationContext;
    public String test;
    
    @BeforeClass
    public static void runOnceBeforeAllTests() throws Exception
    {
        System.setProperty("log4j.configuration", "classpath*:log4j.properties");
        
        // initializes the mock data
        MockData.getInstance();
    }

    @Before
    @Override
    public void setUp() throws Exception
    {
        super.setUp();
        application.setMessageBundle("com.inthinc.pro.resources.Messages");
    }

    @After
    @Override
    public void tearDown() throws Exception
    {
        super.tearDown();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException
    {
        this.applicationContext = applicationContext;
        
    }
    
    
    protected void mockLogin(ProUser proUser)
    {
        // simulates a user logging in

        // USER
        logger.debug("logging in " + proUser.getUsername());
        
        
        TestingAuthenticationToken testToken = new TestingAuthenticationToken(
                proUser, proUser.getPassword(),
                proUser.getAuthorities());

        // AUTHENTICATION PROVIDER
        ProviderManager providerManager = (ProviderManager) applicationContext.getBean("authenticationManager");
        List<TestingAuthenticationProvider> list = new ArrayList<TestingAuthenticationProvider>();
        list.add(new TestingAuthenticationProvider());
        providerManager.setProviders(list);
        
        // Create and store the Spring SecurityContext into the SecurityContextHolder.
        SecurityContext securityContext = new SecurityContextImpl();
        testToken.setAuthenticated(true);
        securityContext.setAuthentication(testToken);
        SecurityContextHolder.setContext(securityContext);
        
    }
    
    protected ProUser loginUser(String username)
    {
        User user = getUserDAO().findByUserName(username);
        String roleName = user.getRole().toString();
        ProUser proUser = new ProUser(user,roleName);
        mockLogin(proUser);
        // TODO: this is a bit of a kludge -- probably can include our authentication provider in the list when logging in here
        initGroupHierarchy();
        return proUser;
        
    }
    
    private GroupHierarchy initGroupHierarchy()
    {
        ProUser proUser = (ProUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        
        Integer topGroupID = proUser.getUser().getGroupID();
        
        GroupDAO groupDAO = (GroupDAO)applicationContext.getBean("groupDAO");
        
        // TODO: re-factor when back end methods getGroupsByGroupIDDeep is available
        Group topGroup = groupDAO.findByID(proUser.getUser().getGroupID());
        List<Group> groupList = groupDAO.getGroupHierarchy(topGroup.getAccountID(), topGroupID);
        
        GroupHierarchy groupHierarchy = new GroupHierarchy(groupList);
        
        proUser.setGroupHierarchy(groupHierarchy);
        
        return groupHierarchy;
    }

    
    // returns the logged on user 
    public User getUser()
    {
        return getProUser().getUser();
    }
    
    public ProUser getProUser()
    {
        return (ProUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    
    protected ScoreDAO getScoreDAO()
    {
        return (ScoreDAO)applicationContext.getBean("scoreDAO");
    }
    protected UserDAO getUserDAO()
    {
        return (UserDAO)applicationContext.getBean("userDAO");
    }

    @Test
    public void dummy()
    {
        assertEquals("", 1,1);
    }
    
	protected String fillInMonths(String expectedChartXml, int numMonths) 
	{
        Calendar todayCal = Calendar.getInstance();
        todayCal.setTime(new Date());

        Object[] month = new String[numMonths];
        for (int i = numMonths-1; i >=0; i--)
        {
        	month[i] = todayCal.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.getDefault());
        	todayCal.add(Calendar.MONTH, -1);
        	System.out.println(i + " " + month[i]);
        }


		return MessageFormat.format(expectedChartXml, month);
	}
    

}
