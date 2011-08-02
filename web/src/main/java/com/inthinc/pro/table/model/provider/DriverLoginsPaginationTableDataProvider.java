package com.inthinc.pro.table.model.provider;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;

import com.inthinc.pro.dao.EventDAO;
import com.inthinc.pro.dao.GroupDAO;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.event.Event;
import com.inthinc.pro.model.event.EventCategory;
import com.inthinc.pro.model.event.LoginEvent;
import com.inthinc.pro.model.pagination.SortOrder;
import com.inthinc.pro.model.pagination.TableFilterField;
import com.inthinc.pro.model.pagination.TableSortField;

public class DriverLoginsPaginationTableDataProvider extends BaseNotificationPaginationDataProvider<Event> {

    private static final Logger logger = Logger.getLogger(DriverLoginsPaginationTableDataProvider.class);

    private EventDAO eventDAO;
    private Integer groupID;
    private GroupDAO groupDAO;
    private EventCategory eventCategory;

    private List<Event> data;
    private List<Event> results;
    private boolean refreshNeeded = true;

    public DriverLoginsPaginationTableDataProvider() {}

    public void loadData() {
        initStartEndDates();
        data = eventDAO.getEventsForGroupFromVehicles(groupID, eventCategory.getNoteTypesInCategory(), startDate, endDate);
        HashMap<Integer,Group> groups = new HashMap<Integer, Group>();
        //TODO: seems like it would be better if vehicle's group name was returned via eventDAO.getEventsForGroupFromVehicles
        for(Event e: data) {
            if(!groups.containsKey(e.getVehicle().getGroupID())){
                groups.put(e.getVehicle().getGroupID(), groupDAO.findByID(e.getVehicle().getGroupID()));
            }
            e.setGroupName(groups.get(e.getVehicle().getGroupID()).getName());
        }
        setRefreshNeeded(false);
    }

    private boolean hasFilter(List<TableFilterField> fields) {
        for (TableFilterField field : fields) {
            if (field.getFilter() != null && !field.getFilter().toString().equals(""))
                return true;
        }
        return false;
    }

    @Override
    public List<Event> getItemsByRange(int firstRow, int endRow) {
        if (endRow < 0) {
            return new ArrayList<Event>();
        }
        if (endDate == null || startDate == null) {
            initStartEndDates();
        }

        if (isRefreshNeeded())
            loadData();

        Collections.sort(results, getComparator(getSort()));
        return results.subList(firstRow, endRow+1);
    }

    private List<Event> doFilter(List<Event> list, List<TableFilterField> filter) {
        if (!hasFilter(filter))
            return list;
        LoginEvent filterEvent = new LoginEvent();
        ArrayList<Event> filteredResults = new ArrayList<Event>();
        try {
            //translate TableFilterFields into the filterEvent
            for (TableFilterField field : filter) {
                for (final PropertyDescriptor descriptor : BeanUtils.getPropertyDescriptors(filterEvent.getClass())) {
                    if (field.getField().equals(descriptor.getName())) {

                        Class type = descriptor.getPropertyType();
                        Object value = null;
                        if (field.getFilter() instanceof List<?>) {
                            for (Object o : (List<?>) field.getFilter()) {
                                if (o.getClass().equals(type)) {
                                    value = o;
                                }
                            }
                        } else if(field.getFilter() instanceof String){
                            value = field.getFilter();
                        }
                        descriptor.getWriteMethod().invoke(filterEvent, value);
                    }
                }
            }
            //filter list to only those that match the filterEvent
            for (Event e : list) {
                if (e instanceof LoginEvent) {
                    if (((LoginEvent) e).matches(filterEvent)){
                        filteredResults.add(e);
                    }
                }
            }
        } catch (IllegalArgumentException e1) {
            logger.error(e1);
        } catch (IllegalAccessException e1) {
            logger.error(e1);
        } catch (InvocationTargetException e1) {
            logger.error(e1);
        }
        return filteredResults;
    }

    private Comparator<Event> getComparator(TableSortField sortField) {
        final int sortDirection = getSort().getOrder() == SortOrder.ASCENDING ? 1 : -1;
        Comparator<Event> comparator;
        if (getSort().getField().equals("groupName")) {
            comparator = new Comparator<Event>() {
                @Override
                public int compare(Event arg0, Event arg1) {
                    if (arg0.getGroupName() == null && arg1.getGroupName() == null)
                        return 0;
                    if (arg0.getGroupName() == null)
                        return -1;
                    if (arg1.getGroupName() == null)
                        return 1;
                    return arg0.getGroupName().compareTo(arg1.getGroupName()) * sortDirection;
                }
            };
        } else if (getSort().getField().equals("vehicleName")) {
            comparator = new Comparator<Event>() {
                @Override
                public int compare(Event arg0, Event arg1) {
                    if (arg0.getVehicleName() == null && arg1.getVehicleName() == null)
                        return 0;
                    if (arg0.getVehicleName() == null)
                        return -1;
                    if (arg1.getVehicleName() == null)
                        return 1;
                    return arg0.getVehicleName().compareTo(arg1.getVehicleName()) * sortDirection;
                }
            };
        } else {
            comparator = new Comparator<Event>() {
                @Override
                public int compare(Event arg0, Event arg1) {
                    if (arg0.getTime() == null && arg1.getTime() == null)
                        return 0;
                    if (arg0.getTime() == null)
                        return -1;
                    if (arg1.getTime() == null)
                        return 1;
                    return arg0.getTime().compareTo(arg1.getTime()) * sortDirection;
                }
            };
        }
        return comparator;
    }

    @Override
    public int getRowCount() {

        if (groupID == null) {
            return 0;
        }
        if (data == null || isRefreshNeeded())
            loadData();
        List<TableFilterField> fields = getFilters();
        results = doFilter(data, fields);
        return results.size();
    }

    public boolean isRefreshNeeded() {
        return refreshNeeded;
    }

    public void setRefreshNeeded(boolean refreshNeeded) {
        this.refreshNeeded = refreshNeeded;
    }

    public EventDAO getEventDAO() {
        return eventDAO;
    }

    public void setEventDAO(EventDAO eventDAO) {
        this.eventDAO = eventDAO;
    }

    public Integer getGroupID() {
        return groupID;
    }

    public void setGroupID(Integer groupID) {
        this.groupID = groupID;
    }

    public EventCategory getEventCategory() {
        return eventCategory;
    }

    public void setEventCategory(EventCategory eventCategory) {
        this.eventCategory = eventCategory;
    }

    public GroupDAO getGroupDAO() {
        return groupDAO;
    }

    public void setGroupDAO(GroupDAO groupDAO) {
        this.groupDAO = groupDAO;
    }

}
