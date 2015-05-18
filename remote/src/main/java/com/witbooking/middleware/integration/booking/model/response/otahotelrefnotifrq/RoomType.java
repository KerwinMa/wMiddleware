package com.witbooking.middleware.integration.booking.model.response.otahotelrefnotifrq;

import java.io.Serializable;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

/**
 *
 * @author Jose Francisco Fiorillo
 * @author jffiorillo@gmail.com
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class RoomType implements Serializable {

    /**
     * RoomType RoomTypeCode: the room type ID as used by BOOKING.COM.
     */
    @XmlAttribute(name = "RoomTypeCode")
    private String roomTypeCode;
    /**
     * Room Description.
     */
    @XmlElement(name = "RoomDescription")
    private RoomDescription roomDescription;
    /**
     * AdditionalDetails
     */
    @XmlElement(name = "AdditionalDetails")
    private AdditionalDetails details;
    /**
     * Amenity: room facility as displayed on the website at the time the
     * reservation was made.
     */
    @XmlElement(name = "Amenities")
    private Amenities amenity;

    public RoomType() {
    }
    
    public String getMealPlan(){
        return (roomDescription != null) ? roomDescription.getMealPlan() : "";
    }
    
    public String getRoomDescription(){
        return (roomDescription != null) ? roomDescription.getRoomDescription() : "";
    }
    
    public String getRoomTypeCode(){
        return roomTypeCode == null ? "" : roomTypeCode;
    }

    private static class Amenities implements Serializable {

        @XmlElement(name = "Amenity")
        private List<String> amenity;

        public Amenities() {
        }

        @Override
        public String toString() {
            return "Amenities{" + "amenity=" + amenity + '}';
        }
    }

    private static class RoomDescription implements Serializable {

        /**
         * Name: Room short name as displayed on the website. Please note that
         * the roomname might differ from the roomname in the roomrates request,
         * depending on the policy and/or rate type. Therefore we suggest to
         * only map based on room ID and rate ID.
         */
        @XmlAttribute(name = "Name")
        private String name;
        /**
         * Text: Room description as currently known for the booked room in the
         * database.
         */
        @XmlElement(name = "Text")
        private String text;
        /**
         * MealPlan: Meal plan (breakfast, lunch or dinner) information that is
         * applicable for the booked room.
         */
        @XmlElement(name = "MealPlan")
        private String mealPlan;
        /**
         * MaxChildren: The static setting of maximum amount of children that
         * can stay free in the booked room. Note that, this does not mean that
         * if the max_children=2, that the guest has entered 2 children in the
         * bookprocess. This is a static setting, defined per room. The maximum
         * age of the children can be found in the policy of the hotel. The
         * hotelier can request this setting with the Booking.com account
         * managers or check in the Booking.com Extranet.
         */
        @XmlElement(name = "MaxChildren")
        private Integer maxChildren;

        public RoomDescription() {
        }

        @Override
        public String toString() {
            return "RoomDescription{" + "name=" + name + ", text=" + text + ", mealPlan=" + mealPlan + ", maxChildren=" + maxChildren + '}';
        }
        
        public String getMealPlan(){
            return (mealPlan!= null) ? mealPlan : "";
        }
        
        public String getRoomDescription(){
            return (name!= null) ? name : "";
        }
    }

    private static class AdditionalDetails implements Serializable {

        @XmlElement(name = "AdditionalDetail")
        private AdditionalDetail additionalDetail;

        public AdditionalDetails() {
        }

        @Override
        public String toString() {
            return "AdditionalDetails{" + "AdditionalDetail=" + additionalDetail + '}';
        }

        private static class AdditionalDetail implements Serializable {

            @XmlElement(name = "DetailDescription")
            private List<DetailDescription> detailDescription;

            public AdditionalDetail() {
            }

            @Override
            public String toString() {
                return "AdditionalDetail{" + "DetailDescription=" + detailDescription + '}';
            }

            private static class DetailDescription implements Serializable {

                /**
                 * Text: room info as displayed on the website at the time the
                 * reservation was made.
                 */
                @XmlElement(name = "Text")
                private String text;

                public DetailDescription() {
                }

                @Override
                public String toString() {
                    return "DetailDescription{" + "text=" + text + '}';
                }
            }
        }
    }

    @Override
    public String toString() {
        return "RoomType{" + "roomTypeCode=" + roomTypeCode + ", roomDescription=" + roomDescription + ", details=" + details + ", amenity=" + amenity + '}';
    }
}
