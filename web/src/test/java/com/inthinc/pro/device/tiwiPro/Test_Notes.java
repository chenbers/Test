package com.inthinc.pro.device.tiwiPro;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.inthinc.pro.dao.hessian.extension.HessianTCPProxyFactory;
import com.inthinc.pro.device.MCMProxy;

public class Test_Notes {
	
	private static MCMProxy mcmProxy;
	private static Package_Note packageNote;
	
	
	public static void setMcmProxy(MCMProxy mcmProxy) {
		Test_Notes.mcmProxy = mcmProxy;
	}

	public static MCMProxy getMcmProxy() {
		return mcmProxy;
	}
	

	public static void setPackageNote(Package_Note packageNote) {
		Test_Notes.packageNote = packageNote;
	}

	public static Package_Note getPackageNote() {
		return packageNote;
	}
	
	public static void main(String args[]){

        HessianTCPProxyFactory factory = new HessianTCPProxyFactory();
        
        setPackageNote(new Package_Note());
        try {
			setMcmProxy((MCMProxy)factory.create( MCMProxy.class, "qa.tiwipro.com", 8190));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
	    }
		List<byte[]> note = new ArrayList<byte[]>();
		System.out.println(getPackageNote().noteToString());
		
		note.add(getPackageNote().Package());
		
		List<Map> reply = getMcmProxy().note("999456789012345", note);
		System.out.println(reply);
	}



}
