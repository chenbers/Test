package com.inthinc.pro.automation.utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

import org.apache.log4j.Logger;

import com.inthinc.pro.automation.enums.TextEnum;
import com.inthinc.pro.automation.enums.Values;
import com.inthinc.pro.automation.resources.FileRW;
import com.inthinc.pro.model.configurator.ProductType;


public class RandomValues {
	
	private final static Logger logger = Logger.getLogger(AutomationLogger.class);
	
	private static ArrayList<String> colors= new ArrayList<String>(), make= new ArrayList<String>(), model = new ArrayList<String>();
	private static HashMap<String, HashMap<String, String>> rfid = new HashMap<String, HashMap<String, String>>();
	private Iterator<String> itr;
	private FileRW file;
	private static HashMap<String, HashMap<ProductType, Integer>> states = new HashMap<String, HashMap<ProductType, Integer>>();

	private Random random;
	

	public RandomValues(){
		random = new Random();
		file = new FileRW();
	}
	
	public static RandomValues newOne(){
		return new RandomValues();
	}
	
	public String randomMixedString(Integer length){
		String randomString = "";
		for (int i=0; i<length;i++){
			Boolean intOrChar = random.nextBoolean();
			if (intOrChar){
				randomString += random.nextInt(10);
			}else if (!intOrChar){
				Boolean capOrLower = random.nextBoolean();
				char character = getCharacter();
				if (capOrLower)randomString += Character.toLowerCase(character);
				else{
					randomString += character;
				}
			}
		}
		return randomString;
	}
	
	private Enum<?> getRandomEnum(Enum<?> startFrom){
		Enum<?>[] values = startFrom.getDeclaringClass().getEnumConstants();
		return values[random.nextInt(values.length)];
	}
	
	public TextEnum getEnum(TextEnum random){
		return (TextEnum)getRandomEnum((Enum<?>) random);
	}
	
	public TextEnum getEnum(TextEnum random, String doesntMatchThis){
		TextEnum value;
		while (true){
			value = getEnum(random);
			if (!value.getText().equals(doesntMatchThis)){
				return value;
			}
		}
	}
		
	
	public String getString(Integer length){
		String random = "";
		for (int i=0;i<length;i++){
			Boolean capOrLower = this.random.nextBoolean();
			char character = getCharacter();
			if (capOrLower){
				random += Character.toLowerCase(character);
			}
			else{ random += character;}
		}
		return random;
	}
	
	public char getCharacter(){
		char character= (char)(random.nextInt(26)+65);
		return character;
	}
	
	public String getUpperString(Integer length){
		String randomString = "";
		for (int i=0; i<length;i++){
			Boolean intOrChar = random.nextBoolean();
			if (intOrChar){
				randomString += random.nextInt(10);
			}else if (!intOrChar){
				char character = getCharacter();
				randomString += character;
			}
		}
		return randomString;
	}
	
	
	public String getNumberString(Integer length){
		String randomString = "";
		for (int i=0;i<length;i++){
			randomString += random.nextInt(10);
		}
		return randomString;
	}
	
	public String getPhoneNumber(){
		String phone = getNumberString(3) + "555" + getNumberString(4);
		return phone;
	}
	
	public String getTextMessageNumber(){
		return getPhoneNumber() + "@tmomail.net";
	}
	
	public Long getLong(Integer length){
		assert(length<Math.pow(2, 32));
		String randomInt = getNumberString(length);
		return Long.parseLong(randomInt);
	}
	
	public String getValue(Values type){
		String value = "";
		switch (type){
		case MAKE: value = getStringFromFile(type); break;
		case MODEL: value = getStringFromFile(type); break;
		case COLOR: value = getStringFromFile(type); break;
		case YEAR: value = getYear(); break;
		}
		return value;
	}
	
	public Integer getStateByName(String name, ProductType productType){
		if (states.isEmpty()){
			getStates();
		}
		
		return states.get(name).get(productType);
	}
	
	public Integer getStateByName(String name){
		return getStateByName(name, ProductType.TIWIPRO_R74);
	}

	private String getYear() {
		Calendar calendar = Calendar.getInstance();
		Integer max = calendar.get(Calendar.YEAR)-1969;
		String randomYear = ""+(random.nextInt(max) + 1970);
		return randomYear;
	}
	
	public String getStateByID(Integer id, ProductType productType){
		if (states.isEmpty()){
			getStates();
		}
		Iterator<String> itr = states.keySet().iterator();
		
		while (itr.hasNext()){
			String state = itr.next();
			if (states.get(state).get(productType)==id) return state;
		}
		return "Invalid ID";
	}
	public String getStateByID(Integer id){
		return getStateByID(id, ProductType.TIWIPRO_R74);
	}
	
	public Integer getRandomStateID(ProductType type){
		Integer state;
		if (states.isEmpty()){
			getStates();
		}
		Integer x = 0;
		Integer y = random.nextInt(states.size());
		Iterator<String> itr = states.keySet().iterator();
		while (itr.hasNext()){
			String next = itr.next();
			if (x==y){
				state = states.get(next).get(type);
				return state;
			}
			x++;
		}
		return 40;
	}
	
	private void getStates(){
		states = new HashMap<String, HashMap<ProductType, Integer>>();
		ArrayList<String> rawStates;
		rawStates = file.read(Values.STATES);
		Iterator<String> itr = rawStates.iterator();
		String[] line = new String[3];
		while (itr.hasNext()){
			line = itr.next().split("=");
			HashMap<ProductType, Integer> state = new HashMap<ProductType, Integer>();
			state.put(ProductType.TIWIPRO_R74, Integer.parseInt(line[0]));
			state.put(ProductType.WAYSMART, Integer.parseInt(line[2]));
			states.put(line[1], state);
		}
	}
	
	public String getRandomState(){
		String state;
		if (states.isEmpty()){
			getStates();
		}
		String[] list = (String[]) states.keySet().toArray();
		state = list[random.nextInt(list.length)];
		return state;
	}
	
	public HashMap<String, String> getRandomRFID(){
		String barcode;
		barcode = String.format("%07d", random.nextInt(0004250+1));
		return getRFID(barcode);
	}
	
	public HashMap<String, String> getRFID(String barcode){
		HashMap<String, String> card;
		ArrayList<String> cards = new ArrayList<String>();
		String[] line = new String[3];
		if (rfid.isEmpty()){
			cards = file.read(Values.RFID);
			itr = cards.iterator();
			while (itr.hasNext()){
				line = itr.next().split("=");
				card = new HashMap<String, String>();
				card.put("Barcode", line[0]);
				card.put("RFID1", line[1]);
				card.put("RFID2", line[2]);
				rfid.put(line[0], card);
			}
		}
		return rfid.get(barcode);
	}
	
	
	private String getStringFromFile(Values type){
		String randomValue = "";
		ArrayList<String> array = null, temp = new ArrayList<String>();
		switch (type){
		case MODEL: array = make;break;
		case MAKE:  array = model;break;
		case COLOR: array = colors;break;
		}
		if (array.isEmpty()){
			temp = file.read(type);
			array.addAll(temp);
		}
		randomValue = array.get(random.nextInt(array.size()));
		logger.debug("Random String from " + array.toString() + ": " + randomValue);
		return randomValue;
	}

	public String getEmail() {
		String address = getString(15)+"@"+"tiwisucks.com";
		logger.debug(address);
		return address;
	}
	
	
}
