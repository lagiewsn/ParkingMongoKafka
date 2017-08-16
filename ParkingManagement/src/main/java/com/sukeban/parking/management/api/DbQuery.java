package com.sukeban.parking.management.api;

import com.mongodb.MongoClient;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.Query;

public class DbQuery {

    Datastore datastore;
    private final MongodbProducer mongoProducer;

    public DbQuery(String DB_NAME, String TOPIC_PRODUCER) {
        Morphia morphia = new Morphia();
        MongoClient mongoClient = new MongoClient();

        // create the Datastore connecting to the default port on the local host
        this.datastore = morphia.createDatastore(mongoClient, DB_NAME);
        this.datastore.ensureIndexes();
        this.mongoProducer = new MongodbProducer(TOPIC_PRODUCER);
    }

    public DbQuery(Datastore datastore, String TOPIC_PRODUCER) {
        this.datastore = datastore;
        this.mongoProducer = new MongodbProducer(TOPIC_PRODUCER);
    }

    public Datastore getDatastore() {
        return this.datastore;
    }

    public Parking getParking(String parkingName) {

        Parking parking = null;
        Query<Parking> query = this.datastore.createQuery(Parking.class)
                .field("parkingName").contains(parkingName);

        if (query.asList().size() == 1) {
            parking = query.asList().get(0);
        }

        return parking;
    }

    public List<ParkingLevel> getAllParkingLevels(String parkingName) {

        Query<ParkingLevel> query = this.datastore.createQuery(ParkingLevel.class)
                .field("parkingName").contains(parkingName);

        return query.asList();
    }

    public ParkingLevel getParkingLevel(String parkingName, String levelName) {
        ParkingLevel level = null;
        Query<ParkingLevel> query = this.datastore.createQuery(ParkingLevel.class)
                .field("parkingName").contains(parkingName)
                .field("levelName").contains(levelName);

        if (query.asList().size() == 1) {
            level = query.asList().get(0);
        }

        return level;
    }

    public ParkingSlots getParkingSlots(String parkingName, String levelName, int slotNumber) {
        ParkingSlots slot = null;
        Query<ParkingSlots> query = this.datastore.createQuery(ParkingSlots.class)
                .field("parkingName").contains(parkingName)
                .field("levelName").contains(levelName)
                .field("slotNumber").contains(Integer.toString(slotNumber));
        if (query.asList().size() == 1) {
            slot = query.asList().get(0);
        }

        return slot;
    }

    public ParkingStatus addParking(Parking parking) {

        String status = "Existing";
        Parking parkingToSave = null;
        ParkingLevel parkingLevelToSave;
        ParkingSlots parkingSlotsToSave;

        Query<Parking> queryCar = this.datastore.createQuery(Parking.class)
                .field("parkingName").contains(parking.getParkingName());

        if (queryCar.asList().isEmpty()) {

            ListIterator<ParkingLevel> it = parking.getParkingLevels().listIterator();
            List<ParkingLevel> parkingLevelList = new ArrayList();
            while (it.hasNext()) {
                
                parkingLevelToSave = it.next();
                ListIterator<ParkingSlots> iterator = parkingLevelToSave.getParkingSlots().listIterator();
                List<ParkingSlots> parkingSlotsList = new ArrayList();
                while(iterator.hasNext()) {
                    
                    parkingSlotsToSave = iterator.next();
                    parkingSlotsList.add(parkingSlotsToSave);
                    this.datastore.save(parkingSlotsToSave);
                }
                parkingLevelToSave = new ParkingLevel(parking.getParkingName(), parkingLevelToSave.getLevelName(), parkingSlotsList);
                parkingLevelList.add(parkingLevelToSave);
                this.datastore.save(parkingLevelToSave);
            }
            parkingToSave = new Parking(parking.getParkingName(), parkingLevelList);
            this.datastore.save(parkingToSave);

            status = "Added";
        }

        return new ParkingStatus(parkingToSave, status);
    }
}
