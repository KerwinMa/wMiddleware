package com.witbooking.middleware.integration;

import com.google.common.base.Objects;
import com.witbooking.middleware.utils.DateUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * EntryQueue.java
 * User: jose
 * Date: 11/4/13
 * Time: 4:21 PM
 */
public class EntryQueue {

    private Integer id, totalExecuted;
    private String hotelTicker;
    private ChannelTicker channelTicker;
    private Date creationDate, lastExecuteDate, executionRequestedDate;
    private CommunicationFinished finished;
    private ChannelConnectionType type;
    private ChannelQueueStatus status;
    private List<EntryQueueItem> items;


    public EntryQueue(Integer id, Integer totalExecuted, String channelTicker, String hotelTicker,
                      Date creationDate, Date lastExecuteDate, Date executionRequestedDate, Boolean finished,
                      String type, String status, List<EntryQueueItem> items) {
        this.finished = finished ? CommunicationFinished.FINISHED : CommunicationFinished.NOT_FINISHED;
        this.id = id;
        this.totalExecuted = totalExecuted;
        this.channelTicker = ChannelTicker.valueOf(channelTicker);
        this.hotelTicker = hotelTicker;
        this.creationDate = creationDate;
        this.lastExecuteDate = lastExecuteDate;
        this.executionRequestedDate = executionRequestedDate;
        this.type = ChannelConnectionType.fromValue(type);
        this.status = ChannelQueueStatus.fromValue(status);
        this.items = items;
    }


    public EntryQueue(Integer id, Integer totalExecuted, String channelTicker, String hotelTicker,
                      Date creationDate, Date lastExecuteDate, Date executionRequestedDate, Boolean finished,
                      String type, String status) {
        this.finished = finished ? CommunicationFinished.FINISHED : CommunicationFinished.NOT_FINISHED;
        this.id = id;
        this.totalExecuted = totalExecuted;
        this.channelTicker = ChannelTicker.valueOf(channelTicker);
        this.hotelTicker = hotelTicker;
        this.creationDate = creationDate;
        this.lastExecuteDate = lastExecuteDate;
        this.executionRequestedDate = executionRequestedDate;
        this.type = ChannelConnectionType.fromValue(type);
        this.status = ChannelQueueStatus.fromValue(status);
    }

    public EntryQueue(String hotelTicker, ChannelTicker channelTicker) {
        this.hotelTicker = hotelTicker;
        this.channelTicker = channelTicker;
    }

    public EntryQueue(String hotelTicker, ChannelTicker channelTicker, Date executionRequestedDate, List<EntryQueueItem> itemsId) {
        this.hotelTicker = hotelTicker;
        this.channelTicker = channelTicker;
        this.executionRequestedDate = executionRequestedDate;
        this.items = itemsId;
    }

    public EntryQueue(String hotelTicker, ChannelTicker channelTicker, List<EntryQueueItem> itemsId) {
        this.hotelTicker = hotelTicker;
        this.items = itemsId;
        this.channelTicker = channelTicker;
    }

    public void setItems(List<EntryQueueItem> items) {
        this.items = items;
    }

    public Integer getId() {
        return id;
    }

    public Integer getTotalExecuted() {
        return totalExecuted;
    }

    public String getHotelTicker() {
        return hotelTicker;
    }

    public ChannelTicker getChannelTicker() {
        return channelTicker;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public Date getLastExecuteDate() {
        return lastExecuteDate;
    }

    public Date getExecutionRequestedDate() {
        return executionRequestedDate;
    }

    public CommunicationFinished getFinished() {
        return finished;
    }

    public ChannelConnectionType getType() {
        return type;
    }

    public ChannelQueueStatus getStatus() {
        return status;
    }

    public List<EntryQueueItem> getItems() {
        return items;
    }

    public EntryQueueItem getFirstItem() {
        return items == null || items.isEmpty()
                ? null
                : items.get(0);
    }

    public List<String> getItemsIds() {
        if (items == null)
            return null;
        else {
            List<String> ids = new ArrayList<>();
            for (EntryQueueItem item : items) {
                ids.add(item.getItemId());
            }
            return ids;
        }
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("id", id)
                .add("totalExecuted", totalExecuted)
                .add("hotelTicker", hotelTicker)
                .add("channelTicker", channelTicker)
                .add("creationDate", creationDate)
                .add("lastExecuteDate", lastExecuteDate)
                .add("executionRequestedDate", executionRequestedDate)
                .add("finished", finished)
                .add("type", type)
                .add("status", status)
                .add("items", items)
                .toString();
    }


    //    @Override
//    public String toString() {
//        return "EntryQueue{" +
//                "id=" + id +
//                ", totalExecuted=" + totalExecuted +
//                ", hotelTicker='" + hotelTicker + '\'' +
//                ", channelTicker=" + channelTicker +
//                ", creationDate=" + creationDate +
//                ", lastExecuteDate=" + lastExecuteDate +
//                ", executionRequestedDate=" + executionRequestedDate +
//                ", finished=" + finished +
//                ", type=" + type +
//                ", status=" + status +
//                ", items=" + items +
//                '}';
//    }

    public String prettyPrintHtml() {
        String print = "EntryQueue {" +
                "<br/>&emsp; id=" + id +
                ",<br/>&emsp; totalExecuted= " + totalExecuted +
                ",<br/>&emsp; hotelTicker= '" + hotelTicker + '\'' +
                ",<br/>&emsp; channelTicker= " + channelTicker +
                ",<br/>&emsp; creationDate= " + DateUtil.timestampFormat(creationDate) +
                ",<br/>&emsp; lastExecuteDate= " + DateUtil.timestampFormat(lastExecuteDate) +
                ",<br/>&emsp; executionRequestedDate= " + DateUtil.timestampFormat(executionRequestedDate) +
                ",<br/>&emsp; finished= " + finished +
                ",<br/>&emsp; type= " + type +
                ",<br/>&emsp; status= " + status +
                ",<br/>&emsp; items= " + items;
        print = print + "<br/>&emsp;}";
        return print;
    }
}