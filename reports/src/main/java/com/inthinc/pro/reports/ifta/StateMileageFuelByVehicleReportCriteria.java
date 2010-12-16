package com.inthinc.pro.reports.ifta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import org.joda.time.Interval;

import com.inthinc.pro.dao.util.MeasurementConversionUtil;
import com.inthinc.pro.model.FuelEfficiencyType;
import com.inthinc.pro.model.MeasurementType;
import com.inthinc.pro.model.StateMileage;
import com.inthinc.pro.reports.ReportCriteria;
import com.inthinc.pro.reports.ReportType;
import com.inthinc.pro.reports.ifta.model.StateMileageFuelByVehicle;

/**
 * Report Criteria for StateMileageFuelByVehicle report.
 */
public class StateMileageFuelByVehicleReportCriteria extends DOTReportCriteria {

    /**
     * Default constructor.
     * @param locale
     */
    public StateMileageFuelByVehicleReportCriteria(Locale locale) {
        super(ReportType.STATE_MILEAGE_FUEL_BY_VEHICLE, locale);
    }

    /**
     * {@inheritDoc}
     * @see com.inthinc.pro.reports.ifta.DOTReportCriteria#getDataByGroup(java.lang.Integer, org.joda.time.Interval, boolean)
     */
    @Override
    List<StateMileage> getDataByGroup(Integer groupID, Interval interval, boolean dotOnly) {
        return stateMileageDAO.getFuelStateMileageByVehicle(groupID, interval, dotOnly);
    }

	/**
     * Populate the data set with data.
     * Copy the fields from the record returned from the Back End to the fields in the beans to be used by Jasper.
     * 
     * @param records The records retrieved from the Back End
     */
    void initDataSet(List<StateMileage> records)
    {   
        List<StateMileageFuelByVehicle> dataList = new ArrayList<StateMileageFuelByVehicle>();
        for (StateMileage item : records) {
        	StateMileageFuelByVehicle rec = new StateMileageFuelByVehicle();
        	rec.setGroupName(getShortGroupName(item.getGroupID()));
            rec.setVehicleName(item.getVehicleName());
            rec.setMonth(item.getMonth());
            rec.setState(item.getStateName());
            rec.setTotalMiles(MeasurementConversionUtil.convertMilesToKilometers(
                    item.getMiles(), getMeasurementType()).doubleValue());
            
            Double totalTruckGas = item.getTruckGallons().doubleValue();
            Double totalTrailerGas = item.getTrailerGallons().doubleValue();
            if (getMeasurementType().equals(MeasurementType.METRIC)){
            	totalTruckGas = MeasurementConversionUtil.fromGallonsToLiters(totalTruckGas).doubleValue();
            	totalTrailerGas = MeasurementConversionUtil.fromGallonsToLiters(totalTrailerGas).doubleValue();
            }
            rec.setTotalTruckGas(totalTruckGas);
            rec.setTotalTrailerGas(totalTrailerGas);
            rec.setMileage(getMileage(rec.getTotalMiles(), totalTruckGas));
            
            dataList.add(rec);
        }
        Collections.sort(dataList, new StateMileageFuelByVehicleComparator());         
        setMainDataset(dataList);
    }

    /**
     * Calculates the mileage as totalMiles / totalTruckGas
     * 
     * Based on the GAIN stored procedure:
     * 
     * CASE WHEN gas.totalTruckGas = 0 THEN 0
 	 * ELSE round(coalesce(miles.totalMiles/gas.totalTruckGas, 0), 2)
     * END  mileage
     * 
     * @param totalMiles The total miles driven
     * @param totalTruckGas The amount of gas for the truck 
     * @return The mileage
     */
    Double getMileage(Double totalMiles, Double totalTruckGas) {
		double mileage = 0F;
		if (totalTruckGas != 0){
			mileage = Math.round((totalMiles / totalTruckGas) * 100.0) / 100.0;
		}
		return mileage;
	}

	/**
     *  Comparator for StateMileageByVehicle report 
     */
    class StateMileageFuelByVehicleComparator implements Comparator<StateMileageFuelByVehicle> {

        @Override
        public int compare(StateMileageFuelByVehicle o1, StateMileageFuelByVehicle o2) {
            int comparison = 0; 
            
            // If they are equal, keep comparing other attribs
            if ((comparison = o1.getGroupName().compareTo(o2.getGroupName())) == 0)
                if ((comparison = o1.getVehicleName().compareTo(o2.getVehicleName())) == 0)
                    if ((comparison = o1.getMonth().compareTo(o2.getMonth())) == 0)
	                    comparison = o1.getState().compareTo(o2.getState());
            return comparison;

        }
    }

    /**
     * Use the FuelEfficiencyType to produce a FuelEfficiency string to be used as a key in the resource file
     * 
     * @return The FuelEfficiency Resource Key.
     */
    private String getFuelEfficiencyResourceKey() {
    	String type = getFuelEfficiencyType().toString();
    	
    	// Switch first letter to lowercase
		return Character.toLowerCase(type.charAt(0)) + type.substring(1);
	}

    /**
     * FUEL_EFFICIENCY_TYPE is passed to the report as a String instead of the FuelEfficiencyType enum.
     * This way we can control from here how the resource keys are generated.
     */
	@Override
	public void setFuelEfficiencyType(FuelEfficiencyType fuelEfficiencyType) {
		super.setFuelEfficiencyType(fuelEfficiencyType);
        addParameter(ReportCriteria.FUEL_EFFICIENCY_TYPE, getFuelEfficiencyResourceKey());
	}
    

    
}
