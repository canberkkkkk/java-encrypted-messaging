package model;

public class UserModel {
  private String username;
  private String password;

  /**
   * Creates user model with given username and password
   *
   * @param username
   * @param password
   */
  public UserModel(String username, String password) {
    this.username = username;
    this.password = password;
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }
}
