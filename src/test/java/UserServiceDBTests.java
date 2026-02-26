import UserService.UserDAO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

public class UserServiceDBTests extends BaseUserServiceDBTest {

    @Test
    @DisplayName("Добавление нового пользователя(поз)")
    void insertUserTestPos() {
        Assertions.assertTrue(Stream.of(user1, user2, user3)
                .allMatch(user -> UserDAO.insertUser(user, connection)));
        Assertions.assertTrue(Stream.of(user1, user2, user3)
                .allMatch(user ->
                        user.getUsername().equals(UserDAO.getUsernameById(user, connection))));
    }
}