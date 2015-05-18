/*
 *   GetHotelInfoResponse.java
 *
 * Copyright(c) 2015 Witbooking.com. All Rights Reserved.
 * This software is the proprietary information of Witbooking.com
 *
 */
package com.witbooking.middleware.integration.dingus.model;


import com.witbooking.middleware.integration.dingus.ConstantsDingus;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.ArrayList;
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
 *         &lt;element name="Hotel">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="Rooms">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="Room">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="Occupations">
 *                                         &lt;complexType>
 *                                           &lt;complexContent>
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                               &lt;sequence>
 *                                                 &lt;element name="MinNumberOfPersons" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                                 &lt;element name="MaxNumberOfPersons" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                                 &lt;element name="MinNumberOfAdults" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                                 &lt;element name="MaxNumberOfAdults" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                                 &lt;element name="MinNumberOfChildren" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                                 &lt;element name="MaxNumberOfChildren" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                               &lt;/sequence>
 *                                             &lt;/restriction>
 *                                           &lt;/complexContent>
 *                                         &lt;/complexType>
 *                                       &lt;/element>
 *                                       &lt;element name="RatePlans">
 *                                         &lt;complexType>
 *                                           &lt;complexContent>
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                               &lt;sequence>
 *                                                 &lt;element name="RatePlan">
 *                                                   &lt;complexType>
 *                                                     &lt;simpleContent>
 *                                                       &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *                                                         &lt;attribute name="Code" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                                         &lt;attribute name="Description" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                                         &lt;attribute name="CurrencyCode" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                                         &lt;attribute name="RateType" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                                       &lt;/extension>
 *                                                     &lt;/simpleContent>
 *                                                   &lt;/complexType>
 *                                                 &lt;/element>
 *                                               &lt;/sequence>
 *                                             &lt;/restriction>
 *                                           &lt;/complexContent>
 *                                         &lt;/complexType>
 *                                       &lt;/element>
 *                                       &lt;element name="MealPlans">
 *                                         &lt;complexType>
 *                                           &lt;complexContent>
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                               &lt;sequence>
 *                                                 &lt;element name="MealPlan">
 *                                                   &lt;complexType>
 *                                                     &lt;simpleContent>
 *                                                       &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *                                                         &lt;attribute name="Code" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                                         &lt;attribute name="Description" type="{http://www.w3.org/2001/XMLSchema}string" />
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
 *                                     &lt;attribute name="Code" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                     &lt;attribute name="Description" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                     &lt;attribute name="PriceType" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *                 &lt;attribute name="Code" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="Name" type="{http://www.w3.org/2001/XMLSchema}string" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "hotelList",
        "errors"
})
@XmlRootElement(name = "GetHotelInfoResponse")
public class GetHotelInfoResponse implements Serializable {

    @XmlElement(name = "Hotel", required = true)
    protected List<GetHotelInfoResponse.Hotel> hotelList;
    @XmlElementWrapper(name = "Errors", namespace = "http://www.opentravel.org/OTA/2003/05")
    @XmlElement(name = "Error", namespace = "http://www.opentravel.org/OTA/2003/05")
    private List<ErrorType> errors;

    /**
     * Gets the value of the hotel property.
     *
     * @return possible object is
     * {@link GetHotelInfoResponse.Hotel }
     */
    public List<GetHotelInfoResponse.Hotel> getHotelList() {
        return hotelList;
    }

    /**
     * Sets the value of the hotel property.
     *
     * @param value allowed object is
     *              {@link GetHotelInfoResponse.Hotel }
     */
    public void setHotelList(List<GetHotelInfoResponse.Hotel> value) {
        this.hotelList = value;
    }

    public void addHotel(Hotel hotel) {
        if (hotelList == null) {
            hotelList = new ArrayList<>();
        }
        hotelList.add(hotel);
    }

    /**
     * Gets the value of the errors property.
     *
     * @return possible object is
     * {@link ErrorType }
     */
    public List<ErrorType> getErrors() {
        return errors;
    }

    /**
     * Sets the value of the errors property.
     *
     * @param value allowed object is
     *              {@link ErrorType }
     */
    public void setErrors(List<ErrorType> value) {
        this.errors = value;
    }

    public void addErrorMessage(String errorMessage) {
        if (errors == null) {
            errors = new ArrayList<>();
        }
        errors.add(new ErrorType(errorMessage));
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "rooms"
    })
    @XmlRootElement(name = "Hotel")
    public static class Hotel {

        @XmlElement(name = "Rooms", required = true)
        protected GetHotelInfoResponse.Hotel.Rooms rooms;
        @XmlAttribute(name = "Code", required = true)
        protected String code;
        @XmlAttribute(name = "Name", required = true)
        protected String name;

        public Hotel() {
            rooms = new Rooms();
        }

        /**
         * Gets the value of the rooms property.
         *
         * @return possible object is
         * {@link GetHotelInfoResponse.Hotel.Rooms }
         */
        public GetHotelInfoResponse.Hotel.Rooms getRooms() {
            return rooms;
        }

        /**
         * Sets the value of the rooms property.
         *
         * @param value allowed object is
         *              {@link GetHotelInfoResponse.Hotel.Rooms }
         */
        public void setRooms(GetHotelInfoResponse.Hotel.Rooms value) {
            this.rooms = value;
        }

        public void addRoom(GetHotelInfoResponse.Hotel.Rooms.Room room) {
            if (rooms == null) {
                rooms = new Rooms();
            }
            if (rooms.getRoomList() == null) {
                rooms.roomList = new ArrayList<>();
            }
            rooms.roomList.add(room);
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

        /**
         * Gets the value of the name property.
         *
         * @return possible object is
         * {@link String }
         */
        public String getName() {
            return name;
        }

        /**
         * Sets the value of the name property.
         *
         * @param value allowed object is
         *              {@link String }
         */
        public void setName(String value) {
            this.name = value;
        }

        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
                "roomList"
        })
        public static class Rooms {

            @XmlElement(name = "Room", required = true)
            protected List<GetHotelInfoResponse.Hotel.Rooms.Room> roomList;

            /**
             * Gets the value of the room property.
             *
             * @return possible object is
             * {@link GetHotelInfoResponse.Hotel.Rooms.Room }
             */
            public List<GetHotelInfoResponse.Hotel.Rooms.Room> getRoomList() {
                return roomList;
            }

            public Room getRoomByCode(String code) {
                if (roomList == null || code == null)
                    return null;
                for (Room room : roomList) {
                    if (room.getCode().equals(code))
                        return room;
                }
                return null;
            }

            /**
             * Sets the value of the room property.
             *
             * @param value allowed object is
             *              {@link GetHotelInfoResponse.Hotel.Rooms.Room }
             */
            public void setRoomList(List<GetHotelInfoResponse.Hotel.Rooms.Room> value) {
                this.roomList = value;
            }

            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                    "occupations",
                    "ratePlans",
                    "mealPlans"
            })
            public static class Room {

                @XmlElement(name = "Occupations", required = true)
                protected GetHotelInfoResponse.Hotel.Rooms.Room.Occupations occupations;
                @XmlElement(name = "RatePlans", required = true)
                protected GetHotelInfoResponse.Hotel.Rooms.Room.RatePlans ratePlans;
                @XmlElement(name = "MealPlans", required = true)
                protected GetHotelInfoResponse.Hotel.Rooms.Room.MealPlans mealPlans;
                @XmlAttribute(name = "Code")
                protected String code;
                @XmlAttribute(name = "Description")
                protected String description;
                @XmlAttribute(name = "PriceType")
                protected String priceType = ConstantsDingus.PriceType.PERSON;

                public Room() {
                    occupations = new Occupations();
                    ratePlans = new RatePlans();
                    mealPlans = new MealPlans();
                }

                /**
                 * Gets the value of the occupations property.
                 *
                 * @return possible object is
                 * {@link GetHotelInfoResponse.Hotel.Rooms.Room.Occupations }
                 */
                public GetHotelInfoResponse.Hotel.Rooms.Room.Occupations getOccupations() {
                    return occupations;
                }

                /**
                 * Sets the value of the occupations property.
                 *
                 * @param value allowed object is
                 *              {@link GetHotelInfoResponse.Hotel.Rooms.Room.Occupations }
                 */
                public void setOccupations(GetHotelInfoResponse.Hotel.Rooms.Room.Occupations value) {
                    this.occupations = value;
                }

                /**
                 * Gets the value of the ratePlans property.
                 *
                 * @return possible object is
                 * {@link GetHotelInfoResponse.Hotel.Rooms.Room.RatePlans }
                 */
                public GetHotelInfoResponse.Hotel.Rooms.Room.RatePlans getRatePlans() {
                    return ratePlans;
                }

                /**
                 * Sets the value of the ratePlans property.
                 *
                 * @param value allowed object is
                 *              {@link GetHotelInfoResponse.Hotel.Rooms.Room.RatePlans }
                 */
                public void setRatePlans(GetHotelInfoResponse.Hotel.Rooms.Room.RatePlans value) {
                    this.ratePlans = value;
                }

                /**
                 * Gets the value of the mealPlans property.
                 *
                 * @return possible object is
                 * {@link GetHotelInfoResponse.Hotel.Rooms.Room.MealPlans }
                 */
                public GetHotelInfoResponse.Hotel.Rooms.Room.MealPlans getMealPlans() {
                    return mealPlans;
                }

                /**
                 * Sets the value of the mealPlans property.
                 *
                 * @param value allowed object is
                 *              {@link GetHotelInfoResponse.Hotel.Rooms.Room.MealPlans }
                 */
                public void setMealPlans(GetHotelInfoResponse.Hotel.Rooms.Room.MealPlans value) {
                    this.mealPlans = value;
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

                /**
                 * Gets the value of the description property.
                 *
                 * @return possible object is
                 * {@link String }
                 */
                public String getDescription() {
                    return description;
                }

                /**
                 * Sets the value of the description property.
                 *
                 * @param value allowed object is
                 *              {@link String }
                 */
                public void setDescription(String value) {
                    this.description = value;
                }

                /**
                 * Gets the value of the priceType property.
                 *
                 * @return possible object is
                 * {@link String }
                 */
                public String getPriceType() {
                    return priceType;
                }

                /**
                 * Sets the value of the priceType property.
                 *
                 * @param value allowed object is
                 *              {@link String }
                 */
                public void setPriceType(String value) {
                    this.priceType = value;
                }


                public void addRatePlan(RatePlans.RatePlan ratePlan) {
                    if (ratePlans.getRatePlanList() == null) {
                        ratePlans.ratePlanList = new ArrayList<>();
                    }
                    ratePlans.ratePlanList.add(ratePlan);
                }

                public void addMealPlan(MealPlans.MealPlan mealPlan) {
                    if (mealPlans.getMealPlanList() == null) {
                        mealPlans.mealPlanList = new ArrayList<>();
                    }
                    mealPlans.mealPlanList.add(mealPlan);
                }


                @XmlAccessorType(XmlAccessType.FIELD)
                @XmlType(name = "", propOrder = {
                        "mealPlanList"
                })
                public static class MealPlans {

                    @XmlElement(name = "MealPlan", required = true)
                    protected List<GetHotelInfoResponse.Hotel.Rooms.Room.MealPlans.MealPlan> mealPlanList;

                    /**
                     * Gets the value of the mealPlan property.
                     *
                     * @return possible object is
                     * {@link GetHotelInfoResponse.Hotel.Rooms.Room.MealPlans.MealPlan }
                     */
                    public List<GetHotelInfoResponse.Hotel.Rooms.Room.MealPlans.MealPlan> getMealPlanList() {
                        return mealPlanList;
                    }

                    /**
                     * Sets the value of the mealPlan property.
                     *
                     * @param value allowed object is
                     *              {@link GetHotelInfoResponse.Hotel.Rooms.Room.MealPlans.MealPlan }
                     */
                    public void setMealPlanList(List<GetHotelInfoResponse.Hotel.Rooms.Room.MealPlans.MealPlan> value) {
                        this.mealPlanList = value;
                    }

                    public MealPlan getMealPlanByCode(String code) {
                        if (mealPlanList == null || code == null)
                            return null;
                        for (MealPlans.MealPlan plan : mealPlanList) {
                            if (plan.getCode().equals(code))
                                return plan;
                        }
                        return null;
                    }

                    @XmlAccessorType(XmlAccessType.FIELD)
                    @XmlType(name = "", propOrder = {
                    })
                    public static class MealPlan {

                        @XmlAttribute(name = "Code")
                        protected String code;
                        @XmlAttribute(name = "Description")
                        protected String description;

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


                        /**
                         * Gets the value of the description property.
                         *
                         * @return possible object is
                         * {@link String }
                         */
                        public String getDescription() {
                            return description;
                        }

                        /**
                         * Sets the value of the description property.
                         *
                         * @param value allowed object is
                         *              {@link String }
                         */
                        public void setDescription(String value) {
                            this.description = value;
                        }

                    }

                }

                @XmlAccessorType(XmlAccessType.FIELD)
                @XmlType(name = "", propOrder = {
                        "minNumberOfPersons",
                        "maxNumberOfPersons",
                        "minNumberOfAdults",
                        "maxNumberOfAdults",
                        "minNumberOfChildren",
                        "maxNumberOfChildren"
                })
                public static class Occupations {

                    @XmlElement(name = "MinNumberOfPersons", required = true)
                    protected Integer minNumberOfPersons;
                    @XmlElement(name = "MaxNumberOfPersons", required = true)
                    protected Integer maxNumberOfPersons;
                    @XmlElement(name = "MinNumberOfAdults", required = true)
                    protected Integer minNumberOfAdults;
                    @XmlElement(name = "MaxNumberOfAdults", required = true)
                    protected Integer maxNumberOfAdults;
                    @XmlElement(name = "MinNumberOfChildren", required = true)
                    protected Integer minNumberOfChildren;
                    @XmlElement(name = "MaxNumberOfChildren", required = true)
                    protected Integer maxNumberOfChildren;

                    /**
                     * Gets the value of the minNumberOfPersons property.
                     *
                     * @return possible object is
                     * {@link String }
                     */
                    public Integer getMinNumberOfPersons() {
                        return minNumberOfPersons;
                    }

                    /**
                     * Sets the value of the minNumberOfPersons property.
                     *
                     * @param value allowed object is
                     *              {@link String }
                     */
                    public void setMinNumberOfPersons(int value) {
                        this.minNumberOfPersons = value;
                    }

                    /**
                     * Gets the value of the maxNumberOfPersons property.
                     *
                     * @return possible object is
                     * {@link String }
                     */
                    public Integer getMaxNumberOfPersons() {
                        return maxNumberOfPersons;
                    }

                    /**
                     * Sets the value of the maxNumberOfPersons property.
                     *
                     * @param value allowed object is
                     *              {@link String }
                     */
                    public void setMaxNumberOfPersons(int value) {
                        this.maxNumberOfPersons = value;
                    }

                    /**
                     * Gets the value of the minNumberOfAdults property.
                     *
                     * @return possible object is
                     * {@link String }
                     */
                    public Integer getMinNumberOfAdults() {
                        return minNumberOfAdults;
                    }

                    /**
                     * Sets the value of the minNumberOfAdults property.
                     *
                     * @param value allowed object is
                     *              {@link String }
                     */
                    public void setMinNumberOfAdults(int value) {
                        this.minNumberOfAdults = value;
                    }

                    /**
                     * Gets the value of the maxNumberOfAdults property.
                     *
                     * @return possible object is
                     * {@link String }
                     */
                    public Integer getMaxNumberOfAdults() {
                        return maxNumberOfAdults;
                    }

                    /**
                     * Sets the value of the maxNumberOfAdults property.
                     *
                     * @param value allowed object is
                     *              {@link String }
                     */
                    public void setMaxNumberOfAdults(int value) {
                        this.maxNumberOfAdults = value;
                    }

                    /**
                     * Gets the value of the minNumberOfChildren property.
                     *
                     * @return possible object is
                     * {@link String }
                     */
                    public Integer getMinNumberOfChildren() {
                        return minNumberOfChildren;
                    }

                    /**
                     * Sets the value of the minNumberOfChildren property.
                     *
                     * @param value allowed object is
                     *              {@link String }
                     */
                    public void setMinNumberOfChildren(int value) {
                        this.minNumberOfChildren = value;
                    }

                    /**
                     * Gets the value of the maxNumberOfChildren property.
                     *
                     * @return possible object is
                     * {@link String }
                     */
                    public Integer getMaxNumberOfChildren() {
                        return maxNumberOfChildren;
                    }

                    /**
                     * Sets the value of the maxNumberOfChildren property.
                     *
                     * @param value allowed object is
                     *              {@link String }
                     */
                    public void setMaxNumberOfChildren(int value) {
                        this.maxNumberOfChildren = value;
                    }

                }

                @XmlAccessorType(XmlAccessType.FIELD)
                @XmlType(name = "", propOrder = {
                        "ratePlanList"
                })
                public static class RatePlans {

                    @XmlElement(name = "RatePlan", required = true)
                    protected List<GetHotelInfoResponse.Hotel.Rooms.Room.RatePlans.RatePlan> ratePlanList;

                    /**
                     * Gets the value of the ratePlan property.
                     *
                     * @return possible object is
                     * {@link GetHotelInfoResponse.Hotel.Rooms.Room.RatePlans.RatePlan }
                     */
                    public List<GetHotelInfoResponse.Hotel.Rooms.Room.RatePlans.RatePlan> getRatePlanList() {
                        return ratePlanList;
                    }


                    public RatePlan getRatePlanByCode(String code) {
                        if (ratePlanList == null || code == null)
                            return null;
                        for (RatePlan plan : ratePlanList) {
                            if (plan.getCode().equals(code))
                                return plan;
                        }
                        return null;
                    }

                    /**
                     * Sets the value of the ratePlan property.
                     *
                     * @param value allowed object is
                     *              {@link GetHotelInfoResponse.Hotel.Rooms.Room.RatePlans.RatePlan }
                     */
                    public void setRatePlanList(List<GetHotelInfoResponse.Hotel.Rooms.Room.RatePlans.RatePlan> value) {
                        this.ratePlanList = value;
                    }

                    @XmlAccessorType(XmlAccessType.FIELD)
                    @XmlType(name = "", propOrder = {
                    })
                    public static class RatePlan {
                        @XmlAttribute(name = "Code")
                        protected String code;
                        @XmlAttribute(name = "Description")
                        protected String description;
                        @XmlAttribute(name = "CurrencyCode")
                        protected String currencyCode;
                        @XmlAttribute(name = "RateType")
                        protected String rateType = ConstantsDingus.RateType.NET;

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

                        /**
                         * Gets the value of the description property.
                         *
                         * @return possible object is
                         * {@link String }
                         */
                        public String getDescription() {
                            return description;
                        }

                        /**
                         * Sets the value of the description property.
                         *
                         * @param value allowed object is
                         *              {@link String }
                         */
                        public void setDescription(String value) {
                            this.description = value;
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
                         * Gets the value of the rateType property.
                         *
                         * @return possible object is
                         * {@link String }
                         */
                        public String getRateType() {
                            return rateType;
                        }

                        /**
                         * Sets the value of the rateType property.
                         *
                         * @param value allowed object is
                         *              {@link String }
                         */
                        public void setRateType(String value) {
                            this.rateType = value;
                        }

                    }
                }
            }
        }
    }
}
