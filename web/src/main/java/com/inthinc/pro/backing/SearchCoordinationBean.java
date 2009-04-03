package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.List;

import com.inthinc.pro.backing.listener.SearchChangeListener;

public class SearchCoordinationBean {

	private String searchFor = "";
	private int navigation;
	

	private List<SearchChangeListener> searchChangeListeners = new ArrayList<SearchChangeListener>();

	public String getSearchFor() {
		
		return searchFor;
	}
	public boolean isGoodSearch(){
		
		return searchFor !=null && !searchFor.isEmpty();
	}
	
	public void setSearchFor(String searchFor) {
		
   		searchFor = searchFor.trim();
		if ( !searchFor.equalsIgnoreCase(this.searchFor)){

			this.searchFor = searchFor;
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
			 return "go_reports";
		case 2:
			return "go_vehicles";
		case 3:
			return "go_idling";
		case 4: 
			return "go_devices";
		}
		return "go_reports";
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
}
