package com.witbooking.middleware.model.dynamicPriceVariation;

import com.witbooking.middleware.exceptions.model.IncompatibleTypesException;
import com.witbooking.middleware.exceptions.model.RangeException;
import org.joda.time.LocalTime;
import org.joda.time.ReadableInstant;
import org.joda.time.ReadablePartial;
import org.joda.time.base.AbstractInstant;
import org.joda.time.base.AbstractPartial;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * Created by mongoose on 9/29/14.
 */
public abstract class DatetimeCondition extends  Condition implements Serializable {
/**/
    private String timezone;
    transient private Comparable start;
    transient private Comparable end;
    public final static String ARGUMENT_CURRENT_TIME= "argument_current_time";
    private static List<String> REQUIRED_ARGUMENTS= Condition.EMPTY_STRING_LIST;
    private static HashSet<ConditionType> VALID_CONDITIONS= new HashSet<ConditionType>(Arrays.asList(ConditionType.INCLUDE,ConditionType.STAY,ConditionType.CONTRACT));


    public void setStart(Comparable start) throws RangeException {
        if((end!=null && end.compareTo(start)<0 ) || start==null ){
            throw new RangeException(" The start param must be a valid LocalTime and must be less than the end property");
        }
        this.start = start;
    }

    public void setEnd(Comparable end) throws RangeException {
        if((start!=null && start.compareTo(end)>0 ) || end==null ){
            throw new RangeException(" The start param must be a valid LocalTime and must be less than the end property");
        }
        this.end = end;
    }

    @Override
    public void addConditionType(ConditionType conditionType, boolean value) throws IncompatibleTypesException {
        super.addConditionType(conditionType, value,VALID_CONDITIONS);
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    @Override
    public Boolean evaluate(Map<String, Object> arguments) {
        if(!super.evaluate(arguments)){
            return false;
        }
        super.validateArguments(arguments, REQUIRED_ARGUMENTS);
        return true;
    }

}