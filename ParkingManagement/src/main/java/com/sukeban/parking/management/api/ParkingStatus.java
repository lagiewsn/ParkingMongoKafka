package com.sukeban.parking.management.api;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ParkingStatus {
    
    @JsonProperty
    String parkingName;
    @JsonProperty
    String status;

    public ParkingStatus() {
    }

    public ParkingStatus(String parkingName, String status) {
        this.parkingName = parkingName;
        this.status = status;
    }

    public String getParking() {
        return parkingName;
    }

    public void setParking(String parkingName) {
        this.parkingName = parkingName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    
}
