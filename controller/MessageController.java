package controller;

import java.util.ArrayList;

import crypto.Crypto;
import model.*;
import response.ControllerResponse;

public class MessageController extends DbController {
  UserController userController;

  /**
   * Message controller for creating, reading and accessing messages in an
   * encrypted database
   */
  public MessageController() {
    super("message.data", "k_e_y_*&");
    this.userController = new UserController();
  }

  /**
   * Reads all the messages from the database
   * 
   * @return All messages in an array list
   */
  public ArrayList<MessageModel> getMessages() {
    String[] messagesArr = super.read().split("</end>"); // use delimeter
    ArrayList<MessageModel> messages = new ArrayList<MessageModel>();

    for (String message : messagesArr) {
      if (message.length() == 0)
        continue;

      String[] messageArr = message.split("</>");

      if (messageArr.length < 4) {
        System.out.println("Error :: Bad message");
        continue;
      }

      String messageId = messageArr[0], content = messageArr[1], password = messageArr[2], receiver = messageArr[3];
      messages.add(new MessageModel(messageId, content, password, receiver));
    }

    return messages;
  }

  /**
   * Gets a message by it's id
   * 
   * @param messageId
   * @return if found return message, null otherwise
   */
  public MessageModel getMessage(String messageId) {
    ArrayList<MessageModel> messages = getMessages();

    for (MessageModel message : messages)
      if (message.getMessageId().equals(messageId))
        return message;

    return null;
  }

  /**
   * Validates message id, password and username
   * 
   * @param messageId
   * @param messagePassword
   * @param username
   * @return true if all args are correct
   */
  public boolean validateArgs(String messageId, String messagePassword, String username) {
    return !(messageId == null || messagePassword == null || username == null || messageId.length() == 0
        || messagePassword.length() == 0 || username.length() == 0);
  }

  /**
   * Authenticates user and checks the message passwords, if all is correct
   * returns true
   * 
   * @param message
   * @param messageId
   * @param messagePassword
   * @param username
   * @param userPassword
   * @return true if message is accessible by given user
   */
  public boolean authenticateMessage(MessageModel message, String messageId, String messagePassword, String username,
      String userPassword) {
    UserModel user = userController.getUser(username);
    if (!userController.authenticateUser(user, username, userPassword) || message == null
        || !message.getPassword().equals(Crypto.createHash(messagePassword)))
      return false;

    return true;
  }

  /**
   * Conducts validation of args and message and also validates if the user is the
   * receiver
   * of a message. Returns the message content wrapped into controller response,
   * if all is validated
   * 
   * @param messageId
   * @param messagePassword
   * @param username
   * @param userPassword
   * @return message content wrapped into controller response
   */
  public ControllerResponse getMessageContent(String messageId, String messagePassword, String username,
      String userPassword) {
    if (!validateArgs(messageId, messagePassword, username) || userPassword == null || userPassword.length() == 0)
      return new ControllerResponse(ControllerResponse.ERROR_STATUS, "Error :: Please fill all the fields");

    MessageModel message = getMessage(messageId);
    if (!authenticateMessage(message, messageId, messagePassword, username, userPassword))
      return new ControllerResponse(ControllerResponse.ERROR_STATUS, "Error :: Fields are not correct");

    if (!username.equals(message.getReceiver()))
      return new ControllerResponse(ControllerResponse.ERROR_STATUS, "Error :: The message is not sent to " + username);

    return new ControllerResponse(ControllerResponse.SUCCESS_STATUS, message.getContent());
  }

  /**
   * Checks if the message id is unique
   * 
   * @param messageId
   * @return true if message id is unique
   */
  public boolean uniqueMessageId(String messageId) {
    ArrayList<MessageModel> messages = getMessages();

    for (MessageModel message : messages)
      if (message.getMessageId().equals(messageId))
        return false;

    return true;
  }

  /**
   * Creates encrypted message and appends it to message database, validates args
   * in the process
   * 
   * @param messageId
   * @param content
   * @param password
   * @param confirmPassword
   * @param username
   * @return Controller response with status
   */
  public ControllerResponse createMessage(String messageId, String content, String password, String confirmPassword,
      String username) {
    if (!validateArgs(messageId, password, username)
        || confirmPassword == null || confirmPassword.length() == 0
        || content == null || content.length() == 0)
      return new ControllerResponse(ControllerResponse.ERROR_STATUS, "Error :: Please fill all the fields");

    if (!password.equals(confirmPassword))
      return new ControllerResponse(ControllerResponse.ERROR_STATUS, "Error :: Passwords do not match");

    if (!uniqueMessageId(messageId))
      return new ControllerResponse(ControllerResponse.ERROR_STATUS, "Error :: Message codename already exists");

    super.create(messageId + "</>" + content + "</>" + Crypto.createHash(password) + "</>" + username + "</end>");
    return new ControllerResponse(ControllerResponse.SUCCESS_STATUS, "Success :: Message is created successfully");
  }
}
