package security;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class PasswordEncoder {

  private static final Charset UTF_8 = StandardCharsets.UTF_8;
  private static final String secretKey = "topSecretKey";

  public static String encode (String password) throws Exception {
    return EncryptorAesGcmPassword.encrypt(password.getBytes(UTF_8), secretKey);
  }

  public static String decode (String encryptedPassword) throws Exception {
    return EncryptorAesGcmPassword.decrypt(encryptedPassword, secretKey);
  }

}
