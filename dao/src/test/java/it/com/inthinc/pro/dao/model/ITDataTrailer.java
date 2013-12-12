package it.com.inthinc.pro.dao.model;

import it.config.IntegrationConfig;

import java.beans.XMLEncoder;
import java.util.Date;

import com.inthinc.pro.dao.hessian.proserver.SiloService;
import com.inthinc.pro.dao.service.TrailerServiceDAO;
import com.inthinc.pro.model.Status;
import com.inthinc.pro.model.Trailer;

public class ITDataTrailer extends ITData {
    public void createTestDataForTrailer(SiloService siloService, XMLEncoder xml, Date assignmentDate, String hardCodedAccountName) {

        removeAccount(siloService, hardCodedAccountName);
        this.hardCodedAccountName = hardCodedAccountName;

        this.siloService = siloService;
        this.xml = xml;
        this.assignmentDate = assignmentDate;
        
        init();

        // Account
        createAccount(true, hardCodedAccountName);
        writeObject(account);

        // Address
        createAddress(account.getAccountID());
        writeObject(address);
        
        // Group Hierarchy
        createGroupHierarchy(account.getAccountID(), true);
        writeObject(fleetGroup);
        writeObject(districtGroup);
        for (GroupData team : teamGroupData)
            writeObject(team.group);

        // User at fleet level
        System.out.println("Fleet Level");
        fleetUser = createUser(account.getAccountID(), fleetGroup);
        writeObject(fleetUser);

        districtUser = createUser(account.getAccountID(), districtGroup);
        writeObject(districtUser);

        // User at team level
        System.out.println("Team Level");
        for (GroupData team : teamGroupData)
        {
            team.user = createUser(account.getAccountID(), team.group);
            team.device = createDevice(team.group, assignmentDate);
            team.driver = createDriver(team.group);
            team.vehicle = createVehicle(team.group, team.device.getDeviceID(), team.driver.getDriverID());
            team.trailer = createTrailer(team);
            writeObject(team.user);
            writeObject(team.device);
            writeObject(team.driver);
            writeObject(team.driver.getPerson());
            writeObject(team.vehicle);
            writeObject(team.trailer);
        }
        
    }

    private Trailer createTrailer(GroupData team) {
        
        Trailer trailer = new Trailer();
        trailer.setAbsOdometer(0);
        trailer.setAccount(account);
        trailer.setColor("TrailerColor");
        trailer.setDevice(team.device);
        trailer.setMake("TrailerMake");
        trailer.setModel("TrailerModel");
        trailer.setName("TRAILER"+ team.group.getGroupID());
        trailer.setOdometer(0);
        trailer.setPairingDate(assignmentDate);
        trailer.setStateID(10);
        trailer.setStatus(Status.ACTIVE);
        trailer.setVin(team.vehicle.getVIN());
        trailer.setWeight(2000);
        trailer.setYear(2013);
        
        
        TrailerServiceDAO trailerServiceDAO = new TrailerServiceDAO(new IntegrationConfig().getProperty(IntegrationConfig.ASSET_API_URL), fleetUser.getUsername(), "password");
        Integer trailerID = trailerServiceDAO.create(team.group.getGroupID(), trailer);
        
        return trailerServiceDAO.findByID(trailerID);
    }

}
