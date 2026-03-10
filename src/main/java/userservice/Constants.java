package userservice;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Constants {

    public final String USER_SERVICE_DB_URL = "jdbc:postgresql://localhost:5432/UserServiceDB";
    public final String USER_SERVICE_DB_USERNAME = "postgres";
    public final String USER_SERVICE_DB_PASSWORD = "postgres";
    public final String LOGGER_MONGODB_URL = "mongodb://mongo:mongo@localhost:27017";
    public final String LOGGER_MONGODB_NAME = "UserServiceLogs";
    public final String LOGGER_MONGODB_COLLECTION = "test_logs";
}