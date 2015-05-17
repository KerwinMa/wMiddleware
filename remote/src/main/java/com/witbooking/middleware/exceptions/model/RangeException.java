package com.witbooking.middleware.exceptions.model;

import java.io.Serializable;

/**
 * Created by mongoose on 11/22/14.
 */
public class RangeException extends IllegalArgumentException implements Serializable {

    public RangeException(){
        super();
    }

    public RangeException(String message){
        super(message);
    }
}
