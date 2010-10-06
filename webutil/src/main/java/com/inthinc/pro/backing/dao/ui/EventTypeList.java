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
			addSelectItem(NoteType.FULLEVENT);
			addSelectItem(NoteType.NOTEEVENT);
			addSelectItem(NoteType.SEATBELT);
			addSelectItem(NoteType.LOCATION);
			addSelectItem(NoteType.NEW_DRIVER);
			addSelectItem(NoteType.RPM);
			addSelectItem(NoteType.IGNITION_ON);
			addSelectItem(NoteType.IGNITION_OFF);
			addSelectItem(NoteType.LOW_BATTERY);
			addSelectItem(NoteType.VERSION);
			addSelectItem(NoteType.NO_DRIVER);
			addSelectItem(NoteType.STATS);
			addSelectItem(NoteType.LOW_POWER_MODE);
			addSelectItem(NoteType.CLEAR_DRIVER);
			addSelectItem(NoteType.WSZONES_ARRIVAL);
			addSelectItem(NoteType.WSZONES_DEPARTURE);
			addSelectItem(NoteType.SPEEDING_EX3);
			addSelectItem(NoteType.WSZONES_ARRIVAL_EX);
			addSelectItem(NoteType.WSZONES_DEPARTURE_EX);
			addSelectItem(NoteType.POWER_ON);
			addSelectItem(NoteType.DIAGNOSTICS_REPORT);
			addSelectItem(NoteType.START_SPEEDING);
			addSelectItem(NoteType.UNPLUGGED);
			addSelectItem(NoteType.START_SEATBELT);
			addSelectItem(NoteType.INCOMING_CALL);
			addSelectItem(NoteType.OUTGOING_CALL);
			addSelectItem(NoteType.IDLE);
			addSelectItem(NoteType.ROLLOVER);
			addSelectItem(NoteType.COACHING_SPEEDING);
			addSelectItem(NoteType.COACHING_SEATBELT);
			addSelectItem(NoteType.UNPLUGGED_ASLEEP);
		}

		return eventTypeSelectList;
	}
	private void addSelectItem(NoteType noteType)
	{
        eventTypeSelectList.add(new SelectItem(noteType.getCode(), noteType.getCode() + " - " + noteType.toString()));
	}
}
