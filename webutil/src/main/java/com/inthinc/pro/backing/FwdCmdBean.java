package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.faces.validator.ValidatorException;

import org.richfaces.model.selection.Selection;

import com.inthinc.pro.dao.AccountDAO;
import com.inthinc.pro.dao.DeviceDAO;
import com.inthinc.pro.model.Account;
import com.inthinc.pro.model.Device;
import com.inthinc.pro.model.ForwardCommand;
import com.inthinc.pro.model.ForwardCommandStatus;

public class FwdCmdBean 
{

    //Forward Command Excetion
    private String imei;
    private Integer fwdcmd;
    private Integer intData;
    private String  stringData;
    private Device  device;
    private UIInput imeiInput;
    //Device Selection
    private List<SelectItem> accountList;
    private Integer acctID;
    private String  userName;
    private List<Device> devices;
    private List<Device> selectedDevices = new ArrayList<Device>();
    private Selection deviceSelection;

    
    private DeviceDAO deviceDAO;
    private AccountDAO accountDAO;
    
  
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
//            try
//            {
                device = deviceDAO.findByIMEI((String) value);
                if (device == null)
                {
                    FacesMessage message = new FacesMessage();
                    message.setSummary("A device with that IMEI does not exist.");
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    throw new ValidatorException(message);
                }
//            }
//            catch (Exception e)
//            {
//                // if this this bean begins to be used by more than QA, then
//                // exception/message handling needs to be more refined
//                FacesMessage message = new FacesMessage();
//                message.setSummary("The IMEI does not exist or a data access problem occured");
//                message.setSeverity(FacesMessage.SEVERITY_ERROR);
//                throw new ValidatorException(message);
//            }
        }
    }
    
    public void loadDevices()
    {
        if(acctID != null)
        {
            devices = new ArrayList<Device>();
            devices = deviceDAO.getDevicesByAcctID(acctID);
        }
        else
            devices = new ArrayList<Device>();
        
        selectedDevices.clear();
        
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
        deviceDAO.queueForwardCommand(device.getDeviceID(), new ForwardCommand(0, fwdcmd, data, ForwardCommandStatus.STATUS_QUEUED));
    }
    
    public String importSelection()
    {
        Iterator<Object> iterator = deviceSelection.getKeys();
        while(iterator.hasNext())
        {
            selectedDevices.add((Device)iterator.next());
        }
        
        StringBuilder sb = new StringBuilder();
        if(imei != null)
           sb.append(imei);
        for(Device device:selectedDevices)
        {
            sb.append(device.getDeviceID());
            sb.append(",");
        }
        
        imei = sb.toString();
        return null;
    }
    
    public String search()
    {
        return null;
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


    public void setAccountDAO(AccountDAO accountDAO)
    {
        this.accountDAO = accountDAO;
    }


    public AccountDAO getAccountDAO()
    {
        return accountDAO;
    }


    public void setAccountList(List<SelectItem> accountList)
    {
        this.accountList = accountList;
    }

    public List<SelectItem> getAccountList()
    {
        if(accountList == null)
        {
            accountList = new ArrayList<SelectItem>();
           
            List<Account> accounts = accountDAO.getAllAcctIDs();
            for(Account account: accounts)
            {
                accountList.add(new SelectItem(account.getAcctID(),account.getAcctName() == null ? account.getAcctID().toString() : account.getAcctName()));
            }
            accountList.set(0,new SelectItem(null,""));
        }
        return accountList;
    }


    public void setAcctID(Integer acctID)
    {
        this.acctID = acctID;
    }


    public Integer getAcctID()
    {
        return acctID;
    }


    public void setUserName(String userName)
    {
        this.userName = userName;
    }


    public String getUserName()
    {
        return userName;
    }


    public void setDevices(List<Device> devices)
    {
        this.devices = devices;
    }


    public List<Device> getDevices()
    {
        return devices;
    }


    public void setSelectedDevices(List<Device> selectedDevices)
    {
        this.selectedDevices = selectedDevices;
    }


    public List<Device> getSelectedDevices()
    {
        return selectedDevices;
    }

    public void setDeviceSelection(Selection deviceSelection)
    {
        this.deviceSelection = deviceSelection;
    }


    public Selection getDeviceSelection()
    {
        return deviceSelection;
    }
}
