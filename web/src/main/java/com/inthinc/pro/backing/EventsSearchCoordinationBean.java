package com.inthinc.pro.backing;

import com.inthinc.pro.model.Group;

public class EventsSearchCoordinationBean extends SearchCoordinationBean {
	
	@Override
	public boolean isGoodSearch(){
		
		return searchFor !=null && !searchFor.isEmpty();
	}

	@Override
	public void setSearchFor(String searchFor) {
		
   		searchFor = searchFor.trim();
		if ( !searchFor.equalsIgnoreCase(this.searchFor)){

			this.searchFor = searchFor;
			notifySearchChangeListeners();
		}		
	}
	
	@Override
	public void setGroup(Group group) {
		
		notifySearchChangeListeners();
		this.group = group;
	}

}
