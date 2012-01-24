package it.util;

import it.com.inthinc.pro.dao.model.BaseITData;
import it.com.inthinc.pro.dao.model.GroupData;

import java.beans.XMLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.inthinc.pro.dao.hessian.exceptions.ProxyException;
import com.inthinc.pro.dao.hessian.exceptions.RemoteServerException;
import com.inthinc.pro.dao.hessian.proserver.SiloService;
import com.inthinc.pro.model.event.Event;
import com.inthinc.pro.model.event.NoteType;
import com.inthinc.pro.notegen.MCMSimulator;

public abstract class DataGenForTesting  {

	public static XMLEncoder xml;
    public static SiloService siloService;
    
    public BaseITData itData;

    protected abstract boolean parseTestData();
    protected abstract void createTestData();


	protected void generateDayData(MCMSimulator mcmSim, Date date, Integer driverType, List<GroupData> teamGroupData) throws Exception 
	{
		for (GroupData groupData : teamGroupData)
		{
			if (groupData.driverType.equals(driverType))
			{
				
				EventGenerator eventGenerator = new EventGenerator();
				switch (driverType.intValue()) {
				case 0:			// good
					eventGenerator.generateTrip(groupData.device.getImei(), mcmSim, date, new EventGeneratorData(0,0,0,0,false,30,0));
					break;
				case 1:			// intermediate
					eventGenerator.generateTrip(groupData.device.getImei(), mcmSim, date, new EventGeneratorData(1,1,1,1,false,25,50));
				break;
				case 2:			// bad
					eventGenerator.generateTrip(groupData.device.getImei(), mcmSim, date, new EventGeneratorData(5,5,5,5,true,20,100));
				break;
				
				}
			}		
		}
			
		
	}
	
    protected boolean genTestEvent(MCMSimulator mcmSim, Event event, String imei) {
        List<byte[]> noteList = new ArrayList<byte[]>();

        byte[] eventBytes = EventGenerator.createDataBytesFromEvent(event);
        noteList.add(eventBytes);
        boolean errorFound = false;
        int retryCnt = 0;
        while (!errorFound) {
            try {
                mcmSim.note(imei, noteList);
                break;
            }
            catch (ProxyException ex) {
                if (ex.getErrorCode() != 414 && ex.getErrorCode() != 302 ) {
                    errorFound = true;
                }
                else {
                    if (retryCnt == 300) {
                        System.out.println("Retries failed after 5 min.");
                        errorFound = true;
                    }
                    else {
                        try {
                            Thread.sleep(1000l);
                            retryCnt++;
                        }
                        catch (InterruptedException e) {
                            errorFound = true;
                            e.printStackTrace();
                        }
                        System.out.print(".");
                        if (retryCnt % 25 == 0)
                            System.out.println();
                    }
                }
            }
            catch (RemoteServerException re) {
                if (re.getErrorCode() != 302 ) {
                    errorFound = true;
                }
            	
            }
            catch (Exception e) {
                e.printStackTrace();
                errorFound = true;
            }
        }
        return !errorFound;
    }

	protected void waitForIMEIs(MCMSimulator mcmSim, int eventDateSec, List<GroupData> teamGroupData) {
		
		for (GroupData data : teamGroupData)
		{

			Event testEvent = new Event(0l, 0, NoteType.STRIPPED_ACKNOWLEDGE_ID_WITH_DATA,
                    new Date(eventDateSec * 1000l), 60, 0,  33.0089, -117.1100);
			if (!genTestEvent(mcmSim, testEvent, data.device.getImei()))
			{
				System.out.println("Error: imei has not moved to central server");
				System.exit(1);
			}
		}
	}

}
