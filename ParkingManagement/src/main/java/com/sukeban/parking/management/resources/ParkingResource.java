package com.sukeban.parking.management.resources;

import com.sukeban.parking.management.api.DbQuery;
import com.sukeban.parking.management.api.Parking;
import com.sukeban.parking.management.api.ParkingLevel;
import com.sukeban.parking.management.api.ParkingSlots;
import com.sukeban.parking.management.api.ParkingStatus;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.mongodb.morphia.Datastore;

@Path("sukeban/parking/")
@Produces(MediaType.APPLICATION_JSON)
public class ParkingResource {

    DbQuery dbQuery;

    public ParkingResource(Datastore datastore, String TOPIC_PRODUCER) {

        this.dbQuery = new DbQuery(datastore, TOPIC_PRODUCER);

    }

    @GET
    @Path("{parkingName}")
    @Produces(MediaType.APPLICATION_JSON)
    public Parking getParking(@PathParam("parkingName") String parkingName) {
        return this.dbQuery.getParking(parkingName);
    }

    @GET
    @Path("{parkingName}/{levelName}")
    @Produces(MediaType.APPLICATION_JSON)
    public ParkingLevel getParkingLevel(
            @PathParam("parkingName") String parkingName,
            @PathParam("levelName") String levelName
    ) {
        return this.dbQuery.getParkingLevel(parkingName, levelName);
    }

    @GET
    @Path("{parkingName}/all")
    @Produces(MediaType.APPLICATION_JSON)
    public List<ParkingLevel> getParkingLevel(
            @PathParam("parkingName") String parkingName
    ) {
        return this.dbQuery.getAllParkingLevels(parkingName);
    }

    @GET
    @Path("{parkingName}/{levelName}/{slotNumber}")
    @Produces(MediaType.APPLICATION_JSON)
    public ParkingSlots getParkingSlots(
            @PathParam("parkingName") String parkingName,
            @PathParam("levelName") String levelName,
            @PathParam("slotNumber") int slotNumber
    ) {

        return this.dbQuery.getParkingSlots(parkingName, levelName, slotNumber);
    }

    @POST
    @Path("add-one-parking")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ParkingStatus addParking(Parking parking) {

        return this.dbQuery.addParking(parking);
        
    }
}
