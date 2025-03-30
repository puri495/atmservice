package com.nativeatm;

import org.bson.Document;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class ATMTransaction {
    private final MongoCollection<Document> collection;

    public ATMTransaction() {
        MongoDatabase db = MongoDBConnector.connect();
        collection = db.getCollection("transactions");
    }

    public boolean createAccount(String user, int pin, int balance) {
        if (findUser(user) != null) return false; // User already exists
        Document doc = new Document("user", user)
                .append("pin", pin)
                .append("balance", balance);
        collection.insertOne(doc);
        return true;
    }

    public Document findUser(String user) {
        return collection.find(new Document("user", user)).first();
    }

    public boolean updateBalance(String user, int amount) {
        Document userDoc = findUser(user);
        if (userDoc == null) return false;

        int currentBalance = userDoc.getInteger("balance");
        collection.updateOne(new Document("user", user),
                new Document("$set", new Document("balance", currentBalance + amount)));
        return true;
    }

    public int getBalance(String user) {
        Document userDoc = findUser(user);
        return userDoc != null ? userDoc.getInteger("balance") : -1;
    }
}

