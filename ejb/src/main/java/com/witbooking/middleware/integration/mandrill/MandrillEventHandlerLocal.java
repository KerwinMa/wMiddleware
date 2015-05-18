package com.witbooking.middleware.integration.mandrill;

import javax.ejb.Local;

/**
 * Created by mongoose on 1/20/15.
 */
@Local
public interface MandrillEventHandlerLocal {

    public String handleOpenEvent(String messageData);

}
