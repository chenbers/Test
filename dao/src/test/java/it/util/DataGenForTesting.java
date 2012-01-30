package it.util;

import it.com.inthinc.pro.dao.model.BaseITData;
import it.com.inthinc.pro.dao.model.GroupData;
import it.config.IntegrationConfig;

import java.beans.XMLEncoder;
import java.net.MalformedURLException;
import java.util.Date;
import java.util.List;

import com.inthinc.pro.dao.hessian.exceptions.ProxyException;
import com.inthinc.pro.dao.hessian.exceptions.RemoteServerException;
import com.inthinc.pro.dao.hessian.extension.HessianTCPProxyFactory;
import com.inthinc.pro.dao.hessian.proserver.SiloService;
import com.inthinc.pro.dao.hessian.proserver.SiloServiceCreator;
import com.inthinc.pro.model.Device;
import com.inthinc.pro.model.configurator.ProductType;
import com.inthinc.pro.model.event.Event;
import com.inthinc.pro.model.event.NoteType;
import com.inthinc.pro.notegen.MCMSimulator;
import com.inthinc.pro.notegen.NoteGenerator;
import com.inthinc.pro.notegen.TiwiProNoteSender;
import com.inthinc.pro.notegen.WSNoteSender;

public abstract class DataGenForTesting  {

	public static XMLEncoder xml;
    public static SiloService siloService;
    public static MCMSimulator mcmSim;
    public static NoteGenerator noteGenerator;
    EventGenerator eventGenerator = new EventGenerator();

    
    public BaseITData itData;

    protected abstract boolean parseTestData();
    protected abstract void createTestData();
    
    protected static void initServices(String configFile) throws MalformedURLException {
        IntegrationConfig config = new IntegrationConfig(configFile);
        initServices(config);
    }
    protected static void initServices() throws MalformedURLException {
        IntegrationConfig config = new IntegrationConfig();
        initServices(config);
    }
    protected static void initServices(IntegrationConfig config) throws MalformedURLException {
        String host = config.get(IntegrationConfig.SILO_HOST).toString();
        Integer port = Integer.valueOf(config.get(IntegrationConfig.SILO_PORT).toString());
        siloService = new SiloServiceCreator(host, port).getService();

        HessianTCPProxyFactory factory = new HessianTCPProxyFactory();
        mcmSim = (MCMSimulator) factory.create(MCMSimulator.class, config.getProperty(IntegrationConfig.MCM_HOST), config.getIntegerProp(IntegrationConfig.MCM_PORT));

        noteGenerator = new NoteGenerator();
        WSNoteSender wsNoteSender = new WSNoteSender();
        wsNoteSender.setUrl(config.get(IntegrationConfig.MINA_HOST).toString());
        wsNoteSender.setPort(Integer.valueOf(config.get(IntegrationConfig.MINA_PORT).toString()));
        noteGenerator.setWsNoteSender(wsNoteSender);
        
        TiwiProNoteSender tiwiProNoteSender = new TiwiProNoteSender();
        tiwiProNoteSender.setMcmSimulator(mcmSim);
        noteGenerator.setTiwiProNoteSender(tiwiProNoteSender);

    }

    EventGeneratorData eventGeneratorDataList[] = {
            new EventGeneratorData(0,0,0,0,false,30,0, false, false, true),
            new EventGeneratorData(1,1,1,1,false,25,50, false, false, true),
            new EventGeneratorData(5,5,5,5,true,20,100, false, false, true)
    };

	protected void generateDayData(Date date, Integer driverType, List<GroupData> teamGroupData) throws Exception 
	{
		for (GroupData groupData : teamGroupData)
		{
			if (groupData.driverType.equals(driverType))
			{
                List<Event> eventList = eventGenerator.generateTripEvents(date, eventGeneratorDataList[driverType.intValue()]);
                noteGenerator.genTrip(eventList, groupData.device);
			}		
		}
			
		
	}
	
    protected boolean genTestEvent(Event event, Device device) {
        boolean errorFound = false;
        int retryCnt = 0;
        while (!errorFound) {
            try {
                noteGenerator.genEvent(event, device);
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

	protected void waitForIMEIs(int eventDateSec, List<GroupData> teamGroupData) {
		
		for (GroupData data : teamGroupData)
		{
		    if (data.device.getProductVersion() == ProductType.WAYSMART)
		        continue;
			Event testEvent = new Event(0l, 0, NoteType.STRIPPED_ACKNOWLEDGE_ID_WITH_DATA,
                    new Date(eventDateSec * 1000l), 60, 0,  33.0089, -117.1100);
			if (!genTestEvent(testEvent, data.device))
			{
				System.out.println("Error: imei has not moved to central server");
				System.exit(1);
			}
		}
	}

}
