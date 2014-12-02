package com.inthinc.pro.backing.dao.ui;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import com.inthinc.pro.model.DriveQMetric;

public class DriveQMetricList implements SelectList {

	List<SelectItem> selectList;

	@Override
	public List<SelectItem> getSelectList() {
		if (selectList == null) {
			selectList = new ArrayList<SelectItem> ();
//			selectList.add(new SelectItem(Integer.valueOf(DriveQMetric.DRIVEQMETRIC_STARTING_ODOMETER), DriveQMetric.DRIVEQMETRIC_STARTING_ODOMETER + " - STARTING_ODOMETER"));
//		    selectList.add(new SelectItem(Integer.valueOf(DriveQMetric.DRIVEQMETRIC_ENDING_ODOMETER), DriveQMetric.DRIVEQMETRIC_ENDING_ODOMETER + " - ENDING_ODOMETER"));
//			selectList.add(new SelectItem(Integer.valueOf(DriveQMetric.DRIVEQMETRIC_ODOMETER), DriveQMetric.DRIVEQMETRIC_ODOMETER + " - ODOMETER"));
		    selectList.add(new SelectItem(Integer.valueOf(DriveQMetric.DRIVEQMETRIC_OVERALL), DriveQMetric.DRIVEQMETRIC_OVERALL + " - OVERALL"));
			selectList.add(new SelectItem(Integer.valueOf(DriveQMetric.DRIVEQMETRIC_SPEEDING), DriveQMetric.DRIVEQMETRIC_SPEEDING + " - SPEEDING"));
		    selectList.add(new SelectItem(Integer.valueOf(DriveQMetric.DRIVEQMETRIC_SPEEDING1), DriveQMetric.DRIVEQMETRIC_SPEEDING1 + " - SPEEDING1"));
			selectList.add(new SelectItem(Integer.valueOf(DriveQMetric.DRIVEQMETRIC_SPEEDING2), DriveQMetric.DRIVEQMETRIC_SPEEDING2 + " - SPEEDING2"));
		    selectList.add(new SelectItem(Integer.valueOf(DriveQMetric.DRIVEQMETRIC_SPEEDING3), DriveQMetric.DRIVEQMETRIC_SPEEDING3 + " - SPEEDING3"));
			selectList.add(new SelectItem(Integer.valueOf(DriveQMetric.DRIVEQMETRIC_SPEEDING4), DriveQMetric.DRIVEQMETRIC_SPEEDING4 + " - SPEEDING4"));
		    selectList.add(new SelectItem(Integer.valueOf(DriveQMetric.DRIVEQMETRIC_SPEEDING5), DriveQMetric.DRIVEQMETRIC_SPEEDING5 + " - SPEEDING5"));
			selectList.add(new SelectItem(Integer.valueOf(DriveQMetric.DRIVEQMETRIC_DRIVING_STYLE ), DriveQMetric.DRIVEQMETRIC_DRIVING_STYLE  + " - DRIVING_STYLE "));
		    selectList.add(new SelectItem(Integer.valueOf(DriveQMetric.DRIVEQMETRIC_AGGRESSIVE_BRAKE ), DriveQMetric.DRIVEQMETRIC_AGGRESSIVE_BRAKE  + " - AGGRESSIVE_BRAKE "));
			selectList.add(new SelectItem(Integer.valueOf(DriveQMetric.DRIVEQMETRIC_AGGRESSIVE_ACCEL ), DriveQMetric.DRIVEQMETRIC_AGGRESSIVE_ACCEL  + " - AGGRESSIVE_ACCEL "));
		    selectList.add(new SelectItem(Integer.valueOf(DriveQMetric.DRIVEQMETRIC_AGGRESSIVE_TURN ), DriveQMetric.DRIVEQMETRIC_AGGRESSIVE_TURN  + " - AGGRESSIVE_TURN "));
			selectList.add(new SelectItem(Integer.valueOf(DriveQMetric.DRIVEQMETRIC_AGGRESSIVE_LEFT ), DriveQMetric.DRIVEQMETRIC_AGGRESSIVE_LEFT  + " - AGGRESSIVE_LEFT "));
		    selectList.add(new SelectItem(Integer.valueOf(DriveQMetric.DRIVEQMETRIC_AGGRESSIVE_RIGHT ), DriveQMetric.DRIVEQMETRIC_AGGRESSIVE_RIGHT  + " - AGGRESSIVE_RIGHT "));
			selectList.add(new SelectItem(Integer.valueOf(DriveQMetric.DRIVEQMETRIC_AGGRESSIVE_BUMP ), DriveQMetric.DRIVEQMETRIC_AGGRESSIVE_BUMP  + " - AGGRESSIVE_BUMP "));
		    selectList.add(new SelectItem(Integer.valueOf(DriveQMetric.DRIVEQMETRIC_SEATBELT ), DriveQMetric.DRIVEQMETRIC_SEATBELT  + " - SEATBELT "));
			selectList.add(new SelectItem(Integer.valueOf(DriveQMetric.DRIVEQMETRIC_COACHING ), DriveQMetric.DRIVEQMETRIC_COACHING  + " - COACHING "));
		    selectList.add(new SelectItem(Integer.valueOf(DriveQMetric.DRIVEQMETRIC_MPG_LIGHT ), DriveQMetric.DRIVEQMETRIC_MPG_LIGHT  + " - MPG_LIGHT "));
			selectList.add(new SelectItem(Integer.valueOf(DriveQMetric.DRIVEQMETRIC_MPG_MEDIUM ), DriveQMetric.DRIVEQMETRIC_MPG_MEDIUM  + " - MPG_MEDIUM "));
		    selectList.add(new SelectItem(Integer.valueOf(DriveQMetric.DRIVEQMETRIC_MPG_HEAVY ), DriveQMetric.DRIVEQMETRIC_MPG_HEAVY  + " - MPG_HEAVY "));
			selectList.add(new SelectItem(Integer.valueOf(DriveQMetric.DRIVEQMETRIC_IDLE_LO ), DriveQMetric.DRIVEQMETRIC_IDLE_LO  + " - IDLE_LO "));
		    selectList.add(new SelectItem(Integer.valueOf(DriveQMetric.DRIVEQMETRIC_IDLE_HI ), DriveQMetric.DRIVEQMETRIC_IDLE_HI  + " - IDLE_HI "));
			selectList.add(new SelectItem(Integer.valueOf(DriveQMetric.DRIVEQMETRIC_DRIVE_TIME ), DriveQMetric.DRIVEQMETRIC_DRIVE_TIME  + " - DRIVE_TIME "));


		}

		return selectList;
	}

}
