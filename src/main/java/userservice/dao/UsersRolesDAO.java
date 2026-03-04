package userservice.dao;

import lombok.experimental.UtilityClass;
import userservice.pojo.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;

@UtilityClass
public class UsersRolesDAO {

    public boolean updateUserRoles(User user, Connection connection) {
        if (user.getRoles().equals(getRolesIdByUser(user, connection))) {
            return true;
        }
        HashSet<Integer> diffInsertSet;
        HashSet<Integer> diffDeleteSet;
        int usersRolesInsertCount = 0;
        int usersRolesDeleteCount = 0;
        int affectedRows;
        String insertSql = "INSERT INTO users_roles (user_id, role_id) VALUES (?, ?)";
        String deleteSql = "DELETE * FROM users_roles WHERE user_id = ? AND role_id = ?";
        diffInsertSet = user.getRoles();
        diffInsertSet.removeAll(getRolesIdByUser(user, connection));
        diffDeleteSet = getRolesIdByUser(user, connection);
        diffDeleteSet.removeAll(user.getRoles());
        for (Integer role_id : diffInsertSet) {
            try (connection; PreparedStatement statement = connection.prepareStatement(insertSql)) {
                statement.setInt(1, user.getId());
                statement.setInt(2, role_id);
                affectedRows = statement.executeUpdate();
                if (affectedRows > 0) {
                    usersRolesInsertCount++;
                }
            } catch (SQLException exception) {
                exception.printStackTrace();
                return false;
            }
        }
        for (Integer role_id : diffDeleteSet) {
            try (connection; PreparedStatement statement = connection.prepareStatement(deleteSql)) {
                statement.setInt(1, user.getId());
                statement.setInt(2, role_id);
                affectedRows = statement.executeUpdate();
                if (affectedRows > 0) {
                    usersRolesDeleteCount++;
                }
            } catch (SQLException exception) {
                exception.printStackTrace();
                return false;
            }
        }
        return (usersRolesInsertCount == diffInsertSet.size()) &&
                (usersRolesDeleteCount == diffDeleteSet.size());
    }

    public HashSet<Integer> getRolesIdByUser(User user, Connection connection) {
        String sql = "SELECT role_id FROM users_roles WHERE user_id = ?";
        HashSet<Integer> rolesId = new HashSet<>();
        try (connection; PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, user.getId());
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                rolesId.add(set.getInt("user_id"));
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
            return null;
        }
        return rolesId;
    }
}