package com.inthinc.pro.backing.dao.ui;
import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import com.inthinc.pro.model.event.EventMapper;

public class EventTypeList implements PickList{

	List<SelectItem> eventTypeSelectList;
	public List<SelectItem> getSelectList() {

		if (eventTypeSelectList == null) {
			eventTypeSelectList = new ArrayList<SelectItem> ();

			eventTypeSelectList.add(new SelectItem(Integer.valueOf(EventMapper.TIWIPRO_EVENT_FULLEVENT), EventMapper.TIWIPRO_EVENT_FULLEVENT + " - FULL EVENT"));
			eventTypeSelectList.add(new SelectItem(Integer.valueOf(EventMapper.TIWIPRO_EVENT_NOTEEVENT), EventMapper.TIWIPRO_EVENT_NOTEEVENT + " - NOTE EVENT"));
			eventTypeSelectList.add(new SelectItem(Integer.valueOf(EventMapper.TIWIPRO_EVENT_SEATBELT), EventMapper.TIWIPRO_EVENT_SEATBELT + " - SEATBELT"));
			eventTypeSelectList.add(new SelectItem(Integer.valueOf(EventMapper.TIWIPRO_EVENT_LOCATION), EventMapper.TIWIPRO_EVENT_LOCATION + " - LOCATION"));
			eventTypeSelectList.add(new SelectItem(Integer.valueOf(EventMapper.TIWIPRO_EVENT_NEW_DRIVER), EventMapper.TIWIPRO_EVENT_NEW_DRIVER + " - NEW DRIVER"));
			eventTypeSelectList.add(new SelectItem(Integer.valueOf(EventMapper.TIWIPRO_EVENT_RPM), EventMapper.TIWIPRO_EVENT_RPM + " - RPM"));
			eventTypeSelectList.add(new SelectItem(Integer.valueOf(EventMapper.TIWIPRO_EVENT_IGNITION_ON), EventMapper.TIWIPRO_EVENT_IGNITION_ON + " - IGNITION_ON"));
			eventTypeSelectList.add(new SelectItem(Integer.valueOf(EventMapper.TIWIPRO_EVENT_IGNITION_OFF), EventMapper.TIWIPRO_EVENT_IGNITION_OFF + " - IGNITION_OFF"));
			eventTypeSelectList.add(new SelectItem(Integer.valueOf(EventMapper.TIWIPRO_EVENT_LOW_BATTERY), EventMapper.TIWIPRO_EVENT_LOW_BATTERY + " - LOW_BATTERY"));
			eventTypeSelectList.add(new SelectItem(Integer.valueOf(EventMapper.TIWIPRO_EVENT_VERSION), EventMapper.TIWIPRO_EVENT_VERSION + " - VERSION"));
			eventTypeSelectList.add(new SelectItem(Integer.valueOf(EventMapper.TIWIPRO_EVENT_NO_DRIVER), EventMapper.TIWIPRO_EVENT_NO_DRIVER + " - NO_DRIVER"));
			eventTypeSelectList.add(new SelectItem(Integer.valueOf(EventMapper.TIWIPRO_EVENT_STATS), EventMapper.TIWIPRO_EVENT_STATS + " - STATS"));
			eventTypeSelectList.add(new SelectItem(Integer.valueOf(EventMapper.TIWIPRO_EVENT_LOW_POWER_MODE), EventMapper.TIWIPRO_EVENT_LOW_POWER_MODE + " - LOW_POWER_MODE"));
			eventTypeSelectList.add(new SelectItem(Integer.valueOf(EventMapper.TIWIPRO_EVENT_CLEAR_DRIVER), EventMapper.TIWIPRO_EVENT_CLEAR_DRIVER + " - CLEAR DRIVER"));
			eventTypeSelectList.add(new SelectItem(Integer.valueOf(EventMapper.TIWIPRO_EVENT_WSZONES_ARRIVAL), EventMapper.TIWIPRO_EVENT_WSZONES_ARRIVAL + " - WSZONES_ARRIVAL"));
			eventTypeSelectList.add(new SelectItem(Integer.valueOf(EventMapper.TIWIPRO_EVENT_WSZONES_DEPARTURE), EventMapper.TIWIPRO_EVENT_WSZONES_DEPARTURE + " - WSZONES_DEPARTURE"));
			eventTypeSelectList.add(new SelectItem(Integer.valueOf(EventMapper.TIWIPRO_EVENT_SPEEDING_EX3), EventMapper.TIWIPRO_EVENT_SPEEDING_EX3 + " - SPEEDING_EX3"));
			eventTypeSelectList.add(new SelectItem(Integer.valueOf(EventMapper.TIWIPRO_EVENT_WSZONES_ARRIVAL_EX), EventMapper.TIWIPRO_EVENT_WSZONES_ARRIVAL_EX + " - WSZONES_ARRIVAL_EX"));
			eventTypeSelectList.add(new SelectItem(Integer.valueOf(EventMapper.TIWIPRO_EVENT_WSZONES_DEPARTURE_EX), EventMapper.TIWIPRO_EVENT_WSZONES_DEPARTURE_EX + " - WSZONES_DEPARTURE_EX"));
			eventTypeSelectList.add(new SelectItem(Integer.valueOf(EventMapper.TIWIPRO_EVENT_POWER_ON), EventMapper.TIWIPRO_EVENT_POWER_ON + " - POWER_ON"));
			eventTypeSelectList.add(new SelectItem(Integer.valueOf(EventMapper.TIWIPRO_EVENT_DIAGNOSTICS_REPORT), EventMapper.TIWIPRO_EVENT_DIAGNOSTICS_REPORT + " - DIAGNOSTICS_REPORT"));
			eventTypeSelectList.add(new SelectItem(Integer.valueOf(EventMapper.TIWIPRO_EVENT_START_SPEEDING), EventMapper.TIWIPRO_EVENT_START_SPEEDING + " - START_SPEEDING"));
			eventTypeSelectList.add(new SelectItem(Integer.valueOf(EventMapper.TIWIPRO_EVENT_UNPLUGGED), EventMapper.TIWIPRO_EVENT_UNPLUGGED + " - UNPLUGGED"));
			eventTypeSelectList.add(new SelectItem(Integer.valueOf(EventMapper.TIWIPRO_EVENT_START_SEATBELT), EventMapper.TIWIPRO_EVENT_START_SEATBELT + " - START_SEATBELT"));
			eventTypeSelectList.add(new SelectItem(Integer.valueOf(EventMapper.TIWIPRO_EVENT_INCOMING_CALL), EventMapper.TIWIPRO_EVENT_INCOMING_CALL + " - INCOMING_CALL"));
			eventTypeSelectList.add(new SelectItem(Integer.valueOf(EventMapper.TIWIPRO_EVENT_OUTGOING_CALL), EventMapper.TIWIPRO_EVENT_OUTGOING_CALL + " - OUTGOING_CALL"));
			eventTypeSelectList.add(new SelectItem(Integer.valueOf(EventMapper.TIWIPRO_EVENT_IDLE), EventMapper.TIWIPRO_EVENT_IDLE + " - IDLE"));
			eventTypeSelectList.add(new SelectItem(Integer.valueOf(EventMapper.TIWIPRO_EVENT_ROLLOVER), EventMapper.TIWIPRO_EVENT_ROLLOVER + " - ROLLOVER"));
			eventTypeSelectList.add(new SelectItem(Integer.valueOf(EventMapper.TIWIPRO_EVENT_COACHING_SPEEDING), EventMapper.TIWIPRO_EVENT_COACHING_SPEEDING + " - COACHING_SPEEDING"));
			eventTypeSelectList.add(new SelectItem(Integer.valueOf(EventMapper.TIWIPRO_EVENT_COACHING_SEATBELT), EventMapper.TIWIPRO_EVENT_COACHING_SEATBELT + " - COACHING_SEATBELT"));
			eventTypeSelectList.add(new SelectItem(Integer.valueOf(EventMapper.TIWIPRO_EVENT_UNPLUGGED_ASLEEP), EventMapper.TIWIPRO_EVENT_UNPLUGGED_ASLEEP + " - UNPLUGGED_ASLEEP"));
		}

		return eventTypeSelectList;
	}

}
