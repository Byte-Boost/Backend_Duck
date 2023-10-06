package net.byteboost.duck.utils;

import net.byteboost.duck.keys.DBKeys;

import java.sql.*;

public class DBUtils {

    public static Connection getConnection() throws SQLException {
        try {
            return DriverManager.getConnection(DBKeys.getSQLDatabase(), DBKeys.getSQLUser(), DBKeys.getSQLPassword());
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
                stmt.setString(2, password);
                stmt.execute();
                stmt.close();
                connection.close();

            }
            catch (SQLException exception) {
                throw new RuntimeException(exception);
            }
        }
        public static void AddRegistry(int user_id,String doc_title,Time time){
            String sql = "INSERT INTO activity_registry(user_id,document_title,access_time) VALUES (?,?,?)";
            try{

                Connection connection = getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql);

                stmt.setInt(1,user_id);
                stmt.setString(2,doc_title);
                stmt.setTime(3,time);
                stmt.execute();
                stmt.close();
                connection.close();
                
            }catch (SQLException e){
                throw  new RuntimeException(e);
            }
        }
        public static String getActivityInfo(String info, int user_id){
            String sql = "SELECT ? FROM activity_register WHERE user_id=?";
            try{

                Connection connection = getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql);
                stmt.setString(1,info);
                stmt.setInt(2,user_id);
                ResultSet rs = stmt.executeQuery(sql);
                return rs.getString(1);
            }catch (SQLException exception){
                throw new RuntimeException(exception);
            }
        }

}
