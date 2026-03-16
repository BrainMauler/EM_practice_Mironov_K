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

public class UserServiceDBTests extends BaseUserServiceDBTest {

    @Test
    @DisplayName("Добавление новых пользователей в таблицу users")
    void insertUserTestPos() {
        for (User user : List.of(user1, user2, user3)) {
            AssertsUtils.assertTrueWithLogs(UserDAO.insertUser(user, CONNECTION),
                    INSERT_USER_TEST_POS, INSERT_USER_TEST_POS_OP + user.getId(), COLLECTION);
            Assertions.assertEquals(user.getUsername(), UserDAO.getUsernameById(user, CONNECTION));
        }
        Assertions.assertTrue(UserDAO.clearUsersTable(CONNECTION));
    }

    @Test
    @DisplayName("Добавление новых ролей в таблицу roles")
    void insertRoleTestPos() {
        for (Role role : List.of(role1, role2, role3, role4)) {
            AssertsUtils.assertTrueWithLogs(RoleDAO.insertRole(role, CONNECTION),
                    INSERT_ROLE_TEST_POS, INSERT_ROLE_TEST_POS_OP + role.getId(), COLLECTION);
            Assertions.assertEquals(role.getRoleName(), RoleDAO.getRoleNameById(role, CONNECTION));
        }
        Assertions.assertTrue(RoleDAO.clearRolesTable(CONNECTION));
    }

    @Test
    @DisplayName("Синхронизация пользователей с их ролями в таблице users_roles")
    void syncUsersRolesTest() {
        for (User user : List.of(user1, user3)) {
            AssertsUtils.assertTrueWithLogs(UserDAO.insertUser(user, CONNECTION),
                    INSERT_USER_TEST_POS, INSERT_USER_TEST_POS_OP + user.getId(), COLLECTION);
            Assertions.assertEquals(user.getUsername(), UserDAO.getUsernameById(user, CONNECTION));
        }
        for (Role role : List.of(role1, role2, role3)) {
            AssertsUtils.assertTrueWithLogs(RoleDAO.insertRole(role, CONNECTION),
                    INSERT_ROLE_TEST_POS, INSERT_ROLE_TEST_POS_OP + role.getId(), COLLECTION);
            Assertions.assertEquals(role.getRoleName(), RoleDAO.getRoleNameById(role, CONNECTION));
        }
        for (User user : List.of(user1, user3)) {
            AssertsUtils.assertTrueWithLogs(UsersRolesDAO.updateUserRoles(user, CONNECTION),
                    SYNC_USERS_ROLES_TEST, SYNC_USERS_ROLES_TEST_OP + user.getId(), COLLECTION);
            Assertions.assertEquals(user.getRoles(), UsersRolesDAO.getRolesIdByUser(user, CONNECTION));
        }
        Assertions.assertTrue(UserDAO.clearUsersTable(CONNECTION));
        Assertions.assertTrue(RoleDAO.clearRolesTable(CONNECTION));
    }

    @Test
    @DisplayName("Обновление ролей пользователя в таблице users_roles")
    void updateUserRolesTest() {
        AssertsUtils.assertTrueWithLogs(UserDAO.insertUser(user1, CONNECTION),
                INSERT_USER_TEST_POS, INSERT_USER_TEST_POS_OP + user1.getId(), COLLECTION);
        Assertions.assertEquals(user1.getUsername(), UserDAO.getUsernameById(user1, CONNECTION));
        for (Role role : List.of(role1, role4)) {
            AssertsUtils.assertTrueWithLogs(RoleDAO.insertRole(role, CONNECTION),
                    INSERT_ROLE_TEST_POS, INSERT_ROLE_TEST_POS_OP + role.getId(), COLLECTION);
            Assertions.assertEquals(role.getRoleName(), RoleDAO.getRoleNameById(role, CONNECTION));
        }
        AssertsUtils.assertTrueWithLogs(UsersRolesDAO.updateUserRoles(user1, CONNECTION),
                SYNC_USERS_ROLES_TEST, SYNC_USERS_ROLES_TEST_OP + user1.getId(), COLLECTION);
        Assertions.assertEquals(user1.getRoles(), UsersRolesDAO.getRolesIdByUser(user1, CONNECTION));
        HashSet<Integer> newUserRolesSet = new HashSet<>(user1.getRoles());
        newUserRolesSet.add(role4.getId());
        newUserRolesSet.remove(role1.getId());
        user1.setRoles(newUserRolesSet);
        AssertsUtils.assertTrueWithLogs(UsersRolesDAO.updateUserRoles(user1, CONNECTION),
                UPDATE_USER_ROLES_TEST, UPDATE_USER_ROLES_TEST_OP, COLLECTION);
        Assertions.assertEquals(user1.getRoles(), UsersRolesDAO.getRolesIdByUser(user1, CONNECTION));
        Assertions.assertTrue(UserDAO.clearUsersTable(CONNECTION));
        Assertions.assertTrue(RoleDAO.clearRolesTable(CONNECTION));
        newUserRolesSet.clear();
        newUserRolesSet.add(role1.getId());
        user1.setRoles(newUserRolesSet);
    }

    @Test
    @DisplayName("Добавление нового пользователя в таблицу users(нег - нарушение уникальности ПК)")
    void insertUserTestNeg() {
        AssertsUtils.assertTrueWithLogs(UserDAO.insertUser(user1, CONNECTION),
                INSERT_USER_TEST_POS, INSERT_USER_TEST_POS_OP + user1.getId(), COLLECTION);
        Assertions.assertEquals(user1.getUsername(), UserDAO.getUsernameById(user1, CONNECTION));
        AssertsUtils.assertSQLThrowsWithLogs(() -> UserDAO.insertUser(user1, CONNECTION),
                INSERT_USER_TEST_NEG, INSERT_USER_TEST_NEG_OP, COLLECTION);
        Assertions.assertTrue(UserDAO.clearUsersTable(CONNECTION));
    }

    @Test
    @DisplayName("Добавление новой роли в таблицу roles(нег - нарушение уникальности ПК)")
    void insertRoleTestNeg() {
        AssertsUtils.assertTrueWithLogs(RoleDAO.insertRole(role1, CONNECTION),
                    INSERT_ROLE_TEST_POS, INSERT_ROLE_TEST_POS_OP + role1.getId(), COLLECTION);
        Assertions.assertEquals(role1.getRoleName(), RoleDAO.getRoleNameById(role1, CONNECTION));
        AssertsUtils.assertSQLThrowsWithLogs(() -> RoleDAO.insertRole(role1, CONNECTION),
                INSERT_ROLE_TEST_NEG, INSERT_ROLE_TEST_NEG_OP, COLLECTION);
        Assertions.assertTrue(RoleDAO.clearRolesTable(CONNECTION));
    }

    @Test
    @DisplayName("Добавление нового пользователя в таблицу users(нег - нарушение требования к полю NotNull)")
    void insertUserTestNullNeg() {
        AssertsUtils.assertSQLThrowsWithLogs(() -> UserDAO.insertUser(userNull, CONNECTION),
                INSERT_USER_TEST_NULL_NEG, INSERT_USER_TEST_NULL_NEG_OP, COLLECTION);
        Assertions.assertNotEquals(userNull.getUsername(), UserDAO.getUsernameById(userNull, CONNECTION));
    }

    @ParameterizedTest
    @CsvSource(value = {"100, Sasha01, qwerty",
            "0, s, 0000",
            "11, s1, qwerty1",
            "1, Sasha, qwerty1"})
    @DisplayName("Обновление существующего пользователя в таблице users")
    void updateUserTestPos(int id, String username, String password) {
        AssertsUtils.assertTrueWithLogs(UserDAO.insertUser(user1, CONNECTION),
                INSERT_USER_TEST_POS, INSERT_USER_TEST_POS_OP + user1.getId(), COLLECTION);
        Assertions.assertEquals(user1.getUsername(), UserDAO.getUsernameById(user1, CONNECTION));
        AssertsUtils.assertTrueWithLogs(UserDAO.updateUser(user1, id, username, password, CONNECTION),
                UPDATE_USER_TEST_POS, UPDATE_USER_TEST_POS_OP + id, COLLECTION);
        Assertions.assertEquals(UserDAO.getPasswordById(id, CONNECTION), password);
        Assertions.assertEquals(UserDAO.getUsernameById(id, CONNECTION), username);
        Assertions.assertTrue(UserDAO.clearUsersTable(CONNECTION));
    }

    @ParameterizedTest
    @CsvSource(value = {"1,  , qwerty1",
            "1, Sasha, q1",})
    @DisplayName("Обновление существующего пользователя в таблице users(нег)")
    void updateUserTestNeg(int id, String username, String password) {
        AssertsUtils.assertTrueWithLogs(UserDAO.insertUser(user1, CONNECTION),
                INSERT_USER_TEST_POS, INSERT_USER_TEST_POS_OP + user1.getId(), COLLECTION);
        Assertions.assertEquals(user1.getUsername(), UserDAO.getUsernameById(user1, CONNECTION));
        AssertsUtils.assertSQLThrowsWithLogs(() -> UserDAO.updateUser(user1, id, username,
                        password, CONNECTION),
                UPDATE_USER_TEST_NEG, UPDATE_USER_TEST_NEG_OP + id, COLLECTION);
        Assertions.assertTrue(UserDAO.clearUsersTable(CONNECTION));
    }

    @ParameterizedTest
    @CsvSource(value = {"1, common_user",
            "0, u1",
            "100, user"})
    @DisplayName("Обновление существующей роли в таблице roles")
    void updateRoleTestPos(int id, String roleName) {
        AssertsUtils.assertTrueWithLogs(RoleDAO.insertRole(role1, CONNECTION),
                INSERT_ROLE_TEST_POS, INSERT_ROLE_TEST_POS_OP + role1.getId(), COLLECTION);
        Assertions.assertEquals(role1.getRoleName(), RoleDAO.getRoleNameById(role1, CONNECTION));
        AssertsUtils.assertTrueWithLogs(RoleDAO.updateRole(role1, id, roleName, CONNECTION),
                UPDATE_ROLE_TEST_POS, UPDATE_ROLE_TEST_POS_OP + id, COLLECTION);
        Assertions.assertEquals(RoleDAO.getRoleNameById(id, CONNECTION), roleName);
        Assertions.assertTrue(RoleDAO.clearRolesTable(CONNECTION));
    }

    @ParameterizedTest
    @CsvSource(value = {"100,  ",
            "100, /",
            "100, 1"})
    @DisplayName("Обновление существующей роли в таблице roles(нег)")
    void updateRoleTestNeg(int id, String RoleName) {
        AssertsUtils.assertTrueWithLogs(RoleDAO.insertRole(role1, CONNECTION),
                INSERT_ROLE_TEST_POS, INSERT_ROLE_TEST_POS_OP + role1.getId(), COLLECTION);
        Assertions.assertEquals(role1.getRoleName(), RoleDAO.getRoleNameById(role1, CONNECTION));
        AssertsUtils.assertSQLThrowsWithLogs(() -> RoleDAO.updateRole(role1, id, RoleName, CONNECTION),
                UPDATE_ROLE_TEST_NEG, UPDATE_ROLE_TEST_NEG_OP + id, COLLECTION);
        Assertions.assertTrue(RoleDAO.clearRolesTable(CONNECTION));
    }

    @Test
    @DisplayName("Удаление пользователя из всех таблиц")
    void deleteUserTest() {
        AssertsUtils.assertTrueWithLogs(UserDAO.insertUser(user3, CONNECTION),
                INSERT_USER_TEST_POS, INSERT_USER_TEST_POS_OP + user3.getId(), COLLECTION);
        Assertions.assertEquals(user3.getUsername(), UserDAO.getUsernameById(user3, CONNECTION));
        for (Role role : List.of(role1, role2, role3)) {
            AssertsUtils.assertTrueWithLogs(RoleDAO.insertRole(role, CONNECTION),
                    INSERT_ROLE_TEST_POS, INSERT_ROLE_TEST_POS_OP + role.getId(), COLLECTION);
            Assertions.assertEquals(role.getRoleName(), RoleDAO.getRoleNameById(role, CONNECTION));
        }
        AssertsUtils.assertTrueWithLogs(UsersRolesDAO.updateUserRoles(user3, CONNECTION),
                SYNC_USERS_ROLES_TEST, SYNC_USERS_ROLES_TEST_OP + user3.getId(), COLLECTION);
        Assertions.assertEquals(user3.getRoles(), UsersRolesDAO.getRolesIdByUser(user3, CONNECTION));
        AssertsUtils.assertTrueWithLogs(UserDAO.deleteUser(user3, CONNECTION),
                DELETE_USER_TEST, DELETE_USER_TEST_OP + user3.getId(), COLLECTION);
        Assertions.assertNotEquals(user3.getUsername(), UserDAO.getUsernameById(user3, CONNECTION));
        Assertions.assertNotEquals(user3.getRoles(), UsersRolesDAO.getRolesIdByUser(user3, CONNECTION));
        Assertions.assertTrue(RoleDAO.clearRolesTable(CONNECTION));
    }

    @Test
    @DisplayName("Удаление роли из таблицы roles")
    void deleteRoleTest() {
        AssertsUtils.assertTrueWithLogs(RoleDAO.insertRole(role1, CONNECTION),
                INSERT_ROLE_TEST_POS, INSERT_ROLE_TEST_POS_OP + role1.getId(), COLLECTION);
        Assertions.assertEquals(role1.getRoleName(), RoleDAO.getRoleNameById(role1, CONNECTION));
        AssertsUtils.assertTrueWithLogs(RoleDAO.deleteRole(role1, CONNECTION),
                DELETE_ROLE_TEST, DELETE_ROLE_TEST_OP + role1.getId(), COLLECTION);
        Assertions.assertNotEquals(role1.getRoleName(), RoleDAO.getRoleNameById(role1, CONNECTION));
    }
}