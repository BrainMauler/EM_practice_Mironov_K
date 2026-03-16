package userservice.dao;

import lombok.experimental.UtilityClass;
import userservice.pojo.Role;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@UtilityClass
public class RoleDAO {

    public boolean insertRole(Role role, Connection connection) {
        String sql = "INSERT INTO roles (id, role_name) VALUES (?, ?)";
        int affectedRows;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, role.getId());
            statement.setString(2, role.getRoleName());
            affectedRows = statement.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
            return false;
        }
        return affectedRows > 0;
    }

    public boolean updateRole(Role role, int id, String role_name, Connection connection) {
        String sql = "UPDATE roles SET id = ?, role_name = ? WHERE id = ?";
        int affectedRows;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.setString(2, role_name);
            statement.setInt(3, role.getId());
            affectedRows = statement.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
            return false;
        }
        return affectedRows > 0;
    }

    public boolean deleteRole(Role role, Connection connection) {
        String sql = "DELETE FROM roles WHERE id = ?";
        int affectedUsersRows;
        try (PreparedStatement usersStatement = connection.prepareStatement(sql)) {
            usersStatement.setInt(1, role.getId());
            affectedUsersRows = usersStatement.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
            return false;
        }
        return affectedUsersRows > 0;
    }

    public String getRoleNameById(Role role, Connection connection) {
        String sql = "SELECT role_name FROM roles WHERE id = ?";
        String roleName = null;
        try (PreparedStatement usersStatement = connection.prepareStatement(sql)) {
            usersStatement.setInt(1, role.getId());
            try (ResultSet set = usersStatement.executeQuery()) {
                if (set.next()) {
                    roleName = set.getString("role_name");
                }
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
            return null;
        }
        return roleName;
    }

    public String getRoleNameById(int id, Connection connection) {
        String sql = "SELECT role_name FROM roles WHERE id = ?";
        String roleName = null;
        try (PreparedStatement usersStatement = connection.prepareStatement(sql)) {
            usersStatement.setInt(1, id);
            try (ResultSet set = usersStatement.executeQuery()) {
                if (set.next()) {
                    roleName = set.getString("role_name");
                }
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
            return null;
        }
        return roleName;
    }

    public boolean clearRolesTable(Connection connection) {
        String sql = "DELETE FROM roles";
        int affectedRows;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            affectedRows = statement.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
            return false;
        }
        return affectedRows > 0;
    }

    public boolean tableRolesIsEmpty(Connection connection) {
        String sql = "SELECT COUNT(*) FROM roles";
        int result = -1;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet set = statement.executeQuery();
            if (set.next()) {
                result = set.getInt(1);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
            return false;
        }
        return result == 0;
    }
}