package com.inthinc.pro.backing.dao.ui;
import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import com.inthinc.pro.model.event.NoteType;

public class EventTypeList implements PickList{

	List<SelectItem> eventTypeSelectList;
	public List<SelectItem> getSelectList() {

		if (eventTypeSelectList == null) {
			eventTypeSelectList = new ArrayList<SelectItem> ();
	        for (NoteType noteType: NoteType.values())
	            addSelectItem(noteType);
		}

		return eventTypeSelectList;
	}
	private void addSelectItem(NoteType noteType)
	{
	    if (noteType.getCode() > 0)
	        eventTypeSelectList.add(new SelectItem(noteType.getCode(), noteType.getCode() + " - " + noteType.getName()));
	}
}
