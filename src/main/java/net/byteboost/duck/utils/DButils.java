package net.byteboost.duck.utils;

import javafx.event.ActionEvent;
import net.byteboost.duck.keys.DBkeys;

import java.sql.*;
import java.util.Objects;


/**
 *
 * @author @jaquemfvs
 */

public class DButils {

    public static Connection getConnection() throws SQLException {
        try {
            return DriverManager.getConnection(DBkeys.getSQLDatabase(), DBkeys.getSQLUser(), DBkeys.getSQLPassword());
        }catch (SQLException exception){
            throw new RuntimeException(exception);
        }
    }

        public static void addUser(String username, String password){
            String sql = "INSERT INTO users(username,password) VALUES(?,?)";
            try {

                Connection connection =  getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql);

                stmt.setString(1, username);
                stmt.setString(2,password);
                stmt.execute();
                stmt.close();
                connection.close();

            }
            catch (SQLException exception) {
                throw new RuntimeException(exception);
            }
        }
        public static void logInUser(ActionEvent event,String username, String password){
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rspassword = null;
        try {
            connection = getConnection();
            stmt = connection.prepareStatement("SELECT password from users where username=?");
            stmt.setString(1,username);
            rspassword = stmt.executeQuery();

            if(!rspassword.isBeforeFirst()){
                System.out.println("User not found");
            }else {
                while (rspassword.next()){
                    String retrievedpassword = rspassword.getString("password");
                    if (retrievedpassword.equals(password)){
                        GUIutils.changeScene(event,"/fxml/upload.fxml","Duck - Upload",username, null, null);
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
}
