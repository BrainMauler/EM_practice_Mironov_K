package UserService;

import lombok.experimental.UtilityClass;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;

@UtilityClass
public class UsersRolesDAO {

    public boolean insertUserRole(User user, Connection connection) {
        String sql = "INSERT INTO users_roles (user_id, role_id) VALUES (?, ?)";
        int usersRolesInsertsCount = 0;
        int affectedRows;
        for (Integer role_id : user.getRoles()) {
            try (connection; PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, user.getId());
                statement.setInt(2, role_id);
                affectedRows = statement.executeUpdate();
                if (affectedRows > 0) {
                    usersRolesInsertsCount++;
                }
            } catch (SQLException exception) {
                exception.printStackTrace();
                return false;
            }
        }
        return usersRolesInsertsCount == user.getRoles().size();
    }

    public HashSet<Integer> getUsersIdByRole(Role role, Connection connection) {
        String sql = "SELECT user_id FROM users_roles WHERE role_id = ?";
        HashSet<Integer> usersId = new HashSet<>();
        try (connection; PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, role.getId());
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                usersId.add(set.getInt("user_id"));
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
            return null;
        }
        return usersId;
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