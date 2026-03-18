package utils;

import com.mongodb.client.MongoCollection;
import lombok.experimental.UtilityClass;
import org.bson.Document;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.function.Executable;

import java.sql.SQLException;

@UtilityClass
public class AssertsUtils {

    public void assertNullWithLogs(Object assertValue, String testName, String operation,
                                   MongoCollection<Document> collection) {
        boolean failCheck = false;
        try {
            Assertions.assertNull(assertValue);
        } catch (AssertionError error) {
            Logger.log(testName, operation, "FAILED", collection);
            Assertions.assertTrue(Logger.validateLogsByEntries("test", testName,
                    "operation", operation,
                    "status", "FAILED",
                    collection));
            failCheck = true;
            error.printStackTrace();
        }
        if (!failCheck) {
            Logger.log(testName, operation, "PASSED", collection);
            Assertions.assertTrue(Logger.validateLogsByEntries("test", testName,
                    "operation", operation,
                    "status", "PASSED",
                    collection));
        }
    }

    public void assertEqualsWithLogs(Object assertValue1, Object assertValue2,
                                     String testName, String operation,
                                     MongoCollection<Document> collection) {
        boolean failCheck = false;
        try {
            Assertions.assertEquals(assertValue1, assertValue2);
        } catch (AssertionError error) {
            Logger.log(testName, operation, "FAILED", collection);
            Assertions.assertTrue(Logger.validateLogsByEntries("test", testName,
                    "operation", operation,
                    "status", "FAILED",
                    collection));
            failCheck = true;
            error.printStackTrace();
        }
        if (!failCheck) {
            Logger.log(testName, operation, "PASSED", collection);
            Assertions.assertTrue(Logger.validateLogsByEntries("test", testName,
                    "operation", operation,
                    "status", "PASSED",
                    collection));
        }
    }

    public void assertSQLThrowsWithLogs(Executable assertExpression, String testName, String operation,
                                        MongoCollection<Document> collection) {
        boolean failCheck = false;
        try {
            Assertions.assertThrows(SQLException.class, assertExpression);
        } catch (AssertionError error) {
            Logger.log(testName, operation, "FAILED", collection);
            Assertions.assertTrue(Logger.validateLogsByEntries("test", testName,
                    "operation", operation,
                    "status", "FAILED",
                    collection));
            failCheck = true;
            error.printStackTrace();
        }
        if (!failCheck) {
            Logger.log(testName, operation, "PASSED", collection);
            Assertions.assertTrue(Logger.validateLogsByEntries("test", testName,
                    "operation", operation,
                    "status", "PASSED",
                    collection));
        }
    }
}