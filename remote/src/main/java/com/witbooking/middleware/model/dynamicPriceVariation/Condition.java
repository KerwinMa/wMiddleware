package com.witbooking.middleware.model.dynamicPriceVariation;

import com.witbooking.middleware.exceptions.model.IncompatibleTypesException;

import java.io.Serializable;
import java.util.*;

/**
 * Created by mongoose on 9/29/14.
 */
public abstract class Condition implements Evaluable, Serializable {
/**/
    private Integer id;
    private String ticker;
    private Map<ConditionType,Boolean> conditionType;
    private String formula;
    public static final List<String> EMPTY_STRING_LIST = new ArrayList<String>();
    private transient List<String> requiredArguments= Condition.EMPTY_STRING_LIST;
    private static HashSet<ConditionType> VALID_CONDITIONS= new HashSet<ConditionType>();
    public static final String CONDITION_ID_PREFIX = "c";

    /**
     * Constant serialized ID used for compatibility.
     */
    private static final long serialVersionUID = 1L;

    protected Condition() {

    }

    public String getTicker() {
        return CONDITION_ID_PREFIX+getId();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Map<ConditionType, Boolean> getConditionType() {
        return conditionType;
    }

    public void isValidConditionType(ConditionType conditionType, boolean value, HashSet<ConditionType> validConditions) throws IncompatibleTypesException {
        if(value && !validConditions.contains(conditionType) ){
            throw new IncompatibleTypesException("Invalid type for condition. Accepted values are: "+validConditions);
        }

        if(conditionType==ConditionType.EXCLUDE && value && isType(ConditionType.INCLUDE)){
            throw new IncompatibleTypesException("Cannot add both EXCLUDE and INCLUDE type");
        }else if(conditionType==ConditionType.INCLUDE && value && isType(ConditionType.EXCLUDE)){
            throw new IncompatibleTypesException("Cannot add both EXCLUDE and INCLUDE type");
        }
    }

    public void addConditionType(ConditionType conditionType,boolean value,HashSet<ConditionType> validConditions) throws IncompatibleTypesException{
        if(this.conditionType == null){
            this.conditionType=new HashMap<ConditionType,Boolean>();
        }
        isValidConditionType(conditionType,value,validConditions);
        this.conditionType.put(conditionType,value);
    }

    public void addConditionType(ConditionType conditionType,boolean value) throws IncompatibleTypesException{
        this.addConditionType(conditionType,value,VALID_CONDITIONS);
    }


    public String getFormula() {
        return formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public List<String> getRequiredArguments() {
        return requiredArguments;
    }

    public boolean isType(ConditionType ct){
        if (getConditionType().containsKey(ct)){
            return getConditionType().get(ct);
        }
        return false;
    }

    public void validateArguments(Map<String, Object> arguments,List<String> requiredArguments) throws IllegalArgumentException{
        if(arguments==null){
            throw new NullPointerException("Method arguments cannot be null ");
        }
        for (String argumentName : requiredArguments) {
            if(!arguments.containsKey(argumentName)){
                throw new IllegalArgumentException("Method arguments map must have a valid DateTime in the "+argumentName+ " key.");
            }
        }
    }

    @Override
    public Boolean evaluate(Map<String, Object> arguments) {
        this.validateArguments(arguments,getRequiredArguments());
        return true;
    }
}