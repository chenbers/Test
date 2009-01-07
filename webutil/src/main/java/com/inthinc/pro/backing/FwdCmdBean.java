package com.inthinc.pro.backing;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import com.inthinc.pro.dao.DeviceDAO;
import com.inthinc.pro.model.Device;
import com.inthinc.pro.model.ForwardCommand;
import com.inthinc.pro.model.ForwardCommandID;
import com.inthinc.pro.model.ForwardCommandStatus;

public class FwdCmdBean 
{

    private String imei;
    private Integer fwdcmd;
    private Integer intData;
    private String stringData;
    private Device device;

    private UIInput imeiInput;
    
    private DeviceDAO deviceDAO;

    public FwdCmdBean()
    {
        super();
    }


    public Integer getFwdcmd()
    {
        return fwdcmd;
    }

    public void setFwdcmd(Integer fwdcmd)
    {
        this.fwdcmd = fwdcmd;
    }

    public Integer getIntData()
    {
        return intData;
    }

    public void setIntData(Integer intData)
    {
        this.intData = intData;
    }

    public String getStringData()
    {
        return stringData;
    }

    public void setStringData(String stringData)
    {
        this.stringData = stringData;
    }


    public void validateIMEI(FacesContext context, UIComponent component, Object value)
    {
        if (imeiInput.isValid())
        {
            try
            {
                device = deviceDAO.findByIMEI((String) value);
                if (device == null)
                {
                    FacesMessage message = new FacesMessage();
                    message.setSummary("A device with that IMEI does not exist.");
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    throw new ValidatorException(message);
                }
            }
            catch (Exception e)
            {
                // if this this bean begins to be used by more than QA, then
                // exception/message handling needs to be more refined
                FacesMessage message = new FacesMessage();
                message.setSummary("The IMEI does not exist or a data access problem occured");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message);
            }
        }
    }

    public void sendFwdCmdAction()
    {

        Object data = null;
        if (stringData != null && !stringData.isEmpty())
        {
            data = stringData;
        }
        else if (intData != null)
        {
            data = intData;
        }
        else
        {
            data = Integer.valueOf(0);
        }
        deviceDAO.queueForwardCommand(device.getDeviceID(), new ForwardCommand(0, ForwardCommandID.valueOf(fwdcmd), data, ForwardCommandStatus.STATUS_QUEUED));
    }


    public String getImei()
    {
        return imei;
    }


    public void setImei(String imei)
    {
        this.imei = imei;
    }


    public UIInput getImeiInput()
    {
        return imeiInput;
    }


    public void setImeiInput(UIInput imeiInput)
    {
        this.imeiInput = imeiInput;
    }


    public DeviceDAO getDeviceDAO()
    {
        return deviceDAO;
    }


    public void setDeviceDAO(DeviceDAO deviceDAO)
    {
        this.deviceDAO = deviceDAO;
    }
}
