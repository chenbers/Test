package com.inthinc.pro.configurator.ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.model.SelectItem;

import com.inthinc.pro.model.Vehicle;

public class MakeModelYearSelectItems {
	
    public static final String ANY_MAKE  = "Any Make";
    public static final String ANY_MODEL = "Any Model";
    public static final String ANY_YEAR  = "Any Year";

    public static final String NO_MAKE  = "No Make";
    public static final String NO_MODEL = "No Model";
    public static final String NO_YEAR  = "No Year";
    
    private Map<String, Map<String, Map<String, List<Integer>>>> makeModelYearCombinations;
    
	private List<SelectItem> makeList;
	private List<SelectItem> modelList;
	private List<SelectItem> yearList;
    
	protected String make;
	protected String model;
	protected String year;

	public MakeModelYearSelectItems(){
		
		makeList = new ArrayList<SelectItem>();
		modelList = new ArrayList<SelectItem>();
		yearList = new ArrayList<SelectItem>();
	}
	public void createMakeModelYear(List<Vehicle> vehicles){
    	
		createMakeModelYearCombinations(vehicles);
		createViewItems();
    }
    private void createMakeModelYearCombinations(List<Vehicle> vehicles){
    	
		makeModelYearCombinations = new HashMap<String, Map<String, Map<String, List<Integer>>>>();
		
		for(Vehicle v:vehicles){
			
			if (v.getMake() != null){
				
	    		Map<String, Map<String, List<Integer>>> modelMap = getModelMap(v.getMake());
	    		
		    	Map<String, List<Integer>> yearMap = getYearMap(modelMap,v.getModel());
		    		
	    		List<Integer> vehicleList = createVehicleListForYear(yearMap, v.getYear());
	    		
	    		vehicleList.add(v.getVehicleID());
			}
		}
    }
    private Map<String,Map<String, List<Integer>>> getModelMap(String make){
    	
    	String useMake = make==null?NO_MAKE:make;
    	Map<String, Map<String, List<Integer>>> modelMap = makeModelYearCombinations.get(useMake);
    	if (modelMap == null){
    		
    		 modelMap = new HashMap<String, Map<String, List<Integer>>>();
    		 makeModelYearCombinations.put(useMake, modelMap);
    	}
    	return modelMap;
    }
    private Map<String, List<Integer>> getYearMap(Map<String,Map<String, List<Integer>>> modelMap, String model){
    	
    	String useModel = model==null?NO_MODEL:model;

    	Map<String, List<Integer>> yearMap = modelMap.get(useModel);
    	
    	if (yearMap == null){
    		
    		yearMap = new HashMap<String, List<Integer>>();
    		modelMap.put(useModel, yearMap);
    	}
    	return yearMap;
    }

    private List<Integer> createVehicleListForYear(Map<String, List<Integer>> yearMap, Integer year){
    	
    	String useYear = year==null?NO_YEAR:""+year;
    	List<Integer> vehicleList = yearMap.get(useYear);
    	
    	if (vehicleList == null){
    		
    		vehicleList = new ArrayList<Integer>();
    		yearMap.put(useYear, vehicleList);
    	}
    	return vehicleList;
    }
	private void createViewItems(){
		
		makeList = getMakes();
		modelList = new ArrayList<SelectItem>(); 
		yearList =  new ArrayList<SelectItem>();
		
		make = MakeModelYearSelectItems.ANY_MAKE;		
	}


    public List<SelectItem> getMakes(){
    	
    	List<SelectItem> makeList = new ArrayList<SelectItem>();
		makeList.add(new SelectItem(ANY_MAKE));
		for (String make:makeModelYearCombinations.keySet()){
			
			makeList.add(new SelectItem(make));
		}
		return makeList;
    }
    public void setMake(String make) {
		this.make = make;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public List<SelectItem> getModels(String make){
    	
    	if(make.equals(ANY_MAKE)) return Collections.emptyList();
    	
    	List<SelectItem> modelList = new ArrayList<SelectItem>();
        modelList.add(new SelectItem(ANY_MODEL));
        for (String model:makeModelYearCombinations.get(make).keySet()){
			
       	 	modelList.add(new SelectItem(model));
		}
        return modelList;
    }
    public List<SelectItem> getYears(String make, String model){
    	
    	if(model.equals(ANY_MODEL)) return Collections.emptyList();
 
    	List<SelectItem> yearList = new ArrayList<SelectItem>();
        yearList.add(new SelectItem(ANY_YEAR));
        for (String year:makeModelYearCombinations.get(make).get(model).keySet()){
			
       	 	yearList.add(new SelectItem(year));
        }
        return yearList;
    }
    public List<Integer> getVehicleIDsForSelectedMakeModelYear(){
    	
    	if(make == null || make.equals(ANY_MAKE)) return Collections.emptyList();
    	
    	if(model == null || model.equals(ANY_MODEL)) return allModelVehicles(make);
    	
    	if(year == null || year.equals(ANY_YEAR)) return allYearVehicles(make, model);
    	
    	List<Integer> vehicleIDs = makeModelYearCombinations.get(make).get(model).get(year);
    	
    	if (vehicleIDs == null) return Collections.emptyList();
    	return vehicleIDs;
    }
    private List<Integer> allYearVehicles(String make, String model){
    	
    	List<Integer> vehicleIDs = new ArrayList<Integer>();
        for (String year:makeModelYearCombinations.get(make).get(model).keySet()){
        	
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
	public List<SelectItem> getMakeList() {
		return makeList;
	}

	public List<SelectItem> getModelList() {
		return modelList;
	}

	public List<SelectItem> getYearList() {
		return yearList;
	}
	public void makeChanged(String make){
		
		this.make = make;
	    yearList.clear();
		
	    modelList = getModels(make);
	}
	public  void modelChanged(String model){
		
		this.model = model;
		yearList = getYears(make, model);
	}
	
	public void yearChanged(String year){
		
		this.year = year;
	}
	public void clearLists(){
		
		makeList.clear();
		modelList.clear();
		yearList.clear();
	}
   public String getMake() {
		return make;
	}

	public String getModel() {
		return model;
	}

	public String getYear() {
		return year;
	}

}
