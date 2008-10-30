package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.inthinc.pro.dao.VehicleDAO;
import com.inthinc.pro.model.SafetyDevice;
import com.inthinc.pro.model.State;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.model.VehicleSensitivity;

/**
 * @author David Gileadi
 */
public class VehiclesBean extends BaseAdminBean<VehiclesBean.VehicleView>
{
    private static final Logger                   logger                 = LogManager.getLogger(VehiclesBean.class);

    private static final List<String>             AVAILABLE_COLUMNS;
    private static final int[]                    DEFAULT_COLUMN_INDICES = new int[] { 0, 1, 8, 12 };

    private static final TreeMap<String, String>  YEARS;
    private static final TreeMap<String, State>   STATES;
    private static final TreeMap<String, Integer> GROUPS;

    static
    {
        // available columns
        AVAILABLE_COLUMNS = new ArrayList<String>();
        AVAILABLE_COLUMNS.add("name");
        AVAILABLE_COLUMNS.add("driver");
        AVAILABLE_COLUMNS.add("group");
        AVAILABLE_COLUMNS.add("year");
        AVAILABLE_COLUMNS.add("make");
        AVAILABLE_COLUMNS.add("model");
        AVAILABLE_COLUMNS.add("color");
        AVAILABLE_COLUMNS.add("unitType");
        AVAILABLE_COLUMNS.add("VIN");
        AVAILABLE_COLUMNS.add("weight");
        AVAILABLE_COLUMNS.add("license");
        AVAILABLE_COLUMNS.add("state");
        AVAILABLE_COLUMNS.add("active");
        AVAILABLE_COLUMNS.add("hardAccelerationLevel");
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

    private VehicleDAO                            vehicleDAO;

    public VehiclesBean()
    {
        super();

        // get the vehicles
        final List<Vehicle> plainVehicles = new LinkedList<Vehicle>();
        for (int i = 0; i < 110; i++)
            plainVehicles.add(createDummyVehicle());
        // TODO: use the commented line below instead
        // final List<Vehicle> plainVehicles = vehicleDAO.getVehiclesByAcctID(getUser().getAccountID());

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

    public void setVehicleDAO(VehicleDAO vehicleDAO)
    {
        this.vehicleDAO = vehicleDAO;
    }

    @Deprecated
    private Vehicle createDummyVehicle()
    {
        final Vehicle vehicle = new Vehicle();
        vehicle.setVehicleID((int) (Math.random() * Integer.MAX_VALUE));
        vehicle.setName(String.valueOf((int) (Math.random() * Integer.MAX_VALUE)));
        vehicle.setYear(String.valueOf(randomInt(39) + 1970));
        vehicle.setMake(createDummyName());
        vehicle.setModel(createDummyName());
        vehicle.setColor(createDummyName());
        vehicle.setWeight(randomInt(20000) + 5000);
        vehicle.setVIN(createDummyString("ABCDEFGHIJKLMNOPRSTUVWYZ1234567890", 17));
        vehicle.setLicense(createDummyString("ABCDEFGHIJKLMNOPRSTUVWYZ1234567890", randomInt(2) + 6));
        vehicle.setState(State.AZ);
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
        // TODO: look up by groupID instead
        vehicleView.setGroup(createDummyName());
        vehicleView.setSelected(false);

        return vehicleView;
    }

    @Override
    protected boolean matchesFilter(VehicleView item, String filterWord)
    {
        return String.valueOf(item.getName()).startsWith(filterWord) || String.valueOf(item.getYear()).startsWith(filterWord)
                || ((item.getUnitType() != null) && item.getUnitType().startsWith(filterWord)) || item.getMake().toLowerCase().startsWith(filterWord)
                || item.getModel().toLowerCase().startsWith(filterWord) || item.getVIN().toLowerCase().startsWith(filterWord)
                || item.getLicense().toLowerCase().startsWith(filterWord);
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
        private String  group;
        private boolean selected;

        public String getDriver()
        {
            return driver;
        }

        public void setDriver(String driver)
        {
            this.driver = driver;
        }

        public String getGroup()
        {
            return group;
        }

        public void setGroup(String group)
        {
            this.group = group;
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
