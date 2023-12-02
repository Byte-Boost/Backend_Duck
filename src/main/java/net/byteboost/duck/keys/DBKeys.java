package net.byteboost.duck.keys;

import net.byteboost.duck.utils.SecretUtils;

import java.util.Properties;

/**
 * Contains key information about the database like SQL user  and SQL password.
 */

public class DBKeys {
    private static final String SQLUser = SecretUtils.getSecret("DB_USER");
    private static final String SQLPassword = SecretUtils.getSecret("DB_PASS");;

    private static final String SQLDatabase = "jdbc:mysql://localhost:3306/duck";

    public static String getSQLUser(){
        return SQLUser;
    }
    public static String getSQLPassword(){
        return SQLPassword;
    }
    public static String getSQLDatabase(){
        return SQLDatabase;
    }
}