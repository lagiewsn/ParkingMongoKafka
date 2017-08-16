package com.sukeban.parking.management.api;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import java.util.List;
import java.util.ListIterator;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

@Entity(value = "PARKING", noClassnameStored = true)
public class Parking {

    @Id
    @JsonIgnore
    private ObjectId id;

    @JsonProperty
    private String parkingName;
    @JsonProperty(access = Access.WRITE_ONLY)
    private int numberSlots;
    @JsonProperty(access = Access.WRITE_ONLY)
    private int numberFreeSlots;
    @JsonProperty(access = Access.WRITE_ONLY)
    private boolean full;
    @JsonProperty
    private List<ParkingLevel> parkingLevels;

    public Parking() {
    }

    public Parking(String parkingName, List<ParkingLevel> parkingLevels) {

        ListIterator<ParkingLevel> it = parkingLevels.listIterator();

        this.parkingName = parkingName;
        this.parkingLevels = parkingLevels;
        this.numberSlots = 0;
        this.numberFreeSlots = 0;

        while (it.hasNext()) {
            ParkingLevel parkingLevel = it.next();
            this.numberSlots = this.numberSlots + parkingLevel.getNumberSlots();
            this.numberFreeSlots = this.numberFreeSlots + parkingLevel.getNumberFreeSlots();
        }

        this.full = this.numberFreeSlots == 0;

    }

    public String getParkingName() {
        return parkingName;
    }

    public void setParkingName(String parkingName) {
        this.parkingName = parkingName;
    }

    public int getNumberSlots() {
        return numberSlots;
    }

    public void setNumberSlots(int numberSlots) {
        this.numberSlots = numberSlots;
    }

    public int getNumberFreeSlots() {
        return numberFreeSlots;
    }

    public void setNumberFreeSlots(int numberFreeSlots) {
        this.numberFreeSlots = numberFreeSlots;
    }

    public boolean isFull() {
        return full;
    }

    public void setFull(boolean full) {
        this.full = full;
    }

    public List<ParkingLevel> getParkingLevels() {
        return parkingLevels;
    }

    public void setParkingLevels(List<ParkingLevel> parkingLevels) {
        this.parkingLevels = parkingLevels;
    }

}
