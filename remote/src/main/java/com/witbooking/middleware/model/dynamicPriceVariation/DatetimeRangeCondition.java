package com.witbooking.middleware.model.dynamicPriceVariation;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by mongoose on 9/29/14.
 */
public class DatetimeRangeCondition  extends DatetimeCondition implements Serializable {

    private DateTime start;
    private DateTime end;
    public final static String ARGUMENT_COMPARISON_DATE = "argument_comparison_date";
    private static final Logger logger = Logger.getLogger(DatetimeRangeCondition.class);
    private static List<String> REQUIRED_ARGUMENTS= Arrays.asList(ARGUMENT_COMPARISON_DATE);

    /**
     * Constant serialized ID used for compatibility.
     */
    private static final long serialVersionUID = 1L;

    public DateTime getStart() {
        return start;
    }

    public void setStart(DateTime start) {
        super.setStart(start);
        this.start = start;
    }

    public DateTime getEnd() {
        return end;
    }

    public void setEnd(DateTime end) {
        super.setEnd(end);
        this.end = end;
    }


    /**
     * The evaluate method compares the given time
     * and check if it's in the range defined by the
     * start/end params
     * @param arguments Argument map, in this particular case
     *                  the only key is the ARGUMENT_COMPARISON_DATE
     *                  key, which will provide the current (JODA) DateTime
     *                  or a specific reservation DateTime, depending
     *                  on the Condition type (Contract/Stay) in UTC.
     * @return Boolean according to agreeing or not with the evaluation criteria
     */
    @Override
    public Boolean evaluate(Map<String,Object> arguments) {
        if(!super.evaluate(arguments)){
            return false;
        }
        super.validateArguments(arguments,REQUIRED_ARGUMENTS);

        DateTime comparisonDateTimeUTC=null;
        try {
            if(isType(ConditionType.CONTRACT)){
                comparisonDateTimeUTC = ((DateTime) arguments.get(ARGUMENT_CURRENT_TIME));
            }else if  (isType(ConditionType.STAY)){
                comparisonDateTimeUTC = ((DateTime) arguments.get(ARGUMENT_COMPARISON_DATE));
            }
        }catch (ClassCastException e){
            logger.error("Invalid arguments in the "+ARGUMENT_COMPARISON_DATE+" key.");
            throw new IllegalArgumentException("Method arguments map must have a valid DateTime in the "+ARGUMENT_COMPARISON_DATE+ " key.");
        }

        return comparisonDateTimeUTC.isAfter(getStart()) && comparisonDateTimeUTC.isBefore(getEnd());
    }
}
