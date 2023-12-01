package net.byteboost.duck.utils;

import javafx.event.ActionEvent;
import net.byteboost.duck.keys.DBKeys;
import net.byteboost.duck.models.RegistryEntry;
import net.byteboost.duck.models.User;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBUtils {

    public static Connection getConnection() throws SQLException {
        try {
            return DriverManager.getConnection(DBKeys.getSQLDatabase(), DBKeys.getSQLUser(), DBKeys.getSQLPassword());
        }catch (SQLException exception){
            throw new RuntimeException(exception);
        }
    }

    public static void addUser(User user) {
        String sql = "INSERT INTO users(username,password,salt) VALUES(?,?,?)";
        try (Connection connection =  getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)){

            String salt = PasswordEncryptionUtils.getSalt();
            String EncryptedPass = PasswordEncryptionUtils.getEncryptedPassword(user.getPassword(), salt);

            stmt.setString(1, user.getUsername());
            stmt.setString(2, EncryptedPass);
            stmt.setString(3, salt);
            stmt.execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void changeAccountPassword(User user, String newPassword){
        String sql = "UPDATE users SET password = ? WHERE user_id = ?";
        try (Connection connection =  getConnection();PreparedStatement stmt = connection.prepareStatement(sql)){

            String salt = DBUtils.getUserSalt(user.getUsername());
            String encryptedPassword = PasswordEncryptionUtils.getEncryptedPassword(newPassword, salt);

            stmt.setString(1, encryptedPassword);
            stmt.setString(2, String.valueOf(user.getId()));
            stmt.execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void logInUser(ActionEvent event, User user){
        String sql = "SELECT password, salt from users where username=?";
        try (Connection connection = getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)){

            stmt.setString(1, user.getUsername());
            try(ResultSet rspassword = stmt.executeQuery()){
                if(!rspassword.isBeforeFirst()) throw new SQLException("User not found");

                while (rspassword.next()){
                    String salt = rspassword.getString("salt");
                    String EncryptedInput = PasswordEncryptionUtils.getEncryptedPassword(user.getPassword(), salt);
                    String retrievedPassword = rspassword.getString("password");

                    if (!retrievedPassword.equals(EncryptedInput)) throw new IOException("password does not match username");
                    GUIUtils.changeScene(event,"/fxml/upload.fxml","Duck - Upload");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void AddRegistry(int user_id, String doc_title, Date time){
        String sql = "INSERT INTO activity_register(user_id,document_title,access_date) VALUES (?,?,?)";
        try(Connection connection = getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)){

            stmt.setInt(1,user_id);
            stmt.setString(2,doc_title);
            stmt.setDate(3,time);
            stmt.execute();

        } catch (SQLException e){
            e.printStackTrace();
        }
    }
    public static List<RegistryEntry> getUserHistory(String username){
        List<RegistryEntry> allEntries = new ArrayList<>();
        String uId = getUserId(username);
        String sql = "SELECT user_id, document_title as title, access_date as access FROM activity_register WHERE user_id=?";

        try (Connection connection = getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)){

            stmt.setString(1, uId);
            try(ResultSet rs = stmt.executeQuery()) {
                if (!rs.isBeforeFirst()) throw new SQLException("User not found");
                while (rs.next()) {
                    String id = rs.getString("user_id");
                    String title = rs.getString("title");
                    String date = rs.getString("access");
                    RegistryEntry entry = new RegistryEntry(id, title, date);
                    allEntries.add(entry);
                }
            }

        } catch (SQLException e){
            e.printStackTrace();
        }

        return allEntries;
    }
    public static String getUsername(String user_id){
        String sql = "SELECT username from users where user_id=?";
        try (Connection connection = getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)){

            stmt.setInt(1, Integer.parseInt(user_id));
            try(ResultSet rs = stmt.executeQuery()) {
                if (!rs.isBeforeFirst()) throw new SQLException("User not found");

                rs.next();
                    return rs.getString(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "No info found";
    }
    public static String getUserId(String username){
        String sql = "SELECT user_id from users where username=?";
        try (Connection connection = getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)){

            stmt.setString(1, username);
            try(ResultSet rs = stmt.executeQuery()) {
                if (!rs.isBeforeFirst()) return null;
                rs.next();
                return rs.getString("user_id");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static String getUserSalt(String username){
        String sql = "select salt from users where username=?";
        try (Connection connection = getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)){

            stmt.setString(1, username);
            try(ResultSet rs = stmt.executeQuery()) {
                if (!rs.isBeforeFirst()) throw new SQLException("User not found");

                rs.next();
                return rs.getString(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "No info found";
    }

}