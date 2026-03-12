package utils;

import com.mongodb.client.MongoCollection;
import lombok.experimental.UtilityClass;
import org.bson.Document;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.function.Executable;

import java.sql.SQLException;

@UtilityClass
public class AssertsUtils {

    public void assertTrueWithLogs(boolean assertValue, String testName, String operation,
                                   MongoCollection<Document> collection) {
        try {
            Assertions.assertTrue(assertValue);
        } catch (AssertionError error) {
            Logger.log(testName, operation, "FAILED", collection);
            Assertions.assertTrue(Logger.validateLogsByEntries("test", testName,
                    "operation", operation,
                    "status", "FAILED",
                    collection));
            error.printStackTrace();
        }
        Logger.log(testName, operation, "PASSED", collection);
        Assertions.assertTrue(Logger.validateLogsByEntries("test", testName,
                "operation", operation,
                "status", "PASSED",
                collection));
    }

    public void assertSQLThrowsWithLogs(Executable assertExpression, String testName, String operation,
                                        MongoCollection<Document> collection) {
        try {
            Assertions.assertThrows(SQLException.class, assertExpression);
        } catch (AssertionError error) {
            Logger.log(testName, operation, "FAILED", collection);
            Assertions.assertTrue(Logger.validateLogsByEntries("test", testName,
                    "operation", operation,
                    "status", "FAILED",
                    collection));
            error.printStackTrace();
        }
        Logger.log(testName, operation, "PASSED", collection);
        Assertions.assertTrue(Logger.validateLogsByEntries("test", testName,
                "operation", operation,
                "status", "PASSED",
                collection));
    }
}