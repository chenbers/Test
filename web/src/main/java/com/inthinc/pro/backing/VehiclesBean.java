package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.TreeMap;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.inthinc.pro.model.SafetyDevice;
import com.inthinc.pro.model.State;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.model.VehicleCompanyInfo;
import com.inthinc.pro.model.VehicleDescription;
import com.inthinc.pro.model.VehicleLicense;
import com.inthinc.pro.model.VehicleSensitivity;

/**
 * @author David Gileadi
 */
public class VehiclesBean extends BaseAdminBean<VehiclesBean.VehicleView>
{
    private static final Logger                   logger                 = LogManager.getLogger(VehiclesBean.class);

    private static final List<String>             AVAILABLE_COLUMNS;
    private static final int[]                    DEFAULT_COLUMN_INDICES = new int[] { 0, 1, 8, 14, 15 };

    private static final TreeMap<String, String>  YEARS;
    private static final TreeMap<String, State>   STATES;
    private static final TreeMap<String, Integer> GROUPS;

    static
    {
        // available columns
        AVAILABLE_COLUMNS = new ArrayList<String>();
        AVAILABLE_COLUMNS.add("vehicleID");
        AVAILABLE_COLUMNS.add("driver");
        AVAILABLE_COLUMNS.add("group");
        AVAILABLE_COLUMNS.add("year");
        AVAILABLE_COLUMNS.add("make");
        AVAILABLE_COLUMNS.add("model");
        AVAILABLE_COLUMNS.add("color");
        AVAILABLE_COLUMNS.add("description");
        AVAILABLE_COLUMNS.add("VIN");
        AVAILABLE_COLUMNS.add("weight");
        AVAILABLE_COLUMNS.add("licenseNumber");
        AVAILABLE_COLUMNS.add("state");
        AVAILABLE_COLUMNS.add("timeZone");
        AVAILABLE_COLUMNS.add("costPerHour");
        AVAILABLE_COLUMNS.add("startOdometer");
        AVAILABLE_COLUMNS.add("active");
        AVAILABLE_COLUMNS.add("hardAccelerationLevel");
        AVAILABLE_COLUMNS.add("hardBrakeLevel");
        AVAILABLE_COLUMNS.add("hardTurnLevel");
        AVAILABLE_COLUMNS.add("hardVerticalLevel");

        // years
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, 1);
        final int nextYear = cal.get(Calendar.YEAR);
        YEARS = new TreeMap<String, String>(new Comparator<String>()
        {
            @Override
            public int compare(String o1, String o2)
            {
                return o2.compareTo(o1);
            }
        });
        for (int year = 1970; year <= nextYear; year++)
            YEARS.put(String.valueOf(year), String.valueOf(year));

        // states
        final State[] states = State.values();
        STATES = new TreeMap<String, State>();
        for (int i = 0; i < states.length; i++)
            STATES.put(states[i].getName(), states[i]);

        // groups
        GROUPS = new TreeMap<String, Integer>();
        // TODO: populate this from some place real
        GROUPS.put("North", 1);
        GROUPS.put("East", 2);
        GROUPS.put("South", 3);
        GROUPS.put("West", 4);
        GROUPS.put("Down Town", 5);
    }

    public VehiclesBean()
    {
        super();

        List<Vehicle> plainVehicles = new LinkedList<Vehicle>();
        // TODO: get the vehicles from some place real
        for (int i = 0; i < 110; i++)
            plainVehicles.add(createDummyVehicle());

        // convert the Vehicles to VehicleViews
        items = new LinkedList<VehicleView>();
        for (final Vehicle vehicle : plainVehicles)
            items.add(createVehicleView(vehicle));

        // init the filtered items
        filteredItems.addAll(items);

        // page, columns
        page = 1;
        columns = getDefaultColumns();
    }

    @Deprecated
    private Vehicle createDummyVehicle()
    {
        final Vehicle vehicle = new Vehicle();
        vehicle.setVehicleID((int) (Math.random() * Integer.MAX_VALUE));
        vehicle.setCompanyInfo(new VehicleCompanyInfo());
        vehicle.getCompanyInfo().setVehicleIdentification(String.valueOf((int) (Math.random() * Integer.MAX_VALUE)));
        vehicle.getCompanyInfo().setCostPerHour(randomInt(200));
        vehicle.setDescription(new VehicleDescription());
        vehicle.getDescription().setYear(String.valueOf(randomInt(39) + 1970));
        vehicle.getDescription().setMake(createDummyName());
        vehicle.getDescription().setModel(createDummyName());
        vehicle.getDescription().setColor(createDummyName());
        vehicle.getDescription().setWeight(randomInt(20000) + 5000);
        vehicle.getDescription().setVIN(createDummyString("abcdefghijklmnoprstuvwyz1234567890", 17));
        vehicle.setLicense(new VehicleLicense());
        vehicle.getLicense().setNumber(createDummyString("abcdefghijklmnoprstuvwyz", randomInt(2) + 6));
        vehicle.getLicense().setState(State.AZ);
        final String[] timeZones = TimeZone.getAvailableIDs();
        vehicle.setTimeZone(timeZones[randomInt(timeZones.length)]);
        vehicle.setStartOdometer(randomInt(200000));
        // vehicle.setTotalOdometer(vehicle.getStartOdometer() + randomInt(100000));
        vehicle.setActive(Math.random() < .75);
        return vehicle;
    }

    @Deprecated
    private String createDummyName()
    {
        return createDummyString("abcdefghijklmnoprstuvwyz", randomInt(9) + 2);
    }

    @Deprecated
    private String createDummyString(final String letters, final int length)
    {
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++)
        {
            final char letter = letters.charAt(randomInt(letters.length()));
            if (i == 0)
                sb.append(String.valueOf(letter).toUpperCase());
            else
                sb.append(letter);
        }
        return sb.toString();
    }

    @Deprecated
    private int randomInt(int limit)
    {
        return (int) (Math.random() * limit);
    }

    /**
     * Creates a VehicleView object from the given Vehicle object.
     * 
     * @param vehicle
     *            The vehicle.
     * @param score
     *            The vehicle's overall score.
     * @return The new VehicleView object.
     */
    private VehicleView createVehicleView(Vehicle vehicle)
    {
        final VehicleView vehicleView = new VehicleView();

        try
        {
            BeanUtils.copyProperties(vehicleView, vehicle);
        }
        catch (Exception e)
        {
            logger.error("Error converting Vehicle to VehicleView", e);
        }

        // TODO: look up the driver by driverID instead
        vehicleView.setDriver(createDummyName() + ' ' + createDummyName());
        vehicleView.setSelected(false);

        return vehicleView;
    }

    @Override
    protected boolean matchesFilter(VehicleView item, String filterWord)
    {
        return String.valueOf(item.getCompanyInfo().getVehicleIdentification()).startsWith(filterWord) || String.valueOf(item.getDescription().getYear()).startsWith(filterWord)
                || item.getDescription().getMake().toLowerCase().startsWith(filterWord) || item.getDescription().getModel().toLowerCase().startsWith(filterWord)
                || item.getDescription().getVIN().toLowerCase().startsWith(filterWord) || item.getLicense().getNumber().toLowerCase().startsWith(filterWord);
    }

    @Override
    public List<String> getAvailableColumns()
    {
        return AVAILABLE_COLUMNS;
    }

    @Override
    public Map<String, Boolean> getDefaultColumns()
    {
        final HashMap<String, Boolean> columns = new HashMap<String, Boolean>();
        final List<String> availableColumns = getAvailableColumns();
        for (int i : DEFAULT_COLUMN_INDICES)
            columns.put(availableColumns.get(i), true);
        return columns;
    }

    @Override
    public void saveColumns()
    {
        // TODO: save the columns
    }

    @Override
    protected VehicleView createAddItem()
    {
        final Vehicle vehicle = new Vehicle();
        vehicle.setActive(true);
        vehicle.setSafetyDevices(new ArrayList<SafetyDevice>());
        vehicle.setCompanyInfo(new VehicleCompanyInfo());
        vehicle.setDescription(new VehicleDescription());
        vehicle.setLicense(new VehicleLicense());
        vehicle.setSensitivity(new VehicleSensitivity());
        return createVehicleView(vehicle);
    }

    @Override
    protected void doDelete(List<VehicleView> deleteItems)
    {
        // TODO delete the items
    }

    @Override
    protected void doSave(List<VehicleView> saveItems)
    {
        // TODO save the items
    }

    @Override
    protected String getEditRedirect()
    {
        return "go_adminEditVehicle";
    }

    @Override
    protected String getFinishedRedirect()
    {
        return "go_adminVehicles";
    }

    public TreeMap<String, String> getYears()
    {
        return YEARS;
    }

    public TreeMap<String, State> getStates()
    {
        return STATES;
    }

    public TreeMap<String, Integer> getGroups()
    {
        return GROUPS;
    }

    public static class VehicleView extends Vehicle implements Selectable
    {
        private String  driver;
        private boolean selected;

        public String getDriver()
        {
            return driver;
        }

        public void setDriver(String driver)
        {
            this.driver = driver;
        }

        /**
         * @return the selected
         */
        public boolean isSelected()
        {
            return selected;
        }

        /**
         * @param selected
         *            the selected to set
         */
        public void setSelected(boolean selected)
        {
            this.selected = selected;
        }
    }
}
