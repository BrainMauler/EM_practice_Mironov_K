package utils;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import lombok.experimental.UtilityClass;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;

@UtilityClass
public class Logger {

    public static MongoClient getMongoClient(String mongoDBUrl) {
        return MongoClients.create(mongoDBUrl);
    }

    public static void closeMongoClient(MongoClient client) {
        if (client != null) {
            client.close();
        }
    }

    public static MongoCollection<Document> getMongoCollection(String mongoDBName,
                                                               String collectionMongoDBName,
                                                               MongoClient client) {
        MongoCollection<Document> mongoCollection;
        try (client) {
            mongoCollection = client
                    .getDatabase(mongoDBName)
                    .getCollection(collectionMongoDBName);
        }
        return mongoCollection;
    }

    public void clearLogs(MongoCollection<Document> mongoCollection) {
        mongoCollection.deleteMany(new Document());
    }

    public void log(String testName, String operation, String status,
                    MongoCollection<Document> mongoCollection) {
        Document logEntry = new Document("test", testName)
                .append("operation", operation)
                .append("status", status)
                .append("timestamp", LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        mongoCollection.insertOne(logEntry);
    }

    public boolean validateLogsByEntries(String key1, String value1,
                                         String key2, String value2,
                                         String key3, String value3,
                                         MongoCollection<Document> collection) {
        Bson filter = and(
                eq(key1, value1),
                eq(key2, value2),
                eq(key3, value3)
        );
        return collection.find(filter).first() != null;
    }
}