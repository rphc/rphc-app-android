package com.rphc.rphc_app_android.rest.model;


import com.google.gson.annotations.SerializedName;


public class AddressableLedStrip {

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("controller")
    private String controller;

    @SerializedName("spi_device")
    private int spiDevice;

    @SerializedName("led_count")
    private int ledCount;

    @SerializedName("usable_led_count")
    private int usableLedCount;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getController() {
        return controller;
    }
    public void setController(String controller) {
        this.controller = controller;
    }

    public int getSpiDevice() {
        return spiDevice;
    }
    public void setSpiDevice(int spiDevice) {
        this.spiDevice = spiDevice;
    }

    public int getLedCount() {
        return ledCount;
    }
    public void setLedCount(int ledCount) {
        this.ledCount = ledCount;
    }

    public int getUsableLedCount() {
        return usableLedCount;
    }
    public void setUsableLedCount(int usableLedCount) {
        this.usableLedCount = usableLedCount;
    }
}
