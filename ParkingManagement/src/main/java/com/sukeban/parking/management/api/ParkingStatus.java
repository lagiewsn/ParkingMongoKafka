package com.sukeban.parking.management.api;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ParkingStatus {
    
    @JsonProperty
    Parking parking;
    @JsonProperty
    String status;

    public ParkingStatus() {
    }

    public ParkingStatus(Parking parking, String status) {
        this.parking = parking;
        this.status = status;
    }

    public Parking getParking() {
        return parking;
    }

    public void setParking(Parking parking) {
        this.parking = parking;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    
}
