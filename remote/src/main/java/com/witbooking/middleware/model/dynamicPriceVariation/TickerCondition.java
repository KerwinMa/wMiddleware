package com.witbooking.middleware.model.dynamicPriceVariation;

import com.witbooking.middleware.exceptions.model.IncompatibleTypesException;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * Created by mongoose on 9/29/14.
 */
public class TickerCondition extends Condition implements Serializable  {

    private static final Logger logger = Logger.getLogger(HourRangeCondition.class);

    public final static String ARGUMENT_TICKER= "argument_ticker";

    private static List<String> REQUIRED_ARGUMENTS= Arrays.asList(ARGUMENT_TICKER);

    private static HashSet<ConditionType> VALID_CONDITIONS= new HashSet<ConditionType>(Arrays.asList(ConditionType.INCLUDE,ConditionType.EXCLUDE,ConditionType.EXACT,ConditionType.LIKE,ConditionType.ALL));

    /**
     * Constant serialized ID used for compatibility.
     */
    private static final long serialVersionUID = 1L;

    private HashSet<String> dataValueHolderTickers;

    @Override
    public void addConditionType(ConditionType conditionType, boolean value) throws IncompatibleTypesException {
        super.addConditionType(conditionType, value,VALID_CONDITIONS);
    }

    public HashSet<String> getDataValueHolderTickers() {
        return dataValueHolderTickers;
    }

    public void setDataValueHolderTickers(HashSet<String> dataValueHolderTickers) {
        this.dataValueHolderTickers = dataValueHolderTickers;
    }


    @Override
    public Boolean evaluate(Map<String, Object> arguments) {
        if(!super.evaluate(arguments)){
            return false;
        }
        super.validateArguments(arguments,REQUIRED_ARGUMENTS);

        String chosenTicker=null;
        try {
            chosenTicker = (String)arguments.get(ARGUMENT_TICKER);;
        }catch (ClassCastException e){
            logger.error("Invalid arguments in the "+ARGUMENT_TICKER+" key.");
            throw new IllegalArgumentException("Method arguments map must have a valid String in the "+ARGUMENT_TICKER+ " key.");
        }

        if(isType(ConditionType.ALL)){
            return true;
        }else if(isType(ConditionType.EXACT)){
            if(isType(ConditionType.INCLUDE)){
                return getDataValueHolderTickers().contains(chosenTicker);
            }else if(isType(ConditionType.EXCLUDE)){
                return !getDataValueHolderTickers().contains(chosenTicker);
            }
        }else if(isType(ConditionType.LIKE)){
            throw new NotImplementedException();
        }else{
            throw new IllegalArgumentException("Invalid or Null Condition Type");
        }
        return false;
    }

}
