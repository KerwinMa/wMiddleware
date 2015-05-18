package com.witbooking.middleware.integration.witbooking;

import com.witbooking.middleware.beans.IntegrationBeanLocal;
import com.witbooking.middleware.beans.IntegrationExecuteBeanLocal;
import com.witbooking.middleware.exceptions.AuthenticationException;
import com.witbooking.middleware.exceptions.MiddlewareException;
import com.witbooking.middleware.resources.MiddlewareProperties;
import com.witbooking.middleware.utils.HttpConnectionUtils;
import com.witbooking.middleware.utils.JsonUtils;
import org.apache.log4j.Logger;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;

/**
 * TripAdvisorExecutorResource.java
 * User: jose
 * Date: 11/14/13
 * Time: 12:43 PM
 */
@Path("/Integration")
@RequestScoped
public class IntegrationResource {

    private static final Logger logger = Logger.getLogger(IntegrationResource.class);

    @EJB
    private IntegrationExecuteBeanLocal integrationExecuteBeanLocal;
    @EJB
    private IntegrationBeanLocal integrationBeanLocal;

    @POST
    @Path("/Queue/executePending")
    public String executePending(@Context HttpHeaders headers) {
        try {
            validPermissionsByHeader(headers);
            integrationExecuteBeanLocal.executePending();
            return "{\"Success\"}";
        } catch (Exception ex) {
            logger.error(ex);
            throw HttpConnectionUtils.generateJaxException(ex, "application/json;charset=utf-8", JsonUtils.exceptionToJson(ex) + "");
        }
    }

    @GET
    @Path("/ReportReservation")
    public String reportReservationByGet(@Context HttpHeaders headers,
                                         @QueryParam("reservationId") String reservationId,
                                         @QueryParam("hotelTicker") String hotelTicker,
                                         @QueryParam("type") String type) {
        return reportReservation(headers, reservationId, hotelTicker, type);
    }


    @POST
    @Path("/ReportReservation")
    public String reportReservation(@Context HttpHeaders headers,
                                    @FormParam("reservationId") String reservationId,
                                    @FormParam("hotelTicker") String hotelTicker,
                                    @FormParam("type") String type) {
        try {
            validPermissionsByHeader(headers);
            integrationBeanLocal.enqueueReservationPush(reservationId, hotelTicker, type);
            return "{\"Success\"}";
        } catch (Exception ex) {
            logger.error(ex);
            throw HttpConnectionUtils.generateJaxException(ex, "application/json;charset=utf-8", JsonUtils.exceptionToJson(ex) + "");
        }
    }


    /**
     * Validating permission that the user in the HttpHeaders have for the Hotel requested. If the user have write
     * permissions return 1, if the user just have read permissions return 0. If the user don't have permissions or
     * the is a problem with the credentials, this method throws an AuthenticationException.
     *
     * @param headers
     * @return
     * @throws MiddlewareException
     */
    private int validPermissionsByHeader(HttpHeaders headers) throws AuthenticationException {
        if (headers.getRequestHeader("username") == null || headers.getRequestHeader("password") == null) {
            throw new AuthenticationException("'username' and 'password' values are mandatory.");
        }
        String username = headers.getRequestHeader("username").get(0);
        String password = headers.getRequestHeader("password").get(0);
        logger.debug("user: '" + username + "' is try this service.");
        //if the user is WitSysAdmin, validate all hotels, and don't make the PHP connection to validate
        if (MiddlewareProperties.HTTP_AUTH_USER.equals(username) && MiddlewareProperties.HTTP_AUTH_PASS.equals(password)) {
            return 1;
        } else {
            //This case means there was an error in the authentication.
            throw new AuthenticationException("Invalid username and password.");
        }
    }

}