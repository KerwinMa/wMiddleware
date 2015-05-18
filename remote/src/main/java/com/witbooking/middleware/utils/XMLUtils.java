package com.witbooking.middleware.utils;

import com.witbooking.middleware.integration.booking.model.Constants;
import org.w3c.dom.Node;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.soap.SOAPMessage;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * XMLUtils class implements several static marshal utilities.
 *
 * @author Jose Francisco Fiorillo < jffiorillo@gmail.com >
 */
public final class XMLUtils {

    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(XMLUtils.class);
    public static final boolean FORMATTED_INPUT = true, NO_FORMATTED_INPUT = false;

    /**
     * //TODO DOC
     *
     * @param item
     * @return
     * @throws JAXBException
     */
    public static String marshalFromObject(Object item, boolean formattedOutput) throws JAXBException {
        if (item == null) {
            return item + "";
        }
//        logger.debug("Print: " + item.getClass() + " to XML.");
//        logger.debug("Object: " + item);
        final JAXBContext jaxbContext;
        String stringXML;
        jaxbContext = JAXBContext.newInstance(item.getClass());
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, formattedOutput);
        //jaxbMarshaller
        StringWriter stringWriter = null;
        try {
            stringWriter = new StringWriter();
            jaxbMarshaller.marshal(item, stringWriter);
            stringXML = stringWriter.toString();
//        logger.debug(stringXML);
            return stringXML;
        } finally {
            try {
                if (stringWriter != null)
                    stringWriter.close();
            } catch (IOException e) {
                logger.error(e);
            }
        }
    }

    /**
     * Marshal a object of
     * <code>classItem</code> from the xmlResponse
     * <code>String</code>.
     *
     * @param xmlResponse <code>String</code> that represents the object to be
     *                    marshal.
     * @param classItem   <code>class</code> of the returns object.
     * @return a object of <code>classItem</code>.
     * @throws JAXBException throw trying to marshal.
     */
    public static Object unmarshalFromString(String xmlResponse, Class classItem) throws JAXBException {
        final JAXBContext jaxbContext = JAXBContext.newInstance(classItem);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        StringReader reader = null;
        try {
            reader = new StringReader(xmlResponse);
            return jaxbUnmarshaller.unmarshal(reader);
        } finally {
            if (reader != null)
                reader.close();
        }
    }

    /**
     * Marshal a object of
     * <code>classItem</code> from the xmlResponse
     * <code>String</code>.
     *
     * @param xmlResponse <code>String</code> that represents the object to be
     *                    marshal.
     * @param classItem   <code>String</code> of the returns object.
     * @return a object of <code>classItem</code>.
     * @throws JAXBException throw trying to marshal.
     */
    public static Object unmarshalFromString(String xmlResponse, String classItem) throws JAXBException {
        final JAXBContext jaxbContext = JAXBContext.newInstance(classItem);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        StringReader reader = null;
        try {
            reader = new StringReader(xmlResponse);
            return jaxbUnmarshaller.unmarshal(reader);
        } finally {
            if (reader != null)
                reader.close();
        }
    }

    /**
     * Returns the Booking message identifier (called by Booking RUID) from a
     * XML response of Booking.
     *
     * @param xmlResponse XML <code>String</code> representations.
     * @return the RUID found, if not found the RUID, <code>null</code>.
     * @throws XMLStreamException throw by {@link XMLStreamReader} or
     *                            {@link XMLInputFactory#createXMLStreamReader(java.io.InputStream)} or
     *                            {@link XMLStreamReader#hasNext() }.
     */
    public static String getRUID(String xmlResponse)
            throws XMLStreamException {
        XMLStreamReader xsr = null;
        try {
            xsr = XMLInputFactory.newFactory().createXMLStreamReader(new StringReader(xmlResponse));
            while (xsr.hasNext()) {
                if (xsr.getEventType() == XMLStreamConstants.COMMENT) {
                    final String str = xsr.getText();
                    if (str.length() > 5) {
                        final String ruid = str.substring(1, 5);
                        if (Constants.RUID.equals(ruid)) {
                            return str.substring(7, str.length() - 1);
                        }
                    }
                }
                xsr.next();
            }
        } finally {
            if (xsr != null)
                xsr.close();
        }
        return null;
    }

    /**
     * Marshals the object given into
     * <code>String</code>.
     *
     * @param item item to marshal.
     * @return the <code>String</code> that represents the object given marshal.
     * @throws JAXBException throw by {@link JAXBContext}.
     */
    public static String parseToString(Object item) throws JAXBException {
        JAXBContext jaxbContext;
        jaxbContext = JAXBContext.newInstance(item.getClass());
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        //jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        StringWriter stringWriter = null;
        try {
            stringWriter = new StringWriter();
            jaxbMarshaller.marshal(item, stringWriter);
            return stringWriter.toString();
        } finally {
            try {
                if (stringWriter != null)
                    stringWriter.close();
            } catch (IOException e) {
                logger.error(e);
            }
        }
    }

    public static XMLGregorianCalendar getNow() {
        return getXMLGeCalendarFromDate(null);
    }

    public static XMLGregorianCalendar getXMLGeCalendarFromDate(final Date date) {
        try {
            final GregorianCalendar gregorianCalendar = new GregorianCalendar();
            if (date != null) {
                gregorianCalendar.setTime(date);
            }
            return DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar);
        } catch (DatatypeConfigurationException ex) {
            logger.error(ex);
        }
        return null;
    }

    /**
     * Method used to print the SOAP Response
     */
    public static String parseDocumentToString(Node doc) {
        String strBody = null;
        StringWriter stringWriter=null;
        try {
            Source source = new DOMSource(doc);
            stringWriter = new StringWriter();
            Result result = new StreamResult(stringWriter);
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer();
            transformer.transform(source, result);
            strBody = stringWriter.getBuffer().toString();
            strBody = strBody.replaceAll("\n", "");
            strBody = strBody.replaceAll("\r", "");
        } catch (TransformerConfigurationException e) {
            logger.error("TransformerConfigurationException while parsing soap body to string: " + e.getMessage());
            logger.error("Stack Trace: ", e);
        } catch (TransformerException e) {
            logger.error("TransformerException while parsing soap body to string: " + e.getMessage());
            logger.error("Stack Trace: ", e);
        } finally {
            try {
                if (stringWriter != null)
                    stringWriter.close();
            } catch (IOException e) {
                logger.error(e);
            }
        }

        return strBody;
    }

    public static String soapMessageToString(SOAPMessage message) {
        String result = null;
        if (message != null) {
            ByteArrayOutputStream baos = null;
            try {
                baos = new ByteArrayOutputStream();
                message.writeTo(baos);
                result = baos.toString();
            } catch (Exception e) {
            } finally {
                if (baos != null) {
                    try {
                        baos.close();
                    } catch (IOException ioe) {
                    }
                }
            }
        }
        return result;
    }

    public static String errorOTAString(String msg) {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                "<ns2:Errors xmlns:ns2=\"http://www.opentravel.org/OTA/2003/05\">" +
                "<ns2:Error Type=\"" + msg + "\"/>" +
                "</ns2:Errors>";
    }
}
