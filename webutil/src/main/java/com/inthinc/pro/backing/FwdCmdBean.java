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
import com.inthinc.pro.dao.GroupDAO;
import com.inthinc.pro.dao.UserDAO;
import com.inthinc.pro.model.Account;
import com.inthinc.pro.model.Device;
import com.inthinc.pro.model.ForwardCommand;
import com.inthinc.pro.model.ForwardCommandStatus;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.User;

public class FwdCmdBean
{

    // Forward Command Excetion
    private Integer fwdcmd;
    private Integer intData;
    private String stringData;
    private UIInput imeiInput;
    // Device Selection
    private List<SelectItem> accountList;
    private Integer acctID;
    private String userName;
    private List<Device> devices;
    private List<Device> selectedDevices = new ArrayList<Device>();
    private Selection deviceSelection;

    private DeviceDAO deviceDAO;
    private AccountDAO accountDAO;
    private UserDAO userDAO;
    private GroupDAO groupDAO;
    private Integer page;

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

    public void loadDevices()
    {
        if (acctID != null)
        {
            devices = new ArrayList<Device>();
            devices = deviceDAO.getDevicesByAcctID(acctID);
        }
        else if(userName != null)
        {
            
            User user = userDAO.findByUserName(userName);
            if(user != null)
            {
                Group group = groupDAO.findByID(user.getGroupID());
                devices = new ArrayList<Device>();
                devices = deviceDAO.getDevicesByAcctID(group.getAccountID());
            }
        }
        else
            devices = new ArrayList<Device>();

        selectedDevices.clear();
    }

    public void sendFwdCmdAction()
    {
        selectedDevices.clear();
        Iterator<Object> iterator = deviceSelection.getKeys();
        while(iterator.hasNext())
        {
            selectedDevices.add(devices.get((Integer)iterator.next()));
        }
        
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
        
        for(Device device:selectedDevices)
        {
            deviceDAO.queueForwardCommand(device.getDeviceID(), new ForwardCommand(0, fwdcmd, data, ForwardCommandStatus.STATUS_QUEUED));
        }
       
    }
    
    public void clearSelection()
    {
        selectedDevices.clear();
        page = 1;
    }

    public String search()
    {
        return null;
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
        if (accountList == null)
        {
            accountList = new ArrayList<SelectItem>();

            List<Account> accounts = accountDAO.getAllAcctIDs();
            for (Account account : accounts)
            {
                accountList.add(new SelectItem(account.getAcctID(), account.getAcctName() == null ? account.getAcctID().toString() : account.getAcctName()));
            }
            accountList.set(0, new SelectItem(null, ""));
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

    public void setUserDAO(UserDAO userDAO)
    {
        this.userDAO = userDAO;
    }

    public UserDAO getUserDAO()
    {
        return userDAO;
    }

    public void setGroupDAO(GroupDAO groupDAO)
    {
        this.groupDAO = groupDAO;
    }

    public GroupDAO getGroupDAO()
    {
        return groupDAO;
    }

    public void setPage(Integer page)
    {
        this.page = page;
    }

    public Integer getPage()
    {
        return page;
    }
}
