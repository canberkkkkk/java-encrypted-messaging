package model;

public class MessageModel {
  private String messageId;
  private String content;
  private String password;
  private String receiver;

  /**
   * Creates the message model with given message id, content, password and
   * receiver
   * 
   * @param messageId
   * @param content
   * @param password
   * @param receiver
   */
  public MessageModel(String messageId, String content, String password, String receiver) {
    this.messageId = messageId;
    this.content = content;
    this.password = password;
    this.receiver = receiver;
  }

  public String getMessageId() {
    return messageId;
  }

  public String getContent() {
    return content;
  }

  public String getPassword() {
    return password;
  }

  public String getReceiver() {
    return receiver;
  }
}
