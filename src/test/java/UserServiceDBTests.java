import userservice.dao.RoleDAO;
import userservice.dao.UserDAO;
import userservice.dao.UsersRolesDAO;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import userservice.pojo.Role;
import userservice.pojo.User;
import utils.AssertsUtils;

import java.util.HashSet;
import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserServiceDBTests extends BaseUserServiceDBTest {

    @Test
    @Order(1)
    @DisplayName("Добавление новых пользователей в таблицу users")
    void insertUserTestPos() {
        for (User user : List.of(user1, user2, user3, user4, user5)) {
            AssertsUtils.assertTrueWithLogs(UserDAO.insertUser(user, CONNECTION),
                    INSERT_USER_TEST_POS, INSERT_USER_TEST_POS_OP + user.getId(), COLLECTION);
            Assertions.assertEquals(user.getUsername(), UserDAO.getUsernameById(user, CONNECTION));
        }
    }

    @Test
    @Order(2)
    @DisplayName("Добавление новых ролей в таблицу roles")
    void insertRoleTestPos() {
        for (Role role : List.of(role1, role2, role3, role4)) {
            AssertsUtils.assertTrueWithLogs(RoleDAO.insertRole(role, CONNECTION),
                    INSERT_ROLE_TEST_POS, INSERT_ROLE_TEST_POS_OP + role.getId(), COLLECTION);
            Assertions.assertEquals(role.getRoleName(), RoleDAO.getRoleNameById(role, CONNECTION));
        }
    }

    @Test
    @Order(3)
    @DisplayName("Синхронизация пользователей с их ролями в таблице users_roles")
    void syncUsersRolesTest() {
        for (User user : List.of(user1, user2, user3, user4, user5)) {
            AssertsUtils.assertTrueWithLogs(UsersRolesDAO.updateUserRoles(user, CONNECTION),
                    SYNC_USERS_ROLES_TEST, SYNC_USERS_ROLES_TEST_OP + user.getId(), COLLECTION);
            Assertions.assertEquals(user.getRoles(), UsersRolesDAO.getRolesIdByUser(user, CONNECTION));
        }
    }

    @Test
    @DisplayName("Обновление ролей пользователя в таблице users_roles")
    void updateUserRolesTest() {
        HashSet<Integer> newUserRolesSet = new HashSet<>(user1.getRoles());
        newUserRolesSet.add(role4.getId());
        newUserRolesSet.remove(role1.getId());
        user1.setRoles(newUserRolesSet);
        AssertsUtils.assertTrueWithLogs(UsersRolesDAO.updateUserRoles(user1, CONNECTION),
                UPDATE_USER_ROLES_TEST, UPDATE_USER_ROLES_TEST_OP, COLLECTION);
        Assertions.assertEquals(user1.getRoles(), UsersRolesDAO.getRolesIdByUser(user1, CONNECTION));
    }

    @Test
    @DisplayName("Добавление нового пользователя в таблицу users(нег - нарушение уникальности ПК)")
    void insertUserTestNeg() {
        AssertsUtils.assertSQLThrowsWithLogs(() -> UserDAO.insertUser(user1, CONNECTION),
                INSERT_USER_TEST_NEG, INSERT_USER_TEST_NEG_OP, COLLECTION);
    }

    @Test
    @DisplayName("Добавление новой роли в таблицу roles(нег - нарушение уникальности ПК)")
    void insertRoleTestNeg() {
        AssertsUtils.assertSQLThrowsWithLogs(() -> RoleDAO.insertRole(role1, CONNECTION),
                INSERT_ROLE_TEST_NEG, INSERT_ROLE_TEST_NEG_OP, COLLECTION);
    }

    @Test
    @DisplayName("Добавление нового пользователя в таблицу users(нег - нарушение требования к полю NotNull)")
    void insertUserTestNullNeg() {
        AssertsUtils.assertSQLThrowsWithLogs(() -> UserDAO.insertUser(user6, CONNECTION),
                INSERT_USER_TEST_NULL_NEG, INSERT_USER_TEST_NULL_NEG_OP, COLLECTION);
    }

    @ParameterizedTest
    @CsvSource(value = {"100, Sasha01, qwerty",
            "0, ., 0000",
            "1, 1, qwerty1",
            "1, Sasha, qwerty1"})
    @DisplayName("Обновление существующего пользователя в таблице users")
    void updateUserTestPos(int id, String username, String password) {
        AssertsUtils.assertTrueWithLogs(UserDAO.updateUser(user1, id, username, password, CONNECTION),
                UPDATE_USER_TEST_POS, UPDATE_USER_TEST_POS_OP + id, COLLECTION);
        Assertions.assertEquals(UserDAO.getPasswordById(user1, CONNECTION), password);
        Assertions.assertEquals(UserDAO.getUsernameById(user1, CONNECTION), username);
    }

    @ParameterizedTest
    @CsvSource(value = {"1,  , qwerty1",
            "1, Sasha, q1",})
    @DisplayName("Обновление существующего пользователя в таблице users(нег)")
    void updateUserTestNeg(int id, String username, String password) {
        AssertsUtils.assertSQLThrowsWithLogs(() -> UserDAO.updateUser(user1, id, username,
                        password, CONNECTION),
                UPDATE_USER_TEST_NEG, UPDATE_USER_TEST_NEG_OP + id, COLLECTION);
    }

    @ParameterizedTest
    @CsvSource(value = {"1, common_user",
            "0, u1",
            "100, user"})
    @DisplayName("Обновление существующей роли в таблице roles")
    void updateRoleTestPos(int id, String roleName) {
        AssertsUtils.assertTrueWithLogs(RoleDAO.updateRole(role1, id, roleName, CONNECTION),
                UPDATE_ROLE_TEST_POS, UPDATE_ROLE_TEST_POS_OP + id, COLLECTION);
        Assertions.assertEquals(RoleDAO.getRoleNameById(role1, CONNECTION), roleName);
    }

    @ParameterizedTest
    @CsvSource(value = {"100,  ",
            "100, /"})
    @DisplayName("Обновление существующей роли в таблице roles(нег)")
    void updateRoleTestNeg(int id, String RoleName) {
        AssertsUtils.assertSQLThrowsWithLogs(() -> RoleDAO.updateRole(role1, id, RoleName, CONNECTION),
                UPDATE_ROLE_TEST_NEG, UPDATE_ROLE_TEST_NEG_OP + id, COLLECTION);
    }

    @Test
    @DisplayName("Удаление пользователя из таблицы users")
    void deleteUserTest() {
        AssertsUtils.assertTrueWithLogs(UserDAO.insertUser(userDel, CONNECTION),
                INSERT_USER_TEST_POS, INSERT_USER_TEST_POS_OP + userDel.getId(), COLLECTION);
        Assertions.assertEquals(userDel.getUsername(), UserDAO.getUsernameById(userDel, CONNECTION));
        Assertions.assertEquals(userDel.getRoles(), UsersRolesDAO.getRolesIdByUser(userDel, CONNECTION));
        AssertsUtils.assertTrueWithLogs(UserDAO.deleteUser(userDel, CONNECTION),
                DELETE_USER_TEST, DELETE_USER_TEST_OP + userDel.getId(), COLLECTION);
        Assertions.assertNotEquals(userDel.getUsername(), UserDAO.getUsernameById(userDel, CONNECTION));
        Assertions.assertNotEquals(userDel.getRoles(), UsersRolesDAO.getRolesIdByUser(userDel, CONNECTION));
    }

    @Test
    @DisplayName("Удаление роли из таблицы roles")
    void deleteRoleTest() {
        AssertsUtils.assertTrueWithLogs(RoleDAO.insertRole(roleDel, CONNECTION),
                INSERT_ROLE_TEST_POS, INSERT_ROLE_TEST_POS_OP + roleDel.getId(), COLLECTION);
        Assertions.assertEquals(roleDel.getRoleName(), RoleDAO.getRoleNameById(roleDel, CONNECTION));
        AssertsUtils.assertTrueWithLogs(RoleDAO.deleteRole(roleDel, CONNECTION),
                DELETE_ROLE_TEST, DELETE_ROLE_TEST_OP + roleDel.getId(), COLLECTION);
        Assertions.assertNotEquals(roleDel.getRoleName(), RoleDAO.getRoleNameById(roleDel, CONNECTION));
    }
}