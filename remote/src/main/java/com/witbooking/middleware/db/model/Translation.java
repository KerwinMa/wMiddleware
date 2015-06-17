package com.witbooking.middleware.db.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Translation.
 */
@Entity
@Table(name = "i18n")
public class Translation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(max = 6)
    @Column(name = "locale", length = 6, nullable = false)
    private String locale;

    @NotNull
    @Size(max = 255)
    @Column(name = "model", length = 255, nullable = false)
    private String model;

    @NotNull
    @Column(name = "foreign_key", nullable = false)
    private String foreign_key;

    @NotNull
    @Size(max = 255)
    @Column(name = "field", length = 255, nullable = false)
    private String field;

    @NotNull
    @Column(name = "content", nullable = true, columnDefinition = "TEXT")
    private String content;

/*
    @Column(name = "lastmodified")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastmodified;
*/


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getForeign_key() {
        return foreign_key;
    }

    public void setForeign_key(String foreign_key) {
        this.foreign_key = foreign_key;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

/*    public Date getLastmodified() {
        return lastmodified;
    }

    public void setLastmodified(Date lastmodified) {
        this.lastmodified = lastmodified;
    }*/

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Translation translation = (Translation) o;

        if (!Objects.equals(id, translation.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Translation{" +
                "id=" + id +
                ", locale='" + locale + "'" +
                ", model='" + model + "'" +
                ", foreign_key='" + foreign_key + "'" +
                ", field='" + field + "'" +
                ", content='" + content + "'" +
/*
                ", lastmodified='" + lastmodified + "'" +
*/
                '}';
    }
}
