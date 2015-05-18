/*
 *  BookingResource.java
 * 
 * Copyright(c) 2013 Witbooking.com. All Rights Reserved.
 * This software is the proprietary information of Witbooking.com
 * 
 */
package com.witbooking.middleware.integration.booking;

import com.witbooking.middleware.exceptions.InvalidEntryException;
import com.witbooking.middleware.utils.DateUtil;
import com.witbooking.middleware.utils.HttpConnectionUtils;
import com.witbooking.middleware.utils.XMLUtils;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.*;
import java.util.Date;
import java.util.List;

/**
 * Insert description here
 *
 * @author Jose Francisco Fiorillo < jffiorillo@gmail.com >
 * @since 1.0 , Aug 21, 2013
 */
@Path("Booking")
@RequestScoped
public class BookingEnqueuerResource {

    @EJB
    private BookingEnqueuerLocal bookingEnqueuerLocal;

    /**
     * Receives push notifications sent by Booking.
     *
     * @param hotelId      The Booking hotel ticker.
     * @param reservationId    The Booking id ReservationRS ticker.
     * @param notificationType The notification type.
     */
    @GET
    @Path("/Notification")
    public String getNotification(@QueryParam("hotelId") String hotelId,
                                  @QueryParam("hotelTicker") String hotelTicker,
                                  @QueryParam("reservationId") String reservationId,
                                  @QueryParam("lastChange") String lastChange,
                                  @QueryParam("type") String notificationType) {
        //this param 'hotelTicker' is for compatibility for previous versions
        if (hotelId == null || hotelId.isEmpty())
            hotelId = hotelTicker;
        Date lastChangeDate = null;
        if (lastChange != null) {
            try {
                if (lastChange.trim().length() == 10)
                    lastChange = lastChange + " 00:00:00";
                lastChangeDate = DateUtil.stringToTimestamp(lastChange);
            } catch (Exception e) {
                throw HttpConnectionUtils.generateJaxException(new InvalidEntryException(e), "application/xml",
                        XMLUtils.errorOTAString("Invalid date value in the 'lastChange' parameter"));
            }
        }
        try {
            return bookingEnqueuerLocal.getNotification(hotelId, reservationId, lastChangeDate, notificationType);
        } catch (Exception e) {
            throw HttpConnectionUtils.generateJaxException(e, "application/xml", XMLUtils.errorOTAString(e.getMessage() + ""));
        }
    }

    @POST
    @Path("/updateARI")
    public String updateARI(@FormParam("hotelTicker") String hotelTicker,
                            @FormParam("invTicker") List<String> invTickers,
                            @FormParam("start") String startString,
                            @FormParam("end") String endString) {
        return bookingEnqueuerLocal.updateARI(hotelTicker, invTickers, startString, endString);
    }

    @POST
    @Path("/updateAmount")
    public String updateAmount(@FormParam("hotelTicker") String hotelTicker,
                               @FormParam("invTicker") List<String> invTickers,
                               @FormParam("start") String startString,
                               @FormParam("end") String endString) {
        return bookingEnqueuerLocal.updateAmount(hotelTicker, invTickers, startString, endString);
    }

}
