package com.inthinc.pro.fulfillment.backing;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.faces.model.SelectItem;

import com.inthinc.pro.dao.AccountDAO;
import com.inthinc.pro.dao.DeviceDAO;
import com.inthinc.pro.dao.EventDAO;
import com.inthinc.pro.dao.GroupDAO;
import com.inthinc.pro.dao.RoleDAO;
import com.inthinc.pro.dao.UserDAO;
import com.inthinc.pro.dao.VehicleDAO;
import com.inthinc.pro.model.Account;
import com.inthinc.pro.model.Device;
import com.inthinc.pro.model.Event;
import com.inthinc.pro.model.EventMapper;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.LastLocation;
import com.inthinc.pro.model.User;
import com.inthinc.pro.model.app.Roles;

public class DAOUtilBean {

	private AccountDAO accountDAO;
	private GroupDAO groupDAO;
	private VehicleDAO vehicleDAO;
	private DeviceDAO deviceDAO;
	private EventDAO eventDAO;
	private UserDAO userDAO;

	private RoleDAO roleDAO;

	private Integer mainAccountID;
	private Device device;

	private Map<Integer, String> accountMap;
	private String imei;
	private Integer selectedAccountID;

	private String errorMsg;
	private String successMsg;
	private List<String> messageList;
	
	
	public DAOUtilBean()
	{
		
	}

	public DAOUtilBean(String server, int port)  {

	}

	public void init() throws Exception
    {
		String username = "TiwiInstallation";
		// username = "dhock";
		User mainuser = userDAO.findByUserName(username);
		if (mainuser == null)
			throw new Exception("Main user not found:" + username);

		mainAccountID = mainuser.getPerson().getAcctID();

		Roles roles = new Roles();
        roles.setRoleDAO(roleDAO);
        roles.init();
        
        messageList = new ArrayList<String>();

    }

	// look for powerup events in last year
	// and get last location
	// TODO what is last location for a device that never got a fix??
	public boolean checkDevice() {
		
		if (device==null)
			loadDevice();
		if (device==null)
			return false;

		Integer vehicleID = device.getVehicleID();
		if (vehicleID == null) {
			setErrorMsg("Warning - Device Not Associated with Vehicle: " + imei);
			return false;
		}
		String warning = "";
		boolean success=true;
		LastLocation loc = vehicleDAO.getLastLocation(vehicleID);
		Calendar calendar = Calendar.getInstance();
		Date endDate = new Date();
		calendar.setTime(endDate);
		calendar.add(Calendar.YEAR, -1);
		Date startDate = calendar.getTime();
		List<Integer> eventTypes = new LinkedList<Integer>();
		eventTypes.add(EventMapper.TIWIPRO_EVENT_POWER_ON);
		List<Event> events = eventDAO.getEventsForVehicle(vehicleID, startDate,
				endDate, eventTypes);
//		for (Iterator<Event> iter = events.iterator(); iter.hasNext();) {
//			Event event = iter.next();
//			System.out.println(EventMapper.getEventType(event.getType()) + " "
//					+ event.getTime());
//		}

		if (loc != null)
		{
			messageList.add("GPS OK - Last fix " + loc.getTime() + " " + loc.getLoc().getLat() + " "
					+ loc.getLoc().getLng());
		}
		else
		{
			warning += "Warning - Device never achieved GPS fix. ";
			success = false;
		}
		if (!events.isEmpty())
		{
			Event event = events.get(0);
			messageList.add("OTA OK - First Event " + EventMapper.getEventType(event.getType()) + " "
					+ event.getTime());
			event = events.get(events.size()-1);
			messageList.add("OTA OK - Last Event " + EventMapper.getEventType(event.getType()) + " "
					+ event.getTime());
		}
		else
		{
			warning += "Warning - No power up events for device. Device never connected OTA. ";
			success = false;
		}
		setErrorMsg(warning);
		return success;
	}

	private void loadDevice()
	{
		device = deviceDAO.findByIMEI(imei);	
		if (device==null)
			setErrorMsg("Error - Device not found: " + imei);
	}
	public void moveDeviceAction() {
		reInitAction();
		loadDevice();
		if (selectedAccountID==null || selectedAccountID<0)
		{
			setErrorMsg("Please select a customer account");
		}
		else if (device != null)
		{
			if (!device.getAccountID().equals(mainAccountID)) {
				Account assignedAccount = accountDAO.findByID(device.getAccountID());
				setErrorMsg("Error - Device "+ imei + " already assigned to account " + assignedAccount.getAcctName());
			} else if (checkDevice())
			{
				deviceDAO.deleteByID(device.getDeviceID());
				device.setAccountID(selectedAccountID);
				// TODO should we set ephone to tech support??
				device.setEphone(null);
				deviceDAO.create(selectedAccountID, device);
				setSuccessMsg("Device " + imei + " successfully assigned to " + getSelectedAccountName());
			}
		}
		setImei(null);
	}

	public List<SelectItem> getAccountSelectList() {
		List<SelectItem> accountList = new ArrayList<SelectItem>();
		accountList.add(new SelectItem(-1,
				"--Select a Account--"));
		for (Integer accountID : getAccountMap().keySet()) {
			if (!accountID.equals(this.mainAccountID))
				accountList.add(new SelectItem(accountID, getAccountMap().get(
					accountID)));
		}
		return accountList;
	}

	public Map<Integer, String> getAccountMap() {

		if (accountMap == null) {
			accountMap = new Hashtable<Integer, String>();
			int limit = 20; // TODO Lose this!!
			List<Account> accounts = accountDAO.getAllAcctIDs();
			for (Iterator<Account> aiter = accounts.iterator(); aiter.hasNext()
					&& limit > 0;) {
				Account account = aiter.next();
				account = accountDAO.findByID(account.getAcctID());
				// System.out.println(address.getAddr1());
				String acctName = account.getAcctName();
				// TODO acctName should NOT be null
				if (acctName == null) {
					groupDAO.getGroupsByAcctID(account.getAcctID());
					List<Group> groups = groupDAO.getGroupsByAcctID(account
							.getAcctID());
					for (Iterator<Group> giter = groups.iterator(); giter
							.hasNext();) {
						Group grp = giter.next();
						if (grp.getParentID() == 0) {
							acctName = grp.getName();
							break;
						}
					}
				}
				if (acctName != null && !acctName.equals("Top"))
					accountMap.put(account.getAcctID(), acctName);
				limit--;
			}
		}
		return accountMap;

	}
    public void clearErrorAction()
    {
    	reInitAction();
        setImei(null);
    }

    public void reInitAction()
    {
        setErrorMsg(null);
        setSuccessMsg(null);
        messageList.clear();
    }

    public AccountDAO getAccountDAO()
    {
        return accountDAO;
    }

    public void setAccountDAO(AccountDAO accountDAO)
    {
        this.accountDAO = accountDAO;
    }

    public UserDAO getUserDAO()
    {
        return userDAO;
    }

    public void setUserDAO(UserDAO userDAO)
    {
        this.userDAO = userDAO;
    }

    public GroupDAO getGroupDAO()
    {
        return groupDAO;
    }

    public void setGroupDAO(GroupDAO groupDAO)
    {
        this.groupDAO = groupDAO;
    }

    public RoleDAO getRoleDAO()
    {
        return roleDAO;
    }

    public void setRoleDAO(RoleDAO roleDAO)
    {
        this.roleDAO = roleDAO;
    }

	public void setVehicleDAO(VehicleDAO vehicleDAO) {
		this.vehicleDAO = vehicleDAO;
	}

	public VehicleDAO getVehicleDAO() {
		return vehicleDAO;
	}

	public DeviceDAO getDeviceDAO() {
		return deviceDAO;
	}

	public void setDeviceDAO(DeviceDAO deviceDAO) {
		this.deviceDAO = deviceDAO;
	}

	public EventDAO getEventDAO() {
		return eventDAO;
	}

	public void setEventDAO(EventDAO eventDAO) {
		this.eventDAO = eventDAO;
	}

	public Integer getSelectedAccountID() {
		return selectedAccountID;
	}

	public void setSelectedAccountID(Integer selectedAccountID) {
		this.selectedAccountID = selectedAccountID;
	}

	public String getSelectedAccountName() {
		if (selectedAccountID==null || selectedAccountID<0 )
			return "--Select a Account--";
		return getAccountMap().get(this.selectedAccountID);
	}
	
	public List<String> getMessageList()
	{
		return messageList;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		if (imei!=null)
			imei=imei.trim();
		this.imei = imei;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getSuccessMsg() {
		return successMsg;
	}

	public void setSuccessMsg(String successMsg) {
		this.successMsg = successMsg;
	}

}
