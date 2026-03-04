import userservice.dao.RoleDAO;
import userservice.dao.UserDAO;
import userservice.dao.UsersRolesDAO;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import utils.Logger;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserServiceDBTests extends BaseUserServiceDBTest {

    @Test
    @Order(1)
    @DisplayName("Добавление нового пользователя(поз)")
    void insertUserTestPos() {
        Assertions.assertTrue(Stream.of(user1, user2, user3, user4, user5)
                .allMatch(user -> UserDAO.insertUser(user, connection)));
        Assertions.assertTrue(Stream.of(user1, user2, user3, user4, user5)
                .allMatch(user ->
                        user.getUsername().equals(UserDAO.getUsernameById(user, connection))));
        Logger.log("Добавление нового пользователя(поз)", "INSERT", "SUCCESS",
                   "В таблицу users добавлено 5 записей", collection);
    }

    @Test
    @Order(2)
    @DisplayName("Добавление новых ролей(поз)")
    void insertRolesTestPos() {
        Assertions.assertTrue(Stream.of(role1, role2, role3, role4)
                .allMatch(role -> RoleDAO.insertRole(role, connection)));
        Assertions.assertTrue(Stream.of(role1, role2, role3, role4)
                .allMatch(role ->
                        role.getRoleName().equals(RoleDAO.getRoleNameById(role, connection))));
    }

    @Test
    @Order(3)
    @DisplayName("Синхронизация ролей пользователей")
    void syncUsersRolesTest() {
        Assertions.assertTrue(Stream.of(user1, user2, user3, user4, user5)
                .allMatch(user -> UsersRolesDAO.updateUserRoles(user, connection)));
        Assertions.assertTrue(Stream.of(user1, user2, user3, user4, user5)
                .allMatch(user ->
                        user.getRoles().equals(UsersRolesDAO.getRolesIdByUser(user, connection))));
    }

    @Test
    @DisplayName("Обновление ролей пользователя")
    void updateUserRolesTest() {
        HashSet<Integer> newUserRolesSet = new HashSet<>(user1.getRoles());
        newUserRolesSet.add(role4.getId());
        newUserRolesSet.remove(role1.getId());
        user1.setRoles(newUserRolesSet);
        Assertions.assertTrue(UsersRolesDAO.updateUserRoles(user1, connection));
        Assertions.assertEquals(user1.getRoles(), UsersRolesDAO.getRolesIdByUser(user1, connection));
    }

    @Test
    @DisplayName("Добавление нового пользователя(нег - нарушение уникальности ПК)")
    void insertUserTestNeg() {
        Assertions.assertThrows(SQLException.class, () -> UserDAO.insertUser(user1, connection));
    }

    @Test
    @DisplayName("Добавление новой роли(нег - нарушение уникальности ПК)")
    void insertRoleTestNeg() {
        Assertions.assertThrows(SQLException.class, () -> RoleDAO.insertRole(role1, connection));
    }

    @Test
    @DisplayName("Добавление нового пользователя(нег - нарушение требования к полю NotNull)")
    void insertUserTestNullNeg() {
        Assertions.assertThrows(SQLException.class, () -> UserDAO.insertUser(user6, connection));
    }

    @ParameterizedTest
    @CsvSource(value = {"100, Sasha01, qwerty",
                        "0, ., 0000",
                        "1, 1, qwerty1",
                        "1, Sasha, qwerty1"})
    @DisplayName("Обновление пользователя(поз)")
    void updateUserTestPos(int id, String username, String password) {
        Assertions.assertTrue(UserDAO.updateUser(user1, id, username, password, connection));
        Assertions.assertEquals(UserDAO.getPasswordById(user1, connection), password);
        Assertions.assertEquals(UserDAO.getUsernameById(user1, connection), username);
    }

    @ParameterizedTest
    @CsvSource(value = {"1,  , qwerty1",
                        "1, Sasha, q1",})
    @DisplayName("Обновление пользователя(нег)")
    void updateUserTestNeg(int id, String username, String password) {
        Assertions.assertThrows(SQLException.class, () ->
                UserDAO.updateUser(user1, id, username, password, connection));
    }

    @ParameterizedTest
    @CsvSource(value = {"1, common_user",
                        "0, u1",
                        "100, user"})
    @DisplayName("Обновление роли(поз)")
    void updateRoleTestPos(int id, String roleName) {
        Assertions.assertTrue(RoleDAO.updateRole(role1, id, roleName, connection));
        Assertions.assertEquals(RoleDAO.getRoleNameById(role1, connection), roleName);
    }

    @ParameterizedTest
    @CsvSource(value = {"100,  ",
                        "100, /"})
    @DisplayName("Обновление роли(нег)")
    void updateRoleTestNeg(int id, String RoleName) {
        Assertions.assertThrows(SQLException.class, () ->
                RoleDAO.updateRole(role1, id, RoleName, connection));
    }
}