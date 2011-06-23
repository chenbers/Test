package com.inthinc.pro.fulfillment.backing;

import java.text.SimpleDateFormat;
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
import com.inthinc.pro.dao.hessian.proserver.SiloServiceCreator;
import com.inthinc.pro.model.Account;
import com.inthinc.pro.model.Address;
import com.inthinc.pro.model.Device;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.ForwardCommand;
import com.inthinc.pro.model.ForwardCommandStatus;
import com.inthinc.pro.model.ForwardCommandType;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.GroupStatus;
import com.inthinc.pro.model.GroupType;
import com.inthinc.pro.model.LastLocation;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.Status;
import com.inthinc.pro.model.User;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.model.configurator.ProductType;
import com.inthinc.pro.model.event.Event;
import com.inthinc.pro.model.event.NoteType;
import com.inthinc.pro.security.userdetails.ProUser;

public class DAOUtilBean implements PhaseListener {

	private static final long serialVersionUID = 3658982314851556109L;
	private AccountDAO accountDAO;
	private GroupDAO groupDAO;
	private VehicleDAO vehicleDAO;
	private DeviceDAO deviceDAO;
	private EventDAO eventDAO;
	private UserDAO userDAO;
	private PersonDAO personDAO;
	private DriverDAO driverDAO;
	private AddressDAO addressDAO;
    SiloServiceCreator siloServiceCreator;
	

	private RoleDAO roleDAO;

	private Integer shipAccountID;
	
	
	private Device device;
	private Vehicle vehicle;
	private Driver driver;
	private Account account;
	private LastLocation lastLocation;


	private Map<Integer, String> accountMap;
	private Map<Integer, String> groupMap;
	private Map<Integer, String> driverMap;
	private Map<Integer, String> vehicleMap;
	private Integer selectedAccountID;
	private Group topGroupOfSelectedAccount;
	private Integer selectedGroupID;
	private Integer selectedDriverID;
	private Integer selectedVehicleID;

		
	private String imei;
	private String serialNum;
	
	private String vehicleName;
	private String VIN;
	
	private String make;
	private String model;
	private String year;
	private String license;
	private Integer vehicleCount=0;
	private String vehicleCriteria;

	private String lastName;
	private String firstName;
	private String empID;
	private String driverID;
	private String RFID;
	private boolean useFOB;	
	private String driverCriteria;
	
	private String errorMsg;
	private String successMsg;
	private List<String> messageList;
	private Account shipAccount;

	private boolean assignedVehiclesOnly;
	private String vehicleGrid;

	private static final String shipusername = "TiwiInstallation";
	
	private static final String FAILURE = "failure";
	private static final String SUCCESS = "success";
	private static final String NORESULTS = "no results";
	private static final String DATE_FORMAT = "MM/dd/yy hh:mm:ssa";


	
	private String jscriptOnLoad;
	
	public ProUser getProUser() {
		return (ProUser) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
	}

	public void init() throws Exception {
		User shipuser = userDAO.findByUserName(shipusername);
		if (shipuser == null)
			throw new Exception("Fulfillment user not found:" + shipusername);

		shipAccountID = shipuser.getPerson().getAcctID();

		shipAccount = accountDAO.findByID(shipAccountID);

		Integer userAccountID = getProUser().getUser().getPerson().getAcctID();
		if (!shipAccountID.equals(userAccountID))
			throw new Exception("Logged in User not in ship account");

		messageList = new ArrayList<String>();

	}

	// look for powerup events in last year
	// and get last location

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
			calendar.add(Calendar.MONTH, -1);
			Date startDate = calendar.getTime();
			List<NoteType> eventTypes = new LinkedList<NoteType>();
			eventTypes.add(NoteType.POWER_ON);
			List<Event> events = eventDAO.getEventsForVehicle(vehicleID,
					startDate, endDate, eventTypes, 1);

			if (loc != null) {
				messageList.add("GPS OK - Last fix " + loc.getTime() + " "
						+ loc.getLoc().getLat() + " " + loc.getLoc().getLng());
			} else {
				messageList.add("Warning - Device never achieved GPS fix. ");
			}
			if (!events.isEmpty()) {
				Event event = events.get(0);
				messageList.add("OTA OK - First Event "
						+ event.getType() + " "
						+ event.getTime());
				event = events.get(events.size() - 1);
				messageList.add("OTA OK - Last Event "
						+ event.getType() + " "
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
			}
			if (id > 0)
				device = deviceDAO.findByID(id);
		}

		if (device == null)
			setErrorMsg("Error: Device not found " + serialNum
					+ ". Please enter an IMEI, Serial Number or deviceID.");
		else
		{
			if (device.getVehicleID()!=null)
				vehicle=vehicleDAO.findByID(device.getVehicleID());
			if (vehicle!=null)
			{
				if (vehicle.getDriverID()!=null)
					driver=driverDAO.findByID(vehicle.getDriverID());
				lastLocation = vehicleDAO.getLastLocation(vehicle.getVehicleID());
				
			}
			account = accountDAO.findByID(device.getAccountID());
		}
	}

	public void queryDeviceAction() {
		


		
		reInitAction();
		loadDevice();
		if (device != null) {
			
			setSerialNum(null);
		}
	}
	
	public String getLastLocationDisplay()
	{
		//Create object of SimpleDateFormat and pass the desired date format.
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		
		if (device != null) {
			
			if (lastLocation != null)
				return sdf.format(lastLocation.getTime()) + getGoogleMapLink(lastLocation.getLoc().getLat(), lastLocation.getLoc().getLng());
			
		}
		return null;
	}
	
	private String getGoogleMapLink(double lat, double lng)
	{
		return "<a target=\"_blank\" href=\"http://maps.google.com/maps?q="
		+ lat + "," + lng
		+ "\">(" + lat + "," + lng + ")</a>";
	}
	public List<String> getForwardCommands()
	{
		//Create object of SimpleDateFormat and pass the desired date format.
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		List<String> cmds = new ArrayList<String>();
		List<ForwardCommand> fwdCmds = deviceDAO.getForwardCommands(device.getDeviceID(), ForwardCommandStatus.STATUS_ALL);
		if (fwdCmds!=null && !fwdCmds.isEmpty())
		{
			for(Iterator<ForwardCommand> fiter = fwdCmds.iterator(); fiter.hasNext();)
			{
				ForwardCommand cmd = fiter.next();
				String name = cmd.getCmd().toString();
				if (ForwardCommandType.getForwardCommandType(cmd.getCmd())!=null)
					name = ForwardCommandType.getForwardCommandType(cmd.getCmd()).getName();
				
				String status=cmd.getStatus().name().replaceAll("STATUS_", "");
				String values = cmd.getData().toString();
				String tim = sdf.format(cmd.getModified());
				
				cmds.add(0, name + " " + " " + status + "<br/>" + tim + " " + values);
			}			
		}

		return cmds;
	}
	
	public List<String> getVehicleNotes()
	{
		List<Event> events = null;
		List<String> noteList = new ArrayList<String>();
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		
		if (vehicle!=null)
		{
			//List events
			Calendar calendar = Calendar.getInstance();
			Date endDate = new Date();
			calendar.setTime(endDate);
			calendar.add(Calendar.HOUR, -1);
			Date startDate = calendar.getTime();
			
			//TODO Last Ignition On
			//TODO Last Power On
			//TODO EMU file, make, model year
			//TODO Firmware Version
			device.getFirmwareVersion();
			List<NoteType> types = new ArrayList<NoteType>();
	        types.add(NoteType.IGNITION_ON);
            types.add(NoteType.IGNITION_OFF);
            types.add(NoteType.UNPLUGGED);
            types.add(NoteType.POWER_ON);
			try {
				events = eventDAO.getEventsForVehicle(vehicle.getVehicleID(), startDate, endDate, types, 1);
				for(Event e: events)
				{
					String msg="";
					msg+=" " + sdf.format(e.getTime());
					msg+=" " + e.getType();
					msg+=" " + this.getGoogleMapLink((Double)e.getLatitude(), (Double)e.getLongitude());
					msg+=" " + e.getLatitude();
                    msg+=" " + e.getLongitude();
					msg+=" " + e.getAttrMap().toString();
					msg+=" " + e.toString();
					noteList.add(0, msg);
				}
					
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}
		
		return noteList;
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
				device.setAccountID(shipAccountID);

//				device.setEphone(null);
                if (device.getProductVersion() == null || device.getProductVersion() == ProductType.UNKNOWN)
                    device.setProductVersion(ProductType.TIWIPRO_R74);
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

//				device.setEphone(null);
				if (device.getProductVersion() == null || device.getProductVersion() == ProductType.UNKNOWN)
				    device.setProductVersion(ProductType.TIWIPRO_R74);
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
			groupID=getTopGroupOfSelectedAccount().getGroupID();
		}
		
		List<Vehicle> vehicles;
		if (assignedVehiclesOnly)		
			vehicles = getAssignedVehicles(groupID);
		else
			vehicles = vehicleDAO.getVehiclesInGroupHierarchy(groupID);
			

		vehicleGrid = "<table class=\"itable\" width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"3\">";
		vehicleGrid += "<tr class=\"header\"><th>Vehicle</th><th>Tiwi</th><th class=\"last\">RFID</th></tr>";

		vehicleCount = 0;
		for (Iterator<Vehicle> viter = vehicles.iterator(); viter.hasNext();) {
			Vehicle vehicle = viter.next();
			if (vehicle.getStatus().equals(Status.ACTIVE))
			{
				String rowStyle = "\"reg\"";
				if (vehicleCount % 2 == 1)
					rowStyle = "\"alt\"";
				String serialnum = "---";
				if (vehicle.getDeviceID() != null) {
					Device device = deviceDAO.findByID(vehicle.getDeviceID());
					serialnum = device.getSerialNum();
				}

				String barcode = "---";
				if (vehicle.getDriverID() != null) {
					Driver driver = driverDAO.findByID(vehicle.getDeviceID());
					if (driver!=null)
						barcode = driver.getBarcode();
				}


				vehicleGrid += "<tr class=" + rowStyle + ">";
				vehicleGrid += "<td class=\"first\">" + vehicle.getName() + "</td>";
				vehicleGrid += "<td>" + serialnum + "</td>";
				vehicleGrid += "<td class=\"last\">" + barcode + "</td>";
				vehicleGrid += "</tr>";
				vehicleCount++;
			}
		}

		vehicleGrid += "</table>";
	}

	public Integer getVehicleCount()
	{
		return vehicleCount;
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
			setVIN(null);
			setVehicleName(null);
		}
	}

	public void assignRFIDAction() {
		reInitAction();
		
		if (selectedAccountID == null || selectedAccountID < 0) {
			setErrorMsg("Error: Please select a customer account");
			return;
		}
		
		Driver driver = findDriver(driverID);

		if (driver == null) {
			setErrorMsg("Error: Driver not found " + driverID);
		} else if (RFID.length()<2) {
			setErrorMsg("Error: Please enter a valid RFID barcode value " + RFID);
		} else {
			String existMsg = "";
			
			//check if barcode in use!
			Integer currentDriverID = driverDAO.getDriverIDByBarcode(RFID);
			if (currentDriverID != null && currentDriverID > 0) {
				if (!currentDriverID.equals(driver.getDriverID())) {
					Driver currentDriver = driverDAO.findByID(currentDriverID);
					String name = driverID;
					if (currentDriver.getPerson() != null)
						name = currentDriver.getPerson().getFullNameWithId();

					existMsg = "Warning: RFID previously assigned to "
							+ name + "<BR/> ";
					currentDriver.setBarcode(null);
					driverDAO.update(currentDriver);
				}
			}
			String currentBarcode = driver.getBarcode();
			if (currentBarcode != null && !currentBarcode.equals(RFID))
				existMsg += "<BR/> Warning: Driver previously had RFID "
						+ currentBarcode;

			driver.setBarcode(RFID);
			driverDAO.update(driver);

			String name = driverID;
			if (driver.getPerson() != null)
				name = driver.getPerson().getFullNameWithId();

			setSuccessMsg(existMsg + "RFID " + RFID + " successfully assigned to driver: "
					+ name);
			setRFID(null);
			setDriverID(null);
			setUseFOB(false);
		}
	}
	
	public void clearVehicleCriteriaAction()
	{
		this.vehicleCriteria="";
	}

	public void clearDriverCriteriaAction()
	{
		this.driverCriteria="";
	}

	public void searchDriverAction() {
		reInitAction();
		if (selectedAccountID == null || selectedAccountID < 0) {
			setErrorMsg("Error: Please select a customer account");
			return;
		} else if (driverCriteria==null || driverCriteria.length()<1) {
			setErrorMsg("Error: Please enter a valid driverID, Last Name, employeeid");
			return;
		}
		findDrivers(selectedAccountID, driverCriteria);
		if (driverMap.isEmpty())
		{
			setErrorMsg("Driver not found");
		}
	}
	
	public void searchVehicleAction() {
		reInitAction();
		if (selectedAccountID == null || selectedAccountID < 0) {
			setErrorMsg("Error: Please select a customer account");
			return;
		} else if (vehicleCriteria==null || vehicleCriteria.length()<1) {
			setErrorMsg("Error: Please enter a valid vehicle name, license or VIN");
			return;
		}
		findVehicles(vehicleCriteria);
		if (vehicleMap.isEmpty())
		{
			setErrorMsg("Vehicle not found");
		}
	}
	
	public String editDriverAction() {
		reInitAction();
		if (selectedAccountID == null || selectedAccountID < 0) {
			setErrorMsg("Error: Please select a customer account");
			return FAILURE;
		} else if (selectedGroupID == null || selectedGroupID < 0) {
			setErrorMsg("Error: Please select a Team");
			return FAILURE;
		} else if (lastName == null || lastName.trim().length() == 0) {
			setErrorMsg("Error: Please enter a valid lastName");
			return FAILURE;
		}
		
		boolean doCreate=true;
		Driver driver;
		Address address = new Address();;
		Person person;
		
		if (selectedDriverID==-1)
		{
			doCreate=true;
			driver = new Driver();
			person = new Person();
			person.setTimeZone(TimeZone.getDefault());
		}
		else
		{
			doCreate=false;
			driver=driverDAO.findByID(selectedDriverID);
			person=personDAO.findByID(driver.getPersonID());
		}

		if (doCreate)
		{
			Integer addressID = addressDAO.create(selectedAccountID, address);
			if (addressID==null || addressID<1)
			{
				setErrorMsg("Error creating address");
				return FAILURE;
			}
			person.setAddressID(addressID);
		}
		
		person.setLast(lastName.trim());
		person.setFirst(firstName.trim());
		person.setEmpid(empID.trim());

		if (doCreate)
		{
			Integer personID = personDAO.create(selectedAccountID, person);
			if (personID==null || personID<1)
			{
				setErrorMsg("Error creating person");
				return FAILURE;
			}
			driver.setPersonID(personID);
			driver.setGroupID(selectedGroupID);
			driver.setStatus(Status.ACTIVE);
		}
		else
		{
			Integer count = personDAO.update(person);
			if (count==null || count<1)
			{
				setErrorMsg("Error updating person");
				return FAILURE;
			}
		}
			
		String dwarn="";

		if (RFID!=null && RFID.trim().length()>0)
		{
			List<Long> rfids = driverDAO.getRfidsByBarcode(RFID);
			if (rfids==null || rfids.size()==0)
			{
				setErrorMsg("RFID not found for barcode: " + RFID);
				return FAILURE;
			}	
			//check if in use
			Integer currentDriverID = driverDAO.getDriverIDByBarcode(RFID);
			if (currentDriverID!=null && !currentDriverID.equals(driver.getDriverID()))
			{
				Driver currentDriver=driverDAO.findByID(currentDriverID);
				currentDriver.setBarcode("");
				driverDAO.update(currentDriver);
				
				dwarn+="Warning, Barcode previously assigned to driver: " + currentDriverID + "<br/> ";
				
			}
			
		}
		driver.setBarcode(RFID);

		try {
			if (doCreate)
			{
				Integer driverID = driverDAO.create(driver.getPersonID(), driver);
				if (driverID==null || driverID<1)
				{
					setErrorMsg(dwarn + " Error creating driver");
					return FAILURE;
				}
				setSuccessMsg(dwarn+"driverID " + driverID + " created successfully");
			}
			else
			{
				Integer count = driverDAO.update(driver);
				if (count==null || count<1)
				{
					setErrorMsg(dwarn + " Error updating driver");
					return FAILURE;
				}
				setSuccessMsg(dwarn+"driverID " + driver.getDriverID() + " updated successfully");
			}
		} catch (Exception e) {
			setErrorMsg(dwarn + " Exception: " + e.getMessage());
			return FAILURE;
		}
			
		
		lastName=null;
		firstName=null;
		RFID=null;
		empID=null;
		driverMap=null;
		
		return SUCCESS;

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
							vwarn = "Warning: Device was previously assigned to vehicle: "
									+ currentV.getName()
									+ " "
									+ currentV.getFullName() + "<BR/> ";
					}
					if (vehicle.getDeviceID()!=null && vehicle.getDeviceID()!=device.getDeviceID())
					{
						Device currentDevice = deviceDAO.findByID(vehicle.getDeviceID());
						vwarn+="Warning: Vehicle " + vehicleName + " previously had device " + currentDevice.getSerialNum();
					}
					vehicleDAO.setVehicleDevice(vehicle.getVehicleID(), device
							.getDeviceID());
					setSuccessMsg(vwarn + "Device " + serialNum
							+ " successfully assigned to vehicle: "
							+ vehicleName + " " + vehicle.getFullName()
							+ " VIN: " + vehicle.getVIN());
					setSerialNum(null);
					setVehicleName(null);
				}
			}
		}
	}

	public String editVehicleAction() {
		reInitAction();
		
		Integer yearint=0;
		
		if (selectedAccountID == null || selectedAccountID < 0) {
			setErrorMsg("Error: Please select a customer account");
			return FAILURE;
		} else if (selectedGroupID == null || selectedGroupID < 0) {
			setErrorMsg("Error: Please select a Team");
			return FAILURE;
		} 
		if (year != null && year.trim().length() > 0) {
			try {
				yearint=Integer.parseInt(year);
			} catch (NumberFormatException e) {
			}
			if (yearint==null || yearint<1900 || yearint>3000)
			{
				setErrorMsg("Error: Please enter a valid Year");
				return FAILURE;
			}
		} 
		if (serialNum!=null && serialNum.trim().length()>0) {
			loadDevice();
			if (device==null)
			{
				setErrorMsg("Error: Device not found:" + serialNum);
				return FAILURE;
			}
			if (!device.getAccountID().equals(selectedAccountID))
			{
				Account assigned=accountDAO.findByID(device.getAccountID());
				setErrorMsg("Error: Device in account:" + assigned.getAcctName());
				return FAILURE;
			}
		}
	
		Vehicle vehicle = new Vehicle();
		boolean doCreate = true;
		
		if (selectedVehicleID>-1)
		{
			vehicle = vehicleDAO.findByID(selectedVehicleID);
			doCreate=false;
		}

		String vwarn = "";
		Vehicle currentV=null;

		if (device!=null)
		{
			Integer currentVID = device.getVehicleID();
			if (currentVID != null && currentVID > 0
					&& !currentVID.equals(vehicle.getVehicleID())) {
				 currentV = vehicleDAO.findByID(currentVID);
				if (currentV != null
						&& currentV.getStatus().equals(Status.ACTIVE))
					vwarn = "Warning: Device was previously assigned to vehicle: "
							+ currentV.getName()
							+ " "
							+ currentV.getFullName() + "<BR/> ";
			}
		}

		if (VIN!=null && VIN.trim().length()>0)
		{
			currentV = vehicleDAO.findByVIN(VIN);
			if (currentV != null && currentV.getStatus().equals(Status.ACTIVE) && !currentV.getVehicleID().equals(vehicle.getVehicleID()))
			{
				vwarn += "Warning: VIN was previously assigned to vehicle: "
					+ currentV.getName()
					+ " "
					+ currentV.getFullName() + "<BR/> ";
				currentV.setVIN(null);
				vehicleDAO.update(currentV);
			}			
		}
	
		vehicle.setName(vehicleName.trim());
		vehicle.setLicense(license.trim());

		if (VIN.trim().length()>0)
			vehicle.setVIN(VIN.trim());
		else 
			vehicle.setVIN(null);

		vehicle.setMake(make.trim());

		vehicle.setModel(model.trim());

		vehicle.setYear(yearint);
		
		if (device==null)
			vehicle.setDeviceID(null);
		
		vehicle.setGroupID(selectedGroupID);
		vehicle.setStatus(Status.ACTIVE);
		
		Integer vehicleID = vehicle.getVehicleID();
		try {
			if (doCreate)
			{
				vehicleID = vehicleDAO.create(selectedGroupID, vehicle);
				if (vehicleID==null || vehicleID<1)
				{
					setErrorMsg("Error creating vehicleID " + vehicleID);
					return FAILURE;
				}
				setSuccessMsg(vwarn + "VehicleID " + vehicleID + " created successfully");
			}
			else
			{
				Integer count=vehicleDAO.update(vehicle);
				if (count==null || count<1)
				{
					setErrorMsg("Error Updating vehicleID " + vehicle.getVehicleID());
					return FAILURE;
				}
				setSuccessMsg(vwarn + "VehicleID " + vehicleID + " updated successfully");
			}
			if (device!=null)
				vehicleDAO.setVehicleDevice(vehicleID, device.getDeviceID());
		} catch (Exception e) {
			setErrorMsg(vwarn+ " Exception: " + e.getMessage());
			return FAILURE;
		}

		setVIN(null);
		setVehicleName(null);
		vehicleMap=null;
		serialNum=null;
		make=null;
		model=null;
		year=null;
		license=null;

		return SUCCESS;
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
	 
	private Group findTopGroup(Integer accountID) {
		Group topGroup = null;
		List<Group> groups = groupDAO.getGroupsByAcctID(accountID);
		for (Iterator<Group> giter = groups.iterator(); giter.hasNext();) {
			topGroup = giter.next();
			if (topGroup.getParentID().equals(0))
				break;
		}
		return topGroup;
	}

	private Driver findDriver(String name)
	{
		Driver driver = null;

		Group topGroup = getTopGroupOfSelectedAccount();

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
	
	private Vehicle findVehicle(Integer accountID, String name) {
		Vehicle vehicle = null;

		Group topGroup = getTopGroupOfSelectedAccount();

		List<Vehicle> vehicles = vehicleDAO
				.getVehiclesInGroupHierarchy(topGroup.getGroupID());
		
		name = name.toUpperCase().trim();
		for (Iterator<Vehicle> viter = vehicles.iterator(); viter.hasNext();) {
			vehicle = viter.next();
			if (vehicle.getStatus()==Status.ACTIVE)
			{
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
		}

		return null;
	}

	private void findDrivers(Integer accountID, String name)
	{
		driverMap = new Hashtable<Integer, String>();
		vehicleMap = new Hashtable<Integer, String>();
		selectedGroupID=null;

		name = name.toUpperCase().trim();
		if (name.length()==0)
			return;

		Group topGroup = getTopGroupOfSelectedAccount();

		Integer did=0;
		try {
			did = Integer.parseInt(name);
		} catch (NumberFormatException e) {

		}

		List<Driver> drivers = driverDAO.getAllDrivers(topGroup.getGroupID());

		for (Iterator<Driver> diter = drivers.iterator(); diter.hasNext();) {
			Driver driver = diter.next();
			if(driver.getStatus()==Status.ACTIVE)
			{
				Person person = driver.getPerson();
				if ((person.getEmpid()!=null && person.getEmpid().trim().toUpperCase().startsWith(name))
					|| (person.getEmpid()!=null && person.getEmpid().trim().toUpperCase().startsWith(name))
					|| (person.getLast()!=null && person.getLast().trim().toUpperCase().startsWith(name))
					|| (person.getFirst()!=null && person.getFirst().trim().toUpperCase().startsWith(name))
					|| (driver.getBarcode()!=null && driver.getBarcode().trim().toUpperCase().startsWith(name))
					|| (did>0 && did.equals(driver.getDriverID()))
				)
				{
					driverMap.put(driver.getDriverID(), person.getFullNameWithId());
				}
			}
		}


	}
	
	public void findVehicles(String name) 
	{
		vehicleMap = new Hashtable<Integer, String>();
		driverMap = new Hashtable<Integer, String>();
		selectedGroupID=null;

		name = name.toUpperCase().trim();
		if (name.length()==0)
			return;

		
		Group topGroup = getTopGroupOfSelectedAccount();

		Integer vid=0;
		try {
			vid = Integer.parseInt(name);
		} catch (NumberFormatException e) {

		}
				
		List<Vehicle> vehicles = vehicleDAO.getVehiclesInGroupHierarchy(topGroup.getGroupID());

		for (Iterator<Vehicle> viter = vehicles.iterator(); viter.hasNext();) {
			Vehicle vehicle = viter.next();
			if (vehicle.getStatus()==Status.ACTIVE)
			{
				if ((vehicle.getLicense() != null && vehicle.getLicense().trim().toUpperCase().startsWith(name))				
				|| (vehicle.getVIN() != null && vehicle.getVIN().trim().toUpperCase().startsWith(name))
				|| (vehicle.getName() != null && vehicle.getName().trim().toUpperCase().startsWith(name))
				|| (vid>0 && vid.equals(vehicle.getVehicleID()))
				)
				{
					vehicleMap.put(vehicle.getVehicleID(), vehicle.getName() + " " + vehicle.getFullName());
				}
			}
		}

	}

	
	public Map<Integer, String> getAccountMap() {

		if (accountMap == null || accountMap.isEmpty()) {
			accountMap = new Hashtable<Integer, String>();

			List<Account> accounts=null;
try {			
			accounts = accountDAO.getAllAcctIDs();
} catch (HessianRuntimeException e)
{
	e.printStackTrace();
}

//TODO Lose this!!
int limit = 700; 
if (accounts.size()>100)
	limit=100;

			for (Iterator<Account> aiter = accounts.iterator(); aiter.hasNext()
					&& limit > 0; limit--) {
				Account account = aiter.next();
				account = accountDAO.findByID(account.getAccountID());
				if (account != null && account.getStatus() == Status.ACTIVE) {
					String acctName = account.getAcctName();
					if (acctName == null) {
						acctName = account.getAccountID().toString();
					}
					accountMap.put(account.getAccountID(), acctName);
				}
			}
		}
		return accountMap;

	}

	public List<SelectItem> getAccountSelectListWithUnselected() {
		List<SelectItem> accountList = this.getAccountSelectList();
		accountList.add(0, new SelectItem(-1,"--Select a Customer--"));
		return accountList;
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
			if (!id.equals(this.shipAccountID))
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

	public Map<Integer, String> getDriverMap() {
		if (driverMap==null || driverMap.isEmpty())
		{
			driverMap = new Hashtable<Integer, String>();
			
			if (selectedGroupID!=null && selectedGroupID> 0)
			{
				List<Driver> drivers = driverDAO.getAllDrivers(selectedGroupID);

				driverMap.put(-1, "--Create New--");

				for(Iterator<Driver> diter=drivers.iterator(); diter.hasNext();)
				{
					Driver driver = diter.next();
					if (driver.getStatus().equals(Status.ACTIVE) && driver.getPersonID()!=null)
					{
						Person person = personDAO.findByID(driver.getPersonID());
						driverMap.put(driver.getDriverID(), person.getFullNameWithId());
					}
					
				}
			}
		}
		return driverMap;
	}

	public List<SelectItem> getDriverSelectList() {

		List<SelectItem> driverSelectList = new ArrayList<SelectItem>();
		ArrayList sortedArrayList = new ArrayList(getDriverMap().entrySet());
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
			driverSelectList.add(new SelectItem(id, name));
		}

		return driverSelectList;
	}

	public Map<Integer, String> getVehicleMap() {
		if (vehicleMap==null || vehicleMap.isEmpty())
		{
			vehicleMap = new Hashtable<Integer, String>();
			
			if (selectedGroupID!=null && selectedGroupID> 0)
			{
				List<Vehicle> vehicles = vehicleDAO.getVehiclesInGroupHierarchy(selectedGroupID);
				
				vehicleMap.put(-1, "--Create New--");

				for(Iterator<Vehicle> viter=vehicles.iterator(); viter.hasNext();)
				{
					Vehicle vehicle = viter.next();
					if (vehicle.getStatus().equals(Status.ACTIVE))
					{
						vehicleMap.put(vehicle.getVehicleID(), vehicle.getName() +  " " + vehicle.getFullName());
					}
					
				}			
			}			
		}
		return vehicleMap;
	}

	
	public List<SelectItem> getVehicleSelectList() {

		List<SelectItem> vehicleSelectList = new ArrayList<SelectItem>();
		ArrayList sortedArrayList = new ArrayList(getVehicleMap().entrySet());
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
			vehicleSelectList.add(new SelectItem(id, name));
		}

		return vehicleSelectList;
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
		vehicleCount=0;
		device=null;
		vehicle=null;
		driver=null;
		account=null;
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

	private Group getTopGroupOfSelectedAccount()
	{
		if (topGroupOfSelectedAccount==null)
			topGroupOfSelectedAccount=findTopGroup(selectedAccountID);
		return topGroupOfSelectedAccount;
	}
	
	public void setSelectedAccountID(Integer selectedAccountID) {
		if (!selectedAccountID.equals(this.selectedAccountID)) {
			selectedGroupID = null;
			topGroupOfSelectedAccount = null;
			groupMap = null;
			driverMap=null;
			vehicleMap=null;
			selectedVehicleID = null;
			selectedDriverID = null;
		}
		this.selectedAccountID = selectedAccountID;
	}

	public String getSelectedAccountName() {
		if (selectedAccountID == null || selectedAccountID < 0)
			return "--Select a Customer--";
		return getAccountMap().get(this.selectedAccountID);
	}

	public String getSelectedGroupName() {
		if (selectedAccountID == null || selectedAccountID < 0)
			return "--Select a Customer--";
		if (selectedGroupID == null || selectedGroupID < 0)
			return "--Select a Team--";
		
		return getGroupMap().get(this.selectedGroupID);
	}

	public String getSelectedDriverName() {
		if (selectedDriverID == null)
			return "--Select a Driver--";
		return getDriverMap().get(this.selectedDriverID);
	}

	public String getSelectedVehicleName() {
		if (selectedVehicleID == null)
			return "--Select a Vehicle--";
		return getVehicleMap().get(this.selectedVehicleID);
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
		selectedVehicleID = null;
		selectedDriverID = null;
		driverMap=null;
		vehicleMap=null;
	}

	public boolean getAssignedVehiclesOnly() {
		return assignedVehiclesOnly;
	}

	public void setAssignedVehiclesOnly(boolean assignedVehiclesOnly) {
		this.assignedVehiclesOnly = assignedVehiclesOnly;
	}

	@Override
	public void afterPhase(PhaseEvent event) {
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

	public Integer getSelectedDriverID() {
		return selectedDriverID;
	}

	public void setSelectedDriverID(Integer selectedDriverID) {
		this.selectedDriverID = selectedDriverID;
		setLastName(null);
		setFirstName(null);
		setEmpID(null);
		setRFID(null);
		if (selectedDriverID!=null && selectedDriverID>0)
		{
			Driver driver = driverDAO.findByID(selectedDriverID);
			selectedGroupID=driver.getGroupID();

			setRFID(driver.getBarcode());
			Person person = personDAO.findByID(driver.getPersonID());
			setLastName(person.getLast());
			setFirstName(person.getFirst());
			setEmpID(person.getEmpid());
		}
	}

	public Integer getSelectedVehicleID() {
		return selectedVehicleID;
	}

	public void setSelectedVehicleID(Integer selectedVehicleID) {
		this.selectedVehicleID = selectedVehicleID;
		setVehicleName(null);
		setVIN(null);
		setMake(null);
		setModel(null);
		setYear(null);
		setLicense(null);
		setSerialNum(null);			
		if (selectedVehicleID!=null && selectedVehicleID>0)
		{
			Vehicle vehicle = vehicleDAO.findByID(selectedVehicleID);
			selectedGroupID=vehicle.getGroupID();

			setVehicleName(vehicle.getName());
			setVIN(vehicle.getVIN());
			setMake(vehicle.getMake());
			setModel(vehicle.getModel());
			year="";
			if (vehicle.getYear()!=null)
				setYear(vehicle.getYear().toString());
			setLicense(vehicle.getLicense());
			
			if (vehicle.getDeviceID()!=null && vehicle.getDeviceID()>0)
			{
				Device device = deviceDAO.findByID(vehicle.getDeviceID());
				setSerialNum(device.getSerialNum());
				
			}
		}
	}

	public String getDriverCriteria() {
		return driverCriteria;
	}

	public void setDriverCriteria(String driverCriteria) {
		this.driverCriteria = driverCriteria;
	}
	public String getVehicleCriteria() {
		return vehicleCriteria;
	}

	public void setVehicleCriteria(String vehicleCriteria) {
		this.vehicleCriteria = vehicleCriteria;
	}

	public String getJscriptOnLoad() {
		String tmp = jscriptOnLoad;
		//This is a one shot method
		jscriptOnLoad=null;
		return tmp;
	}
	
	public Device getDevice()
	{
		return device;
	}

	public void setJscriptOnLoad(String jscriptOnLoad) {
		this.jscriptOnLoad = jscriptOnLoad;
	}

	public String getOutcome() {
		if (errorMsg!=null && errorMsg.length()>0)
			return "failure";
		return "success";
	}

    public SiloServiceCreator getSiloServiceCreator()
    {
        return siloServiceCreator;
    }

    public void setSiloServiceCreator(SiloServiceCreator siloServiceCreator)
    {
        this.siloServiceCreator = siloServiceCreator;
    }

	public Vehicle getVehicle() {
		return vehicle;
	}

	public Driver getDriver() {
		return driver;
	}
	
	public Account getAccount() {
		return account;
	}

	public LastLocation getLastLocation() {
		return lastLocation;
	}
}
