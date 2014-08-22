package com.inthinc.pro.service.impl;

import com.inthinc.pro.model.event.Event;
import com.inthinc.pro.model.event.NoteType;
import com.inthinc.pro.service.model.EventGetter;
import com.inthinc.pro.service.pagination.EventPage;
import mockit.Expectations;
import org.jboss.resteasy.specimpl.MultivaluedMapImpl;
import org.jboss.resteasy.specimpl.PathSegmentImpl;
import org.jboss.resteasy.specimpl.UriInfoImpl;
import org.junit.Test;

import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.PathSegment;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

/**
 * Unit tests for EventServiceImpl.
 */
public class EventServiceImplTest extends BaseUnitTest {
    EventServiceImpl serviceSUT = new EventServiceImpl();
    private static Integer driverId = 1;
    private static int numNotes = 10;
    private static int firstRow = 0;
    private static int pageCount = 10;
    private static List<NoteType> noteTypes = new ArrayList<NoteType>();
    private static Event event = new Event();
    private static List<Event> eventList = new ArrayList<Event>();
    private static PathSegment pathSegment = null;
    private static URI uri = null;
    private static UriInfoImpl uriInfo = null;

    static {
        event.setDriverID(driverId);
        event.setSpeed(1234);
        eventList.add(event);
        event.setLatitude(10d);
        event.setLongitude(20d);

        MultivaluedMap<String, String> mvm = new MultivaluedMapImpl<String, String>();
        mvm.add("start", String.valueOf(firstRow));
        mvm.add("pageCount", String.valueOf(pageCount));

        pathSegment = new PathSegmentImpl("", mvm);


        try {
            uri = new URI("/a/b/c");
            List<PathSegment> pathSegments = new ArrayList<PathSegment>();
            uriInfo = new UriInfoImpl(uri, uri, "", "", pathSegments);
        } catch (URISyntaxException e) {
            fail("Cannot init uriInfo");
        }
    }

    @Test
    public void getEventCountByDurationTest(final EventGetter mockEventGetter) {
        serviceSUT.setEventGetter(mockEventGetter);

        new Expectations() {
            {
                mockEventGetter.getEventCount("driver", driverId, withAny(noteTypes), withAny(new Date()), withAny(new Date()));
                result = numNotes;
            }
        };

        Response response = serviceSUT.getEventCountByDuration(driverId, new Date(), 10);

        assertNotNull(response);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(response.getEntity(), String.valueOf(numNotes));
    }

    @Test
    public void getEventsByDurationTest(final EventGetter mockEventGetter) {
        serviceSUT.setEventGetter(mockEventGetter);

        new Expectations() {
            {
                mockEventGetter.getEvents("driver", driverId, withAny(noteTypes), withAny(new Date()), withAny(new Date()), firstRow, pageCount);
                result = eventList;
                mockEventGetter.getEventCount("driver", driverId, withAny(noteTypes), withAny(new Date()), withAny(new Date()));
                result = numNotes;
            }
        };

        Response response = serviceSUT.getEventsByDuration(driverId, new Date(), 10, pathSegment, uriInfo);

        assertNotNull(response);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        GenericEntity<List<Event>> entity = (GenericEntity<List<Event>>) response.getEntity();
        EventPage eventPage = (EventPage) entity.getEntity();
        Event lEvent = eventPage.getEvents().get(0);
        assertEquals(lEvent, event);
    }
}
