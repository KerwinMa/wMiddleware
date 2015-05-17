/*
 *   JaxbDateSerializer.java
 *
 * Copyright(c) 2013 Witbooking.com. All Rights Reserved.
 * This software is the proprietary information of Witbooking.com
 *
 */
package com.witbooking.middleware.utils.serializers;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 *
 * @author Jose Francisco Fiorillo
 * @author jffiorillo@gmail.com
 */
public class JaxbBooleanAsIntegerSerializer extends XmlAdapter<String, Boolean> {

    public JaxbBooleanAsIntegerSerializer() {}

    @Override
    public String marshal(Boolean bool) throws Exception {
        return bool == null ? null : bool ? "1" : "0";
    }

    @Override
    public Boolean unmarshal(String date) throws Exception {
        return "0".equals(date) ? false : true;
    }
}
