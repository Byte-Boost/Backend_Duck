package net.byteboost.duck.utils;

import java.io.File;
import java.io.FileReader;
import java.util.Properties;

public class SecretUtils {
    public static String getSecret(String key){
        try{
            Properties prop = new Properties();
            File file = new File("src/main/resources/config.properties");
            FileReader fr = new FileReader(file);
            prop.load(fr);
            return prop.getProperty(key);
        } catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
