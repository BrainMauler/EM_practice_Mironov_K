import UserService.Role;
import UserService.User;
import org.junit.jupiter.api.AfterAll;

import java.sql.Connection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import static UserService.Constants.*;
import static utils.DBConnector.*;

public abstract class BaseUserServiceDBTest {

    protected static Connection connection = getDBConnection(USER_SERVICE_DB_URL,
                                                             USER_SERVICE_DB_USERNAME,
                                                             USER_SERVICE_DB_PASSWORD);

    protected static User user1 =
            new User(1, "Sasha", "qwerty1", new HashSet<>(Set.of(100)));
    protected static User user2 =
            new User(2, "Lesha", "qwerty2", new HashSet<>(Set.of(100)));
    protected static User user3 =
            new User(3, "Dima", "qwerty3", new HashSet<>(Set.of(100)));
    protected static User user4 =
            new User(4, "Fedya", "qwerty4", new HashSet<>(Set.of(100, 200)));
    protected static User user5 =
            new User(5, "Petya", "qwerty5", new HashSet<>(Set.of(100, 200, 300)));
    protected static Role role1 = new Role(100, "user");
    protected static Role role2 = new Role(200, "moder");
    protected static Role role3 = new Role(300, "admin");
    protected static Role role4 = new Role(400, "extended_user");

    @AfterAll
    protected static void closeConnection() {
        closeDBConnection(connection);
        Stream.of(user1, user2, user3, user4, user5, role1, role2, role3, role4)
                .map(object -> null)
                .close();
    }
}