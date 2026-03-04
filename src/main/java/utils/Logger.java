package utils;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import lombok.experimental.UtilityClass;
import org.bson.Document;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@UtilityClass
public class Logger {

    public static MongoCollection<Document> getMongoCollection(String mongoDBUrl,
                                                               String mongoDBName,
                                                               String collectionMongoDBName) {
        MongoCollection<Document> mongoCollection;
        try (MongoClient mongoClient = MongoClients.create(mongoDBUrl)) {
            MongoDatabase database = mongoClient.getDatabase(mongoDBName);
            mongoCollection = database.getCollection(collectionMongoDBName);
        }
        return mongoCollection;
    }

    public void clearLogs(MongoCollection<Document> mongoCollection) {
        mongoCollection.deleteMany(new Document());
    }

    public void log(String testName, String operation, String status, String details,
                    MongoCollection<Document> mongoCollection) {
        Document logEntry = new Document("Test: ", testName)
                .append("operation: ", operation)
                .append("status: ", status)
                .append("details: ", details)
                .append("timestamp: ", LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        mongoCollection.insertOne(logEntry);
    }
}