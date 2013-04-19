package com.inthinc.pro.selenium.steps;

import org.jbehave.core.annotations.When;

import com.inthinc.pro.automation.utils.RandomValues;
import com.inthinc.pro.selenium.pageObjects.PageAdminAddEditVehicle;
import com.inthinc.pro.selenium.pageObjects.PageAdminDevices;
import com.inthinc.pro.selenium.pageObjects.PageAdminEditDevice;
import com.inthinc.pro.selenium.pageObjects.PageAdminVehicles;
import com.inthinc.pro.selenium.pageObjects.PageLogin;

public class StressTestSteps extends LoginSteps {
	
    String firstNames[] = { 
    		"Mike", "Jason", "Colleen", "Jacquie", "Todd", "Matt", "Joe", "Dan", "Bob", "Richard", "Lacie",
    		"Bee", "Phillis", "Dorthey", "Julia", "Fawn", "Genaro", "Ora", "Katherina", "Lexie", "Tamatha",
    		"Camellia", "Tracey", "Jacquelyne", "Theresia", "Regena", "Rena", "Luanne", "Bailey", "Carmina",
    		"Arlen", "Sherell", "Mandie", "Siu", "Lorita", "Freda", "Charlette", "Chantay", "Serina", "Han",
       		"Micaela", "Alyce", "Jonah", "Jana", "Andria", "Denae", "Rosalba", "Rosaria", "Mac", "Diana",
    		"Greg", "Shenita", "Karren", "Damon", "Myrtis", "Trinidad", "Rose", "Kerstin", "Malissa", "Laverne",
       		"Edwardo", "Ila", "Marianne", "Alta", "Isabell", "Vada", "Lady", "Pansy", "Melissa", "Kelley",
    		"Malcom", "Hollis", "Dominique", "Danny", "Slyvia", "Lloyd", "Lakita", "Thomas", "Garland", "Luciano",
       		"Daine", "Bev", "Faith", "Leighann", "Devorah", "Jimmie", "Vannesa", "Valentine", "Jeri", "Jamika",
    		"Maudie", "Shiela", "Libbie", "Hyun", "Veronique", "Fred", 	"Cathy", "Tabitha", "Jasmine", "Adella",
       		"Truman", "Cinda", "Rina", "Robyn", "Phil", "Danae", "Darrick", "Lilia", "Jeffie", "Sade"
    };
    
    String lastNames[] = { 
    		"Smith", "Jones", "Roberts", "Hansen", "Jensen", "Walton", "Western", "Smiles", "Starr", "Lennon",
       		"Maker", "Vantassell", "Flanigan", "Reinke", "Crupi", "Ransdell", "Calvi", "Hargreaves", "Twersky",
    		"Wile", "Roger", "Humbertson", "Wormley", "Mcgilvery", "Ziemer", "Eastman", "Spicher", "Gallimore",
    		"Pharr", "Giordano", "Gooding", "Quintanar", "Forshey", "Kirschner", "Meadow", "Irvine", "Scheidler",
    		"Nunes", "Kirkendoll", "Briggs", "Latz", "Kampf", "Mccafferty", "Seely", "Juhl", "Lane", "Ory",
    		"Falcon", "Zastrow", "Sprow", "Brazee", "Manion", "Frampton", "Rodriguz", "Hobbs", "Friedrichs",
    		"Weinberg", "Schmoll", "Petrosino", "Mccaughey", "Blackford", "Fung", "Bombardier", "Carrion",
    		"Curlee", "Leitzel", "Fincher", "Gearhart", "Bosh", "Portalatin", "Manis", "Butkovich", "Peckham",
    		"Robidoux", "Albee", "Doney", "Urquhart", "Aigner", "Bundick", "Disandro", "Gill", "Fabian", "Weston",
    		"Alire", "Petre", "Rasco", "Goetzinger", "Ates", "Barlett", "Woodall", "Turman", "Bagby", "Cessna",
    		"Gressett", "Ridenour", "Dynes", "Wasinger", "Zendejas", "Goldberg", "Gingrich", "Pipes", "Crenshaw",
       		"Easterday", "Rankin", "Bolt", "Chauvin", "Varela", "Savard", "Escobedo", "Perino", "Blurton", "Chadburn",
    		"Reidy", "Califano", "Sessums", "Schaub", "Portalatin", "Hadnott", "Klimek", "Messner", "Wilding",
    		"Wilkison", "Mccarley", "Rhone", "Fleck", "Cool", "Peebles", "Mawson", "Millsaps", "Mohn", "Quillen",
    		"Sondag", "Levert", "Audie", "Kutcher", "Digiovanni", "Trinidad", "Maas", "Overly", "Dufner", "Mapp",
    		"Foston", "Wishart", "Pavon", "Siewert", "Garbett", "Hermosillo", "Townsel", "Resler", "Maricle",
       		"Nilles", "Dorothy", "Lucy", "Ericson", "Dustin", "Kiefer", "Bresett", "Looper", "Garduno"
    };
	
    String makes[] = { 
    		"Ford",
    		"Volkswagen",
    		"Chevrolet",
    		"Hyundai",
    		"Honda",
    		"Toyota",
    		"Dodge",
    		"GMC",
    		"Mazda",
    		"Nissan"
    };
    String models[][] = {
    		{"F-150", "F-250", "Taurus", "F-350", "Mustang", "Focus", "Explorer", "GT", "Mondeo"},
    		{"Jetta", "Rabbit", "Beetle", "Golf", "Sirocco", "GTI", "Passat", "Tiguan", "Touareg"},
    		{"Blazer", "Camaro", "Corvette", "Cruze", "Equinox", "Impala", "Malibu", "Sonic", "Spark"},
    		{"Accent", "Elantra", "Equus", "Genesis", "Santa Fe", "Sonata", "Tucson", "Veloster", "Azera"},
    		{"Accord", "Civic", "CR-V", "Fit", "Odyssey", "Pilot", "CR-Z", "Crosstour", "Insight"},
    		{"Tundra", "Rav-4", "Corolla", "Matrix", "Land Cruiser", "Sequoia", "Sienna", "Venza", "4Runner"},
    		{"Challenger", "Dart", "Stratus", "Avenger", "Charger", "Intrepid", "Neon", "Viper", "Durango"},
    		{"Acadia", "Savana", "Terrain", "Yukon", "Sierra 1500", "Sierra 2500", "Sierra 3500", "Canyon", "Denali"},
    		{"MX-6", "Mazda2", "Mazda3", "Mazdaspeed3", "Mazda5", "Mazda6", "Mazdaspeed6", "Protege", "Millenia"},
    		{"Frontier", "Skyline", "Titan", "Altima", "Maxima", "350Z", "370Z", "Armada", "GT-R"},
    };
	
    PageLogin loginPage = new PageLogin();
    PageAdminVehicles vehPage = new PageAdminVehicles();
    PageAdminAddEditVehicle vehaddedit = new PageAdminAddEditVehicle();
    PageAdminDevices devicesPage = new PageAdminDevices();
    PageAdminEditDevice deviceEditPage = new PageAdminEditDevice();    
    private RandomValues random;
    
    @When("I create one thousand vehicles")
    public void whenICreateOneThousandVehicles() {
    	int i = 0;
    	while (i < 1001) {
    	random = new RandomValues();
		String veh_vin = random.getCharString(17);
		String veh_id = random.getCharString(20);
		int getrandom = random.getInt(9);
        String make = makes[getrandom];
        String model = models[getrandom][random.getInt(8)];
        
		vehaddedit._link().adminVehicles().click();
		vehaddedit._link().adminAddVehicle().click();
		vehaddedit._textField().VIN().type(veh_vin);
		vehaddedit._textField().make().type(make);
		vehaddedit._textField().model().type(model);
		vehaddedit._textField().vehicleID().type(veh_id);
		vehaddedit._dropDown().team().select("Top - Stress Team One");
		vehaddedit._button().saveTop().click();
    	i++;
    	}
    }
    
    @When("I assign devices to vehicles")
    public void whenIAssignDevicesToVehicles() {
    	int i = 1;
    	
    	
    	while (i < 1001) {
    		devicesPage._link().adminDevices().click();
    		devicesPage._link().vehicleID().click();
    		devicesPage._link().entryEdit().getFirstClickableLink().click();
    		deviceEditPage._link().showHideVehiclesForAssignment().click();
    		deviceEditPage._link().assigned().click();
    		deviceEditPage._link().assigned().click();
    		deviceEditPage._button().vehicleTableEntrySelector().click();
    		deviceEditPage._button().saveTop().click();
    	i++;
    	}
    }
    
    @When("I assign drivers to vehicles")
    public void whenIAssignDriversToVehicles() {
    	int i = 1;
    	random = new RandomValues();
    	int getrandom = random.getInt(9);
        String make = makes[getrandom];
        String model = models[getrandom][random.getInt(8)];
        	
    	while (i < 1001) {
    		vehaddedit._link().adminVehicles().click();
    		vehPage._textField().searchVehicle().type("Unknown Driver");
    		vehPage._button().searchVehicle().click();
    		vehPage._link().sortByDevice().click();
    		vehPage._link().sortByDevice().click();
    		vehPage._link().entryEdit().click();
    		vehaddedit._link().assignDriver().click();
    		vehaddedit._popUp().assign()._links().sortAssigned().click();
    		vehaddedit._popUp().assign()._links().assign().getFirstClickableLink().click();
    		vehaddedit._textField().eCallPhone().type("1111111111");
    		vehaddedit._textField().make().type(make);
    		vehaddedit._textField().model().type(model);
    		vehaddedit._button().saveTop().click();
    	i++;
    	}
    }   

}