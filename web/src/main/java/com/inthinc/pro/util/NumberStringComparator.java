package com.inthinc.pro.util;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class NumberStringComparator implements Comparator<String> {

	Locale locale;
	public NumberStringComparator(Locale locale) {
		super();
		this.locale = locale;
	}
	@Override
	public int compare(String thisString, String thatString) {
		
		String [] thisTokens = thisString.split(" ");
		String [] thatTokens = thatString.split(" ");
		int i;
		NumberFormat nf = NumberFormat.getNumberInstance(locale);
		for (i = 0; i< Math.min(thisTokens.length, thatTokens.length);i++){
			Double thisDouble = null;
			Double thatDouble = null;
			try {
				thisDouble = (Double)(nf.parse(thisTokens[i])).doubleValue();
				thatDouble = (Double)(nf.parse(thatTokens[i])).doubleValue();
				
				int k = thisDouble.compareTo(thatDouble);
				if (k!=0) return k;
				
			}
			catch (ParseException nfe){
				
					int j = thisTokens[i].compareToIgnoreCase(thatTokens[i]);
					if ( j!= 0) return j;
			}
		}
		if (i < thisTokens.length) return 1;
		if (i < thatTokens.length) return -1;
		
		return 0;
	}
	public static void main(String[] args){
//		NumberString test11= new NumberString("1 abc");
//		NumberString test12=  new NumberString("12 abc");
//		System.out.println(test11+", "+test12+ " "+test11.compareTo(test12));
//		NumberString test21= new NumberString("abc");
//		NumberString test22=  new NumberString("12 abc");
//		System.out.println(test21+", "+test22+ " "+test21.compareTo(test22));
//		NumberString test31= new NumberString("111 abc");
//		NumberString test32=  new NumberString("12 abc");
//		System.out.println(test31+", "+test32+ " "+test31.compareTo(test32));
		
		List<String> list = new ArrayList<String>();
		
		list.add("1 abc 111");
		list.add("1 abc");
		list.add("11,1 abc");
		list.add("12 abc");
		list.add("abc");
		list.add("1abc");
		list.add("abc 11");
		list.add("abc 2");
		list.add("1 abc def");
		list.add("1 abcd ef hij");
		list.add("1 abc 12");
		list.add("1 abc 2");
		list.add("1 abc 25");
		list.add("abc 7");
		list.add("abc 72");
		list.add("abc 2,1");
		list.add("abc -72,5");
		list.add("-12 abc");
		list.add("7,2 abc");
		list.add("1 abcd ef 1 hij");
		list.add("1 abcd ef 1,2 hij");
		list.add("1 abcd ef 12 hij");
		list.add("abcd ef hij 1.120,4");
		list.add("abcd ef hij 112");
		list.add("abcd ef hij -112");
		list.add("0 abcd ef 10 hij");
		list.add("0,0 abcd ef 1 hij");
		
		Collections.sort(list, new NumberStringComparator(Locale.GERMAN));
		System.out.println("German\n");
		for(String ns:list){
			System.out.println(ns);
		}
		System.out.println("\n\n");
		
		list = new ArrayList<String>();
		list.add("1 abc 111");
		list.add("1 abc");
		list.add("11.1 abc");
		list.add("12 abc");
		list.add("abc");
		list.add("1abc");
		list.add("abc 11");
		list.add("abc 2");
		list.add("1 abc def");
		list.add("1 abcd ef hij");
		list.add("1 abc 12");
		list.add("1 abc 2");
		list.add("1 abc 25");
		list.add("abc 7");
		list.add("abc 72");
		list.add("abc 2.1");
		list.add("abc -72.5");
		list.add("-12 abc");
		list.add("7.2 abc");
		list.add("abcd ef hij 1,120.4");
		list.add("1 abcd ef 1 hij");
		list.add("1.0 abcd ef 1.2 hij");
		list.add("1 abcd ef 12 hij");
		list.add("abcd ef hij 112");
		list.add("abcd ef hij -112");
		list.add("0 abcd ef 10 hij");
		list.add("0.0 abcd ef 1 hij");
		
		Collections.sort(list, new NumberStringComparator(Locale.US));

		System.out.println("American\n");
		
		for(String ns:list){
			System.out.println(ns);
		}
	}
}
