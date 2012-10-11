package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.List;

import com.inthinc.pro.backing.listener.SearchChangeListener;
import com.inthinc.pro.dao.GroupDAO;
import com.inthinc.pro.model.Group;

public class SearchCoordinationBean {

	protected String searchFor = "";
	
	protected Group group;
	private int navigation;
	private Integer groupID;
	private GroupDAO groupDAO;
	

	private List<SearchChangeListener> searchChangeListeners = new ArrayList<SearchChangeListener>();

	public String getSearchFor() {
		
		return searchFor;
	}
	public boolean isGoodSearch(){
		
		return searchFor !=null && !searchFor.isEmpty() && group==null;
	}
	
	public boolean isGoodGroupId(){
		
		return group != null;
	}
	public void setSearchFor(String searchFor) {
		
   		searchFor = searchFor.trim();
		if ( !searchFor.equalsIgnoreCase(this.searchFor)){

			this.searchFor = searchFor;
			group = null;
			notifySearchChangeListeners();
		}		
	}
	public int getNavigation() {
		return navigation;
	}
	public void setNavigation(int navigation) {
		this.navigation = navigation;
	}
	public String navigationAction(){
		
		switch (navigation){
		case 1:
			 return "pretty:driversReportWithCriteria";
		case 2:
			return "pretty:vehiclesReportWithCriteria";
		case 3:
			return "pretty:idlingReportWithCriteria";
		case 4: 
			return "pretty:devicesReportWithCriteria";
		}
		return "pretty:driversReportWithCriteria";
	}
	public List<SearchChangeListener> getSearchChangeListeners() {
		
		return searchChangeListeners;
	}

	public void setSearchChangeListeners(List<SearchChangeListener> searchChangeListeners) {
		
		this.searchChangeListeners = searchChangeListeners;
	}
	
	public void addSearchChangeListener(SearchChangeListener scl){
		
		searchChangeListeners.add(scl);
	}
	
	public void notifySearchChangeListeners(){
		
		for(SearchChangeListener scl:searchChangeListeners){
			
			scl.searchChanged();
		}
	}
	public Group getGroup() {
		return group;
	}
	public void setGroup(Group group) {
		
		if (group != null /* && this.group != group*/){
			searchFor = group.getName();
		}
		else
		{
		    setSearchFor("");
		}
		
		notifySearchChangeListeners();
		this.group = group;
	}
    public void setGroupID(Integer groupID)
    {
        setGroup(groupDAO.findByID(groupID));
        this.groupID = groupID;
    }
    public Integer getGroupID()
    {
        return groupID;
    }
    public void setGroupDAO(GroupDAO groupDAO)
    {
        this.groupDAO = groupDAO;
    }
    public GroupDAO getGroupDAO()
    {
        return groupDAO;
    }
    
    public void clearSearchFor(){
 
	    setSearchFor("");
	    notifySearchChangeListeners();
    }
}
