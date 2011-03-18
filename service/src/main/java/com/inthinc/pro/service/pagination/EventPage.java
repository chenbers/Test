package com.inthinc.pro.service.pagination;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.jboss.resteasy.links.ResourceFacade;

import com.inthinc.pro.model.event.Event;
@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)

public class EventPage implements ResourceFacade<Event> {

    private String groupID;    

    @XmlAttribute
    private Integer totalCount;
    
    @XmlAttribute
    private int pageCount;
    
    @XmlAttribute
    private int start;
    
    private List<Link> links;
    
    private List<Event> events = new ArrayList<Event>();

    
    public void setLinks(List<Link> links)
    {
       this.links = links;
    }

    @XmlTransient
    public String getNext()
    {
       if (links == null) return null;
       for (Link link : links)
       {
          if ("next".equals(link.getRelationship()))
              return link.getHref();
       }
       return null;
    }

    @XmlTransient
    public String getPrevious()
    {
       if (links == null) return null;
       for (Link link : links)
       {
          if ("previous".equals(link.getRelationship()))
                   return link.getHref();
       }
       return null;
    }

    public Class<Event> facadeFor() {
        return Event.class;
    }

    public Map<String, ? extends Object> pathParameters() {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("groupID", groupID);
        return map;
    }

    @XmlElementRef
    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }
    @XmlElementRef
    public List<Link> getLinks()
    {
       return links;
    }

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
}
