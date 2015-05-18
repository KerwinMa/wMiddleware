/*
 *  FrontEndResource.java
 * 
 * Copyright(c) 2013 Witbooking.com. All Rights Reserved.
 * This software is the proprietary information of Witbooking.com
 * 
 */
package com.witbooking.middleware.integration.endpoint;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.witbooking.middleware.beans.EndpointAPIBeanLocal;
import com.witbooking.middleware.exceptions.AuthenticationException;
import com.witbooking.middleware.exceptions.InvalidEntryException;
import com.witbooking.middleware.exceptions.MiddlewareException;
import com.witbooking.middleware.exceptions.WitBookerAPIException;
import com.witbooking.middleware.model.*;
import com.witbooking.middleware.model.values.HashRangeResult;
import com.witbooking.middleware.model.values.HashRangeValue;
import com.witbooking.middleware.model.values.RangeValue;
import com.witbooking.middleware.resources.MiddlewareProperties;
import com.witbooking.middleware.utils.DateUtil;
import com.witbooking.middleware.utils.HttpConnectionUtils;
import com.witbooking.middleware.utils.JSONExclusionStrategy;
import com.witbooking.middleware.utils.JsonUtils;
import com.witbooking.middleware.utils.serializers.JSONReservationSerializer;
import org.apache.log4j.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * REST Web Service
 *
 * @author Christian Delgado
 */
@Path("/endpoint")
@Stateless
public class EndpointAPIResource {

    @EJB
    private EndpointAPIBeanLocal endpointAPIBeanLocal;
    private static final Logger logger = Logger.getLogger(EndpointAPIResource.class);
    private final Gson gsonAllIncluded = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
    private final Gson gsonARI = new GsonBuilder()
            .setExclusionStrategies(JSONExclusionStrategy.JSONExclusionStrategyRangeValues()).setDateFormat("yyyy-MM-dd").create();
    private final Gson gsonReservation = new GsonBuilder().registerTypeAdapter(Reservation.class, new JSONReservationSerializer()).create();

    //TODO: create permissions and limited users
    private static final String[] limitedUsers = {"witbooker@ruralhotelsmallorca.com", "test7@witbooking.com"};

    /**
     * Creates a new instance of FrontEndResource
     */
    public EndpointAPIResource() {
    }

    @GET
    @Path("/")
    public String getServiceInfo() {
        logger.debug("getServiceInfo");
        JsonObject endpoint = new JsonObject();
        endpoint.addProperty("contactInfo", "/contactInfo");
        endpoint.addProperty("contactInfo", "/contactInfo");
        endpoint.addProperty("hotelTickers", "/hotelTickers");
        endpoint.addProperty("inventories", "/establishment/{hotelTicker}/inventories");
        endpoint.addProperty("taxes", "/establishment/{hotelTicker}/taxes");
        endpoint.addProperty("reservations", "/establishment/{hotelTicker}/reservations");
        endpoint.addProperty("reservation", "/establishment/{hotelTicker}/reservation/{reservationId}");
        endpoint.addProperty("getARIValues", "/establishment/{hotelTicker}/getARIValues");
        endpoint.addProperty("updateARIValues", "/establishment/{hotelTicker}/updateARIValues");
        endpoint.addProperty("bookingValues", "/establishment/{hotelTicker}/bookingValues");
        return endpoint + "";
    }


    @GET
    @Produces("application/json;charset=utf-8")
    @Path("/hotelTickers")
    public String getHotelsGet(@Context HttpHeaders headers) {
        logger.debug("getHotelsGet");
        try {
            return validLoginByHeader(headers) + "";
        } catch (MiddlewareException ex) {
            logger.error(ex);
            throw HttpConnectionUtils.generateJaxException(ex, "application/json;charset=utf-8",
                    JsonUtils.exceptionToJson(ex) + "");
        } catch (Exception ex) {
            logger.error(ex);
            throw HttpConnectionUtils.generateJaxException(ex, "application/json;charset=utf-8",
                    JsonUtils.exceptionToJson(new WitBookerAPIException(ex)) + "");
        }
    }

    @GET
    @Produces("application/json;charset=utf-8")
    @Path("/establishment/{hotelTicker}/inventories")
    public String getInventoriesGet(@Context HttpHeaders headers,
                                    @PathParam("hotelTicker") String hotelTicker,
                                    @QueryParam("language") String language) {
        logger.debug("getInventoriesGet");
        try {
            validPermissionsByHeader(headers, hotelTicker);
            JsonArray jsonArray = new JsonArray();
            for (Inventory item : endpointAPIBeanLocal.getInventories(hotelTicker, language)) {
                jsonArray.add(JsonUtils.dataValueHolderResumeToJsonObject(item, false));
            }
            return jsonArray + "";
        } catch (MiddlewareException ex) {
            logger.error(ex);
            logger.error(" Ticker: " + hotelTicker + " language: " + language);
            throw HttpConnectionUtils.generateJaxException(ex, "application/json;charset=utf-8",
                    JsonUtils.exceptionToJson(ex) + "");
        } catch (Exception ex) {
            logger.error(ex);
            logger.error(" Ticker: " + hotelTicker + " language: " + language);
            throw HttpConnectionUtils.generateJaxException(ex, "application/json;charset=utf-8",
                    JsonUtils.exceptionToJson(new WitBookerAPIException(ex)) + "");
        }
    }


    @GET
    @Produces("application/json;charset=utf-8")
    @Path("/establishment/{hotelTicker}/discounts")
    public String getDiscountsGet(@Context HttpHeaders headers,
                                  @PathParam("hotelTicker") String hotelTicker,
                                  @QueryParam("language") String language) {
        logger.debug("getDiscountsGet");
        try {
            validPermissionsByHeader(headers, hotelTicker);
            JsonArray jsonArray = new JsonArray();
            for (Discount item : endpointAPIBeanLocal.getDiscounts(hotelTicker, language)) {
                jsonArray.add(JsonUtils.dataValueHolderResumeToJsonObject(item, false));
            }
            return jsonArray + "";
        } catch (MiddlewareException ex) {
            logger.error(ex);
            logger.error(" Ticker: " + hotelTicker + " language: " + language);
            throw HttpConnectionUtils.generateJaxException(ex, "application/json;charset=utf-8",
                    JsonUtils.exceptionToJson(ex) + "");
        } catch (Exception ex) {
            logger.error(ex);
            logger.error(" Ticker: " + hotelTicker + " language: " + language);
            throw HttpConnectionUtils.generateJaxException(ex, "application/json;charset=utf-8",
                    JsonUtils.exceptionToJson(new WitBookerAPIException(ex)) + "");
        }
    }


    @GET
    @Produces("application/json;charset=utf-8")
    @Path("/establishment/{hotelTicker}/services")
    public String getServicesGet(@Context HttpHeaders headers,
                                 @PathParam("hotelTicker") String hotelTicker,
                                 @QueryParam("language") String language) {
        logger.debug("getServicesGet");
        try {
            validPermissionsByHeader(headers, hotelTicker);
            JsonArray jsonArray = new JsonArray();
            for (Service item : endpointAPIBeanLocal.getServices(hotelTicker, language)) {
                jsonArray.add(JsonUtils.dataValueHolderResumeToJsonObject(item, false));
            }
            return jsonArray + "";
        } catch (MiddlewareException ex) {
            logger.error(ex);
            logger.error(" Ticker: " + hotelTicker + " language: " + language);
            throw HttpConnectionUtils.generateJaxException(ex, "application/json;charset=utf-8",
                    JsonUtils.exceptionToJson(ex) + "");
        } catch (Exception ex) {
            logger.error(ex);
            logger.error(" Ticker: " + hotelTicker + " language: " + language);
            throw HttpConnectionUtils.generateJaxException(ex, "application/json;charset=utf-8",
                    JsonUtils.exceptionToJson(new WitBookerAPIException(ex)) + "");
        }
    }


    @GET
    @Produces("application/json;charset=utf-8")
    @Path("/establishment/{hotelTicker}/ARIManagementData")
    public String getARIManagementData(@Context HttpHeaders headers,
                                       @PathParam("hotelTicker") String hotelTicker,
                                       @QueryParam("language") String language) {
        logger.debug("getARIManagementData");
        try {
            validPermissionsByHeader(headers, hotelTicker);
            return JsonUtils.dataValueHolderResumeToJsonArray(endpointAPIBeanLocal.getARIManagementData(hotelTicker, language), true) + "";
        } catch (MiddlewareException ex) {
            logger.error(ex);
            logger.error(" Ticker: " + hotelTicker + " language: " + language);
            throw HttpConnectionUtils.generateJaxException(ex, "application/json;charset=utf-8",
                    JsonUtils.exceptionToJson(ex) + "");
        } catch (Exception ex) {
            logger.error(ex);
            logger.error(" Ticker: " + hotelTicker + " language: " + language);
            throw HttpConnectionUtils.generateJaxException(ex, "application/json;charset=utf-8",
                    JsonUtils.exceptionToJson(new WitBookerAPIException(ex)) + "");
        }
    }


    @GET
    @Produces("application/json;charset=utf-8")
    @Path("/establishment/{hotelTicker}/getManagementARIValues")
    public String getManagementARIValuesGet(@Context HttpHeaders headers,
                                            @QueryParam("startDate") String startDateString,
                                            @QueryParam("endDate") String endDateString,
                                            @QueryParam("language") String language,
                                            @PathParam("hotelTicker") String hotelTicker) {
        return getManagementARIValuesPost(headers, startDateString, endDateString, language, hotelTicker);
    }

    @POST
    @Produces("application/json;charset=utf-8")
    @Path("/establishment/{hotelTicker}/getManagementARIValues")
    public String getManagementARIValuesPost(@Context HttpHeaders headers,
                                             @FormParam("startDate") String startDateString,
                                             @FormParam("endDate") String endDateString,
                                             @FormParam("language") String language,
                                             @PathParam("hotelTicker") String hotelTicker) {
        logger.debug("getManagementARIValues");
        Date startDate = null;
        Date endDate = null;
        try {
            if (validateEmptyParam(startDateString, "startDate") && validateEmptyParam(endDateString, "endDate")) {
                startDate = DateUtil.stringToCalendarDate(startDateString);
                endDate = DateUtil.stringToCalendarDate(endDateString);
            }
            validPermissionsByHeader(headers, hotelTicker);
            ManagementVisualRepresentation mapValues = endpointAPIBeanLocal.getManagementARIValues(hotelTicker, startDate, endDate, language);
            JsonObject jsonObject = new JsonObject();
            jsonObject.add(ManagementVisualRepresentation.VALUE_HOLDERS_KEY,
                    JsonUtils.dataValueHolderResumeToJsonArray(mapValues.getValueHolders(), true));
            jsonObject.add(ManagementVisualRepresentation.RANGE_VALUES_KEY,
                    gsonARI.toJsonTree(HashRangeValue.listToMapRangeValues(mapValues.getRangeValues())));
            jsonObject.add(ManagementVisualRepresentation.MANAGEMENT_PARAMS_KEY,
                    gsonARI.toJsonTree(mapValues.getManagementParams()));
            return jsonObject + "";
        } catch (MiddlewareException ex) {
            logger.error(ex);
            logger.error(" Ticker: " + hotelTicker + " startDate: " + startDateString + " endDate: " + endDateString);
            throw HttpConnectionUtils.generateJaxException(ex, "application/json;charset=utf-8",
                    JsonUtils.exceptionToJson(ex) + "");
        } catch (Exception ex) {
            logger.error(ex);
            logger.error(" Ticker: " + hotelTicker + " startDate: " + startDateString + " endDate: " + endDateString);
            throw HttpConnectionUtils.generateJaxException(ex, "application/json;charset=utf-8",
                    JsonUtils.exceptionToJson(new WitBookerAPIException(ex)) + "");
        }
    }

    @GET
    @Produces("application/json;charset=utf-8")
    @Path("/establishment/{hotelTicker}/taxes")
    public String getTaxesGet(@Context HttpHeaders headers,
                              @PathParam("hotelTicker") String hotelTicker) {
        logger.debug("getTaxesGet");
        try {
            validPermissionsByHeader(headers, hotelTicker);
            List<Tax> taxes = endpointAPIBeanLocal.getTaxes(hotelTicker);
            return gsonAllIncluded.toJsonTree(taxes) + "";
        } catch (MiddlewareException ex) {
            logger.error(ex);
            logger.error(" Ticker: " + hotelTicker);
            throw HttpConnectionUtils.generateJaxException(ex, "application/json;charset=utf-8",
                    JsonUtils.exceptionToJson(ex) + "");
        } catch (Exception ex) {
            logger.error(ex);
            logger.error(" Ticker: " + hotelTicker);
            throw HttpConnectionUtils.generateJaxException(ex, "application/json;charset=utf-8",
                    JsonUtils.exceptionToJson(new WitBookerAPIException(ex)) + "");
        }
    }

    @GET
    @Produces("application/json;charset=utf-8")
    @Path("/establishment/{hotelTicker}/reservations")
    public String getReservationsByCreationDateGet(@Context HttpHeaders headers,
                                                   @QueryParam("startDate") String startDateString,
                                                   @QueryParam("endDate") String endDateString,
                                                   @PathParam("hotelTicker") String hotelTicker) {
        logger.debug("getReservationsByCreationDateGet");
        Date startDate = null;
        Date endDate = null;
        try {
            if (validateEmptyParam(startDateString, "startDate")) {
                startDate = DateUtil.stringToISODateTimeOrCalendarDate(startDateString);
            }
            if (endDateString == null || endDateString.trim().isEmpty()) {
                endDate = new Date();
            } else {
                endDate = DateUtil.stringToISODateTimeOrCalendarDate(endDateString);
            }
            validPermissionsByHeader(headers, hotelTicker);
            List<Reservation> reservationList = endpointAPIBeanLocal.getReservationsByCreationDate(startDate, endDate, hotelTicker);
            return gsonReservation.toJson(reservationList, new TypeToken<List<Reservation>>() {
            }.getType());
        } catch (MiddlewareException ex) {
            logger.error(ex);
            logger.error(" Ticker: " + hotelTicker + " startDate: " + startDateString + " endDate: " + endDateString);
            throw HttpConnectionUtils.generateJaxException(ex, "application/json;charset=utf-8",
                    JsonUtils.exceptionToJson(ex) + "");
        } catch (Exception ex) {
            logger.error(ex);
            logger.error(" Ticker: " + hotelTicker + " startDate: " + startDateString + " endDate: " + endDateString);
            throw HttpConnectionUtils.generateJaxException(ex, "application/json;charset=utf-8",
                    JsonUtils.exceptionToJson(new WitBookerAPIException(ex)) + "");
        }
    }

    @GET
    @Produces("application/json;charset=utf-8")
    @Path("/establishment/{hotelTicker}/reservation/{reservationId}")
    public String getReservationByReservationIdGet(@Context HttpHeaders headers,
                                                   @PathParam("reservationId") String reservationId,
                                                   @PathParam("hotelTicker") String hotelTicker) {
        logger.debug("getReservationByReservationIdGet");
        logger.debug("reservationId: " + reservationId + " hotelTicker:" + hotelTicker);
        try {
            validPermissionsByHeader(headers, hotelTicker);
            Reservation reservation = endpointAPIBeanLocal.getReservationsByReservationId(reservationId, hotelTicker);
            if (reservation == null) {
                throw new WitBookerAPIException("There is no reservation with this id");
            }
            return gsonReservation.toJson(reservation, new TypeToken<Reservation>() {
            }.getType());
        } catch (MiddlewareException ex) {
            logger.error(ex);
            logger.error(" Ticker: " + hotelTicker + " reservationId: " + reservationId);
            throw HttpConnectionUtils.generateJaxException(ex, "application/json;charset=utf-8",
                    JsonUtils.exceptionToJson(ex) + "");
        } catch (Exception ex) {
            logger.error(ex);
            logger.error(" Ticker: " + hotelTicker + " reservationId: " + reservationId);
            throw HttpConnectionUtils.generateJaxException(ex, "application/json;charset=utf-8",
                    JsonUtils.exceptionToJson(new WitBookerAPIException(ex)) + "");
        }
    }

    @GET
    @Produces("application/json;charset=utf-8")
    @Path("/establishment/{hotelTicker}/getARIValues")
    public String getARIValuesGet(@Context HttpHeaders headers,
                                  @QueryParam("startDate") String startDateString,
                                  @QueryParam("endDate") String endDateString,
                                  @PathParam("hotelTicker") String hotelTicker,
                                  @QueryParam("adults") Integer adults) {
        return getARIValuesPost(headers, startDateString, endDateString, hotelTicker, null, adults);
    }

    @POST
    @Produces("application/json;charset=utf-8")
    @Path("/establishment/{hotelTicker}/getARIValues")
    public String getARIValuesPost(@Context HttpHeaders headers,
                                   @FormParam("startDate") String startDateString,
                                   @FormParam("endDate") String endDateString,
                                   @PathParam("hotelTicker") String hotelTicker,
                                   @FormParam("invTicker") List<String> invTickers,
                                   @FormParam("adults") Integer adults) {
        logger.debug("getARIValues");
        Date startDate = null;
        Date endDate = null;
        try {
            if (validateEmptyParam(startDateString, "startDate") && validateEmptyParam(endDateString, "endDate")) {
                startDate = DateUtil.stringToCalendarDate(startDateString);
                endDate = DateUtil.stringToCalendarDate(endDateString);
            }
            validPermissionsByHeader(headers, hotelTicker);
            List<HashRangeValue> mapValues = endpointAPIBeanLocal.getARIValues(startDate, endDate, hotelTicker,
                    invTickers, adults);
            return gsonARI.toJsonTree(HashRangeValue.listToMapRangeValues(mapValues)) + "";
        } catch (MiddlewareException ex) {
            logger.error(ex);
            logger.error(" Ticker: " + hotelTicker + " startDate: " + startDateString + " endDate: " + endDateString + " invTickers:" + invTickers);
            throw HttpConnectionUtils.generateJaxException(ex, "application/json;charset=utf-8",
                    JsonUtils.exceptionToJson(ex) + "");
        } catch (Exception ex) {
            logger.error(ex);
            logger.error(" Ticker: " + hotelTicker + " startDate: " + startDateString + " endDate: " + endDateString + " invTickers:" + invTickers);
            throw HttpConnectionUtils.generateJaxException(ex, "application/json;charset=utf-8",
                    JsonUtils.exceptionToJson(new WitBookerAPIException(ex)) + "");
        }
    }


    //This service are active to maintain compatibility with old URL services
    @GET
    @Produces("application/json;charset=utf-8")
    @Path("/establishment/{hotelTicker}/ARIValues")
    public String getARIValuesGetLegacy(@Context HttpHeaders headers,
                                        @QueryParam("startDate") String startDateString,
                                        @QueryParam("endDate") String endDateString,
                                        @PathParam("hotelTicker") String hotelTicker,
                                        @QueryParam("adults") Integer adults) {
        return getARIValuesPost(headers, startDateString, endDateString, hotelTicker, null, adults);
    }

    //This service are active to maintain compatibility with old URL services
    @POST
    @Produces("application/json;charset=utf-8")
    @Path("/establishment/{hotelTicker}/ARIValues")
    public String getARIValuesPostLegacy(@Context HttpHeaders headers,
                                         @FormParam("startDate") String startDateString,
                                         @FormParam("endDate") String endDateString,
                                         @PathParam("hotelTicker") String hotelTicker,
                                         @FormParam("invTicker") List<String> invTickers,
                                         @FormParam("adults") Integer adults) {
        return getARIValuesPost(headers, startDateString, endDateString, hotelTicker, invTickers, adults);
    }


    @GET
    @Produces("application/json;charset=utf-8")
    @Path("/establishment/{hotelTicker}/getFullARIValues")
    public String getFullARIValuesGet(@Context HttpHeaders headers,
                                      @QueryParam("startDate") String startDateString,
                                      @QueryParam("endDate") String endDateString,
                                      @PathParam("hotelTicker") String hotelTicker) {
        return getFullARIValuesPost(headers, startDateString, endDateString, hotelTicker);
    }

    @POST
    @Produces("application/json;charset=utf-8")
    @Path("/establishment/{hotelTicker}/getFullARIValues")
    public String getFullARIValuesPost(@Context HttpHeaders headers,
                                       @FormParam("startDate") String startDateString,
                                       @FormParam("endDate") String endDateString,
                                       @PathParam("hotelTicker") String hotelTicker) {
        logger.debug("getFullARIValues");
        Date startDate = null;
        Date endDate = null;
        try {
            if (validateEmptyParam(startDateString, "startDate") && validateEmptyParam(endDateString, "endDate")) {
                startDate = DateUtil.stringToCalendarDate(startDateString);
                endDate = DateUtil.stringToCalendarDate(endDateString);
            }
            validPermissionsByHeader(headers, hotelTicker);
            List<HashRangeValue> mapValues = endpointAPIBeanLocal.getFullARIValues(hotelTicker, startDate, endDate);
            return gsonARI.toJsonTree(HashRangeValue.listToMapRangeValues(mapValues)) + "";
        } catch (MiddlewareException ex) {
            logger.error(ex);
            logger.error(" Ticker: " + hotelTicker + " startDate: " + startDateString + " endDate: " + endDateString);
            throw HttpConnectionUtils.generateJaxException(ex, "application/json;charset=utf-8",
                    JsonUtils.exceptionToJson(ex) + "");
        } catch (Exception ex) {
            logger.error(ex);
            logger.error(" Ticker: " + hotelTicker + " startDate: " + startDateString + " endDate: " + endDateString);
            throw HttpConnectionUtils.generateJaxException(ex, "application/json;charset=utf-8",
                    JsonUtils.exceptionToJson(new WitBookerAPIException(ex)) + "");
        }
    }

    @POST
    @Consumes("application/json")
    @Produces("application/json;charset=utf-8")
    @Path("/establishment/{hotelTicker}/updateARIValues")
    public String updateARIValuesPost(@Context HttpHeaders headers,
                                      @PathParam("hotelTicker") String hotelTicker,
                                      String jsonValues) {
        logger.debug("updateARIValuesPost");
        logger.debug("jsonValues: " + jsonValues);
        try {
            if (validPermissionsByHeader(headers, hotelTicker) < 1) {
                //This case means the username doesn't have write permissions on the hotel.
                throw new AuthenticationException("The username doesn't have write permissions on the hotel '" + hotelTicker + "'");
            }
            Map<String, Map<String, RangeValue>> mapRangeValues;
            try {
                mapRangeValues = gsonARI.fromJson(jsonValues, new TypeToken<Map<String, Map<String, RangeValue>>>() {
                }.getType());
            } catch (JsonSyntaxException ex) {
                throw new WitBookerAPIException("Error Parsing JSON Object", ex.getCause().getMessage());
            } catch (Exception ex) {
                throw new WitBookerAPIException("Error Parsing JSON Object", ex.getMessage());
            }
            List<HashRangeResult> hashRangeResults =
                    endpointAPIBeanLocal.updateARIValues(HashRangeValue.mapToListHashRangeValue(mapRangeValues), hotelTicker);
            return gsonARI.toJsonTree(HashRangeResult.listToMapRangeResult(hashRangeResults)) + "";
        } catch (MiddlewareException ex) {
            logger.error(ex);
            logger.error(" Ticker: " + hotelTicker + " request: " + jsonValues + " user:" + headers.getRequestHeader("username"));
            throw HttpConnectionUtils.generateJaxException(ex, "application/json;charset=utf-8",
                    JsonUtils.exceptionToJson(ex) + "");
        } catch (Exception ex) {
            logger.error(ex);
            logger.error(" Ticker: " + hotelTicker + " request: " + jsonValues + " user:" + headers.getRequestHeader("username"));
            throw HttpConnectionUtils.generateJaxException(ex, "application/json;charset=utf-8",
                    JsonUtils.exceptionToJson(new WitBookerAPIException(ex)) + "");
        }
    }

    @GET
    @Produces("application/json;charset=utf-8")
    @Path("/establishment/{hotelTicker}/bookingValues")
    public String getBookingValuesGet(@Context HttpHeaders headers,
                                      @QueryParam("checkInDate") String checkInDateString,
                                      @QueryParam("nights") Integer nights,
                                      @PathParam("hotelTicker") String hotelTicker,
                                      @QueryParam("adults") Integer adults,
                                      @QueryParam("currency") String currency,
                                      @QueryParam("promotionalCode") String promotionalCode) {
        return getBookingValuesPost(headers, checkInDateString, nights, hotelTicker, null, adults, currency, promotionalCode);
    }

    @POST
    @Produces("application/json;charset=utf-8")
    @Path("/establishment/{hotelTicker}/bookingValues")
    public String getBookingValuesPost(@Context HttpHeaders headers,
                                       @FormParam("checkInDate") String checkInDateString,
                                       @FormParam("nights") Integer nights,
                                       @PathParam("hotelTicker") String hotelTicker,
                                       @FormParam("invTicker") List<String> invTickers,
                                       @FormParam("adults") Integer adults,
                                       @FormParam("currency") String currency,
                                       @FormParam("promotionalCode") String promotionalCode) {
        logger.debug("getARIValues");
        Date checkInDate = null;
        Date checkOutDate = null;
        try {
            if (validateEmptyParam(checkInDateString, "checkInDate")) {
                checkInDate = DateUtil.stringToCalendarDate(checkInDateString);
            }
            if (nights == null || nights < 1) {
                throw new InvalidEntryException("Invalid 'nights' value");
            } else {
                checkOutDate = DateUtil.cloneAndIncrementDays(checkInDate, nights);
            }
            validPermissionsByHeader(headers, hotelTicker);
            List<HashRangeResult> mapValues = endpointAPIBeanLocal.getBookingValues(checkInDate, checkOutDate, hotelTicker,
                    invTickers, adults, currency, promotionalCode);
            return gsonARI.toJsonTree(HashRangeResult.listToMapRangeResult(mapValues)) + "";
        } catch (MiddlewareException ex) {
            logger.error(ex);
            logger.error(" Ticker: " + hotelTicker + "checkInDate: " + checkInDate + " invTickers: " + invTickers);
            throw HttpConnectionUtils.generateJaxException(ex, "application/json;charset=utf-8",
                    JsonUtils.exceptionToJson(ex) + "");
        } catch (Exception ex) {
            logger.error(ex);
            logger.error(" Ticker: " + hotelTicker + "checkInDate: " + checkInDate + " invTickers: " + invTickers);
            throw HttpConnectionUtils.generateJaxException(ex, "application/json;charset=utf-8",
                    JsonUtils.exceptionToJson(new WitBookerAPIException(ex)) + "");
        }
    }

    @GET
    @Produces("application/json;charset=utf-8")
    @Path("/contactInfo")
    public String contactInfo() {
        logger.debug("contactInfo");
        return endpointAPIBeanLocal.contactInfo();
    }

    private JsonArray validLoginByHeader(HttpHeaders headers) throws MiddlewareException {
        try {
            if (headers.getRequestHeader("username") == null || headers.getRequestHeader("password") == null) {
                throw new AuthenticationException("'username' and 'password' values are mandatory.");
            }
            String username = headers.getRequestHeader("username").get(0);
            String password = headers.getRequestHeader("password").get(0);
            logger.debug("user: '" + username + "' is try this service.");
            //if the user is WitSysAdmin, validate all hotels, and don't make the PHP connection to validate
            if (MiddlewareProperties.HTTP_AUTH_USER.equals(username) && MiddlewareProperties.HTTP_AUTH_PASS.equals(password)) {
                JsonParser jsonParser = new JsonParser();
                return (JsonArray) jsonParser.parse("[\"" + username + "\"]");
            }
            final String hotelTickersList = endpointAPIBeanLocal.getHotelTickers(username, password);
            if (hotelTickersList == null) {
                throw new AuthenticationException();
            }
            final JsonParser jsonParser = new JsonParser();
            final JsonElement jsonResponse = jsonParser.parse(hotelTickersList);
            if (jsonResponse == null || jsonResponse.isJsonNull()) {
                //This case means an internal error.
                throw new AuthenticationException();
            } else if (!jsonResponse.isJsonArray()) {
                //This case means there was an error in the authentication.
                throw new AuthenticationException("Invalid username and password.");
            }
            return (JsonArray) jsonResponse;
        } catch (MiddlewareException e) {
            //logger.error(e);
            //This case means an internal error.
            throw new AuthenticationException(e);
        } catch (Exception e) {
            logger.error(e);
            //This case means an internal error.
            throw new AuthenticationException(e);
        }
    }

    /**
     * Validating permission that the user in the HttpHeaders have for the Hotel requested. If the user have write
     * permissions return 1, if the user just have read permissions return 0. If the user don't have permissions or
     * the is a problem with the credentials, this method throws an AuthenticationException.
     *
     * @param headers
     * @param hotelTicker
     * @return
     * @throws MiddlewareException
     */
    private int validPermissionsByHeader(HttpHeaders headers, String hotelTicker) throws MiddlewareException {
        if (hotelTicker == null) {
            throw new AuthenticationException("The param 'hotelTicker' is mandatory.");
        }
        JsonArray hotelTickerList;
        hotelTickerList = validLoginByHeader(headers);
        //if the user is WitSysAdmin, validate all hotels
        if (hotelTickerList.size() == 1 && MiddlewareProperties.HTTP_AUTH_USER.equals(hotelTickerList.get(0).getAsString().replace("\"", ""))) {
            return 1;
        }
        //This case means that the users are perfectly authentic.
        for (JsonElement jsonElement : hotelTickerList) {
            if (jsonElement != null && !jsonElement.isJsonNull()) {
                String next = jsonElement.getAsString();
                if (next.trim().equalsIgnoreCase(hotelTicker)) {
                    logger.debug("Access granted for the hotel: " + hotelTicker);
                    //TODO: manage the permissions for the users.
                    if (Arrays.asList(limitedUsers).contains(headers.getRequestHeader("username").get(0).trim()))
                        return 0;
                    else
                        return 1;
                }
            }
        }
        //This case means the username doesn't have permissions on the hotel.
        throw new AuthenticationException("The username doesn't have permissions on the hotel '" + hotelTicker + "'");
    }

    private boolean validateEmptyParam(String value, String name) throws InvalidEntryException {
        if (value == null || value.trim().isEmpty()) {
            throw new InvalidEntryException("Invalid '" + name + "' value");
        } else {
            return true;
        }
    }
}
