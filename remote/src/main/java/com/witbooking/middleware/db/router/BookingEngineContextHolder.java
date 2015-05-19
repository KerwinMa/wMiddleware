package com.witbooking.middleware.db.router;

/**
 * Created by mongoose on 19/05/15.
 */
public class BookingEngineContextHolder {
    private static final ThreadLocal<BookingEngineData> contextHolder =
            new ThreadLocal<BookingEngineData>();

    public static void setBookingEngineData(BookingEngineData BookingEngineData) {
        contextHolder.set(BookingEngineData);
    }

    public static BookingEngineData getBookingEngineData() {
        return (BookingEngineData) contextHolder.get();
    }

    public static void clearBookingEngineData() {
        contextHolder.remove();
    }
}
