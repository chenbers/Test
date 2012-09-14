package com.inthinc.device.emulation.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.inthinc.device.emulation.interfaces.SbsHessianInterface;
import com.inthinc.pro.automation.logging.Log;
import com.inthinc.sbs.SbsUpdater;
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
    public void addMap(int arg0, int arg1) {
        // TODO Auto-generated method stub
        
    }


    @Override
    public int getEntryCount(int arg0) {
        // TODO Auto-generated method stub
        return 0;
    }


    @Override
    public void incVisitedMapEntryCount(int arg0) {
        // TODO Auto-generated method stub
        
    }


    @Override
    public boolean mapHasBeenVisited(int arg0) {
        // TODO Auto-generated method stub
        return false;
    }


    @Override
    public void removeMap(int arg0) {
        // TODO Auto-generated method stub
        
    }


    @Override
    public void setSbsUpdater(SbsUpdater arg0) {
        // TODO Auto-generated method stub
        
    }

	@Override
	public boolean queueAction(SbsDownloadAction action) {
		Method[] methods = proxy.getClass().getMethods();
		for (Method method : methods) {
			if (method.getName().equals(action.name.toString())){
				try {
					method.invoke(proxy, getArgs(action));
					return true;
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		}
		return false;
	}
	
	private Object[] getArgs(SbsDownloadAction action) {
		
		return new Object[]{};
	}


}
