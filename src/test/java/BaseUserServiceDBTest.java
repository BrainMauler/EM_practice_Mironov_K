import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import enums.TestName;
import org.bson.Document;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import userservice.pojo.Role;
import userservice.dao.RoleDAO;
import userservice.pojo.User;
import userservice.dao.UserDAO;
import exceptions.DBClearingException;
import org.junit.jupiter.api.AfterAll;

import java.sql.Connection;
import java.util.HashSet;
import java.util.Set;

import static userservice.Constants.*;
import static utils.DBConnector.*;
import static utils.Logger.*;

public abstract class BaseUserServiceDBTest {

    protected static final Connection CONNECTION = getDBConnection(USER_SERVICE_DB_URL,
            USER_SERVICE_DB_USERNAME,
            USER_SERVICE_DB_PASSWORD);

    protected static final MongoClient CLIENT = getMongoClient(LOGGER_MONGODB_URL);

    protected static final MongoCollection<Document> COLLECTION = getMongoCollection(LOGGER_MONGODB_NAME,
            LOGGER_MONGODB_COLLECTION,
            CLIENT);

    @BeforeAll
    protected static void clearLogsBeforeTests() {
        clearLogs(COLLECTION);
    }

    protected static User user1 =
            new User(1, "Sasha", "qwerty1", new HashSet<>(Set.of(100)));
    protected static User user2 =
            new User(2, "Fedya", "qwerty2", new HashSet<>(Set.of(100, 200)));
    protected static User user3 =
            new User(3, "Petya", "qwerty3", new HashSet<>(Set.of(100, 200, 300)));
    protected static User userNull =
            new User(4, "NullPasswordUser", null, new HashSet<>(Set.of(100)));

    protected static Role role1 = new Role(100, "user");
    protected static Role role2 = new Role(200, "moder");
    protected static Role role3 = new Role(300, "admin");
    protected static Role role4 = new Role(400, "extended_user");

    protected static final String INSERT_USER_TEST_POS = TestName.INSERT_USER_TEST_POS.camel();
    protected static final String INSERT_ROLE_TEST_POS = TestName.INSERT_ROLE_TEST_POS.camel();
    protected static final String SYNC_USERS_ROLES_TEST = TestName.SYNC_USERS_ROLES_TEST.camel();
    protected static final String UPDATE_USER_ROLES_TEST = TestName.UPDATE_USER_ROLES_TEST.camel();
    protected static final String INSERT_USER_TEST_NEG = TestName.INSERT_USER_TEST_NEG.camel();
    protected static final String INSERT_ROLE_TEST_NEG = TestName.INSERT_ROLE_TEST_NEG.camel();
    protected static final String INSERT_USER_TEST_NULL_NEG = TestName.INSERT_USER_TEST_NULL_NEG.camel();
    protected static final String UPDATE_USER_TEST_POS = TestName.UPDATE_USER_TEST_POS.camel();
    protected static final String UPDATE_USER_TEST_NEG = TestName.UPDATE_USER_TEST_NEG.camel();
    protected static final String UPDATE_ROLE_TEST_POS = TestName.UPDATE_ROLE_TEST_POS.camel();
    protected static final String UPDATE_ROLE_TEST_NEG = TestName.UPDATE_ROLE_TEST_NEG.camel();
    protected static final String DELETE_USER_TEST = TestName.DELETE_USER_TEST.camel();
    protected static final String DELETE_ROLE_TEST = TestName.DELETE_ROLE_TEST.camel();

    protected static final String INSERT_USER_TEST_POS_OP = TestName.INSERT_USER_TEST_POS.getOperation();
    protected static final String INSERT_ROLE_TEST_POS_OP = TestName.INSERT_ROLE_TEST_POS.getOperation();
    protected static final String SYNC_USERS_ROLES_TEST_OP = TestName.SYNC_USERS_ROLES_TEST.getOperation();
    protected static final String UPDATE_USER_ROLES_TEST_OP = TestName.UPDATE_USER_ROLES_TEST.getOperation();
    protected static final String INSERT_USER_TEST_NEG_OP = TestName.INSERT_USER_TEST_NEG.getOperation();
    protected static final String INSERT_ROLE_TEST_NEG_OP = TestName.INSERT_ROLE_TEST_NEG.getOperation();
    protected static final String INSERT_USER_TEST_NULL_NEG_OP = TestName.INSERT_USER_TEST_NULL_NEG.getOperation();
    protected static final String UPDATE_USER_TEST_POS_OP = TestName.UPDATE_USER_TEST_POS.getOperation();
    protected static final String UPDATE_USER_TEST_NEG_OP = TestName.UPDATE_USER_TEST_NEG.getOperation();
    protected static final String UPDATE_ROLE_TEST_POS_OP = TestName.UPDATE_ROLE_TEST_POS.getOperation();
    protected static final String UPDATE_ROLE_TEST_NEG_OP = TestName.UPDATE_ROLE_TEST_NEG.getOperation();
    protected static final String DELETE_USER_TEST_OP = TestName.DELETE_USER_TEST.getOperation();
    protected static final String DELETE_ROLE_TEST_OP = TestName.DELETE_ROLE_TEST.getOperation();

    @AfterEach
    protected void clearRelatesAfterTests() {
        UserDAO.clearUsersTable(CONNECTION);
        RoleDAO.clearRolesTable(CONNECTION);
    }

    @AfterAll
    protected static void clearUp() {
        UserDAO.clearUsersTable(CONNECTION);
        RoleDAO.clearRolesTable(CONNECTION);
        if (!UserDAO.tableUsersIsEmpty(CONNECTION) || !RoleDAO.tableRolesIsEmpty(CONNECTION)) {
            throw new DBClearingException("База данных не очищена от тестовых данных до конца," +
                    " требуется ручное вмешательство");
        }
        closeDBConnection(CONNECTION);
        clearLogs(COLLECTION);
        closeMongoClient(CLIENT);
    }
}