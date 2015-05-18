/*
 *   BookingRetrievalResponse.java
 * 
 * Copyright(c) 2015 Witbooking.com. All Rights Reserved.
 * This software is the proprietary information of Witbooking.com
 * 
 */
package com.witbooking.middleware.integration.dingus.model;

import com.witbooking.middleware.utils.serializers.JaxbDateWithoutTimeSerializer;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * <p>Java class for anonymous complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="HotelReservations">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="HotelReservation">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="Rooms">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="Room">
 *                                         &lt;complexType>
 *                                           &lt;complexContent>
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                               &lt;sequence>
 *                                                 &lt;element name="Guests">
 *                                                   &lt;complexType>
 *                                                     &lt;complexContent>
 *                                                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                                         &lt;sequence>
 *                                                           &lt;element name="Guest">
 *                                                             &lt;complexType>
 *                                                               &lt;simpleContent>
 *                                                                 &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *                                                                   &lt;attribute name="Title" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                                                   &lt;attribute name="GivenName" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                                                   &lt;attribute name="SurName" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                                                   &lt;attribute name="Age" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                                                 &lt;/extension>
 *                                                               &lt;/simpleContent>
 *                                                             &lt;/complexType>
 *                                                           &lt;/element>
 *                                                         &lt;/sequence>
 *                                                       &lt;/restriction>
 *                                                     &lt;/complexContent>
 *                                                   &lt;/complexType>
 *                                                 &lt;/element>
 *                                                 &lt;element name="Pricing">
 *                                                   &lt;complexType>
 *                                                     &lt;complexContent>
 *                                                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                                         &lt;sequence>
 *                                                           &lt;element name="DailyRates">
 *                                                             &lt;complexType>
 *                                                               &lt;complexContent>
 *                                                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                                                   &lt;sequence>
 *                                                                     &lt;element name="DailyRate">
 *                                                                       &lt;complexType>
 *                                                                         &lt;complexContent>
 *                                                                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                                                             &lt;sequence>
 *                                                                               &lt;element name="Total">
 *                                                                                 &lt;complexType>
 *                                                                                   &lt;simpleContent>
 *                                                                                     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *                                                                                       &lt;attribute name="AmountAfterTax" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                                                                       &lt;attribute name="CurrencyCode" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                                                                     &lt;/extension>
 *                                                                                   &lt;/simpleContent>
 *                                                                                 &lt;/complexType>
 *                                                                               &lt;/element>
 *                                                                             &lt;/sequence>
 *                                                                             &lt;attribute name="EffectiveDate" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                                                           &lt;/restriction>
 *                                                                         &lt;/complexContent>
 *                                                                       &lt;/complexType>
 *                                                                     &lt;/element>
 *                                                                   &lt;/sequence>
 *                                                                 &lt;/restriction>
 *                                                               &lt;/complexContent>
 *                                                             &lt;/complexType>
 *                                                           &lt;/element>
 *                                                           &lt;element name="RoomTotals">
 *                                                             &lt;complexType>
 *                                                               &lt;complexContent>
 *                                                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                                                   &lt;sequence>
 *                                                                     &lt;element name="RoomTotal">
 *                                                                       &lt;complexType>
 *                                                                         &lt;simpleContent>
 *                                                                           &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *                                                                             &lt;attribute name="AmountAfterTax" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                                                             &lt;attribute name="CurrencyCode" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                                                             &lt;attribute name="IsNetRate" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                                                             &lt;attribute name="IsGrossRate" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                                                             &lt;attribute name="Commission" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                                                           &lt;/extension>
 *                                                                         &lt;/simpleContent>
 *                                                                       &lt;/complexType>
 *                                                                     &lt;/element>
 *                                                                   &lt;/sequence>
 *                                                                 &lt;/restriction>
 *                                                               &lt;/complexContent>
 *                                                             &lt;/complexType>
 *                                                           &lt;/element>
 *                                                         &lt;/sequence>
 *                                                       &lt;/restriction>
 *                                                     &lt;/complexContent>
 *                                                   &lt;/complexType>
 *                                                 &lt;/element>
 *                                               &lt;/sequence>
 *                                               &lt;attribute name="CheckinDate" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                               &lt;attribute name="CheckOutDate" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                               &lt;attribute name="RoomCode" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                               &lt;attribute name="RoomDescription" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                               &lt;attribute name="MealPlanCode" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                               &lt;attribute name="MealPlanDescription" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                               &lt;attribute name="RatePlanCode" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                               &lt;attribute name="RatePlanDescription" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                               &lt;attribute name="NumberOfGuests" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                               &lt;attribute name="NumberOfAdults" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                               &lt;attribute name="NumberOfChildren" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                               &lt;attribute name="NumberOfBabies" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                             &lt;/restriction>
 *                                           &lt;/complexContent>
 *                                         &lt;/complexType>
 *                                       &lt;/element>
 *                                     &lt;/sequence>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                             &lt;element name="SpecialRequests">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="SpecialRequest">
 *                                         &lt;complexType>
 *                                           &lt;complexContent>
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                               &lt;sequence>
 *                                                 &lt;element name="Text" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                               &lt;/sequence>
 *                                             &lt;/restriction>
 *                                           &lt;/complexContent>
 *                                         &lt;/complexType>
 *                                       &lt;/element>
 *                                     &lt;/sequence>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                             &lt;element name="ReservationInfo">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="Total">
 *                                         &lt;complexType>
 *                                           &lt;simpleContent>
 *                                             &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *                                               &lt;attribute name="AmountAfterTax" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                               &lt;attribute name="IsNetRate" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                               &lt;attribute name="CurrencyCode" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                               &lt;attribute name="IsGrossRate" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                               &lt;attribute name="Commission" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                             &lt;/extension>
 *                                           &lt;/simpleContent>
 *                                         &lt;/complexType>
 *                                       &lt;/element>
 *                                       &lt;element name="Customer">
 *                                         &lt;complexType>
 *                                           &lt;complexContent>
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                               &lt;sequence>
 *                                                 &lt;element name="LeadPax">
 *                                                   &lt;complexType>
 *                                                     &lt;simpleContent>
 *                                                       &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *                                                         &lt;attribute name="GivenName" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                                         &lt;attribute name="SurName" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                                       &lt;/extension>
 *                                                     &lt;/simpleContent>
 *                                                   &lt;/complexType>
 *                                                 &lt;/element>
 *                                                 &lt;element name="PaymentForm">
 *                                                   &lt;complexType>
 *                                                     &lt;complexContent>
 *                                                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                                         &lt;sequence>
 *                                                           &lt;element name="PaymentCard">
 *                                                             &lt;complexType>
 *                                                               &lt;complexContent>
 *                                                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                                                   &lt;sequence>
 *                                                                     &lt;element name="CardHolderName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                                                   &lt;/sequence>
 *                                                                   &lt;attribute name="CardType" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                                                   &lt;attribute name="CardNumber" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                                                   &lt;attribute name="SeriesCode" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                                                   &lt;attribute name="ExpireDate" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                                                 &lt;/restriction>
 *                                                               &lt;/complexContent>
 *                                                             &lt;/complexType>
 *                                                           &lt;/element>
 *                                                         &lt;/sequence>
 *                                                       &lt;/restriction>
 *                                                     &lt;/complexContent>
 *                                                   &lt;/complexType>
 *                                                 &lt;/element>
 *                                                 &lt;element name="Address">
 *                                                   &lt;complexType>
 *                                                     &lt;complexContent>
 *                                                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                                         &lt;sequence>
 *                                                           &lt;element name="StreetName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                                           &lt;element name="StreetNmbr" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                                           &lt;element name="CityName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                                           &lt;element name="PostalCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                                           &lt;element name="CountryName">
 *                                                             &lt;complexType>
 *                                                               &lt;simpleContent>
 *                                                                 &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *                                                                   &lt;attribute name="Code" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                                                 &lt;/extension>
 *                                                               &lt;/simpleContent>
 *                                                             &lt;/complexType>
 *                                                           &lt;/element>
 *                                                         &lt;/sequence>
 *                                                       &lt;/restriction>
 *                                                     &lt;/complexContent>
 *                                                   &lt;/complexType>
 *                                                 &lt;/element>
 *                                               &lt;/sequence>
 *                                             &lt;/restriction>
 *                                           &lt;/complexContent>
 *                                         &lt;/complexType>
 *                                       &lt;/element>
 *                                       &lt;element name="ReservationIDs">
 *                                         &lt;complexType>
 *                                           &lt;complexContent>
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                               &lt;sequence>
 *                                                 &lt;element name="ReservationID">
 *                                                   &lt;complexType>
 *                                                     &lt;simpleContent>
 *                                                       &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *                                                         &lt;attribute name="Valueeee" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                                       &lt;/extension>
 *                                                     &lt;/simpleContent>
 *                                                   &lt;/complexType>
 *                                                 &lt;/element>
 *                                               &lt;/sequence>
 *                                             &lt;/restriction>
 *                                           &lt;/complexContent>
 *                                         &lt;/complexType>
 *                                       &lt;/element>
 *                                     &lt;/sequence>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                             &lt;element name="AdditionalInfoList">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="AdditionalInfo">
 *                                         &lt;complexType>
 *                                           &lt;simpleContent>
 *                                             &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *                                               &lt;attribute name="Code" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                             &lt;/extension>
 *                                           &lt;/simpleContent>
 *                                         &lt;/complexType>
 *                                       &lt;/element>
 *                                     &lt;/sequence>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                           &lt;/sequence>
 *                           &lt;attribute name="ResStatus" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="HotelCode" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="HotelName" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="BookingDate" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="LastModifyDateTime" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="CheckinDate" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="CheckOutDate" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *       &lt;attribute name="responseDate" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "errors",
        "hotelReservations"
})
@XmlRootElement(name = "BookingRetrievalResponse")
public class BookingRetrievalResponse {

    @XmlElementWrapper(name = "Errors")
    @XmlElement(name = "Error")
    private List<ErrorTypeGeneric> errors;
    @XmlElement(name = "HotelReservations", required = true)
    protected BookingRetrievalResponse.HotelReservations hotelReservations;
    @XmlAttribute(name = "responseDate")
    @XmlSchemaType(name = "dateTime")
    protected Date responseDate;

    public BookingRetrievalResponse() {
        this.hotelReservations = new HotelReservations();
    }

    /**
     * Gets the value of the hotelReservations property.
     *
     * @return possible object is
     * {@link BookingRetrievalResponse.HotelReservations }
     */
    public BookingRetrievalResponse.HotelReservations getHotelReservations() {
        return hotelReservations;
    }

    /**
     * Sets the value of the hotelReservations property.
     *
     * @param value allowed object is
     *              {@link BookingRetrievalResponse.HotelReservations }
     */
    public void setHotelReservations(BookingRetrievalResponse.HotelReservations value) {
        this.hotelReservations = value;
    }

    /**
     * Gets the value of the responseDate property.
     *
     * @return possible object is
     * {@link Date }
     */
    public Date getResponseDate() {
        return responseDate;
    }

    /**
     * Sets the value of the responseDate property.
     *
     * @param value allowed object is
     *              {@link Date }
     */
    public void setResponseDate(Date value) {
        this.responseDate = value;
    }

    public List<ErrorTypeGeneric> getErrors() {
        return errors;
    }

    public void setErrors(List<ErrorTypeGeneric> errors) {
        this.errors = errors;
    }

    public void addErrorMessage(String errorMessage) {
        if (errors == null) {
            errors = new ArrayList<>();
        }
        errors.add(new ErrorTypeGeneric(errorMessage));
    }


    public void addHotelReservation(HotelReservations.HotelReservation hotelReservation) {
        if (hotelReservations == null) {
            hotelReservations = new HotelReservations();
        }
        if (hotelReservations.hotelReservationList == null) {
            hotelReservations.hotelReservationList = new ArrayList<>();
        }
        hotelReservations.hotelReservationList.add(hotelReservation);
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "hotelReservationList"
    })
    public static class HotelReservations {

        @XmlElement(name = "HotelReservation", required = true)
        protected List<BookingRetrievalResponse.HotelReservations.HotelReservation> hotelReservationList;

        /**
         * Gets the value of the hotelReservation property.
         *
         * @return possible object is
         * {@link BookingRetrievalResponse.HotelReservations.HotelReservation }
         */
        public List<BookingRetrievalResponse.HotelReservations.HotelReservation> getHotelReservationList() {
            return hotelReservationList;
        }

        /**
         * Sets the value of the hotelReservation property.
         *
         * @param value allowed object is
         *              {@link BookingRetrievalResponse.HotelReservations.HotelReservation }
         */
        public void setHotelReservationList(List<BookingRetrievalResponse.HotelReservations.HotelReservation> value) {
            this.hotelReservationList = value;
        }

        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
                "rooms",
                "specialRequests",
                "reservationInfo",
                "additionalInfoList"
        })
        public static class HotelReservation {

            @XmlElement(name = "Rooms", required = true)
            protected BookingRetrievalResponse.HotelReservations.HotelReservation.Rooms rooms;
            @XmlElement(name = "SpecialRequests", required = true)
            protected BookingRetrievalResponse.HotelReservations.HotelReservation.SpecialRequests specialRequests;
            @XmlElement(name = "ReservationInfo", required = true)
            protected BookingRetrievalResponse.HotelReservations.HotelReservation.ReservationInfo reservationInfo;
            @XmlElement(name = "AdditionalInfoList", required = true)
            protected BookingRetrievalResponse.HotelReservations.HotelReservation.AdditionalInfoList additionalInfoList;
            @XmlAttribute(name = "ResStatus")
            protected String resStatus;
            @XmlAttribute(name = "HotelCode")
            protected String hotelCode;
            @XmlAttribute(name = "HotelName")
            protected String hotelName;
            @XmlAttribute(name = "BookingDate")
            @XmlSchemaType(name = "dateTime")
            protected Date bookingDate;
            @XmlAttribute(name = "LastModifyDateTime")
            @XmlSchemaType(name = "dateTime")
            protected Date lastModifyDateTime;
            @XmlAttribute(name = "CheckinDate", required = true)
            @XmlSchemaType(name = "date")
            @XmlJavaTypeAdapter(JaxbDateWithoutTimeSerializer.class)
            protected Date checkInDate;
            @XmlAttribute(name = "CheckOutDate", required = true)
            @XmlSchemaType(name = "date")
            @XmlJavaTypeAdapter(JaxbDateWithoutTimeSerializer.class)
            protected Date checkOutDate;

            public HotelReservation() {
                this.rooms = new Rooms();
                this.reservationInfo = new ReservationInfo();
            }

            /**
             * Gets the value of the rooms property.
             *
             * @return possible object is
             * {@link BookingRetrievalResponse.HotelReservations.HotelReservation.Rooms }
             */
            public BookingRetrievalResponse.HotelReservations.HotelReservation.Rooms getRooms() {
                return rooms;
            }

            /**
             * Sets the value of the rooms property.
             *
             * @param value allowed object is
             *              {@link BookingRetrievalResponse.HotelReservations.HotelReservation.Rooms }
             */
            public void setRooms(BookingRetrievalResponse.HotelReservations.HotelReservation.Rooms value) {
                this.rooms = value;
            }

            /**
             * Gets the value of the specialRequests property.
             *
             * @return possible object is
             * {@link BookingRetrievalResponse.HotelReservations.HotelReservation.SpecialRequests }
             */
            public BookingRetrievalResponse.HotelReservations.HotelReservation.SpecialRequests getSpecialRequests() {
                return specialRequests;
            }

            /**
             * Sets the value of the specialRequests property.
             *
             * @param value allowed object is
             *              {@link BookingRetrievalResponse.HotelReservations.HotelReservation.SpecialRequests }
             */
            public void setSpecialRequests(BookingRetrievalResponse.HotelReservations.HotelReservation.SpecialRequests value) {
                this.specialRequests = value;
            }

            /**
             * Gets the value of the reservationInfo property.
             *
             * @return possible object is
             * {@link BookingRetrievalResponse.HotelReservations.HotelReservation.ReservationInfo }
             */
            public BookingRetrievalResponse.HotelReservations.HotelReservation.ReservationInfo getReservationInfo() {
                return reservationInfo;
            }

            /**
             * Sets the value of the reservationInfo property.
             *
             * @param value allowed object is
             *              {@link BookingRetrievalResponse.HotelReservations.HotelReservation.ReservationInfo }
             */
            public void setReservationInfo(BookingRetrievalResponse.HotelReservations.HotelReservation.ReservationInfo value) {
                this.reservationInfo = value;
            }

            /**
             * Gets the value of the additionalInfoList property.
             *
             * @return possible object is
             * {@link BookingRetrievalResponse.HotelReservations.HotelReservation.AdditionalInfoList }
             */
            public BookingRetrievalResponse.HotelReservations.HotelReservation.AdditionalInfoList getAdditionalInfoList() {
                return additionalInfoList;
            }

            /**
             * Sets the value of the additionalInfoList property.
             *
             * @param value allowed object is
             *              {@link BookingRetrievalResponse.HotelReservations.HotelReservation.AdditionalInfoList }
             */
            public void setAdditionalInfoList(BookingRetrievalResponse.HotelReservations.HotelReservation.AdditionalInfoList value) {
                this.additionalInfoList = value;
            }

            /**
             * Gets the value of the resStatus property.
             *
             * @return possible object is
             * {@link String }
             */
            public String getResStatus() {
                return resStatus;
            }

            /**
             * Sets the value of the resStatus property.
             *
             * @param value allowed object is
             *              {@link String }
             */
            public void setResStatus(String value) {
                this.resStatus = value;
            }

            /**
             * Gets the value of the hotelCode property.
             *
             * @return possible object is
             * {@link String }
             */
            public String getHotelCode() {
                return hotelCode;
            }

            /**
             * Sets the value of the hotelCode property.
             *
             * @param value allowed object is
             *              {@link String }
             */
            public void setHotelCode(String value) {
                this.hotelCode = value;
            }

            /**
             * Gets the value of the hotelName property.
             *
             * @return possible object is
             * {@link String }
             */
            public String getHotelName() {
                return hotelName;
            }

            /**
             * Sets the value of the hotelName property.
             *
             * @param value allowed object is
             *              {@link String }
             */
            public void setHotelName(String value) {
                this.hotelName = value;
            }

            /**
             * Gets the value of the bookingDate property.
             *
             * @return possible object is
             * {@link String }
             */
            public Date getBookingDate() {
                return bookingDate;
            }

            /**
             * Sets the value of the bookingDate property.
             *
             * @param value allowed object is
             *              {@link String }
             */
            public void setBookingDate(Date value) {
                this.bookingDate = value;
            }

            /**
             * Gets the value of the lastModifyDateTime property.
             *
             * @return possible object is
             * {@link String }
             */
            public Date getLastModifyDateTime() {
                return lastModifyDateTime;
            }

            /**
             * Sets the value of the lastModifyDateTime property.
             *
             * @param value allowed object is
             *              {@link String }
             */
            public void setLastModifyDateTime(Date value) {
                this.lastModifyDateTime = value;
            }

            /**
             * Gets the value of the checkinDate property.
             *
             * @return possible object is
             * {@link String }
             */
            public Date getCheckInDate() {
                return checkInDate;
            }

            /**
             * Sets the value of the checkinDate property.
             *
             * @param value allowed object is
             *              {@link String }
             */
            public void setCheckInDate(Date value) {
                this.checkInDate = value;
            }

            /**
             * Gets the value of the checkOutDate property.
             *
             * @return possible object is
             * {@link String }
             */
            public Date getCheckOutDate() {
                return checkOutDate;
            }

            /**
             * Sets the value of the checkOutDate property.
             *
             * @param value allowed object is
             *              {@link String }
             */
            public void setCheckOutDate(Date value) {
                this.checkOutDate = value;
            }


            public void addSpecialRequest(String comments) {
                if (comments == null)
                    return;
                if (specialRequests == null)
                    specialRequests = new SpecialRequests();
                specialRequests.addSpecialRequest(comments);
            }


            public void addRoom(Rooms.Room room) {
                if (rooms.roomList == null) {
                    rooms.roomList = new ArrayList<>();
                }
                rooms.roomList.add(room);
            }

            public void addRoomList(List<BookingRetrievalResponse.HotelReservations.HotelReservation.Rooms.Room> roomList) {
                rooms.roomList = roomList;
            }



            public void addAdditionalInfo(AdditionalInfoList.AdditionalInfo additionalInfo) {
                if (additionalInfoList == null)
                    additionalInfoList = new AdditionalInfoList();
                if (additionalInfoList.infoList == null) {
                    additionalInfoList.infoList = new ArrayList<>();
                }
                additionalInfoList.infoList.add(additionalInfo);
            }

            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                    "infoList"
            })
            public static class AdditionalInfoList {

                @XmlElement(name = "AdditionalInfo", required = true)
                protected List<BookingRetrievalResponse.HotelReservations.HotelReservation.AdditionalInfoList.AdditionalInfo> infoList;

                /**
                 * Gets the value of the additionalInfo property.
                 *
                 * @return possible object is
                 * {@link BookingRetrievalResponse.HotelReservations.HotelReservation.AdditionalInfoList.AdditionalInfo }
                 */
                public List<BookingRetrievalResponse.HotelReservations.HotelReservation.AdditionalInfoList.AdditionalInfo> getInfoList() {
                    return infoList;
                }

                /**
                 * Sets the value of the additionalInfo property.
                 *
                 * @param value allowed object is
                 *              {@link BookingRetrievalResponse.HotelReservations.HotelReservation.AdditionalInfoList.AdditionalInfo }
                 */
                public void setInfoList(List<BookingRetrievalResponse.HotelReservations.HotelReservation.AdditionalInfoList.AdditionalInfo> value) {
                    this.infoList = value;
                }

                @XmlAccessorType(XmlAccessType.FIELD)
                @XmlType(name = "", propOrder = {
                        "value"
                })
                public static class AdditionalInfo {

                    @XmlValue
                    protected String value;
                    @XmlAttribute(name = "Code")
                    protected String code;

                    public AdditionalInfo() {
                    }

                    public AdditionalInfo(String value, String code) {
                        this.value = value;
                        this.code = code;
                    }

                    /**
                     * Gets the value of the value property.
                     *
                     * @return possible object is
                     * {@link String }
                     */
                    public String getValue() {
                        return value;
                    }

                    /**
                     * Sets the value of the value property.
                     *
                     * @param value allowed object is
                     *              {@link String }
                     */
                    public void setValue(String value) {
                        this.value = value;
                    }

                    /**
                     * Gets the value of the code property.
                     *
                     * @return possible object is
                     * {@link String }
                     */
                    public String getCode() {
                        return code;
                    }

                    /**
                     * Sets the value of the code property.
                     *
                     * @param value allowed object is
                     *              {@link String }
                     */
                    public void setCode(String value) {
                        this.code = value;
                    }

                }

            }

            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                    "total",
                    "customer",
                    "reservationIDs"
            })
            public static class ReservationInfo {

                @XmlElement(name = "Total", required = true)
                protected BookingRetrievalResponse.HotelReservations.HotelReservation.ReservationInfo.Total total;
                @XmlElement(name = "Customer", required = true)
                protected BookingRetrievalResponse.HotelReservations.HotelReservation.ReservationInfo.Customer customer;
                @XmlElement(name = "ReservationIDs", required = true)
                protected BookingRetrievalResponse.HotelReservations.HotelReservation.ReservationInfo.ReservationIDs reservationIDs;

                public ReservationInfo() {
                    this.reservationIDs = new ReservationIDs();
                }

                /**
                 * Gets the value of the total property.
                 *
                 * @return possible object is
                 * {@link BookingRetrievalResponse.HotelReservations.HotelReservation.ReservationInfo.Total }
                 */
                public BookingRetrievalResponse.HotelReservations.HotelReservation.ReservationInfo.Total getTotal() {
                    return total;
                }

                /**
                 * Sets the value of the total property.
                 *
                 * @param value allowed object is
                 *              {@link BookingRetrievalResponse.HotelReservations.HotelReservation.ReservationInfo.Total }
                 */
                public void setTotal(BookingRetrievalResponse.HotelReservations.HotelReservation.ReservationInfo.Total value) {
                    this.total = value;
                }

                /**
                 * Gets the value of the customer property.
                 *
                 * @return possible object is
                 * {@link BookingRetrievalResponse.HotelReservations.HotelReservation.ReservationInfo.Customer }
                 */
                public BookingRetrievalResponse.HotelReservations.HotelReservation.ReservationInfo.Customer getCustomer() {
                    return customer;
                }

                /**
                 * Sets the value of the customer property.
                 *
                 * @param value allowed object is
                 *              {@link BookingRetrievalResponse.HotelReservations.HotelReservation.ReservationInfo.Customer }
                 */
                public void setCustomer(BookingRetrievalResponse.HotelReservations.HotelReservation.ReservationInfo.Customer value) {
                    this.customer = value;
                }

                /**
                 * Gets the value of the reservationIDs property.
                 *
                 * @return possible object is
                 * {@link BookingRetrievalResponse.HotelReservations.HotelReservation.ReservationInfo.ReservationIDs }
                 */
                public BookingRetrievalResponse.HotelReservations.HotelReservation.ReservationInfo.ReservationIDs getReservationIDs() {
                    return reservationIDs;
                }

                /**
                 * Sets the value of the reservationIDs property.
                 *
                 * @param value allowed object is
                 *              {@link BookingRetrievalResponse.HotelReservations.HotelReservation.ReservationInfo.ReservationIDs }
                 */
                public void setReservationIDs(BookingRetrievalResponse.HotelReservations.HotelReservation.ReservationInfo.ReservationIDs value) {
                    this.reservationIDs = value;
                }

                public void setReservationIDs(String value) {
                    this.reservationIDs.setReservationID(value);
                }


                @XmlAccessorType(XmlAccessType.FIELD)
                @XmlType(name = "", propOrder = {
                        "leadPax",
                        "paymentForm",
                        "address"
                })
                public static class Customer {

                    @XmlElement(name = "LeadPax", required = true)
                    protected BookingRetrievalResponse.HotelReservations.HotelReservation.ReservationInfo.Customer.LeadPax leadPax;
                    @XmlElement(name = "PaymentForm", required = true)
                    protected BookingRetrievalResponse.HotelReservations.HotelReservation.ReservationInfo.Customer.PaymentForm paymentForm;
                    @XmlElement(name = "Address", required = true)
                    protected BookingRetrievalResponse.HotelReservations.HotelReservation.ReservationInfo.Customer.Address address;

                    /**
                     * Gets the value of the leadPax property.
                     *
                     * @return possible object is
                     * {@link BookingRetrievalResponse.HotelReservations.HotelReservation.ReservationInfo.Customer.LeadPax }
                     */
                    public BookingRetrievalResponse.HotelReservations.HotelReservation.ReservationInfo.Customer.LeadPax getLeadPax() {
                        return leadPax;
                    }

                    /**
                     * Sets the value of the leadPax property.
                     *
                     * @param value allowed object is
                     *              {@link BookingRetrievalResponse.HotelReservations.HotelReservation.ReservationInfo.Customer.LeadPax }
                     */
                    public void setLeadPax(BookingRetrievalResponse.HotelReservations.HotelReservation.ReservationInfo.Customer.LeadPax value) {
                        this.leadPax = value;
                    }

                    /**
                     * Gets the value of the paymentForm property.
                     *
                     * @return possible object is
                     * {@link BookingRetrievalResponse.HotelReservations.HotelReservation.ReservationInfo.Customer.PaymentForm }
                     */
                    public BookingRetrievalResponse.HotelReservations.HotelReservation.ReservationInfo.Customer.PaymentForm getPaymentForm() {
                        return paymentForm;
                    }

                    /**
                     * Sets the value of the paymentForm property.
                     *
                     * @param value allowed object is
                     *              {@link BookingRetrievalResponse.HotelReservations.HotelReservation.ReservationInfo.Customer.PaymentForm }
                     */
                    public void setPaymentForm(BookingRetrievalResponse.HotelReservations.HotelReservation.ReservationInfo.Customer.PaymentForm value) {
                        this.paymentForm = value;
                    }

                    /**
                     * Gets the value of the address property.
                     *
                     * @return possible object is
                     * {@link BookingRetrievalResponse.HotelReservations.HotelReservation.ReservationInfo.Customer.Address }
                     */
                    public BookingRetrievalResponse.HotelReservations.HotelReservation.ReservationInfo.Customer.Address getAddress() {
                        return address;
                    }

                    /**
                     * Sets the value of the address property.
                     *
                     * @param value allowed object is
                     *              {@link BookingRetrievalResponse.HotelReservations.HotelReservation.ReservationInfo.Customer.Address }
                     */
                    public void setAddress(BookingRetrievalResponse.HotelReservations.HotelReservation.ReservationInfo.Customer.Address value) {
                        this.address = value;
                    }

                    @XmlAccessorType(XmlAccessType.FIELD)
                    @XmlType(name = "", propOrder = {
                            "streetName",
                            "streetNmbr",
                            "cityName",
                            "postalCode",
                            "countryName"
                    })
                    public static class Address {

                        @XmlElement(name = "StreetName")
                        protected String streetName;
                        @XmlElement(name = "StreetNmbr")
                        protected String streetNmbr;
                        @XmlElement(name = "CityName")
                        protected String cityName;
                        @XmlElement(name = "PostalCode")
                        protected String postalCode;
                        @XmlElement(name = "CountryName")
                        protected BookingRetrievalResponse.HotelReservations.HotelReservation.ReservationInfo.Customer.Address.CountryName countryName;

                        public Address() {
                        }

                        public Address(String streetName, String streetNmbr, String cityName, String postalCode, String countryName) {
                            this.streetName = streetName;
                            this.streetNmbr = streetNmbr;
                            this.cityName = cityName;
                            this.postalCode = postalCode;
                            this.countryName = new CountryName(countryName);
                        }

                        /**
                         * Gets the value of the streetName property.
                         *
                         * @return possible object is
                         * {@link String }
                         */
                        public String getStreetName() {
                            return streetName;
                        }

                        /**
                         * Sets the value of the streetName property.
                         *
                         * @param value allowed object is
                         *              {@link String }
                         */
                        public void setStreetName(String value) {
                            this.streetName = value;
                        }

                        /**
                         * Gets the value of the streetNmbr property.
                         *
                         * @return possible object is
                         * {@link String }
                         */
                        public String getStreetNmbr() {
                            return streetNmbr;
                        }

                        /**
                         * Sets the value of the streetNmbr property.
                         *
                         * @param value allowed object is
                         *              {@link String }
                         */
                        public void setStreetNmbr(String value) {
                            this.streetNmbr = value;
                        }

                        /**
                         * Gets the value of the cityName property.
                         *
                         * @return possible object is
                         * {@link String }
                         */
                        public String getCityName() {
                            return cityName;
                        }

                        /**
                         * Sets the value of the cityName property.
                         *
                         * @param value allowed object is
                         *              {@link String }
                         */
                        public void setCityName(String value) {
                            this.cityName = value;
                        }

                        /**
                         * Gets the value of the postalCode property.
                         *
                         * @return possible object is
                         * {@link String }
                         */
                        public String getPostalCode() {
                            return postalCode;
                        }

                        /**
                         * Sets the value of the postalCode property.
                         *
                         * @param value allowed object is
                         *              {@link String }
                         */
                        public void setPostalCode(String value) {
                            this.postalCode = value;
                        }

                        /**
                         * Gets the value of the countryName property.
                         *
                         * @return possible object is
                         * {@link BookingRetrievalResponse.HotelReservations.HotelReservation.ReservationInfo.Customer.Address.CountryName }
                         */
                        public BookingRetrievalResponse.HotelReservations.HotelReservation.ReservationInfo.Customer.Address.CountryName getCountryName() {
                            return countryName;
                        }

                        /**
                         * Sets the value of the countryName property.
                         *
                         * @param value allowed object is
                         *              {@link BookingRetrievalResponse.HotelReservations.HotelReservation.ReservationInfo.Customer.Address.CountryName }
                         */
                        public void setCountryName(BookingRetrievalResponse.HotelReservations.HotelReservation.ReservationInfo.Customer.Address.CountryName value) {
                            this.countryName = value;
                        }


                        @XmlAccessorType(XmlAccessType.FIELD)
                        @XmlType(name = "")
                        public static class CountryName {

                            @XmlAttribute(name = "Code")
                            protected String code;

                            public CountryName() {
                            }

                            public CountryName(String code) {
                                this.code = code;
                            }

                            /**
                             * Gets the value of the code property.
                             *
                             * @return possible object is
                             * {@link String }
                             */
                            public String getCode() {
                                return code;
                            }

                            /**
                             * Sets the value of the code property.
                             *
                             * @param value allowed object is
                             *              {@link String }
                             */
                            public void setCode(String value) {
                                this.code = value;
                            }

                        }

                    }


                    @XmlAccessorType(XmlAccessType.FIELD)
                    @XmlType(name = "")
                    public static class LeadPax {

                        @XmlAttribute(name = "GivenName")
                        protected String givenName;
                        @XmlAttribute(name = "SurName")
                        protected String surName;

                        public LeadPax() {
                        }

                        public LeadPax(String givenName, String surName) {
                            this.givenName = givenName;
                            this.surName = surName;
                        }

                        /**
                         * Gets the value of the givenName property.
                         *
                         * @return possible object is
                         * {@link String }
                         */
                        public String getGivenName() {
                            return givenName;
                        }

                        /**
                         * Sets the value of the givenName property.
                         *
                         * @param value allowed object is
                         *              {@link String }
                         */
                        public void setGivenName(String value) {
                            this.givenName = value;
                        }

                        /**
                         * Gets the value of the surName property.
                         *
                         * @return possible object is
                         * {@link String }
                         */
                        public String getSurName() {
                            return surName;
                        }

                        /**
                         * Sets the value of the surName property.
                         *
                         * @param value allowed object is
                         *              {@link String }
                         */
                        public void setSurName(String value) {
                            this.surName = value;
                        }

                    }


                    @XmlAccessorType(XmlAccessType.FIELD)
                    @XmlType(name = "", propOrder = {
                            "paymentCard"
                    })
                    public static class PaymentForm {

                        @XmlElement(name = "PaymentCard", required = true)
                        protected BookingRetrievalResponse.HotelReservations.HotelReservation.ReservationInfo.Customer.PaymentForm.PaymentCard paymentCard;

                        public PaymentForm() {
                        }

                        public PaymentForm(String cardHolderName, String cardType, String cardNumber, String seriesCode, String expireDate) {
                            this.paymentCard = new PaymentCard(cardHolderName, cardType, cardNumber, seriesCode, expireDate);
                        }

                        /**
                         * Gets the value of the paymentCard property.
                         *
                         * @return possible object is
                         * {@link BookingRetrievalResponse.HotelReservations.HotelReservation.ReservationInfo.Customer.PaymentForm.PaymentCard }
                         */
                        public BookingRetrievalResponse.HotelReservations.HotelReservation.ReservationInfo.Customer.PaymentForm.PaymentCard getPaymentCard() {
                            return paymentCard;
                        }

                        /**
                         * Sets the value of the paymentCard property.
                         *
                         * @param value allowed object is
                         *              {@link BookingRetrievalResponse.HotelReservations.HotelReservation.ReservationInfo.Customer.PaymentForm.PaymentCard }
                         */
                        public void setPaymentCard(BookingRetrievalResponse.HotelReservations.HotelReservation.ReservationInfo.Customer.PaymentForm.PaymentCard value) {
                            this.paymentCard = value;
                        }


                        @XmlAccessorType(XmlAccessType.FIELD)
                        @XmlType(name = "", propOrder = {
                                "cardHolderName"
                        })
                        public static class PaymentCard {

                            @XmlElement(name = "CardHolderName", required = true)
                            protected String cardHolderName;
                            @XmlAttribute(name = "CardType")
                            protected String cardType;
                            @XmlAttribute(name = "CardNumber")
                            protected String cardNumber;
                            @XmlAttribute(name = "SeriesCode")
                            protected String seriesCode;
                            @XmlAttribute(name = "ExpireDate")
                            protected String expireDate;

                            public PaymentCard() {
                            }

                            public PaymentCard(String cardHolderName, String cardType, String cardNumber, String seriesCode, String expireDate) {
                                this.cardHolderName = cardHolderName;
                                this.cardType = cardType;
                                this.cardNumber = cardNumber;
                                this.seriesCode = seriesCode;
                                this.expireDate = expireDate;
                            }

                            /**
                             * Gets the value of the cardHolderName property.
                             *
                             * @return possible object is
                             * {@link String }
                             */
                            public String getCardHolderName() {
                                return cardHolderName;
                            }

                            /**
                             * Sets the value of the cardHolderName property.
                             *
                             * @param value allowed object is
                             *              {@link String }
                             */
                            public void setCardHolderName(String value) {
                                this.cardHolderName = value;
                            }

                            /**
                             * Gets the value of the cardType property.
                             *
                             * @return possible object is
                             * {@link String }
                             */
                            public String getCardType() {
                                return cardType;
                            }

                            /**
                             * Sets the value of the cardType property.
                             *
                             * @param value allowed object is
                             *              {@link String }
                             */
                            public void setCardType(String value) {
                                this.cardType = value;
                            }

                            /**
                             * Gets the value of the cardNumber property.
                             *
                             * @return possible object is
                             * {@link String }
                             */
                            public String getCardNumber() {
                                return cardNumber;
                            }

                            /**
                             * Sets the value of the cardNumber property.
                             *
                             * @param value allowed object is
                             *              {@link String }
                             */
                            public void setCardNumber(String value) {
                                this.cardNumber = value;
                            }

                            /**
                             * Gets the value of the seriesCode property.
                             *
                             * @return possible object is
                             * {@link String }
                             */
                            public String getSeriesCode() {
                                return seriesCode;
                            }

                            /**
                             * Sets the value of the seriesCode property.
                             *
                             * @param value allowed object is
                             *              {@link String }
                             */
                            public void setSeriesCode(String value) {
                                this.seriesCode = value;
                            }

                            /**
                             * Gets the value of the expireDate property.
                             *
                             * @return possible object is
                             * {@link String }
                             */
                            public String getExpireDate() {
                                return expireDate;
                            }

                            /**
                             * Sets the value of the expireDate property.
                             *
                             * @param value allowed object is
                             *              {@link String }
                             */
                            public void setExpireDate(String value) {
                                this.expireDate = value;
                            }

                        }

                    }

                }

                @XmlAccessorType(XmlAccessType.FIELD)
                @XmlType(name = "", propOrder = {
                        "reservationID"
                })
                public static class ReservationIDs {

                    @XmlElement(name = "ReservationID", required = true)
                    protected BookingRetrievalResponse.HotelReservations.HotelReservation.ReservationInfo.ReservationIDs.ReservationID reservationID;

                    public ReservationIDs() {
                        this.reservationID = new ReservationID();
                    }

                    /**
                     * Gets the value of the reservationID property.
                     *
                     * @return possible object is
                     * {@link BookingRetrievalResponse.HotelReservations.HotelReservation.ReservationInfo.ReservationIDs.ReservationID }
                     */
                    public BookingRetrievalResponse.HotelReservations.HotelReservation.ReservationInfo.ReservationIDs.ReservationID getReservationID() {
                        return reservationID;
                    }

                    /**
                     * Sets the value of the reservationID property.
                     *
                     * @param value allowed object is
                     *              {@link BookingRetrievalResponse.HotelReservations.HotelReservation.ReservationInfo.ReservationIDs.ReservationID }
                     */
                    public void setReservationID(BookingRetrievalResponse.HotelReservations.HotelReservation.ReservationInfo.ReservationIDs.ReservationID value) {
                        this.reservationID = value;
                    }


                    public void setReservationID(String value) {
                        this.reservationID.value = value;
                    }

                    @XmlAccessorType(XmlAccessType.FIELD)
                    @XmlType(name = "")
                    public static class ReservationID {

                        @XmlAttribute(name = "Value")
                        protected String value;

                        /**
                         * Gets the value of the value property.
                         *
                         * @return possible object is
                         * {@link String }
                         */
                        public String getValue() {
                            return value;
                        }

                        /**
                         * Sets the value of the value property.
                         *
                         * @param value allowed object is
                         *              {@link String }
                         */
                        public void setValue(String value) {
                            this.value = value;
                        }

                    }

                }

                @XmlAccessorType(XmlAccessType.FIELD)
                @XmlType(name = "")
                public static class Total {

                    @XmlAttribute(name = "AmountAfterTax")
                    protected Float amountAfterTax;
                    @XmlAttribute(name = "CurrencyCode")
                    protected String currencyCode;
                    @XmlAttribute(name = "IsNetRate")
                    protected boolean isNetRate = true;
                    @XmlAttribute(name = "IsGrossRate")
                    protected boolean isGrossRate = false;
                    @XmlAttribute(name = "Commission")
                    protected Float commission;


                    /**
                     * Gets the value of the amountAfterTax property.
                     *
                     * @return possible object is
                     * {@link String }
                     */
                    public Float getAmountAfterTax() {
                        return amountAfterTax;
                    }

                    /**
                     * Sets the value of the amountAfterTax property.
                     *
                     * @param value allowed object is
                     *              {@link String }
                     */
                    public void setAmountAfterTax(Float value) {
                        this.amountAfterTax = value;
                    }

                    /**
                     * Gets the value of the isNetRate property.
                     *
                     * @return possible object is
                     * {@link String }
                     */
                    public boolean getIsNetRate() {
                        return isNetRate;
                    }

                    /**
                     * Sets the value of the isNetRate property.
                     *
                     * @param value allowed object is
                     *              {@link String }
                     */
                    public void setIsNetRate(boolean value) {
                        this.isNetRate = value;
                    }

                    /**
                     * Gets the value of the currencyCode property.
                     *
                     * @return possible object is
                     * {@link String }
                     */
                    public String getCurrencyCode() {
                        return currencyCode;
                    }

                    /**
                     * Sets the value of the currencyCode property.
                     *
                     * @param value allowed object is
                     *              {@link String }
                     */
                    public void setCurrencyCode(String value) {
                        this.currencyCode = value;
                    }

                    /**
                     * Gets the value of the isGrossRate property.
                     *
                     * @return possible object is
                     * {@link String }
                     */
                    public boolean getIsGrossRate() {
                        return isGrossRate;
                    }

                    /**
                     * Sets the value of the isGrossRate property.
                     *
                     * @param value allowed object is
                     *              {@link String }
                     */
                    public void setIsGrossRate(boolean value) {
                        this.isGrossRate = value;
                    }

                    /**
                     * Gets the value of the commission property.
                     *
                     * @return possible object is
                     * {@link String }
                     */
                    public Float getCommission() {
                        return commission;
                    }

                    /**
                     * Sets the value of the commission property.
                     *
                     * @param value allowed object is
                     *              {@link String }
                     */
                    public void setCommission(Float value) {
                        this.commission = value;
                    }

                }

            }

            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                    "roomList"
            })
            public static class Rooms {

                @XmlElement(name = "Room", required = true)
                protected List<BookingRetrievalResponse.HotelReservations.HotelReservation.Rooms.Room> roomList;

                /**
                 * Gets the value of the room property.
                 *
                 * @return possible object is
                 * {@link BookingRetrievalResponse.HotelReservations.HotelReservation.Rooms.Room }
                 */
                public List<BookingRetrievalResponse.HotelReservations.HotelReservation.Rooms.Room> getRoomList() {
                    return roomList;
                }

                /**
                 * Sets the value of the room property.
                 *
                 * @param value allowed object is
                 *              {@link BookingRetrievalResponse.HotelReservations.HotelReservation.Rooms.Room }
                 */
                public void setRoomList(List<BookingRetrievalResponse.HotelReservations.HotelReservation.Rooms.Room> value) {
                    this.roomList = value;
                }


                @XmlAccessorType(XmlAccessType.FIELD)
                @XmlType(name = "", propOrder = {
                        "guests",
                        "pricing"
                })
                public static class Room {

                    @XmlElement(name = "Guests", required = true)
                    protected BookingRetrievalResponse.HotelReservations.HotelReservation.Rooms.Room.Guests guests;
                    @XmlElement(name = "Pricing", required = true)
                    protected BookingRetrievalResponse.HotelReservations.HotelReservation.Rooms.Room.Pricing pricing;

                    @XmlAttribute(name = "CheckinDate", required = true)
                    @XmlSchemaType(name = "date")
                    @XmlJavaTypeAdapter(JaxbDateWithoutTimeSerializer.class)
                    protected Date checkInDate;
                    @XmlAttribute(name = "CheckOutDate", required = true)
                    @XmlSchemaType(name = "date")
                    @XmlJavaTypeAdapter(JaxbDateWithoutTimeSerializer.class)
                    protected Date checkOutDate;
                    @XmlAttribute(name = "RoomCode")
                    protected String roomCode;
                    @XmlAttribute(name = "RoomDescription")
                    protected String roomDescription;
                    @XmlAttribute(name = "MealPlanCode")
                    protected String mealPlanCode;
                    @XmlAttribute(name = "MealPlanDescription")
                    protected String mealPlanDescription;
                    @XmlAttribute(name = "RatePlanCode")
                    protected String ratePlanCode;
                    @XmlAttribute(name = "RatePlanDescription")
                    protected String ratePlanDescription;
                    @XmlAttribute(name = "NumberOfGuests")
                    protected int numberOfGuests;
                    @XmlAttribute(name = "NumberOfAdults")
                    protected int numberOfAdults;
                    @XmlAttribute(name = "NumberOfChildren")
                    protected int numberOfChildren;
                    @XmlAttribute(name = "NumberOfBabies")
                    protected int numberOfBabies;

                    public Room() {
                        this.guests = new Guests();
                        this.pricing = new Pricing();
                    }

                    /**
                     * Gets the value of the guests property.
                     *
                     * @return possible object is
                     * {@link BookingRetrievalResponse.HotelReservations.HotelReservation.Rooms.Room.Guests }
                     */
                    public BookingRetrievalResponse.HotelReservations.HotelReservation.Rooms.Room.Guests getGuests() {
                        return guests;
                    }

                    /**
                     * Sets the value of the guests property.
                     *
                     * @param value allowed object is
                     *              {@link BookingRetrievalResponse.HotelReservations.HotelReservation.Rooms.Room.Guests }
                     */
                    public void setGuests(BookingRetrievalResponse.HotelReservations.HotelReservation.Rooms.Room.Guests value) {
                        this.guests = value;
                    }

                    /**
                     * Gets the value of the pricing property.
                     *
                     * @return possible object is
                     * {@link BookingRetrievalResponse.HotelReservations.HotelReservation.Rooms.Room.Pricing }
                     */
                    public BookingRetrievalResponse.HotelReservations.HotelReservation.Rooms.Room.Pricing getPricing() {
                        return pricing;
                    }

                    /**
                     * Sets the value of the pricing property.
                     *
                     * @param value allowed object is
                     *              {@link BookingRetrievalResponse.HotelReservations.HotelReservation.Rooms.Room.Pricing }
                     */
                    public void setPricing(BookingRetrievalResponse.HotelReservations.HotelReservation.Rooms.Room.Pricing value) {
                        this.pricing = value;
                    }

                    /**
                     * Gets the value of the checkinDate property.
                     *
                     * @return possible object is
                     * {@link String }
                     */
                    public Date getCheckInDate() {
                        return checkInDate;
                    }

                    /**
                     * Sets the value of the checkinDate property.
                     *
                     * @param value allowed object is
                     *              {@link String }
                     */
                    public void setCheckInDate(Date value) {
                        this.checkInDate = value;
                    }

                    /**
                     * Gets the value of the checkOutDate property.
                     *
                     * @return possible object is
                     * {@link String }
                     */
                    public Date getCheckOutDate() {
                        return checkOutDate;
                    }

                    /**
                     * Sets the value of the checkOutDate property.
                     *
                     * @param value allowed object is
                     *              {@link String }
                     */
                    public void setCheckOutDate(Date value) {
                        this.checkOutDate = value;
                    }

                    /**
                     * Gets the value of the roomCode property.
                     *
                     * @return possible object is
                     * {@link String }
                     */
                    public String getRoomCode() {
                        return roomCode;
                    }

                    /**
                     * Sets the value of the roomCode property.
                     *
                     * @param value allowed object is
                     *              {@link String }
                     */
                    public void setRoomCode(String value) {
                        this.roomCode = value;
                    }

                    /**
                     * Gets the value of the roomDescription property.
                     *
                     * @return possible object is
                     * {@link String }
                     */
                    public String getRoomDescription() {
                        return roomDescription;
                    }

                    /**
                     * Sets the value of the roomDescription property.
                     *
                     * @param value allowed object is
                     *              {@link String }
                     */
                    public void setRoomDescription(String value) {
                        this.roomDescription = value;
                    }

                    /**
                     * Gets the value of the mealPlanCode property.
                     *
                     * @return possible object is
                     * {@link String }
                     */
                    public String getMealPlanCode() {
                        return mealPlanCode;
                    }

                    /**
                     * Sets the value of the mealPlanCode property.
                     *
                     * @param value allowed object is
                     *              {@link String }
                     */
                    public void setMealPlanCode(String value) {
                        this.mealPlanCode = value;
                    }

                    /**
                     * Gets the value of the mealPlanDescription property.
                     *
                     * @return possible object is
                     * {@link String }
                     */
                    public String getMealPlanDescription() {
                        return mealPlanDescription;
                    }

                    /**
                     * Sets the value of the mealPlanDescription property.
                     *
                     * @param value allowed object is
                     *              {@link String }
                     */
                    public void setMealPlanDescription(String value) {
                        this.mealPlanDescription = value;
                    }

                    /**
                     * Gets the value of the ratePlanCode property.
                     *
                     * @return possible object is
                     * {@link String }
                     */
                    public String getRatePlanCode() {
                        return ratePlanCode;
                    }

                    /**
                     * Sets the value of the ratePlanCode property.
                     *
                     * @param value allowed object is
                     *              {@link String }
                     */
                    public void setRatePlanCode(String value) {
                        this.ratePlanCode = value;
                    }

                    /**
                     * Gets the value of the ratePlanDescription property.
                     *
                     * @return possible object is
                     * {@link String }
                     */
                    public String getRatePlanDescription() {
                        return ratePlanDescription;
                    }

                    /**
                     * Sets the value of the ratePlanDescription property.
                     *
                     * @param value allowed object is
                     *              {@link String }
                     */
                    public void setRatePlanDescription(String value) {
                        this.ratePlanDescription = value;
                    }

                    /**
                     * Gets the value of the numberOfGuests property.
                     *
                     * @return possible object is
                     * {@link String }
                     */
                    public int getNumberOfGuests() {
                        return numberOfGuests;
                    }

                    /**
                     * Sets the value of the numberOfGuests property.
                     *
                     * @param value allowed object is
                     *              {@link String }
                     */
                    public void setNumberOfGuests(int value) {
                        this.numberOfGuests = value;
                    }

                    /**
                     * Gets the value of the numberOfAdults property.
                     *
                     * @return possible object is
                     * {@link String }
                     */
                    public int getNumberOfAdults() {
                        return numberOfAdults;
                    }

                    /**
                     * Sets the value of the numberOfAdults property.
                     *
                     * @param value allowed object is
                     *              {@link String }
                     */
                    public void setNumberOfAdults(int value) {
                        this.numberOfAdults = value;
                    }

                    /**
                     * Gets the value of the numberOfChildren property.
                     *
                     * @return possible object is
                     * {@link String }
                     */
                    public int getNumberOfChildren() {
                        return numberOfChildren;
                    }

                    /**
                     * Sets the value of the numberOfChildren property.
                     *
                     * @param value allowed object is
                     *              {@link String }
                     */
                    public void setNumberOfChildren(int value) {
                        this.numberOfChildren = value;
                    }

                    /**
                     * Gets the value of the numberOfBabies property.
                     *
                     * @return possible object is
                     * {@link String }
                     */
                    public int getNumberOfBabies() {
                        return numberOfBabies;
                    }

                    /**
                     * Sets the value of the numberOfBabies property.
                     *
                     * @param value allowed object is
                     *              {@link String }
                     */
                    public void setNumberOfBabies(int value) {
                        this.numberOfBabies = value;
                    }


                    public void addGuest(Guests.Guest guest) {
                        if (guests.guestList == null) {
                            guests.guestList = new ArrayList<>();
                        }
                        guests.guestList.add(guest);
                    }


                    public void addDailyRate(Pricing.DailyRates.DailyRate dailyRate) {
                        pricing.addDailyRate(dailyRate);
                    }



                    @XmlAccessorType(XmlAccessType.FIELD)
                    @XmlType(name = "", propOrder = {
                            "guestList"
                    })
                    public static class Guests {

                        @XmlElement(name = "Guest", required = true)
                        protected List<BookingRetrievalResponse.HotelReservations.HotelReservation.Rooms.Room.Guests.Guest> guestList;

                        /**
                         * Gets the value of the guest property.
                         *
                         * @return possible object is
                         * {@link BookingRetrievalResponse.HotelReservations.HotelReservation.Rooms.Room.Guests.Guest }
                         */
                        public List<BookingRetrievalResponse.HotelReservations.HotelReservation.Rooms.Room.Guests.Guest> getGuestList() {
                            return guestList;
                        }

                        /**
                         * Sets the value of the guest property.
                         *
                         * @param value allowed object is
                         *              {@link BookingRetrievalResponse.HotelReservations.HotelReservation.Rooms.Room.Guests.Guest }
                         */
                        public void setGuestList(List<BookingRetrievalResponse.HotelReservations.HotelReservation.Rooms.Room.Guests.Guest> value) {
                            this.guestList = value;
                        }


                        @XmlAccessorType(XmlAccessType.FIELD)
                        @XmlType(name = "", propOrder = {
                        })
                        public static class Guest {

                            @XmlAttribute(name = "Title")
                            protected String title;
                            @XmlAttribute(name = "GivenName")
                            protected String givenName;
                            @XmlAttribute(name = "SurName")
                            protected String surName;
                            @XmlAttribute(name = "Age")
                            protected Integer age;

                            public Guest() {
                            }

                            public Guest(String title, String givenName, String surName, Integer age) {
                                this.title = title;
                                this.givenName = givenName;
                                this.surName = surName;
                                this.age = age;
                            }

                            /**
                             * Gets the value of the title property.
                             *
                             * @return possible object is
                             * {@link String }
                             */
                            public String getTitle() {
                                return title;
                            }

                            /**
                             * Sets the value of the title property.
                             *
                             * @param value allowed object is
                             *              {@link String }
                             */
                            public void setTitle(String value) {
                                this.title = value;
                            }

                            /**
                             * Gets the value of the givenName property.
                             *
                             * @return possible object is
                             * {@link String }
                             */
                            public String getGivenName() {
                                return givenName;
                            }

                            /**
                             * Sets the value of the givenName property.
                             *
                             * @param value allowed object is
                             *              {@link String }
                             */
                            public void setGivenName(String value) {
                                this.givenName = value;
                            }

                            /**
                             * Gets the value of the surName property.
                             *
                             * @return possible object is
                             * {@link String }
                             */
                            public String getSurName() {
                                return surName;
                            }

                            /**
                             * Sets the value of the surName property.
                             *
                             * @param value allowed object is
                             *              {@link String }
                             */
                            public void setSurName(String value) {
                                this.surName = value;
                            }

                            /**
                             * Gets the value of the age property.
                             *
                             * @return possible object is
                             * {@link String }
                             */
                            public Integer getAge() {
                                return age;
                            }

                            /**
                             * Sets the value of the age property.
                             *
                             * @param value allowed object is
                             *              {@link String }
                             */
                            public void setAge(Integer value) {
                                this.age = value;
                            }

                        }

                    }

                    @XmlAccessorType(XmlAccessType.FIELD)
                    @XmlType(name = "", propOrder = {
                            "dailyRates",
                            "roomTotals"
                    })
                    public static class Pricing {

                        @XmlElement(name = "DailyRates", required = true)
                        protected BookingRetrievalResponse.HotelReservations.HotelReservation.Rooms.Room.Pricing.DailyRates dailyRates;
                        @XmlElement(name = "RoomTotals", required = true)
                        protected BookingRetrievalResponse.HotelReservations.HotelReservation.Rooms.Room.Pricing.RoomTotals roomTotals;

                        public Pricing() {
                            this.dailyRates = new DailyRates();
                        }

                        /**
                         * Gets the value of the dailyRates property.
                         *
                         * @return possible object is
                         * {@link BookingRetrievalResponse.HotelReservations.HotelReservation.Rooms.Room.Pricing.DailyRates }
                         */
                        public BookingRetrievalResponse.HotelReservations.HotelReservation.Rooms.Room.Pricing.DailyRates getDailyRates() {
                            return dailyRates;
                        }

                        /**
                         * Sets the value of the dailyRates property.
                         *
                         * @param value allowed object is
                         *              {@link BookingRetrievalResponse.HotelReservations.HotelReservation.Rooms.Room.Pricing.DailyRates }
                         */
                        public void setDailyRates(BookingRetrievalResponse.HotelReservations.HotelReservation.Rooms.Room.Pricing.DailyRates value) {
                            this.dailyRates = value;
                        }

                        /**
                         * Gets the value of the roomTotals property.
                         *
                         * @return possible object is
                         * {@link BookingRetrievalResponse.HotelReservations.HotelReservation.Rooms.Room.Pricing.RoomTotals }
                         */
                        public BookingRetrievalResponse.HotelReservations.HotelReservation.Rooms.Room.Pricing.RoomTotals getRoomTotals() {
                            return roomTotals;
                        }

                        /**
                         * Sets the value of the roomTotals property.
                         *
                         * @param value allowed object is
                         *              {@link BookingRetrievalResponse.HotelReservations.HotelReservation.Rooms.Room.Pricing.RoomTotals }
                         */
                        public void setRoomTotals(BookingRetrievalResponse.HotelReservations.HotelReservation.Rooms.Room.Pricing.RoomTotals value) {
                            this.roomTotals = value;
                        }


                        public void addDailyRate(DailyRates.DailyRate dailyRate) {
                            if (dailyRates.dailyRateList == null) {
                                dailyRates.dailyRateList = new ArrayList<>();
                            }
                            dailyRates.dailyRateList.add(dailyRate);
                        }


                        @XmlAccessorType(XmlAccessType.FIELD)
                        @XmlType(name = "", propOrder = {
                                "dailyRateList"
                        })
                        public static class DailyRates {

                            @XmlElement(name = "DailyRate", required = true)
                            protected List<BookingRetrievalResponse.HotelReservations.HotelReservation.Rooms.Room.Pricing.DailyRates.DailyRate> dailyRateList;

                            /**
                             * Gets the value of the dailyRate property.
                             *
                             * @return possible object is
                             * {@link BookingRetrievalResponse.HotelReservations.HotelReservation.Rooms.Room.Pricing.DailyRates.DailyRate }
                             */
                            public List<BookingRetrievalResponse.HotelReservations.HotelReservation.Rooms.Room.Pricing.DailyRates.DailyRate> getDailyRateList() {
                                return dailyRateList;
                            }

                            /**
                             * Sets the value of the dailyRate property.
                             *
                             * @param value allowed object is
                             *              {@link BookingRetrievalResponse.HotelReservations.HotelReservation.Rooms.Room.Pricing.DailyRates.DailyRate }
                             */
                            public void setDailyRateList(List<BookingRetrievalResponse.HotelReservations.HotelReservation.Rooms.Room.Pricing.DailyRates.DailyRate> value) {
                                this.dailyRateList = value;
                            }

                            @XmlAccessorType(XmlAccessType.FIELD)
                            @XmlType(name = "", propOrder = {
                                    "total"
                            })
                            public static class DailyRate {

                                @XmlElement(name = "Total", required = true)
                                protected BookingRetrievalResponse.HotelReservations.HotelReservation.Rooms.Room.Pricing.DailyRates.DailyRate.Total total;
                                @XmlAttribute(name = "EffectiveDate", required = true)
                                @XmlSchemaType(name = "date")
                                @XmlJavaTypeAdapter(JaxbDateWithoutTimeSerializer.class)
                                protected Date effectiveDate;

                                public DailyRate() {
                                }

                                public DailyRate(Date effectiveDate, Float amountAfterTax, String currencyCode) {
                                    this.total = new Total(amountAfterTax, currencyCode);
                                    this.effectiveDate = effectiveDate;
                                }

                                /**
                                 * Gets the value of the total property.
                                 *
                                 * @return possible object is
                                 * {@link BookingRetrievalResponse.HotelReservations.HotelReservation.Rooms.Room.Pricing.DailyRates.DailyRate.Total }
                                 */
                                public BookingRetrievalResponse.HotelReservations.HotelReservation.Rooms.Room.Pricing.DailyRates.DailyRate.Total getTotal() {
                                    return total;
                                }

                                /**
                                 * Sets the value of the total property.
                                 *
                                 * @param value allowed object is
                                 *              {@link BookingRetrievalResponse.HotelReservations.HotelReservation.Rooms.Room.Pricing.DailyRates.DailyRate.Total }
                                 */
                                public void setTotal(BookingRetrievalResponse.HotelReservations.HotelReservation.Rooms.Room.Pricing.DailyRates.DailyRate.Total value) {
                                    this.total = value;
                                }

                                /**
                                 * Gets the value of the effectiveDate property.
                                 *
                                 * @return possible object is
                                 * {@link String }
                                 */
                                public Date getEffectiveDate() {
                                    return effectiveDate;
                                }

                                /**
                                 * Sets the value of the effectiveDate property.
                                 *
                                 * @param value allowed object is
                                 *              {@link String }
                                 */
                                public void setEffectiveDate(Date value) {
                                    this.effectiveDate = value;
                                }

                                @XmlAccessorType(XmlAccessType.FIELD)
                                @XmlType(name = "", propOrder = {
                                })
                                public static class Total {

                                    @XmlAttribute(name = "AmountAfterTax")
                                    protected Float amountAfterTax;
                                    @XmlAttribute(name = "CurrencyCode")
                                    protected String currencyCode;

                                    public Total() {
                                    }

                                    public Total(Float amountAfterTax, String currencyCode) {
                                        this.amountAfterTax = amountAfterTax;
                                        this.currencyCode = currencyCode;
                                    }

                                    /**
                                     * Gets the value of the amountAfterTax property.
                                     *
                                     * @return possible object is
                                     * {@link String }
                                     */
                                    public Float getAmountAfterTax() {
                                        return amountAfterTax;
                                    }

                                    /**
                                     * Sets the value of the amountAfterTax property.
                                     *
                                     * @param value allowed object is
                                     *              {@link String }
                                     */
                                    public void setAmountAfterTax(Float value) {
                                        this.amountAfterTax = value;
                                    }

                                    /**
                                     * Gets the value of the currencyCode property.
                                     *
                                     * @return possible object is
                                     * {@link String }
                                     */
                                    public String getCurrencyCode() {
                                        return currencyCode;
                                    }

                                    /**
                                     * Sets the value of the currencyCode property.
                                     *
                                     * @param value allowed object is
                                     *              {@link String }
                                     */
                                    public void setCurrencyCode(String value) {
                                        this.currencyCode = value;
                                    }

                                }

                            }

                        }

                        @XmlAccessorType(XmlAccessType.FIELD)
                        @XmlType(name = "", propOrder = {
                                "roomTotal"
                        })
                        public static class RoomTotals {

                            @XmlElement(name = "RoomTotal", required = true)
                            protected BookingRetrievalResponse.HotelReservations.HotelReservation.Rooms.Room.Pricing.RoomTotals.RoomTotal roomTotal;

                            public RoomTotals() {
                            }

                            public RoomTotals(Float amountAfterTax, String currencyCode) {
                                this.roomTotal = new RoomTotal(amountAfterTax, currencyCode);
                            }

                            /**
                             * Gets the value of the roomTotal property.
                             *
                             * @return possible object is
                             * {@link BookingRetrievalResponse.HotelReservations.HotelReservation.Rooms.Room.Pricing.RoomTotals.RoomTotal }
                             */
                            public BookingRetrievalResponse.HotelReservations.HotelReservation.Rooms.Room.Pricing.RoomTotals.RoomTotal getRoomTotal() {
                                return roomTotal;
                            }

                            /**
                             * Sets the value of the roomTotal property.
                             *
                             * @param value allowed object is
                             *              {@link BookingRetrievalResponse.HotelReservations.HotelReservation.Rooms.Room.Pricing.RoomTotals.RoomTotal }
                             */
                            public void setRoomTotal(BookingRetrievalResponse.HotelReservations.HotelReservation.Rooms.Room.Pricing.RoomTotals.RoomTotal value) {
                                this.roomTotal = value;
                            }

                            @XmlAccessorType(XmlAccessType.FIELD)
                            @XmlType(name = "", propOrder = {
                            })
                            public static class RoomTotal {

                                @XmlAttribute(name = "AmountAfterTax")
                                protected Float amountAfterTax;
                                @XmlAttribute(name = "CurrencyCode")
                                protected String currencyCode;
                                @XmlAttribute(name = "IsNetRate")
                                protected boolean isNetRate = true;
                                @XmlAttribute(name = "IsGrossRate")
                                protected boolean isGrossRate = false;
                                @XmlAttribute(name = "Commission")
                                protected Float commission;

                                public RoomTotal() {
                                }

                                public RoomTotal(Float amountAfterTax, String currencyCode) {
                                    this.amountAfterTax = amountAfterTax;
                                    this.currencyCode = currencyCode;
                                }

                                /**
                                 * Gets the value of the amountAfterTax property.
                                 *
                                 * @return possible object is
                                 * {@link String }
                                 */
                                public Float getAmountAfterTax() {
                                    return amountAfterTax;
                                }

                                /**
                                 * Sets the value of the amountAfterTax property.
                                 *
                                 * @param value allowed object is
                                 *              {@link String }
                                 */
                                public void setAmountAfterTax(Float value) {
                                    this.amountAfterTax = value;
                                }

                                /**
                                 * Gets the value of the currencyCode property.
                                 *
                                 * @return possible object is
                                 * {@link String }
                                 */
                                public String getCurrencyCode() {
                                    return currencyCode;
                                }

                                /**
                                 * Sets the value of the currencyCode property.
                                 *
                                 * @param value allowed object is
                                 *              {@link String }
                                 */
                                public void setCurrencyCode(String value) {
                                    this.currencyCode = value;
                                }

                                /**
                                 * Gets the value of the isNetRate property.
                                 *
                                 * @return possible object is
                                 * {@link String }
                                 */
                                public boolean getIsNetRate() {
                                    return isNetRate;
                                }

                                /**
                                 * Sets the value of the isNetRate property.
                                 *
                                 * @param value allowed object is
                                 *              {@link String }
                                 */
                                public void setIsNetRate(boolean value) {
                                    this.isNetRate = value;
                                }

                                /**
                                 * Gets the value of the isGrossRate property.
                                 *
                                 * @return possible object is
                                 * {@link String }
                                 */
                                public boolean getIsGrossRate() {
                                    return isGrossRate;
                                }

                                /**
                                 * Sets the value of the isGrossRate property.
                                 *
                                 * @param value allowed object is
                                 *              {@link String }
                                 */
                                public void setIsGrossRate(boolean value) {
                                    this.isGrossRate = value;
                                }

                                /**
                                 * Gets the value of the commission property.
                                 *
                                 * @return possible object is
                                 * {@link String }
                                 */
                                public Float getCommission() {
                                    return commission;
                                }

                                /**
                                 * Sets the value of the commission property.
                                 *
                                 * @param value allowed object is
                                 *              {@link String }
                                 */
                                public void setCommission(Float value) {
                                    this.commission = value;
                                }

                            }

                        }

                    }

                }

            }

            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                    "specialRequest"
            })
            public static class SpecialRequests {

                @XmlElement(name = "SpecialRequest", required = true)
                protected BookingRetrievalResponse.HotelReservations.HotelReservation.SpecialRequests.SpecialRequest specialRequest;

                public SpecialRequests() {
                    this.specialRequest = new SpecialRequest();
                }

                /**
                 * Gets the value of the specialRequest property.
                 *
                 * @return possible object is
                 * {@link BookingRetrievalResponse.HotelReservations.HotelReservation.SpecialRequests.SpecialRequest }
                 */
                public BookingRetrievalResponse.HotelReservations.HotelReservation.SpecialRequests.SpecialRequest getSpecialRequest() {
                    return specialRequest;
                }

                /**
                 * Sets the value of the specialRequest property.
                 *
                 * @param value allowed object is
                 *              {@link BookingRetrievalResponse.HotelReservations.HotelReservation.SpecialRequests.SpecialRequest }
                 */
                public void setSpecialRequest(BookingRetrievalResponse.HotelReservations.HotelReservation.SpecialRequests.SpecialRequest value) {
                    this.specialRequest = value;
                }


                public void addSpecialRequest(String comments) {
                    if (specialRequest.text == null) {
                        specialRequest.text = new ArrayList<>();
                    }
                    specialRequest.text.add(comments);
                }

                @XmlAccessorType(XmlAccessType.FIELD)
                @XmlType(name = "", propOrder = {
                        "text"
                })
                public static class SpecialRequest {

                    @XmlElement(name = "Text", required = true)
                    protected List<String> text;

                    /**
                     * Gets the value of the text property.
                     *
                     * @return possible object is
                     * {@link String }
                     */
                    public List<String> getText() {
                        return text;
                    }

                    /**
                     * Sets the value of the text property.
                     *
                     * @param value allowed object is
                     *              {@link String }
                     */
                    public void setText(List<String> value) {
                        this.text = value;
                    }

                }

            }

        }

    }

}
