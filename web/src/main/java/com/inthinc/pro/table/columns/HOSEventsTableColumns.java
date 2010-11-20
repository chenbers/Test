package com.inthinc.pro.table.columns;

import com.inthinc.pro.model.TableType;

public class HOSEventsTableColumns extends EventsTableColumns {

	private static final long serialVersionUID = -5835498568541400997L;

	@Override
	public TableType getTableType() {
		return TableType.HOS_EVENTS;
	}

}
