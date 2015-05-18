package com.witbooking.middleware.model.dynamicPriceVariation;

import com.witbooking.middleware.utils.DateUtil;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.mvel2.MVEL;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mongoose on 9/29/14.
 */
public class BookingPriceRule implements Evaluable, Serializable, Comparable {

    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(BookingPriceRule.class);

    private Long id;
    private String ticker;
    private String formula;
    private RulePriority rulePriority;
    private Double priceVariation;
    private boolean percentage;
    private boolean stackable;
    private boolean active;
    private List<Condition> conditions;
    private int order;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public String getFormula() {
        return formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public RulePriority getRulePriority() {
        return rulePriority;
    }

    public void setRulePriority(RulePriority rulePriority) {
        this.rulePriority = rulePriority;
    }

    public Double getPriceVariation() {
        return priceVariation;
    }

    public void setPriceVariation(Double priceVariation) {
        this.priceVariation = priceVariation;
    }

    public boolean isPercentage() {
        return percentage;
    }

    public void setPercentage(boolean percentage) {
        this.percentage = percentage;
    }

    public boolean isStackable() {
        return stackable;
    }

    public void setStackable(boolean stackable) {
        this.stackable = stackable;
    }

    public List<Condition> getConditions() {
        return conditions;
    }

    public void setConditions(List<Condition> conditions) {
        this.conditions = conditions;
    }

    public TickerCondition getTickerCondition() {
        for(Condition condition: conditions){
            if(condition.getClass() == TickerCondition.class){
                return  (TickerCondition)condition;
            }
        }
        return null;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

/*TODO: An improvement to this class in general would be to separate one-time evaluations
    * like WeekDayCondition, DateTimeCondition, CountryCondition, CodeCondition from per ticker
    * conditions like TickerConditions*/

    @Override
    public Boolean evaluate(Map<String, Object> arguments) {

        Map<String,Boolean> expressionPredicates = new HashMap();

        for(Condition condition: getConditions()){
            String conditionID=Condition.CONDITION_ID_PREFIX+condition.getId();
            if(condition.getClass()==TickerCondition.class){
                expressionPredicates.put(conditionID, true);
                continue;
            }

            boolean ruleEvaluation=condition.evaluate(arguments);
            String conditionInfo=null;
            String argumentInfo=null;
            if(condition.getClass().equals(WeekDayCondition.class)){
                conditionInfo=((WeekDayCondition)condition).getDays().toString();
                if(condition.isType(ConditionType.CONTRACT)){
                    argumentInfo=WeekDayCondition.WeekDay.getDay( ( (DateTime) arguments.get(DatetimeCondition.ARGUMENT_CURRENT_TIME)).getDayOfWeek()).toString();
                }else if(condition.isType(ConditionType.STAY)){
                    argumentInfo=arguments.get(WeekDayCondition.ARGUMENT_WEEK_DAY).toString();
                }
            }else if(condition.getClass().equals(HourRangeCondition.class)){
                conditionInfo=((HourRangeCondition)condition).getStart().toString()+" - ";
                conditionInfo+=((HourRangeCondition)condition).getEnd().toString();
                argumentInfo= DateTimeFormat.forPattern("HH:mm:ss").print(((DateTime) arguments.get(DatetimeRangeCondition.ARGUMENT_CURRENT_TIME)));;
            }else if(condition.getClass().equals(DatetimeRangeCondition.class)){
                conditionInfo= DateUtil.timestampFormat(((DatetimeRangeCondition) condition).getStart().toDate())+" - ";
                conditionInfo+=DateUtil.timestampFormat(((DatetimeRangeCondition) condition).getEnd().toDate());
                if(condition.isType(ConditionType.CONTRACT)){
                    argumentInfo=DateUtil.timestampFormat(((DateTime) arguments.get(DatetimeRangeCondition.ARGUMENT_CURRENT_TIME)).toDate());
                }else if(condition.isType(ConditionType.STAY)){
                    argumentInfo=DateUtil.timestampFormat(((DateTime)arguments.get(DatetimeRangeCondition.ARGUMENT_COMPARISON_DATE)).toDate());
                }
            }else if(condition.getClass().equals(CountryOfOriginCondition.class)){
                conditionInfo=((CountryOfOriginCondition)condition).getCountries().toString();
                argumentInfo=arguments.get(CountryOfOriginCondition.ARGUMENT_COUNTRY).toString();
            }else if(condition.getClass().equals(CodeCondition.class)){
                conditionInfo=((CodeCondition)condition).getSupportedCodes().toString();
                argumentInfo=arguments.get(CodeCondition.ARGUMENT_CODE).toString();
            }

            if(ruleEvaluation){
                logger.debug("Passed Condition '" + condition.getClass().getSimpleName() + "' for type: " + conditionInfo + "  " + condition.getConditionType() + "  with arguments " + argumentInfo);
            }else{
                logger.debug("Failed Condition '" + condition.getClass().getSimpleName() + "' for type: " + conditionInfo + "  " + condition.getConditionType() + "  with arguments " + argumentInfo);
            }
            expressionPredicates.put(conditionID, condition.evaluate(arguments));
        };

        Boolean result = (Boolean) MVEL.eval(formula,expressionPredicates);

        return result;
    }


    @Override
    public int compareTo(Object o) {
        BookingPriceRule otherBookingPriceRule=(BookingPriceRule) o;
        return this.getOrder()-otherBookingPriceRule.getOrder();
    }

}