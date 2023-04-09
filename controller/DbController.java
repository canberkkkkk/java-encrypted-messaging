package controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import crypto.Crypto;

public class DbController {
  private String tableName;
  private String secretKey;

  /**
   * General database controller, handles create and read processes with encrypted
   * database
   * 
   * @param tableName
   * @param secretKey
   */
  public DbController(String tableName, String secretKey) {
    this.tableName = tableName;
    this.secretKey = secretKey;
  }

  /**
   * Creates an encrypted data in given table name using secret key.
   * 
   * @param data
   */
  public void create(String data) {
    byte[] encryptedData = (Crypto.encrypt(read() + data, secretKey)).getBytes();
    try {
      Files.write(Paths.get(tableName), encryptedData, StandardOpenOption.CREATE);
    } catch (IOException e) {
      e.printStackTrace();
      System.exit(1);
    }
  }

  /**
   * Reads an encrypted database with given table name and decrypts it with secret
   * key
   * 
   * @return decrypted database as string
   */
  public String read() {
    File file = new File(tableName);
    try {
      try {
        String data = new String(Files.readAllBytes(file.toPath()));
        return Crypto.decrypt(data, secretKey);
      } catch (NoSuchFileException e) {
        file.createNewFile(); // create database if one does not exist
        return ""; // return nothing since the database is newly created
      }
    } catch (Exception e) {
      e.printStackTrace();
      System.exit(1);
      return null;
    }
  }
}
