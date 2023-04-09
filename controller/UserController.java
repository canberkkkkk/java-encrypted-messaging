package controller;

import java.util.ArrayList;

import crypto.Crypto;
import model.UserModel;
import response.ControllerResponse;

public class UserController extends DbController {
  /**
   * User controller for creating, reading and accessing users in an
   * encrypted database
   */
  public UserController() {
    super("user.data", "k_e_y_(/");
  }

  /**
   * Authenticates user by comparing hashed passwords
   * 
   * @param user
   * @param username
   * @param password
   * @return
   */
  public boolean authenticateUser(UserModel user, String username, String password) {
    return user != null && user.getUsername().equals(username)
        && user.getPassword().equals(Crypto.createHash(password));
  }

  /**
   * Gets a single user from the database with given username
   * 
   * @param username
   * @return user if user is found, null otherwise
   */
  public UserModel getUser(String username) {
    ArrayList<UserModel> users = getUsers();

    for (UserModel user : users)
      if (user.getUsername().equals(username))
        return user;

    return null;
  }

  /**
   * Get usernames of all users for UI combobox to render
   * 
   * @return usernames of users in a string array
   */
  public String[] getUsernames() {
    ArrayList<UserModel> users = getUsers();
    String[] usernames = new String[users.size()];

    for (int i = 0; i < users.size(); i++) {
      usernames[i] = users.get(i).getUsername();
    }

    return usernames;
  }

  /**
   * Reads all users from the user database
   * 
   * @return users in an array list
   */
  public ArrayList<UserModel> getUsers() {
    String[] usersArr = super.read().split("</end>"); // use delimeter
    ArrayList<UserModel> users = new ArrayList<UserModel>();

    for (String user : usersArr) {
      if (user.length() == 0)
        continue;

      String[] userArr = user.split("</>");

      if (userArr.length < 2) {
        System.out.println("Error :: Bad user");
        continue;
      }

      String username = userArr[0], password = userArr[1];
      users.add(new UserModel(username, password));
    }

    return users;
  }

  /**
   * Checks if the given username is unique
   * 
   * @param username
   * @return true if the username is unique
   */
  public boolean uniqueUsername(String username) {
    ArrayList<UserModel> users = getUsers();

    for (UserModel user : users)
      if (user.getUsername().equals(username))
        return false;

    return true;
  }

  /**
   * Creates an encrypted user in the user database with given username and
   * password,
   * validates arguments along the process
   * 
   * @param username
   * @param password
   * @return Controller response
   */
  public ControllerResponse createUser(String username, String password) {
    if (username == null || password == null || username.length() == 0 || password.length() == 0)
      return new ControllerResponse(ControllerResponse.ERROR_STATUS, "Error :: Please fill all the fields");

    if (!uniqueUsername(username))
      return new ControllerResponse(ControllerResponse.ERROR_STATUS, "Error :: Username already exists");

    super.create(username + "</>" + Crypto.createHash(password) + "</end>");
    return new ControllerResponse(ControllerResponse.SUCCESS_STATUS, "Success :: User is successfully created");
  }
}
