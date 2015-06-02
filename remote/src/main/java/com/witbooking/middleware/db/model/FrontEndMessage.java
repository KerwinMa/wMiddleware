package com.witbooking.middleware.db.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.witbooking.middleware.db.model.util.CustomDateTimeDeserializer;
import com.witbooking.middleware.db.model.util.CustomDateTimeSerializer;
import org.hibernate.annotations.*;
import org.hibernate.annotations.Cache;
import org.joda.time.DateTime;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


/**
 * A FrontEndMessage.
 */

@Entity
@Table(name = "mensajes")
/*@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)*/
public class FrontEndMessage implements Serializable {

    public String model="Mensaje";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(max = 90)
    @Column(name = "username", length = 90, nullable = false)
    private String username;

    @NotNull
    @Size(max = 90)
    @Column(name = "editedname", length = 90, nullable = false)
    private String editedName;

    @Column(name = "descripcion")
    private String descripcion;

    @Size(max = 100)
    @Column(name = "titulo", length = 100)
    private String titulo;

    @Size(max = 100)
    @Column(name = "seccion", length = 100)
    private String seccion;

    @Size(max = 7)
    @Column(name = "tipo", length = 7)
    private String tipo;

    @Column(name = "visible")
    private Boolean visible;

    @Column(name = "alojamientos")
    private Boolean unavailable;

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @Column(name = "fechainicio")
    private DateTime fechainicio;

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @Column(name = "fechafin")
    private DateTime fechafin;

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @Column(name = "fcreacion")
    private DateTime fcreation;

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @Column(name = "fmodificacion")
    private DateTime fmodification;

    @OneToMany()
    @JoinColumns({
            @JoinColumn(name = "id", referencedColumnName = "foreign_key"),
            @JoinColumn(name = "model", referencedColumnName = "model")
    })
    private Set<Translation> translations = new HashSet<>();



    public FrontEndMessage() {

    }

    public FrontEndMessage(com.witbooking.middleware.model.FrontEndMessage frontEndMessage) {
        this.id=new Long(frontEndMessage.getId());
        this.username =frontEndMessage.getUsername();
        this.editedName =frontEndMessage.getEditedName();
        this.descripcion =frontEndMessage.getDescription();
        this.titulo = null;
        this.seccion =frontEndMessage.getPosition().name();
        this.tipo =frontEndMessage.getType().name();
        this.visible =frontEndMessage.getHidden();
        this.unavailable =frontEndMessage.getUnavailable();
        this.fechainicio =new DateTime(frontEndMessage.getStart());
        this.fechafin =new DateTime(frontEndMessage.getEnd());
        this.fcreation =new DateTime(frontEndMessage.getCreation());
        this.fmodification =new DateTime(frontEndMessage.getLastModification());
    }

    public com.witbooking.middleware.model.FrontEndMessage convertToBusinessObject(){
        com.witbooking.middleware.model.FrontEndMessage frontEndMessage= new com.witbooking.middleware.model.FrontEndMessage();
        frontEndMessage.setId(this.id.intValue());
        frontEndMessage.setUsername(this.username);
        frontEndMessage.setEditedName(this.editedName);
        frontEndMessage.setDescription(this.descripcion);
        frontEndMessage.setPosition(com.witbooking.middleware.model.FrontEndMessage.Position.getPositionFromSqlString(this.seccion));
        frontEndMessage.setType(com.witbooking.middleware.model.FrontEndMessage.Type.getTypeFromSqlString(this.tipo));
        frontEndMessage.setHidden(this.visible );
        frontEndMessage.setUnavailable(this.unavailable );
        frontEndMessage.setStart(this.fechainicio.toDate() );
        frontEndMessage.setEnd(this.fechafin.toDate() );
        frontEndMessage.setCreation(this.fcreation.toDate() );
        frontEndMessage.setLastModification(this.fmodification.toDate());
        return frontEndMessage;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEditedName() {
        return editedName;
    }

    public void setEditedName(String editedName) {
        this.editedName = editedName;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getSeccion() {
        return seccion;
    }

    public void setSeccion(String seccion) {
        this.seccion = seccion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Boolean getVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public Boolean getUnavailable() {
        return unavailable;
    }

    public void setUnavailable(Boolean unavailable) {
        this.unavailable = unavailable;
    }

    public DateTime getFechainicio() {
        return fechainicio;
    }

    public void setFechainicio(DateTime fechainicio) {
        this.fechainicio = fechainicio;
    }

    public DateTime getFechafin() {
        return fechafin;
    }

    public void setFechafin(DateTime fechafin) {
        this.fechafin = fechafin;
    }

    public DateTime getFcreation() {
        return fcreation;
    }

    public void setFcreation(DateTime fcreation) {
        this.fcreation = fcreation;
    }

    public DateTime getFmodification() {
        return fmodification;
    }

    public void setFmodification(DateTime fmodification) {
        this.fmodification = fmodification;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FrontEndMessage frontEndMessage = (FrontEndMessage) o;

        if ( ! Objects.equals(id, frontEndMessage.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "FrontEndMessage{" +
                "id=" + id +
                ", username='" + username + "'" +
                ", editedName='" + editedName + "'" +
                ", descripcion='" + descripcion + "'" +
                ", titulo='" + titulo + "'" +
                ", seccion='" + seccion + "'" +
                ", tipo='" + tipo + "'" +
                ", visible='" + visible + "'" +
                ", unavailable='" + unavailable + "'" +
                ", fechainicio='" + fechainicio + "'" +
                ", fechafin='" + fechafin + "'" +
                ", fcreation='" + fcreation + "'" +
                ", fmodification='" + fmodification + "'" +
                '}';
    }
}
