package com.witbooking.middleware.integration.tripadvisor.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * ReviewExpress.java
 * User: jose
 * Date: 10/24/13
 * Time: 1:02 PM
 */
public class ReviewExpress {

    public static class CreateReviewExpressRQ implements Serializable {
        /**
         * HotelTicker
         */
        protected String location_id;
        /**
         * email
         */
        protected String recipient;
        protected Date checkin;
        protected Date checkout;
        protected String language;
        protected String country;

        public CreateReviewExpressRQ(String location_id, String recipient, Date checkIn, Date checkOut, String language, String country) {
            this.location_id = location_id;
            this.recipient = recipient;
            this.checkin = checkIn;
            this.checkout = checkOut;
            this.language = language;
            this.country = country;
        }

        public String getLocation_id() {
            return location_id;
        }

        public String getRecipient() {
            return recipient;
        }

        public Date getCheckin() {
            return checkin;
        }

        public Date getCheckout() {
            return checkout;
        }

        public String getLanguage() {
            return language;
        }

        public String getCountry() {
            return country;
        }

        @Override
        public String toString() {
            return "CreateReviewExpressRQ{" +
                    "location_id='" + location_id + '\'' +
                    ", recipient='" + recipient + '\'' +
                    ", checkin=" + checkin +
                    ", checkout=" + checkout +
                    ", language='" + language + '\'' +
                    ", country='" + country + '\'' +
                    '}';
        }
    }
//
//    public static class Response implements Serializable {
//        protected List<Errors> errors;
//        protected String request_id;
//        protected String partner_request_id;
//
//        public String getRequest_id() {
//            return request_id;
//        }
//
//        public String getPartner_request_id() {
//            return partner_request_id;
//        }
//
//
//        public List<Errors> getErrors() {
//            return errors;
//        }
//
//        @Override
//        public String toString() {
//            String ret = "Response{" +
//                    " request_id='" + request_id + '\'' +
//                    ", partner_request_id='" + partner_request_id + '\'' +
//                    ", errors= ";
//            if (errors != null) {
//                ret+="[";
//                for (final Errors error : errors) {
//                    ret += error.toString() + ", ";
//                }
//                ret += "]";
//            }else{
//                ret+="null";
//            }
//            ret +=" }";
//            return ret;
//        }
//    }

    public static class CreateReviewExpressRS implements Serializable {
        protected Error error;
        protected String request_id;
        protected String partner_request_id;
        protected String status;

        @Override
        public String toString() {
            return "CreateReviewExpressRS{" +
                    "error=" + error +
                    ", request_id='" + request_id + '\'' +
                    ", partner_request_id='" + partner_request_id + '\'' +
                    ", status='" + status + '\'' +
                    '}';
        }

        public Error getError() {
            return error;
        }

        public String getRequest_id() {
            return request_id;
        }
    }

    public static class Error implements Serializable {
        private String message;
        private String code;
        private String type;

        public String getMessage() {
            return message;
        }

        public String getCode() {
            return code;
        }

        public String getType() {
            return type;
        }

        @Override
        public String toString() {
            return "Error{" +
                    "message='" + message + '\'' +
                    ", code='" + code + '\'' +
                    ", type='" + type + '\'' +
                    '}';
        }
    }

    public static class ErrorType implements Serializable {
        private Error error;

        public Error getError() {
            return error;
        }

        @Override
        public String toString() {
            return "ErrorType{" +
                    "error=" + error +
                    '}';
        }
    }

    public static class ListReservesRS implements Serializable {

        protected List<Error> errors;
        protected String request_id;
        protected String partner_request_id;

        protected List<ReservationRS> data;
        protected Paging paging;

        public List<ReservationRS> getData() {
            return data;
        }

        public static class Paging implements Serializable {
            protected String previous, skkiped, results, next, total_results;

            @Override
            public String toString() {
                return "Paging{" +
                        "previous='" + previous + '\'' +
                        ", skkiped='" + skkiped + '\'' +
                        ", results='" + results + '\'' +
                        ", next='" + next + '\'' +
                        ", total_results='" + total_results + '\'' +
                        '}';
            }
        }

        @Override
        public String toString() {
            String ret = "ListReservesRS{ data=[ ";
            if (data != null) {
                ret += "[";
                for (final ReservationRS reservation : data) {
                    ret += reservation.toString() + ", ";
                }
                ret += "]";
            } else {
                ret += " null, ";
            }
            ret += " }";
            return ret;
        }
    }

    public static class ReservationRS implements Serializable {
        /**
         * HotelTicker
         */
        protected String location_id;
        /**
         * email
         */
        protected String recipient;
        protected Date checkin;
        protected Date checkout;
        protected String language;
        protected String country;
        protected String status;
        protected String request_id;
        protected String partner_request_id;


        public String getStatus() {
            return status;
        }

        public String getRequest_id() {
            return request_id;
        }

        @Override
        public String toString() {
            return "ReservationRS{" +
                    "location_id='" + location_id + '\'' +
                    ", partner_request_id='" + partner_request_id + '\'' +
                    ", recipient='" + recipient + '\'' +
                    ", checkin=" + checkin +
                    ", checkout=" + checkout +
                    ", language='" + language + '\'' +
                    ", country='" + country + '\'' +
                    ", status='" + status + '\'' +
                    ", request_id='" + request_id + '\'' +
                    '}';
        }
    }


    public static class UpdateReserveRS implements Serializable {

        protected Error error;
        protected String request_id;
        protected String partner_request_id;

        private Integer updated;

        public Integer getUpdated() {
            return updated;
        }

        @Override
        public String toString() {
            return "UpdateReserveRS{" +
                    "error=" + error +
                    ", request_id='" + request_id + '\'' +
                    ", partner_request_id='" + partner_request_id + '\'' +
                    ", updated=" + updated +
                    '}';
        }

        public Error getError() {
            return error;
        }
    }

//    public static class CancelReservationRQ implements Serializable {
//        private List<String> partner_request_ids;
//
//        public CancelReservationRQ(String partner_request_ids) {
//            this.partner_request_ids = new ArrayList<String>();
//            this.partner_request_ids.add(partner_request_ids);
//        }
//    }

    public static class CancelReservationRS implements Serializable {
        private Integer request_id;
        private String status;
        private Error error;


        public String getStatus() {
            return status;
        }

        public Error getError() {
            return error;
        }

        @Override
        public String toString() {
            return "CancelReservationRS{" +
                    "request_id=" + request_id +
                    ", status='" + status + '\'' +
                    ", error='" + error + '\'' +
                    '}';
        }
    }

    public static class CheckOptionsInRS implements Serializable {
        private String see_all_photos, web_url, ratingImageUrl, location_string, location_id,
                write_review, price, num_reviews, description, name, longitude, latitude, rating, ranking,
                photo_count;
        private Address address_obj;
        private Category category, subcategory;
        //        private Object distance,bearing;
        private Boolean review_express_opted_in;
        private List<Ancestors> ancestors;
        private RankingData ranking_data;

        public static class Address implements Serializable {
            private StrictMath street1, street2, city, state, country, postalcode;
        }

        public static class Category implements Serializable {
            private String key, name;
        }

        public static class Ancestors implements Serializable {
            private String level, name, location_id;
        }

        public static class RankingData implements Serializable {
            public String geo_location_id, geo_location_name, ranking_out_of, ranking, ranking_category;
        }

        public Boolean getReview_express_opted_in() {
            return review_express_opted_in;
        }
    }

}