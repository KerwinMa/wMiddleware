package com.witbooking.middleware.db.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by mongoose on 17/06/15.
 */

@Entity
@Table(name = "mensajes")
public class Localized implements Serializable {

    public static final String MODEL="Mensaje";


}
