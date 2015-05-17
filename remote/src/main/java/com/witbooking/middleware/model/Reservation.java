/*
 *  Reservation.java
 * 
 * Copyright(c) 2013 Witbooking.com. All Rights Reserved.
 * This software is the proprietary information of Witbooking.com
 * 
 */
package com.witbooking.middleware.model;

import com.witbooking.middleware.integration.mandrill.model.Event;

import java.io.Serializable;
import java.util.*;

/**
 * Insert description here
 *
 * @author Christian Delgado
 * @version 1.0
 * @date 28-ene-2013
 */
public class Reservation implements Serializable {

    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(Reservation.class);
    /**
     * Constant serialized ID used for compatibility.
     */
    private static final long serialVersionUID = 1L;
    //TODO:In WB version V6. this is the value  "idgeneradomulti"
    private String reservationId;
    private Date dateCreation;
    private Customer customer;
    private List<RoomStay> roomStays;
    private String currency;
    private String language;
    //
    private Date emailPostStayDate;
    private Date emailPreStayDate;
    private String comments;
    private Tax tax;
    //
    private Integer agentId;
    private String channelId;//idafiliadoreducido
    private String channelAddress;//idafiliado
    private String codeApplied;//codigo_aplicado
    private String trackingId;//tracking_trivago
    private int cancellationRelease;//reserva_cancelar_release
    private boolean reported;//es_consultada_ws
    private boolean googleAnalyticsReported;//googleanalyticsok
    private String referer;
    //TODO: traducir
    /**
     * Esto representa el estado del pago de la reserva por TPV. Los posibles
     * valores son: 0 no ha pagado, 1 correcto, 2 error.
     */
    private byte paymentStatus;
    //TODO: traducir
    /**
     * Representa el estado de la reserva. Podría ser: reserva, prereserva,
     * cancelada.
     */
    private ReservationStatus status;
    //
    private float amountBeforeTax;
    private float amountAfterTax;

    private Event.EventType userEmailStatus;
    private Event.EventType hotelEmailStatus;

    public Event.EventType getUserEmailStatus() {
        return userEmailStatus;
    }

    public void setUserEmailStatus(Event.EventType userEmailStatus) {
        this.userEmailStatus = userEmailStatus;
    }

    public Event.EventType getHotelEmailStatus() {
        return hotelEmailStatus;
    }

    public void setHotelEmailStatus(Event.EventType hotelEmailStatus) {
        this.hotelEmailStatus = hotelEmailStatus;
    }

    public enum ReservationStatus implements Serializable {
        RESERVATION("reserva"), CANCEL("cancelada"), PRE_RESERVATION("prereserva");
        private String value;
        ReservationStatus(String item) {
            value = item;
        }
        public String getStringValue() {
            return value;
        }
        public static ReservationStatus getValueOf(String item) {
            return item == null
                    ? null
                    : item.equals(RESERVATION.getStringValue())
                    ? RESERVATION
                    : item.equals(CANCEL.getStringValue())
                    ? CANCEL
                    : item.equals(PRE_RESERVATION.getStringValue())
                    ? PRE_RESERVATION
                    : null;
        }
    }

    /**
     * Creates a new instance of
     * <code>ReservationRS</code> without params.
     */
    public Reservation() {
        this.roomStays = new ArrayList<>();
    }

    public String getReservationId() {
        return reservationId;
    }

    public void setReservationId(String reservationId) {
        this.reservationId = reservationId;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public List<RoomStay> getRoomStays() {
        return roomStays;
    }

    public void addRoomStay(RoomStay item) {
        if (roomStays == null) {
            this.roomStays = new ArrayList<>();
        }
        this.roomStays.add(item);
    }

    public void setRoomStays(List<RoomStay> roomStays) {
        this.roomStays = roomStays;
    }

    public Date getEmailPostStayDate() {
        return emailPostStayDate;
    }

    public void setEmailPostStayDate(Date emailPostStayDate) {
        this.emailPostStayDate = emailPostStayDate;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public void setStatus(ReservationStatus status) {
        this.status = status;
    }

    public void setStatus(String status) {
        this.status = ReservationStatus.getValueOf(status);
    }

    public float getAmountAfterTax() {
        return amountAfterTax;
    }

    public void setAmountAfterTax(float amountAfterTax) {
        this.amountAfterTax = amountAfterTax;
    }

    public void increaseAmountAfterTax(final float roomStayAmount) {
        this.amountAfterTax += roomStayAmount;
    }

    public float getAmountBeforeTax() {
        return amountBeforeTax;
    }

    public void setAmountBeforeTax(float amountBeforeTax) {
        this.amountBeforeTax = amountBeforeTax;
    }

    public void increaseAmountBeforeTax(final float amountBeforeTax) {
        this.amountBeforeTax += amountBeforeTax;
    }

    public Tax getTax() {
        return tax;
    }

    public void setTax(Tax tax) {
        this.tax = tax;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public Integer getAgentId() {
        return agentId;
    }

    public void setAgentId(Integer agentId) {
        this.agentId = agentId;
    }

    public boolean isReported() {
        return reported;
    }

    public void setReported(boolean reported) {
        this.reported = reported;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public byte getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(byte paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public boolean isStockReduced(){
        return paymentStatus != 1 && paymentStatus != 3 && paymentStatus != 6;
    }

    public String getChannelAddress() {
        return channelAddress;
    }

    public void setChannelAddress(String channelAddress) {
        this.channelAddress = channelAddress;
    }

    public Date getEmailPreStayDate() {
        return emailPreStayDate;
    }

    public void setEmailPreStayDate(Date emailPreStayDate) {
        this.emailPreStayDate = emailPreStayDate;
    }

    public String getCodeApplied() {
        return codeApplied;
    }

    public void setCodeApplied(String codeApplied) {
        this.codeApplied = codeApplied;
    }

    public boolean isGoogleAnalyticsReported() {
        return googleAnalyticsReported;
    }

    public void setGoogleAnalyticsReported(boolean googleAnalyticsReported) {
        this.googleAnalyticsReported = googleAnalyticsReported;
    }

    public String getTrackingId() {
        return trackingId;
    }

    public int getCancellationRelease() {
        return cancellationRelease;
    }

    public void setCancellationRelease(int cancellationRelease) {
        this.cancellationRelease = cancellationRelease;
    }

    public void setTrackingId(String trackingId) {
        this.trackingId = trackingId;
    }


    public String getReferer() {
        return referer;
    }

    public void setReferer(String referer) {
        this.referer = referer;
    }

    public String getBookingReservationId() {
        return (reservationId.charAt(0) == 'B') ? reservationId.substring(1) : reservationId;
    }

    public Date getFirstCheckIn() {
        Date dateFirstCheckIn = null;
        for (final RoomStay roomStay : roomStays) {
            final Date dateCheckIn = roomStay.getDateCheckIn();
            if (dateFirstCheckIn == null || dateFirstCheckIn.after(dateCheckIn)) {
                dateFirstCheckIn = dateCheckIn;
            }
        }
        return dateFirstCheckIn;
    }

    public Date getLastCheckOut() {
        Date dateFirstCheckOut = null;
        for (final RoomStay roomStay : roomStays) {
            final Date dateCheckOut = roomStay.getDateCheckOut();
            if (dateFirstCheckOut == null || dateFirstCheckOut.before(dateCheckOut)) {
                dateFirstCheckOut = dateCheckOut;
            }
        }
        return dateFirstCheckOut;
    }

    public List<String> getAllInventoryTickers() {
        Set<String> invTickers = new HashSet<>();
        for (final RoomStay roomStay : roomStays) {
            invTickers.add(roomStay.getInventoryTicker());
        }
        return new ArrayList<>(invTickers);
    }

    public static String generateRandomCode(){
        long number = (long) Math.floor(Math.random() * 9000000L) + 1000000L;
        return number+"";
    }

    public void updateModificationDate(){
        Date now=new Date();
        for (RoomStay roomStay : roomStays) {
            roomStay.setDateModification(now);
        }
    }

    //   public String getIdMultiple() {
//      return idMultiple;
//   }
//
//   public void setIdMultiple(String idMultiple) {
//      this.idMultiple = idMultiple;
//   }
//
//   public String getIdOwn() {
//      return idOwn;
//   }
//
//   public void setIdOwn(String idOwn) {
//      this.idOwn = idOwn;
//   }
//   
//   @Override
//   public String toString() {
//      return "ReservationRS{" + "id=" + id
//              + //List 
//              ", roomStays=" + roomStays + //", idOwn=" + idOwn + ", idMultiple=" + idMultiple + 
//              ", totalAmount=" + totalAmount + ", dateCreation=" + dateCreation + ", dateModification=" + dateModification + ", customer=" + customer + ", language=" + language + ", emailPostStayDate=" + emailPostStayDate + ", comments=" + comments + ", status=" + status + ", cancellationCause=" + cancellationCause + ", cancellationDate=" + cancellationDate + ", amountAfterTax=" + amountAfterTax + ", amountBeforeTax=" + amountBeforeTax + ", tax=" + tax + '}';
//   }
//    @Override
//    public String toString() {
//
//        return "ReservationRS{" + "idConfirmation=" + idConfirmation
//                + ", dateCreation=" + DateUtil.timestampFormat(dateCreation)
//                + ", dateModification=" + DateUtil.timestampFormat(dateModification)
//                + ", customer=" + customer
//                + ", language=" + language
//                + ", currency=" + currency
//                + ", roomStays=" + roomStays
//                + ", comments=" + comments
//                + ", status=" + status
//                + ", cancellationCause=" + cancellationCause
//                + ", cancellationDate=" + DateUtil.timestampFormat(cancellationDate)
//                + ", amountAfterTax=" + amountAfterTax
//                + ", amountBeforeTax=" + amountBeforeTax
//                + ", amountGuarantee=" + amountGuarantee
//                + ", tax=" + tax
//                + ", channelId=" + channelId
//                + ", agentId=" + agentId
//                + ", reported=" + reported
//                + ", paymentStatus=" + paymentStatus
//                //              + ", emailPostStayDate=" + DateUtil.timestampFormat(emailPostStayDate)
//                //              + ", emailPreStayDate=" + DateUtil.timestampFormat(emailPreStayDate)
//                + '}';
//    }


    public RoomStay removeRoomStayByIdConfirmation(RoomStay newRoomStay) {
        if (newRoomStay.getIdConfirmation() == null)
            return null;
        for (RoomStay roomStay : new ArrayList<>(roomStays)) {
            if (roomStay.getIdConfirmation() != null &&
                    newRoomStay.getIdConfirmation().equalsIgnoreCase(roomStay.getIdConfirmation())) {
                if (roomStays.remove(roomStay))
                    return roomStay;
            }
        }
        return null;
    }

/*
    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("reservationId", reservationId)
                .add("dateCreation", dateCreation)
                .add("currency", currency)
                .add("language", language)
                .add("customer", customer)
                .add("roomStays", roomStays)
                .add("emailPostStayDate", emailPostStayDate)
                .add("emailPreStayDate", emailPreStayDate)
                .add("comments", comments)
                .add("status", status)
                .add("amountAfterTax", amountAfterTax)
                .add("amountBeforeTax", amountBeforeTax)
                .add("tax", tax)
                .add("channelId", channelId)
                .add("channelAddress", channelAddress)
                .add("agentId", agentId)
                .add("reported", reported)
                .add("paymentStatus", paymentStatus)
                .toString();
    }
*/

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Reservation other = (Reservation) obj;
        if ((this.reservationId == null) ? (other.reservationId != null) : !this.reservationId.equals(other.reservationId)) {
            return false;
        }
        if (this.dateCreation != other.dateCreation && (this.dateCreation == null || !this.dateCreation.equals(other.dateCreation))) {
            return false;
        }
        if ((this.currency == null) ? (other.currency != null) : !this.currency.equals(other.currency)) {
            return false;
        }
        if ((this.language == null) ? (other.language != null) : !this.language.equals(other.language)) {
            return false;
        }
        if (this.customer != other.customer && (this.customer == null || !this.customer.equals(other.customer))) {
            return false;
        }
        if (this.roomStays != other.roomStays && (this.roomStays == null || !this.roomStays.equals(other.roomStays))) {
            return false;
        }
        if (this.emailPostStayDate != other.emailPostStayDate && (this.emailPostStayDate == null || !this.emailPostStayDate.equals(other.emailPostStayDate))) {
            return false;
        }
        if (this.emailPreStayDate != other.emailPreStayDate && (this.emailPreStayDate == null || !this.emailPreStayDate.equals(other.emailPreStayDate))) {
            return false;
        }
        if ((this.comments == null) ? (other.comments != null) : !this.comments.equals(other.comments)) {
            return false;
        }
        if ((this.status == null) ? (other.status != null) : !this.status.equals(other.status)) {
            return false;
        }
        if (Float.floatToIntBits(this.amountAfterTax) != Float.floatToIntBits(other.amountAfterTax)) {
            return false;
        }
        if (Float.floatToIntBits(this.amountBeforeTax) != Float.floatToIntBits(other.amountBeforeTax)) {
            return false;
        }
        if (this.tax != other.tax && (this.tax == null || !this.tax.equals(other.tax))) {
            return false;
        }
        if ((this.channelId == null) ? (other.channelId != null) : !this.channelId.equals(other.channelId)) {
            return false;
        }
        if ((this.channelAddress == null) ? (other.channelAddress != null) : !this.channelAddress.equals(other.channelAddress)) {
            return false;
        }
        if ((this.agentId == null) ? (other.agentId != null) : !this.agentId.equals(other.agentId)) {
            return false;
        }
        if (this.reported != other.reported) {
            return false;
        }
        if (this.paymentStatus != other.paymentStatus) {
            return false;
        }
        return true;
    }
}
