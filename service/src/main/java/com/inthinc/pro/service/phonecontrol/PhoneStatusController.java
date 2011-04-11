package com.inthinc.pro.service.phonecontrol;

import com.inthinc.pro.model.Cellblock;

/**
 * Controller for cell phone status. It will manage and update the list of disabled phones.
 */
public interface PhoneStatusController {

    /**
     * Change the phone status to enabled.
     */
    void setPhoneStatusEnabled(Cellblock cellblock);

    /**
     * Change the phone status to disabled.
     */
    void setPhoneStatusDisabled(Cellblock cellblock);

}
