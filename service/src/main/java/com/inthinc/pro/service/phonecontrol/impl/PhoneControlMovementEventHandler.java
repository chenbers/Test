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

import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.model.Cellblock;
import com.inthinc.pro.model.Driver;
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

    private DriverDAO driverDao;
    private PhoneControlAdapterFactory serviceFactory;
    private PhoneStatusController phoneStatusController;

    private Map<CellProviderType, UpdateStrategy> statusUpdateStrategyMap = new HashMap<CellProviderType, UpdateStrategy>();

    /**
     * Creates an instance of {@link PhoneControlMovementEventHandler}.
     * 
     * @param driverDao
     *            The {@link DriverDAO} instance to use to obtain information about the driver.
     * @param serviceFactory
     *            An instance of the {@link PhoneControlAdapterFactory} to be used to create {@link PhoneControlAdapter} client endpoints.
     * @param phoneDao
     *            TODO
     */
    @Autowired
    public PhoneControlMovementEventHandler(DriverDAO driverDao, PhoneControlAdapterFactory serviceFactory, PhoneStatusController phoneStatusController) {
        this.serviceFactory = serviceFactory;
        this.phoneStatusController = phoneStatusController;
        this.driverDao = driverDao;
    }

    /**
     * Handles the 'driver started moving' event and signals the phone control service provider to disable the driver's cell phone, if applicable (i.e. driver has the service
     * enabled).
     * 
     * @see com.inthinc.pro.service.phonecontrol.MovementEventHandler#handleDriverStartedMoving(java.lang.Integer)
     */
    @Override
    public void handleDriverStartedMoving(Integer driverId) {
        Driver driver = getDriverInfo(driverId);

        if (driver != null) {
            disableDriverPhone(driver);
        }
    }

    /**
     * @see com.inthinc.pro.service.phonecontrol.MovementEventHandler#handleDriverStoppedMoving(java.lang.Integer)
     */
    @Override
    public void handleDriverStoppedMoving(Integer driverId) {
        Driver driver = getDriverInfo(driverId);

        if (driver != null) {
            enableDriverPhone(driver);
        }
    }

    private Driver getDriverInfo(Integer driverId) {
        Driver driver = null;

        logger.debug("Requesting driver information from backend...");
        driver = driverDao.findByID(driverId);

        if (driver == null) {
            logger.warn("No information is available for driver DID-" + driverId + ".");
        } else {
            Cellblock info = driver.getCellblock();

            if (info == null) {
                info = new Cellblock();
            }

            logger.debug("Obtained driver info from the back end. DID-" + driverId + ", PH#-" + info.getCellPhone() + ", provider: "
                    + info.getProvider());
        }

        return driver;
    }

    private void disableDriverPhone(Driver driver) {
        if (driver.getCellblock() != null && driver.getCellblock().getProvider() != null) {

            if (driver.getCellblock().getCellPhone() != null) {
                if (driver.getCellblock().getProviderUsername() != null) {
                    logger.debug("Creating " + driver.getCellblock().getProvider() + " client endpoint proxy...");
                    PhoneControlAdapter phoneControlAdapter = serviceFactory.createAdapter(driver.getCellblock().getProvider(), driver.getCellblock().getProviderUsername(), driver
                            .getCellblock().getProviderPassword());
                    logger.debug("Sending request to " + driver.getCellblock().getProvider() + " client endpoint proxy to disable PH#-" + driver.getCellblock().getCellPhone());
                    phoneControlAdapter.disablePhone(driver.getCellblock().getCellPhone());

                    if (statusUpdateStrategyMap.get(driver.getCellblock().getProvider()) == UpdateStrategy.SYNCHRONOUS) {
                        phoneStatusController.setPhoneStatusDisabled(driver);
                    }
                } else {
                    logger.warn("Driver DID-" + driver.getDriverID() + " is missing the credentials for the remote phone control service endpoint. No updates have been performed.");
                }
            } else {
                logger.warn("Driver DID-" + driver.getDriverID() + " has no associated cell phone number.");
            }

        } else {
            logger.warn("No provider information is available for the driver DID-" + driver.getDriverID() + ".");
        }
    }

    private void enableDriverPhone(Driver driver) {
        if (driver.getCellblock() != null && driver.getCellblock().getProvider() != null) {

            if (driver.getCellblock().getCellPhone() != null) {
                if (driver.getCellblock().getProviderUsername() != null) {
                    logger.debug("Creating " + driver.getCellblock().getProvider() + " client endpoint proxy...");
                    PhoneControlAdapter phoneControlAdapter = serviceFactory.createAdapter(driver.getCellblock().getProvider(), driver.getCellblock().getProviderUsername(), driver
                            .getCellblock().getProviderPassword());
                    logger.debug("Requesting " + driver.getCellblock().getProvider() + " client endpoint proxy to enable PH#-" + driver.getCellblock().getCellPhone());

                    phoneControlAdapter.enablePhone(driver.getCellblock().getCellPhone());

                    if (statusUpdateStrategyMap.get(driver.getCellblock().getProvider()) == UpdateStrategy.SYNCHRONOUS) {
                        phoneStatusController.setPhoneStatusEnabled(driver);
                    }
                } else {
                    logger.warn("Driver DID-" + driver.getDriverID() + " is missing the credentials for the remote phone control service endpoint. No updates have been performed.");
                }
            } else {
                logger.warn("Driver DID-" + driver.getDriverID() + " has no associated cell phone number.");
            }

        } else {
            logger.warn("No provider information is available for the driver DID-" + driver.getDriverID() + ".");
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
