package com.witbooking.middleware.model.channelsIntegration;

/**
 *
 * @author Jose Francisco Fiorillo  < jffiorillo@gmail.com >
 * @author jffiorillo@gmail.com
 */
public class Channel {

    private int id;
    private String channelTicker;
    private String user;
    private String password;
    private float multiplier;
    private int agentId;
    private byte typePush;//0=inactive, 1=http, 2=email
    private String addressPush;
    private boolean active;

    public Channel(int id, String channelTicker, String user, String password, float multiplier, int agentId, byte typePush, String addressPush, boolean active) {
        this.id = id;
        this.channelTicker = channelTicker;
        this.user = user;
        this.password = password;
        this.multiplier = multiplier;
        this.agentId = agentId;
        this.typePush = typePush;
        this.addressPush = addressPush;
        this.active = active;
    }

    public boolean equalsChannelHotelTicker(String hotelTicker){
        return hotelTicker != null &&
                this.channelTicker != null &&
                this.channelTicker.equals(hotelTicker);
    }

    public int getId() {
        return id;
    }

    public String getChannelTicker() {
        return channelTicker;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public float getMultiplier() {
        return multiplier;
    }

    public int getAgentId() {
        return agentId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setChannelTicker(String channelTicker) {
        this.channelTicker = channelTicker;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setMultiplier(float multiplier) {
        this.multiplier = multiplier;
    }

    public void setAgentId(int agentId) {
        this.agentId = agentId;
    }

    public byte getTypePush() {
        return typePush;
    }

    public void setTypePush(byte typePush) {
        this.typePush = typePush;
    }

    public String getAddressPush() {
        return addressPush;
    }

    public void setAddressPush(String addressPush) {
        this.addressPush = addressPush;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "Channel{" +
                "id=" + id +
                ", channelTicker='" + channelTicker + '\'' +
                ", user='" + user + '\'' +
                ", password='" + password + '\'' +
                ", multiplier=" + multiplier +
                ", agentId=" + agentId +
                ", typePush=" + typePush +
                ", addressPush='" + addressPush + '\'' +
                ", active=" + active +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Channel)) return false;
        Channel channel = (Channel) o;
        if (agentId != channel.agentId) return false;
        if (id != channel.id) return false;
        if (Float.compare(channel.multiplier, multiplier) != 0) return false;
        if (typePush != channel.typePush) return false;
        if (addressPush != null ? !addressPush.equals(channel.addressPush) : channel.addressPush != null) return false;
        if (channelTicker != null ? !channelTicker.equals(channel.channelTicker) : channel.channelTicker != null) return false;
        if (password != null ? !password.equals(channel.password) : channel.password != null) return false;
        if (user != null ? !user.equals(channel.user) : channel.user != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + channelTicker.hashCode();
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (multiplier != +0.0f ? Float.floatToIntBits(multiplier) : 0);
        result = 31 * result + agentId;
        result = 31 * result + (int) typePush;
        result = 31 * result + (addressPush != null ? addressPush.hashCode() : 0);
        return result;
    }
}
