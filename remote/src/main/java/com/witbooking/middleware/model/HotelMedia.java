package com.witbooking.middleware.model;

import com.google.common.base.Objects;
import com.witbooking.middleware.db.DAOUtil;
import com.witbooking.middleware.db.DBConnection;
import com.witbooking.middleware.db.handlers.HotelConfigurationDBHandler;
import com.witbooking.middleware.exceptions.ExternalFileException;
import com.witbooking.middleware.exceptions.NonexistentValueException;
import com.witbooking.middleware.exceptions.db.DBAccessException;
import com.witbooking.middleware.resources.DBProperties;

import java.io.Serializable;

/**
 * HotelMedia.java
 * User: jose
 * Date: 11/28/13
 * Time: 8:59 AM
 */
public class HotelMedia implements Serializable {

    private Integer idEntity, id;
    private String hotelTicker;
    private String title;
    private String description;
    private String fileName;
    private String entity;

    public HotelMedia(String hotelTicker, Integer id, String entity, Integer idEntity, String fileName,
                      String title, String description) {
        this.idEntity = idEntity;
        this.id = id;
        this.hotelTicker = hotelTicker;
        this.title = title;
        this.description = description;
        this.fileName = fileName;
        this.entity = entity;
    }

    public HotelMedia(String hotelTicker, String entity, Integer idEntity, String fileName,
                      String title, String description) {
        this.idEntity = idEntity;
        this.title = title;
        this.description = description;
        this.fileName = fileName;
        this.hotelTicker = hotelTicker;
        this.entity = entity;
    }

    /**
     * @throws NonexistentValueException
     * @throws ExternalFileException
     * @throws DBAccessException
     */
    public void save() throws NonexistentValueException, ExternalFileException, DBAccessException {
        if (hotelTicker == null) {
            throw new NonexistentValueException(new NullPointerException("hotelTicker is null."));
        }
        final DBConnection dbConnection = new DBConnection(DBProperties.getDBCustomerByTicker(hotelTicker));
        try {
            save(dbConnection);
        } finally {
            DAOUtil.close(dbConnection);
        }
    }

    public void save(DBConnection dbConnection) throws DBAccessException, NonexistentValueException {
        if (dbConnection != null) {
            save(new HotelConfigurationDBHandler(dbConnection));
        } else {
            throw new NonexistentValueException(new NullPointerException("dbConnection is null."));
        }

    }

    public void save(final HotelConfigurationDBHandler hotelConfigurationDBHandler) throws DBAccessException, NonexistentValueException {
        if (hotelConfigurationDBHandler != null) {
            hotelConfigurationDBHandler.saveMediaHotel(this);
        } else {
            throw new NonexistentValueException(new NullPointerException("hotelConfigurationDBHandler is null."));
        }
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public String getHotelTicker() {
        return hotelTicker;
    }

    public void setHotelTicker(String hotelTicker) {
        this.hotelTicker = hotelTicker;
    }

    public Integer getIdEntity() {
        return idEntity;
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getFileName() {
        return fileName;
    }

    public void setIdEntity(int idEntity) {
        this.idEntity = idEntity;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("idEntity", idEntity)
                .add("id", id)
                .add("hotelTicker", hotelTicker)
                .add("title", title)
                .add("description", description)
                .add("fileName", fileName)
                .add("entity", entity)
                .toString();
    }
}