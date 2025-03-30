package com.nativeatm;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;

public class MongoDBConnector {
    private static final String CONNECTION_STRING = "mongodb://localhost:27017";
    private static final String DB_NAME = "atmdb";

    public static MongoDatabase connect() {
        MongoClient client = MongoClients.create(CONNECTION_STRING);
        return client.getDatabase(DB_NAME);
    }
}
