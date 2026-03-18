import userservice.dao.RoleDAO;
import userservice.dao.UserDAO;
import userservice.dao.UsersRolesDAO;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import userservice.pojo.Role;
import userservice.pojo.User;
import utils.AssertsUtils;

import java.util.List;
import java.util.Set;

public class UserServiceDBTests extends BaseUserServiceDBTest {

    @Test
    @DisplayName("Добавление новых пользователей в таблицу users")
    void insertUserTestPos() {
        for (User user : List.of(user1, user2, user3)) {
            UserDAO.insertUser(user, CONNECTION);
            AssertsUtils.assertEqualsWithLogs(user.getUsername(), UserDAO.getUsernameById(user, CONNECTION),
                    INSERT_USER_TEST_POS, INSERT_USER_TEST_POS_OP, COLLECTION);
        }
    }

    @Test
    @DisplayName("Добавление новых ролей в таблицу roles")
    void insertRoleTestPos() {
        for (Role role : List.of(role1, role2, role3, role4)) {
            RoleDAO.insertRole(role, CONNECTION);
            AssertsUtils.assertEqualsWithLogs(role.getRoleName(), RoleDAO.getRoleNameById(role, CONNECTION),
                    INSERT_ROLE_TEST_POS, INSERT_ROLE_TEST_POS_OP + role.getId(), COLLECTION);
        }
    }

    @Test
    @DisplayName("Синхронизация пользователей с их ролями в таблице users_roles")
    void syncUsersRolesTest() {
        for (User user : List.of(user1, user3)) {
            UserDAO.insertUser(user, CONNECTION);
        }
        for (Role role : List.of(role1, role2, role3)) {
            RoleDAO.insertRole(role, CONNECTION);
        }
        for (User user : List.of(user1, user3)) {
            UsersRolesDAO.updateUserRoles(user, CONNECTION);
            AssertsUtils.assertEqualsWithLogs(user.getRoles(), UsersRolesDAO.getRolesIdByUser(user, CONNECTION),
                    SYNC_USERS_ROLES_TEST, SYNC_USERS_ROLES_TEST_OP + user.getId(), COLLECTION);
        }
    }

    @Test
    @DisplayName("Обновление ролей пользователя в таблице users_roles")
    void updateUserRolesTest() {
        UserDAO.insertUser(user1, CONNECTION);
        for (Role role : List.of(role1, role4)) {
            RoleDAO.insertRole(role, CONNECTION);
        }
        UsersRolesDAO.updateUserRoles(user1, CONNECTION);
        User.switchUserRoles(user1, role4, role1);
        UsersRolesDAO.updateUserRoles(user1, CONNECTION);
        AssertsUtils.assertEqualsWithLogs(user1.getRoles(), UsersRolesDAO.getRolesIdByUser(user1, CONNECTION),
                UPDATE_USER_ROLES_TEST, UPDATE_USER_ROLES_TEST_OP, COLLECTION);
        User.switchUserRoles(user1, role1, role4);
    }

    @Test
    @DisplayName("Добавление нового пользователя в таблицу users(нег - нарушение уникальности ПК)")
    void insertUserTestNeg() {
        UserDAO.insertUser(user1, CONNECTION);
        AssertsUtils.assertSQLThrowsWithLogs(() -> UserDAO.insertUser(user1, CONNECTION),
                INSERT_USER_TEST_NEG, INSERT_USER_TEST_NEG_OP, COLLECTION);
    }

    @Test
    @DisplayName("Добавление новой роли в таблицу roles(нег - нарушение уникальности ПК)")
    void insertRoleTestNeg() {
        RoleDAO.insertRole(role1, CONNECTION);
        AssertsUtils.assertSQLThrowsWithLogs(() -> RoleDAO.insertRole(role1, CONNECTION),
                INSERT_ROLE_TEST_NEG, INSERT_ROLE_TEST_NEG_OP, COLLECTION);
    }

    @Test
    @DisplayName("Добавление нового пользователя в таблицу users(нег - нарушение требования к полю NotNull)")
    void insertUserTestNullNeg() {
        AssertsUtils.assertSQLThrowsWithLogs(() -> UserDAO.insertUser(userNull, CONNECTION),
                INSERT_USER_TEST_NULL_NEG, INSERT_USER_TEST_NULL_NEG_OP, COLLECTION);
    }

    @ParameterizedTest
    @CsvSource(value = {"100, Sasha01, qwerty",
            "0, s, 0000",
            "11, s1, qwerty1",
            "1, Sasha, qwerty1"})
    @DisplayName("Обновление существующего пользователя в таблице users")
    void updateUserTestPos(int id, String username, String password) {
        UserDAO.insertUser(user1, CONNECTION);
        UserDAO.updateUser(user1, id, username, password, CONNECTION);
        AssertsUtils.assertEqualsWithLogs(UserDAO.getUsernameById(id, CONNECTION), username,
                UPDATE_USER_TEST_POS, UPDATE_USER_TEST_POS_OP + id, COLLECTION);
    }

    @ParameterizedTest
    @CsvSource(value = {"1,  , qwerty1",
            "1, Sasha, q1",})
    @DisplayName("Обновление существующего пользователя в таблице users(нег)")
    void updateUserTestNeg(int id, String username, String password) {
        UserDAO.insertUser(user1, CONNECTION);
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
        RoleDAO.insertRole(role1, CONNECTION);
        RoleDAO.updateRole(role1, id, roleName, CONNECTION);
        AssertsUtils.assertEqualsWithLogs(RoleDAO.getRoleNameById(id, CONNECTION), roleName,
                UPDATE_ROLE_TEST_POS, UPDATE_ROLE_TEST_POS_OP + id, COLLECTION);
    }

    @ParameterizedTest
    @CsvSource(value = {"100,  ",
            "100, /",
            "100, 1"})
    @DisplayName("Обновление существующей роли в таблице roles(нег)")
    void updateRoleTestNeg(int id, String RoleName) {
        RoleDAO.insertRole(role1, CONNECTION);
        AssertsUtils.assertSQLThrowsWithLogs(() -> RoleDAO.updateRole(role1, id, RoleName, CONNECTION),
                UPDATE_ROLE_TEST_NEG, UPDATE_ROLE_TEST_NEG_OP + id, COLLECTION);
    }

    @Test
    @DisplayName("Удаление пользователя из всех таблиц")
    void deleteUserTest() {
        UserDAO.insertUser(user3, CONNECTION);
        for (Role role : List.of(role1, role2, role3)) {
            RoleDAO.insertRole(role, CONNECTION);
        }
        UsersRolesDAO.updateUserRoles(user3, CONNECTION);
        UserDAO.deleteUser(user3, CONNECTION);
        AssertsUtils.assertNullWithLogs(UserDAO.getUsernameById(user3, CONNECTION),
                DELETE_USER_TEST, DELETE_USER_TEST_OP + user3.getId(), COLLECTION);
        Assertions.assertEquals(Set.of(), UsersRolesDAO.getRolesIdByUser(user3, CONNECTION));
    }

    @Test
    @DisplayName("Удаление роли из таблицы roles")
    void deleteRoleTest() {
        RoleDAO.insertRole(role1, CONNECTION);
        RoleDAO.deleteRole(role1, CONNECTION);
        AssertsUtils.assertNullWithLogs(RoleDAO.getRoleNameById(role1, CONNECTION),
                DELETE_ROLE_TEST, DELETE_ROLE_TEST_OP + role1.getId(), COLLECTION);
    }
}