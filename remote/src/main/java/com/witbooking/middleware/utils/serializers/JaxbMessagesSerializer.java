/*
 *   JaxbDateSerializer.java
 *
 * Copyright(c) 2013 Witbooking.com. All Rights Reserved.
 * This software is the proprietary information of Witbooking.com
 *
 */
package com.witbooking.middleware.utils.serializers;

import com.witbooking.middleware.integration.booking.model.response.Message;
import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 *
 * @author Jose Francisco Fiorillo
 * @author jffiorillo@gmail.com
 */
public class JaxbMessagesSerializer extends XmlAdapter<String, Message> {

    public JaxbMessagesSerializer() {
    }

    @Override
    public String marshal(Message mess) throws Exception {
        return null;
    }

    @Override
    public Message unmarshal(String mess) throws Exception {
        //return "0".equals(date) ? false : true;
        System.out.println("Trato de unmarshal");
        return null;
    }
}
