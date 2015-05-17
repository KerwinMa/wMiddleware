/*
 *  DefaultResource.java
 * 
 * Copyright(c) 2013 Witbooking.com. All Rights Reserved.
 * This software is the proprietary information of Witbooking.com
 * 
 */
package com.witbooking.middleware.internal;

import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 * REST Web Service
 *
 * @author Christian Delgado
 */
@Path("/")
@Stateless
public class DefaultResource {

    /**
     * Creates a new instance of DefaultResource
     */
    public DefaultResource() {
    }

    @GET
    @Path("/")
    public String getServiceInfo() {
        return "WitBooking Services Available";
    }

}
