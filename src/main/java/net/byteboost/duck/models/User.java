package net.byteboost.duck.models;

import net.byteboost.duck.utils.GUIUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static net.byteboost.duck.utils.DBUtils.getConnection;


public class User {
    private String username;
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public int getId() {
        String sql = "SELECT user_id FROM users WHERE username=?";
        try {
            Connection cn = getConnection();
            PreparedStatement stmt = cn.prepareStatement(sql);
            ResultSet rs;

            stmt.setString(1,username);



            rs = stmt.executeQuery();

            if(!rs.isBeforeFirst()){
                System.out.println("User not found");

            }else {
                rs.next();
                return rs.getInt("user_id");
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return 0;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
