package com.inthinc.pro.backing;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;

import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.dao.PersonDAO;
import com.inthinc.pro.dao.UserDAO;
import com.inthinc.pro.dao.hessian.exceptions.DuplicateEmailException;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.util.MessageUtil;
import com.inthinc.pro.validators.EmailValidator;
public class MyAccountBean extends BaseBean
{
    private static final Logger logger = Logger.getLogger(MyAccountBean.class);
/*    
    private static final Map<String, Integer> ALERT_OPTIONS;
    static
    {
        // alert options
        ALERT_OPTIONS = new LinkedHashMap<String, Integer>();
        for (int i = 0; i < 8; i++)
            if (i != 5) // skip cell phone
                ALERT_OPTIONS.put(MessageUtil.getMessageString("myAccount_alertText" + i), i);
    }
*/    
    private PersonDAO personDAO;
    private UserDAO userDAO;
    private DriverDAO driverDAO;
    private HelpBean helpBean;
//    private AccountOptionsBean accountOptionsBean;

    public MyAccountBean()
    {
        super();
    }


    public void validateEmail(FacesContext context, UIComponent component, Object value)
    {
        String valueStr = (String) value;
        if (valueStr != null && valueStr.length() > 0)
        {
            new EmailValidator().validate(context, component, value);
        }
    }
    public String saveFormAction()
    {
        boolean valid = PersonBean.validatePreferedNotifications(FacesContext.getCurrentInstance(), "my_form:editAccount-info",  getUser().getPerson());
        String result = null;
        try
        {
            personDAO.update(getUser().getPerson());
        }
        catch (DuplicateEmailException ex)
        {
            FacesContext context = FacesContext.getCurrentInstance();
            FacesMessage message = new FacesMessage();
            message.setSummary(MessageUtil.getMessageString("editPerson_uniqueEmail") + " " + getUser().getPerson().getPriEmail());
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            context.addMessage("my_form:editAccount-priEmail", message);
            restorePerson();
            valid = false;
        }
        
        if(valid) {
            userDAO.update(getUser());
            result = "pretty:myAccount";
        }
        return result;
    }

    private void restorePerson()
    {
        getUser().setPerson(personDAO.findByID(getUser().getPerson().getPersonID()));
    }

    public String getRegionName()
    {
        final Group group = getGroupHierarchy().getGroup(getUser().getGroupID());
        if (group != null)
            return group.getName();
        return null;
    }

    public String getTeamName()
    {
        if (getUser().getPerson().getDriver() == null)
            getUser().getPerson().setDriver(driverDAO.findByPersonID(getUser().getPersonID()));
        if (getUser().getPerson().getDriver() != null)
        {
            final Group group = getGroupHierarchy().getGroup(getUser().getPerson().getDriver().getGroupID());
            if (group != null)
                return group.getName();
        }
        return null;
    }

    public Map<String, Integer> getAlertOptions() {
        // alert options
        
        LinkedHashMap<String, Integer> alertOptions = new LinkedHashMap<String, Integer>();
        for (int i = 0; i < 8; i++) {
            if (i == 5 ||  // skip cell phone 
              (!isEnablePhoneAlerts() && (i == 3 || i == 4)))  // skip phone alerts if account is set to this
            	continue;
            alertOptions.put(MessageUtil.getMessageString("myAccount_alertText" + i), i);
        }
        return alertOptions;
    }

    public void setPersonDAO(PersonDAO personDAO)
    {
        this.personDAO = personDAO;
    }

    public void setDriverDAO(DriverDAO driverDAO)
    {
        this.driverDAO = driverDAO;
    }

    public void setUserDAO(UserDAO userDAO)
    {
        this.userDAO = userDAO;
    }
    public HelpBean getHelpBean() {
		return helpBean;
	}

	public void setHelpBean(HelpBean helpBean) {
		this.helpBean = helpBean;
	}

/*    
    public AccountOptionsBean getAccountOptionsBean() {
		return accountOptionsBean;
	}

	public void setAccountOptionsBean(AccountOptionsBean accountOptionsBean) {
		this.accountOptionsBean = accountOptionsBean;
	}
*/
	public Integer getInfo()
	{
		return validAccountAlertValue(getUser().getPerson().getInfo());
	}


	public Integer getWarn()
	{
		return validAccountAlertValue(getUser().getPerson().getWarn());
		
	}
	public Integer getCrit()
	{
		return  validAccountAlertValue(getUser().getPerson().getCrit());
	}
	private Integer validAccountAlertValue(Integer value) {
        if (value == null || 
        	value == 5 ||  // skip cell phone 
           (!isEnablePhoneAlerts() && (value == 3 || value == 4)))  // skip phone alerts if account is set to this
           return 0;
		return value;
	}
	
	
}
