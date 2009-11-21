package com.inthinc.pro.fulfillment.backing;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;

import org.springframework.security.context.SecurityContextHolder;


import com.caucho.hessian.client.HessianRuntimeException;
import com.inthinc.pro.dao.AccountDAO;
import com.inthinc.pro.dao.AddressDAO;
import com.inthinc.pro.dao.DeviceDAO;
import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.dao.EventDAO;
import com.inthinc.pro.dao.GroupDAO;
import com.inthinc.pro.dao.PersonDAO;
import com.inthinc.pro.dao.RoleDAO;
import com.inthinc.pro.dao.UserDAO;
import com.inthinc.pro.dao.VehicleDAO;
import com.inthinc.pro.model.Account;
import com.inthinc.pro.model.Address;
import com.inthinc.pro.model.Device;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Event;
import com.inthinc.pro.model.EventMapper;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.GroupStatus;
import com.inthinc.pro.model.GroupType;
import com.inthinc.pro.model.LastLocation;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.Status;
import com.inthinc.pro.model.User;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.model.app.Roles;
import com.inthinc.pro.security.userdetails.ProUser;
import com.inthinc.pro.util.RFIDBean;

public class DAOUtilBean implements PhaseListener {

	private AccountDAO accountDAO;
	private GroupDAO groupDAO;
	private VehicleDAO vehicleDAO;
	private DeviceDAO deviceDAO;
	private EventDAO eventDAO;
	private UserDAO userDAO;
	private PersonDAO personDAO;
	private DriverDAO driverDAO;
	private AddressDAO addressDAO;

	private RoleDAO roleDAO;

	private Integer shipAccountID;
	private Integer rmaAccountID;
	private Device device;

	private Map<Integer, String> accountMap;
	private Map<Integer, String> groupMap;
	private Integer selectedAccountID;
	private Integer selectedGroupID;

		
	private String imei;
	private String serialNum;
	
	private String vehicleName;
	private String VIN;
	
	private String make;
	private String model;
	private String year;
	private String license;

	private String lastName;
	private String firstName;
	private String empID;
	private String driverID;
	private String RFID;
	private boolean useFOB;	
	
	private String errorMsg;
	private String successMsg;
	private List<String> messageList;
	private Account rmaAccount;
	private Account shipAccount;

	private boolean assignedVehiclesOnly;
	private String vehicleGrid;
	private RFIDBean rfidBean;

	private static final String rmausername = "RMA";
	private static final String shipusername = "TiwiInstallation";

	public ProUser getProUser() {
		return (ProUser) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
	}

	public void init() throws Exception {
		User shipuser = userDAO.findByUserName(shipusername);
		if (shipuser == null)
			throw new Exception("Fulfillment user not found:" + shipusername);

		User rmauser = userDAO.findByUserName(rmausername);
		if (rmauser == null)
			throw new Exception("RMA user not found:" + rmausername);

		shipAccountID = shipuser.getPerson().getAcctID();
		rmaAccountID = rmauser.getPerson().getAcctID();

		shipAccount = accountDAO.findByID(shipAccountID);
		rmaAccount = accountDAO.findByID(rmaAccountID);

		Integer userAccountID = getProUser().getUser().getPerson().getAcctID();
		if (!shipAccountID.equals(userAccountID)
				&& !rmaAccountID.equals(userAccountID))
			throw new Exception("Logged in User not in ship or rma account");

		// Roles roles = new Roles();
		// roles.setRoleDAO(roleDAO);
		// roles.init();

		messageList = new ArrayList<String>();

	}

	// look for powerup events in last year
	// and get last location
	// TODO what is last location for a device that never got a fix??
	public boolean checkDevice() {

		if (device == null)
			loadDevice();
		if (device == null)
			return false;

		Integer vehicleID = device.getVehicleID();
		if (vehicleID == null) {
			messageList.add("Warning - Device Not Associated with Vehicle: "
					+ serialNum);
		}
		if (vehicleID != null) {
			LastLocation loc = vehicleDAO.getLastLocation(vehicleID);
			Calendar calendar = Calendar.getInstance();
			Date endDate = new Date();
			calendar.setTime(endDate);
			calendar.add(Calendar.YEAR, -1);
			Date startDate = calendar.getTime();
			List<Integer> eventTypes = new LinkedList<Integer>();
			eventTypes.add(EventMapper.TIWIPRO_EVENT_POWER_ON);
			List<Event> events = eventDAO.getEventsForVehicle(vehicleID,
					startDate, endDate, eventTypes, null);

			if (loc != null) {
				messageList.add("GPS OK - Last fix " + loc.getTime() + " "
						+ loc.getLoc().getLat() + " " + loc.getLoc().getLng());
			} else {
				messageList.add("Warning - Device never achieved GPS fix. ");
			}
			if (!events.isEmpty()) {
				Event event = events.get(0);
				messageList.add("OTA OK - First Event "
						+ EventMapper.getEventType(event.getType()) + " "
						+ event.getTime());
				event = events.get(events.size() - 1);
				messageList.add("OTA OK - Last Event "
						+ EventMapper.getEventType(event.getType()) + " "
						+ event.getTime());
			} else {
				messageList
						.add("Warning - No power up events for device. Device never connected OTA. ");
			}

		}
		return true;
	}

	private void loadDevice() {
		device = deviceDAO.findBySerialNum(serialNum.toUpperCase());
		if (device == null)
			device = deviceDAO.findByIMEI(serialNum);
		if (device == null) {
			Integer id = 0;
			try {
				id = Integer.parseInt(serialNum);
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (id > 0)
				device = deviceDAO.findByID(id);
		}

		if (device == null)
			setErrorMsg("Error: Device not found " + serialNum
					+ ". Please enter an IMEI, Serial Number or deviceID.");
	}

	public void queryDeviceAction() {
		reInitAction();
		loadDevice();
		if (device != null) {
			Account dacct = accountDAO.findByID(device.getAccountID());
			Vehicle dv = vehicleDAO.findByID(device.getVehicleID());
			Driver dd = null;
			Person dp = null;
			LastLocation ll = null;

			if (dv != null) {
				ll = vehicleDAO.getLastLocation(dv.getVehicleID());
				dd = driverDAO.findByID(dv.getDriverID());
				if (dd != null)
					dp = personDAO.findByID(dd.getPersonID());
			}
			String msg = "";
			msg += "<BR/>ACCT: " + dacct.getAcctName();
			msg += "<BR/>SN: " + device.getSerialNum();
			if (dv != null)
				msg += "<BR/>Vehicle: " + dv.getName() + " " + dv.getVIN()
						+ " " + dv.getFullName();
			if (dp != null)
				msg += "<BR/>Driver: " + dp.getFullNameWithId();
			if (dd != null) {
				String barcode = "Not found";
				Long value = null;
				value = rfidBean.findBarcode(dd.getRFID());
				if (value != null)
					barcode = value.toString();
				msg += "<BR/>RFID Barcode: " + barcode;
			}
			msg += "<BR/>PHONE: <a target=\"_blank\" href=\"tel:"
					+ device.getPhone() + "\">" + device.getPhone() + "</a>";
			msg += "<BR/><a target=\"_blank\" href=\"https://t3.tiwi.com:8084/openreports/executeReport.action?userName=salesuser&password=45Uu9i92A_8&submitRun=Run&reportId=131&msisdn="
					+ device.getEphone() + "\">Radius Call Records</a>";
			msg += "<BR/>IMEI: " + device.getImei();
			msg += "<BR/>SIM: " + device.getSim();
			msg += "<BR/>ECALL: " + device.getEphone();

			if (ll != null)
				msg += "<BR/>LOC: "
						+ ll.getTime()
						+ " <a target=\"_blank\" href=\"http://maps.google.com/maps?q="
						+ ll.getLoc().getLat() + "," + ll.getLoc().getLng()
						+ "\">" + ll.getLoc().toString() + "</a>";

			if (dv != null)
				msg += "<BR/><BR/>" + dv.toString();
			if (dd != null)
				msg += "<BR/><BR/>" + dd.toString();
			if (dp != null)
				msg += "<BR/><BR/>" + dp.toString();

			setSuccessMsg(msg);
		}
		setSerialNum(null);
	}

	public void rmaDeviceAction() {
		reInitAction();
		loadDevice();
		if (selectedAccountID == null || selectedAccountID < 0) {
			setErrorMsg("Please select a customer account");
		} else if (device != null) {
			if (!device.getAccountID().equals(selectedAccountID)) {
				Account assignedAccount = accountDAO.findByID(device
						.getAccountID());
				setErrorMsg("Error - Device " + serialNum
						+ " assigned to account "
						+ assignedAccount.getAcctName());
			} else {
				deviceDAO.deleteByID(device.getDeviceID());
				device.setAccountID(rmaAccountID);
				// TODO should we set ephone to tech support??
				device.setEphone(null);
				deviceDAO.create(rmaAccountID, device);
				setSuccessMsg("Device " + serialNum
						+ " successfully moved to account: "
						+ rmaAccount.getAcctName());
			}
		}
		setSerialNum(null);
	}

	public void reworkDeviceAction() {
		reInitAction();
		loadDevice();
		if (device != null) {
			if (!device.getAccountID().equals(rmaAccount.getAcctID())) {
				Account assignedAccount = accountDAO.findByID(device
						.getAccountID());
				setErrorMsg("Error - Device " + serialNum
						+ " assigned to account "
						+ assignedAccount.getAcctName());
			} else {
				deviceDAO.deleteByID(device.getDeviceID());
				device.setAccountID(shipAccountID);
				// TODO should we set ephone to tech support??
				device.setEphone(null);
				deviceDAO.create(shipAccountID, device);
				setSuccessMsg("Device " + serialNum
						+ " successfully moved to account: "
						+ shipAccount.getAcctName());
			}
		}
		setSerialNum(null);
	}

	public void moveDeviceAction() {
		reInitAction();
		loadDevice();
		if (selectedAccountID == null || selectedAccountID < 0) {
			setErrorMsg("Please select a customer account");
		} else if (device != null) {
			if (!device.getAccountID().equals(shipAccountID)) {
				Account assignedAccount = accountDAO.findByID(device
						.getAccountID());
				setErrorMsg("Error - Device " + serialNum
						+ " already assigned to account "
						+ assignedAccount.getAcctName());
			} else if (checkDevice()) {
				deviceDAO.deleteByID(device.getDeviceID());
				device.setAccountID(selectedAccountID);
				// TODO should we set ephone to tech support??
				device.setEphone(null);
				deviceDAO.create(selectedAccountID, device);
				setSuccessMsg("Device " + serialNum
						+ " successfully assigned to account: "
						+ getSelectedAccountName());
			}
		}
		setSerialNum(null);
	}

	public String getVehicleGrid()
	{
		return vehicleGrid;
	}
	public void getVehiclesAction() {
		vehicleGrid = "";	
		if (selectedAccountID == null || selectedAccountID < 0) {
			return;
		}
		Integer groupID=selectedGroupID;
		if (groupID == null || groupID < 0) {
			groupID=findTopGroup(selectedAccountID).getGroupID();
		}
		
		List<Vehicle> vehicles;
		if (assignedVehiclesOnly)		
			vehicles = getAssignedVehicles(groupID);
		else
			vehicles = vehicleDAO.getVehiclesInGroupHierarchy(groupID);
			

		vehicleGrid = "<table class=\"itable\" width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"3\">";
		vehicleGrid += "<tr class=\"header\"><th>Vehicle</th><th>Tiwi</th><th class=\"last\">RFID</th></tr>";

		int row = 0;
		for (Iterator<Vehicle> viter = vehicles.iterator(); viter.hasNext();) {
			Vehicle vehicle = viter.next();
			if (vehicle.getStatus().equals(Status.ACTIVE))
			{
				String rowStyle = "\"reg\"";
				String serialnum = "---";
				if (vehicle.getDeviceID() != null) {
					Device device = deviceDAO.findByID(vehicle.getDeviceID());
					serialnum = device.getSerialNum();
				}

				String barcode = "---";
				if (vehicle.getDriverID() != null) {
					Driver driver = driverDAO.findByID(vehicle.getDeviceID());
					Long rfid = rfidBean.findBarcode(driver.getRFID());
					if (rfid != null)
						barcode = rfid.toString();
				}

				if (row % 2 == 1)
					rowStyle = "\"alt\"";
				vehicleGrid += "<tr class=" + rowStyle + ">";
				vehicleGrid += "<td class=\"first\">" + vehicle.getName() + "</td>";
				vehicleGrid += "<td>" + serialnum + "</td>";
				vehicleGrid += "<td class=\"last\">" + barcode + "</td>";
				vehicleGrid += "</tr>";
			}
		}

		vehicleGrid += "</table>";
	}

	public void editVINAction() {
		reInitAction();
		if (selectedAccountID == null || selectedAccountID < 0) {
			setErrorMsg("Error: Please select a customer account");
			return;
		} else if (vehicleName == null || vehicleName.trim().length() == 0) {
			setErrorMsg("Error: Please enter a valid vehicle Name or License");
			return;
		} else if (VIN == null || VIN.trim().length() < 10) {
			setErrorMsg("Error: Please enter a valid VIN");
			return;
		}

		Vehicle vehicleVIN = vehicleDAO.findByVIN(VIN);
		if (vehicleVIN != null && vehicleVIN.getStatus().equals(Status.ACTIVE)) {
			Group group = groupDAO.findByID(vehicleVIN.getGroupID());
			Account vinAcct = accountDAO.findByID(group.getAccountID());

			setErrorMsg("Error: VIN " + VIN + " already in use by "
					+ vinAcct.getAcctName() + " Name: " + vehicleVIN.getName()
					+ " License: " + vehicleVIN.getLicense() + " - "
					+ vehicleVIN.getFullName());
			return;
		}
		Vehicle vehicle = this.findVehicle(selectedAccountID, vehicleName);
		if (vehicle == null || !vehicle.getStatus().equals(Status.ACTIVE))
			setErrorMsg("Error: Vehicle " + vehicleName
					+ " not found. Please enter a valid Name, VIN, or License.");
		else {
			vehicle.setVIN(VIN);
			vehicleDAO.update(vehicle);
			setSuccessMsg("VIN " + VIN + " successfully set for " + vehicleName);
		}
		setVIN(null);
		setVehicleName(null);

	}

	public void assignRFIDAction() {
		reInitAction();
		
		if (selectedAccountID == null || selectedAccountID < 0) {
			setErrorMsg("Error: Please select a customer account");
			return;
		}
		
		Long barcode = 0L;

		try {
			barcode = Long.parseLong(RFID);
		} catch (NumberFormatException e) {
			setErrorMsg("Error: Invalid RFID Barcode Number " + RFID);
			return;
		}

		Long rfid = rfidBean.findRFID(barcode, useFOB);

		Driver driver = findDriver(selectedAccountID, driverID);

		if (driver == null) {
			setErrorMsg("Error: Driver not found " + driverID);
		} else if (rfid == null) {
			setErrorMsg("Error: RFID not found " + RFID);
		} else {
			String existMsg = "";
			Integer currentDriverID = driverDAO.getDriverIDForRFID(rfid);
			if (currentDriverID != null && currentDriverID > 0) {
				if (!currentDriverID.equals(driver.getDriverID())) {
					Driver currentDriver = driverDAO.findByID(currentDriverID);
					String name = driverID;
					if (currentDriver.getPerson() != null)
						name = currentDriver.getPerson().getFullNameWithId();

					existMsg = "<BR/> Warning: RFID previously assigned to "
							+ name;
					currentDriver.setRFID(1L);
					driverDAO.update(currentDriver);
				}
			}
			Long currentRFID = driver.getRFID();
			if (currentRFID != null && !currentRFID.equals(rfid))
				existMsg += "<BR/> Warning: Driver previously had RFID "
						+ currentRFID;

			driver.setRFID(rfid);
			driverDAO.update(driver);

			String name = driverID;
			if (driver.getPerson() != null)
				name = driver.getPerson().getFullNameWithId();

			setSuccessMsg("RFID " + rfid + " successfully assigned to driver: "
					+ name + existMsg);
		}
		setRFID(null);
		setDriverID(null);
		setUseFOB(false);
	}

	public void assignDeviceAction() {
		reInitAction();
		loadDevice();
		if (selectedAccountID == null || selectedAccountID < 0) {
			setErrorMsg("Error: Please select a customer account");
		} else if (vehicleName == null || vehicleName.trim().length() == 0) {
			setErrorMsg("Error: Please enter a valid vehicle Name, VIN or License");
		} else if (device != null) {
			if (!device.getAccountID().equals(selectedAccountID)) {
				Account assignedAccount = accountDAO.findByID(device
						.getAccountID());
				setErrorMsg("Error: Device " + serialNum
						+ " already assigned to account "
						+ assignedAccount.getAcctName());
			} else {
				Vehicle vehicle = findVehicle(selectedAccountID, vehicleName);
				if (vehicle == null
						|| !vehicle.getStatus().equals(Status.ACTIVE))
					setErrorMsg("Error: Vehicle "
							+ vehicleName
							+ " not found. Please enter a valid Name, VIN, or License.");
				else {
					Integer currentVID = device.getVehicleID();
					String vwarn = "";
					if (currentVID != null && currentVID > 0
							&& !currentVID.equals(vehicle.getVehicleID())) {
						Vehicle currentV = vehicleDAO.findByID(currentVID);
						if (currentV != null
								&& currentV.getStatus().equals(Status.ACTIVE))
							vwarn = "<BR/>Warning: Device was previously assigned to vehicle: "
									+ currentV.getName()
									+ " "
									+ currentV.getFullName();
					}
					vehicleDAO.setVehicleDevice(vehicle.getVehicleID(), device
							.getDeviceID());
					setSuccessMsg("Device " + serialNum
							+ " successfully assigned to vehicle: "
							+ vehicleName + " " + vehicle.getFullName()
							+ " VIN: " + vehicle.getVIN() + vwarn);
				}
			}
		}
		setSerialNum(null);
		setVehicleName(null);
	}

	public void createDriverAction() {
		reInitAction();
		if (selectedAccountID == null || selectedAccountID < 0) {
			setErrorMsg("Error: Please select a customer account");
			return;
		} else if (selectedGroupID == null || selectedGroupID < 0) {
			setErrorMsg("Error: Please select a Team");
			return;
		} else if (lastName == null || lastName.trim().length() == 0) {
			setErrorMsg("Error: Please enter a valid lastName");
			return;
		}
		
		Address address = new Address();
		Integer addressID = addressDAO.create(selectedAccountID, address);
		if (addressID==null || addressID<1)
		{
			setErrorMsg("Error creating address");
			return;
		}
		
		
		Person person = new Person();
		person.setLast(lastName.trim());
		person.setFirst(firstName.trim());
		person.setAddressID(addressID);
		person.setTimeZone(TimeZone.getDefault());
		person.setEmpid(empID.trim());

		Integer personID = personDAO.create(selectedAccountID, person);
		if (personID==null || personID<1)
		{
			setErrorMsg("Error creating person");
			return;
		}
					
		Driver driver = new Driver();
		driver.setGroupID(selectedGroupID);
		driver.setPersonID(personID);
		driver.setStatus(Status.ACTIVE);

		if (RFID!=null && RFID.trim().length()>0)
		{
			Long barcode = 0L;
			try {
				barcode = Long.parseLong(RFID);
				if (barcode!=null)
				{
					Long rfid = rfidBean.findRFID(barcode, useFOB);
					if (rfid==null)
					{
						setErrorMsg("Error: RFID not found for barcode: " + RFID);
						return;
					}
					driver.setRFID(rfid);
				}
			} catch (NumberFormatException e) {
			}
		}
		
		Integer driverID = driverDAO.create(personID, driver);
		if (driverID==null || driverID<1)
		{
			setErrorMsg("Error creating driver");
			return;
		}

		setSuccessMsg("driverID " + driverID + "created successfully");
		
		lastName=null;
		firstName=null;
		RFID=null;
		empID=null;

	}

	public void createVehicleAction() {
		reInitAction();
		
		Integer yearint=null;
		
		if (selectedAccountID == null || selectedAccountID < 0) {
			setErrorMsg("Error: Please select a customer account");
			return;
		} else if (selectedGroupID == null || selectedGroupID < 0) {
			setErrorMsg("Error: Please select a Team");
			return;
		} else if (VIN == null || VIN.trim().length() == 0) {
			setErrorMsg("Error: Please enter a valid VIN");
			return;
		} else if (year != null && year.trim().length() > 0) {
			try {
				yearint=Integer.parseInt(year);
			} catch (NumberFormatException e) {
			}
			if (yearint==null || yearint<1900 || yearint>3000)
			{
				setErrorMsg("Error: Please enter a valid Year");
				return;
			}
		} else if (serialNum!=null && serialNum.trim().length()>0) {
			loadDevice();
			if (device==null)
				return;
		}
	
		
		Vehicle vehicle = new Vehicle();
		vehicle.setName(vehicleName.trim());
		vehicle.setLicense(license.trim());
		vehicle.setVIN(VIN.trim());
		vehicle.setMake(make.trim());
		vehicle.setModel(model.trim());
		vehicle.setYear(yearint);
		vehicle.setGroupID(selectedGroupID);
		vehicle.setStatus(Status.ACTIVE);
		Integer vehicleID = vehicleDAO.create(selectedGroupID, vehicle);
		if (vehicleID==null || vehicleID<1)
		{
			setErrorMsg("Error creating vehicleID " + vehicleID);
			return;
		}
		if (device!=null)
			vehicleDAO.setVehicleDevice(vehicleID, device.getDeviceID());

		setSuccessMsg("VehicleID " + vehicleID + " successfully created");
		setVIN(null);
		setVehicleName(null);
		serialNum=null;
		make=null;
		model=null;
		year=null;
		license=null;

	}

	private String getGroupPath(Group group) {
		String path = group.getName();
		if (group.getParentID() > 0) {
			Group parent = groupDAO.findByID(group.getParentID());
			path = getGroupPath(parent) + "/" + path;
		}
		return path;
	}

	
	public List<Vehicle> getAssignedVehicles(Integer groupID) {
		List<Vehicle> vehicles = vehicleDAO
				.getVehiclesInGroupHierarchy(groupID);
		List<Vehicle> assignedVehicles = new ArrayList<Vehicle>();
		for (Iterator<Vehicle> viter = vehicles.iterator(); viter.hasNext();) {
			Vehicle vehicle = viter.next();
			if (vehicle.getDeviceID() != null)
				assignedVehicles.add(vehicle);
		}
		return assignedVehicles;

	}
	 
	public Group findTopGroup(Integer accountID) {
		Group topGroup = null;
		List<Group> groups = groupDAO.getGroupsByAcctID(accountID);
		for (Iterator<Group> giter = groups.iterator(); giter.hasNext();) {
			topGroup = giter.next();
			if (topGroup.getParentID().equals(0))
				break;
		}
		return topGroup;
	}

	public Driver findDriver(Integer accountID, String name)
	{
		Driver driver = null;

		Group topGroup = findTopGroup(accountID);
		if (topGroup == null)
			return null;

		List<Person> persons = personDAO.getPeopleInGroupHierarchy(topGroup.getGroupID());
		Person person = null;
		for (Iterator<Person> piter = persons.iterator(); piter.hasNext();) {
			person = piter.next();
			if (person.getEmpid()!=null)
			{
				if (name.toUpperCase().trim().equals(person.getEmpid().trim().toUpperCase()))
				{
					driver = driverDAO.findByPersonID(person.getPersonID());
					if (driver!=null)
						return driver;
				}
			}
		}
		Integer did=0;
		try {
			did = Integer.parseInt(name);
		} catch (NumberFormatException e) {
			return null;
		}

		driver = driverDAO.findByID(did);
		
		return driver;
	}
	
	public Vehicle findVehicle(Integer accountID, String name) {
		Vehicle vehicle = null;

		Group topGroup = findTopGroup(accountID);
		if (topGroup == null)
			return null;

		List<Vehicle> vehicles = vehicleDAO
				.getVehiclesInGroupHierarchy(topGroup.getGroupID());
		name = name.toUpperCase().trim();
		for (Iterator<Vehicle> viter = vehicles.iterator(); viter.hasNext();) {
			vehicle = viter.next();
			if (vehicle.getLicense() != null
					&& vehicle.getLicense().trim().toUpperCase().equals(name))
				return vehicle;
			if (vehicle.getVIN() != null
					&& vehicle.getVIN().trim().toUpperCase().equals(name))
				return vehicle;
			if (vehicle.getName() != null
					&& vehicle.getName().trim().toUpperCase().equals(name))
				return vehicle;
		}

		return null;
	}

	public Map<Integer, String> getAccountMap() {

		if (accountMap == null || accountMap.isEmpty()) {
			accountMap = new Hashtable<Integer, String>();
			int limit = 700; // TODO Lose this!!
limit=10;
			List<Account> accounts=null;
try {			
			accounts = accountDAO.getAllAcctIDs();
} catch (HessianRuntimeException e)
{
	e.printStackTrace();
}
			for (Iterator<Account> aiter = accounts.iterator(); aiter.hasNext()
					&& limit > 0; limit--) {
				Account account = aiter.next();
				account = accountDAO.findByID(account.getAcctID());
				if (account != null && account.getStatus() == Status.ACTIVE) {
					String acctName = account.getAcctName();
					if (acctName == null) {
						acctName = account.getAcctID().toString();
					}
					accountMap.put(account.getAcctID(), acctName);
				}
			}
		}
		return accountMap;

	}

	public List<SelectItem> getAccountSelectList() {
		List<SelectItem> accountList = new ArrayList<SelectItem>();

		// Put keys and values in to an arraylist using entryset
		ArrayList sortedArrayList = new ArrayList(getAccountMap().entrySet());

		// Sort the values based on values first and then keys.
		Collections.sort(sortedArrayList, new AlphaComparator());

		// Show sorted results
		Iterator itr = sortedArrayList.iterator();
		Integer id = -1;
		String name = "";

		while (itr.hasNext()) {
			Map.Entry e = (Map.Entry) itr.next();
			id = (Integer) e.getKey();
			name = ((String) e.getValue());
			if (!id.equals(this.shipAccountID) && !id.equals(this.rmaAccountID))
				accountList.add(new SelectItem(id, name));
		}
		return accountList;
	}

	public Map<Integer, String> getGroupMap() {
		if (groupMap==null || groupMap.isEmpty())
		{
			groupMap = new Hashtable<Integer, String>();
			List<Group> groups = groupDAO
					.getGroupsByAcctID(selectedAccountID);
			for (Iterator<Group> giter = groups.iterator(); giter.hasNext();) {
				Group group = giter.next();
				if (group.getStatus().equals(GroupStatus.GROUP_ACTIVE)
					&& group.getType().equals(GroupType.TEAM))
					groupMap.put(group.getGroupID(), getGroupPath(group));
			}			
		}
		return groupMap;
	}

	public List<SelectItem> getGroupSelectList() {

		List<SelectItem> groupSelectList = new ArrayList<SelectItem>();
		if (selectedAccountID != null && selectedAccountID >= 0) {

			// Put keys and values in to an arraylist using entryset


			ArrayList sortedArrayList = new ArrayList(getGroupMap().entrySet());
			// Sort the values based on values first and then keys.
			Collections.sort(sortedArrayList, new AlphaComparator());

			// Show sorted results
			Iterator itr = sortedArrayList.iterator();
			Integer id = -1;
			String name = "";

			while (itr.hasNext()) {
				Map.Entry e = (Map.Entry) itr.next();
				id = (Integer) e.getKey();
				name = ((String) e.getValue());
				groupSelectList.add(new SelectItem(id, name));
			}
		}

		return groupSelectList;
	}

	
	
	public void clearErrorAction() {
		reInitAction();
		setSerialNum(null);
		// setVehicleName(null);
	}

	public void reInitAction() {
		setErrorMsg(null);
		setSuccessMsg(null);
		vehicleGrid="";
		if (messageList != null)
			messageList.clear();
	}

	public AccountDAO getAccountDAO() {
		return accountDAO;
	}

	public void setAccountDAO(AccountDAO accountDAO) {
		this.accountDAO = accountDAO;
	}

	public UserDAO getUserDAO() {
		return userDAO;
	}

	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	public GroupDAO getGroupDAO() {
		return groupDAO;
	}

	public void setGroupDAO(GroupDAO groupDAO) {
		this.groupDAO = groupDAO;
	}

	public RoleDAO getRoleDAO() {
		return roleDAO;
	}

	public void setRoleDAO(RoleDAO roleDAO) {
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
		if (!selectedAccountID.equals(this.selectedAccountID)) {
			selectedGroupID = null;
			groupMap = null;
		}
		this.selectedAccountID = selectedAccountID;
	}

	public String getSelectedAccountName() {
		if (selectedAccountID == null || selectedAccountID < 0)
			return "--Select an Customer--";
		return getAccountMap().get(this.selectedAccountID);
	}

	public String getSelectedGroupName() {
		if (selectedAccountID == null || selectedAccountID < 0)
			return "--Select an Customer--";
		if (selectedGroupID == null || selectedGroupID < 0)
			return "--Select a Team--";
		
		return getGroupMap().get(this.selectedGroupID);
	}

	public List<String> getMessageList() {
		return messageList;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		if (imei != null)
			imei = imei.trim();
		this.imei = imei;
	}

	public String getSerialNum() {
		return serialNum;
	}

	public void setSerialNum(String serialNum) {
		if (serialNum != null)
			serialNum = serialNum.trim();
		this.serialNum = serialNum;
	}

	public String getErrorMsg() {
		String tmp = errorMsg;
		// errorMsg=null;
		return tmp;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getSuccessMsg() {
		String tmp = successMsg;
		// successMsg=null;
		return tmp;
	}

	public void setSuccessMsg(String successMsg) {
		this.successMsg = successMsg;
	}

	public String getVehicleName() {
		return vehicleName;
	}

	public void setVehicleName(String vehicleName) {
		this.vehicleName = vehicleName;
	}

	static class AlphaComparator implements Comparator {

		public int compare(Object obj1, Object obj2) {

			int result = 0;
			Map.Entry e1 = (Map.Entry) obj1;

			Map.Entry e2 = (Map.Entry) obj2;// Sort based on values.

			String value1 = (String) e1.getValue();
			String value2 = (String) e2.getValue();

			if (value1.compareTo(value2) == 0) {

				Integer id1 = (Integer) e1.getKey();
				Integer id2 = (Integer) e2.getKey();

				// Sort values in a ascending order
				result = id1.compareTo(id2);

			} else {
				// Sort values in a ascending order
				result = value1.compareToIgnoreCase(value2);
			}

			return result;
		}
	}

	public String getDriverID() {
		return driverID;
	}

	public void setDriverID(String driverID) {
		this.driverID = driverID;
	}

	public String getRFID() {
		return RFID;
	}

	public void setRFID(String rfid) {
		RFID = rfid;
	}

	public PersonDAO getPersonDAO() {
		return personDAO;
	}

	public void setPersonDAO(PersonDAO personDAO) {
		this.personDAO = personDAO;
	}

	public DriverDAO getDriverDAO() {
		return driverDAO;
	}

	public void setDriverDAO(DriverDAO driverDAO) {
		this.driverDAO = driverDAO;
	}

	public RFIDBean getRfidBean() {
		return rfidBean;
	}

	public void setRfidBean(RFIDBean rfidBean) {
		this.rfidBean = rfidBean;
	}

	public boolean getUseFOB() {
		return useFOB;
	}

	public void setUseFOB(boolean useFOB) {
		this.useFOB = useFOB;
	}

	public String getVIN() {
		return VIN;
	}

	public void setVIN(String vin) {
		VIN = vin;
	}

	public Integer getSelectedGroupID() {
		return selectedGroupID;
	}

	public void setSelectedGroupID(Integer selectedGroupID) {
		if (groupMap == null || groupMap.isEmpty())
			selectedGroupID = null;
		this.selectedGroupID = selectedGroupID;
	}

	public boolean getAssignedVehiclesOnly() {
		return assignedVehiclesOnly;
	}

	public void setAssignedVehiclesOnly(boolean assignedVehiclesOnly) {
		this.assignedVehiclesOnly = assignedVehiclesOnly;
	}

	@Override
	public void afterPhase(PhaseEvent event) {
		// TODO Auto-generated method stub
		if (event.getPhaseId().equals(PhaseId.RENDER_RESPONSE))
		{
	    	FacesContext context = event.getFacesContext();  
	    	HttpSession session = (HttpSession) context.getExternalContext().getSession(true);  
	    	DAOUtilBean bean = (DAOUtilBean) session.getAttribute("daoUtilBean");
	    	if (bean!=null)
	    		bean.reInitAction();
		}
	}

	@Override
	public void beforePhase(PhaseEvent event) {
		// TODO Auto-generated method stub
	}

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.RENDER_RESPONSE;
	}

	public String getMake() {
		return make;
	}

	public void setMake(String make) {
		this.make = make;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getLicense() {
		return license;
	}

	public void setLicense(String license) {
		this.license = license;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getEmpID() {
		return empID;
	}

	public void setEmpID(String empID) {
		this.empID = empID;
	}

	public AddressDAO getAddressDAO() {
		return addressDAO;
	}

	public void setAddressDAO(AddressDAO addressDAO) {
		this.addressDAO = addressDAO;
	}
}
