package com.sukeban.parking.management;

import com.mongodb.MongoClient;
import com.sukeban.parking.management.health.ParkingHealthCheck;
import com.sukeban.parking.management.resources.ParkingResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

public class ParkingApplication extends Application<ParkingConfiguration> {

    //private static final String GROUP_ID = "group3";
    //private static final String CONSUMER_TOPIC = "UPDATE-USER-MANAGEMENT-DB";
    private static final String TOPIC_PRODUCER = "UPDATE-CAR-MANAGEMENT-DB";

    private static final String DB_NAME = "PARKING-MANAGEMENT-DB";
    private Morphia morphia;
    private MongoClient mongoClient;

    public static void main(String[] args) throws Exception {
        new ParkingApplication().run(args);
    }

    @Override
    public void initialize(Bootstrap<ParkingConfiguration> bootstrap) {
        // nothing to do yet
    }

    @Override
    public void run(ParkingConfiguration configuration, Environment environment) {
        morphia = new Morphia();
        mongoClient = new MongoClient();

        // create the Datastore connecting to the default port on the local host
        final Datastore datastore = morphia.createDatastore(mongoClient, DB_NAME);
        datastore.ensureIndexes();

        environment.healthChecks().register("User", new ParkingHealthCheck(mongoClient));

        final ParkingResource resource = new ParkingResource(datastore, TOPIC_PRODUCER);
        environment.jersey().register(resource);
    }

}
