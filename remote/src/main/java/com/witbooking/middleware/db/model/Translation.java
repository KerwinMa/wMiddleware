package com.witbooking.middleware.db.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.witbooking.middleware.db.model.util.CustomDateTimeDeserializer;
import com.witbooking.middleware.db.model.util.CustomDateTimeSerializer;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;


/**
 * A FrontEndMessage.
 */

@Entity
@Table(name = "i18n")
/*@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)*/
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
    @Column(name = "foreign_key")
    private String referencedId;

    @NotNull
    @Size(max = 255)
    @Column(name = "field", length = 255, nullable = false)
    private String field;

    @NotNull
    @Column(name = "field", nullable = true,columnDefinition = "TEXT")
    private String content;

    @Column(name = "lastmodified")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastmodified;


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

    public String getReferencedId() {
        return referencedId;
    }

    public void setReferencedId(String referencedId) {
        this.referencedId = referencedId;
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

    public Date getLastmodified() {
        return lastmodified;
    }

    public void setLastmodified(Date lastmodified) {
        this.lastmodified = lastmodified;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Translation translation = (Translation) o;

        if ( ! Objects.equals(id, translation.id)) return false;

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
                ", locale='" + locale + '\'' +
                ", model='" + model + '\'' +
                ", referencedId='" + referencedId + '\'' +
                ", field='" + field + '\'' +
                ", content='" + content + '\'' +
                ", lastmodified=" + lastmodified +
                '}';
    }
}
