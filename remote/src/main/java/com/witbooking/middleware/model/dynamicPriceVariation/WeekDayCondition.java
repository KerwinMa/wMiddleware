package com.witbooking.middleware.model.dynamicPriceVariation;

import com.witbooking.middleware.exceptions.model.IncompatibleTypesException;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * Created by mongoose on 9/29/14.
 */
public class WeekDayCondition extends Condition implements Serializable {

    public final static String ARGUMENT_WEEK_DAY = "argument_week_day";

    private static final Logger logger = Logger.getLogger(WeekDayCondition.class);

    private static List<String> REQUIRED_ARGUMENTS= Arrays.asList(ARGUMENT_WEEK_DAY, DatetimeCondition.ARGUMENT_CURRENT_TIME);

    private static HashSet<ConditionType> VALID_CONDITIONS= new HashSet<ConditionType>(Arrays.asList(ConditionType.INCLUDE, ConditionType.EXCLUDE, ConditionType.STAY, ConditionType.CONTRACT));


    public enum WeekDay implements Serializable {

        NONE_TYPE(-1), MONDAY(1), TUESDAY(2), WEDNESDAY(3), THURSDAY(4), FRIDAY(5), SATURDAY(6),SUNDAY(7);
        int value;

        private WeekDay(int value) {
            this.value = value;
        }

        //This is how get values id from DB version 6.1
        public static WeekDay getDay(int value) {
            switch (value) {
                case 1:
                    return MONDAY;
                case 2:
                    return TUESDAY;
                case 3:
                    return WEDNESDAY;
                case 4:
                    return THURSDAY;
                case 5:
                    return FRIDAY;
                case 6:
                    return SATURDAY;
                case 7:
                    return SUNDAY;
                default:
                    return NONE_TYPE;
            }
        }

        //Values id from DB version 6.1
        public int getValue() {
            return value;
        }

        public static WeekDay getFromValue(int value) {
            for(WeekDay e: WeekDay.values()) {
                if(e.value == value) {
                    return e;
                }
            }
            return null;
        }
    }

    private HashSet<WeekDay> days;

    @Override
    public void addConditionType(ConditionType conditionType, boolean value) throws IncompatibleTypesException {
        super.addConditionType(conditionType, value,VALID_CONDITIONS);
    }

    public HashSet<WeekDay> getDays() {
        return days;
    }

    public void setDays(HashSet<WeekDay> days) {
        this.days = days;
    }

    /**
     * Checks if the given day is an allowed day by belonging
     * to the days List of the condition
     * @param arguments Argument map, in this particular case
     *                  the only key is the ARGUMENT_WEEK_DAY
     *                  key, which will provide the current (JODA) Day of Week
     *                  if (condition is a contract) or a Day of Week, corresponding
     *                  to the reservation Date (condition is Stay type) in UTC.
     * @return Boolean according to agreeing or not with the evaluation criteria
     */
    @Override
    public Boolean evaluate(Map<String, Object> arguments) {
        if(!super.evaluate(arguments)){
            return false;
        }
        super.validateArguments(arguments,REQUIRED_ARGUMENTS);
        WeekDay chosenWeekDay=null;
        try{
            if(isType(ConditionType.CONTRACT)){
                chosenWeekDay = WeekDayCondition.WeekDay.getDay( ( (DateTime) arguments.get(DatetimeCondition.ARGUMENT_CURRENT_TIME)).getDayOfWeek());
            }else if  (isType(ConditionType.STAY)){
                chosenWeekDay=(WeekDay)arguments.get(ARGUMENT_WEEK_DAY);
            }
        }catch (ClassCastException e){
            logger.error("Invalid arguments in the "+ARGUMENT_WEEK_DAY+" key.");
            throw new IllegalArgumentException("Method arguments map must have a valid DateTime in the "+ARGUMENT_WEEK_DAY+ " key.");
        }

        return isType(ConditionType.INCLUDE) ? getDays().contains(chosenWeekDay): !getDays().contains(chosenWeekDay);
    }

}
