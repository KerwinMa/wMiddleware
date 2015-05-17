package com.witbooking.middleware.integration;

import com.google.common.base.Objects;

import java.util.Date;

/**
 * EntryQueueItem.java
 * User: jose
 * Date: 11/6/13
 * Time: 12:39 PM
 */
public class EntryQueueItem {

    private String itemId;
    private Date start;
    private Date end;

    public EntryQueueItem(String itemId) {
        this.itemId = itemId;
        start = null;
        end = null;
    }

    public EntryQueueItem(String itemId, Date start, Date end) {
        this.itemId = itemId;
        this.start = start;
        this.end = end;
    }

    public String getItemId() {
        return itemId;
    }

    public Date getStart() {
        return start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public void setStart(Date start) {
        this.start = start;
    }



    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("itemId", itemId)
                .add("start", start)
                .add("end", end)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EntryQueueItem)) return false;
        EntryQueueItem that = (EntryQueueItem) o;
        if (end != null ? !end.equals(that.end) : that.end != null) return false;
        if (itemId != null ? !itemId.equals(that.itemId) : that.itemId != null) return false;
        if (start != null ? !start.equals(that.start) : that.start != null) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = itemId != null ? itemId.hashCode() : 0;
        result = 31 * result + (start != null ? start.hashCode() : 0);
        result = 31 * result + (end != null ? end.hashCode() : 0);
        return result;
    }
}