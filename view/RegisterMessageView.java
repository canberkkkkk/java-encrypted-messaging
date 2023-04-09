package view;

import java.util.ArrayList;
import java.util.Arrays;
import java.awt.Component;

import javax.swing.*;

import controller.MessageController;
import controller.UserController;
import response.ControllerResponse;

public class RegisterMessageView {
  /**
   * Creates register message UI
   * 
   * @param viewRenderer
   */
  public RegisterMessageView(ViewRenderer viewRenderer) {
    UserController userController = new UserController();
    MessageController messageController = new MessageController();

    String[] usernames = userController.getUsernames();

    JComboBox<String> usernameComboBox = new JComboBox<String>(usernames);
    JPasswordField messagePasswordInput = new JPasswordField();
    JPasswordField messagePasswordConfirmInput = new JPasswordField();
    JTextField messageIdInput = new JTextField();
    JTextArea messageContentTextarea = new JTextArea();
    JLabel usernameLabel = new JLabel("Auth. Username:* ");
    JLabel messagePasswordLabel = new JLabel("Password:* ");
    JLabel messagePasswordConfirmLabel = new JLabel("Confirm Password:* ");
    JLabel messageIdLabel = new JLabel("Message Codename:* ");
    JLabel messageContentLabel = new JLabel("Enter Your Message:* ");
    JButton createMessageButton = new JButton("Create Message");
    JButton homeButton = new JButton("Home");
    JSeparator seperator = new JSeparator();
    JScrollPane scroll = new JScrollPane(messageContentTextarea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
        JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

    usernameComboBox.setBounds(170, 30, 200, 40);
    usernameLabel.setBounds(20, 30, 350, 40);

    messagePasswordInput.setBounds(170, 90, 200, 40);
    messagePasswordLabel.setBounds(20, 90, 350, 40);

    messagePasswordConfirmInput.setBounds(530, 90, 200, 40);
    messagePasswordConfirmLabel.setBounds(390, 90, 350, 40);

    messageIdInput.setBounds(170, 150, 200, 40);
    messageIdLabel.setBounds(20, 150, 350, 40);

    messageContentTextarea.setLineWrap(true);
    scroll.setBounds(175, 215, 550, 200);
    messageContentLabel.setBounds(20, 215, 350, 40);

    seperator.setBounds(20, 450, 700, 40);
    createMessageButton.setBounds(20, 490, 350, 40);
    homeButton.setBounds(375, 490, 350, 40);

    // Create message
    createMessageButton.addActionListener(e -> {
      String username = (String) usernameComboBox.getSelectedItem();
      String messagePassword = String.valueOf(messagePasswordInput.getPassword());
      String messagePasswordConfirm = String.valueOf(messagePasswordConfirmInput.getPassword());
      String messageId = messageIdInput.getText();
      String messageContent = messageContentTextarea.getText();
      ControllerResponse response = messageController.createMessage(messageId, messageContent, messagePassword,
          messagePasswordConfirm, username);

      viewRenderer.createDialog(response.getMessage());

      // Go to home screen is message creation is success
      if (response.getStatus() == ControllerResponse.SUCCESS_STATUS)
        new HomeView(viewRenderer);
    });

    homeButton.addActionListener(e -> {
      new HomeView(viewRenderer);
    });

    viewRenderer.render("Register Message Form", 750, 600,
        new ArrayList<Component>(Arrays.asList(usernameComboBox, messagePasswordInput, messagePasswordConfirmInput,
            messageIdInput, usernameLabel, messagePasswordLabel, messagePasswordConfirmLabel,
            messageIdLabel, messageContentLabel, createMessageButton, homeButton, seperator, scroll)));
  }
}
