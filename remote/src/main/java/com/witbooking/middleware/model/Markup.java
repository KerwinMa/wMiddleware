package com.witbooking.middleware.model;

import java.io.Serializable;

/**
 * Created by mongoose on 3/17/15.
 */
public class Markup implements Serializable {

    public static enum Phase {
        STEP1, STEP2, STEP3, ALL;

        public static Phase getTypeFromSqlString(final String type) {
            return "step1".equalsIgnoreCase(type)
                    ? STEP1
                    : "step2".equalsIgnoreCase(type)
                    ? STEP2
                    : "step3".equalsIgnoreCase(type)
                    ? STEP3
                    : "confirmation".equalsIgnoreCase(type)
                    ? STEP3
                    : "all".equalsIgnoreCase(type)
                    ? ALL
                    : null;
        }
    }

    public static enum Position {
        HEAD,FIRST_BODY,END_BODY;

        public static Position getPositionFromSqlString(final String position) {
            return "head".equalsIgnoreCase(position)
                    ? HEAD
                    : "first_body".equalsIgnoreCase(position)
                    ? FIRST_BODY
                    : "end_body".equalsIgnoreCase(position)
                    ? END_BODY
                    : null;
        }
    }

    private static final long serialVersionUID = 1L;
    private Integer id;
    private String name;
    private String code;
    private Position position;
    private Phase phase;
    private boolean oneTime;
    private boolean active;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Phase getPhase() {
        return phase;
    }

    public void setPhase(Phase phase) {
        this.phase = phase;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public boolean isOneTime() {
        return oneTime;
    }

    public void setOneTime(boolean oneTime) {
        this.oneTime = oneTime;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
