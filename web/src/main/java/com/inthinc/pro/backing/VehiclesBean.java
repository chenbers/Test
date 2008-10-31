package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.BeanUtils;

import com.inthinc.pro.backing.model.GroupHierarchy;
import com.inthinc.pro.dao.GroupDAO;
import com.inthinc.pro.dao.VehicleDAO;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.SafetyDevice;
import com.inthinc.pro.model.State;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.model.VehicleSensitivity;

/**
 * @author David Gileadi
 */
public class VehiclesBean extends BaseAdminBean<VehiclesBean.VehicleView>
{
    private static final List<String>            AVAILABLE_COLUMNS;
    private static final int[]                   DEFAULT_COLUMN_INDICES = new int[] { 0, 1, 8, 12 };

    private static final TreeMap<String, String> YEARS;
    private static final TreeMap<String, State>  STATES;

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
    }

    private VehicleDAO                           vehicleDAO;
    private GroupDAO                             groupDAO;
    private TreeMap<String, Integer>             groups;

    public void setVehicleDAO(VehicleDAO vehicleDAO)
    {
        this.vehicleDAO = vehicleDAO;
    }

    public void setGroupDAO(GroupDAO groupDAO)
    {
        this.groupDAO = groupDAO;
    }

    @Override
    protected List<VehicleView> loadItems()
    {
        // get the vehicles
        List<Vehicle> plainVehicles = new LinkedList<Vehicle>();
        for (int i = 0; i < 110; i++)
            plainVehicles.add(createDummyVehicle());
        // TODO: use the commented line below instead
        // final List<Vehicle> plainVehicles = vehicleDAO.getVehiclesInGroupHierarchy(getUser().getGroupID());

        // convert the Vehicles to VehicleViews
        final LinkedList<VehicleView> items = new LinkedList<VehicleView>();
        for (final Vehicle vehicle : plainVehicles)
            items.add(createVehicleView(vehicle));

        return items;
    }

    @Deprecated
    private Vehicle createDummyVehicle()
    {
        final Vehicle vehicle = new Vehicle();
        vehicle.setVehicleID((int) (Math.random() * Integer.MAX_VALUE));
        vehicle.setGroupID((int) (Math.random() * 5) + 110);
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
        BeanUtils.copyProperties(vehicle, vehicleView);

        // TODO: look up the driver by driverID instead
        vehicleView.setDriver(createDummyName() + ' ' + createDummyName());
        vehicleView.setGroup(groupDAO.findByID(vehicle.getGroupID()));
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
        // TODO: uncomment the below
        // for (final VehicleView vehicle : deleteItems)
        // vehicleDAO.deleteByID(vehicle.getVehicleID());
    }

    @Override
    protected void doSave(List<VehicleView> saveItems)
    {
        // TODO: uncomment the below
        // for (final VehicleView vehicle : saveItems)
        // vehicleDAO.update(vehicle);
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
        if (groups == null)
        {
            groups = new TreeMap<String, Integer>();
            final GroupHierarchy hierarchy = getProUser().getGroupHierarchy();
            for (final Group group : hierarchy.getGroupList())
                groups.put(group.getName(), group.getGroupID());
        }
        return groups;
    }

    public static class VehicleView extends Vehicle implements Selectable
    {
        private String  driver;
        private Group   group;
        private boolean selected;

        public String getDriver()
        {
            return driver;
        }

        public void setDriver(String driver)
        {
            this.driver = driver;
        }

        public Group getGroup()
        {
            return group;
        }

        public void setGroup(Group group)
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
