package com.witbooking.middleware.model.dynamicPriceVariation;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.io.Serializable;
import java.util.*;

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
        super.validateArguments(arguments, REQUIRED_ARGUMENTS);

        //Getting the Timezone object from this condition Identifier
        //Because of DST's and such I need to get the timezone everytime
        //due to offsets not being fixed for a given timezone
        DateTimeZone conditionTimezone=DateTimeZone.forID(getTimezone());

        //The start date is saved with UTC timezone in mind, but the corresponding values for date time
        //apply to the user selected timezone, so we must first restore this date to UTC and then
        //switch it back to the users corresponding timezone, to finally restore it to an UTC common ground
        DateTime conditionStart = null;
        DateTime conditionEnd   = null;

        DateTime comparisonDateTimeUTC=null;
        try {
            if(isType(ConditionType.CONTRACT)){
                comparisonDateTimeUTC = ((DateTime) arguments.get(ARGUMENT_CURRENT_TIME));
                conditionStart = getStart().withZone(DateTimeZone.UTC).withZoneRetainFields(conditionTimezone).withZone(DateTimeZone.UTC);
                conditionEnd   = getEnd().withZone(DateTimeZone.UTC).withZoneRetainFields(conditionTimezone).withZone(DateTimeZone.UTC);
            }else if  (isType(ConditionType.STAY)){
                comparisonDateTimeUTC = ((DateTime) arguments.get(ARGUMENT_COMPARISON_DATE));
                conditionStart = getStart().withZone(DateTimeZone.UTC).withZoneRetainFields(conditionTimezone).dayOfMonth().roundFloorCopy().withZoneRetainFields(DateTimeZone.UTC);
                conditionEnd   = getEnd().withZone(DateTimeZone.UTC).withZoneRetainFields(conditionTimezone).dayOfMonth().roundFloorCopy().withZoneRetainFields(DateTimeZone.UTC);
            }
        }catch (ClassCastException e){
            logger.error("Invalid arguments in the "+ARGUMENT_COMPARISON_DATE+" key.");
            throw new IllegalArgumentException("Method arguments map must have a valid DateTime in the "+ARGUMENT_COMPARISON_DATE+ " key.");
        }

        return ( comparisonDateTimeUTC.isEqual(conditionStart) || comparisonDateTimeUTC.isEqual(conditionEnd) ) ||  comparisonDateTimeUTC.isAfter(conditionStart) && comparisonDateTimeUTC.isBefore(conditionEnd);
    }
}
