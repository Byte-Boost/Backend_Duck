package net.byteboost.duck.keys;

import net.byteboost.duck.utils.SecretUtils;

public class EmailKeys {
    public static final String email = SecretUtils.getSecret("EMAIL_USER");
    public static final String password = SecretUtils.getSecret("EMAIL_PASS");
}
