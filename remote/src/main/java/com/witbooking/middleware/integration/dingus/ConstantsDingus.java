/*
 *   ConstantsSiteMinder.java
 * 
 * Copyright(c) 2013 Witbooking.com. All Rights Reserved.
 * This software is the proprietary information of Witbooking.com
 * 
 */
package com.witbooking.middleware.integration.dingus;

import com.witbooking.middleware.model.Reservation;
import com.witbooking.middleware.model.RoomStay;

/**
 * Insert description here
 *
 * @author Christian Delgado
 * @version 1.0
 * @date 07/10/13
 */
public final class ConstantsDingus {

//    public static final String REQUESTOR_CHANNEL_ID = "WTB";
//    public static final String TYPE_RESERVATION = "22";
//    public static final String CHANNEL_NAME = "Witbooking";

    public static final class Error {
        public static final String INVALID_HOTEL_CODE = "Hotel code not found";
        public static final String AUTHENTICATION = "Wrong username or password";
        public static final String INVALID_ROOM_RATE = "Invalid Room or Rate Code";
    }

    public static final class PriceType {
        public static final String PERSON = "Person";
        public static final String UNIT = "Unit";
        public static final String OCCUPANCY = "Occupancy";
    }

    public static final class RateType {
        public static final String GROSS = "Gross";
        public static final String NET = "Net";
    }

    public static final class Price {
        public static final String ADULT = "Adult";
        public static final String SINGLE_USE = "SingleUse";
        public static final String THIRD_ADULT = "ThirdAdult";
        public static final String FIRST_CHILD_A = "FirstChildA";
        public static final String FIRST_CHILD_B = "FirstChildB";
        public static final String SECOND_CHILD_A = "SecondChildA";
        public static final String SECOND_CHILD_B = "SecondChildB";
    }

    public static final class MealPlan {
        public static final String ROOM_ONLY = "RO";
        public static final String BED_AND_BREAKFAST = "BB";
        public static final String HALF_BOARD = "HB";
        public static final String FULL_BOARD = "FB";
        public static final String ALL_INCLUSIVE = "AI";

        public static String getCodeFromTicker(String mealPlanTicker) {
            if (mealPlanTicker == null)
                return ConstantsDingus.MealPlan.ROOM_ONLY;
            switch (mealPlanTicker) {
                case com.witbooking.middleware.model.MealPlan.ROOM_ONLY:
                    return ConstantsDingus.MealPlan.ROOM_ONLY;
                case com.witbooking.middleware.model.MealPlan.BED_AND_BREAKFAST:
                    return ConstantsDingus.MealPlan.BED_AND_BREAKFAST;
                case com.witbooking.middleware.model.MealPlan.HALF_BOARD:
                    return ConstantsDingus.MealPlan.HALF_BOARD;
                case com.witbooking.middleware.model.MealPlan.FULL_BOARD:
                    return ConstantsDingus.MealPlan.FULL_BOARD;
                case com.witbooking.middleware.model.MealPlan.ALL_INCLUSIVE:
                    return ConstantsDingus.MealPlan.ALL_INCLUSIVE;
                default:
                    return ConstantsDingus.MealPlan.ROOM_ONLY;
            }
        }

        public static String getTickerFromCode(String mealPlanCode) {
            if (mealPlanCode == null)
                return "";
            switch (mealPlanCode) {
                case ConstantsDingus.MealPlan.ROOM_ONLY:
                    return com.witbooking.middleware.model.MealPlan.ROOM_ONLY;
                case ConstantsDingus.MealPlan.BED_AND_BREAKFAST:
                    return com.witbooking.middleware.model.MealPlan.BED_AND_BREAKFAST;
                case ConstantsDingus.MealPlan.HALF_BOARD:
                    return com.witbooking.middleware.model.MealPlan.HALF_BOARD;
                case ConstantsDingus.MealPlan.FULL_BOARD:
                    return com.witbooking.middleware.model.MealPlan.FULL_BOARD;
                case ConstantsDingus.MealPlan.ALL_INCLUSIVE:
                    return com.witbooking.middleware.model.MealPlan.ALL_INCLUSIVE;
                default:
                    return "";
            }
        }
    }

    public static final class HotelReservation {
        public static final String RESERVED = "Reserved";
        public static final String AMENDED = "Amended";
        public static final String CANCELLED = "Cancelled";

        //AdditionalInfo
        public static final String DISCOUNT_CODE = "Discount";
        public static final String SERVICE_CODE = "Service";

        public static String getCodeFromReservation(Reservation reservation) {
            String statusCode = RESERVED;
            int cancelled = 0;
            for (RoomStay roomStay : reservation.getRoomStays()) {
                if (roomStay.getDateModification() != null || roomStay.getCancellationDate() != null)
                    statusCode = AMENDED;
                if (roomStay.getStatus() == Reservation.ReservationStatus.CANCEL)
                    cancelled++;
            }
            if (reservation.getRoomStays().size() == cancelled)
                statusCode = CANCELLED;
            return statusCode;
        }
    }
}
