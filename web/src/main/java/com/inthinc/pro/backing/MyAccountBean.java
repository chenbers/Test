package com.inthinc.pro.backing;

import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.component.UISelectOne;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.springframework.context.MessageSource;

import com.inthinc.pro.backing.model.GroupHierarchy;
import com.inthinc.pro.backing.model.GroupLevel;
import com.inthinc.pro.dao.AlertContactDAO;
import com.inthinc.pro.dao.GroupDAO;
import com.inthinc.pro.model.AlertContact;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.GroupType;
import com.inthinc.pro.model.User;

public class MyAccountBean extends BaseBean
{
    private static final Logger logger = Logger.getLogger(MyAccountBean.class);

    private MessageSource messageSource;
    private AlertContactDAO alertContactDAO;
    private GroupDAO groupDAO;
    private AlertContact alertContact;

    private String regionName;
    private String teamName;

    private String informationSelectType;
    private String informationSelectEmail;
    private String informationSelectPhone;
    private String informationSelectText;
    
    private String warningSelectType;
    private String warningSelectEmail;
    private String warningSelectPhone;
    private String warningSelectText;

    private String criticalSelectType;
    private String criticalSelectEmail;
    private String criticalSelectPhone;
    private String criticalSelectText;
    
    private String primaryEmail;
    private String secondaryEmail;
    private String primaryPhone;
    private String secondaryPhone;
    private String cellPhone;
    private String primaryText;
    private String secondaryText;
    
    public MyAccountBean()
    {
        super();
    }
    
    public void validateForm(FacesContext context, UIComponent component, Object value)
    {
    //    String pE = (String) primaryEmailInput.getLocalValue();
    //    pE = (String) primaryEmailInput.getValue();
        logger.info("validatingForm");
    }

    public String saveFormAction()
    {
        User user = getUser();
        AlertContact alertContact = alertContactDAO.findByUserID(user.getUserID());
        boolean isNew = false;
        if (alertContact == null)
        {
            isNew = true;
            alertContact = new AlertContact();
        }
        alertContact.setPriEmail(primaryEmail);
        alertContact.setSecEmail(secondaryEmail);
        alertContact.setPriPhone(primaryPhone);
        alertContact.setSecPhone(secondaryPhone);
        alertContact.setCellPhone(cellPhone);
        alertContact.setPriText(primaryText);
        alertContact.setSecText(secondaryText);

        if ("0".equals(informationSelectType)) alertContact.setInfo(0);
        else if ("1".equals(informationSelectType)) alertContact.setInfo(new Integer(informationSelectEmail));
        else if ("2".equals(informationSelectType)) alertContact.setInfo(new Integer(informationSelectPhone));
        else if ("3".equals(informationSelectType)) alertContact.setInfo(new Integer(informationSelectText));

        if ("0".equals(warningSelectType)) alertContact.setInfo(0);
        else if ("1".equals(warningSelectType)) alertContact.setInfo(new Integer(warningSelectEmail));
        else if ("2".equals(warningSelectType)) alertContact.setInfo(new Integer(warningSelectPhone));
        else if ("3".equals(warningSelectType)) alertContact.setInfo(new Integer(warningSelectText));

        if ("0".equals(criticalSelectType)) alertContact.setInfo(0);
        else if ("1".equals(criticalSelectType)) alertContact.setInfo(new Integer(criticalSelectEmail));
        else if ("2".equals(criticalSelectType)) alertContact.setInfo(new Integer(criticalSelectPhone));
        else if ("3".equals(criticalSelectType)) alertContact.setInfo(new Integer(criticalSelectText));
        
        if (isNew)
        {
            alertContactDAO.create(alertContact.getUserID(), alertContact);
        }
        else
        {
            alertContactDAO.update(alertContact);
        }
        return "go_myAccount";
    }

    public AlertContact getAlertContact()
    {
        if (alertContact == null)
        {
            alertContact = alertContactDAO.findByUserID(getUser().getUserID());
            if (alertContact == null)
            {
                alertContact = new AlertContact();
            }
        }
        return alertContact;
    }
    
    public String getFullName()
    {
        return getUser().getPerson().getFullName();
    }
    
    public String getRegionName()
    {
        if (true || regionName == null)
        {
            Group group = groupDAO.findByID(getUser().getPerson().getGroupID());
            if (group != null && group.getType() == GroupType.DIVISION)
            {
                regionName = group.getName();
            }
            else if (group != null)
            {
                group = groupDAO.findByID(group.getParentID());
                if (group != null && group.getType() == GroupType.DIVISION)
                {
                    regionName = group.getName();
                }
            }
        }
        return regionName;
    }

    public String getTeamName()
    {
        if (teamName == null)
        {
            Group group = groupDAO.findByID(getUser().getPerson().getGroupID());
            if (group != null && group.getType() == GroupType.TEAM)
            {
                regionName = group.getName();
            }
        }
        return teamName;
    }

    public MessageSource getMessageSource()
    {
        return messageSource;
    }

    public void setMessageSource(MessageSource messageSource)
    {
        this.messageSource = messageSource;
    }

    public AlertContactDAO getAlertContactDAO()
    {
        return alertContactDAO;
    }

    public void setAlertContactDAO(AlertContactDAO alertContactDAO)
    {
        this.alertContactDAO = alertContactDAO;
    }

    public GroupDAO getGroupDAO()
    {
        return groupDAO;
    }

    public void setGroupDAO(GroupDAO groupDAO)
    {
        this.groupDAO = groupDAO;
    }

    public String getInformationSelectType()
    {
        if (getAlertContact() != null && getAlertContact().getInfo() != null)
        {
            if (getAlertContact().getInfo() == 0) informationSelectType = "0";
            else if (getAlertContact().getInfo() <= 2) informationSelectType = "1";
            else if (getAlertContact().getInfo() <= 5) informationSelectType = "2";
            else if (getAlertContact().getInfo() <= 7) informationSelectType = "3";
        }
        return informationSelectType;
    }

    public void setInformationSelectType(String informationSelectType)
    {
        this.informationSelectType = informationSelectType;
    }

    public String getInformationSelectEmail()
    {
        if (getAlertContact() != null && getAlertContact().getInfo() != null)
        {
            informationSelectEmail = getAlertContact().getInfo().toString();
        }
        return informationSelectEmail;
    }

    public void setInformationSelectEmail(String informationSelectEmail)
    {
        this.informationSelectEmail = informationSelectEmail;
    }

    public String getInformationSelectPhone()
    {
        if (getAlertContact() != null && getAlertContact().getInfo() != null)
        {
            informationSelectPhone = getAlertContact().getInfo().toString();
        }
        return informationSelectPhone;
    }

    public void setInformationSelectPhone(String informationSelectPhone)
    {
        this.informationSelectPhone = informationSelectPhone;
    }

    public String getInformationSelectText()
    {
        if (getAlertContact() != null && getAlertContact().getInfo() != null)
        {
            informationSelectText = getAlertContact().getInfo().toString();
        }
        return informationSelectText;
    }

    public void setInformationSelectText(String informationSelectText)
    {
        this.informationSelectText = informationSelectText;
    }

    public String getPrimaryEmail()
    {
        return primaryEmail;
    }

    public void setPrimaryEmail(String primaryEmail)
    {
        this.primaryEmail = primaryEmail;
    }

    public String getSecondaryEmail()
    {
        return secondaryEmail;
    }

    public void setSecondaryEmail(String secondaryEmail)
    {
        this.secondaryEmail = secondaryEmail;
    }

    public String getPrimaryPhone()
    {
        return primaryPhone;
    }

    public void setPrimaryPhone(String primaryPhone)
    {
        this.primaryPhone = primaryPhone;
    }

    public String getSecondaryPhone()
    {
        return secondaryPhone;
    }

    public void setSecondaryPhone(String secondaryPhone)
    {
        this.secondaryPhone = secondaryPhone;
    }

    public String getCellPhone()
    {
        return cellPhone;
    }

    public void setCellPhone(String cellPhone)
    {
        this.cellPhone = cellPhone;
    }

    public String getPrimaryText()
    {
        return primaryText;
    }

    public void setPrimaryText(String primaryText)
    {
        this.primaryText = primaryText;
    }

    public String getSecondaryText()
    {
        return secondaryText;
    }

    public void setSecondaryText(String secondaryText)
    {
        this.secondaryText = secondaryText;
    }

    public String getWarningSelectType()
    {
        if (getAlertContact() != null && getAlertContact().getWarn() != null)
        {
            if (getAlertContact().getWarn() == 0) warningSelectType = "0";
            else if (getAlertContact().getWarn() <= 2) warningSelectType = "1";
            else if (getAlertContact().getWarn() <= 5) warningSelectType = "2";
            else if (getAlertContact().getWarn() <= 7) warningSelectType = "3";
        }
        return warningSelectType;
    }

    public void setWarningSelectType(String warningSelectType)
    {
        this.warningSelectType = warningSelectType;
    }

    public String getWarningSelectEmail()
    {
        if (getAlertContact() != null && getAlertContact().getWarn() != null)
        {
            warningSelectEmail = getAlertContact().getWarn().toString();
        }
        return warningSelectEmail;
    }

    public void setWarningSelectEmail(String warningSelectEmail)
    {
        this.warningSelectEmail = warningSelectEmail;
    }

    public String getWarningSelectPhone()
    {
        if (getAlertContact() != null && getAlertContact().getWarn() != null)
        {
            warningSelectPhone = getAlertContact().getWarn().toString();
        }
        return warningSelectPhone;
    }

    public void setWarningSelectPhone(String warningSelectPhone)
    {
        this.warningSelectPhone = warningSelectPhone;
    }

    public String getWarningSelectText()
    {
        if (getAlertContact() != null && getAlertContact().getWarn() != null)
        {
            warningSelectText = getAlertContact().getWarn().toString();
        }
        return warningSelectText;
    }

    public void setWarningSelectText(String warningSelectText)
    {
        this.warningSelectText = warningSelectText;
    }

    public String getCriticalSelectType()
    {
        if (getAlertContact() != null && getAlertContact().getCrit() != null)
        {
            if (getAlertContact().getCrit() == 0) criticalSelectType = "0";
            else if (getAlertContact().getCrit() <= 2) criticalSelectType = "1";
            else if (getAlertContact().getCrit() <= 5) criticalSelectType = "2";
            else if (getAlertContact().getCrit() <= 7) criticalSelectType = "3";
        }
        return criticalSelectType;
    }

    public void setCriticalSelectType(String criticalSelectType)
    {
        this.criticalSelectType = criticalSelectType;
    }

    public String getCriticalSelectEmail()
    {
        if (getAlertContact() != null && getAlertContact().getCrit() != null)
        {
            criticalSelectEmail = getAlertContact().getCrit().toString();
        }
        return criticalSelectEmail;
    }

    public void setCriticalSelectEmail(String criticalSelectEmail)
    {
        this.criticalSelectEmail = criticalSelectEmail;
    }

    public String getCriticalSelectPhone()
    {
        if (getAlertContact() != null && getAlertContact().getCrit() != null)
        {
            criticalSelectPhone = getAlertContact().getCrit().toString();
        }
        return criticalSelectPhone;
    }

    public void setCriticalSelectPhone(String criticalSelectPhone)
    {
        this.criticalSelectPhone = criticalSelectPhone;
    }

    public String getCriticalSelectText()
    {
        if (getAlertContact() != null && getAlertContact().getCrit() != null)
        {
            criticalSelectText = getAlertContact().getCrit().toString();
        }
        return criticalSelectText;
    }

    public void setCriticalSelectText(String criticalSelectText)
    {
        this.criticalSelectText = criticalSelectText;
    }

}
