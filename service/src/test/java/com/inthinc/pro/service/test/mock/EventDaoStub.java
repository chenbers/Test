/**
 * 
 */
package com.inthinc.pro.service.test.mock;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import com.inthinc.pro.dao.EventDAO;
import com.inthinc.pro.model.event.Event;
import com.inthinc.pro.model.event.NoteType;
import com.inthinc.pro.model.pagination.PageParams;
import com.inthinc.pro.model.pagination.TableFilterField;

/**
 * @author dfreitas
 * 
 */
@Component
public class EventDaoStub implements EventDAO {

    @Override
    public Integer update(Event entity) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Event findByID(Integer id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Integer deleteByID(Integer id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Integer create(Integer id, Event entity) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Integer unforgive(Integer driverID, Long noteID) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Event> getWarningEventsForDriver(Integer driverID, Date startDate, Date endDate, Integer includeForgiven) {
        return new ArrayList<Event>();
    }

    @Override
    public List<Event> getViolationEventsForDriver(Integer driverID, Date startDate, Date endDate, Integer includeForgiven) {
        // TODO Auto-generated method stub
        return new ArrayList<Event>();
    }

    @Override
    public List<Event> getMostRecentWarnings(Integer groupID, Integer eventCount) {
        // TODO Auto-generated method stub
        return new ArrayList<Event>();
    }

    @Override
    public List<Event> getMostRecentEvents(Integer groupID, Integer eventCnt) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Event> getMostRecentEmergencies(Integer groupID, Integer eventCount) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Event> getEventsForVehicle(Integer vehicleID, Date startDate, Date endDate, List<NoteType> noteTypes, Integer includeForgiven) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Event> getEventsForGroupFromVehicles(Integer groupID, List<NoteType> noteTypes, Integer daysBack) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Event> getEventsForDriver(Integer driverID, Date startDate, Date endDate, List<NoteType> noteTypes, Integer includeForgiven) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Event> getEventPage(Integer groupID, Date startDate, Date endDate, Integer includeForgiven, List<NoteType> noteTypes, PageParams pageParams) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Event getEventNearLocation(Integer driverID, Double latitude, Double longitude, Date startDate, Date endDate) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Integer getEventCount(Integer groupID, Date startDate, Date endDate, Integer includeForgiven, List<NoteType> noteTypes, List<TableFilterField> filters) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public <T> T getEventByType(Long noteID, Class<T> clazz) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Event> getEmergencyEventsForDriver(Integer driverID, Date startDate, Date endDate, Integer includeForgiven) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Integer forgive(Integer driverID, Long noteID) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Event findByID(Long id) {
        // TODO Auto-generated method stub
        return null;
    }
}
