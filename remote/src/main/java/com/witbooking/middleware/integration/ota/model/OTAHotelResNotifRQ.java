//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.5-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.08.28 at 02:33:51 PM CEST 
//


package com.witbooking.middleware.integration.ota.model;

import com.google.common.base.Objects;
import com.witbooking.middleware.utils.XMLUtils;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;


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
 *         &lt;element name="POS" type="{http://www.opentravel.org/OTA/2003/05}POS_Type" minOccurs="0"/>
 *         &lt;element name="MessageID" type="{http://www.opentravel.org/OTA/2003/05}UniqueID_Type" minOccurs="0"/>
 *         &lt;element name="HotelReservations" type="{http://www.opentravel.org/OTA/2003/05}HotelReservationsType" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attGroup ref="{http://www.opentravel.org/OTA/2003/05}OTA_PayloadStdAttributes"/>
 *       &lt;attribute name="ResStatus" type="{http://www.opentravel.org/OTA/2003/05}TransactionActionType" />
 *       &lt;attribute name="HoldDuration" type="{http://www.w3.org/2001/XMLSchema}duration" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "pos",
        "messageID",
        "hotelReservations"
})
@XmlRootElement(name = "OTA_HotelResNotifRQ")
public class OTAHotelResNotifRQ implements Serializable {

    private static final double VERSION = 1.003;
    private static final String TARGET = "Production";
    //    public static final String NEW_RESERVATION = "Commit";
//    public static final String MODIFY_RESERVATION = "Modify";
//    public static final String CANCEL_RESERVATION = "Cancel";
    @XmlElement(name = "POS")
    protected POSType pos;
    @XmlElement(name = "MessageID")
    protected UniqueIDType messageID;
    @XmlElement(name = "HotelReservations")
    protected HotelReservationsType hotelReservations;
    @XmlAttribute(name = "ResStatus")
    protected TransactionActionType resStatus;
    @XmlAttribute(name = "HoldDuration")
    protected Duration holdDuration;
    @XmlAttribute(name = "EchoToken")
    protected String echoToken;
    @XmlAttribute(name = "TimeStamp")
    @XmlSchemaType(name = "dateTime")
    protected final XMLGregorianCalendar timeStamp = XMLUtils.getNow();
    @XmlAttribute(name = "Target")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String target;
    @XmlAttribute(name = "TargetName")
    protected final String targetName = TARGET;
    @XmlAttribute(name = "Version", required = true)
    protected final double version = VERSION;
    @XmlAttribute(name = "TransactionIdentifier")
    protected String transactionIdentifier;
    @XmlAttribute(name = "SequenceNmbr")
    @XmlSchemaType(name = "nonNegativeInteger")
    protected BigInteger sequenceNmbr;
    @XmlAttribute(name = "TransactionStatusCode")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String transactionStatusCode;
    @XmlAttribute(name = "PrimaryLangID")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "language")
    protected String primaryLangID;
    @XmlAttribute(name = "AltLangID")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "language")
    protected String altLangID;
    @XmlAttribute(name = "RetransmissionIndicator")
    protected Boolean retransmissionIndicator;
    @XmlAttribute(name = "CorrelationID")
    protected String correlationID;

    public OTAHotelResNotifRQ() {
    }

    public OTAHotelResNotifRQ(final String userId, final String channelId,
                              final String channelName, final HotelReservationsType.HotelReservation res) {
        this.resStatus = res.getResStatus();
        this.pos = new POSType(userId, channelId, channelName);
        this.hotelReservations = new HotelReservationsType();
        this.hotelReservations.hotelReservation.add(res);
    }

    /**
     * Gets the value of the pos property.
     *
     * @return possible object is
     *         {@link POSType }
     */
    public POSType getPOS() {
        return pos;
    }

    /**
     * Sets the value of the pos property.
     *
     * @param value allowed object is
     *              {@link POSType }
     */
    public void setPOS(POSType value) {
        this.pos = value;
    }

    /**
     * Gets the value of the messageID property.
     *
     * @return possible object is
     *         {@link UniqueIDType }
     */
    public UniqueIDType getMessageID() {
        return messageID;
    }

    /**
     * Sets the value of the messageID property.
     *
     * @param value allowed object is
     *              {@link UniqueIDType }
     */
    public void setMessageID(UniqueIDType value) {
        this.messageID = value;
    }

    /**
     * Gets the value of the hotelReservations property.
     *
     * @return possible object is
     *         {@link HotelReservationsType }
     */
    public HotelReservationsType getHotelReservations() {
        return hotelReservations;
    }

    /**
     * Sets the value of the hotelReservations property.
     *
     * @param value allowed object is
     *              {@link HotelReservationsType }
     */
    public void setHotelReservations(HotelReservationsType value) {
        this.hotelReservations = value;
    }

    /**
     * Gets the value of the resStatus property.
     *
     * @return possible object is
     *         {@link TransactionActionType }
     */
    public TransactionActionType getResStatus() {
        return resStatus;
    }

    /**
     * Sets the value of the resStatus property.
     *
     * @param value allowed object is
     *              {@link TransactionActionType }
     */
    public void setResStatus(TransactionActionType value) {
        this.resStatus = value;
    }

    /**
     * Gets the value of the holdDuration property.
     *
     * @return possible object is
     *         {@link Duration }
     */
    public Duration getHoldDuration() {
        return holdDuration;
    }

    /**
     * Sets the value of the holdDuration property.
     *
     * @param value allowed object is
     *              {@link Duration }
     */
    public void setHoldDuration(Duration value) {
        this.holdDuration = value;
    }

    /**
     * Gets the value of the echoToken property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getEchoToken() {
        return echoToken;
    }

    /**
     * Sets the value of the echoToken property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setEchoToken(String value) {
        this.echoToken = value;
    }

    /**
     * Gets the value of the timeStamp property.
     *
     * @return possible object is
     *         {@link XMLGregorianCalendar }
     */
    public XMLGregorianCalendar getTimeStamp() {
        return timeStamp;
    }

    /**
     * Sets the value of the timeStamp property.
     *
     * @param value allowed object is
     *              {@link XMLGregorianCalendar }
     */
    public void setTimeStamp(XMLGregorianCalendar value) {
//        this.timeStamp = value;
    }

    /**
     * Gets the value of the target property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getTarget() {
        return target;
    }

    /**
     * Sets the value of the target property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setTarget(String value) {
        this.target = value;
    }

    /**
     * Gets the value of the targetName property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getTargetName() {
        return targetName;
    }

    /**
     * Sets the value of the targetName property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setTargetName(String value) {
//        this.targetName = value;
    }

    /**
     * Gets the value of the version property.
     *
     * @return possible object is
     *         {@link BigDecimal }
     */
    public double getVersion() {
        return version;
    }

    /**
     * Sets the value of the version property.
     *
     * @param value allowed object is
     *              {@link BigDecimal }
     */
    public void setVersion(BigDecimal value) {
//        this.version = value;
    }


    /**
     * Gets the value of the transactionIdentifier property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getTransactionIdentifier() {
        return transactionIdentifier;
    }

    /**
     * Sets the value of the transactionIdentifier property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setTransactionIdentifier(String value) {
        this.transactionIdentifier = value;
    }

    /**
     * Gets the value of the sequenceNmbr property.
     *
     * @return possible object is
     *         {@link BigInteger }
     */
    public BigInteger getSequenceNmbr() {
        return sequenceNmbr;
    }

    /**
     * Sets the value of the sequenceNmbr property.
     *
     * @param value allowed object is
     *              {@link BigInteger }
     */
    public void setSequenceNmbr(BigInteger value) {
        this.sequenceNmbr = value;
    }

    /**
     * Gets the value of the transactionStatusCode property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getTransactionStatusCode() {
        return transactionStatusCode;
    }

    /**
     * Sets the value of the transactionStatusCode property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setTransactionStatusCode(String value) {
        this.transactionStatusCode = value;
    }

    /**
     * Gets the value of the primaryLangID property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getPrimaryLangID() {
        return primaryLangID;
    }

    /**
     * Sets the value of the primaryLangID property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setPrimaryLangID(String value) {
        this.primaryLangID = value;
    }

    /**
     * Gets the value of the altLangID property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getAltLangID() {
        return altLangID;
    }

    /**
     * Sets the value of the altLangID property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setAltLangID(String value) {
        this.altLangID = value;
    }

    /**
     * Gets the value of the retransmissionIndicator property.
     *
     * @return possible object is
     *         {@link Boolean }
     */
    public Boolean isRetransmissionIndicator() {
        return retransmissionIndicator;
    }

    /**
     * Sets the value of the retransmissionIndicator property.
     *
     * @param value allowed object is
     *              {@link Boolean }
     */
    public void setRetransmissionIndicator(Boolean value) {
        this.retransmissionIndicator = value;
    }

    /**
     * Gets the value of the correlationID property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getCorrelationID() {
        return correlationID;
    }

    /**
     * Sets the value of the correlationID property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setCorrelationID(String value) {
        this.correlationID = value;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("pos", pos)
                .add("messageID", messageID)
                .add("hotelReservations", hotelReservations)
                .add("resStatus", resStatus)
                .add("holdDuration", holdDuration)
                .add("echoToken", echoToken)
                .add("timeStamp", timeStamp)
                .add("target", target)
                .add("targetName", targetName)
                .add("version", version)
                .add("transactionIdentifier", transactionIdentifier)
                .add("sequenceNmbr", sequenceNmbr)
                .add("transactionStatusCode", transactionStatusCode)
                .add("primaryLangID", primaryLangID)
                .add("altLangID", altLangID)
                .add("retransmissionIndicator", retransmissionIndicator)
                .add("correlationID", correlationID)
                .toString();
    }


}
