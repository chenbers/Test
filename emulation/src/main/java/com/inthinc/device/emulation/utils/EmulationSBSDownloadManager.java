package com.inthinc.device.emulation.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.inthinc.device.emulation.interfaces.SbsHessianInterface;
import com.inthinc.pro.automation.logging.Log;
import com.inthinc.sbs.downloadmanager.SbsDownloadAction;
import com.inthinc.sbs.downloadmanager.SbsDownloadManager;

public class EmulationSBSDownloadManager implements SbsDownloadManager {
	
	private SbsHessianInterface proxy;
	private String mcmid;
	
	
	public EmulationSBSDownloadManager(SbsHessianInterface proxy, String mcmid) {
		this.proxy = proxy;
		this.mcmid = mcmid;
	}
	

	@Override
	public boolean queueAction(SbsDownloadAction action) {
		Log.info(action.name);
		return false;
	}

//	@Override
//	public boolean queueAction(SbsDownloadAction action) {
//		Method[] methods = proxy.getClass().getMethods();
//		for (Method method : methods) {
//			if (method.getName().equals(action.name.toString())){
//				try {
//					method.invoke(proxy, getArgs(action));
//					return true;
//				} catch (IllegalArgumentException e) {
//					e.printStackTrace();
//				} catch (IllegalAccessException e) {
//					e.printStackTrace();
//				} catch (InvocationTargetException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//		return false;
//	}
//	
//	private Object[] getArgs(SbsDownloadAction action) {
//		
//		return new Object[]{};
//	}


}
