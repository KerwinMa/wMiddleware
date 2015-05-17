package com.witbooking.middleware.integration.booking.model.response.otahotelrefnotifrq;

import com.witbooking.middleware.utils.serializers.JaxbDateWithoutTimeSerializer;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Jose Francisco Fiorillo
 * @author jffiorillo@gmail.com
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class RoomRate implements Serializable {

    /**
     * EffectiveDate: effective date.
     */
    @XmlAttribute(name = "EffectiveDate")
    @XmlJavaTypeAdapter(JaxbDateWithoutTimeSerializer.class)
    private Date effectiveDate;
    /**
     * RatePlanCode: rate category ID.
     */
    @XmlAttribute(name = "RatePlanCode")
    private String ratePlanCode;
    
    @XmlElement(name = "Rates")
    private Rates rates;
    
    public RoomRate(){}
    
    public String getRatePlanCode(){
        return ratePlanCode == null ? "" : ratePlanCode;
    }

    private static class Rates implements Serializable{
            @XmlElement(name = "Rate")
    private List<Rate> rates;

        public Rates() {
        }

        @Override
        public String toString() {
            return "Rates{" + "rates=" + rates + '}';
        }
        
        public float getPrice(){
            float ret =0;
            for(Rate item: rates){
                ret+= item.getPrice();
            }
            return ret;
        }
            
            
    }
    
    @XmlAccessorType(XmlAccessType.FIELD)
    private static class Rate implements Serializable {

        @XmlElement(name = "Total")
        private Total rate;
        
        public Rate(){}

        @Override
        public String toString() {
            return rate.toString();
        }
        
        public float getPrice(){
            return rate.getValue();
        }
        
        
    }

    @Override
    public String toString() {
        return "RoomRate{" + "effectiveDate=" + effectiveDate + ", ratePlanCode=" + ratePlanCode + ", rates=" + rates + '}';
    }
    
    public Date getDate() {
        return effectiveDate;
    }
    
    public float getPrice() {
        return rates.getPrice();
    }
}
