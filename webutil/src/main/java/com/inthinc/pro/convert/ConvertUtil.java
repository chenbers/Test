package com.inthinc.pro.convert;


import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class ConvertUtil {

	private static Map <String, Convert<?,?>> converterMap;
	static {
		converterMap = new HashMap<String, Convert<?,?>>();
		
		converterMap.put(makeKey(java.util.Date.class, Long.class), new DateConvert());
		converterMap.put(makeKey(String.class, Integer.class), new IntegerConvert());
		converterMap.put(makeKey(String.class, Long.class), new LongConvert());
	}

	private static String makeKey(Class<?> from, Class<?> to) {
		return from.getSimpleName()+to.getSimpleName();
		
	}

	public static boolean converterExists(Object from, Class<?> to) 
	{
		return converterExists(from.getClass(), to);
	}
	public static boolean converterExists(Class<?> from, Class<?> to) 
	{
		Convert<?,?> converter = converterMap.get(makeKey(from, to));
		return converter != null;
	}
	
	
	public static Object convert(Object from, Class<?> to) 
	{
		Convert<?,?> converter = converterMap.get(makeKey(from.getClass(), to));
		if (converter == null) {
			return null;
		}
		
		return converter.convert(from.getClass().cast(from));
	}


	public static Object[] convertList(List<?> fromList, Class<?> from, Class<?> to) 
	{
		Convert<?,?> converter = converterMap.get(makeKey(from, to));
		if (converter == null) {
			return null;
		}
		Object[] returnList = (Object[])Array.newInstance(to, fromList.size()); //new Object[fromList.size()];
		int i = 0;
		for (Object obj : fromList) {
			returnList[i++] = converter.convert(from.cast(obj));
		}
		
		return returnList;
	}

}
