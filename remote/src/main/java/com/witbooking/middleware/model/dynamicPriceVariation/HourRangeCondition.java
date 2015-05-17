package com.witbooking.middleware.model.dynamicPriceVariation;

import com.witbooking.middleware.exceptions.model.RangeException;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalTime;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


/**
 * <h1>Hour Range Condition</h1>
 * The HourRangeCondition class describes the validation
 * method to use when creating a condition based on an specific
 * range of hours through the day
 *
 *
 * @author Jorge Lucic
 * @version 1.0
 * @date 11/22/14
 * @since 1.0
 */
public class HourRangeCondition extends DatetimeCondition implements Serializable {

    private LocalTime start;
    private LocalTime end;
    private static final Logger logger = Logger.getLogger(HourRangeCondition.class);
    private static List<String> REQUIRED_ARGUMENTS= Arrays.asList(ARGUMENT_CURRENT_TIME);;


    /**
     * Constant serialized ID used for compatibility.
     */
    private static final long serialVersionUID = 1L;

    public LocalTime getStart() {
        return start;
    }

    public void setStart(LocalTime start) throws RangeException{
        super.setStart(start);
        this.start = start;
    }

    public LocalTime getEnd() {
        return end;
    }

    public void setEnd(LocalTime end) throws RangeException {
        super.setEnd(end);
        this.end = end;
    }

    /**
     * The evaluate method compares the current time
     * and check if it's in the range defined by the
     * start/end params
     * @param arguments Argument map, in this particular case
     *                  the only key is the ARGUMENT_CURRENT_TIME
     *                  key, defined in the parent class, which
     *                  will provide the current (JODA) DateTime
     *                  in UTC
     * @return Boolean according to agreeing or not with the evaluation criteria
     */
    @Override
    public Boolean evaluate(Map<String,Object> arguments) {
        if(!super.evaluate(arguments)){
            return false;
        }
        super.validateArguments(arguments,REQUIRED_ARGUMENTS);

        if (isType(ConditionType.CONTRACT)){

            /*Get Local Timezone configured with the rule */
            DateTimeZone tz = DateTimeZone.forID(getTimezone());

            /*There is no need to Convert start and end hours to UTC  */
            LocalTime startLocalTimeUTC=getStart();
            LocalTime endDateTimeUTC=getEnd();

            /*Get currentTimezone in UTC*/
            LocalTime currentLocalTimeUTC=null;
            try {
                currentLocalTimeUTC = ((DateTime) arguments.get(ARGUMENT_CURRENT_TIME)).toLocalTime();
            }catch (ClassCastException e){
                logger.error("Invalid arguments in the "+ARGUMENT_CURRENT_TIME+" key.");
                throw new IllegalArgumentException("Method arguments map must have a valid DateTime in the "+ARGUMENT_CURRENT_TIME+ " key.");
            }

            /*Compare current time with hour ranges in UTC */
            return currentLocalTimeUTC.isAfter(startLocalTimeUTC) && currentLocalTimeUTC.isBefore(endDateTimeUTC);

        }else if (isType(ConditionType.STAY)){
            /*TODO: IMPLEMENT ON SPECIFIC CHECK-IN / CHECK-OUT HOURS*/
            return true;
        }
        return false;
    }

}