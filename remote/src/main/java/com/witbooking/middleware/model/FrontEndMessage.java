package com.witbooking.middleware.model;

import com.google.common.base.Objects;

import java.io.Serializable;
import java.util.Date;

/**
 * FrontEndMessage.java
 * User: jose
 * Date: 2/17/14
 * Time: 11:59 AM
 */
public class FrontEndMessage implements Serializable {

    public static enum Type {
        INFO, NOTICE, ERROR, SUCCESS;


        public static Type getTypeFromSqlString(final String type) {
            return "info".equalsIgnoreCase(type)
                    ? INFO
                    : "notice".equalsIgnoreCase(type)
                    ? NOTICE
                    : "sucess".equalsIgnoreCase(type)
                    ? SUCCESS
                    : "error".equalsIgnoreCase(type)
                    ? ERROR
                    : null;
        }
    }

    public static enum Position {
        TOP_INVENTORY_STEP_1, BELOW_LOGO_STEP_1, BELOW_PERSONAL_INFO_STEP_2, TOP_METHOD_PAYMENT_STEP_2,
        MODAL_STEP_1, MODAL_STEP_2;

        public static Position getPositionFromSqlString(final String sqlName) {
            return "antes-lista-alojamientos".equals(sqlName)
                    ? TOP_INVENTORY_STEP_1
                    : "antes-certificado".equals(sqlName)
                    ? BELOW_LOGO_STEP_1
                    : "paso2-mensaje-1".equals(sqlName)
                    ? BELOW_PERSONAL_INFO_STEP_2
                    : "paso2-condiciones".equals(sqlName)
                    ? TOP_METHOD_PAYMENT_STEP_2
                    : "modal-step1".equals(sqlName)
                    ? MODAL_STEP_1
                    : "modal-step2".equals(sqlName)
                    ? MODAL_STEP_2
                    : null;
        }
    }

    private Integer id;
    private String username, editedName,
    //position , type,
    description;
    /**
     * Field not used.
     */
    private String title;

    private Position position;
    private Type type;

    private Boolean hidden, unavailable;

    private Date start, end, creation, lastModification;


    public FrontEndMessage(Integer id, String username, String editedName, String description, String title, Position position, Type type, Boolean hidden, Boolean unavailable, Date start, Date end, Date creation, Date lastModification) {
        this.id = id;
        this.username = username;
        this.editedName = editedName;
        this.description = description;
        this.title = title;
        this.position = position;
        this.type = type;
        this.hidden = hidden;
        this.start = start;
        this.end = end;
        this.creation = creation;
        this.lastModification = lastModification;
        this.unavailable = unavailable;
    }

    public Boolean getUnavailable() {
        return unavailable;
    }

    public void setUnavailable(Boolean unavailable) {
        this.unavailable = unavailable;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Boolean getHidden() {
        return hidden;
    }

    public void setHidden(Boolean hidden) {
        this.hidden = hidden;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public Date getCreation() {
        return creation;
    }

    public void setCreation(Date creation) {
        this.creation = creation;
    }

    public Date getLastModification() {
        return lastModification;
    }

    public void setLastModification(Date lastModification) {
        this.lastModification = lastModification;
    }

    public String getEditedName() {
        return editedName;
    }

    public void setEditedName(String editedName) {
        this.editedName = editedName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("id", id)
                .add("username", username)
                .add("editedName", editedName)
                .add("description", description)
                .add("title", title)
                .add("position", position)
                .add("type", type)
                .add("hidden", hidden)
                .add("unavailable", unavailable)
                .add("start", start)
                .add("end", end)
                .add("creation", creation)
                .add("lastModification", lastModification)
                .toString();
    }
}