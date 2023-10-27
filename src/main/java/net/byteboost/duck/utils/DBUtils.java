package net.byteboost.duck.utils;

import javafx.event.ActionEvent;
import net.byteboost.duck.keys.DBKeys;
import net.byteboost.duck.models.User;

import java.sql.*;

public class DBUtils {

    public static Connection getConnection() throws SQLException {
        try {
            return DriverManager.getConnection(DBKeys.getSQLDatabase(), DBKeys.getSQLUser(), DBKeys.getSQLPassword());
        }catch (SQLException exception){
            throw new RuntimeException(exception);
        }
    }

    public static void addUser(User user){
        String sql = "INSERT INTO users(username,password) VALUES(?,?)";
        try {

            Connection connection =  getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql);

            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.execute();
            stmt.close();
            connection.close();

        }
        catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }
    public static void logInUser(ActionEvent event, User user){
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rspassword = null;
        try {
            connection = getConnection();
            stmt = connection.prepareStatement("SELECT password from users where username=?");
            stmt.setString(1, user.getUsername());
            rspassword = stmt.executeQuery();

            if(!rspassword.isBeforeFirst()){
                System.out.println("User not found");
            }else {
                while (rspassword.next()){
                    String retrievedpassword = rspassword.getString("password");
                    if (retrievedpassword.equals(user.getPassword())){
                        GUIUtils.changeScene(event,"/fxml/upload.fxml","Duck - Upload");
                    }else {
                        System.out.println("password does not match username");
                    }
                }
            }

        }catch(SQLException e){
            e.printStackTrace();
        }finally {
            if (rspassword != null){
                try {
                    rspassword.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if (stmt != null){
                try {
                    stmt.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if (connection != null){
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

    }
    public static void AddRegistry(int user_id, String doc_title, Date time){
        String sql = "INSERT INTO activity_register(user_id,document_title,access_date) VALUES (?,?,?)";
        try{

            Connection connection = getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql);

            stmt.setInt(1,user_id);
            stmt.setString(2,doc_title);
            stmt.setDate(3,time);
            stmt.execute();
            stmt.close();
            connection.close();

        }catch (SQLException e){
            throw  new RuntimeException(e);
        }
    }
    public static String getActivityInfo(String info, String row,int equals){
        String sql = "SELECT " + info + " FROM activity_register WHERE "+ row +"="+ equals;
        try{

            Connection connection = getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery(sql);

            if(!rs.isBeforeFirst()){
                System.out.println("User not found");

            }else {
                rs.next();
                return rs.getString(1);
            }

        }catch (SQLException exception){
            throw new RuntimeException(exception);
        }
        return "No info found";
    }

    public static String getUsername(String user_id){

        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            connection = getConnection();
            stmt = connection.prepareStatement("SELECT username from users where user_id=?");
            stmt.setInt(1, Integer.parseInt(user_id));
            rs = stmt.executeQuery();
            if(!rs.isBeforeFirst()){
                System.out.println("User not found");

            }else {
                rs.next();
                return rs.getString(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return "No info found";
    }
}