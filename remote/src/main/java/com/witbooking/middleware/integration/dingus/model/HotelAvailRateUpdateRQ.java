/*
 *   HotelAvailRateUpdateRQ.java
 * 
 * Copyright(c) 2015 Witbooking.com. All Rights Reserved.
 * This software is the proprietary information of Witbooking.com
 * 
 */
package com.witbooking.middleware.integration.dingus.model;

import com.google.common.base.Objects;
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
 *         &lt;element name="Credentials">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="User" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="Password" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="HotelAvailRateMessages">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="HotelAvailRateMessage">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="RoomsToSell" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="Release" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="MinimumStay" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="MaximumStay" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="Closed" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="Rates">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="Rate">
 *                                         &lt;complexType>
 *                                           &lt;complexContent>
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                               &lt;sequence>
 *                                                 &lt;element name="MealPlans">
 *                                                   &lt;complexType>
 *                                                     &lt;complexContent>
 *                                                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                                         &lt;sequence>
 *                                                           &lt;element name="MealPlan">
 *                                                             &lt;complexType>
 *                                                               &lt;complexContent>
 *                                                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                                                   &lt;sequence>
 *                                                                     &lt;element name="Price" maxOccurs="unbounded" minOccurs="0">
 *                                                                       &lt;complexType>
 *                                                                         &lt;simpleContent>
 *                                                                           &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *                                                                             &lt;attribute name="PaxType" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                                                             &lt;attribute name="PaxPrice" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                                                           &lt;/extension>
 *                                                                         &lt;/simpleContent>
 *                                                                       &lt;/complexType>
 *                                                                     &lt;/element>
 *                                                                   &lt;/sequence>
 *                                                                   &lt;attribute name="Code" type="{http://www.w3.org/2001/XMLSchema}string" />
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
 *                                               &lt;attribute name="MinStayBasedRate" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                             &lt;/restriction>
 *                                           &lt;/complexContent>
 *                                         &lt;/complexType>
 *                                       &lt;/element>
 *                                     &lt;/sequence>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                           &lt;/sequence>
 *                           &lt;attribute name="CurrencyCode" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="FromDate" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="ToDate" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="RoomCode" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="RatePlanCode" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *                 &lt;attribute name="HotelCode" type="{http://www.w3.org/2001/XMLSchema}string" />
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
        "credentials",
        "hotelAvailRateMessages"
})
@XmlRootElement(name = "HotelAvailRateUpdateRQ")
public class HotelAvailRateUpdateRQ {

    @XmlElement(name = "Credentials", required = true)
    protected HotelAvailRateUpdateRQ.Credentials credentials;
    @XmlElement(name = "HotelAvailRateMessages", required = true)
    protected HotelAvailRateUpdateRQ.HotelAvailRateMessages hotelAvailRateMessages;

    public HotelAvailRateUpdateRQ() {
        this.credentials = new Credentials();
        this.hotelAvailRateMessages = new HotelAvailRateMessages();
    }

    /**
     * Gets the value of the credentials property.
     *
     * @return possible object is
     * {@link HotelAvailRateUpdateRQ.Credentials }
     */
    public HotelAvailRateUpdateRQ.Credentials getCredentials() {
        return credentials;
    }

    /**
     * Sets the value of the credentials property.
     *
     * @param value allowed object is
     *              {@link HotelAvailRateUpdateRQ.Credentials }
     */
    public void setCredentials(HotelAvailRateUpdateRQ.Credentials value) {
        this.credentials = value;
    }

    /**
     * Gets the value of the hotelAvailRateMessages property.
     *
     * @return possible object is
     * {@link HotelAvailRateUpdateRQ.HotelAvailRateMessages }
     */
    public HotelAvailRateUpdateRQ.HotelAvailRateMessages getHotelAvailRateMessages() {
        return hotelAvailRateMessages;
    }

    /**
     * Sets the value of the hotelAvailRateMessages property.
     *
     * @param value allowed object is
     *              {@link HotelAvailRateUpdateRQ.HotelAvailRateMessages }
     */
    public void setHotelAvailRateMessages(HotelAvailRateUpdateRQ.HotelAvailRateMessages value) {
        this.hotelAvailRateMessages = value;
    }


    public void addHotelAvailRateMessage(HotelAvailRateMessages.HotelAvailRateMessage hotelAvailRateMessage) {
        if (hotelAvailRateMessages == null) {
            hotelAvailRateMessages = new HotelAvailRateMessages();
        }
        if (hotelAvailRateMessages.hotelAvailRateMessageList == null) {
            hotelAvailRateMessages.hotelAvailRateMessageList = new ArrayList<>();
        }
        hotelAvailRateMessages.hotelAvailRateMessageList.add(hotelAvailRateMessage);
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "user",
            "password"
    })
    public static class Credentials {

        @XmlElement(name = "User", required = true)
        protected String user;
        @XmlElement(name = "Password", required = true)
        protected String password;

        /**
         * Gets the value of the user property.
         *
         * @return possible object is
         * {@link String }
         */
        public String getUser() {
            return user;
        }

        /**
         * Sets the value of the user property.
         *
         * @param value allowed object is
         *              {@link String }
         */
        public void setUser(String value) {
            this.user = value;
        }

        /**
         * Gets the value of the password property.
         *
         * @return possible object is
         * {@link String }
         */
        public String getPassword() {
            return password;
        }

        /**
         * Sets the value of the password property.
         *
         * @param value allowed object is
         *              {@link String }
         */
        public void setPassword(String value) {
            this.password = value;
        }

        @Override
        public String toString() {
            return Objects.toStringHelper(this)
                    .add("user", user)
                    .add("password", password)
                    .toString();
        }
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "hotelAvailRateMessageList"
    })
    public static class HotelAvailRateMessages {

        @XmlElement(name = "HotelAvailRateMessage", required = true)
        protected List<HotelAvailRateUpdateRQ.HotelAvailRateMessages.HotelAvailRateMessage> hotelAvailRateMessageList;
        @XmlAttribute(name = "HotelCode")
        protected String hotelCode;

        /**
         * Gets the value of the hotelAvailRateMessage property.
         *
         * @return possible object is
         * {@link HotelAvailRateUpdateRQ.HotelAvailRateMessages.HotelAvailRateMessage }
         */
        public List<HotelAvailRateUpdateRQ.HotelAvailRateMessages.HotelAvailRateMessage> getHotelAvailRateMessageList() {
            return hotelAvailRateMessageList;
        }

        /**
         * Sets the value of the hotelAvailRateMessage property.
         *
         * @param value allowed object is
         *              {@link HotelAvailRateUpdateRQ.HotelAvailRateMessages.HotelAvailRateMessage }
         */
        public void setHotelAvailRateMessageList(List<HotelAvailRateUpdateRQ.HotelAvailRateMessages.HotelAvailRateMessage> value) {
            this.hotelAvailRateMessageList = value;
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

        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
                "roomsToSell",
                "release",
                "minimumStay",
                "maximumStay",
                "closed",
                "rates"
        })
        public static class HotelAvailRateMessage {

            @XmlElement(name = "RoomsToSell", required = true)
            protected int roomsToSell;
            @XmlElement(name = "Release", required = true)
            protected int release;
            @XmlElement(name = "MinimumStay", required = true)
            protected int minimumStay;
            @XmlElement(name = "MaximumStay", required = true)
            protected int maximumStay;
            @XmlElement(name = "Closed", required = true)
            protected boolean closed;
            @XmlElement(name = "Rates", required = true)
            protected HotelAvailRateUpdateRQ.HotelAvailRateMessages.HotelAvailRateMessage.Rates rates;
            @XmlAttribute(name = "CurrencyCode")
            protected String currencyCode;
            @XmlAttribute(name = "FromDate", required = true)
            @XmlSchemaType(name = "date")
            @XmlJavaTypeAdapter(JaxbDateWithoutTimeSerializer.class)
            protected Date fromDate;
            @XmlAttribute(name = "ToDate", required = true)
            @XmlSchemaType(name = "date")
            @XmlJavaTypeAdapter(JaxbDateWithoutTimeSerializer.class)
            protected Date toDate;
            @XmlAttribute(name = "RoomCode")
            protected String roomCode;
            @XmlAttribute(name = "RatePlanCode")
            protected String ratePlanCode;

            public HotelAvailRateMessage() {
                this.rates = new Rates();
            }

            /**
             * Gets the value of the roomsToSell property.
             *
             * @return possible object is
             * {@link String }
             */
            public int getRoomsToSell() {
                return roomsToSell;
            }

            /**
             * Sets the value of the roomsToSell property.
             *
             * @param value allowed object is
             *              {@link String }
             */
            public void setRoomsToSell(int value) {
                this.roomsToSell = value;
            }

            /**
             * Gets the value of the release property.
             *
             * @return possible object is
             * {@link String }
             */
            public int getRelease() {
                return release;
            }

            /**
             * Sets the value of the release property.
             *
             * @param value allowed object is
             *              {@link String }
             */
            public void setRelease(int value) {
                this.release = value;
            }

            /**
             * Gets the value of the minimumStay property.
             *
             * @return possible object is
             * {@link String }
             */
            public int getMinimumStay() {
                return minimumStay;
            }

            /**
             * Sets the value of the minimumStay property.
             *
             * @param value allowed object is
             *              {@link String }
             */
            public void setMinimumStay(int value) {
                this.minimumStay = value;
            }

            /**
             * Gets the value of the maximumStay property.
             *
             * @return possible object is
             * {@link String }
             */
            public int getMaximumStay() {
                return maximumStay;
            }

            /**
             * Sets the value of the maximumStay property.
             *
             * @param value allowed object is
             *              {@link String }
             */
            public void setMaximumStay(int value) {
                this.maximumStay = value;
            }

            /**
             * Gets the value of the closed property.
             *
             * @return possible object is
             * {@link String }
             */
            public boolean getClosed() {
                return closed;
            }

            /**
             * Sets the value of the closed property.
             *
             * @param value allowed object is
             *              {@link String }
             */
            public void setClosed(boolean value) {
                this.closed = value;
            }

            /**
             * Gets the value of the rates property.
             *
             * @return possible object is
             * {@link HotelAvailRateUpdateRQ.HotelAvailRateMessages.HotelAvailRateMessage.Rates }
             */
            public HotelAvailRateUpdateRQ.HotelAvailRateMessages.HotelAvailRateMessage.Rates getRates() {
                return rates;
            }

            public void addRate(Rates.Rate rate) {
                if (rates == null) {
                    rates = new Rates();
                }
                if (rates.rateList == null) {
                    rates.rateList = new ArrayList<>();
                }
                rates.rateList.add(rate);
            }

            /**
             * Sets the value of the rates property.
             *
             * @param value allowed object is
             *              {@link HotelAvailRateUpdateRQ.HotelAvailRateMessages.HotelAvailRateMessage.Rates }
             */
            public void setRates(HotelAvailRateUpdateRQ.HotelAvailRateMessages.HotelAvailRateMessage.Rates value) {
                this.rates = value;
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
             * Gets the value of the fromDate property.
             *
             * @return possible object is
             * {@link String }
             */
            public Date getFromDate() {
                return fromDate;
            }

            /**
             * Sets the value of the fromDate property.
             *
             * @param value allowed object is
             *              {@link String }
             */
            public void setFromDate(Date value) {
                this.fromDate = value;
            }

            /**
             * Gets the value of the toDate property.
             *
             * @return possible object is
             * {@link String }
             */
            public Date getToDate() {
                return toDate;
            }

            /**
             * Sets the value of the toDate property.
             *
             * @param value allowed object is
             *              {@link String }
             */
            public void setToDate(Date value) {
                this.toDate = value;
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

            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                    "rateList"
            })
            public static class Rates {

                @XmlElement(name = "Rate", required = true)
                protected List<HotelAvailRateUpdateRQ.HotelAvailRateMessages.HotelAvailRateMessage.Rates.Rate> rateList;

                /**
                 * Gets the value of the rate property.
                 *
                 * @return possible object is
                 * {@link HotelAvailRateUpdateRQ.HotelAvailRateMessages.HotelAvailRateMessage.Rates.Rate }
                 */
                public List<HotelAvailRateUpdateRQ.HotelAvailRateMessages.HotelAvailRateMessage.Rates.Rate> getRateList() {
                    return rateList;
                }

                /**
                 * Sets the value of the rate property.
                 *
                 * @param value allowed object is
                 *              {@link HotelAvailRateUpdateRQ.HotelAvailRateMessages.HotelAvailRateMessage.Rates.Rate }
                 */
                public void setRateList(List<HotelAvailRateUpdateRQ.HotelAvailRateMessages.HotelAvailRateMessage.Rates.Rate> value) {
                    this.rateList = value;
                }

                @XmlAccessorType(XmlAccessType.FIELD)
                @XmlType(name = "", propOrder = {
                        "mealPlans"
                })
                public static class Rate {

                    @XmlElement(name = "MealPlans", required = true)
                    protected HotelAvailRateUpdateRQ.HotelAvailRateMessages.HotelAvailRateMessage.Rates.Rate.MealPlans mealPlans;
                    @XmlAttribute(name = "MinStayBasedRate")
                    protected int minStayBasedRate = 1;

                    public Rate() {
                        this.mealPlans = new MealPlans();
                    }

                    /**
                     * Gets the value of the mealPlans property.
                     *
                     * @return possible object is
                     * {@link HotelAvailRateUpdateRQ.HotelAvailRateMessages.HotelAvailRateMessage.Rates.Rate.MealPlans }
                     */
                    public HotelAvailRateUpdateRQ.HotelAvailRateMessages.HotelAvailRateMessage.Rates.Rate.MealPlans getMealPlans() {
                        return mealPlans;
                    }

                    /**
                     * Sets the value of the mealPlans property.
                     *
                     * @param value allowed object is
                     *              {@link HotelAvailRateUpdateRQ.HotelAvailRateMessages.HotelAvailRateMessage.Rates.Rate.MealPlans }
                     */
                    public void setMealPlans(HotelAvailRateUpdateRQ.HotelAvailRateMessages.HotelAvailRateMessage.Rates.Rate.MealPlans value) {
                        this.mealPlans = value;
                    }

                    /**
                     * Gets the value of the minStayBasedRate property.
                     *
                     * @return possible object is
                     * {@link String }
                     */
                    public int getMinStayBasedRate() {
                        return minStayBasedRate;
                    }

                    /**
                     * Sets the value of the minStayBasedRate property.
                     *
                     * @param value allowed object is
                     *              {@link String }
                     */
                    public void setMinStayBasedRate(int value) {
                        this.minStayBasedRate = value;
                    }


                    public void addMealPlan(MealPlans.MealPlan mealPlan) {
                        if (mealPlans == null) {
                            mealPlans = new MealPlans();
                        }
                        if (mealPlans.mealPlanList == null) {
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
                        protected List<HotelAvailRateUpdateRQ.HotelAvailRateMessages.HotelAvailRateMessage.Rates.Rate.MealPlans.MealPlan> mealPlanList;

                        /**
                         * Gets the value of the mealPlan property.
                         *
                         * @return possible object is
                         * {@link HotelAvailRateUpdateRQ.HotelAvailRateMessages.HotelAvailRateMessage.Rates.Rate.MealPlans.MealPlan }
                         */
                        public List<HotelAvailRateUpdateRQ.HotelAvailRateMessages.HotelAvailRateMessage.Rates.Rate.MealPlans.MealPlan> getMealPlanList() {
                            return mealPlanList;
                        }

                        /**
                         * Sets the value of the mealPlan property.
                         *
                         * @param value allowed object is
                         *              {@link HotelAvailRateUpdateRQ.HotelAvailRateMessages.HotelAvailRateMessage.Rates.Rate.MealPlans.MealPlan }
                         */
                        public void setMealPlanList(List<HotelAvailRateUpdateRQ.HotelAvailRateMessages.HotelAvailRateMessage.Rates.Rate.MealPlans.MealPlan> value) {
                            this.mealPlanList = value;
                        }

                        @XmlAccessorType(XmlAccessType.FIELD)
                        @XmlType(name = "", propOrder = {
                                "priceList"
                        })
                        public static class MealPlan {

                            @XmlElement(name = "Price")
                            protected List<HotelAvailRateUpdateRQ.HotelAvailRateMessages.HotelAvailRateMessage.Rates.Rate.MealPlans.MealPlan.Price> priceList;
                            @XmlAttribute(name = "Code")
                            protected String code;

                            /**
                             * Gets the value of the price property.
                             * <p/>
                             * <p/>
                             * This accessor method returns a reference to the live list,
                             * not a snapshot. Therefore any modification you make to the
                             * returned list will be present inside the JAXB object.
                             * This is why there is not a <CODE>set</CODE> method for the price property.
                             * <p/>
                             * <p/>
                             * For example, to add a new item, do as follows:
                             * <pre>
                             *    getPrice().add(newItem);
                             * </pre>
                             * <p/>
                             * <p/>
                             * <p/>
                             * Objects of the following type(s) are allowed in the list
                             * {@link HotelAvailRateUpdateRQ.HotelAvailRateMessages.HotelAvailRateMessage.Rates.Rate.MealPlans.MealPlan.Price }
                             */
                            public List<Price> getPriceList() {
                                if (priceList == null) {
                                    priceList = new ArrayList<>();
                                }
                                return this.priceList;
                            }

                            public void setPriceList(List<Price> priceList) {
                                this.priceList = priceList;
                            }


                            public void addPrice(Price price) {
                                if (priceList == null) {
                                    priceList = new ArrayList<>();
                                }
                                priceList.add(price);
                            }


                            public float getPrice(String type) {
                                if (priceList == null || priceList.isEmpty()) {
                                    return 0;
                                }
                                for (Price price : priceList) {
                                    if (price.getPaxType().equals(type))
                                        return price.getPaxPrice();
                                }
                                return 0;
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

                            @XmlAccessorType(XmlAccessType.FIELD)
                            @XmlType(name = "", propOrder = {})
                            public static class Price {

                                @XmlAttribute(name = "PaxType")
                                protected String paxType;
                                @XmlAttribute(name = "PaxPrice")
                                protected Float paxPrice;

                                /**
                                 * Gets the value of the paxType property.
                                 *
                                 * @return possible object is
                                 * {@link String }
                                 */
                                public String getPaxType() {
                                    return paxType;
                                }

                                /**
                                 * Sets the value of the paxType property.
                                 *
                                 * @param value allowed object is
                                 *              {@link String }
                                 */
                                public void setPaxType(String value) {
                                    this.paxType = value;
                                }

                                /**
                                 * Gets the value of the paxPrice property.
                                 *
                                 * @return possible object is
                                 * {@link String }
                                 */
                                public Float getPaxPrice() {
                                    return paxPrice;
                                }

                                /**
                                 * Sets the value of the paxPrice property.
                                 *
                                 * @param value allowed object is
                                 *              {@link String }
                                 */
                                public void setPaxPrice(Float value) {
                                    this.paxPrice = value;
                                }

                            }

                        }

                    }

                }

            }

        }

    }

}
