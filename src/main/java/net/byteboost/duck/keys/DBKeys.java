package net.byteboost.duck.keys;

/**
 * Contains key information about the database like SQL user  and SQL password.
 */

public class DBKeys {
    private static String SQLUser = System.getenv("DUCK_DB_USER");
    private static String SQLPassword = System.getenv("DUCK_DB_PASSWORD");

    private static String SQLDatabase = "jdbc:mysql://localhost:3306/duck";

    public static String getSQLUser(){
        return SQLUser;
    }
    public static String getSQLPassword(){
        return SQLPassword;
    }
    public static String getSQLDatabase(){
        return SQLDatabase;
    }
    public void setSQLUser(String user){
        this.SQLUser = user;
    }
    public void setSQLPassword(String password){
        this.SQLPassword = password;
    }
    public void setSQLDatabase(String database){
        this.SQLDatabase = database;
    }
}