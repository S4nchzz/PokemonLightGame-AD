package org.lightPoke.db.mongo;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class MongoConnection {
    private static MongoDatabase instance;

    private MongoConnection() {}

    public static MongoDatabase getInstance() {
        if (instance == null) {
            instance = MongoClients.create("mongodb://localhost:27017").getDatabase("pokemon_lg_mongo");
        }

        return instance;
    }
}