package com.inthinc.pro.configurator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.model.SelectItem;

import com.inthinc.pro.model.Vehicle;

public class MakeModelYear {
	
    private static final String ANY_MAKE = "Any Make";
    private static final String ANY_MODEL = "Any Model";
    private static final String ANY_YEAR = "Any Year";

    private Map<String, Map<String, Map<Integer, List<Integer>>>> makeModelYearCombinations;
    
    public MakeModelYear(List<Vehicle> vehicles){
        
    	makeModelYearCombinations = new HashMap<String, Map<String, Map<Integer, List<Integer>>>>();
    	
    	for(Vehicle v:vehicles){
    		
    		if (v.getMake() != null){
	    		Map<String, Map<Integer, List<Integer>>> modelMap = getModelMap(makeModelYearCombinations, v.getMake());
	    		
	    		if(v.getModel() != null){
	    			
		    		Map<Integer, List<Integer>> yearMap = getYearMap(modelMap,v.getModel());
		    		
		    		if(v.getYear()!= null){
		    			
			    		List<Integer> vehicleList = getVehicleList(yearMap, v.getYear());
			    		vehicleList.add(v.getVehicleID());
		    		}
	    		}
    		}
    	}
    }

    private Map<String,Map<Integer, List<Integer>>> getModelMap(Map<String, Map<String, Map<Integer, List<Integer>>>> makeModelYearCombinations, String make){
    	
    	Map<String, Map<Integer, List<Integer>>> modelMap = makeModelYearCombinations.get(make);
    	if (modelMap == null){
    		
    		 modelMap = new HashMap<String, Map<Integer, List<Integer>>>();
    		 makeModelYearCombinations.put(make, modelMap);
    	}
    	return modelMap;
    }
 
    private Map<Integer, List<Integer>> getYearMap(Map<String,Map<Integer, List<Integer>>> modelMap, String model){
    	
    	Map<Integer, List<Integer>> yearMap = modelMap.get(model);
    	if (yearMap == null){
    		yearMap = new HashMap<Integer, List<Integer>>();
    		modelMap.put(model, yearMap);
    	}
    	return yearMap;
    }

    private List<Integer> getVehicleList(Map<Integer, List<Integer>> yearMap, Integer year){
    	
    	List<Integer> vehicleList = yearMap.get(year);
    	if (vehicleList == null){
    		vehicleList = new ArrayList<Integer>();
    		yearMap.put(year, vehicleList);
    	}
    	return vehicleList;
    }

    public List<SelectItem> getMakes(){
    	
    	List<SelectItem> makeList = new ArrayList<SelectItem>();
		makeList.add(new SelectItem(ANY_MAKE));
		for (String make:makeModelYearCombinations.keySet()){
			
			makeList.add(new SelectItem(make));
		}
		return makeList;
    }
    public List<SelectItem> getModels(String make){
    	
    	List<SelectItem> modelList = new ArrayList<SelectItem>();
        modelList.add(new SelectItem(ANY_MODEL));
        for (String model:makeModelYearCombinations.get(make).keySet()){
			
       	 	modelList.add(new SelectItem(model));
		}
        return modelList;
    }
    public List<SelectItem> getYears(String make, String model){
    	
    	List<SelectItem> yearList = new ArrayList<SelectItem>();
        yearList.add(new SelectItem(ANY_YEAR));
        for (Integer year:makeModelYearCombinations.get(make).get(model).keySet()){
			
       	 	yearList.add(new SelectItem(year));
        }
        return yearList;
    }
    public List<Integer> getVehicleIDs(String make, String model, Integer year){
    	
    	if(make == null || make.equals(ANY_MAKE)) return Collections.emptyList();
    	
    	if(model == null || model.equals(ANY_MODEL)) return allModelVehicles(make);
    	
    	if(year == null || year.equals(ANY_YEAR)) return allYearVehicles(make, model);
    	
    	return makeModelYearCombinations.get(make).get(model).get(year);
     }
    private List<Integer> allYearVehicles(String make, String model){
    	
    	List<Integer> vehicleIDs = new ArrayList<Integer>();
        for (Integer year:makeModelYearCombinations.get(make).get(model).keySet()){
        	
        	vehicleIDs.addAll(makeModelYearCombinations.get(make).get(model).get(year));
        }
        return vehicleIDs;
    }
    private List<Integer> allModelVehicles(String make){
    	
    	List<Integer> vehicleIDs = new ArrayList<Integer>();
        for (String model:makeModelYearCombinations.get(make).keySet()){
        	
        	vehicleIDs.addAll(allYearVehicles(make, model));
        }
        return vehicleIDs;
    }
}
