/**
 * 
 */
package com.inthinc.pro.service.phonecontrol.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.inthinc.pro.dao.PhoneControlDAO;
import com.inthinc.pro.model.Cellblock;
import com.inthinc.pro.model.phone.CellProviderType;
import com.inthinc.pro.service.phonecontrol.MovementEventHandler;
import com.inthinc.pro.service.phonecontrol.PhoneControlAdapter;
import com.inthinc.pro.service.phonecontrol.PhoneControlAdapterFactory;
import com.inthinc.pro.service.phonecontrol.PhoneStatusController;

/**
 * {@link MovementEventHandler} which requests phone control service provider to disable/enable driver's cell phone once it starts/stops driving.
 */
@Component
public class PhoneControlMovementEventHandler implements MovementEventHandler {

    private static final Logger logger = Logger.getLogger(PhoneControlMovementEventHandler.class);

//    private DriverDAO driverDao;
    private PhoneControlDAO phoneControlDAO;
    private PhoneControlAdapterFactory serviceFactory;
    private PhoneStatusController phoneStatusController;

    private Map<CellProviderType, UpdateStrategy> statusUpdateStrategyMap = new HashMap<CellProviderType, UpdateStrategy>();

    /**
     * Creates an instance of {@link PhoneControlMovementEventHandler}.
     * 
     * @param phoneControlDAO
     *            The {@link PhoneControlDAO} instance to use to obtain information about the driver.
     * @param serviceFactory
     *            An instance of the {@link PhoneControlAdapterFactory} to be used to create {@link PhoneControlAdapter} client endpoints.
     * @param phoneDao
     *            TODO
     */
    @Autowired
    public PhoneControlMovementEventHandler(PhoneControlDAO phoneControlDAO, PhoneControlAdapterFactory serviceFactory, PhoneStatusController phoneStatusController) {
        this.serviceFactory = serviceFactory;
        this.phoneStatusController = phoneStatusController;
        this.phoneControlDAO = phoneControlDAO;
    }

    /**
     * Handles the 'driver started moving' event and signals the phone control service provider to disable the driver's cell phone, if applicable (i.e. driver has the service
     * enabled).
     * 
     * @see com.inthinc.pro.service.phonecontrol.MovementEventHandler#handleDriverStartedMoving(java.lang.Integer)
     */
    @Override
    public void handleDriverStartedMoving(Integer driverId) {
        Cellblock cellblock = getDriverInfo(driverId);

        if (cellblock != null) {
            disableDriverPhone(cellblock);
        }
    }

    /**
     * @see com.inthinc.pro.service.phonecontrol.MovementEventHandler#handleDriverStoppedMoving(java.lang.Integer)
     */
    @Override
    public void handleDriverStoppedMoving(Integer driverId) {
        Cellblock cellblock = getDriverInfo(driverId);

        if (cellblock != null) {
            enableDriverPhone(cellblock);
        }
    }

    private Cellblock getDriverInfo(Integer driverId) {

            Cellblock info = phoneControlDAO.findByID(driverId);

            if (info == null) {
                info = new Cellblock();
            }

            logger.debug("Obtained driver info from the back end. DID-" + driverId + ", PH#-" + info.getCellPhone() + ", provider: "
                    + info.getProvider());
        return info;
    }

    private void disableDriverPhone(Cellblock cellblock) {
        if (cellblock != null && cellblock.getProvider() != null) {

            if (cellblock.getCellPhone() != null) {
                if (cellblock.getProviderUser() != null) {
                    logger.debug("Creating " + cellblock.getProvider() + " client endpoint proxy...");
                    PhoneControlAdapter phoneControlAdapter = serviceFactory.createAdapter(cellblock.getProvider(), cellblock.getProviderUser(), cellblock.getProviderPassword());
                    logger.debug("Sending request to " + cellblock.getProvider() + " client endpoint proxy to disable PH#-" + cellblock.getCellPhone());
                    phoneControlAdapter.disablePhone(cellblock.getCellPhone());

//                    if (statusUpdateStrategyMap.get(cellblock.getProvider()) == UpdateStrategy.SYNCHRONOUS) {
                        phoneStatusController.setPhoneStatusDisabled(cellblock);
//                    }
                } else {
                    logger.warn("Driver DID-" + cellblock.getDriverID() + " is missing the credentials for the remote phone control service endpoint. No updates have been performed.");
                }
            } else {
                logger.warn("Driver DID-" + cellblock.getDriverID() + " has no associated cell phone number.");
            }

        } else {
            logger.warn("No provider information is available for the driver DID-" + cellblock.getDriverID() + ".");
        }
    }

    private void enableDriverPhone(Cellblock cellblock) {
        if (cellblock != null && cellblock.getProvider() != null) {

            if (cellblock.getCellPhone() != null) {
                if (cellblock.getProviderUser() != null) {
                    logger.debug("Creating " + cellblock.getProvider() + " client endpoint proxy...");
                    PhoneControlAdapter phoneControlAdapter = serviceFactory.createAdapter(cellblock.getProvider(), cellblock.getProviderUser(),cellblock.getProviderPassword());
                    logger.debug("Requesting " + cellblock.getProvider() + " client endpoint proxy to enable PH#-" + cellblock.getCellPhone());

                    phoneControlAdapter.enablePhone(cellblock.getCellPhone());

//                    if (statusUpdateStrategyMap.get(cellblock.getProvider()) == UpdateStrategy.SYNCHRONOUS) {
                        phoneStatusController.setPhoneStatusEnabled(cellblock);
//                    }
                } else {
                    logger.warn("Driver DID-" + cellblock.getDriverID() + " is missing the credentials for the remote phone control service endpoint. No updates have been performed.");
                }
            } else {
                logger.warn("Driver DID-" + cellblock.getDriverID() + " has no associated cell phone number.");
            }

        } else {
            logger.warn("No provider information is available for the driver DID-" + cellblock.getDriverID() + ".");
        }
    }

    public Map<CellProviderType, UpdateStrategy> getStatusUpdateStrategyMap() {
        return statusUpdateStrategyMap;
    }

    @Resource(name = "providerStatusUpdateStrategyMap")
    public void setStatusUpdateStrategyMap(Map<CellProviderType, UpdateStrategy> statusUpdateStrategyMap) {
        this.statusUpdateStrategyMap = statusUpdateStrategyMap;
    }
}
