package com.inthinc.pro.backing;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;

import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.dao.PersonDAO;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.util.MessageUtil;
import com.inthinc.pro.validators.EmailValidator;

public class MyAccountBean extends BaseBean
{
    private static final Logger logger = Logger.getLogger(MyAccountBean.class);

    private static final Map<String, Integer> ALERT_OPTIONS;

    static
    {
        // alert options
        ALERT_OPTIONS = new LinkedHashMap<String, Integer>();
        for (int i = 0; i < 8; i++)
            ALERT_OPTIONS.put(MessageUtil.getMessageString("myAccount_alertText" + i), i);
    }

    private PersonDAO personDAO;
    private DriverDAO driverDAO;
    
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
        personDAO.update(getUser().getPerson());
        return "go_myAccount";
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
            getUser().getPerson().setDriver(driverDAO.getDriverByPersonID(getUser().getPersonID()));
        if (getUser().getPerson().getDriver() != null)
        {
            final Group group = getGroupHierarchy().getGroup(getUser().getPerson().getDriver().getGroupID());
            if (group != null)
                return group.getName();
        }
        return null;
    }

    public Map<String, Integer> getAlertOptions()
    {
        return ALERT_OPTIONS;
    }

    public void setPersonDAO(PersonDAO personDAO)
    {
        this.personDAO = personDAO;
    }

    public void setDriverDAO(DriverDAO driverDAO)
    {
        this.driverDAO = driverDAO;
    }
}
