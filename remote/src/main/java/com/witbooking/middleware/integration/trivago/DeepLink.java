/*
 *  DeepLink.java
 * 
 * Copyright(c) 2013 Witbooking.com. All Rights Reserved.
 * This software is the proprietary information of Witbooking.com
 * 
 */
package com.witbooking.middleware.integration.trivago;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;
import java.io.Serializable;

/**
 * Insert description here
 *
 * @author Christian Delgado
 * @date 09-abr-2013
 * @version 1.0
 * @since
 */
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class DeepLink implements Serializable {

   /**
    * Constant serialized ID used for compatibility.
    */
   private static final long serialVersionUID = 1L;

   @XmlValue
   private String value;

   /**
    * Creates a new instance of
    * <code>DeepLink</code> without params.
    */
   public DeepLink() {
   }

   public DeepLink(String link) {
      this.value = link;
   }

   public String getValue() {
      return value;
   }

   public void setValue(String value) {
      this.value = value;
   }

   @Override
   public String toString() {
      return "DeepLink{" + "value=" + value + '}';
   }

   @XmlRootElement(name = "response")
   @XmlAccessorType(XmlAccessType.FIELD)
   public static class Link extends DeepLink {

      public Link() {
      }

      public Link(String link) {
         super(link);
      }
   }

   @XmlRootElement(name = "error")
   @XmlAccessorType(XmlAccessType.FIELD)
   public static class LinkError extends DeepLink {

      public LinkError() {
      }

      public LinkError(String link) {
         super(link);
      }
   }
}
