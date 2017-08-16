package com.sukeban.parking.management.api;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

@Entity(value = "PARKING_SLOTS", noClassnameStored = true)
public class ParkingSlots {

    @Id
    @JsonIgnore
    private ObjectId id;
    @JsonProperty
    private int slotNumber;
    @JsonProperty
    private SlotStatusType slotStatusType;
    @JsonIgnore
    private ParkingLevel parkingLevel;

    public ParkingSlots() {
    }

    public ParkingSlots(ParkingLevel parkingLevel, int slotNumber, SlotStatusType slotStatusType) {
        this.parkingLevel = parkingLevel;
        this.slotNumber = slotNumber;
        this.slotStatusType = slotStatusType;

    }


    public int getSlotNumber() {
        return slotNumber;
    }

    public void setSlotNumber(int slotNumber) {
        this.slotNumber = slotNumber;
    }

    public SlotStatusType getSlotStatusType() {
        return slotStatusType;
    }

    public void setSlotStatusType(SlotStatusType slotStatusType) {
        this.slotStatusType = slotStatusType;
    }

    public ParkingLevel getParkingLevel() {
        return parkingLevel;
    }

    public void setParkingLevel(ParkingLevel parkingLevel) {
        this.parkingLevel = parkingLevel;
    }


}
