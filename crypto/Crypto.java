package crypto;

import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class Crypto {
  /* Base64 will be used throughout the cryption process */
  private static final Encoder ENCODER = Base64.getEncoder().withoutPadding();
  private static final Decoder DECODER = Base64.getDecoder();

  /**
   * Creates hash using SHA-256 with given data
   * 
   * @param data
   * @return hashed data
   */
  public static String createHash(String data) {
    try {
      MessageDigest sha256;
      sha256 = MessageDigest.getInstance("SHA-256");
      byte[] hash = sha256.digest(data.getBytes());
      return new String(ENCODER.encodeToString(hash));
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
      System.exit(1);
      return null;
    }
  }

  /**
   * Gets message and key and creates encrypted string using DES with ECB mode
   * 
   * @param message
   * @param key
   * @return encrypted string
   */
  public static String encrypt(String message, String key) {
    byte[] encrypted = crypt(message.getBytes(), key.getBytes(), Cipher.ENCRYPT_MODE);
    return new String(ENCODER.encodeToString(encrypted));
  }

  /**
   * Gets message and key and creates decrypted string using DES with ECB mode
   * 
   * @param message
   * @param key
   * @return decrypted string
   */
  public static String decrypt(String message, String key) {
    byte[] decrypted = crypt(DECODER.decode(message), key.getBytes(), Cipher.DECRYPT_MODE);
    return new String(decrypted);
  }

  /**
   * Handles encryption, decryption process based on mode, uses DES in ECB mode
   * with PKCS5Padding
   * 
   * @param message
   * @param key
   * @param mode
   * @return
   */
  public static byte[] crypt(byte[] message, byte[] key, int mode) {
    try {
      Cipher cipher;
      SecretKey secretKey = new SecretKeySpec(key, 0, key.length, "DES");
      cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
      cipher.init(mode, secretKey);
      return cipher.doFinal(message);
    } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException
        | BadPaddingException e1) {
      e1.printStackTrace();
      System.exit(1);
      return null;
    }
  }
}
