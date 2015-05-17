package com.witbooking.middleware.model.dynamicPriceVariation;

import com.witbooking.middleware.exceptions.model.IncompatibleTypesException;
import org.apache.log4j.Logger;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * Created by mongoose on 9/29/14.
 */
public class CountryOfOriginCondition extends Condition implements Serializable {

    private static final Logger logger = Logger.getLogger(HourRangeCondition.class);

    public final static String ARGUMENT_COUNTRY= "argument_country";

    private static List<String> REQUIRED_ARGUMENTS= Arrays.asList(ARGUMENT_COUNTRY);

    private static HashSet<ConditionType> VALID_CONDITIONS= new HashSet<ConditionType>(Arrays.asList(ConditionType.INCLUDE, ConditionType.EXCLUDE, ConditionType.EXACT, ConditionType.LIKE, ConditionType.ALL));

    private HashSet<String> countries;

    private transient TickerCondition tickerCondition=new TickerCondition();

    /**
     * Constant serialized ID used for compatibility.
     */
    private static final long serialVersionUID = 1L;


    public HashSet<String> getCountries() {
        return countries;
    }

    public void setCountries(HashSet<String> countries) {
        this.countries = countries;
    }

    @Override
    public void addConditionType(ConditionType conditionType, boolean value) throws IncompatibleTypesException {
        super.addConditionType(conditionType, value,VALID_CONDITIONS);
    }

    public String getCountryCodeFromIP(String ipaddress){

        return "";
    }

    @Override
    public Boolean evaluate(Map<String, Object> arguments) {
        if(!super.evaluate(arguments)){
            return false;
        }
        super.validateArguments(arguments,REQUIRED_ARGUMENTS);

        /*Delegate Functionality to tickerCondition*/
        tickerCondition.setDataValueHolderTickers(getCountries());
        for (Map.Entry<ConditionType, Boolean> entry : getConditionType().entrySet()){
            tickerCondition.addConditionType(entry.getKey(),entry.getValue());
        }
        arguments.put(TickerCondition.ARGUMENT_TICKER, arguments.get(ARGUMENT_COUNTRY));

        return tickerCondition.evaluate(arguments);
    }
}
