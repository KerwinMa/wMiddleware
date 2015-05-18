package com.witbooking.middleware.integration.mandrill;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.witbooking.middleware.db.DBConnection;
import com.witbooking.middleware.db.handlers.ReservationDBHandler;
import com.witbooking.middleware.exceptions.db.DBAccessException;
import com.witbooking.middleware.integration.mandrill.model.Event;
import com.witbooking.middleware.utils.JsonUtils;
import org.apache.log4j.Logger;

import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mongoose on 1/20/15.
 */
@Stateless
public class MandrillEventHandler implements MandrillEventHandlerLocal {

    private static final Logger logger = Logger.getLogger(MandrillEventHandler.class);

    public MandrillEventHandler() {

    }

    @Override
    public String handleOpenEvent(String mandrillData) {
        Gson gson = JsonUtils.gsonInstance();
        List<Event> mandrillEvents=gson.fromJson(mandrillData, new TypeToken<ArrayList<Event>>() {}.getType());
        /* Loop through mandrill events and mark all mails that have been opened */
        DBConnection dBConnection = null;
/*
        try {
            */
/*TODO: RECUPERAR HOTEL TICKER DE MSJ MANDRILL *//*

            dBConnection = new DBConnection(getDBCredentialsByBookingID(bookingHotelTicker));

        } catch (DBAccessException ex) {
            logger.error(ex);
           throw new BookingException(ex);
        }
*/

        ReservationDBHandler reservationDBHandler = new ReservationDBHandler(dBConnection);

        for(Event mandrillEvent: mandrillEvents){
//            ReservationDBHandler
        }
        return null;
    }

}
