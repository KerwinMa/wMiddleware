/*
 *   DataValueHolder.java
 * 
 * Copyright(c) 2014 Witbooking.com. All Rights Reserved.
 * This software is the proprietary information of Witbooking.com
 * 
 */
package com.witbooking.middleware.model;

import com.witbooking.middleware.model.values.*;

/**
 * Insert description here
 *
 * @author Christian Delgado
 * @version 1.0
 * @date 04/07/2014
 */
public interface DataValueHolder {

    public Integer getId();

    public void setId(Integer id);

    public String getTicker();

    public void setTicker(String ticker);

    public RateDataValue getRate();

    public void setRate(RateDataValue rate);

    public AvailabilityDataValue getAvailability();

    public void setAvailability(AvailabilityDataValue availability);

    public LockDataValue getLock();

    public void setLock(LockDataValue lock);

    public StayDataValue getMinStay();

    public void setMinStay(StayDataValue minStay);

    public StayDataValue getMaxStay();

    public void setMaxStay(StayDataValue maxStay);

    public NoticeDataValue getMinNotice();

    public void setMinNotice(NoticeDataValue minNotice);

    public NoticeDataValue getMaxNotice();

    public void setMaxNotice(NoticeDataValue maxNotice);

}
