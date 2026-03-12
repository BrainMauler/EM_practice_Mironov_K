package userservice.dao;

import lombok.experimental.UtilityClass;
import userservice.pojo.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@UtilityClass
public class UserDAO {

    public boolean insertUser(User user, Connection connection) {
        String sql = "INSERT INTO users (id, username, password) VALUES (?, ?, ?)";
        int affectedUsersRows;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, user.getId());
            statement.setString(2, user.getUsername());
            statement.setString(3, user.getPassword());
            affectedUsersRows = statement.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
            return false;
        }
        return affectedUsersRows > 0;
    }

    /* Дабы не перегружать учебную задачу, не стал реализовывать частичное обновление пользователя,
    ибо учебного смысла не вижу - эти методы проще приведенного ниже, хоть и дают покрытие. */
    public boolean updateUser(User user, int id, String username,
                              String password, Connection connection) {
        String sql = "UPDATE users SET id = ?, username = ?, password = ? WHERE id = ?";
        int affectedRows;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.setString(2, username);
            statement.setString(3, password);
            statement.setInt(4, user.getId());
            affectedRows = statement.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
            return false;
        }
        return affectedRows > 0;
    }

    public boolean deleteUser(User user, Connection connection) {
        String sql = "DELETE FROM users WHERE id = ?";
        int affectedUsersRows;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, user.getId());
            affectedUsersRows = statement.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
            return false;
        }
        return affectedUsersRows > 0;
    }

    public String getUsernameById(User user, Connection connection) {
        String sql = "SELECT username FROM users WHERE id = ?";
        String username = null;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, user.getId());
            try (ResultSet set = statement.executeQuery()) {
                if (set.next()) {
                    username = set.getString("username");
                }
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
            return null;
        }
        return username;
    }

    public String getPasswordById(User user, Connection connection) {
        String sql = "SELECT password FROM users WHERE id = ?";
        String password = null;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, user.getId());
            try (ResultSet set = statement.executeQuery()) {
                if (set.next()) {
                    password = set.getString("username");
                }
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
            return null;
        }
        return password;
    }
}