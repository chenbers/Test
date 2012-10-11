package com.inthinc.pro.reports.dao.mock;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.Interval;

import com.inthinc.pro.dao.StateMileageDAO;
import com.inthinc.pro.model.StateMileage;

/**
 * Mock for StateMileageDAO.
 */
public class MockStateMileageDAO implements StateMileageDAO {
    private static final String STATE_1 = "Florida";
    private static final String STATE_2 = "Ohio";
    private static final String MOCK_PREFIX = "STUB ";
    private static final String MOCK_MONTH_AUG = "Aug, 2010";
    private static final String MOCK_MONTH_SEP = "Sep, 2010";
    private static final Integer GROUP_FLEET = 177;
    private static final Integer GROUP_DIVISION = 178;
    private static final Integer GROUP_GOOD = 180;

    private SimpleDateFormat monthOnlyDateFormat = new SimpleDateFormat("MMMM");


    /**
     * Default constructor.
     */
    public MockStateMileageDAO() {}

    /**
     * @{inherit-doc
     * @see com.inthinc.pro.dao.StateMileageDAO#getFuelStateMileageByVehicle(java.lang.Integer, org.joda.time.Interval, java.lang.Boolean)
     */
    @Override
    public List<StateMileage> getFuelStateMileageByVehicle(Integer groupID, Interval interval, Boolean dotOnly) {
        return this.getFuelStateMileageByVehicleData(groupID, interval, dotOnly);
    }

    /**
     * @{inherit-doc
     * @see com.inthinc.pro.dao.StateMileageDAO#getMileageByVehicle(java.lang.Integer, org.joda.time.Interval, java.lang.Boolean)
     */
    @Override
    public List<StateMileage> getMileageByVehicle(Integer groupID, Interval interval, Boolean dotOnly) {
        return this.getDataBis(groupID, interval, dotOnly);
    }

    /**
     * @{inherit-doc
     * @see com.inthinc.pro.dao.StateMileageDAO#getStateMileageByGroup(java.lang.Integer, org.joda.time.Interval, java.lang.Boolean)
     */
    @Override
    public List<StateMileage> getStateMileageByGroup(Integer groupID, Interval interval, Boolean dotOnly) {
        return this.getStateMileageByGroupData(groupID, interval, dotOnly);
    }

    /**
     * @{inherit-doc
     * @see com.inthinc.pro.dao.StateMileageDAO#getStateMileageByGroupAndMonth(java.lang.Integer, org.joda.time.Interval, java.lang.Boolean)
     */
    @Override
    public List<StateMileage> getStateMileageByGroupAndMonth(Integer groupID, Interval interval, Boolean dotOnly) {
        List<StateMileage> list = new ArrayList<StateMileage>();
        StateMileage bean = newInstance(groupID, STATE_1, "257547", 1500L);
        bean.setMonth("October");
        list.add(bean);
        bean = newInstance(groupID, STATE_1, "257547", 1540L);
        bean.setMonth("September");
        list.add(bean);
        bean = newInstance(groupID, STATE_1, "224547", 1360L);
        bean.setMonth("October");
        list.add(bean);
        return list;
    }

    /**
     * @{inherit-doc
     * @see com.inthinc.pro.dao.StateMileageDAO#getStateMileageByVehicle(java.lang.Integer, org.joda.time.Interval, java.lang.Boolean)
     */
    @Override
    public List<StateMileage> getStateMileageByVehicle(Integer groupID, Interval interval, Boolean dotOnly) {
         return getDataBis(groupID, interval, dotOnly);
    }

    /**
     * @{inherit-doc
     * @see com.inthinc.pro.dao.StateMileageDAO#getStateMileageByVehicleRoad(java.lang.Integer, org.joda.time.Interval, java.lang.Boolean)
     */
    @Override
    public List<StateMileage> getStateMileageByVehicleRoad(Integer groupID, Interval interval, Boolean dotOnly) {
        return this.getDataBis(groupID, interval, dotOnly);
    }

    /*
     * ^ | Mocked methods -------------------------------------------------------------------------------------- | Helper Methods v
     */

    private List<StateMileage> getStateMileageByGroupData(Integer groupID, Interval interval, Boolean dotIftaOnly) {
        // only the first call adds data
        List<StateMileage> list = new ArrayList<StateMileage>();

        Date date = new Date();
        Calendar calendar = Calendar.getInstance();

        /*
         * 2010-10-11 ==================================================================================================
         */
        calendar.set(2010, 9, 11);
        date = calendar.getTime();

        if (interval.contains(date.getTime())) {
            /*
             * FLEET group =============================================================================================
             */
            if (GROUP_FLEET.equals(groupID)) {
                if (!dotIftaOnly) {
                    list.add(createStateMileageByGroupBean(groupID, "Vehicle A", "Colorado", 750, date));
                    list.add(createStateMileageByGroupBean(groupID, "Vehicle B", "Utah", 250L, date));
                }

                list.add(createStateMileageByGroupBean(groupID, "Vehicle C", "Pennsylvanya", 500L, date));
            }

            /*
             * FLEET-DIVISION group ====================================================================================
             */
            if (GROUP_DIVISION.equals(groupID)) {
                if (!dotIftaOnly) {
                    list.add(createStateMileageByGroupBean(groupID, "Vehicle FDA", "Colorado", 250L, date));
                    list.add(createStateMileageByGroupBean(groupID, "Vehicle FDB", "Utah", 500L, date));
                    list.add(createStateMileageByGroupBean(groupID, "Vehicle FDC", "Utah", 750, date));
                }

                list.add(createStateMileageByGroupBean(groupID, "Vehicle FDD", "Idaho", 250L, date));
            }

            /*
             * FLEET-DIVISION-GOOD group ===============================================================================
             */
            if (GROUP_GOOD.equals(groupID)) {
                if (!dotIftaOnly) {
                    list.add(createStateMileageByGroupBean(groupID, "Vehicle FDGA", "California", 250L, date));
                }

                list.add(createStateMileageByGroupBean(groupID, "Vehicle FDGB", "Idaho", 500L, date));
            }
        }

        /*
         * 2010-09-12 ==================================================================================================
         */
        calendar.set(2010, 8, 12);
        date = calendar.getTime();

        if (interval.contains(date.getTime())) {
            /*
             * FLEET group =============================================================================================
             */
            if (GROUP_FLEET.equals(groupID)) {
                if (!dotIftaOnly) {
                    list.add(createStateMileageByGroupBean(groupID, "Vehicle A", "Idaho", 250L, date));
                    list.add(createStateMileageByGroupBean(groupID, "Vehicle B", "California", 500L, date));
                }

                list.add(createStateMileageByGroupBean(groupID, "Vehicle C", "Utah", 500L, date));
            }

            /*
             * FLEET-DIVISION group ====================================================================================
             */
            if (GROUP_DIVISION.equals(groupID)) {
                if (!dotIftaOnly) {
                    list.add(createStateMileageByGroupBean(groupID, "Vehicle FDA", "Iowa", 250L, date));
                    list.add(createStateMileageByGroupBean(groupID, "Vehicle FDB", "California", 500L, date));
                    list.add(createStateMileageByGroupBean(groupID, "Vehicle FDC", "Idaho", 500, date));
                }

                list.add(createStateMileageByGroupBean(groupID, "Vehicle FDD", "Colorado", 250L, date));
            }

            /*
             * FLEET-DIVISION-GOOD group ===============================================================================
             */
            if (GROUP_GOOD.equals(groupID)) {
                if (!dotIftaOnly) {
                    list.add(createStateMileageByGroupBean(groupID, "Vehicle FDGA", "Illinois", 250L, date));
                }

                list.add(createStateMileageByGroupBean(groupID, "Vehicle FDGB", "Utah", 1000L, date));
            }
        }

        /*
         * 2010-08-07 ==================================================================================================
         */
        calendar.set(2010, 7, 7);
        date = calendar.getTime();

        if (interval.contains(date.getTime())) {
            /*
             * FLEET group =============================================================================================
             */
            if (GROUP_FLEET.equals(groupID)) {
                if (!dotIftaOnly) {
                    list.add(createStateMileageByGroupBean(groupID, "Vehicle A", "Georgia", 750L, date));
                    list.add(createStateMileageByGroupBean(groupID, "Vehicle B", "South Carolina", 1000L, date));
                }

                list.add(createStateMileageByGroupBean(groupID, "Vehicle C", "Colorado", 750L, date));
            }

            /*
             * FLEET-DIVISION group ====================================================================================
             */
            if (GROUP_DIVISION.equals(groupID)) {
                if (!dotIftaOnly) {
                    list.add(createStateMileageByGroupBean(groupID, "Vehicle FDA", "Nevada", 250L, date));
                    list.add(createStateMileageByGroupBean(groupID, "Vehicle FDB", "Nevada", 250L, date));
                    list.add(createStateMileageByGroupBean(groupID, "Vehicle FDC", "Idaho", 500L, date));
                }

                list.add(createStateMileageByGroupBean(groupID, "Vehicle FDD", "Pennsylvanya", 250L, date));
            }

            /*
             * FLEET-DIVISION-GOOD group ===============================================================================
             */
            if (GROUP_GOOD.equals(groupID)) {
                if (!dotIftaOnly) {
                    list.add(createStateMileageByGroupBean(groupID, "Vehicle FDGA", "Nevada", 250L, date));
                }

                list.add(createStateMileageByGroupBean(groupID, "Vehicle FDGB", "Idaho", 500L, date));
            }
        }

        return list;
    }

    private StateMileage createStateMileageByGroupBean(Integer groupID, String vehicleName, String stateName, long miles, Date date) {
        StateMileage bean;
        bean = new StateMileage();
        bean.setGroupID(groupID);
        bean.setVehicleName(vehicleName);
        bean.setStateName(stateName);
        bean.setMiles(miles);
        bean.setMonth(monthOnlyDateFormat.format(date));
        return bean;
    }

    private List<StateMileage> getDataBis(Integer groupID, Interval interval, Boolean isDotIfta) {
        // only the first call adds data
        List<StateMileage> list = new ArrayList<StateMileage>();

        StateMileage bean = new StateMileage();

        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.set(2010, 9, 10);
        date = calendar.getTime();

        if (interval.contains(date.getTime()) && GROUP_FLEET.equals(groupID)) {
            bean.setGroupID(groupID);
            bean.setVehicleName(MOCK_PREFIX + "10026217");
            bean.setStateName(MOCK_PREFIX + "Colorado");
            bean.setOnRoadFlag(true);
            bean.setMonth("February");
            bean.setMiles(3108L);
            list.add(bean);
        }

        calendar.set(2010, 9, 11);
        date = calendar.getTime();

        if (interval.contains(date.getTime()) && GROUP_FLEET.equals(groupID)) {
            bean = new StateMileage();
            bean.setGroupID(groupID);
            bean.setVehicleName(MOCK_PREFIX + "11077461");
            bean.setStateName(MOCK_PREFIX + "Colorado");
            bean.setOnRoadFlag(true);
            bean.setMonth("February");
            bean.setMiles(4484L);
            list.add(bean);
        }

        calendar.set(2010, 9, 12);
        date = calendar.getTime();

        if (interval.contains(date.getTime()) && GROUP_FLEET.equals(groupID)) {
            bean = new StateMileage();
            bean.setGroupID(groupID);
            bean.setVehicleName(MOCK_PREFIX + "11187740");
            bean.setStateName(MOCK_PREFIX + "Colorado");
            bean.setOnRoadFlag(true);
            bean.setMonth("February");
            bean.setMiles(1817L);
            list.add(bean);
        }

        calendar.set(2010, 9, 13);
        date = calendar.getTime();

        if (interval.contains(date.getTime()) && GROUP_DIVISION.equals(groupID)) {
            bean = new StateMileage();
            bean.setGroupID(groupID);
            bean.setVehicleName(MOCK_PREFIX + "10740909");
            bean.setStateName(MOCK_PREFIX + "New Mexico");
            bean.setOnRoadFlag(false);
            bean.setMonth("February");
            bean.setMiles(827L);
            list.add(bean);
        }

        calendar.set(2010, 9, 14);
        date = calendar.getTime();

        if (interval.contains(date.getTime()) && !isDotIfta && GROUP_DIVISION.equals(groupID)) {
            bean = new StateMileage();
            bean.setGroupID(groupID);
            bean.setVehicleName(MOCK_PREFIX + "12345678");
            bean.setStateName(MOCK_PREFIX + "South Dakota");
            bean.setOnRoadFlag(false);
            bean.setMonth("February");
            bean.setMiles(827L);
            list.add(bean);
        }

        calendar.set(2010, 9, 15);
        date = calendar.getTime();

        if (interval.contains(date.getTime()) && !isDotIfta && GROUP_DIVISION.equals(groupID)) {
            bean = new StateMileage();
            bean.setGroupID(groupID);
            bean.setVehicleName(MOCK_PREFIX + "87654321");
            bean.setStateName(MOCK_PREFIX + "UTAH");
            bean.setOnRoadFlag(false);
            bean.setMonth("February");
            bean.setMiles(927L);
            list.add(bean);
        }

        calendar.set(2010, 9, 15);
        date = calendar.getTime();

        if (interval.contains(date.getTime()) && !isDotIfta && GROUP_GOOD.equals(groupID)) {
            bean = new StateMileage();
            bean.setGroupID(groupID);
            bean.setVehicleName(MOCK_PREFIX + "87654320");
            bean.setStateName(MOCK_PREFIX + "UTAH");
            bean.setOnRoadFlag(true);
            bean.setMonth("February");
            bean.setMiles(1027L);
            list.add(bean);
        }

        return list;
    }

    /* creates a new instance of StateMileage */
    private StateMileage newInstance(Integer groupID, String state, String vehicle, Long miles) {
        StateMileage bean = new StateMileage();
        bean.setGroupID(groupID);
        bean.setStateName(state);
        bean.setVehicleName(vehicle);
        bean.setMiles(miles);
        return bean;
    }

    private List<StateMileage> getFuelStateMileageByVehicleData(Integer groupID, Interval interval, Boolean dotOnly) {

        List<StateMileage> list = new ArrayList<StateMileage>();

        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.set(2010, 8, 19);
        date = calendar.getTime();

        if (interval.contains(date.getTime()) && GROUP_FLEET.equals(groupID)) {
            list.addAll(getFuelList(MOCK_MONTH_AUG));
            list.addAll(getFuelList(MOCK_MONTH_SEP));
        }
        return list;
    }

    List<StateMileage> getFuelList(String month) {
        List<StateMileage> list = new ArrayList<StateMileage>();

        String[] states = { "CO", "MT", "ND", "NM", "SD", "TX", "UT", "WY" };
        Map<String, Map<String, Long>> milesByStateMonth = getFuelMilesMap();

        // For each state add a bean with the specified miles for that state and months
        for (String state : states) {
            list.add(getFuelBean(state, milesByStateMonth.get(month).get(state), month));
        }
        return list;
    }

    private Map<String, Map<String, Long>> getFuelMilesMap() {
        // Map<Month, Map<state, Long>>
        Map<String, Map<String, Long>> milesByStateMonth = new HashMap<String, Map<String, Long>>();
        Map<String, Long> mapAug = new HashMap<String, Long>();
        Map<String, Long> mapSep = new HashMap<String, Long>();

        milesByStateMonth.put(MOCK_MONTH_AUG, mapAug);
        milesByStateMonth.put(MOCK_MONTH_SEP, mapSep);

        mapAug.put("CO", 328L);
        mapAug.put("NM", 664L);
        mapSep.put("CO", 2L);
        mapSep.put("NM", 326L);

        return milesByStateMonth;
    }

    private StateMileage getFuelBean(String stateName, Long miles, String month) {
        StateMileage bean = new StateMileage();
        bean.setGroupID(GROUP_FLEET);
        bean.setVehicleName("10001794");
        bean.setMonth(month);
        bean.setStateName(stateName);
        bean.setMiles(miles == null ? 0L : miles);
        bean.setTruckGallons(0F);
        bean.setTrailerGallons(0F);

        return bean;
    }

}
