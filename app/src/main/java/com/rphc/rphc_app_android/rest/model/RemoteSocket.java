package com.rphc.rphc_app_android.rest.model;


import com.google.gson.annotations.SerializedName;


public class RemoteSocket {

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("transmitter")
    private String transmitterEndpoint;

    @SerializedName("group")
    private String group;

    @SerializedName("device")
    private String device;

    @SerializedName("repeats")
    private int repeats;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getTransmitterEndpoint() {
        return transmitterEndpoint;
    }
    public void setTransmitterEndpoint(String transmitterEndpoint) {
        this.transmitterEndpoint = transmitterEndpoint;
    }

    public String getGroup() {
        return group;
    }
    public void setGroup(String group) {
        this.group = group;
    }

    public String getDevice() {
        return device;
    }
    public void setDevice(String device) {
        this.device = device;
    }

    public int getRepeats() {
        return repeats;
    }
    public void setRepeats(int repeats) {
        this.repeats = repeats;
    }
}
