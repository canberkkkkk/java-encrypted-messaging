package view;

import java.util.ArrayList;
import java.util.Arrays;
import java.awt.Component;

import javax.swing.*;
import controller.MessageController;
import response.ControllerResponse;

public class AccessMessageView {
  /**
   * Creates access message UI
   * 
   * @param viewRenderer
   */
  public AccessMessageView(ViewRenderer viewRenderer) {
    MessageController messageController = new MessageController();

    JTextField messageIdInput = new JTextField();
    JPasswordField messagePasswordInput = new JPasswordField();
    JTextField usernameInput = new JTextField();
    JPasswordField userPasswordInput = new JPasswordField();
    JLabel messageIdLabel = new JLabel("Message Codename:*");
    JLabel messagePasswordLabel = new JLabel("Message Password:*");
    JLabel usernameLabel = new JLabel("Username:*");
    JLabel userPasswordLabel = new JLabel("User Password:*");
    JSeparator firstSeperator = new JSeparator();
    JSeparator secondSeperator = new JSeparator();
    JCheckBox showPasswordCheckbox = new JCheckBox("Show password");
    JButton viewButton = new JButton("VIEW");
    JButton resetButton = new JButton("RESET");
    JButton homeButton = new JButton("HOME");

    messageIdInput.setBounds(160, 30, 210, 40);
    messageIdLabel.setBounds(20, 30, 200, 40);

    messagePasswordInput.setBounds(160, 80, 210, 40);
    messagePasswordLabel.setBounds(20, 80, 200, 40);

    usernameInput.setBounds(160, 170, 210, 40);
    usernameLabel.setBounds(20, 170, 200, 40);

    userPasswordInput.setBounds(160, 220, 210, 40);
    userPasswordLabel.setBounds(20, 220, 200, 40);

    firstSeperator.setBounds(20, 140, 350, 40);
    secondSeperator.setBounds(20, 325, 350, 40);
    showPasswordCheckbox.setBounds(15, 270, 350, 40);

    viewButton.setBounds(15, 360, 110, 40);
    resetButton.setBounds(137, 360, 110, 40);
    homeButton.setBounds(260, 360, 110, 40);

    // Show password
    showPasswordCheckbox.addActionListener(e -> {
      if (showPasswordCheckbox.isSelected())
        userPasswordInput.setEchoChar((char) 0);
      else
        userPasswordInput.setEchoChar('*');
    });

    // Retrieves message content
    viewButton.addActionListener(e -> {
      String messageId = messageIdInput.getText();
      String messagePassword = String.valueOf(messagePasswordInput.getPassword());
      String username = usernameInput.getText();
      String userPassword = String.valueOf(userPasswordInput.getPassword());
      ControllerResponse response = messageController.getMessageContent(messageId, messagePassword, username,
          userPassword);

      if (response.getStatus() == ControllerResponse.SUCCESS_STATUS)
        new MessageContentView(viewRenderer, response.getMessage());
      else
        viewRenderer.createDialog(response.getMessage());
    });

    // Reset inputs
    resetButton.addActionListener(e -> {
      messageIdInput.setText("");
      messagePasswordInput.setText("");
      usernameInput.setText("");
      userPasswordInput.setText("");
      showPasswordCheckbox.setSelected(false);
    });

    homeButton.addActionListener(e -> {
      new HomeView(viewRenderer);
    });

    viewRenderer.render("Message View", 390, 460,
        new ArrayList<Component>(Arrays.asList(messageIdInput, messagePasswordInput, usernameInput, userPasswordInput,
            messageIdLabel, messagePasswordLabel, usernameLabel, userPasswordLabel, firstSeperator, secondSeperator,
            showPasswordCheckbox, viewButton, resetButton, homeButton)));
  }
}
