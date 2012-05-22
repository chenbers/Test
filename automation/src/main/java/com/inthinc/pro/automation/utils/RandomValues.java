package com.inthinc.pro.automation.utils;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

import com.inthinc.pro.automation.enums.ProductType;
import com.inthinc.pro.automation.enums.Values;
import com.inthinc.pro.automation.interfaces.ListEnum;
import com.inthinc.pro.automation.interfaces.TextEnum;
import com.inthinc.pro.automation.logging.Log;

public class RandomValues {

	
	private static HashMap<String, HashMap<String, String>> rfid = new HashMap<String, HashMap<String, String>>();
	private static HashMap<String, HashMap<ProductType, Integer>> states = new HashMap<String, HashMap<ProductType, Integer>>();

	public static final char[] special = { ' ', '!', '"', '#', '$', '%', '&',
			'\'', '(', ')', '*', '+', ',', '.', '/', ':', ';', '<', '=', '>',
			'?', '@', '[', '\\', ']', '^', '_', '`', '{', '|', '}', '~' };

	private static enum Types {
		CHARACTER, INTEGER, SPECIAL
	};

	private Random random;

	public RandomValues() {
		random = new Random();
	}

	public static RandomValues newOne() {
		return new RandomValues();
	}

	public String getMixedString(Integer length) {
		return getString(length, Types.values());
	}

	private String getString(Integer length, Types... types) {
		StringWriter aStringAString = new StringWriter();
		for (int i = 0; i < length; i++) {
			Types type = types[random.nextInt(types.length)];
			if (type == Types.CHARACTER) {
				Boolean upperCase = this.random.nextBoolean();
				if (upperCase) {
					aStringAString.write(getCharacter());
				} else {
					aStringAString.write(getCharacter());
				}
			} else if (type == Types.INTEGER) {
				aStringAString.write(getInt().toString());
			} else if (type == Types.SPECIAL) {
				aStringAString.write(getSpecial());
			}
		}
		return aStringAString.toString();
	}

	public String getCharIntString(Integer length) {
		return getString(length, Types.CHARACTER, Types.INTEGER);
	}

	private Enum<?> getRandomEnum(Enum<?> startFrom) {
		Enum<?>[] values = startFrom.getDeclaringClass().getEnumConstants();
		return values[random.nextInt(values.length)];
	}

	public TextEnum getEnum(TextEnum random) {
		return (TextEnum) getRandomEnum((Enum<?>) random);
	}

	public TextEnum getEnum(TextEnum random, String doesntMatchThis) {
		TextEnum value;
		while (true) {
			value = getEnum(random);
			if (!value.getText().equals(doesntMatchThis)) {
				return value;
			}
		}
	}

	private char getSpecial() {
		return special[random.nextInt(special.length)];
	}

	public String getCharString(Integer length) {
		return getString(length, Types.CHARACTER);
	}

	private Character getCharacter() {
		Character character = (char) (random.nextInt(26) + 65);
		return character;
	}

	private Integer getInt() {
		return random.nextInt(10);
	}
	
	public Integer getInt(Integer max) {
		return random.nextInt(max+1);
	}

	public String getIntString(Integer length) {
		return getString(length, Types.CHARACTER);
	}

	public String getPhoneNumber() {
		String phone = getIntString(3) + "555" + getIntString(4);
		return phone;
	}

	public String getTextMessageNumber() {
		return getTextMessageNumber(10, nextString(Values.TEXT_MESSAGE));
	}
	
	public String getTextMessageNumber(Integer length) {
		return getTextMessageNumber(length, nextString(Values.TEXT_MESSAGE));
	}
	
	
	public String getTextMessageNumber(Integer length, String domain){
		if (!domain.startsWith("@")){
			domain = "@"+domain;
		}
		return getIntString(length)+domain;
	}

	public Long getLong(Integer length) {
		assert (length < Integer.MAX_VALUE);
		String randomInt = getIntString(length);
		return Long.parseLong(randomInt);
	}

	public String getValue(Values type) {
		String value = "";
		switch (type) {
		case YEAR:
			value = getYear();
			break;
		case STATES:
			value = getRandomState();
			break;
		default:
			value = nextString(type);
			break;
		}
		return value;
	}

	public Integer getStateByName(String name, ProductType productType) {
		if (states.isEmpty()) {
			getStates();
		}

		return states.get(name).get(productType);
	}

	public Integer getStateByName(String name) {
		return getStateByName(name, ProductType.TIWIPRO_R74);
	}

	private String getYear() {
		Calendar calendar = Calendar.getInstance();
		Integer max = calendar.get(Calendar.YEAR) - 1969;
		String randomYear = "" + (random.nextInt(max) + 1970);
		return randomYear;
	}

	public String getStateByID(Integer id, ProductType productType) {
		if (states.isEmpty()) {
			getStates();
		}
		Iterator<String> itr = states.keySet().iterator();

		while (itr.hasNext()) {
			String state = itr.next();
			if (states.get(state).get(productType).equals(id))
				return state;
		}
		return "Invalid ID";
	}

	public String getStateByID(Integer id) {
		return getStateByID(id, ProductType.TIWIPRO_R74);
	}

	public Integer getRandomStateID(ProductType type) {
		Integer state;
		if (states.isEmpty()) {
			getStates();
		}
		Integer x = 0;
		Integer y = random.nextInt(states.size());
		Iterator<String> itr = states.keySet().iterator();
		while (itr.hasNext()) {
			String next = itr.next();
			if (x.equals(y)) {
				state = states.get(next).get(type);
				return state;
			}
			x++;
		}
		return 40;
	}

	private void getStates() {
		states = new HashMap<String, HashMap<ProductType, Integer>>();
		ArrayList<String> rawStates;
		rawStates = Values.STATES.getList();
		Iterator<String> itr = rawStates.iterator();
		String[] line = new String[3];
		while (itr.hasNext()) {
			line = itr.next().split("=");
			HashMap<ProductType, Integer> state = new HashMap<ProductType, Integer>();
			state.put(ProductType.TIWIPRO_R74, Integer.parseInt(line[0]));
			state.put(ProductType.WAYSMART, Integer.parseInt(line[2]));
			states.put(line[1], state);
		}
	}

	public String getRandomState() {
		String state;
		if (states.isEmpty()) {
			getStates();
		}
		String[] list = states.keySet().toArray(new String[]{});
		state = list[random.nextInt(list.length)];
		return state;
	}

	public HashMap<String, String> getRandomRFID() {
		String barcode;
		barcode = String.format("%07d", random.nextInt(0004250 + 1));
		return getRFID(barcode);
	}

	public HashMap<String, String> getRFID(String barcode) {
		Iterator<String> itr;
		HashMap<String, String> card;
		ArrayList<String> cards = new ArrayList<String>();
		String[] line = new String[3];
		if (rfid.isEmpty()) {
			cards = Values.RFID.getList();
			itr = cards.iterator();
			while (itr.hasNext()) {
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

	private String nextString(ListEnum enumerated){
		ArrayList<String> list = enumerated.getList();
		return list.get(random.nextInt(list.size()));
	}

	public String getEmail() {
		return getEmail(15, nextString(Values.EMAIL_DOMAINS));
	}

	public String getEmail(Integer length, String domain) {
		String address = getCharString(length) + "@" + domain;
		Log.debug(address);
		return address;
	}

	public String getSpecialString(int length) {
		StringWriter aStringAString = new StringWriter();
		Integer value = random.nextInt(special.length);
		for (int i = 0; i < length; i++) {
			aStringAString.append(special[value]);
		}
		return aStringAString.toString();
	}

}
