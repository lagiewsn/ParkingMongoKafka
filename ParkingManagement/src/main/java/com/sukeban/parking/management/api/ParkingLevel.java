package com.sukeban.parking.management.api;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import java.util.List;
import java.util.ListIterator;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

@Entity(value = "PARKING_LEVEL", noClassnameStored = true)
public class ParkingLevel {

    @Id
    @JsonIgnore
    private ObjectId id;
    @JsonProperty
    private String levelName;
    @JsonProperty(access = Access.WRITE_ONLY)
    private int numberSlots;
    @JsonProperty(access = Access.WRITE_ONLY)
    private int numberFreeSlots;
    @JsonProperty(access = Access.READ_ONLY)
    private String parkingName;
    @JsonProperty
    private List<ParkingSlots> parkingSlots;
    @JsonProperty(access = Access.WRITE_ONLY)
    private boolean full;

    public ParkingLevel() {
    }

    public ParkingLevel(String parkingName, String levelName, List<ParkingSlots> parkingSlots) {

        ListIterator<ParkingSlots> it = parkingSlots.listIterator();

        this.parkingName = parkingName;
        this.levelName = levelName;
        this.parkingSlots = parkingSlots;

        this.numberSlots = parkingSlots.size();

        this.numberFreeSlots = 0;
        while (it.hasNext()) {
            ParkingSlots parkingSlot = it.next();
            if (parkingSlot.getSlotStatusType().equals(SlotStatusType.AVAILABLE)) {
                this.numberFreeSlots = this.numberFreeSlots + 1;
            }
        }
        
        this.full = this.numberFreeSlots == 0;

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

    public String getParking() {
        return parkingName;
    }

    public void setParking(String parkingName) {
        this.parkingName = parkingName;
    }

    public List<ParkingSlots> getParkingSlots() {
        return parkingSlots;
    }

    public void setParkingSlots(List<ParkingSlots> parkingSlots) {
        this.parkingSlots = parkingSlots;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public boolean isFull() {
        return full;
    }

    public void setFull(boolean full) {
        this.full = full;
    }

    
}
