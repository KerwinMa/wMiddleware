package com.witbooking.middleware.model.dynamicPriceVariation;

import com.witbooking.middleware.exceptions.model.IncompatibleTypesException;
import org.apache.log4j.Logger;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * Created by mongoose on 9/29/14.
 */
public class CodeCondition extends Condition implements Serializable  {

    private static final Logger logger = Logger.getLogger(HourRangeCondition.class);

    public final static String ARGUMENT_CODE= "argument_code";

    private static List<String> REQUIRED_ARGUMENTS= Arrays.asList(ARGUMENT_CODE);

    private static HashSet<ConditionType> VALID_CONDITIONS= new HashSet<ConditionType>(Arrays.asList(ConditionType.EXACT));

    /**
     * Constant serialized ID used for compatibility.
     */
    private static final long serialVersionUID = 1L;

    private HashSet<String> supportedCodes;

    @Override
    public void addConditionType(ConditionType conditionType, boolean value) throws IncompatibleTypesException {
        super.addConditionType(conditionType, value,VALID_CONDITIONS);
    }

    public HashSet<String> getSupportedCodes() {
        return supportedCodes;
    }

    public void setSupportedCodes(HashSet<String> supportedCodes) {
        this.supportedCodes = supportedCodes;
    }

    @Override
    public Boolean evaluate(Map<String, Object> arguments) {
        if(!super.evaluate(arguments)){
            return false;
        }
        super.validateArguments(arguments,REQUIRED_ARGUMENTS);

        String givenCode=null;
        try {
            givenCode = (String)arguments.get(ARGUMENT_CODE);;
        }catch (ClassCastException e){
            logger.error("Invalid arguments in the "+ARGUMENT_CODE+" key.");
            throw new IllegalArgumentException("Method arguments map must have a valid String in the "+ARGUMENT_CODE+ " key.");
        }

        if(isType(ConditionType.EXACT)){
            return getSupportedCodes().contains(givenCode);
        }else{
            throw new IllegalArgumentException("Invalid or Null Condition Type");
        }

    }

}
