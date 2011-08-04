package com.inthinc.pro.util;


import java.io.*;
import java.net.*;
import java.util.*;

public class Encrypt
{
	/* **************************************************************************
	   Constructors
	****************************************************************************/

	public static String encrypt(String value)
	{
		String key = new String("BoogerBoy");
		String encryptedValue = new String("");
		
		// cheesy encryption - just xor each character with the key
		int length = value.length();
		for (int i = 0; i < length; i++) {
			int index = i % key.length();
	
			char result = value.charAt(i);
			result ^= key.charAt(index);
	
			// we don't want any special characters in the encrypted value
			if (result < 32) {
				// switch these back
				result ^= key.charAt(index);
			}
	
			encryptedValue = encryptedValue + result;
		}
	
		return encryptedValue;
	}

	public static String decrypt(String key)
	{
		// xor encryption, just call encrypt again to get back to the original value
		return encrypt(key);
	}
} 




