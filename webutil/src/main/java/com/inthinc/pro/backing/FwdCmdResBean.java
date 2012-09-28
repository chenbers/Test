package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import com.inthinc.pro.backing.model.DeviceItem;
import com.inthinc.pro.dao.DeviceDAO;
import com.inthinc.pro.dao.ForwardCommandDefDAO;
import com.inthinc.pro.model.Device;
import com.inthinc.pro.model.ForwardCommand;
import com.inthinc.pro.model.ForwardCommandDef;
import com.inthinc.pro.model.ForwardCommandStatus;
import com.inthinc.pro.model.ForwardCommandType;

public class FwdCmdResBean extends BaseBean {

	private Integer fwdcmd;
	private Integer intData;
	private String stringData;

	private Integer acctID;
	private List<DeviceItem> devices;
	private List<DeviceItem> selectedDevices;
	private DeviceItem filter;


	private DeviceDAO deviceDAO;
	private ForwardCommandDefDAO forwardCommandDefDAO;
	
	Map<Integer, ForwardCommandDef> forwardCommandDefMap;
	List<SelectItem> forwardCommandSelectList;
	

	private boolean               selectAll;


	public FwdCmdResBean() {
		super();
	}

	public void init() {
		List<ForwardCommandDef> fullForwardCommandDefList = forwardCommandDefDAO.getFwdCmdDefs();
		if (!isSuperuser()) {
			acctID = getUser().getPerson().getAcctID();
			loadDevices();
			forwardCommandDefMap = new HashMap<Integer, ForwardCommandDef>();
			for (ForwardCommandDef forwardCommandDef : fullForwardCommandDefList) {
				if (forwardCommandDef.getAccessAllowed()) {
					forwardCommandDefMap.put(forwardCommandDef.getFwdCmd(), forwardCommandDef);
				}
			}
			fwdcmd = (Integer) getForwardCommandSelectList().get(0).getValue();
			initFilter();
		} else {
			// this should not happen -- bean only used in case of non -
			// superuser
		}
	}

	public List<SelectItem> getForwardCommandSelectList() {
		if (forwardCommandSelectList == null) {
			forwardCommandSelectList = new ArrayList<SelectItem>();

			for (ForwardCommandDef forwardCommandDef : forwardCommandDefMap.values()) {
				forwardCommandSelectList.add(new SelectItem(forwardCommandDef.getFwdCmd(), forwardCommandDef.getName()));
			}

			// TODO: SORT
		}

		return forwardCommandSelectList;
	}

	public Integer getFwdcmd() {
		return fwdcmd;
	}

	public void setFwdcmd(Integer fwdcmd) {
		this.fwdcmd = fwdcmd;
	}

	public ForwardCommandDef getForwardCommandDef() {
		if (fwdcmd != null)
			return forwardCommandDefMap.get(fwdcmd);

		return null;
	}

	public Integer getIntData() {
		return intData;
	}

	public void setIntData(Integer intData) {
		this.intData = intData;
	}

	public String getStringData() {
		return stringData;
	}

	public void setStringData(String stringData) {
		this.stringData = stringData;
	}

	public void loadDevices() {
		if (devices != null)
			devices.clear();
		if (acctID != null) {
			devices = new ArrayList<DeviceItem>();
			
			List<Device> deviceList = deviceDAO.getDevicesByAcctID(getUser().getPerson().getAcctID());
			for (Device device : deviceList) {
				DeviceItem item = new DeviceItem(device.getDeviceID(), device.getName(), device.getSim(), device.getImei(), device.getStatus());
				devices.add(item);
			}

		}
	}
	
	public boolean validateDeviceSelected() {
		selectedDevices = new ArrayList<DeviceItem>();
        for (DeviceItem device : getDevices())
            if (device.getSelected() && matchesFilter(device))
            	selectedDevices.add(device);
        
        if (selectedDevices.size() > 0)
        	return true;

		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Please select one or more devices.", null);
		FacesContext.getCurrentInstance().addMessage(null, message);
		return false;
	}

	private boolean matchesFilter(DeviceItem device) {
		
		if (device.getDeviceIMEI().toLowerCase().contains(filter.getDeviceIMEI().trim().toLowerCase()) &&
			device.getDeviceName().toLowerCase().contains(filter.getDeviceName().trim().toLowerCase()) &&
			device.getDeviceSIM().toLowerCase().contains(filter.getDeviceSIM().trim().toLowerCase()))
			return true;
				
		return false;
	}

	public void sendFwdCmdAction() {
		if (!validateDeviceSelected())
			return;
		
		Object data = null;
		if (stringData != null && !stringData.isEmpty()) {
			data = stringData;
		} else if (intData != null) {
			data = intData;
		} else {
			data = Integer.valueOf(0);
		}
		for (DeviceItem device : getSelectedDevices()) {
				deviceDAO.queueForwardCommand(device.getDeviceID(),
						new ForwardCommand(0, fwdcmd, data, ForwardCommandStatus.STATUS_QUEUED));
		}
	}

	public DeviceDAO getDeviceDAO() {
		return deviceDAO;
	}

	public void setDeviceDAO(DeviceDAO deviceDAO) {
		this.deviceDAO = deviceDAO;
	}

	public ForwardCommandType[] getFwdCmdDescriptions() {
		return ForwardCommandType.values();
	}

	public void setAcctID(Integer acctID) {
		this.acctID = acctID;
	}

	public Integer getAcctID() {
		return acctID;
	}

	public List<DeviceItem> getDevices() {
		return devices;
	}

	public void setDevices(List<DeviceItem> devices) {
		this.devices = devices;
	}

	public ForwardCommandDefDAO getForwardCommandDefDAO() {
		return forwardCommandDefDAO;
	}

	public void setForwardCommandDefDAO(ForwardCommandDefDAO forwardCommandDefDAO) {
		this.forwardCommandDefDAO = forwardCommandDefDAO;
	}

	public boolean getSelectAll() {
		return selectAll;
	}

	public void setSelectAll(boolean selectAll) {
		this.selectAll = selectAll;
	}

    public void doSelectAll()
    {
        for (DeviceItem device : this.getDevices())
            device.setSelected(selectAll);
    }
	public List<DeviceItem> getSelectedDevices() {
		return selectedDevices;
	}

	public void setSelectedDevices(List<DeviceItem> selectedDevices) {
		this.selectedDevices = selectedDevices;
	}

    public DeviceItem getFilter() {
		return filter;
	}

	public void setFilter(DeviceItem filter) {
		this.filter = filter;
	}

	public void initFilter()
	{
		filter = new DeviceItem();
		filter.setDeviceIMEI("");
		filter.setDeviceName("");
		filter.setDeviceSIM("");
	}
	
	public boolean filterMethod(Object obj) {
		
		DeviceItem item = (DeviceItem)obj; 
		
		return 	matchesFilter(item);
	}
}
