package net.byteboost.duck.utils;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Base64;

public class PasswordEncryptionUtils {

    public static String getSalt() throws Exception {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[32];
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }
    public static String getEncryptedPassword(String password, String salt) throws Exception {
        byte[] decSalt = Base64.getDecoder().decode(salt);
        KeySpec spec = new PBEKeySpec(password.toCharArray(), decSalt, 10012, 128);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        SecretKey key = factory.generateSecret(spec);
        byte[] EncryptedPass = key.getEncoded();
        return Base64.getEncoder().encodeToString(EncryptedPass);
    }
}
