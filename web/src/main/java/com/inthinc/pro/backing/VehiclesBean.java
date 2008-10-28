package com.inthinc.pro.backing;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.inthinc.pro.backing.ui.ScoreBox;
import com.inthinc.pro.backing.ui.ScoreBoxSizes;
import com.inthinc.pro.model.State;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.model.VehicleDescription;
import com.inthinc.pro.model.VehicleLicense;

/**
 * @author David Gileadi
 */
public class VehiclesBean extends BaseAdminBean<VehiclesBean.VehicleView>
{
    private static final Logger       logger                 = LogManager.getLogger(VehiclesBean.class);

    private static final List<String> AVAILABLE_COLUMNS;
    private static final int[]        DEFAULT_COLUMN_INDICES = new int[] { 0, 1, 2, 9, 15, 16 };

    static
    {
        AVAILABLE_COLUMNS = new ArrayList<String>();
        AVAILABLE_COLUMNS.add("score");
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
            try
            {
                items.add(createVehicleView(vehicle, (int) (Math.random() * 50)));
            }
            catch (Exception e)
            {
                logger.error("Error converting Vehicle to VehicleView", e);
            }

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
        vehicle.setDescription(new VehicleDescription());
        vehicle.getDescription().setYear(String.valueOf(randomInt(39) + 1970));
        vehicle.getDescription().setMake(createDummyName());
        vehicle.getDescription().setModel(createDummyName());
        vehicle.getDescription().setColor(createDummyName());
        vehicle.getDescription().setWeight(randomInt(20000) + 5000);
        vehicle.getDescription().setVIN(createDummyString("abcdefghijklmnoprstuvwyz1234567890", 17));
        vehicle.setLicense(new VehicleLicense());
        vehicle.getLicense().setNumber(createDummyString("abcdefghijklmnoprstuvwyz", randomInt(2) + 6));
        vehicle.getLicense().setState(new State(1, "Arizona", "AZ"));
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
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    @SuppressWarnings("unchecked")
    private VehicleView createVehicleView(Vehicle vehicle, Integer score) throws IllegalAccessException, InvocationTargetException
    {
        final VehicleView vehicleView = new VehicleView();

        BeanUtils.copyProperties(vehicleView, vehicle);

        vehicleView.setScore(score);
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
    public String edit()
    {
        return "go_adminVehicleEdit";
    }

    @Override
    public String delete()
    {
        return "go_adminVehiclesDelete";
    }

    public static class VehicleView extends Vehicle implements Selectable
    {
        private Integer score;
        private String  scoreStyle;
        private String  driver;
        private boolean selected;

        /**
         * @return the score
         */
        public Integer getScore()
        {
            return score;
        }

        /**
         * @param score
         *            the score to set
         */
        public void setScore(Integer score)
        {
            this.score = score;
            this.scoreStyle = new ScoreBox(score, ScoreBoxSizes.SMALL).getScoreStyle();
        }

        /**
         * @return the scoreStyle
         */
        public String getScoreStyle()
        {
            return scoreStyle;
        }

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
