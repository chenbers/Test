package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.List;

import com.inthinc.pro.backing.listener.SearchChangeListener;

public class SearchCoordinationBean {

	private String searchFor;
	
	private List<SearchChangeListener> searchChangeListeners = new ArrayList<SearchChangeListener>();

	public String getSearchFor() {
		return searchFor;
	}

	public void setSearchFor(String searchFor) {
		this.searchFor = searchFor;
	}

	public List<SearchChangeListener> getSearchChangeListeners() {
		return searchChangeListeners;
	}

	public void setSearchChangeListeners(
			List<SearchChangeListener> searchChangeListeners) {
		this.searchChangeListeners = searchChangeListeners;
	}
	
	public void addSearchChangeListener(SearchChangeListener scl){
		
		searchChangeListeners.add(scl);
	}
	
	public void notifySearchChangeListeners(SearchChangeListener notifier,String searchFor){
		
		this.searchFor = searchFor;
		
		for(SearchChangeListener scl:searchChangeListeners){
			
			if (notifier != scl){
				
				scl.searchChanged(searchFor);
			}
		}
	}
}
