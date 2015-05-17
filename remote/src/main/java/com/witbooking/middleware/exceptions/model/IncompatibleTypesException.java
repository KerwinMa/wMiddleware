package com.witbooking.middleware.exceptions.model;

import java.io.Serializable;

/**
 * Created by mongoose on 11/22/14.
 */
public class IncompatibleTypesException extends IllegalArgumentException implements Serializable {

    public IncompatibleTypesException(){
        super();
    }

    public IncompatibleTypesException(String message){
        super(message);
    }
}
